package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.Error;
import com.jim_project.interprete.util.gestor.GestorEtiquetas;

/**
 * Esta clase representa el concepto de etiqueta de salto utilizado en el modelo
 * L.
 */
public class Etiqueta extends Componente {

    private char _grupo;
    private int _indice;
    private int _linea;

    /**
     * Constructor de clase.
     */
    public Etiqueta(String id, int linea, GestorEtiquetas gestorEtiquetas) {
        super(Etiqueta.normalizarID(id), gestorEtiquetas);

        _grupo = obtenerGrupo(_id);
        _indice = obtenerIndice(_id);
        _linea = linea;
    }

    public char grupo() {
        return _grupo;
    }

    public int indice() {
        return _indice;
    }

    /**
     * Devuelve el número de línea en el que se encuentra la etiqueta.
     */
    public int linea() {
        return _linea;
    }

    /**
     * Devuelve una representación en forma de cadena de la etiqueta.
     */
    @Override
    public String toString() {
        return "(" + _id + ", " + _linea + ")";
    }

    /* Métodos auxiliares para la construcción */
    /**
     * Cambia a mayúsculas el identificador de una etiqueta, y concatena un "1"
     * al final si no ha sido indicado.
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

    private char obtenerGrupo(String id) {
        return id.toUpperCase().charAt(0);
    }

    // Interfaz con los métodos comunes entre Etiqueta y Variable?
    private int obtenerIndice(String id) {
        try {
            return Integer.parseInt(id.substring(1));
        } catch (NumberFormatException ex) {
            _gestor.ambito().programa().error().alObtenerIndiceDeEtiqueta(id);
        }

        return 0;
    }
}
