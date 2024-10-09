package com.isf.app.view.gui;

import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Feb 1, 2004
 * Time: 9:22:37 PM
 * To change this template use Options | File Templates.
 */
public class TextPopUp extends JPopupMenu {
    public TextPopUp(JComboBox jComboBox) {
        //  super(metalComboBoxUI, jComboBox);
        super();
        Dimension d = jComboBox.getPreferredSize();
        this.setPreferredSize(new Dimension((int) (d.getWidth() * 1.5), super.getPreferredSize().height));
    }
}
