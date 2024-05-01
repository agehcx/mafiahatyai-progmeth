package main;

import ghost.Ghost;
import ghost.SpeedyGhost;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import logic.GhostSpawner;
import object.Bullet;
import object.Direction;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import static logic.GhostSpawner.spawnerSpawnGhost;

public class GamePanel extends Pane {
    @FXML
    private Label pointLabel;

    @FXML
    private Label levelLabel;
    private static GamePanel instance;

    private int screenWidth;
    private int screenHeight;
    private int playerX;
    private int playerY;
    private final int blockSize = 40; // Size of each block
    private final int screenWidthBlocks = 32;
    private final int screenHeightBlocks = 18;
    private char[][] mapPattern = map.level1.getMapPattern();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private static ArrayList<Ghost> ghosts = new ArrayList<>();
    private Direction playerDirection = Direction.RIGHT;
    private static ArrayList<Pair<Integer,Integer>> spawnablePosition = new ArrayList<>();
    Media buzzer = new Media(new File("res/sound/pewpew.mp3").toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(buzzer);
    private boolean hasGameEnded = false;
    private boolean isUpdatingMap = false;
    private int currentPoint = 0;
    private int currentLevel = 1;
    // Image resources
    private Image characterUp = new Image("file:res/character/manUp.png", blockSize*1.2, blockSize*1.2, true, true);
    private Image characterDown = new Image("file:res/character/manDown.png", blockSize*1.2, blockSize*1.2, true, true);
    private Image characterLeft = new Image("file:res/character/manLeft.png", blockSize*1.2, blockSize*1.2, true, true);
    private Image characterRight = new Image("file:res/character/manRight.png", blockSize*1.2, blockSize*1.2, true, true);
    private Image currentCharacterImage = characterRight;
    private Image wall = new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true);
    private Image footPath = new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true);
    final Image whiteDot = new Image("file:res/gif/whitedot.png", blockSize, blockSize, true, true);
    final Image bulletRight = new Image("file:res/gif/bulletRight.gif", blockSize, blockSize, true, true);
    final Image bulletUp = new Image("file:res/gif/bulletUp.gif", blockSize, blockSize, false, true);
    final Image bulletLeft = new Image("file:res/gif/bulletLeft.gif", blockSize, blockSize, false, true);
    final Image bulletDown = new Image("file:res/gif/bulletDown.gif", blockSize, blockSize, false, true);
    final Image redGhost = new Image("file:res/gif/redghost.gif", blockSize, blockSize, true, true);
    private long startTimeNano = 0;

    public GamePanel() {

        instance = this;

        double v = blockSize * (double) screenWidthBlocks;
        screenWidth = (int) v;
        double v1 = blockSize * (double) screenHeightBlocks;
        screenHeight = (int) v1;

        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: black;");
        this.setFocusTraversable(true);

        playerX = blockSize; // Start player at a specific position
        playerY = blockSize; // Start player at a specific position

//        updateSpawnablePosition();

//        pointLabel = new Label();
//        levelLabel = new Label();
//        pointLabel.setText("Point : 0");
//        levelLabel.setText("Level : 1");

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
//                pewPewSound.play();
                mediaPlayer.setVolume(0.025);
                mediaPlayer.play();

            }
        });

        // Update empty position for future usage
        updateSpawnablePosition();

        startTimeNano = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if(hasGameEnded) return;

                updateBullets();
                updateBulletGhostCollisions();
                repaint();

                long elapsedTimeNano = System.nanoTime() - startTimeNano;
                double elapsedTimeSeconds = elapsedTimeNano / 1_000_000_000.0;

                if (elapsedTimeSeconds >= 5 && ghosts.size() < 5 && !isUpdatingMap) {
//                    System.out.println("Elapsed time: " + elapsedTimeSeconds + " seconds");
                    spawnGhost();
                }
            }
        }.start();
    }

    private void spawnGhost() {

        if (isUpdatingMap) return;

        updateSpawnablePosition();

        setGhosts(spawnerSpawnGhost(getSpawnablePosition(), getGhosts()));
        System.out.println("Ghost spawned at " + getGhosts().get(getGhosts().size() - 1).getX() + "," + getGhosts().get(getGhosts().size() - 1).getY());
    }

    // Update current spawn-able positions
    private void updateSpawnablePosition() {

        spawnablePosition.clear();

        ArrayList<Pair<Integer,Integer>> tmpPos = new ArrayList<>();

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 32; j++) {
                if (mapPattern[i][j] == 'O' && i * blockSize != playerX && j * blockSize != playerY) {
                    tmpPos.add(new Pair<>(i, j));
                }
            }
        }

        setSpawnablePosition(tmpPos);
    }

    // to-be moved to logic
    private void updateMap(int level) {

        char[][] loadedMap = mapPattern;
        ArrayList<Pair<Integer,Integer>> loadedPosition = new ArrayList<>();

        if (level >= 5) {
            return;
        }

        isUpdatingMap = true;

//        setGhosts(new ArrayList<>());

        ghosts.clear();



        switch (level) {
            case 1 -> {
                loadedMap = map.level1.getMapPattern();
                setWall(new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true));
                setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
            }
            case 2 -> {
                loadedMap = map.level2.getMapPattern();
                setWall(new Image("file:res/gif/wood.jpg", blockSize, blockSize, true, true));
                setFootPath(new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true));
            }
            case 3 -> {
                loadedMap = map.level3.getMapPattern();
                setWall(new Image("file:res/gif/water.jpg", blockSize, blockSize, true, true));
                setFootPath(new Image("file:res/gif/wood.jpg", blockSize, blockSize, true, true));
            }
            case 4 -> {
                loadedMap = map.level4.getMapPattern();
                setWall(new Image("file:res/gif/fire.jpg", blockSize, blockSize, true, true));
                setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
            }
            case 5 -> {
                loadedMap = map.level5.getMapPattern();
                setWall(new Image("file:res/gif/water.jpg", blockSize, blockSize, true, true));
                setFootPath(new Image("file:res/grass/rock.jpg", blockSize, blockSize, true, true));
            }
        };

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 32; j++) {
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

        mapPattern = loadedMap;
        updateSpawnablePosition();
//        repaint();
        System.out.println("Upadting map " + getSpawnablePosition());
        setCurrentLevel(getCurrentLevel() + 1);
//        levelLabel.setText(String.format("Level: %d", getCurrentLevel()));
        System.out.println("Loaded level " + (getCurrentLevel()));
        isUpdatingMap = false;

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
//            System.out.println("POSITION: [" + getPlayerY() / blockSize + ", " + getPlayerX() / blockSize + "]");
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
//                    gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
//                    gc.drawImage(bullet, j * blockSize, i * blockSize);
                    gc.drawImage(wall, j * blockSize, i * blockSize);
                } else if (mapPattern[i][j] == 'O') {
                    gc.setFill(Color.LIGHTGRAY);
                    gc.drawImage(footPath, j * blockSize, i * blockSize);
                    //gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
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

        gc.drawImage(currentCharacterImage, getPlayerX(), getPlayerY());
        getChildren().setAll(canvas);
    }

    private void updateDirection(int dx, int dy) {
        if (dx > 0) { // Moving right
            playerDirection = Direction.RIGHT;
            currentCharacterImage = characterRight;
        } else if (dx < 0) { // Moving left
            playerDirection = Direction.LEFT;
            currentCharacterImage = characterLeft;
        } else if (dy > 0) { // Moving down
            playerDirection = Direction.DOWN;
            currentCharacterImage = characterDown;
        } else if (dy < 0) { // Moving up
            playerDirection = Direction.UP;
            currentCharacterImage = characterUp;
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

    // to-be moved to logic
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

    private void updateBulletGhostCollisions() {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Ghost> ghostIterator = ghosts.iterator();
            while (ghostIterator.hasNext()) {
                Ghost ghost = ghostIterator.next();
                // Check if the coordinates of the bullet intersect with the coordinates of the ghost
//                System.out.println("BL " + bullet.getX() + ',' + bullet.getY());
//                System.out.println("GH " + ghost.getX() * blockSize + ',' + ghost.getY() * blockSize);
                if (    bullet.getY() >= ghost.getX() * blockSize - blockSize * 0.25 &&
                        bullet.getY() <= ghost.getX() * blockSize + blockSize * 1.25 &&
                        bullet.getX() >= ghost.getY() * blockSize - blockSize * 0.25 &&
                        bullet.getX() <= ghost.getY() * blockSize + blockSize * 1.25    ) {
                    // Remove the bullet and ghost upon collision
                    bulletIterator.remove();
                    ghostIterator.remove();

                    if (ghost instanceof Ghost) {
                        setCurrentPoint(getCurrentPoint() + 10);
                    } else if (ghost instanceof SpeedyGhost) {
                        setCurrentPoint(getCurrentPoint() + 20);
                    }

//                    this.pointLabel.setText(String.format("Point : %d", this.getCurrentPoint()));

                    System.out.println("Current Point : " + getCurrentPoint() );
                    break; // Exit the inner loop since the bullet can only hit one ghost
                }
            }
        }

        for (Ghost ghost : ghosts) {
            if (playerX == ghost.getY() * blockSize && playerY == ghost.getX() * blockSize) {
                // Player collides with ghost, set current score to 0
                setCurrentPoint(0);
                System.out.println("Dead...");

                playerX = 0;
                playerY = 0;

                ghosts.clear();

                hasGameEnded = true;
                // You might want to add additional game over logic here
                break;
            }
        }
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

    public ArrayList<Pair<Integer, Integer>> getSpawnablePosition() {
        return spawnablePosition;
    }

    public void setSpawnablePosition(ArrayList<Pair<Integer, Integer>> spawnablePosition) {
        GamePanel.spawnablePosition = spawnablePosition;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public void setGhosts(ArrayList<Ghost> ghosts) {
        GamePanel.ghosts = ghosts;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    public static GamePanel getInstance() {
        return instance;
    }

    public Image getWall() {
        return wall;
    }

    public void setWall(Image wall) {
        this.wall = wall;
    }

    public Image getFootPath() {
        return footPath;
    }

    public void setFootPath(Image footPath) {
        this.footPath = footPath;
    }

    public Image getCharacterUp() {
        return characterUp;
    }

    public void setCharacterUp(Image characterUp) {
        this.characterUp = characterUp;
    }

    public Image getCharacterDown() {
        return characterDown;
    }

    public void setCharacterDown(Image characterDown) {
        this.characterDown = characterDown;
    }

    public Image getCharacterLeft() {
        return characterLeft;
    }

    public void setCharacterLeft(Image characterLeft) {
        this.characterLeft = characterLeft;
    }

    public Image getCharacterRight() {
        return characterRight;
    }

    public void setCharacterRight(Image characterRight) {
        this.characterRight = characterRight;
    }
}