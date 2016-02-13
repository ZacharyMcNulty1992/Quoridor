package test.java.client;

import java.util.Collection;
import java.util.Arrays;
import java.awt.Point;

import main.java.client.GamePiece;
import main.java.client.Pawn;
import main.java.client.Wall;

import org.junit.Test;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

/**
 * @author Brandon Williams
 * @date   2/10/2016 - Last updated
 */
@RunWith(Parameterized.class)
public class GamePieceInterfaceTest {

    private GamePiece gamePiece;

    public GamePieceInterfaceTest(GamePiece gamePiece) {

	this.gamePiece = gamePiece;
    }

    @Test
    public void testGamePieceGetPosition() throws Exception {

	System.out.println(gamePiece);

	// Point[] positions = gamePiece.getPosition();

	// if(gamePiece instanceof Pawn) {
	    
	//     Point[] pawnPos = {null, new Point(3,2)};

	//     assertArrayEquals(pawnPos, positions);
	// }

	// else if(gamePiece instanceof Wall) {

	//     Point wallPos = {new Point(3,2), new Point(4,2)};

	//     assertArrayEquals(wallPos, positions);
	// }

	// else {

	//     System.out.println(gamePiece);
	// }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {

	return Arrays.asList( new Object[]{new Pawn(2,3)},
			      new Object[]{new Wall(2,3,'h')} );
    }
}
