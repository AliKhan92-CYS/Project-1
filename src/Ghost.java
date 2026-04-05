import java.awt.*;
import java.util.Random;

public class Ghost {

    int x, y;
    int size = 30;
    int speed = 3;

    int dx, dy;
    int steps = 0;

    Random rand = new Random();

    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
        setRandomDirection(); // ensure movement starts immediately
    }

    // Set a valid random direction
    private void setRandomDirection() {
        int dir = rand.nextInt(4);

        if (dir == 0) { dx = 0; dy = -1; }
        else if (dir == 1) { dx = 0; dy = 1; }
        else if (dir == 2) { dx = -1; dy = 0; }
        else { dx = 1; dy = 0; }

        steps = 20 + rand.nextInt(30);
    }

    public void move(Maze maze) {

        // If no steps left → choose new direction
        if (steps <= 0) {
            setRandomDirection();
        }

        int newX = x + dx * speed;
        int newY = y + dy * speed;

        // Move only if not hitting wall
        if (!maze.isWall(newX, newY, size)) {
            x = newX;
            y = newY;
            steps--;
        } else {
            // If stuck → immediately change direction
            setRandomDirection();
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Body
        g2.setColor(Color.RED);
        g2.fillOval(x, y, size, size);

        // Bottom zig-zag (demon look)
        int[] xPoints = {
                x, x + size/4, x + size/2, x + 3*size/4, x + size
        };
        int[] yPoints = {
                y + size, y + size - 10, y + size, y + size - 10, y + size
        };

        g2.fillPolygon(xPoints, yPoints, 5);

        // Eyes
        g2.setColor(Color.WHITE);
        g2.fillOval(x + 5, y + 5, 6, 6);
        g2.fillOval(x + size - 10, y + 5, 6, 6);

        g2.setColor(Color.BLACK);
        g2.fillOval(x + 7, y + 7, 3, 3);
        g2.fillOval(x + size - 8, y + 7, 3, 3);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}