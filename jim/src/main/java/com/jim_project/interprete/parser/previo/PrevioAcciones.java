package com.jim_project.interprete.parser.previo;

import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

/**
 * Clase que aporta métodos para realizar las operaciones únicas del analizador
 * previo.
 *
 * @author Alberto García González
 */
public class PrevioAcciones extends Acciones {

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito actual.
     */
    public PrevioAcciones(Ambito ambito) {
        super(ambito);
    }

    /**
     * Almacena una variable encontrada en el código en el gestor de variables
     * del ámbito actual.
     *
     * @param idVariable El identificador de la variable.
     */
    public void definirVariable(Object idVariable) {
        _ultimaVariableAsignada = _ambito.gestorVariables().nuevaVariable(idVariable.toString());
    }

    /**
     * Almacena una etiqueta encontrada en el código en el gestor de etiquetas
     * del ámbito actual.
     *
     * @param idEtiqueta El identificador de la etiqueta.
     * @param numeroLinea El número de línea en el que se ha encontrado la
     * etiqueta.
     */
    public void definirEtiqueta(Object idEtiqueta, Object numeroLinea) {
        _ambito.gestorEtiquetas().nuevaEtiqueta(idEtiqueta.toString(),
                obtenerValor(numeroLinea));
    }

    /**
     * Almacena la línea inicial de un bucle encontrado en el código en el
     * gestor de bucles del ámbito actual.
     *
     * @param numeroLinea El número de línea de inicio del bucle.
     */
    public void definirInicioBucle(Object numeroLinea) {
        _ambito.gestorBucles().definirLineaInicio(obtenerValor(numeroLinea));
    }

    /**
     * Almacena la línea final de un bucle encontrado en el código en el gestor
     * de bucles del ámbito actual.
     *
     * @param numeroLinea El número de línea final del bucle.
     */
    public void definirFinBucle(Object numeroLinea) {
        _ambito.gestorBucles().definirLineaFin(obtenerValor(numeroLinea));
    }

    /**
     * Almacena una llamada a macro encontrada en el código en el gestor de
     * llamadas a macro del ámbito actual.
     *
     * @param idMacro El identificador de la macro objetivo de la llamada.
     */
    public void definirLlamadaAMacro(Object idMacro) {
        _ambito.gestorLlamadasAMacro().definirLlamadaAMacro(_ultimaVariableAsignada, idMacro);
    }

    /**
     * Almacena una variable de entrada en la última llamada a macro almacenada
     * en el gestor de llamadas a macros del ámbito actual.
     *
     * @param parametro El parámetro de la llamada.
     */
    public void definirVariableEntradaMacro(Object parametro) {
        _ambito.gestorLlamadasAMacro().definirVariableEntradaMacro(parametro.toString());
    }
}
