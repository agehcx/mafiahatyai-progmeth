package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed,downPressed,leftPressed,rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_W){
            upPressed=true;
        }
        if(code==KeyEvent.VK_S){
            downPressed=true;
        }
        if(code==KeyEvent.VK_A){
            leftPressed=true;
        }
        if(code==KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_W){
            upPressed=false;
            System.out.println("W pressed");
        }
        if(code==KeyEvent.VK_S){
            downPressed=false;
            System.out.println("herereee");
        }
        if(code==KeyEvent.VK_A){
            leftPressed=false;
            System.out.println("A pressed");
        }
        if(code==KeyEvent.VK_D) {
            rightPressed = false;
            System.out.println("D pressed");
        }
    }
}
