package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Arrow extends Entity {
    private BufferedImage arrowImage;
    public Arrow(int x, int y, int s) {
        super(x, y, s);
        setArrowImage();
    }

    public void moveForward() {
        setX(getX() + getSpeed());
    }

    public void setArrowImage() {
        try {
            arrowImage = ImageIO.read(getClass().getResourceAsStream("/Arrow/Arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D, int tileSize) {
        graphics2D.drawImage(arrowImage, getX(), getY(), tileSize, tileSize, null);
    }
}
