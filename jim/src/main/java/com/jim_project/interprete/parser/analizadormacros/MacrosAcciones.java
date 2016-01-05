package com.jim_project.interprete.parser.analizadormacros;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Macro;
import com.jim_project.interprete.parser.Acciones;

/**
 * Clase que provee métodos para realizar las operaciones únicas del analizador
 * de macros.
 *
 * @author Alberto García González
 */
public class MacrosAcciones extends Acciones {

    private Macro _macroEnProceso;
    private final Programa _programa;

    /**
     * Constructor de clase.
     *
     * @param programa Referencia al programa en ejecución.
     */
    public MacrosAcciones(Programa programa) {
        super(null);
        _programa = programa;
    }

    /**
     * Registra una macro en el programa. La macro queda registrada como macro
     * en proceso, de forma que las llamadas a los otros métodos de esta clase
     * actuarán directamente sobre esta macro, hasta que se vuelve a definirse
     * otra.
     *
     * @param idMacro El identificador de la macro.
     * @return Una referencia a la {@link Macro} creada.
     */
    public Macro nuevaMacro(Object idMacro) {
        _macroEnProceso = _programa.gestorMacros().nuevaMacro(idMacro.toString());
        return _macroEnProceso;
    }

    /**
     * Registra una variable en la macro en proceso.
     *
     * @param idVariable El identificador de la variable a registrar.
     */
    public void nuevaVariable(Object idVariable) {
        _macroEnProceso.nuevaVariable(idVariable.toString());
    }

    /**
     * Registra una etiqueta que indica una posición a la que se puede saltar
     * en la macro en proceso.
     *
     * @param idEtiqueta El identificador de la etiqueta a registrar.
     */
    public void nuevaEtiqueta(Object idEtiqueta) {
        _macroEnProceso.nuevaEtiqueta(idEtiqueta.toString());
    }

    /**
     * Registra una etiqueta especificada en una instrucción de salto en la
     * macro en proceso.
     *
     * @param idEtiqueta El identificador de la etiqueta a registrar.
     */
    public void nuevaEtiquetaSalto(Object idEtiqueta) {
        _macroEnProceso.nuevaEtiquetaGoTo(idEtiqueta.toString());
    }

    /**
     * Registra una llamada a macro en la macro en proceso.
     *
     * @param idMacro El identificador de la llamada a macro a registrar.
     */
    public void nuevaLlamadaAMacro(Object idMacro) {
        _macroEnProceso.nuevaLlamadaAMacro(idMacro.toString());
    }

    /**
     * Almacena el código de la macro.
     *
     * @param cuerpoMacro La cadena que almacena el código de la macro.
     */
    public void cuerpo(Object cuerpoMacro) {
        _macroEnProceso.cuerpo(cuerpoMacro.toString());
    }
}
