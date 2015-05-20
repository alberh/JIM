
package interprete.parsers.previo;
//
//import java.util.Hashtable;
//
//class MacrosAcciones {
//
//  public static void Asignacion(Object ident, Object valor)
//  {
//   variables.put((String)ident,valor); 
// }
//
//  // devuelve el valor de una variable (0 entero si no existe, y da mensaje)
//  // (ident debe ser String)
// public static Object LeerValorVariable(Object ident)
// {
//  if ( variables.containsKey((String)ident) )
//   return variables.get((String)ident) ;
// else
// {
//   System.out.println("error: la variable '"+ident+"' no existe (se usa el valor 0)");
//   return new Integer(0) ;
// }
//}
//
//  // convierte el objeto 'obj' a double
//  // 'obj' puede ser 'Double' o 'Integer'
//  //
//public static double ValorReal( Object obj )
//{
//  if ( obj.getClass() == Double.class )
//	return ((Double) obj).doubleValue() ;
//	  else // se asume obj.getClass() == Integer.class
//	  return ((Integer) obj).doubleValue() ;
//	}
//
//  // evalua un operador binario aplicado a dos operandos, devuelve el resultado 
//  // (los operandos pueden ser de tipo Integer o Double)
//  //
//	public static int Operacion(char op, int v1, int v2)
//	{
//	  switch (op)
//	  {
//		case '+': return v1 + v2;
//		case '-': return v1 - v2;
//		case '*': return v1 * v2;
//		case '/': return v1 / v2;
//		default : throw new Exception("Operación no válida (op = '" + op + "', v1 = " + v1 + ", v2 = " + v2 + ").");
//	  }
//	} 
//
//  // evalua un operador unario aplicado a un operando, devuelve el resultado 
//  // (el operando puede ser de tipo Integer o Double)
//  //
//	public static Object Operador( Object operador, Object operando )
//	{
//	  if ( operando.getClass() == Double.class )
//	  {
//		switch ( ( (Character) operador ).charValue() ) 
//		{
//		 case '+' : return operando ;
//		 case '-' : return - ValorReal(operando) ; 
//		 default  : System.out.println("error interno: operador unario desconocido.");
//		 return 0.0 ;
//	   }
//	 }
//	  else  // se asume que: operando.getClass() == Integer.class
//	  {   
//		switch ( ( (Character) operador ).charValue()  ) 
//		{
//		 case '+' : return operando ;
//		 case '-' : return - ((Integer)operando).intValue() ;
//		 default  : System.out.println("error interno: operador unario desconocido");
//		 return 0 ;
//	   }
//	 }
//   } 
//
//} // class Acc
