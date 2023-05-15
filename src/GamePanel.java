import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int origTileSize = 16;
    final int height = 500;
    final int width = 500;
    Thread thread;


    public GamePanel() {
        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);
        this.setBackground(Color.white);
        startThread();
    }

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (thread != null) {
            update();
            repaint();
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

    }
}
