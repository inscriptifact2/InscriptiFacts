package isf.common.view.gui;

import java.io.Serializable;
import javax.swing.ImageIcon;

public interface SpatialListImage
    extends Serializable
{

    public abstract String getTextView();

    public abstract String getTitle();

    public abstract ImageIcon getTextViewImage();

    public abstract String getIndexMapName();

    public abstract void setTextView(String s);

    public abstract void setTitle(String s);

    public abstract void setTextViewImage(ImageIcon imageicon);

    public abstract void setIndexMapName(String s);
}
