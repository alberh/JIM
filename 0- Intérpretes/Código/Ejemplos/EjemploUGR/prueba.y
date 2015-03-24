// fuente byaccj para una calculadora sencilla
 

%{
  import java.io.*;
%}
      


// lista de tokens por orden de prioridad

%token CONSTANTE  // constante (entero o flotante)
%token ABR_PARENT // abrir paréntesis
%token CER_PARENT // cerrar paréntesis
%token ASIG       // simbolo usado para la asignación
%token IDENTIFICADOR 
%token PYC  // punto y coma
%left  OP_MAS_MENOS // operadores binarios de menos prioridad
%left  OP_MULT_DIV   // operadores binarios de mas prioridad  
%token  PEPE            // token para hacer pruebas
      
%%

lista_sent 
       :  lista_sent sentencia
       |
       ;
sentencia 
       :  expr PYC  
                {  System.out.println($1.obj.toString()); 
                }
       |  IDENTIFICADOR ASIG expr PYC
                {  Acc.Asignacion($1.obj,$3.obj) ;
                }
       |  PEPE PYC 
       |  error PYC 
       ;
expr   
       :  CONSTANTE          
                {  $$.obj = $1.obj ; 
                } 
       |  IDENTIFICADOR
                {  $$.obj = Acc.LeerValorVariable($1.obj) ; 
                }      
       |  expr OP_MAS_MENOS expr   
                {  $$.obj = Acc.Operador($2.obj,$1.obj,$3.obj); 
                }    
       |  expr OP_MULT_DIV expr   
                {  $$.obj = Acc.Operador($2.obj,$1.obj,$3.obj); 
                }
       |   OP_MAS_MENOS expr   
                {  $$.obj = Acc.Operador($1.obj,$2.obj); 
                }
       |  ABR_PARENT expr CER_PARENT  
                {  $$.obj = $2.obj ; 
                }     
       ;

%%

  /** referencia al analizador léxico
  **/
  private Yylex analex ;

  /** constructor: crea el analizador léxico (lexer)
  **/
  public Parser(Reader r) 
  {
     analex = new Yylex(r, this);
     //yydebug = true ;
  }

  /** esta función se invoca por el analizador cuando necesita el 
  *** siguiente token del analizador léxico
  **/
  private int yylex () 
  {
    int yyl_return = -1;

    try 
    {
       yylval = new ParserVal(0);
       yyl_return = analex.yylex();
    }
    catch (IOException e) 
    {
       System.err.println("error de E/S:"+e);
    }

    return yyl_return;
  }

  /** invocada cuando se produce un error
  **/
  public void yyerror (String descripcion, int yystate, int token) 
  {
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
    
  }

  public void yyerror (String descripcion) 
  {
     System.err.println ("Error en línea "+Integer.toString(analex.lineaActual())+" : "+descripcion);
     //System.err.println ("Token leido : "+yyname[token]);
   
  }





