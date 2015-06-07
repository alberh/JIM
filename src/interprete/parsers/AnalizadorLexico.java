
package interprete.parsers;

public abstract class AnalizadorLexico {

	protected int linea_actual;

    public int lineaActual() {

    	return linea_actual;
  	}

  	public void lineaActual(int nuevaLinea) {

    	linea_actual = nuevaLinea;
  	}

  	public abstract void yyreset(java.io.Reader reader);
  	public abstract void yyclose() throws java.io.IOException;
}
