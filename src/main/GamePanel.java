package main;

import entity.Player;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int height = 500;
    final int width = 500;
    InputListener inputL;
    Player p;
    Thread gameThread;
    int fps = 60;


    int x = 100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true);
        this.setBackground(Color.black);

        inputL = new InputListener();
        this.addKeyListener(inputL);
        this.setFocusable(true);

        p = new Player(this, inputL);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startGameThread() {

    }


    public void update() {
        if(inputL.isUpPressed()) {
            x -= 10;
        }
        else if(inputL.isDownPressed()) {
            x += 10;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(Color.white);
        graphics2D.fillRect(x, 100, 10,10);
        graphics2D.dispose();
    }

    @Override
    public void run() {
        double drawInterval = 100000000/fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
