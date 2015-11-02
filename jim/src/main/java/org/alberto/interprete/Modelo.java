/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alberto.interprete;

import org.alberto.interprete.parsers.Parser;
import org.alberto.interprete.parsers.lmodel.LParser;
import org.alberto.interprete.parsers.loopmodel.LoopParser;
import org.alberto.interprete.parsers.whilemodel.WhileParser;
import org.alberto.interprete.util.Configuracion;

/**
 *
 * @author alber_000
 */
public class Modelo {
    
    public enum Tipo {

        L, LOOP, WHILE
    };
    
    private Tipo _tipo;
    private Parser _parser;
    
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
    }
    
    public Tipo tipo() {
        return _tipo;
    }
    
    public Parser parser() {
        return _parser;
    }
    
    public String ruta() {
        switch (_tipo) {
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
