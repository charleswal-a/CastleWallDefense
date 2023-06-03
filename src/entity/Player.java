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
    private final int moveCooldown = 20;
    private BufferedImage bowDrawn;

    public Player(int x, int y, int s, GamePanel gp, KeyHandler keyH) {
        super(x, y, s);
        this.gp = gp;
        this.keyH = keyH;

        setPlayerImages();
        framesFromLastMove = 0;
    }

    public void setPlayerImages() {
        try {
            bowDrawn = ImageIO.read(getClass().getResourceAsStream("/Player/Player-still-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(framesFromLastMove >= moveCooldown) {
            if (keyH.isUpPressed()) {
                if (getY() > 72) {
                    setY(getY() - getSpeed());
                }
                framesFromLastMove = 0;
            } else if (keyH.isDownPressed()) {
                if (getY() < 648) {
                    setY(getY() + getSpeed());
                }
                framesFromLastMove = 0;
            }
        }
        framesFromLastMove++;
    }

    public void draw(Graphics2D graphics2D, int tileSize){
        graphics2D.drawImage(bowDrawn, getX(), getY(), tileSize, tileSize, null);
    }
}
