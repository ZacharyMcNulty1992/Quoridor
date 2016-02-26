package main.java.client.gui;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;

public class Main extends Application{

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        Pane root = new Pane();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setGridLinesVisible(false);

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                Rectangle r = new Rectangle(50, 50, Color.GREEN);
                gridPane.add(r, i, j);
                r.setMouseTransparent(false);
                GridPane.setRowIndex(r, i);
                GridPane.setColumnIndex(r, j);
            }
        }

        root.getChildren().add(gridPane);

        Scene scene = new Scene(root, 600, 600, Color.WHITE);
        primaryStage.setScene(scene);

        primaryStage.show();

        gridPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                for( Node node: gridPane.getChildren()) {

                    if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                        System.out.println(GridPane.getRowIndex(node) + "," + GridPane.getColumnIndex(node));
                    }
                }
            }
        });

    }
}
   /* @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        theScene.getStylesheets().add
            (Quoridor.class.getResource("Quoridor.css").toExternalForm());

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        root.getChildren().add(grid);

        GridPane vGrid = new GridPane();
        vGrid.setAlignment(Pos.CENTER);
        vGrid.setHgap(50);
        vGrid.setVgap(10);
        vGrid.setPadding(new Insets(25, 25, 25, 25));

        root.getChildren().add(vGrid);

        GridPane hGrid = new GridPane();
        hGrid.setAlignment(Pos.CENTER);
        hGrid.setHgap(10);
        hGrid.setVgap(50);
        hGrid.setPadding(new Insets(25, 25, 25, 25));

        root.getChildren().add(hGrid);

        grid.setGridLinesVisible(false);

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                grid.add(new Rectangle(50, 50, Color.GREEN), i, j);
                grid.add(new Text(i +", " + j), i, j);
            }
        }

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 9; j++) {
                vGrid.add(new Rectangle(10, 50, Color.RED), i+ 1, j);
            }
        } 

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 8; j++) {
                hGrid.add(new Rectangle(50, 10, Color.BLUE), i, j + 1);
            }
        }

        primaryStage.show();
    }*/