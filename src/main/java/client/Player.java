package client;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author Brandon
 * @date 3/16/16
 *
 */
public class Player {

  protected int wallCount;
  private int playerNumber;

  private static boolean isFirstInstance = true;

  //Keeps track of all players win positions 
  private static ArrayList<ArrayList<Point>> winPositions;

  //Keeps track of all players pawn positions
  private static ArrayList<Point> pawnPos;

  private static GameBoard gameBoard;


  /**
   * 
   * @param playerNumber
   * @param playerName
   */
  public Player (int playerNumber, int wallCount) {

    this.wallCount = wallCount;
    this.playerNumber = playerNumber;

    if(isFirstInstance) {

      isFirstInstance = false;

      if(wallCount == 10){
        Player.winPositions = new ArrayList<ArrayList<Point>>(2);
        Player.pawnPos = new ArrayList<Point>(2);

        for(int i = 0; i < 2; i++)
          winPositions.add(new ArrayList<Point>(9));
      }
      else if (wallCount == 5){
        Player.winPositions = new ArrayList<ArrayList<Point>>(4);
        Player.pawnPos = new ArrayList<Point>(4);

        for(int i = 0; i < 4; i++)
          winPositions.add(new ArrayList<Point>(9));
      }

      Player.gameBoard = GameBoard.getInstance();
    }

    setInitPosWinPos();
  }

  /**
   * Use for AI only
   */
  public Player(){
    gameBoard = new GameBoard();
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

    Space movePos = new Space(column, row);

    if(!isValidMove(movePos)){

      gameBoard.removePawn(pawnPos.get(playerNumber - 1));

      return "GOTE";
    }

    gameBoard.movePawn(pawnPos.get(playerNumber - 1), movePos);

    pawnPos.set(playerNumber - 1, movePos);

    return hasWon();
  }

  public String placeWall(int column, int row, char direction) {

    Point placementPos = new Point(column, row);

    if(!isValidWallPlacement(placementPos, direction)){

      return "GOTE";
    }
    gameBoard.placeWall(placementPos, direction);

    wallCount--;

    return "ATARI";
  }

  public Point getCurrentPos(){
    return pawnPos.get(playerNumber - 1);
  }

  /**
   * Testing purposes only
   */
  public void resetBoard() {

    gameBoard  = new GameBoard();

    if(pawnPos.size() == 2){
      wallCount = 10;
      Player.winPositions = new ArrayList<ArrayList<Point>>(2);
      Player.pawnPos = new ArrayList<Point>(2);

      for(int i = 0; i < 2; i++)
        winPositions.add(new ArrayList<Point>(9));
    }
    else if (pawnPos.size() == 4){
      wallCount = 5;
      Player.winPositions = new ArrayList<ArrayList<Point>>(4);
      Player.pawnPos = new ArrayList<Point>(4);

      for(int i = 0; i < 4; i++)
        winPositions.add(new ArrayList<Point>(9));
    }

    setInitPosWinPos();
  }

  @Override
  public String toString(){

    return String.format("Player number: %s\n"
        + "Win Positions: %s\n"
        + "Current Position: %s", playerNumber,
        winPositions.get(playerNumber-1), pawnPos.get(playerNumber-1));
  }

  /**
   * Sets the initial position of the pawn based on
   * on the player number
   */
  private void setInitPosWinPos() {

    Point initPoint;

    if(playerNumber == 1 && !pawnPos.contains(new Point(4,0))) {
      pawnPos.add(initPoint = new Point(4, 0));
      gameBoard.placePawn(initPoint);

      for(int i = 0; i < 9; i++)
        winPositions.get(0).add(new Point(i, 8));
    }
    else if(playerNumber == 2 && !pawnPos.contains(new Point(4,8)))  {
      pawnPos.add(initPoint = new Point(4, 8));
      gameBoard.placePawn(initPoint);

      for(int i = 0; i < 9; i++)
        winPositions.get(1).add(new Point(i, 0));

    }
    else if(playerNumber == 3 && !pawnPos.contains(new Point(0,4))) {
      pawnPos.add(initPoint = new Point(0, 4));
      gameBoard.placePawn(initPoint);

      for(int i = 0; i < 9; i++)
        winPositions.get(2).add(new Point(8, i));

    }
    else if(playerNumber == 4 && !pawnPos.contains(new Point(8,4))) {
      pawnPos.add(initPoint = new Point(8, 4));
      gameBoard.placePawn(initPoint);

      for(int i = 0; i < 9; i++)
        winPositions.get(3).add(new Point(0, i));

    }
  }

  /**
   * Checks if the requested position is a valid move
   *
   * @param movePos The requested move to validate
   */
  private boolean isValidMove(Space movePos) {

    //System.out.println("Position to move to: " + movePos);

    ArrayList<Space> validMoves = 
        getValidMoves(gameBoard.getSpaceAt(pawnPos.get(playerNumber - 1).x, pawnPos.get(playerNumber - 1).y),
            new ArrayList<Space>(10), 
            new ArrayList<Space>(4) );

    //System.out.printf("All valid positions are: %s\n\n", validMoves);  

    if(validMoves.contains(movePos)) 		
      return true;

    return false;
  }

  /**
   * 
   * @param currentPos
   * @param validPos
   * @param visitedSpaces
   * @return
   */
  private ArrayList<Space> getValidMoves(Space currentPos, 
      ArrayList<Space> validPos, 
      ArrayList<Space> visitedSpaces) {

    visitedSpaces.add(currentPos);

    for(Space spc : currentPos.edges) {

      //System.out.printf("%s\n%s\n%s\n\n", currentPos, validPos, visitedSpaces);
      // Haven't visited yet
      if( !visitedSpaces.contains(spc)) {

        //System.out.printf("Space: %s unvisited\n", spc);

        if(spc.occupied){
          //System.out.printf("Space: %s is occupied\n", spc);
          getValidMoves(spc, validPos, visitedSpaces);
        }
        else
          //System.out.printf("Space: %s is valid position\n", spc);
          validPos.add(spc);
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
  protected boolean isValidWallPlacement(Point wallPos, char direction) {

    if (wallCount < 1)
      return false;		 

    if (wallPos.x == 8 || wallPos.y == 8)
      return false;

    try { 

      Space[] spaces = new Space[] { 
          gameBoard.getSpaceAt(wallPos.x, wallPos.y), // the current space
          gameBoard.getSpaceAt(wallPos.x+1, wallPos.y), // the space to the right
          gameBoard.getSpaceAt(wallPos.x, wallPos.y+1), //the space below 
          gameBoard.getSpaceAt(wallPos.x+1, wallPos.y+1), //the space to the right and below
      };

      if( direction == 'v' ) { // A vertical wall

        // If this position and the position above or below of it already has a 
        // vertical wall an invalid move was made.

        if( !spaces[0].edges.contains(spaces[1]) || 
            spaces[0].edges.contains(spaces[1]) && 
            !spaces[2].edges.contains(spaces[3]) ) {

          return false;
        }

        //Check for intersecting Walls
        else if( !spaces[0].edges.contains(spaces[2]) && 
            !spaces[1].edges.contains(spaces[3]) ) {

          return false;
        }

      } else { //A horizontal wall 

        // If this position and the position left or right 
        // of it already has a horizontal wall, an invalid 
        // move was made.


        if( !spaces[0].edges.contains(spaces[2]) || 
            spaces[0].edges.contains(spaces[2]) && 
            !spaces[1].edges.contains(spaces[3]) ){

          return false;
        }

        //Check for intersecting wall
        else if( !spaces[0].edges.contains(spaces[1]) && 
            !spaces[2].edges.contains(spaces[3]) )

          return false;
      }


    } catch(IndexOutOfBoundsException ex) {

      //Do nothing
    }

    return !doesBlockPath(wallPos, direction);
  }

  private boolean doesBlockPath(Point wallPos, char direction) {

    //initialization
    ArrayList<Space> q = new ArrayList<>();
    ArrayList<Space> visited = new ArrayList<>();

    gameBoard.placeWall(wallPos, direction);


    for(int i = 0; i < pawnPos.size(); i++){

      boolean pathIsBlocked = true;

      Space current = gameBoard.getSpaceAt(pawnPos.get(i).x, pawnPos.get(i).y);

      current.prev = null;
      HashSet<Space> NeighbourSet;
      q.add(current);
      visited.add(current);

      while (!q.isEmpty()) { //main loop

        //get the set of neighbour nodes
        NeighbourSet = current.edges;
        for (Space a : NeighbourSet) {
          //if the node we are checking has not been visited
          if (!visited.contains(a)) {
            //we add it to the queue
            q.add(a);
            //add it to visited list
            visited.add(a);
            //make the previous node the current node
            a.prev = current;

            if(winPositions.get(i).contains(a)){
              pathIsBlocked = false;
            }
          }// end if !viseted...
        } // end for each Space...
        
        q.remove(current);

        //see if the queue is empty
        if (!q.isEmpty()) {
          //if it isnt then get the next node from the queue
          current = q.get(0);
        }

      } // end while !q...
      
      if (pathIsBlocked){
        gameBoard.removeWall(wallPos, direction);
        
        return true;
      }
    } // end for int i...

    return false;
  }

  /**
   * Checks to see if the player has won
   * 
   * @return "KIKASHI" if the pawn is in the appropriate
   * win position else return "ATARI" (Message for a legal 
   * pawn move).
   */
  private String hasWon() {

    return winPositions.get(playerNumber - 1).contains(pawnPos.get(playerNumber - 1)) ? "KIKASHI" : "ATARI";
  }
}