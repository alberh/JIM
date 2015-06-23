
/* Día 24 de junio reunión con Tomeu
 * Cambiar clase abstracta AnalizadorLexico a Interfaz
 * Añadir PrevioAcciones
 *
 * Sobre expansión de macros:
 * PRIMERO EXPANDIR MACROS Y LUEGO SUSTITUIR VARIABLES. ASÍ SE PUEDEN IR EXPANDIENDO LAS LLAMADAS INTERNAS A MACROS
 * SEGÚN SE VAN ENCONTRADO POR EL PREVIO, Y POR ÚLTIMO SE ASIGNAN LAS VARIABLES QUE QUEDEN.
 *
 * Cambio futuro: para las condicionales, definir en gramáticas las expresiones lógicas y permitir != N, N es un natural, menor que,
 * mayor que, etc...
 */


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;

import interprete.*;
import interprete.Variable.EVariable;
import interprete.parsers.previo.*;
import interprete.parsers.lmodel.*;

public class Main {

	public static void main(String[] args) {

		bienvenida();
		Configuracion.cargar();

		if (args.length > 0) {

			String modelo = args[0];
			int[] parametros = null;

			if (args.length > 1) {

				parametros = new int[args.length - 1];
				
				int cont = 0;
				for (int i = 1; i < args.length; ++i) {

					parametros[cont++] = Integer.parseInt(args[i]);
				}
			}

			if (modelo.equalsIgnoreCase("l")) {

				pruebasL(parametros);
			} else if (modelo.equalsIgnoreCase("loop")) {

				pruebasLoop(parametros);
			} else if (modelo.equalsIgnoreCase("while")) {

				pruebasWhile(parametros);
			}
		} else {

			pruebasL(null);
			// pruebasLoop();
			// pruebasWhile();
		}

		// pruebaspruebaPrevioParser();
		// pruebasVariables();
		// pruebasEtiquetas();
	}

	public static void bienvenida() {

		System.out.println("JIM - Intérprete de Modelos");
		System.out.println("Intérprete de modelos de computación L, LOOP y WHILE");
		System.out.println("Versión " + Configuracion.version());
		System.out.println();
	}

	public static void pruebasL(int[] parametros) {

		String programa = "ejemplos/entradaL.txt";
		Programa.cargar(programa, Programa.TipoModelos.L);

		restoPrueba(parametros);
	}

	public static void pruebasLoop(int[] parametros) {

		String programa = "ejemplos/entradaLoop.txt";
		Programa.cargar(programa, Programa.TipoModelos.LOOP);

		restoPrueba(parametros);
	}

	public static void pruebasWhile(int[] parametros) {

		String programa = "ejemplos/entradaWhile.txt";
		Programa.cargar(programa, Programa.TipoModelos.WHILE);

		restoPrueba(parametros);
	}

	private static void restoPrueba(int[] parametros) {

		Programa.iniciar(parametros);

		System.out.println("====================");
		System.out.println("Estado de la memoria");
		System.out.println("====================");

		// Programa.imprimirComponentes();

		System.out.println("====================");

		System.out.println("Resultado: " + Programa.resultado());

		// Programa.imprimirPrograma();
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

	// RE-HACER LAS DOS SIGUIENTES
	public static void pruebaPrevioParser(String[] args) {

		System.out.println("Ejecutando pruebaPrevioParser");
		System.out.println("=================");

		pruebaPrevioParser analizador = new pruebaPrevioParser(AbrirLector(args)) ;
	    analizador.parse();
	}

	public static void pruebaspruebaPrevioParser() {

		String[] args = { "entradapruebaPrevioParser.txt" };

		pruebaPrevioParser(args);
		Programa.imprimirEstado();
	}
*/
}
