/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jim_project.interprete.gui;

import java.io.PrintStream;
import javax.swing.JTextArea;

/**
 *
 * @author alber_000
 */
public class Consola {
    
    private static JTextArea _estandar = null;
    private static JTextArea _errores = null;
    private static PrintStream _salidaEstandar;
    private static PrintStream _salidaErrores;
    
    public static void inicializar(JTextArea estandar, JTextArea errores) {
        _estandar = estandar;
        _errores = errores;
        
        _salidaEstandar = new PrintStream(new TextAreaOutputStream(_estandar));
        _salidaErrores = new PrintStream(new TextAreaOutputStream(_errores));
    }
    
    public static PrintStream estandar() {
        return _salidaEstandar;
    }
    
    public static PrintStream errores() {
        return _salidaErrores;
    }
}
