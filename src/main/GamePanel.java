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

    private int playerX = 0;
    private int playerY = 0;
    private int playerSpeed = 5;
    private long keyPressedTime = 0;

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
                setUpPressed(true);
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                setDownPressed(true);
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                setLeftPressed(true);
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                setRightPressed(true);
            } else if (code == KeyCode.ESCAPE) {
                System.out.println("PAUSE ");
            }
            setKeyPressedTime(System.nanoTime());
        });

        this.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            boolean hasUpdated = false;
            if (code == KeyCode.UP || code == KeyCode.W) {
                setUpPressed(false);
                hasUpdated = true;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                setDownPressed(false);
                hasUpdated = true;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                setLeftPressed(false);
                hasUpdated = true;
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                setRightPressed(false);
                hasUpdated = true;
            }

            if (hasUpdated) {
                System.out.println("POSITION : [" + getPlayerX() + ',' + getPlayerY() + ']');
            }

            setKeyPressedTime(0);
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
        long elapsedTime = System.nanoTime() - getKeyPressedTime();
        double speedMultiplier = 1.0; // When pressed two key at the same time this function is completely exploded
//        double speedMultiplier = 1.0 + (elapsedTime / 1e9);
//         Speed multiplier based on time held

        // TOP
        if (isUpPressed() && getPlayerY() - getPlayerSpeed() * speedMultiplier >= 0) {
            setPlayerY(getPlayerY() - (int) (getPlayerSpeed() * speedMultiplier));
        }
        // BOTTOM
        if (isDownPressed() && getPlayerY() + titleSize <= screenHeight) {
            setPlayerY(getPlayerY() + (int) (getPlayerSpeed() * speedMultiplier));
        }

        // LEFT
        if (isLeftPressed() && getPlayerX() - getPlayerSpeed() * speedMultiplier >= 0) {
            setPlayerX(getPlayerX() - (int) (getPlayerSpeed() * speedMultiplier));
        }

        // RIGHT
        if (isRightPressed() && getPlayerX() + titleSize + getPlayerSpeed() * speedMultiplier <= screenWidth) {
            setPlayerX(getPlayerX() + (int) (getPlayerSpeed() * speedMultiplier));
        }
    }

    private void repaint() {
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(getPlayerX(), getPlayerY(), titleSize, titleSize);
        getChildren().setAll(canvas);
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            return new GamePanel();
        }
        return instance;
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

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public long getKeyPressedTime() {
        return keyPressedTime;
    }

    public void setKeyPressedTime(long keyPressedTime) {
        this.keyPressedTime = keyPressedTime;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
}
