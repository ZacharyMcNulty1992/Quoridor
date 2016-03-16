package client;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Brandon Williams
 * @date   2/21/2016 - Last updated
 */
public class GameBoardTest {

  private GameBoard gb;

  @Before //This runs when the class is initialized
  public void setup() {

    gb = new GameBoard();
  }

  @Test
  public void testGameBoardConstructor() throws Exception {

    assertNotNull(gb);
  }

  @Test
  public void testGetGameBoard() throws Exception {

    ArrayList<Space> board = gb.getGameBoard();

    assertNotNull( board );

    //Also want to check if the board is of the correct size
    assertEquals("Board is of worng size.", 81, board.size());

    //Check to see that all the elements are not null
    for( int i = 0; i < board.size(); i++ ) {

      assertNotNull( "Index " + i + " of board is null" );
    }
  }
}
