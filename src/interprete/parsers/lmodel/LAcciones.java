
package interprete.parsers.lmodel;

import interprete.*;
import interprete.parsers.Acciones;

public class LAcciones extends Acciones {

  public static void saltoCondicional(Object idVariable, Object idEtiqueta) {

    Variable v = obtenerVariable(idVariable);

    if (v.valor() == 0) {

      saltoIncondicional(idEtiqueta);
    }
  }

	public static void saltoIncondicional(Object idEtiqueta) {

    Etiqueta et = obtenerEtiqueta(idEtiqueta);

    if (et == null) {

      // Gestión de errores.
      return ;
    }

    Programa.numeroLineaActual(et.linea());
	}

  protected static Etiqueta obtenerEtiqueta(Object id) {

    // tratamiento de errores
    return Etiqueta.get(id.toString());
  }
}
