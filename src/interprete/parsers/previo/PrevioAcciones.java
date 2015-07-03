
package interprete.parsers.previo;

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

  public static void prepararParaExpandir(Object idMacro) {

    _ultimaExpansion = new ContenedorParametrosExpansion();
    _expansiones.add(_ultimaExpansion);

    _ultimaExpansion.linea = Programa.numeroLineaActual();
    _ultimaExpansion.idVariableSalida = _idUltimaVariable;
    _ultimaExpansion.idMacro = idMacro.toString();
    _ultimaExpansion.parametros = new ArrayList<String>();
  }

  public static void prepararParametro(Object parametro) {

    _ultimaExpansion.parametros.add(parametro.toString());
  }

  public static void expandir() {

    _expansiones.forEach(

      contenedorExpansion -> {

        String resultadoExpansion = Macro.expandir(contenedorExpansion.idVariableSalida,
                                                   contenedorExpansion.idMacro,
                                                   contenedorExpansion.parametros);

        if (resultadoExpansion != null) {

          Programa.insertarExpansion(contenedorExpansion.linea, resultadoExpansion);
        } else {

          // gestión de errores
        }
      }
    );
  }
}
