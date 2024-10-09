package isf.controls.plaf;

import java.awt.Color;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;


public class DesktopTheme extends DefaultMetalTheme {
    private Color titleColor;

    public DesktopTheme(Color titleColor) {
        this.titleColor = titleColor;


    }
/* If the title bar's background color is rather dark, its text should */
/* come in white, otherwise in black. */
    public ColorUIResource getWindowTitleForeground() {
        return (new ColorUIResource(new Color(0xac5539)));
    }

    public ColorUIResource getWindowTitleInactiveForeground() {
        return (new ColorUIResource(Color.black));
    }


    public ColorUIResource getWindowTitleBackground() {
        return (new ColorUIResource(titleColor));

    }

    public ColorUIResource getWindowTitleInactiveBackground() {
        return (new ColorUIResource(titleColor));

    }

    public ColorUIResource getPrimaryControl() {
        return (new ColorUIResource(titleColor));

    }


}