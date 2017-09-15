import java.javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MinesweeperFrame {
    private MinesweeperGame game;
    private String selectedDifficulty;
    private JFrame gameFrame;
    private JLabel flagsRemaining;
    // more
    // difficulty field(s)

    public MinesweeperFrame() {
        game = new MinesweeperGame(16, 30, 99);
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
        JButton newGame = new JButton("New Game");
        flagsRemaining = new JLabel("Flags Remaining");

        JPanel north = new JPanel();
        north.add(flagsRemaining);
        gameFrame.add(north, BorderLayout.PAGE_START)

        JPanel gridPanel = new JPanel();
        // TODO: create grid
        gameFrame.add(gridPanel, BorderLayout.LINE_START);

        JPanel difficultyPanel = createDifficultyPanel();

        JPanel south = new JPanel();
        south.add(newGame);
        gameFrame.add(south, BorderLayout.PAGE_START);
        newGame.addActionListener(e -> newGameClicked());
    }

    private JPanel createDifficultyRadio() {
        JPanel panel = new JPanel();
        ArrayList<AbstractButton> buttons = new ArrayList<AbstractButton>();
        buttons.add(new JRadioButton("Easy"));
        buttons.add(new JRadioButton("Intermediate"));
        buttons.add(new JRadioButton("Hard"));

        ButtonGroup group = new ButtonGroup();
        for (AbstractButton b : buttons) {
            group.add(b);
            b.setMnemonic(KeyEvent.KV_D);
            b.addActionListener(e -> )


        }


        return panel;
    }

    /** TODO: difficulty args
     */
    private void newGameClicked() {
        this.game = new MinesweeperGame(30, 16, 90);
    }

    public static void main(String[] args) {
        MinesweeperFrame gui = new MinesweeperFrame();
    }
}
