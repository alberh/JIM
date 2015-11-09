package com.jim_project.interprete.parser.loopmodel;

import com.jim_project.interprete.componente.Bucle;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.parser.Acciones;

public class LoopAcciones extends Acciones {

    public static void abreBucle(Object idVariable, int lineaApertura) {

        Bucle bucle = Bucle.getPorLineaInicio(lineaApertura);
        Variable variable = obtenerVariable(idVariable);

        if (!bucle.inicializado()) {

            bucle.contador(variable.valor());
        }

        if (bucle.contador() > 0) {

            bucle.decremento();
        } else {

            Programa.numeroLineaActual(bucle.lineaFin());
            bucle.resetContador();
        }
    }

    public static void cierraBucle(int lineaCierre) {

        Bucle bucle = Bucle.getPorLineaFin(lineaCierre);

        Programa.salto(bucle.lineaInicio());
    }
}
