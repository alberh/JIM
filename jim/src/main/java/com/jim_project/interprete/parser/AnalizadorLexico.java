package com.jim_project.interprete.parser;

/**
 * Clase abstracta que sirve como clase para las clases de los analizadores
 * léxicos generados por la herramenta jflex.
 *
 * @author Alberto García González
 */
public abstract class AnalizadorLexico {

    /**
     * Reinicia el analizador y le asigna un nuevo {@link java.io.Reader} del que
     * obtener los caracteres.
     *
     * @param reader El nuevo lector de entrada.
     */
    public abstract void yyreset(java.io.Reader reader);

    /**
     * Detiene el analizador y cierra el lector de entrada.
     *
     * @throws java.io.IOException Si hay algún error de entrada/salida.
     */
    public abstract void yyclose() throws java.io.IOException;

    /**
     * Devuelve el siguiente tóken leído.
     *
     * @return El siguiente tóken.
     * @throws java.io.IOException Si hay algún error de entrada/salida.
     */
    public abstract int yylex() throws java.io.IOException;
}
