import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Graphics;

public class GameWindow extends JFrame {
    private JPanel panel;
    private JTextArea score;
    private JTextArea wallHealth;
    private Image background;
    private Graphics2D g2d;

    public GameWindow() {
        super("Game");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocation(50, 50);
        setVisible(true);

        background = new ImageIcon("src/orange-fox-sprite.png").getImage();
        score = new JTextArea();
        wallHealth = new JTextArea();
        Graphics Graphics = null;
        setUpGame(Graphics);
    }

    public void setUpGame(Graphics g) {
        Graphics2D graphic_2D = (Graphics2D) g;
        score.setBounds(0,0,100, 25);

        graphic_2D.drawImage(background, 0, 0, null);
    }
}
