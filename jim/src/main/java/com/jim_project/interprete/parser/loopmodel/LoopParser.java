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



package com.jim_project.interprete.parser.loopmodel;



//#line 2 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
  import java.io.*;
  import com.jim_project.interprete.*;
  import com.jim_project.interprete.componente.*;
  import com.jim_project.interprete.parser.*;
  import com.jim_project.interprete.util.ControladorEjecucion;
//#line 23 "LoopParser.java"




public class LoopParser
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
//public class LoopParserVal is defined in LoopParserVal.java


String   yytext;//user variable to return contextual strings
LoopParserVal yyval; //used to return semantic vals from action routines
LoopParserVal yylval;//the 'lval' (result) I got from yylex()
LoopParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new LoopParserVal[YYSTACKSIZE];
  yyval=new LoopParserVal();
  yylval=new LoopParserVal();
  valptr=-1;
}
void val_push(LoopParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
LoopParserVal val_pop()
{
  if (valptr<0)
    return new LoopParserVal();
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
LoopParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new LoopParserVal();
  return valstk[ptr];
}
final LoopParserVal dup_yyval(LoopParserVal val)
{
  LoopParserVal dup = new LoopParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short FLECHA=257;
public final static short INCREMENTO=258;
public final static short LOOP=259;
public final static short END=260;
public final static short VARIABLE=261;
public final static short IDMACRO=262;
public final static short NUMERO=263;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    7,    0,    0,    8,    2,    2,    2,    2,    3,    3,
    3,    9,    3,    1,    1,    4,    4,    5,    5,    6,
    6,
};
final static short yylen[] = {                            2,
    0,    3,    0,    0,    4,    2,    2,    1,    1,    1,
    1,    0,    5,    3,    3,    1,    1,    2,    0,    3,
    0,
};
final static short yydefred[] = {                         0,
    0,    8,    0,    0,    1,    7,    6,    0,    0,    0,
    2,    0,   12,    0,   11,    5,    0,    0,    0,    0,
    0,   17,   16,   14,   15,    0,    0,    0,   18,   13,
    0,   20,
};
final static short yydgoto[] = {                          4,
   15,    5,   16,   17,   27,   29,    9,    8,   18,
};
final static short yysindex[] = {                      -251,
 -258,    0, -241,    0,    0,    0,    0, -239, -251, -250,
    0,    0,    0,    0,    0,    0,  -27,  -21, -261, -261,
 -261,    0,    0,    0,    0,  -24,  -20, -261,    0,    0,
  -24,    0,
};
final static short yyrindex[] = {                        22,
    0,    0, -234,    0,    0,    0,    0,    0,   22,    0,
    0,    1,    0,    4,    0,    0,    0,    0,    0,    0,
  -17,    0,    0,    0,    0,  -16,    0,    0,    0,    0,
  -16,    0,
};
final static short yygindex[] = {                        17,
    0,    0,    0,  -14,    0,   -4,    0,    0,    0,
};
final static int YYTABLESIZE=265;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         22,
    9,   23,    6,   10,   24,   25,   26,    1,    2,    3,
   12,   13,   14,   31,   20,   19,    7,   10,   21,   28,
   30,    3,    4,   19,   21,   11,   32,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   17,   17,    0,   16,   16,    0,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    9,
    9,    9,   10,   10,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        261,
    0,  263,  261,    0,   19,   20,   21,  259,  260,  261,
  261,  262,  263,   28,   42,   43,  258,  257,   40,   44,
   41,    0,  257,   41,   41,    9,   31,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   42,   43,   -1,   42,   43,   -1,   -1,   -1,
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
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,
  260,  261,  259,  260,  261,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=263;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
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
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"FLECHA","INCREMENTO","LOOP","END","VARIABLE","IDMACRO",
"NUMERO",
};
final static String yyrule[] = {
"$accept : inicio",
"$$1 :",
"inicio : instruccion $$1 inicio",
"inicio :",
"$$2 :",
"instruccion : VARIABLE $$2 FLECHA finInstruccion",
"instruccion : VARIABLE INCREMENTO",
"instruccion : LOOP VARIABLE",
"instruccion : END",
"finInstruccion : VARIABLE",
"finInstruccion : NUMERO",
"finInstruccion : operacion",
"$$3 :",
"finInstruccion : IDMACRO $$3 '(' parametrosMacro ')'",
"operacion : operando '+' operando",
"operacion : operando '*' operando",
"operando : NUMERO",
"operando : VARIABLE",
"parametrosMacro : operando masParametrosMacro",
"parametrosMacro :",
"masParametrosMacro : ',' operando masParametrosMacro",
"masParametrosMacro :",
};

//#line 50 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
  
  private LoopAcciones _acciones;

  /**
   * Constructor de clase.
   *
   * @param r Referencia al lector de entrada.
   * @param controladorEjecucion Referencia al controlador de ejecución en marcha.
   */
  public LoopParser(Reader r, ControladorEjecucion controladorEjecucion) {
     super(controladorEjecucion);
     _analizadorLexico = new LoopLex(r, this);
     _acciones = new LoopAcciones(_controladorEjecucion.ambito());
  }

  @Override
  public int parse() {
    return this.yyparse();
  }

  @Override
  protected int yylex() {
    int yyl_return = -1;

    try {
      yylval = new LoopParserVal(0);
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
//#line 288 "LoopParser.java"
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
//#line 23 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 3:
//#line 24 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = new LoopParserVal(); }
break;
case 4:
//#line 26 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ _acciones.variableAsignada(val_peek(0).sval); }
break;
case 5:
//#line 26 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ _acciones.asignacion(val_peek(3).sval, val_peek(0).obj); }
break;
case 6:
//#line 27 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ _acciones.incremento(val_peek(1).sval); }
break;
case 7:
//#line 28 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ _acciones.abreBucle(val_peek(0).sval, _controladorEjecucion.numeroLineaActual()); }
break;
case 8:
//#line 29 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ _acciones.cierraBucle(_controladorEjecucion.numeroLineaActual()); }
break;
case 9:
//#line 31 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = val_peek(0).sval; }
break;
case 10:
//#line 32 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 11:
//#line 33 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 12:
//#line 34 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ _acciones.llamadaAMacro(val_peek(0).sval); }
break;
case 13:
//#line 34 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = new LoopParserVal(); }
break;
case 14:
//#line 36 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.ival = _acciones.operacionBinaria('+', val_peek(2).obj, val_peek(0).obj); }
break;
case 15:
//#line 37 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.ival = _acciones.operacionBinaria('*', val_peek(2).obj, val_peek(0).obj); }
break;
case 16:
//#line 39 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = val_peek(0).ival; }
break;
case 17:
//#line 40 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = val_peek(0).sval; }
break;
case 18:
//#line 42 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = new LoopParserVal(); }
break;
case 19:
//#line 43 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = new LoopParserVal(); }
break;
case 20:
//#line 45 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = new LoopParserVal(); }
break;
case 21:
//#line 46 "C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/loopmodel/gramatica.y"
{ yyval.obj = new LoopParserVal(); }
break;
//#line 517 "LoopParser.java"
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
