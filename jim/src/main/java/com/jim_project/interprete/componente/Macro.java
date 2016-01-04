package com.jim_project.interprete.componente;

import java.util.ArrayList;
import com.jim_project.interprete.util.gestor.GestorMacros;

/**
 * Clase que abstrae el concepto de macro. Mantiene información sobre el fichero
 * en el que ha sido definida, su código y varias listas que almacenan los
 * identificadores de los componentes encontrados en la macro, para facilitar
 * su expansión en caso de ser necesario.
 * @author Alberto García González
 */
public class Macro extends Componente {

    private String _definidaEn;
    private String _cuerpo;

    // Componentes usados en la expansión de macros
    private final ArrayList<String> _variablesEntrada;
    private final ArrayList<String> _variablesLocales;
    private final ArrayList<String> _etiquetas;
    private final ArrayList<String> _etiquetasGoTo;
    private final ArrayList<String> _llamadasAMacros;

    /**
     * Constructor de clase.
     * @param id El identificador de la macro.
     * @param gestorMacros Una referencia al gestor de macros que la contiene.
     */
    public Macro(String id, GestorMacros gestorMacros) {
        super(id.toUpperCase(), gestorMacros);

        _definidaEn = _gestor.programa().ficheroEnProceso();

        if (_definidaEn.equals("jim.tmp")) {
            _definidaEn = "Editor";
        }

        _variablesEntrada = new ArrayList<>();
        _variablesLocales = new ArrayList<>();
        _etiquetas = new ArrayList<>();
        _etiquetasGoTo = new ArrayList<>();
        _llamadasAMacros = new ArrayList<>();
    }

    /**
     * Devuelve la ruta al fichero en el que ha sido definida.
     * @return La ruta al fichero en el que ha sido definida.
     */
    public String definidaEn() {
        return _definidaEn;
    }

    /**
     * Devuelve el cuerpo o código de la macro.
     * @return El cuerpo de la macro.
     */
    public String cuerpo() {
        return _cuerpo;
    }

    /**
     * Define el cuerpo o código de la macro.
     * @param cuerpo El nuevo código de la macro.
     */
    public void cuerpo(String cuerpo) {
        _cuerpo = cuerpo.toUpperCase();
    }

    /**
     * Devuelve la lista de identificadores de las variables de entrada utilizadas en la macro.
     * @return La lista de identificadores de las variables de entrada utilizadas en la macro.
     */
    public ArrayList<String> variablesEntrada() {
        return _variablesEntrada;
    }

    /**
     * Devuelve la lista de identificadores de las variables locales utilizadas en la macro.
     * @return La lista de identificadores de las variables locales utilizadas en la macro.
     */
    public ArrayList<String> variablesLocales() {
        return _variablesLocales;
    }

    /**
     * Devuelve la lista de identificadores de las etiquetas que indican una posición
     * a la que se puede saltar en el cuerpo de la macro.
     * @return La lista de identificadores de las etiquetas que indican una posición
     * a la que se puede saltar.
     */
    public ArrayList<String> etiquetas() {
        return _etiquetas;
    }

    /**
     * Devuelve la lista de identificadores de las etiquetas especificados en las
     * distintas instrucciones de salto.
     * @return La lista de identificadores de las etiquetas especificados en las
     * distintas instrucciones de salto.
     */
    public ArrayList<String> etiquetasSalto() {
        return _etiquetasGoTo;
    }

    /**
     * Devuelve la lista de identificadores de las distintas llamadas a
     * macro del cuerpo de la macro.
     * @return La lista de identificadores de las distintas llamadas a
     * macro del cuerpo de la macro.
     */
    public ArrayList<String> llamadasAMacros() {
        return _llamadasAMacros;
    }

    /**
     * Registra una nueva variable en la macro.
     * @param id El identificador de la macro.
     */
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

    /**
     * Registra una nueva etiqueta indicadora de una posición de salto en la macro.
     * @param id El identificador de la etiqueta.
     */
    public void nuevaEtiqueta(String id) {
        Etiqueta e = new Etiqueta(id, 0, null);
        if (!_etiquetas.contains(e.id())) {
            _etiquetas.add(e.id());
        }
    }

    /**
     * Registra una nueva etiqueta especificada en una instrucción de salto en la macro.
     * @param id El identificador de la etiqueta.
     */
    public void nuevaEtiquetaGoTo(String id) {
        Etiqueta e = new Etiqueta(id, 0, null);
        if (!_etiquetasGoTo.contains(e.id())) {
            _etiquetasGoTo.add(e.id());
        }
    }

    /**
     * Registra una nueva llamada a macro en la macro.
     * @param id El identificador de la llamada a macro.
     */
    public void nuevaLlamadaAMacro(String id) {
        LlamadaAMacro llm = new LlamadaAMacro(0, "", id);
        _llamadasAMacros.add(llm.id());
    }

    /**
     * Devuelve una cadena que representa la macro.
     * Ésta contiene su identificador, su cuerpo y todos sus componentes.
     * @return Una cadena que representa la macro y sus componentes.
     */
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
