package client;

import java.awt.Point;

/**
 * 
 * @author Brandon
 * @date 3/16/16
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
   * @param column - The column position that the pawn
   *        will move to
   *
   * @param row - The row position that the pawn will
   *        move to.
   *
   * @return The opCode to be sent to the servers. 
   *         If a valid move was made "ATARI" is
   *         returned. "KIKASHI" if it is a winning
   *         move or "GOTE" if it was an illegal
   *         move
   */
    public String movePawn(int column, int row) {

	Point movePos = new Point(row, column);

	if(!isValidMove())
	    return "GOTE";

	pawnPos = movePos;

	//Update GUI goes here

	return hasWon()
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

  /**
   * Does not verify that the path has to the end has not been
   * blocked yet! Need shortest path algorithm to determine.
   * 
   * @param piece - The game piece to be placed on the board
   * @return False if the wall placed is intersecting another
   * wall or the same wall is placed. Else, return true.
   */
  private boolean isValidMove(Point movePos) {

      if( (movePos.x == pawnPos.x && movePos.y == pawnPos.y + 1) ||
	  (movePos.y == pawnPos.y && movePos.x == pawnPos.x + 1)) {
	  
	  return true;
      }

      return false;
  }

  /**
   * Checks to see if the player has won
   * 
   * @return "KIKASHI" if the pawn is in the appropriate
   * win position else return "ATARI" (A legal pawn move).
   */
  private String hasWon() {

    if((playerNumber == 1 && pawnPos.y == 8) ||
        (playerNumber == 2 && pawnPos.y == 0) ||
        (playerNumber == 3 && pawnPos.x == 8) ||
        (playerNumber == 4 && pawnPos.x == 0)) {

      return "KIKASHI";
    }

    return "ATARI";
  }
}
