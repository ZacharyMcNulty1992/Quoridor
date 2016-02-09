package client;

/**
 * @author Brandon Williams
 * @date   2/9/2016 - Last updated
 */
public class GameBoard {

    private GameObject[][] gameBoard;

    /**
     * Constructs an empty game board
     */
    public GameBoard() {

	gameBoard = new GameObject[9][9];
    }

    /**
     * @return The gameGame board data structure
     */
    public GameObject[][] getGameBoard() {

	return gameBoard;
    }
}