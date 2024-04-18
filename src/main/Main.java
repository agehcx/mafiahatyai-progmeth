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
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");

        // Create a GamePanel instance and add it to the root node
        GamePanel gamePanel = new GamePanel();
        root.getChildren().add(gamePanel);

        // Create a Scene with the root node
        Scene scene = new Scene(root, 800, 600, Color.BLACK);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mafia Hatyai Progmeth");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
