package client;

import java.awt.Point;
import java.util.ArrayList;

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

    Point movePos = new Point(row,column);

    if(!isValidMove(movePos)){

      gameBoard.removePawn(pawnPos);

      return "GOTE";
    }

    gameBoard.movePawn(pawnPos, movePos);

    pawnPos = movePos;
    
    return hasWon();
  }

  public String placeWall(int column, int row, char direction) {

    Point placementPos = new Point(row, column);

    if(!isValidWallPlacement(placementPos, direction)){

      gameBoard.removePawn(pawnPos);

      return "GOTE";
    }

    gameBoard.placeWall(placementPos, direction);

    wallCount--;

    return "ATARI";
  }

  public Point getCurrentPos(){
    return pawnPos;
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

    ArrayList<Point> validMoves = getValidMoves(pawnPos, new ArrayList<Point>(10), new ArrayList<Point>(4));
    

    if(validMoves.contains(movePos)) {

      System.out.println(validMoves + "\n");      

      return true;
    }

    return false;
  }

  /**
   * 
   * @param currentPos
   * @param validPos
   * @param visitedSpaces
   * @return
   */
  private ArrayList<Point> getValidMoves(Point currentPos, 
      ArrayList<Point> validPos, ArrayList<Point> visitedSpaces) {

    visitedSpaces.add(currentPos);

    Point[] adjacentPos = new Point[] {
        new Point (currentPos.x+1, currentPos.y),
        new Point (currentPos.x-1, currentPos.y),
        new Point (currentPos.x, currentPos.y+1),
        new Point (currentPos.x, currentPos.y-1)
    };

    for(int i = 0; i < adjacentPos.length; i++) {

      if( !visitedSpaces.contains(adjacentPos[i]) ) {
        
        try {
          
          if(gameBoard.getSpaceAt(adjacentPos[i].x,
              adjacentPos[i].y).occupied){

           getValidMoves(adjacentPos[i], validPos, visitedSpaces);
          }
          else    
            validPos.add(adjacentPos[i]);
          
        } catch (IndexOutOfBoundsException e) {

          //Do nothing
        }
      }

    }



    return validPos;
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

      gameBoard.removePawn(pawnPos);
	
      return "KIKASHI";
    }

    return "ATARI";
  }
}
