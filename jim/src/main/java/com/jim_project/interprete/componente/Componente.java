package com.jim_project.interprete.componente;

public abstract class Componente {

    protected String _id;

    public Componente(String id) {
        _id = id;
    }

    /**
     * Devuelve el identificador del componente.
     */
    public String id() {
        return _id;
    }
}
