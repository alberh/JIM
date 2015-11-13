
%{
	import java.io.*;
  	import com.jim_project.interprete.*;
        import com.jim_project.interprete.componente.*;
  	import com.jim_project.interprete.parser.*;
%}


// lista de tokens por orden de prioridad

%token DEFMACRO   // comienzo de la declaración de una macro
%token IDMACRO    // identificador de la macro
%token VARIABLE
%token GOTO
%token ETIQUETA
%token ENDMACRO   // fin de la declaración de una macro

%token <sval> VARIABLE ETIQUETA IDMACRO ENDMACRO

%%

inicio :  sentencia inicio
	   |
;
sentencia : DEFMACRO IDMACRO { macroEnProceso = MacrosAcciones.nuevaMacro($2); } simbolos ENDMACRO { MacrosAcciones.cuerpo($5, macroEnProceso); }
;
simbolos :  VARIABLE { MacrosAcciones.nuevaVariable($1, macroEnProceso); } simbolos
         |  IDMACRO { MacrosAcciones.nuevaLlamadaAMacro($1, macroEnProceso); } simbolos
         |  '[' ETIQUETA ']' { MacrosAcciones.nuevaEtiqueta($2, macroEnProceso); } simbolos
	 |  GOTO ETIQUETA { MacrosAcciones.nuevaEtiquetaSalto($2, macroEnProceso); } simbolos
	 |
;

%%

	/** referencia al analizador léxico
  **/
	private MacrosLex analex;
        private Macro macroEnProceso;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public MacrosParser(Reader r) {
	analex = new MacrosLex(r, this);
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
		yylval = new MacrosParserVal(0);
		yyl_return = analex.yylex();
	} catch (IOException e) {
		com.jim_project.interprete.util.Error.deESEnAnalizadorLexico(77); //analex.lineaActual());
	}

	return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) {
  	String nombreToken = yyname[token];
	com.jim_project.interprete.util.Error.deTokenNoEsperado(analex.lineaActual(), nombreToken, descripcion);
  	
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

  @Override
  public void yyerror(String descripcion) {
  	com.jim_project.interprete.util.Error.deTokenNoEsperado(analex.lineaActual(), descripcion);
  }
