package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a StackPane as the root node
        StackPane root = new StackPane();

        // Set the background color of the root node to black
        root.setStyle("-fx-background-color: black;");

        // Create a Scene with the root node
//        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        GamePanel gamePanel= new GamePanel();
        Pane pane = new Pane();

        primaryStage.setResizable(false);

        primaryStage.centerOnScreen();

        Scene scene = new Scene(pane, GamePanel.getInstance().getHeight(), GamePanel.getInstance().getWidth(),Color.BLACK);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);

        // Set the title of the window
        primaryStage.setTitle("Black Screen");

        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
