package com.jim_project.interprete.gestor;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;

/**
 * Clase abstracta que modela las bases para la implementación de gestores para
 * los distintos componentes del programa.
 *
 * @author Alberto García González
 */
public abstract class GestorComponentes {

    /**
     * Referencia al programa en ejecución.
     */
    protected Programa _programa;
    /**
     * Referencia al ámbito al que pertenece el gestor.
     */
    protected Ambito _ambito;

    /**
     * Constructor de clase, pensado para ser utilizado desde
     * {@link GestorMacros}.
     *
     * @param programa El programa en ejecución.
     */
    public GestorComponentes(Programa programa) {
        _programa = programa;
        _ambito = null;
    }

    /**
     * Constructor de clase.
     *
     * @param ambito El ámbito que contiene al gestor.
     */
    public GestorComponentes(Ambito ambito) {
        _ambito = ambito;
        _programa = _ambito.programa();
    }

    /**
     * Devuelve el programa en ejecución.
     *
     * @return Una referencia al programa en ejecución.
     */
    public Programa programa() {
        return _programa;
    }

    /**
     * Devuelve el ámbito al cual pertenece este gestor.
     *
     * @return Una referencia al ámbito al que pertenece.
     */
    public Ambito ambito() {
        return _ambito;
    }

    /**
     * Limpia los distintos contenedores del gestor.
     */
    public abstract void limpiar();

    /**
     * Devuelve la cantidad de componentes almacenados por el gestor.
     *
     * @return La cantidad de componentes almacenados por el gestor.
     */
    public abstract int count();

    /**
     * Comprueba si el gestor no contiene ningún componente.
     *
     * @return {@code true}, si el gestor no contiene componentes;
     * {@code false}, en caso de contener alguno.
     */
    public abstract boolean vacio();
}
