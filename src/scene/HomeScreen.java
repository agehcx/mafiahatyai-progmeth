package scene;


import component.GameButton;
import component.TextFont;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.GameInstance;
import main.GamePanel;

public class HomeScreen {

    private final Stage stage;
    private static Scene scene;
    private static StackPane root;
    private VBox buttonContainer;
    private TextFont title;
    private GameButton playButton;
    private GameButton tutorialButton;
    private GameButton creditButton;
    private GameButton quitButton;
    // Edit full screen
    private final boolean fullScreen = false;

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

        title = new TextFont("Hatyai Tycoon", 90);

        title.setFill(Color.rgb(127,35,255));

        playButton = new GameButton(330, 80, 40, Color.rgb(82, 210, 145));
        playButton.addText("Play", 20, Color.WHITE);

        tutorialButton = new GameButton(330, 80, 40, Color.rgb(82, 210, 145));
        tutorialButton.addText("Tutorial", 20, Color.WHITE);

        creditButton = new GameButton(330, 80, 40, Color.rgb(82, 210, 145));
        creditButton.addText("Credits", 20, Color.WHITE);

        quitButton = new GameButton(330, 80, 40, Color.rgb(82, 210, 145));
        quitButton.addText("Quit Game", 20, Color.WHITE);

        eventSetup(gameInstance);

    }


    private void eventSetup(GameInstance gameInstance) {
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                // Handle play button click event
                root.getChildren().add(new GamePanel());
            }
        });

        tutorialButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                try {
//                    Desktop.getDesktop().browse(new URI("https://www.youtube.com"));
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        creditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                // To handle credit button click event
            }
        });

        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                stage.close();
                // Close the stage when quit button is clicked
            }
        });

        buttonContainer.getChildren().addAll(title, playButton, tutorialButton, creditButton, quitButton);

    }


    private void stageSetup() {

        root = new StackPane();

        root.setPrefSize(1200, 675);

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
