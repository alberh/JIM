package com.jim_project.interprete.util.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Macro;
import com.jim_project.interprete.componente.Variable;

public class GestorMacros extends GestorComponentes {

    private HashMap<String, Macro> _macros;

    public GestorMacros(Programa programa) {
        super(programa);
        _macros = new HashMap<>();
    }

    public Macro nuevaMacro(String id) {
        Macro macro = new Macro(id, this);
        _macros.put(macro.id(), macro);

        return macro;
    }

    public Macro obtenerMacro(String id) {
        return _macros.get(id.toUpperCase());
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
    private boolean hayRecursividadEnMacros(Macro macro) {
        return hayRecursividadEnMacros(macro, new ArrayList<>());
    }

    private boolean hayRecursividadEnMacros(Macro macro, ArrayList<String> marcas) {
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
            Macro m = obtenerMacro(llamadas.get(i));
            hayRecursividad = hayRecursividad || hayRecursividadEnMacros(m, marcas);
        }
        // si hay recursividad, no borrar
        marcas.remove(macro.id());

        return hayRecursividad;
    }

    public String expandir(LlamadaAMacro llamadaAMacro) {
        String separador = System.getProperty("line.separator");
        String idMacro = llamadaAMacro.idMacro();
        String ficheroMacro;
        ArrayList<String> parametrosEntrada = llamadaAMacro.parametros();
        int numeroLinea = llamadaAMacro.linea();

        String idVariableSalida = llamadaAMacro.idVariableSalida().toUpperCase();
        String asignaciones = idVariableSalida + " <- 0" + separador;

        Macro macro = obtenerMacro(idMacro);

        if (macro == null) {
            _programa.error().deMacroNoDefinida(numeroLinea, idMacro);
            return null;
        }
        ficheroMacro = macro.definidaEn();

        // Añadir comprobación del número de parámetros (nP)
        //  - Permitir llamadas con 0 a Nv parámetros, siendo Nv
        //    el número de variables de entrada que se utilizan
        //    en la macro. Si nP > Nv, mostrar error.
        int nP = parametrosEntrada.size();
        int nV = macro.variablesEntrada().size();

        if (nP != nV) {
            _programa.error().enNumeroParametros(numeroLinea, idMacro, nV, nP);
            return null;
        }

        // Comprobamos que no hay llamadas recursivas directas ni indirectas
        // en la macro a expandir
        /*
         if (hayRecursividadEnMacros(macro)) {
         _programa.error().deRecursividadEnMacros(numeroLinea, idMacro);
         return null;
         }
         */
        String expansion = new String(macro.cuerpo());

        ArrayList<String> variablesEntrada = macro.variablesEntrada();
        variablesEntrada.sort(null);

        ArrayList<String> variablesLocales = macro.variablesLocales();
        Ambito ambitoRaiz = _programa.gestorAmbitos().ambitoRaiz();

        ArrayList<String> etiquetas = macro.etiquetas();
        ArrayList<String> etiquetasSalto = macro.etiquetasSalto();

        HashMap<String, String> reemplazosEntrada = new HashMap<>();
        HashMap<String, String> reemplazosLocales = new HashMap<>();
        HashMap<String, String> reemplazosEtiquetas = null;
        String variableSalidaLocal = null;

        Variable vAuxiliar;
        for (int i = 0; i < variablesEntrada.size(); ++i) {
            String vAntigua = variablesEntrada.get(i).toUpperCase();
            vAuxiliar = ambitoRaiz.variables().nuevaVariable(Variable.Tipo.LOCAL);
            String vNueva = vAuxiliar.tokenTipo() + "_" + vAuxiliar.indice();
            
            reemplazosEntrada.put(vAntigua, vNueva);
        }

        for (int i = 0; i < variablesLocales.size(); ++i) {
            String vAntigua = variablesLocales.get(i);
            vAuxiliar = ambitoRaiz.variables().nuevaVariable(Variable.Tipo.LOCAL);
            String vNueva = vAuxiliar.tokenTipo() + "_" + vAuxiliar.indice();
            
            reemplazosLocales.put(vAntigua, vNueva);
        }

        if (programa().modelo().tipo() == Modelo.Tipo.L) {
            /* Se tienen en cuenta sólo las etiquetas que indican un objetivo de salto
             */
            reemplazosEtiquetas = new HashMap<>();
            for (String etiqueta : etiquetas) {
                Etiqueta eAuxiliar = ambitoRaiz.etiquetas().nuevaEtiqueta();
                String nuevaEtiqueta = eAuxiliar.grupo() + "_" + eAuxiliar.indice();
                reemplazosEtiquetas.put(etiqueta, nuevaEtiqueta);
            }
        }

        vAuxiliar = ambitoRaiz.variables().nuevaVariable(Variable.Tipo.LOCAL);
        variableSalidaLocal = "_" + vAuxiliar.tokenTipo() + "_" + vAuxiliar.indice() + "_";

        // Insertar y reemplazar
        String vAntigua;
        String vNueva;
        for (int i = 0; i < variablesEntrada.size(); ++i) {
            vAntigua = variablesEntrada.get(i);
            vNueva = reemplazosEntrada.get(vAntigua);
            
            asignaciones += vNueva + " <- "
                    + parametrosEntrada.get(i).toUpperCase() + separador;
        }

        expansion = expansion.replace("Y", variableSalidaLocal);

        for (int i = variablesLocales.size() - 1; i >= 0; --i) {
            vAntigua = variablesLocales.get(i);
            vNueva = reemplazosLocales.get(vAntigua);

            expansion = expansion.replace(vAntigua, vNueva);
        }

        for (int i = variablesEntrada.size() - 1; i >= 0; --i) {
            vAntigua = variablesEntrada.get(i);
            vNueva = reemplazosEntrada.get(vAntigua);

            expansion = expansion.replace(vAntigua, vNueva);
        }

        expansion = "# Expansión de " + idMacro + " (" + ficheroMacro + ")"
                + separador + asignaciones
                + expansion;

        if (reemplazosEtiquetas != null) {
            /* Reempaza las etiquetas que indican un objetivo de salto
             */
            for (String eAntigua : etiquetas) {
                String eNueva = reemplazosEtiquetas.get(eAntigua);
                expansion = expansion.replace(eAntigua, eNueva);
            }

            /* Reemplaza todas las etiquetas que son objetivo de un salto
             */
            Etiqueta eAuxiliar = ambitoRaiz.etiquetas().nuevaEtiqueta();
            String etiquetaSalida = eAuxiliar.grupo() + "_" + eAuxiliar.indice();
            for (String eAntigua : etiquetasSalto) {
                if (reemplazosEtiquetas.containsKey(eAntigua)) {
                    String eNueva = reemplazosEtiquetas.get(eAntigua);
                    expansion = expansion.replace(eAntigua, eNueva);
                } else {
                    expansion = expansion.replace(eAntigua, etiquetaSalida);
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

        expansion = expansion.replace("_", "");
        return expansion + "\n# Fin expansión de " + idMacro + separador;
    }

    @Override
    public int count() {
        return _macros.size();
    }

    @Override
    public boolean vacio() {
        return _macros.isEmpty();
    }
}
