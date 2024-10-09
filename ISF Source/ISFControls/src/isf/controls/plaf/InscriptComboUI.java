package isf.controls.plaf;

import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Feb 1, 2004
 * Time: 12:45:01 PM
 * To change this template use Options | File Templates.
 */
public class InscriptComboUI extends MetalComboBoxUI {

    public InscriptComboUI() {
        super();
    }

    public static ComponentUI createUI(JComponent jcomponent) {
        return new InscriptComboUI();
    }

    public void setPopSize(Dimension d, JComboBox jcb) {
        listBox.setPreferredSize(new Dimension(d.width, d.height * 7));
        Rectangle r = jcb.getBounds();

        listBox.getParent().setBounds(r.x, r.y + r.height + 1, d.width + 14, d.height * 7);

    }

}
