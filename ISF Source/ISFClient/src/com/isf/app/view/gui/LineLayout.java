package com.isf.app.view.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.Serializable;

public class LineLayout
        implements LayoutManager, Serializable {

    private int rows;
    private int columns;
    private boolean isVertical;
    public static int VERTICAL_TOP = 11;
    public static int VERTICAL_CENTER = 12;
    public static int VERTICAL_BOTTOM = 13;
    public static int HORIJONTAL_LEFT = 14;
    public static int HORIJONTAL_CENTER = 15;
    public static int HORIJONTAL_RIGHT = 16;
    public static int lVERTICAL_TOP = 111;
    public static int lVERTICAL_CENTER = 112;
    public static int lVERTICAL_BOTTOM = 113;
    public static int lHORIJONTAL_LEFT = 114;
    public static int lHORIJONTAL_CENTER = 115;
    public static int lHORIJONTAL_RIGHT = 116;
    private int cellspacing;
    private int borderWidth;
    private int valignment;
    private int halignment;
    public int vlayoutalignment;
    public int hlayoutalignment;

    public LineLayout(boolean flag) {
        rows = 1;
        columns = 1;
        isVertical = true;
        cellspacing = 0;
        borderWidth = 0;
        valignment = VERTICAL_CENTER;
        halignment = HORIJONTAL_CENTER;
        vlayoutalignment = lVERTICAL_CENTER;
        hlayoutalignment = lHORIJONTAL_CENTER;
        isVertical = flag;
    }

    public LineLayout(boolean flag, int i, int j) {
        rows = 1;
        columns = 1;
        isVertical = true;
        cellspacing = 0;
        borderWidth = 0;
        valignment = VERTICAL_CENTER;
        halignment = HORIJONTAL_CENTER;
        vlayoutalignment = lVERTICAL_CENTER;
        hlayoutalignment = lHORIJONTAL_CENTER;
        isVertical = flag;
        valignment = valignment;
        halignment = halignment;
        vlayoutalignment = i;
        hlayoutalignment = j;
    }

    public LineLayout(boolean flag, int i, int j, int k, int l, int i1, int j1) {
        rows = 1;
        columns = 1;
        isVertical = true;
        cellspacing = 0;
        borderWidth = 0;
        valignment = VERTICAL_CENTER;
        halignment = HORIJONTAL_CENTER;
        vlayoutalignment = lVERTICAL_CENTER;
        hlayoutalignment = lHORIJONTAL_CENTER;
        isVertical = flag;
        cellspacing = i;
        borderWidth = j;
        valignment = k;
        halignment = l;
        vlayoutalignment = i1;
        hlayoutalignment = j1;
    }

    public void addLayoutComponent(String s, Component component) {
    }

    public void removeLayoutComponent(Component component) {
    }

    public void layoutContainer(Container container) {
        Component acomponent[] = container.getComponents();
        int i = 0;
        int j = borderWidth + cellspacing;
        if (isVertical) {
            columns = 1;
            rows = acomponent.length;
        } else {
            columns = acomponent.length;
            rows = 1;
        }
        for (int k = 0; k < rows; k++) {
            int l = getMaxHeightForRow(k, acomponent);
            int i1 = borderWidth + cellspacing;
            for (int j1 = 0; j1 < columns; j1++) {
                int k1 = getMaxWidthForCol(j1, acomponent);
                Component component = null;
                try {
                    component = acomponent[i];
                } catch (Exception exception) {
                    continue;
                }
                setAlignmentAndLocation(container, component, i1, j, k1, l);
                i++;
                i1 += k1 + cellspacing;
            }

            j += l + cellspacing;
        }

    }

    private void setAlignmentAndLocation(Container container, Component component, int i, int j, int k, int l) {
        if (!isVertical) {
            switch (valignment) {
                case 12: // '\f'
                    j += l / 2 - component.getPreferredSize().height / 2;
                    break;

                case 13: // '\r'
                    j += l - component.getPreferredSize().height;
                    break;
            }
        }
        if (isVertical) {
            switch (halignment) {
                case 15: // '\017'
                    i += k / 2 - component.getPreferredSize().width / 2;
                    break;

                case 16: // '\020'
                    i += k - component.getPreferredSize().width;
                    break;
            }
        }
        switch (vlayoutalignment) {
            case 112: // 'p'
                j += container.getSize().height / 2 - getRequiredHeight(container) / 2;
                break;

            case 113: // 'q'
                j += container.getSize().height - getRequiredHeight(container);
                break;
        }
        switch (hlayoutalignment) {
            case 115: // 's'
                i += container.getSize().width / 2 - getRequiredWidth(container) / 2;
                break;

            case 116: // 't'
                i += container.getSize().width - getRequiredWidth(container);
                break;
        }
        k = component.getPreferredSize().width;
        l = component.getPreferredSize().height;
        component.setBounds(i, j, k, l);
    }

    private int getMaxWidthForCol(int i, Component acomponent[]) {
        int j = 0;
        if (rows != 1) {
            for (int k = i; k < rows * columns; k += columns) {
                Component component1 = null;
                try {
                    component1 = acomponent[k];
                } catch (Exception exception1) {
                    continue;
                }
                if (j < component1.getPreferredSize().width) {
                    j = component1.getPreferredSize().width;
                }
                i += columns;
            }

        } else {
            Component component = null;
            try {
                component = acomponent[i];
            } catch (Exception exception) {
            }
            if (j < component.getPreferredSize().width) {
                j = component.getPreferredSize().width;
            }
        }
        return j;
    }

    private int getMaxHeightForRow(int i, Component acomponent[]) {
        int j = 0;
        if (columns != 1) {
            for (int k = columns * i; k < columns * (i + 1); k++) {
                Component component1 = null;
                try {
                    component1 = acomponent[k];
                } catch (Exception exception1) {
                    continue;
                }
                if (j < component1.getPreferredSize().height) {
                    j = component1.getPreferredSize().height;
                }
                i += columns;
            }

        } else {
            Component component = null;
            try {
                component = acomponent[i];
            } catch (Exception exception) {
            }
            if (j < component.getPreferredSize().height) {
                j = component.getPreferredSize().height;
            }
        }
        return j;
    }

    public Dimension preferredLayoutSize(Container container) {
        Dimension dimension = new Dimension(getRequiredWidth(container), getRequiredHeight(container));
        return dimension;
    }

    public Dimension minimumLayoutSize(Container container) {
        Dimension dimension = new Dimension(getMinimumWidth(container), getMinimumHeight(container));
        return dimension;
    }

    private int getRequiredHeight(Container container) {
        Component acomponent[] = container.getComponents();
        int i = borderWidth + cellspacing;
        if (isVertical) {
            for (int j = 0; j < acomponent.length; j++) {
                i += acomponent[j].getPreferredSize().height;
                i += cellspacing;
            }

        } else {
            i += getHeightForRow(acomponent) + cellspacing;
        }
        i += borderWidth;
        return i;
    }

    private int getHeightForRow(Component acomponent[]) {
        int i = 0;
        for (int j = 0; j < acomponent.length; j++) {
            if (i < acomponent[j].getPreferredSize().height) {
                i = acomponent[j].getPreferredSize().height;
            }
        }

        return i;
    }

    private int getWidthForCol(Component acomponent[]) {
        int i = 0;
        for (int j = 0; j < acomponent.length; j++) {
            if (i < acomponent[j].getPreferredSize().width) {
                i = acomponent[j].getPreferredSize().width;
            }
        }

        return i;
    }

    private int getRequiredWidth(Container container) {
        Component acomponent[] = container.getComponents();
        int i = borderWidth + cellspacing;
        if (!isVertical) {
            for (int j = 0; j < acomponent.length; j++) {
                i += acomponent[j].getPreferredSize().width;
                i += cellspacing;
            }

        } else {
            i += getWidthForCol(acomponent) + cellspacing;
        }
        i += borderWidth;
        return i;
    }

    private int getMinimumHeight(Container container) {
        Component acomponent[] = container.getComponents();
        int i = 0;
        i = borderWidth + cellspacing;
        if (isVertical) {
            for (int j = 0; j < acomponent.length; j++) {
                i += acomponent[j].getMinimumSize().height;
                i += cellspacing;
            }

        } else {
            i += getMinHeightForRow(acomponent) + cellspacing;
        }
        i += borderWidth;
        return i;
    }

    private int getMinHeightForRow(Component acomponent[]) {
        int i = 0;
        for (int j = 0; j < acomponent.length; j++) {
            if (i < acomponent[j].getMinimumSize().height) {
                i = acomponent[j].getMinimumSize().height;
            }
        }

        return i;
    }

    private int getMinWidthForCol(Component acomponent[]) {
        int i = 0;
        for (int j = 0; j < acomponent.length; j++) {
            if (i < acomponent[j].getMinimumSize().width) {
                i = acomponent[j].getMinimumSize().width;
            }
        }

        return i;
    }

    private int getMinimumWidth(Container container) {
        Component acomponent[] = container.getComponents();
        int i = 0;
        i = borderWidth + cellspacing;
        if (!isVertical) {
            for (int j = 0; j < acomponent.length; j++) {
                i += acomponent[j].getMinimumSize().width;
                i += cellspacing;
            }

        } else {
            i += getMinWidthForCol(acomponent) + cellspacing;
        }
        i += borderWidth;
        return i;
    }

    public void setCellSpacing(int i) {
        cellspacing = i;
    }

    public void setBorderWidth(int i) {
        borderWidth = i;
    }

    public int getCellSpacing() {
        return cellspacing;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setVComponentAlignment(int i) {
        valignment = i;
    }

    public int getVComponentAlignment() {
        return valignment;
    }

    public void setHComponentAlignment(int i) {
        halignment = i;
    }

    public int getHComponentAlignment() {
        return halignment;
    }

    public void setLayoutVAlignment(int i) {
        vlayoutalignment = i;
    }

    public int getLayoutVAlignment() {
        return vlayoutalignment;
    }

    public void setLayoutHAlignment(int i) {
        hlayoutalignment = i;
    }

    public int getLayoutHAlignment() {
        return hlayoutalignment;
    }

}
