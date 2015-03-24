
package Interprete;

import java.util.Hashtable;

public class Variable {
	
	private static Hashtable<Integer, Variable> _entrada = new Hashtable<>();
	private static Hashtable<Integer, Variable> _locales = new Hashtable<>();
	private static Variable _salida = new Variable("Y");
	
	private static int _mayorEntrada = -1;
	private static int _mayorLocal = -1;
	
	private int _valor;
	private String _id;
	
	public enum EVariable {
		ENTRADA,
		LOCAL,
		SALIDA
	}
	
	public static void set(String id) {
		
		set(id, 0);
	}
	
 	public static void set(String id, int valor) {
 		
 		id = id.toUpperCase();
 		char tipo = id.charAt(0);
 		int indice = -1;
 		
 		if (tipo != 'Y') {
 			
 			indice = Integer.parseInt(id.substring(1, id.length()));
 		}
		
		switch (tipo) {
			case 'X':
				if (!_entrada.containsKey(indice)) {
					
					_entrada.put(indice, new Variable(id, valor));
					
					if (indice > _mayorEntrada) {
						
						_mayorEntrada = indice;
					}
				}
				break;
			
			case 'Z':
				if (!_locales.containsKey(indice)) {
					
					_locales.put(indice, new Variable(id, valor));
					
					if (indice > _mayorLocal) {
						
						_mayorLocal = indice;
					}
				}
				break;
				
			case 'Y':
				_salida = new Variable("Y", valor);
				break;
				
			default:
				// Añadir número de línea
				System.err.println("Error: Tipo de variable desconocido: '" + tipo + "'.");
		}
 	}
 	
 	public static Variable get(EVariable tipo) {
 		
 		Variable v = null;
 		
 		switch (tipo) {
 			case ENTRADA:
 				_mayorEntrada++;
 				v = new Variable("X" + _mayorEntrada);
 				_entrada.put(_mayorEntrada, v);
 				break;
 			case LOCAL:
 				_mayorLocal++;
 				v = new Variable("Z" + _mayorLocal);
 				_locales.put(_mayorLocal, v);
 				break;
 			case SALIDA:
 				v = _salida;
 		}
 		
 		return v;
 	}
 	
 	public static Variable get(String id) {
 		
 		id = id.toUpperCase();
 		char tipo = id.charAt(0);
 		Variable v = null;
 		int indice = -1;
 		
 		if (tipo != 'Y') {
 		
 			indice = Integer.parseInt(id.substring(1, id.length()));
 		}
 		
 		switch (tipo) {
 			case 'X':
 				v = _entrada.get(indice);
 				break;
 			case 'Z':
 				v = _locales.get(indice);
 				break;
 			case 'Y':
 				v = _salida;
 				break;
 			default:
				// Añadir número de línea
				System.err.println("Error: Tipo de variable desconocido: '" + tipo + "'.");
 		}
 		
 		return v;
 	}
 	
 	public static void clear() {
 		
 		_entrada.clear();
 		_locales.clear();
 		_salida = new Variable("Y");
 		
 		_mayorEntrada = -1;
 		_mayorLocal = -1;
 	}

 	private Variable(String id) {
 		
 		this._id = id;
 		this._valor = 0;
 	}
 	
 	private Variable(String id, int valor) {
 		
 		this(id);
 		if (valor > 0)
 			this._valor = valor;
 	}

 	public String id() {
 		
 		return _id;
 	}
 	
 	public int valor() {
 		
 		return _valor;
 	}
 	
 	public void valor(int nuevoValor) {
 		
 		this._valor = 0;
 		if (nuevoValor > 0)
 			this._valor = nuevoValor;
 	}
 	
 	@Override
 	public String toString() {
 		
 		return "(" + _id + ", " + _valor + ")";
 	}
 	
 	public static void pintar() {
 		
 		System.out.println("Entrada");
 		System.out.println(_entrada);
 		System.out.println("Locales");
 		System.out.println(_locales);
 		System.out.println("Salida");
 		System.out.println(_salida);
 	}
	
}
