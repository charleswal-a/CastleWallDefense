package entity;

public class Arrow {
    private int xCoord;
    private int yCoord;

    public Arrow(int y) {
        yCoord = y;
        xCoord = 10;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void moveArrow() {
        xCoord += 5;
    }
}
