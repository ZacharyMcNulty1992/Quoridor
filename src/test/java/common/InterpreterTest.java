package common;

import java.awt.Point;
import java.util.*;
import java.io.*;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * @author Jade Kevin Betsami
 * @edited Brandon Williams
 * @date   03/30/2016
 */
 
 public class InterpreterTest {
 
     private String testString1;
     private String testString2;
     private String testString3;
     private String testString4;
     private String testString5;
     private String testString6;
     private String testString7;
     
     @Before
     public void setup() {
     
     //strings to test for
     testString1 = "TESUJI (1, 3)";
     testString2 = "TESUJI (1,9)";
     testString3 = "TESUJI [(2, 6), h]";
     testString4 = "TESUJI [(5,4),v]";
     testString5 = "TESUJI [(8, 0), o]";
     testString6 = "OUWETGEAGEA6969";
     testString7 = "TTESUJI [(2,6),v]";
	 
     }

     @Test
     public void testParseString() throws Exception {
	 
	 assertArrayEquals("Tesuji validation error",
			   new String[] {"1","3"}, 
			   Interpreter.parseString(testString1));
	 
	 assertArrayEquals("Tesuji validation error",
			   new String[] {"1","9"}, 
			   Interpreter.parseString(testString2));
	 
	 assertArrayEquals("Tesuji validation error",
			   new String[] {"2","6", "h"}, 
			   Interpreter.parseString(testString3));

	 assertArrayEquals("Tesuji validation error",
			   new String[] {"5","4", "v"}, 
			   Interpreter.parseString(testString4));
	 
	 assertArrayEquals("Tesuji validation error",
			   new String[] {"GOTE"}, 
			   Interpreter.parseString(testString5));
	 
	 assertArrayEquals("Tesuji validation error",
			    new String[] {"GOTE"}, 
			    Interpreter.parseString(testString6));
	  
	  assertArrayEquals("Tesuji validation error",
			    new String[] {"GOTE"}, 
			    Interpreter.parseString(testString7));
     }
 }