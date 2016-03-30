//I will rewrite this method more efficiently using isValid()

package common;

import java.awt.Point;
import java.util.*;
import java.io.*;


/**
 * @author Jade
 * @edited Brandon
 * @date 3/30/16
 */
public class Interpreter {

    /**
     * Parses and validates the message coming in from
     * the server.
     * 
     * @param input The message recieved from the server.
     */
    public static String[] parseString(String input){
	
	String[] splitInput = input.split();

	switch(splitInput[0]) {

	case "TESUJI":
	    return validateTesuji(splitInput);

	default:
	    
	}
    } 
    
    /**
     * Checks if proper arguments are given to Tesuji op code
     * 
     * @param args The arguments passed to Tesuji.
     */
    private static String[] validateTesuji(String[] args) {
	
	String moveArg = "";

	//If it is a pawn move
	if(args[1].startsWith("(")) {
	    
	    //If there are no spaces in the <move> string
	    if(args.length == 2){
		
		//Replace the parenthesis with empty string 
		moveArg = args[1].replace("(", "")
		    .replace(")",""); 

	    }// end if args.length == 2
	    
	    else if(args.length == 3) {
		
		//Remove parenthesis
		moveArg = args[1].replace("(", "") + 
		    args[2].replace(")", "");
	    }
	}
	    
	else if(args[1].startsWith("[")) {
	    
	    if(args.length == 2){

		//Replace the parenthesis and brackets with empty string 
		moveArg = args[1].replace("[(", "")
		    .replace(")",""). replace("]","");
	    }

	    else if( args.length == 4 ) {

		// Replace parenthesis and brackets with empty string
		// and concantenate elements 
		moveArg = args[1].replace("[(", "") +
		    args[2].replace(")", "") + 
		    args[3].replace("]", "");
	    }

	}// end if args[1] startWith "["

	return validateMoveString(moveArg);
    }

    private static String[] validateMoveString(String moveString) {

	//Then split on the comma since there are no spaces
	String[] position = moveString.split(",");

	try {	
	    //
	    if(position.length == 2) {	
		
		Integer.parseInt(position[0]);
		Integer.parseInt(position[1]);
	    }
	    
	    else if(posAndDirection.length == 3 &&
		    (position[2] == "h" || position[2] == "v")) {
		
		Integer.parseInt(position[0]);
		Integer.parseInt(position[1]);
	    }
	    
	    else
		return new String[] {"GOTE"};

	} catch(NumberFormatException ex) {
	    
	    return new String[] {"GOTE"};
	}
	
	return position;
    }
}
