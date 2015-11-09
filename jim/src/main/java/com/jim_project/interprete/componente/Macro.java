package com.jim_project.interprete.componente;

import java.util.HashMap;
import java.util.ArrayList;
import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.util.ContenedorParametrosExpansion;
import com.jim_project.interprete.util.Error;

public class Macro extends Componente {

    private String _definidaEn;
    
    private String _cuerpo;
    
    // Componentes usados en la expansión de macros ilustrativa
    private ArrayList<String> _variablesEntrada = new ArrayList<>();
    private ArrayList<String> _variablesLocales = new ArrayList<>();

    private ArrayList<String> _etiquetas = new ArrayList<>();
    private ArrayList<String> _etiquetasGoTo = new ArrayList<>();

    private ArrayList<String> _llamadasAMacros = new ArrayList<>();

    public Macro(String id) {
        super(id);

        // esto habrá que cambiarlo
        _definidaEn = Programa.ficheroEnProceso();

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
        Variable v = new Variable(id);
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

    /**
     * **************************************************************
     * Refactor
     */
    private static HashMap<String, Macro> _macros = new HashMap<>();

    public static void pintar() {
        System.out.println("Macros");
        _macros.forEach(
                (k, v) -> System.out.println(v)
        );
        System.out.println();
    }

    public static Macro set(String id) {
        Macro macro = new Macro(id);
        _macros.put(id, macro);

        return macro;
    }

    public static Macro get(String id) {
        return _macros.get(id);
    }

    public static void limpiar() {
        _macros.clear();
    }

    private static boolean hayRecursividadEnMacros(Macro macro) {
        return hayRecursividadEnMacros(macro, new ArrayList<>());
    }

    private static boolean hayRecursividadEnMacros(Macro macro, ArrayList<String> marcas) {
        String macroActual = macro.id();

        if (marcas.contains(macroActual)) {
            // se puede mejorar para que este método devuelva una lista
            // de las macros que contienen recursividad (sería la lista marcas)
            return true;
        }
        marcas.add(macroActual);

        boolean hayRecursividad = false;
        ArrayList<String> llamadas = macro.llamadasAMacros();

        for (int i = 0; i < llamadas.size() && !hayRecursividad; ++i) {
            Macro m = Macro.get(llamadas.get(i));
            hayRecursividad = hayRecursividad || hayRecursividadEnMacros(m, marcas);
        }
        // si hay recursividad, no borrar
        marcas.remove(macro.id());

        return hayRecursividad;
    }

    public static String expandir(ContenedorParametrosExpansion parametrosExpansion) {
        String idMacro = parametrosExpansion.idMacro;
        String idVariableSalida = parametrosExpansion.idVariableSalida;
        ArrayList<String> parametrosEntrada = parametrosExpansion.variablesEntrada;
        int numeroLinea = parametrosExpansion.linea;

        Macro macro = Macro.get(idMacro);

        if (macro == null) {
            Error.deMacroNoDefinida(numeroLinea, idMacro);
            return null;
        }

        // Añadir comprobación del número de parámetros (nP)
        //  - Permitir llamadas con 0 a Nv parámetros, siendo Nv
        //    el número de variables de entrada que se utilizan
        //    en la macro. Si nP > Nv, mostrar error.
        int nP = parametrosEntrada.size();
        int nV = macro.variablesEntrada().size();

        if (nP > nV) {
            Error.enNumeroParametros(numeroLinea, idMacro, nV, nP);
            return null;
        }

        // Comprobamos que no hay llamadas recursivas directas ni indirectas
        // en la macro a expandir
        if (hayRecursividadEnMacros(macro)) {
            Error.deRecursividadEnMacros(numeroLinea, idMacro);
            return null;
        }

        idVariableSalida = idVariableSalida.toUpperCase();

        String separador = System.getProperty("line.separator");
        String expansion = new String(macro.cuerpo());
        String asignaciones = idVariableSalida + " <- 0" + separador;

        ArrayList<String> vEntrada = macro.variablesEntrada();
        vEntrada.sort(null);

        ArrayList<String> vLocales = macro.variablesLocales();

        for (int i = 0; i < vEntrada.size(); ++i) {
            String variable = vEntrada.get(i);
            String nuevaVariable = Variable.get(Variable.Tipo.LOCAL).id();

            expansion = expansion.replace(variable, nuevaVariable);

            if (i < parametrosEntrada.size()) {
                asignaciones += nuevaVariable + " <- "
                        + parametrosEntrada.get(i).toUpperCase() + separador;
            }
        }

        for (String variable : vLocales) {
            String nuevaVariable = Variable.get(Variable.Tipo.LOCAL).id();
            expansion = expansion.replace(variable, nuevaVariable);
        }

        /* Se obtiene una nueva variable local y se reemplaza todas las
         * referencias a la variable de salida Y por esta nueva variable
         */
        String variableSalidaLocal = Variable.get(Variable.Tipo.LOCAL).id();
        expansion = "# Expansión de " + idMacro + separador
                + asignaciones + expansion.replace("VY", variableSalidaLocal);

        if (Programa.modelo().tipo() == Modelo.Tipo.L) {
            ArrayList<String> etiquetas = macro.etiquetas();
            ArrayList<String> etiquetasSalto = macro.etiquetasSalto();

            /* Reempaza las etiquetas que marcan un objetivo de salto
             */
            HashMap<String, String> etiquetasReemplazadas = new HashMap<>();
            for (String etiqueta : etiquetas) {
                // registrar el número de línea de la etiqueta desplazado según
                // el número de línea de la llamada a la macro + el número de
                // asignaciones añadidas al código expandido
                String nuevaEtiqueta = Etiqueta.get().id();
                etiquetasReemplazadas.put(etiqueta, nuevaEtiqueta);

                expansion = expansion.replace(etiqueta, nuevaEtiqueta);
            }

            /* Reemplaza todas las etiquetas que son objetivo de un salto
             */
            String etiquetaSalida = Etiqueta.get().id();
            for (String etiqueta : etiquetasSalto) {
                if (etiquetasReemplazadas.containsKey(etiqueta)) {
                    expansion = expansion.replace(etiqueta,
                            etiquetasReemplazadas.get(etiqueta));
                } else {
                    expansion = expansion.replace(etiqueta, etiquetaSalida);
                }
            }
            /* Añadimos una última línea con la etiqueta designada como etiqueta
             * de salida de la macro y la asignación a la variable de salida
             * indicada por el usuario
             */
            expansion = expansion + separador + "[" + etiquetaSalida + "] "
                    + idVariableSalida + " <- " + variableSalidaLocal;
        } else {
            /* Añadimos una última línea con la asignación a la variable de
             * salida indicada por el usuario
             */
            expansion = expansion + idVariableSalida + " <- " + variableSalidaLocal;
        }

        return expansion + "\n# Fin expansión de " + idMacro + separador;
    }
}