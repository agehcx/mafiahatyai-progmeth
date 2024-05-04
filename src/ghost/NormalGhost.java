package ghost;

public class NormalGhost extends Ghost {
    public NormalGhost() {
        super(1,1,2);
    }

    public NormalGhost(int x, int y) {
        super(x, y, 2);
    }

    public NormalGhost(int x, int y, int speed) {
        super(x, y, speed);
    }

}
