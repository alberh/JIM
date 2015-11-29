package com.jim_project.interprete;

import com.jim_project.interprete.util.Configuracion;

public class Modelo {

    public enum Tipo {

        PREVIO, L, LOOP, WHILE
    };

    private Tipo _tipo;
    private String _ruta;

    public Modelo(String nombre) {
        this(Modelo.obtenerTipo(nombre));
    }

    public Modelo(Tipo tipo) {
        _tipo = tipo;
        _ruta = Modelo.obtenerRuta(_tipo);
    }

    public Tipo tipo() {
        return _tipo;
    }
    
    public String ruta() {
        return _ruta;
    }

    // Métodos estáticos
    private static Tipo obtenerTipo(String nombre) {
        switch (nombre.toUpperCase()) {
            case "L":
                return Tipo.L;

            case "LOOP":
                return Tipo.LOOP;

            case "WHILE":
                return Tipo.WHILE;

            default:
                return null;
        }
    }

    private static String obtenerRuta(Tipo tipo) {
        switch (tipo) {
            case L:
                return Configuracion.rutaMacrosL();

            case LOOP:
                return Configuracion.rutaMacrosLoop();

            case WHILE:
                return Configuracion.rutaMacrosWhile();

            default:
                return null;
        }
    }
}
