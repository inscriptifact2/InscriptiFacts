package com.isf.app.view.containers;

import com.isf.app.controllers.MenuControlManager;
import com.isf.app.models.PathNode;
import com.isf.app.models.TreeChild;
import com.isf.app.models.TreeParent;
import com.isf.app.view.controls.InscriptDyanBorder;
import com.isf.app.view.controls.QueryMenu;
import com.isf.app.view.controls.QueryMenuItem;
import com.isf.app.view.controls.TrimmedIconMenu;
import com.isf.app.view.gui.PathSelectionManager;
import com.isf.app.view.listeners.MainSearchListener;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.TrimmedPanel;
import isf.controls.view.gui.VerticalLayout;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class QueryScrollPane extends JScrollPane implements MouseListener, MouseMotionListener {

 
    Logger logger = LogManager.getLogger(com.isf.app.view.containers.QueryScrollPane.class);
    public QueryScrollPane latestPane;
    public QueryScrollPane previousPane;
    private int paneIndex;
    private Dimension dim;
    public JPanel compPanel;
    private VerticalLayout vl;
    public Container parentPanel;
    public JMenu menu;
    private Component component;
    private MainSearchListener mainSearchListener;
    private MouseListener mouseL;
    public boolean isClicked = false;
    public boolean isEnabled = true;
    private int start = 0;
    boolean fromMove = true;
    Object obj = null;
    int nMenus = 0;
    int nHeight = 0;
    //  static int count=0;
    public QueryScrollPane() {
        if (ResolutionManager.getInstance().getScreen().height > 800) {
            nMenus = 18;
            nHeight = 29;
        } else {
            nMenus = 12;
            nHeight = 29;
        }
        latestPane = null;
        previousPane = null;
        paneIndex = 1;
        dim = new Dimension(61, nHeight);
        compPanel = new TrimmedPanel(new Insets(0, 0, 0, 0));
        compPanel.setLayout(vl = new VerticalLayout());
        compPanel.setBackground(Color.white);
        compPanel.setPreferredSize(dim);
        setViewportView(compPanel);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // addMouseMotionListener(this);
    }

    public QueryScrollPane(Container container) {
        this();
        parentPanel = container;
    }

    public Component add(Component component) {
        //   this.component=component;
        component.addMouseListener(this);
        // component.addMouseListener(mouseL);
        component.addMouseMotionListener(this);
        boolean flag = ((AbstractButton) component).isSelected();
        if (flag) {
            component.setForeground(new Color(0xac5539));
        } else {
            component.setForeground(Color.black);
            component.setBackground(Color.white);
        }
        Dimension dimension = component.getPreferredSize();
        dimension.height = nHeight;
        component.setSize(dimension);
        compPanel.add(component);
        compPanel.validate();
        component.addNotify();
        Rectangle rectangle = component.getBounds();
        dim.width = (int) rectangle.getWidth();
        dim.height = (int) rectangle.getHeight();
        compPanel.addNotify();
        return component;
    }

    public void addMainSearchListener(MainSearchListener mListener) {

        mainSearchListener = mListener;

    }

    public void addMouseList(MouseListener ml) {
        mouseL = ml;
    }

    public void setVisible(boolean flag) {
        super.setVisible(flag);
    }

    public void removeSubMenu(JMenu jmenu) {
        try {
            if (jmenu != null) {
                Component acomponent[] = compPanel.getComponents();
                for (int i = 0; i < acomponent.length; i++) {
                    jmenu.add((JMenuItem) acomponent[i]);
                    acomponent[i].removeMouseListener(this);
                }

            }
            if (latestPane != null) {
                latestPane.removeSubMenu(menu);
            }
            parentPanel.remove(this);

        } catch (Exception exception) {
            logger.error(exception);
        }
    }

    public Dimension getPreferredSize() {
        int i = compPanel.getComponentCount();
        if (i == 0) {
            return dim;
        }
        dim = vl.preferredLayoutSize(compPanel);
        Dimension dimension = new Dimension(dim.width + (i > nMenus ? 15 : 0), dim.height);                   //todo changedheretoremovescroll
        //  Dimension dimension = new Dimension(dim.width , dim.height);                                  //todo changedheretoremovescrollbar abd put cell entry scroll
        if (i < nMenus) {
            dimension.height = dim.height + 1;
            setBorder(new InscriptDyanBorder(Color.black, 1, 0, 0, 0));
        } else {
            dimension.height = ((int) (dim.height / i)) * nMenus;
            setBorder(new InscriptDyanBorder(Color.black, 1, 0, 0, 1));
        }
        compPanel.setPreferredSize(dim);
        return dimension;
    }

    public void setModel(Component acomponent[]) {
        compPanel.removeAll();
        for (int i = 0; i < acomponent.length; i++) {
            add(acomponent[i]);
        }

        compPanel.validate();
        compPanel.repaint();
    }

    public void setModal(TreeParent treeparent) {
        compPanel.removeAll();
        int i = 0;
        for (int j = treeparent.getChildCount(); i < j; i++) {
            TreeParent treeparent1 = (TreeParent) treeparent.getChildAt(i);
            TrimmedIconMenu trimmediconmenu = new TrimmedIconMenu(treeparent1.toString());
            addMenu(trimmediconmenu, treeparent1);
            add(trimmediconmenu);
        }

        compPanel.validate();
        compPanel.repaint();
    }

    public void addMenu(QueryMenu jmenu, TreeParent treeparent) {
        int i = treeparent.getChildCount();
        for (int j = 0; j < i; j++) {
            TreeChild treechild = treeparent.getChildAt(j);
            if (treechild.isLeaf()) {
                jmenu.add(new QueryMenuItem(treechild.toString()));
            } else {
                QueryMenu querymenu = new QueryMenu(treechild.toString());
                addMenu(querymenu, (TreeParent) treechild);
                jmenu.add(querymenu);
            }
        }

    }

    public void mouseExited(MouseEvent mouseevent) {
        if (isEnabled) {
            if (mouseevent.getSource() instanceof TrimmedIconMenu) {
                TrimmedIconMenu trimmediconmenu = (TrimmedIconMenu) mouseevent.getSource();
                trimmediconmenu.setForeground(Color.black);
                MenuControlManager.setMenuHasControl(false);
            }
        }
    }

    public void mousePressed(MouseEvent mouseevent) {
    }

    public void mouseClicked(MouseEvent mouseevent) {

        if (isEnabled) {
            if (isClicked)
                return;
            if (mouseevent.getSource() instanceof TrimmedIconMenu) {
                if (latestPane != null) {
                    latestPane.removeSubMenu(menu);
                }
                // ((QueryMenuPanel) parentPanel).removeLabels();
                Object obj = mouseevent.getSource();
                ((AbstractButton) obj).setBackground(Color.lightGray);
                ((AbstractButton) obj).setForeground(new Color(0xac5539));
                if (mouseevent.getSource() instanceof JMenu)//Jmenu
                {
                    /* menu = (JMenu) mouseevent.getSource();
                     Component acomponent[] = menu.getMenuComponents();

                     latestPane = new QueryScrollPane(parentPanel);
                     latestPane.addMainSearchListener(mainSearchListener);
                     latestPane.setModel(acomponent);
                     parentPanel.add(latestPane);
                     Rectangle rectangle = menu.getBounds();
                     Point coord = menu.getLocationOnScreen() ;
                     Point pan = parentPanel.getLocationOnScreen();
                     Dimension dimension = latestPane.getPreferredSize();
                     Rectangle rectangle1 = parentPanel.getBounds();
                   //  int j = parentPanel.getComponentCount();
                     //todo modify the below to find out the correct place of the scrollpane....................
                     int endpoint = (int)(dimension.getHeight() + coord.y ) ;
                     int pepoint = (int)(rectangle1.getHeight() + pan.y );
                     int diff=coord.y-pan.y;


                     if(pepoint < endpoint)
                         diff -=  endpoint-pepoint;

                     latestPane.setBounds(count != 0 && count != 1 ? (int) (getBounds().getX() + getBounds().getWidth()) : 0,diff,dimension.width, dimension.height);

                     latestPane.setPaneIndex(paneIndex + 1);
                     latestPane.previousPane = this;
                     latestPane.setVisible(true);
                     count++;*/

                    menu = (JMenu) mouseevent.getSource();
                    Component acomponent[] = menu.getMenuComponents();
                    Point point = ((AbstractButton) obj).getLocationOnScreen();
                    Point point1 = parentPanel.getLocationOnScreen();
                    Point point2 = getParent().getLocationOnScreen();
                    latestPane = new QueryScrollPane(parentPanel);
                    latestPane.addMainSearchListener(mainSearchListener);
                    latestPane.setModel(acomponent);
                    parentPanel.add(latestPane, JLayeredPane.MODAL_LAYER);
                    Rectangle rectangle = getBounds();
                    Dimension dimension = latestPane.getPreferredSize();
                    Rectangle rectangle1 = parentPanel.getBounds();
                    int i = ((point.y - point2.y) + dimension.height) - (int) rectangle1.getHeight() - (nHeight + 1);
                    //int j = parentPanel.getComponentCount();
                    int j = getPanelCount();

                    latestPane.setBounds(j != 0 && j != 1 ? (int) (rectangle.getX() + rectangle.getWidth()) : 0, i <= 0 ? point.y - point1.y : point.y - point1.y - i, dimension.width, dimension.height);
                    latestPane.setPaneIndex(paneIndex + 1);
                    latestPane.previousPane = this;
                    latestPane.setVisible(true);


                } else {
                    menu = null;
                }
                parentPanel.validate();
                parentPanel.repaint();
                isClicked = true;
            }
            ((QueryMenuPanel) parentPanel).addLabels();
        }
    }

    public void mouseEntered(MouseEvent mouseevent) {

        if (isEnabled) {
            ((JComponent) mouseevent.getSource()).grabFocus();
            if (latestPane != null) {
                latestPane.removeSubMenu(menu);
            }
            latestPane = null;
            menu = null;
            if (!isClicked && mouseevent.getSource() instanceof TrimmedIconMenu) {
                return;
            }
            //  ((QueryMenuPanel) parentPanel).removeLabels();
            Object obj = mouseevent.getSource();
            ((AbstractButton) obj).setBackground(Color.lightGray);
            ((AbstractButton) obj).setForeground(new Color(0xac5539));
            if (mouseevent.getSource() instanceof JMenu) //JMenu
            {
                /*menu = (JMenu) mouseevent.getSource();
                Component acomponent[] = menu.getMenuComponents();*/
                /* Point point = ((AbstractButton) obj).getLocationOnScreen();
                Point point1 = parentPanel.getLocationOnScreen();
                Point point2 = getParent().getLocationOnScreen();
                latestPane = new QueryScrollPane(parentPanel);
                latestPane.addMainSearchListener(mainSearchListener);
                latestPane.setModel(acomponent);
                parentPanel.add(latestPane);
                Rectangle rectangle = getBounds();
                Dimension dimension = latestPane.getPreferredSize();
                Rectangle rectangle1 = parentPanel.getBounds();
                int i = ((point.y - point2.y) + dimension.height) - (int) rectangle1.getHeight() - 30;
                int j = parentPanel.getComponentCount();
                latestPane.setBounds(j != 0 && j != 1 ? (int) (rectangle.getX() + rectangle.getWidth()) : 0, i <= 0 ? point.y - point1.y : point.y - point1.y - i, dimension.width, dimension.height);*/
                /* latestPane = new QueryScrollPane(parentPanel);
                 latestPane.addMainSearchListener(mainSearchListener);
                 latestPane.setModel(acomponent);
                 parentPanel.add(latestPane);
                 Rectangle rectangle = menu.getBounds();
                 Point coord = menu.getLocationOnScreen() ;
                 Point pan = parentPanel.getLocationOnScreen();
                 Dimension dimension = latestPane.getPreferredSize();
                 Rectangle rectangle1 = parentPanel.getBounds();
              //   int j = parentPanel.getComponentCount();
                 //todo modify the below to find out the correct place of the scrollpane....................
                 int endpoint = (int)(dimension.getHeight() + coord.y ) ;
                 int pepoint = (int)(rectangle1.getHeight() + pan.y );
                 int diff=coord.y-pan.y;


                 if(pepoint < endpoint)
                     diff -=  endpoint-pepoint;

                 latestPane.setBounds(count != 0 && count != 1 ? (int) (getBounds().getX() + getBounds().getWidth()) : 0,diff,dimension.width, dimension.height);

                 latestPane.setPaneIndex(paneIndex + 1);
                 latestPane.previousPane = this;
                 latestPane.setVisible(true);
                 count++;*/
                menu = (JMenu) mouseevent.getSource();
                Component acomponent[] = menu.getMenuComponents();
                Point point = ((AbstractButton) obj).getLocationOnScreen();
                Point point1 = parentPanel.getLocationOnScreen();
                Point point2 = getParent().getLocationOnScreen();
                latestPane = new QueryScrollPane(parentPanel);
                latestPane.addMainSearchListener(mainSearchListener);
                latestPane.setModel(acomponent);
                parentPanel.add(latestPane, JLayeredPane.MODAL_LAYER);
                Rectangle rectangle = getBounds();
                Dimension dimension = latestPane.getPreferredSize();
                Rectangle rectangle1 = parentPanel.getBounds();
                int i = ((point.y - point2.y) + dimension.height) - (int) rectangle1.getHeight() - (nHeight + 1);
                //int j = parentPanel.getComponentCount();
                int j = getPanelCount();
                latestPane.setBounds((j != 0 && j != 1) ? (int) (rectangle.getX() + rectangle.getWidth()) : 0, (i <= 0) ? point.y - point1.y : point.y - point1.y - i, dimension.width, dimension.height);
                //System.out.println("i is:" + i + "\n j is:" + j + "\n bounds is:" + latestPane.getBounds());

                latestPane.setPaneIndex(paneIndex + 1);
                latestPane.previousPane = this;
                latestPane.setVisible(true);

                MenuControlManager.setMenuHasControl(true);
            } else {
                menu = null;
            }
            parentPanel.validate();
            parentPanel.repaint();
        }

    }

    public void mouseReleased(MouseEvent mouseevent) {

        if (isEnabled) {
            if (mouseevent.getSource() instanceof TrimmedIconMenu) {
                // System.out.println("clicked on sp****************************************");
                return;
            }
            JMenuItem jmenuitem = (JMenuItem) mouseevent.getSource();
            PathNode apathnode[] = new PathNode[paneIndex];
            getSelectionArray(apathnode);
            try {
                if (!jmenuitem.isSelected()) {
                    apathnode[paneIndex - 1] = new PathNode(jmenuitem.getActionCommand(), true);
                    PathSelectionManager.getInstance().push(apathnode);
                    jmenuitem.setSelected(true);
                    mainSearchListener.querySelection(1);
                } else {
                    apathnode[paneIndex - 1] = new PathNode(jmenuitem.getActionCommand(), false);
                    boolean flag = PathSelectionManager.getInstance().pop(apathnode);
                    jmenuitem.setSelected(false);
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                JOptionPane.showMessageDialog(parentPanel, illegalargumentexception.getMessage(), "Selection Error", 0);
            }
        }
    }

    public void deselectMenuItem(JMenuItem jmenuitem) {
        try {
            String as[] = {
                    jmenuitem.getActionCommand()
            };
            PathSelectionManager.getInstance().reset();
            if (latestPane != null) {
                latestPane.removeSubMenu(menu);
            }
            if (parentPanel != null) {
                //((QueryMenuPanel) parentPanel).addLabels();
                // System.out.println("called from qsp.deseclt menu");
                ((QueryMenuPanel) parentPanel).addPanels();
            }
        } catch (Exception exception) {
        }
    }

    private void selectParents() {
        if (previousPane != null) {
            previousPane.selectParents();
        }
        if (menu != null) {
            menu.setSelected(true);
        }
    }

    private void deselectRoot() {
        if (previousPane == null) {
            menu.setSelected(false);
            return;
        }
        QueryScrollPane queryscrollpane;
        for (queryscrollpane = previousPane; queryscrollpane != null; queryscrollpane = queryscrollpane.previousPane) {
            if (queryscrollpane.previousPane == null) {
                break;
            }
        }

        queryscrollpane.menu.setSelected(false);
    }

    private void setChildsSelected(boolean flag) {
        Component acomponent[] = compPanel.getComponents();
        for (int i = 0; i < acomponent.length; i++) {
            if (acomponent[i] instanceof JMenu) {
                unselectChilds((JMenu) acomponent[i]);
            } else {
                ((JMenuItem) acomponent[i]).setSelected(false);
            }
        }

    }

    private void unselectChilds(JMenu jmenu) {
        Component acomponent[] = jmenu.getMenuComponents();
        for (int i = 0; i < acomponent.length; i++) {
            JMenuItem jmenuitem = (JMenuItem) acomponent[i];
            if (jmenuitem instanceof JMenu) {
                //todo: unselect  this and comment out bottom line... QueryMenu Changes
                unselectChilds(((JMenu) ((QueryMenu) jmenuitem)));
                //unselectChilds(((JMenu) jmenuitem));
            }
            jmenuitem.setSelected(false);
            jmenu.add(jmenuitem);
        }

        jmenu.setSelected(false);
    }

    public void getSelectionArray(PathNode apathnode[]) {
        if (menu != null) {
            apathnode[paneIndex - 1] = new PathNode(menu.getActionCommand(), menu.isSelected());
        }
        if (previousPane != null) {
            previousPane.getSelectionArray(apathnode);
        }
    }

    public void setPaneIndex(int i) {
        paneIndex = i;
    }

    public void reset() {
        if (latestPane != null) {
            latestPane.removeSubMenu(menu);
        }
        setChildsSelected(false);
        PathSelectionManager.getInstance().reset();
        if (parentPanel != null) {
            ((QueryMenuPanel) parentPanel).removeLabels();
        }
        ///  validate();
        //  repaint();
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }


    public void mouseDragged(MouseEvent event) {
    }

    public void mouseMoved(MouseEvent event) {

        int i = compPanel.getComponentCount();

        if (i <= nMenus)
            return;
        int cur = 0;
        for (; cur < i; cur++) {
            if (compPanel.getComponent(cur).equals(event.getSource())) {
                break;
            }
        }

        if (i > nMenus) {
            if (start > 0 && cur == start && fromMove) {
                getViewport().setViewPosition(new Point(0, (int) (getViewport().getViewPosition().getY() - nHeight)));
                start--;
                fromMove = false;
            } else if ((i > start + nMenus) && (cur == start + (nMenus - 1)) && fromMove) {
                getViewport().setViewPosition(new Point(0, (int) (getViewport().getViewPosition().getY() + nHeight)));
                start++;
                fromMove = false;
            }
            Thread th = new Thread() {
                public void run() {
                    try {
                        sleep(1000);
                        fromMove = true;
                    } catch (Exception ie) {

                    }
                }
            };
            th.start();
            //     SwingUtilities.invokeLater(th);

        }
    }

    public int getPanelCount() {
        int count = 0;
        int x = parentPanel.getComponentCount();
        for (int i = 0; i < x; i++) {
            if (parentPanel.getComponent(i) instanceof QueryScrollPane)
                count++;
        }
        // System.out.println("count:"+count);
        return count;
    }

}
