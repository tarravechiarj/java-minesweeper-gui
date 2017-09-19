package minesweeper;

import java.awt.event;

public class MinesweeperListener implements MouseListener {
    private GameState game;
    private int x;
    private int y;

    public MinesweeperListener(GameState game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
    }
    
}
