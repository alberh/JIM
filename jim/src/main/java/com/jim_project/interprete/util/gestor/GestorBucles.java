package com.jim_project.interprete.util.gestor;

import java.util.HashMap;
import java.util.Stack;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Bucle;

/**
 * Clase encargada de gestionar los bucles de un ámbito.
 *
 * @author Alberto García González
 */
public class GestorBucles extends GestorComponentes {

    private final HashMap<Integer, Bucle> _buclesLineaInicio;
    private final HashMap<Integer, Bucle> _buclesLineaFin;
    private final Stack<Integer> _lineasInicioBucles;

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito al cual pertenece este gestor.
     */
    public GestorBucles(Ambito ambito) {
        super(ambito);

        _buclesLineaInicio = new HashMap<>();
        _buclesLineaFin = new HashMap<>();
        _lineasInicioBucles = new Stack<>();
    }

    /**
     * Comienza a definir un nuevo bucle dentro del gestor especificando su
     * línea de inicio.
     *
     * @param linea El número de línea donde comienza el bucle en el código.
     */
    public void definirLineaInicio(int linea) {
        _lineasInicioBucles.push(linea);
    }

    /**
     * Termina la definición de un nuevo bucle en el gestor especificando su
     * línea de cierre, creando finalmente el bucle y añadiéndolo al gestor.
     *
     * @param linea El número de línea donde termina el bucle en el código.
     */
    public void definirLineaFin(int linea) {
        if (!_lineasInicioBucles.empty()) {
            int lineaInicio = _lineasInicioBucles.pop();
            Bucle bucle = new Bucle(lineaInicio, linea, this);

            _buclesLineaInicio.put(lineaInicio, bucle);
            _buclesLineaFin.put(linea, bucle);
        } else {
            _programa.error().alDefinirCierreBucle(linea);
        }
    }

    /**
     * Busca un bucle en el gestor según su línea inicial.
     *
     * @param linea El número de línea de inicio del bucle.
     * @return Una referencia al bucle que comienza en la línea {@code linea};
     * {@code null} si no hay ningún bucle que comience en dicha línea.
     */
    public Bucle obtenerBucleLineaInicio(int linea) {
        return _buclesLineaInicio.get(linea);
    }

    /**
     * Busca un bucle en el gestor según su línea final.
     *
     * @param linea El número de línea final del bucle.
     * @return Una referencia al bucle que comienza en la línea {@code linea};
     * {@code null} si no hay ningún bucle que termine en dicha línea.
     */
    public Bucle obtenerBucleLineaFin(int linea) {
        return _buclesLineaFin.get(linea);
    }

    /**
     * Elimina todos los bucles almacenados y las definiciones de bucles sin
     * termianr.
     */
    @Override
    public void limpiar() {
        _buclesLineaInicio.clear();
        _buclesLineaFin.clear();
        _lineasInicioBucles.clear();
    }

    /**
     * Devuelve el número de bucles almacenados por el gestor.
     *
     * @return El número de bucles almacenados por el gestor.
     */
    @Override
    public int count() {
        return _buclesLineaInicio.size();
    }

    /**
     * Comprueba si el gestor está vacío.
     *
     * @return {@code true}, si el gestor está vacío; {@code false}, si contiene
     * algún bucle.
     */
    @Override
    public boolean vacio() {
        return _buclesLineaInicio.isEmpty();
    }
}
