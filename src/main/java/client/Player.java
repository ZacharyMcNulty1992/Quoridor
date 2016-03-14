package client;

import java.awt.Point;

/**
 * 
 * @author Brandon
 *
 */
public class Player {

  private int playerNumber;
  private String playerName;
  private Point pawnPos;
  private boolean isTurn;

  /**
   * 
   * @param playerNumber
   * @param playerName
   */
  public Player (int playerNumber, String playerName) {

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
   * Sets the initial position of the pawn based on
   * on the player number
   */
  private void setInitPos() {

    if(playerNumber == 1)
      pawnPos = new Point(3, 0);
    else if(playerNumber == 2) 
      pawnPos = new Point(3, 8);
    else if(playerNumber == 3)
      pawnPos = new Point(0, 3);
    else
      pawnPos = new Point(8, 3);
  }
}
