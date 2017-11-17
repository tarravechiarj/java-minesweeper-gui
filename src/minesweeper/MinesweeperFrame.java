package minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MinesweeperFrame {
    private GameState game;
    private JFrame gameFrame;
    private JLabel flagsRemaining;
    private JLabel outcome;
    private JLabel time;
    private Timer timer;
    private JPanel minePanel;
    private JButton[][] gridButtons;
    private Difficulty selectedDifficulty = Difficulty.HARD;
    private static final Dimension gridButtonSize = new Dimension(26, 26);

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
        createTopPanel();
        createBottomPanel();
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
            b.addActionListener(e -> {
                selectedDifficulty = Difficulty.valueOf(b.getActionCommand());
            });
        }
        gameFrame.add(difficultyPanel, BorderLayout.EAST);
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        flagsRemaining = new JLabel("Flags");
        outcome = new JLabel("");
        time = new JLabel("");
        timer = new Timer(1000, e -> {
            int t = Integer.parseUnsignedInt(time.getText().trim());
            time.setText(Integer.toUnsignedString(++t));
        });

        topPanel.add(flagsRemaining);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(outcome);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(time);
        gameFrame.add(topPanel, BorderLayout.PAGE_START);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> newGameClicked());
        bottomPanel.add(newGameButton);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void createMinePanel() {
        minePanel = new JPanel(new GridLayout());
        gameFrame.add(minePanel, BorderLayout.CENTER);
    }

    private void newGameClicked() {
        int rows = selectedDifficulty.getRows();
        int cols = selectedDifficulty.getCols();
        int mines = selectedDifficulty.getMines();
        game = new GameState(this, rows, cols, mines);
        minePanel.removeAll();
        setMinePanelSize(rows, cols);
        gridButtons = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton b = new JButton();
                b.setPreferredSize(gridButtonSize);
                b.setMinimumSize(gridButtonSize);
                b.setMaximumSize(gridButtonSize);
                b.addMouseListener(new MinesweeperListener(i, j));
                gridButtons[i][j] = b;
                minePanel.add(b);
            }
        }

        outcome.setText("");
        time.setText("0");
        setFlagsRemaining();
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

    void setFlagsRemaining() {
        int flags = game.getFlagsRemaining();
        flagsRemaining.setText(Integer.toUnsignedString(flags));
    }

    void displayFlag(int x, int y) {
        gridButtons[x][y].setIcon(GameIcon.FLAG.getIcon());
    }

    void displayDefault(int x, int y) {
        gridButtons[x][y].setIcon(null);
    }

    void displayCount(int x, int y, int count) {
        if (count == 0) {
            gridButtons[x][y].setEnabled(false);
        }
        else {
            Icon icon = numToIcon(count).getIcon();
            gridButtons[x][y].setIcon(icon);
        }
    }

    void gameLost(int x, int y) {
        timer.stop();
        gridButtons[x][y].setIcon(GameIcon.EXP_MINE.getIcon());

        for (int i = 0; i < gridButtons.length; i++) {
            for (int j = 0; j < gridButtons[0].length; j++) {
                JButton b = gridButtons[i][j];
                ImageIcon icon = (ImageIcon) b.getIcon();
                disableButton(b);
                if (game.isMine(i, j) && icon == null)
                    b.setIcon(GameIcon.MINE.getIcon());
                if (!game.isMine(i,j) && icon == GameIcon.FLAG.getIcon())
                    b.setIcon(GameIcon.X_MINE.getIcon());
            }
        }
        outcome.setText("YOU LOSE");
    }

    void gameWon() {
        timer.stop();
        for (int i = 0; i < gridButtons.length; i++) {
            for (int j = 0; j < gridButtons[0].length; j++) {
                disableButton(gridButtons[i][j]);
            }
        }
        outcome.setText("YOU WIN");
    }

    private static void disableButton(JButton b) {
        for (MouseListener m : b.getMouseListeners())
            b.removeMouseListener(m);
    }

    private static GameIcon numToIcon(int numMines) {
        switch(numMines) {
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

    private class MinesweeperListener extends MouseAdapter {
        private int x;
        private int y;

        public MinesweeperListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e))
                game.select(x, y);
            if (SwingUtilities.isRightMouseButton(e))
                game.flag(x, y);
        }
    }

    public static void main(String[] args) {
        MinesweeperFrame gui = new MinesweeperFrame();
    }
}
