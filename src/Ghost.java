import java.awt.*;
import java.util.Random;

public class Ghost {

    int x, y;
    int size = 30;
    int speed = 3;

    int dx, dy;
    int steps;

    Random rand = new Random();

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        setRandomDirection();
    }

    private void setRandomDirection() {
        int dir = rand.nextInt(4);

        if (dir == 0) { dx = 1; dy = 0; }
        else if (dir == 1) { dx = -1; dy = 0; }
        else if (dir == 2) { dx = 0; dy = 1; }
        else { dx = 0; dy = -1; }

        steps = 15 + rand.nextInt(25); // smoother movement cycles
    }

    public void move(Maze maze) {

        int newX = x + dx * speed;
        int newY = y + dy * speed;

        // ✅ check if movement is valid
        boolean canMove =
                !maze.isWall(newX, newY) &&
                        !maze.isWall(newX + size - 1, newY) &&
                        !maze.isWall(newX, newY + size - 1) &&
                        !maze.isWall(newX + size - 1, newY + size - 1);

        if (canMove) {
            x = newX;
            y = newY;
            steps--;
        } else {
            // 🔥 instead of instant random, try new direction
            setRandomDirection();
        }

        // 🔥 only change direction when steps end (not every collision)
        if (steps <= 0) {
            setRandomDirection();
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);
        g2.fillArc(x, y, size, size, 0, 180);
        g2.fillRect(x, y + size / 2, size, size / 2);

        int[] xPoints = {x, x + size/4, x + size/2, x + 3*size/4, x + size};
        int[] yPoints = {y + size, y + size - 10, y + size, y + size - 10, y + size};

        g2.fillPolygon(xPoints, yPoints, 5);

        g2.setColor(Color.WHITE);
        g2.fillOval(x + 6, y + 5, 7, 7);
        g2.fillOval(x + size - 13, y + 5, 7, 7);

        g2.setColor(Color.BLACK);
        g2.fillOval(x + 8, y + 7, 3, 3);
        g2.fillOval(x + size - 11, y + 7, 3, 3);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}