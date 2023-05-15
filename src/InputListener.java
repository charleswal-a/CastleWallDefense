import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class InputListener implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean spacePressed;

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        if (key == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (key == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
