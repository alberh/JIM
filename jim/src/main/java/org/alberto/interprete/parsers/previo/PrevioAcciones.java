package org.alberto.interprete.parsers.previo;

import org.alberto.interprete.Variable;
import org.alberto.interprete.Macro;
import org.alberto.interprete.Programa;
import org.alberto.interprete.ContenedorParametrosExpansion;
import java.util.Arrays;
import java.util.ArrayList;

import org.alberto.interprete.parsers.Acciones;

public class PrevioAcciones extends Acciones {

    private static String _idUltimaVariable;
    private static ArrayList<ContenedorParametrosExpansion> _expansiones = new ArrayList<>();
    private static ContenedorParametrosExpansion _ultimaExpansion;

    public static void definirVariable(Object idVariable) {
        Variable.set(idVariable.toString());
    }

    public static void definirVariableYMantener(Object idVariable) {
        Variable.set(idVariable.toString());
        _idUltimaVariable = idVariable.toString();
    }

    public static int llamadasAMacros() {
        return _expansiones.size();
    }

    public static void prepararParaExpandir(Object idMacro) {
        _ultimaExpansion = new ContenedorParametrosExpansion();
        _expansiones.add(_ultimaExpansion);

        _ultimaExpansion.linea = Programa.numeroLineaActual();
        _ultimaExpansion.idVariableSalida = _idUltimaVariable;
        _ultimaExpansion.idMacro = idMacro.toString();
        _ultimaExpansion.variablesEntrada = new ArrayList<String>();
    }

    public static void prepararVariableEntrada(Object parametro) {
        _ultimaExpansion.variablesEntrada.add(parametro.toString());
    }

    private static int incrementoLineas;

    private static int getIncrementoLineas() {
        return incrementoLineas;
    }

    private static void addIncremento(int n) {
        incrementoLineas += n;
    }

    private static void setIncremento(int n) {
        incrementoLineas = n;
    }

    public static boolean expandir() {
        if (Programa.estadoOk()) {
            setIncremento(0);

            for (ContenedorParametrosExpansion contenedorExpansion : _expansiones) {
                String resultadoExpansion = Macro.expandir(contenedorExpansion);

                if (resultadoExpansion != null) {

                    ArrayList<String> lineasExpansion = new ArrayList<>(
                            Arrays.asList(resultadoExpansion.split("[\n\r]+"))
                    );

                    Programa.insertarExpansion(
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

        return true;
    }

    public static void limpiar() {
        _expansiones.clear();
    }
}
