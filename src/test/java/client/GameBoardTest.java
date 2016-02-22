package test.java.client;
import main.java.client.GameBoard;

import org.junit.Test;
import org.junit.Before;

import org.mockito.Mockito;

import static org.junit.Assert.assertArrayEquals;
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

    @Before //This runs before every test
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

    /**
     * This test will need to change once the client
     * recieves move and wall place messages from servers
     */
    @Test
    public void testPlacePiece() throws Exception {

	//Test placing a pawn
	Pawn pawn = Mockito.mock(Pawn.class);
	Mockito.when(pawn.getPosition())
	    .thenReturn(new Point[] {null, new Point(0,3)});

	gb.placePiece(pawn);

	ArrayList<Space> board = gb.getGameBoard();
	
	Space expectedSpace = board.get(3);

	assertTrue("Pawn was not placed on the board correctly",
		   expectedSpace.occupied);

	//Test placing a wall
	Wall wall = Mockito.mock(Wall.class);
	Mockito.when(wall.getPosition())
	    .thenReturn(new Point[] {new Point(0,3), new Point(0,4)});
	
	gb.placePiece(wall);

	//Check if there is a all at (0,3) and (0,4)
	if(expectedSpace.edges.contains(board.get(4))
	   || board.get(4).edges.contains(board.get(3)))
	    
	    fail("Wall was not placed correctly");
        
    }
}
