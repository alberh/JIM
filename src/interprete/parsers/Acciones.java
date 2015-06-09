
package interprete.parsers;

import interprete.*;

public class Acciones {

	public static void asignacion(Object lvalue, Object rvalue) {

    Variable variable = obtenerVariable(lvalue);
    int valor = obtenerValor(rvalue);

    variable.valor(valor);
	}

	public static void incremento(Object lvalue) {

    obtenerVariable(lvalue).incremento();
	}

	public static void decremento(Object lvalue) {

    obtenerVariable(lvalue).decremento();
	}

	public static int operacion(char operador, Object op1, Object op2) {

    int v1, v2;

    v1 = obtenerValor(op1);
    v2 = obtenerValor(op2);

    switch (operador) {

      case '+':
        return v1 + v2;

      case '-':
        int res = v1 - v2;
        return res >= 0 ? res : 0;

      case '*':
        return v1 * v2;

      case '/':
        if (v2 == 0) {

          // tratamiento de errores
          return 0;
        }

        return v1 / v2;

      case '%':
        if (v2 == 0) {

          // tratamiento de errores
          return 0;
        }

        return v1 % v2;

      default:
        // informar de error
        return 0;
    }
	}

  protected static Variable obtenerVariable(Object id) {

    // tratamiento de errores
    return Variable.get(id.toString());
  }

  protected static int obtenerValor(Object o) {

    // falta debido a no haber implementado aún el tratamiento de macros
    if (o == null) {

      return 999;
    }

    int valor = 0;

    if (o.getClass() == Integer.class) {

      valor = ((Integer)o).intValue();
    } else {

      try {

         valor = Variable.get((String)o).valor();
      } catch (Exception ex) {

        System.err.println("Error: La variable " + o + " no está inicializada internamente"
                          + " (Probablemente debido a que no se ha ejecutado el analizado previo).");
      }
    }

    return valor;
  }

  protected static Etiqueta obtenerEtiqueta(Object id) {

    // tratamiento de errores
    return Etiqueta.get(id.toString());
  }
}
