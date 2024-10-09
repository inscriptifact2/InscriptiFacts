package isf.controls.plaf;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 * Metal UI for JComboBox
 * User: kamesh
 * Date: Aug 9, 2003
 */
public class ISFComboBoxUI extends BasicComboBoxUI {
    public ISFComboBoxUI() {
        super();
        //     listBox=null;
    }

    public static ComponentUI createUI(JComponent c) {
        return new ISFComboBoxUI();
    }

    public void paint(Graphics g, JComponent c) {
        if (!comboBox.isEnabled())
            listBox.setForeground(Color.gray);
        else
            listBox.setForeground(Color.black);
    }

    protected ComboBoxEditor createEditor() {
        return new ISFComboBoxEditor.UIResource();
    }

    protected ComboPopup createPopup() {
        MetalComboPopup cmp = new MetalComboPopup(comboBox);

        return cmp;
    }

    protected JButton createArrowButton() {
        JButton button = new ISFComboBoxButton(comboBox,
                new ISFComboBoxIcon(),
                comboBox.isEditable(),
                currentValuePane,
                listBox);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    public PropertyChangeListener createPropertyChangeListener() {
        return new MetalPropertyChangeListener();
    }

    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     */
    public class MetalPropertyChangeListener extends BasicComboBoxUI.PropertyChangeHandler {

        public void propertyChange(PropertyChangeEvent e) {
            super.propertyChange(e);
            String propertyName = e.getPropertyName();
            if (propertyName.equals("editable")) {
                ISFComboBoxButton button = (ISFComboBoxButton) arrowButton;
                button.setIconOnly(comboBox.isEditable());
                comboBox.repaint();
            } else if (propertyName.equals("background")) {
                Color color = (Color) e.getNewValue();
                arrowButton.setBackground(color);
                // listBox.setBackground(color);
            } else if (propertyName.equals("foreground")) {
                Color color = (Color) e.getNewValue();
                arrowButton.setForeground(color);
                // listBox.setForeground(color);
            }
        }
    }

    /**
     * As of Java 2 platform v1.4 this method is no longer used. Do not call or
     * override. All the functionality of this method is in the
     * MetalPropertyChangeListener.
     *
     * @deprecated As of Java 2 platform v1.4.
     */
    protected void editablePropertyChanged(PropertyChangeEvent e) {
    }

    protected LayoutManager createLayoutManager() {
        return new MetalComboBoxLayoutManager();
    }

    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     */
    public class MetalComboBoxLayoutManager extends BasicComboBoxUI.ComboBoxLayoutManager {

        public void layoutContainer(Container parent) {
            layoutComboBox(parent, this);
        }

        public void superLayout(Container parent) {
            super.layoutContainer(parent);
        }
    }

    // This is here because of a bug in the compiler.
    // When a protected-inner-class-savvy compiler comes out we
    // should move this into MetalComboBoxLayoutManager.
    public void layoutComboBox(Container parent, MetalComboBoxLayoutManager manager) {
        if (comboBox.isEditable()) {
            manager.superLayout(parent);
        } else {
            if (arrowButton != null) {
                Insets insets = comboBox.getInsets();
                int width = comboBox.getWidth();
                int height = comboBox.getHeight();
                arrowButton.setBounds(insets.left, insets.top,
                        width - (insets.left + insets.right),
                        height - (insets.top + insets.bottom));
            }
        }
    }

    /**
     * As of Java 2 platform v1.4 this method is no
     * longer used.
     *
     * @deprecated As of Java 2 platform v1.4.
     */
    protected void removeListeners() {
        if (propertyChangeListener != null) {
            comboBox.removePropertyChangeListener(propertyChangeListener);
        }
    }

    // These two methods were overloaded and made public. This was probably a
    // mistake in the implementation. The functionality that they used to
    // provide is no longer necessary and should be removed. However,
    // removing them will create an uncompatible API change.

    public void configureEditor() {
        super.configureEditor();
    }

    public void unconfigureEditor() {
        super.unconfigureEditor();
    }

    public Dimension getMinimumSize(JComponent c) {
        if (!isMinimumSizeDirty) {
            return new Dimension(cachedMinimumSize);
        }
        Dimension size = null;
        if (!comboBox.isEditable() &&
                arrowButton != null &&
                arrowButton instanceof ISFComboBoxButton) {
            ISFComboBoxButton button = (ISFComboBoxButton) arrowButton;
            Insets buttonInsets = button.getInsets();
            Insets insets = comboBox.getInsets();
            size = getDisplaySize();
            size.width += insets.left + insets.right;
            size.width += buttonInsets.left + buttonInsets.right;
            size.width += buttonInsets.right + button.getComboIcon().getIconWidth();
            size.height += insets.top + insets.bottom;
            size.height += buttonInsets.top + buttonInsets.bottom;
        } else if (comboBox.isEditable() &&
                arrowButton != null &&
                editor != null) {
            size = super.getMinimumSize(c);
            Insets margin = arrowButton.getMargin();
            size.height += margin.top + margin.bottom;
        } else {
            size = super.getMinimumSize(c);
        }
        cachedMinimumSize.setSize(size.width, size.height);
        isMinimumSizeDirty = false;
        return new Dimension(cachedMinimumSize);
    }

    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <FooUI>.
     * <p/>
     * This class is now obsolete and doesn't do anything and
     * is only included for backwards API compatibility. Do not call or
     * override.
     *
     * @deprecated As of Java 2 platform v1.4.
     */
    public class MetalComboPopup extends BasicComboPopup {
        Dimension dim;
        //  JScrollPane scroller;
        //    JList list;
        int visible;

        public MetalComboPopup(JComboBox cBox) {
            super(cBox);

        }

        // This method was overloaded and made public. This was probably
        // mistake in the implementation. The functionality that they used to
        // provide is no longer necessary and should be removed. However,
        // removing them will create an uncompatible API change.

        public void delegateFocus(MouseEvent e) {
            super.delegateFocus(e);
        }

        public Dimension getPreferredSize() {
            visible = comboBox.getMaximumRowCount();
            // scroller.setVe

            setBackground(Color.white);
            int maxRec = comboBox.getModel().getSize();
            list.setPreferredSize(new Dimension(150, maxRec * 17 + 1));
            Dimension dim = new Dimension(150, (int) (visible * 17) + 1);

            return dim;
        }

        protected void configurePopup() {

            setLayout(new BorderLayout());
            scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            scroller.setViewportView(list);
            scroller.getVerticalScrollBar().setUnitIncrement(17);
            //  scroller.getViewport().setExtentSize(new Dimension(150,(int)10 *40 ))  ;

            //    scroller.setPreferredSize(new Dimension(150,(int)list.getPreferredScrollableViewportSize().getHeight()))      ;
            ////    scroller.getViewport().setViewSize(new Dimension(150,320))      ;

            // scroller.getViewport().setViewSize(new Dimension(150,(int)comboBox.getMaximumRowCount() *20 ));
            //    scroller.setViewportView(list);

            add(scroller, "Center");
        }
    }
}