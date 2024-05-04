package logic;

import component.RetroFont;
import ghost.Ghost;
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
    final Image redGhost = new Image("file:res/gif/redghost.gif", blockSize, blockSize, true, true);
    final Image chest = new Image("file:res/gif/chest.gif", blockSize, blockSize, true,true);
    final Image house = new Image("file:res/gif/house.png", blockSize * 2, blockSize * 2, true,true);

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

        for (Ghost ghost : GhostSpawner.getGhosts()) {
            if (GamePanel.getInstance().getPlayerX() == ghost.getY() * blockSize && GamePanel.getInstance().getPlayerY() == ghost.getX() * blockSize) {
                // Player collides with ghost, set current score to 0
                GamePanel.getInstance().setCurrentPoint(0);
                System.out.println("Dead...");
                System.out.println("Has game end starting ? " + GamePanel.getInstance().isHasGameEnded());


                GamePanel.getInstance().setPlayerX(blockSize);
                GamePanel.getInstance().setPlayerY(blockSize);

                GamePanel.getInstance().setMapPattern(level1.getMapPattern());

//                GamePanel.getInstance().resetMapToLevel1();

                GhostSpawner.setGhosts(new ArrayList<>());

                GamePanel.getInstance().setHasGameEnded(true);

                System.out.println("IS GAME ENDED ? " + GamePanel.getInstance().isHasGameEnded());

//                GamePanel.getInstance().resetInstance();

//                GameInstance gi = new GameInstance();
//                gi.resetGameInstance();

                System.out.println("GAME ENDED !!");

                // You might want to add additional game over logic here
            }
        }

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
            if (ghost instanceof Ghost) {
                gc.drawImage(redGhost, ghost.getY() * blockSize, ghost.getX() * blockSize);
            }
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

//        if ()
        gc.setFill(Color.WHITE);
        gc.setFont(new RetroFont("Arial", 20).getFont());
        gc.fillText("Score : " + GamePanel.getInstance().getCurrentPoint(), 20, screenHeight - 15);
        gc.setFont(new RetroFont("Arial", 20).getFont());
        gc.fillText("Level : " + GamePanel.getInstance().getCurrentLevel(), screenWidth - 125, screenHeight - 15);
//        gc.fillText("GameEnd : " + GamePanel.getInstance().isHasGameEnded(), screenWidth - 500, screenHeight - 15);

        gc.drawImage(GamePanel.getInstance().getImageManager().getCurrentCharacterImage(), GamePanel.getInstance().getPlayerX() + 0.05 * blockSize, GamePanel.getInstance().getPlayerY() - 0.2 * blockSize);
        GamePanel.getInstance().getChildren().setAll(canvas);
    }
}
