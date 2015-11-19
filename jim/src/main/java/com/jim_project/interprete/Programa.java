package com.jim_project.interprete;

import com.jim_project.interprete.parser.analizadormacros.MacrosParser;
import com.jim_project.interprete.util.Error;
import com.jim_project.interprete.parser.previo.PrevioAcciones;
import com.jim_project.interprete.util.Configuracion;
import com.jim_project.interprete.util.ControladorEjecucion;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.jim_project.interprete.util.gestor.GestorAmbitos;
import com.jim_project.interprete.util.gestor.GestorMacros;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Programa {

    public enum Estado {

        OK, ERROR
    };

    public enum Objetivo {

        EJECUTAR, EXPANDIR
    };

    private String _fichero;
    private String _ficheroEnProceso;

    private Estado _estado;
    private ControladorEjecucion.Etapa _etapa;
    private Objetivo _objetivo;
    private boolean _modoFlexible;
    private boolean _macrosPermitidas;
    private Error _error;

    private Modelo _modelo;
    private GestorAmbitos _gestorAmbitos;
    private GestorMacros _gestorMacros;

    public Programa(String fichero, Modelo modelo,
            Objetivo objetivo, boolean modoFlexible,
            boolean macrosPermitidas) {

        _fichero = fichero;
        _ficheroEnProceso = fichero;
        _estado = Estado.OK;
        _objetivo = objetivo;
        _modoFlexible = modoFlexible;
        _macrosPermitidas = macrosPermitidas;
        _modelo = modelo;
        _gestorAmbitos = new GestorAmbitos(this);
        _gestorMacros = new GestorMacros(this);
        _ficheroEnProceso = fichero;
    }

    public String ficheroEnProceso() {
        return new File(_ficheroEnProceso).getName();
    }

    public void ficheroEnProceso(String ficheroEnProceso) {
        _ficheroEnProceso = ficheroEnProceso;
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
    
    public ControladorEjecucion.Etapa etapa() {
        if (_gestorAmbitos.vacio()) {
            return _etapa;
        } else {
            return _gestorAmbitos.ambitoActual().controladorEjecucion().etapa();
        }
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
    
    public Error error() {
        return _error;
    }

    public Modelo modelo() {
        return _modelo;
    }

    public String nombreModelo() {
        String modelo = _modelo.toString();
        return modelo.charAt(0) + modelo.substring(1).toLowerCase();
    }

    public GestorAmbitos gestorAmbitos() {
        return _gestorAmbitos;
    }

    public void iniciar(int[] parametros) {
        if (estadoOk()) {
            comprobarDirectoriosMacros();
            cargarMacros();

            if (estadoOk()) {
                limpiar();

                ArrayList<String> lineas = new ArrayList<>();

                try (Scanner scanner = new Scanner(new File(_fichero))) {
                    while (scanner.hasNextLine()) {
                        lineas.add(scanner.nextLine());
                    }
                } catch (FileNotFoundException ex) {
                    _error.alCargarPrograma(_fichero);
                }

                _gestorAmbitos.nuevoAmbito(parametros, lineas);
                _gestorAmbitos.ambitoRaiz().iniciar(parametros);
            }
        }
    }

    public void iniciarExpansionMacros() {
        if (estadoOk()) {
            limpiar();
            _gestorAmbitos.ambitoRaiz().iniciarExpansionMacros();
        }
    }

    public int resultado() {
        return _gestorAmbitos.ambitoRaiz().resultado();
    }

    public String traza() {
        return _gestorAmbitos.ambitoRaiz().controladorEjecucion().traza();
    }

    private void limpiar() {
        _gestorAmbitos.limpiar();
        _gestorMacros.limpiar();
    }

    @Override
    public String toString() {
        ArrayList<String> lineas = _gestorAmbitos.ambitoRaiz().controladorEjecucion().lineas();
        StringBuilder sb = new StringBuilder();

        lineas.forEach(
                linea -> sb.append(linea).append("\n")
        );

        return sb.toString();
    }

    public String toStringDetallado() {
        ArrayList<String> lineas = _gestorAmbitos.ambitoRaiz().controladorEjecucion().lineas();
        int numeroLineas = _gestorAmbitos.ambitoRaiz().controladorEjecucion().numeroLineas();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lineas.size(); ++i) {
            sb.append(i + 1).append(": ").append(lineas.get(i)).append("\n");
        }
        sb.append(numeroLineas).append(" líneas.").append("\n");

        return sb.toString();
    }

    /**
     * Delegar en clase GestorMacros?
     */
    public void cargarMacros() {
        _etapa = ControladorEjecucion.Etapa.CARGANDO_MACROS;
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
            DirectoryStream<Path> directoryStream
                    = Files.newDirectoryStream(Paths.get(rutaMacros));

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
                    ficheroEnProceso(rutaFichero);
                    try {
                        MacrosParser macrosParser
                                = new MacrosParser(new FileReader(rutaFichero));
                        macrosParser.parse();
                    } catch (FileNotFoundException ex) {
                        _error.alCargarMacros(rutaFichero);
                    }
                }
            }
        } catch (NotDirectoryException ex) {
            _error.alComprobarDirectorio(rutaMacros);
        } catch (IOException ex) {
            _error.alObtenerListaFicherosMacros(rutaMacros);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void comprobarDirectoriosMacros() {
        _etapa = ControladorEjecucion.Etapa.COMPROBANDO_DIRECTORIO_MACROS;
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
                _error.alCrearDirectoriosMacros();
            }
        } else {
            // es un directorio
            if (!directorio.isDirectory()) {
                _error.alComprobarDirectorio(directorio.getAbsolutePath());
            }

            if (!directorio.canRead()) {
                _error.alComprobarAccesoDirectorio(directorio.getAbsolutePath());
            }
        }
    }

    /**
     * Para refactor
     */
    /*
     private ArrayList<String> _lineas; // OK
     private int _lineaActual; // OK
     private boolean _salto; // OK
     // En cargar:
     //      _lineaActual = numeroLineas();

     // Ambito
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

     // Ambito
     public void insertarExpansion(int linea, ArrayList<String> lineasExpansion) {
     _lineas.remove(linea - 1);
     _lineas.addAll(linea - 1, lineasExpansion);
     }

     // Ambito
     public String obtenerPrograma() {
     StringBuilder sb = new StringBuilder();
     _lineas.forEach(
     linea -> sb.append(linea).append("\n")
     );

     return sb.toString();
     }

     // ControladorEjecucion OK
     public int resultado() {
     return Variable.get("Y").valor();
     }

     // Ambito OK BORRAR
     private void asignarVariablesEntrada(int[] parametros) {
     for (int i = 0; i < parametros.length; ++i) {
     Variable.set("X" + (i + 1), parametros[i]);
     }
     }

     // ControladorEjecucion OK
     public void cargarMacros() {
     System.out.println("Cargando macros...");

     if (estadoOk()) {
     procesarFicherosMacros(Configuracion.rutaMacros());
     }

     if (estadoOk()) {
     procesarFicherosMacros(_modelo.ruta());
     }
     }

     // ControladorEjecucion OK
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

     // ControladorEjecucion OK
     private void comprobarDirectoriosMacros() {
     System.out.println("Comprobando directorios de macros...");
     /* Esquema por defecto:
     * macros/
     * ...l/
     * ...loop/
     * ...while/
         
     comprobarDirectorio(new File(Configuracion.rutaMacros()));
     comprobarDirectorio(new File(Configuracion.rutaMacrosL()));
     comprobarDirectorio(new File(Configuracion.rutaMacrosLoop()));
     comprobarDirectorio(new File(Configuracion.rutaMacrosWhile()));
     }

     // ControladorEjecucion OK
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

     // ControladorEjecucion OK
     // Deberá pasar el analizador previo en todos los ámbitos
     private void previo() {
     if (estadoOk()) {
     ejecutar(new PrevioParser(null));
     }
     }

     // ControladorEjecucion OK
     private void ejecutar(Parser parser) {
     ejecutar(parser, false);
     }

     // ControladorEjecución OK
     private void ejecutar(Parser parser, boolean traza) {
     // _traza = new StringBuilder("[");
     _lineaActual = 0;
     _salto = false;
     AnalizadorLexico lex = parser.analizadorLexico();
     int instruccionesEjecutadas = 0;

     if (numeroLineas() > 0) {
     String linea = lineaSiguiente();

     do {
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
         
     }

     // Ambito OK
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

     // Ambito OK
     /*
     public String traza() {
     return _traza.toString();
     }
     
     private void concatenarVariables(ArrayList<Variable> variables, StringBuilder sb) {
     concatenarVariable(variables.get(0), sb);

     for (int i = 1; i < variables.size(); ++i) {
     sb.append(", ");
     concatenarVariable(variables.get(i), sb);
     }
     }

     // Ambito OK
     private void concatenarVariable(Variable variable, StringBuilder sb) {
     sb.append(variable.id()).append(" = ").append(variable.valor());
     }

     // ControladorEjecucion OK
     public int numeroLineaActual() {
     return _lineaActual;
     }

     // ControladorEjecucion OK
     public void numeroLineaActual(int n) {
     if (lineaValida(n)) {
     _lineaActual = n;
     } else {
     terminar();
     }
     }

     // ControladorEjecucion OK
     public String lineaActual() {
     return finalizado() ? null : _lineas.get(_lineaActual - 1);
     }

     // ControladorEjecucion OK
     public String lineaSiguiente() {
     numeroLineaActual(_lineaActual + 1);
     return lineaActual();
     }

     // ControladorEjecucion OK
     public String linea(int n) {
     if (lineaValida(n)) {
     return _lineas.get(n);
     } else {
     return null;
     }
     }

     // ControladorEjecucion OK
     public boolean lineaValida(int numeroLinea) {
     return numeroLinea >= 1 && numeroLinea <= numeroLineas();
     }

     // ControladorEjecucion OK
     public int numeroLineas() {
     return _lineas.size();
     }

     // ControladorEjecucion OK
     public ArrayList<String> lineas() {
     return _lineas;
     }

     // ControladorEjecucion OK
     public void terminar() {
     _lineaActual = numeroLineas() + 1;
     }

     // ControladorEjecucion OK
     public boolean finalizado() {
     return numeroLineaActual() <= 0 || numeroLineaActual() > numeroLineas();
     }

     // ControladorEjecucion OK
     public void salto(int linea) {
     numeroLineaActual(linea);
     _salto = true;
     }

     // Ambito OK
     public void imprimirComponentes() {
     Variable.pintar();

     if (_modelo.tipo() == Modelo.Tipo.L) {
     Etiqueta.pintar();
     } else {
     Bucle.pintar();
     }

     Macro.pintar();
     }
     */
}
