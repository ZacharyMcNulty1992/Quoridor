package client.gui;

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
    static Point currentPos;
    static String destination;

    public static void main(String[] args) {
        //playerCount = args[0];
        Application.launch(args);
    }

    public static void setPlayerCount(int pc){
	playerCount = pc;
    }

    public static void Atari(Point cur, String dest){
	currentPos = cur;
	destination = dest;
    }

    @Override
    public void start(final Stage primaryStage) {

        // Root pane to add other panes to
        Pane root = new Pane();
	//System.out.println("Testing if parameters are passed: " + currentPos);
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
                //r.setMouseTransparent(true);
                //GridPane.setRowIndex(r, i);
                //GridPane.setColumnIndex(r, j);
            }
        }

        // Creates a GridPane for pawns to move on
        GridPane pawns = new GridPane();
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

        // Creates a gridPane with spacing for vertical walls
        GridPane vWallGrid = new GridPane();
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
        GridPane hWallGrid = new GridPane();
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
        root.getChildren().add(pawns);


        // Creates a scene with a default size and sets the primaryStage(all panes so far) on it.
        Scene scene = new Scene(root, 600, 600, Color.BLACK);
        primaryStage.setScene(scene);

        // Display primaryStage
        primaryStage.show();

        
        // Handles click events for the game
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                // int row = 0;
                // int column = 4;

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

}
