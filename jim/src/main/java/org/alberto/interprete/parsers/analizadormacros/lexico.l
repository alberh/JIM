
package org.alberto.interprete.parsers.analizadormacros;

import org.alberto.interprete.parsers.AnalizadorLexico;
import org.alberto.interprete.Variable;
import org.alberto.interprete.Etiqueta;
import org.alberto.interprete.Error;

import java.lang.StringBuilder;

%%

%byaccj
%public
%class MacrosLex
%extends AnalizadorLexico

%{

  private MacrosParser yyparser;

  /** constructor del analizador sintáctico
  *** acepta como parámetro el lector de caracteres a usar
  **/

  public MacrosLex(java.io.Reader r, MacrosParser p) {
    this(r);
    yyparser = p;
    _contenido = new StringBuilder();
    _lineaActual = 1;
  }

  private StringBuilder _contenido;
  private int _lineaActual;

  public int lineaActual() {
    return _lineaActual;
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

Reserved = "if"|"IF"|"while"|"WHILE"|"loop"|"LOOP"|"end"|"END"

%state CUERPO

%%

<YYINITIAL> {

{Comment}         { }
"defmacro"        { return MacrosParser.DEFMACRO;
                  }
{Identifier}      { yyparser.yylval = new MacrosParserVal(yytext());
                    _contenido.setLength(0);
                    yybegin(CUERPO);
                    return MacrosParser.IDMACRO;
                  }
{LineTerminator}  { }
{WhiteSpace}      { }
.                 { Error.deCaracterNoReconocido(_lineaActual, yytext());
                    /*yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido");*/
                  }

} // YYINITIAL

<CUERPO> {

"#".*             { }
"goto"|"GOTO"     { _contenido.append(yytext());
                    return MacrosParser.GOTO;
                  }
{Reserved}        { _contenido.append(yytext());
                  }
{Label}           { String id = Etiqueta.filtrar(yytext());
                    yyparser.yylval = new MacrosParserVal("L" + id);
                    _contenido.append("L" + id);
                    return MacrosParser.ETIQUETA;
                  }
{Variable}        { String id = Variable.filtrar(yytext());
                    yyparser.yylval = new MacrosParserVal("V" + id);
                    _contenido.append("V" + id);
                    return MacrosParser.VARIABLE;
                  }
"defmacro"        { Error.deDefinicionInterior(_lineaActual); }
"endmacro"        { yyparser.yylval = new MacrosParserVal(_contenido.toString());
                    yybegin(YYINITIAL);
                    return MacrosParser.ENDMACRO;
                  }
{Identifier}      { String id = yytext();
                    yyparser.yylval = new MacrosParserVal(id);
                    _contenido.append(id);
                    return MacrosParser.IDMACRO;
                  }
[\[\]]            { _contenido.append(yytext());
                    return yycharat(0);
                  }
{InputCharacter}  { _contenido.append(yytext());
                  }
{LineTerminator}  { _contenido.append(yytext());
                  }

} // CUERPO
