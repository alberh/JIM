package com.jim_project.interprete;

public class ArgumentosPrograma {

    public String fichero;
    public Modelo modelo;
    //public boolean modoFlexible;
    public boolean macrosPermitidas;
    public boolean verbose;
    public String[] parametros;
    public Programa.Objetivo objetivo;

    public ArgumentosPrograma() {
        fichero = null;
        modelo = null;
        //modoFlexible = false;
        macrosPermitidas = false;
        verbose = false;
        parametros = null;
        objetivo = null;
    }
}
