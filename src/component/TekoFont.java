package component;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TekoFont extends Text {
    public TekoFont(String text, Integer fontSize, Paint fontColor) {
        super(text);
        Font font = Font.loadFont("file:res/font/Teko-VariableFont_wght.ttf", fontSize);
        setFont(font);
        setFill(fontColor);
        setFill(Color.BLACK);
    }

    public TekoFont(String text, Integer fontSize) {
        super(text);
        Font font = Font.loadFont("file:res/font/Teko-VariableFont_wght.ttf", fontSize);
        setFont(font);
        setFill(Color.BLACK);
    }

    public TekoFont(String text) {
        super(text);
        Font font = Font.loadFont("file:res/font/Teko-VariableFont_wght.ttf", 16);
        setFont(font);
        setFill(Color.BLACK);
    }
}