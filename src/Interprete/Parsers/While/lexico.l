
package Interprete.Parsers.While;

%%

%byaccj
%class WhileLex

%{

  private WhileParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public WhileLex(java.io.Reader r, WhileParser p) 
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
{Variable}        { yyparser.yylval = new WhileParserVal( yytext() );
                    return WhileParser.VARIABLE;
                  }
"while"|"WHILE"   { return WhileParser.WHILE;
                  }
"!="{WhiteSpace}*"0"
                  { return WhileParser.DISTINTO;
                  }
"end"|"END"       { return WhileParser.END;
                  }
"<-"              { return WhileParser.FLECHA;
                  }
"++"              { return WhileParser.INCREMENTO;
                  }
"--"              { return WhileParser.DECREMENTO;
                  }
[-+*/%,()]        { return yycharat(0);
                  }
{Number}          { yyparser.yylval = new WhileParserVal( Integer.parseInt(yytext()) );
                    return WhileParser.NUMERO;
                  }
{Identifier}      { yyparser.yylval = new WhileParserVal( yytext() );
                    return WhileParser.IDMACRO;
                  }
{LineTerminator}  { linea_actual++;
                  }
.                 { yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido"); 
                  }
