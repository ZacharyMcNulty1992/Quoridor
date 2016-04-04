package server;

import java.util.ArrayList;

import client.GameBoard;
import client.Space;
import static java.lang.Math.abs;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class AI {
  
    public final int WEIGHT = 1;
    
    public GameBoard gameBoard; //the current game board
    private ArrayList<Space> openList; //locations where we can go
    private ArrayList<Space> closedList; //where we have visited
    private ArrayList<Space> path; //the shortest path
    private ArrayList<Space> aiPath; //path of the ai
    private ArrayList<Space> opponentPath; //path of the opponent
    
  //ai player number
  private int playerNum;
  private int opponentNum;
  
  //arrays for the current position of each player
  private int X[]; // X-coord
  private int Y[]; // Y-coord
  
  public AI(int playerNumber) {

    playerNum = playerNumber; //give the ai its player number
    
    //get the opponents number
    if(playerNumber == 1)
        opponentNum = 2;
    else
        opponentNum = 1;
    
    X = new int[4]; 
    Y = new int[4];
    
    X[0] = 4; //player 1 x
    Y[0] = 0; //player 1 y
    
    X[1] = 4; //player 2 x
    Y[1] = 8; //player 2 y
    
    //TODO: make the same thing for player 3 and 4 blah blah blah
    
    //create the game board here for future use
    gameBoard = new GameBoard();
  }

  public ArrayList<Space> getShortestPath(int playerNum){
    
    System.out.println("Current Position: " + X[playerNum] + ", " + Y[playerNum]);
      
    //initialization
    ArrayList<Space> q = new ArrayList<>();
    ArrayList<Space> visited = new ArrayList<>();
    path = new ArrayList<>();
    
    int targetY = 0;
    
    if(playerNum == 1) //this is testing for player 2 not player 1
        targetY = 0;
    else                //player 1 is at index 0
        targetY = 8;
    
    Space targetNode = gameBoard.getSpaceAt(targetY, X[playerNum - 1]);
    
    
    Space current = gameBoard.getSpaceAt(Y[playerNum], X[playerNum]); //where to start our search
    current.prev = null;
    HashSet<Space> NeighbourSet = new HashSet<>();
    q.add(current);
    visited.add(current);
    
    while(!q.isEmpty()){ //main loop
                
        //get the set of neighbour nodes
        NeighbourSet = current.edges;
        for(Space a : NeighbourSet){
                if(!visited.contains(a)){
                    q.add(a);
                    visited.add(a);
                    a.prev = current;
                }
        }
        
        //get the next node to test
        if(!q.isEmpty()){
            q.remove(current);
            if(!q.isEmpty())
                current = q.get(0);
        }
        
    } //end of while loop

    
    current = targetNode;
    
    while(true){
        
        path.add(current);
        
        if(current.x == X[playerNum] && current.y == Y[playerNum])
            break;
        else
            current = current.prev;
    }
    
    
    System.out.println("path size = " + path.size());
    return path;
  }
    
  public String getMove(){
      //the ai's shortest path list
      ArrayList<Space> ais = new ArrayList<>();
      
      //other players shortest path list
      //ArrayList<Space> opponent = new ArrayList<>();
      //get our ai's shortest path to the end
      ais = getShortestPath(playerNum);
      //get the shortest path of the other players
      //opponent = getShortestPath(opponentNum);
      
      //compare the sizes of the arrays
      int aiSize = ais.size();
      //int opponentSize = opponent.size();
      
      Space move;
      
      if(ais.size() < 2)
         move = ais.get(0); //get the next move we should make from here
      else
          move = ais.get(ais.size()- 2);
      
      //testing
      updatePlayerPosition(move.x, move.y, playerNum);
      
      System.out.println("current position: " + X[playerNum] + ", " + Y[playerNum]);
      
    return ("TESUJI (" + move.x + ", " + move.y + ")"); 
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
