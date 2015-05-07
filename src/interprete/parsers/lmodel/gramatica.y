// fuente byaccj para una calculadora sencilla
 

%{
  import java.io.*;
%}


// lista de tokens por orden de prioridad

%token ETIQUETA
%token VARIABLE
%token FLECHA
%token INCREMENTO
%token DECREMENTO
%token IF
%token DISTINTO
%token GOTO
%token NUMERO
%token IDMACRO

%%

inicio :  sentencia inicio
       |
;

sentencia : etiqueta instruccion
;

etiqueta :  '[' ETIQUETA ']' { System.out.println("Etiqueta " + $2.sval); }
         |
;

instruccion : VARIABLE FLECHA {System.out.print("Variable (" + $1.sval + ") <- ");} finInstruccion
            | VARIABLE INCREMENTO {System.out.println("Variable (" + $1.sval + ") ++");}
            | VARIABLE DECREMENTO {System.out.println("Variable (" + $1.sval + ") --");}
            | IF VARIABLE DISTINTO GOTO ETIQUETA {System.out.println("If Variable (" + $2.sval + ") != 0 goto etiqueta (" + $5.sval + ")");}
            | GOTO ETIQUETA {System.out.println("Goto Etiqueta ("+ $2.sval + ")");}
;

finInstruccion :  VARIABLE {System.out.print("Variable (" + $1.sval + ")");} operacion
               |  NUMERO {System.out.print("Numero (" + $1.ival + ")");} operacion
               |  IDMACRO {System.out.println("Macro (" + $1.sval + ")");} '(' parametrosMacro ')'
;

operacion : '+' {System.out.print(" + ");} parametros
          | '-' {System.out.print(" - ");} parametros
          | '*' {System.out.print(" * ");} parametros
          | '/' {System.out.print(" / ");} parametros
          | '%' {System.out.print(" % ");} parametros
          | {System.out.println();}
;

parametros :  NUMERO  { System.out.println("Numero: " + $1.ival); }
           |  VARIABLE { $$.sval = $1.sval; System.out.println("Variable: " + $1.sval); }
;

parametrosMacro : parametros {$$.sval = $1.sval; System.out.println("Parámetro: " + $1.sval);} masParametrosMacro
                |
;

masParametrosMacro :  ',' parametros {$$.sval = $2.sval; System.out.println("Parámetro: " + $2.sval);} masParametrosMacro
                   |
;

%%

  /** referencia al analizador léxico
  **/
  private LLex analex;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public LParser(Reader r) 
  {
     analex = new LLex(r, this);
     //yydebug = true;
  }

  /** esta función se invoca por el analizador cuando necesita el 
  *** siguiente token del analizador léxico
  **/
  private int yylex () 
  {
    int yyl_return = -1;

    try 
    {
       yylval = new LParserVal(0);
       yyl_return = analex.yylex();
    }
    catch (IOException e) 
    {
       System.err.println("error de E/S:"+e);
    }

    return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) 
  {
     System.err.println ("Error en línea "+Integer.toString(analex.lineaActual())+" : "+descripcion);
     System.err.println ("Token leído : "+yyname[token]);
     System.err.print("Token(s) que se esperaba(n) : ");

     String  nombresTokens = "" ;

     int yyn ;

     // añadir en 'nombresTokens' los tokens que permitirian desplazar

     nombresTokens += "desplazan: " ;

     for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
     {
        yyn = yysindex[yystate] ;  
        if ((yyn != 0) && (yyn += yychar) >= 0 &&
             yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
            nombresTokens += yyname[yychar] + " ";
        }
     }

     // añadir tokens que permitirian reducir

     nombresTokens += "reducen: " ;

     for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
     {
         yyn = yyrindex[yystate] ;  
         if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
         {
            nombresTokens += yyname[yychar] + " " ;
         }
     }

    System.err.println(nombresTokens);
    
  }

  public void yyerror (String descripcion) 
  {
     System.err.println ("Error en línea "+Integer.toString(analex.lineaActual())+" : "+descripcion);
     //System.err.println ("Token leido : "+yyname[token]);
   
  }





