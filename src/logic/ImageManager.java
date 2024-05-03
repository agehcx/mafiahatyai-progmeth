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
    private Image whiteDot;
    private Image bulletRight;
    private Image bulletUp;
    private Image bulletLeft;
    private Image bulletDown;
    private Image redGhost;

    public ImageManager() {
        characterUp = new Image("file:res/character/manUp.png", blockSize*1.2, blockSize*1.2, true, true);
        characterDown = new Image("file:res/character/manDown.png", blockSize*1.2, blockSize*1.2, true, true);
        characterLeft = new Image("file:res/character/manLeft.png", blockSize*1.2, blockSize*1.2, true, true);
        characterRight = new Image("file:res/character/manRight.png", blockSize*1.2, blockSize*1.2, true, true);
        currentCharacterImage = characterRight;
        wall = new Image("file:res/gif/grass.jpg", blockSize, blockSize, true, true);
        footPath = new Image("file:res/gif/rock.jpg", blockSize, blockSize, true, true);
        whiteDot = new Image("file:res/gif/whitedot.png", blockSize, blockSize, true, true);
        bulletRight = new Image("file:res/gif/bulletRight.gif", blockSize, blockSize, true, true);
        bulletUp = new Image("file:res/gif/bulletUp.gif", blockSize, blockSize, false, true);
        bulletLeft = new Image("file:res/gif/bulletLeft.gif", blockSize, blockSize, false, true);
        bulletDown = new Image("file:res/gif/bulletDown.gif", blockSize, blockSize, false, true);
        redGhost = new Image("file:res/gif/redghost.gif", blockSize, blockSize, true, true);
    }

    public Image getCurrentCharacterImage() {
        return currentCharacterImage;
    }

    public void setCurrentCharacterImage(Image currentCharacterImage) {
        this.currentCharacterImage = currentCharacterImage;
    }

    public Image getCharacterUp() {
        return characterUp;
    }

    public void setCharacterUp(Image characterUp) {
        this.characterUp = characterUp;
    }

    public Image getCharacterDown() {
        return characterDown;
    }

    public void setCharacterDown(Image characterDown) {
        this.characterDown = characterDown;
    }

    public Image getCharacterLeft() {
        return characterLeft;
    }

    public void setCharacterLeft(Image characterLeft) {
        this.characterLeft = characterLeft;
    }

    public Image getCharacterRight() {
        return characterRight;
    }

    public void setCharacterRight(Image characterRight) {
        this.characterRight = characterRight;
    }

    public Image getWall() {
        return wall;
    }

    public void setWall(Image wall) {
        this.wall = wall;
    }

    public Image getFootPath() {
        return footPath;
    }

    public void setFootPath(Image footPath) {
        this.footPath = footPath;
    }

    public Image getWhiteDot() {
        return whiteDot;
    }

    public void setWhiteDot(Image whiteDot) {
        this.whiteDot = whiteDot;
    }

    public Image getBulletRight() {
        return bulletRight;
    }

    public void setBulletRight(Image bulletRight) {
        this.bulletRight = bulletRight;
    }

    public Image getBulletUp() {
        return bulletUp;
    }

    public void setBulletUp(Image bulletUp) {
        this.bulletUp = bulletUp;
    }

    public Image getBulletLeft() {
        return bulletLeft;
    }

    public void setBulletLeft(Image bulletLeft) {
        this.bulletLeft = bulletLeft;
    }

    public Image getBulletDown() {
        return bulletDown;
    }

    public void setBulletDown(Image bulletDown) {
        this.bulletDown = bulletDown;
    }

    public Image getRedGhost() {
        return redGhost;
    }

    public void setRedGhost(Image redGhost) {
        this.redGhost = redGhost;
    }
}
