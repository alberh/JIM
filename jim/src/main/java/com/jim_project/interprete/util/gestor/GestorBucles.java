package com.jim_project.interprete.util.gestor;

import java.util.HashMap;
import java.util.Stack;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Bucle;

public class GestorBucles extends GestorComponentes {

    private HashMap<Integer, Bucle> _buclesLineaInicio;
    private HashMap<Integer, Bucle> _buclesLineaFin;

    private Stack<Integer> _lineasInicioBucles;

    public GestorBucles(Ambito ambito) {
        super(ambito);

        _buclesLineaInicio = new HashMap<>();
        _buclesLineaFin = new HashMap<>();
        _lineasInicioBucles = new Stack<>();
    }

    /**
     * Mantiene el número de línea indicado en memoria, representando el número
     * de línea inicial de un objeto Bucle que será creado más tarde, una vez se
     * encuentre el número de línea de cierre y sea indicado con el método
     * {@link #definirLineaFin(int) cerrar}.
     */
    public void definirLineaInicio(int lineaInicio) {
        _lineasInicioBucles.push(lineaInicio);
    }

    /**
     * Recupera la última línea de inicio de bucle indicada y crea un nuevo
     * objeto Bucle representado por dicha línea de inicio y la línea de fin
     * indicada por el parámetro lineaFin. El objeto creado es añadido a la
     * colección de bucles.
     */
    public void definirLineaFin(int lineaFin) {
        if (!_lineasInicioBucles.empty()) {
            int lineaInicio = _lineasInicioBucles.pop();
            Bucle bucle = new Bucle(lineaInicio, lineaFin, this);

            _buclesLineaInicio.put(lineaInicio, bucle);
            _buclesLineaFin.put(lineaFin, bucle);
        } else {
            _programa.error().alCerrarBucle(lineaFin);
        }
    }

    /**
     * Devuelve el objeto Bucle que comience por la línea de inicio indicada.
     */
    public Bucle obtenerBucleLineaInicio(int lineaInicio) {
        return _buclesLineaInicio.get(lineaInicio);
    }

    /**
     * Devuelve el objeto Bucle que comience por la línea de fin indicada.
     */
    public Bucle obtenerBucleLineaFin(int lineaFin) {
        return _buclesLineaFin.get(lineaFin);
    }

    /**
     * Borra todos los bucles creados.
     */
    @Override
    public void limpiar() {
        _buclesLineaInicio.clear();
        _buclesLineaFin.clear();
        _lineasInicioBucles.clear();
    }

    /**
     * Devuelve una cadena con la representación de los bucles almacenados.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        _buclesLineaFin.forEach((k, v) -> sb.append(v).append("\n"));
        return sb.toString();
    }

    @Override
    public int count() {
        return _buclesLineaInicio.size();
    }

    @Override
    public boolean vacio() {
        return _buclesLineaInicio.isEmpty();
    }
}
