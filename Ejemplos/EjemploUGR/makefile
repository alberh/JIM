JFLEX  = jflex 
BYACCJ = ../../byaccj/yacc.linux -J 
JAVAC  = javac

.SUFFIXES:
.PHONY: ejecuta

ejecuta: Main.class entrada.txt
	java Main entrada.txt

Main.class: Yylex.java Parser.java ParserVal.java Main.java Acc.java
	rm -f *.class
	$(JAVAC) Main.java

Yylex.java: prueba.l
	$(JFLEX) prueba.l

Parser.java ParserVal.java: prueba.y
	$(BYACCJ) prueba.y
	sed s/'yyerror("syntax error")'/'yyerror("error sintactico",yystate,yychar)'/ Parser.java >tmp.java
	mv tmp.java Parser.java


limpia:
	rm -f *~ *.class Yylex.java Parser.java ParserVal.java

SRCS = prueba.l prueba.y entrada.txt Acc.java Main.java makefile

lyj.tgz: $(SRCS)
	tar czvf lyj.tgz $(SRCS)
