package minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MinesweeperFrame {
    private GameState game;
    private Difficulty selectedDifficulty;
    private JFrame gameFrame;
    private JLabel flagsRemaining;
    private JPanel minePanel;
    private static final Dimension gridButtonSize = new Dimension(20, 20);

    public MinesweeperFrame() {
        gameFrame = new JFrame();
        gameFrame.setLocation(200,200);
        gameFrame.setMinimumSize(new Dimension(303,240));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("Minesweeper");

        initializeComponents();
    }

    private void initializeComponents() {
        createDifficultyPanel();

        JPanel north = new JPanel();
        flagsRemaining = new JLabel("Flags Remaining");
        north.add(flagsRemaining);
        gameFrame.add(north, BorderLayout.PAGE_START);

        JPanel south = new JPanel();
        JButton newGame = new JButton("New Game");
        south.add(newGame);
        gameFrame.add(south, BorderLayout.SOUTH);
        newGame.addActionListener(e -> newGameClicked());

        minePanel = new JPanel(new GridLayout());
        gameFrame.add(minePanel, BorderLayout.CENTER);
        newGameClicked();
    }

    private void createDifficultyPanel() {
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel,
                                                BoxLayout.Y_AXIS));
        ButtonGroup radioGroup = new ButtonGroup();

        for (Difficulty d : Difficulty.values()) {
            JRadioButton b = new JRadioButton(d.getDescription());
            b.setActionCommand(d.toString());
            radioGroup.add(b);
            difficultyPanel.add(b);
            b.addActionListener(e -> selectedDifficulty =
                                Difficulty.valueOf(b.getActionCommand()));
            if (d == Difficulty.HARD)
                b.doClick();
        }
        gameFrame.add(difficultyPanel, BorderLayout.EAST);
    }

    private void newGameClicked() {
        int rows = selectedDifficulty.getRows();
        int cols = selectedDifficulty.getCols();
        int mines = selectedDifficulty.getMines();
        game = new GameState(rows, cols, mines);
        minePanel.removeAll();
        setMinePanelSize(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton b = new JButton("");
                b.setPreferredSize(gridButtonSize);
                b.setMinimumSize(gridButtonSize);
                b.setMaximumSize(gridButtonSize);
                //b.addActionListener(e -> );
                //b.addMouseListener();
                minePanel.add(b);
            }
        }

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    private void setMinePanelSize(int rows, int cols) {
        GridLayout layout = (GridLayout) minePanel.getLayout();
        layout.setRows(rows);
        layout.setColumns(cols);
        Dimension panelSize = new Dimension();
        double width = gridButtonSize.getWidth() * cols;
        double height = gridButtonSize.getHeight() * rows;
        panelSize.setSize(width, height);
        minePanel.setMinimumSize(panelSize);
        minePanel.setMaximumSize(panelSize);
    }

    public static void main(String[] args) {
        MinesweeperFrame gui = new MinesweeperFrame();
    }
}
