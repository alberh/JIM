
%{
	import java.io.*;
	import com.jim_project.interprete.*;
	import com.jim_project.interprete.util.*;
        import com.jim_project.interprete.componente.*;
	import com.jim_project.interprete.parser.*;
        import com.jim_project.interprete.util.ControladorEjecucion;
%}

// lista tokens por orden de prioridad

%token FLECHA
%token INCREMENTO
%token DECREMENTO
%token IF
%token DISTINTO
%token GOTO
%token WHILE
%token LOOP
%token END

%token <sval> ETIQUETA VARIABLE IDMACRO NUMERO

%type <sval> inicio sentencia etiqueta instruccion finInstruccion operacion operando parametros parametrosMacro masParametrosMacro

%%

inicio : sentencia { $$ = $1; } inicio
       | { $$ = null; }
;
sentencia : etiqueta instruccion { ; }
;
etiqueta :  '[' ETIQUETA ']' { _acciones.definirEtiqueta($2, _controladorEjecucion.numeroLineaActual()); }
         | { ; }
;
instruccion : VARIABLE { _acciones.definirVariable($1); } FLECHA finInstruccion
            | VARIABLE INCREMENTO { _acciones.definirVariable($1); }
            | VARIABLE DECREMENTO { _acciones.definirVariable($1); }
            | IF VARIABLE DISTINTO GOTO ETIQUETA { _acciones.definirVariable($2); }
            | GOTO ETIQUETA { ; }
            | LOOP VARIABLE { _acciones.definirVariable($2); _acciones.definirInicioBucle(_controladorEjecucion.numeroLineaActual()); }
            | WHILE VARIABLE DISTINTO { _acciones.definirVariable($2); _acciones.definirInicioBucle(_controladorEjecucion.numeroLineaActual()); }
            | END { _acciones.definirFinBucle(_controladorEjecucion.numeroLineaActual()); }
;
finInstruccion :  VARIABLE { _acciones.definirVariable($1); }
               |  NUMERO { ; }
               |  operacion { ; }
               |  IDMACRO { _acciones.definirLlamadaAMacro($1); } '(' parametrosMacro ')'
;
operacion	   :  operando '+' operando { ; }
			   |  operando '-' operando { ; }
			   |  operando '*' operando { ; }
			   |  operando '/' operando { ; }
			   |  operando '%' operando { ; }
;
operando :  NUMERO { ; }
           |  VARIABLE { _acciones.definirVariable($1); }
;
parametros :  NUMERO { _acciones.definirVariableEntradaMacro($1); }
           	|  VARIABLE { _acciones.definirVariable($1); _acciones.definirVariableEntradaMacro($1); }
;
parametrosMacro : parametros masParametrosMacro { ; }
                | { ; }
;
masParametrosMacro :  ',' parametros masParametrosMacro { ; }
                   | { ; }
;

%%

  private PrevioAcciones _acciones;

  /**
   * Constructor de clase.
   *
   * @param r Referencia al lector de entrada.
   * @param controladorEjecucion Referencia al controlador de ejecución en marcha.
   */
  public PrevioParser(Reader r, ControladorEjecucion controladorEjecucion) {
        super(controladorEjecucion);
        _analizadorLexico = new PrevioLex(r, this);
        _acciones = new PrevioAcciones(_controladorEjecucion.ambito());
  }

  @Override
  public int parse() {
    return this.yyparse();
  }

  @Override
  protected int yylex() {
	int yyl_return = -1;

	try {
		yylval = new PrevioParserVal(0);
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
