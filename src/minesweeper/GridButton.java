package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridButton extends JButton {
    //private ImageIcon hiddenIcon;

    public GridButton(int mineCount) {
        super();
        this.setMaximumSize(new Dimension(30, 30));
        this.addActionListener(e -> leftClick());
        //hiddenIcon = setHiddenIcon(mineCount);
    }

    // TODO: remove mouselistener and make icons, etc.
    // TODO: also tell game object about it
    private void leftClick() {
        this.setEnabled(false);
        // this.setDisabledIcon(...);
    }


}
