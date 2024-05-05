package ghost;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import logic.GhostSpawner;
import logic.Painter;
import main.GamePanel;
import main.Player;

import java.text.spi.BreakIteratorProvider;
import java.util.concurrent.TimeUnit;

public class BossGhost extends Ghost {

    public BossGhost() {
        super(50,1,1,2);
    }
    public BossGhost(int hp, int x, int y, int speed) {
        super(hp, x, y, speed);
    }

    final int radius = 3;

    @Override
    public void move(char[][] mapPattern) {
//        if (1==1) return;
//        System.out.println(getX() + "," + getY());
        switch (getGhostDirection()) {
            case UP:
                if (isValidMove(getX() - getSpeed(), getY(), mapPattern)) {
                    setX(getX() - getSpeed());
                } else {
                    changeDirection();
                }
                break;
            case DOWN:
                // Prevent moving to Height - 2
                // In this case the ghost will override screen border
                if (mapPattern[getX() + 2][getY()] == 'X') {
                    changeDirection();
                } else if (isValidMove(getX() + getSpeed(), getY(), mapPattern)) {
                    setX(getX() + getSpeed());
                } else {
                    changeDirection();
                }
                break;
            case LEFT:
                if (isValidMove(getX(), getY() - getSpeed(), mapPattern)) {
                    setY(getY() - getSpeed());
                } else {
                    changeDirection();
                }
                break;
            case RIGHT:
                if (isValidMove(getX(), getY() + getSpeed(), mapPattern)) {
                    setY(getY() + getSpeed());
                } else {
                    changeDirection();
                }
                break;
        }
    }

    public void spinBlade() {
        System.out.println("Spinning blade..");
        Thread spinThread = new Thread(() -> {
            for (int i = 0; i < 36000; i++) {
                double angleRadians = Math.toRadians(i);
                // Convert degrees to radians
                double sinValue = Math.sin(angleRadians);
                double cosValue = Math.cos(angleRadians);

                GhostSpawner.bladeX = GhostSpawner.bossX + (sinValue * radius);
                GhostSpawner.bladeY = GhostSpawner.bossY + (cosValue * radius);

                // Call Painter to update graphics
                Platform.runLater(() -> GamePanel.getInstance().getPainter().repaint());

                // Delay for a short period
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            GamePanel.getInstance().setHasGameEnded(true);

            // If player cannot kill the boss within 180 seconds player lose.

        });

        spinThread.start();


    }

}
