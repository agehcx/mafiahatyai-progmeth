package logic;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {

    private boolean hasGameEnded;
    private PlayerMovement playerMovement;
    private BulletLogic bulletLogic;
    private Runnable updateMap;
    private Runnable updateSpawnablePosition;
    private int blockSize;
    private int currentLevel;

    public KeyHandler(boolean hasGameEnded, PlayerMovement playerMovement, BulletLogic bulletLogic,
                          Runnable updateMap, Runnable updateSpawnablePosition, int blockSize, int currentLevel) {
        this.hasGameEnded = hasGameEnded;
        this.playerMovement = playerMovement;
        this.bulletLogic = bulletLogic;
        this.updateMap = updateMap;
        this.updateSpawnablePosition = updateSpawnablePosition;
        this.blockSize = blockSize;
        this.currentLevel = currentLevel;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if (!hasGameEnded) {
            if (code == KeyCode.UP || code == KeyCode.W) {
                playerMovement.movePlayer(0, -blockSize); // Move up
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                playerMovement.movePlayer(0, blockSize); // Move down
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                playerMovement.movePlayer(-blockSize, 0); // Move left
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                playerMovement.movePlayer(blockSize, 0); // Move right
            } else if (code == KeyCode.ESCAPE) {
                updateMap.run();
            } else if (code == KeyCode.SPACE) {
                bulletLogic.shootBullet();
            } else if (code == KeyCode.P && hasGameEnded) {
                System.out.println("Pressed P !");
                GameInstance gi = new GameInstance();
                gi.resetGameInstance();
                updateMap.run();
                updateSpawnablePosition.run();
            }
        }
    }
}
