package com.jim_project.interprete.parser.previo;

import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

public class PrevioAcciones extends Acciones {

    public PrevioAcciones(Ambito ambito) {
        super(ambito);
    }

    public void definirVariable(Object idVariable) {
        _ultimaVariableAsignada = _ambito.gestorVariables().nuevaVariable(idVariable.toString());
    }

    public void definirEtiqueta(Object idEtiqueta, Object numeroLinea) {
        _ambito.gestorEtiquetas().nuevaEtiqueta(idEtiqueta.toString(),
                obtenerValor(numeroLinea));
    }

    public void definirInicioBucle(Object numeroLinea) {
        _ambito.gestorBucles().definirLineaInicio(obtenerValor(numeroLinea));
    }

    public void definirFinBucle(Object numeroLinea) {
        _ambito.gestorBucles().definirLineaFin(obtenerValor(numeroLinea));
    }
    
    public void definirLlamadaAMacro(Object idMacro) {
        _ambito.gestorLlamadasAMacro().definirLlamadaAMacro(_ultimaVariableAsignada, idMacro);
    }

    public void definirVariableEntradaMacro(Object parametro) {
        _ambito.gestorLlamadasAMacro().definirVariableEntradaMacro(parametro.toString());
    }
}
