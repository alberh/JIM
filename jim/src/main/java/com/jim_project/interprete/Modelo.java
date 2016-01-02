package com.jim_project.interprete;

import com.jim_project.interprete.util.Configuracion;
import com.jim_project.interprete.util.Error;

public class Modelo {

    private Configuracion _configuracion;

    public enum Tipo {

        PREVIO, L, LOOP, WHILE
    };

    private Tipo _tipo;
    private final String _ruta;

    public Modelo(String nombre, Configuracion configuracion) {
        _configuracion = configuracion;

        if (_configuracion != null) {
            switch (nombre.toUpperCase()) {
                case "L":
                    _tipo = Tipo.L;
                    break;

                case "LOOP":
                    _tipo = Tipo.LOOP;
                    break;

                case "WHILE":
                    _tipo = Tipo.WHILE;
                    break;

                default:
                    Error.deModeloNoValido(nombre);
                    _tipo = null;
            }
            switch (_tipo) {
                case L:
                    _ruta = _configuracion.rutaMacrosL();
                    break;

                case LOOP:
                    _ruta = _configuracion.rutaMacrosLoop();
                    break;

                case WHILE:
                    _ruta = _configuracion.rutaMacrosWhile();
                    break;
                    
                default:
                    _ruta = null;
            }
        } else {
            _tipo = null;
            _ruta = null;
        }
    }

    public Tipo tipo() {
        return _tipo;
    }

    public String ruta() {
        return _ruta;
    }
}
