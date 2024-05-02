package logic;

import main.GamePanel;
import object.Direction;

public class GameInstance {

    public GameInstance() {
    }

    public void resetGameInstance() {
        // Reset player position
        GamePanel.getInstance().setPlayerX(GamePanel.getInstance().getBlockSize() * 2);
        GamePanel.getInstance().setPlayerY(GamePanel.getInstance().getBlockSize() * 2);

        // Clear bullets
//        bullets.clear();
        GamePanel.getInstance().getBullets().clear();

        // Clear ghosts
//        ghosts.clear();
        GamePanel.getInstance().getGhosts().clear();

        // Reset game state variables
//        hasGameEnded = false;
//        currentPoint = 0;
//        currentLevel = 1;
//        mapPattern = map.level1.getMapPattern();
        GamePanel.getInstance().setHasGameEnded(false);
        GamePanel.getInstance().setCurrentPoint(0);
        GamePanel.getInstance().setCurrentLevel(1);
        GamePanel.getInstance().setMapPattern(map.level1.getMapPattern());

        // Reset player direction and image
//        playerDirection = Direction.RIGHT;
//        currentCharacterImage = characterRight;
        GamePanel.getInstance().setPlayerDirection(Direction.RIGHT);
        GamePanel.getInstance().setCurrentCharacterImage(GamePanel.getInstance().getCharacterRight());

        // Update spawnable positions
//        updateSpawnablePosition();

        // Start the game loop again

    }
}
