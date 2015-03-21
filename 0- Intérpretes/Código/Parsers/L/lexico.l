// fuente 'jflex' para

%%

%byaccj
%class LLex

%{

  private LParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public LLex(java.io.Reader r, LParser p) 
  {
    this(r);
    linea_actual = 1;
    yyparser = p;
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
  

%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*

Comment = "#" {InputCharacter}* {LineTerminator}?
Label = [lL][0-9]+
InputVariable = [xX][0-9]+
LocalVariable = [zZ][0-9]+
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}
Number = 0|[1-9]+[0-9]*

%%
{WhiteSpace}      { }
{Comment}         { }
{Label}           { yyparser.yylval = new LParserVal( yytext() ); // en las acciones, hashtable n etiqueta a n linea
                    return LParser.ETIQUETA;
                  }
{Variable}        { yyparser.yylval = new LParserVal( yytext() );
                    return LParser.VARIABLE;
                  }
"<-"              { return LParser.FLECHA;
                  }
"++"              { return LParser.INCREMENTO;
                  }
"--"              { return LParser.DECREMENTO;
                  }
"if"|"IF"         { return LParser.IF;
                  }
"!="{WhiteSpace}?"0"
                  { return LParser.DISTINTO;
                  }
"goto"|"GOTO"     { return LParser.GOTO;
                  }
[-+*/%,\[\]()]    { return yycharat(0);
                  }
{Number}          { yyparser.yylval = new LParserVal( Integer.parseInt(yytext()) );
                    return LParser.NUMERO;
                  }
{Identifier}      { yyparser.yylval = new LParserVal( yytext() );
                    return LParser.IDMACRO;
                  }
{LineTerminator}  { linea_actual++;
                  }
.                 { yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido"); 
                  }
