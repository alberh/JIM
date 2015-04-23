
// package interprete;

import java.util.Hashtable;

public class Etiqueta {
	
	private static Hashtable<Integer, Etiqueta> _etiquetas = new Hashtable<>();
	private static int _ultima = -1;
	
	private String _id;
	private int _linea;
	
	public static Etiqueta set(String id, int linea) {
		
		int indice = Integer.parseInt(id.substring(1, id.length()));
		Etiqueta et = new Etiqueta(id.toUpperCase(), linea);
		
		_etiquetas.put(indice, et);

		if (indice > _ultima) {

			_ultima = indice;
		}

		return et;
	}
	
	public static Etiqueta get(String id) {
		
		int indice = Integer.parseInt(id.substring(1, id.length()));
		
		return _etiquetas.get(indice);
	}

	public static Etiqueta get() {

		++_ultima;
		Etiqueta etiqueta = new Etiqueta("A" + _ultima, 0);

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
