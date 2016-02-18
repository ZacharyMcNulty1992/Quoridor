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

public class Main extends Application{ 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        /*Text scenetitle = new Text("Quoridor");
        scenetitle.setId("Quoridor-text");
        grid.add(scenetitle, 0, 0, 2, 1);

        /*GridPane buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setPadding(new Insets(25, 25, 25, 25));*/

        /*Button abtn = new Button("2 Player");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(abtn);
        grid.add(hbBtn, 0, 1);

        Button bbtn = new Button("4 Player");
        HBox bBtn = new HBox(10);
        bBtn.setAlignment(Pos.BOTTOM_RIGHT);
        bBtn.getChildren().add(bbtn);
        grid.add(bBtn, 1, 1);*/

        grid.setGridLinesVisible(false);

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                grid.add(new Rectangle(50, 50, Color.WHITE), i, j);
                grid.add(new Text(i +", " + j), i, j);
            }
        }

        Scene scene = new Scene(grid, 800, 800);
        //Scene btnScene = new Scene(buttonGrid, 300, 300);

        primaryStage.setScene(scene);
        //primaryStage.setScene(btnScene);

        scene.getStylesheets().add
            (Main.class.getResource("Main.css").toExternalForm());

        primaryStage.show();
    }
}