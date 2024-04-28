package main;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import map.level1;

public class GamePanel extends Pane {
    private int screenWidth;
    private int screenHeight;
    private int playerX;
    private int playerY;
    private final int blockSize = 25; // Size of each block
    private final int screenWidthBlocks = 48;
    private final int screenHeightBlocks = 27;
    private char[][] mapPattern = map.level1.getMapPattern();
    private String side="pacmanDown";

    public GamePanel() {
        screenWidth = blockSize * screenWidthBlocks;
        screenHeight = blockSize * screenHeightBlocks;

        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: black;");
        this.setFocusTraversable(true);

        playerX = blockSize; // Start player at a specific position
        playerY = blockSize; // Start player at a specific position

        this.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP || code == KeyCode.W) {
                setSide("pacmanUp");
                movePlayer(0, -blockSize); // Move up
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                setSide("pacmanDown");
                movePlayer(0, blockSize); // Move down
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                setSide("pacmanLeft");
                movePlayer(-blockSize, 0); // Move left
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                setSide("pacmanRight");
                movePlayer(blockSize, 0); // Move right
            } else if (code == KeyCode.ESCAPE) {
                System.out.println("Skipping Level !!");

                this.mapPattern = map.level2.getMapPattern();
            }
        });

        // No need for key released event handling for this version

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // No need for update method in this version
                repaint();
            }
        }.start();
    }

    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        // Check if new position is within bounds and is not a wall
        if (newX >= 0 && newX + blockSize <= screenWidth && newY >= 0 && newY + blockSize <= screenHeight
                && mapPattern[newY / blockSize][newX / blockSize] != 'X') {
            setPlayerX(newX);
            setPlayerY(newY);
            System.out.println("POSITION: [" + getPlayerX() + ", " + getPlayerY() + "]");
        }
    }

    private void repaint() {
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, screenWidth, screenHeight);

        // Draw map
        for (int i = 0; i < screenHeightBlocks; i++) {
            for (int j = 0; j < screenWidthBlocks; j++) {
                if (mapPattern[i][j] == 'X') {
                    gc.setFill(Color.DARKGREY);
                    gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                } else if (mapPattern[i][j] == 'O') {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                }
            }
        }

        // Draw player
        Image player = new Image("file:res/gif/"+getSide()+".gif", blockSize, blockSize, true, true);
        gc.drawImage(player, getPlayerX(), getPlayerY());

        getChildren().setAll(canvas);
    }

    // Getter and setter methods
    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String position) {
        this.side = position;
    }
}
