package org.alberto.interprete.parsers.analizadormacros;

import org.alberto.interprete.Programa;
import org.alberto.interprete.util.Macro;
import org.alberto.interprete.parsers.Acciones;
import org.alberto.interprete.util.Etiqueta;
import org.alberto.interprete.util.Variable;

public class MacrosAcciones extends Acciones {

    private static Macro _macroEnProceso;

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

    public static void nuevaMacro(Object idMacro) {
        _macroEnProceso = Macro.set(idMacro.toString());
    }

    public static void nuevaVariable(Object idVariable) {
        _macroEnProceso.nuevaVariable(idVariable.toString());
    }

    public static void nuevaEtiqueta(Object idEtiqueta) {
        _macroEnProceso.nuevaEtiqueta(idEtiqueta.toString());
    }

    public static void nuevaEtiquetaSalto(Object idEtiqueta) {
        _macroEnProceso.nuevaEtiquetaGoTo(idEtiqueta.toString());
    }

    public static void nuevaLlamadaAMacro(Object idMacro) {
        _macroEnProceso.nuevaLlamadaAMacro(idMacro.toString());
    }

    public static void cuerpo(Object cuerpoMacro) {
        _macroEnProceso.cuerpo(cuerpoMacro.toString());
    }
}
