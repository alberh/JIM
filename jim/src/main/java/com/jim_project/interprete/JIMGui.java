package com.jim_project.interprete;

import com.jim_project.interprete.gui.MainWindow;

public class JIMGui {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(
                () -> {
                    new MainWindow().setVisible(true);
                }
        );
    }
}
