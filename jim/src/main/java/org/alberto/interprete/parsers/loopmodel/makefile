JFLEX  = jflex 
BYACCJ = ../../../../../byaccj/yacc.linux -Jclass=LoopParser -Jpackage=org.alberto.interprete.parsers.loopmodel -Jimplements=IParser #-Jnodebug
JAVAC = javac -d /home/alberto/Programación/tfg-int-rpretes/JIM/build/classes/main -classpath /home/alberto/Programación/tfg-int-rpretes/JIM/src/main/java/org

all: LoopLex.java LoopParser.java LoopParserVal.java
	# rm -f *.class
	# $(JAVAC) *.java

LoopLex.java: lexico.l
	$(JFLEX) lexico.l

LoopParser.java LoopParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ LoopParser.java >tmp.java
	mv tmp.java LoopParser.java

clean:
	rm -f *~ *.class LoopLex.java LoopParser.java LoopParserVal.java
