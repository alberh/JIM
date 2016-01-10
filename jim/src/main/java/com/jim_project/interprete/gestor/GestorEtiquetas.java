package com.jim_project.interprete.gestor;

import java.util.HashMap;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Etiqueta;

/**
 * Clase encargada de gestionar las etiquetas de un ámbito.
 *
 * @author Alberto García González
 */
public class GestorEtiquetas extends GestorComponentes {

    private final HashMap<String, Etiqueta> _etiquetas;
    private int _indiceUltimaA;

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito que contiene este gestor.
     */
    public GestorEtiquetas(Ambito ambito) {
        super(ambito);
        _etiquetas = new HashMap<>();
        _indiceUltimaA = 0;
    }

    /**
     * Crea una nueva etiqueta y la añade al gestor.
     *
     * @param id El identificador de la etiqueta.
     * @param linea El número de línea en el que está definida.
     * @return Una referencia a la etiqueta recién creada.
     */
    public Etiqueta nuevaEtiqueta(String id, int linea) {
        Etiqueta et = new Etiqueta(id, linea, this);

        if (_etiquetas.containsKey(et.id())) {
            return _etiquetas.get(et.id());
        } else {
            _etiquetas.put(et.id(), et);

            if (et.grupo() == 'A' && et.indice() > _indiceUltimaA) {
                _indiceUltimaA = et.indice();
            }

            return et;
        }
    }

    /**
     * Crea una nueva etiqueta con grupo 'A' e índice igual a n + 1, siendo n
     * igual al mayor índice de cualquier etiqueta del grupo 'A' almacenada en
     * el gestor hasta el momento. La nueva etiqueta no es añadida al gestor.
     *
     * @return Una referencia a la etiqueta recién creada.
     */
    public Etiqueta nuevaEtiqueta() {
        ++_indiceUltimaA;
        Etiqueta etiqueta = new Etiqueta("A" + _indiceUltimaA, 0, this);

        return etiqueta;
    }

    /**
     * Busca una etiqueta por su identificador.
     *
     * @param id El identificador de la etiqueta.
     * @return Una referencia a la etiqueta buscada, si existe; {@code null} si
     * no existe.
     */
    public Etiqueta obtenerEtiqueta(String id) {
        return _etiquetas.get(Etiqueta.normalizarID(id));
    }

    /**
     * Elimina todas las etiquetas almacenadas en el gestor.
     */
    @Override
    public void limpiar() {
        _etiquetas.clear();
        _indiceUltimaA = 0;
    }

    /**
     * Devuelve el número de etiquetas almacenadas en el gestor.
     *
     * @return El número de etiquetas almacenadas en el gestor.
     */
    @Override
    public int count() {
        return _etiquetas.size();
    }

    /**
     * Comprueba si el gestor está vacío.
     *
     * @return {@code true}, si el gestor está vacío; {@code false}, si contiene
     * alguna etiqueta.
     */
    @Override
    public boolean vacio() {
        return _etiquetas.isEmpty();
    }
}
