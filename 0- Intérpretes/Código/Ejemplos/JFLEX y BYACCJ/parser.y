
%{
  import java.io.*;
%}
      
%token NL          /* newline  */
%token <dval> NUM  /* a number */

%type <dval> exp

%left '-' '+'
..
%right '^'         /* exponentiation */
      
%%

..
      
exp:     NUM          { $$ = $1; }
       | exp '+' exp  { $$ = $1 + $3; }
       ..
       | exp '^' exp  { $$ = Math.pow($1, $3); }
       | '(' exp ')'  { $$ = $2; }
       ;

%%
  /* a reference to the lexer object */
  private Yylex lexer;

  /* interface to the lexer */
  private int yylex () {
    int yyl_return = -1;
    try {
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

  /* error reporting */
  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }

  /* lexer is created in the constructor */
  public parser(Reader r) {
    lexer = new Yylex(r, this);
  }

  /* that's how you use the parser */
  public static void main(String args[]) throws IOException {
    parser yyparser = new parser(new FileReader(args[0]));
    yyparser.yyparse();    
  }
