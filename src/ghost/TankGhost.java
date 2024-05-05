package ghost;

public class TankGhost extends Ghost {
    public TankGhost() {
        super(2,1,1,1);
    }

    public TankGhost(int x, int y) {
        super(2,x, y, 1);
    }

    public TankGhost(int x,  int y, int speed) {
        super(2, x, y, speed);
    }

    public TankGhost(int hp, int x, int y, int speed) {
        super(hp, x, y, speed);
    }
}
