package com.jim_project.interprete.parser.loopmodel;

import com.jim_project.interprete.parser.AnalizadorLexico;

%%

%byaccj
%public
%class LoopLex
%extends AnalizadorLexico

%{
  private LoopParser yyparser;

  /**
   * Constructor de clase.
   * @param r Referencia al lector de entrada.
   * @param p Referencia al analizador sintáctico.
   */
  public LoopLex(java.io.Reader r, LoopParser p) {
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
{Variable}        { yyparser.yylval = new LoopParserVal(yytext());
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
{Number}          { yyparser.yylval = new LoopParserVal(Integer.parseInt(yytext()));
                    return LoopParser.NUMERO;
                  }
{Identifier}      { yyparser.yylval = new LoopParserVal(yytext());
                    return LoopParser.IDMACRO;
                  }
{LineTerminator}  { }
.                 { yyparser.programa().error().deCaracterNoReconocido(yytext());
                  }
