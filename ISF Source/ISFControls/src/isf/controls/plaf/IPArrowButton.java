package isf.controls.plaf;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;

/**
 * Button used with ScrollBar
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPArrowButton extends JButton {

    public IPArrowButton() {
        this(5);
    }

    public IPArrowButton(int orientation) {
        setBorderPainted(false);
        arrowIcon = new IPArrowIcon(orientation);
    }

    public boolean isFocusable() {
        return false;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
        int width = getWidth();
        int height = getHeight();
        boolean isEnabled = isEnabled();
        Color borderColor = rolloverBorderColor;
        if (isEnabled) {
            if (model != null)
                borderColor = model.isRollover() ? rolloverBorderColor : arrowIcon.getBorderColor();
        } else {
            borderColor = disabledBorderColor;
        }
        int iconHeight = arrowIcon.getIconHeight();
        int iconWidth = arrowIcon.getIconWidth();
        int x = (width - iconWidth) / 2;
        int y = (height - iconHeight) / 2;
        switch (arrowIcon.getOrientation()) {
            case 3:
            case 7:
                x = (width - iconHeight) / 2;
                y = (height - iconWidth) / 2 - 1;
                break;
            case 1:
                y -= 2;
                break;
        }
        arrowIcon.setGlyphColor(getForeground());
        arrowIcon.paintIcon(this, graphics, x, y);
    }

    public void setBorderColor(Color borderColor) {
        //arrowIcon.setBorderColor(borderColor);
    }

    public void setDisabledBorderColor(Color disabledBorderColor) {
        this.disabledBorderColor = disabledBorderColor;
    }

    public void setRolloverBorderColor(Color rolloverBorderColor) {
        this.rolloverBorderColor = rolloverBorderColor;
    }

    protected IPArrowIcon arrowIcon;
    protected Color disabledBorderColor;
    protected Color rolloverBorderColor;
    public static final int SIDE_LENGTH = 19;
}
