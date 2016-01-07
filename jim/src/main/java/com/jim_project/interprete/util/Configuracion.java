package com.jim_project.interprete.util;

import java.util.Properties;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que aporta métodos para almacenar físicamente y recuperar la
 * configuración de la aplicación, así como para editar sus distintas
 * propiedades.
 */
public class Configuracion {

    private final Properties _propiedades;
    private final String _version;

    /**
     * Constructor de clase.
     */
    public Configuracion() {
        _propiedades = new Properties();
        _version = "1.0";
    }

    /**
     * Carga la configuración del programa desde el fichero "jim.properties". Si el
     * fichero no existe, lo crea.
     *
     * @return Una referencia a este objeto.
     */
    public Configuracion cargar() {
        return cargar(false);
    }

    /**
     * Carga la configuración del programa desde el fichero "jim.properties". Si el
     * fichero no existe, lo crea.
     *
     * @param verbose Bandera que indica si la salida detallada está activada.
     * @return Una referencia a este objeto.
     */
    public Configuracion cargar(boolean verbose) {
        File ficheroConfig = new File("jim.properties");
        if (verbose) {
            System.out.println("Cargando configuración del programa.");
        }

        if (!ficheroConfig.exists()) {
            crearFicheroConfiguracion(ficheroConfig, verbose);
        } else {
            try (FileReader fr = new FileReader(ficheroConfig)) {
                _propiedades.load(fr);

                if (_propiedades.stringPropertyNames().size() != 6) {
                    _propiedades.clear();
                    // Asigna las propiedades que no estén en el fichero de configuración
                    _propiedades.setProperty("rutaMacros", _propiedades.getProperty("rutaMacros", "macros"));
                    _propiedades.setProperty("rutaMacrosL", _propiedades.getProperty("rutaMacrosL", "macros/l"));
                    _propiedades.setProperty("rutaMacrosLoop", _propiedades.getProperty("rutaMacrosLoop", "macros/loop"));
                    _propiedades.setProperty("rutaMacrosWhile", _propiedades.getProperty("rutaMacrosWhile", "macros/while"));
                    _propiedades.setProperty("macrosPermitidas", _propiedades.getProperty("macrosPermitidas", "true"));
                    //_propiedades.setProperty("modoFlexible", _propiedades.getProperty("modoFlexible", "false"));
                    _propiedades.setProperty("salidaDetallada", _propiedades.getProperty("salidaDetallada", "true"));

                    guardar(verbose);
                }

                if (verbose) {
                    System.out.println("Fichero de configuración cargado con éxito.");
                }
            } catch (Exception ex) {
                Error.alCargarConfiguracion(ficheroConfig.getAbsolutePath());
            }
        }

        return this;
    }

    /**
     * Crea el fichero "jim.properties" y almacena la configuración.
     *
     * @param ficheroConfig El fichero a crear.
     * @param verbose Bandera que indica si la salida detallada está activada.
     */
    private void crearFicheroConfiguracion(File ficheroConfig, boolean verbose) {
        if (verbose) {
            System.out.println("Creando fichero de configuración.");
        }

        try (FileWriter fw = new FileWriter(ficheroConfig)) {
            ficheroConfig.createNewFile();

            _propiedades.setProperty("rutaMacros", "macros");
            _propiedades.setProperty("rutaMacrosL", "macros/l");
            _propiedades.setProperty("rutaMacrosLoop", "macros/loop");
            _propiedades.setProperty("rutaMacrosWhile", "macros/while");
            _propiedades.setProperty("macrosPermitidas", "true");
            //_propiedades.setProperty("modoFlexible", "false");
            _propiedades.setProperty("salidaDetallada", "true");
            _propiedades.store(fw, null);

            if (verbose) {
                System.out.println("Fichero de configuración creado con éxito.");
            }
        } catch (IOException ex) {
            Error.alCrearFicheroConfiguracion(ficheroConfig.getAbsolutePath());
        }
    }

    /**
     * Almacena las propiedades en el fichero de configuración.
     *
     * @param verbose Bandera que indica si la salida detallada está activada.
     */
    public void guardar(boolean verbose) {
        if (verbose) {
            System.out.println("Guardando configuración.");
        }

        File ficheroConfig = new File("jim.properties");

        if (!ficheroConfig.exists()) {
            crearFicheroConfiguracion(ficheroConfig, verbose);
        } else {
            try (FileWriter fw = new FileWriter(ficheroConfig)) {
                _propiedades.store(fw, null);
            } catch (Exception ex) {
                Error.alGuardarConfiguracion();
            }
        }
    }

    /**
     * Devuelve la ruta al directorio de macros comunes.
     *
     * @return La ruta al directorio de macros comunes.
     */
    public String rutaMacros() {
        return _propiedades.getProperty("rutaMacros");
    }

    /**
     * Cambia la ruta al directorio de macros comunes.
     *
     * @param nuevaRuta La nueva ruta al directorio de macros comunes.
     */
    public void rutaMacros(String nuevaRuta) {
        _propiedades.setProperty("rutaMacros", nuevaRuta);
    }

    /**
     * Devuelve la ruta al directorio de macros del modelo L.
     *
     * @return La ruta al directorio de macros del modelo L.
     */
    public String rutaMacrosL() {
        return _propiedades.getProperty("rutaMacrosL");
    }

    /**
     * Cambia la ruta al directorio de macros del modelo L.
     *
     * @param nuevaRuta La nueva ruta al directorio de macros del modelo L.
     */
    public void rutaMacrosL(String nuevaRuta) {
        _propiedades.setProperty("rutaMacrosL", nuevaRuta);
    }

    /**
     * Devuelve la ruta al directorio de macros del modelo Loop.
     *
     * @return La ruta al directorio de macros del modelo Loop.
     */
    public String rutaMacrosLoop() {
        return _propiedades.getProperty("rutaMacrosLoop");
    }

    /**
     * Cambia la ruta al directorio de macros del modelo Loop.
     *
     * @param nuevaRuta La nueva ruta al directorio de macros del modelo Loop.
     */
    public void rutaMacrosLoop(String nuevaRuta) {
        _propiedades.setProperty("rutaMacrosLoop", nuevaRuta);
    }

    /**
     * Devuelve la ruta al directorio de macros del modelo While.
     *
     * @return La ruta al directorio de macros del modelo While.
     */
    public String rutaMacrosWhile() {
        return _propiedades.getProperty("rutaMacrosWhile");
    }

    /**
     * Cambia la ruta al directorio de macros del modelo While.
     *
     * @param nuevaRuta La nueva ruta al directorio de macros del modelo While.
     */
    public void rutaMacrosWhile(String nuevaRuta) {
        _propiedades.setProperty("rutaMacrosWhile", nuevaRuta);
    }

    /**
     * Indica si la ejecución de macros está permitida en el fichero de
     * configuración.
     *
     * @return {@code true}, si las macros están permitidas; {@code false} en
     * caso contrario.
     */
    public boolean macrosPermitidas() {
        return _propiedades.getProperty("macrosPermitidas").equalsIgnoreCase("true");
    }

    /**
     * Cambia la opción de ejecución de macros en el fichero de configuración.
     *
     * @param b Indica si las macros estarán permitidas o no.
     */
    public void macrosPermitidas(boolean b) {
        _propiedades.setProperty("macrosPermitidas", b ? "true" : "false");
    }

    /*
     public boolean modoFlexible() {
     //return _propiedades.getProperty("modoFlexible").equalsIgnoreCase("true");
     return false;
     }

     public void modoFlexible(boolean b) {
     //_propiedades.setProperty("modoFlexible", b ? "true" : "false");
     }
     */
    /**
     * Indica si la salida detallada está activada en el fichero de
     * configuración.
     *
     * @return {@code true}, si la salida detallada está activada; {@code false}
     * en caso contrario.
     */
    public boolean salidaDetallada() {
        return _propiedades.getProperty("salidaDetallada").equalsIgnoreCase("true");
    }

    /**
     * Cambia la opción de salida detallada en el fichero de configuración.
     *
     * @param b Indica si la salida detallada estará activada o no.
     */
    public void salidaDetallada(boolean b) {
        _propiedades.setProperty("salidaDetallada", b ? "true" : "false");
    }

    /**
     * Devuelve la versión actual del programa.
     *
     * @return Una cadena que contiene la versión del programa.
     */
    public String version() {
        return _version;
    }
}
