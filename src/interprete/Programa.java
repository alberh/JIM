
package interprete;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringBuilder;
import java.util.ArrayList;

import interprete.*;
import interprete.parsers.*;
import interprete.parsers.analizadormacros.*;
import interprete.parsers.previo.*;
import interprete.parsers.lmodel.*;

public class Programa {

	private static ArrayList<String> _lineas = new ArrayList();
	private static int _lineaActual;

	public enum TipoModelos { L, LOOP, WHILE };
	
	private static TipoModelos _modelo;
	private static IParser _parser;
	
	private static StringBuilder _lineaReader;
	private static StringReader _reader;

	private Programa() { }

	public static void cargar(BufferedReader br, TipoModelos modelo) {

		String linea;

		try {

			while ((linea = br.readLine()) != null) {

				_lineas.add(linea);
			}
			br.close();
		} catch (IOException ex) {

			System.err.println("Error en el volcado del programa a memoria.");
			System.exit(1);
		}

		_lineaActual = numeroLineas();

		_modelo = modelo;


		/*
		QUIZÁ LO QUE HAGA FALTA ES AÑADIR UN MÉTODO PARA CAMBIAR EL READER
		A LA INTERFAZ PARSER Y CREAR UN NUEVO LECTOR PARA CADA LÍNEA
		*/

		/*
		Añadir dos líneas del código al principio y así ir siempre una por adelantado,
		de forma que el buffer ...
		*/

		/*
		Almacenar las posiciones de los comienzos de línea en el buffer según se van almacenando
		dichas líneas en Programa. Cuando haya un salto debido a una sentencia de salto condicional,
		incondicional o bucle, se obtendrá la posición de la línea destino del salto y se cambiará
		la posición del lector en el buffer.

		Métodos a usar:
			- mark(): para marcar el inicio del buffer nada más comenzar el código
			- reset(): pone la posición del lector en la última marca (si no hay marca -> excepción)
			- skip(long n): salta caracteres
		*/

		// Crear instancia del buffer
		_lineaReader = new StringBuilder();
		_reader = new StringReader(_lineaReader);

		switch (_modelo) {

			case L:
				_parser = new LParser(_reader);
				break;

			case LOOP:
				//_parser = new LoopParser(bf);
				break;

			case WHILE:
				//_parser = new WhileParser(bf);
				break;
		}
	}

	/*
	public static void cargar(String nombrePrograma, TipoModelos modelo) throws Exception {

		/* CAMBIOS:
		 *	Comprobar existencia de directorios al inicio del programa y crear los que falten.
		 *	Basar la ruta hacia estos directorios en un fichero de propiedades y la clase
		 *	encargada de cargar y gestionar éstas propiedades y su volcado al fichero.

		String rutaUsuario = System.getProperty("user.dir");
		String rutaBase = rutaUsuario + "/codigos/L/";
		
		File f = new File(rutaBase);
		if (!f.exists() && f.isDirectory()) {

			f.mkdirs();
		}

		String rutaFichero = rutaBase + nombrePrograma;

		f = new File(rutaFichero);
		if (!f.exists() && !f.isDirectory()) {

			throw new Exception("No se encuentra el fichero \"" + rutaFichero + "\".");
		}

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			
			String linea;
			while ((linea = br.readLine()) != null) {

				_lineas.add(linea);
			}

			_lineaActual = numeroLineas();
		} catch(IOException exc) {
			
			throw new Exception("No se pudo abrir el fichero \"" + rutaFichero + "\".\nCausa: " + exc.getMessage(), exc);
		}
	}
	*/

	public static void iniciar() {

		// Reiniciar variables, etiquetas, bucles y macros
		// Cargar macros
		// Pasar previo
		ejecutar();

		// Informar fin programa y resultado
	}

	private static void ejecutar() {

		_lineaActual = 0;

		if (numeroLineas() > 0) {

			String linea = lineaSiguiente();

			do {

				// añadir línea al buffer del parser
				// parser.parse();

				linea = lineaSiguiente();
			} while (!finalizado());
		}
	}

	public static int numeroLineaActual() {

		return _lineaActual;
	}

	public static void numeroLineaActual(int n) {

		if (lineaValida(n)) {

			_lineaActual = n;
		} else {

			_lineaActual = numeroLineas();
		}
	}

	public static String lineaActual() {

		return finalizado() ? null : _lineas.get(_lineaActual);
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

		return numeroLinea >= 1 && numeroLinea < numeroLineas();
	}

	public static int numeroLineas() {

		return _lineas.size();
	}

	public static ArrayList<String> lineas() {

		return _lineas;
	}

	public static boolean finalizado() {

		return numeroLineaActual() == numeroLineas();
	}

	public static void imprimirEstado() {

		Variable.pintar();
	    Etiqueta.pintar();
	    Macro.pintar();
	    Bucle.pintar();
	}
}
