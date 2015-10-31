package org.alberto.interprete.util;

import java.util.ArrayList;
import java.util.HashMap;

public class Variable extends Componente {

    public enum Tipo {

        ENTRADA, LOCAL, SALIDA
    }

    private int _valor;
    private Tipo _tipo;
    private int _indice;
    private boolean _creadaEnExpansion = false;

    public Variable(String id) {
        this(id, 0, false);
    }

    public Variable(String id, int valor) {
        this(id, valor, false);
    }

    public Variable(String id, boolean creadaEnExpansion) {
        this(id, 0, creadaEnExpansion);
    }

    public Variable(String id, int valor, boolean creadaEnExpansion) {
        super(Variable.normalizarID(id));

        _valor = valor;
        _creadaEnExpansion = creadaEnExpansion;

        if (_id.length() >= 1) {
            switch (_id.charAt(0)) {
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
                // Error.
            }

            _indice = Variable.obtenerIndice(_id);
        } else {
            // Error.
        }
    }

    public String id() {
        return _id;
    }

    public Tipo tipo() {
        return _tipo;
    }

    public int indice() {
        return _indice;
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
    
    public void creadaEnExpansion(boolean b) {
        _creadaEnExpansion = b;
    }

    public boolean creadaEnExpansion() {
        return _creadaEnExpansion;
    }

    @Override
    public String toString() {
        return "(" + _id + ", " + _valor + ")";
    }
    
    /* Métodos estáticos
     */
    public static String normalizarID(String id) {
        id = id.toUpperCase();
        int len = id.length();

        if (len > 0) {
            if (len == 1) {
                if (id.charAt(0) == 'Y') {
                    return id;
                } else {
                    return id + "1";
                }
            }
        } else {
            Error.deNombreDeVariableVacio();
        }

        return id;
    }

    public static int obtenerIndice(String id) {
        int len = id.length();

        if (len > 0) {
            if (len > 1) {
                try {
                    return Integer.parseInt(id.substring(1));
                } catch (NumberFormatException ex) {
                    Error.alObtenerIndiceDeVariable(id);
                }
            } else {
                return 1;
            }
        } else {
            // Error.
        }

        return 0;
    }

    /**
     * *************************************************************************
     * PURGA DE CARA A REFACTOR
     */
    private static HashMap<Integer, Variable> _entrada = new HashMap<>();
    private static HashMap<Integer, Variable> _locales = new HashMap<>();
    private static Variable _salida = new Variable("Y");

    private static int _mayorEntrada = 0;
    private static int _mayorLocal = 0;

    public static Variable set(String id) {
        return set(id, 0);
    }

    public static Variable set(String id, int valor) {
        id = normalizarID(id);
        char tipo = id.charAt(0);
        int indice = 1;
        Variable v = new Variable(id, valor);

        if (tipo != 'Y') {
            indice = obtenerIndice(id);
        }

        switch (tipo) {
            case 'X':
                if (_entrada.containsKey(indice)) {
                    v._creadaEnExpansion = _entrada.get(indice)._creadaEnExpansion;
                }

                _entrada.put(indice, v);
                if (indice > _mayorEntrada) {
                    _mayorEntrada = indice;
                }
                break;

            case 'Z':
                if (_locales.containsKey(indice)) {
                    v._creadaEnExpansion = _locales.get(indice)._creadaEnExpansion;
                }

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

    // Sólo utilizado por Macro.expandir
    public static Variable get(Tipo tipo) {
        Variable v = null;

        switch (tipo) {
            case ENTRADA:
                _mayorEntrada++;
                v = new Variable("X" + _mayorEntrada);
                v._creadaEnExpansion = true;
                _entrada.put(_mayorEntrada, v);
                break;

            case LOCAL:
                _mayorLocal++;
                v = new Variable("Z" + _mayorLocal);
                v._creadaEnExpansion = true;
                _locales.put(_mayorLocal, v);
                break;

            case SALIDA:
                v = _salida;
        }

        return v;
    }

    public static Variable get(String id) {
        id = normalizarID(id);
        char tipo = id.charAt(0);
        Variable v = null;
        int indice = 1;

        if (tipo != 'Y') {
            indice = obtenerIndice(id);
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
        ArrayList<Variable> variables = new ArrayList<>(_entrada.values());
        variables.sort(new ComparadorVariables());
        return variables;
    }

    public static ArrayList<Variable> variablesLocales() {
        ArrayList<Variable> variables = new ArrayList<>();

        _locales.values().stream()
                .filter(v -> !v._creadaEnExpansion)
                .forEach(v -> variables.add(v));

        variables.sort(new ComparadorVariables());
        return variables;
    }

    // Devuelve todas
    public static ArrayList<Variable> variablesLocalesExp() {
        ArrayList<Variable> variables = new ArrayList<>(_locales.values());
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
