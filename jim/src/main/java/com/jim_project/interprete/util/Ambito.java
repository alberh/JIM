package com.jim_project.interprete.util;

import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.componente.Macro;
import java.util.ArrayList;
import com.jim_project.interprete.Interprete;
import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.util.gestor.GestorEtiquetas;
import com.jim_project.interprete.util.gestor.GestorMacros;
import com.jim_project.interprete.util.gestor.GestorBucles;
import com.jim_project.interprete.util.gestor.GestorVariables;

public class Ambito {

    private ControladorEjecucion _controladorEjecucion;

    private GestorVariables _gestorVariables;
    private GestorBucles _gestorBucles;
    private GestorEtiquetas _gestorEtiquetas;
    private GestorMacros _gestorMacros;

    private Interprete _programa;

    private int[] _parametrosEntrada;
    private Macro _macroAsociada;

    public Ambito(Interprete programa) {
        this(programa, null, null);
    }

    public Ambito(Interprete programa, int[] parametrosEntrada) {
        this(programa, parametrosEntrada, null);
    }

    public Ambito(Interprete programa, Macro macroAsociada) {
        this(programa, null, macroAsociada);
    }

    public Ambito(Interprete programa,
            int[] parametrosEntrada,
            Macro macroAsociada) {

        _programa = programa;

        _controladorEjecucion = new ControladorEjecucion(this);

        _gestorVariables = new GestorVariables(_programa);
        _gestorBucles = new GestorBucles(_programa);
        _gestorEtiquetas = new GestorEtiquetas(_programa);
        _gestorMacros = new GestorMacros(_programa);

        _parametrosEntrada = parametrosEntrada;
        _macroAsociada = macroAsociada;
    }

    public ControladorEjecucion controladorEjecucion() {
        return _controladorEjecucion;
    }

    public GestorVariables variables() {
        return _gestorVariables;
    }

    public GestorBucles bucles() {
        return _gestorBucles;
    }

    public GestorEtiquetas etiquetas() {
        return _gestorEtiquetas;
    }

    public GestorMacros macros() {
        return _gestorMacros;
    }

    public Interprete programa() {
        return _programa;
    }

    public int[] parametrosEntrada() {
        return _parametrosEntrada;
    }

    public Macro macroAsociada() {
        return _macroAsociada;
    }

    public String estadoMemoria() {
        boolean comaAlFinal = false;
        StringBuilder sb = new StringBuilder();

        sb.append("(").append(_controladorEjecucion.numeroLineaActual())
                .append(", <");

        ArrayList<Variable> variables = Variable.variablesEntrada();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
            comaAlFinal = false;
        }

        variables = Variable.variablesLocales();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
        }
        concatenarVariable(Variable.variableSalida(), sb);

        sb.append(">)");

        return sb.toString();
    }

    private void concatenarVariables(ArrayList<Variable> variables, StringBuilder sb) {
        concatenarVariable(variables.get(0), sb);

        for (int i = 1; i < variables.size(); ++i) {
            sb.append(", ");
            concatenarVariable(variables.get(i), sb);
        }
    }

    private void concatenarVariable(Variable variable, StringBuilder sb) {
        sb.append(variable.id()).append(" = ").append(variable.valor());
    }

    public void imprimirComponentes() {
        System.out.println(_gestorVariables);

        if (_programa.modelo().tipo() == Modelo.Tipo.L) {
            System.out.println(_gestorEtiquetas);
        } else {
            System.out.println(_gestorBucles);
        }

        System.out.println(_gestorMacros);
    }

    public void limpiar() {
        _gestorVariables.limpiar();
        _gestorBucles.limpiar();
        _gestorEtiquetas.limpiar();
        _gestorMacros.limpiar();
    }
}
