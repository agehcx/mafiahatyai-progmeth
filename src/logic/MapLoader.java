package logic;

import javafx.scene.image.Image;
import javafx.util.Pair;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Random;

public class MapLoader {

    public static GamePanel updateMap(int level) {

        GamePanel gamePanel = GamePanel.getInstance();

        char[][] mapPattern = gamePanel.getMapPattern();
        ArrayList<Pair<Integer,Integer>> spawnablePosition = new ArrayList<>();
        int playerX = gamePanel.getPlayerX();
        int playerY = gamePanel.getPlayerY();
        int blockSize = gamePanel.getBlockSize();
        int currentLevel = gamePanel.getCurrentLevel();

        if (level > 5) {
            return gamePanel;
        }

        boolean isUpdatingMap = true;

        gamePanel.getGhosts().clear();

        switch (level) {
            case 1 -> {
                mapPattern = map.level1.getMapPattern();
                gamePanel.setWall(new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true));
                gamePanel.setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
            }
            case 2 -> {
                mapPattern = map.level2.getMapPattern();
                gamePanel.setWall(new Image("file:res/gif/wood.jpg", blockSize, blockSize, true, true));
                gamePanel.setFootPath(new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true));
                gamePanel.setCharacterUp(new Image("file:res/character/wmUp.png", blockSize*1.2, blockSize*1.2, true, true));
                gamePanel.setCharacterDown(new Image("file:res/character/wmDown.png", blockSize*1.2, blockSize*1.2, true, true));
                gamePanel.setCharacterRight(new Image("file:res/character/wmRight.png", blockSize*1.2, blockSize*1.2, true, true));
                gamePanel.setCharacterLeft(new Image("file:res/character/wmLeft.png", blockSize*1.2, blockSize*1.2, true, true));
            }
            case 3 -> {
                mapPattern = map.level3.getMapPattern();
                gamePanel.setWall(new Image("file:res/gif/water.jpg", blockSize, blockSize, true, true));
                gamePanel.setFootPath(new Image("file:res/gif/wood.jpg", blockSize, blockSize, true, true));
            }
            case 4 -> {
                mapPattern = map.level4.getMapPattern();
                gamePanel.setWall(new Image("file:res/gif/fire.jpg", blockSize, blockSize, true, true));
                gamePanel.setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
            }
            case 5 -> {
                mapPattern = map.level5.getMapPattern();
                gamePanel.setWall(new Image("file:res/gif/water.jpg", blockSize, blockSize, true, true));
                gamePanel.setFootPath(new Image("file:res/grass/rock.jpg", blockSize, blockSize, true, true));
            }
        };

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 32; j++) {
                if (mapPattern[i][j] == 'O') {
                    spawnablePosition.add(new Pair<>(i, j));
                }
            }
        }

        if (!(spawnablePosition.stream()
                .anyMatch(pair -> pair.getKey() == playerY / blockSize && pair.getValue() == playerX / blockSize))) {

            Random random = new Random();
            int rndPosition = random.nextInt(spawnablePosition.size());

            Pair<Integer, Integer> randomPosition = spawnablePosition.get(rndPosition);
            int randomX = randomPosition.getValue() * blockSize;
            int randomY = randomPosition.getKey() * blockSize;

            gamePanel.setPlayerX(randomX);
            gamePanel.setPlayerY(randomY);
        }

        gamePanel.setMapPattern(mapPattern);
        gamePanel.setSpawnablePosition(spawnablePosition);
        gamePanel.setCurrentLevel(currentLevel + 1);
        isUpdatingMap = false;

        return gamePanel;
    }
}
