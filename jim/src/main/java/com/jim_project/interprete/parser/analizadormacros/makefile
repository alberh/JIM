JFLEX  = jflex 
BYACCJ = ../../../../../byaccj/yacc.linux -Jclass=MacrosParser -Jpackage=com.jim_project.interprete.parser.analizadormacros -Jextends=Parser -Jnoconstruct #-Jnodebug
JAVAC = javac -d /home/alberto/Programación/tfg-int-rpretes/JIM/build/classes/main -classpath /home/alberto/Programación/tfg-int-rpretes/JIM/src/main/java

all: MacrosLex.java MacrosParser.java MacrosParserVal.java

MacrosLex.java: lexico.l
	$(JFLEX) lexico.l

MacrosParser.java MacrosParserVal.java: gramatica.y
	$(BYACCJ) gramatica.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ MacrosParser.java >tmp.java
	mv tmp.java MacrosParser.java

clean:
	rm -f *~ *.class MacrosLex.java MacrosParser.java MacrosParserVal.java
