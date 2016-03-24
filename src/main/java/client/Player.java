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
  private GameBoard gameBoard;


  /**
   * 
   * @param playerNumber
   * @param playerName
   */
  public Player (int playerNumber, String playerName, int wallCount) {

    this.wallCount = wallCount;
    this.playerNumber = playerNumber;
    this.playerName = playerName;
    this.gameBoard = GameBoard.getInstance();

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

    if(!isValidMove(movePos))
      return "GOTE";

    gameBoard.movePawn(pawnPos, movePos);

    pawnPos = movePos;

    return hasWon();
  }

  public String placeWall(int column, int row, char direction) {

    Point placementPos = new Point(row, column);

    if(!isValidWallPlacement(placementPos, direction))
      return "GOTE";

    gameBoard.placeWall(placementPos, direction);

    wallCount--;

    return "ATARI";
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
   * Checks if the requested position is a valid move
   *
   * @param movePos The requested move to validate
   */
  private boolean isValidMove(Point movePos) {

      //Implement using recursion.

    return false;
  }

  /**
   * Does not verify that the path has to the end has not been
   * blocked yet! Need shortest path algorithm to determine.
   * 
   * @param wallPos - The position to place the wall on the board
   * @return False if the wall placed is intersecting another
   * wall or the same wall is placed. Else, return true.
   */
  private boolean isValidWallPlacement(Point wallPos, char direction) {

    if (wallCount < 1)
      return false;    
    
    if (wallPos.x == 8 || wallPos.y == 8)
      return false;
    
    try { 

      if( direction == 'v' ) { // A vertical wall


        // If this position and the position above or below of it already has a 
        // vertical wall an invalid move was made.
        if( !gameBoard.getSpaceAt(wallPos.x, wallPos.y).edges
            .contains(gameBoard.getSpaceAt(wallPos.x + 1, wallPos.y)) &&
            (!gameBoard.getSpaceAt(wallPos.x, wallPos.y - 1).edges
                .contains(gameBoard.getSpaceAt(wallPos.x + 1, 
                    wallPos.y - 1 ) ) 
                || !gameBoard.getSpaceAt(wallPos.x, wallPos.y + 1).edges
                .contains(gameBoard.getSpaceAt(wallPos.x + 1, 
                    wallPos.y + 1 ) )) ) {

          return false;
        }

        //Check for intersecting Walls
        else if(!gameBoard.getSpaceAt(wallPos.x, wallPos.y).edges
            .contains(gameBoard.getSpaceAt(wallPos.x, 
                wallPos.y + 1 ) ) 
            && !gameBoard.getSpaceAt(wallPos.x + 1, wallPos.y).edges
            .contains(gameBoard.getSpaceAt(wallPos.x + 1, 
                wallPos.y + 1 )) ) {

          return false;
        }

      } else { //A horizontal wall 

        // If this position and the position left or right of it already has a 
        // horizontal wall, an invalid move was made.
        if( !gameBoard.getSpaceAt(wallPos.x, wallPos.y).edges
            .contains(gameBoard.getSpaceAt(wallPos.x, wallPos.y + 1)) &&
            (!gameBoard.getSpaceAt(wallPos.x - 1, wallPos.y).edges
                .contains(gameBoard.getSpaceAt(wallPos.x - 1, 
                    wallPos.y + 1 ) ) 
                || !gameBoard.getSpaceAt(wallPos.x + 1, wallPos.y).edges
                .contains(gameBoard.getSpaceAt(wallPos.x + 1, 
                    wallPos.y + 1 ) )) ) {

          return false;
        }

        //Check for intersecting wall
        else if(!gameBoard.getSpaceAt(wallPos.x, wallPos.y).edges
            .contains(gameBoard.getSpaceAt(wallPos.x + 1, 
                wallPos.y) ) 
            && !gameBoard.getSpaceAt(wallPos.x, wallPos.y + 1).edges
            .contains(gameBoard.getSpaceAt(wallPos.x + 1, 
                wallPos.y + 1 )) ) {

          return false;
        }

      }


    } catch(IndexOutOfBoundsException ex) {

      //Do nothing

    }

    return true;
  }

  /**
   * Checks to see if the player has won
   * 
   * @return "KIKASHI" if the pawn is in the appropriate
   * win position else return "ATARI" (Message for a legal 
   * pawn move).
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
