import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Timer timer;
    Pacman pacman;
    Maze maze;

    Ghost[] ghosts;
    int ghostCount = 1;

    boolean up, down, left, right;

    int spawnTimer = 0;   // controls delay
    Random rand = new Random();

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        pacman = new Pacman(50, 50);
        maze = new Maze();

        ghosts = new Ghost[10];

        // Spawn first ghost safely
        Point p = getValidSpawn();
        ghosts[0] = new Ghost(p.x, p.y);

        timer = new Timer(40, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {

        // Movement control
        int dx = 0, dy = 0;

        if (up) dy = -1;
        if (down) dy = 1;
        if (left) dx = -1;
        if (right) dx = 1;

        pacman.setDirection(dx, dy);
        pacman.move(maze);

        // Move ghosts
        for (int i = 0; i < ghostCount; i++) {
            ghosts[i].move(maze);
        }

        // Spawn ghosts slowly (every few seconds)
        spawnTimer++;

        if (spawnTimer > 500 && ghostCount < ghosts.length) {  // delay ~6 seconds
            Point p = getValidSpawn();
            ghosts[ghostCount] = new Ghost(p.x, p.y);
            ghostCount++;
            spawnTimer = 0; // reset timer
        }

        // Collision
        for (int i = 0; i < ghostCount; i++) {
            if (pacman.getBounds().intersects(ghosts[i].getBounds())) {
                JOptionPane.showMessageDialog(this, "Game Over!");
                System.exit(0);
            }
        }

        // Win
        if (maze.isFinished()) {
            JOptionPane.showMessageDialog(this, "You Win!");
            System.exit(0);
        }

        repaint();
    }

    //  IMPORTANT: Find safe spawn location
    private Point getValidSpawn() {
        int tileSize = maze.tileSize;

        while (true) {
            int row = rand.nextInt(maze.map.length);
            int col = rand.nextInt(maze.map[0].length);

            // Spawn only on path (food or empty)
            if (maze.map[row][col] != 1) {
                int x = col * tileSize;
                int y = row * tileSize;
                return new Point(x, y);
            }
        }
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