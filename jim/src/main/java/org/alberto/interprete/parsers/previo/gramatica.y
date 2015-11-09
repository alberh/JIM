
%{
	import java.io.*;
	import org.alberto.interprete.*;
	import org.alberto.interprete.util.*;
        import org.alberto.interprete.componente.*;
	import org.alberto.interprete.parsers.*;
%}


// lista de tokens por orden de prioridad

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
%token WHILE
%token LOOP
%token END

%token <sval> ETIQUETA VARIABLE IDMACRO
%token <sval> NUMERO

%type <sval> operacion
%type <sval> inicio sentencia etiqueta finInstruccion operando parametros parametrosMacro masParametrosMacro

%%

inicio : sentencia { $$ = $1; } inicio
       | { $$ = null; }
;
sentencia : etiqueta instruccion { ; }
;
etiqueta :  '[' ETIQUETA ']' { Etiqueta.set($2, Programa.numeroLineaActual()); }
         | { ; }
;
instruccion : VARIABLE { PrevioAcciones.definirVariableYMantener($1); } FLECHA finInstruccion
            | VARIABLE INCREMENTO { PrevioAcciones.definirVariable($1); }
            | VARIABLE DECREMENTO { PrevioAcciones.definirVariable($1); }
            | IF VARIABLE DISTINTO GOTO ETIQUETA { PrevioAcciones.definirVariable($2); }
            | GOTO ETIQUETA { ; }
            | LOOP VARIABLE { PrevioAcciones.definirVariable($2); Bucle.abrir(Programa.numeroLineaActual()); }
            | WHILE VARIABLE DISTINTO { PrevioAcciones.definirVariable($2); Bucle.abrir(Programa.numeroLineaActual()); }
            | END { Bucle.cerrar(Programa.numeroLineaActual()); }
;
finInstruccion :  VARIABLE { PrevioAcciones.definirVariable($1); }
               |  NUMERO { ; }
               |  operacion { ; }
               |  IDMACRO { PrevioAcciones.prepararParaExpandir($1); } '(' parametrosMacro ')'
;
operacion	   :  operando '+' operando { ; }
			   |  operando '-' operando { ; }
			   |  operando '*' operando { ; }
			   |  operando '/' operando { ; }
			   |  operando '%' operando { ; }
;
operando :  NUMERO { ; }
           |  VARIABLE { PrevioAcciones.definirVariable($1); }
;
parametros :  NUMERO { PrevioAcciones.prepararVariableEntrada($1); }
           	|  VARIABLE { PrevioAcciones.definirVariable($1); PrevioAcciones.prepararVariableEntrada($1); }
;
parametrosMacro : parametros masParametrosMacro { ; }
                | { ; }
;
masParametrosMacro :  ',' parametros masParametrosMacro { ; }
                   | { ; }
;

%%

	/** referencia al analizador léxico
  **/
	private PrevioLex analex;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public PrevioParser(Reader r) {
	analex = new PrevioLex(r, this);
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
		yylval = new PrevioParserVal(0);
		yyl_return = analex.yylex();
	} catch (IOException e) {
		org.alberto.interprete.util.Error.deESEnAnalizadorLexico();
	}

	return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) {
  	String nombreToken = yyname[token];
  	org.alberto.interprete.util.Error.deTokenNoEsperado(nombreToken, descripcion);
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
