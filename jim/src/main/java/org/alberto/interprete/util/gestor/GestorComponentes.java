package org.alberto.interprete.util.gestor;

import org.alberto.interprete.Interprete;
import org.alberto.interprete.util.Ambito;

public abstract class GestorComponentes {

    protected Interprete _programa;

    public GestorComponentes(Interprete programa) {
        _programa = programa;
    }

    protected Interprete programa() {
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
