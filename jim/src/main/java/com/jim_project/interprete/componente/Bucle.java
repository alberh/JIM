package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.gestor.GestorBucles;

/**
 * Clase encargada de gestionar los distintos bucles del programa interpretado.
 */
public class Bucle extends Componente {

    private int _lineaInicio;
    private int _lineaFin;
    private int _contador;

    /**
     * Constructor de clase.
     *
     * @param	lineaInicio El número de línea inicial del bucle.
     * @param	lineaFin El número de línea final del bucle.
     */
    public Bucle(int lineaInicio, int lineaFin, GestorBucles gestorBucles) {
        super("[" + lineaInicio + ":" + lineaFin + "]", gestorBucles);

        _lineaInicio = lineaInicio;
        _lineaFin = lineaFin;
        _contador = -1;
    }

    /**
     * Devuelve la línea inicial.
     */
    public int lineaInicio() {
        return _lineaInicio;
    }

    /**
     * Devuelve la línea final.
     */
    public int lineaFin() {
        return _lineaFin;
    }

    /**
     * Devuelve el contador asociado al bucle. El contador marca el número de
     * veces que se ha ejecutado el bucle.
     */
    public int contador() {
        return _contador;
    }

    /**
     * Establece el valor del contador del bucle.
     */
    public void contador(int nuevoValor) {
        this._contador = nuevoValor;
    }

    /**
     * Comprueba si el bucle ha sido inicializado. Se considera que un bucle ha
     * sido inicializado si el valor de su contador es igual o mayor que 0.
     */
    public boolean inicializado() {
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
     * Reinicia el contador del bucle. El bucle pasa a estar no inicializado.
     */
    public void resetContador() {
        this._contador = -1;
    }

    /**
     * Devuelve una cadena que representa el bucle.
     */
    @Override
    public String toString() {
        return "(" + _lineaInicio + ", " + _lineaFin + ")";
    }
}
