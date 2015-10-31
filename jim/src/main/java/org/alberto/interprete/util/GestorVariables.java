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
        return GestorVariables.this.nuevaVariable(id, 0);
    }

    public Variable nuevaVariable(String id, int valor) {
        Variable v = new Variable(id, valor);
        int indice = v.indice();

        switch (v.tipo()) {
            case ENTRADA:
                if (_variablesEntrada.containsKey(indice)) {
                    boolean enExpansion = _variablesEntrada.get(indice)
                            .creadaEnExpansion();
                    v.creadaEnExpansion(enExpansion);
                }
                _variablesEntrada.put(indice, v);

                if (indice > _mayorIndiceEntrada) {
                    _mayorIndiceEntrada = indice;
                }
                break;

            case LOCAL:
                if (_variablesLocales.containsKey(indice)) {
                    boolean enExpansion = _variablesLocales.get(indice)
                            .creadaEnExpansion();
                    v.creadaEnExpansion(enExpansion);
                }
                _variablesLocales.put(indice, v);

                if (indice > _mayorIndiceLocal) {
                    _mayorIndiceLocal = indice;
                }
                break;

            case SALIDA:
                _variableSalida = v;
                break;
        }

        return v;
    }

    // Sólo utilizado por Macro.expandir
    public Variable nuevaVariable(Variable.Tipo tipo) {
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

    public Variable obtenerVariable(String id) {
        Variable v = new Variable(id);

        switch (v.tipo()) {
            case ENTRADA:
                v = _variablesEntrada.get(v.indice());
                break;

            case LOCAL:
                v = _variablesLocales.get(v.indice());
                break;

            case SALIDA:
                v = _variableSalida;
                break;
        }

        return v;
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
