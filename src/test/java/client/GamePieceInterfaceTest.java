package client;

import java.util.Collection;
import java.util.Arrays;
import java.awt.Point;

import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

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

    Point[] positions = gamePiece.getPosition();

    if(gamePiece instanceof Pawn) {

      Point[] pawnPos = {null, new Point(3,2)};

      assertArrayEquals(pawnPos, positions);
    }

    else if(gamePiece instanceof Wall) {

      Point[] wallPos = {new Point(3,2), new Point(4,2)};

      assertArrayEquals(wallPos, positions);
    }

    else {

      fail();
    }
  }

  @Parameterized.Parameters
  public static Collection<Object[]> instancesToTest() {

    return Arrays.asList( new Object[]{new Pawn(2,3)},
        new Object[]{new Wall(2,3,'h')} );
  }
}
