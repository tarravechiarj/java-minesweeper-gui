package minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// TODO: end of game code: stop timer
public class MinesweeperFrame {
    private GameState game;
    private Difficulty selectedDifficulty;
    private JFrame gameFrame;
    private JLabel flagsRemaining;
    private JLabel time;
    private Timer timer;
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
        createLabelPanel();
        createNewGamePanel();
        createMinePanel();
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

    private void createLabelPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

        flagsRemaining = new JLabel("Flags");
        time = new JLabel("");
        timer = new Timer(1000, e -> {
            int t = Integer.parseUnsignedInt(time.getText().trim());
            time.setText(Integer.toUnsignedString(++t));
        });

        labelPanel.add(flagsRemaining);
        labelPanel.add(Box.createHorizontalGlue());
        labelPanel.add(time);
        gameFrame.add(labelPanel, BorderLayout.PAGE_START);
    }

    private void createNewGamePanel() {
        JPanel newGamePanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        newGamePanel.add(newGameButton);
        newGameButton.addActionListener(e -> newGameClicked());
        gameFrame.add(newGamePanel, BorderLayout.SOUTH);
    }

    private void createMinePanel() {
        minePanel = new JPanel(new GridLayout());
        gameFrame.add(minePanel, BorderLayout.CENTER);
    }

    private void newGameClicked() {
        int rows = selectedDifficulty.getRows();
        int cols = selectedDifficulty.getCols();
        int mines = selectedDifficulty.getMines();
        game = new GameState(rows, cols, mines);
        time.setText("0");
        minePanel.removeAll();
        setMinePanelSize(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton b = new JButton("");
                b.setPreferredSize(gridButtonSize);
                b.setMinimumSize(gridButtonSize);
                b.setMaximumSize(gridButtonSize);
                //b.addMinesweeperListener(game, i, j);
                minePanel.add(b);
            }
        }

        gameFrame.pack();
        gameFrame.setVisible(true);
        timer.start();
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
