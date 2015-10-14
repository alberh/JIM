JFLEX  = jflex 
BYACCJ = ../../../../../byaccj/yacc.linux -Jclass=PrevioParser -Jpackage=org.alberto.interprete.parsers.previo -Jextends=Parser
JAVAC = javac -d /home/alberto/Programación/tfg-int-rpretes/JIM/build/classes/main -classpath /home/alberto/Programación/tfg-int-rpretes/JIM/src/main/java/org

all: PrevioLex.java PrevioParser.java PrevioParserVal.java

PrevioLex.java: lexico.l
	$(JFLEX) lexico.l

PrevioParser.java PrevioParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ PrevioParser.java >tmp.java
	mv tmp.java PrevioParser.java

clean:
	rm -f *~ *.class PrevioLex.java PrevioParser.java PrevioParserVal.java
