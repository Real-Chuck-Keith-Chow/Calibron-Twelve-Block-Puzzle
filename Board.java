import java.awt.Dimension;
import java.awt.Point;

public class Board {
    public static final int SIZE = 56;
    private boolean[][] grid = new boolean[SIZE][SIZE];
    private Point nextFree = new Point(0, 0);
    private int space = SIZE * SIZE;

    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = true; // true means unoccupied
            }
        }
    }

    public boolean doesFit(Dimension piece) {
        if (nextFree.x + piece.width > SIZE || nextFree.y + piece.height > SIZE) {
            return false;
        }
        for (int w = 0; w < piece.width; w++) {
            for (int h = 0; h < piece.height; h++) {
                if (!grid[nextFree.y + h][nextFree.x + w]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Point insert(Dimension piece) {
        Point position = new Point(nextFree);

        for (int x = 0; x < piece.width; x++) {
            for (int y = 0; y < piece.height; y++) {
                grid[nextFree.y + y][nextFree.x + x] = false;
            }
        }

        boolean updated = false;
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (!updated && grid[y][x]) {
                    nextFree = new Point(x, y);
                    updated = true;
                }
            }
        }

        space -= piece.width * piece.height;
        return position;
    }

    public Board copy() {
        Board copiedBoard = new Board();
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(this.grid[i], 0, copiedBoard.grid[i], 0, SIZE);
        }
        copiedBoard.nextFree = new Point(nextFree);
        copiedBoard.space = this.space;
        return copiedBoard;
    }

    public int getSpace() {
        return space;
    }
}
