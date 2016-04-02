//I will rewrite this method more efficiently using isValid()

package common;

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
  public static String[] parseString(String input){

    String[] splitInput = input.split(" ");

    switch(splitInput[0]) {

    case "TESUJI":
      return validateTesuji(splitInput);

    default:
      //System.out.println("Bad Op Code");
      return new String[] {"GOTE"};
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

  private static String[] validateMoveString(String moveString) {

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
        return new String[] {"GOTE"};

    } catch(NumberFormatException ex) {

      return new String[] {"GOTE"};
    }

    return position;
  }
}
