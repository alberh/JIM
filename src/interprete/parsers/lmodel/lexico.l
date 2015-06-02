
package interprete.parsers.lmodel;

import interprete.parsers.AnalizadorLexico;

%%

%byaccj
%public
%class LLex
%extends AnalizadorLexico

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

Identifier = [a-zA-Z]{2} [a-zA-Z]*

Comment = "#" {InputCharacter}* {LineTerminator}?
Label = [a-eA-E]([1-9]+[0-9]*)?
InputVariable = [xX]([1-9]+[0-9]*)?
LocalVariable = [zZ]([1-9]+[0-9]*)?
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}
Number = 0|[1-9]+[0-9]*

%%
{WhiteSpace}      { }
{Comment}         { linea_actual++;
                  }
{Label}           { yyparser.yylval = new LParserVal( yytext() );
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
"!="{WhiteSpace}*"0"
                  { return LParser.DISTINTO;
                  }
[-+*/%,\[\]()]    { return yycharat(0);
                  }
{Number}          { yyparser.yylval = new LParserVal( Integer.parseInt(yytext()) );
                    return LParser.NUMERO;
                  }
"if"|"IF"         { return LParser.IF;
                  }
"goto"|"GOTO"     { return LParser.GOTO;
                  }
{Identifier}      { yyparser.yylval = new LParserVal( yytext() );
                    return LParser.IDMACRO;
                  }
{LineTerminator}  { linea_actual++;
                  }
.                 { yyparser.yyerror("Error: Carácter '"+yytext()+"' no reconocido en línea " + linea_actual + ".");
                  }
