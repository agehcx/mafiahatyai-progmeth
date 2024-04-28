package logic;

import ghost.Ghost;
import javafx.util.Pair;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Random;

import static main.GamePanel.getGhosts;
import static main.GamePanel.getSpawnablePosition;

public class GhostSpawner {
    static ArrayList<Pair<Integer,Integer>> spawnablePosition = getSpawnablePosition();
    static ArrayList<Ghost> ghosts = getGhosts();

    public static ArrayList<Ghost> spawnGhost() {
        Random random = new Random();
        int rndPosition = random.nextInt(spawnablePosition.size());

        Pair<Integer, Integer> pair = spawnablePosition.get(rndPosition);
        int spawnedGhostX = pair.getKey();
        int spawnedGhostY = pair.getValue();

        Ghost spawnedGhost = new Ghost(spawnedGhostX, spawnedGhostY, 1);

        ghosts.add(spawnedGhost);

        return ghosts;
    }
}
