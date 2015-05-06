
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
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*

Label = [a-eA-E][0-9]?
InputVariable = [xX][0-9]+
LocalVariable = [zZ][0-9]+
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}

Reserved = "if"|"IF"|"goto"|"GOTO"|"while"|"WHILE"|"loop"|"LOOP"|"end"|"END"

%%

"#".*             { }

{Reserved}        { }

"["               { yyparser.yylval = new PrevioParserVal( yytext().charAt(0) );
                    return PrevioParser.ABRE_CORCHETES;
                  }

"]"               { yyparser.yylval = new PrevioParserVal( yytext().charAt(0) );
                    return PrevioParser.CIERRA_CORCHETES;
                  }

{Label}           { String id = Etiqueta.filtrar("L" + yytext());
                    yyparser.yylval = new PrevioParserVal( (Object)id );
                    contenido += id;
                    return PrevioParser.ETIQUETA;
                  }

{Variable}        { String id = Variable.filtrar("V" + yytext());
                    yyparser.yylval = new PrevioParserVal( (Object)id );
                    contenido += id;
                    return PrevioParser.VARIABLE;
                  }

{LineTerminator}  { linea_actual++;
                  }

{WhiteSpace}      { }

.                 { /*yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido");*/
                  }
