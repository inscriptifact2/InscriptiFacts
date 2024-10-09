/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 * Java Imports
 */


import isf.controls.view.gui.InscriptListCellRenderer;
import java.awt.*;
import javax.swing.*;

/**
 * JButton subclass to help out ComboBoxUI
 * User: kamesh
 * Date: Aug 9, 2003
 */
public class ISFComboBoxButton extends JButton {

    protected JComboBox comboBox;
    protected JList listBox;
    protected CellRendererPane rendererPane;
    protected Icon comboIcon;
    protected boolean iconOnly = false;

    public final JComboBox getComboBox() {
        return comboBox;
    }

    public final void setComboBox(JComboBox cb) {
        comboBox = cb;
    }

    public final Icon getComboIcon() {
        return comboIcon;
    }

    public final void setComboIcon(Icon i) {
        comboIcon = i;
    }

    public final boolean isIconOnly() {
        return iconOnly;
    }

    public final void setIconOnly(boolean isIconOnly) {
        iconOnly = isIconOnly;
    }

    /**
     * Default Constructor
     */
    ISFComboBoxButton() {
        super("");
        DefaultButtonModel model = new DefaultButtonModel() {
            public void setArmed(boolean armed) {
                super.setArmed(isPressed() ? true : armed);
            }
        };
        setModel(model);
        //setBorder(new LineBorder(Color.black)) ;
    }

    /**
     * Constructor with parameters
     *
     * @param cb   Combobox
     * @param i    Icon
     * @param pane CellRendererPane
     * @param list List
     */
    public ISFComboBoxButton(JComboBox cb, Icon i,
                             CellRendererPane pane, JList list) {
        this();
        comboBox = cb;
        comboIcon = i;
        rendererPane = pane;
        listBox = list;
        listBox.setCellRenderer(new InscriptListCellRenderer());
        setEnabled(comboBox.isEnabled());
    }

    /**
     * Constructor with parameters
     *
     * @param cb
     * @param i
     * @param onlyIcon
     * @param pane
     * @param list
     */
    public ISFComboBoxButton(JComboBox cb, Icon i, boolean onlyIcon,
                             CellRendererPane pane, JList list) {
        this(cb, i, pane, list);
        iconOnly = onlyIcon;
    }

    /**
     * This method will check combo as traversable or not
     *
     * @return boolean
     */
    public boolean isFocusTraversable() {
        return false;
    }

    /**
     * This method will check combo as isLeftToRight or not
     *
     * @param c component
     * @return boolean
     */
    private boolean isLeftToRight(Component c) {
        return c.getComponentOrientation().isLeftToRight();
    }

    public void paintComponent(Graphics g) {
        boolean leftToRight = isLeftToRight(comboBox);
        int iconDrawLeft = -30;
        int iconDrawTop = -30;
        // Paint the button as usual
        super.paintComponent(g);
        Insets insets = getInsets();
        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);
        if (height <= 0 || width <= 0) {
            return;
        }
        int left = insets.left;
        int top = insets.top;
        int right = left + (width - 1);
        int bottom = top + (height - 1);
        int iconWidth = 0;
        int iconLeft = (leftToRight) ? right : left;

        // Paint the icon
        if (comboIcon != null) {
            iconWidth = comboIcon.getIconWidth();
            int iconHeight = comboIcon.getIconHeight();
            int iconTop = 0;
            if (iconOnly) {
                iconLeft = (getWidth() / 2) - (iconWidth / 2);
                iconTop = (getHeight() / 2) - (iconHeight / 2);
            } else {
                if (leftToRight) {
                    iconLeft = (left + (width - 1)) - iconWidth;
                } else {
                    iconLeft = left;
                }
                iconTop = (top + ((bottom - top) / 2)) - (iconHeight / 2);
            }
            iconDrawLeft = iconLeft;
            iconDrawTop = iconTop;
        }

        listBox.setPreferredSize(new Dimension((int) (comboBox.getWidth() * 1.5), 100));


        ListCellRenderer renderer = comboBox.getRenderer();
        // renderer.setPreferredSize(new Dimension ((int)(comboBox.getWidth()*1.5),20))   ;
        Component c;
        boolean renderPressed = getModel().isPressed();
        listBox.setSelectionBackground(Color.white);
        listBox.setSelectionForeground(new Color(0xac5539));
        listBox.setBackground(Color.white);                                            //UIManager.getColor("ComboBox.list.background")
        c = renderer.getListCellRendererComponent(listBox, comboBox.getSelectedItem(), -1, renderPressed, false);
        c.setSize(new Dimension((int) (comboBox.getWidth() * 1.5), 20));
        int cWidth = width - (insets.right);

        // Fix for 4238829: should lay out the JPanel.
        boolean shouldValidate = false;
        if (c instanceof JPanel) {
            shouldValidate = true;
        }

        // Paint the focus
        if (comboBox.hasFocus())
            c.setBackground(Color.white);//UIManager.getColor("ComboBox.focusColor")
        else
            c.setBackground(Color.white);
        rendererPane.paintComponent(g, c, this, left, top, width, height, shouldValidate);
        if (!comboBox.isEnabled())
            listBox.setForeground(Color.lightGray);                                              //UIManager.getColor("ComboBox.disabledForeground")
        else
            listBox.setForeground(Color.black);
        if (comboBox.isEnabled())
            g.setColor(Color.white);//UIManager.getColor("ComboBox.borderColor")
        else
            g.setColor(Color.white);        //UIManager.getColor("ComboBox.disabledForeground")

        //  g.setColor(Color.black);
        //g.drawRect(0, 0, width - 1, height - 1);
        //   g.drawRect(left + cWidth - 18, 3, comboIcon.getIconWidth() + 7, height - 6);
        comboIcon.paintIcon(this, g, iconDrawLeft - 5, iconDrawTop + 1);
    }
}