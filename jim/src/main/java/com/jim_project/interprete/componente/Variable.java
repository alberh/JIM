package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.Error;
import com.jim_project.interprete.gestor.GestorVariables;

/**
 * Clase que abstrae el concepto de variable del programa. Mantiene información
 * sobre su tipo, valor e íncide.
 *
 * @author Alberto García González
 */
public class Variable extends Componente {

    /**
     * Enumeración para distinguir los tipos de variables.
     */
    public enum Tipo {

        /**
         * Variables de entrada.
         */
        ENTRADA,
        /**
         * Variables locales.
         */
        LOCAL,
        /**
         * Variable de salida.
         */
        SALIDA
    }

    private int _valor;
    private Tipo _tipo;
    private int _indice;

    /**
     * Constructor de clase.
     *
     * @param id El identificador de la variable.
     * @param gestorVariables Una referencia al gestor que la contiene.
     */
    public Variable(String id, GestorVariables gestorVariables) {
        this(id, 0, gestorVariables);
    }

    /**
     * Constructor de clase.
     *
     * @param id El identificador de la variable.
     * @param valor El valor de la variable.
     * @param gestorVariables Una referencia al gestor que la contiene.
     */
    public Variable(String id, int valor, GestorVariables gestorVariables) {
        super(Variable.normalizarID(id), gestorVariables);

        _valor = valor;

        char charTipo = _id.toUpperCase().charAt(0);
        switch (charTipo) {
            case 'X':
                _tipo = Tipo.ENTRADA;
                break;

            case 'Y':
                _tipo = Tipo.SALIDA;
                break;

            case 'Z':
                _tipo = Tipo.LOCAL;
                break;

            default:
                _gestor.ambito().programa().error().deTipoDeVariableNoValido(charTipo);
                _tipo = null;
        }

        if (_id.equals("Y")) {
            _indice = 1;
        } else {
            try {
                _indice = Integer.parseInt(_id.substring(1));
            } catch (NumberFormatException ex) {
                _indice = 0;
                _gestor.ambito().programa().error().alObtenerIndiceDeVariable(_id);
            }
        }
    }

    /**
     * Devuelve el tipo de la variable.
     *
     * @return El tipo de la variable.
     */
    public Tipo tipo() {
        return _tipo;
    }

    /**
     * Devuelve el carácter que representa el tipo de la variable. Los posibles
     * tipos son: 'X' para las variables de entrada, 'Z' para las locales e 'Y'
     * para la variable de salida.
     *
     * @return El carácter que representa el tipo de la variable.
     */
    public char tokenTipo() {
        switch (_tipo) {
            case ENTRADA:
                return 'X';

            case LOCAL:
                return 'Z';

            case SALIDA:
                return 'Y';
        }

        return '0';
    }

    /**
     * Devuelve el índice de la variable.
     *
     * @return El índice de la variable.
     */
    public int indice() {
        return _indice;
    }

    /**
     * Devuelve el valor de la variable.
     *
     * @return El valor de la variable.
     */
    public int valor() {
        return _valor;
    }

    /**
     * Asigna un nuevo valor a la variable. Si nuevoValor es negativo, se le
     * asignará el valor 0.
     *
     * @param nuevoValor El nuevo valor de la variable.
     */
    public void valor(int nuevoValor) {
        _valor = Math.max(0, nuevoValor);
    }

    /**
     * Realiza un incremento unitario sobre el valor de la variable. Si el valor
     * de la variable alcanza el máximo permitido por un entero, no se
     * modificará.
     */
    public void incremento() {
        if (_valor < Integer.MAX_VALUE) {
            ++_valor;
        }
    }

    /**
     * Realiza un decremento unitario sobre el valor de la variable. Si el valor
     * de la variable es 0, no se modificará.
     */
    public void decremento() {
        if (_valor > 0) {
            --_valor;
        }
    }

    /**
     * Devuelve una cadena que representa la variable mediante su identificador
     * y su valor.
     *
     * @return Una cadena que representa la variable.
     */
    @Override
    public String toString() {
        return "(" + _id + ", " + _valor + ")";
    }

    /**
     * Método encargado de normalizar el identificador de una variable,
     * cambiando a mayúsculas su grupo y añadiendo el subíndice 1 cuando sea
     * necesario.
     *
     * @param id El identificador de la variable.
     * @return El identificador normalizado.
     */
    public static String normalizarID(String id) {
        if (id != null) {
            id = id.toUpperCase();
            int len = id.length();

            if (!id.contains("Y") && len == 1) {
                return id + "1";
            } else {
                return id;
            }
        } else {
            return "";
        }
    }
}
