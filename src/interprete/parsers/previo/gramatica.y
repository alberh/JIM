
%{
	import java.io.*;
%}


// lista de tokens por orden de prioridad

%token VARIABLE
%token ETIQUETA
%token ABRE_CORCHETES
%token CIERRA_CORCHETES

%%

inicio :  sentencia inicio
	   |
;
sentencia :	VARIABLE { Variable.set($1.obj.toString()); } sentencia
		  |	ABRE_CORCHETES ETIQUETA CIERRA_CORCHETES { Etiqueta.set($2.obj.toString(), analex.lineaActual()); } sentencia
;

%%

	private Macro macro;

	/** referencia al analizador léxico
  **/
	private PrevioLex analex;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public PrevioParser(Reader r) 
  {
	analex = new PrevioLex(r, this);
	 //yydebug = true;
  }

  /** esta función se invoca por el analizador cuando necesita el 
  *** siguiente token del analizador léxico
  **/
  private int yylex () 
  {
	int yyl_return = -1;

	try 
	{
		yylval = new PrevioParserVal(0);
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





