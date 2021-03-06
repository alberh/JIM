/* The following code was generated by JFlex 1.6.1 */

package com.jim_project.interprete.parser.analizadormacros;

import com.jim_project.interprete.parser.AnalizadorLexico;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.componente.Etiqueta;
import java.lang.StringBuilder;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1
 * from the specification file <tt>C:/Users/alber_000/Documents/NetBeansProjects/tfg-int-rpretes/jim/src/main/java/com/jim_project/interprete/parser/analizadormacros/lexico.l</tt>
 */
public class MacrosLex extends AnalizadorLexico {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int CUERPO = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\43\1\44\1\1\22\0\1\3\2\0\1\5"+
    "\14\0\1\10\11\7\7\0\3\6\1\36\1\26\1\16\1\47\1\24"+
    "\1\15\2\4\1\25\1\4\1\35\1\31\1\32\3\4\1\50\2\4"+
    "\1\23\1\11\1\12\1\11\1\51\1\0\1\51\3\0\1\40\1\6"+
    "\1\41\1\34\1\22\1\14\1\45\1\20\1\13\2\4\1\21\1\37"+
    "\1\33\1\27\1\30\1\4\1\42\1\4\1\46\2\4\1\17\1\11"+
    "\1\12\1\11\12\0\1\43\u1fa2\0\1\43\1\43\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\udfe6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\1\3\1\1\1\3\1\1\1\4"+
    "\2\5\1\4\1\3\1\6\2\7\4\4\1\6\2\4"+
    "\2\6\2\4\1\10\1\11\1\3\1\11\1\12\1\6"+
    "\1\7\11\12\1\11\2\12\1\4\5\12\1\11\4\12"+
    "\1\13\1\11\2\12\1\11\2\12\1\11\2\12\1\14"+
    "\1\15\1\16";

  private static int [] zzUnpackAction() {
    int [] result = new int[71];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\52\0\124\0\176\0\124\0\124\0\250\0\322"+
    "\0\374\0\124\0\u0126\0\124\0\u0150\0\u017a\0\u01a4\0\u01ce"+
    "\0\u0150\0\u01f8\0\u0222\0\u024c\0\u0276\0\u02a0\0\u02ca\0\u02f4"+
    "\0\u031e\0\u0348\0\u0372\0\u039c\0\124\0\250\0\u03c6\0\u03f0"+
    "\0\u0150\0\u041a\0\u0444\0\u046e\0\u0498\0\u04c2\0\u04ec\0\u0516"+
    "\0\u0540\0\u056a\0\u0594\0\u05be\0\u05e8\0\u0612\0\u063c\0\u0666"+
    "\0\u0690\0\u06ba\0\u06e4\0\u070e\0\u0738\0\u0762\0\u078c\0\u07b6"+
    "\0\u07e0\0\u080a\0\u0150\0\u0834\0\u085e\0\u0888\0\u08b2\0\u08dc"+
    "\0\u0906\0\u0930\0\u095a\0\u0984\0\250\0\u0150\0\u0150";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[71];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\1\6\1\7\1\10\1\7\2\3"+
    "\23\7\1\11\6\7\1\0\1\6\4\7\1\3\1\12"+
    "\1\13\1\14\1\12\1\15\1\16\1\17\2\12\1\20"+
    "\1\21\1\22\1\15\1\23\1\15\1\24\1\15\1\25"+
    "\1\26\1\27\1\15\1\30\1\31\5\15\1\32\1\15"+
    "\1\17\1\15\2\17\1\15\2\12\1\33\1\15\1\34"+
    "\1\15\1\35\54\0\1\5\53\0\1\36\1\0\1\36"+
    "\2\0\32\36\2\0\4\36\1\0\1\10\1\37\1\6"+
    "\47\10\4\0\1\36\1\0\1\36\2\0\11\36\1\40"+
    "\20\36\2\0\4\36\3\0\1\14\53\0\1\41\1\0"+
    "\1\41\2\0\32\41\2\0\4\41\1\0\1\16\2\0"+
    "\40\16\2\0\5\16\4\0\1\41\1\0\1\41\1\42"+
    "\1\0\32\41\2\0\4\41\5\0\1\41\1\0\1\41"+
    "\1\43\1\0\32\41\2\0\4\41\5\0\1\41\1\0"+
    "\1\41\2\0\3\41\1\15\26\41\2\0\4\41\5\0"+
    "\1\41\1\0\1\41\2\0\5\41\1\15\24\41\2\0"+
    "\4\41\5\0\1\41\1\0\1\41\2\0\7\41\1\44"+
    "\22\41\2\0\4\41\5\0\1\41\1\0\1\41\2\0"+
    "\16\41\1\45\13\41\2\0\4\41\5\0\1\41\1\0"+
    "\1\41\1\42\1\0\22\41\1\46\7\41\2\0\4\41"+
    "\5\0\1\41\1\0\1\41\2\0\13\41\1\47\16\41"+
    "\2\0\4\41\5\0\1\41\1\0\1\41\2\0\20\41"+
    "\1\50\11\41\2\0\4\41\5\0\1\41\1\0\1\41"+
    "\1\42\1\0\24\41\1\51\5\41\2\0\4\41\5\0"+
    "\1\41\1\0\1\41\1\42\1\0\11\41\1\52\20\41"+
    "\2\0\4\41\5\0\1\41\1\0\1\41\2\0\16\41"+
    "\1\53\13\41\2\0\4\41\5\0\1\41\1\0\1\41"+
    "\2\0\20\41\1\54\11\41\2\0\4\41\3\0\1\6"+
    "\53\0\1\36\1\0\1\36\2\0\3\36\1\55\26\36"+
    "\2\0\4\36\10\0\2\42\50\0\2\43\45\0\1\41"+
    "\1\0\1\41\2\0\2\41\1\56\27\41\2\0\4\41"+
    "\5\0\1\41\1\0\1\41\2\0\16\41\1\57\13\41"+
    "\2\0\4\41\5\0\1\41\1\0\1\41\2\0\23\41"+
    "\1\60\6\41\2\0\4\41\5\0\1\41\1\0\1\41"+
    "\2\0\4\41\1\61\25\41\2\0\4\41\5\0\1\41"+
    "\1\0\1\41\2\0\20\41\1\62\11\41\2\0\4\41"+
    "\5\0\1\41\1\0\1\41\2\0\25\41\1\15\4\41"+
    "\2\0\4\41\5\0\1\41\1\0\1\41\2\0\3\41"+
    "\1\63\26\41\2\0\4\41\5\0\1\41\1\0\1\41"+
    "\2\0\32\41\2\0\1\41\1\64\2\41\5\0\1\41"+
    "\1\0\1\41\2\0\32\41\2\0\3\41\1\65\5\0"+
    "\1\36\1\0\1\36\2\0\26\36\1\66\3\36\2\0"+
    "\4\36\5\0\1\41\1\0\1\41\2\0\10\41\1\67"+
    "\21\41\2\0\4\41\5\0\1\41\1\0\1\41\2\0"+
    "\17\41\1\15\12\41\2\0\4\41\5\0\1\41\1\0"+
    "\1\41\2\0\26\41\1\70\3\41\2\0\4\41\5\0"+
    "\1\41\1\0\1\41\2\0\14\41\1\71\15\41\2\0"+
    "\4\41\5\0\1\41\1\0\1\41\2\0\21\41\1\15"+
    "\10\41\2\0\4\41\5\0\1\41\1\0\1\41\2\0"+
    "\26\41\1\72\3\41\2\0\4\41\5\0\1\41\1\0"+
    "\1\41\2\0\16\41\1\73\13\41\2\0\4\41\5\0"+
    "\1\41\1\0\1\41\2\0\20\41\1\73\11\41\2\0"+
    "\4\41\5\0\1\36\1\0\1\36\2\0\27\36\1\74"+
    "\2\36\2\0\4\36\5\0\1\41\1\0\1\41\2\0"+
    "\11\41\1\15\20\41\2\0\4\41\5\0\1\41\1\0"+
    "\1\41\2\0\27\41\1\75\2\41\2\0\4\41\5\0"+
    "\1\41\1\0\1\41\2\0\15\41\1\15\14\41\2\0"+
    "\4\41\5\0\1\41\1\0\1\41\2\0\27\41\1\76"+
    "\2\41\2\0\4\41\5\0\1\36\1\0\1\36\2\0"+
    "\30\36\1\77\1\36\2\0\4\36\5\0\1\41\1\0"+
    "\1\41\2\0\30\41\1\100\1\41\2\0\4\41\5\0"+
    "\1\41\1\0\1\41\2\0\30\41\1\101\1\41\2\0"+
    "\4\41\5\0\1\36\1\0\1\36\2\0\31\36\1\102"+
    "\2\0\4\36\5\0\1\41\1\0\1\41\2\0\31\41"+
    "\1\103\2\0\4\41\5\0\1\41\1\0\1\41\2\0"+
    "\31\41\1\104\2\0\4\41\5\0\1\36\1\0\1\36"+
    "\2\0\16\36\1\105\13\36\2\0\4\36\5\0\1\41"+
    "\1\0\1\41\2\0\16\41\1\106\13\41\2\0\4\41"+
    "\5\0\1\41\1\0\1\41\2\0\16\41\1\107\13\41"+
    "\2\0\4\41\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2478];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\2\11\3\1\1\11\1\1\1\11"+
    "\20\1\1\11\52\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[71];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
  private MacrosParser yyparser;

/**
 * Constructor de clase.
 * @param r Referencia al lector de entrada.
 * @param p Referencia al analizador sintáctico.
 */
  public MacrosLex(java.io.Reader r, MacrosParser p) {
    this(r);
    yyparser = p;
    _contenido = new StringBuilder();
    _lineaActual = 1;
  }

  private StringBuilder _contenido;
  private int _lineaActual;

  /**
   * Devuelve el número de línea actual.
   */
  public int lineaActual() {
    return _lineaActual;
  }



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public MacrosLex(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 168) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return 0; }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { yyparser.programa().error().deCaracterNoReconocido(_lineaActual, yytext());
            }
          case 15: break;
          case 2: 
            { _lineaActual++;
            }
          case 16: break;
          case 3: 
            { 
            }
          case 17: break;
          case 4: 
            { _contenido.append(yytext());
            }
          case 18: break;
          case 5: 
            { _contenido.append(yytext());
                    _lineaActual++;
            }
          case 19: break;
          case 6: 
            { String id = Etiqueta.normalizarID(yytext());
                    yyparser.yylval = new MacrosParserVal(id);
                    _contenido.append(id);
                    return MacrosParser.ETIQUETA;
            }
          case 20: break;
          case 7: 
            { String id = Variable.normalizarID(yytext());
                    yyparser.yylval = new MacrosParserVal(id);
                    _contenido.append(id);
                    return MacrosParser.VARIABLE;
            }
          case 21: break;
          case 8: 
            { _contenido.append(yytext());
                    return yycharat(0);
            }
          case 22: break;
          case 9: 
            { yyparser.yylval = new MacrosParserVal(yytext());
                    _contenido.setLength(0);
                    yybegin(CUERPO);
                    return MacrosParser.IDMACRO;
            }
          case 23: break;
          case 10: 
            { String id = yytext();
                    yyparser.yylval = new MacrosParserVal(id);
                    _contenido.append(id);
                    return MacrosParser.IDMACRO;
            }
          case 24: break;
          case 11: 
            { _contenido.append(yytext());
                    return MacrosParser.GOTO;
            }
          case 25: break;
          case 12: 
            { return MacrosParser.DEFMACRO;
            }
          case 26: break;
          case 13: 
            { yyparser.yylval = new MacrosParserVal(_contenido.toString());
                    yybegin(YYINITIAL);
                    return MacrosParser.ENDMACRO;
            }
          case 27: break;
          case 14: 
            { yyparser.programa().error().deDefinicionInterior(_lineaActual);
            }
          case 28: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
