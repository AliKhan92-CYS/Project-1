import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Timer timer;
    Pacman pacman;
    Maze maze;

    Ghost[] ghosts;
    int ghostCount = 0;

    boolean up, down, left, right;

    int spawnTimer = 0;
    Random rand = new Random();

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        pacman = new Pacman(50, 50);
        maze = new Maze();

        // ✅ FIX 1: initialize array BEFORE using it
        ghosts = new Ghost[10];  // you can change size if needed

        // safe first spawn
        Point p = getValidSpawn();
        ghosts[0] = new Ghost(p.x, p.y);
        ghostCount = 1;

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 520);
    }

    public void actionPerformed(ActionEvent e) {

        int dx = 0, dy = 0;
        if (up) dy = -1;
        if (down) dy = 1;
        if (left) dx = -1;
        if (right) dx = 1;

        pacman.setDirection(dx, dy);
        pacman.move(maze);

        for (int i = 0; i < ghostCount; i++) {
            ghosts[i].move(maze);
        }

        spawnTimer++;

        // spawn new ghost safely
        if (spawnTimer > 300 && ghostCount < ghosts.length) {
            Point p = getValidSpawn();
            ghosts[ghostCount] = new Ghost(p.x, p.y);
            ghostCount++;
            spawnTimer = 0;
        }

        // collision check
        for (int i = 0; i < ghostCount; i++) {
            if (pacman.getBounds().intersects(ghosts[i].getBounds())) {
                JOptionPane.showMessageDialog(this, "Game Over!");
                System.exit(0);
            }
        }

        if (maze.isFinished()) {
            JOptionPane.showMessageDialog(this, "You Win!");
            System.exit(0);
        }

        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        maze.draw(g);
        pacman.draw(g);

        for (int i = 0; i < ghostCount; i++) {
            ghosts[i].draw(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + pacman.score, 20, 20);
    }

    private Point getValidSpawn() {
        while (true) {
            int row = rand.nextInt(maze.map.length);
            int col = rand.nextInt(maze.map[0].length);

            if (maze.map[row][col] == 0) {
                int x = col * maze.tileSize;
                int y = row * maze.tileSize;
                return new Point(x, y);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
    }

    public void keyTyped(KeyEvent e) {}
}