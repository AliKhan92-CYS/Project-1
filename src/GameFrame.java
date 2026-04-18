import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Pac-Man Game");

        GamePanel panel = new GamePanel();
        add(panel);

        pack(); // auto size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}