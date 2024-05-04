package logic;

import ghost.Ghost;
import ghost.SpeedyGhost;
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
            bullet.move(GamePanel.getInstance().getBlockSize());
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
        gunshot.setVolume(0.05);
        gunshot.play();
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
                if (    bullet.getY() >= ghost.getX() * blockSize - blockSize * 0.25 &&
                        bullet.getY() <= ghost.getX() * blockSize + blockSize * 1.25 &&
                        bullet.getX() >= ghost.getY() * blockSize - blockSize * 0.25 &&
                        bullet.getX() <= ghost.getY() * blockSize + blockSize * 1.25    ) {

                    // Remove the bullet and ghost upon collision
                    bulletIterator.remove();
                    ghostIterator.remove();

                    if (ghost instanceof Ghost) {
                        GamePanel.getInstance().setCurrentPoint(GamePanel.getInstance().getCurrentPoint() + 10);
                    } else if (ghost instanceof SpeedyGhost) {
                        GamePanel.getInstance().setCurrentPoint(GamePanel.getInstance().getCurrentPoint() + 20);
//                        setCurrentPoint(getCurrentPoint() + 20);
                    }

//                    this.pointLabel.setText(String.format("Point : %d", this.getCurrentPoint()));

                    System.out.println("Current Point : " + GamePanel.getInstance().getCurrentPoint());
                    break;
                }
            }
        }

    }
}
