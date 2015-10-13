
all:
	cd org/alberto/interprete/parsers/previo; make
	cd org/alberto/interprete/parsers/analizadormacros; make
	cd org/alberto/interprete/parsers/lmodel; make
	cd org/alberto/interprete/parsers/loopmodel; make
	cd org/alberto/interprete/parsers/whilemodel; make
	cd org/alberto/interprete; make all

analizadormacros:
	cd org/alberto/interprete/parsers/analizadormacros; make
	make

previo:
	cd org/alberto/interprete/parsers/previo; make
	make

l:
	cd org/alberto/interprete/parsers/lmodel; make
	make

loop:
	cd org/alberto/interprete/parsers/loopmodel; make
	make Main.class

while:
	cd org/alberto/interprete/parsers/whilemodel; make
	make Main.class

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
	cd org/alberto/interprete; make clean

cleanprevio:
	make clean
	cd org/alberto/interprete/parsers/previo; make clean

cleananalizadormacros:
	make clean
	cd org/alberto/interprete/parsers/analizadormacros; make clean

cleanl:
	make clean
	cd org/alberto/interprete/parsers/lmodel; make clean

cleanloop:
	make clean
	cd org/alberto/interprete/parsers/loopmodel; make clean

cleanwhile:
	make clean
	cd org/alberto/interprete/parsers/whilemodel; make clean

cleanjavadoc:
	rm -rf ../doc
