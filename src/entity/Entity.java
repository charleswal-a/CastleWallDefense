package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x;
    public int y;
    public int speed;
    public String direction;

    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
