package org.alberto.interprete.parsers.whilemodel;

import org.alberto.interprete.Bucle;
import org.alberto.interprete.Variable;
import org.alberto.interprete.Programa;
import org.alberto.interprete.parsers.Acciones;

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
