package org.alberto.interprete;

import java.util.HashMap;

/**
 * Esta clase representa el concepto de etiqueta de salto utilizado en el modelo
 * L.
 */
public class Etiqueta {

    private static HashMap<String, Etiqueta> _etiquetas = new HashMap<>();
    private static int _ultimaA = 0;

    private String _id;
    private int _linea;

    /**
     * Define una nueva etiqueta.
     */
    public static Etiqueta set(String id, int linea) {
        id = filtrar(id);

        if (_etiquetas.containsKey(id)) {
            return _etiquetas.get(id);
        }

        char letra = id.charAt(0);
        int indice = Integer.parseInt(id.substring(1, id.length()));

        Etiqueta et = new Etiqueta(id, linea);
        _etiquetas.put(id, et);

        if (letra == 'A' && indice > _ultimaA) {
            _ultimaA = indice;
        }

        return et;
    }

    /**
     * Obtiene una etiqueta previamente creada, según su identificador.
     */
    public static Etiqueta get(String id) {
        return _etiquetas.get(filtrar(id));
    }

    /**
     * Crea y devuelve una nueva etiqueta etiquetada como An, donde n es una
     * unidad superior al mayor índice de cualquier etiqueta A creada.
     */
    public static Etiqueta get() {
        ++_ultimaA;
        Etiqueta etiqueta = new Etiqueta("A" + _ultimaA, 0);
        
        return etiqueta;
    }

    /**
     * Devuelve el identificador de la última etiqueta A creada.
     */
    public static String ultimaEtiqueta() {
        return "A" + _ultimaA;
    }

    /**
     * Elimina todas las etiquetas.
     */
    public static void limpiar() {
        _etiquetas.clear();
        _ultimaA = 0;
    }

    /**
     * Constructor de clase.
     */
    private Etiqueta(String id, int linea) {
        this._id = id;
        this._linea = linea;
    }

    /**
     * Devuelve el identificador de la etiqueta.
     */
    public String id() {
        return _id;
    }

    /**
     * Devuelve el número de línea en el que se encuentra la etiqueta.
     */
    public int linea() {
        return _linea;
    }

    /**
     * Cambia a mayúsculas el identificador de una etiqueta, y concatena un "1"
     * al final si no ha sido indicado.
     */
    public static String filtrar(String id) {
        id = id.toUpperCase();

        if (id.length() == 1) {
            return id + "1";
        } else {
            return id;
        }
    }

    /**
     * Devuelve una representación en forma de cadena de la etiqueta.
     */
    @Override
    public String toString() {
        return "(" + _id + ", " + _linea + ")";
    }

    /**
     * Imprime en pantalla todas las etiquetas.
     */
    public static void pintar() {
        System.out.println("Etiquetas");
        System.out.println(_etiquetas);
        System.out.println();
    }
}
