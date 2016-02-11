package client;

/**
 * @author Brandon Williams
 * @date   2/9/2016 - Last updated
 */
public class GameBoard {

    private GamePiece[][] gameBoard;

    /**
     * Constructs an empty game board
     */
    public GameBoard() {

	   gameBoard = new GamePiece[9][9];
    }

    /**
     * @return The gameGame board data structure
     */
    public GamePiece[][] getGameBoard() {

	   return gameBoard;
    }
}