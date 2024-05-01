package logic;

import ghost.Ghost;
import javafx.util.Pair;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Random;

public class GhostSpawner {
    static ArrayList<Pair<Integer,Integer>> spawnablePosition;
    static ArrayList<Ghost> ghosts;

    public static ArrayList<Ghost> spawnerSpawnGhost(ArrayList<Pair<Integer, Integer>> spawnLocation, ArrayList<Ghost> currentGhosts) {
        spawnablePosition = spawnLocation;
        ghosts = currentGhosts;

        // Filter spawnable positions to ensure ghosts spawn only on footpath
        ArrayList<Pair<Integer, Integer>> footPathPositions = new ArrayList<>();
        for (Pair<Integer, Integer> position : spawnablePosition) {
            int x = position.getKey();
            int y = position.getValue();
            if (GamePanel.getInstance().getMapPattern()[x][y] == 'O') {
                footPathPositions.add(position);
            }
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
}
