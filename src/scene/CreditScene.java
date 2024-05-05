package scene;

import component.BBFont;
import component.ReturnButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CreditScene extends StackPane {

    private final VBox container;

    private final BBFont title;

    private final VBox titleContainer;

    private final StackPane topContainer;

    private final VBox textContainer;


    public CreditScene() {
        // StackPane(this) setup
        setPrefSize(800, 450);
        setBackground(new Background(new BackgroundFill(Color.rgb(165,69,155), null, null)));
        // title & titleContainer setup
        title = new BBFont("Our Team", 60);
        title.setFill(Color.rgb(255, 255, 255));
        titleContainer = new VBox(title);
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
        textContainer.setSpacing(2);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setPadding(new Insets(25, 0, 0, 0));
        textContainer.getChildren().add(new BBFont("Mafiahatyai", 50));
    }

}
