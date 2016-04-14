package server;

import java.util.ArrayList;

import client.GameBoard;
import client.Space;
import java.awt.Point;
import java.util.HashSet;

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

    //arrays for the current position of each player
    private int X[]; // X-coord
    private int Y[]; // Y-coord

    public AI(int playerNumber) {

        playerNum = playerNumber; //give the ai its player number

        X = new int[5];
        Y = new int[5];

        X[1] = 4; //player 1 x
        Y[1] = 0; //player 1 y

        X[2] = 4; //player 2 x
        Y[2] = 8; //player 2 y

        X[3] = 0;
        Y[3] = 4;

        X[4] = 8;
        Y[4] = 4;

        //create the game board here for future use
        gameBoard = new GameBoard();
    }

    public ArrayList<Space> getShortestPath(int playerNum) {

        System.out.println("starting from: " + X[playerNum] + ", " + Y[playerNum]);

        //initialization
        ArrayList<Space> q = new ArrayList<>();
        ArrayList<Space> visited = new ArrayList<>();
        path = new ArrayList<>();

        int targetY = 0;

        if (playerNum == 1) //this is testing for player 2 not player 1
        {
            targetY = 8;
        } else //player 1 is at index 0
        {
            targetY = 0;
        }

        Space targetNode = gameBoard.getSpaceAt(X[playerNum], targetY);

        Space current = gameBoard.getSpaceAt(X[playerNum], Y[playerNum]); //where to start our search

        current.prev = null;
        HashSet<Space> NeighbourSet = new HashSet<>();
        q.add(current);
        visited.add(current);

        while (!q.isEmpty()) { //main loop

            //get the set of neighbour nodes
            NeighbourSet = current.edges;
            for (Space a : NeighbourSet) {
                if (!visited.contains(a)) {
                    q.add(a);
                    visited.add(a);
                    a.prev = current;
                }
            }

            //get the next node to test
            if (!q.isEmpty()) {
                q.remove(current);
                if (!q.isEmpty()) {
                    current = q.get(0);
                } else {
                    break;
                }
            }

        } //end of while loop

        current = targetNode;

        while (true) {

            path.add(current);

            if (current.x == X[playerNum] && current.y == Y[playerNum]) {
                break;
            } else {
                current = current.prev;
            }
        }

        return path;

    }

    public String getMove() {
        //the ai's shortest path list
        ArrayList<Space> ais;

        //get our ai's shortest path to the end
        ais = getShortestPath(playerNum);

        //compare the sizes of the arrays
        int aiSize = ais.size();
        //int opponentSize = opponent.size();

        Space move;

        if (ais.size() < 2) {
            move = ais.get(0); //get the next move we should make from here
        } else {
            move = ais.get(ais.size() - 2);
        }

        //see if we can pawn jump
        ArrayList<Space> valid;
        if (move.occupied) {
            //get valid spaces
            valid = gameBoard.getValidPlayerJumpMoves(move);

            //now we see what space is in our path and thats where we will go
            for (Space v : valid) {
                //if v is in our path
                if (ais.contains(v)) {
                    //we make that our move
                    move = v;
                }
                //else we check the next node
                //TODO: if we have no moves in the path
            }

        }

        //testing
        updatePlayerPosition(move.x, move.y, playerNum);

        System.out.println("moving to: " + X[playerNum] + ", " + Y[playerNum]);

        //slow things down
//        try {
//
//            Thread.sleep(1000);
//
//        } catch (InterruptedException e) {
//
//            e.printStackTrace();
//
//        }

        return ("TESUJI (" + move.x + ", " + move.y + ")");
    }

    /**
     *
     * @param x = the x-coord
     * @param y = the y-coord
     * @param playerNum = the player whos coords need to be updated
     */
    public void updatePlayerPosition(int x, int y, int playerNum) {

        Point currentPos = new Point(X[playerNum], Y[playerNum]);

        X[playerNum] = x;
        Y[playerNum] = y;

        Point newPos = new Point(X[playerNum], Y[playerNum]);

        gameBoard.movePawn(currentPos, newPos);
    }

    public void placeWalls(int x, int y, char direction) {
        Point p = new Point(x, y);
        gameBoard.placeWall(p, direction);
    }
}
