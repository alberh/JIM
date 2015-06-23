
package interprete;

import java.util.Properties;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** 
 * Clase encargada de gestionar la configuración del programa y su almacenamiento en disco.
 */
public abstract class Configuracion {
	
	private static Properties _propiedades = new Properties();
	private static String _version = "0.1";

	/** 
	 * Carga la configuración del programa desde el fichero "jim.cfg". Si el fichero no existe, lo crea.
	 */
	public static void cargar() {

		File ficheroConfig = new File("jim.cfg");

		if (!ficheroConfig.exists()) {

			crearFicheroConfiguracion(ficheroConfig);
		} else {

			try (FileReader fr = new FileReader(ficheroConfig)) {

				_propiedades.load(fr);

				if (_propiedades.stringPropertyNames().size() != 3) {
					
					// Asigna las propiedades que no estén en el fichero de configuración
					_propiedades.setProperty("rutaMacrosL", _propiedades.getProperty("rutaMacrosL", "macros/l"));
					_propiedades.setProperty("rutaMacrosLoop", _propiedades.getProperty("rutaMacrosLoop", "macros/loop"));
					_propiedades.setProperty("rutaMacrosWhile", _propiedades.getProperty("rutaMacrosWhile", "macros/while"));

					guardar();
				}
			} catch (Exception ex) { }
		}
	}

	/** 
	 * Crea el fichero de configuración en el disco y almacena la configuración por defecto.
	 * @param	ficheroConfig	El objeto File que hace referencia al fichero.
	 * @see 					File
	 */
	private static void crearFicheroConfiguracion(File ficheroConfig) {

		System.out.println("entra a crear el fichero");
		try (FileWriter fw = new FileWriter(ficheroConfig)) {

			ficheroConfig.createNewFile();
			
			_propiedades.setProperty("rutaMacrosL", "macros/l");
			_propiedades.setProperty("rutaMacrosLoop", "macros/loop");
			_propiedades.setProperty("rutaMacrosWhile", "macros/while");

			_propiedades.store(fw, null);
		} catch (IOException ex) {

			// gestión de errores
			return;
		}
	}

	/** 
	 * Almacena el fichero de configuración en el disco.
	 */
	public static void guardar() {

		File ficheroConfig = new File("jim.cfg");

		if (!ficheroConfig.exists()) {

			crearFicheroConfiguracion(ficheroConfig);
		} else {

			try (FileWriter fw = new FileWriter(ficheroConfig)) {

				_propiedades.store(fw, null);
			} catch (Exception ex) { }
		}
	}

	/** 
	 * Devuelve la ruta al directorio de macros del modelo L.
	 * @return			La ruta que apunta al directorio del modelo L.
	 * @see 			String
	 */
	public static String rutaMacrosL() {

		return _propiedades.getProperty("rutaMacrosL");
	}

	/** 
	 * Define la ruta al directorio de macros del modelo L.
	 * @param	nuevaRuta	La ruta que apunta al directorio del modelo L.
	 * @see 				String
	 */
	public static void rutaMacrosL(String nuevaRuta) {

		_propiedades.setProperty("rutaMacrosL", nuevaRuta);
	}

	/** 
	 * Devuelve la ruta al directorio de macros del modelo Loop.
	 * @return			La ruta que apunta al directorio del modelo Loop.
	 * @see 			String
	 */
	public static String rutaMacrosLoop() {

		return _propiedades.getProperty("rutaMacrosLoop");
	}

	/** 
	 * Define la ruta al directorio de macros del modelo Loop.
	 * @param	nuevaRuta	La ruta que apunta al directorio del modelo Loop.
	 * @see 				String
	 */
	public static void rutaMacrosLoop(String nuevaRuta) {

		_propiedades.setProperty("rutaMacrosLoop", nuevaRuta);
	}

	/** 
	 * Devuelve la ruta al directorio de macros del modelo While.
	 * @return			La ruta que apunta al directorio del modelo While.
	 * @see 			String
	 */
	public static String rutaMacrosWhile() {

		return _propiedades.getProperty("rutaMacrosWhile");
	}

	/** 
	 * Define la ruta al directorio de macros del modelo While.
	 * @param	nuevaRuta	La ruta que apunta al directorio del modelo While.
	 * @see 				String
	 */
	public static void rutaMacrosWhile(String nuevaRuta) {

		_propiedades.setProperty("rutaMacrosWhile", nuevaRuta);
	}

	/** 
	 * Devuelve la versión actual del programa.
	 * @return		Una cadena representando la versión del programa.
	 * @see 		String
	 */
	public static String version() {

		return _version;
	}
}
