
package interprete;

import java.util.Hashtable;
import java.util.Stack;

public class Bucle {

	// Los bucles estarán indexados por su línea final (o donde está la palabra reservada END).
	// De este modo es más fácil realizar la ejecución del ciclo.
	// Los distintos comportamientos según el modelo While o el modelo Loop queda delegado a la clase
	// de acciones del parser correspondiente a cada modelo.
	private static Hashtable<Integer, Bucle> _bucles = new Hashtable<>();
	private static Stack<Integer> _inicioBucles = new Stack<>();
	
	private int _lineaInicio;
	private int _lineaFin;
	
	public static void abrir(int lineaInicio) {
		
		_inicioBucles.push(lineaInicio);
	}
	
	public static void cerrar(int lineaFin) {
		
		int lineaInicio = _inicioBucles.pop();
		_bucles.put(lineaFin, new Bucle(lineaInicio, lineaFin));
	}
	
	public static Bucle get(int lineaFin) {
		
		return _bucles.get(lineaFin);
	}
	
	public static void clear() {
		
		_bucles.clear();
		_inicioBucles.clear();
	}
	
	public Bucle(int lineaInicio, int lineaFin) {
		
		this._lineaInicio = lineaInicio;
		this._lineaFin = lineaFin;
	}
	
	public int lineaInicio() {
		
		return _lineaInicio;
	}
	
	public int lineaFin() {
		
		return _lineaFin;
	}

	@Override
	public String toString() {

		return "(" + _lineaInicio + ", " + _lineaFin + ")";
	}

	public static void pintar() {

		System.out.println("Bucles:");
 		_bucles.forEach( (k, v) -> System.out.println(v) );
 		System.out.println();
 	}
}
