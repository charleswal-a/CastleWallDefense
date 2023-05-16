package entity;

import main.GamePanel;
import main.InputListener;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    InputListener inputL;

    public Player(GamePanel gp, InputListener inputL) {
        super(30, 250);
        this.gp = gp;
        this.inputL = inputL;

        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/images/Piskel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(inputL.isUpPressed()) {
            setY(getY() - 5);
        }
        if(inputL.isDownPressed()) {
            setY(getY() + 5);
        }
    }

    public void draw(Graphics2D g2D) {
        g2D.drawImage(getImage(), getX(), getY(), null);
    }
}
