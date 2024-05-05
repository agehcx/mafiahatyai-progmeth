package logic;

import javafx.scene.image.Image;
import javafx.util.Pair;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Random;

public class MapLoader {

    static boolean isUpdatingMap;
    static int chestX = 16;
    static int chestY = 10;
    static int homeX = 16;
    static int homeY = 1;
    static int currentLevel = 1;

    public static GamePanel updateMap(int level) {

        char[][] mapPattern = GamePanel.getInstance().getMapPattern();
        ArrayList<Pair<Integer,Integer>> spawnablePosition = new ArrayList<>();
        int playerX = GamePanel.getInstance().getPlayerX();
        int playerY = GamePanel.getInstance().getPlayerY();
        int blockSize = GamePanel.getInstance().getBlockSize();
        int currentLevel = GamePanel.getInstance().getCurrentLevel();

        if (level > 5) {
            return GamePanel.getInstance();
        }

        isUpdatingMap = true;

        GhostSpawner.setGhosts(new ArrayList<>());

        switch (level) {
            case 1 -> {
                mapPattern = map.level1.getMapPattern();
                GamePanel.getInstance().getImageManager().setWall(new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true));
                GamePanel.getInstance().getImageManager().setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
            }
            case 2 -> {
                mapPattern = map.level2.getMapPattern();
                GamePanel.getInstance().getImageManager().setWall(new Image("file:res/gif/wood.jpg", blockSize, blockSize, true, true));
                GamePanel.getInstance().getImageManager().setFootPath(new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true));
                GamePanel.getInstance().getImageManager().setCharacterUp(new Image("file:res/character/wmUp.png", blockSize*1.2, blockSize*1.2, true, true));
                GamePanel.getInstance().getImageManager().setCharacterDown(new Image("file:res/character/wmDown.png", blockSize*1.2, blockSize*1.2, true, true));
                GamePanel.getInstance().getImageManager().setCharacterRight(new Image("file:res/character/wmRight.png", blockSize*1.2, blockSize*1.2, true, true));
                GamePanel.getInstance().getImageManager().setCharacterLeft(new Image("file:res/character/wmLeft.png", blockSize*1.2, blockSize*1.2, true, true));
            }
            case 3 -> {
                mapPattern = map.level3.getMapPattern();
                GamePanel.getInstance().getImageManager().setWall(new Image("file:res/gif/grass.jpg", blockSize*1.3, blockSize*1.3, true, true));
                GamePanel.getInstance().getImageManager().setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
            }
            case 4 -> {
                mapPattern = map.level4.getMapPattern();
                GamePanel.getInstance().getImageManager().setWall(new Image("file:res/gif/fire.jpg", blockSize, blockSize, true, true));
                GamePanel.getInstance().getImageManager().setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
            }
            case 5 -> {
                GamePanel.getInstance().playBossMusic();
                mapPattern = map.level5.getMapPattern();
                GamePanel.getInstance().getImageManager().setWall(new Image("file:res/gif/water.jpg", blockSize, blockSize, true, true));
                GamePanel.getInstance().getImageManager().setFootPath(new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true));
                GhostSpawner.spawnBoss();
            }
        };

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 32; j++) {
                if (mapPattern[i][j] == 'O') {
                    spawnablePosition.add(new Pair<>(i, j));
                } else if (mapPattern[i][j] == 'C') {
                    chestX = j;
                    chestY = i;
                } else if (mapPattern[i][j] == 'H') {
                    homeX = j;
                    homeY = i;
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

            GamePanel.getInstance().setPlayerX(randomX);
            GamePanel.getInstance().setPlayerY(randomY);
        }

        GamePanel.getInstance().setMapPattern(mapPattern);
        GamePanel.setSpawnablePosition(spawnablePosition);
        GamePanel.getInstance().setCurrentLevel(currentLevel + 1);
        isUpdatingMap = false;
        System.out.println("Map update successfully. ");

        return GamePanel.getInstance();
    }

    public static boolean isIsUpdatingMap() {
        return isUpdatingMap;
    }

    public static void setIsUpdatingMap(boolean isUpdatingMap) {
        MapLoader.isUpdatingMap = isUpdatingMap;
    }

    public static int getChestX() {
        return chestX;
    }

    public static void setChestX(int chestX) {
        MapLoader.chestX = chestX;
    }

    public static int getChestY() {
        return chestY;
    }

    public static void setChestY(int chestY) {
        MapLoader.chestY = chestY;
    }

    public static int getHomeX() {
        return homeX;
    }

    public static void setHomeX(int homeX) {
        MapLoader.homeX = homeX;
    }

    public static int getHomeY() {
        return homeY;
    }

    public static void setHomeY(int homeY) {
        MapLoader.homeY = homeY;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int currentLevel) {
        MapLoader.currentLevel = currentLevel;
    }
}
