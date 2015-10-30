package org.alberto.interprete.util;

import java.util.Comparator;

class ComparadorVariables implements Comparator<Variable> {

    @Override
    public int compare(Variable v1, Variable v2) {
        return v1.id().compareToIgnoreCase(v2.id());
    }
}
