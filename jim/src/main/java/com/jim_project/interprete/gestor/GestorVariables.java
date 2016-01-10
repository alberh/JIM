package com.jim_project.interprete.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Variable;
import java.util.Comparator;

class ComparadorVariables implements Comparator<Variable> {

    @Override
    public int compare(Variable v1, Variable v2) {
        return v1.id().compareToIgnoreCase(v2.id());
    }
}

/**
 * Clase encargada de gestionar las variables de un ámbito.
 *
 * @author Alberto García González
 */
public class GestorVariables extends GestorComponentes {

    private final HashMap<Integer, Variable> _variablesEntrada;
    private final HashMap<Integer, Variable> _variablesLocales;
    private final Variable _variableSalida;

    private int _mayorIndiceEntrada;
    private int _mayorIndiceLocal;

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito que contiene este gestor.
     */
    public GestorVariables(Ambito ambito) {
        super(ambito);

        _variablesEntrada = new HashMap<>();
        _variablesLocales = new HashMap<>();
        _variableSalida = new Variable("Y", this);

        _mayorIndiceEntrada = 0;
        _mayorIndiceLocal = 0;
    }

    /**
     * Crea una nueva variable con valor 0 y la añade al gestor.
     *
     * @param id El identificador de la variable.
     * @return Una referencia a la variable recién creada.
     */
    public Variable nuevaVariable(String id) {
        return nuevaVariable(id, 0);
    }

    /**
     * Crea una nueva variable con el valor indicado y la añade al gestor.
     *
     * @param id El identificador de la variable.
     * @param valor El valor de la variable.
     * @return Una referencia a la variable recién creada.
     */
    public Variable nuevaVariable(String id, int valor) {
        Variable v = new Variable(id, valor, this);
        int indice = v.indice();

        switch (v.tipo()) {
            case ENTRADA:
                _variablesEntrada.put(indice, v);

                if (indice > _mayorIndiceEntrada) {
                    _mayorIndiceEntrada = indice;
                }
                break;

            case LOCAL:
                _variablesLocales.put(indice, v);

                if (indice > _mayorIndiceLocal) {
                    _mayorIndiceLocal = indice;
                }
                break;

            case SALIDA:
                _variableSalida.valor(v.valor());
                break;
        }

        return v;
    }

    /**
     * Crea una nueva variable del tipo indicado, con valor 0 e índice = n+1,
     * siendo n el mayor índice de una variable del mismo tipo almacenada en
     * este gestor, y la añade al gestor.
     *
     * @param tipo El tipo de la variable.
     * @return Una referencia a la variable recién creada.
     */
    public Variable nuevaVariable(Variable.Tipo tipo) {
        return nuevaVariable(tipo, 0);
    }

    /**
     * Crea una nueva variable del tipo indicado y valor indicados e índice =
     * n+1, siendo n el mayor índice de una variable del mismo tipo almacenada
     * en este gestor, y la añade al gestor.
     *
     * @param tipo El tipo de la variable.
     * @param valor El valor de la variable.
     * @return Una referencia a la variable recién creada.
     */
    public Variable nuevaVariable(Variable.Tipo tipo, int valor) {
        Variable variable = null;

        switch (tipo) {
            case ENTRADA:
                _mayorIndiceEntrada++;

                variable = new Variable("X" + _mayorIndiceEntrada, valor, this);
                _variablesEntrada.put(_mayorIndiceEntrada, variable);
                break;

            case LOCAL:
                _mayorIndiceLocal++;

                variable = new Variable("Z" + _mayorIndiceLocal, valor, this);
                _variablesLocales.put(_mayorIndiceLocal, variable);
                break;

            case SALIDA:
                _variableSalida.valor(valor);
                variable = _variableSalida;
        }

        return variable;
    }

    /**
     * Busca una variable por su identificador.
     *
     * @param id El identificador de la variable.
     * @return Una referencia a la variable buscada, si se encuentra en el
     * gestor; {@code null} si la variable no ha sido encontrada.
     */
    public Variable obtenerVariable(String id) {
        Variable v = new Variable(id, this);

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

    /**
     * Devuelve la lista de variables de entrada almacenadas en el gestor.
     *
     * @return La lista de variables de entrada almacenadas en el gestor.
     */
    public ArrayList<Variable> variablesEntrada() {
        ArrayList<Variable> variables = new ArrayList<>(_variablesEntrada.values());
        variables.sort(new ComparadorVariables());
        return variables;
    }

    /**
     * Devuelve la lista de variables locales almacenadas en el gestor.
     *
     * @return La lista de variables locales almacenadas en el gestor.
     */
    public ArrayList<Variable> variablesLocales() {
        ArrayList<Variable> variables = new ArrayList<>();

        _variablesLocales.values().stream()
                .forEach(v -> variables.add(v));

        variables.sort(new ComparadorVariables());
        return variables;
    }

    /**
     * Devuelve la variable de salida del gestor.
     *
     * @return La variable de salida del gestor.
     */
    public Variable variableSalida() {
        return _variableSalida;
    }

    /**
     * Elimina todas las variables almacenadas en el gestor.
     */
    @Override
    public void limpiar() {
        _variablesEntrada.clear();
        _variablesLocales.clear();
        _variableSalida.valor(0);

        _mayorIndiceEntrada = 0;
        _mayorIndiceLocal = 0;
    }

    /**
     * Devuelve el número de variables almacenadas en el gestor.
     *
     * @return El número de variables almacenadas en el gestor.
     */
    @Override
    public int count() {
        return _variablesEntrada.size() + _variablesLocales.size() + 1;
    }

    /**
     * Comprueba si el gestor está vacío.
     *
     * @return {@code true}, si el gestor está vacío; {@code false}, si contiene
     * alguna variable.
     */
    @Override
    public boolean vacio() {
        return false;
    }
}
