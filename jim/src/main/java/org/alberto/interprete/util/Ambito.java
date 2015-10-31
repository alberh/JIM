package org.alberto.interprete.util;

public class Ambito {
    
    private GestorVariables _gestorVariables;
    private GestorBucles _gestorBucles;
    private GestorEtiquetas _gestorEtiquetas;
    private GestorMacros _gestorMacros;
    
    private Ambito _ambitoPadre;
    
    public Ambito() {
        this(null);
    }
    
    public Ambito(Ambito ambitoPadre) {
        _ambitoPadre = ambitoPadre;
        
        _gestorVariables = new GestorVariables();
        _gestorBucles = new GestorBucles();
        _gestorEtiquetas = new GestorEtiquetas();
        _gestorMacros = new GestorMacros();
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
    
    public Ambito ambitoPadre() {
        return _ambitoPadre;
    }
    
    public boolean esAmbitoRaiz() {
        return _ambitoPadre == null;
    }
}
