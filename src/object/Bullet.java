package object;

public class Bullet {
    private int x;
    private int y;
    private Direction direction;

    public Bullet(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move(int blockSize) {
        switch (direction) {
            case UP:
                y -= blockSize;
                break;
            case DOWN:
                y += blockSize;
                break;
            case LEFT:
                x -= blockSize;
                break;
            case RIGHT:
                x += blockSize;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}

