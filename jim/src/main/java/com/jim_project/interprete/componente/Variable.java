package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.Error;
import com.jim_project.interprete.util.gestor.GestorVariables;

public class Variable extends Componente {

    public enum Tipo {

        ENTRADA, LOCAL, SALIDA
    }

    private int _valor;
    private Tipo _tipo;
    private int _indice;

    public Variable(String id, GestorVariables gestorVariables) {
        this(id, 0, gestorVariables);
    }

    public Variable(String id, int valor, GestorVariables gestorVariables) {
        super(Variable.normalizarID(id), gestorVariables);

        _valor = valor;
        _tipo = obtenerTipo(_id);
        _indice = obtenerIndice(_id);
    }

    public Tipo tipo() {
        return _tipo;
    }

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

    public int indice() {
        return _indice;
    }

    public int valor() {
        return _valor;
    }

    public void valor(int nuevoValor) {
        _valor = Math.max(0, nuevoValor);
    }

    public void incremento() {
        if (_valor < Integer.MAX_VALUE) {
            ++_valor;
        }
    }

    public void decremento() {
        if (_valor > 0) {
            --_valor;
        }
    }

    @Override
    public String toString() {
        return "(" + _id + ", " + _valor + ")";
    }

    /* Métodos auxiliares para la construcción
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

    private int obtenerIndice(String id) {
        if (id.equals("Y")) {
            return 1;
        } else {
            try {
                return Integer.parseInt(id.substring(1));
            } catch (NumberFormatException ex) {
                Error.alObtenerIndiceDeVariable(id);
            }
        }

        return 0;
    }

    private Tipo obtenerTipo(String id) {
        char tipo = id.toUpperCase().charAt(0);

        switch (tipo) {
            case 'X':
                return Tipo.ENTRADA;

            case 'Y':
                return Tipo.SALIDA;

            case 'Z':
                return Tipo.LOCAL;

            default:
                Error.deTipoDeVariableNoValido(tipo);
                return null;
        }
    }
}
