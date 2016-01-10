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



package com.jim_project.interprete.parser.whilemodel;



//#line 3 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
  import java.io.*;
  import com.jim_project.interprete.*;
  import com.jim_project.interprete.componente.*;
  import com.jim_project.interprete.parser.*;
//#line 22 "WhileParser.java"




public class WhileParser
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
//public class WhileParserVal is defined in WhileParserVal.java


String   yytext;//user variable to return contextual strings
WhileParserVal yyval; //used to return semantic vals from action routines
WhileParserVal yylval;//the 'lval' (result) I got from yylex()
WhileParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new WhileParserVal[YYSTACKSIZE];
  yyval=new WhileParserVal();
  yylval=new WhileParserVal();
  valptr=-1;
}
void val_push(WhileParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
WhileParserVal val_pop()
{
  if (valptr<0)
    return new WhileParserVal();
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
WhileParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new WhileParserVal();
  return valstk[ptr];
}
final WhileParserVal dup_yyval(WhileParserVal val)
{
  WhileParserVal dup = new WhileParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short FLECHA=257;
public final static short INCREMENTO=258;
public final static short DECREMENTO=259;
public final static short WHILE=260;
public final static short DISTINTO=261;
public final static short END=262;
public final static short VARIABLE=263;
public final static short IDMACRO=264;
public final static short NUMERO=265;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    7,    0,    0,    8,    2,    2,    2,    2,    2,    3,
    3,    3,    9,    3,    1,    1,    1,    1,    1,    4,
    4,   10,    5,    5,   11,    6,    6,
};
final static short yylen[] = {                            2,
    0,    3,    0,    0,    4,    2,    2,    3,    1,    1,
    1,    1,    0,    5,    3,    3,    3,    3,    3,    1,
    1,    0,    3,    0,    0,    4,    0,
};
final static short yydefred[] = {                         0,
    0,    9,    0,    0,    1,    0,    6,    7,    0,    0,
    8,    0,    2,    0,   13,    0,   12,    5,    0,    0,
    0,    0,    0,    0,    0,    0,   21,   20,   15,   16,
   17,   18,   19,   22,    0,    0,   14,    0,   23,   25,
    0,   26,
};
final static short yydgoto[] = {                          4,
   17,    5,   18,   19,   35,   39,   10,    9,   20,   36,
   41,
};
final static short yysindex[] = {                      -251,
 -263,    0, -245,    0,    0, -244,    0,    0, -232, -251,
    0, -242,    0,    0,    0,    0,    0,    0,  -27,  -13,
 -239, -239, -239, -239, -239, -239,    0,    0,    0,    0,
    0,    0,    0,    0,  -12,  -16,    0, -239,    0,    0,
  -16,    0,
};
final static short yyrindex[] = {                        30,
    0,    0, -226,    0,    0,    0,    0,    0,    0,   30,
    0,    0,    0,    1,    0,    8,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -8,    0,    0,    0,    0,
   -8,    0,
};
final static short yygindex[] = {                        24,
    0,    0,    0,  -19,    0,   -6,    0,    0,    0,    0,
    0,
};
final static int YYTABLESIZE=271;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          6,
   10,   29,   30,   31,   32,   33,   34,   11,    1,   25,
    2,    3,    7,    8,   23,   21,   11,   22,   40,   24,
   14,   15,   16,   27,   12,   28,   26,   38,   37,    3,
    4,   24,   27,   13,   42,    0,    0,   21,    0,    0,
    0,    0,   21,   21,   20,   21,    0,   21,    0,   20,
   20,    0,   20,    0,   20,    0,    0,    0,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,    0,   10,   10,    0,    0,    0,   11,    0,   11,
   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        263,
    0,   21,   22,   23,   24,   25,   26,    0,  260,   37,
  262,  263,  258,  259,   42,   43,  261,   45,   38,   47,
  263,  264,  265,  263,  257,  265,   40,   44,   41,    0,
  257,   41,   41,   10,   41,   -1,   -1,   37,   -1,   -1,
   -1,   -1,   42,   43,   37,   45,   -1,   47,   -1,   42,
   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,
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
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  260,   -1,  262,  263,   -1,   -1,   -1,  260,   -1,  262,
  263,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=265;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"FLECHA","INCREMENTO","DECREMENTO","WHILE",
"DISTINTO","END","VARIABLE","IDMACRO","NUMERO",
};
final static String yyrule[] = {
"$accept : inicio",
"$$1 :",
"inicio : instruccion $$1 inicio",
"inicio :",
"$$2 :",
"instruccion : VARIABLE $$2 FLECHA finInstruccion",
"instruccion : VARIABLE INCREMENTO",
"instruccion : VARIABLE DECREMENTO",
"instruccion : WHILE VARIABLE DISTINTO",
"instruccion : END",
"finInstruccion : VARIABLE",
"finInstruccion : NUMERO",
"finInstruccion : operacion",
"$$3 :",
"finInstruccion : IDMACRO $$3 '(' parametrosMacro ')'",
"operacion : operando '+' operando",
"operacion : operando '-' operando",
"operacion : operando '*' operando",
"operacion : operando '/' operando",
"operacion : operando '%' operando",
"operando : NUMERO",
"operando : VARIABLE",
"$$4 :",
"parametrosMacro : operando $$4 masParametrosMacro",
"parametrosMacro :",
"$$5 :",
"masParametrosMacro : ',' operando $$5 masParametrosMacro",
"masParametrosMacro :",
};

//#line 56 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"

  private WhileAcciones _acciones;

  /**
   * Constructor de clase.
   *
   * @param r Referencia al lector de entrada.
   * @param controladorEjecucion Referencia al controlador de ejecución en marcha.
   */
  public WhileParser(Reader r, ControladorEjecucion controladorEjecucion) {
     super(controladorEjecucion);
     _analizadorLexico = new WhileLex(r, this);
     _acciones = new WhileAcciones(_controladorEjecucion.ambito());
  }

  @Override
  public int parse() {
    return this.yyparse();
  }

  @Override
  protected int yylex() {
    int yyl_return = -1;

    try {
      yylval = new WhileParserVal(0);
      yyl_return = _analizadorLexico.yylex();
    } catch (IOException e) {
      _programa.error().deESEnAnalizadorLexico();
    }

    return yyl_return;
  }

  @Override
  public void yyerror(String descripcion, int yystate, int token) {
    String nombreToken = yyname[token];
    _programa.error().deTokenNoEsperado(nombreToken, descripcion);
  }
//#line 302 "WhileParser.java"
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
        yyerror("syntax error");
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
//#line 25 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 3:
//#line 26 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = new WhileParserVal(); }
break;
case 4:
//#line 28 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ _acciones.variableAsignada(val_peek(0).sval); }
break;
case 5:
//#line 28 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ _acciones.asignacion(val_peek(3).sval, val_peek(0).obj); }
break;
case 6:
//#line 29 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ _acciones.incremento(val_peek(1).sval); }
break;
case 7:
//#line 30 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ _acciones.decremento(val_peek(1).sval); }
break;
case 8:
//#line 31 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ _acciones.abreBucle(val_peek(1).sval, _controladorEjecucion.numeroLineaActual()); }
break;
case 9:
//#line 32 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ _acciones.cierraBucle(_controladorEjecucion.numeroLineaActual()); }
break;
case 10:
//#line 34 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = val_peek(0).sval; }
break;
case 11:
//#line 35 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 12:
//#line 36 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 13:
//#line 37 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ _acciones.llamadaAMacro(val_peek(0).sval); }
break;
case 14:
//#line 37 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = new WhileParserVal(); }
break;
case 15:
//#line 39 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.ival = _acciones.operacionBinaria('+', val_peek(2).obj, val_peek(0).obj); }
break;
case 16:
//#line 40 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.ival = _acciones.operacionBinaria('-', val_peek(2).obj, val_peek(0).obj); }
break;
case 17:
//#line 41 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.ival = _acciones.operacionBinaria('*', val_peek(2).obj, val_peek(0).obj); }
break;
case 18:
//#line 42 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.ival = _acciones.operacionBinaria('/', val_peek(2).obj, val_peek(0).obj); }
break;
case 19:
//#line 43 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.ival = _acciones.operacionBinaria('%', val_peek(2).obj, val_peek(0).obj); }
break;
case 20:
//#line 45 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 21:
//#line 46 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = val_peek(0).sval; }
break;
case 22:
//#line 48 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{yyval.obj = val_peek(0).obj; }
break;
case 23:
//#line 48 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = new WhileParserVal(); }
break;
case 24:
//#line 49 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = new WhileParserVal(); }
break;
case 25:
//#line 51 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 26:
//#line 51 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = new WhileParserVal(); }
break;
case 27:
//#line 52 "C:\Users\alber_000\Documents\NetBeansProjects\tfg-int-rpretes\jim/src/main/java/com/jim_project/interprete/parser/whilemodel/gramatica.y"
{ yyval.obj = new WhileParserVal(); }
break;
//#line 555 "WhileParser.java"
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
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
