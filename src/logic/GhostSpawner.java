package logic;

import ghost.Ghost;
import javafx.util.Pair;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Random;

public class GhostSpawner {
    static ArrayList<Pair<Integer,Integer>> spawnablePosition;

    static ArrayList<Ghost> ghosts;

    public GhostSpawner() {
        spawnablePosition = new ArrayList<>();
        ghosts = new ArrayList<>();
    }

    public static ArrayList<Ghost> spawnerSpawnGhost(ArrayList<Pair<Integer, Integer>> spawnLocation, ArrayList<Ghost> currentGhosts) {
        spawnablePosition = spawnLocation;
        ghosts = currentGhosts;

        // Filter spawnable positions to ensure ghosts spawn only on footpath and not within 5x5 radius of player
        ArrayList<Pair<Integer, Integer>> footPathPositions = new ArrayList<>();
        for (Pair<Integer, Integer> position : spawnablePosition) {
            int x = position.getKey();
            int y = position.getValue();

            // Check if the position is within the allowed range and on footpath
            if (isWithinRangeOfPlayer(x, y) || !isOnFootpath(x, y)) {
                continue;
            }

            footPathPositions.add(position);
        }

        // Check if there are any footpath positions available
        if (footPathPositions.isEmpty()) {
            // If there are no footpath positions available, return current ghosts without spawning a new ghost
            return ghosts;
        }

        // Select a random footpath position to spawn the ghost
        Random random = new Random();
        int rndPosition = random.nextInt(footPathPositions.size());
        Pair<Integer, Integer> pair = footPathPositions.get(rndPosition);
        int spawnedGhostX = pair.getKey();
        int spawnedGhostY = pair.getValue();

        Ghost spawnedGhost = new Ghost(spawnedGhostX, spawnedGhostY, 1);
        ghosts.add(spawnedGhost);

        return ghosts;
    }

    // Check if the given position is on footpath
    private static boolean isOnFootpath(int x, int y) {
        return GamePanel.getInstance().getMapPattern()[x][y] == 'O';
    }

    // Check if the given position is within the allowed range of the player
    private static boolean isWithinRangeOfPlayer(int x, int y) {
        int playerX = GamePanel.getInstance().getPlayerX();
        int playerY = GamePanel.getInstance().getPlayerY();
        return Math.abs(playerX - y) <= 2 && Math.abs(playerY - x) <= 2;
    }
}
