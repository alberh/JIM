package org.alberto.interprete.util;

import org.alberto.interprete.ProgramaNoEstatico;
import org.alberto.interprete.util.gestor.GestorEtiquetas;
import org.alberto.interprete.util.gestor.GestorMacros;
import org.alberto.interprete.util.gestor.GestorBucles;
import org.alberto.interprete.util.gestor.GestorVariables;

public class Ambito {
    
    private GestorVariables _gestorVariables;
    private GestorBucles _gestorBucles;
    private GestorEtiquetas _gestorEtiquetas;
    private GestorMacros _gestorMacros;
    
    private ProgramaNoEstatico _programa;
    
    private int[] _parametrosEntrada;
    private Macro _macroAsociada;
    
    public Ambito(ProgramaNoEstatico programa) {
        this(programa, null, null);
    }
    
    public Ambito(ProgramaNoEstatico programa, int[] parametrosEntrada) {
        this(programa, parametrosEntrada, null);
    }
    
    public Ambito(ProgramaNoEstatico programa, Macro macroAsociada) {
        this(programa, null, macroAsociada);
    }
    
    public Ambito(ProgramaNoEstatico programa,
            int[] parametrosEntrada,
            Macro macroAsociada) {
        
        _programa = programa;
        
        _gestorVariables = new GestorVariables(_programa);
        _gestorBucles = new GestorBucles(_programa);
        _gestorEtiquetas = new GestorEtiquetas(_programa);
        _gestorMacros = new GestorMacros(_programa);
        
        _parametrosEntrada = parametrosEntrada;
        _macroAsociada = macroAsociada;
    }
    
    public GestorVariables variables() {
        return _gestorVariables;
    }
    
    public GestorBucles bucles() {
        return _gestorBucles;
    }
    
    public GestorEtiquetas etiquetas() {
        return _gestorEtiquetas;
    }
    
    public GestorMacros macros() {
        return _gestorMacros;
    }
    
    public int[] parametrosEntrada() {
        return _parametrosEntrada;
    }
    
    public Macro macroAsociada() {
        return _macroAsociada;
    }
    
    public void limpiar() {
        _gestorVariables.limpiar();
        _gestorBucles.limpiar();
        _gestorEtiquetas.limpiar();
        _gestorMacros.limpiar();
    }
}
