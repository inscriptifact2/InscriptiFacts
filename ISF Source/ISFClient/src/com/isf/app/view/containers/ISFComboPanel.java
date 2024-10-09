package com.isf.app.view.containers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Mar 6, 2004
 * Time: 9:24:10 AM
 * To change this template use Options | File Templates.
 */
public class ISFComboPanel extends JPanel implements ActionListener, MouseListener {
    JButton btn;
    JLabel lbl;
    JPopupMenu pop;
    JList list;
    JScrollPane scroll;
    private Vector data;


    public ISFComboPanel() {
        super();
        //  setBackground(Color.white);
        setBorder(new LineBorder(Color.black, 1));
        setLayout(null);
        setOpaque(true);
        init();
    }

    public ISFComboPanel(Vector data) {
        this();
        setData(data);
    }


    private void init() {
        data = new Vector();
        btn = new JButton("S");
        btn.setBackground(Color.gray);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(20, 20));
        lbl = new JLabel("hai", JLabel.LEFT);
        lbl.setBackground(Color.white);
        lbl.setForeground(Color.black);
        lbl.setPreferredSize(new Dimension(50, 20));
        lbl.setOpaque(false);
        lbl.setBounds(1, 1, 50, 20);
        btn.setBounds(51, 1, 20, 20);
        btn.addActionListener(this);

        add(lbl);
        add(btn);
        pop = new JPopupMenu();
        /*     list = new JList(data);
             scroll = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
             pop.setLayout(new BorderLayout());
             pop.add(scroll);
             pop.setPreferredSize(150,data.size())*/


    }

    public Dimension getPreferredSize() {
        return new Dimension(72, 20);
    }

    public Insets getInsets() {
        return new Insets(1, 0, 1, 0);
    }

    public void setData(Vector d) {
        data = d;
        list = new JList(data);
        scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pop.setLayout(new BorderLayout());
        scroll.setPreferredSize(new Dimension(150, data.size() * 17));
        pop.add(scroll);
        pop.setPreferredSize(new Dimension(150, data.size() * 17));
    }


    public Object getSelectedItem() {
        return "";
    }

    public void addActionListener(ActionListener al) {

    }

    public void setSelectedIndex(int ind) {

    }

    public int getSelectedIndex() {
        return -1;
    }

    public boolean isPopupVisible() {
        return pop.isVisible();
    }

    public void hidePopup() {
        //  pop.setVisible(false);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(btn)) {
            if (!pop.isVisible()) {
                // System.out.println("yes");
                pop.setBounds(0, 22, (int) (pop.getPreferredSize().getWidth()), (int) pop.getPreferredSize().getHeight());
                pop.setVisible(true);
            }
            //    pop.show(scroll,0,22);
        }
    }

    public void mouseClicked(MouseEvent event) {
    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }
}
