
package interprete.parsers.loopmodel;

%%

%byaccj
%class LoopLex

%{

  private LoopParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public LoopLex(java.io.Reader r, LoopParser p) 
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
InputVariable = [xX][0-9]+
LocalVariable = [zZ][0-9]+
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}
Number = 0|[1-9]+[0-9]*

%%
{WhiteSpace}      { }
{Comment}         { }
{Variable}        { yyparser.yylval = new LoopParserVal( yytext() );
                    return LoopParser.VARIABLE;
                  }
"loop"|"LOOP"     { return LoopParser.LOOP;
                  }
"end"|"END"       { return LoopParser.END;
                  }
"<-"              { return LoopParser.FLECHA;
                  }
"++"              { return LoopParser.INCREMENTO;
                  }
[+*,()]           { return yycharat(0);
                  }
{Number}          { yyparser.yylval = new LoopParserVal( Integer.parseInt(yytext()) );
                    return LoopParser.NUMERO;
                  }
{Identifier}      { yyparser.yylval = new LoopParserVal( yytext() );
                    return LoopParser.IDMACRO;
                  }
{LineTerminator}  { linea_actual++;
                  }
.                 { yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido"); 
                  }
