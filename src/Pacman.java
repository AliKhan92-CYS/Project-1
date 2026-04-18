import java.awt.*;

public class Pacman {

    int x, y;
    int size = 30;
    int speed = 5;

    int dx = 0, dy = 0;
    int score = 0;

    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void move(Maze maze) {
        int newX = x + dx * speed;
        int newY = y + dy * speed;

        // 4-corner collision check
        if (!maze.isWall(newX, newY) &&
                !maze.isWall(newX + size - 1, newY) &&
                !maze.isWall(newX, newY + size - 1) &&
                !maze.isWall(newX + size - 1, newY + size - 1)) {

            x = newX;
            y = newY;
        }

        if (maze.eatFood(x, y)) {
            score += 10;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);

        int startAngle = getMouthAngle();
        int arcAngle = 300; // mouth opening size

        g.fillArc(x, y, size, size, startAngle, arcAngle);

        g.setColor(Color.BLACK);
        g.fillOval(x + 20, y + 5, 5, 5);
    }

    // ✅ NEW: direction-based mouth rotation
    private int getMouthAngle() {
        if (dx == 1) return 30;    // right
        if (dx == -1) return 210;  // left
        if (dy == -1) return 120;  // up
        if (dy == 1) return 300;   // down

        return 30; // default (right)
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}