%{
  import java.io.*;
  import com.jim_project.interprete.*;
  import com.jim_project.interprete.componente.*;
  import com.jim_project.interprete.parser.*;
%}

%token FLECHA
%token INCREMENTO
%token DECREMENTO
%token IF
%token DISTINTO
%token GOTO

%token <sval> VARIABLE
%token <sval> ETIQUETA
%token <sval> IDMACRO
%token <ival> NUMERO

%type <ival> operacion
%type <obj> inicio sentencia etiqueta instruccion finInstruccion operando parametrosMacro masParametrosMacro

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
               |  IDMACRO { _acciones.llamadaAMacro($1); } '(' parametrosMacro ')' { $$ = new LParserVal(); }
;
operacion   :  operando '+' operando { $$ = _acciones.operacionBinaria('+', $1, $3); }
                |  operando '-' operando { $$ = _acciones.operacionBinaria('-', $1, $3); }
                |  operando '*' operando { $$ = _acciones.operacionBinaria('*', $1, $3); }
                |  operando '/' operando { $$ = _acciones.operacionBinaria('/', $1, $3); }
                |  operando '%' operando { $$ = _acciones.operacionBinaria('%', $1, $3); }
;
operando : NUMERO  { $$ = $1; }
         |  VARIABLE { $$ = $1; }
;
parametrosMacro : operando masParametrosMacro { $$ = new LParserVal(); }
                | { $$ = new LParserVal(); }
;
masParametrosMacro :  ',' operando masParametrosMacro { $$ = new LParserVal(); }
                   | { $$ = new LParserVal(); }
;

%%

  private LAcciones _acciones;

  /**
   * Constructor de clase.
   *
   * @param r Referencia al lector de entrada.
   * @param controladorEjecucion Referencia al controlador de ejecución en marcha.
   */
  public LParser(Reader r, ControladorEjecucion controladorEjecucion) {
     super(controladorEjecucion);
     _analizadorLexico = new LLex(r, this);
     _acciones = new LAcciones(_controladorEjecucion.ambito());
  }

  @Override
  public int parse() {
    return this.yyparse();
  }

  @Override
  protected int yylex() {
    int yyl_return = -1;

    try {
      yylval = new LParserVal(0);
      yyl_return = _analizadorLexico.yylex();
    } catch (IOException e) {
      _programa.error().deESEnAnalizadorLexico();
    }

    return yyl_return;
  }

  @Override
  public void yyerror(String descripcion, int yystate, int token) {
    String nombreToken = yyname[token];
    _programa.error().deTokenNoEsperado(nombreToken, descripcion);
  }
