package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    private GamePanel gp;
    private KeyHandler keyH;
    private int framesFromLastMove;
    private boolean movingUp;
    private boolean movingDown;

    public Player(int x, int y, int s, GamePanel gp, KeyHandler keyH) {
        super(x, y, s);
        this.gp = gp;
        this.keyH = keyH;

        setPlayerImages();
        direction = "still";
        framesFromLastMove = 0;
        movingUp = false;
        movingDown = false;
    }

    public void setPlayerImages() {
        try {
            bowDrawn = ImageIO.read(getClass().getResourceAsStream("/Player/Player-still-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(framesFromLastMove >= 40) {
            if (keyH.isUpPressed()) {
                direction = "up";
                if (y > 0) {
                    y -= speed;
                }
                framesFromLastMove = 0;
            } else if (keyH.isDownPressed()) {
                direction = "down";
                if (y < 576) {
                    y += speed;
                }
                framesFromLastMove = 0;
            }
        }
        framesFromLastMove++;
    }

    public void draw(Graphics2D graphics2D, int tileSize){
        BufferedImage image = null;
        switch(direction) {
            case "up":
                image = bowDrawn;
                break;
            case "still":
                image = bowDrawn;
                break;
            case "down":
                image = bowDrawn;
                break;
        }

        graphics2D.drawImage(image, x, y, tileSize, tileSize, null);
        graphics2D.drawImage(image, tileSize, tileSize, tileSize, tileSize, null);
    }


}
