package client;

import java.util.Collection;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

/**
 * @author Brandon Williams
 * @date   2/9/2016 - Last updated
 */
@RunWith(Parameterized.class)
public class GamePieceInterfaceTest {

    private GamePiece gamePiece;

    public GamePieceInterfaceTest(GamePiece gamePiece) {

	this.gamePiece = gamePiece;
    }

    @Test
    public void testGamePieceGetPosition() throws Exception {

	Point[] positions = gamePiece.getPosition();

	if(gamePiece instanceof Pawn) {
	    
	    Point[] pawnPos = {null, new Point(2,3)};

	    assertArrayEquals(pawnPos, positions);
	}

	else if(gamePiece instanceof Wall) {

	    Point wallPos = {new Point(2,3), new Point(3,3)}

	    assertArrayEquals(wallPos, positions);
	}

	else {

	    fail("Incorrect type give. Must be of type Pawn or Wall.");
	}
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {

	return Arrays.asList( new Object[]{new Pawn(2,3)},
			      new Object[]{new Wall(2,3,'h')} );
    }
}
