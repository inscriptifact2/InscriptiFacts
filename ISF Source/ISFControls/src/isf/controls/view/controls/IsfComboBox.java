package isf.controls.view.controls;

import isf.controls.plaf.IPComboBoxUI;
import isf.controls.view.gui.InscriptListCellRenderer;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Mar 6, 2004
 * Time: 11:31:36 PM
 * To change this template use Options | File Templates.
 */
public class IsfComboBox extends JComboBox {
    /*   public IsfComboBox() {
           super();
           updateUI();
           setRenderer(new InscriptListCellRenderer());
       }

       public IsfComboBox(Vector v) {
           super(v);
           updateUI();
           setRenderer(new InscriptListCellRenderer());
       }

       public void updateUI() {
           ISFComboBoxUI cui = new ISFComboBoxUI();
           //  System.out.println("called uisetting for combo"+cui);
           setUI(cui);
       }
   */

    protected int popupWidth;

    public IsfComboBox(ComboBoxModel aModel) {
        super(aModel);
        setUI(new IPComboBoxUI());
        setRenderer(new InscriptListCellRenderer());
        popupWidth = 0;
    }

    public IsfComboBox(final Object[] items) {
        super(items);
        setUI(new IPComboBoxUI());
        setRenderer(new InscriptListCellRenderer());
        popupWidth = 0;
    }

    public IsfComboBox(Vector items) {
        super(items);
        setUI(new IPComboBoxUI());
        setRenderer(new InscriptListCellRenderer());
        popupWidth = 0;
    }


    public void setPopupWidth(int width) {
        popupWidth = width;
    }

    public Dimension getPopupSize() {
        Dimension size = getSize();
        if (popupWidth < 1) popupWidth = size.width;
        return new Dimension(popupWidth, size.height);
    }





}
