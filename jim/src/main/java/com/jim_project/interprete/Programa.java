package com.jim_project.interprete;

import com.jim_project.interprete.parser.analizadormacros.MacrosParser;
import com.jim_project.interprete.util.Error;
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
        _error = new Error(this);
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
    
    public GestorMacros gestorMacros() {
        return _gestorMacros;
    }

    public void iniciar(String[] parametros) {
        if (estadoOk()) {
            limpiar();
            comprobarDirectoriosMacros();
            cargarMacros();
            ficheroEnProceso(_fichero);

            if (estadoOk()) {
                ArrayList<String> lineas = new ArrayList<>();

                try (Scanner scanner = new Scanner(new File(_fichero))) {
                    while (scanner.hasNextLine()) {
                        lineas.add(scanner.nextLine());
                    }
                } catch (FileNotFoundException ex) {
                    _error.alCargarPrograma(_fichero);
                }

                _gestorAmbitos.nuevoAmbito(parametros, lineas).iniciar();
            }
        }
    }

    public void iniciarExpansionMacros() {
        limpiar();
        _gestorAmbitos.ambitoRaiz().iniciarExpansionMacros();
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
                                = new MacrosParser(new FileReader(rutaFichero), this);
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
}
