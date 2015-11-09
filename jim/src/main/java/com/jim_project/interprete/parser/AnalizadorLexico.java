package com.jim_project.interprete.parser;

public abstract class AnalizadorLexico {

    public abstract void yyreset(java.io.Reader reader);

    public abstract void yyclose() throws java.io.IOException;
}
