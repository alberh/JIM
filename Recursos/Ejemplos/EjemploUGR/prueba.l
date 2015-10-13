// fuente 'jflex' para una calculadora sencilla

%%

%byaccj

%{

  private Parser yyparser ;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public Yylex(java.io.Reader r, Parser p ) 
  {
    this(r);
    linea_actual = 1 ;
    yyparser = p  ;
  }

  /** guarda el número de linea actual
  **/

  private int linea_actual ;

  /** devuelve el numero de linea donde está el último caracter leido
  **/

  public int lineaActual()
  {
     return linea_actual ;
  }
  

%}


%%

"="    { return Parser.ASIG ;
       }
"("    { return Parser.ABR_PARENT ; 
       }
")"    { return Parser.CER_PARENT ; 
       }
";"    { return Parser.PYC ;
       }
"+"|"-"
       {  yyparser.yylval = new ParserVal( new Character(yytext().charAt(0)) ) ;
          return Parser.OP_MAS_MENOS ;
       }
"*"|"/"
       {  yyparser.yylval = new ParserVal( new Character(yytext().charAt(0)) ) ;
          return Parser.OP_MULT_DIV ;
       }
[0-9]+ 
       {  yyparser.yylval = new ParserVal( new Integer(yytext()) );
          return Parser.CONSTANTE ; 
       }
[0-9]+"."[0-9]+ ?  
       {  yyparser.yylval = new ParserVal( new Double(yytext()) ); 
          return Parser.CONSTANTE ; 
       }
"pepe" {  return Parser.PEPE ;
       }
[a-zA-Z][a-zA-Z0-9_]*
       {  yyparser.yylval = new ParserVal( (Object) yytext() ); 
          return Parser.IDENTIFICADOR ;  
       }

\n     { // lleva la cuenta de lineas 
         linea_actual ++ ;
       }



[ \t]+ {  /** accion vacia: se ignoran los espacios y tabuladores **/ 
       }


.      {  /** token desconocido: se produce un mensaje de error **/
          yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido"); 
       }
