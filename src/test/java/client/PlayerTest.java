package client;

import java.awt.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    int wallCount = Whitebox.getInternalState(player, "wallCount");
    Assert.assertEquals(10, wallCount);
  }

  @Test
  public void testHasWon() throws Exception {

    String actualResult = Whitebox.invokeMethod(player, "hasWon");
    Assert.assertEquals("ATARI", actualResult);
    
    //move to win position
    for(int i = 1; i < 9; i++) {
      
      actualResult = player.movePawn(i, 4);
      
      if(i != 8)
          Assert.assertEquals("ATARI", actualResult);
    }
   
    Assert.assertEquals("KIKASHI", actualResult);
  }


  @Test
  public void testMovePawn() throws Exception {

    Assert.assertEquals("ATARI", player.movePawn(1,4));
    Assert.assertEquals("ATARI", player.movePawn(1,3));
    Assert.assertEquals("ATARI", player.movePawn(0,3));
    Assert.assertEquals("ATARI", player.movePawn(0,4));

    Assert.assertEquals("GOTE", player.movePawn(1,3));	
  }

  /**
   * Tests both placeWall and isValidWallPlacement()
   */
  @Test
  public void testPlaceWall() throws Exception{
    
    // Test that you cannot place walls at the bottom or right 
    // edge of the board
    Assert.assertEquals("GOTE", player.placeWall(4,8,'v'));
    Assert.assertEquals("GOTE", player.placeWall(4,8,'h'));
    Assert.assertEquals("GOTE", player.placeWall(8,4,'v'));
    Assert.assertEquals("GOTE", player.placeWall(8,4,'h'));
    
    int actualWallCount = Whitebox.getInternalState(player, "wallCount");
    Assert.assertEquals(10, actualWallCount);

    Assert.assertEquals("ATARI", player.placeWall(4,7,'v'));
    
    actualWallCount = Whitebox.getInternalState(player, "wallCount");
    Assert.assertEquals(9, actualWallCount);

    //Test that you cannot place intersecting walls
    Assert.assertEquals("GOTE", player.placeWall(4,7,'h'));
    //or the same wall
    Assert.assertEquals("GOTE", player.placeWall(4,7,'v'));
    
    actualWallCount = Whitebox.getInternalState(player, "wallCount");
    Assert.assertEquals(9, actualWallCount);
  }
}
