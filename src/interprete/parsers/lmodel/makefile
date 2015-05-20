JFLEX  = jflex 
BYACCJ = ../../../byaccj/yacc.linux -Jclass=LParser -Jpackage=interprete.parsers.lmodel
JAVAC  = javac

#.SUFFIXES:
#.PHONY: ejecuta

#ejecuta: Main.class entrada.txt
#	java Main entrada.txt

Main.class: LLex.java LParser.java LParserVal.java LAcciones.java
	rm -f *.class
	$(JAVAC) Main.java

LLex.java: lexico.l
	$(JFLEX) lexico.l

LParser.java LParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ LParser.java >tmp.java
	mv tmp.java LParser.java

clean:
	rm -f *~ *.class LLex.java LParser.java LParserVal.java
