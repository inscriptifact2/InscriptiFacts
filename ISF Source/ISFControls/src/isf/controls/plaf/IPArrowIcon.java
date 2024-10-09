package isf.controls.plaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * Icon Used with ScrollBar ArrowButton
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPArrowIcon
        implements IPButtonIcon {

    public IPArrowIcon() {
        this(5);
    }

    public IPArrowIcon(int orientation) {
        paintGlyph = true;
        setOrientation(orientation);
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getIconHeight() {
        return 4;
    }

    public int getIconWidth() {
        return 8;
    }

    public int getOrientation() {
        return orientation;
    }

    public void paintIcon(Component component, Graphics graphics, int x, int y) {
        if (paintGlyph) {
            if (interiorColor != null) {
                graphics.setColor(interiorColor);
                graphics.fillRect(x, y, getIconWidth() - 1, getIconHeight() - 1);
            }
            graphics.setColor(glyphColor);
            graphics.translate(x, y);
            graphics.fillPolygon(arrowPolygon);
            graphics.translate(-x, -y);
        }
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setGlyphColor(Color glyphColor) {
        this.glyphColor = glyphColor;
    }

    public void setInteriorColor(Color interiorColor) {
        this.interiorColor = interiorColor;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        switch (orientation) {
            case 1:
            case 2:
            case 4:
            case 6:
            default:
                if (upArrowPolygon == null) {
                    upArrowPolygon = new Polygon();
                    upArrowPolygon.addPoint(1, 5);
                    upArrowPolygon.addPoint(4, 1);
                    upArrowPolygon.addPoint(9, 6);
                }
                arrowPolygon = upArrowPolygon;
                break;
            case 5:
                if (downArrowPolygon == null) {
                    downArrowPolygon = new Polygon();
                    downArrowPolygon.addPoint(1, 0);
                    downArrowPolygon.addPoint(8, 0);
                    downArrowPolygon.addPoint(4, 4);
                }
                arrowPolygon = downArrowPolygon;
                break;
            case 3:
                if (rightArrowPolygon == null) {
                    rightArrowPolygon = new Polygon();
                    rightArrowPolygon.addPoint(1, 1);
                    rightArrowPolygon.addPoint(5, 5);
                    rightArrowPolygon.addPoint(1, 9);
                }
                arrowPolygon = rightArrowPolygon;
                break;
            case 7:
                if (leftArrowPolygon == null) {
                    leftArrowPolygon = new Polygon();
                    leftArrowPolygon.addPoint(0, 5);
                    leftArrowPolygon.addPoint(4, 1);
                    leftArrowPolygon.addPoint(4, 9);
                }
                arrowPolygon = leftArrowPolygon;
                break;
        }
    }

    public void setPaintGlyph(boolean paintGlyph) {
        this.paintGlyph = paintGlyph;
    }

    protected static final int ARROW_HEIGHT = 4;
    protected static final int ARROW_WIDTH = 8;
    protected Polygon arrowPolygon;
    private Color borderColor;
    protected static Polygon downArrowPolygon;
    private Color glyphColor;
    private Color interiorColor;
    protected static Polygon leftArrowPolygon;
    private int orientation;
    private boolean paintGlyph;
    protected static Polygon rightArrowPolygon;
    protected static Polygon upArrowPolygon;
}
