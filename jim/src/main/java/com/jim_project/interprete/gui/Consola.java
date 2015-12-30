package com.jim_project.interprete.gui;

import java.io.PrintStream;
import javax.swing.JTextArea;

public class Consola {

    private JTextArea _estandar = null;
    private JTextArea _errores = null;
    private final PrintStream _salidaEstandar;
    private final PrintStream _salidaErrores;

    public Consola(JTextArea estandar, JTextArea errores) {
        _estandar = estandar;
        _errores = errores;

        _salidaEstandar = new PrintStream(new TextAreaOutputStream(_estandar));
        _salidaErrores = new PrintStream(new TextAreaOutputStream(_errores));
    }

    public PrintStream salidaEstandar() {
        return _salidaEstandar;
    }

    public PrintStream salidaErrores() {
        return _salidaErrores;
    }
}
