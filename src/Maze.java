import java.awt.*;

public class Maze {

    int tileSize = 50;

    int[][] map = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,2,2,2,2,2,2,2,2,1},
            {1,2,1,1,2,1,1,2,2,1},
            {1,2,2,2,2,2,1,2,2,1},
            {1,2,1,2,1,2,2,2,2,1},
            {1,2,2,2,1,2,1,1,2,1},
            {1,1,1,2,2,2,2,2,2,1},
            {1,2,2,2,1,1,2,1,2,1},
            {1,2,2,2,2,2,2,2,2,1},
            {1,1,1,1,1,1,1,1,1,1}
    };

    public void draw(Graphics g) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {

                if (map[row][col] == 1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                }

                if (map[row][col] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(col * tileSize + tileSize/3,
                            row * tileSize + tileSize/3,
                            tileSize/4, tileSize/4);
                }
            }
        }
    }

    public boolean isWall(int x, int y, int size) {

        int left = x / tileSize;
        int right = (x + size - 1) / tileSize;
        int top = y / tileSize;
        int bottom = (y + size - 1) / tileSize;

        if (top < 0 || left < 0 || bottom >= map.length || right >= map[0].length)
            return true;

        return map[top][left] == 1 ||
                map[top][right] == 1 ||
                map[bottom][left] == 1 ||
                map[bottom][right] == 1;
    }

    public boolean eatFood(int x, int y) {
        int col = x / tileSize;
        int row = y / tileSize;

        if (map[row][col] == 2) {
            map[row][col] = 0;
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        for (int[] row : map) {
            for (int cell : row) {
                if (cell == 2) return false;
            }
        }
        return true;
    }
}