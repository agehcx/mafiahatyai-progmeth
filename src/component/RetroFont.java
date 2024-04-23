package component;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RetroFont extends Text {
    public RetroFont(String text, Integer fontSize, Paint fontColor) {
        super(text);
        Font font = Font.loadFont("file:res/font/RetroGaming.ttf", fontSize);
        setFont(font);
        setFill(fontColor);
        setFill(Color.BLACK);
    }

    public RetroFont(String text, Integer fontSize) {
        super(text);
        Font font = Font.loadFont("file:res/font/RetroGaming.ttf", fontSize);
        setFont(font);
        setFill(Color.BLACK);
    }

    public RetroFont(String text) {
        super(text);
        Font font = Font.loadFont("file:res/font/RetroGaming.ttf", 16);
        setFont(font);
        setFill(Color.BLACK);
    }
}