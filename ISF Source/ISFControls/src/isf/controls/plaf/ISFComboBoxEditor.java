/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 *  Java Imports
 */


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * The default editor for Metal editable combo boxes
 * User: kamesh
 * Date: Aug 9, 2003
 */
public class ISFComboBoxEditor extends BasicComboBoxEditor {

    /**
     * Default constructor
     */
    public ISFComboBoxEditor() {
        //super();
        //editor.removeFocusListener(this);
        editor = new JTextField("", 9);
        editor.setBorder(new EditorBorder());
        editor.setSelectionColor(Color.white);
        //editor.setBackground(Color.red);
        editor.setBackground(Color.white);
        //editor.addFocusListener(this);
    }

    /**
     * This method listens the focus gained event occurences
     *
     * @param e FocusEvent
     */
    public void focusGained(FocusEvent e) {
    }

    protected static Insets editorBorderInsets = new Insets(2, 2, 2, 0);

    /**
     * EditorBorder Inner class is used for creating new border
     * by extending the abstract border
     */
    class EditorBorder extends AbstractBorder {

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.translate(x, y);
            g.setColor(Color.black);
            //g.setColor( MetalLookAndFeel.getControlDarkShadow() );
            g.drawLine(0, 0, w - 1, 0);
            g.drawLine(0, 0, 0, h - 2);
            g.drawLine(0, h - 2, w - 1, h - 2);
            //g.setColor( MetalLookAndFeel.getControlHighlight() );
            g.drawLine(1, 1, w - 1, 1);
            g.drawLine(1, 1, 1, h - 1);
            g.drawLine(1, h - 1, w - 1, h - 1);
            //g.setColor( MetalLookAndFeel.getControl() );
            g.drawLine(1, h - 2, 1, h - 2);
            g.translate(-x, -y);
        }

        /**
         * This method will get the insets  form the component
         *
         * @param c Componenet
         * @return Insets
         */
        public Insets getBorderInsets(Component c) {
            return editorBorderInsets;
        }
    }

    /**
     * A subclass of BasicComboBoxEditor that implements UIResource.
     * BasicComboBoxEditor doesn't implement UIResource
     * directly so that applications can safely override the
     * cellRenderer property with BasicListCellRenderer subclasses.
     * <p/>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package.
     * Please see {@}.
     */
    public static class UIResource extends ISFComboBoxEditor
            implements javax.swing.plaf.UIResource {

    }
}

