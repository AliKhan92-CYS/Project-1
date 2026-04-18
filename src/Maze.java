import java.awt.*;

public class Maze {

    int tileSize = 50;

    int[][] map = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,0,0,1},
            {1,0,0,0,0,0,1,0,0,1},
            {1,0,1,0,1,0,0,0,0,1},
            {1,0,1,0,1,0,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,0,1,1,0,1,0,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    boolean[][] food = new boolean[10][10];

    public Maze() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[i][j] == 0) {
                    food[i][j] = true;
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                if (map[i][j] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);

                    if (food[i][j]) {
                        g.setColor(Color.WHITE);
                        g.fillOval(j * tileSize + 20, i * tileSize + 20, 10, 10);
                    }
                }
            }
        }
    }

    public boolean isWall(int x, int y) {

        int col = x / tileSize;
        int row = y / tileSize;

        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length) {
            return true;
        }

        return map[row][col] == 1;
    }

    public boolean eatFood(int x, int y) {
        int row = y / tileSize;
        int col = x / tileSize;

        if (food[row][col]) {
            food[row][col] = false;
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (food[i][j]) return false;
            }
        }
        return true;
    }
}