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
        Bucle bucle = _ambito.gestorBucles().obtenerBucleLineaInicio(lineaApertura);
        Variable variable = obtenerVariable(idVariable);

        if (!bucle.contadorEnUso()) {
            bucle.contador(variable.valor());
        }

        if (bucle.contador() > 0) {
            bucle.decremento();
        } else {
            _ambito.controladorEjecucion().numeroLineaActual(bucle.lineaFin());
            bucle.reiniciarContador();
        }
    }

    public void cierraBucle(int lineaCierre) {
        Bucle bucle = _ambito.gestorBucles().obtenerBucleLineaFin(lineaCierre);
        _ambito.controladorEjecucion().salto(bucle.lineaInicio());
    }
}
