package minesweeper;

import java.util.Random;

public class GameState {
    private int[][] grid;
    private boolean[][] visible;
    private boolean[][] flagged;
    private int rows;
    private int cols;
    private int mines;
    private int flagsRemaining;
    private int cellsRemaining;

    public GameState(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        grid = new int[rows][cols];
        visible = new boolean[rows][cols];
        flagged = new boolean[rows][cols];
        flagsRemaining = mines;
        cellsRemaining = rows * cols - mines;
        initializeGrid();
    }

    public GameIcon select(int x, int y) {
        if (flagged[x][y] || visible[x][y])
            return null;

        GameIcon icon = numToIcon(grid[x][y]);
        visible[x][y] = true;

        if (icon == GameIcon.MINE)
            return GameIcon.EXP_MINE;

        cellsRemaining--;
        return icon;
    }

    public GameIcon flag(int x, int y) {
        if (visible[x][y])
            return null;

        if (flagged[x][y]) {
            flagged[x][y] = false;
            flagsRemaining++;
            return GameIcon.BLANK;
        }
        else if (flagsRemaining > 0) {
            flagged[x][y] = true;
            flagsRemaining--;
            return GameIcon.FLAG;
        }
        else {
            return GameIcon.BLANK;
        }
    }

    public boolean isMine(int x, int y) {
        return grid[x][y] < 0;
    }

    public boolean isVisible(int x, int y) {
        return visible[x][y];
    }

    public boolean isFlagged(int x, int y) {
        return flagged[x][y];
    }

    private int adjMineCount(int x, int y) {
        return grid[x][y];
    }

    public boolean inGrid(int x, int y) {
        return (x >= 0 && x < rows) && (y >= 0 && y < cols);
    }

    public int getFlagsRemaining() {
        return flagsRemaining;
    }

    public int getMines() {
        return mines;
    }

    public boolean hasWon() {
        return cellsRemaining == 0;
    }

    private GameIcon numToIcon(int numMines) {
        switch(numMines) {
            case -1:
                return GameIcon.MINE;
            case 0:
                return GameIcon.BLANK;
            case 1:
                return GameIcon.ONE;
            case 2:
                return GameIcon.TWO;
            case 3:
                return GameIcon.THREE;
            case 4:
                return GameIcon.FOUR;
            case 5:
                return GameIcon.FIVE;
            case 6:
                return GameIcon.SIX;
            case 7:
                return GameIcon.SEVEN;
            case 8:
                return GameIcon.EIGHT;
            default:
                return null;
        }
    }

    private void initializeGrid() {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < mines) {
            int x = rand.nextInt(rows);
            int y = rand.nextInt(cols);

            if (!isMine(x, y)) {
                grid[x][y] = -1;
                incAdjMineCount(x, y);
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
