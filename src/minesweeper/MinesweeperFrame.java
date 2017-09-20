package minesweeper;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MinesweeperFrame {
    private GameState game;
    private Difficulty selectedDifficulty;
    private JFrame gameFrame;
    private JLabel flagsRemaining;
    private JLabel outcome;
    private JLabel time;
    private Timer timer;
    private JPanel minePanel;
    private JButton[][] gridButtons;
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
        outcome = new JLabel("");
        time = new JLabel("");
        timer = new Timer(1000, e -> {
            int t = Integer.parseUnsignedInt(time.getText().trim());
            time.setText(Integer.toUnsignedString(++t));
        });

        labelPanel.add(flagsRemaining);
        labelPanel.add(Box.createHorizontalGlue());
        labelPanel.add(outcome);
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
        gridButtons = new JButton[rows][cols];
        minePanel.removeAll();
        setMinePanelSize(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton b = new JButton("");
                gridButtons[i][j] = b;
                b.setPreferredSize(gridButtonSize);
                b.setMinimumSize(gridButtonSize);
                b.setMaximumSize(gridButtonSize);
                b.addMouseListener(new MinesweeperListener(i, j));
                minePanel.add(b);
            }
        }

        outcome.setText("");
        int flags = game.getFlagsRemaining();
        flagsRemaining.setText(Integer.toUnsignedString(flags));
        time.setText("0");
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

    private void recursiveReveal(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (!game.inGrid(i, j))
                    continue;
                if (game.isVisible(i, j))
                    continue;
                if (game.isMine(i, j))
                    continue;
                if (game.isFlagged(i, j))
                    continue;
                leftClick(i, j);
            }
        }
    }

    private void leftClick(int x, int y) {
        JButton b = gridButtons[x][y];
        GameIcon icon = game.select(x, y);
        if (icon == null) return;

        switch(icon) {
            case BLANK:
                recursiveReveal(x, y);
                removeMouseListeners(b);
                b.setEnabled(false);
                break;
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
                b.setIcon(icon.getIcon());
                removeMouseListeners(b);
                if (game.hasWon())
                    wonGame();
                break;
            case EXP_MINE:
                b.setIcon(icon.getIcon());
                lostGame();
                break;
            default:
                break;
        }
    }

    private void rightClick(int x, int y) {
        JButton b = gridButtons[x][y];
        GameIcon icon = game.flag(x, y);
        b.setIcon(icon.getIcon());

        int flags = game.getFlagsRemaining();
        flagsRemaining.setText(Integer.toUnsignedString(flags));
    }

    private static void removeMouseListeners(JButton b) {
        for (MouseListener m : b.getMouseListeners())
            b.removeMouseListener(m);
    }

    private void wonGame() {
        timer.stop();

        for (int i = 0; i < gridButtons.length; i++) {
            for (int j = 0; j < gridButtons[0].length; j++) {
                removeMouseListeners(gridButtons[i][j]);
            }
        }

        outcome.setText("YOU WIN");
    }

    private void lostGame() {
        timer.stop();

        for (int i = 0; i < gridButtons.length; i++) {
            for (int j = 0; j < gridButtons[0].length; j++) {
                JButton b = gridButtons[i][j];
                ImageIcon icon = (ImageIcon) b.getIcon();
                removeMouseListeners(b);
                if (game.isMine(i, j) && icon == null)
                    b.setIcon(GameIcon.MINE.getIcon());
                if (!game.isMine(i,j) && icon == GameIcon.FLAG.getIcon())
                    b.setIcon(GameIcon.X_MINE.getIcon());
            }
        }

        outcome.setText("YOU LOSE");
    }

    public class MinesweeperListener implements MouseListener {
        private int x;
        private int y;

        public MinesweeperListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e))
                leftClick(x, y);
            if (SwingUtilities.isRightMouseButton(e))
                rightClick(x, y);
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    public static void main(String[] args) {
        MinesweeperFrame gui = new MinesweeperFrame();
    }
}
