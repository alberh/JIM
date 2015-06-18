
package interprete.parsers.previo;

import java.util.ArrayList;

import interprete.*;
import interprete.parsers.Acciones;

class ContenedorParametrosExpansion {

  public static int linea;
  public static String idVariableSalida;
  public static String idMacro;
  public static ArrayList<String> parametros;
}

public class PrevioAcciones extends Acciones {

  private static String _idUltimaVariable;
  private static ContenedorParametrosExpansion _contenedorExpansion;
  private static ArrayList<ContenedorParametrosExpansion> _expansiones = new ArrayList<>();

  public static void definirVariable(Object idVariable) {

    Variable.set(idVariable.toString());
  }

  public static void definirVariableYMantener(Object idVariable) {

    _idUltimaVariable = idVariable.toString();
  }

  public static void prepararParaExpandir(Object idMacro) {

    _contenedorExpansion = new ContenedorParametrosExpansion();
    _expansiones.add(_contenedorExpansion);

    _contenedorExpansion.linea = Programa.numeroLineaActual();
    _contenedorExpansion.idVariableSalida = _idUltimaVariable;
    _contenedorExpansion.idMacro = idMacro.toString();
    _contenedorExpansion.parametros = new ArrayList<String>();
  }

  public static void prepararParametro(Object parametro) {

    _contenedorExpansion.parametros.add(parametro.toString());
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
