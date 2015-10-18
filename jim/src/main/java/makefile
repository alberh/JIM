
all:
	cd org/alberto/interprete/parsers/previo; make
	cd org/alberto/interprete/parsers/analizadormacros; make
	cd org/alberto/interprete/parsers/lmodel; make
	cd org/alberto/interprete/parsers/loopmodel; make
	cd org/alberto/interprete/parsers/whilemodel; make
	cd org/alberto/interprete; make all

analizadormacros:
	cd org/alberto/interprete/parsers/analizadormacros; make
	cd org/alberto/interprete; make all

previo:
	cd org/alberto/interprete/parsers/previo; make
	cd org/alberto/interprete; make all

l:
	cd org/alberto/interprete/parsers/lmodel; make
	cd org/alberto/interprete; make all

loop:
	cd org/alberto/interprete/parsers/loopmodel; make
	cd org/alberto/interprete; make all

while:
	cd org/alberto/interprete/parsers/whilemodel; make
	cd org/alberto/interprete; make all

javadoc:
	cd ..; mkdir -p doc; javadoc -d doc org/alberto/interprete/*.java -subpackages gui:parsers

cleanall:
	cd org/alberto/interprete/parsers/previo; make clean
	cd org/alberto/interprete/parsers/analizadormacros; make clean
	cd org/alberto/interprete/parsers/lmodel; make clean
	cd org/alberto/interprete/parsers/loopmodel; make clean
	cd org/alberto/interprete/parsers/whilemodel; make clean
	cd org/alberto/interprete/gui; rm -f *.class
	make clean

clean:
	cd ../../../build/classes/main/org; rm -rf *

cleanjavadoc:
	rm -rf ../doc
