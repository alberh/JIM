package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.gestor.GestorComponentes;

public abstract class Componente {

    protected String _id;
    protected GestorComponentes _gestor;

    public Componente(String id, GestorComponentes gestor) {
        _id = id;
        _gestor = gestor;
    }

    /**
     * Devuelve el identificador del componente.
     */
    public String id() {
        return _id;
    }
    
    public GestorComponentes gestor() {
        return _gestor;
    }
}
