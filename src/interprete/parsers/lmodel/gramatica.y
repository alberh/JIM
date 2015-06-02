
%{
  import java.io.*;
  import interprete.*;
  import interprete.parsers.*;
%}

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

%token <sval> VARIABLE ETIQUETA IDMACRO
%token <ival> NUMERO

%type <obj> parametros finInstruccion
%type <ival> operacion
%type <obj> inicio sentencia etiqueta instruccion parametrosMacro masParametrosMacro

%%

inicio :  sentencia { $$ = $1; } inicio
       | { $$ = new LParserVal(); }
;
sentencia : etiqueta instruccion { $$ = new LParserVal(); }
;
etiqueta : '[' ETIQUETA ']' { $$ = $2; /*System.out.println("Etiqueta " + $2);*/ }
         | { $$ = new LParserVal(); }
;
instruccion : VARIABLE FLECHA { /*System.out.print("Variable (" + $1 + ") <- ");*/ } finInstruccion { LAcciones.asignacion($1, $4); }
            | VARIABLE INCREMENTO { LAcciones.incremento($1); /*System.out.println("Variable (" + $1 + ") ++");*/}
            | VARIABLE DECREMENTO { LAcciones.decremento($1); /*System.out.println("Variable (" + $1 + ") --");*/}
            | IF VARIABLE DISTINTO GOTO ETIQUETA { LAcciones.saltoCondicional($2, $5); /*System.out.println("If Variable (" + $2 + ") != 0 goto etiqueta (" + $5 + ")");*/}
            | GOTO ETIQUETA { LAcciones.saltoIncondicional($2); /*System.out.println("Goto Etiqueta ("+ $2 + ")");*/}
;
finInstruccion :  VARIABLE { $$ = $1; /*System.out.println("Variable (" + $1 + ")");*/}
               |  NUMERO { $$ = $1; /*System.out.println("Numero (" + $1 + ")");*/}
               |  operacion { $$ = $1; }
               |  IDMACRO { $$ = new LParserVal(); /*System.out.println("Macro (" + $1 + ")");*/} '(' parametrosMacro ')' { /* Tratamiento de macros */ }
;
operacion	   :  parametros '+' parametros { $$ = LAcciones.operacion('+', $1, $3); /*System.out.println($1 + " + " + $3);*/ }
			       |  parametros '-' parametros { $$ = LAcciones.operacion('-', $1, $3); /*System.out.println($1 + " - " + $3);*/ }
			       |  parametros '*' parametros { $$ = LAcciones.operacion('*', $1, $3); /*System.out.println($1 + " * " + $3);*/ }
			       |  parametros '/' parametros { $$ = LAcciones.operacion('/', $1, $3); /*System.out.println($1 + " / " + $3);*/ }
			       |  parametros '%' parametros { $$ = LAcciones.operacion('%', $1, $3); /*System.out.println($1 + " % " + $3);*/ }
;
parametros :  NUMERO  { $$ = $1; }
           |  VARIABLE { $$ = $1; }
;

parametrosMacro : parametros {$$ = $1; /*System.out.println("Parámetro: " + $1);*/} masParametrosMacro
                | { $$ = new LParserVal(); }
;

masParametrosMacro :  ',' parametros {$$ = $2; /*System.out.println("Parámetro: " + $2);*/} masParametrosMacro
                   | { $$ = new LParserVal(); }
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

  public int parse() {
    
    return this.yyparse();
  }

  public AnalizadorLexico analizadorLexico() {

    return analex;
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





