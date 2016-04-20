package client;

import java.awt.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

/**
 * @author Brandon
 * @date 3/18/16
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Player.class)
public class PlayerTest {

  private Player player;

  @Before //This runs before every test
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
  public void testMovePawn() throws Exception {

      System.out.println("Move Pawn Test");
      
      Assert.assertEquals("ATARI", player.movePawn(4,1));
      Assert.assertEquals("ATARI", player.movePawn(3,1));
      Assert.assertEquals("ATARI", player.movePawn(3,0));
      Assert.assertEquals("ATARI", player.movePawn(4,0));
      
      Assert.assertEquals("GOTE", player.movePawn(3,1));
      
      PowerMockito.verifyPrivate(player, Mockito.times(5))
	  .invoke("isValidMove", Matchers.any());
      
      
      
      //Reinitialize player
      player = PowerMockito.spy(new Player(1, "playerName", 10));
      
      //Test 2 player pawn jumps
      Player p2 = PowerMockito.spy(new Player(2,"player2Name", 10));
      
      System.out.println("Test Pawn Jump");

      //Move pawns so they are next to each other
      for (int i = 1; i < 4; i++) {
	  
	  Assert.assertEquals("ATARI",player.movePawn(4, i));
	  Assert.assertEquals("ATARI",p2.movePawn(4, 8-i));
      }
      Assert.assertEquals("ATARI", player.movePawn(4,4));
      
      //Test player pawn jumps
      Assert.assertEquals("ATARI", player.movePawn(4,6));    
      Assert.assertEquals("ATARI", player.movePawn(5,5));
      Assert.assertEquals("ATARI", player.movePawn(3,5));
      Assert.assertEquals("ATARI", player.movePawn(4,4));
      
      //Test p2 pawn jumps
      Assert.assertEquals("ATARI", p2.movePawn(4,3));
      Assert.assertEquals("ATARI", p2.movePawn(3,4));
      Assert.assertEquals("ATARI", p2.movePawn(4,5));
      Assert.assertEquals("ATARI", p2.movePawn(5,4));
      
      Assert.assertEquals("GOTE", player.movePawn(0,0));
      Assert.assertEquals("GOTE", p2.movePawn(0,0));
      
  }

  @Test
  public void testHasWon() throws Exception {

      System.out.println("Has Won Test");  

    String actualResult = Whitebox.invokeMethod(player, "hasWon");
    Assert.assertEquals("ATARI", actualResult);

    //move to win position
    for(int i = 1; i < 9; i++) {

	actualResult = player.movePawn(4, i);

      if(i != 8){

	Assert.assertEquals("ATARI", actualResult);
      }
    }

    Assert.assertEquals("KIKASHI", actualResult);
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

    Assert.assertEquals("ATARI", player.placeWall(4,0,'h'));

    actualWallCount = Whitebox.getInternalState(player, "wallCount");
    Assert.assertEquals(9, actualWallCount);

    //Test that you cannot place intersecting walls
    Assert.assertEquals("GOTE", player.placeWall(4,0,'v'));
    //or the same wall
    Assert.assertEquals("GOTE", player.placeWall(4,0,'h'));
    //or an overlapping wall
    Assert.assertEquals("GOTE", player.placeWall(5,0,'h'));

    actualWallCount = Whitebox.getInternalState(player, "wallCount");
    Assert.assertEquals(9, actualWallCount);

    PowerMockito.verifyPrivate(player, Mockito.times(8))
    .invoke("isValidWallPlacement", Matchers.any(), Matchers.anyChar());
  }
}
