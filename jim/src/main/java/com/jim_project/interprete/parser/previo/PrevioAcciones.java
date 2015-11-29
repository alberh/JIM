package com.jim_project.interprete.parser.previo;

import com.jim_project.interprete.util.ParametrosExpansion;
import java.util.ArrayList;

import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

public class PrevioAcciones extends Acciones {

    private String _idUltimaVariable;
    private ArrayList<ParametrosExpansion> _expansiones;
    private ParametrosExpansion _ultimaExpansion;

    public PrevioAcciones(Ambito ambito) {
        super(ambito);
        _expansiones = _ambito.expansiones();
    }

    public void definirVariable(Object idVariable) {
        _ambito.variables().nuevaVariable(idVariable.toString());
    }

    public void definirVariableYMantener(Object idVariable) {
        definirVariable(idVariable);
        _idUltimaVariable = idVariable.toString();
    }

    public void definirEtiqueta(Object idEtiqueta, Object numeroLinea) {
        _ambito.etiquetas().nuevaEtiqueta(idEtiqueta.toString(), obtenerValor(numeroLinea));
    }

    public void definirInicioBucle(Object numeroLinea) {
        _ambito.bucles().definirLineaInicio(obtenerValor(numeroLinea));
    }

    public void definirFinBucle(Object numeroLinea) {
        _ambito.bucles().definirLineaFin(obtenerValor(numeroLinea));
    }

    public int llamadasAMacros() {
        return _expansiones.size();
    }

    public void prepararParaExpandir(Object idMacro) {
        int linea = _ambito.controladorEjecucion().numeroLineaActual();
        String vSalida = _idUltimaVariable;
        
        _ultimaExpansion = new ParametrosExpansion(linea, vSalida, idMacro.toString());
        _expansiones.add(_ultimaExpansion);
    }

    public void prepararVariableEntrada(Object parametro) {
        _ultimaExpansion.variablesEntrada().add(parametro.toString());
    }
}
