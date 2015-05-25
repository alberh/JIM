
%{
	import java.io.*;
	import interprete.parsers.IParser;
%}


// lista de tokens por orden de prioridad

%token DEFMACRO   // comienzo de la declaración de una macro
%token IDMACRO    // identificador de la macro
%token VARIABLE
%token ETIQUETA
%token ENDMACRO   // fin de la declaración de una macro

%%

inicio :  sentencia inicio
	   |
;
sentencia : DEFMACRO IDMACRO { macro = Macro.set($2.obj.toString()); } simbolos ENDMACRO { macro.cuerpo($5.obj.toString()); }
;
simbolos :	VARIABLE simbolos { macro.nuevaVariable($1.obj.toString()); }
		 |	ETIQUETA simbolos { macro.nuevaEtiqueta($1.obj.toString()); }
		 |
;

%%

	private Macro macro;

	/** referencia al analizador léxico
  **/
	private MacrosLex analex;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public MacrosParser(Reader r) 
  {
	analex = new MacrosLex(r, this);
	 //yydebug = true;
  }

  public int parse() {
		
	return this.yyparse();
  }

  /** esta función se invoca por el analizador cuando necesita el 
  *** siguiente token del analizador léxico
  **/
  private int yylex () 
  {
	int yyl_return = -1;

	try 
	{
		yylval = new MacrosParserVal(0);
		yyl_return = analex.yylex();
	}
	catch (IOException e) 
	{
		System.err.println("error de E/S:"+e);
	}

	return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) 
  {
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
	
  }

  public void yyerror (String descripcion) 
  {
	System.err.println ("Error en línea "+Integer.toString(analex.lineaActual())+" : "+descripcion);
	 //System.err.println ("Token leido : "+yyname[token]);
	
  }





