package scene;

import component.BBFont;
import component.ReturnButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CreditScene extends StackPane {

    private final VBox container;

    private final BBFont title;
    private final BBFont title2;

    private final VBox titleContainer;

    private final StackPane topContainer;

    private final VBox textContainer;


    public CreditScene() {
        // StackPane(this) setup
        setPrefSize(800, 450);
//        setBackground(new Background(new BackgroundFill(Color.rgb(50,50,50), null, null)));
        setBackground(new Background(new BackgroundImage(new Image("file:res/img/finalbackground.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1280, 760, false, false, false, false))));
        // title & titleContainer setup
        title = new BBFont("Our Team", 60);
        title.setFill(Color.WHITE); // Set text color to white
        title.setEffect(new DropShadow(10, Color.BLACK)); // Add drop shadow
        title2 = new BBFont("Mafiahatyai", 50);
        title2.setFill(Color.WHITE); // Set text color to white
        title2.setEffect(new DropShadow(10, Color.BLACK)); // Add drop shadow
        titleContainer = new VBox(title, title2);
        titleContainer.setAlignment(Pos.CENTER);
        // topContainer setup
        topContainer = new StackPane();
        topContainer.setAlignment(Pos.CENTER_LEFT);
        topContainer.setPadding(new Insets(15, 30, 0, 30));

        topContainer.getChildren().addAll(titleContainer, new ReturnButton());
        // textContainer setup
        textContainer = new VBox();
        textContainerSetup();
        // container setup
        container = new VBox();
        container.getChildren().addAll(topContainer);
        container.getChildren().add(textContainer);
        getChildren().add(container);
    }


    private void textContainerSetup() {
        textContainer.setSpacing(10);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setPadding(new Insets(50, 20, 15, 5));
//        textContainer.getChildren().add(new BBFont("Mafiahatyai", 50));
//        textContainer.setSpacing(10);
        topContainer.setPadding(new Insets(150, 30, 0, 30));
//        VBox.setMargin(textContainer.getChildren().get(textContainer.getChildren().size() - 1), new Insets(50, 0, 0, 0));
        // Add text with white color and drop shadow
        BBFont text1 = new BBFont("6633289321 Ameen Chebueraheng", 35, Color.WHITE);
        text1.setFill(Color.WHITE);
        text1.setEffect(new DropShadow(10, Color.BLACK));
        BBFont text2 = new BBFont("6633078221 Daniel Samae", 35, Color.WHITE);
        text2.setFill(Color.WHITE);
        text2.setEffect(new DropShadow(10, Color.BLACK));
        BBFont text3 = new BBFont("6633068021 Natkamon Maleehuan", 35, Color.WHITE);
        text3.setFill(Color.WHITE);
        text3.setEffect(new DropShadow(10, Color.BLACK));
        textContainer.getChildren().addAll(text1, text2, text3);
    }
}
