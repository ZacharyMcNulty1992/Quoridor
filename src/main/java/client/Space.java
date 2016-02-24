package main.java.client;

import java.awt.Point;
import java.util.HashSet;

public class Space extends Point{
    
    // variables can be accessed by calling them
    // thus we do not need getters.
    public HashSet<Space> edges;
    public boolean occupied;
    
    /**
     * constructor
     * 
     * @param x - the x-coordinate of the space
     * @param y - the y-coordinate of the space
     */
    public Space(int x, int y) {
    	super(x,y);
	
		edges = new HashSet<Space>(4);
		occupied = false;
    }
    
}// end of class
