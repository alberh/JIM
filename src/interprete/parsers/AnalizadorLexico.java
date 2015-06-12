
package interprete.parsers;

public abstract class AnalizadorLexico {

	public abstract void yyreset(java.io.Reader reader);
  	public abstract void yyclose() throws java.io.IOException;
}
