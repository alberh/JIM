
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

  public static void pruebaParser(String[] args) {

    MacrosParser analizador = new MacrosParser(AbrirLector(args)) ;
    analizador.yyparse();

    Macro.pintar();

    ArrayList<String> params = new ArrayList<>();
    params.add("7");
    params.add("3");
    params.add("5");

    //Variable.set("X5");
    //Variable.set("Z5");
    //System.out.println("Última entrada: " + Variable.get(Variable.EVariable.ENTRADA).id());
    //System.out.println("Última local: " + Variable.get(Variable.EVariable.LOCAL).id());

    String exp = Macro.expandir("Z3", "PrimeraMacro", params);
    System.out.println("PrimeraMacro expandida:");
    System.out.println(exp);
/*
    String exp = Macro.expandir("Z4", "macro2", params);
    System.out.println("macro2 expandida:");
    System.out.println(exp);
*/
  }

  public static void pruebaExpansion() {

    String nombre = "sumar";
    String cuerpo = "IF X1 != 0 GOTO B7\n"
            + "Z12++\n"
            + "IF Z12 != 0 GOTO B8\n"
            + "[B7] X1--\n"
            + "X2++\n"
            + "IF X1 != 0 GOTO B7\n"
            + "[B8] Y <- X2\n";

    String salida = "z8";

    ArrayList<String> params = new ArrayList<>();
    params.add("3");
    params.add("5");

    Macro.set(nombre);
    Macro.get(nombre).cuerpo(cuerpo);
    Macro m = Macro.get(nombre);
    m.nuevaVariable("X2");
    m.nuevaVariable("X1");
    m.nuevaVariable("Z12");

    m.nuevaEtiqueta("B7");
    m.nuevaEtiqueta("B8");

    String s = Macro.expandir(salida, nombre, params);
    System.out.println("Original:");
    System.out.print(salida + " <- " + nombre + ": ");
    params.forEach(p -> System.out.print(p + ", "));
    System.out.println();
    System.out.println(cuerpo);
    System.out.println();
    System.out.println("Expansión:\n" + s);
  }

  public static void main(String[] args) throws IOException 
  {
    pruebaParser(args);
  	
    // pruebaExpansion();

  }   

}


