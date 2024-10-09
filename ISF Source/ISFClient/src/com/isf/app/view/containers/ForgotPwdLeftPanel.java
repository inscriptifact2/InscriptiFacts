package com.isf.app.view.containers;

import com.isf.app.view.controls.TrimmedIconButton;
import com.isf.app.view.gui.SimpleVerticalLayout;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ForgotPwdLeftPanel extends TrimmedPanel //implements FocusListener
{
    //private ActionListener al=null;

    public JLabel userNameLB, confirmLB, pwdLB, newpwdLB, changeLB;
    public JTextField userNameT;
    public JPasswordField pwdT, newpwdT, confirmT;
    public TrimmedIconButton confirmBTL, cancelBTL;
    private isf.controls.utils.ResolutionManager resManager = null;
    private double[] r;
    private ImageIcon titleIcon = null;
    private ImageIcon orIcon = null;
    private int width = 0;

    public ForgotPwdLeftPanel() {
        resManager = isf.controls.utils.ResolutionManager.getInstance();
        r = resManager.getRatio();
        setLayout(new SimpleVerticalLayout());
        titleIcon = resManager.getImageIcon("images/" + "titlebar.gif");
        orIcon = resManager.getImageIcon("images/" + "or.gif");
        width = (titleIcon.getIconWidth() / 2) - orIcon.getIconWidth();
        setSize(width, (int) (r[1] * 470));
        setBackground(new Color(0xE6DFD9));
        //setOpaque(false);


        changeLB = new JLabel(resManager.getImageIcon("images/" + "changepassword.gif"));

        //	userNameLB=new JLabel(resManager.getInstance().getImageIcon("images/"+"username.gif"));
        userNameLB = new JLabel("User Name");
        userNameLB.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        userNameLB.setForeground(Color.black);

        //	pwdLB=new JLabel(resManager.getImageIcon("images/"+"password.gif"));
        pwdLB = new JLabel("Password");
        pwdLB.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        pwdLB.setForeground(Color.black);

        //	newpwdLB=new JLabel(resManager.getImageIcon("images/"+"newpassword.gif"));
        newpwdLB = new JLabel("New Password");
        newpwdLB.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        newpwdLB.setForeground(Color.black);

        //	confirmLB=new JLabel(resManager.getImageIcon("images/"+"confirmpassword.gif"));
        confirmLB = new JLabel("Confirm Password");
        confirmLB.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        confirmLB.setForeground(Color.black);

        confirmBTL = new TrimmedIconButton(resManager.getImageIcon("images/" + "confirm.gif"));
        confirmBTL.setActionCommand("ChangeConfirm");
        confirmBTL.setFocusPainted(true);

        /*  confirmBTL.addActionListener(this);
            cancelBTL.addActionListener(this);
           */

        cancelBTL = new TrimmedIconButton(resManager.getImageIcon("images/" + "cancel.gif"));
        cancelBTL.setActionCommand("ChangeCancel");
        cancelBTL.setFocusPainted(true);


        userNameT = new JTextField("", 10);
        userNameT.setFont(new Font("Arial", Font.BOLD, 12));
        //userNameT.setFocusPainted(true);
        userNameT.setForeground(new Color(0x00329B));


        pwdT = new JPasswordField(10);
        pwdT.setFont(new Font("Arial", Font.BOLD, 12));
        pwdT.setForeground(new Color(0x00329B));
        //pwdT.setFocusPainted(true);


        newpwdT = new JPasswordField(10);
        newpwdT.setFont(new Font("Arial", Font.BOLD, 12));
        newpwdT.setForeground(new Color(0x00329B));
        //newpwdT.setFocusPainted(true);


        confirmT = new JPasswordField(10);
        confirmT.setFont(new Font("Arial", Font.BOLD, 12));
        confirmT.setForeground(new Color(0x00329B));
        //confirmT.setFocusPainted(true);


        add(changeLB);
        //add(new JLabel("  "));
        add(new JLabel("     "));
        add(userNameLB);
        add(userNameT);
        add(new JLabel("     "));
        add(pwdLB);
        add(pwdT);
        add(new JLabel("     "));
        add(newpwdLB);
        add(newpwdT);
        add(new JLabel("     "));
        add(confirmLB);
        add(confirmT);
        add(new JLabel("     "));
        add(confirmBTL);
        add(new JLabel("     "));
        add(cancelBTL);
        //userNameT.requestFocus();


    }

    public void addActionListener(ActionListener al) {
        //this.al=al;
        confirmBTL.addActionListener(al);
        cancelBTL.addActionListener(al);
    }

    public Insets getInsets() {
        double[] r = isf.controls.utils.ResolutionManager.getInstance().getRatio();
        //Insets ins=new Insets((int)(r[1]*30),(int)(r[0]*30),0,0);
        Insets ins = new Insets((int) (r[1] * 30), (int) (r[0] * 30), 0, 0);
        return ins;
    }

    public Dimension getPreferredSize() {
        double[] r = resManager.getRatio();
        return new Dimension(width, (int) (r[1] * 470));//dim;
    }


   /* PSVM(String args[]) {
        JFrame frame = new JFrame();
        ForgotPwdLeftPanel ui = new ForgotPwdLeftPanel();
        frame.getContentPane().add(ui);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }*/
}