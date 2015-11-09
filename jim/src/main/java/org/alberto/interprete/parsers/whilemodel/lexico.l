
package org.alberto.interprete.parsers.whilemodel;

import org.alberto.interprete.parsers.AnalizadorLexico;

%%

%byaccj
%public
%class WhileLex
%extends AnalizadorLexico

%{

  private WhileParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public WhileLex(java.io.Reader r, WhileParser p) {
    this(r);
    yyparser = p;
  }
  

%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = [ \t\f]

Identifier = [a-zA-Z]{2} [a-zA-Z]*

Comment = "#" {InputCharacter}* {LineTerminator}?
InputVariable = [xX]([1-9]+[0-9]*)?
LocalVariable = [zZ]([1-9]+[0-9]*)?
OutputVariable = [yY]
Variable = {InputVariable}|{LocalVariable}|{OutputVariable}
Number = 0|[1-9]+[0-9]*

%%
{WhiteSpace}      { }
{Comment}         { }
{Variable}        { yyparser.yylval = new WhileParserVal(yytext());
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
{Number}          { yyparser.yylval = new WhileParserVal(Integer.parseInt(yytext()));
                    return WhileParser.NUMERO;
                  }
{Identifier}      { yyparser.yylval = new WhileParserVal(yytext());
                    return WhileParser.IDMACRO;
                  }
{LineTerminator}  { }
.                 { org.alberto.interprete.util.Error.deCaracterNoReconocido(yytext());
                  }
