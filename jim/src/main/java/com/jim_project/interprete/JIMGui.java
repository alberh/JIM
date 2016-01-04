package com.jim_project.interprete;

import com.jim_project.interprete.gui.MainWindow;

/**
 * Clase principal de la aplicación en su versión de escritorio, utilizada como
 * envoltorio de la clase {@link MainWindow}.
 * @author Alberto García González
 */
public class JIMGui {

    /**
     * Método de entrada de la aplicación.
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(
                () -> {
                    new MainWindow().setVisible(true);
                }
        );
    }
}
