/*
 * @(#)MetalSplitPaneDivider.java	1.14 00/02/02
 *
 * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package isf.controls.plaf;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalLookAndFeel;


/**
 * Metal's split pane divider
 * <p/>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * @author Steve Wilson
 * @author Ralph kar
 * @version 1.14 02/02/00
 */
class ISFSplitPaneDivider extends Container implements MouseListener, PropertyChangeListener {
    protected static final int ONE_TOUCH_SIZE = 6;
    protected static final int ONE_TOUCH_OFFSET = 2;
    private Border border;
    protected ISFSplitPaneUI splitPaneUI;
    /*  private MetalBumps bumps = new MetalBumps(10, 10,
                                  MetalLookAndFeel.getControlHighlight(),
                                  MetalLookAndFeel.getControlDarkShadow(),
                                  MetalLookAndFeel.getControl() );

      private MetalBumps focusBumps = new MetalBumps(10, 10,
                                  MetalLookAndFeel.getPrimaryControlHighlight(),
                                  MetalLookAndFeel.getPrimaryControlDarkShadow(),
                                  MetalLookAndFeel.getPrimaryControl() ); */

    private int inset = 2;
    protected int dividerSize = 0;
    protected int orientation;
    protected JSplitPane splitPane;

    private Color controlColor = MetalLookAndFeel.getControl();
    private Color primaryControlColor = MetalLookAndFeel.getPrimaryControl();
    private JButton divBut = new JButton();

    public ISFSplitPaneDivider(ISFSplitPaneUI ui) {
        setBorder(new BevelBorder(BevelBorder.RAISED, Color.yellow, Color.black));
        splitPaneUI = ui;
        if (splitPane == null)
            splitPane = splitPaneUI.getSplitPane();
        orientation = splitPane.getOrientation();
        setBackground(UIManager.getColor("SplitPane.background"));
        divBut.addMouseListener(this);
        divBut.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        setLayout(new ISFDividerLayout());

    }

    public void paint(Graphics g) {


//       border.paintBorder(this, g, 0, 0, getSize().width, getSize().height);

        if (splitPane.hasFocus()) {
            //    usedBumps = focusBumps;

            g.setColor(primaryControlColor);
        } else {
            //    usedBumps = bumps;
            g.setColor(controlColor);
        }
        Rectangle clip = g.getClipBounds();
        Insets insets = getInsets();
        g.fillRect(clip.x, clip.y, clip.width, clip.height);
        Dimension size = getSize();
        size.width -= inset * 2;
        size.height -= inset * 2;
        int drawX = inset;
        int drawY = inset;
        if (insets != null) {
            size.width -= (insets.left + insets.right);
            size.height -= (insets.top + insets.bottom);
            drawX += insets.left;
            drawY += insets.top;
        }
        //  usedBumps.setBumpArea(size);
        //   usedBumps.paintIcon(this, g, drawX, drawY);
        super.paint(g);
        if (border != null)
            border.paintBorder(this, g, 0, 0, getSize().width, getSize().height);

    }

    /**
     * Creates and return an instance of JButton that can be used to
     * collapse the left component in the metal split pane.
     */
    protected JButton createLeftOneTouchButton() {
        JButton b = new JButton() {
            // Sprite buffer for the arrow image of the left button
            int[][] Lbuffer = {{0, 0, 0, 2, 2, 0, 0, 0, 0},
                               {0, 0, 2, 1, 1, 1, 0, 0, 0},
                               {0, 2, 1, 1, 1, 1, 1, 0, 0},
                               {2, 1, 1, 1, 1, 1, 1, 1, 0},
                               {0, 3, 3, 3, 3, 3, 3, 3, 3}};

            public void setBorder(Border b) {
            }

            public void paint(Graphics g) {
                JSplitPane splitPane = getSplitPaneFromSuper();
                if (splitPane != null) {
                    int oneTouchSize = getOneTouchSizeFromSuper();
                    int orientation = getOrientationFromSuper();
                    int blockSize = Math.min(getDividerSize(),
                            oneTouchSize);

                    // Initialize the color array
                    Color[] colors = {
                        this.getBackground(),
                        MetalLookAndFeel.getPrimaryControlDarkShadow(),
                        MetalLookAndFeel.getPrimaryControlInfo(),
                        MetalLookAndFeel.getPrimaryControlHighlight()};

                    // Fill the background first ...
                    g.setColor(this.getBackground());
                    g.fillRect(0, 0, this.getWidth(),
                            this.getHeight());

                    // ... then draw the arrow.
                    if (getModel().isPressed()) {
                        // Adjust color mapping for pressed button state
                        colors[1] = colors[2];
                    }
                    if (orientation == JSplitPane.VERTICAL_SPLIT) {
                        // Draw the image for a vertical split
                        for (int i = 1; i <= Lbuffer[0].length; i++) {
                            for (int j = 1; j < blockSize; j++) {
                                if (Lbuffer[j - 1][i - 1] == 0) {
                                    continue;
                                } else {
                                    g.setColor(colors[Lbuffer[j - 1][i - 1]]);
                                }
                                g.drawLine(i, j, i, j);
                            }
                        }
                    } else {
                        // Draw the image for a horizontal split
                        // by simply swaping the i and j axis.
                        // Except the drawLine() call this code is
                        // identical to the code block above. This was done
                        // in order to remove the additional orientation
                        // check for each pixel.
                        for (int i = 1; i <= Lbuffer[0].length; i++) {
                            for (int j = 1; j < blockSize; j++) {
                                if (Lbuffer[j - 1][i - 1] == 0) {
                                    // Nothing needs
                                    // to be drawn
                                    continue;
                                } else {
                                    // Set the color from the
                                    // color map
                                    g.setColor(colors[Lbuffer[j - 1][i - 1]]);
                                }
                                // Draw a pixel
                                g.drawLine(j, i, j, i);
                            }
                        }
                    }
                }
            }

            // Don't want the button to participate in focus traversable.
            public boolean isFocusTraversable() {
                return false;
            }
        };
        b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        b.setFocusPainted(false);
        b.setBorderPainted(false);


        return b;
    }

    /**
     * Creates and return an instance of JButton that can be used to
     * collapse the right component in the metal split pane.
     */
    protected JButton createRightOneTouchButton() {
        JButton b = new JButton() {
            // Sprite buffer for the arrow image of the right button
            int[][] buffer = {{2, 2, 2, 2, 2, 2, 2, 2},
                              {0, 1, 1, 1, 1, 1, 1, 3},
                              {0, 0, 1, 1, 1, 1, 3, 0},
                              {0, 0, 0, 1, 1, 3, 0, 0},
                              {0, 0, 0, 0, 3, 0, 0, 0}};

            public void setBorder(Border border) {
            }

            public void paint(Graphics g) {
                JSplitPane splitPane = getSplitPaneFromSuper();
                if (splitPane != null) {
                    int oneTouchSize = getOneTouchSizeFromSuper();
                    int orientation = getOrientationFromSuper();
                    int blockSize = Math.min(getDividerSize(),
                            oneTouchSize);

                    // Initialize the color array
                    Color[] colors = {
                        this.getBackground(),
                        MetalLookAndFeel.getPrimaryControlDarkShadow(),
                        MetalLookAndFeel.getPrimaryControlInfo(),
                        MetalLookAndFeel.getPrimaryControlHighlight()};

                    // Fill the background first ...
                    g.setColor(this.getBackground());
                    g.fillRect(0, 0, this.getWidth(),
                            this.getHeight());

                    // ... then draw the arrow.
                    if (getModel().isPressed()) {
                        // Adjust color mapping for pressed button state
                        colors[1] = colors[2];
                    }
                    if (orientation == JSplitPane.VERTICAL_SPLIT) {
                        // Draw the image for a vertical split
                        for (int i = 1; i <= buffer[0].length; i++) {
                            for (int j = 1; j < blockSize; j++) {
                                if (buffer[j - 1][i - 1] == 0) {
                                    continue;
                                } else {
                                    g.setColor(colors[buffer[j - 1][i - 1]]);
                                }
                                g.drawLine(i, j, i, j);
                            }
                        }
                    } else {
                        // Draw the image for a horizontal split
                        // by simply swaping the i and j axis.
                        // Except the drawLine() call this code is
                        // identical to the code block above. This was done
                        // in order to remove the additional orientation
                        // check for each pixel.
                        for (int i = 1; i <= buffer[0].length; i++) {
                            for (int j = 1; j < blockSize; j++) {
                                if (buffer[j - 1][i - 1] == 0) {
                                    // Nothing needs
                                    // to be drawn
                                    continue;
                                } else {
                                    // Set the color from the
                                    // color map
                                    g.setColor(colors[buffer[j - 1][i - 1]]);
                                }
                                // Draw a pixel
                                g.drawLine(j, i, j, i);
                            }
                        }
                    }
                }
            }

            // Don't want the button to participate in focus traversable.
            public boolean isFocusTraversable() {
                return false;
            }
        };
        b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        return b;
    }

    /**
     * Used to layout a MetalSplitPaneDivider. Layout for the divider
     * involves appropriately moving the left/right buttons around.
     * <p/>
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of MetalSplitPaneDivider.
     */
    public class ISFDividerLayout implements LayoutManager {
        public void layoutContainer(Container c) {
            JSplitPane splitPane = getSplitPaneFromSuper();


            int orientation = getOrientationFromSuper();
            int oneTouchSize = getOneTouchSizeFromSuper();
            int oneTouchOffset = getOneTouchOffsetFromSuper();
            Insets insets = getInsets();

            // This layout differs from the one used in BasicSplitPaneDivider.
            // It does not center justify the oneTouchExpadable buttons.
            // This was necessary in order to meet the spec of the Metal
            // splitpane divider.
            if (divBut != null &&
                    c == ISFSplitPaneDivider.this) {
                if (splitPane.isOneTouchExpandable()) {
                    if (orientation == JSplitPane.VERTICAL_SPLIT) {
                        int extraY = (insets != null) ? insets.top : 0;
                        int blockSize = getDividerSize();

                        if (insets != null) {
                            blockSize -= (insets.top + insets.bottom);
                        }
                        blockSize = Math.min(blockSize, oneTouchSize);
                        /*  if(splitPane.getDividerLocation() ==0)
                          rightButton.setBounds(oneTouchOffset +
                                                oneTouchSize * 2, extraY,
                                                blockSize * 2, blockSize);
                          else*/
                        divBut.setText("" + splitPane.getDividerLocation());
                        divBut.setBounds(oneTouchOffset, extraY, splitPane.getWidth(), blockSize);
                        // blockSize * 2, blockSize);
                    } else {
                        int blockSize = getDividerSize();
                        int extraX = (insets != null) ? insets.left : 0;

                        if (insets != null) {
                            blockSize -= (insets.left + insets.right);
                        }
                        blockSize = Math.min(blockSize, oneTouchSize);


                        /* if(splitPane.getDividerLocation()==0)
                         rightButton.setBounds(extraX, oneTouchOffset +
                                               oneTouchSize * 2, blockSize,
                                               blockSize * 2);
                         else*/
                        divBut.setText("" + splitPane.getDividerLocation());
                        divBut.setBounds(extraX, oneTouchOffset, blockSize, splitPane.getHeight());
                        divBut.setBackground(Color.red);
                        //   blockSize, blockSize * 2);
                    }
                } else {
                    /*if(splitPane.getDividerLocation()==0)
                    rightButton.setBounds(-5, -5, 1, 1);
                    else
                    leftButton.setBounds(-5, -5, 1, 1);*/
                    //   System.out.println("something is wrong");
                    divBut.setBounds(-5, -5, 1, 1);
                }
            }
        }

        public Dimension minimumLayoutSize(Container c) {
            return new Dimension(0, 0);
        }

        public Dimension preferredLayoutSize(Container c) {
            return new Dimension(0, 0);
        }

        public void removeLayoutComponent(Component c) {
        }

        public void addLayoutComponent(String string, Component c) {
        }
    }

    /*
     * The following methods only exist in order to be able to access protected
     * members in the superclass, because these are otherwise not available
     * in any inner class.
     */

    int getOneTouchSizeFromSuper() {
        return ONE_TOUCH_SIZE;
    }

    int getOneTouchOffsetFromSuper() {
        return ONE_TOUCH_OFFSET;
    }

    int getOrientationFromSuper() {
        return orientation;
    }

    JSplitPane getSplitPaneFromSuper() {
        return splitPane;
    }


    public void mouseClicked(MouseEvent e) {

        splitPane.setDividerSize(splitPane.getLastDividerLocation());

    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        divBut.setCursor(Cursor.getDefaultCursor());
    }

    public void mouseExited(MouseEvent e) {

    }

    /**
     * Sets the size of the divider to <code>newSize</code>. That is
     * the width if the splitpane is <code>HORIZONTAL_SPLIT</code>, or
     * the height of <code>VERTICAL_SPLIT</code>.
     */
    public void setDividerSize(int newSize) {
        dividerSize = newSize;
    }


    /**
     * Returns the size of the divider, that is the width if the splitpane
     * is HORIZONTAL_SPLIT, or the height of VERTICAL_SPLIT.
     */
    public int getDividerSize() {
        return dividerSize;
    }


    public void setBorder(Border border) {
        Border oldBorder = this.border;

        this.border = border;
    }

    /**
     * If a border has been set on this component, returns the
     * border's insets, else calls super.getInsets.
     *
     * @return the value of the insets property.
     * @see #setBorder
     */
    public Insets getInsets() {
        Border border = getBorder();

        if (border != null) {
            return border.getBorderInsets(this);
        }
        return super.getInsets();
    }

    /**
     * Returns dividerSize x dividerSize
     */
    public Dimension getPreferredSize() {
        return new Dimension(getDividerSize(), getDividerSize());
    }

    public Border getBorder() {
        return border;
    }

    /**
     * Returns dividerSize x dividerSize
     */
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public void propertyChange(PropertyChangeEvent evt) {
    }
}
