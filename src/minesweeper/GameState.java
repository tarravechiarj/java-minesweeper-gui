package minesweeper;

import java.util.Random;

public class GameState {
    private MinesweeperFrame view;
    private int[][] grid;
    private boolean[][] visible;
    private boolean[][] flagged;
    private int rows;
    private int cols;
    private int mines;
    private int flagsRemaining;
    private int cellsRemaining;

    public GameState(MinesweeperFrame view, int rows, int cols, int mines) {
        this.view = view;
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        visible = new boolean[rows][cols];
        flagged = new boolean[rows][cols];
        flagsRemaining = mines;
        cellsRemaining = rows * cols - mines;
    }

    void select(int x, int y) {
        if (flagged[x][y] || visible[x][y])
            return;
        if (grid == null)
            initializeGrid(x, y);

        visible[x][y] = true;
        int mineCount = grid[x][y];

        switch(mineCount) {
            case -1:
                view.gameLost(x, y);
                break;
            case 0:
                recursiveReveal(x, y);
                // fall through
            default:
                view.displayCount(x, y, mineCount);
                break;
        }

        if (--cellsRemaining == 0)
            view.gameWon();
    }

    private void recursiveReveal(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (!inGrid(i, j)) continue;
                if (visible[i][j]) continue;
                if (flagged[i][j]) continue;
                if (isMine(i, j)) continue;
                select(i, j);
            }
        }
    }

    void flag(int x, int y) {
        if (visible[x][y]) {
            return;
        }
        else if (flagged[x][y]) {
            flagged[x][y] = false;
            flagsRemaining++;
            view.displayDefault(x, y);
            view.setFlagsRemaining();
        }
        else if (flagsRemaining > 0) {
            flagged[x][y] = true;
            flagsRemaining--;
            view.displayFlag(x, y);
            view.setFlagsRemaining();
        }
    }

    boolean isMine(int x, int y) {
        return grid[x][y] < 0;
    }

    int getFlagsRemaining() {
        return flagsRemaining;
    }

    private boolean inGrid(int x, int y) {
        return (x >= 0 && x < rows) && (y >= 0 && y < cols);
    }

    private void initializeGrid(int x, int y) {
        grid = new int[rows][cols];
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < mines) {
            int i = rand.nextInt(rows);
            int j = rand.nextInt(cols);

            if (!isMine(i, j) && (i != x || j != y)) {
                grid[i][j] = -1;
                incAdjMineCount(i, j);
                minesPlaced++;
            }
        }
    }

    private void incAdjMineCount(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (inGrid(i, j) && !isMine(i, j))
                    grid[i][j]++;
            }
        }
    }

}
