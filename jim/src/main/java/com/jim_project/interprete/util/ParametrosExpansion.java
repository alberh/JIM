package com.jim_project.interprete.util;

import java.util.ArrayList;

public class ParametrosExpansion {

    private int _linea;
    private String _idVariableSalida;
    private String _idMacro;
    private ArrayList<String> _variablesEntrada;

    public ParametrosExpansion(int linea,
            String idVariableSalida,
            String idMacro) {

        this(linea, idVariableSalida, idMacro, new ArrayList<>());
    }

    public ParametrosExpansion(int linea,
            String idVariableSalida,
            String idMacro,
            ArrayList<String> variablesEntrada) {

        _linea = linea;
        _idVariableSalida = idVariableSalida;
        _idMacro = idMacro;
        _variablesEntrada = variablesEntrada;
    }

    public int linea() {
        return _linea;
    }

    public String idVariableSalida() {
        return _idVariableSalida;
    }

    public String idMacro() {
        return _idMacro;
    }

    public ArrayList<String> variablesEntrada() {
        return _variablesEntrada;
    }
}
