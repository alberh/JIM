package com.jim_project.interprete.parser.analizadormacros;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Macro;
import com.jim_project.interprete.parser.Acciones;

public class MacrosAcciones extends Acciones {
    
    private Macro _macroEnProceso;
    private Programa _programa;

    public MacrosAcciones(Programa programa) {
        super(null);
        _programa = programa;
    }
    
    public Macro nuevaMacro(Object idMacro) {
        _macroEnProceso = _programa.gestorMacros().nuevaMacro(idMacro.toString());
        return _macroEnProceso;
    }

    public void nuevaVariable(Object idVariable) {
        _macroEnProceso.nuevaVariable(idVariable.toString());
    }

    public void nuevaEtiqueta(Object idEtiqueta) {
        _macroEnProceso.nuevaEtiqueta(idEtiqueta.toString());
    }

    public void nuevaEtiquetaSalto(Object idEtiqueta) {
        _macroEnProceso.nuevaEtiquetaGoTo(idEtiqueta.toString());
    }

    public void nuevaLlamadaAMacro(Object idMacro) {
        _macroEnProceso.nuevaLlamadaAMacro(idMacro.toString());
    }

    public void cuerpo(Object cuerpoMacro) {
        _macroEnProceso.cuerpo(cuerpoMacro.toString());
    }
}
