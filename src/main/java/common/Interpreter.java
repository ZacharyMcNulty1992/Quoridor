//I will rewrite this method more efficiently using isValid()

package common;

import java.util.Arrays;

/**
 * @author Jade
 * @edited Brandon
 * @date 3/31/16
 */
public class Interpreter {

  /**
   * Parses and validates the message coming in from
   * the server.
   * 
   * @param input The message recieved from the client or server.
   */
  public static boolean isValidString(String input){

    String[] splitInput = input.split(" ");

    switch(splitInput[0]) {

    case "HELLO":
    case "MYOUSHU":
      return splitInput.length == 1;

    case "IAM":
      return splitInput.length == 2;

    case "GAME":
      return validateGame(splitInput);

    case "TESUJI":
      return validateTesuji(splitInput);
      
    case "ATARI":
      return validateAtari(splitInput);
      
    case "GOTE":
    case "KIKASHI":
      
      try{
        
        if(Integer.parseInt(splitInput[1]) > 4 || Integer.parseInt(splitInput[1]) < 0){
          
          return false;
        }
        
      } catch(NumberFormatException e){
        
        return false;
      }
      
      return splitInput.length == 2;

    default:
      //System.out.println("Bad Op Code");
      return false;
    }
  } 

  /**
   * Checks if proper arguments are given to Tesuji op code
   * 
   * @param args The arguments passed to Tesuji.
   */
  private static boolean validateTesuji(String[] args) {

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

      //System.out.println("Wall move seen");

      if(args.length == 2){

        //System.out.println("Wall move with no spaces seen");

        //Replace the parenthesis and brackets with empty string 
        moveArg = args[1].replace("[(", "")
            .replace(")",""). replace("]","");

        //System.out.println(moveArg);
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

  /**
   * 
   * @param moveString
   * @return
   */
  private static boolean validateMoveString(String moveString) {

    //Then split on the comma since there are no spaces
    String[] position = moveString.split(",");

    /*	for(String s : position)
	    System.out.print(s + " ");

	    System.out.println();*/

    try {	
      //
      if(position.length == 2) {	

        Integer.parseInt(position[0]);
        Integer.parseInt(position[1]);
      }

      else if(position.length == 3 &&
          (position[2].equals("h") || 
              position[2].equals("v")) ) {

        Integer.parseInt(position[0]);
        Integer.parseInt(position[1]);
      }

      else
        return false;

    } catch(NumberFormatException ex) {

      return false;
    }

    return true;
  }

  /**
   * 
   * @param args
   * @return
   */
  private static boolean validateGame(String[] args) {

    if (args.length != 4 || args.length != 6) {

      return false;
    }

    try{

      if(Integer.parseInt(args[1]) > 4 || Integer.parseInt(args[1]) < 0){

        return false;
      }

    } catch(NumberFormatException e){

      return false;
    }

    return true;
  }

  /**
   * 
   * @param args
   * @return
   */
  private static boolean validateAtari(String[] args) {

    try{

      if(Integer.parseInt(args[1]) > 4 || Integer.parseInt(args[1]) < 0){

        return false;
      }

    } catch(NumberFormatException e){

      return false;
    }
    
    return validateTesuji(Arrays.copyOfRange(args, 1, args.length));
  }
}
