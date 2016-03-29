package client.gui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;

public class Main extends Application{

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        // Root pane to add other panes to
        Pane root = new Pane();

        // Creates a gridPane for the board squares
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(10);
        board.setVgap(10);
        board.setPadding(new Insets(25, 25, 25, 25));
        board.setGridLinesVisible(false);

        // Creates a gridPane with spacing for vertical walls
        GridPane vWallGrid = new GridPane();
        vWallGrid.setAlignment(Pos.CENTER);
        vWallGrid.setHgap(50);
        vWallGrid.setVgap(10);
        vWallGrid.setPadding(new Insets(25, 25, 25, 25));

        // Creates a gridPane with spacing for horizontal walls
        GridPane hWallGrid = new GridPane();
        hWallGrid.setAlignment(Pos.CENTER);
        hWallGrid.setHgap(10);
        hWallGrid.setVgap(50);
        hWallGrid.setPadding(new Insets(25, 25, 25, 25));


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

        // Loop to create locations for vertical walls 
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 9; j++) {
                vWallGrid.add(new Rectangle(10, 50, Color.TRANSPARENT), i+ 1, j);
            }
        } 

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

        // Creates a scene with a default size and sets the primaryStage(all panes so far) on it.
        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        primaryStage.setScene(scene);

        // Display primaryStage
        primaryStage.show();

        // Handles click events for the game
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                // returns coordinate on the board when a square is clicked
                for(Node node: board.getChildren()) {

                    if(node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                        System.out.println(GridPane.getRowIndex(node) + "," + GridPane.getColumnIndex(node));
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
