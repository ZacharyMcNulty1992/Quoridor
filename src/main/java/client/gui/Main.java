package client.gui;
import client.Player;

import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import java.util.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.scene.Node.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.awt.Point;


public class Main extends Application{

    static int playerCount;
    public static int currentPlayer = 1;
    public static Point currentPos;
    static Point destination;

    static Pane root = new Pane();
    static GridPane vWallGrid;
    static GridPane hWallGrid;
    static GridPane pawns;

    static Circle pawn1 = new Circle(25, Color.WHITE);
    static Circle pawn2 = new Circle(25, Color.BLUE);
    static Circle pawn3 = new Circle(25, Color.ORANGE);
    static Circle pawn4 = new Circle(25, Color.PURPLE);

    static Player p1;
    static Player p2;
    static Player p3;
    static Player p4;

    public static Main gui = null;
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Main(){
        guiStartUpTest(this);
    }

    public static void setPlayerCount(int pc){
        playerCount = pc;
    }

    public static Main waitForStartUp(){
    try{
        latch.await();
    }catch(InterruptedException e){
        e.printStackTrace();
    }
    return gui;
    }

    public static Player currentPlayer(){
    if(currentPlayer == 1)
        return p1;
    else if(currentPlayer == 2)
        return p2;
    else if(currentPlayer == 3)
        return p3;
    else
        return p4;
    }

    public static void setCurrentPlayer(int pn) {
       currentPlayer = pn;
    }

    public static void guiStartUpTest(Main g){
    gui = g;
    latch.countDown();
    }

    public static void Atari(Point cur, Point dest){
        currentPos = cur;
        destination = dest;

	Platform.runLater(new Runnable(){
	    @Override
	    public void run(){
		//currentPlayer().movePawn(dest.x,dest.y);   
        //d.relocate(dest.x,dest.y);
        //pawns.setConstraints(pawn1, dest.x,dest.y );
        //root.requestLayout();
            
        movePawns(currentPlayer, dest);
        
		//System.out.println("Cur = " + currentPos);
		System.out.println("Dest = "+ dest);
		//currentPos = destination;
	    }
	});

    }

    @Override
    public void start(final Stage primaryStage) {
        drawBoard();
        handlePawns();
        pawns.add(pawn1, 4,0);
        pawns.add(pawn2, 4,8);
        if (playerCount > 2){
            pawns.add(pawn3, 0,4);
            pawns.add(pawn4, 8,4);
        }

        // Creates a scene with a default size and sets the primaryStage(all panes so far) on it.
        Scene scene = new Scene(root, 600, 600, Color.BLACK);
        primaryStage.setScene(scene);

        // Display primaryStage
        primaryStage.show();

        
        // Handles click events for the game
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                // returns coordinate on the board when a square is clicked
                for(Node node: pawns.getChildren()) {
                    node.setOpacity(0.0);

                    if(node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                        System.out.println(GridPane.getRowIndex(node) + "," + GridPane.getColumnIndex(node));
                        // row = GridPane.getRowIndex(node);
                        // column = GridPane.getColumnIndex(node);
                        node.setOpacity(1.0);
                        // Circle c = new Circle(25, Color.WHITE);
                        // board.add(c, GridPane.getColumnIndex(node), GridPane.getRowIndex(node));
                    }
                }

                // returns coordinate on the board when a vertical wall is clicked
                for(Node node: vWallGrid.getChildren()) {

                    if(node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                        System.out.println("v "+ GridPane.getRowIndex(node) + "," + GridPane.getColumnIndex(node));
                    }
                }

                // returns coordinate on the board when a horizontal wall is clicked
                for(Node node: hWallGrid.getChildren()) {

                    if(node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                        System.out.println("h "+ GridPane.getRowIndex(node) + "," + GridPane.getColumnIndex(node));
                    }
                }
            }
        });
    }

    private void drawBoard(){
        // Creates a gridPane for the board squares
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(10);
        board.setVgap(10);
        board.setPadding(new Insets(25, 25, 25, 25));
        board.setGridLinesVisible(false);

        // Loop to create the board
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                Rectangle r = new Rectangle(50, 50, Color.GREEN);
                board.add(r, i, j);
            }
        }

        // Creates a gridPane with spacing for vertical walls
        vWallGrid = new GridPane();
        vWallGrid.setAlignment(Pos.CENTER);
        vWallGrid.setHgap(50);
        vWallGrid.setVgap(10);
        vWallGrid.setPadding(new Insets(25, 25, 25, 25));

        // Loop to create locations for vertical walls 
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 9; j++) {
                vWallGrid.add(new Rectangle(10, 50, Color.TRANSPARENT), i+ 1, j);
            }
        } 

        // Creates a gridPane with spacing for horizontal walls
        hWallGrid = new GridPane();
        hWallGrid.setAlignment(Pos.CENTER);
        hWallGrid.setHgap(10);
        hWallGrid.setVgap(50);
        hWallGrid.setPadding(new Insets(25, 25, 25, 25));

        // Loop to create locations for horizontal walls
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 8; j++) {
                hWallGrid.add(new Rectangle(50, 10, Color.TRANSPARENT), i, j + 1);
            }
        }

        // Adds the grip panes to the root pane which will be displayed
        root.getChildren().add(board);
        root.getChildren().add(vWallGrid);
        root.getChildren().add(hWallGrid);
    }

    private void handlePawns(){
        // Creates a GridPane for pawns to move on
        pawns = new GridPane();
        pawns.setAlignment(Pos.CENTER);
        pawns.setHgap(10);
        pawns.setVgap(10);
        pawns.setPadding(new Insets(25, 25, 25, 25));
        pawns.setGridLinesVisible(false);

        // Creates a grid of invisible circles for pawns to populate
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                Circle c = new Circle(25, Color.WHITE);
                c.setOpacity(0.0);
                pawns.add(c, i, j);
            }
        }
        root.getChildren().add(pawns);
    }

    public static void setPlayers(ArrayList<Player> playerList){
        p1 = playerList.get(0);
        p2 = playerList.get(1);
        if(playerList.size() == 4){
            p3 = playerList.get(2);
            p4 = playerList.get(3);
        }
    }

    public static void movePawns(int pn, Point dest){
        switch (pn){
            case 1: 
                pawns.setConstraints(pawn1, dest.x, dest.y );
                break;
            case 2: 
                pawns.setConstraints(pawn2, dest.x, dest.y );
                break;
            case 3: 
                pawns.setConstraints(pawn3, dest.x, dest.y );
                break;
            default: 
                pawns.setConstraints(pawn4, dest.x, dest.y );
                break;

        }
    }
}
