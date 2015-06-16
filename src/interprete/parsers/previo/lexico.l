
package interprete.parsers.previo;

import interprete.parsers.AnalizadorLexico;

%%

%byaccj
%public
%class PrevioLex
%extends AnalizadorLexico

%{

  private PrevioParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public PrevioLex(java.io.Reader r, PrevioParser p) 
  {
    this(r);
    yyparser = p;
    contenido = "";
    _lineaActual = 1;
  }

  protected int _lineaActual;

  public int lineaActual() {

    return _lineaActual;
  }
/*
  public void lineaActual(int nuevaLinea) {

    _lineaActual = nuevaLinea;
  }
*/
  private String contenido;
  

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
{Comment}         { _lineaActual++;
                  }
{Label}           { yyparser.yylval = new PrevioParserVal( yytext() );
                    return PrevioParser.ETIQUETA;
                  }
{Variable}        { yyparser.yylval = new PrevioParserVal( yytext() );
                    return PrevioParser.VARIABLE;
                  }
"<-"              { return PrevioParser.FLECHA;
                  }
"++"              { return PrevioParser.INCREMENTO;
                  }
"--"              { return PrevioParser.DECREMENTO;
                  }
"!="{WhiteSpace}*"0"
                  { return PrevioParser.DISTINTO;
                  }
[-+*/%,\[\]()]    { return yycharat(0);
                  }
{Number}          { yyparser.yylval = new PrevioParserVal( yytext() );
                    return PrevioParser.NUMERO;
                  }
"if"|"IF"         { return PrevioParser.IF;
                  }
"goto"|"GOTO"     { return PrevioParser.GOTO;
                  }
"while"|"WHILE"   { return PrevioParser.WHILE;
                  }
"loop"|"LOOP"     { return PrevioParser.LOOP;
                  }
"end"|"END"       { return PrevioParser.END;
                  }
{Identifier}      { yyparser.yylval = new PrevioParserVal( yytext() );
                    return PrevioParser.IDMACRO;
                  }
{LineTerminator}  { _lineaActual++;
                  }
.                 { yyparser.yyerror("Error: Carácter '"+yytext()+"' no reconocido en línea " + _lineaActual + ".");
                  }
