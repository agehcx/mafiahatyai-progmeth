package main;

import javafx.application.Application;
import javafx.stage.Stage;

import scene.HomeScreen;

public class Main extends Application {
//    @Override
    public void start(Stage primaryStage) {
//        StackPane root = new StackPane();
//        root.setStyle("-fx-background-color: black;");
//
//        // Create a GamePanel instance and add it to the root node
//        GamePanel gamePanel = new GamePanel();
//        root.getChildren().add(gamePanel);
//
//        // Create a Scene with the root node
//        Scene scene = new Scene(root, 800, 600, Color.BLACK);

//         Set the scene to the primary stage
//        primaryStage.setScene(scene);
        new HomeScreen(primaryStage);

        primaryStage.setTitle("Hatyai Tycoon");
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
