package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    private GamePanel gp;
    private KeyHandler keyH;

    public Player(int x, int y, int s, GamePanel gp, KeyHandler keyH) {
        super(x, y, s);
        this.gp = gp;
        this.keyH = keyH;

        setPlayerImages();
        direction = "still";
    }

    public void setPlayerImages() {
        try {
            bowDrawn = ImageIO.read(getClass().getResourceAsStream("/Player/Player-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.isUpPressed()) {
            direction = "up";
            if (y > 30) {
                y -= speed;
            }
        }
        else if(keyH.isDownPressed()) {
            direction = "down";
            if (y < 510) {
                y += speed;
            }
        }
    }


    public void draw(Graphics2D graphics2D, int tileSize){
//        graphics2D.setColor(Color.white);
//        graphics2D.fillRect(x, y, tileSize, tileSize);

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

    }
}
