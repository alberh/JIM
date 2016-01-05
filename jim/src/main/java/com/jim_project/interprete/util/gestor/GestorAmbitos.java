package com.jim_project.interprete.util.gestor;

import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Macro;

public class GestorAmbitos extends GestorComponentes {

    private final ArrayList<Ambito> _ambitos;
    private Ambito _ultimoAmbito;

    public GestorAmbitos(Programa programa) {
        super(programa);
        _ambitos = new ArrayList<>();
        _ultimoAmbito = null;
    }

    public Ambito nuevoAmbito(String[] parametrosEntrada, Macro macroAsociada, int profundidad) {
        _ultimoAmbito = new Ambito(_programa, parametrosEntrada, macroAsociada, profundidad);
        _ambitos.add(_ultimoAmbito);

        return _ultimoAmbito;
    }

    public Ambito nuevoAmbito(String[] parametrosEntrada, ArrayList<String> lineas) {
        _ultimoAmbito = new Ambito(_programa, parametrosEntrada, lineas);
        _ambitos.add(_ultimoAmbito);

        return _ultimoAmbito;
    }

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

    public ArrayList<Ambito> ambitos() {
        return _ambitos;
    }

    public Ambito ambitoRaiz() {
        if (!_ambitos.isEmpty()) {
            return _ambitos.get(0);
        } else {
            return null;
        }
    }

    public Ambito ambitoPadre(int profundidad) {
        if (profundidad > 0 && profundidad < _ambitos.size()) {
            return _ambitos.get(profundidad - 1);
        } else {
            return null;
        }
    }

    public Ambito ambitoActual() {
        return _ultimoAmbito;
    }

    public boolean ambitoActualEsRaiz() {
        if (_ultimoAmbito != null && !_ambitos.isEmpty()) {
            return _ultimoAmbito == _ambitos.get(0);
        } else {
            return false;
        }

    }

    @Override
    public void limpiar() {
        _ambitos.clear();
        _ultimoAmbito = null;
    }

    @Override
    public int count() {
        return _ambitos.size();
    }

    @Override
    public boolean vacio() {
        return _ambitos.isEmpty();
    }
}
