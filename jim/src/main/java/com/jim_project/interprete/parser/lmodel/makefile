JFLEX  = jflex 
BYACCJ = ../../../../../byaccj/yacc.linux -Jclass=LParser -Jpackage=com.jim_project.interprete.parser.lmodel -Jextends=Parser -Jnoconstruct #-Jnodebug
JAVAC = javac -d /home/alberto/Programación/tfg-int-rpretes/JIM/build/classes/main -classpath /home/alberto/Programación/tfg-int-rpretes/JIM/src/main/java/org

all: LLex.java LParser.java LParserVal.java

LLex.java: lexico.l
	$(JFLEX) lexico.l

LParser.java LParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ LParser.java >tmp.java
	mv tmp.java LParser.java

clean:
	rm -f *~ *.class LLex.java LParser.java LParserVal.java
