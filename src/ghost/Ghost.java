package ghost;

public class Ghost {

    private int x;
    private int y;
    private int speed;

    public Ghost() {
        this.x = 1;
        this.y = 1;
        this.speed = 1;
    }

    public Ghost(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
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
}
