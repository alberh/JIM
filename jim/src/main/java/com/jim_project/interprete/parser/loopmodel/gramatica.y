

%{
  import java.io.*;
  import com.jim_project.interprete.*;
  import com.jim_project.interprete.componente.*;
  import com.jim_project.interprete.parser.*;
  import com.jim_project.interprete.util.ControladorEjecucion;
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

%type <ival> operacion
%type <obj> inicio operando finInstruccion instruccion parametrosMacro masParametrosMacro

%%

inicio :  instruccion { $$ = $1; } inicio
       | { $$ = new LoopParserVal(); }
;
instruccion : VARIABLE { _acciones.variableAsignada($1); } FLECHA finInstruccion { _acciones.asignacion($1, $4); }
            | VARIABLE INCREMENTO { _acciones.incremento($1); }
            | LOOP VARIABLE { _acciones.abreBucle($2, _controladorEjecucion.numeroLineaActual()); }
            | END { _acciones.cierraBucle(_controladorEjecucion.numeroLineaActual()); }
;
finInstruccion :  VARIABLE { $$ = $1; }
               |  NUMERO { $$ = $1; }
               |  operacion { $$ = $1; }
               |  IDMACRO { _acciones.llamadaAMacro($1); } '(' parametrosMacro ')'
;
operacion    :  operando '+' operando { $$ = _acciones.operacionBinaria('+', $1, $3); }
             |  operando '*' operando { $$ = _acciones.operacionBinaria('*', $1, $3); }
;
operando :  NUMERO  { $$ = $1; }
           |  VARIABLE { $$ = $1; }
;
parametrosMacro : operando masParametrosMacro
                | { ; }
;
masParametrosMacro :  ',' operando masParametrosMacro
                   | { ; }
;

%%
  
  private LoopAcciones _acciones;

  public LoopParser(Reader r, ControladorEjecucion controladorEjecucion) {
     super(controladorEjecucion);
     _analizadorLexico = new LoopLex(r, this);
     _acciones = new LoopAcciones(_controladorEjecucion.ambito());
     //yydebug = true;
  }

  public int parse() {
    return this.yyparse();
  }

  /** esta función se invoca por el analizador cuando necesita el 
  *** siguiente token del analizador léxico
  **/
  protected int yylex () {
  int yyl_return = -1;

  try {
    yylval = new LoopParserVal(0);
    yyl_return = _analizadorLexico.yylex();
  } catch (IOException e) {
    _programa.error().deESEnAnalizadorLexico();
  }

  return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) {
    String nombreToken = yyname[token];
    _programa.error().deTokenNoEsperado(nombreToken, descripcion);
    /*
  System.err.println ("Error en línea "+Integer.toString(_analizadorLexico.lineaActual())+" : "+descripcion);
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
