package logic;

import ghost.*;
import javafx.util.Pair;
import main.GamePanel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GhostSpawner {

    public static double bossX;
    public static double bossY;
    public static double bladeX;
    public static double bladeY;
    public static boolean isBossDead = false;
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

        Random rnd = new Random();
        int rndGhost = random.nextInt(3);

        switch (rndGhost) {
            case 0 -> {
                NormalGhost spawnedGhost = new NormalGhost(1, spawnedGhostX, spawnedGhostY, 1);
                ghosts.add(spawnedGhost);
            }
            case 1 -> {
                SpeedyGhost speedGhost = new SpeedyGhost(1, spawnedGhostX,spawnedGhostY, 2);
                ghosts.add(speedGhost);
            }
            case 2 -> {
                TankGhost tankGhost = new TankGhost(2, spawnedGhostX, spawnedGhostY, 1);
                ghosts.add(tankGhost);
            }
        }


        return ghosts;
    }

    private static boolean isOnFootpath(int x, int y) {
        return GamePanel.getInstance().getMapPattern()[x][y] == 'O';
    }

    private static boolean isWithinRangeOfPlayer(int x, int y) {
        int playerX = GamePanel.getInstance().getPlayerX();
        int playerY = GamePanel.getInstance().getPlayerY();
        return (Math.abs(playerX - y) <= 2 && Math.abs(playerY - x) <= 2) || Math.abs(playerX - x) <= 2 && Math.abs(playerY - y) <= 2;
    }

    public static void spawnBoss() {
        System.out.println("BOSS SPAWNED");
        ghosts.add(new BossGhost(20, 9, 16, 1));
        BossGhost bg = (BossGhost) ghosts.get(ghosts.size() - 1);
        bg.spinBlade();
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