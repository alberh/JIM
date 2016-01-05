package com.jim_project.interprete.util.gestor;

import java.util.HashMap;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Etiqueta;

public class GestorEtiquetas extends GestorComponentes {

    private HashMap<String, Etiqueta> _etiquetas;
    private int _indiceUltimaA;

    public GestorEtiquetas(Ambito ambito) {
        super(ambito);
        _etiquetas = new HashMap<>();
        _indiceUltimaA = 0;
    }

    /**
     * Define una nueva etiqueta.
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
     * Crea y devuelve una nueva etiqueta etiquetada como An, donde n es una
     * unidad superior al mayor índice de cualquier etiqueta A creada.
     */
    public Etiqueta nuevaEtiqueta() {
        ++_indiceUltimaA;
        Etiqueta etiqueta = new Etiqueta("A" + _indiceUltimaA, 0, this);

        return etiqueta;
    }

    /**
     * Obtiene una etiqueta previamente creada, según su identificador.
     */
    public Etiqueta obtenerEtiqueta(String id) {
        return _etiquetas.get(Etiqueta.normalizarID(id));
    }

    /**
     * Devuelve el identificador de la última etiqueta A creada.
     */
    public String ultimaEtiqueta() {
        return "A" + _indiceUltimaA;
    }

    /**
     * Elimina todas las etiquetas.
     */
    @Override
    public void limpiar() {
        _etiquetas.clear();
        _indiceUltimaA = 0;
    }

    /**
     * Imprime en pantalla todas las etiquetas.
     */
    @Override
    public String toString() {
        return _etiquetas + "\n";
    }

    @Override
    public int count() {
        return _etiquetas.size();
    }

    @Override
    public boolean vacio() {
        return _etiquetas.isEmpty();
    }
}
