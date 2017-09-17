package Minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MinesweeperFrame {
    private MinesweeperGame game;
    private Difficulty selectedDifficulty;
    private JFrame gameFrame;
    private JLabel flagsRemaining;
    private JTable gameGrid;
    // more
    // difficulty field(s)

    public MinesweeperFrame() {
        gameFrame = new JFrame();
        gameFrame.setLocation(200,200);
        gameFrame.setSize(800,800);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("Minesweeper");

        initializeComponents();

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    private void initializeComponents() {
        JPanel difficultyPanel = createDifficultyPanel();
        gameFrame.add(difficultyPanel, BorderLayout.LINE_END);

        JPanel south = new JPanel();
        JButton newGame = new JButton("New Game");
        south.add(newGame);
        gameFrame.add(south, BorderLayout.PAGE_START);
        newGame.addActionListener(e -> newGameClicked());
        newGame.doClick();

        JPanel gridPanel = createGridPanel();
        gameFrame.add(gridPanel, BorderLayout.LINE_START);

        JPanel north = new JPanel();
        flagsRemaining = new JLabel("Flags Remaining");
        north.add(flagsRemaining);
        gameFrame.add(north, BorderLayout.PAGE_START);
    }

    private JPanel createDifficultyPanel() {
        JPanel panel = new JPanel();
        ButtonGroup group = new ButtonGroup();

        for (Difficulty d : Difficulty.values()) {
            JRadioButton b = new JRadioButton(d.toString());
            group.add(b);
            panel.add(b);
            b.addActionListener(e -> selectedDifficulty =
                                Difficulty.valueOf(b.getText()));
            if (d == Difficulty.HARD)
                b.doClick();
        }
        return panel;
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel();

        gameGrid = new JTable();

        panel.add(gameGrid);
        return panel;
    }

    private void newGameClicked() {
        int rows = selectedDifficulty.getRows();
        int cols = selectedDifficulty.getCols();
        int bombs = selectedDifficulty.getBombs();
        this.game = new MinesweeperGame(rows, cols, bombs);
        // TODO: new game display (method call)
        /*
        JButton[][] buttonGrid = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                new GridButton(game.getCell(i, j));
            }
        }
        gameGrid = new JTable(gridButton, null);
        */
    }

    public static void main(String[] args) {
        MinesweeperFrame gui = new MinesweeperFrame();
    }
}
