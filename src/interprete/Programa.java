
package interprete;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.NotDirectoryException;

import interprete.*;
import interprete.parsers.*;
import interprete.parsers.analizadormacros.*;
import interprete.parsers.previo.*;
import interprete.parsers.lmodel.*;
import interprete.parsers.loopmodel.*;
import interprete.parsers.whilemodel.*;

public class Programa {

	private static ArrayList<String> _lineas;
	private static int _lineaActual;
	private static IParser _parser;
	private static String _programa;

	public enum TipoModelos { L, LOOP, WHILE };
	private static TipoModelos _modelo;

	private Programa() { }

	public static void cargar(String programa, TipoModelos modelo) {

		try {

			_lineas = new ArrayList(Files.readAllLines(Paths.get(programa)));
			_lineaActual = numeroLineas();

			_programa = programa;
			_modelo = modelo;

			switch (_modelo) {

				case L:
					_parser = new LParser(null);
					break;

				case LOOP:
					_parser = new LoopParser(null);
					break;

				case WHILE:
					_parser = new WhileParser(null);
					break;
			}
		} catch (IOException ex) {

			System.err.println("Error en el volcado del programa a memoria.");
			System.exit(1);
		}
	}

	public static void iniciar() {

		iniciar(null);
	}

	public static void iniciar(int[] parametros) {

		// Reiniciar variables, etiquetas, bucles y macros
		System.out.println("Limpiando...");
		reiniciar();

		// Cargar macros del modelo (ToDo: almacenar macros por modelo y cargarlas una vez al iniciar programa? (qué pasa con nuevas macros?))
		System.out.println("Comprobando directorios de macros...");
		comprobarDirectoriosMacros();

		System.out.println("Cargando macros...");
		cargarMacros();

		// Pasar previo
		System.out.println("Pasando previo...");
		previo();

		System.out.println("Programa antes de la expansión");
		imprimirPrograma();
		System.out.println();

		// Expandir macros
		PrevioAcciones.expandir();

		System.out.println("Programa después de la expansión");
		imprimirPrograma();
		System.out.println();

		// Se vuelve a pasar el previo para establecer las nuevas variables y etiquetas tras la expansión de macros
		System.out.println("Se vuelve a pasar previo...");
		previo();

		// Asignar variables de entrada
		if (parametros != null) {

			asignarVariablesEntrada(parametros);
		}

		// Lanzar
		System.out.println("Ejecutando...");
		ejecutar(_parser);
	}

	private static void asignarVariablesEntrada(int[] parametros) {

		for (int i = 0; i < parametros.length; ++i) {

 			Variable.set("X" + (i + 1), parametros[i]);
 		}
	}

	private static void reiniciar() {

		Variable.clear();
		Bucle.clear();
		Etiqueta.clear();
		Macro.clear();
	}

	public static void cargarMacros() {

		try {

			Stream<Path> streamListaFicheros = Files.list(Paths.get(obtenerRutaModelo()));

			streamListaFicheros.forEach(

				p -> {

					String fichero = p.getFileName().toString();
					System.out.println("Leyendo fichero de macros \"" + fichero + "\"");

					try {
						
						String rutaFichero = obtenerRutaModelo() + "/" + fichero;
						MacrosParser macrosParser = new MacrosParser(new FileReader(rutaFichero));
						macrosParser.parse();
					} catch (IOException ex) {

						// gestión de errores
						System.err.println("Error en el volcado del programa a memoria.");
						System.exit(1);
					}
				}
			);
		} catch (NotDirectoryException ex) {

			// gestión de errores
			System.err.println(obtenerRutaModelo() + "no es un directorio.");
			System.exit(1);
		} catch (IOException ex) {

			// gestión de errores
			System.err.println("Error al obtener lista de ficheros.");
			System.exit(1);
		}
	}

	private static String obtenerRutaModelo() {

		String ruta = null;

		switch (_modelo) {

			case L:
				ruta = Configuracion.rutaMacrosL();
				break;

			case LOOP:
				ruta = Configuracion.rutaMacrosLoop();
				break;

			case WHILE:
				ruta = Configuracion.rutaMacrosWhile();
				break;
		}

		return ruta;
	}

	public static TipoModelos modelo() {

		return _modelo;
	}

	private static void comprobarDirectoriosMacros() {

		/* macros/
		 * ...l/
		 * ...loop/
		 * ...while/
		 */

		comprobarDirectorio(new File(Configuracion.rutaMacrosL()));
		comprobarDirectorio(new File(Configuracion.rutaMacrosLoop()));
		comprobarDirectorio(new File(Configuracion.rutaMacrosWhile()));
	}

	private static void comprobarDirectorio(File directorio) {

		if (!directorio.exists()) {

			boolean creados = directorio.mkdirs();

			if (!creados) {

				// gestión de errores
			}
		} else {

			// es un directorio
			if (!directorio.isDirectory()) {

				// gestión de errores
			}

			if (!directorio.canRead()) {

				// gestión de errores
			}
		}
	}

	private static void previo() {

		ejecutar(new PrevioParser(null));
	}

	public static void insertarExpansion(int linea, String expansion) {

		System.out.println("Expandiendo en línea " + linea);

		_lineas.remove(linea - 1);
		_lineas.addAll(linea - 1, Arrays.asList(expansion.split("\n")));
	}

	private static void ejecutar(IParser parser) {

		_lineaActual = 0;
		AnalizadorLexico lex = parser.analizadorLexico();

		if (numeroLineas() > 0) {

			String linea = lineaSiguiente();

			do {

				System.out.println(_lineaActual + ": " + linea);
				// System.out.println(estadoMemoria());

				try {
					
					lex.yyclose();
				} catch (Exception ex) { }
				lex.yyreset(new BufferedReader(new StringReader(linea)));

				parser.parse();

				linea = lineaSiguiente();
			} while (!finalizado());
		}
	}

	public static String estadoMemoria() {

		boolean comaAlFinal = false;
		StringBuilder sb = new StringBuilder();
		sb.append("(" + _lineaActual + ", <");

		ArrayList<Variable> variables = Variable.variablesEntrada();
		if (variables.size() > 0) {

			comaAlFinal = true;
			concatenarVariables(variables, sb);
		}

		if (comaAlFinal) {

			sb.append(", ");
			comaAlFinal = false;
		}

		variables = Variable.variablesLocales();
		if (variables.size() > 0) {

			comaAlFinal = true;
			concatenarVariables(variables, sb);
		}

		if (comaAlFinal) {

			sb.append(", ");
		}
		concatenarVariable(Variable.variableSalida(), sb);

		sb.append(">)");

		return sb.toString();
	}

	private static void concatenarVariables(ArrayList<Variable> variables, StringBuilder sb) {

		concatenarVariable(variables.get(0), sb);

		for (int i = 1; i < variables.size(); ++i) {

			sb.append(", ");
			concatenarVariable(variables.get(i), sb);
		}
	}

	private static void concatenarVariable(Variable variable, StringBuilder sb) {

		sb.append(variable.id() + " = " + variable.valor());
	}

	public static int numeroLineaActual() {

		return _lineaActual;
	}

	public static void numeroLineaActual(int n) {

		if (lineaValida(n)) {

			_lineaActual = n;
		} else {

			terminar();
		}
	}

	public static String lineaActual() {

		return finalizado() ? null : _lineas.get(_lineaActual - 1);
	}

	public static String lineaSiguiente() {

		numeroLineaActual(_lineaActual + 1);
		return lineaActual();
	}

	public static String linea(int n) {

		if (lineaValida(n)) {

			return _lineas.get(n);
		} else {

			return null;
		}
	}

	public static boolean lineaValida(int numeroLinea) {

		return numeroLinea >= 1 && numeroLinea <= numeroLineas();
	}

	public static int numeroLineas() {

		return _lineas.size();
	}

	public static ArrayList<String> lineas() {

		return _lineas;
	}

	public static void terminar() {

		_lineaActual = numeroLineas() + 1;
	}

	public static boolean finalizado() {

		return numeroLineaActual() <= 0 || numeroLineaActual() > numeroLineas();
	}

	public static void imprimirComponentes() {

		Variable.pintar();

		if (_modelo == TipoModelos.L) {

	    	Etiqueta.pintar();
		} else {

	    	Bucle.pintar();
		}

		Macro.pintar();
	}

	public static void imprimirPrograma() {

		for (int i = 0; i < _lineas.size(); ++i) {

			System.out.println((i + 1) + ": " + _lineas.get(i));
		}

		System.out.println(numeroLineas() + " líneas.");
	}

	public static int resultado() {

		return Variable.get("Y").valor();
	}
}
