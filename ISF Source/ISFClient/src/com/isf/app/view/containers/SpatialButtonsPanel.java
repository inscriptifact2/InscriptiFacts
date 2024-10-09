package com.isf.app.view.containers;

import com.isf.app.view.controls.TrimmedIconButton;
import com.isf.app.view.gui.LineLayout;
import isf.controls.view.controls.InscriptBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JPanel;

public class SpatialButtonsPanel extends JPanel
        implements ActionListener {

    public TrimmedIconButton begin;
    public TrimmedIconButton newb;
    private ActionListener al;

    public SpatialButtonsPanel(String as[], String as1[]) {
        al = null;
        setLayout(new LineLayout(false));
        setBackground(new Color(0xd9d9d9));
        setBorder(new InscriptBorder(Color.white));
        begin = new TrimmedIconButton(as[3], as[0], as[2], as[0]);
        //  begin.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(as[1]));
        // begin.setDisabledIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(as[2]));
        // begin.setActionCommand(as[3]);
        // begin.addActionListener(this);
        // begin.setBackground(new Color(0xd9d9d9));
        begin.setBorderPainted(false);
        begin.setFocusPainted(false);
        begin.setContentAreaFilled(false);
        begin.setSelected(true);
        newb = new TrimmedIconButton(as1[3], as1[0], as1[2], as1[1]);
        // newb.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(as1[1]));
        // newb.setDisabledIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(as1[2]));
        // newb.setActionCommand(as[3]);
        // newb.addActionListener(this);
        newb.setBorderPainted(false);
        newb.setBackground(new Color(0xd9d9d9));
        newb.setFocusPainted(false);
        newb.setContentAreaFilled(false);
        newb.setEnabled(false);

        add(begin);
        add(newb);
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        begin.addActionListener(actionlistener);
        newb.addActionListener(actionlistener);
    }

    public void actionPerformed(ActionEvent actionevent) {
        AbstractButton abstractbutton = (AbstractButton) actionevent.getSource();
        abstractbutton.setSelected(!abstractbutton.isSelected());
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public Dimension getPreferredSize() {
        return new Dimension(begin.getSelectedIcon().getIconWidth() + newb.getSelectedIcon().getIconWidth(),
                begin.getSelectedIcon().getIconHeight());
    }
}
