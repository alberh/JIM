package org.alberto.interprete.parsers.analizadormacros;

import org.alberto.interprete.Macro;
import org.alberto.interprete.parsers.Acciones;

public class MacrosAcciones extends Acciones {
    
    private static Macro _macro;
    
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
