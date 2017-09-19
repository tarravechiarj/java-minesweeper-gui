package minesweeper;

import javax.swing.ImageIcon;

public enum GameIcon {
    FLAG     ("flag.png"),
    MINE     ("mine.png"),
    EXP_MINE ("explodedMine.png"),
    X_MINE   ("crossedMine.png"),
    ONE      ("1.png"),
    TWO      ("2.png"),
    THREE    ("3.png"),
    FOUR     ("4.png"),
    FIVE     ("5.png"),
    SIX      ("6.png"),
    SEVEN    ("7.png"),
    EIGHT    ("8.png");

    private final ImageIcon icon;

    GameIcon(String name) {
        icon = new ImageIcon("icons/" + name);
    }

    public ImageIcon getIcon() {
        return icon;
    }
}
