package com.isf.app.view.containers;

import com.isf.app.view.controls.TrimmedIconButton;
import com.isf.app.view.gui.SimpleVerticalLayout;
import com.isf.app.view.listeners.LoginListener;
import isf.cache.utils.ImageUtils;
import isf.common.utils.LogManager;
import isf.controls.controllers.PTMFrameManager;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import org.apache.log4j.Logger;

public class LoginPanel extends TrimmedPanel
        implements ActionListener, KeyListener {

    public JPanel panel;
    public JTextField userNameTF;
    public JPasswordField passwordTF;
    public JLabel loginLB;
    public JLabel userNameLB;
    public JLabel passWordLB;
    // public JLabel textLB;
    public JLabel empty1;
    public JLabel empty2;
    public JLabel empty3;
    public JLabel empty4;
    public JLabel empty5;
    public TrimmedIconButton enterBT;
    public TrimmedIconButton exitBT;
    private LoginListener ll;
    private String userName;
    private String password;
    private String option;
    private Dimension resolution;
    Logger logger = LogManager.getLogger(com.isf.app.view.containers.LoginPanel.class);

    public LoginPanel(int height) {

        isf.controls.utils.ResolutionManager resolutionmanager = isf.controls.utils.ResolutionManager.getInstance();
        resolution = resolutionmanager.getScreen();
        double ad[] = isf.controls.utils.ResolutionManager.getInstance().getRatio();
        javax.swing.ImageIcon imageicon = resolutionmanager.getImageIcon("images/enter_new.jpg");
        javax.swing.ImageIcon imageicon1 = resolutionmanager.getImageIcon("images/exit_new.jpg");
        javax.swing.ImageIcon imageicon2 = resolutionmanager.getImageIcon("images/1_login.jpg");
        javax.swing.ImageIcon imageicon3 = null;

        setOpaque(false);
        JPanel controls = new JPanel();
        this.setLayout(new BorderLayout());
        controls.setLayout(new SimpleVerticalLayout());
        controls.setOpaque(false);
        controls.setBackground(new Color(0xe6dfd9));
        empty1 = new JLabel("  ");
        empty2 = new JLabel("  ");
        empty3 = new JLabel("  ");
        empty4 = new JLabel("  ");
        empty5 = new JLabel("  ");
        loginLB = new JLabel(imageicon2);
        loginLB.setHorizontalAlignment(2);
        userNameLB = new JLabel("User Name");
        userNameLB.setFont(new Font("Trebuchet MS", 1, 14));
        userNameLB.setForeground(Color.black);
        passWordLB = new JLabel("Password");
        passWordLB.setFont(new Font("Trebuchet MS", 1, 14));
        passWordLB.setForeground(Color.black);
        userNameTF = new JTextField("", 10);
        userNameTF.setFont(new Font("Arial", 1, 12));
        userNameTF.setForeground(new Color(12955));

        passwordTF = new JPasswordField(10);
        passwordTF.setForeground(new Color(12955));
        enterBT = new TrimmedIconButton(imageicon);
        enterBT.setActionCommand("Enter");
        enterBT.setHorizontalAlignment(2);
        enterBT.addActionListener(this);
        enterBT.addKeyListener(this);
        exitBT = new TrimmedIconButton(imageicon1);
        exitBT.setActionCommand("Exit");
        exitBT.setHorizontalAlignment(2);
        exitBT.addActionListener(this);
        exitBT.addKeyListener(this);
        userNameTF.addKeyListener(this);
        passwordTF.addKeyListener(this);
        //textLB = new JLabel();
        controls.add(loginLB);
        controls.add(empty1);
        controls.add(empty2);
        controls.add(userNameLB);
        controls.add(userNameTF);

        controls.add(empty3);
        controls.add(passWordLB);
        controls.add(passwordTF);
        controls.add(empty4);
        controls.add(enterBT);
        controls.add(empty5);
        controls.add(exitBT);

        /*  /* int temp = height -(12*30 )  ;
           if(temp<0)
              temp=0;
           else* /
          // int temp = (int) Math.ceil(height / 20);

          for (int i = 0; i < 2; i++) {    //instead of two used temp-12
              add(new JLabel(" "));
              //   System.out.println("");
          }
          // System.out.println("temp:" + temp + " --:" + height);

          //add(new JLabel(" "));*/
        this.add(controls, BorderLayout.CENTER);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setBackground(new Color(0xe6dfd9));
        text.setLayout(new SimpleVerticalLayout());
      //  text.add(new JLabel(getTextLabel()));

        JLabel textLB = new JLabel();
        textLB.setIcon(getLogos());
        text.add(textLB);
        text.add(new JLabel(" "));
        this.add(text, BorderLayout.SOUTH);

        //  addTextLabels();
        //  setLogos();
        validate();
        userNameTF.requestFocus();

    }

   /* public String getTextLabel() {
        String text = "<html><body> <pre><FONT SIZE=\"^\" face=\"Arial\" color=\"black\"><b>" +
                "The  West  Semitic  Research  Project  and  InscriptiFact  would  like  to<br>" +
                "thank the <font color=\"#ac5539\">Underwood Family Trust Fund</font> for its generous funding of the<br>" +
                "Internet Database Prototype.</b></font><br><br>" +
                "<FONT SIZE=\"*\" face=\"Arial\" color=\"black\"><b>" +
                "We would also like to thank the following for their support of the West Semitic Research<br>" +
                "Project and  Inscriptifact:</b></font> <br><FONT SIZE=\"$\" face=\"Arial\" color=\"black\">" +
                "The Ahmanson Foundation<br>" +
                "The Andrew W. Mellon Foundation<br>" +
                "The Hewlett-Packard Company<br>" +
                "Information Service Division, University of Southern California<br>" +
                "LizardTech Inc.<br>" +
                "The Oracle Corporation<br>" +
                "System Science Applications Inc.<br>" +
                "The University of Southern California Annenberg Center for Communication<br>" +
                "Zumberge Research and Innovation Fund, University of Southern California<br>" +
                "3Caos </font></pre> </body></html>";
        

        if (resolution.getWidth() < 1100) {
            text = text.replace('^', '3');
            text = text.replace('*', '2');
            text = text.replace('$', '1');
        } else if (resolution.getWidth() >= 1100 && resolution.getWidth() < 2000) {
            text = text.replace('^', '4');
            text = text.replace('*', '3');
            text = text.replace('$', '2');
        } else if (resolution.getWidth() >= 2000 && resolution.getWidth() < 2600) {
            text = text.replace('^', '5');
            text = text.replace('*', '4');
            text = text.replace('$', '3');
        } else if (resolution.getWidth() >= 2600) {
            text = text.replace('^', '6');
            text = text.replace('*', '5');
            text = text.replace('$', '4');
        }
        //add(new JLabel(text));
        return text;

    }*/


    private ImageIcon getLogos() {
        ImageIcon imageicon3 = null;
        if (resolution.getWidth() < 1280)
            imageicon3 = ResolutionManager.getInstance().loadImage("images/logos_1024.jpg");
        else if (resolution.getWidth() >= 1280 && resolution.getWidth() < 1600)
            imageicon3 = ResolutionManager.getInstance().loadImage("images/logos_1280.jpg");
        else if (resolution.getWidth() >= 1600)
            imageicon3 = ResolutionManager.getInstance().loadImage("images/logos_1600.jpg");
        //textLB.setIcon(imageicon3);
        // add(textLB);
        return imageicon3;

    }

    public void addLoginListener(LoginListener loginlistener) {
        ll = loginlistener;
    }

    public void actionPerformed(ActionEvent actionevent) {
        // setCursor(new Cursor(3));
        //  CursorManager.changeCursor(3);
        option = actionevent.getActionCommand();
        if (option.equals("Enter")) {
            validations(option);
        }
        if (option.equals("Exit")) {
             if (PTMFrameManager.getVersion() >= 1.5) {
              logger.debug("calling Clean Cache");
              ImageUtils.cleanCache();
            
          }
            System.exit(0);
        }
       
    }

    public void keyPressed(KeyEvent keyevent) {
        Object obj = keyevent.getSource();
      
        if (obj instanceof JButton && ((JButton) obj == exitBT) && keyevent.getKeyCode() == 10)
            System.exit(0);
        else if (keyevent.getKeyCode() == 10) {
            validations(enterBT.getActionCommand());
        }
    }

    public void keyReleased(KeyEvent keyevent) {
    }

    public void keyTyped(KeyEvent keyevent) {
    }

    private void validations(String s) {
        userName = userNameTF.getText().trim();
        password = (new String(passwordTF.getPassword())).trim();
        if ((userName.equals("") || userName == null) && (password == null || password.equals(""))) {
            JOptionPane.showMessageDialog(this, "Enter user name", "Error", 0);
            userNameTF.requestFocus();
            return;
        }
        if ((userName == null || userName.equals("")) && !password.equals("") && password != null) {
            JOptionPane.showMessageDialog(this, "Enter user name", "Error", 0);
            userNameTF.requestFocus();
            return;
        }
        if (!userName.equals("") && userName != null && (password == null || password.equals(""))) {
            JOptionPane.showMessageDialog(this, "Enter password", "Error", 0);
            passwordTF.requestFocus();
            return;
        }
        if (!userName.equals("") && userName != null && password != null && !password.equals("")) {
            logger.debug(s);
            ll.actionChanged(s, userName, password);
        }
    }

    public Insets getInsets() {
        double ad[] = isf.controls.utils.ResolutionManager.getInstance().getRatio();
        Insets insets = new Insets((int) (ad[1] * 50D), (int) (ad[0] * 50D), 0, 0);
        return insets;
    }

    /*  public Dimension getPreferredSize() {
          double ad[] = isf.controls.utils.ResolutionManager.getInstance().getRatio();
          Dimension dimension;
          if (resolution.getWidth() == 1024D) {
              dimension = new Dimension((int) (ad[0] * 260D), (int) (ad[1] * 1024D));
          } else {
              dimension = new Dimension((int) (ad[0] * 620D), (int) (ad[1] * 1024D));
          }
          return dimension;
      }*/




}
