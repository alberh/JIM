package com.jim_project.interprete.util;

import com.jim_project.interprete.componente.LlamadaAMacro;
import java.util.Comparator;

/**
 * Clase que implementa un {@link Comparator} para poder comparar dos objetos {@link LlamadaAMacro}
 * según su número de línea a través del método {@link java.util.List#sort()}.
 * @author Alberto García González
 */
public class ComparadorLlamadasAMacro implements Comparator<LlamadaAMacro> {

    /**
     * Compara dos {@link LlamadaAMacro}.
     * @param llm1 El primer objeto a ser comparado.
     * @param llm2 El segundo objeto a ser comparado.
     * @return Un entero negativo, cero, o un entero positivo si el primer argumento
     * es menor, igual, o mayor que el segundo, respectivamente.
     */
    @Override
    public int compare(LlamadaAMacro llm1, LlamadaAMacro llm2) {
        return llm1.linea() - llm2.linea();
    }
}
