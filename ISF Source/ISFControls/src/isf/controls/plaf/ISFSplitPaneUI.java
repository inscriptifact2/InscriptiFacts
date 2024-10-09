/*
 * @(#)MetalSplitPaneUI.java	1.6 00/02/02
 *
 * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package isf.controls.plaf;

import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Metal split pane.
 * <p/>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * @author Steve Wilson
 * @version 1.6 02/02/00
 */
public class ISFSplitPaneUI extends BasicSplitPaneUI {
    protected JSplitPane splitPane;
    protected ISFSplitPaneDivider divider;
    private int orientation;
    private int lastDragLocation;
    private boolean continuousLayout;
    private boolean dividerKeyboardResize;
    private boolean dividerLocationIsSet;
    protected int dividerSize;
    protected Component nonContinuousLayoutDivider;
    protected boolean draggingHW;
    /**
     * The divider used for non-continuous layout is added to the split pane
     * with this object.
     */
    protected static final String NON_CONTINUOUS_DIVIDER =
            "nonContinuousDivider";

    boolean painted;
    /**
     * If true, setDividerLocation does nothing.
     */
    boolean ignoreDividerLocationChange;

    protected ISFSplitPaneUI.ISFHorizontalLayoutManager layoutManager;

    /**
     * Creates a new MetalSplitPaneUI instance
     */
    public static ComponentUI createUI(JComponent x) {
        return new ISFSplitPaneUI();
    }

    public void installUI(JComponent c) {
        splitPane = (JSplitPane) c;
        dividerLocationIsSet = false;
        dividerKeyboardResize = false;
        installDefaults();
        //installListeners();
        //setLastDragLocation(-1);
    }


    protected void installDefaults() {
        LookAndFeel.installBorder(splitPane, "SplitPane.border");

        if (divider == null) divider = new ISFSplitPaneDivider(this);
        // divider.setBasicSplitPaneUI(this);

        Border b = divider.getBorder();

        if (b == null || !(b instanceof UIResource)) {
            divider.setBorder(UIManager.getBorder("SplitPaneDivider.border"));
        }

        setOrientation(splitPane.getOrientation());

        // This plus 2 here is to provide backwards consistancy. Previously,
        // the old size did not include the 2 pixel border around the divider,
        // it now does.
        splitPane.setDividerSize(((Integer) (UIManager.get("SplitPane.dividerSize"))).intValue());

        divider.setDividerSize(splitPane.getDividerSize());
        dividerSize = divider.getDividerSize();
        splitPane.add(divider, JSplitPane.DIVIDER);

        setContinuousLayout(splitPane.isContinuousLayout());

        resetLayoutManager();

        /* Install the nonContinuousLayoutDivider here to avoid having to
        add/remove everything later. */
        if (nonContinuousLayoutDivider == null) {
            setNonContinuousLayoutDivider(createDefaultNonContinuousLayoutDivider(),
                    true);
        } else {
            setNonContinuousLayoutDivider(nonContinuousLayoutDivider, true);
        }
    }


    /**
     * Resets the layout manager based on orientation and messages it
     * with invalidateLayout to pull in appropriate Components.
     */
    protected void resetLayoutManager() {
        if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
            layoutManager = new ISFSplitPaneUI.ISFHorizontalLayoutManager();
        } else {
            layoutManager = new ISFSplitPaneUI.ISFVerticalLayoutManager();
        }
        splitPane.setLayout(layoutManager);
        layoutManager.updateComponents();
        splitPane.revalidate();
        splitPane.repaint();
    }


    /**
     * Returns the orientation for the JSplitPane.
     */
    public int getOrientation() {
        return orientation;
    }


    /**
     * Set the orientation for the JSplitPane.
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }


    /**
     * Determines wether the JSplitPane is set to use a continuous layout.
     */
    public boolean isContinuousLayout() {
        return continuousLayout;
    }


    /**
     * Turn continuous layout on/off.
     */
    public void setContinuousLayout(boolean b) {
        continuousLayout = b;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * Returns the location of the divider, which may differ from what
     * the splitpane thinks the location of the divider is.
     */
    public int getDividerLocation(JSplitPane jc) {
        if (orientation == JSplitPane.HORIZONTAL_SPLIT)
            return divider.getLocation().x;
        return divider.getLocation().y;
    }


    /**
     * Gets the minimum location of the divider.
     */
    public int getMinimumDividerLocation(JSplitPane jc) {
        int minLoc = 0;
        Component leftC = splitPane.getLeftComponent();

        if ((leftC != null) && (leftC.isVisible())) {
            Insets insets = splitPane.getInsets();
            Dimension minSize = leftC.getMinimumSize();
            if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                minLoc = minSize.width;
            } else {
                minLoc = minSize.height;
            }
            if (insets != null) {
                if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                    minLoc += insets.left;
                } else {
                    minLoc += insets.top;
                }
            }
        }
        return minLoc;
    }


    /**
     * Gets the maximum location of the divider.
     */
    public int getMaximumDividerLocation(JSplitPane jc) {
        Dimension splitPaneSize = splitPane.getSize();
        int maxLoc = 0;
        Component rightC = splitPane.getRightComponent();

        if (rightC != null) {
            Insets insets = splitPane.getInsets();
            Dimension minSize = new Dimension(0, 0);
            if (rightC.isVisible()) {
                minSize = rightC.getMinimumSize();
            }
            if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                maxLoc = splitPaneSize.width - minSize.width;
            } else {
                maxLoc = splitPaneSize.height - minSize.height;
            }
            maxLoc -= dividerSize;
            if (insets != null) {
                if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                    maxLoc -= insets.right;
                } else {
                    maxLoc -= insets.top;
                }
            }
        }
        return Math.max(getMinimumDividerLocation(splitPane), maxLoc);
    }


    /**
     * Messaged after the JSplitPane the receiver is providing the look
     * and feel for paints its children.
     */
    public void finishedPaintingChildren(JSplitPane jc, Graphics g) {
        if (jc == splitPane && getLastDragLocation() != -1 &&
                !isContinuousLayout() && !draggingHW) {
            Dimension size = splitPane.getSize();

            g.setColor(Color.darkGray);
            if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                g.fillRect(getLastDragLocation(), 0, dividerSize - 1,
                        size.height - 1);
            } else {
                g.fillRect(0, lastDragLocation, size.width - 1,
                        dividerSize - 1);
            }
        }
    }

    /**
     * Messaged to reset the preferred sizes.
     */
    public void resetToPreferredSizes(JSplitPane jc) {
        if (splitPane != null) {
            layoutManager.resetToPreferredSizes();
            splitPane.revalidate();
            layoutManager.layoutContainer(splitPane);
            splitPane.repaint();
        }
    }


    /**
     * Sets the location of the divider to location.
     */
    public void setDividerLocation(JSplitPane jc, int location) {
        if (!ignoreDividerLocationChange) {
            dividerLocationIsSet = true;
            splitPane.revalidate();
            splitPane.repaint();
        } else {
            ignoreDividerLocationChange = false;
        }
    }

    public int getLastDragLocation() {
        return -1;
    }


    /**
     * LayoutManager for JSplitPanes that have an orientation of
     * HORIZONTAL_SPLIT.
     * <p/>
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     */
    public class ISFHorizontalLayoutManager implements LayoutManager2 {
        /* left, right, divider. (in this exact order) */
        protected int[] sizes;
        protected Component[] components;
        /**
         * Size of the splitpane the last time layed out.
         */
        private int lastSplitPaneSize;
        /**
         * True if resetToPreferredSizes has been invoked.
         */
        private boolean doReset;
        /**
         * Axis, 0 for horizontal, or 1 for veritcal.
         */
        private int axis;


        ISFHorizontalLayoutManager() {
            this(0);
        }

        ISFHorizontalLayoutManager(int axis) {
            this.axis = axis;
            components = new Component[3];
            components[0] = components[1] = components[2] = null;
            sizes = new int[3];
        }

        //
        // LayoutManager
        //

        /**
         * Does the actual layout.
         */
        public void layoutContainer(Container container) {
            Dimension containerSize = container.getSize();

            // If the splitpane has a zero size then no op out of here.
            // If we execute this function now, we're going to cause ourselves
            // much grief.
            if (containerSize.height <= 0 || containerSize.width <= 0) {
                lastSplitPaneSize = 0;
                return;
            }

            int spDividerLocation = splitPane.getDividerLocation();
            Insets insets = splitPane.getInsets();
            int availableSize = getAvailableSize(containerSize,
                    insets);
            int newSize = getSizeForPrimaryAxis(containerSize);
            int beginLocation = getDividerLocation(splitPane);
            int dOffset = getSizeForPrimaryAxis(insets, true);
            Dimension dSize = (components[2] == null) ? null :
                    components[2].getPreferredSize();

            if ((doReset && !dividerLocationIsSet) || spDividerLocation < 0) {
                resetToPreferredSizes(availableSize);
            } else if (lastSplitPaneSize <= 0 ||
                    availableSize == lastSplitPaneSize || !painted ||
                    (dSize != null &&
                    getSizeForPrimaryAxis(dSize) != sizes[2])) {
                if (dSize != null) {
                    sizes[2] = getSizeForPrimaryAxis(dSize);
                } else {
                    sizes[2] = 0;
                }
                setDividerLocation(spDividerLocation - dOffset, availableSize);
                dividerLocationIsSet = false;
            } else if (availableSize != lastSplitPaneSize) {
                distributeSpace(availableSize - lastSplitPaneSize);
            }
            doReset = false;
            dividerLocationIsSet = false;
            lastSplitPaneSize = availableSize;

            // Reset the bounds of each component
            int nextLocation = getInitialLocation(insets);
            int counter = 0;

            while (counter < 3) {
                if (components[counter] != null &&
                        components[counter].isVisible()) {
                    setComponentToSize(components[counter], sizes[counter],
                            nextLocation, insets, containerSize);
                    nextLocation += sizes[counter];
                }
                switch (counter) {
                    case 0:
                        counter = 2;
                        break;
                    case 2:
                        counter = 1;
                        break;
                    case 1:
                        counter = 3;
                        break;
                }
            }
            if (painted) {
                // This is tricky, there is never a good time for us
                // to push the value to the splitpane, painted appears to
                // the best time to do it. What is really needed is
                // notification that layout has completed.
                int newLocation = getDividerLocation(splitPane);

                if (newLocation != (spDividerLocation - dOffset)) {
                    int lastLocation = splitPane.getLastDividerLocation();

                    ignoreDividerLocationChange = true;
                    try {
                        splitPane.setDividerLocation(newLocation);
                        // This is not always needed, but is rather tricky
                        // to determine when... The case this is needed for
                        // is if the user sets the divider location to some
                        // bogus value, say 0, and the actual value is 1, the
                        // call to setDividerLocation(1) will preserve the
                        // old value of 0, when we really want the divider
                        // location value  before the call. This is needed for
                        // the one touch buttons.
                        splitPane.setLastDividerLocation(lastLocation);
                    } finally {
                        ignoreDividerLocationChange = false;
                    }
                }
            }
        }


        /**
         * Adds the component at place.  Place must be one of
         * JSplitPane.LEFT, RIGHT, TOP, BOTTOM, or null (for the
         * divider).
         */
        public void addLayoutComponent(String place, Component component) {
            boolean isValid = true;

            if (place != null) {
                if (place.equals(JSplitPane.DIVIDER)) {
                    /* Divider. */
                    components[2] = component;
                    sizes[2] = getSizeForPrimaryAxis(component.
                            getPreferredSize());
                } else if (place.equals(JSplitPane.LEFT) ||
                        place.equals(JSplitPane.TOP)) {
                    components[0] = component;
                    sizes[0] = 0;
                } else if (place.equals(JSplitPane.RIGHT) ||
                        place.equals(JSplitPane.BOTTOM)) {
                    components[1] = component;
                    sizes[1] = 0;
                } else if (!place.equals(ISFSplitPaneUI.NON_CONTINUOUS_DIVIDER))
                    isValid = false;
            } else {
                isValid = false;
            }
            if (!isValid)
                throw new IllegalArgumentException("cannot add to layout: " +
                        "unknown constraint: " +
                        place);
            doReset = true;
        }


        /**
         * Returns the minimum size needed to contain the children.
         * The width is the sum of all the childrens min widths and
         * the height is the largest of the childrens minimum heights.
         */
        public Dimension minimumLayoutSize(Container container) {
            int minPrimary = 0;
            int minSecondary = 0;
            Insets insets = splitPane.getInsets();

            for (int counter = 0; counter < 3; counter++) {
                if (components[counter] != null) {
                    Dimension minSize = components[counter].getMinimumSize();
                    int secSize = getSizeForSecondaryAxis(minSize);

                    minPrimary += getSizeForPrimaryAxis(minSize);
                    if (secSize > minSecondary)
                        minSecondary = secSize;
                }
            }
            if (insets != null) {
                minPrimary += getSizeForPrimaryAxis(insets, true) +
                        getSizeForPrimaryAxis(insets, false);
                minSecondary += getSizeForSecondaryAxis(insets, true) +
                        getSizeForSecondaryAxis(insets, false);
            }
            if (axis == 0) {
                return new Dimension(minPrimary, minSecondary);
            }
            return new Dimension(minSecondary, minPrimary);
        }


        /**
         * Returns the preferred size needed to contain the children.
         * The width is the sum of all the childrens preferred widths and
         * the height is the largest of the childrens preferred heights.
         */
        public Dimension preferredLayoutSize(Container container) {
            int prePrimary = 0;
            int preSecondary = 0;
            Insets insets = splitPane.getInsets();

            for (int counter = 0; counter < 3; counter++) {
                if (components[counter] != null) {
                    Dimension preSize = components[counter].
                            getPreferredSize();
                    int secSize = getSizeForSecondaryAxis(preSize);

                    prePrimary += getSizeForPrimaryAxis(preSize);
                    if (secSize > preSecondary)
                        preSecondary = secSize;
                }
            }
            if (insets != null) {
                prePrimary += getSizeForPrimaryAxis(insets, true) +
                        getSizeForPrimaryAxis(insets, false);
                preSecondary += getSizeForSecondaryAxis(insets, true) +
                        getSizeForSecondaryAxis(insets, false);
            }
            if (axis == 0) {
                return new Dimension(prePrimary, preSecondary);
            }
            return new Dimension(preSecondary, prePrimary);
        }


        /**
         * Removes the specified component from our knowledge.
         */
        public void removeLayoutComponent(Component component) {
            for (int counter = 0; counter < 3; counter++) {
                if (components[counter] == component) {
                    components[counter] = null;
                    sizes[counter] = 0;
                    doReset = true;
                }
            }
        }


        //
        // LayoutManager2
        //


        /**
         * Adds the specified component to the layout, using the specified
         * constraint object.
         *
         * @param comp        the component to be added
         * @param constraints where/how the component is added to the layout.
         */
        public void addLayoutComponent(Component comp, Object constraints) {
            if ((constraints == null) || (constraints instanceof String)) {
                addLayoutComponent((String) constraints, comp);
            } else {
                throw new IllegalArgumentException("cannot add to layout: " +
                        "constraint must be a " +
                        "string (or null)");
            }
        }


        /**
         * Returns the alignment along the x axis.  This specifies how
         * the component would like to be aligned relative to other
         * components.  The value should be a number between 0 and 1
         * where 0 represents alignment along the origin, 1 is aligned
         * the furthest away from the origin, 0.5 is centered, etc.
         */
        public float getLayoutAlignmentX(Container target) {
            return 0.0f;
        }


        /**
         * Returns the alignment along the y axis.  This specifies how
         * the component would like to be aligned relative to other
         * components.  The value should be a number between 0 and 1
         * where 0 represents alignment along the origin, 1 is aligned
         * the furthest away from the origin, 0.5 is centered, etc.
         */
        public float getLayoutAlignmentY(Container target) {
            return 0.0f;
        }


        /**
         * Does nothing. If the developer really wants to change the
         * size of one of the views JSplitPane.resetToPreferredSizes should
         * be messaged.
         */
        public void invalidateLayout(Container c) {
        }


        /**
         * Returns the maximum layout size, which is Integer.MAX_VALUE
         * in both directions.
         */
        public Dimension maximumLayoutSize(Container target) {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }


        //
        // New methods.
        //

        /**
         * Marks the receiver so that the next time this instance is
         * layed out it'll ask for the preferred sizes.
         */
        public void resetToPreferredSizes() {
            doReset = true;
        }

        /**
         * Resets the size of the Component at the passed in location.
         */
        protected void resetSizeAt(int index) {
            sizes[index] = 0;
            doReset = true;
        }


        /**
         * Sets the sizes to <code>newSizes</code>.
         */
        protected void setSizes(int[] newSizes) {
            System.arraycopy(newSizes, 0, sizes, 0, 3);
        }


        /**
         * Returns the sizes of the components.
         */
        protected int[] getSizes() {
            int[] retSizes = new int[3];

            System.arraycopy(sizes, 0, retSizes, 0, 3);
            return retSizes;
        }


        /**
         * Returns the width of the passed in Components preferred size.
         */
        protected int getPreferredSizeOfComponent(Component c) {
            return getSizeForPrimaryAxis(c.getPreferredSize());
        }


        /**
         * Returns the width of the passed in Components minimum size.
         */
        int getMinimumSizeOfComponent(Component c) {
            return getSizeForPrimaryAxis(c.getMinimumSize());
        }


        /**
         * Returns the width of the passed in component.
         */
        protected int getSizeOfComponent(Component c) {
            return getSizeForPrimaryAxis(c.getSize());
        }


        /**
         * Returns the available width based on the containers size and
         * Insets.
         */
        protected int getAvailableSize(Dimension containerSize,
                                       Insets insets) {
            if (insets == null)
                return getSizeForPrimaryAxis(containerSize);
            return (getSizeForPrimaryAxis(containerSize) -
                    (getSizeForPrimaryAxis(insets, true) +
                    getSizeForPrimaryAxis(insets, false)));
        }


        /**
         * Returns the left inset, unless the Insets are null in which case
         * 0 is returned.
         */
        protected int getInitialLocation(Insets insets) {
            if (insets != null)
                return getSizeForPrimaryAxis(insets, true);
            return 0;
        }


        /**
         * Sets the width of the component c to be size, placing its
         * x location at location, y to the insets.top and height
         * to the containersize.height less the top and bottom insets.
         */
        protected void setComponentToSize(Component c, int size,
                                          int location, Insets insets,
                                          Dimension containerSize) {
            if (insets != null) {
                if (axis == 0) {
                    c.setBounds(location, insets.top, size,
                            containerSize.height -
                            (insets.top + insets.bottom));
                } else {
                    c.setBounds(insets.left, location, containerSize.width -
                            (insets.left + insets.right), size);
                }
            } else {
                if (axis == 0) {
                    c.setBounds(location, 0, size, containerSize.height);
                } else {
                    c.setBounds(0, location, containerSize.width, size);
                }
            }
        }

        /**
         * If the axis == 0, the width is returned, otherwise the height.
         */
        int getSizeForPrimaryAxis(Dimension size) {
            if (axis == 0) {
                return size.width;
            }
            return size.height;
        }

        /**
         * If the axis == 0, the width is returned, otherwise the height.
         */
        int getSizeForSecondaryAxis(Dimension size) {
            if (axis == 0) {
                return size.height;
            }
            return size.width;
        }

        /**
         * Returns a particular value of the inset identified by the
         * axis and <code>isTop</code><p>
         * axis isTop
         * 0    true    - left
         * 0    false   - right
         * 1    true    - top
         * 1    false   - bottom
         */
        int getSizeForPrimaryAxis(Insets insets, boolean isTop) {
            if (axis == 0) {
                if (isTop) {
                    return insets.left;
                }
                return insets.right;
            }
            if (isTop) {
                return insets.top;
            }
            return insets.bottom;
        }

        /**
         * Returns a particular value of the inset identified by the
         * axis and <code>isTop</code><p>
         * axis isTop
         * 0    true    - left
         * 0    false   - right
         * 1    true    - top
         * 1    false   - bottom
         */
        int getSizeForSecondaryAxis(Insets insets, boolean isTop) {
            if (axis == 0) {
                if (isTop) {
                    return insets.top;
                }
                return insets.bottom;
            }
            if (isTop) {
                return insets.left;
            }
            return insets.right;
        }

        /**
         * Determines the components. This should be called whenever
         * a new instance of this is installed into an existing
         * SplitPane.
         */
        protected void updateComponents() {
            Component comp;

            comp = splitPane.getLeftComponent();
            if (components[0] != comp) {
                components[0] = comp;
                if (comp == null) {
                    sizes[0] = 0;
                } else {
                    sizes[0] = -1;
                }
            }

            comp = splitPane.getRightComponent();
            if (components[1] != comp) {
                components[1] = comp;
                if (comp == null) {
                    sizes[1] = 0;
                } else {
                    sizes[1] = -1;
                }
            }

            /* Find the divider. */
            Component[] children = splitPane.getComponents();
            Component oldDivider = components[2];

            components[2] = null;
            for (int counter = children.length - 1; counter >= 0; counter--) {
                if (children[counter] != components[0] &&
                        children[counter] != components[1] &&
                        children[counter] != nonContinuousLayoutDivider) {
                    if (oldDivider != children[counter]) {
                        components[2] = children[counter];
                    } else {
                        components[2] = oldDivider;
                    }
                    break;
                }
            }
            if (components[2] == null) {
                sizes[2] = 0;
            } else {
                sizes[2] = getSizeForPrimaryAxis(splitPane.getPreferredSize());
            }
        }

        /**
         * Resets the size of the first component to <code>leftSize</code>,
         * and the right component to the remainder of the space.
         */
        void setDividerLocation(int leftSize, int availableSize) {
            boolean lValid = (components[0] != null &&
                    components[0].isVisible());
            boolean rValid = (components[1] != null &&
                    components[1].isVisible());
            boolean dValid = (components[2] != null &&
                    components[2].isVisible());
            int max = availableSize;

            if (dValid) {
                max -= sizes[2];
            }
            leftSize = Math.max(0, Math.min(leftSize, max));
            if (lValid) {
                if (rValid) {
                    sizes[0] = leftSize;
                    sizes[1] = max - leftSize;
                } else {
                    sizes[0] = max;
                    sizes[1] = 0;
                }
            } else if (rValid) {
                sizes[1] = max;
                sizes[0] = 0;
            }
        }

        /**
         * Returns an array of the minimum sizes of the components.
         */
        int[] getPreferredSizes() {
            int[] retValue = new int[3];

            for (int counter = 0; counter < 3; counter++) {
                if (components[counter] != null &&
                        components[counter].isVisible()) {
                    retValue[counter] = getPreferredSizeOfComponent
                            (components[counter]);
                } else {
                    retValue[counter] = -1;
                }
            }
            return retValue;
        }

        /**
         * Returns an array of the minimum sizes of the components.
         */
        int[] getMinimumSizes() {
            int[] retValue = new int[3];

            for (int counter = 0; counter < 2; counter++) {
                if (components[counter] != null &&
                        components[counter].isVisible()) {
                    retValue[counter] = getMinimumSizeOfComponent
                            (components[counter]);
                } else {
                    retValue[counter] = -1;
                }
            }
            retValue[2] = (components[2] != null) ? sizes[2] : -1;
            return retValue;
        }

        /**
         * Resets the components to their preferred sizes.
         */
        void resetToPreferredSizes(int availableSize) {
            // Set the sizes to the preferred sizes (if fits), otherwise
            // set to min sizes and distribute any extra space.
            int[] testSizes = getPreferredSizes();
            int totalSize = 0;

            for (int counter = 0; counter < 3; counter++) {
                if (testSizes[counter] != -1) {
                    totalSize += testSizes[counter];
                }
            }
            if (totalSize > availableSize) {
                testSizes = getMinimumSizes();

                totalSize = 0;
                for (int counter = 0; counter < 3; counter++) {
                    if (testSizes[counter] != -1) {
                        totalSize += testSizes[counter];
                    }
                }
            }
            setSizes(testSizes);
            distributeSpace(availableSize - totalSize);
        }

        /**
         * Distributes <code>space</code> between the two components
         * (divider won't get any extra space) based on the weighting. This
         * attempts to honor the min size of the components.
         */
        void distributeSpace(int space) {
            boolean lValid = (components[0] != null &&
                    components[0].isVisible());
            boolean rValid = (components[1] != null &&
                    components[1].isVisible());

            if (lValid && rValid) {
                double weight = splitPane.getResizeWeight();
                int lExtra = (int) (weight * (double) space);
                int rExtra = (space - lExtra);

                sizes[0] += lExtra;
                sizes[1] += rExtra;

                int lMin = getMinimumSizeOfComponent(components[0]);
                int rMin = getMinimumSizeOfComponent(components[1]);
                boolean lMinValid = (sizes[0] >= lMin);
                boolean rMinValid = (sizes[1] >= rMin);

                if (!lMinValid && !rMinValid) {
                    if (sizes[0] < 0) {
                        sizes[1] += sizes[0];
                        sizes[0] = 0;
                    } else if (sizes[1] < 0) {
                        sizes[0] += sizes[1];
                        sizes[1] = 0;
                    }
                } else if (!lMinValid) {
                    if (sizes[1] - (lMin - sizes[0]) < rMin) {
                        // both below min, just make sure > 0
                        if (sizes[0] < 0) {
                            sizes[1] += sizes[0];
                            sizes[0] = 0;
                        }
                    } else {
                        sizes[1] -= (lMin - sizes[0]);
                        sizes[0] = lMin;
                    }
                } else if (!rMinValid) {
                    if (sizes[0] - (rMin - sizes[1]) < lMin) {
                        // both below min, just make sure > 0
                        if (sizes[1] < 0) {
                            sizes[0] += sizes[1];
                            sizes[1] = 0;
                        }
                    } else {
                        sizes[0] -= (rMin - sizes[1]);
                        sizes[1] = rMin;
                    }
                }
                if (sizes[0] < 0) {
                    sizes[0] = 0;
                }
                if (sizes[1] < 0) {
                    sizes[1] = 0;
                }
            } else if (lValid) {
                sizes[0] = Math.max(0, sizes[0] + space);
            } else if (rValid) {
                sizes[1] = Math.max(0, sizes[1] + space);
            }
        }
    }


    /**
     * LayoutManager used for JSplitPanes with an orientation of
     * VERTICAL_SPLIT.
     * <p/>
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     */
    public class ISFVerticalLayoutManager extends
            ISFSplitPaneUI.ISFHorizontalLayoutManager {
        public ISFVerticalLayoutManager() {
            super(1);
        }
    }

    protected void setNonContinuousLayoutDivider(Component newDivider) {
        setNonContinuousLayoutDivider(newDivider, true);
    }


    /**
     * Sets the divider to use.
     */
    protected void setNonContinuousLayoutDivider(Component newDivider,
                                                 boolean rememberSizes) {
        if (nonContinuousLayoutDivider != null && splitPane != null) {
            splitPane.remove(nonContinuousLayoutDivider);
        }
        nonContinuousLayoutDivider = newDivider;
        if (nonContinuousLayoutDivider != null && splitPane != null) {
            nonContinuousLayoutDivider.setLocation(-1000, -1000);

            /* Needs to remove all the components and readd them! YECK! */
            // This is all done so that the nonContinuousLayoutDivider will
            // be drawn on top of the other components, without this, one
            // of the heavyweights will draw over the divider!
            Component leftC = splitPane.getLeftComponent();
            Component rightC = splitPane.getRightComponent();
            int lastLocation = splitPane.
                    getDividerLocation();

            if (leftC != null)
                splitPane.setLeftComponent(null);
            if (rightC != null)
                splitPane.setRightComponent(null);
            splitPane.remove(divider);
            splitPane.add(nonContinuousLayoutDivider, ISFSplitPaneUI.
                    NON_CONTINUOUS_DIVIDER,
                    splitPane.getComponentCount());
            splitPane.setLeftComponent(leftC);
            splitPane.setRightComponent(rightC);
            splitPane.add(divider, JSplitPane.DIVIDER);
            if (rememberSizes)
                splitPane.setDividerLocation(lastLocation);
            splitPane.revalidate();
            splitPane.paintImmediately(splitPane.getX(),
                    splitPane.getY(),
                    splitPane.getWidth(),
                    splitPane.getHeight());
        }
    }

    /**
     * Returns the default non continuous layout divider, which is an
     * instanceof Canvas that fills the background in dark gray.
     */
    protected Component createDefaultNonContinuousLayoutDivider() {
        return new Canvas() {
            public void paint(Graphics g) {
                if (!isContinuousLayout() && getLastDragLocation() != -1) {
                    Dimension size = splitPane.getSize();

                    g.setColor(Color.darkGray);
                    if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                        g.fillRect(0, 0, dividerSize - 1, size.height - 1);
                    } else {
                        g.fillRect(0, 0, size.width - 1, dividerSize - 1);
                    }
                }
            }
        };
    }


}
