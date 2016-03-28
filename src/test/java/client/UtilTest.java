package client;


import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class UtilTest{

 
	private String testString1;
	private String testString2;
	private String testString3;
	private String testString4;
	private String testString5;
	private String testString6;
	private String testString7;
	private String testString8;
	private String testString9;
	private String testString10;
	private String testString11;
	private String testString12;
	private String testString13;
	private String testString14;
	private String testString15;
	
	@Before
	public void setup() {
		
		//strings to test isValid() on
		testString1 = "TESUJI (1, 3)"; //valid
		testString2 = "TESUJI (7, 9)"; //9 is out of bounds [0-8]
		testString3 = "TESUJI [(2, 6), h]"; //valid
		testString4 = "TESUJI [(8, 0), o]";	// 'o' != 'v'||'h'
		testString5 = "OUWETGEAGEA6969";	// not valid
		testString6 = "TTESUJI [(2, 6), h]";	// not valid
		testString7 = "IAM CANDYSUXX"; //valid
		testString8 = "IAM CANDY SUXX"; //name cannot contain ws
		testString9 = "GAME 2 gin ToNiC"; //valid
		testString10 = "GAME 3 gin ToNiC"; //not valid (3!=1,2)
		testString11 = "GAME 4 shampoo conditioner soap towel"; //valid
		testString12 = "GAME 4 shampoo conditioner soap"; //not valid nameP4?
		testString13 = "ATARI 3 [(2, 5), h]"; //valid
		testString14 = "MYOUSHU"; //valid
		testString15 = "IAM ___-33!#123@@#"; //only ws not allowed in name
	}
	
	@Test
	public void testisValid() throws Exception {

		assertTrue(Util.isValid(testString1));
		assertTrue(Util.isValid(testString1, "TESUJI"));
		
		assertTrue(Util.isValid(testString3));
		assertTrue(Util.isValid(testString3, "T"));
		
		assertTrue(Util.isValid(testString7));
		assertTrue(Util.isValid(testString7, "IA"));
		
		assertTrue(Util.isValid(testString9));
		assertTrue(Util.isValid(testString9,"GA"));
		
		try{
		assertTrue(Util.isValid(testString9,"iwjer"));//not a command name
		}catch(IllegalArgumentException e){
			//exception has been thrown
			//else test will fail
		}
		
		try{
		assertTrue(Util.isValid(testString9,"G"));//not a command name
		}catch(IllegalArgumentException e){
			//exception has been thrown
			//else test will fail
		}
		
		assertTrue(Util.isValid(testString11));
		assertFalse(Util.isValid(testString11,"GO"));
		
		
		assertTrue(Util.isValid(testString13));
		
		assertTrue(Util.isValid(testString14));
		
		assertTrue(Util.isValid(testString15));
		assertFalse(Util.isValid(testString11,"TESUJI"));
		
		assertFalse(Util.isValid(testString2));
		assertFalse(Util.isValid(testString2, "T"));
		
		assertFalse(Util.isValid(testString4));
		assertFalse(Util.isValid(testString5));
		assertFalse(Util.isValid(testString6));
		assertFalse(Util.isValid(testString8));
		assertFalse(Util.isValid(testString10));
		assertFalse(Util.isValid(testString12));

		
	}
	
}