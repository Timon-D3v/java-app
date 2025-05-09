package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;

    @Override
    public void keyTyped(KeyEvent event) {
        // Unused
    }

    @Override
    public void keyPressed(KeyEvent event) {
        
        int code = event.getKeyCode(); // Get the key code of the pressed key

        switch (code) {

            case KeyEvent.VK_W -> upPressed = true; // Set upPressed to true if 'W' is pressed

            case KeyEvent.VK_S -> downPressed = true;

            case KeyEvent.VK_A -> leftPressed = true;

            case KeyEvent.VK_D -> rightPressed = true;

            default -> {
                // Do nothing for other keys
            }

        }

    }

    @Override
    public void keyReleased(KeyEvent event) {
        
        int code = event.getKeyCode(); // Get the key code of the pressed key

        switch (code) {
            
            case KeyEvent.VK_W -> upPressed = false; // Set upPressed to false if 'W' is released

            case KeyEvent.VK_S -> downPressed = false;

            case KeyEvent.VK_A -> leftPressed = false;

            case KeyEvent.VK_D -> rightPressed = false;

            default -> {
                // Do nothing for other keys
            }

        }

    }

}
