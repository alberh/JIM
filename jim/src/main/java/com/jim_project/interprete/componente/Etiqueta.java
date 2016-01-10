package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.Error;
import com.jim_project.interprete.gestor.GestorEtiquetas;

/**
 * Clase que abstrae el concepto de etiqueta, almacenando información sobre su
 * letra, índice y localización en el código.
 */
public class Etiqueta extends Componente {

    private final char _grupo;
    private final int _indice;
    private final int _linea;

    /**
     * Constructor de clase.
     *
     * @param id El identificador de la etiqueta.
     * @param linea La línea en la que se encuentra definida.
     * @param gestorEtiquetas El gestor que la contiene.
     */
    public Etiqueta(String id, int linea, GestorEtiquetas gestorEtiquetas) {
        super(Etiqueta.normalizarID(id), gestorEtiquetas);

        _grupo = id.toUpperCase().charAt(0);
        int i = 0;
        try {
            i = Integer.parseInt(_id.substring(1));
        } catch (NumberFormatException ex) {
            _gestor.ambito().programa().error().alObtenerIndiceDeEtiqueta(id);
        } finally {
            _indice = i;
        }
        _linea = linea;
    }

    /**
     * Devuelve el carácter que representa el grupo al que pertenece la
     * etiqueta. Los grupos van de la 'A' a la 'E'.
     *
     * @return El grupo de la etiqueta.
     */
    public char grupo() {
        return _grupo;
    }

    /**
     * Devuelve el índice de la etiqueta.
     *
     * @return El índice de la etiqueta.
     */
    public int indice() {
        return _indice;
    }

    /**
     * Devuelve el número de línea en el que se encuentra la etiqueta.
     *
     * @return El número de línea de la etiqueta.
     */
    public int linea() {
        return _linea;
    }

    /**
     * Devuelve una representación en forma de cadena de la etiqueta, formada
     * por su identificador y su línea.
     *
     * @return Una cadena que representa la etiqueta.
     */
    @Override
    public String toString() {
        return "(" + _id + ", " + _linea + ")";
    }

    /**
     * Método encargado de normalizar el identificador de una etiqueta,
     * cambiando a mayúsculas su grupo y añadiendo el subíndice 1 cuando sea
     * necesario.
     *
     * @param id El identificador de la etiqueta.
     * @return El identificador normalizado.
     */
    public static String normalizarID(String id) {
        if (id != null) {
            id = id.toUpperCase();
            int len = id.length();

            if (len == 1) {
                return id + "1";
            } else {
                return id;
            }
        } else {
            Error.deIdentificadorDeEtiquetaVacio();
            return "";
        }
    }
}
