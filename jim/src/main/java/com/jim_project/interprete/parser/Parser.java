package com.jim_project.interprete.parser;

public abstract class Parser {

    public abstract int parse();

    public abstract AnalizadorLexico analizadorLexico();

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
        com.jim_project.interprete.util.Error.deTokenNoEsperado(descripcion);
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
