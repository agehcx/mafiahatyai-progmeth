package component;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Paint;

public class SupermarketFont extends Text {
    public SupermarketFont(String text, Integer fontSize, Paint fontColor) {
        super(text);
        Font font = Font.loadFont("file:res/font/supermarket.ttf", fontSize);
        setFont(font);
        setFill(fontColor);
        setFill(Color.BLACK);
    }

    public SupermarketFont(String text, Integer fontSize) {
        super(text);
        Font font = Font.loadFont("file:res/font/supermarket.ttf", fontSize);
        setFont(font);
        setFill(Color.BLACK);
    }

    public SupermarketFont(String text) {
        super(text);
        Font font = Font.loadFont("file:res/font/supermarket.ttf", 16);
        setFont(font);
        setFill(Color.BLACK);
    }
}