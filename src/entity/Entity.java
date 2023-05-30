package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x;
    public int y;
    public int speed;
    public String direction;
    public BufferedImage bowDrawn, down1, down2, up1, up2;
    public final int[] xValues = {0, 144, 288, 432, 576};

    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
