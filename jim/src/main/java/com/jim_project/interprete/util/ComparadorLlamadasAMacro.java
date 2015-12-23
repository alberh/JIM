package com.jim_project.interprete.util;

import com.jim_project.interprete.componente.LlamadaAMacro;
import java.util.Comparator;

public class ComparadorLlamadasAMacro implements Comparator<LlamadaAMacro> {

    @Override
    public int compare(LlamadaAMacro llm1, LlamadaAMacro llm2) {
        return llm1.linea() - llm2.linea();
    }
}
