package logic;

import component.RetroFont;
import ghost.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.GamePanel;
import map.level1;
import object.Bullet;

import java.util.ArrayList;

public class Painter {
    private int blockSize = 40;
    private Image characterUp = new Image("file:res/character/manUp.png", blockSize*1.2, blockSize*1.2, true, true);
    private Image characterDown = new Image("file:res/character/manDown.png", blockSize*1.2, blockSize*1.2, true, true);
    private Image characterLeft = new Image("file:res/character/manLeft.png", blockSize*1.2, blockSize*1.2, true, true);
    private Image characterRight = new Image("file:res/character/manRight.png", blockSize*1.2, blockSize*1.2, true, true);
    //    private Image currentCharacterImage = characterRight;
    private Image wall = new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true);
    private Image footPath = new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true);
    final Image whiteDot = new Image("file:res/gif/whitedot.png", blockSize, blockSize, true, true);
    final Image bulletRight = new Image("file:res/gif/bulletRight.gif", blockSize, blockSize, true, true);
    final Image bulletUp = new Image("file:res/gif/bulletUp.gif", blockSize, blockSize, false, true);
    final Image bulletLeft = new Image("file:res/gif/bulletLeft.gif", blockSize, blockSize, false, true);
    final Image bulletDown = new Image("file:res/gif/bulletDown.gif", blockSize, blockSize, false, true);
    final Image normalGhost = new Image("file:res/gif/redghost.gif", blockSize, blockSize, true, true);
    final Image tankGhost = new Image("file:res/gif/blueghost.gif", blockSize, blockSize, true, true);
    final Image speedyGhost = new Image("file:res/gif/ghost2.gif", blockSize, blockSize, true, true);
    final Image slime = new Image("file:res/gif/slime.gif", blockSize * 2.5, blockSize * 2.5, true, true);
    final Image chest = new Image("file:res/gif/chest.gif", blockSize, blockSize, true,true);
    final Image house = new Image("file:res/gif/house.png", blockSize * 2, blockSize * 2, true,true);
    final Image sword = new Image("file:res/gif/sword.gif", blockSize * 2, blockSize * 2, true,true);
    final Image crab = new Image("file:res/gif/crab2.gif", blockSize, blockSize, true,true);
    final Image flyeye = new Image("file:res/gif/fly-eye2.gif", blockSize, blockSize, true,true);
    final Image bat = new Image("file:res/gif/bat2.gif", blockSize, blockSize, true,true);
    final Image heart = new Image("file:res/gif/heart.png", blockSize, blockSize * 0.75, true,true);

    public Painter() {};

    public void repaint() {
        int blockSize = GamePanel.getInstance().getBlockSize();
        int screenWidth = GamePanel.getInstance().getScreenWidth();
        int screenHeight = GamePanel.getInstance().getScreenHeight();
        int screenHeightBlocks = GamePanel.getInstance().getScreenHeightBlocks();
        int screenWidthBlocks = GamePanel.getInstance().getScreenWidthBlocks();

        Image wall = GamePanel.getInstance().getImageManager().getWall();
        Image footPath = GamePanel.getInstance().getImageManager().getFootPath();

        Canvas canvas = new Canvas(screenWidth, screenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, screenWidth, screenHeight);

        gc.setFill(Color.web("#FF93AC"));
        gc.fillRect(0, 0, screenWidth, screenHeight);

        ArrayList<Pair<Integer, Integer>> toRenderList = new ArrayList<>();

        // Draw map
        for (int i = 0; i < screenHeightBlocks; i++) {
            for (int j = 0; j < screenWidthBlocks; j++) {
                if (GamePanel.getInstance().getMapPattern()[i][j] == 'X') {
                    gc.setFill(Color.GREEN);
//                    gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
//                    gc.drawImage(bullet, j * blockSize, i * blockSize);
                    gc.drawImage(wall, j * blockSize, i * blockSize);
                } else if (GamePanel.getInstance().getMapPattern()[i][j] == 'O') {
                    gc.setFill(Color.LIGHTGRAY);
                    gc.drawImage(footPath, j * blockSize, i * blockSize);
                    //gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                } else if (GamePanel.getInstance().getMapPattern()[i][j] == 'C') {
                    gc.drawImage(footPath, j * blockSize, i * blockSize);
                    gc.setFill(Color.GREEN);
                    gc.drawImage(chest, j * blockSize, i * blockSize);
                } else if (GamePanel.getInstance().getMapPattern()[i][j] == 'H') {
//                    gc.drawImage(footPath, j * blockSize, i * blockSize);
//                    gc.setFill(Color.GREEN);
//                    gc.drawImage(house, j * blockSize, i * blockSize);
                    toRenderList.add(new Pair<>(i, j));
                }
            }
        }

        for (Pair<Integer, Integer> position : toRenderList) {
            // Re render-house for larger size and front z-index
            int i = position.getKey();
            int j = position.getValue();
            gc.drawImage(footPath, j * blockSize, i * blockSize);
            gc.setFill(Color.GREEN);
            gc.drawImage(house, j * blockSize - 0.5 * blockSize , i * blockSize - blockSize );
            // Offset
        }

        // Update ghost
        for (Ghost ghost : GhostSpawner.getGhosts()) {
            if (ghost instanceof NormalGhost) {
                gc.drawImage(crab, ghost.getY() * blockSize, ghost.getX() * blockSize);
            } else if (ghost instanceof SpeedyGhost) {
                gc.drawImage(flyeye, ghost.getY() * blockSize, ghost.getX() * blockSize);
            } else if (ghost instanceof TankGhost) {
                gc.drawImage(bat, ghost.getY() * blockSize, ghost.getX() * blockSize);
            } else if (ghost instanceof BossGhost) {
                gc.drawImage(slime, ghost.getY() * blockSize - 0.5 * blockSize , ghost.getX() * blockSize - 0.5 * blockSize);;
                int bossHP = ghost.getHp();
                int maxHP = 15;
                double bossX = ghost.getX() * blockSize;
                double bossY = ghost.getY() * blockSize;
                // Render red HP bar
                double barWidth = blockSize * 2; // Adjust as needed
                double barHeight = blockSize / 3; // Adjust as needed
                double barX = bossX - (blockSize / 4);
                double barY = bossY - 0.5 * blockSize; // Adjust for the position above boss
                // Calculate HP ratio for the bar length
                double hpRatio = (double) bossHP / maxHP;
                double filledWidth = barWidth * hpRatio;

                gc.setFill(Color.RED);
                gc.fillRect(barY, barX, barWidth, barHeight);

                // Render filled portion in green
                gc.setFill(Color.GREEN);
                gc.fillRect(barY, barX, filledWidth, barHeight);

                // Render HP text
                gc.setFill(Color.WHITE);
                gc.setFont(new RetroFont("Arial", 20).getFont()); // Adjust font size as needed
                gc.fillText("Boss HP: " + bossHP + "/" + maxHP, bossY - (blockSize / 2), bossX - 0.5  * blockSize);            }
        }

        // Update bullet
        for (Bullet bullet : GamePanel.getInstance().getBullets()) {
            switch (bullet.getDirection()) {
                case UP -> gc.drawImage(bulletUp, bullet.getX(), bullet.getY());
                case DOWN -> gc.drawImage(bulletDown, bullet.getX(), bullet.getY());
                case LEFT -> gc.drawImage(bulletLeft, bullet.getX(), bullet.getY());
                default -> gc.drawImage(bulletRight, bullet.getX(), bullet.getY());
            };
        }

        gc.setFill(Color.WHITE);
        gc.setFont(new RetroFont("Arial", 20).getFont());
        gc.fillText("Score : " + GamePanel.getInstance().getCurrentPoint(), 20, screenHeight - 15);
        gc.setFont(new RetroFont("Arial", 20).getFont());
        gc.fillText("Level : " + GamePanel.getInstance().getCurrentLevel(), screenWidth - 125, screenHeight - 15);
//        gc.fillText("GameEnd : " + GamePanel.getInstance().isHasGameEnded(), screenWidth - 500, screenHeight - 15);

        gc.drawImage(GamePanel.getInstance().getImageManager().getCurrentCharacterImage(), GamePanel.getInstance().getPlayerX() + 0.05 * blockSize, GamePanel.getInstance().getPlayerY() - 0.2 * blockSize);

        int hp = GamePanel.getInstance().getPlayer().getPlayerHp();
        double heartX = screenWidth - (hp * (heart.getWidth() + 5)); // Adjust 5 for spacing between hearts
        double heartY = 5; // Adjust as needed for vertical position

        // Render hearts
        for (int i = 0; i < hp; i++) {
            gc.drawImage(heart, heartX + (i * (heart.getWidth() + 5)), heartY);
        }


        // Boss spinning blade section


//        gc.drawImage(whiteDot, GhostSpawner.bladeY * blockSize, GhostSpawner.bladeX * blockSize);
//        System.out.println("Painter : " + GhostSpawner.bladeY + "," + GhostSpawner.bladeX);

        gc.save(); // Save the current state
        gc.translate(GhostSpawner.bladeY * blockSize , GhostSpawner.bladeX * blockSize); // Translate to blade position
        GamePanel.getInstance().getBossLogic().swordHitPlayer();
//        System.out.println("Repaint Blade : " + GhostSpawner.bladeX + "," + GhostSpawner.bladeY);
        gc.drawImage(sword, -0.5 * blockSize,  0); // Render the blade
        gc.restore(); // Restore the previous state


        GamePanel.getInstance().getChildren().setAll(canvas);

    }
}
