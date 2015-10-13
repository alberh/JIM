package interprete.parsers.loopmodel;

import interprete.*;
import interprete.parsers.Acciones;

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
