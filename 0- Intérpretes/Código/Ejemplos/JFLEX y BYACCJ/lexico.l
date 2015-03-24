
%%

%byaccj

%{
  /* store a reference to the parser object */
  private parser yyparser;

  /* constructor taking an additional parser object */
  public Yylex(java.io.Reader r, parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

NUM = [0-9]+ ("." [0-9]+)?
NL  = \n | \r | \r\n

%%

/* operators */
"+" | 
..
"(" | 
")"    { return (int) yycharat(0); }

/* newline */
{NL}   { return parser.NL; }

/* float */
{NUM}  { yyparser.yylval = new parserval(Double.parseDouble(yytext()));
         return parser.NUM; }
