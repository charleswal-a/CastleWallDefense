import javax.swing.*;

public class GameWindow extends JFrame {
    private JPanel panel;
    private JLabel health;
    private JLabel killCount;

    public GameWindow() {
        setContentPane(panel);
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500, 500);
    }
}
