

%{
  import java.io.*;
  import org.alberto.interprete.*;
  import org.alberto.interprete.parsers.*;
%}

%token VARIABLE
%token FLECHA
%token INCREMENTO
%token NUMERO
%token IDMACRO
%token LOOP
%token END

%token <sval> VARIABLE IDMACRO
%token <ival> NUMERO

%type <obj> parametros finInstruccion
%type <ival> operacion
%type <obj> inicio instruccion parametrosMacro masParametrosMacro

%%

inicio :  instruccion { $$ = $1; } inicio
       | { $$ = new LoopParserVal(); }
;
instruccion : VARIABLE FLECHA finInstruccion { LoopAcciones.asignacion($1, $3); }
            | VARIABLE INCREMENTO { LoopAcciones.incremento($1); }
            | LOOP VARIABLE { LoopAcciones.abreBucle($2, Programa.numeroLineaActual()); }
            | END { LoopAcciones.cierraBucle(Programa.numeroLineaActual()); }
;
finInstruccion :  VARIABLE { $$ = $1; }
               |  NUMERO { $$ = $1; }
               |  operacion { $$ = $1; }
               |  IDMACRO { $$ = new LoopParserVal(); } '(' parametrosMacro ')' { /* Tratamiento de macros */ }
;
operacion    :  parametros '+' parametros { $$ = LoopAcciones.operacion('+', $1, $3); }
             |  parametros '*' parametros { $$ = LoopAcciones.operacion('*', $1, $3); }
;
parametros :  NUMERO  { $$ = $1; }
           |  VARIABLE { $$ = $1; }
;
parametrosMacro : parametros {$$ = $1; } masParametrosMacro
                | { $$ = new LoopParserVal(); }
;
masParametrosMacro :  ',' parametros {$$ = $2; } masParametrosMacro
                   | { $$ = new LoopParserVal(); }
;

%%

  /** referencia al analizador léxico
  **/
  private LoopLex analex;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public LoopParser(Reader r) {
     analex = new LoopLex(r, this);
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
  protected int yylex () {
  int yyl_return = -1;

  try {
    yylval = new LoopParserVal(0);
    yyl_return = analex.yylex();
  } catch (IOException e) {
    org.alberto.interprete.Error.deESenAnalizadorLexico();
  }

  return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) {
    String nombreToken = yyname[token];
    org.alberto.interprete.Error.deTokenNoEsperado(nombreToken, descripcion);
    /*
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
  */
  }
