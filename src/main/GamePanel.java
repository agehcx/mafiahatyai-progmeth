package main;

import ghost.BossGhost;
import ghost.Ghost;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;

import logic.*;
import object.Bullet;
import object.Direction;

import java.io.File;
import java.util.ArrayList;

public class GamePanel extends Pane {

    private static GamePanel instance;
    private Movement movement = new Movement();
    private BulletLogic bulletLogic = new BulletLogic();
    private MapLoader mapLoader = new MapLoader();
    private GhostSpawner ghostSpawner = new GhostSpawner();
    private ImageManager imageManager = new ImageManager();
    private Player player = new Player();
    private Painter painter = new Painter();
    private BossLogic bossLogic = new BossLogic();
    private int screenWidth;
    private int screenHeight;
    private final int blockSize = 40; // Size of each block
    private final int screenWidthBlocks = 32;
    private final int screenHeightBlocks = 18;
    private char[][] mapPattern;
    private ArrayList<Bullet> bullets = new ArrayList<>();
//    private static ArrayList<Ghost> ghosts = new ArrayList<>();
    private Direction playerDirection = Direction.RIGHT;
    private static ArrayList<Pair<Integer,Integer>> spawnablePosition = new ArrayList<>();
    Media buzzer = new Media(new File("res/sound/shoot.mp3").toURI().toString());
    MediaPlayer gunshotSound = new MediaPlayer(buzzer);
    Media bgm = new Media(new File("res/sound/ingame.mp3").toURI().toString());
    MediaPlayer backgroundMusic = new MediaPlayer(bgm);
    private boolean hasGameEnded = false;
    private boolean isUpdatingMap = false;
    private boolean hasKey = false;
    private int currentPoint = 0;
    private int currentLevel = 1;
    // Image resources
    private final int[] extraGhost = {0, 1, 3, 5, 8};
    private final int[] levelSpawntime = {0, 0, 0, 1, 2};
    private long startTimeNano = 0;

    boolean hasSpawnSpinningBlade = false;

    public GamePanel() {
        instance = this;

        double v = blockSize * (double) screenWidthBlocks;
        screenWidth = (int) v;
        double v1 = blockSize * (double) screenHeightBlocks;
        screenHeight = (int) v1 + 40;

        this.setPrefSize(screenWidth, screenHeight);
        this.setStyle("-fx-background-color: black;");
        this.setFocusTraversable(true);

        mapPattern = map.level1.getMapPattern();

        GhostSpawner.spawnGhost();

        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusic.setVolume(0.125);
        backgroundMusic.play();

//        Ghost slc = GhostSpawner.getGhosts().get(0);
//        if (slc instanceof BossGhost && !hasSpawnSpinningBlade) {
//            hasSpawnSpinningBlade = true;
//            ((BossGhost) slc).spinBlade();
//            System.out.println("BLADE SPINNED");
//            hasSpawnSpinningBlade = false;
//        }

        player.setPlayerX(blockSize * 2); // Start player at a specific position
        player.setPlayerY(blockSize * 2); // Start player at a specific position


        KeyHandler keyHandler = new KeyHandler(movement, bulletLogic,
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
//                     Handle game end logic
                    System.out.println("GAME ENDED");
                    GhostSpawner.setGhosts(new ArrayList<>());
                } else {
                    bulletLogic.updateBullets();
                    bulletLogic.updateBulletGhostCollisions();
                    GamePanel.getInstance().getPainter().repaint();

                    long elapsedTimeNano = System.nanoTime() - lastGhostSpawnTime;
                    double elapsedTimeSeconds = elapsedTimeNano / 1_000_000_000.0;

                    if (elapsedTimeSeconds >= (3 - levelSpawntime[currentLevel - 1])  && GhostSpawner.getGhosts().size() < 5 + extraGhost[currentLevel - 1] && !isUpdatingMap) {
                        GhostSpawner.spawnGhost(); // Use ghostSpawner field
                        lastGhostSpawnTime = System.nanoTime();
                    }

                    elapsedTimeNano = System.nanoTime() - lastGhostMoveTime;
                    elapsedTimeSeconds = elapsedTimeNano / 1_000_000_000.0;
                    if (elapsedTimeSeconds >= 1) {
                        movement.moveGhosts();
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
        return player.getPlayerX();
    }

    public void setPlayerX(int playerX) {
        player.setPlayerX(playerX);
    }

    public int getPlayerY() {
        return player.getPlayerY();
    }

    public void setPlayerY(int playerY) {
        player.setPlayerY(playerY);
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

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    // Non-getter/setter functions

    public void playBossMusic() {
        backgroundMusic.setVolume(0);

    }

    // Instance + Logic classes

    public static GamePanel getInstance() {
        return instance;
    }

    public static void setInstance(GamePanel instance) {
        GamePanel.instance = instance;
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

    public Player getPlayer() {
        return player;
    }

    public Movement getMovement() {
        return movement;
    }

    public Painter getPainter() {
        return painter;
    }

    public BossLogic getBossLogic() {
        return bossLogic;
    }
}