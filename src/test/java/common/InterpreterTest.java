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
     
     private Pawn testPawn;
     private Wall testWall;
     
     Interpreter testInt1;
     Interpreter testInt2; 
     Interpreter testInt3;
     Interpreter testInt4;
     Interpreter testInt5;
     Interpreter testInt6;
     
     @Before
     public void setup() {
     
     //strings to test for
     testString1 = "TESUJI (1,3)";
     testString2 = "TESUJI (1,9)";
     testString3 = "TESUJI [(2,6), h]";
     testString4 = "TESUJI [(8,0), o]";
     testString5 = "OUWETGEAGEA6969";
     testString6 = "TTESUJI [(2,6), h]";
	 
     }

     @Test
     public void testParseString() throws Exception {
	 
	 assertArrayEquals("Tesuji validation error",
			   new String[] {"1","3"}, 
			   Interpreter.parseString(testString1);
     }