package client;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;

/**
 * @author Brandon Williams
 * @date   2/9/2016 - Last updated
 */
public class GameBoardTest {

    private GameBoard gb;

    @Before
    public void setup() {

	gb = new GameBoard();
    }

    @Test
    public void testGameBoardConstructor() throws Exception {
	
	assertNotNull(gb);
    }

    @Test
    public void testGetGameBoard() throws Exception {

	assertNotNull(gb.getGameBoard());
    }
}
