package com.jim_project.interprete.parser.previo;

import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

public class PrevioAcciones extends Acciones {

    public PrevioAcciones(Ambito ambito) {
        super(ambito);
    }

    public void definirVariable(Object idVariable) {
        _ultimaVariableAsignada = _ambito.variables().nuevaVariable(idVariable.toString());
    }

    public void definirEtiqueta(Object idEtiqueta, Object numeroLinea) {
        _ambito.etiquetas().nuevaEtiqueta(idEtiqueta.toString(),
                obtenerValor(numeroLinea));
    }

    public void definirInicioBucle(Object numeroLinea) {
        _ambito.bucles().definirLineaInicio(obtenerValor(numeroLinea));
    }

    public void definirFinBucle(Object numeroLinea) {
        _ambito.bucles().definirLineaFin(obtenerValor(numeroLinea));
    }
    
    public void definirLlamadaAMacro(Object idMacro) {
        _ambito.llamadasAMacro().definirMacro(_ultimaVariableAsignada, idMacro);
    }

    public void definirVariableEntradaMacro(Object parametro) {
        _ambito.llamadasAMacro().definirVariableEntradaMacro(parametro.toString());
    }
}
