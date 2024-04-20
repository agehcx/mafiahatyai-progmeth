package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GamePanel extends Pane {
    private static GamePanel instance;
    final int originalTitleSize = 16;
    final int scale = 3;

    final int titleSize = originalTitleSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 16;
    final int screenWidth = titleSize * maxScreenCol;
    final int screenHeight = titleSize * maxScreenRow;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public GamePanel() {

        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: black;");
        this.setFocusTraversable(true);

        this.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP || code == KeyCode.W) {
                upPressed = true;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                downPressed = true;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                leftPressed = true;
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                rightPressed = true;
            }
        });

        this.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP || code == KeyCode.W) {
                System.out.println("UP");
                upPressed = false;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                System.out.println("DOWN");
                downPressed = false;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                System.out.println("LEFT");
                leftPressed = false;
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                System.out.println("RIGHT");
                rightPressed = false;
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                repaint();
            }
        }.start();
    }

    private void update() {
        if (upPressed) {
            playerY -= playerSpeed;
        } else if (downPressed) {
            playerY += playerSpeed;
        } else if (leftPressed) {
            playerX -= playerSpeed;
        } else if (rightPressed) {
            playerX += playerSpeed;
        }
    }

    private void repaint() {
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(playerX, playerY, titleSize, titleSize);
        getChildren().setAll(canvas);
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            return new GamePanel();
        }
        return instance;
    }
}
