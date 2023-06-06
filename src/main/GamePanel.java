package main;

import entity.Arrow;
import entity.Player;
import entity.Enemy;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    private final int ORIGINAL_TILE_SIZE = 16;
    private final int SCALE = 9;
    private final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private final int MAX_SCREEN_COL = 9;
    private final int MAX_SCREEN_ROW = 5;
    private final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    private final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW + 72;
    private final KeyHandler KEY_H = new KeyHandler();
    private Sound backgroundMusic, titleScreenMusic;
    private BufferedImage gameBackground, titleSrn1, titleSrn2, howToPlay, endingSrn1, endingSrn2, topBar;
    private BufferedImage number1, number2, number3, number4, number5, number6, number7, number8, number9, number0;
    private BufferedImage newBarricade, damagedBarricade, brokenBarricade;
    private String backgroundState;
    private String titleScreenState;
    private int framesSinceLastEnter;
    private Thread gameThread;
    private int enemyCooldown;
    private int frameCount;
    private int enemySpeed;
    private Player p;
    private ArrayList<Arrow> arrows;
    private ArrayList<Enemy> enemies;
    private int fps;
    private int barricadeHealth;
    private int killCount;
    private int highScore;
    private int killsUntilSpeedBuff;
    private final int[] yValues = {72, 216, 360, 504, 648};


    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(KEY_H);
        this.setFocusable(true);
        this.setBackground(new Color(0, 199, 255));
        setValues();
    }

    private void setValues() {
        resetGame();
        fps = 60;
        framesSinceLastEnter = 0;
        titleScreenState = "option 1";
        backgroundState = "title screen";
        highScore = getHighScore();
        backgroundMusic = new Sound("/Users/walford/IdeaProjects/CastleWallDefense/assets/Sounds/backgroundMusic.wav");
        titleScreenMusic = new Sound("/Users/walford/IdeaProjects/CastleWallDefense/assets/Sounds/titleScreenMusic.wav");
        titleScreenMusic.playLooped();

        try {
            gameBackground = ImageIO.read(getClass().getResourceAsStream("/Background/Background.png"));
            newBarricade = ImageIO.read(getClass().getResourceAsStream("/Barricade/Barricade-new.png"));
            damagedBarricade = ImageIO.read(getClass().getResourceAsStream("/Barricade/Barricade-damaged.png"));
            brokenBarricade = ImageIO.read(getClass().getResourceAsStream("/Barricade/Barricade-broken.png"));
            titleSrn1 = ImageIO.read(getClass().getResourceAsStream("/Background/Title-screen-1.png"));
            titleSrn2 = ImageIO.read(getClass().getResourceAsStream("/Background/Title-screen-2.png"));
            howToPlay = ImageIO.read(getClass().getResourceAsStream("/Background/How-to-play.png"));
            endingSrn1 = ImageIO.read(getClass().getResourceAsStream("/Background/Ending-no-new-score.png"));
            endingSrn2 = ImageIO.read(getClass().getResourceAsStream("/Background/Ending-new-score.png"));
            topBar = ImageIO.read(getClass().getResourceAsStream("/Background/Top-bar.png"));

            number1 = ImageIO.read(getClass().getResourceAsStream("/Numbers/1.png"));
            number2 = ImageIO.read(getClass().getResourceAsStream("/Numbers/2.png"));
            number3 = ImageIO.read(getClass().getResourceAsStream("/Numbers/3.png"));
            number4 = ImageIO.read(getClass().getResourceAsStream("/Numbers/4.png"));
            number5 = ImageIO.read(getClass().getResourceAsStream("/Numbers/5.png"));
            number6 = ImageIO.read(getClass().getResourceAsStream("/Numbers/6.png"));
            number7 = ImageIO.read(getClass().getResourceAsStream("/Numbers/7.png"));
            number8 = ImageIO.read(getClass().getResourceAsStream("/Numbers/8.png"));
            number9 = ImageIO.read(getClass().getResourceAsStream("/Numbers/9.png"));
            number0 = ImageIO.read(getClass().getResourceAsStream("/Numbers/0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetGame() {
        arrows = new ArrayList<Arrow>();
        enemies = new ArrayList<Enemy>();
        enemyCooldown = 120;
        frameCount = 0;
        enemySpeed = 5;
        barricadeHealth = 100;
        killCount = 0;
        killsUntilSpeedBuff = 10;
        p = new Player(99, TILE_SIZE * 2 + 72, TILE_SIZE, KEY_H);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (backgroundState.equals("playing")) {
            p.update();
            frameCount++;

            if (frameCount >= enemyCooldown) {
                int x = MAX_SCREEN_COL * TILE_SIZE;
                int i = (int) (Math.random() * yValues.length);
                Enemy e = new Enemy(x, yValues[i], enemySpeed);
                enemies.add(e);
                frameCount = 0;
            }

            if (KEY_H.isSpacePressed()) {
                boolean tooClose = false;

                for (Arrow a : arrows) {
                    if (a.getX() < p.getX() + 300) {
                        tooClose = true;
                    }
                }
                if (!tooClose) {
                    Arrow a = new Arrow(p.getX(), p.getY(), 20);
                    arrows.add(a);
                }
            }

            for (int i = 0; i < arrows.size(); i++) {
                Arrow a = arrows.get(i);
                if (a.getX() > TILE_SIZE * MAX_SCREEN_COL) {
                    arrows.remove(i);
                } else {
                    a.moveForward();
                }

                for (int k = 0; k < enemies.size(); k++) {
                    Enemy e = enemies.get(k);
                    if (a.getY() == e.getY()) {
                        if (a.getX() >= e.getX()) {
                            killCount++;
                            killsUntilSpeedBuff--;
                            enemies.remove(k);
                            if (arrows.size() > 0) {
                                arrows.remove(i);
                            }
                            k = enemies.size();
                        }
                    }
                }

                if (killsUntilSpeedBuff == 0) {
                    enemySpeed += 2;
                    if (enemyCooldown != 60) {
                        enemyCooldown -= 20;
                    }
                    for (Enemy e : enemies) {
                        e.setSpeed(enemySpeed);
                    }
                    killsUntilSpeedBuff = 10;
                }

            }

            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                if (e.getX() + e.getSpeed() > 200) {
                    e.moveForward();
                } else if (e.getState().equals("attacking") && e.getFrameCount() <= 25) {
                    if (barricadeHealth != 0) {
                        barricadeHealth -= 10;
                    }
                    enemies.remove(i);
                    i--;
                }
            }

            if (barricadeHealth == 0) {
                backgroundState = "ended";
                framesSinceLastEnter = 0;
                backgroundMusic.stopMusic();
                titleScreenMusic.playLooped();
            }
        }
        else if (backgroundState.equals("title screen")) {
            resetGame();

            switch (titleScreenState) {
                case "option 1":
                    if (KEY_H.isDownPressed()) {
                        titleScreenState = "option 2";
                    } else if (KEY_H.isEnterPressed() && framesSinceLastEnter >= 30) {
                        backgroundState = "playing";
                        framesSinceLastEnter = 0;
                        titleScreenMusic.stopMusic();
                        backgroundMusic.playLooped();
                    }
                    break;
                case "option 2":
                    if (KEY_H.isUpPressed()) {
                        titleScreenState = "option 1";
                    } else if (KEY_H.isEnterPressed() && framesSinceLastEnter >= 30) {
                            titleScreenState = "how to play";
                            framesSinceLastEnter = 0;
                    }
                    break;
                case "how to play":
                    if (KEY_H.isEnterPressed() && framesSinceLastEnter >= 30) {
                        titleScreenState = "option 2";
                        framesSinceLastEnter = 0;
                    }
                    break;
            }
            framesSinceLastEnter++;
        }
        else if(backgroundState.equals("ended")) {
            if(KEY_H.isEnterPressed() && framesSinceLastEnter >= 60) {
                backgroundState = "title screen";
                framesSinceLastEnter = 0;
            }
            framesSinceLastEnter++;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        if(backgroundState.equals("title screen")) {
            BufferedImage image = null;
            switch (titleScreenState) {
                case "option 1":
                    image = titleSrn1;
                    break;
                case "option 2":
                    image = titleSrn2;
                    break;
                case "how to play":
                    image = howToPlay;
                    break;
            }

            graphics2D.drawImage(image, 0, 72, SCREEN_WIDTH, SCREEN_HEIGHT-72, null);
        }
        if(backgroundState.equals("ended")) {
            if(killCount < highScore) {
                graphics2D.drawImage(endingSrn1, 0, 72, SCREEN_WIDTH, SCREEN_HEIGHT-72, null);
            }
            else {
                if(highScore != killCount) {
                    saveScore(killCount);
                }
                highScore = killCount;
                graphics2D.drawImage(endingSrn2, 0, 72, SCREEN_WIDTH, SCREEN_HEIGHT-72, null);
            }

            drawNumbers(graphics2D, 846, 477, killCount);
            drawNumbers(graphics2D,  837, 576, highScore);
        }
        if(backgroundState.equals("playing")) {
            graphics2D.drawImage(gameBackground, 0, 72, TILE_SIZE * MAX_SCREEN_COL, TILE_SIZE * MAX_SCREEN_ROW, null);
            graphics2D.drawImage(topBar, 0,0, SCREEN_WIDTH, 72, null);

            drawNumbers(graphics2D, 558, 9, barricadeHealth);
            drawNumbers(graphics2D, 1206, 9, killCount);

            BufferedImage barricade = null;
            if(barricadeHealth <= 10) {
                barricade = brokenBarricade;
            }
            else if(barricadeHealth <= 50) {
                barricade = damagedBarricade;
            }
            else if(barricadeHealth <= 100) {
                barricade = newBarricade;
            }
            graphics2D.drawImage(barricade, TILE_SIZE+32, 72, TILE_SIZE/2, SCREEN_HEIGHT-72, null);

            p.draw(graphics2D, TILE_SIZE);

            for (Arrow a : arrows) {
                a.draw(graphics2D, TILE_SIZE);
            }
            for (Enemy e : enemies) {
                e.draw(graphics2D, TILE_SIZE);
            }
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

    public void saveScore(int score) {
        try {
            FileOutputStream writeData = new FileOutputStream("src/highScore.data");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            writeStream.writeObject(score);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        try {
            FileInputStream readData;
            ObjectInputStream readStream;

            if(new File("src/highScore.data").length() != 0) {
                readData = new FileInputStream("src/highScore.data");
                readStream = new ObjectInputStream(readData);
                return (int) readStream.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public BufferedImage determineNumImage(int digit) {
        BufferedImage image;
        if(digit == 1) {
            image = number1;
        }
        else if(digit == 2) {
            image = number2;
        }
        else if(digit == 3) {
            image = number3;
        }
        else if(digit == 4) {
            image = number4;
        }
        else if(digit == 5) {
            image = number5;
        }
        else if(digit == 6) {
            image = number6;
        }
        else if(digit == 7) {
            image = number7;
        }
        else if(digit == 8) {
            image = number8;
        }
        else if(digit == 9) {
            image = number9;
        }
        else {
            image = number0;
        }

        return image;
    }

    public void drawNumbers(Graphics2D graphics2D, int x, int y, int num) {
        String numAsString = Integer.toString(num);

        for(int i = 0; i < numAsString.length(); i++) {
            int digit = Integer.parseInt(numAsString.substring(i, i+1));
            BufferedImage image = determineNumImage(digit);
            graphics2D.drawImage(image, x + i * 45, y, 36, 45, null);
        }
    }
}
