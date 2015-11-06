package org.alberto.interprete;

import org.alberto.interprete.parsers.Parser;
import org.alberto.interprete.parsers.lmodel.LParser;
import org.alberto.interprete.parsers.loopmodel.LoopParser;
import org.alberto.interprete.parsers.whilemodel.WhileParser;
import org.alberto.interprete.util.Configuracion;

public class Modelo {

    public enum Tipo {

        L, LOOP, WHILE
    };

    private Tipo _tipo;
    private Parser _parser;
    private String _ruta;

    public Modelo(String nombre) {
        this(Modelo.obtenerTipo(nombre));
    }

    public Modelo(Tipo tipo) {
        _tipo = tipo;

        switch (_tipo) {
            case L:
                _parser = new LParser(null);
                break;

            case LOOP:
                _parser = new LoopParser(null);
                break;

            case WHILE:
                _parser = new WhileParser(null);
                break;
        }
        
        _ruta = Modelo.obtenerRuta(_tipo);
    }

    public Tipo tipo() {
        return _tipo;
    }

    public Parser parser() {
        return _parser;
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
