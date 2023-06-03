package entity;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private BufferedImage running, still, attacking;
    private String state;
    private int frameCount;

    public Enemy(int x, int y, int s) {
        super(x, y, s);
        setEnemyImages();
        state = "running";
        frameCount = 1;
    }

    public void moveForward() {
        setX(getX() - getSpeed());
        if(getX() - getSpeed() < 200) {
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
                if(frameCount <= 20) {
                    image = running;
                    frameCount++;
                }
                else {
                    image = still;
                    frameCount++;
                }
                break;
            case "attacking":
                if(frameCount <= 25) {
                    image = attacking;
                    frameCount++;
                }
                else {
                    image = still;
                    frameCount++;
                }
                break;
        }
        graphics2D.drawImage(image, getX(), getY(), tileSize, tileSize, null);

        if (frameCount == 40) {
            frameCount = 1;
        }
    }

    public String getState() {
        return state;
    }

    public int getFrameCount() {
        return frameCount;
    }
}
