package com.jim_project.interprete.parser;

import com.jim_project.interprete.util.ControladorEjecucion;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;

public abstract class Parser {
    
    protected AnalizadorLexico _analizadorLexico;
    protected ControladorEjecucion _controladorEjecucion;
    protected Ambito _ambito;
    protected Programa _programa;
    
    public Parser(Programa programa) {
        _programa = programa;
        _controladorEjecucion = null;
        _ambito = null;
    }
    
    public Parser(ControladorEjecucion controladorEjecucion) {
        _controladorEjecucion = controladorEjecucion;

        if (_controladorEjecucion != null) {
            _ambito = _controladorEjecucion.ambito();
            _programa = _ambito.programa();
        } else {
            _ambito = null;
            _programa = null;
        }
    }
    
    public AnalizadorLexico analizadorLexico() {
        return _analizadorLexico;
    }
    
    public ControladorEjecucion controladorEjecucion() {
        return _controladorEjecucion;
    }

    public Ambito ambito() {
        return _ambito;
    }

    public Programa programa() {
        return _programa;
    }

    public abstract int parse();

    /**
     * esta función se invoca por el analizador cuando necesita el siguiente
     * token del analizador léxico
     *
     */
    protected abstract int yylex();

    /**
     * invocada cuando se produce un error
     *
     */
    public abstract void yyerror(String descripcion, int yystate, int token);

    public void yyerror(String descripcion) {
        _programa.error().deTokenNoEsperado(descripcion);
    }

    /*
    public abstract void yyerror(String descripcion);
    /*
    {
        System.err.println("Error en línea " + Integer.toString(analex.lineaActual()) + " : " + descripcion);
        System.err.println("Token leido : " + yyname[token]);
    }
    */
}
