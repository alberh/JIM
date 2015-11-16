package com.jim_project.interprete.util.gestor;

import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Macro;

public class GestorAmbitos extends GestorComponentes {

    private ArrayList<Ambito> _ambitos;
    private Ambito _ultimoAmbito;

    public GestorAmbitos(Programa programa) {
        super(programa);
        _ambitos = new ArrayList<>();
        _ultimoAmbito = null;
    }

    public Ambito nuevoAmbito(int[] parametrosEntrada, Macro macroAsociada) {
        _ultimoAmbito = new Ambito(_programa, parametrosEntrada, macroAsociada, this);
        _ambitos.add(_ultimoAmbito);

        return _ultimoAmbito;
    }
    
    public Ambito nuevoAmbito(int[] parametrosEntrada, ArrayList<String> lineas) {
        _ultimoAmbito = new Ambito(_programa, parametrosEntrada, lineas, this);
        _ambitos.add(_ultimoAmbito);

        return _ultimoAmbito;
    }
    
    public void eliminarAmbitoActual() {
        if (!_ambitos.isEmpty()) {
            _ambitos.remove(_ultimoAmbito);

            if (!_ambitos.isEmpty()) {
                _ultimoAmbito = _ambitos.get(_ambitos.size() - 1);
            } else {
                _ultimoAmbito = null;
            }
        }
    }

    public Ambito ambitoRaiz() {
        if (!_ambitos.isEmpty()) {
            return _ambitos.get(0);
        } else {
            return null;
        }
    }

    @Override
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
