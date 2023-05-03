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
        setUp();
    }

    public void setUp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocation(50, 50);
        setVisible(true);


        panel = new JPanel();
        panel.setBounds(0,0,500,400);
        background = new ImageIcon("Background.png").getImage();
        score = new JTextArea();
        score.setBounds(0,0,100, 25);
        wallHealth = new JTextArea();

        add(wallHealth);
        wallHealth.setText("This is the health");
    }

    public void paint(Graphics g) {
        Graphics2D graphic_2D = (Graphics2D) g;
        graphic_2D.drawImage(background, 0,0,null);
    }
}
