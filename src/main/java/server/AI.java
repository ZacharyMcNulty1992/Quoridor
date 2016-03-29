package server;

import java.util.ArrayList;

import client.GameBoard;
import client.Space;
import static java.lang.Math.abs;
import java.util.HashSet;
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
    
    X[0] = 4; //player 1 x
    Y[0] = 0; //player 1 y
    
    X[1] = 4; //player 2 x
    Y[1] = 8; //player 2 y
    
    //TODO: make the same thing for player 3 and 4 blah blah blah
    
    //create the game board here for future use
    gameBoard = GameBoard.getInstance();
  }

  public ArrayList<Space> getShortestPath(int playerNum){
    
    //initialization
    ArrayList<Space> q = new ArrayList<>();
    closedList = new ArrayList<>();
    path = new ArrayList<>();
    
    int targetY = 0;
    
    if(playerNum == 1)
        targetY = 0;
    else
        targetY = 8;
    
    //get all of the nodes of the board into a list
    for(Space b : gameBoard.getGameBoard()){
        q.add(b);
    }
    
    Space current = gameBoard.getSpaceAt(X[playerNum], Y[playerNum]); //where to start our search
    current.distance = 0; //set the distance of the starting node as 0
    int f_dist; //distance between nodes
    HashSet<Space> NeighbourSet = new HashSet<>();
    
    while(!q.isEmpty()){ //main loop
          
        //if the current node is at the target area then we return
        if(current.x == targetY) 
            break;
        
        //find the new current node
        int least_distance = Integer.MAX_VALUE;
        for(Space a : q){
            
            if(a.distance  < least_distance){
                least_distance = a.distance;
                current = a;
            }
        }
        
        //remove that node from the queue
        q.remove(current);
        
        
        //System.out.println("q size = " + q.size());
        //System.out.println("current node is at: " + current.x + " " + current.y);
        
        //System.out.println();
        
        //get the set of neighbour nodes
        NeighbourSet = current.edges;
        for(Space a : NeighbourSet){ 
            //get the distance between the current node and the node we are looking at
            f_dist = current.distance + WEIGHT; 
            if(f_dist < a.distance){
                a.distance = f_dist;
                a.prev = current;
            }
        }
    } //end of while loop
    
    Stack<Space> st = new Stack<>();
    //trace the path back to the 
    while (current.x != X[playerNum] && current.y != Y[playerNum]){
        System.out.println(current.x + " , " + current.y);
        st.push(current);
        current = current.prev;
    }
    
    //add the path in the correct order
    while(!st.isEmpty()){
        path.add(st.pop());
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
      
      Space move = ais.get(1); //get the next move we should make from here
      
      //testing
      updatePlayerPosition(move.x, move.y, playerNum);
      
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
