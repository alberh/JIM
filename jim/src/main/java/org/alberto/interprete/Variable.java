package org.alberto.interprete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

class ComparadorVariables implements Comparator<Variable> {

    @Override
    public int compare(Variable v1, Variable v2) {
        return v1.id().compareToIgnoreCase(v2.id());
    }
}

public class Variable {

    private static HashMap<Integer, Variable> _entrada = new HashMap<>();
    private static HashMap<Integer, Variable> _locales = new HashMap<>();
    private static Variable _salida = new Variable("Y");

    private static int _mayorEntrada = 0;
    private static int _mayorLocal = 0;

    private int _valor;
    private String _id;

    public enum EVariable {

        ENTRADA,
        LOCAL,
        SALIDA
    }

    public static Variable set(String id) {
        return set(id, 0);
    }

    public static Variable set(String id, int valor) {
        id = filtrar(id);
        char tipo = id.charAt(0);
        int indice = 1;
        Variable v = new Variable(id, valor);

        if (tipo != 'Y') {
            indice = Integer.parseInt(id.substring(1, id.length()));
        }

        switch (tipo) {
            case 'X':
                _entrada.put(indice, v);
                if (indice > _mayorEntrada) {
                    _mayorEntrada = indice;
                }
                break;

            case 'Z':
                _locales.put(indice, v);
                if (indice > _mayorLocal) {
                    _mayorLocal = indice;
                }
                break;

            case 'Y':
                _salida = v;
                break;
        }

        return v;
    }

    public static Variable get(EVariable tipo) {
        Variable v = null;

        switch (tipo) {
            case ENTRADA:
                _mayorEntrada++;
                v = new Variable("X" + _mayorEntrada);
                _entrada.put(_mayorEntrada, v);
                break;

            case LOCAL:
                _mayorLocal++;
                v = new Variable("Z" + _mayorLocal);
                _locales.put(_mayorLocal, v);
                break;

            case SALIDA:
                v = _salida;
        }

        return v;
    }

    public static Variable get(String id) {
        id = filtrar(id);
        char tipo = id.charAt(0);
        Variable v = null;
        int indice = 1;

        if (tipo != 'Y') {
            indice = Integer.parseInt(id.substring(1, id.length()));
        }

        switch (tipo) {
            case 'X':
                v = _entrada.get(indice);
                break;
                
            case 'Z':
                v = _locales.get(indice);
                break;
                
            case 'Y':
                v = _salida;
                break;
        }

        return v;
    }

    public static ArrayList<Variable> variablesEntrada() {
        ArrayList<Variable> variables = new ArrayList<Variable>(_entrada.values());
        variables.sort(new ComparadorVariables());
        return variables;
    }

    public static ArrayList<Variable> variablesLocales() {
        ArrayList<Variable> variables = new ArrayList<Variable>(_locales.values());
        variables.sort(new ComparadorVariables());
        return variables;
    }

    public static Variable variableSalida() {
        return _salida;
    }

    public static void limpiar() {
        _entrada.clear();
        _locales.clear();
        _salida = new Variable("Y");

        _mayorEntrada = 0;
        _mayorLocal = 0;
    }

    private Variable(String id) {
        this._id = id;
        this._valor = 0;
    }

    private Variable(String id, int valor) {
        this(id);
        if (valor > 0) {
            this._valor = valor;
        }
    }

    public String id() {
        return _id;
    }

    public int valor() {
        return _valor;
    }

    public void valor(int nuevoValor) {
        this._valor = Math.max(0, nuevoValor);
    }

    public void incremento() {
        this._valor++;
    }

    public void decremento() {
        if (this._valor > 0) {
            this._valor--;
        }
    }

    public static String filtrar(String id) {
        id = id.toUpperCase();

        if (id.length() == 1) {
            if (id.charAt(0) == 'Y') {
                return id;
            } else {
                return id + "1";
            }
        } else {
            return id;
        }
    }

    @Override
    public String toString() {
        return "(" + _id + ", " + _valor + ")";
    }

    public static void pintar() {
        System.out.println("Variables de entrada");
        System.out.println(_entrada);
        System.out.println();

        System.out.println("Variables locales");
        System.out.println(_locales);
        System.out.println();

        System.out.println("Variable de salida");
        System.out.println(_salida);
        System.out.println();
    }

}