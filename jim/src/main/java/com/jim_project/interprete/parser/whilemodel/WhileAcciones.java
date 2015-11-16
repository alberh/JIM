package com.jim_project.interprete.parser.whilemodel;

import com.jim_project.interprete.componente.Bucle;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

public class WhileAcciones extends Acciones {
    
    public WhileAcciones(Ambito ambito) {
        super(ambito);
    }

    public void abreBucle(Object idVariable, int lineaApertura) {
        Bucle bucle = _ambito.bucles().obtenerBucleLineaInicio(lineaApertura);
        Variable variable = obtenerVariable(idVariable);

        if (variable.valor() == 0) {
            _ambito.controladorEjecucion().numeroLineaActual(bucle.lineaFin());
        }
    }

    public void cierraBucle(int lineaCierre) {
        Bucle bucle = _ambito.bucles().obtenerBucleLineaFin(lineaCierre);
        _ambito.controladorEjecucion().salto(bucle.lineaInicio());
    }
}
