package org.alberto.interprete.util.gestor;

import org.alberto.interprete.ProgramaNoEstatico;
import org.alberto.interprete.util.Ambito;

public abstract class GestorComponentes {

    protected ProgramaNoEstatico _programa;

    public GestorComponentes(ProgramaNoEstatico programa) {
        _programa = programa;
    }

    protected ProgramaNoEstatico programa() {
        return _programa;
    }
    
    protected GestorAmbitos gestorAmbitos() {
        return _programa.gestorAmbitos();
    }
    
    protected Ambito ambitoActual() {
        return _programa.gestorAmbitos().ambitoActual();
    }

    protected abstract int count();

    protected abstract boolean vacio();
}
