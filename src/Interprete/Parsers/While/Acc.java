// fuente Java para las acciones semánticas de una calculadora sencilla

import java.util.Hashtable ;


class Acc
{
  // tabla con las variables definidas y sus valores
  private static Hashtable<String,Object> variables = new Hashtable<String,Object>() ;
             // documentacion sobre 'Hashtable' aqui:
             // http://java.sun.com/j2se/1.4.2/docs/api/java/util/Hashtable.html

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

} // class Acc
