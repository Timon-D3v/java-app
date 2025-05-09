package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // Scale the tile size by 3x

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16; // 16 tiles across the screen
    final int maxScreenRow = 12; // 12 tiles down the screen
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels wide
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels tall

    // FPS (Frames Per Second) settings
    int FPS = 240;
    int currentFPS = 0;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread; // Thread for the game loop

    // Set players default position
    int playerX = 100;
    int playerY = 100;
    final int playerSpeed = 1; // (BASE_FPS (60) / FPS (240)) * 4

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // Enable double buffering for smoother graphics
        this.addKeyListener(keyHandler); // Add the key listener for keyboard input
        this.setFocusable(true); // Make the panel focusable to receive key events

    }

    public void startGameThread() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the thread, which will call the run() method
    }

    @Override
    public void run() {
        
        double targetTime = 1e9d; // 1 second in nanoseconds
        double drawInterval = targetTime / FPS; // Time between frames in nanoseconds 
        double delta = 0d; // Time accumulated since the last frame
        long lastTime = System.nanoTime(); // Get the current time in nanoseconds
        long currentTime; // Variable to store the current time

        Deque<Long> frameTimestampsDeque = new ArrayDeque<>();

        while (gameThread != null) {

            currentTime = System.nanoTime(); // Get the current time in nanoseconds

            delta += (currentTime - lastTime) / drawInterval; // Calculate the delta time

            lastTime = currentTime; // Update the last time

            if (delta >= 1) {

                // 1. UPDATE: Update information such as character position, game state, etc.
                update(); // Call the update method to update the game state

                // 2. DRAW: Draw the screen with the updated information
                repaint(); // Call the repaint method to redraw the screen

                delta--; // Decrease delta by 1 to indicate that a frame has been drawn

                // 3. FPS: Calculate the current FPS
                long now = System.currentTimeMillis();
                frameTimestampsDeque.addLast(now);

                // Remove timestamps older than 1000ms
                while (!frameTimestampsDeque.isEmpty() && frameTimestampsDeque.peekFirst() <= now - 1000) {

                    frameTimestampsDeque.removeFirst();

                }
                
                currentFPS = frameTimestampsDeque.size(); // Set the current FPS to the size of the deque

            }

        }

    }

    public void update() {

        if (keyHandler.upPressed) {
            playerY -= playerSpeed; // Move the player up
        }

        if (keyHandler.downPressed) {
            playerY += playerSpeed; // Move the player down
        }
        
        if (keyHandler.leftPressed) {
            playerX -= playerSpeed; // Move the player left
        }
        
        if (keyHandler.rightPressed) {
            playerX += playerSpeed; // Move the player right
        }

    }

    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        final Graphics2D graphics2d = (Graphics2D) graphics; // Cast to Graphics2D for advanced graphics

        // Draw the player as a white square
        graphics2d.setColor(Color.white); // Set the color to white
        graphics2d.fillRect(playerX, playerY, tileSize, tileSize);

        // Display the FPS
        graphics2d.setColor(Color.green); // Set text color
        graphics2d.setFont(new Font("Arial", Font.BOLD, 16)); // Set text font
        graphics2d.drawString("FPS: " + currentFPS, 10, 20); // Draw text at x=10, y=20

        graphics2d.dispose(); // Dispose of the graphics context to free up resources

    }

}