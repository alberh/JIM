package org.alberto.interprete.util.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import org.alberto.interprete.Modelo;
import org.alberto.interprete.Programa;
import org.alberto.interprete.Interprete;
import org.alberto.interprete.util.ContenedorParametrosExpansion;
import org.alberto.interprete.util.Error;
import org.alberto.interprete.util.Etiqueta;
import org.alberto.interprete.util.Macro;
import org.alberto.interprete.util.Variable;

public class GestorMacros extends GestorComponentes {

    private HashMap<String, Macro> _macros;

    public GestorMacros(Interprete programa) {
        super(programa);
        _macros = new HashMap<>();
    }

    public Macro nuevaMacro(String id) {
        Macro macro = new Macro(id);
        _macros.put(id, macro);

        return macro;
    }

    public Macro obtenerMacro(String id) {
        return _macros.get(id);
    }

    public void limpiar() {
        _macros.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        _macros.forEach(
                (k, v) -> {
                    sb.append(v);
                }
        );
        sb.append("\n");

        return sb.toString();
    }

    // Métodos estáticos
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
        //
        //
        // Usar salto de línea del sistema!
        //
        //
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

    @Override
    protected int count() {
        return _macros.size();
    }

    @Override
    protected boolean vacio() {
        return _macros.isEmpty();
    }
}
