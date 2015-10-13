
JAVAC = javac

simple:
	make Main.class

all:
	cd interprete/parsers/previo; make
	cd interprete/parsers/analizadormacros; make
	cd interprete/parsers/lmodel; make
	cd interprete/parsers/loopmodel; make
	cd interprete/parsers/whilemodel; make
	make Main.class
	
cleanall:
	cd interprete/parsers/previo; make clean
	cd interprete/parsers/analizadormacros; make clean
	cd interprete/parsers/lmodel; make clean
	cd interprete/parsers/loopmodel; make clean
	cd interprete/parsers/whilemodel; make clean
	make clean

macros:
	cd interprete/parsers/analizadormacros; make
	make Main.class

cleanmacros:
	make clean
	cd interprete/parsers/analizadormacros; make clean

previo:
	cd interprete/parsers/previo; make
	make Main.class

cleanprevio:
	make clean
	cd interprete/parsers/previo; make clean

l:
	cd interprete/parsers/lmodel; make
	make Main.class

cleanl:
	make clean
	cd interprete/parsers/lmodel; make clean
	make cleandoc

Main.class: Main.java
	rm -f *.class
	$(JAVAC) Main.java

javadoc:
	mkdir -p doc
	javadoc -d doc *.java -subpackages interprete

clean:
	rm -f *~ *.class interprete/*.class interprete/parsers/*.class

cleandoc:
	rm -rf doc
