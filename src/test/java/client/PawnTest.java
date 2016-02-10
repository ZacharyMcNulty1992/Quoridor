package client;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

/**
 * @author Brandon Williams
 * @date   2/9/2016 - Last Updated
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

	Point[] expectedVals = {null, new Point(2,3)};

	assertArrayEquals(expectedVals, testPawn.getPostition);
    }
}
