package com.isf.app.view.containers;

import com.isf.app.controllers.MenuControlManager;
import com.isf.app.models.PathNode;
import com.isf.app.view.controls.TrimmedLabel;
import com.isf.app.view.gui.PathSelectionManager;
import com.isf.app.view.listeners.MenuListener;
import isf.common.Debug;
import isf.common.utils.LogManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import org.apache.log4j.Logger;

public class QueryMenuPanel extends JLayeredPane
        implements MouseListener, MenuListener {

    private QueryScrollPane pane;
    private static String pathStr[] = {
            "Corpus", "Medium", "Find Site", "Time Period", "Language", "Script", "Repository"
    };
    private TrimmedLabel label[];
    // private static TrimmedIconMenu m;
    private Point p;
    private Point p1;
    private String str;
    int height;
    //  private static Dimension d;
    public static boolean isClicked = false;
    Logger logger = LogManager.getLogger(com.isf.app.view.containers.QueryMenuPanel.class);

    //  private MainSearchListener mainSearchListener;

    public QueryMenuPanel() {
        label = new TrimmedLabel[2];
        p = new Point(1, 90);
        p1 = new Point(0, 0);
        height = 29;
        setLayout(null);
        p = new Point(1, 90);
        super.setBackground(new Color(0xa49585));
        addMouseListener(this);
        for (int i = 0; i < label.length; i++) {
            label[i] = new TrimmedLabel(new Insets(20, 20, 20, 20));
            label[i].setBackground(new Color(0xd9d9d9));
            label[i].setForeground(new Color(0xac5539));
            label[i].setFont(new Font("Arial", 1, 11));
            label[i].setBorder(BorderFactory.createLineBorder(new Color(0xffffff)));
            label[i].setOpaque(true);
        }
        MenuControlManager.getInstance().setMenuListener(this);

    }

    public QueryMenuPanel(LayoutManager layoutmanager) {
        //    super(layoutmanager);
        label = new TrimmedLabel[2];
        p = new Point(1, 90);
        p1 = new Point(0, 0);
        height = 29;
        p = new Point(1, 90);
        super.setBackground(new Color(0xa49585));
        addMouseListener(this);
        for (int i = 0; i < label.length; i++) {
            label[i] = new TrimmedLabel(new Insets(1, 1, 1, 1));
        }

    }

    public void addQueryScrollPane(QueryScrollPane queryscrollpane) {
        pane = queryscrollpane;
    }
    /*   public void addMainSearchListener(MainSearchListener mListener)
      {
          mainSearchListener=mListener;
      }  */

    public int getStringIndex(String s) {
        for (int i = 0; i < pathStr.length; i++) {
            if (s.equals(pathStr[i])) {
                return i;
            }
        }

        return -1;
    }

    public void addLabels() {

        try {
            for (int i = 0; i < label.length; i++) {
                remove(label[i]);
            }

        } catch (Exception exception) {
        }
        //new CleanUpThread();
        populateLabels();
        //  MenuControlManager.pollCleanup();
    }

    public void addPanels() {
        MenuControlManager.pollCleanup();
    }

    public void removeChilds() {
        try {
            for (int i = 0; i < label.length; i++) {
                remove(label[i]);
            }

        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    public void removeLabels() {
        try {
            for (int i = 0; i < label.length; i++) {
                remove(label[i]);

            }

        } catch (Exception exception) {
        }
        revalidate();
        validate();
        repaint();
    }


    public String getLabelText(PathNode apathnode[][]) {
        String s = "";
        String s1 = "";
        str = "";
        Debug.debug("  in getLabel text ");
        for (int i = 0; i < apathnode.length; i++) {
            if (apathnode[i][0] != null) {
                str += s1 + "[";
                String s2 = "";
                for (int j = 0; j < apathnode[i].length; j++) {
                    if (apathnode[i][j] != null) {
                        str += apathnode[i][j] == null ? "" : s2 + apathnode[i][j].name;
                        s2 = ",";
                    }
                }

                str += "] ";
                s1 = " (OR) ";
            }
        }

        s = s + str;
        return s;
    }

    public void hideAll() {
        if (pane != null) {
            try {
                pane.latestPane.removeSubMenu(pane.menu);
                removeLabels();
                validate();
                repaint();
            } catch (Exception exception) {
            }
        }
    }


    public void removePane() {
        if (pane != null) {
            try {
                pane.latestPane.removeSubMenu(pane.menu);
                validate();
                repaint();
            } catch (Exception exception) {
            }
        }
    }

    public void mouseEntered(MouseEvent mouseevent) {

        /*if (isClicked) {
            System.out.println("QMP ENTERED");
            grabFocus();
            if (pane != null) {
                try {
                    if (pane.latestPane != null) {
                        pane.latestPane.removeSubMenu(pane.menu);
                    }
                    addLabels();
                    validate();
                    repaint();
                } catch (Exception exception) {
                }
            }
        } */


    }

    public void mouseClicked(MouseEvent mouseevent) {
        /*if (isClicked) {
           System.out.println("QMP CLICK");
            if (pane != null) {
                try {
                    if (pane.latestPane != null) {
                        pane.latestPane.removeSubMenu(pane.menu);
                    }
                    validate();
                    repaint();
                    pane.isClicked = false;
                } catch (Exception exception) {
                }

            }
        }*/

    }

    public void mouseExited(MouseEvent mouseevent) {
    }

    public void mousePressed(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
    }



    public void cleanUp() {
        if (pane.isShowing()) {
            //removeAll();
            removePane();
            //  populateLabels();
            /*p = pane.getLocationOnScreen();
            java.awt.Rectangle rectangle = getBounds();
            p1 = getLocationOnScreen();
            Hashtable hashtable = PathSelectionManager.getInstance().paths;
            int i = 0;
            Dimension dimension = getSize();
            if (hashtable.size() == 0) {
                revalidate();
                validate();
                repaint();
                return;
            }
            for (Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements();) {
                PathNode pathnode = (PathNode) enumeration.nextElement();
                int j = getStringIndex(pathnode.name);
                PathNode apathnode[][] = (PathNode[][]) hashtable.get(pathnode);
                label[i].setText(getLabelText(apathnode));
                Debug.debug("index = " + j);
                label[i].setBounds(1, (p.y - p1.y) + height * j, dimension.width - 10, height);

                add(label[i]);
                i++;
            }
     */
            revalidate();
            validate();
            repaint();
        }
    }

    static {
        // m = new TrimmedIconMenu("m");
        //  Dimension dimension = m.getPreferredSize();
        //  d = new Dimension(500, dimension.height);
    }

    private void populateLabels() {


        p = pane.getLocationOnScreen();
        java.awt.Rectangle rectangle = getBounds();
        p1 = getLocationOnScreen();
        Hashtable hashtable = PathSelectionManager.getInstance().paths;
        int i = 0;
        Dimension dimension = getSize();
        if (hashtable.size() == 0) {
            revalidate();
            validate();
            repaint();
            return;
        }
        for (Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements();) {
            PathNode pathnode = (PathNode) enumeration.nextElement();
            int j = getStringIndex(pathnode.name);
            PathNode apathnode[][] = (PathNode[][]) hashtable.get(pathnode);
            label[i].setText(getLabelText(apathnode));
            Debug.debug("index = " + j);
            label[i].setBounds(1, (p.y - p1.y) + height * j, dimension.width - 10, height);


            add(label[i], JLayeredPane.PALETTE_LAYER);
            i++;
        }


    }
}
