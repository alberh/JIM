package com.jim_project.interprete.parser.whilemodel;

import com.jim_project.interprete.componente.Bucle;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.parser.Acciones;

public class WhileAcciones extends Acciones {

    public static void abreBucle(Object idVariable, int lineaApertura) {
        Bucle bucle = Bucle.getPorLineaInicio(lineaApertura);
        Variable variable = obtenerVariable(idVariable);

        if (variable.valor() == 0) {
            Programa.numeroLineaActual(bucle.lineaFin());
        }
    }

    public static void cierraBucle(int lineaCierre) {
        Bucle bucle = Bucle.getPorLineaFin(lineaCierre);
        Programa.salto(bucle.lineaInicio());
    }
}
