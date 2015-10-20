package org.alberto.interprete;

import org.alberto.interprete.parsers.analizadormacros.MacrosParser;
import org.alberto.interprete.parsers.lmodel.LParser;
import org.alberto.interprete.parsers.loopmodel.LoopParser;
import org.alberto.interprete.parsers.previo.PrevioAcciones;
import org.alberto.interprete.parsers.previo.PrevioParser;
import org.alberto.interprete.parsers.whilemodel.WhileParser;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.NotDirectoryException;

import org.alberto.interprete.parsers.*;
import java.io.FileNotFoundException;
import java.nio.file.DirectoryStream;
import java.util.Scanner;

public class Programa {

    private static String _ficheroPrograma;
    private static String _ficheroEnProceso;
    private static ArrayList<String> _lineas;
    private static int _lineaActual;
    
    private static Parser _parser;
    private static boolean _salto;
    

    public enum Estado {

        OK, ERROR
    };
    private static Estado _estado = Estado.OK;

    public enum Modelos {

        L, LOOP, WHILE
    };
    private static Modelos _modelo;

    private Programa() {
    }

    public static void estado(Estado estado) {
        _estado = estado;
    }

    public static Estado estado() {
        return _estado;
    }

    public static boolean estadoOk() {
        return _estado == Estado.OK;
    }
    
    public static String ficheroEnProceso() {
        return new File(_ficheroEnProceso).getName();
    }

    public static boolean cargar(String fichero, Modelos modelo) {
        try {
            _modelo = modelo;
            _lineas = new ArrayList<>();
            _ficheroPrograma = fichero;

            Scanner scanner = new Scanner(new File(fichero));
            while (scanner.hasNextLine()) {
                _lineas.add(scanner.nextLine());
            }
            scanner.close();

            _ficheroEnProceso = fichero;
            _lineaActual = numeroLineas();

            switch (_modelo) {

                case L:
                    _parser = new LParser(null);
                    break;

                case LOOP:
                    _parser = new LoopParser(null);
                    break;

                case WHILE:
                    _parser = new WhileParser(null);
                    break;
            }

            _estado = Estado.OK;

            return true;
        } catch (FileNotFoundException ex) {
            Error.alCargarPrograma(fichero);
        }

        return false;
    }

    public static void iniciar() {
        iniciar(null);
    }

    public static void iniciar(int[] parametros) {
        limpiar();
        comprobarDirectoriosMacros();
        cargarMacros();
        
        _ficheroEnProceso = _ficheroPrograma;
        if (estadoOk()) {
            System.out.println("Analizando el programa...");
        }
        previo();

        if (estadoOk()) {
            do {
                // Se vuelve a pasar el previo para establecer las nuevas variables y
                // etiquetas tras la expansión de macros
                PrevioAcciones.expandir();
                previo();
            } while (PrevioAcciones.llamadasAMacros() > 0 && estadoOk());
        }

        if (estadoOk()) {
            // Asignar variables de entrada
            if (parametros != null) {
                asignarVariablesEntrada(parametros);
            }

            // Lanzar
            // System.out.println("Ejecutando...");
            ejecutar(_parser);
        } else {
            _estado = Estado.ERROR;
        }
    }

    public static void iniciarExpansionMacros() {
        limpiar();
        comprobarDirectoriosMacros();
        cargarMacros();
        
        if (estadoOk()) {
            System.out.println("Analizando el programa...");
        }
        previo();

        if (estadoOk()) {
            int llamadas = PrevioAcciones.llamadasAMacros();
            if (llamadas > 0) {
                System.out.println("Expandiendo macros...");
                PrevioAcciones.expandir();
            } else {
                System.out.println("No hay llamadas a macros en el programa.");
            }
        }
    }

    private static void asignarVariablesEntrada(int[] parametros) {
        for (int i = 0; i < parametros.length; ++i) {
            Variable.set("X" + (i + 1), parametros[i]);
        }
    }

    private static void limpiar() {
        System.out.println("Limpiando memoria...");

        Variable.limpiar();
        Bucle.limpiar();
        Etiqueta.limpiar();
        Macro.limpiar();
        PrevioAcciones.limpiar();
    }

    public static void cargarMacros() {
        System.out.println("Cargando macros...");

        if (estadoOk()) {
            procesarFicherosMacros(Configuracion.rutaMacros());
        }

        if (estadoOk()) {
            procesarFicherosMacros(obtenerRutaModelo());
        }
    }

    private static void procesarFicherosMacros(String rutaMacros) {
        //Error.alObtenerListaFicherosMacros(rutaMacros);
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
                if (estadoOk()) {
                    System.out.println("   " + p.toAbsolutePath());

                    String fichero = p.getFileName().toString();
                    String rutaFichero = rutaMacros + "/" + fichero;
                    _ficheroEnProceso = rutaFichero;
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

    private static String obtenerRutaModelo() {
        String ruta = null;

        switch (_modelo) {
            case L:
                ruta = Configuracion.rutaMacrosL();
                break;

            case LOOP:
                ruta = Configuracion.rutaMacrosLoop();
                break;

            case WHILE:
                ruta = Configuracion.rutaMacrosWhile();
                break;
        }

        return ruta;
    }

    public static Modelos modelo() {
        return _modelo;
    }

    public static String nombreModelo() {
        String modelo = _modelo.toString();
        return modelo.charAt(0) + modelo.substring(1).toLowerCase();
    }

    private static void comprobarDirectoriosMacros() {
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

    private static void comprobarDirectorio(File directorio) {
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

    private static void previo() {
        if (estadoOk()) {
            ejecutar(new PrevioParser(null));
        }
    }

    public static void insertarExpansion(int linea, ArrayList<String> lineasExpansion) {
        _lineas.remove(linea - 1);
        _lineas.addAll(linea - 1, lineasExpansion);
    }

    private static void ejecutar(Parser parser) {
        ejecutar(parser, false);
    }

    private static void ejecutar(Parser parser, boolean traza) {
        _ficheroEnProceso = _ficheroPrograma;
        _lineaActual = 0;
        _salto = false;
        AnalizadorLexico lex = parser.analizadorLexico();

        if (numeroLineas() > 0) {
            String linea = lineaSiguiente();

            do {
                if (traza) {
                    System.out.println(_lineaActual + ": " + linea);
                    System.out.println(estadoMemoria());
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
            } while (!finalizado() && estadoOk());
        }
    }

    public static String estadoMemoria() {
        boolean comaAlFinal = false;
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(_lineaActual).append(", <");

        ArrayList<Variable> variables = Variable.variablesEntrada();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
            comaAlFinal = false;
        }

        variables = Variable.variablesLocales();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
        }
        concatenarVariable(Variable.variableSalida(), sb);

        sb.append(">)");

        return sb.toString();
    }

    private static void concatenarVariables(ArrayList<Variable> variables, StringBuilder sb) {
        concatenarVariable(variables.get(0), sb);

        for (int i = 1; i < variables.size(); ++i) {
            sb.append(", ");
            concatenarVariable(variables.get(i), sb);
        }
    }

    private static void concatenarVariable(Variable variable, StringBuilder sb) {
        sb.append(variable.id()).append(" = ").append(variable.valor());
    }

    public static int numeroLineaActual() {
        return _lineaActual;
    }

    public static void numeroLineaActual(int n) {
        if (lineaValida(n)) {
            _lineaActual = n;
        } else {
            terminar();
        }
    }

    public static String lineaActual() {
        return finalizado() ? null : _lineas.get(_lineaActual - 1);
    }

    public static String lineaSiguiente() {
        numeroLineaActual(_lineaActual + 1);
        return lineaActual();
    }

    public static String linea(int n) {
        if (lineaValida(n)) {
            return _lineas.get(n);
        } else {
            return null;
        }
    }

    public static boolean lineaValida(int numeroLinea) {
        return numeroLinea >= 1 && numeroLinea <= numeroLineas();
    }

    public static int numeroLineas() {
        return _lineas.size();
    }

    public static ArrayList<String> lineas() {
        return _lineas;
    }

    public static void terminar() {
        _lineaActual = numeroLineas() + 1;
    }

    public static boolean finalizado() {
        return numeroLineaActual() <= 0 || numeroLineaActual() > numeroLineas();
    }

    public static void salto(int linea) {
        numeroLineaActual(linea);
        _salto = true;
    }

    public static void imprimirComponentes() {
        Variable.pintar();

        if (_modelo == Modelos.L) {
            Etiqueta.pintar();
        } else {
            Bucle.pintar();
        }

        Macro.pintar();
    }

    public static void imprimirPrograma() {
        for (int i = 0; i < _lineas.size(); ++i) {
            System.out.println((i + 1) + ": " + _lineas.get(i));
        }

        System.out.println(numeroLineas() + " líneas.");
    }

    public static String obtenerPrograma() {
        StringBuilder sb = new StringBuilder();
        _lineas.forEach(
                linea -> sb.append(linea).append("\n")
        );

        return sb.toString();
    }

    public static int resultado() {
        return Variable.get("Y").valor();
    }
}