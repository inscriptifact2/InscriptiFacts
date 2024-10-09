package com.isf.app.view.containers;

import com.isf.app.models.ThumbData;
import com.isf.app.view.controls.TrimmedIconButton;
import com.isf.app.view.gui.QuickLookHeader;
import isf.common.Debug;
import isf.common.request.RequestProxy;
import isf.common.utils.InscriptConfigLoader;
import isf.common.utils.LogManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

public class QuickViewPanel extends TrimmedPanel
        implements ActionListener {

    private QuickLookHeader qlh;
    public TrimmedIconButton downArrow;
    public ActionListener al;
    public boolean hasImage = false;
    //JLabel jlabel;
    QuickImagePanel qip;
    JLabel jlabel1;
    Logger log = LogManager.getLogger(com.isf.app.view.containers.QuickViewPanel.class);
    public QuickViewPanel() {
    }

    public QuickViewPanel(Insets insets, ThumbData thumbdata) {
        super(insets);
        setLayout(new BorderLayout());
        setBackground(Color.black);
        TrimmedPanel trimmedpanel = new TrimmedPanel(new Insets(0, 0, 0, 0));
        trimmedpanel.setLayout(new BorderLayout(0, 0));
        trimmedpanel.setBackground(new Color(0));
        String s = thumbdata.getHeader();
        qlh = new QuickLookHeader(s);
        ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/down_arrow.jpg");
        downArrow = new TrimmedIconButton(imageicon);
        Debug.debug("downArrow from qvp");
        int i = imageicon.getIconHeight();
        TrimmedPanel trimmedpanel1 = new TrimmedPanel() {

            public Dimension getPreferredSize() {
                // ImageIcon imageicon2 = ResolutionManager.getInstance().getImageIcon("images/sid_512.jpg");
                ImageIcon imageicon3 = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/down_arrow.jpg");
                return new Dimension(512, imageicon3.getIconHeight() * 2);
            }

        };
        trimmedpanel1.setLayout(new BorderLayout(0, 0));
        trimmedpanel1.add(qlh, "Center");
        trimmedpanel1.add(downArrow, "East");
        trimmedpanel.add(trimmedpanel1, "North");
        //  ImageIcon imageicon1 = thumbdata.getIcon();
        /*jlabel = new JLabel() {

            public Dimension getPreferredSize() {
                return new Dimension(512, 512);
            }
            public Insets getInsets(){
                return new Insets(0,0,0,0);
            }

        };*/
        qip = new QuickImagePanel();
        // trimmedpanel.add(jlabel, "Center");
        trimmedpanel.add(qip, "Center");

        String s1 = thumbdata.getLabel();
        TrimmedPanel trimmedpanel2 = new TrimmedPanel() {
            public Dimension getPreferredSize() {
                // ImageIcon imageicon2 = ResolutionManager.getInstance().getImageIcon("images/sid_512.jpg");
                ImageIcon imageicon3 = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/down_arrow.jpg");
                return new Dimension(512, imageicon3.getIconHeight() * 2);
            }
        };
        trimmedpanel2.setLayout(new FlowLayout(0, 0, 5));
        jlabel1 = new JLabel(s1);
        jlabel1.setFont(new Font("Arial", 1, 15));
        jlabel1.setForeground(new Color(0));
        trimmedpanel2.add(jlabel1);
        trimmedpanel.add(trimmedpanel2, "South");
        add(trimmedpanel);
        String s2 = thumbdata.getPhotoIdentificationNumber();
        String extension = "";
        if (thumbdata.getType().equalsIgnoreCase("SID"))
            extension = "q.jpg";
        else if (thumbdata.getType().equalsIgnoreCase("RTI"))
            extension = "_ptm_q.jpg";
        else if (thumbdata.getType().equalsIgnoreCase("HSH"))
            extension = "_hsh_q.jpg";


        /*  String s3 = "<QUERY TYPE='quickimage' MODE=''>";
          s3 = s3 + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='photoid' VALUE='" + s2 + "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
          s3 = s3 + "</QUERY>";
          RequestDispatcherObject requestdispatcherobject = new RequestDispatcherObject();
          byte abyte0[] = (byte[])requestdispatcherobject.handleQueryEvent(s3);
          imageicon1 = new ImageIcon(abyte0);*/
        String cat = InscriptConfigLoader.getInstance().getProperty("Locquick");
        if (cat == null) cat = "Quicklook";
        String tmp = "cat=" + cat + "&item=/" + s2.trim() + extension + "&cp=0.5,0.5&wid=512&hei=512";

        //  jlabel.setIcon(new ImageRequestObject().fetchImage(tmp));
        ImageIcon ic = RequestProxy.getRequestDispatcher().fetchImage(tmp);
        /*if (ic.getDescription().indexOf("<ERRMSG") > -1) {
            JOptionPane.showMessageDialog(this, "Connection to the server is lost. Please reconnect to the server.", "ERROR IN CONNECTION", 0);
            //move to isfwelcomescreen
        } */
        if (ic == null)
            hasImage = false;
            //JOptionPane.showMessageDialog(this, "Connection to the server is lost. Please reconnect to the server.", "ERROR IN CONNECTION", 0);
        else {
            hasImage = true;
            /*  jlabel.setIcon(ic);
              jlabel.setHorizontalAlignment(JLabel.CENTER );
              jlabel.setVerticalAlignment(JLabel.CENTER );
  */
            qip.setImage(ic);
            log.debug("ImageHeight:" + ic.getIconHeight() + "---ImageWidth:" + ic.getIconWidth());

        }

    }

    public void updateThumbData(ThumbData thumbdata) {
        qlh.setTitle(thumbdata.getHeader());
        jlabel1.setText(thumbdata.getLabel());
        String s2 = thumbdata.getPhotoIdentificationNumber();

        String cat = InscriptConfigLoader.getInstance().getProperty("Locquick");
        if (cat == null) cat = "Quicklook";
        String tmp = "cat=" + cat + "&item=/" + s2.trim() + "q.jpg" + "&cp=0.5,0.5&wid=512&hei=512";

        //  jlabel.setIcon(new ImageRequestObject().fetchImage(tmp));
        ImageIcon ic = RequestProxy.getRequestDispatcher().fetchImage(tmp);
        //  jlabel.setIcon(null);
        if (ic == null)
            hasImage = false;
            //JOptionPane.showMessageDialog(this, "Connection to the server is lost. Please reconnect to the server.", "ERROR IN CONNECTION", 0);
        else {
            hasImage = true;
            // jlabel.setIcon(ic);
            qip.setImage(ic);
            if (isVisible() && isShowing()) {
                revalidate();
                repaint();
            }
        }
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        downArrow.addActionListener(actionlistener);
    }

    public void actionPerformed(ActionEvent actionevent) {
        TrimmedIconButton trimmediconbutton = (TrimmedIconButton) actionevent.getSource();
        if (trimmediconbutton != downArrow) ;
    }

    public void addMouseListener(MouseListener mouselistener) {
        qlh.addMouseListener(mouselistener);
    }

    public void addMouseMotionListener(MouseMotionListener mousemotionlistener) {
        qlh.addMouseMotionListener(mousemotionlistener);
    }

    public Dimension getPreferredSize() {
        ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/down_arrow.jpg");
        return new Dimension(512, 512 + imageicon.getIconHeight() * 4);
    }

    public boolean isValidIcon() {
        boolean valid = false;
        try {
            //valid = (jlabel.getIcon().getIconHeight() > -1);
            valid = (qip.getImage().getIconHeight() > -1);
        } catch (Exception e) {
        } finally {
            return valid;
        }
    }


}
