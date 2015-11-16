package com.jim_project.interprete.util;

import com.jim_project.interprete.componente.Ambito;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.parser.AnalizadorLexico;
import com.jim_project.interprete.parser.Parser;
import com.jim_project.interprete.parser.analizadormacros.MacrosParser;
import com.jim_project.interprete.parser.previo.PrevioAcciones;
import com.jim_project.interprete.parser.previo.PrevioParser;

public class ControladorEjecucion {

    public enum Etapa {

        INICIANDO, CARGANDO_FICHERO, COMPROBANDO_DIRECTORIO_MACROS,
        CARGANDO_MACROS, ANALIZANDO, EXPANDIENDO_MACROS, EJECUTANDO
    };

    private Ambito _ambito;
    private Programa _programa;

    private Etapa _etapa;

    private ArrayList<String> _lineas;
    private int _lineaActual;
    private boolean _salto;

    private StringBuilder _traza;
    // En cargar:
    //      _lineaActual = numeroLineas();

    public ControladorEjecucion(Ambito ambito, ArrayList<String> lineas) {
        _ambito = ambito;
        _programa = _ambito.programa();
        _lineas = lineas;
        // para que devuelva uno vacío si es el caso
        _traza = new StringBuilder();
    }

    public String traza() {
        return _traza.toString();
    }

    public void iniciar(int[] parametros) {
        _ambito.limpiar();
        comprobarDirectoriosMacros();
        cargarMacros();

        if (_programa.estadoOk()) {
            System.out.println("Analizando el programa...");
        }
        previo();

        if (_programa.estadoOk()) {
            do {
                // Se vuelve a pasar el previo para establecer las nuevas variables y
                // etiquetas tras la expansión de macros
                PrevioAcciones.expandir();
                previo();
            } while (PrevioAcciones.llamadasAMacros() > 0 && _programa.estadoOk());
        }

        if (_programa.estadoOk()) {
            // Asignar variables de entrada
            if (parametros != null) {
                asignarVariablesEntrada(parametros);
            }

            // Lanzar
            System.out.println("Ejecutando...");
            System.out.println("Si el programa no termina en unos segundos, "
                    + "probablemente haya caído en un bucle infinito.");

            ejecutar(_programa.modelo().parser());
        } else {
            _programa.estado(Programa.Estado.ERROR);
        }
    }

    private void previo() {
        _etapa = Etapa.ANALIZANDO;
        if (_programa.estadoOk()) {
            ejecutar(new PrevioParser(null));
        }
    }

    private int ejecutar(Parser parser) {
        if (!(parser instanceof PrevioParser)) {
            _etapa = Etapa.EJECUTANDO;
        }
        // System.out.println("Etapa en ejecutar: " + _etapa);

        _traza = new StringBuilder("[");
        _lineaActual = 0;
        _salto = false;
        AnalizadorLexico lex = parser.analizadorLexico();
        int instruccionesEjecutadas = 0;

        if (numeroLineas() > 0) {
            String linea = lineaSiguiente();

            do {
                if (_etapa == Etapa.EJECUTANDO) {
                    // System.out.println(_lineaActual + ": " + linea);
                    _traza.append(_ambito.estadoMemoria());

                    if (instruccionesEjecutadas > 0) {
                        _traza.append(",")
                                .append(System.getProperty("line.separator"));
                    }
                    _traza.append(_ambito.estadoMemoria());
                }

                try {
                    lex.yyclose();
                } catch (Exception ex) {
                    Error.deEjecucion();
                }
                lex.yyreset(new BufferedReader(new StringReader(linea)));

                parser.parse();

                if (!_salto) {
                    linea = lineaSiguiente();
                } else {
                    linea = lineaActual();
                    _salto = false;
                }

                ++instruccionesEjecutadas;
            } while (!finalizado() && _programa.estadoOk());
        }

        _traza.append(",")
                .append(System.getProperty("line.separator"));
        _traza.append(_ambito.estadoMemoria());
        _traza.append("]");

        // falta sumar las instrucciones ejecutadas por las macros
        return instruccionesEjecutadas;
    }

    public int numeroLineaActual() {
        return _lineaActual;
    }

    public void numeroLineaActual(int n) {
        if (lineaValida(n)) {
            _lineaActual = n;
        } else {
            terminar();
        }
    }

    public String lineaActual() {
        return finalizado() ? null : _lineas.get(_lineaActual - 1);
    }

    public String lineaSiguiente() {
        numeroLineaActual(_lineaActual + 1);
        return lineaActual();
    }

    public String linea(int n) {
        if (lineaValida(n)) {
            return _lineas.get(n);
        } else {
            return null;
        }
    }

    public boolean lineaValida(int numeroLinea) {
        return numeroLinea >= 1 && numeroLinea <= numeroLineas();
    }

    public int numeroLineas() {
        return _lineas.size();
    }

    public ArrayList<String> lineas() {
        return _lineas;
    }

    public void terminar() {
        _lineaActual = numeroLineas() + 1;
    }

    public boolean finalizado() {
        return numeroLineaActual() <= 0 || numeroLineaActual() > numeroLineas();
    }

    public void salto(int linea) {
        numeroLineaActual(linea);
        _salto = true;
    }

    private void asignarVariablesEntrada(int[] parametros) {
        for (int i = 0; i < parametros.length; ++i) {
            _ambito.variables().nuevaVariable("X" + (i + 1), parametros[i]);
        }
    }
    
    public void iniciarExpansionMacros() {
        /*
        limpiar();
        comprobarDirectoriosMacros();
        cargarMacros();

        if (estadoOk()) {
            System.out.println("Analizando el programa...");
        }
        previo();

        if (estadoOk()) {
            System.out.println("Expandiendo macros...");

            int llamadas = PrevioAcciones.llamadasAMacros();
            if (llamadas > 0) {
                PrevioAcciones.expandir();

                System.out.println();
                System.out.println(llamadas + " llamadas a macro expandidas.");
            } else {
                System.out.println();
                System.out.println("No hay llamadas a macros en el programa.");
            }
        }
        */
    }

    public void insertarExpansion(int linea, ArrayList<String> lineasExpansion) {
        /*
        _lineas.remove(linea - 1);
        _lineas.addAll(linea - 1, lineasExpansion);
        */
    }
}
