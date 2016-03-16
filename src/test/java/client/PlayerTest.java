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
 }
  
  @Test
  public void testHasWon() {
    
    Assert.assertFalse(player.hasWon());
  }
  
  
  @Test
  public void testMovePawn() throws Exception {

    
  }
  
  @Test
  public void testIsValidPlacement() throws Exception {

    
  }
}
