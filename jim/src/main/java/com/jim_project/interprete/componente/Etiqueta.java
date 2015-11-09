package com.jim_project.interprete.componente;

import java.util.HashMap;
import com.jim_project.interprete.util.Error;

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
    public Etiqueta(String id, int linea) {
        super(Etiqueta.normalizarID(id));

        _grupo = Etiqueta.obtenerGrupo(id);
        _indice = Etiqueta.obtenerIndice(id);
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

    // Métodos estáticos
    /**
     * Cambia a mayúsculas el identificador de una etiqueta, y concatena un "1"
     * al final si no ha sido indicado.
     */
    public static String normalizarID(String id) {
        id = id.toUpperCase();
        int len = id.length();

        if (len > 0) {
            if (len == 1 || (len == 2 && id.charAt(0) == 'L')) {
                return id + "1";
            } else {
                return id;
            }
        } else {
            Error.deIdentificadorDeEtiquetaVacio();
            return "";
        }
    }

    public static char obtenerGrupo(String id) {
        id = Etiqueta.normalizarID(id);
        if (Character.isDigit(id.charAt(1))) {
            return id.charAt(0);
        } else {
            return id.charAt(1);
        }
    }

    // Interfaz con los métodos comunes entre Etiqueta y Variable?
    public static int obtenerIndice(String id) {
        id = Etiqueta.normalizarID(id);

        if (id.length() > 1) {
            int indiceInicio;

            if (Character.isDigit(id.charAt(1))) {
                indiceInicio = 1;
            } else {
                indiceInicio = 2;
            }

            try {
                return Integer.parseInt(id.substring(indiceInicio));
            } catch (NumberFormatException ex) {
                Error.alObtenerIndiceDeEtiqueta(id);
            }
        } else {
            return 1;
        }

        return 0;
    }

    /**
     * *************************************************************
     * Refactor
     */
    private static HashMap<String, Etiqueta> _etiquetas = new HashMap<>();
    private static int _ultimaA = 0;

    /**
     * Imprime en pantalla todas las etiquetas.
     */
    public static void pintar() {
        System.out.println("Etiquetas");
        System.out.println(_etiquetas);
        System.out.println();
    }

    /**
     * Define una nueva etiqueta.
     */
    public static Etiqueta set(String id, int linea) {
        id = normalizarID(id);

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
        return _etiquetas.get(normalizarID(id));
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
}
