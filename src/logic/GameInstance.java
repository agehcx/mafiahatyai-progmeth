package logic;

import main.GamePanel;
import object.Direction;

import java.util.ArrayList;

public class GameInstance {

    public GameInstance() {}

    public void resetGameInstance() {
        // Reset player position
        GamePanel.getInstance().setPlayerX(GamePanel.getInstance().getBlockSize() * 2);
        GamePanel.getInstance().setPlayerY(GamePanel.getInstance().getBlockSize() * 2);

        System.out.println("Resetting Game Instance");

        // Clear bullets
        GamePanel.getInstance().setBullets(new ArrayList<>());

        // Clear ghosts
        GhostSpawner.setGhosts(new ArrayList<>());

        // Reset game state variables
        GamePanel.getInstance().setHasGameEnded(false);
        GamePanel.getInstance().setCurrentPoint(0);
        GamePanel.getInstance().setCurrentLevel(0);
        GamePanel.getInstance().setMapPattern(map.level1.getMapPattern());
//        GamePanel.getInstance().resetMapToLevel1();

        // Reset player direction and image
        GamePanel.getInstance().setPlayerDirection(Direction.RIGHT);
        GamePanel.getInstance().getImageManager().setCurrentCharacterImage(GamePanel.getInstance().getImageManager().getCharacterRight());
    }

}
