
package interprete;

import java.util.Hashtable;

public class Bucle {

	private static Hashtable<Integer, Bucle> _bucles = new Hashtable<>();
	
	private int _lineaInicio;
	private int _lineaFin;
	
	public static void set(int lineaInicio) {
		
		_bucles.put(lineaInicio, new Bucle(lineaInicio));
	}
	
	public static Bucle get(int lineaInicio) {
		
		return _bucles.get(lineaInicio);
	}
	
	public static Bucle check(int linea) {
		
		return new Bucle(0);
	}
	
	public static void clear() {
		
		_bucles.clear();
	}
	
	public Bucle(int lineaInicio) {
		
		this._lineaInicio = lineaInicio;
		this._lineaFin = -1;
	}
	
	public int lineaInicio() {
		
		return _lineaInicio;
	}
	
	public int lineaFin() {
		
		return _lineaFin;
	}
	
	public void lineaFin(int lineaFin) {
		
		if (lineaFin > 0 /* && lineaFin < Programa.length() */ ) {
			this._lineaFin = lineaFin;
		}
	}
}
