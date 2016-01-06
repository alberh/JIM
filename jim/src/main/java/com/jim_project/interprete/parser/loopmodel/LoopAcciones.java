package com.jim_project.interprete.parser.loopmodel;

import com.jim_project.interprete.componente.Bucle;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

/**
 * Clase que provee métodos para realizar las operaciones únicas del modelo
 * Loop.
 *
 * @author Alberto García González
 */
public class LoopAcciones extends Acciones {

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito actual.
     */
    public LoopAcciones(Ambito ambito) {
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
