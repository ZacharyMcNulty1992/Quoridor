package main.java.server;

import java.util.ArrayList;

import main.java.client.Space;

public class AI {
    public ArrayList<Space> gameBoard; //the current game board
    private ArrayList<Space> openList; //locations where we can go
    private ArrayList<Space> closedList; //where we have been
    
    //ai player number
    private int playerNum;
    
    //current position of ai on the board
    private int currentX; // X-coord
    private int currentY; // Y-coord
    
    //current position of the player on the board
    private int playerX; // players X-coord
    private int playerY; // players Y-coord
    
    public AI(int playerNumber) {
    	
    	playerNum = playerNumber; //give the ai its player number
    	
    	//init our array lists
    	openList = new ArrayList<Space>();
    	closedList = new ArrayList<Space>();
    	
    	//create the game board here for future use
    	initGraph();
    }
    
    public ArrayList<Space> getShortestPath(int playerNum){
    	//initialization
    	
    	return null;
    }
    
    public String getMove(){
    	return ""; //sub
    }
    
    public void initGraph() {
	
	gameBoard = new ArrayList<Space>();
	Space s; // temp space variable
	// create all the spaces objects
	for (int i = 0; i < 9; i++) {
	    for (int p = 0; p < 9; p++) {
		s = new Space(i, p);
		gameBoard.add(s);
	    }
	}
	
	// now we establish the connections
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
}
