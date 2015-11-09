package com.jim_project.interprete.parser.lmodel;

import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.parser.Acciones;

public class LAcciones extends Acciones {

    public static void saltoCondicional(Object idVariable, Object idEtiqueta) {

        Variable v = obtenerVariable(idVariable);

        if (v.valor() != 0) {

            saltoIncondicional(idEtiqueta);
        }
    }

    public static void saltoIncondicional(Object idEtiqueta) {

        Etiqueta et = obtenerEtiqueta(idEtiqueta);

        if (et == null) {

            Programa.terminar();
        } else {

            // el -1 es para que cuando se llame a Programa.lineaSiguiente() no se salte la línea a la que queremos ir
            Programa.salto(et.linea());
        }
    }
}
