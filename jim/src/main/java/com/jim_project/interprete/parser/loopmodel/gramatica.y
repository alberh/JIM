%{
  import java.io.*;
  import com.jim_project.interprete.*;
  import com.jim_project.interprete.componente.*;
  import com.jim_project.interprete.parser.*;
%}

%token FLECHA
%token INCREMENTO
%token LOOP
%token END

%token <sval> VARIABLE
%token <sval> IDMACRO
%token <ival> NUMERO

%type <ival> operacion
%type <obj> inicio instruccion finInstruccion operando parametrosMacro masParametrosMacro

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
               |  IDMACRO { _acciones.llamadaAMacro($1); } '(' parametrosMacro ')' { $$ = new LoopParserVal(); }
;
operacion    :  operando '+' operando { $$ = _acciones.operacionBinaria('+', $1, $3); }
             |  operando '*' operando { $$ = _acciones.operacionBinaria('*', $1, $3); }
;
operando : NUMERO  { $$ = $1; }
         |  VARIABLE { $$ = $1; }
;
parametrosMacro : operando masParametrosMacro { $$ = new LoopParserVal(); }
                | { $$ = new LoopParserVal(); }
;
masParametrosMacro :  ',' operando masParametrosMacro { $$ = new LoopParserVal(); }
                   | { $$ = new LoopParserVal(); }
;

%%
  
  private LoopAcciones _acciones;

  /**
   * Constructor de clase.
   *
   * @param r Referencia al lector de entrada.
   * @param controladorEjecucion Referencia al controlador de ejecución en marcha.
   */
  public LoopParser(Reader r, ControladorEjecucion controladorEjecucion) {
     super(controladorEjecucion);
     _analizadorLexico = new LoopLex(r, this);
     _acciones = new LoopAcciones(_controladorEjecucion.ambito());
  }

  @Override
  public int parse() {
    return this.yyparse();
  }

  @Override
  protected int yylex() {
    int yyl_return = -1;

    try {
      yylval = new LoopParserVal(0);
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
