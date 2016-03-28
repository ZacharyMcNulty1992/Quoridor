package client;

import java.util.regex.Pattern;

/**
 * @author Jade Kevin Bestami
 * @date   3/27/2016 - last updated
 */
 

public class Util{
	
	//all regex values for protocol's command strings
	public static final String[] regexValues = 
					
					{"IAM [^\\s]+",
					"GAME [12] [^\\s]+ [^\\s]+",
					"GAME [1-4] [^\\s]+ [^\\s]+ [^\\s]+ [^\\s]+",
					"TESUJI \\[\\([0-8], [0-8]\\), [vh]\\]",
					"TESUJI \\([0-8], [0-8]\\)",
					"ATARI [1-4] \\[\\([0-8], [0-8]\\), [vh]\\]",
					"ATARI [1-4] \\([0-8], [0-8]\\)",
					"GOTE [1-4]",
					"KIKASHI [1-4]",
					"MYOUSHU"};
					
					

	//simple valid function, takes string to test as arg
	//returns true if valid, false if not
	public static boolean isValid(String toTest){
	
	for(String regex : regexValues){
			if(Pattern.matches(regex, toTest))
				return true;
		}
		
		return false;
	}
	
	//param: command that toTest has to be,
	//only first letter necessary, (T for TESUJI)
	//except for GAME and GOTE
	//returns true if valid and corresponds to command
	//returns false if not
	public static boolean isValid(String toTest, String command)
								throws IllegalArgumentException{
	
		boolean err = true;
		
		for(String regex : regexValues){
			if(regex.startsWith(command))
				err=false;
		}
		
		if(err)
			throw new IllegalArgumentException(command +" is not a valid command name");
		if(command.equals("G"))
			throw new IllegalArgumentException("GAME or GOTE?");

	
	
		if(isValid(toTest)){
			if(toTest.charAt(0)=='G')
				return toTest.charAt(1)==command.charAt(1);
			return toTest.charAt(0)==command.charAt(0);
		}
		
		return false;
		
	}
	
	// still need parser method? Probably do the stuff 
    // the Interpreter class does but instead of returning
    // a GamePiece object, it returns an array of the important
    // variables, or maybe an object? in order to have 
    // for a wall 2 ints and a char...
    // maybe add a method that returns what type type of command it is?:
	// maybe one of the isvalid method is superfluous? 
					
}
