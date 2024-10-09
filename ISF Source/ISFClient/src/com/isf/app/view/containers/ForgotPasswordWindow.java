package com.isf.app.view.containers;

import com.isf.app.utils.GeneralXmlConverter;
import com.isf.app.utils.ResultsUtil;
import com.isf.app.view.controls.InscriptDyanBorder;
import isf.common.Debug;
import isf.common.ISFBase64;
import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
import isf.controls.utils.CursorManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.controls.InscriptButton;
import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import org.apache.log4j.Logger;

/**
 * Class that is extends
 */
public class ForgotPasswordWindow extends JPanel implements ActionListener, WindowListener, FocusListener { //JWindow
    // private ActionListener al=null;

    public JButton title;    // titel of the window
    

    // background color that is set to the panel in the window
    //private static Color bgColor = new Color(0xD9D9D9);
    //private static ImageIcon titleIcon = ResolutionManager.getInstance().loadImage("images/"+"titlebar.gif");
    private static ImageIcon titleIcon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/" + "titlebar.gif");
    public ForgotPwdLeftPanel left = null;
    public ForgotPwdRightPanel right = null;
    Logger logger = LogManager.getLogger(com.isf.app.view.containers.ForgotPasswordWindow.class);

    // width of the text pane
    //private int width ;
    private double[] r;

    // scrollpane to whose view port the text pane is added
    JScrollPane jScrollPane1;

    /**
     * Contructor that takes Frame instance as a paremeter which acts as parent of
     * this window
     */
    public ForgotPasswordWindow() {             //JFrame parent
        super();

        // setting the UIMaanager scrollbarUI to user defined InscriptScrollbarUI
        ///////////////////////////////////////////UIManager.put("ScrollBarUI", "InscriptScrollBarUI");

        // adding the window listener
        Container con = this; //getContentPane();
        //    addWindowListener(this);
        addFocusListener(this);

        con.setLayout(new BorderLayout(0, 0));
        r = isf.controls.utils.ResolutionManager.getInstance().getRatio();
        /////    Creating button placed on the top /////////////
        title = new InscriptButton(titleIcon);//"images/"+"titlebar.gif");
        //     title.setSize(titleIcon.getIconWidth()+(int)(r[0]*50),titleIcon.getIconHeight());
        title.setActionCommand("Title");
        title.setBorderPainted(false);
        title.setFocusPainted(false);
        title.setContentAreaFilled(false);
        title.addActionListener(this);
        // add(title,"North");
//setSize((titleIcon.getIconWidth()+(int)(r[0]*50)),(int)(r[1]*550));


        /////// creating panel with insets and etched border ////////
        /*     TrimmedPanel panel = new TrimmedPanel(new Insets(10,5,0,5));
        panel.setBackground(new Color(0xE6DFD9));
        panel.setBorder(new InscriptDyanBorder(new Color(0x808080),0,1,0,1));//(new EtchedBorder(EtchedBorder.RAISED,Color.gray,Color.gray));
         */
        left = new ForgotPwdLeftPanel();

        right = new ForgotPwdRightPanel();


        left.addActionListener(this);
        right.addActionListener(this);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(0xE6DFD9));
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        centerPanel.setSize(titleIcon.getIconWidth(), (int) (r[1] * 550));
        ImageIcon icon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/" + "or.gif");
        JLabel orLB = new JLabel(icon);
        orLB.setSize(icon.getIconWidth(), (int) (r[1] * 550));//icon.getIconHeight());
        centerPanel.add(left);
        centerPanel.add(orLB);
        centerPanel.add(right);
        //centerPanel.setOpaque(false);
        centerPanel.setBorder(new InscriptDyanBorder(new Color(0x808080), 0, 1, 0, 1));
        //System.out.println("window  :"+getSize()+"  left  :"+left.getSize()+"  right  :"+right.getSize()+"  or  :"+orLB.getSize());

        //// creating a label containing the copyright information ////////
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.setBackground(new Color(0xE6DFD9));
        tempPanel.setBorder(new InscriptDyanBorder(new Color(0x808080), 0, 1, 1, 1));
//  tempPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED,Color.gray,Color.gray));

        JLabel label = new JLabel("© Copyright 2001 - "+(new GregorianCalendar()).get(GregorianCalendar.YEAR)+" University of Southern California. All Rights Reserved.");
        label.setFont(new Font("Arial", Font.BOLD, 10));
        label.setOpaque(true);
        label.setBackground(new Color(0xE6DFD9));
        label.setForeground(Color.black);
        tempPanel.add(label);
        JPanel basePanel = new JPanel();
        basePanel.setOpaque(true);
        basePanel.setBackground(new Color(0xE6DFD9));
        basePanel.setLayout(new BorderLayout(0, 0));
        basePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.gray, Color.gray));
        basePanel.add(title, "North");
        basePanel.add(centerPanel, "Center");
        basePanel.add(tempPanel, "South");
        con.add(basePanel, "Center");
        setWindow();
        setVisible(true);


    }

    public void setWindow() {
        setSize(titleIcon.getIconWidth(), (int) (r[1] * 550));
        setLocation((int) (r[0] * 570), (int) (r[1] * 225));
    }

    /**
     * Method from te action listener that is invoked when an action is performed on
     * the buttons in the window
     *
     * @param ae -- ActionEvent that fired this method
     */
    public void actionPerformed(ActionEvent ae) {
        Object obj = ae.getSource();
        String userName = left.userNameT.getText().trim();
        String oldP = new String(left.pwdT.getPassword()).trim();
        String newP = new String(left.newpwdT.getPassword()).trim();
        String conP = new String(left.confirmT.getPassword()).trim();
        //System.out.println("user " + userName + "  " + oldP + "  " + newP + " " + conP);
        String userID = right.useridT.getText().trim();
        String mailID = right.mailT.getText().trim();
        GeneralXmlConverter xc = null;


        if ((JButton) obj == title) {
            /* If the source of this action event is  title button
            dispose this window */
            //    dispose();
            //todo implement close
            dispose();
        }

        if ((JButton) obj == left.confirmBTL) {
            if (left.userNameT.isEnabled()) {
                if (userName == null || userName.equals("")) {
                    JOptionPane.showMessageDialog(this, "Enter User Name", "Error", JOptionPane.ERROR_MESSAGE);
                    left.userNameT.setText("");
                    left.userNameT.grabFocus();
                    return;
                }
                if (oldP == null || oldP.equals("")) {

                    JOptionPane.showMessageDialog(this, "Enter Old Password", "Error", JOptionPane.ERROR_MESSAGE);
                    left.pwdT.setText("");
                    left.pwdT.grabFocus();
                    return;
                }
                if ((newP == null || newP.equals("")) && (conP == null || conP.equals(""))) {
                    JOptionPane.showMessageDialog(this, "You have to enter new password and confirm password", "Error", JOptionPane.ERROR_MESSAGE);
                    left.newpwdT.setText("");
                    left.newpwdT.grabFocus();
                    return;
                }
                if (!newP.equals(conP) || newP == null || newP.equals("") || conP == null || conP.equals("")) {
                    Debug.debug("confirm your password");
                    JOptionPane.showMessageDialog(this, "new password and confirm password must be equal",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    left.confirmT.setText("");
                    left.confirmT.grabFocus();
                    return;
                }

                if ((userName != null && !userName.equals("")) && (oldP != null && !oldP.equals("")) && (newP != null && !newP.equals("")) && (conP != null && !conP.equals("")) && (newP.equals(conP))) {
                    // pl.changePassword(userName,newP);
                    //System.out.println("from password listener(change)");

                    CursorManager.changeCursor(new Cursor(Cursor.WAIT_CURSOR));
                    try {
                        String reqXml = "<QUERY TYPE='ChangePassword' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" +
                                "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='useridentity' VALUE='" + ISFBase64.getQueryVal(userName) + "'" + " OPERATOR='=' CONNECTOR=''>" +
                                "</QUERY_CONDITION></CLAUSE><CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='userpassword' VALUE='" + ISFBase64.getQueryVal(oldP) + "'" + " OPERATOR='=' CONNECTOR=''>" +
                                "</QUERY_CONDITION></CLAUSE>" +
                                "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='userpassword' VALUE='" + ISFBase64.getQueryVal(newP) + "'" + " OPERATOR='=' CONNECTOR=''>" +
                                "</QUERY_CONDITION></CLAUSE></QUERY>";
                        String[] messages = new String[]{"", "", "", ""};

                        Vector v = null;

                        v = ResultsUtil.getResults("ChangePassword", reqXml, messages,false);


                        /* //	RequestDispatcherObject rdo=new RequestDispatcherObject();
                        String xml = (String) RequestDispatcherObject.getInstance().handleQueryEvent(reqXml);
                        if (xml != null) {
                        //System.out.println("xml  " + xml);
                        DataStructureFactory dsf = new DataStructureFactory();
                        xc = (GeneralXmlConverter) dsf.getConverter("ChangePassword");

                        SAXParser sp = new SAXParser();
                        sp.setContentHandler(xc);
                        InputSource iso = new InputSource(new StringReader(xml));
                        //System.out.println("Xml:"+xml);
                        //System.out.println("********iso***** "+iso);
                        sp.parse(iso);
                        String str = xc.getSccMsg();
                        //System.out.println("Message  "+str);*/
                        if (messages[0].equals("Password Changed")) {
                            JOptionPane.showMessageDialog(this, "Password Changed", "Success", JOptionPane.ERROR_MESSAGE);
                            //todo implement close
                            dispose();
                        } else if (!"".equalsIgnoreCase(messages[1])) {
                            String msg = "";
                            if (messages[1].indexOf("ConnectException") > -1) {
                                msg = "Connection to server lost. Please reconnect.";
                            } else {
                                msg = "Unknown error in retrieving request. Contact Administrator. ";
                            }

                            JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    //System.out.println("/n ***************** FINISHED FROM IUI *********************");

                    } catch (Exception err) {

                       logger.error(err);
                    //System.out.println("   " + err.getMessage());
                    }

                    CursorManager.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

        }
        if ((JButton) obj == right.confirmBTR) {
            if (right.useridT.isEnabled()) {
                //System.out.println("from password listener(forgot)");
                if (userID == null || userID.equals("")) {
                    JOptionPane.showMessageDialog(this, "Enter User Name", "Error", JOptionPane.ERROR_MESSAGE);
                    right.useridT.grabFocus();
                    return;
                }
                if (mailID == null || mailID.equals("")) {

                    JOptionPane.showMessageDialog(this, "Enter Old Password", "Error", JOptionPane.ERROR_MESSAGE);
                    right.mailT.grabFocus();
                    return;
                }
                if ((userID != null && !userID.equals("")) && (mailID != null && !mailID.equals(""))) {
                    CursorManager.changeCursor(new Cursor(Cursor.WAIT_CURSOR));
                    try {
                        String reqXml = "<QUERY TYPE='ForgotPassword' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" +
                                "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='useridentity' VALUE='" + ISFBase64.getQueryVal(userID) + "'" + " OPERATOR='=' CONNECTOR=''>" +
                                "</QUERY_CONDITION></CLAUSE><CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='mailid' VALUE='" + ISFBase64.getQueryVal(mailID) + "'" + " OPERATOR='=' CONNECTOR=''>" +
                                "</QUERY_CONDITION></CLAUSE></QUERY>";

                        String[] messages = new String[]{"", "", "", ""};
                        Vector v = null;
                        v = ResultsUtil.getResults("ForgotPassword", reqXml, messages,false);
                        //	RequestDispatcherObject rdo=new RequestDispatcherObject();
                     /*   String xml = (String) RequestDispatcherObject.getInstance().handleQueryEvent(reqXml);
                        if (xml != null) {
                        //System.out.println("xml  "+xml);
                        xc = new GeneralXmlConverter();
                        SAXParser sp = new SAXParser();
                        sp.setContentHandler(xc);
                        InputSource iso = new InputSource(new StringReader(xml));
                        //System.out.println("Xml:"+xml);
                        //System.out.println("********iso***** "+iso);
                        sp.parse(iso);
                        String str = xc.getSccMsg();
                        //System.out.println("*********************Message  " + str);*/
                        if (messages[0].equals("Forgot Password")) {
                            JOptionPane.showMessageDialog(this, "Your password has been sent through mail", "Success", JOptionPane.ERROR_MESSAGE);
                            // todo implement close
                            dispose();
                        } else if (!"".equalsIgnoreCase(messages[1])) {
                            String msg = "";
                            if (xc.getErrMsg().indexOf("ConnectException") > -1) {
                                msg = "Connection to the server is lost. Please reconnect";
                            } else {
                                msg = "Unknown error in retrieving request. Contact Administration";
                            }
                            JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } /*  } *catch (SAXParseException err) {

                   System.out.println("** Warning" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
                    //System.out.println("   " + err.getMessage());
                    }*/ catch (Exception e) {
                        logger.error(e);
                    }

                    CursorManager.changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));


                }
            }
        }
        if (obj == left.cancelBTL) {
            if (left.userNameT.isEnabled()) {
                /* If the source of this action event is  Cancel button
                dispose this window */
                //todo implement close
                dispose();
            }
        }
        if (obj == right.cancelBTR) {
            if (right.useridT.isEnabled()) {
                /* If the source of this action event is  Cancel button
                dispose this window */
                //todo implement close
                dispose();
            }
        }
    }

    public void windowActivated(WindowEvent we) {
    }

    public void windowDeactivated(WindowEvent we) {
    }

    public void windowClosing(WindowEvent we) {
    }

    public void windowClosed(WindowEvent me) {
    }

    public void windowOpened(WindowEvent we) {
    }

    public void windowIconified(WindowEvent we) {
    }

    public void windowDeiconified(WindowEvent we) {
    }

    private void dispose() {
        GlassPaneManager.getInstance().hideAll();


    }

    public void focusGained(FocusEvent fe) {
        //System.out.println("Focus Gained");
        if (right.useridT.isEnabled()) {
            right.useridT.requestFocus();
        } else {
            left.userNameT.requestFocus();
        }


    }

    public void focusLost(FocusEvent fe) {
        //System.out.println("Focus lost");
    }

    /*  PSVM(String args[]) {
    JFrame f = new JFrame();
    f.setVisible(false);
    ForgotPasswordWindow cw = new ForgotPasswordWindow(f);
    cw.show();

    }*/
}