package com.jim_project.interprete.util.gestor;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.util.Ambito;

public abstract class GestorComponentes {

    protected Programa _programa;

    public GestorComponentes(Programa programa) {
        _programa = programa;
    }

    protected Programa programa() {
        return _programa;
    }
    
    protected GestorAmbitos gestorAmbitos() {
        return _programa.gestorAmbitos();
    }
    
    protected Ambito ambitoActual() {
        return _programa.gestorAmbitos().ambitoActual();
    }

    protected abstract int count();

    protected abstract boolean vacio();
}
