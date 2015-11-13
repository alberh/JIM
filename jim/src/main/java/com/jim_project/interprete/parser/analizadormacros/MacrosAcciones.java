package com.jim_project.interprete.parser.analizadormacros;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Macro;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.Variable;

public class MacrosAcciones extends Acciones {

    public static String filtrarIDVariable(String id) {
        id = Variable.normalizarID(id);
        
        // if (programa.objetivo() == Programa.OBJETIVO.EXPANDIR) {
        if (Programa.etapaFinal() == Programa.Etapa.EXPANDIENDO_MACROS) {
            id = "V" + id;
        }
        
        return id;
    }

    public static String filtrarIDEtiqueta(String id) {
        id = Etiqueta.normalizarID(id);
        
        // if (programa.objetivo() == Programa.OBJETIVO.EXPANDIR) {
        if (Programa.etapaFinal() == Programa.Etapa.EXPANDIENDO_MACROS) {
            id = "L" + id;
        }
        
        return id;
    }

    @Deprecated
    public static Macro nuevaMacro(Object idMacro) {
        return Macro.set(idMacro.toString());
    }

    public static void nuevaVariable(Object idVariable, Macro macro) {
        macro.nuevaVariable(idVariable.toString());
    }

    public static void nuevaEtiqueta(Object idEtiqueta, Macro macro) {
        macro.nuevaEtiqueta(idEtiqueta.toString());
    }

    public static void nuevaEtiquetaSalto(Object idEtiqueta, Macro macro) {
        macro.nuevaEtiquetaGoTo(idEtiqueta.toString());
    }

    public static void nuevaLlamadaAMacro(Object idMacro, Macro macro) {
        macro.nuevaLlamadaAMacro(idMacro.toString());
    }

    public static void cuerpo(Object cuerpoMacro, Macro macro) {
        macro.cuerpo(cuerpoMacro.toString());
    }
}
