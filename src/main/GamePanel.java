package main;

import ghost.Ghost;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import logic.GhostSpawner;
import object.Bullet;
import object.Direction;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends Pane {
    private int screenWidth;
    private int screenHeight;
    private int playerX;
    private int playerY;
    private final int blockSize = 25; // Size of each block
    private final int screenWidthBlocks = 48;
    private final int screenHeightBlocks = 27;
    private char[][] mapPattern = map.level1.getMapPattern();
    private int currentLevel = 1;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private static ArrayList<Ghost> ghosts = new ArrayList<>();
    private Direction playerDirection = Direction.RIGHT;

    private static ArrayList<Pair<Integer,Integer>> spawnablePosition = new ArrayList<>();
    final Image pacmanUp = new Image("file:res/gif/pacmanup.gif", blockSize, blockSize, true, true);
    final Image pacmanDown = new Image("file:res/gif/pacmandown.gif", blockSize, blockSize, true, true);
    final Image pacmanLeft = new Image("file:res/gif/pacmanleft.gif", blockSize, blockSize, true, true);
    final Image pacmanRight = new Image("file:res/gif/pacmanright.gif", blockSize, blockSize, true, true);
    private Image currentPacmanImage = pacmanRight;
    final Image wall = new Image("file:res/gif/wall.png", blockSize, blockSize, true, true);
    final Image whiteDot = new Image("file:res/gif/whitedot.png", blockSize, blockSize, true, true);
    final Image bulletRight = new Image("file:res/gif/bulletRight.gif", blockSize, blockSize, true, true);
    final Image bulletUp = new Image("file:res/gif/bulletUp.gif", blockSize, blockSize, false, true);
    final Image bulletLeft = new Image("file:res/gif/bulletLeft.gif", blockSize, blockSize, false, true);
    final Image bulletDown = new Image("file:res/gif/bulletDown.gif", blockSize, blockSize, false, true);
    final Image redGhost = new Image("file:res/gif/redghost.gif", blockSize, blockSize, true, true);
    private long startTimeNano;

    public GamePanel() {
        screenWidth = blockSize * screenWidthBlocks;
        screenHeight = blockSize * screenHeightBlocks;

        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: black;");
        this.setFocusTraversable(true);

        playerX = blockSize; // Start player at a specific position
        playerY = blockSize; // Start player at a specific position

        updateSpawnablePosition();

        this.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP || code == KeyCode.W) {
                movePlayer(0, -blockSize); // Move up
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                movePlayer(0, blockSize); // Move down
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                movePlayer(-blockSize, 0); // Move left
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                movePlayer(blockSize, 0); // Move right
            } else if (code == KeyCode.ESCAPE) {
                updateMap(getCurrentLevel() + 1);
            } else if (code == KeyCode.SPACE) {
//                System.out.println("Firing bullet");
                shootBullet();
            }
        });

        // Update empty position for future usage
        updateSpawnablePosition();

        startTimeNano = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateBullets();
                repaint();
                long elapsedTimeNano = System.nanoTime() - startTimeNano;
                double elapsedTimeSeconds = elapsedTimeNano / 1_000_000_000.0;

                if (elapsedTimeSeconds >= 15 && ghosts.size() < 3) {
                    System.out.println("Elapsed time: " + elapsedTimeSeconds + " seconds");
                    spawnGhost();
                }
            }
        }.start();
    }

    private void spawnGhost() {
        logic.GhostSpawner.spawnGhost();

        System.out.println("Ghost spawned at" + getGhosts().get(getGhosts().size() - 1).getX() + "," + getGhosts().get(getGhosts().size() - 1).getY());
    }

    // Update current spawn-able positions
    private void updateSpawnablePosition() {

        spawnablePosition.clear();

        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 48; j++) {
                if (mapPattern[i][j] == 'O' && i * blockSize != playerX && j * blockSize != playerY) {
                    spawnablePosition.add(new Pair<>(i, j));
                }
            }
        }
    }

    private void updateMap(int level) {

        char[][] loadedMap = mapPattern;
        ArrayList<Pair<Integer,Integer>> loadedPosition = new ArrayList<>();

        switch (level) {
            case 1 -> loadedMap = map.level1.getMapPattern();
            case 2 -> loadedMap = map.level2.getMapPattern();
            case 3 -> loadedMap = map.level3.getMapPattern();
            case 4 -> loadedMap = map.level4.getMapPattern();
            case 5 -> loadedMap = map.level5.getMapPattern();
            default -> {
                if (level > 5) {
//                    System.out.println("Exceed level limit.");
                    return;
                }
            }
        };

        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 48; j++) {
                if (loadedMap[i][j] == 'O') {
                    loadedPosition.add(new Pair<>(i, j));
                }
            }
        }

        if (!(loadedPosition.stream()
                .anyMatch(pair -> pair.getKey() == getPlayerY() / blockSize && pair.getValue() == getPlayerX() / blockSize))) {

            System.out.println("Not in LIST");

            Random random = new Random();
            int rndPosition = random.nextInt(loadedPosition.size());

            Pair<Integer, Integer> randomPosition = loadedPosition.get(rndPosition);
            int randomX = randomPosition.getValue() * blockSize;
            int randomY = randomPosition.getKey() * blockSize;

            setPlayerX(randomX);
            setPlayerY(randomY);
        }

        this.mapPattern = loadedMap;
        setCurrentLevel(getCurrentLevel() + 1);
        System.out.println("Loaded level " + (getCurrentLevel()));

    }

    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        // Check if new position is within bounds and is not a wall
        if (newX >= 0 && newX + blockSize <= screenWidth && newY >= 0 && newY + blockSize <= screenHeight
                && mapPattern[newY / blockSize][newX / blockSize] != 'X') {
            setPlayerX(newX);
            setPlayerY(newY);
            updateDirection(dx, dy);
            System.out.println("POSITION: [" + getPlayerY() / blockSize + ", " + getPlayerX() / blockSize + "]");
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
                    gc.setFill(Color.GREEN);
                    gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
//                    gc.drawImage(bullet, j * blockSize, i * blockSize);
//                    gc.drawImage(wall, j * blockSize, i * blockSize);
                } else if (mapPattern[i][j] == 'O') {
                    gc.setFill(Color.DARKGOLDENROD);
                    gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                }
            }
        }

        for (Ghost ghost : ghosts) {
            gc.drawImage(redGhost, ghost.getY() * blockSize, ghost.getX() * blockSize);
        }

        for (Bullet bullet : bullets) {
            switch (bullet.getDirection()) {
                case UP -> gc.drawImage(bulletUp, bullet.getX(), bullet.getY());
                case DOWN -> gc.drawImage(bulletDown, bullet.getX(), bullet.getY());
                case LEFT -> gc.drawImage(bulletLeft, bullet.getX(), bullet.getY());
                default -> gc.drawImage(bulletRight, bullet.getX(), bullet.getY());
            };
        }

        gc.drawImage(currentPacmanImage, getPlayerX(), getPlayerY());
        getChildren().setAll(canvas);
    }

    private void updateDirection(int dx, int dy) {
        if (dx > 0) { // Moving right
            playerDirection = Direction.RIGHT;
            currentPacmanImage = pacmanRight;
        } else if (dx < 0) { // Moving left
            playerDirection = Direction.LEFT;
            currentPacmanImage = pacmanLeft;
        } else if (dy > 0) { // Moving down
            playerDirection = Direction.DOWN;
            currentPacmanImage = pacmanDown;
        } else if (dy < 0) { // Moving up
            playerDirection = Direction.UP;
            currentPacmanImage = pacmanUp;
        }
    }

    private void updateBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move(blockSize);
            if (bulletHitsWall(bullet.getX(), bullet.getY())) {
                iterator.remove(); // Safely removes the current bullet from the list
            }
        }
    }

    private boolean bulletHitsWall(int x, int y) {
        return mapPattern[y / blockSize][x / blockSize] == 'X';
    }

    private void shootBullet() {
        int bulletX = playerX;
        int bulletY = playerY;
        switch (getPlayerDirection()) {
            case UP:
                bulletY -= blockSize / 2;
                break;
            case DOWN:
                bulletY += blockSize / 2;
                break;
            case LEFT:
                bulletX -= blockSize / 2;
                break;
            case RIGHT:
                bulletX += blockSize / 2;
                break;
        }
        bullets.add(new Bullet(bulletX, bulletY, getPlayerDirection()));
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

    public int getBlockSize() {
        return blockSize;
    }

    public int getScreenWidthBlocks() {
        return screenWidthBlocks;
    }

    public int getScreenHeightBlocks() {
        return screenHeightBlocks;
    }

    public char[][] getMapPattern() {
        return mapPattern;
    }

    public void setMapPattern(char[][] mapPattern) {
        this.mapPattern = mapPattern;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Direction getPlayerDirection() {
        return playerDirection;
    }

    public void setPlayerDirection(Direction playerDirection) {
        this.playerDirection = playerDirection;
    }

    public static ArrayList<Pair<Integer, Integer>> getSpawnablePosition() {
        return spawnablePosition;
    }

    public void setSpawnablePosition(ArrayList<Pair<Integer, Integer>> spawnablePosition) {
        GamePanel.spawnablePosition = spawnablePosition;
    }

    public static ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public void setGhosts(ArrayList<Ghost> ghosts) {
        GamePanel.ghosts = ghosts;
    }
}