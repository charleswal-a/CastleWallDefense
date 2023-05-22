package main;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setTitle("Game");

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setBounds(0,0,500,500);

        JPanel gamePanel = new GamePanel();
        gameFrame.add(gamePanel);
        gameFrame.pack();
    }
}
