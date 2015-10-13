JFLEX  = jflex 
BYACCJ = ../../../../../byaccj/yacc.linux -Jclass=WhileParser -Jpackage=org.alberto.interprete.parsers.whilemodel -Jimplements=IParser #-Jnodebug
JAVAC = javac -d /home/alberto/Programación/tfg-int-rpretes/JIM/build/classes/main -classpath /home/alberto/Programación/tfg-int-rpretes/JIM/src/main/java/org

all: WhileLex.java WhileParser.java WhileParserVal.java
	# rm -f *.class
	# $(JAVAC) *.java

WhileLex.java: lexico.l
	$(JFLEX) lexico.l

WhileParser.java WhileParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ WhileParser.java >tmp.java
	mv tmp.java WhileParser.java

clean:
	rm -f *~ *.class WhileLex.java WhileParser.java WhileParserVal.java
