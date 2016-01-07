package com.jim_project.interprete;

/**
 * Clase encargada de almacenar los argumentos del {@link Programa} que se
 * quiere lanza a ejecución.
 *
 * @author Alberto García González
 */
public class ArgumentosPrograma {

    /**
     * La ruta del fichero que contiene el código a ejecutar.
     */
    public String fichero;

    /**
     * El {@link Modelo} a simular.
     */
    public Modelo modelo;

    /**
     * Bandera que indica si se permite la ejecución de macros.
     */
    public boolean macrosPermitidas;

    /**
     * Bandera que indica si debe realizarse la traza de la ejecución.
     */
    public boolean traza;

    /**
     * Bandera que indica si deben trazarse las llamadas a macro.
     */
    public boolean trazarMacros;

    /**
     * Bandera que indica si está activado el modo de salida detallada.
     */
    public boolean verbose;

    /**
     * Los parámetros de entrada del programa.
     */
    public String[] parametros;

    /**
     * El objetivo final del programa: ejecutar o expandir el código.
     */
    public Programa.Objetivo objetivo;

    /**
     * Constructor predeterminado.
     */
    public ArgumentosPrograma() {
        fichero = null;
        modelo = null;
        //modoFlexible = false;
        macrosPermitidas = false;
        traza = false;
        trazarMacros = false;
        verbose = false;
        parametros = null;
        objetivo = null;
    }
}
