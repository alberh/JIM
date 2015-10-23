package org.alberto.interprete;

import java.util.HashMap;
import java.util.ArrayList;

public class Macro {

    private static HashMap<String, Macro> _macros = new HashMap<>();

    private String _id;
    private String _cuerpo;
    private ArrayList<String> _entrada = new ArrayList<>();
    private ArrayList<String> _locales = new ArrayList<>();

    private ArrayList<String> _etiquetas = new ArrayList<>();
    private ArrayList<String> _etiquetasSalto = new ArrayList<>();

    private ArrayList<String> _llamadasAMacros = new ArrayList<>();

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
            String nuevaVariable = Variable.get(Variable.EVariable.LOCAL).id();

            expansion = expansion.replace(variable, nuevaVariable);

            if (i < parametrosEntrada.size()) {
                asignaciones += nuevaVariable + " <- "
                        + parametrosEntrada.get(i).toUpperCase() + separador;
            }
        }

        for (String variable : vLocales) {
            String nuevaVariable = Variable.get(Variable.EVariable.LOCAL).id();
            expansion = expansion.replace(variable, nuevaVariable);
        }

        /* Se obtiene una nueva variable local y se reemplaza todas las referencias a la variable de salida Y
         * por esta nueva variable
         */
        String variableSalidaLocal = Variable.get(Variable.EVariable.LOCAL).id();
        expansion = "# Expansión de " + idMacro + separador
                + asignaciones + expansion.replace("VY", variableSalidaLocal);

        if (Programa.modelo() == Programa.Modelos.L) {
            ArrayList<String> etiquetas = macro.etiquetas();
            ArrayList<String> etiquetasSalto = macro.etiquetasSalto();

            /* Reempaza las etiquetas que marcan un objetivo de salto
             */
            HashMap<String, String> etiquetasReemplazadas = new HashMap<>();
            for (String etiqueta : etiquetas) {
                // registrar el número de línea de la etiqueta desplazado según el número de línea
                // de la llamada a la macro + el número de asignaciones añadidas al código expandido
                String nuevaEtiqueta = Etiqueta.get().id();
                etiquetasReemplazadas.put(etiqueta, nuevaEtiqueta);

                expansion = expansion.replace(etiqueta, nuevaEtiqueta);
            }

            /* Reemplaza todas las etiquetas que son objetivo de un salto
             */
            String etiquetaSalida = Etiqueta.get().id();
            for (String etiqueta : etiquetasSalto) {
                if (etiquetasReemplazadas.containsKey(etiqueta)) {
                    expansion = expansion.replace(etiqueta, etiquetasReemplazadas.get(etiqueta));
                } else {
                    expansion = expansion.replace(etiqueta, etiquetaSalida);
                }
            }
            /* Añadimos una última línea con la etiqueta designada como etiqueta de salida de la macro
             * y la asignación a la variable de salida indicada por el usuario
             */
            expansion = expansion + separador + "[" + etiquetaSalida + "] "
                    + idVariableSalida + " <- " + variableSalidaLocal;
        } else {
            /* Añadimos una última línea con la asignación a la variable de salida indicada por el usuario
             */
            expansion = expansion + idVariableSalida + " <- " + variableSalidaLocal;
        }

        return expansion + "\n# Fin expansión de " + idMacro + separador;
    }

    public Macro(String id) {
        this._id = id;
    }

    public String id() {
        return _id;
    }

    public String cuerpo() {
        return _cuerpo;
    }

    public void cuerpo(String cuerpo) {
        this._cuerpo = cuerpo;
    }

    public ArrayList<String> variablesEntrada() {
        return _entrada;
    }

    public ArrayList<String> variablesLocales() {
        return _locales;
    }

    public ArrayList<String> etiquetas() {
        return _etiquetas;
    }

    public ArrayList<String> etiquetasSalto() {
        return _etiquetasSalto;
    }

    public ArrayList<String> llamadasAMacros() {
        return _llamadasAMacros;
    }

    public void nuevaVariable(String id) {
        char tipo = id.charAt(1);

        switch (tipo) {
            case 'X':
                if (!_entrada.contains(id)) {
                    _entrada.add(id);
                }
                break;

            case 'Z':
                if (!_locales.contains(id)) {
                    _locales.add(id);
                }
                break;

            case 'Y':
                break;

            default:
                System.err.println("Error: Tipo de variable '" + id
                        + "' desconocido.");
        }
    }

    public void nuevaEtiqueta(String id) {
        if (!_etiquetas.contains(id)) {
            _etiquetas.add(id);
        }
    }

    public void nuevaEtiquetaSalto(String id) {
        if (!_etiquetasSalto.contains(id)) {
            _etiquetasSalto.add(id);
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
        for (int i = 0; i < _entrada.size(); ++i) {
            sb.append(_entrada.get(i)).append(" ");
        }
        sb.append("\n");

        sb.append("Variables locales: ");
        for (int i = 0; i < _locales.size(); ++i) {
            sb.append(_locales.get(i)).append(" ");
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

    public static void pintar() {
        System.out.println("Macros");
        _macros.forEach(
                (k, v) -> System.out.println(v)
        );
        System.out.println();
    }

}
