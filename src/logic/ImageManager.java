package logic;

import javafx.scene.image.Image;
import main.GamePanel;

public class ImageManager {
    private static final int blockSize = 40;
    private Image characterUp;
    private Image characterDown;
    private Image characterLeft;
    private Image characterRight;
    private Image currentCharacterImage = characterRight;
    private Image wall;
    private Image footPath;
    private Image bulletRight;
    private Image bulletUp;
    private Image bulletLeft;
    private Image bulletDown;
    private Image redGhost;
    private Image whiteDot;
    private Image normalGhost;
    private Image tankGhost;
    private Image speedyGhost;
    private Image slime;
    private Image chest;
    private Image house;
    private Image bush;
    private Image sword;
    private Image crab;
    private Image flyeye;
    private Image bat;
    private Image heart;


    public ImageManager() {
        characterUp = new Image("file:res/character/manUp.png", blockSize*1.2, blockSize*1.2, true, true);
        characterDown = new Image("file:res/character/manDown.png", blockSize*1.2, blockSize*1.2, true, true);
        characterLeft = new Image("file:res/character/manLeft.png", blockSize*1.2, blockSize*1.2, true, true);
        characterRight = new Image("file:res/character/manRight.png", blockSize*1.2, blockSize*1.2, true, true);
        currentCharacterImage = characterRight;
        wall = new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true);
        footPath = new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true);
        bulletRight = new Image("file:res/gif/bulletRight.gif", blockSize, blockSize, true, true);
        bulletUp = new Image("file:res/gif/bulletUp.gif", blockSize, blockSize, false, true);
        bulletLeft = new Image("file:res/gif/bulletLeft.gif", blockSize, blockSize, false, true);
        bulletDown = new Image("file:res/gif/bulletDown.gif", blockSize, blockSize, false, true);
        redGhost = new Image("file:res/gif/redghost.gif", blockSize, blockSize, true, true);

        whiteDot = new Image("file:res/gif/whitedot.png", blockSize, blockSize, true, true);
        normalGhost = new Image("file:res/gif/redghost.gif", blockSize, blockSize, true, true);
        tankGhost = new Image("file:res/gif/blueghost.gif", blockSize, blockSize, true, true);
        speedyGhost = new Image("file:res/gif/ghost2.gif", blockSize, blockSize, true, true);
        slime = new Image("file:res/gif/slime.gif", blockSize * 2.5, blockSize * 2.5, true, true);
        chest = new Image("file:res/gif/chest.gif", blockSize, blockSize, true,true);
        house = new Image("file:res/gif/house.png", blockSize * 2, blockSize * 2, true,true);
        bush = new Image("file:res/gif/tree2.png", blockSize , blockSize, true,true);
        sword = new Image("file:res/gif/sword.gif", blockSize * 2, blockSize * 2, true,true);
        crab = new Image("file:res/gif/crab2.gif", blockSize, blockSize, true,true);
        flyeye = new Image("file:res/gif/fly-eye2.gif", blockSize, blockSize, true,true);
        bat = new Image("file:res/gif/bat2.gif", blockSize, blockSize, true,true);
        heart = new Image("file:res/gif/heart.png", blockSize, blockSize * 0.75, true,true);
    }

    public Image getCharacterUp() {
        return characterUp;
    }

    public Image getCharacterDown() {
        return characterDown;
    }

    public Image getCharacterLeft() {
        return characterLeft;
    }

    public Image getCharacterRight() {
        return characterRight;
    }

    public Image getCurrentCharacterImage() {
        return currentCharacterImage;
    }

    public Image getWall() {
        return wall;
    }

    public Image getFootPath() {
        return footPath;
    }

    public Image getBulletRight() {
        return bulletRight;
    }

    public Image getBulletUp() {
        return bulletUp;
    }

    public Image getBulletLeft() {
        return bulletLeft;
    }

    public Image getBulletDown() {
        return bulletDown;
    }

    public Image getRedGhost() {
        return redGhost;
    }

    public Image getWhiteDot() {
        return whiteDot;
    }

    public Image getNormalGhost() {
        return normalGhost;
    }

    public Image getTankGhost() {
        return tankGhost;
    }

    public Image getSpeedyGhost() {
        return speedyGhost;
    }

    public Image getSlime() {
        return slime;
    }

    public Image getChest() {
        return chest;
    }

    public Image getHouse() {
        return house;
    }

    public Image getBush() {
        return bush;
    }

    public Image getSword() {
        return sword;
    }

    public Image getCrab() {
        return crab;
    }

    public Image getFlyeye() {
        return flyeye;
    }

    public Image getBat() {
        return bat;
    }

    public Image getHeart() {
        return heart;
    }

    public void setCharacterUp(Image characterUp) {
        this.characterUp = characterUp;
    }

    public void setCharacterDown(Image characterDown) {
        this.characterDown = characterDown;
    }

    public void setCharacterLeft(Image characterLeft) {
        this.characterLeft = characterLeft;
    }

    public void setCharacterRight(Image characterRight) {
        this.characterRight = characterRight;
    }

    public void setCurrentCharacterImage(Image currentCharacterImage) {
        this.currentCharacterImage = currentCharacterImage;
    }

    public void setWall(Image wall) {
        this.wall = wall;
    }

    public void setFootPath(Image footPath) {
        this.footPath = footPath;
    }
}
