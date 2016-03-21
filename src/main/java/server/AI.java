package server;

import java.util.ArrayList;

import client.GameBoard;
import client.Space;

public class AI {
  public GameBoard gameBoard; //the current game board
  private ArrayList<Space> openList; //locations where we can go
  private ArrayList<Space> closedList; //where we have been

  //ai player number
  private int playerNum;

  //arrays for the current position of each player
  private int X[]; // X-coord
  private int Y[]; // Y-coord

  public AI(int playerNumber) {

    playerNum = playerNumber; //give the ai its player number
    X = new int[4]; 
    Y = new int[4];
    //init our array lists
    openList = new ArrayList<Space>();
    closedList = new ArrayList<Space>();

    //create the game board here for future use
    gameBoard = GameBoard.getInstance();
  }

  public ArrayList<Space> getShortestPath(int playerNum){
    //initialization
    openList = new ArrayList<Space>();
    openList.add(gameBoard.getSpaceAt(X[playerNum], Y[playerNum]));



    return null;
  }

  public String getMove(){
    return ""; //sub
  }

  /**
   * 
   * @param x = the x-coord
   * @param y = the y-coord
   * @param playerNum = the player whos coords need to be updated
   */
  public void updatePlayerPosition(int x, int y, int playerNum){
    X[playerNum] = x;
    Y[playerNum] = y;
  }
}
