package test.java.client;

import java.awt.Point;

import main.java.client.Wall;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

/**
 * @author Brandon Williams
 * @date   2/10/2016 - Last Updated
 */
public class WallTest {

    private Wall testWall;
    
    @Before
    public void setup() {

	testWall = new Wall(2,3,'h');
    }

    @Test
    public void testWallConstructor() throws Exception {

	assertNotNull(testWall);
    }

    @Test
    public void testWallGetPosition() throws Exception {

	Point[] expectedVals = {new Point(3,2), new Point(4,2)};

	assertArrayEquals(expectedVals, testWall.getPosition());
    }
}
