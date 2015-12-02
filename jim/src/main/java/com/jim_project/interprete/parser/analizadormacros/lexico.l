
package com.jim_project.interprete.parser.analizadormacros;

import com.jim_project.interprete.parser.AnalizadorLexico;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.componente.Etiqueta;
// Debido a conflicto con la clase Error del propio analizador,
// se hace referencia a la clase Error especificando toda la jerarquía
// de paquetes, en lugar de añadir el sigiente import.
// import com.jim_project.interprete.Error;

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
{LineTerminator}  { _lineaActual++; }
{WhiteSpace}      { }
.                 { yyparser.programa().error().deCaracterNoReconocido(_lineaActual, yytext());
                  }

} // YYINITIAL

<CUERPO> {
"#".*             { }
"goto"|"GOTO"     { _contenido.append(yytext());
                    return MacrosParser.GOTO;
                  }
{Reserved}        { _contenido.append(yytext());
                  }
{Label}           { String id = yytext();
                    yyparser.yylval = new MacrosParserVal(id);
                    _contenido.append(id);
                    return MacrosParser.ETIQUETA;
                  }
{Variable}        { String id = yytext();
                    yyparser.yylval = new MacrosParserVal(id);
                    _contenido.append(id);
                    return MacrosParser.VARIABLE;
                  }
"defmacro"        { yyparser.programa().error().deDefinicionInterior(_lineaActual); }
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
                    _lineaActual++;
                  }

} // CUERPO
