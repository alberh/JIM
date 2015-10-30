package org.alberto.interprete.util;

import java.util.ArrayList;
import java.util.HashMap;

public class GestorVariables {

    private HashMap<Integer, Variable> _variablesEntrada;
    private HashMap<Integer, Variable> _variablesLocales;
    private Variable _variableSalida;

    private int _mayorIndiceEntrada;
    private int _mayorIndiceLocal;

    public GestorVariables() {
        _variablesEntrada = new HashMap<>();
        _variablesLocales = new HashMap<>();
        _variableSalida = new Variable("Y");

        _mayorIndiceEntrada = 0;
        _mayorIndiceLocal = 0;
    }

    public Variable nuevaVariable(String id) {
        return nuevaVariable(id, 0);
    }

    // Re-hacer este método
    public Variable nuevaVariable(String id, int valor) {
        id = normalizarID(id);
        char tipo = id.charAt(0);
        int indice = 1;
        Variable v = null;

        if (tipo != 'Y') {
            indice = obtenerIndice(id);
        }

        switch (tipo) {
            case 'X': {
                if (_variablesEntrada.containsKey(indice)) {
                    boolean enExpansion = _variablesEntrada.get(indice).creadaEnExpansion();
                    v = new Variable(id, valor, enExpansion);
                } else {
                    v = new Variable(id, valor);
                }

                _variablesEntrada.put(indice, v);
                if (indice > _mayorIndiceEntrada) {
                    _mayorIndiceEntrada = indice;
                }
            }
            break;

            case 'Z': {
                if (_variablesLocales.containsKey(indice)) {
                    boolean enExpansion = _variablesLocales.get(indice).creadaEnExpansion();
                    v = new Variable(id, valor, enExpansion);
                } else {
                    v = new Variable(id, valor);
                }

                _variablesLocales.put(indice, v);
                if (indice > _mayorIndiceLocal) {
                    _mayorIndiceLocal = indice;
                }
            }
            break;

            case 'Y':
                if (_variableSalida != null) {
                    v = new Variable(id, valor, _variableSalida.creadaEnExpansion());
                } else {
                    v = new Variable(id, valor);
                }
                
                _variableSalida = v;
                
                break;
        }

        return v;
    }

    private int obtenerIndice(String id) {
        try {
            return Integer.parseInt(id.substring(1, id.length()));
        } catch (NumberFormatException ex) {
            Error.alObtenerIndiceDeVariable(id);
            return 0;
        }
    }

    // Sólo utilizado por Macro.expandir
    public Variable get(Variable.Tipo tipo) {
        Variable v = null;

        switch (tipo) {
            case ENTRADA:
                _mayorIndiceEntrada++;
                v = new Variable("X" + _mayorIndiceEntrada, true);
                _variablesEntrada.put(_mayorIndiceEntrada, v);
                break;

            case LOCAL:
                _mayorIndiceLocal++;
                v = new Variable("Z" + _mayorIndiceLocal, true);
                _variablesLocales.put(_mayorIndiceLocal, v);
                break;

            case SALIDA:
                v = _variableSalida;
        }

        return v;
    }

    public Variable get(String id) {
        id = normalizarID(id);
        char tipo = id.charAt(0);
        Variable v = null;
        int indice = 1;

        if (tipo != 'Y') {
            indice = obtenerIndice(id);
        }

        switch (tipo) {
            case 'X':
                v = _variablesEntrada.get(indice);
                break;

            case 'Z':
                v = _variablesLocales.get(indice);
                break;

            case 'Y':
                v = _variableSalida;
                break;
        }

        return v;
    }

    public static String normalizarID(String id) {
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

    public ArrayList<Variable> variablesEntrada() {
        ArrayList<Variable> variables = new ArrayList<>(_variablesEntrada.values());
        variables.sort(new ComparadorVariables());
        return variables;
    }

    public ArrayList<Variable> variablesLocales() {
        ArrayList<Variable> variables = new ArrayList<>();

        _variablesLocales.values().stream()
                .filter(v -> !v.creadaEnExpansion())
                .forEach(v -> variables.add(v));

        variables.sort(new ComparadorVariables());
        return variables;
    }

    // Devuelve todas
    /*
    public ArrayList<Variable> variablesLocalesExp() {
        ArrayList<Variable> variables = new ArrayList<>(_locales.values());
        variables.sort(new ComparadorVariables());
        return variables;
    }
    */

    public Variable variableSalida() {
        return _variableSalida;
    }

    public void limpiar() {
        _variablesEntrada.clear();
        _variablesLocales.clear();
        _variableSalida = new Variable("Y");

        _mayorIndiceEntrada = 0;
        _mayorIndiceLocal = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Variables de entrada").append("\n");
        sb.append(_variablesEntrada).append("\n");
        sb.append("\n");

        sb.append("Variables locales").append("\n");
        sb.append(_variablesLocales).append("\n");
        sb.append("\n");

        sb.append("Variable de salida").append("\n");
        sb.append(_variableSalida).append("\n");
        sb.append("\n");
        
        return sb.toString();
    }
}
