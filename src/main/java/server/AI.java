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

public class AI extends Player {

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

    //arrays for the current position of each player
    private int X[]; // X-coord
    private int Y[]; // Y-coord

    public AI(int playerNumber, int numberOfPlayers) {

        playerNum = playerNumber; //give the ai its player number
        numOfPlayers = numberOfPlayers;

        if (numOfPlayers == 2) {
            numWalls = 10;
        } else {
            numWalls = 5;
        }
        super.wallCount = numWalls;

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
        System.out.println("starting from: " + X[playerNum] + ", " + Y[playerNum]);

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
        if (numWalls >= 1) {
            String wall = makeValidWallPlacement(ais);

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
        if (move.occupied) {

            //we check the spaces in our path if they are occupied we can jump past them
            for (Space balls : ais) {
                //if a space in our path is not occupied then we can move there
                if (!balls.occupied) {
                    move = balls;
                    break; //we're done once we have a valid place to move
                }
            }

        }

        //to show where the ai is moving to
        System.out.println("moving to: " + move.x + ", " + move.y);

        //slow things down
        try {

            Thread.sleep(2000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

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
        //super.placeWall(x, y, direction);
        Point p = new Point(x, y);
        gameBoard.placeWall(p, direction);
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

                            if (super.isValidWallPlacement(p, d)) {
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

                            if (super.isValidWallPlacement(p, d)) {
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

                        if (super.isValidWallPlacement(p, d)) {
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
                            if (super.isValidWallPlacement(p, d)) {
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
                            if (super.isValidWallPlacement(p, d)) {
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

                        if (super.isValidWallPlacement(p, d)) {
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
                        if (super.isValidWallPlacement(p, d)) {
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
                        if (super.isValidWallPlacement(p, d)) {
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
                        if (super.isValidWallPlacement(p, d)) {
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
                        if (super.isValidWallPlacement(p, d)) {
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
                        if (super.isValidWallPlacement(p, d)) {
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
                        if (super.isValidWallPlacement(p, d)) {
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
}
