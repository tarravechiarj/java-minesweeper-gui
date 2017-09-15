import javax.swing.JToggleButton;
import java.awt.Dimension;

public class GridButton extends JToggleButton {
    private int x;
    private int y;
    private boolean clicked;
    private boolean flagged;

    public class GridButton(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        clicked = false;
        flagged = false;
        this.setMaximumSize(new Dimension(10, 10));
    }


}
