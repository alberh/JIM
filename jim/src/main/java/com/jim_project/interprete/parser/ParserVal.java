package com.jim_project.interprete.parser;

/**
 * BYACC/J Semantic Value for parser: WhileParser This class provides some of
 * the functionality of the yacc/C 'union' directive
 */
public class ParserVal {

    /**
     * integer value of this 'union'
     */
    public int ival;

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
    public ParserVal() {
    }

    /**
     * Initialize me as an int
     *
     * @param val The value for the new object.
     */
    public ParserVal(int val) {
        ival = val;
    }

    /**
     * Initialize me as a string
     *
     * @param val The value for the new object.
     */
    public ParserVal(String val) {
        sval = val;
    }

    /**
     * Initialize me as an Object
     *
     * @param val The value for the new object.
     */
    public ParserVal(Object val) {
        obj = val;
    }
}
