/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete.gui;

import java.io.PrintStream;
import javax.swing.JTextArea;

/**
 *
 * @author alber_000
 */
public class Consola {
    
    private static JTextArea _consola = null;
    private static PrintStream _out;
    
    public static void inicializar(JTextArea textArea) {
        _consola = textArea;
        
        TextAreaOutputStream taos = new TextAreaOutputStream(_consola);
        _out = new PrintStream(taos);
    }
    
    public static PrintStream out() {
        return _out;
    }
}
