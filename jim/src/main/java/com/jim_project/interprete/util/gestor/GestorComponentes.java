package com.jim_project.interprete.util.gestor;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;

public abstract class GestorComponentes {

    protected Programa _programa;

    public GestorComponentes(Programa programa) {
        _programa = programa;
    }

    public Programa programa() {
        return _programa;
    }
    
    public GestorAmbitos gestorAmbitos() {
        return _programa.gestorAmbitos();
    }
    
    public Ambito ambitoActual() {
        return _programa.gestorAmbitos().ambitoActual();
    }

    public abstract int count();

    public abstract boolean vacio();
}
