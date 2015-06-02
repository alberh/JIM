
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;

import interprete.Programa;
import interprete.Bucle;
import interprete.Etiqueta;
import interprete.Macro;
import interprete.Variable;
import interprete.Variable.EVariable;
import interprete.parsers.previo.*;
import interprete.parsers.lmodel.*;

public class Main {

	private static final int _mayorVersion = 0, _minorVersion = 1;

	public static void main(String[] args) {

		bienvenida();

		// pruebasL();
		// pruebasLoop();
		pruebasWhile();

		// pruebasprevioParser();
		// pruebasVariables();
		// pruebasEtiquetas();
	}
/*
	public static void previoParser(String[] args) {

		System.out.println("Ejecutando previoParser");
		System.out.println("=================");

		PrevioParser analizador = new PrevioParser(AbrirLector(args)) ;
	    analizador.parse();
	}
*/
	public static void pruebasL() {

		String programa = "entradaL.txt";
		Programa.cargar(AbrirLector(programa), Programa.TipoModelos.L);

		int[] parametros = { 2, 6 };
		// Programa.iniciar();
		Programa.iniciar(parametros);

		System.out.println("====================");
		System.out.println("Estado de la memoria");
		System.out.println("====================");

		Programa.imprimirEstado();

		System.out.println("====================");

		System.out.println("Resultado: " + Programa.resultado());


		// Programa.imprimirPrograma();
	}

	public static void pruebasLoop() {

		String programa = "entradaLoop.txt";
		Programa.cargar(AbrirLector(programa), Programa.TipoModelos.LOOP);

		int[] parametros = { 2, 6 };
		// Programa.iniciar();
		Programa.iniciar(parametros);

		System.out.println("====================");
		System.out.println("Estado de la memoria");
		System.out.println("====================");

		Programa.imprimirEstado();

		System.out.println("====================");

		System.out.println("Resultado: " + Programa.resultado());


		// Programa.imprimirPrograma();
	}

	public static void pruebasWhile() {

		String programa = "entradaWhile.txt";
		Programa.cargar(AbrirLector(programa), Programa.TipoModelos.WHILE);

		int[] parametros = { 2, 6 };
		// Programa.iniciar();
		Programa.iniciar(parametros);

		System.out.println("====================");
		System.out.println("Estado de la memoria");
		System.out.println("====================");

		Programa.imprimirEstado();

		System.out.println("====================");

		System.out.println("Resultado: " + Programa.resultado());


		// Programa.imprimirPrograma();
	}
/*
	public static void pruebasprevioParser() {

		String[] args = { "entradaprevioParser.txt" };

		previoParser(args);
		Programa.imprimirEstado();
	}
*/
	private static BufferedReader AbrirLector(String programa) {

		BufferedReader lector = null;

		try {
				
			lector = new BufferedReader(new FileReader(programa));
		} catch( IOException exc ) {
			
			/*
			System.err.println("imposible abrir archivo '"+args[0]+"'");
			System.err.println("causa: "+exc.getMessage());
			*/
			System.err.println("Error al abrir el lector.");
			System.exit(1);
		}

		return lector;
	}

	public static void bienvenida() {

		System.out.println("JIM - Intérprete de Modelos");
		System.out.println("Intérprete de modelos de computación L, LOOP y WHILE");
		System.out.println("Versión " + _mayorVersion + "." + _minorVersion);
		System.out.println();
	}
/*
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
	*/
}
