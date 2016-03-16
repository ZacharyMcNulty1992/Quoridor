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
 * @date 3/16/16
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
  
    @Test
    public void testIsValidMove() throws Exception {

	Assert.assertTrue(Whitebox
			  .invokeMethod(player, "isValidMove",
					new Point(3, 1)
					)
			  );

	Assert.assertFalse(Whitebox
			   .invokeMethod(player, "isValidMove",
					 new Point(4, 0)
					 )
			   );
    }
}
