package org.alberto.interprete.util;

import org.alberto.interprete.componente.Variable;
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
import org.alberto.interprete.Interprete;
import org.alberto.interprete.parsers.AnalizadorLexico;
import org.alberto.interprete.parsers.Parser;
import org.alberto.interprete.parsers.analizadormacros.MacrosParser;
import org.alberto.interprete.parsers.previo.PrevioAcciones;
import org.alberto.interprete.parsers.previo.PrevioParser;

public class ControladorEjecucion {

    public enum Etapa {

        INICIANDO, CARGANDO_FICHERO, COMPROBANDO_DIRECTORIO_MACROS,
        CARGANDO_MACROS, ANALIZANDO, EXPANDIENDO_MACROS, EJECUTANDO
    };

    private Ambito _ambito;
    private Interprete _programa;

    private Etapa _etapa;

    private ArrayList<String> _lineas;
    private int _lineaActual;
    private boolean _salto;
    
    private StringBuilder _traza;
    // En cargar:
    //      _lineaActual = numeroLineas();

    public ControladorEjecucion(Ambito ambito) {
        _ambito = ambito;
        _programa = _ambito.programa();
        _traza = new StringBuilder();
    }
    
    public StringBuilder traza() {
        return _traza;
    }

    public void iniciar() {
        iniciar(null);
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
            _programa.estado(Interprete.Estado.ERROR);
        }
    }

    public void cargarMacros() {
        _etapa = Etapa.CARGANDO_MACROS;
        System.out.println("Cargando macros...");

        if (_programa.estadoOk()) {
            procesarFicherosMacros(Configuracion.rutaMacros());
        }

        if (_programa.estadoOk()) {
            procesarFicherosMacros(_programa.modelo().ruta());
        }
    }

    private void procesarFicherosMacros(String rutaMacros) {
        try {
            ArrayList<Path> rutas = new ArrayList<>();

            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(rutaMacros));
            for (Path path : directoryStream) {
                if (path.toFile().isFile()) {
                    rutas.add(path);
                }
            }
            directoryStream.close();

            for (Path p : rutas) {
                if (_programa.estadoOk()) {
                    System.out.println("   " + p.toAbsolutePath());

                    String fichero = p.getFileName().toString();
                    String rutaFichero = rutaMacros + "/" + fichero;
                    _programa.ficheroEnProceso(rutaFichero);
                    try {
                        MacrosParser macrosParser
                                = new MacrosParser(new FileReader(rutaFichero));
                        macrosParser.parse();
                    } catch (FileNotFoundException ex) {
                        Error.alCargarMacros(rutaFichero);
                    }
                }
            }
        } catch (NotDirectoryException ex) {
            Error.alComprobarDirectorio(rutaMacros);
        } catch (IOException ex) {
            Error.alObtenerListaFicherosMacros(rutaMacros);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void comprobarDirectoriosMacros() {
        _etapa = Etapa.COMPROBANDO_DIRECTORIO_MACROS;
        System.out.println("Comprobando directorios de macros...");
        /* Esquema por defecto:
         * macros/
         * ...l/
         * ...loop/
         * ...while/
         */
        comprobarDirectorio(new File(Configuracion.rutaMacros()));
        comprobarDirectorio(new File(Configuracion.rutaMacrosL()));
        comprobarDirectorio(new File(Configuracion.rutaMacrosLoop()));
        comprobarDirectorio(new File(Configuracion.rutaMacrosWhile()));
    }

    private void comprobarDirectorio(File directorio) {
        System.out.println("   " + directorio.getAbsolutePath());

        if (!directorio.exists()) {
            boolean creados = directorio.mkdirs();

            if (!creados) {
                Error.alCrearDirectoriosMacros();
            }
        } else {
            // es un directorio
            if (!directorio.isDirectory()) {
                Error.alComprobarDirectorio(directorio.getAbsolutePath());
            }

            if (!directorio.canRead()) {
                Error.alComprobarAccesoDirectorio(directorio.getAbsolutePath());
            }
        }
    }

    private void previo() {
        _etapa = Etapa.ANALIZANDO;
        if (_programa.estadoOk()) {
            ejecutar(new PrevioParser(null));
        }
    }

    private int ejecutar(Parser parser) {
        return ejecutar(parser, false);
    }

    private int ejecutar(Parser parser, boolean traza) {
        if (!(parser instanceof PrevioParser)) {
            _etapa = Etapa.EJECUTANDO;
        }
        // System.out.println("Etapa en ejecutar: " + _etapa);

        // _traza = new StringBuilder("[");
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
        /*
         _traza.append(",")
         .append(System.getProperty("line.separator"));
         _traza.append(estadoMemoria());
         _traza.append("]");
         */
        
        // falta sumar las instrucciones ejecutadas por las macros
        return instruccionesEjecutadas;
    }

    public int numeroLineaActual() {
        return _lineaActual;
    }

    // ControladorEjecucion
    public void numeroLineaActual(int n) {
        if (lineaValida(n)) {
            _lineaActual = n;
        } else {
            terminar();
        }
    }

    // ControladorEjecucion
    public String lineaActual() {
        return finalizado() ? null : _lineas.get(_lineaActual - 1);
    }

    // ControladorEjecucion
    public String lineaSiguiente() {
        numeroLineaActual(_lineaActual + 1);
        return lineaActual();
    }

    // ControladorEjecucion
    public String linea(int n) {
        if (lineaValida(n)) {
            return _lineas.get(n);
        } else {
            return null;
        }
    }

    // ControladorEjecucion
    public boolean lineaValida(int numeroLinea) {
        return numeroLinea >= 1 && numeroLinea <= numeroLineas();
    }

    // ControladorEjecucion
    public int numeroLineas() {
        return _lineas.size();
    }

    // ControladorEjecucion
    public ArrayList<String> lineas() {
        return _lineas;
    }

    // ControladorEjecucion
    public void terminar() {
        _lineaActual = numeroLineas() + 1;
    }

    // ControladorEjecucion
    public boolean finalizado() {
        return numeroLineaActual() <= 0 || numeroLineaActual() > numeroLineas();
    }

    // ControladorEjecucion
    public void salto(int linea) {
        numeroLineaActual(linea);
        _salto = true;
    }

    private void asignarVariablesEntrada(int[] parametros) {
        for (int n : parametros) {
            _ambito.variables().nuevaVariable(Variable.Tipo.ENTRADA, n);
        }
    }
}
