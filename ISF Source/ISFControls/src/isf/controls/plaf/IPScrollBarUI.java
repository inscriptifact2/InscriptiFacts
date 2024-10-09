package isf.controls.plaf;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Implements the ScrollBar Look and Feel
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPScrollBarUI extends BasicScrollBarUI {

    protected class IPScrollBarMouseListener extends MouseAdapter {

        public void mouseEntered(MouseEvent mouseEvent) {
            setRollover(true);
        }

        public void mouseExited(MouseEvent mouseEvent) {
            setRollover(false);
        }

        public void mousePressed(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent))
                setArmed(true);
        }

        public void mouseReleased(MouseEvent mouseEvent) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent))
                setArmed(false);
        }

        protected IPScrollBarMouseListener() {
        }
    }

    protected class IPScrollBarArrowButton extends IPArrowButton {

        public void setBorder(Border border1) {
        }

        public void paint(Graphics graphics) {
            super.paint(graphics);
            buttonBorder.paintBorder(this, graphics, 0, 0, getWidth(), getHeight());
        }

        public IPScrollBarArrowButton(int orientation) {
            super(orientation);
            setMargin(new Insets(2, 2, 2, 2));
        }
    }

    public IPScrollBarUI() {
        isArmed = false;
        isRollover = false;
    }

    protected void configureArrowButton(JButton button) {
        button.setBackground(backgroundColor);
        button.setBorder(buttonBorder);
        button.setBorderPainted(true);
        button.setForeground(borderColor);
        button.setPreferredSize(new Dimension(16, 16));
        button.setUI(new IPButtonUI());
        ComponentUI userInterface = button.getUI();
        if (userInterface instanceof IPButtonUI) {
            IPButtonUI buttonUI = (IPButtonUI) userInterface;
            buttonUI.setBackgroundColor(backgroundColor);
        }
    }

    protected JButton createDecreaseButton(int orientation) {
        JButton button = new IPScrollBarArrowButton(orientation);
        configureArrowButton(button);
        return button;
    }

    protected JButton createIncreaseButton(int orientation) {
        JButton button = new IPScrollBarArrowButton(orientation);
        configureArrowButton(button);
        return button;
    }

    protected MouseListener createMouseListener() {
        return new IPScrollBarMouseListener();
    }

    public static ComponentUI createUI(JComponent component) {
        return new IPScrollBarUI();
    }

    public Dimension getPreferredSize(JComponent component) {
        Dimension preferredSize = super.getPreferredSize(component);
        if (preferredSize != null && scrollbar != null)
            switch (scrollbar.getOrientation()) {
                case 0:
                    preferredSize.height = 16;
                    break;
                case 1:
                    preferredSize.width = 16;
                    break;
            }
        return preferredSize;
    }

    protected void installDefaults() {
        backgroundColor = UIManager.getColor("ScrollBar.background");
        borderColor = UIManager.getColor("ScrollBar.borderColor");
        buttonBorder = UIManager.getBorder("ScrollBar.buttonBorder");
        disabledBackgroundColor = UIManager.getColor("ScrollBar.disabledThumbBackground");
        disabledBorderColor = UIManager.getColor("ScrollBar.disabledBorder");
        rolloverBackgroundColor = UIManager.getColor("ScrollBar.rolloverBackground");
        selectedForegroundColor = UIManager.getColor("ScrollBar.selectForeground");
        thumbBackgroundColor = UIManager.getColor("ScrollBar.thumb");
        thumbRolloverBackgroundColor = UIManager.getColor("ScrollBar.rolloverBackground");
        trackBackgroundColor = UIManager.getColor("ScrollBar.trackBackground");
        super.installDefaults();
    }

    protected void installListeners() {
        super.installListeners();
        if (scrollbar != null) {
            if (mouseListener == null)
                mouseListener = createMouseListener();
            scrollbar.addMouseListener(mouseListener);
        }
    }

    protected void paintThumb(Graphics graphics, JComponent component, Rectangle thumbRectangle) {
        int x = thumbRectangle.x;
        int y = thumbRectangle.y;
        int height = thumbRectangle.height;
        int width = thumbRectangle.width;
        if (scrollbar != null)
            switch (scrollbar.getOrientation()) {
                case 0:
                    y++;
                    height -= 2;
                    break;
                case 1:
                    x++;
                    width -= 2;
                    break;
            }
        boolean isEnabled = component.isEnabled();
        graphics.setColor(isEnabled ? !isRollover && !isArmed ? thumbBackgroundColor : thumbRolloverBackgroundColor : disabledBackgroundColor);
        graphics.fillRect(x, y, width, height);
        graphics.setColor(isEnabled ? borderColor : disabledBorderColor);
        //graphics.drawRect(x, y, width - 1, height - 1);
        if (scrollbar != null) {
            switch (scrollbar.getOrientation()) {
                case 0:
                    graphics.drawLine(x, y, x, height);
                    graphics.drawLine(x + width - 1, y, x + width - 1, height);
                    break;
                case 1:
                    graphics.drawLine(x, y, width, y);
                    graphics.drawLine(x, y + height - 1, width, y + height - 1);
                    break;
            }
        }
    }

    protected void paintTrack(Graphics graphics, JComponent component, Rectangle trackRectangle) {
        graphics.setColor(trackBackgroundColor);
        int x = trackRectangle.x;
        int y = trackRectangle.y;
        int width = trackRectangle.width;
        int height = trackRectangle.height;
        graphics.fillRect(x, y, width, height);
        if (scrollbar != null) {
            //graphics.setColor(selectedForegroundColor);
            graphics.setColor(borderColor);
            int x2 = 0;
            int y2 = 0;
            switch (scrollbar.getOrientation()) {
                case 0:
                    x2 = x + width;
                    graphics.drawLine(x, y, x2, y);
                    y2 = (y + height) - 1;
                    graphics.drawLine(x, y2, x2, y2);
                    break;
                case 1:
                    x2 = (x + width) - 1;
                    graphics.drawLine(x, y, x, y + height);
                    graphics.drawLine(x2, y, x2, y + height);
                    break;
            }
        }
    }

    public void setArmed(boolean isArmed) {
        if (isArmed != this.isArmed) {
            this.isArmed = isArmed;
            if (scrollbar != null)
                scrollbar.repaint();
        }
    }

    public void setRollover(boolean isRollover) {
        if (isRollover != this.isRollover) {
            this.isRollover = isRollover;
            if (scrollbar != null)
                scrollbar.repaint();
        }
    }

    protected void uninstallDefaults() {
        super.uninstallDefaults();
        borderColor = null;
        backgroundColor = null;
        buttonBorder = null;
        disabledBackgroundColor = null;
        disabledBorderColor = null;
        rolloverBackgroundColor = null;
        selectedForegroundColor = null;
        thumbBackgroundColor = null;
        thumbRolloverBackgroundColor = null;
        trackBackgroundColor = null;
    }

    protected void uninstallListeners() {
        if (scrollbar != null) {
            if (mouseListener != null)
                scrollbar.removeMouseListener(mouseListener);
            mouseListener = null;
        }
    }

    private Color backgroundColor;
    private Color borderColor;
    private Border buttonBorder;
    private Color disabledBackgroundColor;
    private Color disabledBorderColor;
    private boolean isArmed;
    private boolean isRollover;
    private MouseListener mouseListener;
    private Color rolloverBackgroundColor;
    private Color selectedForegroundColor;
    private Color thumbBackgroundColor;
    private Color thumbRolloverBackgroundColor;
    private Color trackBackgroundColor;
}
