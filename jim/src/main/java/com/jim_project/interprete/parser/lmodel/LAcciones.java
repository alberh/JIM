package com.jim_project.interprete.parser.lmodel;

import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

/**
 * Clase que provee métodos para realizar las operaciones únicas del modelo L.
 *
 * @author Alberto García González
 */
public class LAcciones extends Acciones {

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito actual.
     */
    public LAcciones(Ambito ambito) {
        super(ambito);
    }

    /**
     * Simula un salto condicional en el modelo L.
     *
     * @param idVariable El identificador de la variable a comprobar para
     * decidir el resultado del salto.
     * @param idEtiqueta El identificador de la etiqueta a la que saltar si
     * corresponde.
     */
    public void saltoCondicional(Object idVariable, Object idEtiqueta) {
        Variable v = obtenerVariable(idVariable);

        if (v.valor() != 0) {
            saltoIncondicional(idEtiqueta, true);
        }
    }

    /**
     * Simula un salto incondicional en el modelo L.
     *
     * @param idEtiqueta El identificador de la etiqueta a la que saltar.
     */
    public void saltoIncondicional(Object idEtiqueta) {
        saltoIncondicional(idEtiqueta, false);
    }

    private void saltoIncondicional(Object idEtiqueta, boolean vieneDeCondicion) {
        // La instrucción GOTO L del modelo L es considerada una macro.
        if (!vieneDeCondicion && !_ambito.programa().macrosPermitidas()) {
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        Etiqueta et = _ambito.gestorEtiquetas().obtenerEtiqueta(idEtiqueta.toString());

        if (et == null) {
            _ambito.controladorEjecucion().terminar();
        } else {
            _ambito.controladorEjecucion().salto(et.linea());
        }
    }
}
