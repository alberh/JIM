// fuente 'jflex' para

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
    linea_actual = 1;
    yyparser = p;
    contenido = "";
  }

  /** guarda el número de linea actual
  **/

  private int linea_actual;

  /** devuelve el numero de linea donde está el último caracter leido
  **/

  public int lineaActual()
  {
     return linea_actual;
  }


  private String contenido;
  

%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*

/*
Comment = "#" {InputCharacter}* {LineTerminator}?
Label = [lL][0-9]+
InputVariable = [xX][0-9]+
LocalVariable = [zZ][0-9]+
OutputVariable = "Y"
ReservedWords = "<-" | "IF" | "!=" | "GOTO" | "LOOP" | "END" | "WHILE" | "++" | "--"
*/


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
