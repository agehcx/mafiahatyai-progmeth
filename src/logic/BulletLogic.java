package logic;

import ghost.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.GamePanel;
import map.level1;
import object.Bullet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class BulletLogic {
    public BulletLogic() {}

    public void updateBullets() {
        Iterator<Bullet> iterator = GamePanel.getInstance().getBullets().iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move(GamePanel.getInstance().getBlockSize() / 3); // Decrease bullet speed
            if (bulletHitsWall(bullet.getX(), bullet.getY())) {
                iterator.remove(); // Safely removes the current bullet from the list
            }
        }
    }

    private boolean bulletHitsWall(int x, int y) {
        return GamePanel.getInstance().getMapPattern()[y / GamePanel.getInstance().getBlockSize()][x / GamePanel.getInstance().getBlockSize()] == 'X';
    }

    // to-be moved to logic
    public void shootBullet() {
        int playerX = GamePanel.getInstance().getPlayerX();
        int playerY = GamePanel.getInstance().getPlayerY();
        System.out.println(playerX + "," + playerY);
        if (GamePanel.getInstance().getMapPattern()[playerY / GamePanel.getInstance().getBlockSize()][playerX / GamePanel.getInstance().getBlockSize()] != 'B') {

            int bulletX = GamePanel.getInstance().getPlayerX();
            int bulletY = GamePanel.getInstance().getPlayerY();

            ArrayList<Bullet> updatedBullet = GamePanel.getInstance().getBullets();
            updatedBullet.add(new Bullet(bulletX, bulletY, GamePanel.getInstance().getPlayerDirection()));
//        GamePanel.getInstance().setBullets(GamePanel.getInstance().getBullets().add(new Bullet(bulletX, bulletY, GamePanel.getInstance().getPlayerDirection()))));
            GamePanel.getInstance().setBullets(updatedBullet);
//        mediaPlayer.setVolume(0.025);
//        mediaPlayer.play();
//        GamePanel.getInstance().getGunshotSound().setVolume(0.25);
//        GamePanel.getInstance().getGunshotSound().play();
            Media buzzerGunshot = new Media(new File("res/sound/shoot.mp3").toURI().toString());
            MediaPlayer gunshot = new MediaPlayer(buzzerGunshot);
            gunshot.setVolume(0.2);
            gunshot.play();

        }
    }

    public void updateBulletGhostCollisions() {
        final int blockSize = GamePanel.getInstance().getBlockSize();
        Iterator<Bullet> bulletIterator = GamePanel.getInstance().getBullets().iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Ghost> ghostIterator = GhostSpawner.getGhosts().iterator();
            while (ghostIterator.hasNext()) {
                Ghost ghost = ghostIterator.next();
                // Check if the coordinates of the bullet intersect with the coordinates of the ghost
//                System.out.println("BL " + bullet.getX() + ',' + bullet.getY());
//                System.out.println("GH " + ghost.getX() * blockSize + ',' + ghost.getY() * blockSize);
                if (ghost instanceof BossGhost) {
                    int ghostX = ghost.getX() * blockSize;
                    int ghostY = ghost.getY() * blockSize;
                    int ghostWidth = 2 * blockSize; // BossGhost has 2x2 hitbox
                    int ghostHeight = 2 * blockSize;

                    if (bullet.getY() >= ghostX && bullet.getY() < ghostX + ghostWidth &&
                        bullet.getX() >= ghostY && bullet.getX() < ghostY + ghostHeight) {
                        // Collision detected with BossGhost
//                        MediaPlayer hurtSound = new MediaPlayer(hurt);
//                        hurtSound.setVolume(0.33);
//                        hurtSound.play();
                        GamePanel.getInstance().getSoundLoader().playEnemyHitSound();

                        // Remove the bullet upon collision
                        bulletIterator.remove();

                        // Decrease BossGhost hp
                        ghost.setHp(ghost.getHp() - 1);
                        if (ghost.getHp() == 0) {
                            ghostIterator.remove();
                            // Boss dead
                            GamePanel.getInstance().setHasWon(true);
                        }
                        break;
                    }
                } else {

                    if (bullet.getY() >= ghost.getX() * blockSize - blockSize * 0.25 &&
                        bullet.getY() <= ghost.getX() * blockSize + blockSize * 0.25 &&
                        bullet.getX() >= ghost.getY() * blockSize - blockSize * 0.25 &&
                        bullet.getX() <= ghost.getY() * blockSize + blockSize * 0.25    ) {

                        GamePanel.getInstance().getSoundLoader().playEnemyHitSound();

                        // Remove the bullet and ghost upon collision
                        if (ghost.getHp() == 1) {
                            bulletIterator.remove();
                            ghostIterator.remove();

                            if (ghost instanceof NormalGhost) {
                                GamePanel.getInstance().setCurrentPoint(GamePanel.getInstance().getCurrentPoint() + 10);
                            } else if (ghost instanceof SpeedyGhost) {
                                GamePanel.getInstance().setCurrentPoint(GamePanel.getInstance().getCurrentPoint() + 15);
                            } else if (ghost instanceof TankGhost) {
                                GamePanel.getInstance().setCurrentPoint(GamePanel.getInstance().getCurrentPoint() + 20);
                            }

    //                    this.pointLabel.setText(String.format("Point : %d", this.getCurrentPoint()));

                            System.out.println("Current Point : " + GamePanel.getInstance().getCurrentPoint());

                        } else {
                            ghost.setHp(ghost.getHp() - 1);
                            bulletIterator.remove();
                        }

                        break;
                    }
                }
            }
        }

    }
}
