package com.jim_project.interprete.util.gestor;

import com.jim_project.interprete.Interprete;
import com.jim_project.interprete.util.Ambito;

public abstract class GestorComponentes {

    protected Interprete _programa;

    public GestorComponentes(Interprete programa) {
        _programa = programa;
    }

    protected Interprete programa() {
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
