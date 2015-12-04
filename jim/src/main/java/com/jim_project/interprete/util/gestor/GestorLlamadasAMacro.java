package com.jim_project.interprete.util.gestor;

import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Variable;
import java.util.ArrayList;

public class GestorLlamadasAMacro extends GestorComponentes {

    private ArrayList<LlamadaAMacro> _llamadasAMacros;
    private LlamadaAMacro _ultimaLlamadaAMacro;

    public GestorLlamadasAMacro(Ambito ambito) {
        super(ambito);

        _llamadasAMacros = new ArrayList<>();
        _ultimaLlamadaAMacro = null;
    }

    public void definirLlamadaAMacro(Variable ultimaVariableAsignada, Object idMacro) {
        int linea = _ambito.controladorEjecucion().numeroLineaActual();

        _ultimaLlamadaAMacro
                = new LlamadaAMacro(linea,
                        ultimaVariableAsignada.id(),
                        idMacro.toString());
        _llamadasAMacros.add(_ultimaLlamadaAMacro);
    }

    public void definirVariableEntradaMacro(Object parametro) {
        _ultimaLlamadaAMacro.variablesEntrada().add(parametro.toString());
    }

    public LlamadaAMacro obtenerLlamadaAMacro(String idMacro) {
        for (LlamadaAMacro llamada : _llamadasAMacros) {
            if (llamada.idMacro().equalsIgnoreCase(idMacro)
                    && llamada.linea()
                    == _programa.gestorAmbitos().ambitoActual()
                    .controladorEjecucion().numeroLineaActual()) {

                return llamada;
            }
        }
        
        return null;
    }

    @Override
    public void limpiar() {
        _llamadasAMacros.clear();
    }

    @Override
    public int count() {
        return _llamadasAMacros.size();
    }

    @Override
    public boolean vacio() {
        return _llamadasAMacros.isEmpty();
    }

}
