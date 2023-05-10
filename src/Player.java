public class Player {
    private final int X_COORD = 10;
    private int yCoord;

    public Player() {
        yCoord = 150;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
