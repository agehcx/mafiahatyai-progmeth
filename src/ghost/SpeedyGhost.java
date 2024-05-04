package ghost;

public class SpeedyGhost extends Ghost {

    public SpeedyGhost() {
        super(1,1,1,2);
    }

    public SpeedyGhost(int x, int y) {
        super(1,x, y, 2);
    }

    public SpeedyGhost(int x, int y, int speed) {
        super(1, x, y, speed);
    }

    public SpeedyGhost(int hp, int x, int y, int speed) {
        super(hp, x, y, speed);
    }

}
