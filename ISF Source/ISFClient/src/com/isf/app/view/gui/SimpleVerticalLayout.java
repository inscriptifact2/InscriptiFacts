package com.isf.app.view.gui;

import java.awt.*;
import java.io.Serializable;

public class SimpleVerticalLayout
        implements LayoutManager, Serializable {

    int hy;
    int wx;

    public SimpleVerticalLayout() {
        hy = 0;
        wx = 0;
    }

    public void addLayoutComponent(String s, Component component) {
    }

    public void removeLayoutComponent(Component component) {
    }

    public void layoutContainer(Container container) {
        Insets insets = container.getInsets();
        Component acomponent[] = container.getComponents();
        int i = insets.top;
        int j = 0;
        int k = 0;
        for (int l = 0; l < acomponent.length; l++) {
            Dimension dimension = acomponent[l].getPreferredSize();
            if (dimension.width > k) {
                k = dimension.width;
            }
        }

        for (int i1 = 0; i1 < acomponent.length; i1++) {
            Dimension dimension1 = acomponent[i1].getPreferredSize();
            acomponent[i1].setBounds(insets.left, i, dimension1.width, dimension1.height);
            i += dimension1.height;
            if (dimension1.width > j) {
                j = dimension1.width;
            }
            container.add(acomponent[i1]);
        }

        hy = i + insets.top + insets.bottom;
        wx = insets.left + j + insets.right;
    }

    public Dimension preferredLayoutSize(Container container) {
        if (container != null) {
            layoutContainer(container);
        }
        return new Dimension(wx, hy);
    }

    public Dimension minimumLayoutSize(Container container) {
        return new Dimension(wx, hy);
    }
}
