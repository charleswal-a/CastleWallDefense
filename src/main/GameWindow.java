package main;

import javax.swing.JFrame;

public class GameWindow {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setTitle("Castle Wall Defense");

        GamePanel gamePanel = new GamePanel();
        gameFrame.add(gamePanel);
        gameFrame.pack();

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        gamePanel.startGameThread();

    }
}
