package com.isf.app.view.gui;

import java.awt.*;
import java.io.Serializable;

public class TableLayout
        implements LayoutManager, Serializable {

    private int rows;
    private int columns;
    private int cellspacing;
    private int borderWidth;

    public TableLayout(int i, int j, int k, int l) {
        rows = i;
        columns = j;
        cellspacing = k;
        borderWidth = l;
        if (i < 1) {
            i = 1;
        }
        if (j < 1) {
            j = 1;
        }
    }

    public void addLayoutComponent(String s, Component component) {
    }

    public void removeLayoutComponent(Component component) {
    }

    public void layoutContainer(Container container) {
        Insets insets = container.getInsets();
        Component acomponent[] = container.getComponents();
        int i = 0;
        int j = borderWidth + cellspacing;
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
                component.setBounds(insets.left + i1, j, k1, l);
                i++;
                i1 += k1 + cellspacing;
            }

            j += l + cellspacing;
        }

    }

    private int getMaxWidthForCol(int i, Component acomponent[]) {
        int j = 0;
        for (int k = i; k < rows * columns; k += columns) {
            Component component = null;
            try {
                component = acomponent[k];
            } catch (Exception exception) {
                continue;
            }
            if (j < component.getPreferredSize().width) {
                j = component.getPreferredSize().width;
            }
            i += columns;
        }

        return j;
    }

    private int getMaxMinWidthForCol(int i, Component acomponent[]) {
        int j = 0;
        for (int k = i; k < rows * columns; k += columns) {
            Component component = null;
            try {
                component = acomponent[k];
            } catch (Exception exception) {
                continue;
            }
            if (j < component.getMaximumSize().width) {    //getMinimumSize()
                j = component.getMaximumSize().width;      //getMinimumSize()
            }
            i += columns;
        }

        return j;
    }

    private int getMaxHeightForRow(int i, Component acomponent[]) {
        int j = 0;
        for (int k = columns * i; k < columns * (i + 1); k++) {
            Component component = null;
            try {
                component = acomponent[k];
            } catch (Exception exception) {
                continue;
            }
            if (j < component.getPreferredSize().height) {
                j = component.getPreferredSize().height;
            }
        }

        return j;
    }

    private int getMaxMinHeightForRow(int i, Component acomponent[]) {
        int j = 0;
        for (int k = columns * i; k < columns * (i + 1); k++) {
            Component component = null;
            try {
                component = acomponent[k];
            } catch (Exception exception) {
                continue;
            }
            if (j < component.getMaximumSize().height) { //getMinimumSize()
                j = component.getMaximumSize().height;   //getMinimumSize()
            }
            i += columns;
        }

        return j;
    }

    public Dimension preferredLayoutSize(Container container) {
        Dimension dimension = new Dimension(getRequiredWidth(container), getRequiredHeight(container));
        return dimension;
    }

    public Dimension minimumLayoutSize(Container container) {
        Dimension dimension = new Dimension(getMinRequiredWidth(container), getMinRequiredHeight(container));
        return dimension;
    }

    private int getMinRequiredWidth(Container container) {
        Component acomponent[] = container.getComponents();
        int i = borderWidth + cellspacing;
        for (int j = 0; j < columns; j++) {
            i += getMaxMinWidthForCol(j, acomponent);
            i += cellspacing;
        }

        i += borderWidth;
        return i;
    }

    private int getMinRequiredHeight(Container container) {
        Component acomponent[] = container.getComponents();
        int i = borderWidth + cellspacing;
        for (int j = 0; j < rows; j++) {
            i += getMaxMinHeightForRow(j, acomponent);
            i += cellspacing;
        }

        i += borderWidth;
        return i;
    }

    private int getRequiredHeight(Container container) {
        Component acomponent[] = container.getComponents();
        int i = borderWidth + cellspacing;
        for (int j = 0; j < rows; j++) {
            i += getMaxHeightForRow(j, acomponent);
            i += cellspacing;
        }

        i += borderWidth;
        return i;
    }

    private int getRequiredWidth(Container container) {
        Component acomponent[] = container.getComponents();
        int i = borderWidth + cellspacing;
        for (int j = 0; j < columns; j++) {
            i += getMaxWidthForCol(j, acomponent);
            i += cellspacing;
        }

        i += borderWidth;
        return i;
    }

    public void setColumns(int i) {
        columns = i;
    }

    public void setRows(int i) {
        rows = i;
    }

    public void setCellSpacing(int i) {
        cellspacing = i;
    }

    public void setBorderWidth(int i) {
        borderWidth = i;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getCellSpacing() {
        return cellspacing;
    }

    public int getBorderWidth() {
        return borderWidth;
    }
}
