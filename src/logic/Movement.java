package logic;

import ghost.BossGhost;
import ghost.Ghost;
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
            checkPlayerCollide();
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
            } else {
                ghost.move(GamePanel.getInstance().getMapPattern());
            }
            GamePanel.getInstance().getPainter().repaint();
        }
//        List<Ghost> ghosts = new ArrayList<>(GamePanel.getInstance().getGhosts());
//        for (Ghost ghost : ghosts) {
//            ghost.move(GamePanel.getInstance().getMapPattern());
//        }

        checkPlayerCollide();
    }

    void checkPlayerCollide() {
        for (Iterator<Ghost> iterator = GhostSpawner.getGhosts().iterator(); iterator.hasNext();) {
            Ghost ghost = iterator.next();
            if (GamePanel.getInstance().getPlayerX() == ghost.getY() * blockSize && GamePanel.getInstance().getPlayerY() == ghost.getX() * blockSize) {
                // Upon player collide with ghost player lose 1 hp, then ghost will be destroyed.
                if (GamePanel.getInstance().getPlayer().getPlayerHp() <= 1) {
                    // Player collides with ghost, set current score to 0
                    GamePanel.getInstance().setCurrentPoint(0);
                    System.out.println("Dead...");
                    System.out.println("Has game end starting ? " + GamePanel.getInstance().isHasGameEnded());

                    GamePanel.getInstance().setPlayerX(blockSize);
                    GamePanel.getInstance().setPlayerY(blockSize);
                    GamePanel.getInstance().setMapPattern(level1.getMapPattern());

//                    GamePanel.getInstance().resetMapToLevel1();

                    GhostSpawner.setGhosts(new ArrayList<>());
                    GamePanel.getInstance().setHasGameEnded(true);

                    System.out.println("IS GAME ENDED ? " + GamePanel.getInstance().isHasGameEnded());

//                    GamePanel.getInstance().resetInstance();
//                    GameInstance gi = new GameInstance();
//                    gi.resetGameInstance();

                    System.out.println("GAME ENDED !!");

//                    displayGameOverScreen();

//                    GamePanel.setInstance(new GamePanel());

                } else {
                    GamePanel.getInstance().getPlayer().setPlayerHp(GamePanel.getInstance().getPlayer().getPlayerHp() - 1);
                    GamePanel.getInstance().getPainter().repaint();
                    System.out.println("CASE HERE");
                    if (!(ghost instanceof BossGhost)) {
                        iterator.remove();
                    }
//                    GamePanel.getInstance().getPlayer().setPlayerHp(2);
                }

            } if (ghost instanceof BossGhost) {
                GhostSpawner.bossX = ghost.getX();
                GhostSpawner.bossY = ghost.getY();
            }
        }
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
