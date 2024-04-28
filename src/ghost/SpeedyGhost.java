package ghost;

public class SpeedyGhost extends Ghost {

    public SpeedyGhost() {
        super(1,1,2);
    }

    public SpeedyGhost(int x, int y) {
        super(x, y, 2);
    }

    public SpeedyGhost(int x, int y, int speed) {
        super(x, y, speed);
    }

}
