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
package com.jim_project.interprete.parser.previo;

//#line 3 "gramatica.y"
import java.io.*;
import com.jim_project.interprete.*;
import com.jim_project.interprete.util.*;
import com.jim_project.interprete.componente.*;
import com.jim_project.interprete.parser.*;
import com.jim_project.interprete.util.ControladorEjecucion;
//#line 24 "PrevioParser.java"

public class PrevioParser
        extends Parser {

    boolean yydebug;        //do I want debug output?
    int yynerrs;            //number of errors so far
    int yyerrflag;          //was there an error?
    int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
    void debug(String msg) {
        if (yydebug) {
            System.out.println(msg);
        }
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

    final void state_push(int state) {
        try {
            stateptr++;
            statestk[stateptr] = state;
        } catch (ArrayIndexOutOfBoundsException e) {
            int oldsize = statestk.length;
            int newsize = oldsize * 2;
            int[] newstack = new int[newsize];
            System.arraycopy(statestk, 0, newstack, 0, oldsize);
            statestk = newstack;
            statestk[stateptr] = state;
        }
    }

    final int state_pop() {
        return statestk[stateptr--];
    }

    final void state_drop(int cnt) {
        stateptr -= cnt;
    }

    final int state_peek(int relative) {
        return statestk[stateptr - relative];
    }
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################

    final boolean init_stacks() {
        stateptr = -1;
        val_init();
        return true;
    }
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################

    void dump_stacks(int count) {
        int i;
        System.out.println("=index==state====value=     s:" + stateptr + "  v:" + valptr);
        for (i = 0; i < count; i++) {
            System.out.println(" " + i + "    " + statestk[i] + "      " + valstk[i]);
        }
        System.out.println("======================");
    }

//########## SEMANTIC VALUES ##########
//public class PrevioParserVal is defined in PrevioParserVal.java
    String yytext;//user variable to return contextual strings
    PrevioParserVal yyval; //used to return semantic vals from action routines
    PrevioParserVal yylval;//the 'lval' (result) I got from yylex()
    PrevioParserVal valstk[];
    int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################

    void val_init() {
        valstk = new PrevioParserVal[YYSTACKSIZE];
        yyval = new PrevioParserVal();
        yylval = new PrevioParserVal();
        valptr = -1;
    }

    void val_push(PrevioParserVal val) {
        if (valptr >= YYSTACKSIZE) {
            return;
        }
        valstk[++valptr] = val;
    }

    PrevioParserVal val_pop() {
        if (valptr < 0) {
            return new PrevioParserVal();
        }
        return valstk[valptr--];
    }

    void val_drop(int cnt) {
        int ptr;
        ptr = valptr - cnt;
        if (ptr < 0) {
            return;
        }
        valptr = ptr;
    }

    PrevioParserVal val_peek(int relative) {
        int ptr;
        ptr = valptr - relative;
        if (ptr < 0) {
            return new PrevioParserVal();
        }
        return valstk[ptr];
    }

    final PrevioParserVal dup_yyval(PrevioParserVal val) {
        PrevioParserVal dup = new PrevioParserVal();
        dup.ival = val.ival;
        dup.dval = val.dval;
        dup.sval = val.sval;
        dup.obj = val.obj;
        return dup;
    }
//#### end semantic value section ####
    public final static short FLECHA = 257;
    public final static short INCREMENTO = 258;
    public final static short DECREMENTO = 259;
    public final static short IF = 260;
    public final static short DISTINTO = 261;
    public final static short GOTO = 262;
    public final static short WHILE = 263;
    public final static short LOOP = 264;
    public final static short END = 265;
    public final static short ETIQUETA = 266;
    public final static short VARIABLE = 267;
    public final static short IDMACRO = 268;
    public final static short NUMERO = 269;
    public final static short YYERRCODE = 256;
    final static short yylhs[] = {-1,
        10, 0, 0, 1, 2, 2, 11, 3, 3, 3,
        3, 3, 3, 3, 3, 4, 4, 4, 12, 4,
        5, 5, 5, 5, 5, 6, 6, 7, 7, 8,
        8, 9, 9,};
    final static short yylen[] = {2,
        0, 3, 0, 2, 3, 0, 0, 4, 2, 2,
        5, 2, 2, 3, 1, 1, 1, 1, 0, 5,
        3, 3, 3, 3, 3, 1, 1, 1, 1, 2,
        0, 3, 0,};
    final static short yydefred[] = {0,
        0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
        15, 0, 4, 5, 2, 0, 12, 0, 13, 9,
        10, 0, 0, 14, 0, 0, 0, 19, 0, 8,
        18, 0, 11, 0, 0, 0, 0, 0, 0, 0,
        27, 26, 21, 22, 23, 24, 25, 29, 28, 0,
        0, 0, 30, 20, 0, 32,};
    final static short yydgoto[] = {2,
        3, 4, 13, 30, 31, 32, 50, 51, 53, 6,
        22, 34,};
    final static short yysindex[] = {-84,
        -236, 0, 0, -245, -72, -84, -235, -233, -232, -231,
        0, -230, 0, 0, 0, -227, 0, -224, 0, 0,
        0, -226, -223, 0, -263, -225, 0, 0, 0, 0,
        0, -34, 0, 2, -267, -267, -267, -267, -267, -255,
        0, 0, 0, 0, 0, 0, 0, 0, 0, -4,
        4, -255, 0, 0, -4, 0,};
    final static short yyrindex[] = {16,
        0, 0, 0, 0, 0, 16, 0, 0, 0, 0,
        0, -208, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 1, 0, 10, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 9,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 13,
        0, 0, 0, 0, 13, 0,};
    final static short yygindex[] = {45,
        0, 0, 0, 0, 0, -12, 6, 0, 5, 0,
        0, 0,};
    final static int YYTABLESIZE = 283;
    static short yytable[];

    static {
        yytable();
    }

    static void yytable() {
        yytable = new short[]{41,
            16, 42, 39, 27, 28, 29, 1, 37, 35, 17,
            36, 48, 38, 49, 7, 3, 8, 9, 10, 11,
            14, 12, 43, 44, 45, 46, 47, 20, 21, 5,
            25, 16, 17, 23, 18, 19, 24, 27, 26, 52,
            33, 40, 27, 27, 54, 27, 26, 27, 7, 31,
            15, 26, 26, 33, 26, 0, 26, 55, 0, 56,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 16, 0, 0, 0, 0, 0, 0, 0, 0,
            17, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            16, 0, 16, 16, 16, 16, 0, 16, 0, 17,
            0, 17, 17, 17, 17, 6, 17, 6, 6, 6,
            6, 0, 6,};
    }
    static short yycheck[];

    static {
        yycheck();
    }

    static void yycheck() {
        yycheck = new short[]{267,
            0, 269, 37, 267, 268, 269, 91, 42, 43, 0,
            45, 267, 47, 269, 260, 0, 262, 263, 264, 265,
            93, 267, 35, 36, 37, 38, 39, 258, 259, 266,
            257, 267, 266, 261, 267, 267, 261, 37, 262, 44,
            266, 40, 42, 43, 41, 45, 37, 47, 257, 41,
            6, 42, 43, 41, 45, -1, 47, 52, -1, 55,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 91, -1, -1, -1, -1, -1, -1, -1, -1,
            91, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            260, -1, 262, 263, 264, 265, -1, 267, -1, 260,
            -1, 262, 263, 264, 265, 260, 267, 262, 263, 264,
            265, -1, 267,};
    }
    final static short YYFINAL = 2;
    final static short YYMAXTOKEN = 269;
    final static String yyname[] = {
        "end-of-file", null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, "'%'", null, null, "'('", "')'", "'*'", "'+'",
        "','", "'-'", null, "'/'", null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        "'['", null, "']'", null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, "FLECHA", "INCREMENTO", "DECREMENTO", "IF",
        "DISTINTO", "GOTO", "WHILE", "LOOP", "END", "ETIQUETA", "VARIABLE", "IDMACRO", "NUMERO",};
    final static String yyrule[] = {
        "$accept : inicio",
        "$$1 :",
        "inicio : sentencia $$1 inicio",
        "inicio :",
        "sentencia : etiqueta instruccion",
        "etiqueta : '[' ETIQUETA ']'",
        "etiqueta :",
        "$$2 :",
        "instruccion : VARIABLE $$2 FLECHA finInstruccion",
        "instruccion : VARIABLE INCREMENTO",
        "instruccion : VARIABLE DECREMENTO",
        "instruccion : IF VARIABLE DISTINTO GOTO ETIQUETA",
        "instruccion : GOTO ETIQUETA",
        "instruccion : LOOP VARIABLE",
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
        "parametros : NUMERO",
        "parametros : VARIABLE",
        "parametrosMacro : parametros masParametrosMacro",
        "parametrosMacro :",
        "masParametrosMacro : ',' parametros masParametrosMacro",
        "masParametrosMacro :",};

//#line 71 "gramatica.y"
    private PrevioAcciones _acciones;

    public PrevioParser(Reader r, ControladorEjecucion controladorEjecucion) {
        super(controladorEjecucion);
        _analizadorLexico = new PrevioLex(r, this);
        _acciones = new PrevioAcciones(_controladorEjecucion.ambito());
        //yydebug = true;
    }

    @Override
    public int parse() {
        return this.yyparse();
    }

    /**
     * esta función se invoca por el analizador cuando necesita el ** siguiente
     * token del analizador léxico
  *
     */
    @Override
    protected int yylex() {
        int yyl_return = -1;

        try {
            yylval = new PrevioParserVal(0);
            yyl_return = _analizadorLexico.yylex();
        } catch (IOException e) {
            _programa.error().deESEnAnalizadorLexico();
        }

        return yyl_return;
    }

    /**
     * invocada cuando se produce un error
  *
     */
    @Override
    public void yyerror(String descripcion, int yystate, int token) {
        String nombreToken = yyname[token];
        _programa.error().deTokenNoEsperado(nombreToken, descripcion);
        /*
         System.err.println ("Error en línea "+Integer.toString(_analizadorLexico.lineaActual())+" : "+descripcion);
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
//#line 357 "PrevioParser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################

    void yylexdebug(int state, int ch) {
        String s = null;
        if (ch < 0) {
            ch = 0;
        }
        if (ch <= YYMAXTOKEN) //check index bounds
        {
            s = yyname[ch];    //now get it
        }
        if (s == null) {
            s = "illegal-symbol";
        }
        debug("state " + state + ", reading " + ch + " (" + s + ")");
    }

//The following are now global, to aid in error reporting
    int yyn;       //next next thing to do
    int yym;       //
    int yystate;   //current parsing state from state table
    String yys;    //current token string

//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
    int yyparse() {
        boolean doaction;
        init_stacks();
        yynerrs = 0;
        yyerrflag = 0;
        yychar = -1;          //impossible char forces a read
        yystate = 0;            //initial state
        state_push(yystate);  //save it
        val_push(yylval);     //save empty value
        while (true) //until parsing is done, either correctly, or w/error
        {
            doaction = true;
            if (yydebug) {
                debug("loop");
            }
            //#### NEXT ACTION (from reduction table)
            for (yyn = yydefred[yystate]; yyn == 0; yyn = yydefred[yystate]) {
                if (yydebug) {
                    debug("yyn:" + yyn + "  state:" + yystate + "  yychar:" + yychar);
                }
                if (yychar < 0) //we want a char?
                {
                    yychar = yylex();  //get next token
                    if (yydebug) {
                        debug(" next yychar:" + yychar);
                    }
                    //#### ERROR CHECK ####
                    if (yychar < 0) //it it didn't work/error
                    {
                        yychar = 0;      //change it to default string (no -1!)
                        if (yydebug) {
                            yylexdebug(yystate, yychar);
                        }
                    }
                }//yychar<0
                yyn = yysindex[yystate];  //get amount to shift by (shift index)
                if ((yyn != 0) && (yyn += yychar) >= 0
                        && yyn <= YYTABLESIZE && yycheck[yyn] == yychar) {
                    if (yydebug) {
                        debug("state " + yystate + ", shifting to state " + yytable[yyn]);
                    }
                    //#### NEXT STATE ####
                    yystate = yytable[yyn];//we are in a new state
                    state_push(yystate);   //save it
                    val_push(yylval);      //push our lval as the input for next rule
                    yychar = -1;           //since we have 'eaten' a token, say we need another
                    if (yyerrflag > 0) //have we recovered an error?
                    {
                        --yyerrflag;        //give ourselves credit
                    }
                    doaction = false;        //but don't process yet
                    break;   //quit the yyn=0 loop
                }

                yyn = yyrindex[yystate];  //reduce
                if ((yyn != 0) && (yyn += yychar) >= 0
                        && yyn <= YYTABLESIZE && yycheck[yyn] == yychar) {   //we reduced!
                    if (yydebug) {
                        debug("reduce");
                    }
                    yyn = yytable[yyn];
                    doaction = true; //get ready to execute
                    break;         //drop down to actions
                } else //ERROR RECOVERY
                {
                    if (yyerrflag == 0) {
                        yyerror("error sintactico", yystate, yychar);
                        yynerrs++;
                    }
                    if (yyerrflag < 3) //low error count?
                    {
                        yyerrflag = 3;
                        while (true) //do until break
                        {
                            if (stateptr < 0) //check for under & overflow here
                            {
                                yyerror("stack underflow. aborting...");  //note lower case 's'
                                return 1;
                            }
                            yyn = yysindex[state_peek(0)];
                            if ((yyn != 0) && (yyn += YYERRCODE) >= 0
                                    && yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE) {
                                if (yydebug) {
                                    debug("state " + state_peek(0) + ", error recovery shifting to state " + yytable[yyn] + " ");
                                }
                                yystate = yytable[yyn];
                                state_push(yystate);
                                val_push(yylval);
                                doaction = false;
                                break;
                            } else {
                                if (yydebug) {
                                    debug("error recovery discarding state " + state_peek(0) + " ");
                                }
                                if (stateptr < 0) //check for under & overflow here
                                {
                                    yyerror("Stack underflow. aborting...");  //capital 'S'
                                    return 1;
                                }
                                state_pop();
                                val_pop();
                            }
                        }
                    } else //discard this token
                    {
                        if (yychar == 0) {
                            return 1; //yyabort
                        }
                        if (yydebug) {
                            yys = null;
                            if (yychar <= YYMAXTOKEN) {
                                yys = yyname[yychar];
                            }
                            if (yys == null) {
                                yys = "illegal-symbol";
                            }
                            debug("state " + yystate + ", error recovery discards token " + yychar + " (" + yys + ")");
                        }
                        yychar = -1;  //read another
                    }
                }//end error recovery
            }//yyn=0 loop
            if (!doaction) //any reason not to proceed?
            {
                continue;      //skip action
            }
            yym = yylen[yyn];          //get count of terminals on rhs
            if (yydebug) {
                debug("state " + yystate + ", reducing " + yym + " by rule " + yyn + " (" + yyrule[yyn] + ")");
            }
            if (yym > 0) //if count of rhs not 'nil'
            {
                yyval = val_peek(yym - 1); //get current semantic value
            }
            yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
            switch (yyn) {
//########## USER-SUPPLIED ACTIONS ##########
                case 1: //#line 29 "gramatica.y"
                {
                    yyval.sval = val_peek(0).sval;
                }
                break;
                case 3: //#line 30 "gramatica.y"
                {
                    yyval.sval = null;
                }
                break;
                case 4: //#line 32 "gramatica.y"
                {;
                }
                break;
                case 5: //#line 34 "gramatica.y"
                {
                    _acciones.definirEtiqueta(val_peek(1).sval, _controladorEjecucion.numeroLineaActual());
                }
                break;
                case 6: //#line 35 "gramatica.y"
                {;
                }
                break;
                case 7: //#line 37 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(0).sval);
                }
                break;
                case 9: //#line 38 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(1).sval);
                }
                break;
                case 10: //#line 39 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(1).sval);
                }
                break;
                case 11: //#line 40 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(3).sval);
                }
                break;
                case 12: //#line 41 "gramatica.y"
                {;
                }
                break;
                case 13: //#line 42 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(0).sval);
                    _acciones.definirInicioBucle(_controladorEjecucion.numeroLineaActual());
                }
                break;
                case 14: //#line 43 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(1).sval);
                    _acciones.definirInicioBucle(_controladorEjecucion.numeroLineaActual());
                }
                break;
                case 15: //#line 44 "gramatica.y"
                {
                    _acciones.definirFinBucle(_controladorEjecucion.numeroLineaActual());
                }
                break;
                case 16: //#line 46 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(0).sval);
                }
                break;
                case 17: //#line 47 "gramatica.y"
                {;
                }
                break;
                case 18: //#line 48 "gramatica.y"
                {;
                }
                break;
                case 19: //#line 49 "gramatica.y"
                {
                    _acciones.definirLlamadaAMacro(val_peek(0).sval);
                }
                break;
                case 21: //#line 51 "gramatica.y"
                {;
                }
                break;
                case 22: //#line 52 "gramatica.y"
                {;
                }
                break;
                case 23: //#line 53 "gramatica.y"
                {;
                }
                break;
                case 24: //#line 54 "gramatica.y"
                {;
                }
                break;
                case 25: //#line 55 "gramatica.y"
                {;
                }
                break;
                case 26: //#line 57 "gramatica.y"
                {;
                }
                break;
                case 27: //#line 58 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(0).sval);
                }
                break;
                case 28: //#line 60 "gramatica.y"
                {
                    _acciones.definirVariableEntradaMacro(val_peek(0).sval);
                }
                break;
                case 29: //#line 61 "gramatica.y"
                {
                    _acciones.definirVariable(val_peek(0).sval);
                    _acciones.definirVariableEntradaMacro(val_peek(0).sval);
                }
                break;
                case 30: //#line 63 "gramatica.y"
                {;
                }
                break;
                case 31: //#line 64 "gramatica.y"
                {;
                }
                break;
                case 32: //#line 66 "gramatica.y"
                {;
                }
                break;
                case 33: //#line 67 "gramatica.y"
                {;
                }
                break;
//#line 626 "PrevioParser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
            }//switch
            //#### Now let's reduce... ####
            if (yydebug) {
                debug("reduce");
            }
            state_drop(yym);             //we just reduced yylen states
            yystate = state_peek(0);     //get new state
            val_drop(yym);               //corresponding value drop
            yym = yylhs[yyn];            //select next TERMINAL(on lhs)
            if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
            {
                if (yydebug) {
                    debug("After reduction, shifting from state 0 to state " + YYFINAL + "");
                }
                yystate = YYFINAL;         //explicitly say we're done
                state_push(YYFINAL);       //and save it
                val_push(yyval);           //also save the semantic value of parsing
                if (yychar < 0) //we want another character?
                {
                    yychar = yylex();        //get next character
                    if (yychar < 0) {
                        yychar = 0;  //clean, if necessary
                    }
                    if (yydebug) {
                        yylexdebug(yystate, yychar);
                    }
                }
                if (yychar == 0) //Good exit (if lex returns 0 ;-)
                {
                    break;                 //quit the loop--all DONE
                }
            }//if yystate
            else //else not done yet
            {                         //get next state and push, for next yydefred[]
                yyn = yygindex[yym];      //find out where to go
                if ((yyn != 0) && (yyn += yystate) >= 0
                        && yyn <= YYTABLESIZE && yycheck[yyn] == yystate) {
                    yystate = yytable[yyn]; //get new state
                } else {
                    yystate = yydgoto[yym]; //else go to new defred
                }
                if (yydebug) {
                    debug("after reduction, shifting from state " + state_peek(0) + " to state " + yystate + "");
                }
                state_push(yystate);     //going again, so push state & val...
                val_push(yyval);         //for next action
            }
        }//main loop
        return 0;//yyaccept!!
    }
//## end of method parse() ######################################

//## run() --- for Thread #######################################
    /**
     * A default run method, used for operating this parser object in the
     * background. It is intended for extending Thread or implementing Runnable.
     * Turn off with -Jnorun .
     */
    public void run() {
        yyparse();
    }
//## end of method run() ########################################

//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################
}
//################### END OF CLASS ##############################
