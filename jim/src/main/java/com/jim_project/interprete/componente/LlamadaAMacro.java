package com.jim_project.interprete.componente;

import java.util.ArrayList;

/**
 * Clase que abstrae el contepto de llamada a macro dentro del código del
 * programa, manteniendo información sobre la línea en la que se encuentra la
 * llamada, sus parámetros de entrada y la variable a la que se asignará el
 * valor resultante.
 *
 * @author Alberto García González
 */
public class LlamadaAMacro extends Componente {

    private final int _linea;
    private final String _idVariableSalida;
    private final ArrayList<String> _parametros;

    /**
     * Constructor de clase.
     *
     * @param linea La línea en la que se encuentra la llamada.
     * @param idVariableSalida La variable a la que se asignará el resultado de
     * la llamada.
     * @param idMacro El identificador de la macro.
     */
    public LlamadaAMacro(int linea,
            String idVariableSalida,
            String idMacro) {

        super(idMacro.toUpperCase(), null);
        _linea = linea;
        _idVariableSalida = idVariableSalida;
        _parametros = new ArrayList<>();
    }

    /**
     * Devuelve el número de línea en el que se encuentra la llamada a macro.
     *
     * @return El número de línea en el que se encuentra.
     */
    public int linea() {
        return _linea;
    }

    /**
     * Devuelve el identificador de la destino de la asignación de la llamada.
     *
     * @return El identificador de la variable de salida.
     */
    public String idVariableSalida() {
        return _idVariableSalida;
    }

    /**
     * Devuelve los parámetros de entrada de la llamada a macro.
     *
     * @return Los parámetros de entrada.
     */
    public ArrayList<String> parametros() {
        return _parametros;
    }
}
