package com.jim_project.interprete.gui;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 * Un flujo de salida que escribe su salida a un {@link JTextArea}.
 *
 * @author Ranganath Kini
 * @see javax.swing.JTextArea
 */
public class TextAreaOutputStream extends OutputStream {

    private final JTextArea textControl;

    /**
     * Constructor de clase.
     *
     * @param control Referencia al {@link JTextArea} al cual redirigir la
     * salida.
     */
    public TextAreaOutputStream(JTextArea control) {
        textControl = control;
    }

    /**
     * Escribe el byte especificado como carácter en el {@link JTextArea}.
     *
     * @param b El byte a escribir.
     * @throws java.io.IOException Si hay algún error de entrada/salida.
     */
    @Override
    public void write(final int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

    /**
     * Escribe el byte especificado como carácter en el {@link JTextArea},
     * indicando desplazamiento y longitud.
     *
     * @param b La serie de bytes a escribir.
     * @param off El índice del primer byte a escribir.
     * @param len El número de bytes a escribir.
     * @throws IOException Si hay algún error de entrada/salida.
     */
    @Override
    public void write(final byte[] b, final int off, final int len)
            throws IOException {

        final String str = new String(b, off, len);

        try {
            textControl.getDocument().insertString(
                    textControl.getDocument().getLength(), str, null);
        } catch (Exception e) {
        }
    }
}
