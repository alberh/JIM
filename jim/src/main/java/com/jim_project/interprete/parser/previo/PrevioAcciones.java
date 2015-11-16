package com.jim_project.interprete.parser.previo;

import com.jim_project.interprete.util.ContenedorParametrosExpansion;
import java.util.ArrayList;

import com.jim_project.interprete.parser.Acciones;
import com.jim_project.interprete.componente.Ambito;

public class PrevioAcciones extends Acciones {

    private String _idUltimaVariable;
    private ArrayList<ContenedorParametrosExpansion> _expansiones = new ArrayList<>();
    private ContenedorParametrosExpansion _ultimaExpansion;

    public PrevioAcciones(Ambito ambito) {
        super(ambito);
    }

    public void definirVariable(Object idVariable) {
        _ambito.variables().nuevaVariable(idVariable.toString());
    }

    public void definirVariableYMantener(Object idVariable) {
        definirVariable(idVariable);
        _idUltimaVariable = idVariable.toString();
    }

    public int llamadasAMacros() {
        return _expansiones.size();
    }

    public void prepararParaExpandir(Object idMacro) {
        _ultimaExpansion = new ContenedorParametrosExpansion();
        _expansiones.add(_ultimaExpansion);

        _ultimaExpansion.linea = _ambito.controladorEjecucion().numeroLineaActual();
        _ultimaExpansion.idVariableSalida = _idUltimaVariable;
        _ultimaExpansion.idMacro = idMacro.toString();
        _ultimaExpansion.variablesEntrada = new ArrayList<>();
    }

    public void prepararVariableEntrada(Object parametro) {
        _ultimaExpansion.variablesEntrada.add(parametro.toString());
    }

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

         for (ContenedorParametrosExpansion contenedorExpansion : _expansiones) {
         String resultadoExpansion = Macro.expandir(contenedorExpansion);

         if (resultadoExpansion != null) {

         ArrayList<String> lineasExpansion = new ArrayList<>(
         Arrays.asList(resultadoExpansion.split("[\n\r]+"))
         );

         _programa.insertarExpansion(
         contenedorExpansion.linea + getIncrementoLineas(),
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

    public void limpiar() {
        _expansiones.clear();
    }
}
