package isf.controls.view.containers;

import java.awt.Insets;
import javax.swing.JPanel;

public class TrimmedPanel extends JPanel {

    private Insets ins;

    public TrimmedPanel() {
        ins = new Insets(0, 0, 0, 0);
    }

    public TrimmedPanel(Insets insets) {
        ins = new Insets(0, 0, 0, 0);
        ins = insets;
    }

    public Insets getInsets() {
        return ins;
    }
}
