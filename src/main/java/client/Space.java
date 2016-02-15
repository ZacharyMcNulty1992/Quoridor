package main.java.client;

import java.awt.Point;

public class Space extends Point{
    
    // variables can be accessed by calling them
    // thus we do not need getters.
    public Space Left;
    public Space Right;
    public Space Up;
    public Space Down;
    public boolean occupied;
    
    /**
     * constructor
     * 
     * @param x - the x-coordinate of the space
     * @param y - the y-coordinate of the space
     */
    public Space(int x, int y) {
	super(x,y);
    }
    
    /**
     * setOccupied
     *
     * @param x - boolean value of weather the space has a player on it or not
     */
    public void setOccupied(boolean x) {
	occupied = x;
    }
    
}// end of class
