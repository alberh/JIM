package org.alberto.interprete.util;

import org.alberto.interprete.componente.Variable;
import java.util.Comparator;

public class ComparadorVariables implements Comparator<Variable> {

    @Override
    public int compare(Variable v1, Variable v2) {
        return v1.id().compareToIgnoreCase(v2.id());
    }
}
