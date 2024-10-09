package com.isf.app.view.containers;

import com.isf.app.models.*;
import com.isf.app.utils.*;
import com.isf.app.view.controls.*;
import com.isf.app.view.gui.PathSelectionManager;
import com.isf.app.view.gui.TableLayout;
import com.isf.app.view.gui.ToolBarManager;
import com.isf.app.view.listeners.LoginListener;
import com.isf.app.view.listeners.MainSearchListener;
import com.isf.app.view.listeners.ObjectListListener;
import isf.cache.utils.ImageUtils;
import isf.common.Debug;
import isf.common.ISFBase64;
import isf.common.request.RequestProxy;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.InscriptConfigLoader;
import isf.common.utils.LogManager;
import isf.common.utils.LogoutListener;
import isf.common.view.gui.SpatialListImage;
import isf.common.view.gui.TextDivision;
import isf.common.view.gui.TextDivisionSubDivision;
import isf.controls.controllers.*;
import isf.controls.plaf.DesktopTheme;
import isf.controls.utils.*;
import isf.controls.view.containers.ISFSplitPane;
import isf.controls.view.containers.TrimmedPanel;
import isf.controls.view.listeners.CursorListener;
import isf.controls.view.listeners.KeyHandler;
import isf.net.request.NetManager;
import isf.net.request.RequestDispatcherObject;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import org.apache.log4j.Logger;

public class InscriptUI extends JFrame
        implements ActionListener, MouseListener, ObjectListListener, LoginListener, MainSearchListener,
        LogoutListener, CursorListener, FocusListener, KeyHandler {

    private TrimmedIconButton navim1;
    private TrimmedIconButton navim2;
    private TrimmedIconButton navim3;
    private TrimmedIconButton navim4;
    private TrimmedIconButton navim5;
    private TrimmedIconButton navim6;
    private TrimmedIconButton navim7;
    static Logger log = null;
    private JMenuBar genmb;
    private ToolBarPanel navmb;
    private MainSearchPanel msp;
    private Container con;
    private CatalougePanel s2;
    private TextDivisionPanel tdp;
    private SpatialSearchPanel slp;
    private ImageResultsPanel irp;
    private TrimmedPanel center;
    private TrimmedPanel copyright;
    private JLabel cr;
    private InscriptMenuBar title;
    private DesktopViewer vd;
    private LoginUI loginp;
    private ISFSplitPane titlehead;
    private ForgotPasswordWindow dialog;
    private String sdType;
    private boolean firstTime;
    private JPanel jp;
    private GeneralMenu blank;
    private GeneralMenu session;
    private GeneralMenu edu;
    private GeneralMenu search;
    private GeneralMenu bginfo;
    private GeneralMenu tools;
    private GeneralMenu comm;
    private GeneralMenu help;
    private GeneralMenu download;
    private GeneralMenuItem newmi;
    private GeneralMenuItem open;
    private GeneralMenuItem save;
    private GeneralMenuItem close;
    private GeneralMenuItem print;
    private GeneralMenuItem start;
    private GeneralMenuItem genInfo;
    private GeneralMenuItem selDB;
    private GeneralMenuItem curPage;
    private GeneralMenuItem contents;
    private GeneralMenuItem index;
    private GeneralMenuItem keywords;
    private GeneralMenuItem fts;
    private GeneralMenuItem lf;
    private GeneralMenuItem email;
    private GeneralMenuItem cws;
    private GeneralMenuItem messaging;
    private GeneralMenuItem dnw;
    private GeneralMenuItem ccws;
    private GeneralMenuItem chat;
    private GeneralMenuItem cp;
    private GeneralMenuItem cont;
    private GeneralMenuItem ind;
    private GeneralMenuItem keyw;
    private GeneralMenuItem images;
    private GeneralMenuItem cat;
    private GeneralMenuItem imagesAndCat;
    // private GeneralMenuItem rti;
    private QueryDefinitionPanel qdp;
    private Cursor oldCursor = null;
    private int lastSelected = 0;
    private boolean winMenuClicked = false;
    public boolean initData = false;
    private boolean trapEnter = false;
    // private boolean canproceed=false;
    public String logMsg = "";
    public Vector initVector = null;
    private String userName = "";
    public TrimmedPanel titlePane, bodyPane;
    private boolean collection = true;
    private JProgressBar progress;

    public InscriptUI() {
         log = LogManager.getLogger(com.isf.app.view.containers.InscriptUI.class);
        sdType = null;
        firstTime = true;
      //  LogManager.initialize("log4j.txt");
      /*  String version = InscriptConfigLoader.getInstance().getProperty("version");
        String strTitle = InscriptConfigLoader.getInstance().getProperty("title");
        setTitle(strTitle + version + " ::");*/
        addNotify();
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent) {
                closeMe();
            }
        });
        addFocusListener(this);
        /*
         * Container c = getContentPane(); c.setLayout(new BorderLayout(0, 0));
         * c.setBackground(new Color(0xa49585)); JScrollPane sp = new
         * JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
         * JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS ); c.add(sp);
         */


        con = getContentPane();
        con.setLayout(new BorderLayout(0, 0));
        con.setBackground(new Color(0xa49585));

        loginp = new LoginUI();
        loginp.addLoginListener(this);
        con.add(loginp, "Center");
        setIconImage(ResolutionManager.getInstance().getImageIcon("images/isf.jpg").getImage());
        CursorManager.getInstance().addCursorListener(this);
        //  Dimension dimension = ResolutionManager.getInstance().getScreen();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width, dimension.height);
        setVisible(true);
        loginp.loginPanel.userNameTF.grabFocus();
        //  setBorder(new EtchedBorder());
        setBackground(new Color(225, 225, 225));

        if(RequestProxy.getRequestDispatcher() instanceof RequestDispatcherObject )
            ((RequestDispatcherObject)RequestProxy.getRequestDispatcher()).addLogoutListener(this);
        GlassPaneManager.getInstance().setUpGlasssPane(this);
        // GlassPaneManager.getInstance().registerMainApp(this);
        EventQueue q = Toolkit.getDefaultToolkit().getSystemEventQueue();
        q.push(new ISFKeyEventTracker());

    }

    public Insets getInsets() {
        return new Insets(22, 2, 2, 2);
    }

    private void buildUI() {
        //System.out.println("Started Build:"+System.currentTimeMillis());
        titlePane = new TrimmedPanel();
        bodyPane = new TrimmedPanel();
        titlePane.setLayout(new BorderLayout(0, 0));
        bodyPane.setLayout(new BorderLayout(0, 0));
        center = new TrimmedPanel();
        center.setLayout(new BorderLayout(0, 0));
        center.setBackground(Color.pink);
        copyright = new TrimmedPanel();
        copyright.addMouseListener(this);
        copyright.setLayout(new FlowLayout(0, 20, 20));
        copyright.setBackground(new Color(0xa49585));
        cr = new JLabel("<html><body><font face='Arial' size='10pts' color='C8C0BC'>© Copyright 2001 - "
                +(new GregorianCalendar()).get(GregorianCalendar.YEAR)+" University "
                + "of Southern California. All Rights Reserved.</font></body></html>");
        cr.setBackground(new Color(0xa49585));
        copyright.add(cr);
        //add progress bar here....todo
        //GeneralMenu.owner = this;
        progress = new JProgressBar() {

            public Dimension getPreferredSize() {
                return new Dimension(400, 30);
            }
        };
        // progress.setPreferredSize(new Dimension(400,30));
        progress.setVisible(false);
        progress.setStringPainted(true);
        progress.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        progress.setForeground(Color.cyan);
        LoadManager.getInstance().registerMonitor(progress);
        copyright.add(progress);
        buildGeneralMenuBar();
        generateSessionMenuItems();
        generateEducationalAccessMenuItems();
        generateSearchMenuItems();
        generateBackgroundMenuItems();
        generateToolsMenuItems();
        generateCommunicationsMenuItems();
        generateCommunicationSubMenuItems();
        generateHelpMenuItems();
        generateDownloadMenuItems();
        title = new InscriptMenuBar("images/inscript.jpg");
        //todo changed here for vert split
        //setJMenuBar(strTitle);
        titlePane.add(title, "North");
        buildNavigationalMenuBar();
        ToolBarManager.getInstance();

        jp = new JPanel();
        jp.setLayout(new TableLayout(2, 1, 0, 0));
        // jp.setLayout(new BorderLayout(0,0));

        jp.setBorder(null);
        jp.add(genmb);
        jp.add(navmb);
        jp.setBackground(new Color(0xa49585));

        center.add(msp, "Center");
        // System.out.println("Ended Build:"+System.currentTimeMillis());
    }

    public void setInitData(Vector vector) {
//System.out.println("Vector  "+vector);

        try {
            msp.qdp.qsp.addMouseList(this);

            msp.setData((TreeParent) vector.elementAt(0));
            //  System.out.println("Vector msp -- "+(TreeParent)vector.elementAt(0));
            //	System.out.println("Vector txtpanel-- "+vector.elementAt(1));
            qdp.textPanel.setTextPublicationData((Vector) vector.elementAt(1));
            // qdp.textPanel.textNo.updateUI();
            qdp.textPanel.textNo.addActionListener(this);
            qdp.textPanel.publicationNo.addMouseListener(this);
            qdp.textPanel.keyWord.addMouseListener(this);
            qdp.textPanel.addMouseListener(this);
            // qdp.bp.addMouseList(this);
            msp.qdp.qsp.addMouseList(this);
            msp.qmu.addMouseListener(this);

        } catch (Exception exception) {
            log.error(exception);
        }
        ////////todo changed here     for vert split
        titlePane.add(jp, "Center");
        int height = titlePane.getPreferredSize().height;
        bodyPane.add(center, "Center");
        bodyPane.add(copyright, "South");
        
        titlehead = new ISFSplitPane(JSplitPane.VERTICAL_SPLIT, false, titlePane, bodyPane, height + 1, 0);
        titlehead.setDivider(0);
        // titlehead.setOneSize(ht);
        titlehead.setOneSize(height + 1);
        titlehead.setBorder(new LineBorder(Color.cyan));
        IsfSplitManager.getInstance().setSplitPane(titlehead, 0);
        
        titlehead.setDivider(0);
        titlehead.setEnabled(false);
        titlehead.setOneTouchExpandable(false);
       
        con.add(titlehead, "Center");

    }

    private void setUpPanels() {
        msp = new MainSearchPanel(new Insets(0, 0, 0, 0));
        msp.addActionListener(this);
        msp.addMainSearchListener(this);
        //msp.addMouseListener(this);
        qdp = msp.qdp;
        qdp.addMouseListener(this);
        slp = new SpatialSearchPanel();
        slp.addActionListener(this);
        tdp = new TextDivisionPanel();
        irp = new ImageResultsPanel();
        // irp.addDesktopListener(this);
        vd = new DesktopViewer(this);
        KeyProcessManager.getInstance().addKeyHandler(this);

    }

    public void actionChanged(String type, String userid, String pwd) {
        userName = userid;
        if (CursorManager.wait) {
            //System.out.println(""+CursorManager.wait);
            return;
        }
        CursorManager.changeCursor(3);
        try {
            String query = "";
            String queryType = "";
            if (type.equals("Enter") && !initData) {
                queryType = "Authentication";
            } else if (type.equals("Enter")) {
                queryType = "Login";

            } else if (type.equalsIgnoreCase("ACCEPT")) {
                queryType = "UserAcceptance";
            }
            query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='NEW'><CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='useridentity' VALUE='"
                    + ISFBase64.getQueryVal(userid) + "'" + " OPERATOR='=' CONNECTOR=''>" + "</QUERY_CONDITION></CLAUSE><CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='userpassword' VALUE='"
                    + ISFBase64.getQueryVal(pwd) + "'" + " OPERATOR='=' CONNECTOR=''>" + "</QUERY_CONDITION></CLAUSE></QUERY>";


            try {
                String[] messages = new String[]{"", "", "", ""};
                Vector vector = ResultsUtil.getResults(queryType, query, messages, false);

                logMsg = messages[1];
                if(logMsg==null || logMsg.trim().equalsIgnoreCase("")){
                if (!initData) {
                    initVector = vector;
                }
                RequestProxy.getRequestDispatcher().sessionid = messages[2];
                initData = true;
                }

            } catch (Exception ie) {
                log.error(ie);
                JOptionPane.showMessageDialog(this, ie.getMessage(), "Unknown Error", 0);
                //setCursor(new Cursor(0));
                return;
            }
            if (logMsg != null && !logMsg.equals("")) {
                if (logMsg.indexOf("20001") != -1) {
                    JOptionPane.showMessageDialog(this, "System is busy as maximum users logged in. Please try again after some time", "System is busy", 1);
                    // setCursor(new Cursor(0));
                    initData = false;
                    return;
                } else if (logMsg.indexOf("20002") != -1) {
                    logMsg = "User ID is wrong.";

                } else if (logMsg.indexOf("20003") != -1) {
                    logMsg = "Incorrect password. Please try again.";
                } else if (logMsg.indexOf("20004") != -1) {
                    logMsg = "A user with the same id has already logged in";
                } else if (logMsg.indexOf("20005") != -1) {
                    logMsg = "In order to login to InscriptiFact, you must accept the User Agreement. \n"
                            + "To read and accept the User Agreement, click USER AGREEMENT.";
                    Object[] options = {"USER AGREEMENT", "EXIT"};

                    int choice = JOptionPane.showOptionDialog(this, logMsg, "User Agreement", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                    if (choice == JOptionPane.YES_OPTION) {
                        logMsg = "<HTML><BODY><table width='400px' align='center'><tr><td align='left' > I agree to use the InscriptiFact digital library under the following conditions:</td></tr>"
                                + "<tr><td >&nbsp;</td></tr>"
                                + "<tr><td align='left' style='text-wrap:normal;' >1. All images accessed through InscriptiFact can only be used for study or classroom purposes.</td></tr>"
                                + "<tr><td>&nbsp;</td></tr>"
                                + "<tr><td align='left' style='text-wrap:normal;'>2. Reproduction of any image accessed from InscriptiFact for purposes of publication, e.g., in a book or article-length study or "
                                + "for any commercial purpose, requires prior written permission from the owner(s) of the original text(s) and holder(s) of the photographic rights to the"
                                + " original image. Anyone wishing to publish such an image must further agree to supply all proper credits as specified by the owner(s) of the original "
                                + "text and holder(s) of the photographic rights. Anyone wishing to publish such an image must also comply with any further conditions and/or "
                                + "restrictions required by the original owner(s) and/or holder(s) of the photographic rights.</td></tr><tr><td>&nbsp;</td></tr>"
                                + "<tr><td align='left' style='text-wrap:normal;'>3. Reproduction of any image accessed from InscriptiFact for purposes of reproduction in any form on the Internet also requires "
                                + "prior written permission from the owner(s) of the original text(s) and holder(s) of the photographic rights to the original image. Anyone wishing to "
                                + "reproduce such an image must further agree to supply all proper credits as specified by the owner(s) of the original text and holder(s) of the "
                                + "photographic rights. Anyone wishing to publish such an image must also comply with any further conditions and/or restrictions required by the original"
                                + " owner(s) and/or holder(s) of the photographic rights.</td></tr>"
                                + "<tr><td>&nbsp;</td></tr>"
                                + "</table></BODY></HTML>";
                        Object[] options1 = {"ACCEPT", "DECLINE"};
                        choice = JOptionPane.showOptionDialog(this, logMsg, "User Agreement", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options1, options1[0]);
                        if (choice == JOptionPane.YES_OPTION) {
                            log.debug("ACCEPT");
                            CursorManager.changeCursor(0);
                            actionChanged("ACCEPT", userid, pwd);
                            //Carry on with Logging in 
                            //first update acceptance and then login create new action and new xml
                            return;
                        } else {
                            log.debug("DECLINE");
                            System.exit(0);
                        }
                    } else {
                        System.exit(0);
                    }

                } else {
                    if (logMsg.indexOf("ConnectException") > -1) {
                        logMsg = "Connection to the server is lost. Please reconnect to the internet";
                    } else {
                        logMsg = "Unknown error occured. Please contact administrator";
                    }
                }

                userName = "";
                JOptionPane.showMessageDialog(this, logMsg, "Error", 0);
                loginp.loginPanel.userNameTF.setText("");
                loginp.loginPanel.passwordTF.setText("");
                initData = false;
                logMsg = "";
                return;

            }

            setUpPanels();
            buildUI();
            con.removeAll();

            setInitData(initVector);
            userName = userid;
            navmb.enable(1);
            KeyProcessManager.getInstance().setActive(this);
            con.validate();
            con.repaint();

            validate();
            repaint();
            trapEnter = false;
            //setCursor(new Cursor(0));




        } catch (Exception ie) {
            //System.out.println(ie.getMessage());
            log.error(ie);
        } finally {
            CursorManager.changeCursor(0);
        }

    }

    public void passwordSelectionChanged(String s) {
        if (CursorManager.wait) {
            return;
            //setCursor(new Cursor(3));
            //loginp.loginPanel.userNameTF.transferFocus();
        }
        dialog = new ForgotPasswordWindow();       //this
        //dialog.left.userNameT.gFocus();

        dialog.setVisible(true);
        GlassPaneManager.getInstance().showComponent(dialog);
        ////////:todo:--------change here
        // dialog.right.useridT.disable();
        // dialog.right.mailT.disable();
        // dialog.right.confirmBTR.disable();
        //  dialog.right.cancelBTR.disable();

        // dialog.setWindow();
        //   dialog.requestFocus();
        dialog.left.userNameT.requestFocus();
        //setCursor(new Cursor(0));
        //

        //loginp.loginPanel.userNameTF.grabFocus();
    }

    public void forgotSelectionChanged(String s) {
        if (CursorManager.wait) {
            return;
            //setCursor(new Cursor(3));
        }
        dialog = new ForgotPasswordWindow(); //this


        dialog.setVisible(true);
        GlassPaneManager.getInstance().showComponent(dialog);
        dialog.left.userNameT.disable();
        dialog.left.pwdT.disable();
        dialog.left.newpwdT.disable();
        dialog.left.confirmT.disable();
        dialog.left.confirmBTL.disable();
        dialog.left.cancelBTL.disable();
        dialog.right.useridT.requestFocus();

    }

    ///MainSearchListener implementation
    public void querySelection(int flag) {
        setCurrentSelection(flag);
    }

    public void actionPerformed(ActionEvent actionevent) {
        long timer = System.currentTimeMillis();
        Vector vector = new Vector();
        String[] messages = new String[]{"", "", "", ""};
        String queryType = "";
        String query = "";
        if (CursorManager.wait) {
            return;
        }

        if ((actionevent.getSource() instanceof TrimmedIconButton) && ((TrimmedIconButton) actionevent.getSource()).isSelected()) {
            return;
        }
        Window awindow[] = getOwnedWindows();
        for (int i = 0; i < awindow.length; i++) {
            if (awindow[i].isShowing()) {
                awindow[i].dispose();
            }
        }
        if (!actionevent.getActionCommand().equals("Qm_beginsearch_active") && titlehead.getDividerSize() > 1 && !(actionevent.getSource() instanceof GeneralMenu)) {
            titlehead.setDivider(0);
            titlehead.setEnabled(false);
            titlehead.setOneTouchExpandable(false);
        }
        if (actionevent.getActionCommand().equals("combobox")) {// || actionevent.getActionCommand().equals("textfield")){

            resetToClick();
            if (qdp.textPanel.textNo.getSelectedIndex() == -1) {
                querySelection(0);
                return;
            }
            if (lastSelected == 0 || lastSelected == 2) {
                qdp.textPanel.keyWord.setEnabled(false);

                querySelection(2);
            } else {
                // setCursor(new Cursor(0));
                CursorManager.changeCursor(0);
                JOptionPane.showMessageDialog(this, "Only one Selection is possible", "Message", 1);
                return;
            }
            return;
        }


        if (actionevent.getActionCommand().equals("Spatial")) {
            slp.showGlass();
        } else {
            GlassPaneManager.getInstance().hideAll();
            invalidate();
            validate();
        }


        if (actionevent.getActionCommand().equals("Exit")) {

            images.setEnabled(false);
            closeMe();
            return;
        }


        if (actionevent.getActionCommand().equals("Welcome")) {
            CursorManager.changeCursor(3);
            String result = ResultsUtil.getLogoutResults(userName);
            if (result != null) {
                if (result.indexOf("SUCCESSMSG") == -1) {
                    JOptionPane.showMessageDialog(this, "System could not log you out. Please try again.", "Log Out Error",
                            0);
                    CursorManager.changeCursor(0);
                    return;
                }
                try {
                    images.setEnabled(false);
                    center.removeAll();
                    qdp.textPanel.publicationNo.setText("");
                    qdp.textPanel.keyWord.setText("");
                    qdp.reset();
                } catch (Exception e) {
                }
            } else {
                return;
            }
            CursorManager.changeCursor(Cursor.WAIT_CURSOR);
            if (vd != null) {
                vd.activexPanel.reset();
                if (LoadManager.getInstance().isLoading()) {
                    LoadManager.getInstance().stopAnimation();
                }
                Runtime.getRuntime().gc();
            }
            resetAll();
            con.removeAll();
            userName = "";
            loginp.loginPanel.userNameTF.setText("");
            loginp.loginPanel.passwordTF.setText("");
            PathSelectionManager.getInstance().reset();
            ToolBarManager.getInstance().reset();
            con.add(loginp, "Center");
            CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
            validate();
            repaint();
            loginp.loginPanel.userNameTF.grabFocus();
            msp.qdp = null;
            msp = null;
            qdp = null;
            irp = null;
            vd = null;
            slp = null;
            tdp = null;
            CursorManager.changeCursor(0);
            return;
        }


        if (actionevent.getActionCommand().equals("Qm_beginsearch_active")) {
            qdp.textPanel.textNo.setEnabled(true);
            qdp.textPanel.publicationNo.setEnabled(true);
            qdp.textPanel.keyWord.setEnabled(true);
            resetToClick();
            ImageLoaderManager.resetcache();
            CursorManager.changeCursor(3);
            QueryConstants queryconstants = new QueryConstants();
            if (s2 == null) {
                s2 = new CatalougePanel();
                s2.ce.addActionListener(this);
            }
            if (!firstTime) {
                resetAll();
            } else {
                firstTime = false;
            }
            TextPublicationObject textpublicationobject = (TextPublicationObject) qdp.textPanel.textNo.getSelectedItem();
            String s3 = qdp.textPanel.publicationNo.getText();
            String s6 = qdp.textPanel.keyWord.getText().trim();
            String s10 = "";
            queryType = "isfData";
            query = "";
            if (textpublicationobject == null && !s3.trim().equals("")) {
                CursorManager.changeCursor(0);
                JOptionPane.showMessageDialog(this, "Sorry, cannot search on textpublication no. suffix with out selecting the prefix", "Selection message", 0);
                return;
            }
            if (textpublicationobject != null) {
                if (lastSelected == 2) {
                    s10 = "";
                    s10 = buildQuery(textpublicationobject, s3);
                    queryType = "isfComboData";
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" + s10 + "</QUERY>";
                }
            }
            if (QueryConstants.isQuerySelected()) {
                if (lastSelected == 1) {
                    if (!s6.trim().equals("")) {
                        queryconstants.setFlag(true);
                    }
                    s10 = s10 + queryconstants.getQuery();
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" + s10 + "</QUERY>";
                }
            }
            if (!s6.trim().equals("")) {
                if (lastSelected == 1) {
                    String s16 = buildKeywords(s6).toUpperCase();
                    if (!s16.equals("()")) {
                        s10 = s10 + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='KEYWORDSORPHRASES' VALUE='" + s16 + "' OPERATOR='in' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                    }
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" + s10 + "</QUERY>";
                }
            }
            if (lastSelected == 0 && query.equals("")) {
                CursorManager.changeCursor(0);
                JOptionPane.showMessageDialog(this, "No Matching Records are fetched", "Query Information", 0);
                return;
            }

            String recCount, isfData;
            try {
                vector = ResultsUtil.getResults(queryType, query, messages, false);
                recCount = messages[2];
                isfData = messages[3];

                if (!"".equalsIgnoreCase(messages[1])) {
                    CursorManager.changeCursor(0);
                    JOptionPane.showMessageDialog(this, messages[1], "ERROR ", JOptionPane.ERROR_MESSAGE);
                    return;

                }
            } catch (Exception exp) {

                CursorManager.changeCursor(0);
                log.error(exp);
                JOptionPane.showMessageDialog(this, "Unknown Error in the System. Please Contact Administrator.", "ERROR ",
                        JOptionPane.ERROR_MESSAGE);
                return;

            }


            int l = vector.size();
            if (l == 0) {
                // setCursor(new Cursor(0));
                CursorManager.changeCursor(0);
                JOptionPane.showMessageDialog(this, "No matching records are fetched.", "Query Information", 0);
                return;
            }
            if (l > 0) {
                navmb.enable(2);
                center.removeAll();
                center.add(s2, "West");

            }
            ////////// s2.setIsfData(vector8);
            setPanelColor(Color.black);
            s2.populateData(vector, recCount, isfData);
            invalidate();
            validate();
            repaint();

            //  setCursor(new Cursor(0));
            CursorManager.changeCursor(0);
            //  System.out.println("finished:" + (System.currentTimeMillis() - timer));

            return;
            /*
             * } else { CursorManager.changeCursor(0); return; }
             */

        }
        if (actionevent.getActionCommand().equals("Qm_newsearch_active")) {
            CursorManager.changeCursor(3);
            ImageLoaderManager.resetcache();
            qdp.textPanel.textNo.setEnabled(true);
            qdp.textPanel.publicationNo.setEnabled(true);
            qdp.textPanel.keyWord.setEnabled(true);
            qdp.textPanel.textNo.setSelectedIndex(-1);
            msp.qdp.reset();
            lastSelected = 0;
            msp.qdp.qsp.isEnabled = true;
            resetToClick();

            resetAll();

            CursorManager.changeCursor(0);
            return;
        }
        if (actionevent.getActionCommand().equals("Result")) {
            //  setCursor(new Cursor(3));
            CursorManager.changeCursor(Cursor.WAIT_CURSOR);
            images.setEnabled(false);

            if (vd != null) {
                vd.activexPanel.reset();
                if (LoadManager.getInstance().isLoading()) {
                    LoadManager.getInstance().stopAnimation();
                }
                Runtime.getRuntime().gc();
            }
            center.removeAll();
            navmb.enable(2);
            center.add(s2, "West");
            setPanelColor(Color.black);
            invalidate();
            validate();
            repaint();
            CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
            return;
        }
        if (actionevent.getActionCommand().equals("Main")) {
            //  setCursor(new Cursor(3));
            CursorManager.changeCursor(Cursor.WAIT_CURSOR);
            images.setEnabled(false);
            if (vd != null) {
                vd.activexPanel.reset();
                if (LoadManager.getInstance().isLoading()) {
                    LoadManager.getInstance().stopAnimation();
                }
                Runtime.getRuntime().gc();
            }
            center.removeAll();
            navmb.enable(1);
            center.add(msp, "Center");
            KeyProcessManager.getInstance().setActive(this);
            setPanelColor(new Color(0xa49585));
            validate();
            repaint();
            //setCursor(new Cursor(0));
            CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
            //  System.out.println("finished:" + (System.currentTimeMillis() - timer));
            trapEnter = true;
            return;
        } else {
            trapEnter = false;
        }
        if (actionevent.getActionCommand().equals("List")) {
            // setCursor(new Cursor(3));
            CursorManager.changeCursor(Cursor.WAIT_CURSOR);
            images.setEnabled(false);

            if (vd != null) {
                vd.activexPanel.reset();
                if (LoadManager.getInstance().isLoading()) {
                    LoadManager.getInstance().stopAnimation();
                }
                Runtime.getRuntime().gc();
            }
            center.removeAll();
            navmb.enable(3);
            center.add(tdp, "West");
            setPanelColor(Color.black);
            validate();
            repaint();
            // setCursor(new Cursor(0));
            CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
            //  System.out.println("finished:" + (System.currentTimeMillis() - timer));

            return;
        }
        if (actionevent.getActionCommand().equals("go")) {
            CursorManager.changeCursor(Cursor.WAIT_CURSOR);
            CatalougeTextDataObject catalougetextdataobject = (CatalougeTextDataObject) s2.jTable3.getValueAt(s2.jTable3.getSelectedRow(), 0);
            String textCode = catalougetextdataobject.textCode;
            navmb.disable(3);
            navmb.disable(4);
            navmb.disable(5);
            images.setEnabled(false);

            // ImageLoaderManager.setTextCode(textCode);
            sdType = catalougetextdataobject.displayType;

            if (sdType.equals("1")) {
                CursorManager.changeCursor(new Cursor(3));
                String s11 = "";
                try {
                    queryType = "spatialdata";
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid
                            + "'>";
                    query = query + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfassignedtextno' VALUE='"
                            + textCode + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>"; //ISFBase64.getQueryVal(catalougetextdataobject.textCode)
                    query = query + "</QUERY>";

                    vector = ResultsUtil.getResults(queryType, query, messages, true);





                    if (!"".equalsIgnoreCase(messages[1])) {

                        CursorManager.changeCursor(new Cursor(0));
                        JOptionPane.showMessageDialog(this, messages[1], "Error Message", 0);
                        return;
                    }


                    if (vector.size() == 0) {
                        //  setCursor(new Cursor(0));
                        CursorManager.changeCursor(0);

                        JOptionPane.showMessageDialog(this, " No records are available for spatial search",
                                "Error Message", 0);
                        return;
                    }
                    center.removeAll();
                    navmb.enable(4);
                    slp = new SpatialSearchPanel();
                    slp.addActionListener(this);
                    slp.reset();


                    center.add(slp, "Center");
                    slp.setSpatialData(vector);

                    setPanelColor(Color.black);
                    invalidate();
                    validate();
                    repaint();


                    slp.setDefault();
                    // setCursor(new Cursor(0));
                    CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);

                    /*
                     * } else { return; }
                     */
                } catch (Exception ie) {
                    // setCursor(new Cursor(0));
                    CursorManager.changeCursor(0);
                    log.error(ie);
                    JOptionPane.showMessageDialog(this, "Unknown error occured. Please contact Administrator", "ERROR ",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (sdType.equals("2") || sdType.equals("3")) {
                //   setCursor(new Cursor(3));
                CursorManager.changeCursor(3);

                String s12 = "";
                queryType = "textdivdata";
                try {
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid
                            + "'>";
                    query = query + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfassignedtextno' VALUE='" + (textCode)
                            + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                    query = query + "</QUERY>";


                    vector = ResultsUtil.getResults(queryType, query, messages, true);
                    if (!"".equalsIgnoreCase(messages[1])) {

                        CursorManager.changeCursor(0);

                        JOptionPane.showMessageDialog(this, messages[1], "Error", 0);
                        return;
                    }

                    Vector vector6 = (Vector) vector.elementAt(0);
                    tdp = new TextDivisionPanel();
                    tdp.reset();
                    // setCursor(new Cursor(0));
                    CursorManager.changeCursor(0);

                    navmb.enable(3);
                    center.removeAll();
                    setPanelColor(Color.black);


                    if (vector6 == null || vector6.size() != 0) {
                        tdp.setData(vector6);
                        tdp.setTopTitle((String) vector.elementAt(1));
                        tdp.addObjectListListener(this);
                    } else {
                        // setCursor(new Cursor(0));
                        CursorManager.changeCursor(0);

                        String s19 = "No Records Exist For The Selected Criteria.";
                        JOptionPane.showMessageDialog(new JFrame(), s19, "Click OK", 1);
                    }

                    center.add(tdp, "West");
                    invalidate();
                    validate();
                    repaint();
                    /*
                     * } else { return; }
                     */
                } catch (Exception ie) {
                    // setCursor(new Cursor(0));
                    CursorManager.changeCursor(0);

                    JOptionPane.showMessageDialog(this, "Unknown exception occured. Please contact Administrator", "ERROR ",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }


            } else if (sdType.equals("4")) {
                // setCursor(new Cursor(3));
                CursorManager.changeCursor(3);



                String size = "", list = "";
                String s14 = "";
                queryType = "catphotoid";

                try {
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid
                            + "'>";
                    query = query + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfassignedtextno' VALUE='" + (textCode)
                            + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                    query = query + "</QUERY>";


                    vector = ResultsUtil.getResults(queryType, query, messages, true);
                    if (!"".equalsIgnoreCase(messages[1])) {
                        CursorManager.changeCursor(0);
                        JOptionPane.showMessageDialog(this, messages[1], "Error Message", 0);
                        return;
                    }

                    size = messages[2];
                    list = messages[3];

                } catch (Exception ie) {
                    CursorManager.changeCursor(0);

                    JOptionPane.showMessageDialog(this, "Unknown exception occured. Please contact Administrator", "ERROR ",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (vector.size() == 0) {
                    CursorManager.changeCursor(0);

                    JOptionPane.showMessageDialog(this, "No photoobjects are retrieved matching this query",
                            "Error Message", 0);
                    return;
                }
                center.removeAll();
                navmb.enable(5);
                images.setEnabled(false);
                if (irp == null) {
                    irp = new ImageResultsPanel();
                    //  irp.addQuickViewListener(this);
                }
                irp.reset();

                setPanelColor(Color.black);

                irp.populateData(vector, size, list);
                center.add(irp, "West");
                invalidate();
                validate();
                repaint();


                CursorManager.changeCursor(0);



            }
            // System.out.println("finished:" + (System.currentTimeMillis() - timer));

            return;

        }
        if (actionevent.getActionCommand().equals("SPATIALGO")
                || actionevent.getActionCommand().equals("ImageResults")) {

            String isfList = "";
            String ct = "0";
            CursorManager.changeCursor(3);
            if (vd != null) {
                vd.activexPanel.reset();
                if (LoadManager.getInstance().isLoading()) {
                    LoadManager.getInstance().stopAnimation();
                }
                Runtime.getRuntime().gc();
            }
            // center.removeAll();
            if (actionevent.getActionCommand().equals("SPATIALGO")) {
                String s15 = "";


                try {
                    irp.reset();
                    SpatialListImage spatiallistimage = slp.getSelSpatialListImage();
                    if (spatiallistimage == null) {
                        // setCursor(new Cursor(0));
                        CursorManager.changeCursor(0);

                        JOptionPane.showMessageDialog(this, "Index map is not selected for spatial search",
                                "Error Message", 0);
                        return;
                    }
                    String s1 = spatiallistimage.getIndexMapName().trim();
                    Rectangle rectangle = slp.getSelectedPoints();

                    queryType = "spatialphotoid";
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid
                            + "'>";
                    query = query + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='filename' VALUE='" + ISFBase64.getQueryVal(s1)
                            + "' OPERATOR='=' CONNECTOR=''>";
                    query = query + "</QUERY_CONDITION></CLAUSE><CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='X' VALUE='"
                            + rectangle.getX() + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                    query = query + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='Y' VALUE='" + rectangle.getY()
                            + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                    query = query + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='WIDTH' VALUE='" + rectangle.getWidth()
                            + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                    query = query + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='HEIGHT' VALUE='" + rectangle.getHeight()
                            + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE></QUERY>";


                    vector = ResultsUtil.getResults(queryType, query, messages, true);


                    if (!"".equalsIgnoreCase(messages[1])) {

                        CursorManager.changeCursor(0);

                        JOptionPane.showMessageDialog(this, messages[1], "Error Message", 0);
                        return;
                    }

                    if (vector.size() == 0) {
                        // setCursor(new Cursor(0));
                        CursorManager.changeCursor(0);

                        JOptionPane.showMessageDialog(this, "No photoobjects are retrieved matching this query", "Error Message", 0);
                        return;
                    }
                    center.removeAll();
                    irp.reset();
                    ct = messages[2];
                    isfList = messages[3];

                } catch (Exception ie) {
                    //  setCursor(new Cursor(0));
                    CursorManager.changeCursor(0);

                    JOptionPane.showMessageDialog(this, "Unknown exception occured. Please contact Administrator", "ERROR ",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // irp.setPhotoIds(vector);
                //                irp.setData(vector);
                // CursorManager.changeCursor(0);

            } else {
                center.removeAll();
            }
            navmb.enable(5);
            // download.setEnabled(true);
            images.setEnabled(false);


            setPanelColor(Color.black);
            if (actionevent.getActionCommand().equals("SPATIALGO")) {
                irp.reset();
                slp.clearAll();
                irp.populateData(vector, ct, isfList);
            }
            center.add(irp, "West");
            invalidate();
            validate();
            repaint();


            // setCursor(new Cursor(0));
            CursorManager.changeCursor(0);
            // System.out.println("finished:" + (System.currentTimeMillis() - timer));

            return;

        }
        if (actionevent.getActionCommand().equals("Spatial")) {
            // setCursor(new Cursor(3));
            CursorManager.changeCursor(3);

            images.setEnabled(false);

            if (vd != null) {
                vd.activexPanel.reset();
                if (LoadManager.getInstance().isLoading()) {
                    LoadManager.getInstance().stopAnimation();
                }
                Runtime.getRuntime().gc();
            }
            center.removeAll();
            navmb.enable(4);
            center.add(slp, "Center");
            setPanelColor(Color.black);
            validate();
            repaint();
            // setCursor(new Cursor(0));
            CursorManager.changeCursor(0);
            // System.out.println("finished:" + (System.currentTimeMillis() - timer));

            return;
        }
        if (actionevent.getActionCommand().equals("Desktop")) {
            CursorManager.changeCursor(3);

            final Vector thumbs = irp.tvp.tv.getSelectedThumbs();

            if (thumbs.size() == 0) {
                JOptionPane.showMessageDialog(this, "You have not yet selected an image to view. \n Select an image by clicking the thumbnail picture (not the thumbnail header)."
                        + "\n  You may select from up to 5 images before clicking 'View Images'", "Select Thumbnails", JOptionPane.OK_OPTION);

                navim7.setSelected(false);
                CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
                return;
            }

            navmb.enable(6);

            if (vd != null) {
                vd.reset();
            }
            titlehead.setDivider(10);
            titlehead.setEnabled(true);
            titlehead.setOneTouchExpandable(true);
            boolean hasRTI = false;
            for (int k = 0; k < thumbs.size(); k++) {
                ThumbData thumbdata = (ThumbData) thumbs.elementAt(k);
                if (thumbdata.getType().equalsIgnoreCase("RTI") || thumbdata.getType().equalsIgnoreCase("HSH")) {
                    hasRTI = true;
                    break;
                }

            }
            images.setEnabled(true);

            vd.jp.setDividerLocation(1);
            center.removeAll();
            center.add(vd, "Center");
            KeyProcessManager.getInstance().setActive(vd.getKeyHandler());
            center.addNotify();
            vd.addNotify();
            vd.jp.addNotify();
            setPanelColor(Color.black);
            vd.validate();
            vd.repaint();
            validate();
            repaint();
            CursorManager.changeCursor(Cursor.WAIT_CURSOR);


            Vector vector2 = irp.getSelectedData();
            vd.setSelectedData(vector2);

            Dimension d = vd.vd.getCatButtonSize();
            vd.jp.setDividerLocation(d.width);



            PopulateThread populate = new PopulateThread(thumbs, vd);

            populate.setPriority(9);
            populate.start();
            long size = 0;
            try {
                while (size == 0) {
                    Thread.sleep(5000);
                    size = LoadManager.getInstance().getActual();
                }
            } catch (Exception exp) {
                log.error(exp);
            }

            // CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
            if (size > 209715200 && size <= 241172400)//reduced the limit to 200 from 220
            {
                CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
                PTMFrameManager.getInstance().setDiffuseOperation(false, true);
                JOptionPane.showMessageDialog(this, "Total size of RTI files being retrieved is greater than 200 MB. Performance of application may be effected.",
                        "Warning Message", 0, ResolutionManager.getInstance().loadImage("images/isf.jpg"));

            } else if (size > 241172400) {
                CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
                PTMFrameManager.getInstance().setDiffuseOperation(true, false);
                JOptionPane.showMessageDialog(this, "The " + LoadManager.getInstance().getNotLoaded() + " will not be loaded because of size limitations.  Please view the selected images one by one.",
                        "Warning Message", 0, ResolutionManager.getInstance().loadImage("images/isf.jpg"));

            } else {
                PTMFrameManager.getInstance().setDiffuseOperation(true, false);
            }
            return;
        }


        if (actionevent.getActionCommand().equals("Images")) {
            String cacheDir = CacheManagerConstants.cacheFolderName;
           // String saveDir = CacheManagerConstants.folderName;
            String downloadDir = CacheManagerConstants.downloadFolderName;

            vector = irp.tvp.tv.getSelectedThumbs();
            if (vector.size() <= 0) {
                JOptionPane.showMessageDialog(this, "Please select a thumbnail to download the image.",
                        "Error Message", 0, ResolutionManager.getInstance().loadImage("images/isf.jpg"));
                return;
            }

            Hashtable files = new Hashtable();
            String ext = "";


            Object[] str = new Object[vector.size()];
            for (int i = 0; i < vector.size(); i++) {
                ThumbData thumbdata = (ThumbData) vector.elementAt(i);
                if (thumbdata.getType().equalsIgnoreCase("RTI") || thumbdata.getType().equalsIgnoreCase("HSH")) {
                    ext = thumbdata.getType().equalsIgnoreCase("RTI") ? ".ptm" : thumbdata.getType().equalsIgnoreCase("HSH") ? ".rti" : "";
                } else {
                    ext = "d.jp2";
                }
                String key = thumbdata.getPhotoIdentificationNumber() + " (" + thumbdata.getHeader() + ")";
                files.put(key, thumbdata.getPhotoIdentificationNumber() + ext);
                //list.add(thumbdata.getPhotoIdentificationNumber() + ext);
                str[i] = key;
            }
///todo: based on image selected download the appropriate image. Check fo the  ptm already downloaded.
            String selectedImage = (String) JOptionPane.showInputDialog(this,
                    "The downloaded image will be located in\n\"" + //  "You have requested to download an JPEG2000 image.\n The jp2 image will be downloaded to\n\"" +
                    downloadDir + "\" folder.",
                    "Please Select The Image To Download",
                    JOptionPane.QUESTION_MESSAGE,
                    ResolutionManager.getInstance().loadImage("images/isf.jpg"),
                    str,
                    str[0]);
            //System.out.println("SelectedImage :"+selectedImage);

            String fName = (String) files.get(selectedImage);
            //RTI
            if (fName.endsWith(".ptm") || fName.endsWith(".rti") || fName.endsWith(".hsh")) {
                File source = new File(cacheDir + File.separator + fName);
               // File destination = new File(saveDir + File.separator + fName);
                File anotherDest = new File(CacheManagerConstants.downloadFolderName+File.separator+fName);
                try {
                   // PTMUtils.copyPTM(source, destination);
                    PTMUtils.copyPTM(source, anotherDest);
                } catch (Exception exp) {
                    log.error(exp);
                }
                JOptionPane.showMessageDialog(this, "Requested RTI file ("+fName+") has been successfuly downloaded. It can be found in the "+downloadDir+" folder.", "Information", 1);
            } else if (fName.endsWith(".jp2")) {
                String name = "", size = "";
                for (int x = 0; x < str.length; x++) {
                    if (str[x] == selectedImage) {
                        ThumbData td = (ThumbData) vector.elementAt(x);
                        name = td.getPhotoIdentificationNumber();
                        size = td.getArchivalFileSize();//td.getActualSidSize() ;//td.getArchivalFileSize();
                        if (size == null) {
                            size = "0";
                        }
                        break;
                    }
                }

                try {
                    long l = Long.parseLong(size);
                    Downloader d = new Downloader(name, size, this);
                } catch (Exception e) {
                    log.debug("size is not a number:" + size);
                    log.error(e);
                }


            }

        }
        return;

    }

    public void loggedout(String msg) {
        GlassPaneManager.getInstance().hideAll();
        try {
            images.setEnabled(false);
            center.removeAll();
            qdp.textPanel.publicationNo.setText("");
            qdp.textPanel.keyWord.setText("");
            qdp.reset();
        } catch (Exception e) {
        }
        resetAll();
        con.removeAll();
        // setJMenuBar(null);
        userName =
                "";
        loginp.loginPanel.userNameTF.setText("");
        loginp.loginPanel.passwordTF.setText("");
        PathSelectionManager.getInstance().reset();
        ToolBarManager.getInstance().reset();
        con.add(loginp, "Center");
        //loginp.loginPanel.userNameTF.requestFocus();

        invalidate();

        validate();

        repaint();

        loginp.loginPanel.userNameTF.grabFocus();
        JOptionPane.showMessageDialog(this, msg, "INFORMATION ",
                JOptionPane.INFORMATION_MESSAGE);
        // setCursor(new Cursor(0));
        CursorManager.changeCursor(0);

    }

    public void disconnected() {
        JOptionPane.showMessageDialog(this, "Unable To Connect To The Server. Please Check Your Connection.", "Connection Error ",
                JOptionPane.ERROR_MESSAGE);
        // setCursor(new Cursor(0));
        CursorManager.changeCursor(0);

    }

    public void raiseError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error ",
                JOptionPane.ERROR_MESSAGE);
        // setCursor(new Cursor(0));
        CursorManager.changeCursor(0);

    }

    public void objectSelected(TreeChild treechild) {
        //  setCursor(new Cursor(3));
        CursorManager.changeCursor(3);

        String s = "";
        String s1 = "";
        String queryType = "";

        String[] messages = new String[]{"", "", "", ""};
        boolean flag = false;
        Object obj = null;
        String query = "";
        Vector vector = new Vector();

        if (treechild instanceof TreeParent) {
            if (treechild.isLeaf()) {
                TextDivision textdivision = (TextDivision) treechild.getChild();
                s1 = textdivision.getSpatialSearchCode();
                query = query + "<CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='isfassignedtextno' VALUE='" + (textdivision.getIsfAssignedTextNo().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                query = query + "<CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='textdesctitle' VALUE='" + ISFBase64.getQueryVal(textdivision.getTextDescTitle().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                query = query + "<CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='textdivision' VALUE='" + ISFBase64.getQueryVal(textdivision.getTextDivisionName().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
                query = query + "</QUERY>";
                if (s1.equals("100")) {
                    obj = textdivision;
                    queryType = "spatialdata";
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" + query;
                } else {
                    queryType = "textdivphotoid";
                    query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" + query;
                }

            }
        } else if (treechild instanceof TreeChild) {
            TextDivisionSubDivision textdivisionsubdivision = (TextDivisionSubDivision) treechild.getChild();
            s1 = textdivisionsubdivision.getSpatialSearchCode();
            query = query + "<CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='isfassignedtextno' VALUE='" + (textdivisionsubdivision.getIsfAssignedTextNo().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
            query = query + "<CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='textdesctitle' VALUE='" + ISFBase64.getQueryVal(textdivisionsubdivision.getTextDescTitle().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
            query = query + "<CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='textdivision' VALUE='" + ISFBase64.getQueryVal(textdivisionsubdivision.getTextDivisionName().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
            query = query + "<CLAUSE CONNECTOR='AND'><QUERY_CONDITION KEY='textsubdivision' VALUE='" + ISFBase64.getQueryVal(textdivisionsubdivision.getTextDivisionSubDivisionName().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
            query = query + "</QUERY>";
            if (s1.equals("100")) {
                obj = textdivisionsubdivision;
                queryType = "spatialdata";
                query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" + query;
            } else {
                queryType = "textsuddivphotoid";
                query = "<QUERY TYPE='" + queryType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>" + query;

            }

        }

        if (obj == null && s1.equals("100")) {
            CursorManager.changeCursor(0);
            JOptionPane.showMessageDialog(this, "No textdivision or textsubdivision or spatial search is selected ", "Spatial Search Error Message", 0);
            return;
        } else {
            try {
                vector = ResultsUtil.getResults(queryType, query, messages, true);

                if (!"".equalsIgnoreCase(messages[1])) {
                    CursorManager.changeCursor(0);
                    JOptionPane.showMessageDialog(this, messages[1], "Error Message", 0);
                    return;
                }
                if (vector.size() == 0) {
                    CursorManager.changeCursor(0);
                    JOptionPane.showMessageDialog(this, "No search objects are retrieved matching this query", "Error Message", 0);
                    return;
                }
            } catch (Exception ie) {
                log.error(ie);
                CursorManager.changeCursor(0);
                JOptionPane.showMessageDialog(this, "Unknown error occurred. Please contact Administrator", "ERROR ",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (s1.equals("100")) {
                slp.reset();
                irp.reset();
                slp.setSpatialData(vector);
                navmb.enable(4);
                navmb.disable(5);
                navmb.disable(6);
                images.setEnabled(false);
                center.removeAll();
                center.add(slp, "Center");
                setPanelColor(Color.black);
                validate();
                repaint();
                slp.setDefault();
                CursorManager.changeCursor(0);

            } else if (s1.equals("000")) {
                slp.reset();
                irp.reset();
                navmb.disable(4);
                center.removeAll();
                navmb.enable(5);
                images.setEnabled(false);
                irp.populateData(vector, messages[2], messages[3]);
                center.add(irp, "West");
                center.setBackground(new Color(0));
                validate();
                repaint();
                setPanelColor(Color.black);
                CursorManager.changeCursor(0);
            } else {
                CursorManager.changeCursor(0);
                String s7 = "Search  Code Did Not Match";
                JOptionPane.showMessageDialog(new JFrame(), s7, "Click OK", 1);
                return;

            }
        }
    }

    private String buildQuery(TextPublicationObject textpublicationobject, String s) {
        String query = "";
        if (textpublicationobject.pre.equals("M") || textpublicationobject.pre.equals("A")) {
            if (s.equals("")) {
                query = query + "<CLAUSE CONNECTOR=''>";
                query = query + "<QUERY_CONDITION KEY='MAINTEXTORPUBLCNNOPREFIX' VALUE='" + ISFBase64.getQueryVal(textpublicationobject.toString().trim()) + "' OPERATOR='=' CONNECTOR='OR'></QUERY_CONDITION>";
                query = query + "<QUERY_CONDITION KEY='ALTTEXTORPUBLCNNOPREFIX' VALUE='" + ISFBase64.getQueryVal(textpublicationobject.toString().trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION>";
                query = query + "</CLAUSE>";
            } else {
                query = query + "<CLAUSE CONNECTOR='OR'>";
                query = query + "<QUERY_CONDITION KEY='MAINTEXTORPUBLCNNOPREFIX' VALUE='" + ISFBase64.getQueryVal(textpublicationobject.toString().trim()) + "' OPERATOR='=' CONNECTOR='AND'></QUERY_CONDITION>";
                query = query + "<QUERY_CONDITION KEY='MAINTEXTORPUBLCNNOSUFFIX' VALUE='" + ISFBase64.getQueryVal(s.trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION>";
                query = query + "</CLAUSE>";
                query = query + "<CLAUSE CONNECTOR=''>";
                query = query + "<QUERY_CONDITION KEY='ALTTEXTORPUBLCNNOPREFIX' VALUE='" + ISFBase64.getQueryVal(textpublicationobject.toString().trim()) + "' OPERATOR='=' CONNECTOR='AND'></QUERY_CONDITION>";
                query = query + "<QUERY_CONDITION KEY='ALTTEXTORPUBLCNNOSUFFIX' VALUE='" + ISFBase64.getQueryVal(s.trim()) + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION>";
                query = query + "</CLAUSE>";
            }
        }
        return query;
    }

    private String buildKeywords(String s) {
        String query = "";
        String separator = "";
        String token = "";
        Vector vector = new Vector();
        for (StringTokenizer stringtokenizer = new StringTokenizer(s, "\"", true); stringtokenizer.hasMoreTokens();) {
            token = stringtokenizer.nextToken();
            if (token.equals("\"")) {
                query = query + separator + stringtokenizer.nextToken();
                vector.addElement(stringtokenizer.nextToken());
            } else {
                for (StringTokenizer stringtokenizer1 = new StringTokenizer(token, " "); stringtokenizer1.hasMoreTokens();) {
                    String strTok = stringtokenizer1.nextToken();
                    query = query + separator + strTok;
                    vector.addElement(strTok);
                    separator = ",";
                }
            }
            separator = ",";
        }
        return query;
    }

    private void buildGeneralMenuBar() {
        genmb = new JMenuBar();
        blank = new GeneralMenu("Blank", "images/GM_blank1.jpg");
        blank.addMouseListener(this);
        session = new GeneralMenu("Session", "images/GM_session_active.jpg");
        session.addMouseListener(this);
        edu = new GeneralMenu("Educational Access", "images/GM_eduaccess_active.jpg");
        edu.addMouseListener(this);
        search = new GeneralMenu("Search Options", "images/GM_search_active.jpg");
        search.addMouseListener(this);
        bginfo = new GeneralMenu("Background Information", "images/GM_backinfo_active.jpg");
        bginfo.addMouseListener(this);
        tools = new GeneralMenu("Tools", "images/GM_tools_active.jpg");
        tools.addMouseListener(this);
        comm = new GeneralMenu("Communication", "images/GM_communication_active.jpg");
        comm.addMouseListener(this);
        help = new GeneralMenu("Help", "images/GM_help_active.jpg");
        help.addMouseListener(this);
        download = new GeneralMenu("Download", "images/GM_download_active.jpg");
        download.addMouseListener(this);
        genmb.add(blank);
        genmb.add(session);
        genmb.add(edu);
        genmb.add(search);
        genmb.add(bginfo);
        genmb.add(tools);
        genmb.add(comm);
        genmb.add(help);
        genmb.add(download);

    }

    private void buildNavigationalMenuBar() {
        navmb = new ToolBarPanel();
        navmb.setBackground(new Color(0xa49585));
        navmb.setBorderPainted(false);
        ImageIcon imageicon = ResolutionManager.getInstance().getImageIcon("images/nm_welcome_select.jpg");
        navim1 = new TrimmedIconButton(imageicon);
        navim1.setActionCommand("Welcome");
        navim2 = new TrimmedIconButton("Main", "images/nm_mainsearch_active.jpg", "images/nm_mainsearch_disabled.jpg", "images/nm_mainsearch_select.jpg");
        navim3 = new TrimmedIconButton("Result", "images/nm_resultslisting_active.jpg", "images/nm_resultslisting_disabled.jpg", "images/nm_resultslisting_select.jpg");
        navim4 = new TrimmedIconButton("List", "images/nm_listoftext_active.jpg", "images/nm_listoftext_disabled.jpg", "images/nm_listoftext_select.jpg");
        navim5 = new TrimmedIconButton("Spatial", "images/nm_spatialsearch_active.jpg", "images/nm_spatialsearch_disabled.jpg", "images/nm_spatialsearch_select.jpg");
        navim6 = new TrimmedIconButton("ImageResults", "images/nm_imgresults_active.jpg", "images/nm_imgresults_disabled.jpg", "images/nm_imgresults_select.jpg");
        navim7 = new TrimmedIconButton("Desktop", "images/nm_viewdesktop_active.jpg", "images/nm_viewdesktop_disabled.gif", "images/nm_viewdesktop_select.gif");
        navim1.addActionListener(this);
        navim2.addActionListener(this);
        navim3.addActionListener(this);
        navim4.addActionListener(this);
        navim5.addActionListener(this);
        navim6.addActionListener(this);
        navim7.addActionListener(this);
        navmb.add(navim1, true);
        navmb.add(navim2, true);
        navmb.add(navim3, true);
        navmb.add(navim4, true);
        navmb.add(navim5, true);
        navmb.add(navim6, true);
        navmb.add(navim7, true);
    }

    private void generateSessionMenuItems() {
        newmi = new GeneralMenuItem("New");
        open = new GeneralMenuItem("Open");
        save = new GeneralMenuItem("Save");
        close = new GeneralMenuItem("Close");
        print = new GeneralMenuItem("Print");
        session.add(newmi);
        newmi.setEnabled(false);
        session.add(open);
        open.setEnabled(false);
        session.add(save);
        save.setEnabled(false);
        session.add(close);
        close.setEnabled(false);
        session.add(print);
        print.setEnabled(false);
    }

    private void generateEducationalAccessMenuItems() {
        start = new GeneralMenuItem("Start");
        genInfo = new GeneralMenuItem("General Information");
        edu.add(start);
        start.setEnabled(false);
        edu.add(genInfo);
        genInfo.setEnabled(false);
    }

    private void generateSearchMenuItems() {
        selDB = new GeneralMenuItem("Select Database");
        search.add(selDB);
        selDB.setEnabled(false);
    }

    private void generateBackgroundMenuItems() {
        curPage = new GeneralMenuItem("Current Page");
        contents = new GeneralMenuItem("Contents");
        index = new GeneralMenuItem("Index");
        keywords = new GeneralMenuItem("Keywords");
        bginfo.add(curPage);
        curPage.setEnabled(false);
        bginfo.add(contents);
        contents.setEnabled(false);
        bginfo.add(index);
        index.setEnabled(false);
        bginfo.add(keywords);
        keywords.setEnabled(false);
    }

    private void generateToolsMenuItems() {
        fts = new GeneralMenuItem("Full Text Search");
        lf = new GeneralMenuItem("Lexicon Features");
        tools.add(fts);
        fts.setEnabled(false);
        tools.add(lf);
        lf.setEnabled(false);
    }

    private void generateCommunicationsMenuItems() {
        email = new GeneralMenuItem("E mail");
        cws = new GeneralMenuItem("Collaborative Workspace");
        messaging = new GeneralMenuItem("Messaging");
        comm.add(email);
        email.setEnabled(false);
        comm.add(cws);
        cws.setEnabled(false);
        comm.add(messaging);
        messaging.setEnabled(false);
    }

    private void generateCommunicationSubMenuItems() {
        dnw = new GeneralMenuItem("Define New Workspace");
        ccws = new GeneralMenuItem("Current Collaborative Workspace");
        chat = new GeneralMenuItem("Chat");
        cws.add(dnw);
        dnw.setEnabled(false);
        cws.add(ccws);
        ccws.setEnabled(false);
        cws.add(chat);
        chat.setEnabled(false);
    }

    private void generateHelpMenuItems() {
        cp = new GeneralMenuItem("Current Page");
        cont = new GeneralMenuItem("Contents");
        ind = new GeneralMenuItem("Index");
        keyw = new GeneralMenuItem("Keywords");
        help.add(cp);
        cp.setEnabled(false);
        help.add(cont);
        cont.setEnabled(false);
        help.add(ind);
        ind.setEnabled(false);
        help.add(keyw);
        keyw.setEnabled(false);
    }

    private void generateDownloadMenuItems() {
        images = new GeneralMenuItem("Images");
        images.addActionListener(this);
        cat = new GeneralMenuItem("Cataloging");
        imagesAndCat = new GeneralMenuItem("Images & Cataloging");
        download.add(images);
        images.setEnabled(false);
        download.add(cat);
        cat.setEnabled(false);
        download.add(imagesAndCat);
        imagesAndCat.setEnabled(false);
    }

    public void mouseEntered(MouseEvent mouseevent) {
        if (lastSelected != 2) {
            try {
                if (msp != null && msp.qdp.textPanel.textNo.isPopupVisible()) {
                    msp.qdp.textPanel.textNo.hidePopup();
                }

                Object obj = mouseevent.getSource();
                if (obj instanceof InscriptGenMenu) {
                    ((InscriptGenMenu) mouseevent.getSource()).doClick(1);
                }

                if (obj instanceof JPanel || obj instanceof JLayeredPane) {
                    msp.qdp.qspParent.addPanels();
                }

            } catch (Exception e) {
            }
        }

    }

    public void mouseExited(MouseEvent mouseevent) {
        if (msp != null && msp.qdp.textPanel.textNo.isPopupVisible()) {
            msp.qdp.textPanel.textNo.hidePopup();
        }

        Object obj = mouseevent.getSource();
    }

    public void mousePressed(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
    }

    public void mouseClicked(MouseEvent mouseevent) {
        Object obj = mouseevent.getSource();


        if (obj instanceof GeneralMenu) {

            if (!GeneralMenuManager.isGMBClicked) {
                GeneralMenuManager.isGMBClicked = true;
            }

        }
        if (obj instanceof TrimmedIconMenu) {
            if (lastSelected == 2) {
                msp.qdp.qsp.isClicked = false;
                return;

            } else {
                msp.qdp.qsp.isClicked = true;
                qdp.textPanel.textNo.setEnabled(false);
                qdp.textPanel.publicationNo.setEnabled(false);
                qdp.textPanel.keyWord.setEnabled(true);

            }

            msp.qmu.validate();
            msp.qmu.repaint();
        }
        if (obj instanceof JTextArea) {

            resetToClick();
            String s3 = qdp.textPanel.publicationNo.getText();
            String s6 = qdp.textPanel.keyWord.getText().trim();
            if (lastSelected == 0 || lastSelected == 1) {
                qdp.textPanel.textNo.setEnabled(false);
                qdp.textPanel.publicationNo.setEnabled(false);
                querySelection(1);
            } else {
                CursorManager.changeCursor(0);
                return;

            }
        }

        if (obj instanceof JTextField) {

            resetToClick();
            if (lastSelected == 0 || lastSelected == 2) {
                qdp.textPanel.keyWord.setEnabled(false);
                querySelection(2);
            } else {
                CursorManager.changeCursor(0);

                return;

            }

        }

        if (obj instanceof JPanel || obj instanceof JLayeredPane) {

            resetToClick();

        }

        if (obj instanceof TrimmedPanel) {
            resetToClick();
        }

    }

    private void resetToClick() {

        try {

            if (msp != null && msp.qdp != null && msp.qdp.qsp != null) {
                msp.qdp.qsp.isClicked = false;
            }

        } catch (Exception e) {
            log.error(e);
        }

    }

    public void closeMe() {

        resetAll();
        if (!"".equals(userName)) {
            CursorManager.changeCursor(3);

            String s4 = "<QUERY TYPE='Logout' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid
                    + "'><CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='useridentity' VALUE='" + ISFBase64.getQueryVal(userName)
                    + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE></QUERY>";
            String result = (String) RequestProxy.getRequestDispatcher().handleQueryEvent(s4);
            userName = "";
        }

        if (qdp != null) {
            qdp.textPanel.publicationNo.setText("");
            qdp.textPanel.keyWord.setText("");
            qdp.reset();
        }

        resetToClick();
        if (center != null) {
            center.removeAll();
        }

        center = null;
        if (collector != null) {
            collector.stop();
            //cleanup the cache
        }

        if (PTMFrameManager.getVersion() >= 1.5) {
            log.debug("calling Clean Cache");
            ImageUtils.cleanCache();

        }
        System.exit(0);
    }

    public void setPanelColor(Color color) {
        copyright.setBackground(color);
        navmb.setBackground(color);
        center.setBackground(color);
        cr.setBackground(color);
        jp.setBackground(color);
    }

    public void resetAll() {
        ToolBarManager toolbarmanager = ToolBarManager.getInstance();
        try {
            if (navmb != null) {
                navmb.enable(1);

                if (toolbarmanager.isEnabled(4)) {
                    slp.reset();
                    navmb.disable(4);
                }

                if (toolbarmanager.isEnabled(2)) {
                    s2.reset();
                    navmb.disable(2);
                }

                if (toolbarmanager.isEnabled(5)) {
                    irp.reset();
                    vd.reset();
                    navmb.disable(5);
                    images.setEnabled(false);
                }

                if (toolbarmanager.isEnabled(3)) {
                    tdp.reset();
                    navmb.disable(3);
                }

            }
        } catch (NullPointerException ne) {
            log.error(ne);
        } catch (Exception exception) {
            log.error(exception);
        }

    }

    public void resetState() {
        loginp.reset();
    }

    private void setCurrentSelection(int selected) {
        lastSelected = selected;
        if (lastSelected == 2) {
            msp.qdp.qsp.isEnabled = false;
        } else if (lastSelected != 2) {
            msp.qdp.qsp.isEnabled = true;
        }

    }

    public static void main(String args[]) {

        System.setProperty("sun.awt.noerasebackground", "true");
        InscriptConfigLoader.setAppName("ISF");
        System.out.println("Called main method first");
        InscriptConfigLoader.initialize("InscriptIni.ini");
        System.out.println("Called main method second");
        String version = InscriptConfigLoader.getInstance().getProperty("version");
        String strTitle = InscriptConfigLoader.getInstance().getProperty("title");
        String title = strTitle +" - "+ version ;
        if (PTMFrameManager.getVersion() >= 1.5) {
            Properties sys = System.getProperties();
            String os = sys.getProperty("os.name");
            if (os != null && ( os.toUpperCase().indexOf("MAC OS") >-1 || os.toUpperCase().indexOf("OS X") >-1)) {
                sys.put("com.sun.media.jai.disableMediaLib", "true");
                sys.put("com.apple.mrj.application.apple.menu.about.name", title);
                System.setProperties(sys);
            }

        }


        try {

            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.getDefaults().put("ToolTip.background", new ColorUIResource(new Color(252, 250, 182)));
            UIManager.getDefaults().put("InternalFrame.closeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/exit.gif")));
            UIManager.getDefaults().put("InternalFrame.minimizeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/max.gif")));
            UIManager.getDefaults().put("InternalFrame.maximizeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/norm.gif")));
            UIManager.getDefaults().put("InternalFrame.iconifyIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/min.gif")));
            UIManager.getDefaults().put("InternalFrame.icon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/isf.jpg")));
            UIManager.getDefaults().put("InternalFrame.titleFont", new FontUIResource(new Font("Arial", 3, 10)));
            UIManager.getDefaults().put("InternalFrame.activeTitleForeground", new ColorUIResource(new Color(0xac5539)));
            UIManager.getDefaults().put("InternalFrame.activeTitleBackground", new ColorUIResource(new Color(0xd9d9d9)));
            UIManager.getDefaults().put("InternalFrame.inactiveTitleBackground", new ColorUIResource(new Color(0xd9d9d9)));
            UIManager.getDefaults().put("InternalFrame.inactiveTitleForeground", new ColorUIResource(Color.black));

            UIManager.getDefaults().put("ScrollBar.background", new ColorUIResource(new Color(0xd9d9d9)));
            UIManager.getDefaults().put("ScrollBar.borderColor", new ColorUIResource(Color.gray));
            UIManager.getDefaults().put("ScrollBar.buttonBorder", new LineBorder(Color.black, 1));
            UIManager.getDefaults().put("ScrollBar.disabledBorder", new LineBorder(Color.black, 1));
            UIManager.getDefaults().put("ScrollBar.thumb", new ColorUIResource(new Color(0xa7a7a7)));
            UIManager.getDefaults().put("ScrollBar.trackBackground", new ColorUIResource(new Color(0xa7a7a7)));
            UIManager.getDefaults().put("ScrollBar.border", null);

            UIManager.getDefaults().put("ProgressBar.selectionBackground", Color.blue);
            UIManager.getDefaults().put("ProgressBar.selectionForeground", Color.white);
            UIManager.getDefaults().put("ProgressBar.foreground", new Color(84, 146, 87));
            UIManager.getDefaults().put("ProgressBar.repaintInterval", new Integer(50));
            UIManager.getDefaults().put("ProgressBar.cycleTime", new Integer(5000));
            UIManager.put("MenuItemUI", "isf.controls.plaf.InscriptMenuItemUI");
            UIManager.put("SplitPaneDividerUI", "javax.swing.plaf.basic.BasicSplitPaneDivider");

        } catch (Exception ie) {
            log.error(ie);
        }

        DesktopTheme dt = new DesktopTheme(new Color(0xd9d9d9));
        MetalLookAndFeel.setCurrentTheme(dt);

        
       /* try{
        Class.forName("isf.net.request.NetManager");
        }catch(ClassNotFoundException cnfe){
            log.error(cnfe);
        }*/
        NetManager.initialize();
        
        int connect = RequestProxy.getRequestDispatcher().checkConnection();
        if (connect == 1) {
            JOptionPane.showMessageDialog(new JFrame(), "Unable To Establish Connection To Remote Server. Please Check Your Connectivity.", "Error", 0);
            System.exit(0);
            return;

        } else if (connect == 2) {
            JOptionPane.showMessageDialog(new JFrame(), " Remote Server is currently unavailable. Please contact Administrator.", "Information", 1);
            System.exit(0);
            return;

        } else if (connect == 3) {
            JOptionPane.showMessageDialog(new JFrame(), "Unknown Error. Please contact Administrator.", "Error", 0);
            System.exit(0);
            return;

        }
        Dimension dimension = ResolutionManager.getInstance().getScreen();
        if ((dimension.width < 1024 || dimension.width > 2560) || dimension.height < 768 || dimension.height > 1600) {
            Debug.debug("Please change the system's resolution");
            String s = "<html><b><font face='Arial' size='12pts' color='#000000'> &nbsp;&nbsp; This appl"
                    + "ication supports only 1024 x 768 <br> &nbsp; or 1200 x 1024 or 1600 x 1200 "
                    + " resolutions.<br>Please set the system resolution as specified.</font></b></html>";
            JOptionPane.showMessageDialog(new JFrame(), s, "Click OK", 1);
            return;

        } else if (dimension.width > 1600 || dimension.height > 1200) {

            String s = "<html><b><font face='Arial' size='12pts' color='#000000'> &nbsp;&nbsp; This appl"
                    + "ication is best viewed with 1024 x 768 <br> &nbsp; or 1200 x 1024 or 1600 x 1200 "
                    + " resolutions.</font></b></html>";

            JOptionPane.showMessageDialog(new JFrame(), s, "Information", 1);

        }
        InscriptUI inscriptui = new InscriptUI();
        inscriptui.setTitle(title);
        inscriptui.collector = new ISFGarbageCollector();
        
    }

    public void changeCursor(int c) {
        if (c == -1) {
            if (oldCursor == null) {
                oldCursor = Cursor.getDefaultCursor();
            }

            setCursor(oldCursor);
            oldCursor =
                    null;
        } else {
            oldCursor = getCursor();
            setCursor(new Cursor(c));

        }

    }

    public void changeCursor(Cursor r) {
        if (r != null) {
            oldCursor = getCursor();
            setCursor(r);
        }

    }

    public void focusGained(FocusEvent event) {
        if (loginp.isShowing()) {
            loginp.loginPanel.userNameTF.grabFocus();
        }

    }

    public void focusLost(FocusEvent event) {
    }
    private ISFGarbageCollector collector;

    public boolean trapKeys() {
        if (msp != null) {
            return msp.isShowing();
        } else {
            return false;
        }

    }

    public void fireKeyEvent(KeyEvent event) {
        if (event.getID() == KeyEvent.KEY_RELEASED) {
            if (event.getKeyCode() == KeyEvent.VK_ENTER && trapEnter) {
                actionPerformed(new ActionEvent(msp.qdp.bp.begin, 0, "Qm_beginsearch_active"));
            } else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                trapEnter = true;

            }
        }
    }

    public Rectangle getFrameBounds() {
        return getBounds();
    }
}
