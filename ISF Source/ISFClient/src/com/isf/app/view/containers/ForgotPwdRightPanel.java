package com.isf.app.view.containers;

import com.isf.app.view.controls.TrimmedIconButton;
import com.isf.app.view.gui.SimpleVerticalLayout;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ForgotPwdRightPanel extends TrimmedPanel {
    // private ActionListener al;
    public JLabel useridLB, forgotLB, mailLB, label1, label2, label3, label4;
    public JTextField useridT, mailT;
    private ImageIcon titleIcon = null;
    private ImageIcon orIcon = null;
    private int width = 0;
    public TrimmedIconButton confirmBTR, cancelBTR;
    private isf.controls.utils.ResolutionManager resManager = null;
    private double[] r;

    public ForgotPwdRightPanel() {
        resManager = isf.controls.utils.ResolutionManager.getInstance();
        r = resManager.getRatio();
        setLayout(new SimpleVerticalLayout());
        //setOpaque(false);
        setBackground(new Color(0xE6DFD9));
        titleIcon = resManager.getImageIcon("images/" + "titlebar.gif");
        orIcon = resManager.getImageIcon("images/" + "or.gif");
        width = (titleIcon.getIconWidth() - orIcon.getIconWidth()) / 2;
        setSize(width, (int) (r[1] * 470));


        forgotLB = new JLabel(resManager.getImageIcon("images/" + "forgotpassword.gif"));

        useridLB = new JLabel("User ID");
        useridLB.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        useridLB.setForeground(Color.black);
        //	useridLB=new JLabel(resManager.getImageIcon("images/"+"userid.gif"));

        //	mailLB=new JLabel(resManager.getImageIcon("images/"+"emailaddress.gif"));
        mailLB = new JLabel("Your Email Address");
        mailLB.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        mailLB.setForeground(Color.black);

        confirmBTR = new TrimmedIconButton(resManager.getImageIcon("images/" + "confirm.gif"));
        confirmBTR.setActionCommand("ForgotConfirm");


        cancelBTR = new TrimmedIconButton(resManager.getImageIcon("images/" + "cancel.gif"));
        cancelBTR.setActionCommand("ForgotCancel");


        useridT = new JTextField("", 10);
        useridT.setFont(new Font("Arial", Font.BOLD, 12));
        useridT.setForeground(new Color(0x00329B));


        mailT = new JTextField("", 15);
        mailT.setFont(new Font("Arial", Font.BOLD, 12));
        mailT.setForeground(new Color(0x00329B));


        add(forgotLB);
        add(new JLabel("    "));
        //	add(new JLabel("     "));
        add(useridLB);
        //	add(new JLabel("     "));
        add(useridT);
        add(new JLabel("     "));
        add(mailLB);
        //	add(new JLabel("  "));
        add(mailT);
        add(new JLabel("  "));
        add(confirmBTR);
        add(new JLabel("  "));
        add(cancelBTR);
        //useridT.requestFocus();

    }

    public void addActionListener(ActionListener al) {
        //this.al=al;
        confirmBTR.addActionListener(al);
        cancelBTR.addActionListener(al);
    }

    public Insets getInsets() {
        double[] r = isf.controls.utils.ResolutionManager.getInstance().getRatio();
        Insets ins = new Insets((int) (r[1] * 30), (int) (r[0] * 50), 0, 0);
        //Insets ins=new Insets((int)(r[1]*30),0,0,0);
        return ins;
    }

    public Dimension getPreferredSize() {
        double[] r = resManager.getRatio();
        /*	ImageIcon icon=resManager.getInstance().getImageIcon("images/"+"copyright.gif");

            Dimension dim=new Dimension((int)(icon.getIconWidth()*0.48),icon.getIconHeight());//597
                        */
        return new Dimension(width, (int) (r[1] * 470));//dim;
    }

  /*  PSVM(String args[]) {
        JFrame frame = new JFrame();
        ForgotPwdRightPanel ui = new ForgotPwdRightPanel();
        ui.addActionListener(new ForgotPasswordWindow());
        frame.getContentPane().add(ui);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }*/
}