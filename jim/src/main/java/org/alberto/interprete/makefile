
JAVAC = javac -d ../../../../../../build/classes/main -classpath ../../..

all:
	$(JAVAC) *.java

consola:
	make Main.class

gui:
	make MainWindow.class

Main.class:
	cd org/alberto/interprete; rm -f *.class; $(JAVAC) *.java

MainWindow.class:
	cd org/alberto/interprete; rm -f *.class; $(JAVAC) *.java

clean:
	rm -f *.class parsers/*.class gui/*.class