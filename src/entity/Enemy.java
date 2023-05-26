package entity;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private BufferedImage running, still, attacking;
    private String state;
    private int nextImage;

    public Enemy(int x, int y, int s) {
        super(x, y, s);
        setEnemyImages();
        state = "running";
        nextImage = 1;
    }

    public void moveForward() {
        x -= speed;
        if(x - speed < 100) {
            state = "attacking";
        }
    }

    public void setEnemyImages() {
        try {
            still = ImageIO.read(getClass().getResourceAsStream("/Enemy/Enemy-still.png"));
            attacking = ImageIO.read(getClass().getResourceAsStream("/Enemy/Enemy-attacking.png"));
            running = ImageIO.read(getClass().getResourceAsStream("/Enemy/Enemy-running.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D, int tileSize) {
        BufferedImage image = null;

        switch(state) {
            case "running":
                if(nextImage <= 25) {
                    image = running;
                    nextImage++;
                }
                else {
                    image = still;
                    nextImage++;
                }
                break;
            case "attacking":
                if(nextImage <= 25) {
                    image = attacking;
                    nextImage++;
                }
                else {
                    image = still;
                    nextImage++;
                }
                break;
        }
        graphics2D.drawImage(image, x, y, tileSize, tileSize, null);

        if (nextImage == 50) {
            nextImage = 1;
        }
    }
}
