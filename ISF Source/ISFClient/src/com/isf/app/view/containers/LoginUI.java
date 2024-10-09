package com.isf.app.view.containers;

import com.isf.app.view.controls.ImageBorder;
import com.isf.app.view.listeners.LoginListener;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginUI extends TrimmedPanel {

    public LoginPanel loginPanel;
    public PasswordPanel passwordPanel;
    public JLabel inscriptLB;
    public JLabel changeLB;
    public JLabel forgotLB;
    public JLabel devLB;
    private Image img;
    LoginListener ll;

    public LoginUI() {
        setLayout(new BorderLayout(0, 0));
        setSize(400, 400);
        setBackground(new Color(0xe6dfd9));
        javax.swing.ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/1_inscript_1280.jpg");
        Dimension resolution = ResolutionManager.getInstance().getScreen();
        ImageBorder imageborder = null;
        imageborder = new ImageBorder("images/wire.gif", true);
        imageborder.setBorderInsets(0, 0, 0, 0);
        loginPanel = new LoginPanel(imageborder.getHeight());
        passwordPanel = new PasswordPanel();
        inscriptLB = new JLabel(imageicon);
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout(0, 0));
        jpanel.add(loginPanel, "West");

        jpanel.add(passwordPanel, "Center");
        add(jpanel, "Center");

        add(inscriptLB, "North");

        jpanel.setBorder(imageborder);
        jpanel.setOpaque(true);
        jpanel.setBackground(new Color(0xe6dfd9));
        validate();
    }

    public void addLoginListener(LoginListener loginlistener) {
        ll = loginlistener;
        loginPanel.addLoginListener(loginlistener);
        passwordPanel.addLoginListener(loginlistener);
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public void reset() {
        passwordPanel.resetCursors();
    }

 


}
