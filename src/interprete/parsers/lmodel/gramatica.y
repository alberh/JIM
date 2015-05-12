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

%type <LParserVal> ETIQUETA VARIABLE NUMERO IDMACRO
%type <LParserVal> operacion parametros finInstruccion parametrosMacro masParametrosMacro

%%

inicio :  sentencia inicio
       |
;

sentencia : etiqueta instruccion
;

etiqueta :  '[' ETIQUETA ']' { System.out.println("Etiqueta " + $2.sval); }
         |
;

instruccion : VARIABLE FLECHA {System.out.print("Variable (" + $1.sval + ") <- ");} finInstruccion { LAcciones.asignacion($1.sval, $4.oval); }
            | VARIABLE INCREMENTO { LAcciones.incremento($1.sval); System.out.println("Variable (" + $1.sval + ") ++");}
            | VARIABLE DECREMENTO { LAcciones.decremento($1.sval); System.out.println("Variable (" + $1.sval + ") --");}
            | IF VARIABLE DISTINTO GOTO ETIQUETA {System.out.println("If Variable (" + $2.sval + ") != 0 goto etiqueta (" + $5.sval + ")");}
            | GOTO ETIQUETA { LAcciones.salto($2.sval); System.out.println("Goto Etiqueta ("+ $2.sval + ")");}
;

finInstruccion :  VARIABLE { $<LParserVal>0.sval = $1.sval; System.out.print("Variable (" + $1.sval + ")");} operacion
               |  NUMERO { $<LParserVal>0.ival = $1.ival; System.out.print("Numero (" + $1.ival + ")");} operacion
               |  IDMACRO { $<LParserVal>0.sval = $1.sval; System.out.println("Macro (" + $1.sval + ")");} '(' parametrosMacro ')'
;

operacion : '+' {System.out.print(" + ");} parametros { $$.ival = LAcciones.operacion('+', $<LParserVal>$, $3); }
          | '-' {System.out.print(" - ");} parametros { $$.ival = LAcciones.operacion('-', $<LParserVal>$, $3); }
          | '*' {System.out.print(" * ");} parametros { $$.ival = LAcciones.operacion('*', $<LParserVal>$, $3); }
          | '/' {System.out.print(" / ");} parametros { $$.ival = LAcciones.operacion('/', $<LParserVal>$, $3); }
          | '%' {System.out.print(" % ");} parametros { $$.ival = LAcciones.operacion('%', $<LParserVal>$, $3); }
          | {  System.out.println();}
;

parametros :  NUMERO  { System.out.println("Numero: " + $1.ival); $$.ival = $1.ival; }
           |  VARIABLE { $$.sval = $1.sval; System.out.println("Variable: " + $1.sval); $$.sval = $1.sval; }
;

parametrosMacro : parametros {$$.sval = $1.sval; System.out.println("Parámetro: " + $1.sval);} masParametrosMacro
                | { $$.sval = ""; }
;

masParametrosMacro :  ',' parametros {$$.sval = $2.sval; System.out.println("Parámetro: " + $2.sval);} masParametrosMacro
                   | { $$.sval = ""; }
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





