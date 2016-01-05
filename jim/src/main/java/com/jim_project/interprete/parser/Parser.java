package com.jim_project.interprete.parser;

import com.jim_project.interprete.util.ControladorEjecucion;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.parser.analizadormacros.MacrosParser;

/**
 * Clase abstracta que sirve como base para las clases de los analizadores
 * gramaticales generados por la herramenta byacc/j.
 *
 * @author Alberto García González
 */
public abstract class Parser {

    /**
     * Referencia al analizador léxico con el que trabaja.
     */
    protected AnalizadorLexico _analizadorLexico;
    /**
     * Referencia al controlador de ejecución que ha creado este parser.
     */
    protected ControladorEjecucion _controladorEjecucion;
    /**
     * Referencia al ámbito que contiene el controlador de ejecución asociado.
     */
    protected Ambito _ambito;
    /**
     * Referencia al programa en ejecución.
     */
    protected Programa _programa;

    /**
     * Constructor de clase, utilizado únicamente por {@link MacrosParser} antes
     * de la creación de ningún ámbito, por lo que recibe una referencia al
     * programa en ejecución.
     *
     * @param programa El programa en ejecución.
     */
    public Parser(Programa programa) {
        _programa = programa;
        _controladorEjecucion = null;
        _ambito = null;
    }

    /**
     * Constructor de clase, utilizado por los parsers del analizador previo y
     * los de los modelos. Recibe una referencia al controlador de ejecución que
     * ha creado este parser.
     *
     * @param controladorEjecucion El controlador de ejecución que lo contiene.
     */
    public Parser(ControladorEjecucion controladorEjecucion) {
        _controladorEjecucion = controladorEjecucion;

        if (_controladorEjecucion != null) {
            _ambito = _controladorEjecucion.ambito();
            _programa = _ambito.programa();
        } else {
            _ambito = null;
            _programa = null;
        }
    }

    /**
     * Devuelve el analizador léxico asociado.
     *
     * @return El analizador léxico asociado.
     */
    public AnalizadorLexico analizadorLexico() {
        return _analizadorLexico;
    }

    /**
     * Devuelve el controlador de ejecución asociado.
     *
     * @return El controlador de ejecución asociado.
     */
    public ControladorEjecucion controladorEjecucion() {
        return _controladorEjecucion;
    }

    /**
     * Devuelve el ámbito asociado.
     *
     * @return El ámbito asociado.
     */
    public Ambito ambito() {
        return _ambito;
    }

    /**
     * Devuelve el programa asociado.
     *
     * @return El programa asociado.
     */
    public Programa programa() {
        return _programa;
    }

    /**
     * Método utilizado para hacer público el método {@code yyparse()}, que está
     * marcado como protegido debido a que así está marcado en las clases
     * generadas por byacc/j.
     *
     * @return El valor devuelto por {@link Parser#yylex()}.
     * @see Parser#yylex()
     */
    public abstract int parse();

    /**
     * Devuelve el siguiente tóken del analizador léxico.
     *
     * @return El siguiente tóken del analizador léxico.
     */
    protected abstract int yylex();

    /**
     * Método utilizado para gestionar errores de forma detallada.
     *
     * @param descripcion Descripción del error.
     * @param yystate Estado del autómata.
     * @param token Tóken leído.
     */
    public abstract void yyerror(String descripcion, int yystate, int token);

    /**
     * Método utilizado para gestionar errores de forma general.
     *
     * @param descripcion Descripción del error.
     */
    public void yyerror(String descripcion) {
        _programa.error().deTokenNoEsperado(descripcion);
    }
}
