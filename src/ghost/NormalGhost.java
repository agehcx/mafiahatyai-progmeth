package ghost;

public class NormalGhost extends Ghost {
    public NormalGhost() {
        super(1,1,1,2);
    }

    public NormalGhost(int x, int y) {
        super(1, x, y, 2);
    }

    public NormalGhost(int x, int y, int speed) {
        super(1, x, y, speed);
    }

    public NormalGhost(int hp, int x, int y, int speed) {
        super(hp, x, y, speed);
    }
}
