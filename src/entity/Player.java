package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    private GamePanel gp;
    private KeyHandler keyH;

    public Player(int x, int y, int s, GamePanel gp, KeyHandler keyH) {
        super(x, y, s);
        this.gp = gp;
        this.keyH = keyH;
    }

    public void update() {
        if(keyH.isUpPressed()) {

        }
        else if(keyH.isDownPressed()) {

        }
        else if(keyH.isSpacePressed()) {

        }
    }

    public void draw(){

    }
}
