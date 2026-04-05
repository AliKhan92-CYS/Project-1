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

        if (!maze.isWall(newX, newY, size)) {
            x = newX;
            y = newY;

            if (maze.eatFood(x, y)) {
                score += 10;
            }
        }
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);

        int startAngle = 30;

        if (dx == 1) startAngle = 30;        // RIGHT
        else if (dx == -1) startAngle = 210; // LEFT
        else if (dy == -1) startAngle = 120; // UP
        else if (dy == 1) startAngle = 300;  // DOWN

        // Draw body (mouth)
        g2.fillArc(x, y, size, size, startAngle, 300);
        g2.setColor(Color.BLACK);

        int eyeX = x + size / 2;
        int eyeY = y + size / 2;

        if (dx == 1) { // RIGHT
            eyeX = x + (int)(size * 0.65);
            eyeY = y + (int)(size * 0.25);
        }
        else if (dx == -1) { // LEFT
            eyeX = x + (int)(size * 0.25);
            eyeY = y + (int)(size * 0.25);
        }
        else if (dy == -1) { // UP
            eyeX = x + (int)(size * 0.30);
            eyeY = y + (int)(size * 0.25);
        }
        else if (dy == 1) { // DOWN
            eyeX = x + (int)(size * 0.30);
            eyeY = y + (int)(size * 0.65);
        }

        g2.fillOval(eyeX, eyeY, size / 6, size / 6);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}