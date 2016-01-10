package com.jim_project.interprete.gestor;

import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Macro;

/**
 * Clase encargada de gestionar los ámbitos de un programa.
 *
 * @author Alberto García González
 */
public class GestorAmbitos extends GestorComponentes {

    private final ArrayList<Ambito> _ambitos;
    private Ambito _ultimoAmbito;

    /**
     * Constructor de clase.
     *
     * @param programa Referencia al programa en ejecución.
     */
    public GestorAmbitos(Programa programa) {
        super(programa);
        _ambitos = new ArrayList<>();
        _ultimoAmbito = null;
    }

    /**
     * Crea un nuevo ámbito y lo añade al gestor.
     *
     * @param parametrosEntrada Los parámetros de entrada del nuevo ámbito.
     * @param macroAsociada La macro asociada al nuevo ámbito.
     * @param profundidad La profundidad del nuevo ámbito.
     * @return Una referencia la nuevo ámbito.
     */
    public Ambito nuevoAmbito(String[] parametrosEntrada, Macro macroAsociada, int profundidad) {
        _ultimoAmbito = new Ambito(_programa, parametrosEntrada, macroAsociada, profundidad);
        _ambitos.add(_ultimoAmbito);

        return _ultimoAmbito;
    }

    /**
     * Crea un nuevo ámbito y lo añade al gestor.
     *
     * @param parametrosEntrada Los parámetros de entrada del neuvo ámbito.
     * @param lineas Las líneas que forman el código asociado al nuevo ámbito.
     * @return Una referencia al nuevo ámbito.
     */
    public Ambito nuevoAmbito(String[] parametrosEntrada, ArrayList<String> lineas) {
        _ultimoAmbito = new Ambito(_programa, parametrosEntrada, lineas);
        _ambitos.add(_ultimoAmbito);

        return _ultimoAmbito;
    }

    /**
     * Elimina el último ámbito, si hay alguno.
     */
    public void eliminarUltimoAmbito() {
        if (!_ambitos.isEmpty()) {
            _ambitos.remove(_ultimoAmbito);

            if (!_ambitos.isEmpty()) {
                _ultimoAmbito = _ambitos.get(_ambitos.size() - 1);
            } else {
                _ultimoAmbito = null;
            }
        }
    }

    /**
     * Devuelve la lista de ámbitos.
     *
     * @return La lista de ámbitos.
     */
    public ArrayList<Ambito> ambitos() {
        return _ambitos;
    }

    /**
     * Devuelva una referencia al ámbito raíz.
     *
     * @return Una referencia al ámbito raíz.
     */
    public Ambito ambitoRaiz() {
        if (!_ambitos.isEmpty()) {
            return _ambitos.get(0);
        } else {
            return null;
        }
    }

    /**
     * Devuelve una referencia al ámbito padre de cualquier ámbito existente con
     * la profundidad indicada.
     *
     * @param profundidad La profundidad del ámbito cuyo padre quiere obtenerse.
     * @return El padre del ámbito cuya profundidad ha sido indicada, o
     * {@code null} si el ámbito no tiene padre.
     */
    public Ambito ambitoPadre(int profundidad) {
        if (profundidad > 0 && profundidad < _ambitos.size()) {
            return _ambitos.get(profundidad - 1);
        } else {
            return null;
        }
    }

    /**
     * Devuelve el ámbito actual.
     *
     * @return El ámbito actual.
     */
    public Ambito ambitoActual() {
        return _ultimoAmbito;
    }

    /**
     * Elimina los ámbitos almacenados.
     */
    @Override
    public void limpiar() {
        _ambitos.clear();
        _ultimoAmbito = null;
    }

    /**
     * Devuelve el número de ámbitos almacenados.
     *
     * @return El número de ámbitos almacenados.
     */
    @Override
    public int count() {
        return _ambitos.size();
    }

    /**
     * Comprueba si no existe ámbitos.
     *
     * @return {@code true}, si el gestor está vacío; {@code false}, si contiene
     * algún ámbito.
     */
    @Override
    public boolean vacio() {
        return _ambitos.isEmpty();
    }
}
