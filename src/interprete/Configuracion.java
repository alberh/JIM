
package interprete;

import java.util.Properties;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Configuracion {
	
	private static Properties _propiedades = new Properties();
	private static String _version = "0.1";

	public static void cargar() {

		File ficheroConfig = new File("jim.cfg");

		if (!ficheroConfig.exists()) {

			crearFicheroConfiguracion(ficheroConfig);
		}

		try (FileReader fr = new FileReader(ficheroConfig)) {

			_propiedades.getProperty("rutaMacrosL");
			_propiedades.getProperty("rutaMacrosLoop");
			_propiedades.getProperty("rutaMacrosWhile");
		} catch (Exception ex) { }
	}

	private static void crearFicheroConfiguracion(File ficheroConfig) {

		try (FileWriter fw = new FileWriter(ficheroConfig)) {

			ficheroConfig.createNewFile();
			
			_propiedades.setProperty("rutaMacrosL", "/macros/l");
			_propiedades.setProperty("rutaMacrosLoop", "/macros/loop");
			_propiedades.setProperty("rutaMacrosWhile", "/macros/while");

			_propiedades.store(fw, null);
		} catch (IOException ex) {

			// gestión de errores
			return;
		}
	}

	public static void guardar() {

		File ficheroConfig = new File("jim.cfg");

		if (!ficheroConfig.exists()) {

			try {
				
				ficheroConfig.createNewFile();
			} catch (IOException ex) {

				// ...
				return;
			}
		}

		try (FileWriter fw = new FileWriter(ficheroConfig)) {

			_propiedades.store(fw, null);
		} catch (Exception ex) { }
	}

	public static String rutaMacrosL() {

		return _propiedades.getProperty("rutaMacrosL", "/macros/l");
	}

	public static void rutaMacrosL(String nuevaRuta) {

		_propiedades.setProperty("rutaMacrosL", nuevaRuta);
	}

	public static String rutaMacrosLoop() {

		return _propiedades.getProperty("rutaMacrosLoop", "/macros/loop");
	}

	public static void rutaMacrosLoop(String nuevaRuta) {

		_propiedades.setProperty("rutaMacrosLoop", nuevaRuta);
	}

	public static String rutaMacrosWhile() {

		return _propiedades.getProperty("rutaMacrosWhile", "/macros/while");
	}

	public static void rutaMacrosWhile(String nuevaRuta) {

		_propiedades.setProperty("rutaMacrosWhile", nuevaRuta);
	}

	public static String version() {

		return _version;
	}
}
