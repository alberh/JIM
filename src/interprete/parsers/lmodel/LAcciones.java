
package interprete.parsers.lmodel;

import interprete.*;

public class LAcciones {

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

	public static void salto(Object etiqueta) {


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

  private static Variable obtenerVariable(Object id) {

    // tratamiento de errores
    return Variable.get(id.toString());
  }

  private static int obtenerValor(Object o) {

    // falta debido a no haber implementado aún el tratamiento de macros
    if (o == null) {

      return 999;
    }

    if (o.getClass() == Integer.class) {

      return ((Integer)o).intValue();
    } else {

      return Variable.get((String)o).valor();
    }
  }

	/*
  // cambia o inicializa el valor de una variable
  // (ident debe ser String)
  public static void Asignacion(Object ident, Object valor)
  {
       variables.put((String)ident,valor); 
  }

  // devuelve el valor de una variable (0 entero si no existe, y da mensaje)
  // (ident debe ser String)
  public static Object LeerValorVariable(Object ident)
  {
      if ( variables.containsKey((String)ident) )
         return variables.get((String)ident) ;
      else
      {
         System.out.println("error: la variable '"+ident+"' no existe (se usa el valor 0)");
         return new Integer(0) ;
      }
  }

  // convierte el objeto 'obj' a double
  // 'obj' puede ser 'Double' o 'Integer'
  //
  public static double ValorReal( Object obj )
  {
      if ( obj.getClass() == Double.class )
          return ((Double) obj).doubleValue() ;
      else // se asume obj.getClass() == Integer.class
          return ((Integer) obj).doubleValue() ;
  }

  // evalua un operador binario aplicado a dos operandos, devuelve el resultado 
  // (los operandos pueden ser de tipo Integer o Double)
  //
  public static Object Operador( Object operador, Object operando1, Object operando2 )
  {
      if ( operando1.getClass() == Double.class || operando2.getClass() == Double.class )
      {
          double op1 = ValorReal(operando1) ,
                 op2 = ValorReal(operando2) ;

          switch ( ( (Character) operador ).charValue() )
          {
             case '+' : return op1+op2 ; 
             case '-' : return op1-op2 ; 
             case '*' : return op1*op2 ; 
             case '/' : return op1/op2 ; 
             default  : System.out.println("error interno: operador binario desconocido");
                        return 0 ;
          }
      }
      else
      {
          int op1 = ((Integer)operando1).intValue() ,
              op2 = ((Integer)operando2).intValue() ;

          switch ( ( (Character) operador ).charValue() )
          {
             case '+' : return op1+op2 ; 
             case '-' : return op1-op2 ; 
             case '*' : return op1*op2 ; 
             case '/' : return op1/op2 ; 
             default  : System.out.println("error interno: operador binario desconocido");
                        return 0 ;
          }
      }
  } 

  // evalua un operador unario aplicado a un operando, devuelve el resultado 
  // (el operando puede ser de tipo Integer o Double)
  //
  public static Object Operador( Object operador, Object operando )
  {
      if ( operando.getClass() == Double.class )
      {
          switch ( ( (Character) operador ).charValue() ) 
          {
             case '+' : return operando ;
             case '-' : return - ValorReal(operando) ; 
             default  : System.out.println("error interno: operador unario desconocido.");
                        return 0.0 ;
          }
      }
      else  // se asume que: operando.getClass() == Integer.class
      {   
          switch ( ( (Character) operador ).charValue()  ) 
          {
             case '+' : return operando ;
             case '-' : return - ((Integer)operando).intValue() ;
             default  : System.out.println("error interno: operador unario desconocido");
                        return 0 ;
          }
      }
  } 
	 */
}
