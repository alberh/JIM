package com.jim_project.interprete.parser;

import com.jim_project.interprete.util.ControladorEjecucion;

public abstract class Parser {
    
    protected AnalizadorLexico _analizadorLexico;
    protected ControladorEjecucion _controladorEjecucion;
    protected Acciones _acciones;
    
    public Parser(AnalizadorLexico analizadorLexico, ControladorEjecucion controladorEjecucion) {
        _analizadorLexico = analizadorLexico;
        _controladorEjecucion = controladorEjecucion;
    }
    
    public AnalizadorLexico analizadorLexico() {
        return _analizadorLexico;
    }
    
    public ControladorEjecucion controladorEjecucion() {
        return _controladorEjecucion;
    }
    
    public Acciones acciones() {
        return _acciones;
    }

    public abstract int parse();

    /**
     * esta función se invoca por el analizador cuando necesita el ** siguiente
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
        _controladorEjecucion.ambito().programa().error().deTokenNoEsperado(descripcion);
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
