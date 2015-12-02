package com.jim_project.interprete.componente;

import java.util.ArrayList;
import com.jim_project.interprete.util.gestor.GestorMacros;

public class Macro extends Componente {

    private String _definidaEn;
    private String _cuerpo;

    // Componentes usados en la expansión de macros ilustrativa
    private ArrayList<String> _variablesEntrada = new ArrayList<>();
    private ArrayList<String> _variablesLocales = new ArrayList<>();

    private ArrayList<String> _etiquetas = new ArrayList<>();
    private ArrayList<String> _etiquetasGoTo = new ArrayList<>();

    private ArrayList<String> _llamadasAMacros = new ArrayList<>();

    public Macro(String id, GestorMacros gestorMacros) {
        super(id, gestorMacros);

        _definidaEn = _gestor.programa().ficheroEnProceso();

        if (_definidaEn.equals("jim.tmp")) {
            _definidaEn = "Editor";
        }
    }

    public String definidaEn() {
        return _definidaEn;
    }

    public String cuerpo() {
        return _cuerpo;
    }

    public void cuerpo(String cuerpo) {
        _cuerpo = cuerpo;
    }

    public ArrayList<String> variablesEntrada() {
        return _variablesEntrada;
    }

    public ArrayList<String> variablesLocales() {
        return _variablesLocales;
    }

    public ArrayList<String> etiquetas() {
        return _etiquetas;
    }

    public ArrayList<String> etiquetasSalto() {
        return _etiquetasGoTo;
    }

    public ArrayList<String> llamadasAMacros() {
        return _llamadasAMacros;
    }

    public void nuevaVariable(String id) {
        Variable v = new Variable(id, null);
        id = v.id();

        switch (v.tipo()) {
            case ENTRADA:
                if (!_variablesEntrada.contains(id)) {
                    _variablesEntrada.add(id);
                }
                break;

            case LOCAL:
                if (!_variablesLocales.contains(id)) {
                    _variablesLocales.add(id);
                }
                break;

            case SALIDA:
                break;
        }
    }

    public void nuevaEtiqueta(String id) {
        if (!_etiquetas.contains(id)) {
            _etiquetas.add(id);
        }
    }

    public void nuevaEtiquetaGoTo(String id) {
        if (!_etiquetasGoTo.contains(id)) {
            _etiquetasGoTo.add(id);
        }
    }

    public void nuevaLlamadaAMacro(String id) {
        _llamadasAMacros.add(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(_id).append("\n");
        sb.append("Cuerpo: ").append(_cuerpo).append("\n");

        sb.append("Variables de entrada: ");
        for (int i = 0; i < _variablesEntrada.size(); ++i) {
            sb.append(_variablesEntrada.get(i)).append(" ");
        }
        sb.append("\n");

        sb.append("Variables locales: ");
        for (int i = 0; i < _variablesLocales.size(); ++i) {
            sb.append(_variablesLocales.get(i)).append(" ");
        }
        sb.append("\n");

        sb.append("Etiquetas: ");
        for (int i = 0; i < _etiquetas.size(); ++i) {
            sb.append(_etiquetas.get(i)).append(" ");
        }
        sb.append("\n");

        sb.append("Llamadas a macros: ");
        for (int i = 0; i < _llamadasAMacros.size(); ++i) {
            sb.append(_llamadasAMacros.get(i)).append(" ");
        }
        sb.append("\n");

        return sb.toString();
    }
}
