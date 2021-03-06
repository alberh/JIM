package com.jim_project.interprete.parser.lmodel;

import com.jim_project.interprete.parser.AnalizadorLexico;

%%

%byaccj
%public
%class LLex
%extends AnalizadorLexico

%{
  private LParser yyparser;

  /**
   * Constructor de clase.
   * @param r Referencia al lector de entrada.
   * @param p Referencia al analizador sintáctico.
   */
  public LLex(java.io.Reader r, LParser p) {
    this(r);
    yyparser = p;
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
{Comment}         { }
{Label}           { yyparser.yylval = new LParserVal(yytext());
                    return LParser.ETIQUETA;
                  }
{Variable}        { yyparser.yylval = new LParserVal(yytext());
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
{Number}          { yyparser.yylval = new LParserVal(Integer.parseInt(yytext()));
                    return LParser.NUMERO;
                  }
"if"|"IF"         { return LParser.IF;
                  }
"goto"|"GOTO"     { return LParser.GOTO;
                  }
{Identifier}      { yyparser.yylval = new LParserVal(yytext());
                    return LParser.IDMACRO;
                  }
{LineTerminator}  { }
.                 { yyparser.programa().error().deCaracterNoReconocido(yytext());
                  }
