package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mafia Hatyai Progmeth");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
