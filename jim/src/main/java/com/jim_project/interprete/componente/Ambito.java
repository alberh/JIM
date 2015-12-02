package com.jim_project.interprete.componente;

import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.util.ControladorEjecucion;
import com.jim_project.interprete.util.gestor.GestorAmbitos;
import com.jim_project.interprete.util.gestor.GestorEtiquetas;
import com.jim_project.interprete.util.gestor.GestorBucles;
import com.jim_project.interprete.util.gestor.GestorLlamadasAMacro;
import com.jim_project.interprete.util.gestor.GestorVariables;
import java.util.Arrays;

public class Ambito extends Componente {

    private Programa _programa;
    private ControladorEjecucion _controladorEjecucion;

    private GestorVariables _gestorVariables;
    private GestorBucles _gestorBucles;
    private GestorEtiquetas _gestorEtiquetas;
    private GestorLlamadasAMacro _gestorLlamadasAMacro;

    private int[] _parametrosEntrada;
    private Macro _macroAsociada;

    public Ambito(Programa programa,
            int[] parametrosEntrada,
            Macro macroAsociada,
            GestorAmbitos gestorAmbitos) {

        this(programa, parametrosEntrada, gestorAmbitos);

        ArrayList<String> lineas = new ArrayList<>(
                Arrays.asList(macroAsociada.cuerpo().split("[\n\r]+"))
        );

        _controladorEjecucion = new ControladorEjecucion(this, lineas);
        _macroAsociada = macroAsociada;
    }

    public Ambito(Programa programa,
            int[] parametrosEntrada,
            ArrayList<String> lineas,
            GestorAmbitos gestorAmbitos) {

        this(programa, parametrosEntrada, gestorAmbitos);

        _controladorEjecucion = new ControladorEjecucion(this, lineas);
        _macroAsociada = null;
    }

    private Ambito(Programa programa,
            int[] parametrosEntrada,
            GestorAmbitos gestorAmbitos) {

        super(programa.ficheroEnProceso(), gestorAmbitos);

        _programa = programa;
        _parametrosEntrada = parametrosEntrada;

        _gestorVariables = new GestorVariables(this);
        _gestorBucles = new GestorBucles(this);
        _gestorEtiquetas = new GestorEtiquetas(this);
        _gestorLlamadasAMacro = new GestorLlamadasAMacro(this);
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

    public GestorLlamadasAMacro llamadasAMacro() {
        return _gestorLlamadasAMacro;
    }

    public Programa programa() {
        return _programa;
    }

    public int[] parametrosEntrada() {
        return _parametrosEntrada;
    }

    public Macro macroAsociada() {
        return _macroAsociada;
    }

    public boolean tieneMacroAsociada() {
        return _macroAsociada != null;
    }

    public void iniciar(int[] parametros) {
        _controladorEjecucion.iniciar(parametros);
    }

    public String estadoMemoria() {
        boolean comaAlFinal = false;
        StringBuilder sb = new StringBuilder();

        sb.append("(").append(_controladorEjecucion.numeroLineaActual())
                .append(", <");

        ArrayList<Variable> variables = _gestorVariables.variablesEntrada();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
            comaAlFinal = false;
        }

        variables = _gestorVariables.variablesLocales();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
        }
        concatenarVariable(_gestorVariables.variableSalida(), sb);

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

    public int resultado() {
        return _gestorVariables.variableSalida().valor();
    }

    public void iniciarExpansionMacros() {
        _controladorEjecucion.iniciarExpansionMacros();
    }

    public void imprimirComponentes() {
        System.out.println(_gestorVariables);

        if (_programa.modelo().tipo() == Modelo.Tipo.L) {
            System.out.println(_gestorEtiquetas);
        } else {
            System.out.println(_gestorBucles);
        }
    }

    public void limpiar() {
        _gestorVariables.limpiar();
        _gestorBucles.limpiar();
        _gestorEtiquetas.limpiar();
        _gestorLlamadasAMacro.limpiar();
    }
    
    /**
     * Método expansión de macros.
     * Decidir si dejar aquí o mover a nueva clase.
     */
    private int incrementoLineas;

    private int getIncrementoLineas() {
        return incrementoLineas;
    }

    private void addIncremento(int n) {
        incrementoLineas += n;
    }

    private void setIncremento(int n) {
        incrementoLineas = n;
    }

    public boolean expandir() {
        /*
        if (_programa.estadoOk()) {
            setIncremento(0);

            for (LlamadaAMacro contenedorExpansion : _expansiones) {
                String resultadoExpansion = Macro.expandir(contenedorExpansion);

                if (resultadoExpansion != null) {

                    ArrayList<String> lineasExpansion = new ArrayList<>(
                            Arrays.asList(resultadoExpansion.split("[\n\r]+"))
                    );

                    _programa.insertarExpansion(
                            contenedorExpansion.linea() + getIncrementoLineas(),
                            lineasExpansion
                    );

                    addIncremento(lineasExpansion.size() - 1);
                } else {
                    return false;
                }
            }

            _expansiones.clear();
        }
        */
        return true;
    }
}
