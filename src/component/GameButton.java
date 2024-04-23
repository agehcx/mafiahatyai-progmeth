package component;

import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameButton extends StackPane {
    private final Rectangle background;


    public GameButton(int width, int height, int cornerRadius, Paint colorPaint) {
        background = new Rectangle(width, height, colorPaint);

        background.setArcHeight(cornerRadius);
        background.setArcWidth(cornerRadius);

        getChildren().add(background);

        setCursor(Cursor.HAND);

        DropShadow dropShadow = new DropShadow();

        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

        setEffect(dropShadow);
    }

    public GameButton() {

        background = new Rectangle(60, 60, Color.WHITE);

        background.setArcHeight(20);
        background.setArcWidth(20);
        getChildren().add(background);
        setCursor(Cursor.HAND);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        setEffect(dropShadow);
    }

    public void addText(String s, int size, Paint paint) {
        TekoFont text = new TekoFont(s, size);
        text.setFill(paint);
        getChildren().add(text);
    }

    public void setColor(Paint paint) {
        background.setFill(paint);
    }
}