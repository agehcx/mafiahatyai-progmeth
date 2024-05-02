package ghost;

import object.Direction;

import java.util.Random;

public class Ghost {

    private int x;
    private int y;
    private int speed;
    private Direction ghostDirection;

    public Ghost() {
        this.x = 1;
        this.y = 1;
        this.speed = 1;
        this.ghostDirection = Direction.UP;
    }

    public Ghost(Direction dir) {
        this.x = 1;
        this.y = 1;
        this.speed = 1;
        this.ghostDirection = dir;
    }

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        this.speed = 1;
        this.ghostDirection = Direction.UP;
    }

    public Ghost(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.ghostDirection = Direction.UP;
    }

    public Ghost(int x, int y, int speed, Direction dir) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.ghostDirection = dir;
    }

    public void move(char[][] mapPattern) {
        // Move the ghost based on its direction
        switch (ghostDirection) {
            case UP:
                if (isValidMove(x - speed, y, mapPattern)) {
                    x -= speed;
                } else {
                    changeDirection();
                }
                break;
            case DOWN:
                if (isValidMove(x + speed, y, mapPattern)) {
                    x += speed;
                } else {
                    changeDirection();
                }
                break;
            case LEFT:
                if (isValidMove(x, y - speed, mapPattern)) {
                    y -= speed;
                } else {
                    changeDirection();
                }
                break;
            case RIGHT:
                if (isValidMove(x, y + speed, mapPattern)) {
                    y += speed;
                } else {
                    changeDirection();
                }
                break;
        }
    }

    private boolean isValidMove(int newX, int newY, char[][] mapPattern) {
        // Check if the new position is within the map boundaries and not a wall
        return newX >= 0 && newX < mapPattern.length &&
                newY >= 0 && newY < mapPattern[0].length &&
                mapPattern[newX][newY] != 'X';
    }

    private void changeDirection() {
        // If the ghost hits a wall, change its direction randomly
        Random random = new Random();
        int randomDirectionIndex = random.nextInt(4); // 0 to 3
        ghostDirection = Direction.values()[randomDirectionIndex];
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getGhostDirection() {
        return ghostDirection;
    }

    public void setGhostDirection(Direction ghostDirection) {
        this.ghostDirection = ghostDirection;
    }
}
