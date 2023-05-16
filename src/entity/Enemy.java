package entity;

public class Enemy {
    private int xCoord;
    private int yCoord;

    public Enemy(int y) {
        xCoord = 100;
        yCoord = y;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void moveForward() {
        xCoord += 5;
    }
}
