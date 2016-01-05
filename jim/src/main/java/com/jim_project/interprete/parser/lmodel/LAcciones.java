package com.jim_project.interprete.parser.lmodel;

import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

public class LAcciones extends Acciones {

    public LAcciones(Ambito ambito) {
        super(ambito);
    }

    public void saltoCondicional(Object idVariable, Object idEtiqueta) {
        Variable v = obtenerVariable(idVariable);

        if (v.valor() != 0) {
            saltoIncondicional(idEtiqueta, true);
        }
    }

    public void saltoIncondicional(Object idEtiqueta) {
        saltoIncondicional(idEtiqueta, false);
    }

    public void saltoIncondicional(Object idEtiqueta, boolean vieneDeCondicion) {
        // La instrucción GOTO L del modelo L es considerada una macro.
        if (!vieneDeCondicion && !_ambito.programa().macrosPermitidas()) {
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        Etiqueta et = obtenerEtiqueta(idEtiqueta);

        if (et == null) {
            _ambito.controladorEjecucion().terminar();
        } else {
            _ambito.controladorEjecucion().salto(et.linea());
        }
    }

    protected Etiqueta obtenerEtiqueta(Object id) {
        // tratamiento de errores
        return _ambito.gestorEtiquetas().obtenerEtiqueta(id.toString());
    }
}
