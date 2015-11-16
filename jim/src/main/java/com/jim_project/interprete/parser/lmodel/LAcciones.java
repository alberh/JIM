package com.jim_project.interprete.parser.lmodel;

import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.parser.Acciones;

public class LAcciones extends Acciones {

    public LAcciones(Programa programa) {
        super(programa);
    }

    public void saltoCondicional(Object idVariable, Object idEtiqueta) {
        Variable v = obtenerVariable(idVariable);

        if (v.valor() != 0) {
            saltoIncondicional(idEtiqueta);
        }
    }

    public void saltoIncondicional(Object idEtiqueta) {
        Etiqueta et = obtenerEtiqueta(idEtiqueta);

        if (et == null) {
            _programa.gestorAmbitos().ambitoActual().controladorEjecucion().terminar();
        } else {
            // el -1 es para que cuando se llame a Programa.lineaSiguiente() no se salte la línea a la que queremos ir
            _programa.gestorAmbitos().ambitoActual().controladorEjecucion().salto(et.linea());
        }
    }

    protected Etiqueta obtenerEtiqueta(Object id) {
        // tratamiento de errores
        return _programa.gestorAmbitos().ambitoActual().etiquetas().obtenerEtiqueta(id.toString());
    }
}
