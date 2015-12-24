package com.jim_project.interprete.util;

import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.componente.Ambito;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.AnalizadorLexico;
import com.jim_project.interprete.parser.Parser;
import com.jim_project.interprete.parser.lmodel.LParser;
import com.jim_project.interprete.parser.loopmodel.LoopParser;
import com.jim_project.interprete.parser.previo.PrevioParser;
import com.jim_project.interprete.parser.whilemodel.WhileParser;
import com.jim_project.interprete.util.gestor.GestorAmbitos;
import java.util.Arrays;

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

    public Ambito ambito() {
        return _ambito;
    }

    public String traza() {
        return _traza.toString();
    }

    public Etapa etapa() {
        return _etapa;
    }

    public void iniciar(String[] parametros) {
        // No es necesario ahora mismo puesto que el programa crea un nuevo
        // ámbito cada vez que se realiza una ejecución, pero lo dejo para
        // futuras optimizaciones
        // _ambito.limpiar();

        Ambito ambitoRaiz = _ambito.programa().gestorAmbitos().ambitoRaiz();

        if (_ambito.programa().verbose()) {
            if (_ambito == ambitoRaiz) {
                System.out.println("Analizando el programa");
            } else {
                System.out.println("Analizando macro \"" + _ambito.macroAsociada().id() + "\"");
            }
        }
        previo();

        if (_programa.estadoOk()) {
            // Asignar variables de entrada
            if (parametros != null) {
                asignarVariablesEntrada(parametros);
            }

            // Lanzar
            if (_ambito.programa().verbose()) {
                if (_ambito == ambitoRaiz) {
                    System.out.println("Ejecutando el programa");
                    //System.out.println("Si el programa no termina en unos segundos, "
                    //        + "probablemente haya caído en un bucle infinito.");
                } else {
                    System.out.println("Ejecutando macro \"" + _ambito.macroAsociada().id() + "\"");
                }
            }

            ejecutar(_programa.modelo().tipo());
        }
    }

    public String expandir() {
        if (_ambito.programa().verbose()) {
            System.out.println("Analizando el programa");
        }
        previo();

        ArrayList<LlamadaAMacro> llamadas
                = new ArrayList(_ambito.gestorLlamadasAMacro().llamadasAMacro());
        // llamadas.sort(new ComparadorLlamadasAMacro());
        if (_ambito.programa().verbose()) {
            if (!llamadas.isEmpty()) {
                System.out.println("Expandiendo macros");
            } else {
                System.out.println("No hay llamadas a macros");
            }
        }

        String expansion = "";
        ArrayList<String> lineas = new ArrayList(_lineas);
        int incremento = 0;

        for (int i = 0; i < llamadas.size() && _programa.estadoOk(); ++i) {
            LlamadaAMacro llamada = llamadas.get(i);

            if (_ambito.programa().verbose()) {
                System.out.println("   Expandiendo llamada a macro \"" + llamada.idMacro() + "\" en línea " + llamada.linea());
            }

            llamada.linea(llamada.linea() + incremento);
            String resultadoExpansion = _programa.gestorMacros().expandir(llamada);

            if (resultadoExpansion != null) {
                ArrayList<String> lineasExpansion = new ArrayList<>(
                        Arrays.asList(resultadoExpansion.split("[\n\r]+"))
                );

                lineas.remove(llamada.linea() - 1);
                lineas.addAll(llamada.linea() - 1, lineasExpansion);
                incremento += lineasExpansion.size() - 1;
            }

            if (_programa.worker() != null && _programa.worker().isCancelled()) {
                terminar();
            }
        }

        if (_programa.estadoOk()) {
            StringBuilder sb = new StringBuilder();
            lineas.forEach(
                    linea -> sb.append(linea).append("\n")
            );
            expansion = sb.toString();
        }

        return expansion;
    }

    private void previo() {
        _etapa = Etapa.ANALIZANDO;
        if (_programa.estadoOk()) {
            ejecutar(Modelo.Tipo.PREVIO);
        }
    }

    private Parser obtenerParser(Modelo.Tipo tipo, String lineaReader) {
        BufferedReader reader = new BufferedReader(new StringReader(lineaReader));

        switch (tipo) {
            case PREVIO:
                return new PrevioParser(reader, this);

            case L:
                return new LParser(reader, this);

            case LOOP:
                return new LoopParser(reader, this);

            case WHILE:
                return new WhileParser(reader, this);

            default:
                return null;
        }
    }

    private int ejecutar(Modelo.Tipo tipoParser) {
        if (tipoParser != Modelo.Tipo.PREVIO) {
            _etapa = Etapa.EJECUTANDO;
        }
        // System.out.println("Etapa en ejecutar: " + _etapa);

        _traza = new StringBuilder("[");
        _lineaActual = 0;
        _salto = false;
        int instruccionesEjecutadas = 0;

        if (numeroLineas() > 0) {
            String linea = lineaSiguiente();
            Parser parser = obtenerParser(tipoParser, linea);
            AnalizadorLexico lex = parser.analizadorLexico();

            do {
                // Límite de tamaño a la traza
                if (_traza.length() < 10000) {
                    if (instruccionesEjecutadas > 0) {
                        _traza.append(",")
                                .append(System.getProperty("line.separator"));
                    }
                    _traza.append(_ambito.estadoMemoria());
                }

                try {
                    lex.yyclose();
                } catch (Exception ex) {
                    _ambito.programa().error().deEjecucion();
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
                
                // Limpieza forzada cada 100 instrucciones
                if (instruccionesEjecutadas % 1000 == 0) {
                    Runtime.getRuntime().gc();
                }

                if (_programa.worker() != null && _programa.worker().isCancelled()) {
                    terminar();
                }
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

    private void asignarVariablesEntrada(String[] parametros) {
        if (_ambito == _ambito.programa().gestorAmbitos().ambitoRaiz()) {
            // En el caso del ámbito raíz no es necesario envolver la llamada a
            // parseInt en un bloque try-catch.
            for (int i = 0; i < parametros.length; ++i) {
                int valor = Integer.parseInt(parametros[i]);
                _ambito.variables().nuevaVariable("X" + (i + 1), valor);
            }
        } else {
            GestorAmbitos gestor = (GestorAmbitos) _ambito.gestor();
            Ambito ambitoPadre = gestor.ambitoPadre(_ambito.profundidad());

            for (int i = 0; i < parametros.length; ++i) {
                int valor = 0;
                try {
                    valor = Integer.parseInt(parametros[i]);
                } catch (NumberFormatException ex) {
                    Variable v = ambitoPadre.variables().obtenerVariable(parametros[i]);
                    if (v != null) {
                        valor = v.valor();
                    }
                }
                _ambito.variables().nuevaVariable("X" + (i + 1), valor);
            }
        }
    }
}
