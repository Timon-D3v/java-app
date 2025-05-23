package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        final JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // Adjust the window size to fit the game panel

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread(); // Start the game loop

    }
}