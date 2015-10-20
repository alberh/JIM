
package interprete.parsers.previo;
/*
import java.io.* ;
import java.util.ArrayList;
/*
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
/*
  public static void pruebaParser(String[] args) {

    PrevioParser analizador = new PrevioParser(AbrirLector(args)) ;
    analizador.yyparse();

    Variable.pintar();
    Etiqueta.pintar();
    Macro.pintar();
    Bucle.pintar();
  }

  public static void main(String[] args) throws IOException {

    pruebaParser(args);
  }   

}
*/