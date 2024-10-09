package com.isf.app.view.containers;

import com.isf.app.view.controls.TrimmedIconButton;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.gui.VerticalLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Dec 28, 2003
 * Time: 8:07:15 AM
 * To change this template use Options | File Templates.
 */
public class IMapGP extends JPanel implements ActionListener, MouseListener {
    //  Container c;
    boolean dontshow = false;
    ActionListener al;
    Point loc = new Point(0, 0);
    public int current = 0;
    ImageIcon ic1, ic2, icbtn;
    TrimmedIconButton act;
    ImageIcon msg;
    JLabel l;
    ImageIcon ic3;
    TrimmedIconButton tb1;
    TrimmedIconButton tic;
    TrimmedIconButton msg1;

    public IMapGP() {
        super();
        //c = this;
        // setOpaque(false);
        setLayout(null);

        Dimension dscreen = ResolutionManager.getInstance().getScreen();
        loc = new Point((int) dscreen.getWidth() / 2, (int) dscreen.getHeight() / 2);
        setBackground(new Color(252, 250, 182));
        setBorder(new LineBorder(Color.black, 2));
        addMouseListener(this);
        ic1 = ResolutionManager.getInstance().loadImage("images/cb.jpg");
        ic2 = ResolutionManager.getInstance().loadImage("images/cb_sel.jpg");
        icbtn = ResolutionManager.getInstance().getImageIcon("images/buttgo.gif");
        msg = ResolutionManager.getInstance().loadImage("images/load_msg.jpg");
        l = new JLabel(msg);
        ic3 = ResolutionManager.getInstance().loadImage("images/msg.jpg");

        act = new TrimmedIconButton(icbtn);
        act.setActionCommand("SPATIALGO");
        tb1 = new TrimmedIconButton(ic1);
        tic = new TrimmedIconButton(ResolutionManager.getInstance().loadImage("images/close_b.jpg"));
        msg1 = new TrimmedIconButton(ic3);


    }

    public void showLoading() {
        removeAll();
        setLayout(new BorderLayout());


        /*  l.setText("<html><body><font face='Trebuchet MS' color='#ae5537' size='8pts'><center><b>&nbsp;&nbsp;Loading Reference Image - " +
 "Please Wait ...</b></center></font></body></html>");     */
        setBorder(new LineBorder(Color.black, 2));

//      l.setIcon( msg)  ;
        add(l);
        setBounds(loc.x - 200, loc.y - 25, msg.getIconWidth() + 2, msg.getIconHeight());
        //   invalidate();
        //   validate();
        setVisible(true);
        // toFront();
        current = 1;

    }

    public void addActionListener(ActionListener al) {
        this.al = al;
        act.addActionListener(this.al);

    }

    public void showMsg(ActionListener temp) {

        // getParent().setVisible(true);
        if (current != 2) {
            setVisible(false);

            removeAll();
            setLayout(new VerticalLayout());

            tb1.setActionCommand("ShowAgain");
            setBounds(loc.x - 125, loc.y - 90, tb1.getIcon().getIconWidth() + 4, ic3.getIconHeight() + tb1.getIcon().getIconHeight() + tic.getIcon().getIconHeight() + 2);
            setBorder(new LineBorder(Color.black, 2));


            tb1.addActionListener(this);
            tb1.setFocusPainted(false);
            add(msg1);
            add(tb1);
            tic.addActionListener(temp);
            tic.setFocusPainted(false);
            tic.addMouseListener(this);
            add(tic);

            //revalidate();
            //validate();
            //repaint();

            current = 2;

        }
        //System.out.println("c.has"+getComponentCount() );
        setVisible(true);
        //toFront();

        // getParent().setVisible(true);
    }

    public void setAction(Point p) {
        setVisible(false);
        removeAll();
        setLayout(new BorderLayout());
        setBounds(p.x, p.y - 20, icbtn.getIconWidth(), icbtn.getIconHeight() + 4);
        setBorder(null);

        act.addMouseListener(this);
        setOpaque(false);
        //  act.setOpaque(false);
        add(act);

        setVisible(true);
        //toFront();

        current = 3;
        //System.out.println("button shown");
    }


    /*  public void stateChanged(ChangeEvent event) {
          dontshow = ((JCheckBox) event.getSource()).isSelected();
      }*/


    public void mouseEntered(MouseEvent me) {
        if (me.getSource() instanceof TrimmedIconButton)
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        //  CursorManager.changeCursor(Cursor.HAND_CURSOR) ;


    }

    public void mouseExited(MouseEvent me) {
        if (me.getSource() instanceof TrimmedIconButton)
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        // CursorManager.changeCursor(-1) ;
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("ShowAgain")) {
            TrimmedIconButton tib = (TrimmedIconButton) event.getSource();
            dontshow = !dontshow;
            if (dontshow)
                tib.setIcon(ic2);
            else
                tib.setIcon(ic1);

            // tib.setSelected(dontshow);


        }
    }
    /* public void setVisible(boolean flag) {
        if (flag) {
           // FocusHandler focushandler = (FocusHandler) getComponent(0);
          //  focushandler.setFocus();
            if (getParent() != null) {
                getParent().validate();
            }
        }
        else{
        removeAll();
        }
        super.setVisible(flag);
    }*/

}


