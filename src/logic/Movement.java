package logic;

import component.RetroFont;
import ghost.BossGhost;
import ghost.Ghost;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.GamePanel;
import map.level1;
import object.Bullet;
import object.Direction;

import java.util.*;

public class Movement {

    private final int blockSize = 40;

    public void movePlayer(int dx, int dy) {
        int newX = GamePanel.getInstance().getPlayerX() + dx;
        int newY = GamePanel.getInstance().getPlayerY() + dy;
        int blockSize = GamePanel.getInstance().getBlockSize();
        // Check if new position is within bounds and is not a wall
        if (newX >= 0 && newX + blockSize <= GamePanel.getInstance().getScreenWidth() && newY >= 0 && newY + blockSize <= GamePanel.getInstance().getScreenHeight()
                && GamePanel.getInstance().getMapPattern()[newY / blockSize][newX / blockSize] != 'X') {
            GamePanel.getInstance().setPlayerX(newX);
            GamePanel.getInstance().setPlayerY(newY);
            updateDirection(dx, dy);
//            System.out.println("POSITION: [" + getPlayerY() / blockSize + ", " + getPlayerX() / blockSize + "]");
        }

    }

    public void moveGhosts() {
        for (Ghost ghost : GhostSpawner.getGhosts()) {
            if (ghost instanceof BossGhost) {
                BossGhost bg = (BossGhost) ghost;
                int prevX = bg.getX();
                int prevY = bg.getY();
                ghost.move(GamePanel.getInstance().getMapPattern());
//                if (prevX != ghost.getX() || prevY != ghost.getY()) {
//                    ((BossGhost) ghost).spinBlade();
//                }
//                ((BossGhost) ghost).spinBlade();
            } else {
                ghost.move(GamePanel.getInstance().getMapPattern());
            }
            GamePanel.getInstance().getPainter().repaint();
        }
//        List<Ghost> ghosts = new ArrayList<>(GamePanel.getInstance().getGhosts());
//        for (Ghost ghost : ghosts) {
//            ghost.move(GamePanel.getInstance().getMapPattern());
//        }
        GamePanel.getInstance().getPainter().repaint();

    }

    void updateDirection(int dx, int dy) {
        if (dx > 0) { // Moving right
            GamePanel.getInstance().setPlayerDirection(Direction.RIGHT);
            GamePanel.getInstance().getImageManager().setCurrentCharacterImage(GamePanel.getInstance().getImageManager().getCharacterRight());
        } else if (dx < 0) { // Moving left
//            playerDirection = Direction.LEFT;
            GamePanel.getInstance().setPlayerDirection(Direction.LEFT);
            GamePanel.getInstance().getImageManager().setCurrentCharacterImage(GamePanel.getInstance().getImageManager().getCharacterLeft());
//            currentCharacterImage = characterLeft;
        } else if (dy > 0) { // Moving down
//            playerDirection = Direction.DOWN;
            GamePanel.getInstance().setPlayerDirection(Direction.DOWN);
            GamePanel.getInstance().getImageManager().setCurrentCharacterImage(GamePanel.getInstance().getImageManager().getCharacterDown());
//            currentCharacterImage = characterDown;
        } else if (dy < 0) { // Moving up
//            playerDirection = Direction.UP;
            GamePanel.getInstance().setPlayerDirection(Direction.UP);
            GamePanel.getInstance().getImageManager().setCurrentCharacterImage(GamePanel.getInstance().getImageManager().getCharacterUp());
//            currentCharacterImage = characterUp;
        }
    }
}
