JFLEX  = jflex 
BYACCJ = ../../../byaccj/yacc.linux -Jclass=PrevioParser -Jpackage=interprete.parsers.previo -Jimplements=IParser
JAVAC  = javac

#.SUFFIXES:
#.PHONY: ejecuta

#ejecuta: Main.class entrada.txt
#	java Main entrada.txt

Main.class: PrevioLex.java PrevioParser.java PrevioParserVal.java PrevioAcciones.java
	rm -f *.class
	$(JAVAC) Main.java

PrevioLex.java: lexico.l
	$(JFLEX) lexico.l

PrevioParser.java PrevioParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ PrevioParser.java >tmp.java
	mv tmp.java PrevioParser.java


clean:
	rm -f *~ *.class PrevioLex.java PrevioParser.java PrevioParserVal.java
