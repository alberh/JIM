
package interprete;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.nio.CharBuffer;

import interprete.*;
import interprete.parsers.*;
import interprete.parsers.analizadormacros.*;
import interprete.parsers.previo.*;
import interprete.parsers.lmodel.*;

public class Programa {

	private static ArrayList<String> _lineas = new ArrayList();
	private static BufferedReader _reader;
	private static StringReader _stringReader;
	private static int _lineaActual;

	public enum TipoModelos { L, LOOP, WHILE };
	
	private static TipoModelos _modelo;
	private static IParser _parser;

	private Programa() { }

	public static void cargar(BufferedReader br, TipoModelos modelo) {

		String linea;

		_reader = br;

		try {

			while ((linea = _reader.readLine()) != null) {

				_lineas.add(linea);
			}
			_reader.close();
		} catch (IOException ex) {

			System.err.println("Error en el volcado del programa a memoria.");
			System.exit(1);
		}

		_lineaActual = numeroLineas();

		_modelo = modelo;

		switch (_modelo) {

			case L:
				_parser = new LParser( new BufferedReader(new StringReader("")) );
				break;

			case LOOP:
				//_parser = new LoopParser(bf);
				break;

			case WHILE:
				//_parser = new WhileParser(bf);
				break;
		}
	}

	public static void iniciar() {

		iniciar(null);
	}

	public static void iniciar(int[] parametros) {

		// Reiniciar variables, etiquetas, bucles y macros
		reiniciar();
		// Cargar macros
		// ...
		// Pasar previo
		previo();
		// Asignar variables de entrada
		if (parametros != null) {

			asignarVariablesEntrada(parametros);
		}
		// Lanzar
		ejecutar();
	}

	private static void asignarVariablesEntrada(int[] parametros) {

		for (int i = 0; i < parametros.length; ++i) {

 			Variable.set("X" + (i + 1), parametros[i]);
 		}
	}

	private static void reiniciar() {

		System.out.println("Limpiando...");

		Variable.clear();
		Bucle.clear();
		Etiqueta.clear();
		Macro.clear();
	}

	private static void previo() {

		System.out.println("Pasando previo...");

		PrevioParser previoParser = new PrevioParser( new BufferedReader(new StringReader("")) );

		_lineaActual = 0;

		if (numeroLineas() > 0) {

			String linea = lineaSiguiente();

			do {

				// añadir línea al buffer del parser
				PrevioLex lex = (PrevioLex)previoParser.analizadorLexico();
				lex.lineaActual(_lineaActual);

				try {
					
					lex.yyclose();
				} catch (Exception ex) { }
				lex.yyreset( new BufferedReader(new StringReader(linea)) );


				previoParser.parse();

				linea = lineaSiguiente();
			} while (!finalizado());
		}
	}

	private static void ejecutar() {

		System.out.println("Ejecutando...");

		_lineaActual = 0;

		if (numeroLineas() > 0) {

			String linea = lineaSiguiente();

			do {

				System.out.println(_lineaActual + ": " + linea);

				// añadir línea al buffer del parser
				LLex lex = (LLex)_parser.analizadorLexico();

				try {
					
					lex.yyclose();
				} catch (Exception ex) { }
				lex.yyreset( new BufferedReader(new StringReader(linea)) );


				_parser.parse();

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

	public static void imprimirEstado() {

		Variable.pintar();
	    Etiqueta.pintar();
	    Macro.pintar();
	    Bucle.pintar();
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
}
