package client;

import java.awt.Point;

/**
 * 
 * @author Brandon
 *
 */
public class Player {

  private int wallCount;
  private int playerNumber;
  private String playerName;
  private Point pawnPos;
  private boolean isTurn;

  /**
   * 
   * @param playerNumber
   * @param playerName
   */
  public Player (int playerNumber, String playerName, int wallCount) {

    this.wallCount = wallCount;
    this.playerNumber = playerNumber;
    this.playerName = playerName;
    this.isTurn = false;

    setInitPos();
  }

  /**
   * 
   * @return true if the pawn is in the appropriate
   * win position else return false.
   */
  public boolean hasWon() {

    if((playerNumber == 1 && pawnPos.y == 8) ||
        (playerNumber == 2 && pawnPos.y == 0) ||
        (playerNumber == 3 && pawnPos.x == 8) ||
        (playerNumber == 4 && pawnPos.x == 0)) {

      return true;
    }

    return false;
  }

  /**
   * 
   *
   * @param piece The 
   *
   */
  public void placePawn() {

 
  }
  
  /**
   * Does not verify that the path has to the end has not been
   * blocked yet! Need shortest path algorithm to determine.
   * 
   * @param piece - The game piece to be placed on the board
   * @return False if the wall placed is intersecting another
   * wall or the same wall is placed. Else, return true.
   */
  private boolean isValidPlacement(GamePiece piece) {

   return true;
  }
  
  /**
   * Sets the initial position of the pawn based on
   * on the player number
   */
  private void setInitPos() {

    if(playerNumber == 1)
      pawnPos = new Point(4, 0);
    else if(playerNumber == 2) 
      pawnPos = new Point(4, 8);
    else if(playerNumber == 3)
      pawnPos = new Point(0, 4);
    else
      pawnPos = new Point(8, 4);
  }
}
