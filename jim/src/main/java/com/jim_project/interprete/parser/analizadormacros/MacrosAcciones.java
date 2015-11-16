package com.jim_project.interprete.parser.analizadormacros;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Macro;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.componente.Ambito;

public class MacrosAcciones extends Acciones {

    public MacrosAcciones(Ambito ambito) {
        super(ambito);
    }
    
    public String filtrarIDVariable(String id) {
        id = Variable.normalizarID(id);
        
        if (_ambito.programa().objetivo() == Programa.Objetivo.EXPANDIR) {
            id = "V" + id;
        }
        
        return id;
    }

    public String filtrarIDEtiqueta(String id) {
        id = Etiqueta.normalizarID(id);
        
        if (_ambito.programa().objetivo() == Programa.Objetivo.EXPANDIR) {
            id = "L" + id;
        }
        
        return id;
    }

    public Macro nuevaMacro(Object idMacro) {
        return _ambito.macros().nuevaMacro(idMacro.toString());
    }

    public void nuevaVariable(Object idVariable, Macro macro) {
        macro.nuevaVariable(idVariable.toString());
    }

    public void nuevaEtiqueta(Object idEtiqueta, Macro macro) {
        macro.nuevaEtiqueta(idEtiqueta.toString());
    }

    public void nuevaEtiquetaSalto(Object idEtiqueta, Macro macro) {
        macro.nuevaEtiquetaGoTo(idEtiqueta.toString());
    }

    public void nuevaLlamadaAMacro(Object idMacro, Macro macro) {
        macro.nuevaLlamadaAMacro(idMacro.toString());
    }

    public void cuerpo(Object cuerpoMacro, Macro macro) {
        macro.cuerpo(cuerpoMacro.toString());
    }
}
