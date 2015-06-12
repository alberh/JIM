
package interprete.parsers.analizadormacros;

import interprete.parsers.AnalizadorLexico;
import interprete.Variable;
import interprete.Etiqueta;

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

  public MacrosLex(java.io.Reader r, MacrosParser p) 
  {
    this(r);
    yyparser = p;
    _contenido = new StringBuilder();
  }

  private StringBuilder _contenido;

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

Reserved = "if"|"IF"|"goto"|"GOTO"|"while"|"WHILE"|"loop"|"LOOP"|"end"|"END"

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
.                 { /*yyparser.yyerror("el(los) carácter(es) '"+yytext()+"' no forma(n) ningún token conocido");*/
                  }

} // YYINITIAL

<CUERPO> {

"#".*             { }
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
"endmacro"        { yyparser.yylval = new MacrosParserVal(_contenido.toString());
                    yybegin(YYINITIAL);
                    return MacrosParser.ENDMACRO;
                  }
{InputCharacter}  { _contenido.append(yytext());
                  }
{LineTerminator}  { _contenido.append(yytext());
                  }

} // CUERPO
