
//package interprete.parsers.analizador;

import java.io.* ;
import java.util.ArrayList;

class Main
{

  private static Reader AbrirLector(String args[]) 
  {
    Reader lector = null ;
   
    if ( args.length > 0 ) 
    {
       try
       {
          lector = new FileReader(args[0]) ;
       }
       catch( IOException exc )
       {
          System.err.println("imposible abrir archivo '"+args[0]+"'");
          System.err.println("causa: "+exc.getMessage()) ;
          System.exit(1) ;
       }
 
       System.out.println("leyendo archivo '"+args[0]+"'");
    }
    else 
    {
       lector = new InputStreamReader(System.in) ;  
       System.out.println("leyendo entrada estándard (terminar con ctrl-d)");
    }
    
    return lector ;
  }

  /***************************************************************************/

  public static void main(String args[]) throws IOException 
  {
  	/*
      MacrosParser analizador = new MacrosParser(AbrirLector(args)) ;
      analizador.yyparse();
	*/
      String nombre = "miMacro";
      String cuerpo = "x1 <- 3\n"
      				+ "y <- x5";

      String salida = "z8";

      ArrayList<String> params = new ArrayList<>();
      params.add("7");
      params.add("x2");
      params.add("z5");

      Macro.set(nombre, cuerpo);
      Macro m = Macro.get(nombre);
      m.nuevaVariable("x1");
      m.nuevaVariable("y");
      m.nuevaVariable("x5");

      String s = Macro.expandir(nombre, salida, params);
      System.out.println("Original:");
      System.out.print(salida + " <- " + nombre + ": ");
      params.forEach(p -> System.out.print(p + ", "));
      System.out.println();
      System.out.println(cuerpo);
      System.out.println();
      System.out.println("Expansión:\n" + s);

  }   

}
