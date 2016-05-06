package server;

import java.util.ArrayList;

import client.GameBoard;
import client.Player;
import client.Space;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AI {

    public final int WEIGHT = 1;
    public int numWalls;
    public HashMap<Point, Character> wallsMap;
    public GameBoard gameBoard; //the current game board
    private ArrayList<Space> openList; //locations where we can go
    private ArrayList<Space> closedList; //where we have visited
    private ArrayList<Space> path; //the shortest path
    private ArrayList<Space> aiPath; //path of the ai
    private ArrayList<Space> opponentPath; //path of the opponent

    //ai player number
    private int playerNum;
    private int numOfPlayers;
    private int delay;

    //arrays for the current position of each player
    private int X[]; // X-coord
    private int Y[]; // Y-coord

    public AI(int playerNumber, int numberOfPlayers, int delayTime) {

        playerNum = playerNumber; //give the ai its player number
        numOfPlayers = numberOfPlayers;
        delay = delayTime;

        if (numOfPlayers == 2) {
            numWalls = 10;
        } else {
            numWalls = 5;
        }

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

    /*
     * @Params: int player number 
     * @Returns: an arrayList containing the shortest path
     */
    public ArrayList<Space> getShortestPath(int playerNum) {

        //print where we are starting from
//        System.out.println("player : " + playerNum + " Starting from: " + X[playerNum] + ", " + Y[playerNum]);
        //initialization
        ArrayList<Space> q = new ArrayList<>();
        ArrayList<Space> visited = new ArrayList<>();
        path = new ArrayList<>();

        //init variables
        int targetY = 0;
        int targetX = 0;

        //get the win condition for each player
        switch (playerNum) {
            case 1:
                targetY = 8;
                break;
            case 2:
                targetY = 0;
                break;
            case 3:
                targetX = 8;
                break;
            case 4:
                targetX = 0;
                break;
            default:
                break;
        }

        Space targetNode = gameBoard.getSpaceAt(targetX, targetY);

        Space current = gameBoard.getSpaceAt(X[playerNum], Y[playerNum]); //where to start our search

        current.prev = null;
        HashSet<Space> NeighbourSet;
        q.add(current);
        visited.add(current);
        boolean pathFound = false;

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

                    //if we are player 1 or 2
                    if (playerNum == 1 || playerNum == 2) {
                        //check to see if this node is a winning node
                        if (a.y == targetY) {
                            //if it is then we have found a path
                            pathFound = true;
                            //make the target node the node we are checking
                            targetNode = a;
                        }

                        //if we are player 3 or 4
                    } else if (playerNum == 3 || playerNum == 4) {
                        //check to see if the current node is a winning node
                        if (a.x == targetX) {
                            //we have found a path
                            pathFound = true;
                            //the target node is the node we are looking at
                            targetNode = a;
                        }
                    }

                }
            }
            //if the path is found then we break out of the loop
            if (pathFound) {
                break;
            }

            //get the next node to test
            if (!q.isEmpty()) {
                //we remove the current node
                q.remove(current);

                //see if the queue is empty
                if (!q.isEmpty()) {
                    //if it isnt then get the next node from the queue
                    current = q.get(0);
                } else {
                    //if it is empty then we are done
                    break;
                }

            }

        } //end of while loop

        //look at the target node
        current = targetNode;

        while (true) {

            //here we are adding nodes to a list to have a path
            path.add(current);

            //if the node we are looking at is the node occupied by the player
            if (current.x == X[playerNum] && current.y == Y[playerNum]) {
                //if it is then we are done
                break;
            } else {
                //if not we get the next node
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

        //based on the player number we see other players shortest path
        //the they are shorter than our path then we will want to place a wall
        //assuming we have some left.
        System.out.println("Number of Walls left: " + numWalls);
        if (numWalls >= 1) {
            //String wall = makeValidWallPlacement(ais);

            String wall = "";

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (numOfPlayers == 2) {
                ArrayList<Space> o2 = new ArrayList<>();

                //if we are player 1 get player 2's shortest path
                if (playerNum == 1) {
                    o2 = getShortestPath(2);
                } else //else we are player 2 and we want player 1's path
                {
                    o2 = getShortestPath(1);
                }
                wall = makeGoodWallPlacement2(ais, o2);
            } else {
                wall = makeValidWallPlacement(ais);
            }

            //if we want to place a wall return here
            if (wall != null) {
                return wall;
            }
            //else 
            //do nothing

        }

        //compare the sizes of the arrays
        int aiSize = ais.size();

        //holder variable for the move
        Space move;

        //we need to flip the path as it is backwards right now
        //add all elements in our path to a stack
        Stack<Space> x = new Stack();
        for (Space c : ais) {
            x.push(c);
        }
        ais = new ArrayList<>();
        //now we pop off from the stack to inverse the order of the arrayList
        while (!x.empty()) {
            ais.add(x.pop());
        }

        //now we get a move
        if (ais.size() < 2) {
            move = ais.get(0); //get the next move we should make from here
        } else {
            move = ais.get(1);
        }

        //see if we can pawn jump
        ArrayList<Space> valid;
        //if the space we want to move to is occupied
        if (move.occupied && ais.size() > 2) {

            //we check the spaces in our path if they are occupied we can jump past them
            for (Space balls : ais) {
                //if a space in our path is not occupied then we can move there
                if (!balls.occupied) {
                    move = balls;
                    break; //we're done once we have a valid place to move
                }
            }

        } else if (move.occupied && ais.size() < 2) {
            //in this case we only have one space we can move but it is occupied
            HashSet<Space> lastMove = move.edges;
            for (Space v : lastMove) {
                if (!v.occupied) {
                    move = v;
                    break;
                }
            }
        }

        //to show where the ai is moving to
        System.out.println("player: " + playerNum + " is moving to: " + move.x + ", " + move.y);

        //slow things down
        try {

            Thread.sleep(delay);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        System.out.println("player: " + playerNum + " is sending " + "TESUJI (" + move.x + ", " + move.y + ")");

        //return a properly formated move string
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

    private String makeGoodWallPlacement2(ArrayList<Space> ais, ArrayList<Space> o1) {

        String wallPlacement = "";
        boolean done = false; //if we are done finding a wall to place
        boolean valid = false; //used to see if a wall placement is valid
        boolean blockpath1 = false;
        boolean blockpath2 = false;
        int opponent = 0;
        char dir; //direction the other player is going
        Space tmp; //current space of the player 2

        int x = 0;
        int y = 0;

        //x and y of player 1 or 2
        if (playerNum == 1) { //get player 2's coords
            x = X[1];
            y = Y[1];
            opponent = 2;
        } else if (playerNum == 2) { //get player 1's coords
            x = X[0];
            y = Y[0];
            opponent = 1;
        }

        //change in x and y
        int deltaX = 0;
        int deltaY = 0;

        //counter to check different nodes in the opponent's path
        int count = o1.size() - 1;

        //get the move infront of the player
        tmp = o1.get(count);
        if (tmp.x < 8 || tmp.y > 8) {
            if (count < o1.size()) {
                tmp = o1.get(count - 1);
            } else {
                return null;
            }
        }

        while (!done) {

            //now determine where the player is headed
            //check the players position compared to the position of the next space
            deltaX = tmp.x - x;
            deltaY = tmp.y - y;

            //if our change in x between the space we are checking and the next space
            if (deltaX != 0) {
                //if we are here the shortest path says the ai will move either left or right
                dir = 'h';
                //see if the wall is a valid placement first
                //ie) it doesnt intersect other walls and it is not placed off the board
                valid = gameBoard.isWallPlacementValid(tmp, dir);
                if (valid) {

                    //check to see if this wall will block any players from being
                    //able to finish
                    blockpath1 = doesWallBlockPath(tmp, dir, 1);
                    blockpath2 = doesWallBlockPath(tmp, dir, 2);

                    System.out.println("path 1 blocked: " + blockpath1);
                    System.out.println("path 2 blocked: " + blockpath2);

                    if (!blockpath1 && !blockpath2) { //if not then we can place the wall
                        return "TESUJI [(" + tmp.x + ", " + tmp.y + "), " + dir + "]";
                    }
                } else {
                    //do nothing
                }

            } else if (deltaY != 0) { //here the player will be moving forward or backward
                //here the ai will determine if the player will move from 
                dir = 'v';

                //check to see if the wall is a valid wall placement
                valid = gameBoard.isWallPlacementValid(tmp, dir);
                if (valid) {
                    //check to see if the wall will block a player from being able to finish
                    blockpath1 = doesWallBlockPath(tmp, dir, 1);
                    blockpath2 = doesWallBlockPath(tmp, dir, 2);

                    System.out.println("path 1 blocked: " + blockpath1);
                    System.out.println("path 2 blocked: " + blockpath2);

                    if (!blockpath1 && !blockpath2) { //if not then we can place the wall
                        {
                            return "TESUJI [(" + tmp.x + ", " + tmp.y + "), " + dir + "]";
                        }
                    }
                } else {
                    //do nothing 
                }
            }

            //here we need to check the next node since we did not 
            x = tmp.x;
            y = tmp.y;

            if (count <= 0) {
                //if we get here then there is no optimal wall placement
                //and we are fucked and return null
                return null;
            } else {
                //get the next node to look at
                count--;
                tmp = o1.get(count);
//                System.out.println("now looking at node " + tmp);
            }
            //if we get here we will check the next space to see if it would be 
            //a good place to place a wall
        }

        return null;

    }

    private String makeGoodWallPlacement4(ArrayList<Space> ais, ArrayList<Space> o1, ArrayList<Space> o2, ArrayList<Space> o3) {

        return null;
    }

    private String makeValidWallPlacement(ArrayList<Space> ais) {
        ArrayList<Space> opponent2Path;
        ArrayList<Space> opponent3Path;
        ArrayList<Space> opponent4Path;
        Random xcoord = new Random();
        Random ycoord = new Random();
        Point p; //for wall placement
        char d; //direction of wall
        int r;
        int e;

        switch (playerNum) {

            case 1: //this case we are player 1
                //get player 2's shortest path    
                opponent2Path = getShortestPath(2);
                if (numOfPlayers == 4) {
                    opponent3Path = getShortestPath(3);
                    opponent4Path = getShortestPath(4);
                    if (opponent3Path.size() < ais.size()) {
                        //here we will see if we need to block off player 3
                        //return a wall blocking player 3
                        for (; true;) {
                            r = xcoord.nextInt() % 7 + 1;
                            e = ycoord.nextInt() % 7 + 1;
                            if (r < 0) {
                                r = r * -1;
                            }
                            if (e < 0) {
                                e = e * -1;
                            }

                            p = new Point(r, e);

                            if ((xcoord.nextInt() % 2) == 1) {
                                d = 'v';
                            } else {
                                d = 'h';
                            }

                            if (gameBoard.isWallPlacementValid(p, d)) {
                                numWalls--;
                                return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                            }
                        }
                    } else if (opponent4Path.size() < ais.size()) {
                        //block off player 4
                        //return a wall blocking player 4
                        for (; true;) {
                            r = xcoord.nextInt() % 7 + 1;
                            e = ycoord.nextInt() % 7 + 1;
                            if (r < 0) {
                                r = r * -1;
                            }
                            if (e < 0) {
                                e = e * -1;
                            }

                            p = new Point(r, e);

                            if ((xcoord.nextInt() % 2) == 1) {
                                d = 'v';
                            } else {
                                d = 'h';
                            }

                            if (gameBoard.isWallPlacementValid(p, d)) {
                                numWalls--;
                                return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                            }
                        }
                    }
                }
                if (opponent2Path.size() < ais.size()) {
                    //block player 2
                    //return a wall blocking player 2
                    for (; true;) {
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        if (gameBoard.isWallPlacementValid(p, d)) {
                            //if it is then we reduce the number of walls
                            numWalls--;

                            //we return a wall placement string
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                }
                //if we are the shortest path we do nothing
                break;

            case 2:
                //this case we are player 2
                //get player 1's shortest path    
                opponent2Path = getShortestPath(1);
                if (numOfPlayers == 4) {
                    //get player 3,4's shortest path
                    opponent3Path = getShortestPath(3);
                    opponent4Path = getShortestPath(4);
                    if (opponent3Path.size() < ais.size()) {
                        //here we will see if we need to block off player 3
                        //return a wall blocking player 3
                        for (; true;) {
                            //get a random point
                            r = xcoord.nextInt() % 7 + 1;
                            e = ycoord.nextInt() % 7 + 1;
                            if (r < 0) {
                                r = r * -1;
                            }
                            if (e < 0) {
                                e = e * -1;
                            }

                            p = new Point(r, e);

                            //get a random direction
                            if ((xcoord.nextInt() % 2) == 1) {
                                d = 'v';
                            } else {
                                d = 'h';
                            }

                            //see if the wall placement is valid
                            if (gameBoard.isWallPlacementValid(p, d)) {
                                //if it is then we reduce the number of walls we have
                                numWalls--;
                                //return the wall placement string
                                return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                            }
                        }
                    } else if (opponent4Path.size() < ais.size()) {
                        for (; true;) {
                            //make a random point
                            r = xcoord.nextInt() % 7 + 1;
                            e = ycoord.nextInt() % 7 + 1;
                            if (r < 0) {
                                r = r * -1;
                            }
                            if (e < 0) {
                                e = e * -1;
                            }

                            p = new Point(r, e);

                            //get a random direction
                            if ((xcoord.nextInt() % 2) == 1) {
                                d = 'v';
                            } else {
                                d = 'h';
                            }

                            //check to see if the wall is valid
                            if (gameBoard.isWallPlacementValid(p, d)) {
                                //if it is reduce the number of walls we have
                                numWalls--;
                                //and return a wall placement string
                                return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                            }
                        }
                    }
                }
                if (opponent2Path.size() < ais.size()) {
                    //block player 1
                    //return a wall blocking player 1
                    for (; true;) {
                        //get a random point
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        //get a random direction
                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        if (gameBoard.isWallPlacementValid(p, d)) {
                            //reduce the number of walls
                            numWalls--;

                            //return a wall placement string
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                }
                //if we are the shortest path we do nothing
                break;

            case 3:
                //this case we are player 3
                //get player 1,2,4's shortest path    
                opponent2Path = getShortestPath(1);
                opponent3Path = getShortestPath(2);
                opponent4Path = getShortestPath(4);
                if (opponent3Path.size() < ais.size()) {
                    //here we will see if we need to block off player 3
                    //return a wall blocking player 3
                    for (; true;) {
                        //get a random point
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        //get a random direction
                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        //see if the wall placement is valid
                        if (gameBoard.isWallPlacementValid(p, d)) {
                            numWalls--;
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                } else if (opponent4Path.size() < ais.size()) {
                    //block off player 4
                    //return a wall blocking player 4
                    for (; true;) {
                        //get random point
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        //get random direction
                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        //see if the wall placement is valid
                        if (gameBoard.isWallPlacementValid(p, d)) {
                            //if it is then we reduce the number of walls we have
                            numWalls--;

                            //and return a wall placement string
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                }

                if (opponent2Path.size() < ais.size()) {
                    //block player 1
                    //return a wall blocking player 1
                    for (; true;) {
                        //get a random point
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        //get a random direction
                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        //see if the wall placement is valid
                        if (gameBoard.isWallPlacementValid(p, d)) {
                            //reduce the number of walls
                            numWalls--;

                            //return a wall placement string
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                }
                //if we are the shortest path we do nothing
                break;
            case 4:
                //this case we are player 4
                //get player 1,2,3's shortest path    
                opponent2Path = getShortestPath(1);
                opponent3Path = getShortestPath(2);
                opponent4Path = getShortestPath(3);
                if (opponent3Path.size() < ais.size()) {
                    //here we will see if we need to block off player 2
                    //return a wall blocking player 2
                    for (; true;) {
                        //get a random point
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        //get a random direction
                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        //see if the wall placement is valid
                        if (gameBoard.isWallPlacementValid(p, d)) {
                            numWalls--;
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                } else if (opponent4Path.size() < ais.size()) {
                    //block off player 3
                    //return a wall blocking player 3
                    for (; true;) {
                        //get random point
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        //get random direction
                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        //see if the wall placement is valid
                        if (gameBoard.isWallPlacementValid(p, d)) {
                            //if it is then we reduce the number of walls we have
                            numWalls--;

                            //and return a wall placement string
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                }

                if (opponent2Path.size() < ais.size()) {
                    //block player 1
                    //return a wall blocking player 1
                    for (; true;) {
                        //get a random point
                        r = xcoord.nextInt() % 7 + 1;
                        e = ycoord.nextInt() % 7 + 1;
                        if (r < 0) {
                            r = r * -1;
                        }
                        if (e < 0) {
                            e = e * -1;
                        }

                        p = new Point(r, e);

                        //get a random direction
                        if ((xcoord.nextInt() % 2) == 1) {
                            d = 'v';
                        } else {
                            d = 'h';
                        }

                        //see if the wall placement is valid
                        if (gameBoard.isWallPlacementValid(p, d)) {
                            //reduce the number of walls
                            numWalls--;

                            //return a wall placement string
                            return ("TESUJI [(" + p.x + ", " + p.y + "), " + d + "]");
                        }
                    }
                }
                //if we are the shortest path we do nothing
                break;

            default:
                break; //we only get here if something has gone wrong
        }

        //if we get here we do not want to place a wall
        return null;
    }

    /**
     * @param wallPos -- the point where we are trying to place a wall
     * @param direction -- the direction of the wall placement
     * @param playerNum -- player number of the player we want to see if we are
     * blocking
     * @return boolean if the wall is valid this will return false
     */
    public boolean doesWallBlockPath(Point wallPos, char direction, int playerNum) {

        //place a sudo wall
        gameBoard.placeWall(wallPos, direction);

        //variables for the target node location
        int targetY = 0;
        int targetX = 0;

        //get the shortest path of the current player
        ArrayList<Space> spath = getShortestPath(playerNum);

        //target- should be the target node if it isnt then we dont have
        //a path to the end of the board
        Space target = spath.get(0);

        //get the current position of the player we are looking at
        Space startingPos = gameBoard.getSpaceAt(X[playerNum], Y[playerNum]);

        //get the winnning positions for the player we are looking at
        switch (playerNum) {
            case 1:
                targetY = 8;
                break;
            case 2:
                targetY = 0;
                break;
            case 3:
                targetX = 8;
                break;
            case 4:
                targetX = 0;
                break;
            default:
                break;
        }

        System.out.println("player " + playerNum + " path.get(0) is: " + target);

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }

        //if we check the path for the players 1 and 2
        if (playerNum == 1 || playerNum == 2) {
            if ((target.y == targetY) && spath.contains(startingPos)) {
                gameBoard.removeWall(wallPos, direction); //remove the wall
                return false; //we dont block the player with the wall placement
            } else {
                gameBoard.removeWall(wallPos, direction); //remove the wall
                return true;//we do block the player with that wall placement
            }
        } else //we are looking for player 3 or 4
         if ((target.x == targetX) && spath.contains(startingPos)) {
                gameBoard.removeWall(wallPos, direction); //remove the wall
                return false; //we dont block the player with the wall placement
            } else {
                gameBoard.removeWall(wallPos, direction); //remove the wall
                return true; //we do block the player with that wall placement
            }
    }
}
