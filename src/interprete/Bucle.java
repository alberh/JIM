
package interprete;

import java.util.Hashtable;
import java.util.Stack;

public class Bucle {

	// Los bucles estarán indexados por su línea final (o donde está la palabra reservada END).
	// De este modo es más fácil realizar la ejecución del ciclo.
	// Los distintos comportamientos según el modelo While o el modelo Loop queda delegado a la clase
	// de acciones del parser correspondiente a cada modelo.
	private static Hashtable<Integer, Bucle> _buclesLineaApertura = new Hashtable<>();
	private static Hashtable<Integer, Bucle> _buclesLineaCierre = new Hashtable<>();
	private static Stack<Integer> _inicioBucles = new Stack<>();
	
	private int _lineaInicio;
	private int _lineaFin;
	private int _contador;
	
	public static void abrir(int lineaInicio) {
		
		_inicioBucles.push(lineaInicio);
	}
	
	public static void cerrar(int lineaFin) {
		
		int lineaInicio = _inicioBucles.pop();
		_buclesLineaApertura.put(lineaInicio, new Bucle(lineaInicio, lineaFin));
		_buclesLineaCierre.put(lineaFin, new Bucle(lineaInicio, lineaFin));
	}
	
	public static Bucle getPorLineaInicio(int lineaInicio) {
		
		Bucle bucle = null;

		try {

			bucle = _buclesLineaApertura.get(lineaInicio);
		} catch (Exception ex) {

			System.err.println("No se encuentra la apertura del bucle.");
		}
		
		return bucle;
	}

	public static Bucle getPorLineaFin(int lineaFin) {
		
		Bucle bucle = null;

		try {

			bucle = _buclesLineaCierre.get(lineaFin);
		} catch (Exception ex) {

			System.err.println("No se encuentra la apertura del bucle.");
		}
		
		return bucle;
	}
	
	public static void clear() {
		
		_buclesLineaApertura.clear();
		_buclesLineaCierre.clear();
		_inicioBucles.clear();
	}
	
	public Bucle(int lineaInicio, int lineaFin) {
		
		this._lineaInicio = lineaInicio;
		this._lineaFin = lineaFin;
		this._contador = -1;
	}
	
	public int lineaInicio() {
		
		return _lineaInicio;
	}
	
	public int lineaFin() {
		
		return _lineaFin;
	}

	public int contador() {

		return _contador;
	}

	public void contador(int nuevoValor) {

		this._contador = nuevoValor;
	}

	public boolean inicializado() {

		return _contador != -1;
	}

	public void decremento() {

		if (_contador > 0) {

			_contador--;
		}
	}

	public void resetContador() {

		this._contador = -1;
	}

	@Override
	public String toString() {

		return "(" + _lineaInicio + ", " + _lineaFin + ")";
	}

	public static void pintar() {

		System.out.println("Bucles");
 		_buclesLineaCierre.forEach( (k, v) -> System.out.println(v) );
 		System.out.println();
 	}
}
