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
	
	public void setup() {
		
		//strings to test for
		testString1 = "TESUJI (1,3)";
		testString2 = "TESUJI (1,9)";
		testString3 = "TESUJI [(2,6), h]";
		testString4 = "TESUJI [(8,0), o]";
		testString5 = "OUWETGEAGEA6969";
		testString6 = "TTESUJI [(2,6), h]";
		
		//constructors
		Interpreter testInt1 = new Interpreter(testString1);
		Interpreter testInt2 = new Interpreter(testString2);
		Interpreter testInt3 = new Interpreter(testString3);
		Interpreter testInt4 = new Interpreter(testString4);
		Interpreter testInt5 = new Interpreter(testString5);
		Interpreter testInt6 = new Interpreter(testString6);
		
		//Pawn that should be returned
		testPawn = new Pawn(1,3);
		
		//Wall that should be returned
		testWall1 = new Wall(2,6,h);
		
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
		assertTrue(testInt4.respProt());
		assertFalse(testInt5.respProt());
		assertFalse(testInt6.respProt());
		
	}
	
	//getPiece()
	//calls constructors with parsed values
	//returns piece, even if illegal 
	//should not call pawn constructor if err in parsing 
	@Test
	public void testInterpreterGetPiece() throws Exception {
		
		Point[] expectedVals1 = {null, new Point(1,3)};
		Point[] expectedVals2 = {null, new Point(1,9)};
		Point[] expectedVals3 = {new Point(2,6), new Point(3,6)};
		
		assertArrayEquals(expectedVals1, testInt1.getPiece().getPosition());
		assertArrayEquals(expectedVals2, testInt2.getPiece().getPosition());
		assertArrayEquals(expectedVals3, testInt3.getPiece().getPosition());
		assertNull(testInt4.getPiece());
		assertNull(testInt5.getPiece());
		assertNull(testInt6.getPiece());
		
	}
	
	//getInString() returns input string
	public void testInterpreterGetInString() throws Exception {
		
		
		assertArrayEquals(testString1.toCharArray(), testInt1.getPiece().getInString().toCharArray());
		assertArrayEquals(testString2.toCharArray(), testInt2.getPiece().getInString().toCharArray());
		assertArrayEquals(testString3.toCharArray(), testInt3.getPiece().getInString().toCharArray());
		assertArrayEquals(testString4.toCharArray(), testInt3.getPiece().getInString().toCharArray());
		assertArrayEquals(testString5.toCharArray(), testInt3.getPiece().getInString().toCharArray());
		assertArrayEquals(testString6.toCharArray(), testInt3.getPiece().getInString().toCharArray());
		
	}
	
	//getOutString() returns the ATARI outstring
	//returns null if parse error
	public void testInterpreterGetOutString() throws Exception {
		
		expString1 = "ATARI (1,3)";
		expString2 = "ATARI (1,9)";
		expString3 = "ATARI [(2,6), h]";
		
		assertArrayEquals(expString1.toCharArray(), testInt1.getPiece().getOutString().toCharArray());
		assertArrayEquals(expString2.toCharArray(), testInt2.getPiece().getOutString().toCharArray());
		assertArrayEquals(expString3.toCharArray(), testInt3.getPiece().getOutString().toCharArray());
		assertArrayNull(testInt4.getPiece().getOutString());
		assertArrayNull(testInt5.getPiece().getOutString());
		assertArrayNull(testInt6.getPiece().getOutString());
		
	}
	
	
	
 
	
 }