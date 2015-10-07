package interprete.parsers.previo;

import java.util.Arrays;
import java.util.ArrayList;

import interprete.*;
import interprete.parsers.Acciones;

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

    public static void expandir() {

        setIncremento(0);

        _expansiones.forEach(
                contenedorExpansion -> {

                    String resultadoExpansion = Macro.expandir(contenedorExpansion.idVariableSalida,
                            contenedorExpansion.idMacro,
                            contenedorExpansion.variablesEntrada
                    );

                    if (resultadoExpansion != null) {

                        ArrayList<String> lineasExpansion = new ArrayList<>(
                                Arrays.asList(resultadoExpansion.split("\n"))
                        );

                        Programa.insertarExpansion(
                                contenedorExpansion.linea + getIncrementoLineas(),
                                lineasExpansion
                        );

                        addIncremento(lineasExpansion.size() - 1);
                    } else {
                        System.err.println(Macro.errorEnExpansion());
                    }
                }
        );
        
        _expansiones.clear();
    }
}
