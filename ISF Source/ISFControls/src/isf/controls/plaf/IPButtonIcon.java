package isf.controls.plaf;

import java.awt.Color;
import javax.swing.Icon;

/**
 * ButtonIcon Interface used with  with ScrollBar.
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public interface IPButtonIcon
        extends Icon {

    public abstract void setBorderColor(Color color);

    public abstract void setGlyphColor(Color color);

    public abstract void setInteriorColor(Color color);

    public abstract void setPaintGlyph(boolean flag);
}
