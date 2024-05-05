package main;

public class Player {

    private int playerHp;
    private int playerX;
    private int playerY;

    public Player() {
        this.playerHp = 3;
        this.playerX = 2 * 40;
        this.playerY = 2 * 40;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerHp() {
        return playerHp;
    }

    public void setPlayerHp(int playerHp) {
        this.playerHp = playerHp;
    }
}
