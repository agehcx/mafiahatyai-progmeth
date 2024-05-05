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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
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
    private BBFont titleBorder;
    private BBFont title3;
    // Edit full screen
    private final boolean fullScreen = false;

    private final Color buttonColor = Color.rgb(82, 210, 145);
//    private final Color buttonColor = Color.rgb(162, 43, 43);
//    private final Color buttonColor = Color.rgb(255, 255, 255);

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
        title2.setFill(Color.rgb(162,43,43));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        dropShadow.setColor(Color.rgb(50, 50, 50, 0.7)); // Adjust color and opacity as needed

        // Apply drop shadow effect to the title text
        title2.setEffect(dropShadow);

//        titleBorder = new BBFont("Hatyai Tycoon", 90);
//        titleBorder.setFill(Color.WHITE);
//        titleBorder.setStroke(Color.WHITE);
//        titleBorder.setStrokeWidth(2);
//        titleBorder.setStrokeType(StrokeType.OUTSIDE);

        playButton = new GameButton(330, 80, 40, buttonColor);
        playButton.addText("Play", 30, Color.WHITE);
//        playButton.addText("Play", 35, Color.rgb(162,43,43));

        tutorialButton = new GameButton(330, 80, 40, buttonColor);
        tutorialButton.addText("Tutorial", 30, Color.WHITE);
//        tutorialButton.addText("Tutorial", 35, Color.rgb(162,43,43));

        creditButton = new GameButton(330, 80, 40, buttonColor);
        creditButton.addText("Credits", 30, Color.WHITE);
//        creditButton.addText("Credits", 35, Color.rgb(162,43,43));

        quitButton = new GameButton(330, 80, 40, buttonColor);
        quitButton.addText("Quit Game", 30, Color.WHITE);
//        quitButton.addText("Quit Game", 35, Color.rgb(162,43,43));


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
                    Desktop.getDesktop().browse(new URI("https://github.com/agehcx/mafiahatyai-progmeth"));
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        creditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                root.getChildren().add(new CreditScene());
            }
        });


        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                stage.close();
                // Close the stage when quit button is clicked
            }
        });

        buttonContainer.getChildren().addAll(title2,  playButton, tutorialButton, creditButton, quitButton);

    }


    private void stageSetup() {

        root = new StackPane();

        root.setPrefSize(1280, 760);

//        root.setPrefSize(1053, 593);
//        Fixed resolution size for game collision rule
        root.setBackground(new Background(new BackgroundImage(new Image("file:res/img/finalbackground.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 760, false, false, false, false))));

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