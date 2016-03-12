package client;

import java.awt.Point;
import java.util.*;
import java.io.*;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * @author Jade Kevin Betsami
 * @date   03/04/2016 - Last Updated
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
		
		//constructors
		testInt1 = new Interpreter(testString1);
		testInt2 = new Interpreter(testString2);
		testInt3 = new Interpreter(testString3);
		testInt4 = new Interpreter(testString4);
		testInt5 = new Interpreter(testString5);
		testInt6 = new Interpreter(testString6);
		
		//Pawn that should be returned
		Pawn testPawn = new Pawn(1,3);
		
		//Wall that should be returned
		Wall testWall1 = new Wall(2,6,'h');
		
	}
	
	@Test
	public void testInterpreterConstructor() throws Exception {

		assertNotNull(testInt1);
		assertNotNull(testInt2);
		assertNotNull(testInt3);
		assertNotNull(testInt4);
		assertNotNull(testInt5);
		assertNotNull(testInt6);
		
	}
	
	//repProt() is true when no error in parse
	@Test
	public void testInterpreterRespProt() throws Exception {

		assertTrue(testInt1.respProt());
		assertTrue(testInt2.respProt());
		assertTrue(testInt3.respProt());
		assertFalse(testInt4.respProt());
		assertFalse(testInt5.respProt());
		assertFalse(testInt6.respProt());
		
	}
	
	//getPiece()
	//calls constructors with parsed values
	//returns piece, even if illegal 
	//should not call pawn constructor if err in parsing 
	@Test
	public void testInterpreterGetPiece() throws Exception {
		
		Point[] expectedVals1 = {null, new Point(3,1)};
		Point[] expectedVals2 = {null, new Point(9,1)};
		Point[] expectedVals3 = {new Point(6,2), new Point(7,2)};
		
		assertArrayEquals(expectedVals1, testInt1.getPiece().getPosition());
		assertArrayEquals(expectedVals2, testInt2.getPiece().getPosition());
		assertArrayEquals(expectedVals3, testInt3.getPiece().getPosition());
		assertNull(testInt4.getPiece());
		assertNull(testInt5.getPiece());
		assertNull(testInt6.getPiece());
	}
	
	//getInString() returns input string
	@Test
	public void testInterpreterGetInString() throws Exception {
		
		
		assertArrayEquals(testString1.toCharArray(), testInt1.getInString().toCharArray());
		assertArrayEquals(testString2.toCharArray(), testInt2.getInString().toCharArray());
		assertArrayEquals(testString3.toCharArray(), testInt3.getInString().toCharArray());
		assertArrayEquals(testString4.toCharArray(), testInt4.getInString().toCharArray());
		assertArrayEquals(testString5.toCharArray(), testInt5.getInString().toCharArray());
		assertArrayEquals(testString6.toCharArray(), testInt6.getInString().toCharArray());
		
	}
	
	
	
 
}