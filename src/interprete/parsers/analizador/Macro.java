
//package interprete;

import java.util.Hashtable;
import java.util.ArrayList;
import java.lang.Math;

public class Macro {

	private static Hashtable<String, Macro> _macros = new Hashtable<>();
	
	private String _id;
	private String _cuerpo;
	private ArrayList<String> _entrada = new ArrayList<>();
	private ArrayList<String> _locales = new ArrayList<>();
	private ArrayList<String> _etiquetas = new ArrayList<>();
	
 	public static void set(String id) {
 		
 		_macros.put(id, new Macro(id));
 	}

 	public static Macro get(String id) {

 		return _macros.get(id);
 	}

 	public static void clear() {

 		_macros.clear();
 	}

 	public static int ejecutar(String idMacro, String vSalida, ArrayList<String> parametros) {

 		// crear un nuevo parser para el lenguaje que sea, ejecutar y obtener el valor de Y

 		// otra opción: quitar este método y meter otro método dentro de cada parser que haga lo propio

 		return 0;
 	}

 	public static String expandir(int lineaLlamada, String vSalida, String idMacro, ArrayList<String> parametros) {

 		Macro macro = Macro.get(idMacro);
 		String expansion = new String(macro.cuerpo());
 		String asignaciones = vSalida.toUpperCase() + " <- 0\n";

 		ArrayList<String> vEntrada = macro.variablesEntrada();
 		vEntrada.sort(null);

 		ArrayList<String> vLocales = macro.variablesLocales();
 		ArrayList<String> etiquetas = macro.etiquetas();

 		for (int i = 0; i < vEntrada.size(); ++i) {

 			String variable = vEntrada.get(i);
 			String nuevaVariable = Variable.get(Variable.EVariable.LOCAL).id();

 			expansion = expansion.replace(variable, nuevaVariable);

 			if (i < parametros.size()) {

 				asignaciones += nuevaVariable + " <- " + parametros.get(i) + "\n";
 			} else {

 				asignaciones += nuevaVariable + " <- 0\n";
 			}
 		}

 		for (String variable : vLocales) {

 			String nuevaVariable = Variable.get(Variable.EVariable.LOCAL).id();

 			expansion = expansion.replace(variable, nuevaVariable);
 		}

 		for (String etiqueta : etiquetas) {

 			Etiqueta nuevaEtiqueta = Etiqueta.get();
 			
 			expansion = expansion.replace(etiqueta, nuevaEtiqueta.id());
 		}

 		expansion = asignaciones + expansion.replace("Y", vSalida.toUpperCase());

 		return expansion;
 	}

 	public Macro(String id) {

 		this._id = id;
 	}
 	
 	public String id() {

 		return _id;
 	}

 	public String cuerpo() {

 		return _cuerpo;
 	}

 	public void cuerpo(String cuerpo) {

 		this._cuerpo = cuerpo.toUpperCase();
 	}

 	public ArrayList<String> variablesEntrada() {

 		return _entrada;
 	}

 	public ArrayList<String> variablesLocales() {

 		return _locales;
 	}

 	public ArrayList<String> etiquetas() {

 		return _etiquetas;
 	}

 	public void nuevaVariable(String id) {

 		id = id.toUpperCase();
 		char tipo = id.charAt(0);
		
		switch (tipo) {
			case 'X':
				if (!_entrada.contains(id)) {
					
					_entrada.add(id);
				}
				break;
			
			case 'Z':
				if (!_locales.contains(id)) {
					
					_locales.add(id);
				}
				break;
				
			case 'Y':
				break;
				
			default:
				// Añadir número de línea
				System.err.println("Error: Tipo de variable desconocido: '" + tipo + "'.");
		}
 	}

 	public void nuevaEtiqueta(String id) {

 		_etiquetas.add(id);
 	}
 	
 	@Override
 	public String toString() {
 		
 		return _id + "\n" + _cuerpo + "\n";
 	}
 	
 	public static void pintar() {
 		
 		//_macros.forEach(s -> { System.out.println(s); });
 	}
	
}
