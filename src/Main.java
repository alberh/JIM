
import interprete.*;
import interprete.Variable.EVariable;

public class Main {
	
	public static void main(String[] args) {
		
		pruebasVariables();
		pruebasEtiquetas();
	}
	
	public static void pruebasVariables() {
		
		Variable.set("x33");
		Variable.set("x1", 7);
		Variable.set("z7");
		Variable.set("z5", 9);
		Variable.set("Y", 5);
		
		Variable v = Variable.get("x1");
		System.out.println(v);
		v = Variable.get("z5");
		System.out.println(v);
		v = Variable.get("y");
		System.out.println(v);
		System.out.println();
		
		v = Variable.get(EVariable.ENTRADA);
		System.out.println(v);
		v = Variable.get(EVariable.LOCAL);
		System.out.println(v);
		v = Variable.get(EVariable.SALIDA);
		System.out.println(v);
		System.out.println();
		
		Variable.clear();
		v = Variable.get(EVariable.ENTRADA);
		v.valor(77);
		System.out.println(v.id() + ", " + v.valor());
		v = Variable.get(EVariable.LOCAL);
		System.out.println(v);
		v = Variable.get(EVariable.SALIDA);
		System.out.println(v);
		System.out.println();
		
		Variable.pintar();
		System.out.println();
	}
	
	public static void pruebasEtiquetas() {
		
		Etiqueta.set("l3", 3);
		Etiqueta.set("l0", 55);
		
		Etiqueta e = Etiqueta.get("l3");
		System.out.println(e);
		e = Etiqueta.get("l0");
		System.out.println(e.id() + ", " + e.linea());
		System.out.println();
		
		Etiqueta.pintar();
		System.out.println();
	}
}
