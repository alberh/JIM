package com.jim_project.interprete.componente;

import com.jim_project.interprete.gestor.GestorBucles;

/**
 * Clase que abstrae el concepto de bucle dentro del programa. Se encarga de
 * mantener la línea inicial y final del bucle, así como un contador interno
 * para facilitar la simulación de los bucles del modelo Loop.
 */
public class Bucle extends Componente {

    private final int _lineaInicio;
    private final int _lineaFin;
    private int _contador;

    /**
     * Constructor de clase.
     *
     * @param lineaInicio El número de línea inicial del bucle.
     * @param lineaFin El número de línea final del bucle.
     * @param gestorBucles Una referencia al gestor de bucles que contiene este
     * bucle.
     */
    public Bucle(int lineaInicio, int lineaFin, GestorBucles gestorBucles) {
        super("[" + lineaInicio + ":" + lineaFin + "]", gestorBucles);

        _lineaInicio = lineaInicio;
        _lineaFin = lineaFin;
        _contador = -1;
    }

    /**
     * Devuelve la línea inicial del bucle.
     *
     * @return La línea inicial del bucle.
     */
    public int lineaInicio() {
        return _lineaInicio;
    }

    /**
     * Devuelve la línea final del bucle.
     *
     * @return La línea final del bucle.
     */
    public int lineaFin() {
        return _lineaFin;
    }

    /**
     * Devuelve el valor actual del contador del bucle.
     *
     * @return El valor del contador del bucle.
     */
    public int contador() {
        return _contador;
    }

    /**
     * Establece el valor del contador del bucle.
     *
     * @param nuevoValor El nuevo valor para el contador del bucle.
     */
    public void contador(int nuevoValor) {
        this._contador = nuevoValor;
    }

    /**
     * Comprueba si el contador del bucle está siendo utilizado.
     *
     * @return true, si el contador del bucle ha sido inciado; false en caso
     * contrario.
     */
    public boolean contadorEnUso() {
        return _contador != -1;
    }

    /**
     * Decrementa el contador del bucle una unidad.
     */
    public void decremento() {
        if (_contador > 0) {
            _contador--;
        }
    }

    /**
     * Reinicia el contador del bucle y pasa a no estar siendo utilizado.
     */
    public void reiniciarContador() {
        this._contador = -1;
    }

    /**
     * Devuelve una cadena que representa este objeto según su identificador.
     *
     * @return El identificador del bucle.
     */
    @Override
    public String toString() {
        return _id;
    }
}
