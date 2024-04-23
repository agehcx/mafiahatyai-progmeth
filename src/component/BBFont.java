package component;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BBFont extends Text {
    public BBFont(String text, Integer fontSize, Paint fontColor) {
        super(text);
        Font font = Font.loadFont("file:res/font/04B_30__.TTF", fontSize);
        setFont(font);
        setFill(fontColor);
        setFill(Color.BLACK);
    }

    public BBFont(String text, Integer fontSize) {
        super(text);
        Font font = Font.loadFont("file:res/font/04B_30__.TTF", fontSize);
        setFont(font);
        setFill(Color.BLACK);
    }

    public BBFont(String text) {
        super(text);
        Font font = Font.loadFont("file:res/font/04B_30__.TTF", 16);
        setFont(font);
        setFill(Color.BLACK);
    }
}