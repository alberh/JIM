JFLEX  = jflex 
BYACCJ = ../../../byaccj/yacc.linux -Jclass=WhileParser -Jpackage=interprete.parsers.whilemodel -Jimplements=IParser #-Jnodebug
JAVAC  = javac

#.SUFFIXES:
#.PHONY: ejecuta

#ejecuta: Main.class entrada.txt
#	java Main entrada.txt

Main.class: WhileLex.java WhileParser.java WhileParserVal.java WhileAcciones.java
	rm -f *.class
	$(JAVAC) Main.java

WhileLex.java: lexico.l
	$(JFLEX) lexico.l

WhileParser.java WhileParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ WhileParser.java >tmp.java
	mv tmp.java WhileParser.java

clean:
	rm -f *~ *.class WhileLex.java WhileParser.java WhileParserVal.java
