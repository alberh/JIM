
package interprete.parsers;

public abstract class AnalizadorLexico {

	protected int linea_actual;

    public int lineaActual() {

    	return linea_actual;
  	}

  	public void lineaActual(int nuevaLinea) {

    	linea_actual = nuevaLinea;
  	}
}
