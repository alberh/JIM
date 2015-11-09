package com.jim_project.interprete.gui;

/*
 *
 * @(#) TextAreaOutputStream.java
 *
 */
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 * An output stream that writes its output to a javax.swing.JTextArea control.
 * 
* @author Ranganath Kini
 * @see javax.swing.JTextArea
 */
public class TextAreaOutputStream extends OutputStream {

    private final JTextArea textControl;

    /**
     * Creates a new instance of TextAreaOutputStream which writes to the
     * specified instance of javax.swing.JTextArea control.
     *
     * @param control A reference to the javax.swing.JTextArea control to which
     * the output must be redirected to.
     */
    public TextAreaOutputStream(JTextArea control) {
        textControl = control;
    }

    /**
     * Writes the specified byte as a character to the javax.swing.JTextArea.
     *
     * @param b The byte to be written as character to the JTextArea.
     */
    @Override
    public void write(final int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }

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
