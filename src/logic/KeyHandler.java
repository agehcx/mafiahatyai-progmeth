package logic;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.GamePanel;

public class KeyHandler implements EventHandler<KeyEvent> {

    private Movement playerMovement;
    private BulletLogic bulletLogic;
    private Runnable updateMap;
    private Runnable updateSpawnablePosition;
    private int blockSize;
    private int currentLevel;

    public KeyHandler(Movement movement, BulletLogic bulletLogic,
                      Runnable updateMap, Runnable updateSpawnablePosition, int blockSize, int currentLevel) {
        this.playerMovement = movement;
        this.bulletLogic = bulletLogic;
        this.updateMap = updateMap;
        this.updateSpawnablePosition = updateSpawnablePosition;
        this.blockSize = blockSize;
        this.currentLevel = currentLevel;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if (!GamePanel.getInstance().isHasGameEnded()) {
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
            } else if (code == KeyCode.P) {
                System.out.println("Restart P !");
                GameInstance gi = new GameInstance();
                gi.resetGameInstance();
                updateMap.run();
                updateSpawnablePosition.run();
            } else if (code == KeyCode.E) {
//                System.out.println(GamePanel.getInstance().getPlayerX() + "," + GamePanel.getInstance().getPlayerY());
//                System.out.println(MapLoader.chestX + "," + MapLoader.chestY);
//                System.out.println(MapLoader.homeX  + "," + MapLoader.homeY );
                if (   GamePanel.getInstance().getPlayerX() / blockSize == MapLoader.chestX
                    && GamePanel.getInstance().getPlayerY() / blockSize == MapLoader.chestY) {
//                    updateMap.run();\
                    if (GamePanel.getInstance().getCurrentPoint() >= 50 && !GamePanel.getInstance().isHasKey()) {
                        GamePanel.getInstance().setCurrentPoint(GamePanel.getInstance().getCurrentPoint() - 50);
                        GamePanel.getInstance().setHasKey(true);
                    }
                } else if (GamePanel.getInstance().getPlayerX() / blockSize == MapLoader.homeX
                        && GamePanel.getInstance().getPlayerY() / blockSize == MapLoader.homeY) {
                    if (GamePanel.getInstance().isHasKey()) {
                        updateMap.run();
                        GamePanel.getInstance().setHasKey(false);
                    }
                }
            }
        } else {
            if (code == KeyCode.O && GamePanel.getInstance().isHasGameEnded()) {
                System.out.println("Pressed OOOOOOOOOOOOOOOOOOOOOOOOO !");
                GameInstance gi = new GameInstance();
                gi.resetGameInstance();
                updateMap.run();
                updateSpawnablePosition.run();
            }
        }
    }
}
