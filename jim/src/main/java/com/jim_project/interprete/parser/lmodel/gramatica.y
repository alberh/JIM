
%{
  import java.io.*;
  import com.jim_project.interprete.*;
  import com.jim_project.interprete.componente.*;
  import com.jim_project.interprete.parser.*;
  import com.jim_project.interprete.util.ControladorEjecucion;
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

%type <ival> operacion
%type <obj> inicio sentencia operando instruccion finInstruccion etiqueta parametrosMacro masParametrosMacro

%%

inicio :  sentencia { $$ = $1; } inicio
       | { $$ = new LParserVal(); }
;
sentencia : etiqueta instruccion { $$ = new LParserVal(); }
;
etiqueta : '[' ETIQUETA ']' { $$ = $2; }
         | { $$ = new LParserVal(); }
;
instruccion : VARIABLE { _acciones.variableAsignada($1); } FLECHA finInstruccion { _acciones.asignacion($1, $4); }
            | VARIABLE INCREMENTO { _acciones.incremento($1); }
            | VARIABLE DECREMENTO { _acciones.decremento($1); }
            | IF VARIABLE DISTINTO GOTO ETIQUETA { _acciones.saltoCondicional($2, $5); }
            | GOTO ETIQUETA { _acciones.saltoIncondicional($2); }
;
finInstruccion :  VARIABLE { $$ = $1; }
               |  NUMERO { $$ = $1; }
               |  operacion { $$ = $1; }
               |  IDMACRO { _acciones.llamadaAMacro($1); } '(' parametrosMacro ')'
;
operacion   :  operando '+' operando { $$ = _acciones.operacionBinaria('+', $1, $3); }
                |  operando '-' operando { $$ = _acciones.operacionBinaria('-', $1, $3); }
                |  operando '*' operando { $$ = _acciones.operacionBinaria('*', $1, $3); }
                |  operando '/' operando { $$ = _acciones.operacionBinaria('/', $1, $3); }
                |  operando '%' operando { $$ = _acciones.operacionBinaria('%', $1, $3); }
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

  private LAcciones _acciones;

  public LParser(Reader r, ControladorEjecucion controladorEjecucion) {
     super(controladorEjecucion);
     _analizadorLexico = new LLex(r, this);
     _acciones = new LAcciones(_controladorEjecucion.ambito());
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
    yylval = new LParserVal(0);
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
