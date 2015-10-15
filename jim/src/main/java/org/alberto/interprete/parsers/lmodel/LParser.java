//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package org.alberto.interprete.parsers.lmodel;



//#line 3 "gramatica.y"
  import java.io.*;
  import org.alberto.interprete.*;
  import org.alberto.interprete.parsers.*;
//#line 21 "LParser.java"




public class LParser
             extends Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class LParserVal is defined in LParserVal.java


String   yytext;//user variable to return contextual strings
LParserVal yyval; //used to return semantic vals from action routines
LParserVal yylval;//the 'lval' (result) I got from yylex()
LParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new LParserVal[YYSTACKSIZE];
  yyval=new LParserVal();
  yylval=new LParserVal();
  valptr=-1;
}
void val_push(LParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
LParserVal val_pop()
{
  if (valptr<0)
    return new LParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
LParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new LParserVal();
  return valstk[ptr];
}
final LParserVal dup_yyval(LParserVal val)
{
  LParserVal dup = new LParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ETIQUETA=257;
public final static short VARIABLE=258;
public final static short FLECHA=259;
public final static short INCREMENTO=260;
public final static short DECREMENTO=261;
public final static short IF=262;
public final static short DISTINTO=263;
public final static short GOTO=264;
public final static short NUMERO=265;
public final static short IDMACRO=266;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    9,    0,    0,    4,    5,    5,    6,    6,    6,    6,
    6,    2,    2,    2,   10,    2,    3,    3,    3,    3,
    3,    1,    1,   11,    7,    7,   12,    8,    8,
};
final static short yylen[] = {                            2,
    0,    3,    0,    2,    3,    0,    3,    2,    2,    5,
    2,    1,    1,    1,    0,    5,    3,    3,    3,    3,
    3,    1,    1,    0,    3,    0,    0,    4,    0,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    4,
    5,    2,    0,    8,    9,    0,   11,    0,    0,   15,
    0,    7,   14,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,   22,   17,   18,   19,   20,   21,   10,
   24,    0,    0,   16,    0,   25,   27,    0,   28,
};
final static short yydgoto[] = {                          2,
   21,   22,   23,    3,    4,   10,   42,   46,    6,   25,
   43,   48,
};
final static short yysindex[] = {                       -86,
 -251,    0,    0, -240,  -76,  -86, -257, -230, -227,    0,
    0,    0, -239,    0,    0, -232,    0,    0,    0,    0,
  -22,    0,    0, -231,   -8, -258, -258, -258, -258, -258,
 -223, -258,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -6,   -7,    0, -258,    0,    0,   -7,    0,
};
final static short yyrindex[] = {                         9,
    0,    0,    0,    0,    0,    9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    8,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -5,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -2,    0,    0,    0,    0,   -2,    0,
};
final static short yygindex[] = {                        34,
  -16,    0,    0,    0,    0,    0,    0,   -1,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=273;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         33,
   12,   13,   14,   15,    1,    5,   34,   13,    3,   35,
   36,   37,   38,   39,   30,   41,   11,    7,   18,   28,
   26,    8,   27,    9,   29,   19,   20,   16,   47,   17,
   24,   32,   31,   40,   44,   26,   45,   23,   29,   12,
    0,    0,   23,   23,   22,   23,   49,   23,    0,   22,
   22,    0,   22,    0,   22,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   12,    0,    0,    0,    0,    0,    0,   13,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   12,    0,
    0,    0,   12,    0,   12,   13,    6,    0,    0,   13,
    6,   13,    6,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        258,
    0,  259,  260,  261,   91,  257,  265,    0,    0,   26,
   27,   28,   29,   30,   37,   32,   93,  258,  258,   42,
   43,  262,   45,  264,   47,  265,  266,  258,   45,  257,
  263,   40,  264,  257,   41,   41,   44,   37,   41,    6,
   -1,   -1,   42,   43,   37,   45,   48,   47,   -1,   42,
   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,   -1,
   -1,   -1,  262,   -1,  264,  258,  258,   -1,   -1,  262,
  262,  264,  264,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=266;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ETIQUETA","VARIABLE","FLECHA","INCREMENTO",
"DECREMENTO","IF","DISTINTO","GOTO","NUMERO","IDMACRO",
};
final static String yyrule[] = {
"$accept : inicio",
"$$1 :",
"inicio : sentencia $$1 inicio",
"inicio :",
"sentencia : etiqueta instruccion",
"etiqueta : '[' ETIQUETA ']'",
"etiqueta :",
"instruccion : VARIABLE FLECHA finInstruccion",
"instruccion : VARIABLE INCREMENTO",
"instruccion : VARIABLE DECREMENTO",
"instruccion : IF VARIABLE DISTINTO GOTO ETIQUETA",
"instruccion : GOTO ETIQUETA",
"finInstruccion : VARIABLE",
"finInstruccion : NUMERO",
"finInstruccion : operacion",
"$$2 :",
"finInstruccion : IDMACRO $$2 '(' parametrosMacro ')'",
"operacion : parametros '+' parametros",
"operacion : parametros '-' parametros",
"operacion : parametros '*' parametros",
"operacion : parametros '/' parametros",
"operacion : parametros '%' parametros",
"parametros : NUMERO",
"parametros : VARIABLE",
"$$3 :",
"parametrosMacro : parametros $$3 masParametrosMacro",
"parametrosMacro :",
"$$4 :",
"masParametrosMacro : ',' parametros $$4 masParametrosMacro",
"masParametrosMacro :",
};

//#line 64 "gramatica.y"

  /** referencia al analizador léxico
  **/
  private LLex analex;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public LParser(Reader r) {
     analex = new LLex(r, this);
     //yydebug = true;
  }

  public int parse() {
    return this.yyparse();
  }

  public AnalizadorLexico analizadorLexico() {

    return analex;
  }

  /** esta función se invoca por el analizador cuando necesita el 
  *** siguiente token del analizador léxico
  **/
  protected int yylex () {
  int yyl_return = -1;

  try {
    yylval = new LParserVal(0);
    yyl_return = analex.yylex();
  } catch (IOException e) {
    org.alberto.interprete.Error.deESenAnalizadorLexico();
  }

  return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) {
    String nombreToken = yyname[token];
    org.alberto.interprete.Error.deTokenNoEsperado(nombreToken, descripcion);
    /*
  System.err.println ("Error en línea "+Integer.toString(analex.lineaActual())+" : "+descripcion);
  System.err.println ("Token leído : "+yyname[token]);
  System.err.print("Token(s) que se esperaba(n) : ");

  String  nombresTokens = "" ;

  int yyn ;

   // añadir en 'nombresTokens' los tokens que permitirian desplazar

  nombresTokens += "desplazan: " ;

  for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
  {
    yyn = yysindex[yystate] ;  
    if ((yyn != 0) && (yyn += yychar) >= 0 &&
      yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
      nombresTokens += yyname[yychar] + " ";
    }
  }

   // añadir tokens que permitirian reducir

  nombresTokens += "reducen: " ;

  for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
  {
    yyn = yyrindex[yystate] ;  
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
      yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
      nombresTokens += yyname[yychar] + " " ;
    }
  }

  System.err.println(nombresTokens);
  */
  }
//#line 347 "LParser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("error sintactico",yystate,yychar);
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 28 "gramatica.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 3:
//#line 29 "gramatica.y"
{ yyval.obj = new LParserVal(); }
break;
case 4:
//#line 31 "gramatica.y"
{ yyval.obj = new LParserVal(); }
break;
case 5:
//#line 33 "gramatica.y"
{ yyval.obj = val_peek(1).sval; }
break;
case 6:
//#line 34 "gramatica.y"
{ yyval.obj = new LParserVal(); }
break;
case 7:
//#line 36 "gramatica.y"
{ LAcciones.asignacion(val_peek(2).sval, val_peek(0).obj); }
break;
case 8:
//#line 37 "gramatica.y"
{ LAcciones.incremento(val_peek(1).sval); }
break;
case 9:
//#line 38 "gramatica.y"
{ LAcciones.decremento(val_peek(1).sval); }
break;
case 10:
//#line 39 "gramatica.y"
{ LAcciones.saltoCondicional(val_peek(3).sval, val_peek(0).sval); }
break;
case 11:
//#line 40 "gramatica.y"
{ LAcciones.saltoIncondicional(val_peek(0).sval); }
break;
case 12:
//#line 42 "gramatica.y"
{ yyval.obj = val_peek(0).sval; }
break;
case 13:
//#line 43 "gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 14:
//#line 44 "gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 15:
//#line 45 "gramatica.y"
{ yyval.obj = new LParserVal(); }
break;
case 16:
//#line 45 "gramatica.y"
{ /* Tratamiento de macros */ }
break;
case 17:
//#line 47 "gramatica.y"
{ yyval.ival = LAcciones.operacion('+', val_peek(2).obj, val_peek(0).obj); }
break;
case 18:
//#line 48 "gramatica.y"
{ yyval.ival = LAcciones.operacion('-', val_peek(2).obj, val_peek(0).obj); }
break;
case 19:
//#line 49 "gramatica.y"
{ yyval.ival = LAcciones.operacion('*', val_peek(2).obj, val_peek(0).obj); }
break;
case 20:
//#line 50 "gramatica.y"
{ yyval.ival = LAcciones.operacion('/', val_peek(2).obj, val_peek(0).obj); }
break;
case 21:
//#line 51 "gramatica.y"
{ yyval.ival = LAcciones.operacion('%', val_peek(2).obj, val_peek(0).obj); }
break;
case 22:
//#line 53 "gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 23:
//#line 54 "gramatica.y"
{ yyval.obj = val_peek(0).sval; }
break;
case 24:
//#line 56 "gramatica.y"
{yyval.obj = val_peek(0).obj; }
break;
case 26:
//#line 57 "gramatica.y"
{ yyval.obj = new LParserVal(); }
break;
case 27:
//#line 59 "gramatica.y"
{yyval.obj = val_peek(0).obj; }
break;
case 29:
//#line 60 "gramatica.y"
{ yyval.obj = new LParserVal(); }
break;
//#line 600 "LParser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public LParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public LParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
