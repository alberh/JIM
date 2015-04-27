
// package interprete;

import java.util.Hashtable;

public class Etiqueta {
	
	private static Hashtable<String, Etiqueta> _etiquetas = new Hashtable<>();
	private static int _ultimaA = 0;
	
	private String _id;
	private int _linea;
	
	public static Etiqueta set(String id, int linea) {
		
		id = id.toUpperCase();
		char letra = id.charAt(0);
		int indice = 1;

		if (id.length() > 1) {

			indice = Integer.parseInt(id.substring(1, id.length()));
		} else if (id.length() == 1) {

			id += "1";
		}

		Etiqueta et = new Etiqueta(id, linea);
		
		_etiquetas.put(id, et);

		if (letra == 'A' && indice > _ultimaA) {

			_ultimaA = indice;
		}

		return et;
	}
	
	public static Etiqueta get(String id) {
		
		id = id.toUpperCase();

		if (id.length() == 1) {

			id += "1";
		}
		
		return _etiquetas.get(id);
	}

	public static Etiqueta get() {

		++_ultimaA;
		Etiqueta etiqueta = new Etiqueta("A" + _ultimaA, 0);

		return etiqueta;
	}
	
	public static void clear() {
		
		_etiquetas.clear();
	}

	private Etiqueta(String id, int linea) {
		
		this._id = id;
		this._linea = linea;
	}
	 	
	public String id() {
		
		return _id;
	}
	
	public int linea() {
		
		return _linea;
	}
	
	@Override
	public String toString() {
		
		return "(" + _id + ", " + _linea + ")";
	}
	
	public static void pintar()  {
		
		System.out.println(_etiquetas);
	}
}
