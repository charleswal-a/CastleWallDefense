package main;

import entity.Arrow;
import entity.Player;
import entity.Enemy;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 16;
    private final int scale = 9;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 8;
    private final int maxScreenRow = 5;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    private int enemyCooldown;
    private int frameCount;
    private int enemySpeed;

    private final KeyHandler keyH = new KeyHandler();
    private Thread gameThread;

    private Player p;
    private ArrayList<Arrow> arrows;
    private ArrayList<Enemy> enemies;
    private int playerSpeed;
    private int fps;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.green);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        arrows = new ArrayList<Arrow>();
        enemies = new ArrayList<Enemy>();
        fps = 60;
        playerSpeed = 5;
        enemyCooldown = 120;
        frameCount = 0;
        enemySpeed = 5;

        p = new Player(30, 100, playerSpeed, this, keyH);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        p.update();
        frameCount++;

        if(frameCount == enemyCooldown) {
            int x = maxScreenCol * tileSize;
            int y = (int) (Math.random() * 650);
            Enemy e = new Enemy (x, y, enemySpeed);
            enemies.add(e);
            frameCount = 0;
        }

        if(keyH.isSpacePressed()) {
            boolean tooClose = false;

            for(Arrow a : arrows) {
                if(a.x < p.x + 300) {
                    tooClose = true;
                }
            }
            if(!tooClose) {
                Arrow a = new Arrow(p.x, p.y, 20);
                arrows.add(a);
                System.out.println(arrows);
            }
        }

        for(int i = 0; i < arrows.size(); i++) {
            if(arrows.get(i).x > tileSize * maxScreenCol) {
                arrows.remove(arrows.get(i));
            }
            else {
                arrows.get(i).moveForward();
            }
        }

        for (int i = 0; i < enemies.size(); i++){
            if (enemies.get(i).x + enemies.get(i).speed > 100) {
                enemies.get(i).moveForward();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        p.draw(graphics2D, tileSize);
        for(Arrow a : arrows) {
            a.draw(graphics2D, tileSize);
        }
        for(Enemy e : enemies) {
            e.draw(graphics2D, tileSize);
        }
        graphics2D.dispose();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/fps;
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
