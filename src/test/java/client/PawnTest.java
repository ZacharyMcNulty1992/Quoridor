package client;

import java.awt.Point;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

/**
 * @author Brandon Williams
 * @date   2/10/2016 - Last Updated
 */
public class PawnTest {

    private Pawn testPawn;
    
    @Before
    public void setup() {

	testPawn = new Pawn(2,3);
    }

    @Test
    public void testPawnConstructor() throws Exception {

	assertNotNull(testPawn);
    }

    @Test
    public void testGetPosition() throws Exception {

	Point[] expectedVals = {null, new Point(3,2)};

	assertArrayEquals(expectedVals, testPawn.getPostition);
    }
}
