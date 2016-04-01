package client;

import java.util.ArrayList;
import java.awt.Point;

/**
 * @author Brandon Williams
 * @date   3/18/16
 * @edited Brandon Williams
 */
public class GameBoard {

  private static GameBoard instance = null;
  private ArrayList<Space> gameBoard;

  /**
   * Singleton implementation of GameBoard
   * @return A new GameBoard on first execution or the
   *         GameBoard created on the first execution.
   */
  public static GameBoard getInstance() {

    if(instance == null)
      return instance = new GameBoard();

    return instance;
  }

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
   * 
   * @param row The row of the space to get
   * @param column The column of the space to get
   * @return The space at (row, column)
   */
  public Space getSpaceAt(int row, int column){

    return gameBoard.get(row + (column*9));
  }

  /**
   * 
   * @param currentPos
   * @param newPos
   */
  public void movePawn(Point currentPos, Point newPos) {

    getSpaceAt(currentPos.x, currentPos.y).occupied = false;
    getSpaceAt(newPos.x, newPos.y).occupied = true;
  }

  /**
   * 
   * @param wallPos
   * @param direction
   */
  public void placeWall(Point wallPos, char direction) {

    if( direction == 'v' ) {

      getSpaceAt(wallPos.x + 1, wallPos.y).edges
      .remove(getSpaceAt(wallPos.x, wallPos.y));

      getSpaceAt(wallPos.x, wallPos.y).edges
      .remove(getSpaceAt(wallPos.x + 1, wallPos.y));

      getSpaceAt(wallPos.x + 1, wallPos.y + 1).edges
      .remove(getSpaceAt(wallPos.x, wallPos.y + 1));

      getSpaceAt(wallPos.x, wallPos.y + 1).edges
      .remove(getSpaceAt(wallPos.x + 1, wallPos.y + 1));
    }

    else {

      getSpaceAt(wallPos.x, wallPos.y + 1).edges
      .remove(getSpaceAt(wallPos.x, wallPos.y));

      getSpaceAt(wallPos.x, wallPos.y).edges
      .remove(getSpaceAt(wallPos.x, wallPos.y + 1));

      getSpaceAt(wallPos.x + 1, wallPos.y + 1).edges
      .remove(getSpaceAt(wallPos.x + 1, wallPos.y));

      getSpaceAt(wallPos.x + 1, wallPos.y).edges
      .remove(getSpaceAt(wallPos.x + 1, wallPos.y + 1));
    }

  }
  
  /**
   * 
   * @param pawnPos
   */
  public void removePawn(Point pawnPos) {
   
      getSpaceAt(pawnPos.x, pawnPos.y).occupied = false;
  }

}
