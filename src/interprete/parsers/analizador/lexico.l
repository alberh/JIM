
//package Interprete.Parsers.AnalizadorMacros;

%%

%byaccj
%class MacrosLex

%{

  private MacrosParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public MacrosLex(java.io.Reader r, MacrosParser p) 
  {
    this(r);
    numero_variables = 0;
    linea_actual = 1;
    yyparser = p;
    contenido = "";
  }

  private int numero_variables;
  private int numero_etiquetas;
  private int linea_actual;

  public int numVariables() {
    return numero_variables;
  }

  public int numEtiquetas() {
    return numero_etiquetas;
  }

  public int lineaActual() {
     return linea_actual;
  }


  private String contenido;
  

%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*

Label = [a-eA-E]([1-9]+[0-9]*)?
InputVariable = [xX]([1-9]+[0-9]*)?
LocalVariable = [zZ]([1-9]+[0-9]*)?
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}

Reserved = "if"|"IF"|"goto"|"GOTO"|"while"|"WHILE"|"loop"|"LOOP"|"end"|"END"

%state CUERPO

%%

<YYINITIAL> {

"defmacro"        { return MacrosParser.DEFMACRO;
                  }

{Identifier}      { yyparser.yylval = new MacrosParserVal( (Object)yytext() );
                    yybegin(CUERPO);
                    return MacrosParser.IDMACRO;
                  }

{LineTerminator}  { linea_actual++;
                  }

{WhiteSpace}      { }

.                 { yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido"); 
                  }

} // YYINITIAL

<CUERPO> {

{Reserved}        { contenido += ( yytext() );
                  }

{Label}           { String id = Etiqueta.filtrar("L" + yytext());
                    yyparser.yylval = new MacrosParserVal( (Object)id );
                    contenido += id;
                    return MacrosParser.ETIQUETA;
                  }

{Variable}        { String id = Variable.filtrar("V" + yytext());
                    yyparser.yylval = new MacrosParserVal( (Object)id );
                    contenido += id;
                    return MacrosParser.VARIABLE;
                  }

"endmacro"        { yyparser.yylval = new MacrosParserVal( (Object)contenido );
                    contenido = "";
                    yybegin(YYINITIAL);
                    return MacrosParser.ENDMACRO;
                  }

{InputCharacter}
                  { contenido += ( yytext().charAt(0) );
                  }

{LineTerminator}  { contenido += ( yytext().charAt(0) );
                    linea_actual++;
                  }

} // CUERPO
