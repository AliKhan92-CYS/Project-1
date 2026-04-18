import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {

    private JButton startButton;
    private JLabel titleLabel;
    private JPanel panel;

    public StartScreen() {
        initUI();
    }

    private void initUI() {
        setTitle("Pac-Man Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("PAC-MAN GAME");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action when button is clicked
        startButton.addActionListener(e -> startGame());

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(startButton);
        panel.add(Box.createVerticalGlue());

        add(panel);
        setVisible(true);
    }

    // Separated logic (better design)
    private void startGame() {
        dispose(); // close start screen

        GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}