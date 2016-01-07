package com.jim_project.interprete.gui;

import java.io.PrintStream;
import javax.swing.JTextArea;

/**
 * Clase utiliza dos {@link java.io.PrintStream} y dos {@link TextAreaOutputStream} para
 * facilitar el volcado de la salida estándar y de errores del sistema en elementos
 * de la interfaz gráfica.
 *
 * @author Alberto García González
 */
public class Consola {

    private final JTextArea _estandar;
    private final JTextArea _errores;
    private final PrintStream _salidaEstandar;
    private final PrintStream _salidaErrores;

    /**
     * Constructor de clase.
     *
     * @param estandar Referencia al {@link JTextArea} que muestra la salida
     * estándar.
     * @param errores Referencia al {@link JTextArea} que muestra la salida de
     * errores.
     */
    public Consola(JTextArea estandar, JTextArea errores) {
        _estandar = estandar;
        _errores = errores;

        _salidaEstandar = new PrintStream(new TextAreaOutputStream(_estandar));
        _salidaErrores = new PrintStream(new TextAreaOutputStream(_errores));
    }

    /**
     * Devuelve el {@link PrintStream} de salida estándar.
     *
     * @return El {@link PrintStream} de salida estándar.
     */
    public PrintStream salidaEstandar() {
        return _salidaEstandar;
    }

    /**
     * Devuelve el {@link PrintStream} de salida de errores.
     *
     * @return El {@link PrintStream} de salida de errores.
     */
    public PrintStream salidaErrores() {
        return _salidaErrores;
    }
}
