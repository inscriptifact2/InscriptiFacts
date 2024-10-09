/**
 * Package Declaration
 */
package isf.controls.plaf;

/**
 * Java Imports
 */


import isf.controls.utils.CursorManager;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.ISFSplitPane;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

/**
 * Description:  This class provides the look and feel for the IPSplitPaneDivider <p>
 * User: ravishankar
 * Date: Aug 9, 2003
 */
public class IPSplitPaneDivider extends BasicSplitPaneDivider
        implements PropertyChangeListener, MouseListener {

    protected int defaultDividerSize = 0;
    protected int thumbSize = 0;
    protected static Color lightShadow;
    protected static Color mediumShadow;
    protected static Color darkShadow;
    protected static Color control;
    protected static Color focus;
    protected ImageIcon icon1, icon2, current;

    // public TrimmedIconButton button;
    static final Cursor crosshairCursor =
            Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    static final Cursor defaultCursor =
            Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

    /**
     * Constructor with SplitPane UI
     *
     * @param ui spelitpaneUI
     */
    public IPSplitPaneDivider(IPSplitPaneUI ui) {
        super(ui);
        //  setBackground(Color.blue);
        // setLayout(new BorderLayout());
        /* if(splitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT )
         button = new TrimmedIconButton("","images/buttonknob.jpg","images/buttonknoba.jpg",1);
         else
         button = new TrimmedIconButton("","images/buttonvert.jpg","images/buttonverta.jpg",1);
         button.addActionListener(this);
         button.setSelected(false);     */
        if (splitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
            icon1 = ResolutionManager.getInstance().getImageIcon("images/buttonknob.jpg");
            icon2 = ResolutionManager.getInstance().getImageIcon("images/buttonknoba.jpg");
        } else {
            icon1 = ResolutionManager.getInstance().getImageIcon("images/buttonverta.jpg");
            icon2 = ResolutionManager.getInstance().getImageIcon("images/buttonvert.jpg");

        }
        current = icon1;
        lightShadow = Color.white;
        mediumShadow = Color.white;
        darkShadow = Color.blue;
        control = Color.red;
        focus = Color.blue;
        setDividerSize(thumbSize);
        addMouseListener(this);
        // add(button);
    }

    /**
     * Constructor with  New SplitPane UI
     *
     * @param newUI spelitpaneUI
     */
    public void setIPSplitPaneUI(IPSplitPaneUI newUI) {
    }

    /**
     * Get Thumb Bounds
     *
     * @return Rectangle
     */
    public Rectangle getThumbBounds() {
        Dimension size = getSize();
        int x = size.width - 2 * thumbSize;
        int y = size.height - 2 * thumbSize;
        if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
            return new Rectangle(0, y, thumbSize, thumbSize);
        } else {
            return new Rectangle(x, 0, thumbSize, thumbSize);
        }
    }

    public void setDividerSize(int newSize) {
        if (newSize > 0) {
            super.setDividerSize(10);
            defaultDividerSize = 10;
            thumbSize = 8;
        } else {
            super.setDividerSize(0);
            defaultDividerSize = 0;
            thumbSize = 0;
        }
        //  setPreferredSize(new Dimension(splitPane.getWidth(),newSize));
    }

    /**
     * get the divider size of splitpane divider
     *
     * @return int
     */
    public int getDividerSize() {
        return defaultDividerSize;
    }

    /**
     * paints divider with graphics
     *
     * @param g graphics
     */
    public void paint(Graphics g) {
        Color bgColor = getBackground();
        Dimension size = getSize();
        g.setColor(bgColor);//Color.darkGray );
        if (getBasicSplitPaneUI().getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
            int center = (size.width - 1) / 2;
            int y = size.height - 2 * thumbSize;
            g.fillRect(0, 0, size.width, size.height);
        } else {
            int center = (size.height - 1) / 2;
            int x = size.width - 2 * thumbSize;
            g.fillRect(0, 0, size.width, size.height);
        }
        if (splitPane.getDividerSize() > 0) {
            /* if(splitPane.getDividerLocation() >0 )
                 g.drawImage(icon1.getImage(), (size.width-icon1.getIconWidth()) /2, (size.height-icon1.getIconHeight() )/2,this);
             else*/
            g.drawImage(current.getImage(), (size.width - current.getIconWidth()) / 2, (size.height - current.getIconHeight()) / 2, this);
            g.setColor(Color.darkGray);
            g.drawRect(0, 0, size.width, size.height);

        }

    }

    /**
     * paint top
     *
     * @param g
     * @param x
     */
    public static void paintTop(Graphics g, int x) {
        g.setColor(control);
        //g.fillRect(x+2, 0, 6, 2);
    }

    /**
     * paint the bottom
     *
     * @param g
     * @param x
     * @param y
     */
    public static void paintBottom(Graphics g, int x, int y) {
        g.setColor(control);
        //g.fillRect(x+2, y-1, 6, 2);
    }

    /**
     * paints left
     *
     * @param g
     * @param y
     */
    public static void paintLeft(Graphics g, int y) {
        g.setColor(control);
        //g.fillRect(0, y+2, 2, 6);
    }

    /**
     * paints right of divider
     *
     * @param g
     * @param y
     * @param x
     */
    public static void paintRight(Graphics g, int y, int x) {
        g.setColor(control);
        //g.fillRect(x-1, y+2, 2, 6);
    }

    /**
     * drawing border
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param lightShadow
     * @param mediumShadow
     * @param darkShadow
     * @param isRaised
     */
    private void drawBorder(Graphics g, int x, int y, int w, int h,
                            Color lightShadow, Color mediumShadow,
                            Color darkShadow, boolean isRaised) {
        /*g.translate(x, y);
        if (isRaised) {
            g.setColor(lightShadow);
            g.drawLine(0, 0, w-1, 0);
            g.drawLine(0, 0, 0, h-1);
            g.setColor(darkShadow);
            g.drawLine(w-1, 0, w-1, h-1);
            g.drawLine(0, h-1, w-1, h-1);
            g.setColor(mediumShadow);
            g.drawLine(w-1-1, 2, w-1-1, h-1-1);
            g.drawLine(2, h-1-1, w-1-1, h-1-1);
        }
        else {
            g.setColor(mediumShadow);
            g.drawLine(0, 0, w-1, 0);
            g.drawLine(0, 0, 0, h-1);
            g.setColor(lightShadow);
            g.drawLine(w-1, 1, w-1, h-1);
            g.drawLine(1, h-1, w-1, h-1);
            g.setColor(darkShadow);
            g.drawLine(1, 1, w-2, 1);
            g.drawLine(1, 1, 1, h-2);
        }
        g.translate(-x, -y);*/
    }

    /*   public void actionPerformed(ActionEvent e) {
           if(splitPane.getDividerLocation() >0)
               splitPane.setDividerLocation(0);
           else if(splitPane.getDividerLocation() ==0)
               splitPane.setDividerLocation(splitPane.getLastDividerLocation() );
           button.setSelected(!button.isSelected() );


       }
   */
    /* public Dimension getPreferredSize(){

         return new Dimension(splitPane.getWidth() , 0);
     }*/

    public void mouseClicked(MouseEvent e) {
        // splitPane.setDividerLocation()
        if (splitPane instanceof ISFSplitPane) {
            // System.out.println("****"+splitPane+"****"+splitPane.getDividerLocation() );
            ((ISFSplitPane) splitPane).updateLocation();
            changeImage();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        CursorManager.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseExited(MouseEvent e) {
    }

    public void changeImage() {
        current = (current == icon1) ? icon2 : icon1;
    }
}
