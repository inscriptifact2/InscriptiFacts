/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 * Java Imports
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.View;

/**
 * This class provides the look and feel for the Dark Button
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPButtonUI extends BasicButtonUI {

    private final static IPButtonUI buttonUI = new IPButtonUI();

    /**
     * a static final constant
     */
    public static final int TOP = 0;
    /**
     * a static final constant
     */
    public static final int BOTTOM = 1;
    /**
     * interger variable
     */
    private int radiant = BOTTOM;

    /**
     * Staticmethod
     */
    public static ComponentUI createUI(JComponent c) {
        return buttonUI;
    }

    /**
     * Default Constructor
     */
    public IPButtonUI() {
    }

    /**
     * constructor with parameters
     */
    public IPButtonUI(int radiant) {
        this.radiant = radiant;
    }

    /**
     * This method is used for painting button component
     *
     * @param graphics  Graphics instance
     * @param component Component
     */
    public void paint(Graphics graphics, JComponent component) {
        super.paint(graphics, component);
        if (component.getParent() instanceof javax.swing.JComboBox) {
            AbstractButton button = (AbstractButton) component;
            graphics.setColor(Color.red);
            graphics.fillRoundRect(1, 1, component.getSize().width - 2, component.getSize().height - 2, 12, 12);
            button.setContentAreaFilled(false);
        } else {
            AbstractButton button = (AbstractButton) component;
            if ((button.getText() == null || button.getText().trim().length() == 0) && button.getIcon() != null) {
                super.paint(graphics, component);
                return;
            }
            Insets insets = button.getInsets();
            viewRectangle.x = insets.left;
            viewRectangle.y = insets.top;
            viewRectangle.width = button.getWidth() - (insets.right + viewRectangle.x);
            viewRectangle.height = button.getHeight() - (insets.bottom + viewRectangle.y);
            textRectangle.x = textRectangle.y = textRectangle.width = textRectangle.height = 0;
            iconRectangle.x = iconRectangle.y = iconRectangle.width = iconRectangle.height = 0;
            ButtonModel buttonModel = button.getModel();
            Font font = defaultFont;
            Color background = backgroundColor;
            Color foreground = foregroundColor;
            Color normalBorderColor = borderColor;
            if (buttonModel != null) {
                if (!buttonModel.isEnabled()) {
                    background = disabledBackgroundColor;
                    foreground = disabledForegroundColor;
                    normalBorderColor = disabledBorderColor;
                } else if (buttonModel.isRollover() || buttonModel.isArmed() || button.isOpaque() || buttonModel.isPressed()) {
                    background = rolloverBackgroundColor;
                    foreground = rolloverForegroundColor;
                }
                graphics.setColor(background);
                graphics.fillRect(1, 1, button.getWidth() - 2, button.getHeight() - 3);
                graphics.setColor(normalBorderColor);
                graphics.drawRect(1, 1, button.getWidth() - 2, button.getHeight() - 3);
            }
            String textString = SwingUtilities.layoutCompoundLabel(component, graphics.getFontMetrics(),
                    button.getText(), button.getIcon(), button.getVerticalAlignment(), button.getHorizontalAlignment(),
                    button.getVerticalTextPosition(), button.getHorizontalTextPosition(), viewRectangle, iconRectangle,
                    textRectangle, button.getText() != null ? 4 : 0);
            if (button.getIcon() != null)
                paintIcon(graphics, button, iconRectangle);
            graphics.setColor(foreground);
            graphics.setFont(font);
            if (textString != null && !textString.equals("")) {
                View view = (View) component.getClientProperty("html");
                if (view != null)
                    view.paint(graphics, textRectangle);
                else
                    writeText(graphics, button, textRectangle, textString);
            }
        }
    }

    private void writeText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        FontMetrics fm = g.getFontMetrics();
        // int mnemonicIndex = b.getDisplayedMnemonicIndex();
        if (model.isEnabled()) {
            /*** paint the text normally */
            g.setColor(b.getForeground());
//            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex,
//                    textRect.x + getTextShiftOffset(),
//                    textRect.y + fm.getAscent() + getTextShiftOffset());
        } else {
            /*** paint the text disabled ***/
            g.setColor(b.getBackground().brighter());
//            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex,
//                    textRect.x, textRect.y + fm.getAscent() + 1);
            g.setColor(b.getBackground().darker());
//            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex,
//                    textRect.x - 1, textRect.y + fm.getAscent());
        }
    }

    /**
     * This method is used to paint  External API Button images
     *
     * @param g Graphics
     * @param c Component
     */
    private void paintExternalButtonImages(Graphics g, JComponent c) {
        super.paint(g, c);
        c.setBackground(new Color(240, 237, 229));
    }

    protected void installDefaults(AbstractButton button) {
        backgroundColor = UIManager.getColor("Button.background");
        borderColor = UIManager.getColor("Button.borderColor");
        defaultFont = new FontUIResource(UIManager.getFont("Button.font"));
        disabledBackgroundColor = UIManager.getColor("Button.disabledBackground");
        disabledBorderColor = UIManager.getColor("Button.disabledBorderColor");
        disabledForegroundColor = UIManager.getColor("Button.disabledForeground");
        foregroundColor = UIManager.getColor("Button.foreground");
        rolloverBackgroundColor = UIManager.getColor("Button.rolloverBackground");
        rolloverFont = new FontUIResource(UIManager.getFont("Button.rolloverFont"));
        rolloverForegroundColor = UIManager.getColor("Button.rolloverForeground");
        selectedBackgroundColor = UIManager.getColor("Button.selectBackground");
        selectedForegroundColor = UIManager.getColor("Button.selectForeground");
        flushableBackgroundColor = UIManager.getColor("Flushable.Button.background");

        flushableBorderColor = UIManager.getColor("Flushable.Button.borderColor");

        flushableForgroundColor = UIManager.getColor("Flushable.Button.foreground");

        flushableFont = new FontUIResource(UIManager.getFont("Flushable.Button.Font"));
    }

    public void setBackgroundColor(Color background) {
        backgroundColor = background;
    }

    protected Color backgroundColor;
    protected Color borderColor;
    protected FontUIResource defaultFont;
    protected Color disabledBackgroundColor;
    protected Color disabledBorderColor;
    protected Color disabledForegroundColor;
    protected Color foregroundColor;
    protected Color rolloverBackgroundColor;
    protected FontUIResource rolloverFont;
    protected Color rolloverForegroundColor;
    protected Color selectedBackgroundColor;
    protected Color selectedForegroundColor;
    protected Color flushableBackgroundColor;
    protected Color flushableForgroundColor;
    protected Color flushableBorderColor;
    protected FontUIResource flushableFont;
    protected static Rectangle iconRectangle = new Rectangle();
    protected static Rectangle textRectangle = new Rectangle();
    protected static Rectangle viewRectangle = new Rectangle();
}