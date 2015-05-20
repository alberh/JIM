
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import interprete.Bucle;
import interprete.Etiqueta;
import interprete.Macro;
import interprete.Variable;
import interprete.Variable.EVariable;
import interprete.parsers.previo.*;

public class Main {

	private static final int _mayorVersion = 0, _minorVersion = 1;

	public static void main(String[] args) {

		bienvenida();
		pruebasPrevio();
		// pruebasVariables();
		// pruebasEtiquetas();
	}

	public static void pruebasPrevio() {

		String[] args = { "entradaPrevio.txt" };

		PrevioParser analizador = new PrevioParser(AbrirLector(args)) ;
	    analizador.parse();

	    Variable.pintar();
	    Etiqueta.pintar();
	    Macro.pintar();
	    Bucle.pintar();
	}

	private static Reader AbrirLector(String args[]) {
		Reader lector = null;

		if (args.length > 0) {
			
			try {
				
				lector = new FileReader(args[0]);
			} catch( IOException exc ) {
				
				System.err.println("imposible abrir archivo '"+args[0]+"'");
				System.err.println("causa: "+exc.getMessage());
				System.exit(1);
			}

			System.out.println("leyendo archivo '"+args[0]+"'");
		} else {
			
			lector = new InputStreamReader(System.in);
			System.out.println("leyendo entrada estándard (terminar con ctrl-d)");
		}

		return lector ;
	}

	public static void bienvenida() {

		System.out.println();
		System.out.println("JIM - Intérprete de Modelos");
		System.out.println("Intérprete de modelos de computación L, LOOP y WHILE");
		System.out.println("Versión " + _mayorVersion + "." + _minorVersion);
	}

	public static void pruebasVariables() {

		Variable.set("x33");
		Variable.set("x1", 7);
		Variable.set("z7");
		Variable.set("z5", 9);
		Variable.set("Y", 5);

		Variable v = Variable.get("x1");
		System.out.println(v);
		v = Variable.get("z5");
		System.out.println(v);
		v = Variable.get("y");
		System.out.println(v);
		System.out.println();

		v = Variable.get(EVariable.ENTRADA);
		System.out.println(v);
		v = Variable.get(EVariable.LOCAL);
		System.out.println(v);
		v = Variable.get(EVariable.SALIDA);
		System.out.println(v);
		System.out.println();

		Variable.clear();
		v = Variable.get(EVariable.ENTRADA);
		v.valor(77);
		System.out.println(v.id() + ", " + v.valor());
		v = Variable.get(EVariable.LOCAL);
		System.out.println(v);
		v = Variable.get(EVariable.SALIDA);
		System.out.println(v);
		System.out.println();

		Variable.pintar();
		System.out.println();
	}

	public static void pruebasEtiquetas() {

		Etiqueta.set("l3", 3);
		Etiqueta.set("l0", 55);

		Etiqueta e = Etiqueta.get("l3");
		System.out.println(e);
		e = Etiqueta.get("l0");
		System.out.println(e.id() + ", " + e.linea());
		System.out.println();

		Etiqueta.pintar();
		System.out.println();
	}
}
