
%{
  import java.io.*;
  import com.jim_project.interprete.*;
  import com.jim_project.interprete.componente.*;
  import com.jim_project.interprete.parser.*;
%}

%token FLECHA
%token INCREMENTO
%token DECREMENTO
%token WHILE
%token DISTINTO
%token END

%token <sval> VARIABLE
%token <sval> IDMACRO
%token <ival> NUMERO

%type <ival> operacion
%type <obj> inicio instruccion finInstruccion operando parametrosMacro masParametrosMacro

%%

inicio :  instruccion { $$ = $1; } inicio
       | { $$ = new WhileParserVal(); }
;
instruccion : VARIABLE { _acciones.variableAsignada($1); } FLECHA finInstruccion { _acciones.asignacion($1, $4); }
            | VARIABLE INCREMENTO { _acciones.incremento($1); }
            | VARIABLE DECREMENTO { _acciones.decremento($1); }
            | WHILE VARIABLE DISTINTO { _acciones.abreBucle($2, _controladorEjecucion.numeroLineaActual()); }
            | END { _acciones.cierraBucle(_controladorEjecucion.numeroLineaActual()); }
;
finInstruccion :  VARIABLE { $$ = $1; }
               |  NUMERO { $$ = $1; }
               |  operacion { $$ = $1; }
               |  IDMACRO { _acciones.llamadaAMacro($1); } '(' parametrosMacro ')' { $$ = new WhileParserVal(); }
;
operacion   :  operando '+' operando { $$ = _acciones.operacionBinaria('+', $1, $3); }
                |  operando '-' operando { $$ = _acciones.operacionBinaria('-', $1, $3); }
                |  operando '*' operando { $$ = _acciones.operacionBinaria('*', $1, $3); }
                |  operando '/' operando { $$ = _acciones.operacionBinaria('/', $1, $3); }
                |  operando '%' operando { $$ = _acciones.operacionBinaria('%', $1, $3); }
;
operando : NUMERO { $$ = $1; }
         |  VARIABLE { $$ = $1; }
;
parametrosMacro : operando {$$ = $1; } masParametrosMacro { $$ = new WhileParserVal(); }
                | { $$ = new WhileParserVal(); }
;
masParametrosMacro :  ',' operando { $$ = $2; } masParametrosMacro { $$ = new WhileParserVal(); }
                   | { $$ = new WhileParserVal(); }
;

%%

  private WhileAcciones _acciones;

  /**
   * Constructor de clase.
   *
   * @param r Referencia al lector de entrada.
   * @param controladorEjecucion Referencia al controlador de ejecución en marcha.
   */
  public WhileParser(Reader r, ControladorEjecucion controladorEjecucion) {
     super(controladorEjecucion);
     _analizadorLexico = new WhileLex(r, this);
     _acciones = new WhileAcciones(_controladorEjecucion.ambito());
  }

  @Override
  public int parse() {
    return this.yyparse();
  }

  @Override
  protected int yylex() {
    int yyl_return = -1;

    try {
      yylval = new WhileParserVal(0);
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
