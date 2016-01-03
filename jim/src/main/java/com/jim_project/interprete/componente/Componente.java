package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.gestor.GestorComponentes;

/**
 * Clase abstracta que representa un componente del programa, como una variable,
 * una etiqueta o una llamada a macro.
 * @author Alberto García González
 */
public abstract class Componente {

    /**
     * El identificador del componente.
     */
    protected String _id;
    
    /**
     * Una referencia al gestor que contiene al componente.
     */
    protected GestorComponentes _gestor;
    
    /**
     * Constructor que recibe el identificador del componente y una referencia
     * al gestor que lo contiene.
     * @param id El identificador del componente.
     * @param gestor La referencia al {@link GestorComponentes}.
     */
    public Componente(String id, GestorComponentes gestor) {
        _id = id;
        _gestor = gestor;
    }

    /**
     * Devuelve el identificador del componente.
     * @return El identificador del componente.
     */
    public String id() {
        return _id;
    }
    
    /**
     * Devuelve una referencia al gestor de componentes que lo contiene.
     * @return Una referencia al {@link GestorComponentes} que lo contiene.
     */
    public GestorComponentes gestor() {
        return _gestor;
    }
}
