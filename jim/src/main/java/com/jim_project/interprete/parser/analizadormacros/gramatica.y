
%{
	import java.io.*;
  	import com.jim_project.interprete.*;
        import com.jim_project.interprete.componente.*;
  	import com.jim_project.interprete.parser.*;
        import com.jim_project.interprete.util.ControladorEjecucion;
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
sentencia : DEFMACRO IDMACRO { _acciones.nuevaMacro($2); } simbolos ENDMACRO { _acciones.cuerpo($5); }
;
simbolos :  VARIABLE { _acciones.nuevaVariable($1); } simbolos
         |  IDMACRO { _acciones.nuevaLlamadaAMacro($1); } simbolos
         |  '[' ETIQUETA ']' { _acciones.nuevaEtiqueta($2); } simbolos
	 |  GOTO ETIQUETA { _acciones.nuevaEtiquetaSalto($2); } simbolos
	 |
;

%%

  public MacrosParser(Reader r, Programa programa) {
    super(null);
    _analizadorLexico = new MacrosLex(r, this);
    _acciones = new MacrosAcciones(programa);
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
		yylval = new MacrosParserVal(0);
		yyl_return = _analizadorLexico.yylex();
	} catch (IOException e) {
		com.jim_project.interprete.util.Error.deESEnAnalizadorLexico(77); //_analizadorLexico.lineaActual());
	}

	return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) {
  	String nombreToken = yyname[token];
	com.jim_project.interprete.util.Error.deTokenNoEsperado(_analizadorLexico.lineaActual(), nombreToken, descripcion);
  	
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

  @Override
  public void yyerror(String descripcion) {
  	com.jim_project.interprete.util.Error.deTokenNoEsperado(_analizadorLexico.lineaActual(), descripcion);
  }
