package logic;

import ghost.Ghost;
import javafx.util.Pair;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GhostSpawner {
    private static ArrayList<Pair<Integer,Integer>> spawnablePosition;
    private static ArrayList<Ghost> ghosts;

    public GhostSpawner() {
        spawnablePosition = new ArrayList<>();
        ghosts = new ArrayList<>();
    }

    public static void spawnGhost() {
        if (!MapLoader.isIsUpdatingMap()) {
            updateSpawnablePosition();
            GamePanel.getInstance().getGhostSpawner().setGhosts(spawnerSpawnGhost(GamePanel.getInstance().getSpawnablePosition(), GhostSpawner.getGhosts()));
            System.out.println("Ghost spawned at " + GhostSpawner.getGhosts().get(GhostSpawner.getGhosts().size() - 1).getX() + "," + GhostSpawner.getGhosts().get(GhostSpawner.getGhosts().size() - 1).getY());
        }
    }

    // Update current spawn-able positions
    public static void updateSpawnablePosition() {

        if (spawnablePosition == null) {
            spawnablePosition = new ArrayList<>();
        } else {
            spawnablePosition.clear();
        }

        int blockSize = GamePanel.getInstance().getBlockSize();

        ArrayList<Pair<Integer,Integer>> tmpPos = new ArrayList<>();

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 32; j++) {
                if (    GamePanel.getInstance().getMapPattern()[i][j] == 'O'
                        && i * blockSize != GamePanel.getInstance().getPlayerX()
                        && j * blockSize != GamePanel.getInstance().getPlayerY()) {
                    tmpPos.add(new Pair<>(i, j));
                }
            }
        }

        GamePanel.getInstance().setSpawnablePosition(tmpPos);
    }

    public static ArrayList<Ghost> spawnerSpawnGhost(ArrayList<Pair<Integer, Integer>> spawnLocation, ArrayList<Ghost> currentGhosts) {
        spawnablePosition = spawnLocation;
        ghosts = currentGhosts;

        ArrayList<Pair<Integer, Integer>> footPathPositions = new ArrayList<>();
        for (Pair<Integer, Integer> position : spawnablePosition) {
            int x = position.getKey();
            int y = position.getValue();

            if (isWithinRangeOfPlayer(x, y) || !isOnFootpath(x, y)) {
                continue;
            }

            footPathPositions.add(position);
        }

        if (footPathPositions.isEmpty()) {
            return ghosts;
        }

        Random random = new Random();
        int rndPosition = random.nextInt(footPathPositions.size());
        Pair<Integer, Integer> pair = footPathPositions.get(rndPosition);
        int spawnedGhostX = pair.getKey();
        int spawnedGhostY = pair.getValue();

        Ghost spawnedGhost = new Ghost(spawnedGhostX, spawnedGhostY, 1);
        ghosts.add(spawnedGhost);

        return ghosts;
    }

    private static boolean isOnFootpath(int x, int y) {
        return GamePanel.getInstance().getMapPattern()[x][y] == 'O';
    }

    private static boolean isWithinRangeOfPlayer(int x, int y) {
        int playerX = GamePanel.getInstance().getPlayerX();
        int playerY = GamePanel.getInstance().getPlayerY();
        return Math.abs(playerX - y) <= 2 && Math.abs(playerY - x) <= 2;
    }

    // Getter for ghosts list
    public static ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    // Setter for ghosts list
    public static void setGhosts(ArrayList<Ghost> newGhosts) {
        ghosts = newGhosts;
    }
}