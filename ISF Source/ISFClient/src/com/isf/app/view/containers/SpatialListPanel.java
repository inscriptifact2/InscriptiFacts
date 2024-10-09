package com.isf.app.view.containers;

import com.isf.app.view.controls.InscriptDyanBorder;
import com.isf.app.view.listeners.SpatialListSelectionListener;
import isf.common.view.gui.SpatialListImage;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpatialListPanel extends JPanel
        implements ActionListener {

    private SpatialButtonsPanel bp;
    // private InscriptButton begin;
    private SpatialListScrollPane pane;
    public ActionListener al;
    private JLabel msgLabel;

    /* public SpatialListPanel(Vector vector)
     {
         this();
         setSpatialData(vector);
     }*/


    public void setDefault() {
        pane.setDefault();
    }

    public SpatialListPanel() {
        al = null;
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.black);
        //   begin = new InscriptButton("images/QM_beginsearch_go_active.jpg");
        Dimension d = ResolutionManager.getInstance().getScreen();
        JLabel jlabel = new JLabel();

        /*   if (d.width == 1024) {
               begin.setScaledStatus(true);
               begin.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/QM_beginsearch_go_select.jpg"));
               begin.setDisabledIcon(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/QM_beginsearch_go_disabled.jpg"));
               jlabel = new JLabel(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/QM_curverightblack.jpg"));

           } else {
               begin.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/QM_beginsearch_go_select.jpg"));
               begin.setDisabledIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/QM_beginsearch_go_disabled.jpg"));
               jlabel = new JLabel(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/QM_curverightblack.jpg"));

           }
           begin.setBorder(null);
           begin.setBorderPainted(false);
           begin.setActionCommand("SPATIALGO");
           begin.addActionListener(al);
           begin.addActionListener(this);
           begin.setMargin(new Insets(0, 0, 0, 0));
           JPanel jpanel = new JPanel();
           jpanel.setLayout(new FlowLayout(0, 0, 0));
           jpanel.setAlignmentY(0.0F);
           jpanel.setAlignmentX(0.0F);
           jpanel.add(begin);
           add(jpanel, "North");*/
        String as[] = {
                //"images/Qm_textviews_active.gif", "images/Qm_textviews_select.gif", "images/Qm_textviews_inactive.gif", "TEXTVIEWS"
                "images/sp_refAct.jpg", "images/sp_refRoll.jpg", "images/sp_refDis.jpg", "TEXTVIEWS"
        };
        String as1[] = {
                // "images/Qm_imagelist_active.gif", "images/Qm_imagelist_select.gif", "images/Qm_imagelist_inactive.gif", "IMAGELIST"
                "images/sp_imgAct.jpg", "images/sp_imgRoll.jpg", "images/sp_imgDis.jpg", "IMAGELIST"
        };
        bp = new SpatialButtonsPanel(as, as1);


        add(bp, "North");
        msgLabel = new JLabel("");
        // msgLabel.setBorder(new LineBorder(Color.blue)) ;
        msgLabel.setBackground(new Color(0xd9d9d9));
        msgLabel.setOpaque(true);
        TrimmedPanel tempLabel = new TrimmedPanel(new Insets(5, 10, 5, 10)) {
            public Dimension getPreferredSize() {
                return new Dimension((int) bp.getPreferredSize().getWidth() - 14, (int) bp.getPreferredSize().getHeight() * 2);
            }
        };
        tempLabel.setBackground(new Color(0xd9d9d9));
        tempLabel.setLayout(new BorderLayout(0, 0));
        tempLabel.add(msgLabel);
        msgLabel.setText("<html><body><font face='Trebuchet MS' size=8pts color=#ae5537><b>Click on one image for" +
                " spatial search</b></font></body></html>");


        TrimmedPanel trimmedpanel = new TrimmedPanel(new Insets(0, 14, 0, 1));
        trimmedpanel.setBackground(Color.black);
        trimmedpanel.setLayout(new BorderLayout(0, 0));
        trimmedpanel.add(tempLabel, "North");
        trimmedpanel.setBorder(new InscriptDyanBorder(Color.black, 0, 0, 0, 1));
        TrimmedPanel trimmedpanel1 = new TrimmedPanel(new Insets(0, 0, 0, 0));
        trimmedpanel1.setLayout(new BorderLayout());
        trimmedpanel1.setBackground(new Color(0xd9d9d9));
        trimmedpanel1.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 0, 1));
        pane = new SpatialListScrollPane();
        trimmedpanel1.add(pane, "Center");

        add(jlabel, "South");
        trimmedpanel.add(trimmedpanel1, "Center");
        add(trimmedpanel, "Center");
        bp.addActionListener(this);
    }

    public void setSpatialData(Vector response) {
        pane.setModal(response);
        if (response.size() <= 1) {
            bp.begin.setEnabled(true);
            bp.newb.setEnabled(false);
        } else {
            bp.begin.setEnabled(true);
            bp.begin.setSelected(true);
            bp.newb.setEnabled(false);
        }

    }


    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        // begin.addActionListener(actionlistener);
    }

    public SpatialListImage getSelectedObject() {
        return pane.getSelectedObject();
    }

    public void actionPerformed(ActionEvent actionevent) {
        AbstractButton abstractbutton = (AbstractButton) actionevent.getSource();
        // abstractbutton.setSelected(!abstractbutton.isSelected());
        if (abstractbutton.getActionCommand().equals("TEXTVIEWS")) {
            msgLabel.setText("<html><body><font face='Trebuchet MS' size=8pts color=#ae5537><b>Click on one image for" +
                    " spatial search</b></font></body></html>");

        } else if (abstractbutton.getActionCommand().equals("IMAGELIST")) {
            msgLabel.setText("<html><body><font face='Trebuchet MS' " +
                    "size=8pts color=#ae5537><b>Click on image description to select image</b></font></body></html>");
        }
    }

    public void addSpatialListSelectionListener(SpatialListSelectionListener spatiallistselectionlistener) {
        if (pane != null) {
            pane.addSpatialListSelectionListener(spatiallistselectionlistener);
        }
    }

    public void reset() {
        pane.reset();
    }
/*
    PSVM(String args[]) {


        JFrame jframe = new JFrame();
        jframe.setSize(200, 200);

        SpatialListPanel spatiallistpanel = new SpatialListPanel();
        jframe.getContentPane().add(spatiallistpanel, "West");
        jframe.setVisible(true);
    }
*/
}
