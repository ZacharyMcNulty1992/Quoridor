package server;

import java.util.ArrayList;

import client.GameBoard;
import client.Space;
import static java.lang.Math.abs;
import java.util.HashSet;
import java.util.Set;

public class AI {
  public GameBoard gameBoard; //the current game board
  private ArrayList<Space> openList; //locations where we can go
  private ArrayList<Space> closedList; //where we have been

  //ai player number
  private int playerNum;
  private int opponentNum;
  
  //arrays for the current position of each player
  private int X[]; // X-coord
  private int Y[]; // Y-coord

  private int heuristic;
  
  
  public AI(int playerNumber) {

    playerNum = playerNumber; //give the ai its player number
    
    //get the opponents number
    if(playerNumber == 1)
        opponentNum = 2;
    else
        opponentNum = 1;
    
    X = new int[4]; 
    Y = new int[4];

    //create the game board here for future use
    gameBoard = GameBoard.getInstance();
  }

  public ArrayList<Space> getShortestPath(int playerNum){
    
    //initialization
    openList = new ArrayList<Space>();
    closedList = new ArrayList<Space>();
    
    int current[] = new int[2];
    int target; //just the y coord of where they need to go
    
    //get the current position of the player
    current[0] = X[playerNum];
    current[1] = X[playerNum];
    //we only care about getting to the other side of the board
    //so we will only use the y coord for the heuristic for the shortest 
    if(Y[playerNum] == 0)
        target = 8;
    else
        target = 0;
    calcHeuristic(current, target);
    
    
    
    
    return null;
  }
  
  /**
   * this method will calculate a heuristic for finding a shortest path
   * @param currentPos - [x,y] where the player currently is
   * @param targetPos  - y-coordinate we want to have to win
   */
  
  private void calcHeuristic(int currentPos[], int targetY) {
    int dx = abs(currentPos[0]);
    int dy = abs(currentPos[1] - targetY);
    heuristic = (dx - dy);
  }
    
  public String getMove(){
      //the ai's shortest path list
      ArrayList<Space> ais = new ArrayList<>();
      
      //other players shortest path list
      ArrayList<Space> opponent = new ArrayList<>();
      //get our ai's shortest path to the end
      ais = getShortestPath(playerNum);
      //get the shortest path of the other players
      opponent = getShortestPath(opponentNum);
      
      //compare the sizes of the arrays
      int aiSize = ais.size();
      int opponentSize = opponent.size();
      
      
      
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
