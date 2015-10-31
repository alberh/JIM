package org.alberto.interprete.parsers.analizadormacros;

import org.alberto.interprete.Programa;
import org.alberto.interprete.util.Macro;
import org.alberto.interprete.parsers.Acciones;
import org.alberto.interprete.util.Etiqueta;
import org.alberto.interprete.util.GestorVariables;
import org.alberto.interprete.util.Variable;

public class MacrosAcciones extends Acciones {

    private static Macro _macro;

    public static String filtrarIDVariable(String id) {
        id = Variable.normalizarID(id);
        
        if (Programa.etapaFinal() == Programa.Etapa.EXPANDIENDO_MACROS) {
            id = "V" + id;
        }
        
        return id;
    }

    public static String filtrarIDEtiqueta(String id) {
        id = Etiqueta.normalizarID(id);
        
        if (Programa.etapaFinal() == Programa.Etapa.EXPANDIENDO_MACROS) {
            id = "L" + id;
        }
        
        return id;
    }

    public static void nuevaMacro(Object idMacro) {
        _macro = Macro.set(idMacro.toString());
    }

    public static void nuevaVariable(Object idVariable) {
        _macro.nuevaVariable(idVariable.toString());
    }

    public static void nuevaEtiqueta(Object idEtiqueta) {
        _macro.nuevaEtiqueta(idEtiqueta.toString());
    }

    public static void nuevaEtiquetaSalto(Object idEtiqueta) {
        _macro.nuevaEtiquetaSalto(idEtiqueta.toString());
    }

    public static void nuevaLlamadaAMacro(Object idMacro) {
        _macro.nuevaLlamadaAMacro(idMacro.toString());
    }

    public static void defineCuerpo(Object cuerpoMacro) {
        _macro.cuerpo(cuerpoMacro.toString());
    }
}
