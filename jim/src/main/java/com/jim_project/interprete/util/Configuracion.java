package com.jim_project.interprete.util;

import java.util.Properties;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase encargada de gestionar la configuración del programa y su
 * almacenamiento en disco.
 */
public class Configuracion {

    private final Properties _propiedades;
    private final String _version;

    public Configuracion() {
        _propiedades = new Properties();
        _version = "1.0";
    }

    /**
     * Carga la configuración del programa desde el fichero "jim.cfg". Si el
     * fichero no existe, lo crea.
     */
    public Configuracion cargar() {
        return cargar(false);
    }

    public Configuracion cargar(boolean verbose) {
        File ficheroConfig = new File("jim.cfg");
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
     * Crea el fichero de configuración en el disco y almacena la configuración
     * por defecto.
     *
     * @param	ficheroConfig	El objeto File que hace referencia al fichero.
     * @see File
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
     * Almacena el fichero de configuración en el disco.
     */
    public void guardar(boolean verbose) {
        if (verbose) {
            System.out.println("Guardando configuración.");
        }

        File ficheroConfig = new File("jim.cfg");

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
     * @return	La ruta que apunta al directorio común.
     * @see String
     */
    public String rutaMacros() {
        return _propiedades.getProperty("rutaMacros");
    }

    /**
     * Define la ruta al directorio de macros comunes.
     *
     * @param	nuevaRuta	La ruta que apunta al directorio común.
     * @see String
     */
    public void rutaMacros(String nuevaRuta) {
        _propiedades.setProperty("rutaMacros", nuevaRuta);
    }

    /**
     * Devuelve la ruta al directorio de macros del modelo L.
     *
     * @return	La ruta que apunta al directorio del modelo L.
     * @see String
     */
    public String rutaMacrosL() {
        return _propiedades.getProperty("rutaMacrosL");
    }

    /**
     * Define la ruta al directorio de macros del modelo L.
     *
     * @param	nuevaRuta	La ruta que apunta al directorio del modelo L.
     * @see String
     */
    public void rutaMacrosL(String nuevaRuta) {
        _propiedades.setProperty("rutaMacrosL", nuevaRuta);
    }

    /**
     * Devuelve la ruta al directorio de macros del modelo Loop.
     *
     * @return	La ruta que apunta al directorio del modelo Loop.
     * @see String
     */
    public String rutaMacrosLoop() {
        return _propiedades.getProperty("rutaMacrosLoop");
    }

    /**
     * Define la ruta al directorio de macros del modelo Loop.
     *
     * @param	nuevaRuta	La ruta que apunta al directorio del modelo Loop.
     * @see String
     */
    public void rutaMacrosLoop(String nuevaRuta) {
        _propiedades.setProperty("rutaMacrosLoop", nuevaRuta);
    }

    /**
     * Devuelve la ruta al directorio de macros del modelo While.
     *
     * @return	La ruta que apunta al directorio del modelo While.
     * @see String
     */
    public String rutaMacrosWhile() {
        return _propiedades.getProperty("rutaMacrosWhile");
    }

    /**
     * Define la ruta al directorio de macros del modelo While.
     *
     * @param	nuevaRuta	La ruta que apunta al directorio del modelo While.
     * @see String
     */
    public void rutaMacrosWhile(String nuevaRuta) {
        _propiedades.setProperty("rutaMacrosWhile", nuevaRuta);
    }

    public boolean macrosPermitidas() {
        return _propiedades.getProperty("macrosPermitidas").equalsIgnoreCase("true");
    }

    public void macrosPermitidas(boolean b) {
        _propiedades.setProperty("macrosPermitidas", b ? "true" : "false");
    }

    public boolean modoFlexible() {
        //return _propiedades.getProperty("modoFlexible").equalsIgnoreCase("true");
        return false;
    }

    public void modoFlexible(boolean b) {
        //_propiedades.setProperty("modoFlexible", b ? "true" : "false");
    }

    public boolean salidaDetallada() {
        return _propiedades.getProperty("salidaDetallada").equalsIgnoreCase("true");
    }

    public void salidaDetallada(boolean b) {
        _propiedades.setProperty("salidaDetallada", b ? "true" : "false");
    }

    /**
     * Devuelve la versión actual del programa.
     *
     * @return	Una cadena representando la versión del programa.
     * @see String
     */
    public String version() {
        return _version;
    }
}
