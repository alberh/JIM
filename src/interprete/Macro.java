
//package interprete;

import java.util.Hashtable;
import java.util.ArrayList;
import java.lang.Math;

public class Macro {
	
	private static Hashtable<String, Macro> _macros = new Hashtable<>();
	
	private String _id;
	private String _cuerpo;
	// Se puede cambiar por un conjunto
	private ArrayList<String> _variables = new ArrayList<>();
	
 	public static void set(String id, String cuerpo) {
 		
 		_macros.put(id, new Macro(id, cuerpo.toUpperCase()));
 	}

 	public static Macro get(String id) {

 		return _macros.get(id);
 	}

 	public static void clear() {

 		_macros.clear();
 	}

 	public static String expandir(String idMacro, String vSalida, ArrayList<String> parametros) {

 		Macro macro = Macro.get(idMacro);
 		String expansion = new String(macro.cuerpo());
 		String asignaciones = "";
 		ArrayList<String> variables = macro.variables();

 		// realizar asignaciones a nuevas variables locales desde todos los parametros usados en
 		// la llamada a la macro, bien desde los valores directos o bien desde las variables pasadas.
 		// según se vayan tomando nuevas variables locales, realizar en el cuerpo la sustitución
 		for (int i = 0; i < parametros.size(); ++i) {
 			
			String param = parametros.get(i).toUpperCase();
 			String nuevaLocal = Variable.get(Variable.EVariable.LOCAL).id();

			expansion = expansion.replace(param, nuevaLocal);
			asignaciones += nuevaLocal + " <- " + param + "\n";
 		}

 		for (int i = 0; i < variables.size(); ++i) {

 			String nuevaEntrada = Variable.get(Variable.EVariable.LOCAL).id();

 			expansion = expansion.replace(variables.get(i), nuevaEntrada);
 		}

 		// sustituir variable de salida 'Y' por 'vSalida'
 		expansion = asignaciones + expansion.replace("Y", vSalida.toUpperCase());

 		return expansion;
 	}
 		
 	public Macro(String id, String cuerpo) {

 		this._id = id;
 		this._cuerpo = cuerpo;
 	}
 	
 	public String id() {

 		return _id;
 	}

 	public String cuerpo() {

 		return _cuerpo;
 	}

 	public ArrayList<String> variables() {

 		return _variables;
 	}

 	public void nuevaVariable(String id) {

 		id = id.toUpperCase();

 		if (!_variables.contains(id) && !id.equals("Y")) {
			
			_variables.add(id);
		}
 	}
 	
 	@Override
 	public String toString() {
 		
 		return _id + "\n" + _cuerpo + "\n";
 	}
 	
 	public static void pintar() {
 		
 		//_macros.forEach(s -> { System.out.println(s); });
 	}
	
}
