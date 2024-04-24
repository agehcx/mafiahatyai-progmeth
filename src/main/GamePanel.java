package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GamePanel extends Pane {
    private static GamePanel instance;
    final int originalTitleSize = 10;
    final int scale = 3;

    final int titleSize = originalTitleSize * scale;
    final int maxScreenCol = 36;
    final int maxScreenRow = 20;
    final int screenWidth = titleSize * maxScreenCol;
    final int screenHeight = titleSize * maxScreenRow;

    int playerX = 0;
    int playerY = 0;
    int playerSpeed = 5;
    long keyPressedTime = 0;

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
            } else if (code == KeyCode.ESCAPE) {
                System.out.println("PAUSE ");
            }
            keyPressedTime = System.nanoTime();
        });

        this.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            boolean hasUpdated = false;
            if (code == KeyCode.UP || code == KeyCode.W) {
//                System.out.println("UP");
                upPressed = false;
                hasUpdated = true;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
//                System.out.println("DOWN");
                downPressed = false;
                hasUpdated = true;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
//                System.out.println("LEFT");
                leftPressed = false;
                hasUpdated = true;
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
//                System.out.println("RIGHT");
                rightPressed = false;
                hasUpdated = true;
            }

            if (hasUpdated) {
                System.out.println("POSITION : [" + playerX + ',' + playerY + ']');
            }

            keyPressedTime = 0;
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
        // Border collide rule added
        long elapsedTime = System.nanoTime() - keyPressedTime;
        double speedMultiplier = 1.0; // When pressed two key at the same time this function is completely exploded
//        double speedMultiplier = 1.0 + (elapsedTime / 1e9);
//         Speed multiplier based on time held

        // TOP
        if (upPressed && playerY - playerSpeed * speedMultiplier >= 0) {
            playerY -= (int) (playerSpeed * speedMultiplier);
        }
        // BOTTOM
        if (downPressed && playerY + titleSize <= screenHeight) {
            playerY += (int) (playerSpeed * speedMultiplier);
        }

        // LEFT
        if (leftPressed && playerX - playerSpeed * speedMultiplier >= 0) {
            playerX -= (int) (playerSpeed * speedMultiplier);
        }

        // RIGHT
        if (rightPressed && playerX + titleSize + playerSpeed * speedMultiplier <= screenWidth) {
            playerX += (int) (playerSpeed * speedMultiplier);
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


// 29.625
// Height 1185 -> 40
// Width 16/9 = x/40
// x = 71.111
// Width = 71.111*29.625 = 2106.6663375

// 35.555 * 20
// 1/2 res => 1053.333 * 592.5