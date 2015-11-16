package com.jim_project.interprete.parser.loopmodel;

import com.jim_project.interprete.componente.Bucle;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

public class LoopAcciones extends Acciones {

    public LoopAcciones(Ambito ambito) {
        super(ambito);
    }

    public void abreBucle(Object idVariable, int lineaApertura) {
        Bucle bucle = _ambito.bucles().obtenerBucleLineaInicio(lineaApertura);
        Variable variable = obtenerVariable(idVariable);

        if (!bucle.inicializado()) {
            bucle.contador(variable.valor());
        }

        if (bucle.contador() > 0) {
            bucle.decremento();
        } else {
            _ambito.controladorEjecucion().numeroLineaActual(bucle.lineaFin());
            bucle.resetContador();
        }
    }

    public void cierraBucle(int lineaCierre) {
        Bucle bucle = _ambito.bucles().obtenerBucleLineaFin(lineaCierre);
        _ambito.controladorEjecucion().salto(bucle.lineaInicio());
    }
}
