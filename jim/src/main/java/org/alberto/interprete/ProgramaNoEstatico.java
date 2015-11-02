package org.alberto.interprete;

import org.alberto.interprete.util.Error;
import org.alberto.interprete.util.Bucle;
import org.alberto.interprete.util.Configuracion;
import org.alberto.interprete.util.Macro;
import org.alberto.interprete.util.Variable;
import org.alberto.interprete.util.Etiqueta;
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
import org.alberto.interprete.util.gestor.GestorAmbitos;

public class ProgramaNoEstatico {

    public enum Estado {

        OK, ERROR
    };

    public enum Etapa {

        INICIANDO, CARGANDO_FICHERO, COMPROBANDO_DIRECTORIO_MACROS,
        CARGANDO_MACROS, ANALIZANDO, EXPANDIENDO_MACROS, EJECUTANDO
    };

    public enum Objetivo {

        EJECUTAR, EXPANDIR
    };

    private String _ficheroPrograma;
    private String _ficheroEnProceso;

    private Estado _estado;
    private Etapa _etapa;
    private Objetivo _objetivo;
    private boolean _modoFlexible;
    private boolean _macrosPermitidas;

    private Modelo _modelo;
    private GestorAmbitos _gestorAmbitos;

    // Cada ámbito lleva su propia traza.
    // private StringBuilder _traza;
    //
    public ProgramaNoEstatico(String fichero, Modelo modelo,
            Objetivo objetivo, boolean modoFlexible,
            boolean macrosPermitidas) {

        _ficheroPrograma = fichero;
        _ficheroEnProceso = fichero;
        _estado = Estado.OK;
        _etapa = Etapa.INICIANDO;
        _objetivo = objetivo;
        _modoFlexible = modoFlexible;
        _macrosPermitidas = macrosPermitidas;

        _modelo = modelo;
        _gestorAmbitos = new GestorAmbitos(this);

        try (Scanner scanner = new Scanner(new File(fichero))) {
            while (scanner.hasNextLine()) {
                _lineas.add(scanner.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Error.alCargarPrograma(fichero);
        }

        _ficheroEnProceso = fichero;

    }
    
    public String ficheroPrograma() {
        return new File(_ficheroPrograma).getName();
    }
    
    public String ficheroEnProceso() {
        return new File(_ficheroEnProceso).getName();
    }

    public void estado(Estado estado) {
        _estado = estado;
    }

    public Estado estado() {
        return _estado;
    }

    public boolean estadoOk() {
        return _estado == Estado.OK;
    }

    public void etapa(Etapa etapa) {
        _etapa = etapa;
    }

    public Etapa estapa() {
        return _etapa;
    }

    public void objetivo(Objetivo objetivo) {
        _objetivo = objetivo;
    }

    public Objetivo objetivo() {
        return _objetivo;
    }

    public void modoFlexible(boolean b) {
        _modoFlexible = b;
    }

    public boolean modoFlexible() {
        return _modoFlexible;
    }

    public void macrosPermitidas(boolean b) {
        _macrosPermitidas = b;
    }

    public boolean macrosPermitidas() {
        return _macrosPermitidas;
    }

    public GestorAmbitos gestorAmbitos() {
        return _gestorAmbitos;
    }

    public void iniciar() {
        iniciar(null);
    }

    public void iniciar(int[] parametros) {
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
            System.out.println("Ejecutando...");
            System.out.println("Si el programa no termina en unos segundos, "
                    + "probablemente haya caído en un bucle infinito.");
            ejecutar(_modelo.parser());
        } else {
            _estado = Estado.ERROR;
        }
    }

    public void iniciarExpansionMacros() {
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
    }

    private void asignarVariablesEntrada(int[] parametros) {
        for (int i = 0; i < parametros.length; ++i) {
            Variable.set("X" + (i + 1), parametros[i]);
        }
    }

    private void limpiar() {
        System.out.println("Limpiando memoria...");

        _gestorAmbitos.limpiar();
        PrevioAcciones.limpiar();
    }

    public void cargarMacros() {
        _etapa = Etapa.CARGANDO_MACROS;
        System.out.println("Cargando macros...");

        if (estadoOk()) {
            procesarFicherosMacros(Configuracion.rutaMacros());
        }

        if (estadoOk()) {
            procesarFicherosMacros(_modelo.ruta());
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

    public Modelo modelo() {
        return _modelo;
    }

    public String nombreModelo() {
        String modelo = _modelo.toString();
        return modelo.charAt(0) + modelo.substring(1).toLowerCase();
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
        if (estadoOk()) {
            ejecutar(new PrevioParser(null));
        }
    }

    public void insertarExpansion(int linea, ArrayList<String> lineasExpansion) {
        _lineas.remove(linea - 1);
        _lineas.addAll(linea - 1, lineasExpansion);
    }

    /**
     * *************************************************************************
     * Para rediseñar en base a métodos de Ambito o GestorAmbitos
     */
    private void ejecutar(Parser parser) {
        ejecutar(parser, false);
    }

    private void ejecutar(Parser parser, boolean traza) {
        if (!(parser instanceof PrevioParser)) {
            _etapa = Etapa.EJECUTANDO;
        }
        // System.out.println("Etapa en ejecutar: " + _etapa);

        // _traza = new StringBuilder("[");
        _ficheroEnProceso = _ficheroPrograma;
        _lineaActual = 0;
        _salto = false;
        AnalizadorLexico lex = parser.analizadorLexico();
        int instruccionesEjecutadas = 0;

        if (numeroLineas() > 0) {
            String linea = lineaSiguiente();

            do {
                if (_etapa == Etapa.EJECUTANDO) {
                    if (traza) {
                        // System.out.println(_lineaActual + ": " + linea);
                        System.out.println(estadoMemoria());
                    }
                    /*
                     if (instruccionesEjecutadas > 0) {
                     _traza.append(",")
                     .append(System.getProperty("line.separator"));
                     }
                     _traza.append(estadoMemoria());
                     */
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
            } while (!finalizado() && estadoOk());
        }
        /*
         _traza.append(",")
         .append(System.getProperty("line.separator"));
         _traza.append(estadoMemoria());
         _traza.append("]");
         */
    }

    public String estadoMemoria() {
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

    /*
     public String traza() {
     return _traza.toString();
     }
     */
    private void concatenarVariables(ArrayList<Variable> variables, StringBuilder sb) {
        concatenarVariable(variables.get(0), sb);

        for (int i = 1; i < variables.size(); ++i) {
            sb.append(", ");
            concatenarVariable(variables.get(i), sb);
        }
    }

    private void concatenarVariable(Variable variable, StringBuilder sb) {
        sb.append(variable.id()).append(" = ").append(variable.valor());
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

    public void imprimirComponentes() {
        Variable.pintar();

        if (_modelo.tipo() == Modelo.Tipo.L) {
            Etiqueta.pintar();
        } else {
            Bucle.pintar();
        }

        Macro.pintar();
    }

    public void imprimirPrograma() {
        for (int i = 0; i < _lineas.size(); ++i) {
            System.out.println((i + 1) + ": " + _lineas.get(i));
        }

        System.out.println(numeroLineas() + " líneas.");
    }

    public String obtenerPrograma() {
        StringBuilder sb = new StringBuilder();
        _lineas.forEach(
                linea -> sb.append(linea).append("\n")
        );

        return sb.toString();
    }

    public int resultado() {
        return Variable.get("Y").valor();
    }

    /**
     * *************************************************************************
     * Para refactor a Ambito
     */
    private ArrayList<String> _lineas;
    private int _lineaActual;
    private boolean _salto;
    // En cargar:
    //      _lineaActual = numeroLineas();
}
