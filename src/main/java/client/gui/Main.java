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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.Group;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.awt.Point;

public class Main extends Application {

    // Holds number of players in the game
    static int playerCount;

    // current player to handle turns
    public static int currentPlayer = 1;

    // BorderPane to have access the sides and not just the center. 
    static BorderPane mainPane;

    // root pane all other panes will be added to
    static Pane root; // Making this a group will allow it to be dynamically centered

    // Gridpane for vertical walls.
    static GridPane vWallGrid;

    // Gridpane for horizontal walls.
    static GridPane hWallGrid;

    // Gridpane for pawn locations.
    static GridPane pawns;

    static GridPane board;

    // 4 cirlces to represnt th pawns
    static Circle pawn1 = new Circle(25, Color.WHITE);
    static Circle pawn2 = new Circle(25, Color.BLUE);
    static Circle pawn3 = new Circle(25, Color.ORANGE);
    static Circle pawn4 = new Circle(25, Color.PURPLE);

    // 4 Player objects to hold the players being passed in
    static Player p1;
    static Player p2;
    static Player p3;
    static Player p4;

    //public static int player2walls;

    static HashMap<Point, Character> tempMap;

    public static Main gui = null;
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Main() {
        tempMap = new HashMap<>();
        guiStartUpTest(this);
    }

    // Sets the playerCount for the game, used in intial board setup.
    public static void setPlayerCount(int pc) {
        playerCount = pc;
    }

    public static Main waitForStartUp() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gui;
    }

    // Returns the correct player object corresponding to the player number
    public static Player currentPlayer() {
        if (currentPlayer == 1) {
            return p1;
        } else if (currentPlayer == 2) {
            return p2;
        } else if (currentPlayer == 3) {
            return p3;
        } else {
            return p4;
        }
    }

    // sets the current player number
    public static void setCurrentPlayer(int pn) {
        currentPlayer = pn;
    }

    public static void guiStartUpTest(Main g) {
        gui = g;
        latch.countDown();
    }

    // Receives player moves from the client.
    public static void Atari(Point dest) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                movePawns(currentPlayer, dest);
                System.out.println("Player " + currentPlayer + " Dest = " + dest);
            }
        });

    }

    // Receives walls from the client.
    public static void AtariWall(HashMap<Point, Character> mappy) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                drawWalls(mappy);
                mainPane.setLeft(setLeftRegion());
            }
        });
        // if (currentPlayer == 2)
        //     player2walls --;
    }

    // Calls removePlayer when gote is recieved.
    public static void gote(int pn){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                removePlayer(pn);
            }
        });
    }

    // Starts the GUI and draws the inital board. Also starts a click event listener.
    @Override
    public void start(final Stage primaryStage) {
        mainPane = new BorderPane();
        mainPane.setId("main");
        root = new Pane();
        mainPane.setCenter(root);
        mainPane.setTop(setTitleRegion());
        //mainPane.setLeft(setLeftRegion());
        //mainPane.setAlignment(root, Pos.CENTER);
        mainPane.setLeft(setLeftRegion());
        //mainPane.setAlignment(root, Pos.CENTER);


        drawBoard();
        handlePawns();

        pawns.add(pawn1, 4, 0);
        pawns.add(pawn2, 4, 8);
        if (playerCount > 2) {
            pawns.add(pawn3, 0, 4);
            pawns.add(pawn4, 8, 4);
        }

        // Creates a scene with a default size and sets the primaryStage(all panes so far) on it.
        Scene scene = new Scene(mainPane, 800, 800, Color.GREY);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("Main.css");
        // Display primaryStage
        primaryStage.show();

        // Handles click events for the game
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                // returns coordinate on the board when a square is clicked
                for (Node node : pawns.getChildren()) {
                    //node.setOpacity(0.0);

                    if (node.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
                        System.out.println(GridPane.getColumnIndex(node) + "," + GridPane.getRowIndex(node));
                        //node.setOpacity(1.0);
                    }
                }

                // returns coordinate on the board when a vertical wall is clicked
                for (Node node : vWallGrid.getChildren()) {

                    if (node.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
                        System.out.println("v " + GridPane.getColumnIndex(node) + "," + GridPane.getRowIndex(node));
                    }
                }

                // returns coordinate on the board when a horizontal wall is clicked
                for (Node node : hWallGrid.getChildren()) {

                    if (node.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
                        System.out.println("h " + GridPane.getColumnIndex(node) + "," + GridPane.getRowIndex(node));
                    }
                }
            }
        });
    }

    // Draws the inital board, spaces and possible wall locations,
    private void drawBoard() {
        // Creates a gridPane for the board squares
        board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(10);
        board.setVgap(10);
        board.setPadding(new Insets(25, 25, 25, 25));
        board.setGridLinesVisible(false);

        // Loop to create the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Rectangle r = new Rectangle(50, 50, Color.GREEN);
                board.add(r, i, j);
            }
        }

        // Creates a gridPane with spacing for vertical walls
        vWallGrid = new GridPane();
        vWallGrid.setAlignment(Pos.CENTER);
        vWallGrid.setHgap(50);
        vWallGrid.setVgap(10);
        vWallGrid.setPadding(new Insets(25, 25, 25, 75));

        // Loop to create locations for vertical walls 
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                vWallGrid.add(new Rectangle(10, 50, Color.TRANSPARENT), i, j);
            }
        }

        // Creates a gridPane with spacing for horizontal walls
        hWallGrid = new GridPane();
        hWallGrid.setAlignment(Pos.CENTER);
        hWallGrid.setHgap(10);
        hWallGrid.setVgap(50);
        hWallGrid.setPadding(new Insets(75, 25, 25, 25));

        // Loop to create locations for horizontal walls
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                hWallGrid.add(new Rectangle(50, 10, Color.TRANSPARENT), i, j);
            }
        }

        // Adds the gridpanes to the root pane which will be displayed
        root.getChildren().add(board);
        root.getChildren().add(vWallGrid);
        root.getChildren().add(hWallGrid);
        //root.getChildren().add(bp);
    }

    // sets up locations for possible pawn movement.
    private void handlePawns() {
        // Creates a GridPane for pawns to move on
        pawns = new GridPane();
        pawns.setAlignment(Pos.CENTER);
        pawns.setHgap(10);
        pawns.setVgap(10);
        pawns.setPadding(new Insets(25, 25, 25, 25));
        pawns.setGridLinesVisible(false);

        // Creates a grid of invisible circles for pawns to populate
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Circle c = new Circle(25, Color.WHITE);
                c.setOpacity(0.0);
                pawns.add(c, i, j);
            }
        }
        root.getChildren().add(pawns);
    }

    // Sets the Player objects to the correct players received from the client.
    public static void setPlayers(ArrayList<Player> playerList) {
        p1 = playerList.get(0);
        p2 = playerList.get(1);
        if (playerList.size() == 4) {
            p3 = playerList.get(2);
            p4 = playerList.get(3);
        }
    }

    // Checks player number (pn) and moves indicated pawn.
    public static void movePawns(int pn, Point dest) {
        switch (pn) {
            case 1:
                pawns.setConstraints(pawn1, dest.x, dest.y);
                break;
            case 2:
                pawns.setConstraints(pawn2, dest.x, dest.y);
                break;
            case 3:
                pawns.setConstraints(pawn3, dest.x, dest.y);
                break;
            default:
                pawns.setConstraints(pawn4, dest.x, dest.y);
                break;

        }
    }

    // Removes pawns form the guis as the players are kicked.
    public static void removePlayer(int pn) {
        switch(pn) {
            case 1:
                pawns.getChildren().remove(pawn1);
                break;
            case 2:
                pawns.getChildren().remove(pawn2);
                break;
            case 3:
                pawns.getChildren().remove(pawn3);
                break;
            case 4:
                pawns.getChildren().remove(pawn4);
                break;
            default:
                break;
        }
    }

    // Recieves walls from the client, updates the GUI to show them.
    public static void drawWalls(HashMap<Point, Character> wallsMap) {
        for (Point key : wallsMap.keySet()) {
            if(!tempMap.containsKey(key)){
                tempMap.put(key, wallsMap.get(key));
                if (tempMap.get(key) == 'v') {
                    System.out.println("Player " + currentPlayer + " vWall = " + key);
                     vWallGrid.add(new Rectangle(10, 50, Color.BLACK), key.x, key.y);
                     vWallGrid.add(new Rectangle(10, 50, Color.BLACK), key.x, key.y + 1);
                } else {
                    System.out.println("Player " + currentPlayer + " hWall = " + key);
                     hWallGrid.add(new Rectangle(50, 10, Color.BLACK), key.x, key.y);
                     hWallGrid.add(new Rectangle(50, 10, Color.BLACK), key.x + 1, key.y);
                }
            }
        }
    }

    private Region setTitleRegion() {
        Text text = new Text("QUORIDOR");
        text.setStyle("-fx-background-color: gray");
        text.setId("title_text");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        
        StackPane stackPane = new StackPane();
        Insets inset = new Insets(20, 20, 20, 20);
        stackPane.setPadding(inset);
        
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(text);
        // set the max width 
        stackPane.setMinHeight(135);
        //stackPane.getStylesheets().addAll(this.getClass().getResource("Layout.css").toExternalForm());
        stackPane.setId("title");
        return stackPane;
    }

  private static Region setLeftRegion() {
        //player2walls = p2.wallCount;

        VBox vb = new VBox();
        vb.setPadding(new Insets(100, 0, 0, 0));
        vb.setSpacing(20);

        Label lbl = new Label("Walls");
        lbl.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        vb.getChildren().add(lbl);

        Label p1walls = new Label("Player 1: " + p1.wallCount);
        vb.getChildren().add(p1walls);

        Label p2walls = new Label("Player 2: " + p2.wallCount);
        vb.getChildren().add(p2walls);

        if (playerCount > 2){
            Label p3walls = new Label("Player 3: " + p3.wallCount);
            vb.getChildren().add(p3walls);

            Label p4walls = new Label("Player 4: " + p4.wallCount);
            vb.getChildren().add(p4walls);
        }
        return vb;
    }
}
