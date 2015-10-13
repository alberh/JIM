JFLEX  = jflex 
BYACCJ = ../../../byaccj/yacc.linux -Jclass=LoopParser -Jpackage=interprete.parsers.loopmodel -Jimplements=IParser #-Jnodebug
JAVAC  = javac

#.SUFFIXES:
#.PHONY: ejecuta

#ejecuta: Main.class entrada.txt
#	java Main entrada.txt

Main.class: LoopLex.java LoopParser.java LoopParserVal.java LoopAcciones.java
	rm -f *.class
	$(JAVAC) Main.java

LoopLex.java: lexico.l
	$(JFLEX) lexico.l

LoopParser.java LoopParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ LoopParser.java >tmp.java
	mv tmp.java LoopParser.java

clean:
	rm -f *~ *.class LoopLex.java LoopParser.java LoopParserVal.java
