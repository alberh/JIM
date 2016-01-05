package com.jim_project.interprete.util.gestor;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;

public abstract class GestorComponentes {

    protected Programa _programa;
    protected Ambito _ambito;

    public GestorComponentes(Programa programa) {
        _programa = programa;
        _ambito = null;
    }

    public GestorComponentes(Ambito ambito) {
        _ambito = ambito;
        _programa = _ambito.programa();
    }

    public Programa programa() {
        return _programa;
    }

    public Ambito ambito() {
        return _ambito;
    }

    public abstract void limpiar();

    public abstract int count();

    public abstract boolean vacio();
}
