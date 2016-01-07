%{
	import java.io.*;
  	import com.jim_project.interprete.*;
        import com.jim_project.interprete.componente.*;
  	import com.jim_project.interprete.parser.*;
        import com.jim_project.interprete.util.ControladorEjecucion;
%}

%token DEFMACRO
%token GOTO

%token <sval> VARIABLE ETIQUETA IDMACRO ENDMACRO

%%

inicio :  sentencia inicio
	   |
;
sentencia : DEFMACRO IDMACRO { _acciones.nuevaMacro($2); } simbolos ENDMACRO { _acciones.cuerpo($5); }
;
simbolos :  VARIABLE { _acciones.nuevaVariable($1); } simbolos
         |  IDMACRO { _acciones.nuevaLlamadaAMacro($1); } simbolos
         |  '[' ETIQUETA ']' { _acciones.nuevaEtiqueta($2); } simbolos
	 |  GOTO ETIQUETA { _acciones.nuevaEtiquetaSalto($2); } simbolos
	 |
;

%%

  private MacrosAcciones _acciones;

  /**
   * Constructor de clase.
   *
   * @param r Referencia al lector de entrada.
   * @param programa Referencia al programa en ejecución.
   */
  public MacrosParser(Reader r, Programa programa) {
    super(programa);
    _analizadorLexico = new MacrosLex(r, this);
    _acciones = new MacrosAcciones(programa);
  }

  @Override
  public int parse() {
    return this.yyparse();
  }

  @Override
  protected int yylex() {
	int yyl_return = -1;

	try {
		yylval = new MacrosParserVal(0);
		yyl_return = _analizadorLexico.yylex();
	} catch (IOException e) {
		_programa.error().deESEnAnalizadorLexico(((MacrosLex)_analizadorLexico).lineaActual());
	}

	return yyl_return;
  }

  @Override
  public void yyerror(String descripcion, int yystate, int token) {
  	String nombreToken = yyname[token];
	_programa.error().deTokenNoEsperado(((MacrosLex)_analizadorLexico).lineaActual(), nombreToken, descripcion);
  }
