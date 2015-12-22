package com.jim_project.interprete;

import com.jim_project.interprete.Programa.Objetivo;
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

    private ArgumentosPrograma _argumentos;
    private String _ficheroEnProceso;
    private ControladorEjecucion.Etapa _etapa;
    private Error _error;
    private Estado _estado;

    private GestorAmbitos _gestorAmbitos;
    private GestorMacros _gestorMacros;

    public Programa(ArgumentosPrograma argumentos) {
        _argumentos = argumentos;
        _ficheroEnProceso = argumentos.fichero;
        _estado = Estado.OK;
        _error = new Error(this);
        _gestorAmbitos = new GestorAmbitos(this);
        _gestorMacros = new GestorMacros(this);
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
        _argumentos.objetivo = objetivo;
    }

    public Objetivo objetivo() {
        return _argumentos.objetivo;
    }

    public boolean modoFlexible() {
        return _argumentos.modoFlexible;
    }

    public boolean macrosPermitidas() {
        return _argumentos.macrosPermitidas;
    }

    public boolean verbose() {
        return _argumentos.verbose;
    }

    public Error error() {
        return _error;
    }

    public Modelo modelo() {
        return _argumentos.modelo;
    }

    public String nombreModelo() {
        String modelo = _argumentos.modelo.toString();
        return modelo.charAt(0) + modelo.substring(1).toLowerCase();
    }

    public GestorAmbitos gestorAmbitos() {
        return _gestorAmbitos;
    }

    public GestorMacros gestorMacros() {
        return _gestorMacros;
    }

    public void iniciar() {
        if (estadoOk()) {
            limpiar();
            comprobarDirectoriosMacros();
            cargarMacros();
            ficheroEnProceso(_argumentos.fichero);

            if (estadoOk()) {
                ArrayList<String> lineas = new ArrayList<>();

                try (Scanner scanner = new Scanner(new File(_argumentos.fichero))) {
                    while (scanner.hasNextLine()) {
                        lineas.add(scanner.nextLine());
                    }
                } catch (FileNotFoundException ex) {
                    _error.alCargarPrograma(_argumentos.fichero);
                }

                _gestorAmbitos.nuevoAmbito(_argumentos.parametros, lineas).iniciar();
            }
        }
    }

    public void iniciar(String[] parametros) {
        if (estadoOk()) {
            limpiar();
            comprobarDirectoriosMacros();
            cargarMacros();
            ficheroEnProceso(_argumentos.fichero);

            if (estadoOk()) {
                ArrayList<String> lineas = new ArrayList<>();

                try (Scanner scanner = new Scanner(new File(_argumentos.fichero))) {
                    while (scanner.hasNextLine()) {
                        lineas.add(scanner.nextLine());
                    }
                } catch (FileNotFoundException ex) {
                    _error.alCargarPrograma(_argumentos.fichero);
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
        if (_argumentos.verbose) {
        System.out.println("Cargando macros");
        }

        if (estadoOk()) {
            procesarFicherosMacros(Configuracion.rutaMacros());
        }

        if (estadoOk()) {
            procesarFicherosMacros(_argumentos.modelo.ruta());
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
                    if (_argumentos.verbose) {
                        System.out.println("   " + p.toAbsolutePath());
                    }

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
        if (_argumentos.verbose) {
            System.out.println("Comprobando directorios de macros");
        }
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
        if (_argumentos.verbose) {
            System.out.println("   " + directorio.getAbsolutePath());
        }

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
