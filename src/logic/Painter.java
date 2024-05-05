package logic;

import component.RetroFont;
import ghost.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;
import main.GamePanel;
import map.level1;
import object.Bullet;

import java.util.ArrayList;

public class Painter {
    private int blockSize = 40;

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
                    gc.drawImage(GamePanel.getInstance().getImageManager().getWall(), j * blockSize, i * blockSize);
                } else if (GamePanel.getInstance().getMapPattern()[i][j] == 'O') {
                    gc.setFill(Color.LIGHTGRAY);
                    gc.drawImage(GamePanel.getInstance().getImageManager().getFootPath(), j * blockSize, i * blockSize);
                    //gc.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                } else if (GamePanel.getInstance().getMapPattern()[i][j] == 'C') {
                    gc.drawImage(GamePanel.getInstance().getImageManager().getFootPath(), j * blockSize, i * blockSize);
                    gc.setFill(Color.GREEN);
                    gc.drawImage(GamePanel.getInstance().getImageManager().getChest(), j * blockSize, i * blockSize);
                } else if (GamePanel.getInstance().getMapPattern()[i][j] == 'H') {
//                    gc.drawImage(footPath, j * blockSize, i * blockSize);
//                    gc.setFill(Color.GREEN);
//                    gc.drawImage(house, j * blockSize, i * blockSize);
                    toRenderList.add(new Pair<>(i, j));
                } else if (GamePanel.getInstance().getMapPattern()[i][j] == 'B') {
                    gc.drawImage(GamePanel.getInstance().getImageManager().getFootPath(), j * blockSize, i * blockSize);
                    gc.drawImage(GamePanel.getInstance().getImageManager().getBush(), j * blockSize, i * blockSize);
                }
            }
        }

        for (Pair<Integer, Integer> position : toRenderList) {
            // Re render-house for larger size and front z-index
            int i = position.getKey();
            int j = position.getValue();
            gc.drawImage(footPath, j * blockSize, i * blockSize);
            gc.setFill(Color.GREEN);
            gc.drawImage(GamePanel.getInstance().getImageManager().getHouse(), j * blockSize - 0.5 * blockSize , i * blockSize - blockSize );
            // Offset
        }

        // Update ghost
        for (Ghost ghost : GhostSpawner.getGhosts()) {
            if (ghost instanceof NormalGhost) {
                gc.drawImage(GamePanel.getInstance().getImageManager().getCrab(), ghost.getY() * blockSize, ghost.getX() * blockSize);
            } else if (ghost instanceof SpeedyGhost) {
                gc.drawImage(GamePanel.getInstance().getImageManager().getFlyeye(), ghost.getY() * blockSize, ghost.getX() * blockSize);
            } else if (ghost instanceof TankGhost) {
                gc.drawImage(GamePanel.getInstance().getImageManager().getBat(), ghost.getY() * blockSize, ghost.getX() * blockSize);
            } else if (ghost instanceof BossGhost) {
                gc.drawImage(GamePanel.getInstance().getImageManager().getSlime(), ghost.getY() * blockSize - 0.5 * blockSize , ghost.getX() * blockSize - 0.5 * blockSize);;
                int bossHP = ghost.getHp();
                int maxHP = 20;
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
                case UP -> gc.drawImage(GamePanel.getInstance().getImageManager().getBulletUp(), bullet.getX(), bullet.getY());
                case DOWN -> gc.drawImage(GamePanel.getInstance().getImageManager().getBulletDown(), bullet.getX(), bullet.getY());
                case LEFT -> gc.drawImage(GamePanel.getInstance().getImageManager().getBulletLeft(), bullet.getX(), bullet.getY());
                default -> gc.drawImage(GamePanel.getInstance().getImageManager().getBulletRight(), bullet.getX(), bullet.getY());
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
        double heartX = screenWidth - (hp * (GamePanel.getInstance().getImageManager().getHeart().getWidth() + 5)); // Adjust 5 for spacing between hearts
        double heartY = 5; // Adjust as needed for vertical position

        // Render hearts
        for (int i = 0; i < hp; i++) {
            gc.drawImage(GamePanel.getInstance().getImageManager().getHeart(), heartX + (i * (GamePanel.getInstance().getImageManager().getHeart().getWidth() + 5)), heartY);
        }


        // Boss spinning blade section


//        gc.drawImage(whiteDot, GhostSpawner.bladeY * blockSize, GhostSpawner.bladeX * blockSize);
//        System.out.println("Painter : " + GhostSpawner.bladeY + "," + GhostSpawner.bladeX);

        if (GamePanel.getInstance().getCurrentLevel() == 5) {
            gc.save(); // Save the current state
            gc.translate(GhostSpawner.bladeY * blockSize , GhostSpawner.bladeX * blockSize); // Translate to blade position
            GamePanel.getInstance().getBossLogic().swordHitPlayer();
//        System.out.println("Repaint Blade : " + GhostSpawner.bladeX + "," + GhostSpawner.bladeY);
            gc.drawImage(GamePanel.getInstance().getImageManager().getSword(), -0.5 * blockSize,  0); // Render the blade
            gc.restore(); // Restore the previous state
        }


        GamePanel.getInstance().getChildren().setAll(canvas);

        if (GamePanel.getInstance().isHasWon()) {
            displayGameWonScreen(gc);
            return;
        } else if (GamePanel.getInstance().isHasGameEnded()) {
            displayGameOverScreen(gc);
            return;
        }
    }

    private void displayGameOverScreen(GraphicsContext gc) {
        int screenWidth = GamePanel.getInstance().getScreenWidth();
        int screenHeight = GamePanel.getInstance().getScreenHeight();
        // Clear canvas
        gc.clearRect(0, 0, screenWidth, screenHeight);

        // Fill with black color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        gc.setFill(Color.WHITE);
        Font font = new RetroFont("Arial", 40).getFont();
        gc.setFont(font); // Adjust font size as needed

        // Create a temporary Text object to calculate text width
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(font);
        double gameOverTextWidth = gameOverText.getLayoutBounds().getWidth();
        double gameOverTextHeight = font.getSize();
        double gameOverTextX = (screenWidth - gameOverTextWidth) / 2;
        double gameOverTextY = (screenHeight - gameOverTextHeight) / 2;

        // Draw "GAME OVER" text
        gc.fillText("GAME OVER", gameOverTextX, gameOverTextY);

        Text tryAgain = new Text("Press O to try again.");
        tryAgain.setFont(font);
        double tryAgainWidth = tryAgain.getLayoutBounds().getWidth();
        double tryAgainX = (screenWidth - tryAgainWidth) / 2;
        double tryAgainY = gameOverTextY + gameOverTextHeight + 35; // Adjust for spacing
        gc.fillText("Press O to try again", tryAgainX, tryAgainY);
    }

    private void displayGameWonScreen(GraphicsContext gc) {
        int screenWidth = GamePanel.getInstance().getScreenWidth();
        int screenHeight = GamePanel.getInstance().getScreenHeight();
        // Clear canvas
        gc.clearRect(0, 0, screenWidth, screenHeight);

        // Fill with black color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // Load "You Won" image
        Image wonImage = new Image("file:res/img/Won.jpg");
        ImageView imageView = new ImageView(wonImage);
        imageView.setX((screenWidth - wonImage.getWidth()) / 2);
        imageView.setY((screenHeight - wonImage.getHeight()) / 2);

        // Add image to the canvas
        gc.drawImage(wonImage, imageView.getX(), imageView.getY());
    }
}
