package com.jim_project.interprete.componente;

import java.util.ArrayList;

public class LlamadaAMacro extends Componente {

    private int _linea;
    private final String _idVariableSalida;
    //private final String _idMacro;
    private final ArrayList<String> _parametros;

    public LlamadaAMacro(int linea,
            String idVariableSalida,
            String idMacro) {
        
        super(idMacro.toUpperCase(), null);
        _linea = linea;
        _idVariableSalida = idVariableSalida;
        //_idMacro = idMacro.toUpperCase();
        _parametros = new ArrayList<>();
    }

    public int linea() {
        return _linea;
    }
    
    public void linea(int nuevaLinea) {
        _linea = nuevaLinea;
    }

    public String idVariableSalida() {
        return _idVariableSalida;
    }
/*
    public String id() {
        return _idMacro;
    }
*/
    public ArrayList<String> parametros() {
        return _parametros;
    }
}
