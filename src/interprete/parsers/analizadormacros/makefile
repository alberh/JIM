JFLEX  = jflex 
BYACCJ = ../../../byaccj/yacc.linux -Jclass=MacrosParser -Jpackage=interprete.parsers.analizadormacros -Jimplements=IParser
JAVAC  = javac

#.SUFFIXES:
#.PHONY: ejecuta

#ejecuta: Main.class entrada.txt
#	java Main entrada.txt

Main.class: MacrosLex.java MacrosParser.java MacrosParserVal.java Main.java MacrosAcciones.java
	rm -f *.class
	$(JAVAC) Main.java

MacrosLex.java: lexico.l
	$(JFLEX) lexico.l

MacrosParser.java MacrosParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ MacrosParser.java >tmp.java
	mv tmp.java MacrosParser.java

clean:
	rm -f *~ *.class MacrosLex.java MacrosParser.java MacrosParserVal.java
