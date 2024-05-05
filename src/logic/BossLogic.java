package logic;

import main.GamePanel;

public class BossLogic {

    // Constants for blade movement directions
    public static final int BLADE_DIRECTION_UP = 0;
    public static final int BLADE_DIRECTION_DOWN = 1;
    public static final int BLADE_DIRECTION_LEFT = 2;
    public static final int BLADE_DIRECTION_RIGHT = 3;

    private int bladeX;
    private int bladeY;
    private int bladeDirection;

    // Constructor
    public BossLogic(int initialX, int initialY, int initialDirection) {
        this.bladeX = initialX;
        this.bladeY = initialY;
        this.bladeDirection = initialDirection;
    }

    // Method to update boss blade position and check collision with player
    public void update() {
        // Update boss blade position based on direction
        switch (bladeDirection) {
            case BLADE_DIRECTION_UP:
                bladeX--;
                break;
            case BLADE_DIRECTION_DOWN:
                bladeX++;
                break;
            case BLADE_DIRECTION_LEFT:
                bladeY--;
                break;
            case BLADE_DIRECTION_RIGHT:
                bladeY++;
                break;
        }

        // Check collision with player
        if (bladeX == GamePanel.getInstance().getPlayerX() && bladeY == GamePanel.getInstance().getPlayerY()) {
            // Collision occurred, decrement player's health points
            GamePanel.getInstance().decrementPlayerHealth(); // Implement this method in GamePanel class
        }
    }

    // Getters and setters
    public int getBladeX() {
        return bladeX;
    }

    public void setBladeX(int bladeX) {
        this.bladeX = bladeX;
    }

    public int getBladeY() {
        return bladeY;
    }

    public void setBladeY(int bladeY) {
        this.bladeY = bladeY;
    }

    public int getBladeDirection() {
        return bladeDirection;
    }

    public void setBladeDirection(int bladeDirection) {
        this.bladeDirection = bladeDirection;
    }
}
