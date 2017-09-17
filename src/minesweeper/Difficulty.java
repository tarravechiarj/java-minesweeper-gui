package minesweeper;

public enum Difficulty {
    EASY          (9, 9, 10),
    INTERMEDIATE  (16, 16, 40),
    HARD          (16, 30, 99);

    private final int rows;
    private final int cols;
    private final int bombs;

    Difficulty(int rows, int cols, int bombs) {
        this.rows = rows;
        this.cols = cols;
        this.bombs = bombs;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return rows;
    }

    public int getBombs() {
        return rows;
    }
}
