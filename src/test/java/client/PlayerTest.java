package client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

/**
 * @author Brandon
 * @date 3/18/16
 */
public class PlayerTest {
    
    private Player player;
    
    @Before
    public void setup() {
	
      player = PowerMockito.spy(new Player(1, "playerName", 10));
    }
    
    @Test
    public void testPlayerConstructor() throws Exception {
	
    Assert.assertNotNull(player);
	
    Point pawnPos = Whitebox.getInternalState(player, "pawnPos");
    Point expectedPos = new Point(4,0);
    Assert.assertEquals(expectedPos, pawnPos);
    
    String playerName = Whitebox.getInternalState(player, "playerName");
    String expectedName = "playerName";
    Assert.assertEquals(expectedName, playerName);
    
    int playerNumber = Whitebox.getInternalState(player, "playerNumber");
    Assert.assertEquals(1, playerNumber);
    
    boolean isPlayerTurn = Whitebox.getInternalState(player, "isTurn");
    Assert.assertFalse(isPlayerTurn);

    int wallCount = Whitebox.getInternalState(player, "wallCount");
    Assert.assertEquals(10, wallCount);
  }
    
    @Test
    public void testHasWon() {
      
	Assert.assertEquals("ATARI", 
			    Whitebox.invokeMethod(player, "hasWon");
  }
  
  
    @Test
    public void testMovePawn() throws Exception {
	
	Assert.assertEquals("ATARI", player.movePawn(4,0));
	Assert.assertEquals("ATARI", player.movePawn(4,1));
	Assert.assertEquals("ATARI", player.movePawn(3,1));
	Assert.assertEquals("ATARI", player.movePawn(3,0));
	
	Assert.assertEquals("GOTE", player.movePawn(3,1));	
  }

    /**
     * Tests both placeWall and isValidWallPlacement()
     */
    @Test
    public void testPlaceWall() {

	// Test that you cannot place walls at the bottom or right 
	// edge of the board
	Assert.assertEquals("GOTE", player.placeWall(4,8,'v'));
	Assert.assertEquals("GOTE", player.placeWall(4,8,'h'));
	Assert.assertEquals("GOTE", player.placeWall(8,4,'v'));
	Assert.assertEquals("GOTE", player.placeWall(8,4,'h'));
	Assert.assertEquals(10, Whitebox.getInternalState(player, "wallCount"));

	Assert.assertEquals("ATARI", player.placeWall(4,7,'v'));
	Assert.assertEquals(9, Whitebox.getInternalState(player, "wallCount"));

	//Test that you cannot place intersecting walls
	Assert.assertEquals("GOTE", player,placeWall(4,7,'h'));
	//or the same wall
	Assert.assertEquals("GOTE", player,placeWall(4,7,'v'));
	Assert.assertEquals(9, Whitebox.getInternalState(player, "wallCount"));
    }
}
