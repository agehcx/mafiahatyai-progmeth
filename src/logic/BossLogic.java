package logic;

import main.GamePanel;

public class BossLogic {

    private long lastHitTime = 0;
    private final long hitCooldownMillis = 1500; // 1.5 seconds in milliseconds

    // Constructor
    public BossLogic() {}

    // Method to update boss blade position and check collision with player
    public boolean swordHitPlayer() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHitTime < hitCooldownMillis) {
            // Cannot get hit again when on cooldown
            return false;
        }

        int blockSize = GamePanel.getInstance().getBlockSize();

        // Get player and blade coordinates
        int playerX = GamePanel.getInstance().getPlayer().getPlayerX();
        int playerY = GamePanel.getInstance().getPlayer().getPlayerY();
        double bladeX = GhostSpawner.bladeX * blockSize;
        double bladeY = GhostSpawner.bladeY * blockSize;

        // Check if the sword hits the player
        if (playerX >= bladeY - 0.5 * blockSize && playerX <= bladeY + 0.5 * blockSize &&
            playerY >= bladeX - 0.5 * blockSize && playerY <= bladeX + 0.5 * blockSize ) {
            // Sword hit player
            System.out.println("Sword hit player");

            // Reduce player HP
            int currentHP = GamePanel.getInstance().getPlayer().getPlayerHp();
            GamePanel.getInstance().getPlayer().setPlayerHp(currentHP - 1);

            lastHitTime = currentTime;

            return true; // Return
        }

        return false;
    }
}
