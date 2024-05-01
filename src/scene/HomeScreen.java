package scene;


import component.BBFont;
import component.GameButton;
import component.SupermarketFont;
import component.TekoFont;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameInstance;
import main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class HomeScreen {

    private final Stage stage;
    private static Scene scene;
    private static StackPane root;
    private VBox buttonContainer;
    private SupermarketFont title;
    private BBFont title2;
    private GameButton playButton;
    private GameButton tutorialButton;
    private GameButton creditButton;
    private GameButton quitButton;
    // Edit full screen
    private final boolean fullScreen = false;

    private final Color buttonColor = Color.rgb(82, 210, 145);

    public HomeScreen(Stage stage) {
        this.stage = stage;
        GameInstance gameInstance = new GameInstance();
        componentSetup(gameInstance);
        stageSetup();
    }

    private void componentSetup(GameInstance gameInstance) {
        buttonContainer = new VBox();
        buttonContainer.setSpacing(15);
        buttonContainer.setAlignment(Pos.CENTER);
        title2 = new BBFont("Hatyai Tycoon", 90);

        title2.setFill(Color.rgb(255,255,255));

        playButton = new GameButton(330, 80, 40, buttonColor);
        playButton.addText("Play", 35, Color.WHITE);

        tutorialButton = new GameButton(330, 80, 40, buttonColor);
        tutorialButton.addText("Tutorial", 35, Color.WHITE);

        creditButton = new GameButton(330, 80, 40, buttonColor);
        creditButton.addText("Credits", 35, Color.WHITE);

        quitButton = new GameButton(330, 80, 40, buttonColor);
        quitButton.addText("Quit Game", 35, Color.WHITE);

        eventSetup(gameInstance);

    }


    private void eventSetup(GameInstance gameInstance) {
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                // Handle play button click event
                FXMLLoader gamePageLoader = new FXMLLoader(getClass().getResource("GamePage.fxml"));
                try {
                    Parent rootGame = gamePageLoader.load();
                    GamePanel controller = gamePageLoader.getController();

                    // Get the current scene
                    Scene currentScene = playButton.getScene();

                    // Replace the current scene's root with the new root
                    currentScene.setRoot(rootGame);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        tutorialButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.github.com"));
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        creditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                // To handle credit button click event
                root.getChildren().clear();
                try {
                    root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/loading_screen.fxml"))));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                stage.close();
                // Close the stage when quit button is clicked
            }
        });

        buttonContainer.getChildren().addAll(title2, playButton, tutorialButton, creditButton, quitButton);

    }


    private void stageSetup() {

        root = new StackPane();

        root.setPrefSize(1280, 765);

//        root.setPrefSize(1053, 593);
//        Fixed resolution size for game collision rule
        root.setBackground(new Background(new BackgroundImage(new Image("file:res/img/background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 765, false, false, false, false))));

        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Hatyai Tycoon");

        stage.setResizable(false);
        stage.setFullScreen(fullScreen);

        root.getChildren().add(buttonContainer);
    }

    public static StackPane getRoot() {
        return root;
    }

    public static Scene getScene() {
        return scene;
    }
}