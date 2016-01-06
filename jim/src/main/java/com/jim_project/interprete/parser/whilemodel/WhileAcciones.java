package com.jim_project.interprete.parser.whilemodel;

import com.jim_project.interprete.componente.Bucle;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

/**
 * Clase que aporta métodos encargados de realizar las operaciones únicas del
 * modelo While.
 *
 * @author Alberto García González
 */
public class WhileAcciones extends Acciones {

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito actual.
     */
    public WhileAcciones(Ambito ambito) {
        super(ambito);
    }

    /**
     * Indica al programa que se está entrando en un bucle.
     *
     * @param idVariable El identificador de la variable indicada en la
     * condición del bucle.
     * @param lineaApertura Número de línea del incio del bucle.
     */
    public void abreBucle(Object idVariable, int lineaApertura) {
        Bucle bucle = _ambito.gestorBucles().obtenerBucleLineaInicio(lineaApertura);
        Variable variable = obtenerVariable(idVariable);

        if (variable.valor() == 0) {
            _ambito.controladorEjecucion().numeroLineaActual(bucle.lineaFin());
        }
    }

    /**
     * Indica al programa que se ha llegado a la línea final de un bucle.
     *
     * @param lineaCierre El número de línea donde termina el bucle.
     */
    public void cierraBucle(int lineaCierre) {
        Bucle bucle = _ambito.gestorBucles().obtenerBucleLineaFin(lineaCierre);
        _ambito.controladorEjecucion().salto(bucle.lineaInicio());
    }
}
