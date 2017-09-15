
public class MinesweeperGame {
    private MinesweeperBoard board;
    private int rows;
    private int cols;
    private int bombs;
    private int flagsRemaining;
    private int cellsRemaining;

    public MinesweeperGame(int rows, int cols, int bombs) {
        this.rows = rows;
        this.cols = cols;
        board = new MinesweeperBoard(rows, cols, bombs);
        flagsRemaining = bombs;
        cellsRemaining = rows * cols - bombs;
    }
}
