//#############################################
//## file: LoopParser.java
//## Generated by Byacc/j
//#############################################
package com.jim_project.interprete.parser.loopmodel;

/**
 * BYACC/J Semantic Value for parser: LoopParser
 * This class provides some of the functionality
 * of the yacc/C 'union' directive
 */
public class LoopParserVal
{
/**
 * integer value of this 'union'
 */
public int ival;

/**
 * double value of this 'union'
 */
public double dval;

/**
 * string value of this 'union'
 */
public String sval;

/**
 * object value of this 'union'
 */
public Object obj;

//#############################################
//## C O N S T R U C T O R S
//#############################################
/**
 * Initialize me without a value
 */
public LoopParserVal()
{
}
/**
 * Initialize me as an int
 */
public LoopParserVal(int val)
{
  ival=val;
}

/**
 * Initialize me as a double
 */
public LoopParserVal(double val)
{
  dval=val;
}

/**
 * Initialize me as a string
 */
public LoopParserVal(String val)
{
  sval=val;
}

/**
 * Initialize me as an Object
 */
public LoopParserVal(Object val)
{
  obj=val;
}
}//end class

//#############################################
//## E N D    O F    F I L E
//#############################################
