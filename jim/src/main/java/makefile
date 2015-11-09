
all:
	cd com/jim_project/interprete/parsers/previo; make
	cd com/jim_project/interprete/parsers/analizadormacros; make
	cd com/jim_project/interprete/parsers/lmodel; make
	cd com/jim_project/interprete/parsers/loopmodel; make
	cd com/jim_project/interprete/parsers/whilemodel; make
	cd com/jim_project/interprete; make all

analizadormacros:
	cd com/jim_project/interprete/parsers/analizadormacros; make
	cd com/jim_project/interprete; make all

previo:
	cd com/jim_project/interprete/parsers/previo; make
	cd com/jim_project/interprete; make all

l:
	cd com/jim_project/interprete/parsers/lmodel; make
	cd com/jim_project/interprete; make all

loop:
	cd com/jim_project/interprete/parsers/loopmodel; make
	cd com/jim_project/interprete; make all

while:
	cd com/jim_project/interprete/parsers/whilemodel; make
	cd com/jim_project/interprete; make all

javadoc:
	cd ..; mkdir -p doc; javadoc -d doc com/jim_project/interprete/*.java -subpackages gui:parsers

cleanall:
	cd com/jim_project/interprete/parsers/previo; make clean
	cd com/jim_project/interprete/parsers/analizadormacros; make clean
	cd com/jim_project/interprete/parsers/lmodel; make clean
	cd com/jim_project/interprete/parsers/loopmodel; make clean
	cd com/jim_project/interprete/parsers/whilemodel; make clean
	cd com/jim_project/interprete/gui; rm -f *.class
	make clean

clean:
	cd ../../../build/classes/main/org; rm -rf *

cleanjavadoc:
	rm -rf ../doc
