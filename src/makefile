
JAVAC = javac

all:
	cd interprete/parsers/previo; make
	cd interprete/parsers/analizadormacros; make
	#cd interprete/parsers/lmodel; make
	#cd interprete/parsers/loopmodel; make
	#cd interprete/parsers/whilemodel; make
	make Main.class
	
cleanall:
	make clean
	cd interprete/parsers/previo; make clean
	cd interprete/parsers/analizadormacros; make clean
	cd interprete/parsers/lmodel; make clean
	cd interprete/parsers/loopmodel; make clean
	cd interprete/parsers/whilemodel; make clean

Main.class: Main.java
	rm -f *.class
	$(JAVAC) Main.java

clean:
	rm -f *~ *.class interprete/*.class
