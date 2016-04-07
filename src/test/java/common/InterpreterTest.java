package common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jade Kevin Betsami
 * @edited Brandon Williams
 * @date   04/7/2016
 */

public class InterpreterTest {


  @Test
  public void testParseString() throws Exception {

    assertArrayEquals("Tesuji validation error",
        new String[] {"1","3"}, 
        Interpreter.parseString("TESUJI (1, 3)"));

    assertArrayEquals("Tesuji validation error",
        new String[] {"1","9"}, 
        Interpreter.parseString("TESUJI (1,9)"));

    assertArrayEquals("Tesuji validation error",
        new String[] {"2","6", "h"}, 
        Interpreter.parseString("TESUJI [(2, 6), h]"));

    assertArrayEquals("Tesuji validation error",
        new String[] {"5","4", "v"}, 
        Interpreter.parseString("TESUJI [(5,4),v]"));

    assertArrayEquals("Atari validation error",
        new String[] {"1", "3"}, 
        Interpreter.parseString("ATARI 1 (1, 3)"));

    assertArrayEquals("Atari validation error",
        new String[] {"GOTE"}, 
        Interpreter.parseString("ATARI 5 (1, 3)"));

    assertArrayEquals("Atari validation error",
        new String[] {"1", "3", "h"}, 
        Interpreter.parseString("ATARI 3 [(1, 3), h]"));
    
    assertArrayEquals("Atari validation error",
        new String[] {"1", "3", "h"}, 
        Interpreter.parseString("ATARI 3 [(1, 3), h]"));
    
  }
}