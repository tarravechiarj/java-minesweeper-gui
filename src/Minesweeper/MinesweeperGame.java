package Minesweeper;

import java.util.Random;

public class MinesweeperGame {
    private int[][] grid;
    private int rows;
    private int cols;
    private int mines;
    private int flagsRemaining;
    private int cellsRemaining;

    public MinesweeperGame(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        grid = new int[rows][cols];
        flagsRemaining = mines;
        cellsRemaining = rows * cols - mines;
        initializeGrid();
    }

    public boolean isMine(int x, int y) {
        return grid[x][y] == 0;
    }

    public int getCell(int x, int y) {
        return grid[x][y];
    }

    private void initializeGrid() {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < mines) {
            int x = rand.nextInt(rows);
            int y = rand.nextInt(cols);

            if (grid[x][y] >= 0) {
                grid[x][y] = -1;
                incAdjMineCount(x, y);
                minesPlaced++;
            }
        }
    }

    private void incAdjMineCount(int x, int y) {
        for (int i = x; i < x + 2; x++) {
            for (int j = y; j < y + 2; j++) {
                if (inGrid(i, j) && (i != x || j != y) && (grid[i][j] >= 0))
                    grid[i][j]++;
            }
        }
    }

    private boolean inGrid(int x, int y) {
        return (x >= 0 && x < rows) && (y >= 0 && y < cols);
    }

}
