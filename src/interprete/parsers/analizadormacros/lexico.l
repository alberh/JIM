
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
Identifier = [a-zA-Z]{2} [a-zA-Z]*

Comment = "#" {InputCharacter}* {LineTerminator}?
Label = [a-eA-E]([1-9]+[0-9]*)?
InputVariable = [xX]([1-9]+[0-9]*)?
LocalVariable = [zZ]([1-9]+[0-9]*)?
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}

Reserved = "if"|"IF"|"goto"|"GOTO"|"while"|"WHILE"|"loop"|"LOOP"|"end"|"END"

%state CUERPO

%%

<YYINITIAL> {

{Comment}         { }

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

"#".*             { linea_actual++;
                  }

{Reserved}        { contenido += ( yytext() );
                  }

{Label}           { String id = Etiqueta.filtrar(yytext());
                    yyparser.yylval = new MacrosParserVal( (Object)("L" + id) );
                    contenido += "L" + id;
                    return MacrosParser.ETIQUETA;
                  }

{Variable}        { String id = Variable.filtrar(yytext());
                    yyparser.yylval = new MacrosParserVal( (Object)("V" + id) );
                    contenido += "V" + id;
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
