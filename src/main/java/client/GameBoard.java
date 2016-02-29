package client;

import java.util.ArrayList;
import java.awt.Point;

/**
 * @author Brandon Williams
 * @date   2/29/2016 - Last updated
 * @edited by Zachary McNulty
 */
public class GameBoard {

  private ArrayList<Space> gameBoard;

  /**
   * Constructs an empty game board
   */
  public GameBoard() {

    //gameBoard = new GamePiece[9][9];

    gameBoard = new ArrayList<Space>();
    Space s; //temp space variable
    //create all the spaces objects
    for(int i = 0; i < 9; i++){
      for(int p = 0; p < 9; p++){
        s = new Space(i, p);
        gameBoard.add(s);
      }
    }


    //now we establish the connections
    for(int i = 0; i < 9; i++) {
      for(int p = 0; p < 9; p++) {

        s = gameBoard.get(p+i*9); //using that temp variable we can get each node

        //here we will check if we are adding connections on the top row.
        if(i == 0){
          //if we are here we will not add a connection to the up pointer

          if(p == 0){
            //here we are on the right hand side of the board
            s.edges.add(gameBoard.get(p + (i*9) + 1)); //here we get the node next to the current one
            s.edges.add(gameBoard.get(p + (i*9) + 9)); //get the node directly below the current node
          }
          else if(p == 8){
            //here we are at the left hand side of the board
            s.edges.add(gameBoard.get(p + (i*9) + 9)); //get the node directly below the current node
            s.edges.add(gameBoard.get(p + (i*9) - 1)); //get the node that is just before the current node
          }
          else{
            //here we are in the middle of the board
            s.edges.add(gameBoard.get(p + (i*9) + 9)); //get the node directly below this node
            s.edges.add(gameBoard.get(p + (i*9) - 1)); //get the node that is just before the current node
            s.edges.add(gameBoard.get(p + (i*9) + 1)); //here we get the node next to the current one
          }

        }else if (i == 8){ //here we are looking at the bottom row of the board    

          if(p == 0){
            //here we are on the right hand side of the board
            s.edges.add(gameBoard.get(p + (i*9) + 1)); //here we get the node next to the current one
            s.edges.add(gameBoard.get(p + (i*9) - 9)); //get the node directly above the current node
          }
          else if(p == 8){
            //here we are at the left hand side of the board
            s.edges.add(gameBoard.get(p + (i*9) - 9)); //get the node directly above the current node
            s.edges.add(gameBoard.get(p + (i*9) - 1)); //get the node that is just before the current node
          }
          else{
            //here we are in the middle of the board
            s.edges.add(gameBoard.get(p + (i*9) - 9)); //get the node directly above this node
            s.edges.add(gameBoard.get(p + (i*9) - 1)); //get the node that is just before the current node
            s.edges.add(gameBoard.get(p+(i*9) + 1)); //here we get the node next to the current one
          }
        }
        else{ //here we are looking at the middle of the board
          if(p == 0){
            //here we are on the right hand side of the board
            s.edges.add(gameBoard.get(p + (i*9) + 1)); //here we get the node next to the current one
            s.edges.add(gameBoard.get(p + (i*9) - 9)); //get the node directly above the current node
            s.edges.add(gameBoard.get(p + (i*9) + 9)); //get the node directly below this node
          }
          else if(p == 8){
            //here we are at the left hand side of the board
            s.edges.add(gameBoard.get(p + (i*9) - 9)); //get the node directly above the current node
            s.edges.add(gameBoard.get(p + (i*9) - 1)); //get the node that is just before the current node
            s.edges.add(gameBoard.get(p + (i*9) + 9)); //get the node directly below this node
          }
          else{
            //here we are in the middle of the board
            s.edges.add(gameBoard.get(p + (i*9) - 9)); //get the node directly above this node
            s.edges.add(gameBoard.get(p + (i*9) - 1)); //get the node that is just before the current node
            s.edges.add(gameBoard.get(p + (i*9) + 1)); //here we get the node next to the current one
            s.edges.add(gameBoard.get(p + (i*9) + 9)); //get the node directly below this node
          }
        }
      }
    }
  }

  /**
   * @return The gameGame board data structure
   */
  public ArrayList<Space> getGameBoard() {

    return gameBoard;
  }

  /**
   * Places a GamePiece on the GameBoard.
   * Will more than likely need to be reworked
   * after move and place wall messages are
   * implemented.
   *
   * @param piece The pawn or wall to be
   * placed on the board
   *
   */
  public void placePiece(GamePiece piece) {

    Point[] pos = piece.getPosition();
    int x = -1;
    int y = -1;

    if(pos[0] == null && isValidPlacement(piece)) { //Pawn

      x = (int)piece.getPosition()[1].getX();
      y = (int)piece.getPosition()[1].getY();

      gameBoard.get(y + x * 9).occupied = true;
    }

    else if( isValidPlacement(piece) ) { //Wall

      //Place wall at first point
      x = (int)pos[0].getX();
      y = (int)pos[0].getY();

      int posIndex = y + x * 9;

      if(pos[1].getX() > x) { //horizontal wall

        while(posIndex < (y+(x*9))+2) {

          gameBoard.get(posIndex).edges
          .remove(gameBoard.get(posIndex+9));

          gameBoard.get(posIndex+9).edges
          .remove(gameBoard.get(posIndex));

          posIndex++;
        }
      }

      else if(pos[1].getY() > y) { //Vertical

        while(posIndex < (y+((x+2)*9))) {

          gameBoard.get(posIndex).edges
          .remove(gameBoard.get(posIndex+1));

          gameBoard.get(posIndex+1).edges
          .remove(gameBoard.get(posIndex));

          posIndex+=9;
        }
      }
    }
  }

  /**
   * 
   * @param x The x coordinate of the space
   * @param y
   * @return
   */
  public Space getSpaceAt(int x, int y){

    return gameBoard.get(y + (x*9));
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

    //Validate Wall Placement
    Point[] pos = piece.getPosition();

    if(pos[0] != null) { //If piece is a wall

      Space[] wallSpaces = new Space[] {
          getSpaceAt(pos[0].x, pos[0].y),
          getSpaceAt(pos[1].x, pos[1].y)};

      try {

        Space[] possibleWallSpaces = new Space[]{ 
            getSpaceAt(pos[0].x + 1, pos[0].y), 
            getSpaceAt(pos[0].x, pos[0].y + 1)};

        //If the same wall or an intersecting wall has been placed
        if( (!possibleWallSpaces[0].edges.contains(wallSpaces[0]) && // Vertical wall check
            !getSpaceAt(pos[0].x + 1, pos[0].y + 1).edges.contains(possibleWallSpaces[1])) ||
            (!possibleWallSpaces[1].edges.contains(wallSpaces[0]) && // Horizontal wall check
                !getSpaceAt(pos[0].x + 1, pos[0].y + 1).edges.contains(possibleWallSpaces[0])) ) { 

          return false;
        }

      } catch (ArrayIndexOutOfBoundsException e) { // If you try to place a wall off the board

        return false;
      }

    }

    return true;
  }
}
