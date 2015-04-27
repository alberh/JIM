
//package Interprete.Parsers.Previo;

%%

%byaccj
%class PrevioLex

%{

  private PrevioParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public PrevioLex(java.io.Reader r, PrevioParser p) 
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

Label = [a-eA-E][0-9]?
InputVariable = [xX][0-9]+
LocalVariable = [zZ][0-9]+
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}


%state CUERPO

%%

<YYINITIAL> {

"defmacro"        { return PrevioParser.DEFMACRO;
                  }

{Identifier}      { yyparser.yylval = new PrevioParserVal( (Object)yytext() );
                    yybegin(CUERPO);
                    return PrevioParser.IDMACRO;
                  }

{LineTerminator}  { linea_actual++;
                  }

{WhiteSpace}      { }

.                 { yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido"); 
                  }

} // YYINITIAL

<CUERPO> {

{Label}           { yyparser.yylval = new PrevioParserVal( (Object)("L" + yytext()) );
                    contenido += "L" + yytext();
                    return PrevioParser.ETIQUETA;
                  }

{Variable}        { yyparser.yylval = new PrevioParserVal( (Object)("V" + yytext()) );
                    contenido += "V" + yytext();
                    return PrevioParser.VARIABLE;
                  }

"endmacro"        { yyparser.yylval = new PrevioParserVal( (Object)contenido );
                    contenido = "";
                    yybegin(YYINITIAL);
                    return PrevioParser.ENDMACRO;
                  }

{InputCharacter}
                  { contenido += ( yytext().charAt(0) );
                  }

{LineTerminator}  { contenido += ( yytext().charAt(0) );
                    linea_actual++;
                  }

} // CUERPO
