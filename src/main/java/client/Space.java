package client;

public class Space{

//variables can be accessed by calling them 
//thus we do not need getters.
public Space Left;
public Space Right;
public Space Up;
public Space Down;

//coords on the Board
public int x;
public int y;

/*
 * Space
 * 
 * Params: A pointer to each adj node, x coord, y coord
 */
public Space(Space up, Space down, Space left, Space right, int x, int y){	
	this.x = x;
	this.y = y;
	this.Left = left;
	this.Right = right;
	this.Down = down;
	this.Up = up;
}//end of constructor

}//end of class
