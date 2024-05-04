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

import logic.*;
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
    private PlayerMovement playerMovement = new PlayerMovement();
    private BulletLogic bulletLogic = new BulletLogic();
    private MapLoader mapLoader = new MapLoader();
    private GhostSpawner ghostSpawner = new GhostSpawner();
    private ImageManager imageManager = new ImageManager();

    private int screenWidth;
    private int screenHeight;
    private int playerX;
    private int playerY;
    private final int blockSize = 40; // Size of each block
    private final int screenWidthBlocks = 32;
    private final int screenHeightBlocks = 18;
    private char[][] mapPattern;
    private ArrayList<Bullet> bullets = new ArrayList<>();
//    private static ArrayList<Ghost> ghosts = new ArrayList<>();
    private Direction playerDirection = Direction.RIGHT;
    private static ArrayList<Pair<Integer,Integer>> spawnablePosition = new ArrayList<>();
    Media buzzer = new Media(new File("res/sound/pewpew.mp3").toURI().toString());
    MediaPlayer gunshotSound = new MediaPlayer(buzzer);
    private boolean hasGameEnded = false;
    private boolean isUpdatingMap = false;
    private boolean hasKey = false;
    private int currentPoint = 0;
    private int currentLevel = 1;
    // Image resources
    private final int[] extraGhost = {0, 1, 3, 5, 8};
    private final int[] levelSpawntime = {0, 0, 0, 1, 2};
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

        playerX = blockSize * 2; // Start player at a specific position
        playerY = blockSize * 2; // Start player at a specific position

        mapPattern = map.level1.getMapPattern();

        KeyHandler keyHandler = new KeyHandler(playerMovement, bulletLogic,
                () -> MapLoader.updateMap(GamePanel.getInstance().getCurrentLevel() + 1),
                GhostSpawner::updateSpawnablePosition, GamePanel.getInstance().getBlockSize(), GamePanel.getInstance().getCurrentLevel());
        this.setOnKeyPressed(keyHandler);

        GhostSpawner.updateSpawnablePosition();

        startTimeNano = System.nanoTime();

        new AnimationTimer() {
            private long lastGhostSpawnTime = startTimeNano;
            private long lastGhostMoveTime = startTimeNano;

            @Override
            public void handle(long now) {
                if (hasGameEnded) {
                    // Handle game end logic
                } else {
                    bulletLogic.updateBullets();
                    bulletLogic.updateBulletGhostCollisions();
                    playerMovement.repaint();

                    long elapsedTimeNano = System.nanoTime() - lastGhostSpawnTime;
                    double elapsedTimeSeconds = elapsedTimeNano / 1_000_000_000.0;

                    if (elapsedTimeSeconds >= (3 - levelSpawntime[currentLevel - 1])  && GhostSpawner.getGhosts().size() < 5 + extraGhost[currentLevel - 1] && !isUpdatingMap) {
                        GhostSpawner.spawnGhost(); // Use ghostSpawner field
                        lastGhostSpawnTime = System.nanoTime();
                    }

                    elapsedTimeNano = System.nanoTime() - lastGhostMoveTime;
                    elapsedTimeSeconds = elapsedTimeNano / 1_000_000_000.0;
                    if (elapsedTimeSeconds >= 1) {
                        playerMovement.moveGhosts();
                        lastGhostMoveTime = System.nanoTime();
                    }
                }
            }
        }.start();
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

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

//    public static ArrayList<Ghost> getGhosts() {
//        return ghosts;
//    }
//
//    public static void setGhosts(ArrayList<Ghost> ghosts) {
//        GamePanel.ghosts = ghosts;
//    }

    public Direction getPlayerDirection() {
        return playerDirection;
    }

    public void setPlayerDirection(Direction playerDirection) {
        this.playerDirection = playerDirection;
    }

    public static ArrayList<Pair<Integer, Integer>> getSpawnablePosition() {
        return spawnablePosition;
    }

    public static void setSpawnablePosition(ArrayList<Pair<Integer, Integer>> spawnablePosition) {
        GamePanel.spawnablePosition = spawnablePosition;
    }

    public Media getBuzzer() {
        return buzzer;
    }

    public void setBuzzer(Media buzzer) {
        this.buzzer = buzzer;
    }

    public boolean isHasGameEnded() {
        return hasGameEnded;
    }

    public void setHasGameEnded(boolean hasGameEnded) {
        this.hasGameEnded = hasGameEnded;
    }

    public boolean isUpdatingMap() {
        return isUpdatingMap;
    }

    public void setUpdatingMap(boolean updatingMap) {
        isUpdatingMap = updatingMap;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public MediaPlayer getGunshotSound() {
        return gunshotSound;
    }


    // Instance + Logic classes


    public static GamePanel getInstance() {
        return instance;
    }

    public static void setInstance(GamePanel instance) {
        GamePanel.instance = instance;
    }

    public PlayerMovement getPlayerMovement() {
        return playerMovement;
    }

    public BulletLogic getBulletLogic() {
        return bulletLogic;
    }

    public MapLoader getMapLoader() {
        return mapLoader;
    }

    public GhostSpawner getGhostSpawner() {
        return ghostSpawner;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }
}