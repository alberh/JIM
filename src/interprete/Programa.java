
package interprete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import interprete.*;

public class Programa {

	private static ArrayList<String> _lineas = new ArrayList();
	private static int _lineaActual;

	public enum TipoModelos { L, LOOP, WHILE };

	private Programa() { }

	public static void cargar(String nombrePrograma, TipoModelos modelo) throws Exception {

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
}
