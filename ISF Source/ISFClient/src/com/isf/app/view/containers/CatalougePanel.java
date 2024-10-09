package com.isf.app.view.containers;

import com.isf.app.exception.NoDataFoundException;
import com.isf.app.models.CatalougeTableModel;
import com.isf.app.models.CatalougeTextDataObject;
import com.isf.app.utils.*;
import com.isf.app.view.controls.*;
import com.isf.app.view.gui.InscriptFileNameFilter;
import com.isf.app.view.listeners.ABListener;
import isf.common.request.RequestProxy;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.InscriptConfigLoader;
import isf.common.utils.LogManager;
import isf.controls.utils.CatalogueConstants;
import isf.controls.utils.CursorManager;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.InscriptTextPane;
import isf.controls.view.controls.InscriptButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class CatalougePanel extends JPanel
        implements ActionListener, ABListener {

    private Vector data;
    private Hashtable mainData;
    private Vector ranges;
    private String textcode;
    private String tempRange;
    private String range;
    private boolean flag;
    private boolean start;

    private HashMap rangesData;
    private int currentRange = 1;
    private int totalRecords = 0;
    private int max = 0;
    Logger log = LogManager.getLogger(com.isf.app.view.containers.CatalougePanel.class);

    private ResolutionManager rm;
    private int width;
    double r[];
    private JPanel jPanel1;
    private JButton jButton1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    //  private InscriptTable jTable2;
    public InscriptTable jTable3;
    private InscriptButton head;
    private InscriptTextPane cat;
    // private CatalougeButton goto1;
    private CatalougeButton next;
    private CatalougeButton back;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JButton jButton3;
    private ImageIcon ti1;
    // private AnchorButton a2;
    private JLabel a1;
    private CatalougeTableModel ctm;
    private CatalougeDataManager cdm;
    public CatalogueCellEditor ce;
    //boolean CanProcess = false;
    int selectedInt = 0;
    int fontsize = 0;

    public CatalougePanel() {
        data = new Vector();
        textcode = "";
        tempRange = "";
        range = "";
        flag = false;
        start = true;
        rm = ResolutionManager.getInstance();
        r = ResolutionManager.getInstance().getRatio();
        mainData = new Hashtable();
        ranges = new Vector();
        cdm = new CatalougeDataManager();
        CatalougeObserver.abl = this;

        rangesData = new HashMap();
        max = Integer.parseInt(InscriptConfigLoader.getInstance().getProperty("TestListSize"));
        initComponents();
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        fontsize = 2;
        if (current.width > 1450 && current.height > 800){
            fontsize = 3;
        }else if(current.width <=1280 && current.height <=768)
        {
            fontsize= 1;
        }
    }

    /* public void setIsfData(Vector vector) {  // System.out.println("vector from cp-------------------------------------------------"+vector);
    cdm.setIsfAssignedTextData(vector);
    ranges = cdm.getRanges();
    setData();
    }*/


    public void populateData(Vector v, String tot, String isfrange) {

        totalRecords = Integer.parseInt(tot);
        int ct = (1 - 1) * max + 1;
        int lim = ct + max - 1;
        if ((lim) > totalRecords)
            lim = totalRecords;
        String str = "Records " + ct + " - " + lim + " of " + totalRecords + ".";
        if (totalRecords > 500)
            str += " Please refine your search.";
        a1.setText("<html><b><font face='Trebuchet MS' size='8pts' color='3300CC'>" + str +
                "</font></b></html>");
        StringTokenizer strTok = new StringTokenizer(isfrange, "#");
        currentRange = 1;
        int cur = 1;
        while (strTok.hasMoreTokens()) {
            rangesData.put(cur + "", strTok.nextToken());
            cur++;
        }


        setData(v);

        if (totalRecords <= max) {
            back.setEnabled(false);
            next.setEnabled(false);
        } else {
            next.setEnabled(true);
            back.setEnabled(false);
        }

        CacheManagerObject.getInstance().storeToCache(new CacheEntry(currentRange + "v.s2"), v);
        jScrollPane2.getVerticalScrollBar().setValue(0);


    }


    public void setData(Vector v) {
        ctm.resetData();
        mainData = new Hashtable();


        int k = 0;
        String strTemp = "";

        for (int l = v.size(); k < l; k++) {
            try {
                Hashtable hashtable = (Hashtable) v.elementAt(k);
                CatalougeTextDataObject catalougetextdataobject = new CatalougeTextDataObject(15, 30);
                strTemp = ((String) hashtable.get("MAINTEXTORPUBLCNNO"));
                if (strTemp == null)
                    strTemp = (String) hashtable.get("ISFASSIGNEDTEXTNO");
                if (hashtable.get("TEXTDESCRIPTIVETITLE") == null)
                    hashtable.put("TEXTDESCRIPTIVETITLE", "");
                catalougetextdataobject.setValue(strTemp, ((String) hashtable.get("TEXTDESCRIPTIVETITLE")));
                catalougetextdataobject.setDisplayType(((String) hashtable.get("TEXTSEARCHANDDISPLAYCODE")));
                String isftext = (String) hashtable.get("ISFASSIGNEDTEXTNO");
                catalougetextdataobject.setTextCode(isftext);
                ctm.addElement(catalougetextdataobject);
                String CD = buildCatalougeData(isftext, hashtable);
                String DCD = buildDubinData(isftext, hashtable);
                Vector vec = new Vector();
                vec.addElement(CD);
                vec.addElement(DCD);
//                System.out.println(isftext + ":" + DCD);
                //   jScrollPane2.invalidate();
                // jScrollPane2.validate();
                // jScrollPane2.repaint();
                mainData.put(isftext, vec);

                //**// jTable3.setRowHeights();
            } catch (Exception e) {
                log.debug("Error:" + strTemp);
                log.error(e);
            }

        }
    }

    /*public void setData() {
    range = (String) ranges.firstElement();
    start = false;

    data = cdm.getRangeData(range);

    jScrollPane1.getVerticalScrollBar().setValue(0);
    jScrollPane2.getVerticalScrollBar().setValue(0);
    ctm.setData(data);
    jScrollPane2.invalidate();
    jScrollPane2.validate();
    jScrollPane2.repaint();
    jTable3.setRowHeights();
    mainData = cdm.getData();
//	System.out.println("maindata from ctp--- "+mainData);
buildAnchorButtons();
}*/

    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel1.setBackground(Color.black);
        jButton1 = new InscriptButton(rm.getImageIcon("images/catalougeinactive.jpg"));
        jButton1.setActionCommand("catalougeinactive");
        jButton1.setSelectedIcon(rm.getImageIcon("images/catalouge.jpg"));
        jButton1.setSelected(true);
        jButton1.addActionListener(this);
        jPanel2 = new JPanel() {

            public Insets getInsets() {
                return new Insets(0, 14, 0, 0);
            }

        };
        jPanel2.setBackground(Color.black);
        jPanel3 = new JPanel();
        jButton3 = new InscriptButton(rm.getImageIcon("images/dublininactive.jpg"));
        jButton3.setActionCommand("dublininactive");
        jButton3.addActionListener(this);
        jButton3.setSelectedIcon(rm.getImageIcon("images/dublin.jpg"));
        setLayout(new BorderLayout());
        ImageIcon imageicon = rm.getImageIcon("images/dublininactive.jpg");
        jPanel1.setLayout(new FlowLayout(0, 0, 0));
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setAlignmentX(0.0F);
        jScrollPane1 = new JScrollPane(22, 31);
        jButton1.setMargin(new Insets(0, 0, 0, 0));
        jButton1.setBorderPainted(false);
        jPanel1.add(jButton1);
        add(jPanel1, "North");
        head = new InscriptButton("images/listoftextretrieved.jpg");
        head.setMargin(new Insets(0, 0, 0, 0));
        head.setBorderPainted(false);
        ti1 = rm.getImageIcon("images/listoftextretrieved.jpg");
        jPanel6 = new JPanel() {

            public Insets getInsets() {
                return new Insets(0, 0, 0, 0);
            }

            public Dimension getPreferredSize() {
                return new Dimension(ti1.getIconWidth(), ti1.getIconHeight());
            }

        };
        jPanel6.setLayout(new FlowLayout(0, 0, 0));
        jPanel6.setAlignmentY(0.0F);
        jPanel6.setAlignmentX(0.0F);
        jPanel6.add(head);
        jPanel5 = new JPanel() {

            public Insets getInsets() {
                return new Insets(0, 0, 0, 0);
            }

            public Dimension getPreferredSize() {
                return new Dimension(ti1.getIconWidth(), ti1.getIconHeight());
            }

        };
        jScrollPane2 = new JScrollPane(22, 31);
        jScrollPane2.getViewport().setBackground(Color.black);
        ce = new CatalogueCellEditor();
        CatalougePanelTableCellRenderer catalougepaneltablecellrenderer = new CatalougePanelTableCellRenderer(new Dimension(ti1.getIconWidth(), ti1.getIconHeight()));
        catalougepaneltablecellrenderer.addActionListener(this);
        //   ctm = new CatalougeTableModel(mainData);
        ctm = new CatalougeTableModel(new Vector());
        jTable3 = new InscriptTable(ctm);

        jTable3.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent mouseevent) {
                try {
                    int i = jTable3.getSelectedRow();
                    //System.out.println("SELECTEDROW:"+i);
                    CatalougeTextDataObject catalougetextdataobject = (CatalougeTextDataObject) jTable3.getValueAt(i, 0);
                    textcode = catalougetextdataobject.textCode;
                    //  System.out.println(" textcode----from ctp  :"+textcode);
                    if (!flag) {
                        jScrollPane1.getVerticalScrollBar().setValue(0);
                        cat.setText((String) ((Vector) mainData.get(textcode)).elementAt(0));
                        // setDocumentContent((String) ((Vector) mainData.get(textcode)).elementAt(0))  ;
                        cat.setCaretPosition(0);
                    } else {
                        jScrollPane1.getVerticalScrollBar().setValue(0);
                        cat.setText((String) ((Vector) mainData.get(textcode)).elementAt(1));
                        // setDocumentContent((String) ((Vector) mainData.get(textcode)).elementAt(1)  );
                        cat.setCaretPosition(0);
                    }
                } catch (Exception exception) {
                    jScrollPane1.getVerticalScrollBar().setValue(0);
                    cat.setCaretPosition(0);
                }
            }

        });
        //   jTable3.setRowHeights();
        jTable3.setBackground(Color.black);
        jTable3.getColumnModel().getColumn(0).setCellRenderer(catalougepaneltablecellrenderer);
        jTable3.getColumnModel().getColumn(0).setCellEditor(ce);
        jScrollPane2.setViewportView(jTable3);
        jPanel7 = new JPanel() {

            public Insets getInsets() {
                return new Insets(0, 0, 0, 0);
            }

        };
        jPanel7.setBackground(new Color(0xd9d9d9));
        //     goto1 = new CatalougeButton("images/goto.jpg");
        //     goto1.setActionCommand("goto");
        //     goto1.addActionListener(this);
        next = new CatalougeButton("images/next.gif");//todo next.jpg
        next.setActionCommand("next");
        next.addActionListener(this);
        back = new CatalougeButton("images/back.gif"); //todo back.jpg
        back.setActionCommand("back");
        back.addActionListener(this);
        jPanel7.setLayout(new FlowLayout(2, 10, (ti1.getIconHeight() - next.getIcon().getIconHeight()) / 2));
        jPanel7.add(back);
        //       jPanel7.add(goto1);
        jPanel7.add(next);
        jPanel8 = new JPanel() {

            public Insets getInsets() {
                return new Insets(0, 0, 0, 0);
            }

        };
        jPanel8.setBackground(new Color(0xd9d9d9));
        jPanel8.setLayout(new FlowLayout(0, 5, 0));
        a1 = new JLabel();
        jPanel8.add(a1);
        jPanel9 = new JPanel() {

            public Insets getInsets() {
                return new Insets(0, 0, 0, 0);
            }

            public Dimension getPreferredSize() {
                return new Dimension(ti1.getIconWidth(), ti1.getIconHeight());
            }

        };
        jPanel9.setBackground(new Color(0xd9d9d9));
        jPanel9.setLayout(new BorderLayout(0, 0));
        jPanel9.add(jPanel7, "East");
        jPanel9.add(jPanel8, "West");
        jPanel5.setLayout(new BorderLayout(0, 0));
        jPanel5.add(jPanel6, "North");
        jPanel5.setBackground(Color.black);
        jPanel5.add(jScrollPane2, "Center");
        jPanel5.add(jPanel9, "South");
        jPanel2.setLayout(new BorderLayout());
        jPanel2.setAlignmentY(0.0F);
        jPanel2.setAlignmentX(0.0F);
        cat = new InscriptTextPane(new Dimension(imageicon.getIconWidth() - 14, 9000), false);
//        System.out.println("DIMENSION : SIZE:" + imageicon.getIconWidth() + ":::");
        cat.setEditable(false);
        cat.setSelectionColor(Color.white);
        cat.setContentType("text/html");
        jScrollPane1.setViewportView(cat);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(100);
        jPanel2.add(jScrollPane1, "Center");
        width = cat.getPreferredSize().width;
        //width = imageicon.getIconWidth() - 14;


        jPanel3.setLayout(new FlowLayout(0, 0, 0));
        jButton3.setMargin(new Insets(0, 0, 0, 0));
        jButton3.setBorderPainted(false);
        jPanel3.add(jButton3);
        jPanel2.add(jPanel3, "North");
        jPanel4 = new JPanel() {

            public Dimension getPreferredSize() {
                return new Dimension(ti1.getIconWidth(), 50);
            }

        };
        jPanel4.setBackground(Color.black);
        add(jPanel2, "West");
        add(jPanel5, "Center");
        add(jPanel4, "South");
    }

    public void actionPerformed(ActionEvent actionevent) {
        if (actionevent.getSource() == jButton3) {
            try {
                flag = true;
                if(textcode.trim().length()>0)
                  cat.setText((String) ((Vector) mainData.get(textcode)).elementAt(1));
                else
                    cat.setText("");
                // setDocumentContent((String) ((Vector) mainData.get(textcode)).elementAt(1)) ;
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setCaretPosition(0);
                jButton3.setSelected(true);
                jButton1.setSelected(false);
            } catch (Exception exception) {
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setCaretPosition(0);
            }
        } else if (actionevent.getSource() == jButton1) {
            try {
                flag = false;
                if(textcode.trim().length()>0)
                    cat.setText((String) ((Vector) mainData.get(textcode)).elementAt(0));
                else
                    cat.setText("");
                // setDocumentContent((String) ((Vector) mainData.get(textcode)).elementAt(0));
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setCaretPosition(0);
                jButton3.setSelected(false);
                jButton1.setSelected(true);
            } catch (Exception exception1) {
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setCaretPosition(0);
            }
        } /*else if (actionevent.getActionCommand().equals("goto")) {
        if (start) {

        start = false;
        setCursor(new Cursor(3));
        // goto1.setEnabled(false);


        data = cdm.getRangeData(tempRange);
        mainData = cdm.getData();

        jTable3.removeEditor();
        jScrollPane2.getVerticalScrollBar().setValue(0);
        ctm.setData(data);
        cat.setText("");
        jTable3.setRowHeights();
        //back.setEnabled(true);
        //next.setEnabled(true);
        // goto1.setEnabled(false);
        validate();
        setCursor(new Cursor(0));
        //start = true;

        }
        }*/
        else if (actionevent.getActionCommand().equals("next")) {
            try {
            CursorManager.changeCursor(3);
                currentRange++;
                setData(getNextData());


                jTable3.removeEditor();
                jScrollPane2.getVerticalScrollBar().setValue(0);
                //// ctm.setData(data);
                cat.setText("");
                CursorManager.changeCursor(0);
                // start = true;
            } catch (NoDataFoundException nodatafoundexception) {
                start = true;
                //setCursor(new Cursor(0));
                CursorManager.changeCursor(0);

                JOptionPane.showMessageDialog(this, nodatafoundexception.getMessage(), "Error", 0);
            } catch (Exception exception5) {
                start = true;
                CursorManager.changeCursor(0);
                log.error(exception5);
            }

        } else if (actionevent.getActionCommand().equals("back")) {
            start = false;

            try {

                CursorManager.changeCursor(3);
                currentRange--;
                setData(getNextData());
                jTable3.removeEditor();
                jScrollPane2.getVerticalScrollBar().setValue(0);
                cat.setText("");
                CursorManager.changeCursor(0);

            } catch (NoDataFoundException nodatafoundexception1) {
                start = true;
                //    setCursor(new Cursor(0));
                CursorManager.changeCursor(0);

                JOptionPane.showMessageDialog(this, nodatafoundexception1.getMessage(), "Error", 0);
            } catch (Exception exception6) {
                start = true;
                CursorManager.changeCursor(0);
                log.error(exception6);
            }
        } else {
            int i = 0;
            for (int j = ranges.size(); i < j; i++) {
                if (actionevent.getActionCommand().equals((i + 1) + "")) {

                    if (start) {
                        switchOffAnchors();

                        AnchorButton anchorbutton = (AnchorButton) actionevent.getSource();
                        anchorbutton.setSelected(true);
                        selectedInt = i;
                        tempRange = anchorbutton.getRange();
                        cdm.setRange(tempRange);
                        if (i + 1 == 1) {
                            // goto1.setEnabled(true);
                            next.setEnabled(false);
                            back.setEnabled(false);
                        } else if (i + 1 == j) {
                            back.setEnabled(false);
                            // goto1.setEnabled(true);
                            next.setEnabled(false);
                        } else {
                            back.setEnabled(false);
                            //  goto1.setEnabled(true);
                            next.setEnabled(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "System is building cache for the next page. Please try after a few seconds", ":: Information ::", 1, new ImageIcon("images/isf.jpg"));
                    }
                }
            }

        }
        cat.setCaretPosition(0);
    }

    private void switchOffAnchors() {
        Component acomponent[] = jPanel8.getComponents();
        int i = 0;
        for (int j = acomponent.length; i < j; i++) {
            if (acomponent[i] instanceof AnchorButton) {
                ((AnchorButton) acomponent[i]).setSelected(false);
            }
        }

    }

    private void enableNextAnchor() {
        Component acomponent[] = jPanel8.getComponents();
        int i = 1;
        for (int j = acomponent.length; i < j; i++) {
            AnchorButton anchorbutton = (AnchorButton) acomponent[i];
            if (!anchorbutton.isSelected()) {
                continue;
            }
            if (i < acomponent.length - 1) {
                ((AnchorButton) acomponent[i + 1]).setSelected(true);
                selectedInt = i + 1;
                anchorbutton.setSelected(false);
                /*   if(i + 1 == j - 1)
                {
                next.setEnabled(false);
                back.setEnabled(true);
                }
                if(i + 1 > 1 && i + 1 < j - 1)
                {
                back.setEnabled(true);
                next.setEnabled(true);
                }*/
            }
            break;
        }

    }

    private void enablePrevAnchor() {
        Component acomponent[] = jPanel8.getComponents();
        int i = 1;
        for (int j = acomponent.length; i < j; i++) {
            AnchorButton anchorbutton = (AnchorButton) acomponent[i];
            if (!anchorbutton.isSelected()) {
                continue;
            }
            if (i > 1) {
                ((AnchorButton) acomponent[i - 1]).setSelected(true);
                selectedInt = i - 1;
                anchorbutton.setSelected(false);
                /* if(i - 1 == 1)
                {
                goto1.setEnabled(false);
                next.setEnabled(true);
                back.setEnabled(false);
                } else
                if(i - 1 == j - 1)
                {
                back.setEnabled(true);
                goto1.setEnabled(false);
                next.setEnabled(false);
                } else
                {
                back.setEnabled(true);
                goto1.setEnabled(false);
                next.setEnabled(true);
                }*/
            } /*else
            if(i == 1)
            {
            goto1.setEnabled(false);
            next.setEnabled(true);
            back.setEnabled(false);
            }*/
            break;
        }

    }

    private void buildAnchorButtons() {
        Component acomponent[] = jPanel8.getComponents();
        for (int i = 1; i < acomponent.length; i++) {
            if (acomponent[i] != a1) {
                jPanel8.remove(acomponent[i]);
            }
        }

        /* if(ranges.size() <= 1)
        {*/
        //   goto1.setEnabled(false);
        back.setEnabled(false);
        next.setEnabled(false);
        /*  } else
        {
        goto1.setEnabled(false);
        back.setEnabled(false);
        next.setEnabled(true);
        }*/
        ////////////////////removedby kamesh
        /*  int j = 0;
        for (int k = ranges.size(); j < k; j++) {
        a2 = new AnchorButton((j + 1) + "");
        a2.setRange((String) ranges.elementAt(j));
        a2.addActionListener(this);
        if (j == 0) {
        a2.setSelected(true);
        }
        jPanel8.add(a2);
        }*/

        jPanel8.validate();
        validate();
        jPanel8.repaint();
    }

    public String buildCatalougeData(String s, Hashtable hashtable) {
        String as[][] = CatalogueConstants.textCatalogueNames;
        // String s1 = "<html><body ><table border=0   align='left' width='" + width + "' cellspacing=2 cellpadding=2>";
        //String s2 = "<tr><td colspan =2></td></tr></table></body></html>";

      /*  String s1 = "<html><head><style>" +
                ".headtext{color:#000000;font-family :Arial;font-size:12pt;font-weight :bold;text-decoration:underline;} " +
                ".bullet{color:#AC5539;font-family :Arial;font-size:11pt;font-weight :plain;text-indent:2;margin-left:10px;} " +
                ".bodytext{color:#000000;font-family :Arial;font-size:11pt;font-weight :plain;} </style> " +
                "</head><body><table border=0 align='left' width='" + width + "' cellspacing=2 cellpadding=2>";
        String s2 = "<tr><td colspan =2></td></tr></table></body></html>";      //  String s3 = "<tr><td colspan =2><hr></td></tr>";*/
        String s1 = "<html><head><style> table{border: 1px black solid; background-color: black;  }  th,  td{ word-wrap:break-word; background-color: white;  }</style></head><body><table cellspacing=1  align='left' width='99%' >";
        String s2 = "</table></body></html>";


        //  String s3 = "<tr><td colspan =2><hr></td></tr>";
        StringBuffer sb = new StringBuffer();
      //  sb.append(s1);
        // int i = 0;
        // for (int j = mainData.size(); i < j; i++) {
        // Hashtable hashtable = (Hashtable) mainData.elementAt(i);
        if (s.equals(hashtable.get("ISFASSIGNEDTEXTNO"))) {
            int k = 0;
            for (int l = as.length; k < l;) {
                //   System.out.println("as[k][1] ------- "+as[k][1]);
                //   System.out.println("as[k][0] ------- "+as[k][0]);
                //    System.out.println("hashtable.get(as[k][1]) ------- "+hashtable.get(as[k][1]));
                //      System.out.println("obj  : "+hashtable.get(as[k][1])+" --s : "+as[k][0]);
                try {
                    if (!as[k][1].equalsIgnoreCase("ISPARTOFCORPUS")) {
                        sb.append(getString(hashtable.get(as[k][1]), as[k][0], ""));
                        k++;
                    } else {
                        Vector corpus = (Vector) hashtable.get(as[k][1]);
                        Vector corpusCategory = (Vector) hashtable.get(as[k + 1][1]);
                        if(corpus != null && corpusCategory != null){
                      //  System.out.println("Corpus:"+as[k][l]+".."+corpus+"--"+as[k+1][l]);
                        for (int cor = 0; cor < corpus.size(); cor++) {
                            sb.append(getString((String) corpus.elementAt(cor), as[k][0], ""));
                            if (corpusCategory.size() > cor) {
                                sb.append(getString((String) corpusCategory.elementAt(cor)
                                        , as[k + 1][0], ""));
                            }
                        }
                        }
                        k += 2;
                    }

                } catch (Exception exception) {
                    log.error(exception);
                    exception.printStackTrace();
                }
            }

        }
      //  sb.append(s2);
        //  }

         return sb.toString().trim().length() >0 ? sb.insert(0, s1).append(s2).toString(): "";
    }

    private String buildDubinData(String s, Hashtable hashtable) {
        int fontsize = 2;
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        if (current.width > 1450 && current.height > 800){
            fontsize = 3;
    } else if(current.width <=1280 && current.height <=768)
        {
            fontsize= 1;
        }
        String as[][] = CatalogueConstants.textDublinNames;
        /* String s1 = "<html><body><table border=0 align='left' width='" + width + "' cellspacing=2 cellpadding=2>";
         String s2 = "<tr><td colspan =2></td></tr></table></body></html>";*/

      /*  String s1 = "<html><head><style>" +
                ".headtext{color:#000000;font-family :Arial;font-size:13pt;font-weight :bold;text-decoration:underline;} " +
                ".bullet{color:#AC5539;font-family :Arial;font-size:12pt;font-weight :plain;text-indent:2;margin-left:10px;} " +
                ".bodytext{color:#000000;font-family :Arial;font-size:12pt;font-weight :plain;} </style> " +
                "</head><body><table border=0 align='left' width='" + width + "' cellspacing=2 cellpadding=2>";
        String s2 = "<tr><td colspan =2></td></tr></table></body></html>";      //  String s3 = "<tr><td colspan =2><hr></td></tr>";


        String s3 = "";
        String s4 = "<tr><td width='" + ((width * 52) / 100 -2) + "' class='headtext'>" + " ";
        String s5 = "</td><td width='" + ((width * 48) / 100 +2) + "' >" + "&nbsp;</td></tr>";
        String s6 = "<tr><td colspan =2><hr></td></tr>";
        String s7 = "<li type=disc class='bullet'>";
        */
        String s1 = "<html><head><style> table{border: 1px black solid; background-color: black;  }  th,  td{ word-wrap:break-word; background-color: white;  }</style></head><body><table cellspacing=1  align='left' width='99%' >";
        String s2 = "</table></body></html";
        String s3="";
        String s4 = "<tr ><td colspan=2 >" + "<b><u><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></u></b><br>&nbsp;</td></tr>";
        String s6 = "";//<tr><td colspan =2><hr ></td></tr>";
        String s7 = "<li>";


        // String s8 = "";
        StringBuffer cat = new StringBuffer();
        StringBuffer sb = new StringBuffer();
       // sb.append(s1);
        //  int i = 0;
        // for (int j = mainData.size(); i < j; i++) {
        //     Hashtable hashtable = (Hashtable) mainData.elementAt(i);
        if (s.equals(hashtable.get("ISFASSIGNEDTEXTNO"))) {
           // System.out.println("length=" + as.length);
            int k = 0;
            for (int l = as.length; k < l; k++) {
               // System.out.println("Processing :" + k);
                try {
                    switch (k) {
                        case 1: // '\002'
                        case 4: // '\003'
                        case 6: // '\005'
                        case 7: // '\006'
                        case 8: // '\007'
                        case 9: // '\b'
                        case 10: // '\t'
                        case 11: // '\n'
                        case 12: // '\013'
                        case 13:
                            //  case 14: // '\016'
                        case 16: // '\017'
                        case 17: // '\020'
                        case 18:
                        case 21: // '\024'
                        case 23: // '\025'
                        case 24: // '\026'
                        case 25: // '\027'
                        case 26: // '\030'
                        case 27: // '\032'
                        case 29: // '\033'
                        case 31: // '\035'
                        case 32: // '\036'
                        case 33: // '\037'
                        case 36:
                        default:
                            break;

                        case 0: // '\0'
                            String s9 = s4 + "TYPE" + s5;
                            //   System.out.println("hastype");
                            String s10 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (s10.equals("")) {
                                sb.append(s9);
                                sb.append(cat.toString());
                                sb.append(s6);//+ s8 + s6);
                                //s8 = "";
                                cat = new StringBuffer();
                            }
                            if (!s10.equals("")) {
                                cat.append(s10);

                            }
                            s10 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s10.equals("")) {
                                cat.append(s10);
                            }

                            if (!cat.toString().equals("")) {
                                // sb.append(s9 + s8 + s6);
                                sb.append(s9);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 2: // '\001'
                            String s11 = s4 + "TITLE" + s5;
                            //  System.out.println("hastitle");
                            String s12 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s12.equals("")) {
                                cat.append(s12);
                            }
                            s12 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s12.equals("")) {
                                cat.append(s12);
                            }
                            s12 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s12.equals("")) {
                                cat.append(s12);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s11);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 5: // '\004'
                            String s13 = s4 + "DESCRIPTION" + s5;
                            String s14 = getString(hashtable.get(as[k][1]), as[k][0], s7);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], s7);

                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 4][1]), as[k + 4][0], s7);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 5][1]), as[k + 5][0], s7);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 6][1]), as[k + 6][0], s7);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 7][1]), as[k + 7][0], s7);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            s14 = getString(hashtable.get(as[k + 8][1]), as[k + 8][0], s7);
                            if (!s14.equals("")) {
                                cat.append(s14);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s13);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 14: // '\f'12
                            String s15 = s4 + "IDENTIFIER" + s5;
                            String s16 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s16.equals("")) {
                                cat.append(s16);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s15);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 15: // '\r'13
                            String s17 = s4 + "RELATION" + s5;
                            //todo change k and k+1 with corpus and corpus category
                            Vector corpus = (Vector) hashtable.get(as[k][1]);
                            Vector corpusCategory = (Vector) hashtable.get(as[k + 1][1]);
                            StringBuffer vect = new StringBuffer();
                            //check for logic here ///////////////////////////******//////
                            for (int cor = 0; cor < corpus.size(); cor++) {
                                vect.append(getString((String) corpus.elementAt(cor), as[k][0], s7));
                                if (corpusCategory.size() > cor) {  //>= cor causing arrayindexoutofbounds. not sure how this worked for so long.
                                    vect.append(getString((String) corpusCategory.elementAt(cor)
                                            , as[k + 1][0], s7));
                                }
                            }
                            String s18 = vect.toString();
                            if (!s18.equals("")) {
                                cat.append(s18);
                            }       //todo till here : uncomment lower section to revert.
                            /*  String s18 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                              if (!s18.equals("")) {
                              cat.append(s18);
                              }
                              s18 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                              if (!s18.equals("")) {
                              cat.append(s18);
                              }*/

                            s18 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s18.equals("")) {
                                cat.append(s18);
                            }
                            s18 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], s7);
                            if (!s18.equals("")) {
                                cat.append(s18);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s17);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 19: // '\021'17
                            String s19 = s4 + "DATE" + s5;
                            String s20 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s20.equals("")) {
                                cat.append(s20);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s19);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 20: // '\022'18
                            String s21 = s4 + "LANGUAGE" + s5;
                            String s22 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s22.equals("")) {
                                cat.append(s22);
                            }
                            s22 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s22.equals("")) {
                                cat.append(s22);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s21);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 22: // '\023'19
                            String s23 = s4 + "GEOGRAPHICAL COVERAGE" + s5;
                            String s24 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], s7);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 4][1]), as[k + 4][0], s7);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            s24 = getString(hashtable.get(as[k + 5][1]), as[k + 5][0], s7);
                            if (!s24.equals("")) {
                                cat.append(s24);
                            }
                            /*   s24 = getString(hashtable.get(as[k + 6][1]), as[k + 6][0], s7);
if(!s24.equals(""))
{
cat.append( s24;
}*/
                            if (!cat.toString().equals("")) {
                                sb.append(s23);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 28: // '\031'25
                            String s25 = s4 + "TEMPORAL COVERAGE" + s5;
                            String s26 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s26.equals("")) {
                                cat.append(s26);
                            }
                            s26 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s26.equals("")) {
                                cat.append(s26);
                            }
                            /*  s26 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                              if (!s26.equals("")) {
                                  cat.append( s26);
                              }*/
                            if (!cat.toString().equals("")) {
                                sb.append(s25);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            break;

                        case 30: // '\034'28
                            String s27 = s4 + "SOURCE" + s5;
                            String s28 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            s28 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            s28 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            s28 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], s7);
                            if (!s28.equals("")) {
                                cat.append(s28);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s27);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            // System.out.println("source is empty....");
                            break;

                        case 34: // ' '32
                            String s29 = s4 + "RIGHTS" + s5;

                            String s30 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s30.equals("")) {
                                cat.append(s30);
                            }
                            if (!cat.toString().equals("")) {
                                sb.append(s29);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            // System.out.println("rights is empty....");
                            break;

                        case 35: // '!'33
                            String s31 = s4 + "SUBJECT" + s5;
                            String s32 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s32.equals("")) {
                                cat.append(s32);
                            }
                            s32 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s32.equals("")) {
                                cat.append(s32);
                            }

                            if (!cat.toString().equals("")) {
                                sb.append(s31);
                                sb.append(cat.toString());
                                sb.append(s6);
                                cat = new StringBuffer();
                            }
                            // System.out.println("subject is empty....");
                            break;
                    }
                 //   System.out.println("finished processing :" + k);
                } catch (Exception exception) {
                    log.error(exception);
                  // System.out.println("Exception:"+ exception);
                }
            }
            //sb.append(s2);
        }
        //  }
        //findNoOfRows(s1 + sb.toString() + s2);
         return sb.toString().trim().length() >0 ? sb.insert(0, s1).append(s2).toString(): "";
    }

    private String getString(Object obj, String s, String s1) {


     /*   String s2 = "<tr><td width='" + ((width * 52) / 100 -2)+ "' valign='top'>" + "<b><font face='Arial' size='" + fontsize + "' color='AC5539'>";
        String s3 = "</font></b></td>";
        String s4 = "<td width='" + ((width * 48) / 100 +2) + "' >" + "<b><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></b></td></tr>";
        String s6 = s1.equals("") ? "<tr><td colspan =2><hr></td></tr>" : "";
        //String s7 = "";*/
        
        String s2 = "<tr ><td width='48%' valign='top' >" + "<b><font face='Arial' size='" + fontsize + "' color='AC5539'>";
        String s3 = "</font></b><br>&nbsp;</td>";
        String s4 = "<td width='52%'  >" + "<b><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></b><br>&nbsp;</td></tr>";
        String s6 = "";//s1.equals("") ? "<tr><td colspan =2><hr></td></tr>" : "";
        StringBuffer retString = new StringBuffer();
        if (obj instanceof String) {
            // return isValid((String)obj) ? s2 + s1 + s + s3 + s4 + replaceSark((String)obj) + s5 + s6 : "";
            return isValid((String) obj) ? s2 + s1 + s + s3 + s4 + replaceSark((String) obj) + s5 + s6 : "";
        }
        if (obj instanceof Vector) {
            Vector vector = (Vector) obj;
            //  System.out.println("-------- vector  :"+vector);
            if (vector.size() == 0 || vector == null) {
                return "";
            }
            int i = 0;
            for (int j = vector.size(); i < j; i++) {
                Object obj1 = vector.elementAt(i);
                if (obj1 instanceof String) {
                    if (isValid((String) obj1)) {
                        retString.append(s2);
                        retString.append(s1);
                        retString.append(s);
                        retString.append(s3);
                        retString.append(s4);
                        retString.append(replaceSark((String) obj1));
                        retString.append(s5);
                        retString.append(s6);
                    }
                } else if (obj1 instanceof Vector) {

                    Vector vector1 = (Vector) obj1;
                    //    System.out.println(" ************* 1 :"+vector1);
                    //String s8 = "";
                    StringBuffer cats = new StringBuffer();
                    if (vector1.size() == 0 || vector1 == null) {
                        //return "";
                        cats = new StringBuffer();
                        //   break;
                    }

                    int k = 0;
                    for (int l = vector1.size(); k < l; k++) {
                        if (s.equals("Alternate Text or Publication Number:")) {
                            cats.append((String) vector1.elementAt(0) + " ");
                            break;
                        } else if (s.equals("Script : Sub-script") || s.startsWith("Script :")) {
                            String script = (String) vector1.elementAt(0);
                            //	System.out.println("********** 2 : "+script);
                            String subScript = "";
                            if (vector1.size() > 1) {
                                subScript = (String) vector1.elementAt(1);
                                //	System.out.println("********** 3 : "+subScript);
                                cats.append(script);
                                cats.append(" : ");
                                cats.append(subScript);
                                cats.append(" ");
                                //	System.out.println("********** 4 : "+s8);
                            } else if (script == null || script.equals(" ")) {
                                cats = new StringBuffer();
                            } else {
                                cats.append(script + " ");
                            }
                            break;
                        } else if (s.equals("Text Division:")) {
                            int index = vector1.size();
                            cats.append("<br>");
                            String sep = "";
                            for (int m = 0; m < index; m++) {
                                cats.append(sep + (String) vector1.elementAt(m));   //index-1
                                sep = " <br><br>";
                            }
                            /* cats.append( (String) vector1.elementAt(index - 3));
                             cats.append( " <br><br>" );
                             cats.append( (String) vector1.elementAt(index - 2) );*/
                            cats.append(" ");
                            break;
                        } else
                            cats.append((String) vector1.elementAt(k) + " ");

                    }
                    if (cats != null && !cats.toString().equals("")) {
                        retString.append(s2);
                        retString.append(s1);
                        retString.append(s);
                        retString.append(s3);
                        retString.append(s4);
                        retString.append(replaceSark(cats.toString()));
                        retString.append(s5);
                        retString.append(s6);
                    }
                }
            }

        }
        return retString.toString();
    }

    private boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        } else {
            return !s.trim().equals("(   -   )");
        }
    }

    private String replaceSark(String s) {
        ////////////  for(; s.indexOf("@sark@") != -1; s = s.substring(0, s.indexOf("@sark@")) + "'" + s.substring(s.indexOf("@sark@") + 6)) { }
        /*   if(s.indexOf("&")>-1)
              System.out.println("yes in here:"+s);*/
        return s;
    }

    public void reset() {
       
        jTable3.removeEditor();
        jTable3.clearSelection();
        jTable3.doLayout();
        jButton3.setSelected(false);
        jButton1.setSelected(true);
        data.clear();
        mainData.clear();
        if (cat != null) {
            cat.setText("");
            //  setDocumentContent("");
        }
        flag = false;
        validate();
        clearCache();
            
    }

    private void clearCache() {
        CacheManagerObject cachemanagerobject = CacheManagerObject.getInstance();
        InscriptFileNameFilter inscriptfilenamefilter = new InscriptFileNameFilter(".s2");
        File file = new File(CacheManagerConstants.folderName);
        String as[] = file.list(inscriptfilenamefilter);
        for (int i = 0; i < as.length; i++) {
            CacheEntry cacheentry = new CacheEntry(as[i]);
            cachemanagerobject.removeFromCache(cacheentry);

        }

        file = null;
    }

    /* PSVM(String args[])
    throws Exception
    {
    JFrame jframe = new JFrame();
    CatalougePanel catalougepanel = new CatalougePanel();
    jframe.getContentPane().add(catalougepanel);
    String s = "select distinct ISFASSIGNEDTEXTNO from textobject_ov where ( (SCRIPT like '%Hier" +
    "oglyphs%'  ) AND (NAMEDTIMEPERIOD like '%New Kingdom%'  )  )" ;
    String s="select distinct ISFASSIGNEDTEXTNO from textobject_ov where "+
    "( UPPER(trim(KEYWORDSORPHRASES)) in ( 'AMULET') )";
    jframe.setSize(1024, 700);
    jframe.setVisible(true);
    }

    */

    public void fireUpdateButtons() {
        start = true;

        updateButtons();


    }

    public void updateButtons() {
        if (ranges.size() <= 1) {
            // goto1.setEnabled(false);
            back.setEnabled(false);
            next.setEnabled(false);
        } else {
            //   goto1.setEnabled(false);
            back.setEnabled(true);
            next.setEnabled(true);
        }

        if (selectedInt == 0)
            back.setEnabled(false);
        else if (selectedInt == ranges.size())
            next.setEnabled(false);


    }

    /*
    public void fireEnableNext(boolean b)
    {}
    public void fireEnablePrev(boolean b)
    {}
    */

    public Vector getNextData() throws NoDataFoundException {
        int times = totalRecords / max;
        if (currentRange > 1)
            back.setEnabled(true);
        else
            back.setEnabled(false);
        if (currentRange <= times)
            next.setEnabled(true);
        else
            next.setEnabled(false);


        Vector v;
        CacheManagerObject cmo = CacheManagerObject.getInstance();
        CacheEntry ce = new CacheEntry(currentRange + "v.s2");
        boolean cache = cmo.isCached(ce);
        if (cache)
            v = (Vector) cmo.retrieveFromCache(ce);
        else {


            String isftext = (String) rangesData.get(currentRange + "");

            StringTokenizer st = new StringTokenizer(isftext, ",");
            String s1 = "<QUERY TYPE='textmetadata' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>";
            while (st.hasMoreTokens())
                s1 = s1 + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfnumber' VALUE=" + st.nextToken() + " OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";

            s1 = s1 + "</QUERY>";
            // RequestDispatcherObject requestdispatcherobject = new RequestDispatcherObject();
            String s2 = (String) RequestProxy.getRequestDispatcher().handleQueryEvent(s1);
           // log.debug("\n" + s2);
            MetaDataXmlConverter metadataxmlconverter = new MetaDataXmlConverter();
            InputSource inputsource = new InputSource(new StringReader(s2));
            try {
                  javax.xml.parsers.SAXParser saxparser = SAXParserFactory.newInstance().newSAXParser();
                saxparser.parse(inputsource,metadataxmlconverter);
            } catch (SAXParseException saxparseexception) {

                log.error(saxparseexception);
                        
            } catch (Exception exception) {
                log.error(exception);
            }
            v = (Vector) metadataxmlconverter.getDataStructure();
            cmo.storeToCache(ce, v);
        }
        textcode = "";
        int count = (currentRange - 1) * max + 1;
        int lim = count + max - 1;
        if (lim > totalRecords)
            lim = totalRecords;

        String str = "Records " + (count) + " - " + lim + " of " + totalRecords + ".";
        if (totalRecords > 500)
            str += " Please refine your search.";
        a1.setText("<html><b><font face='Trebuchet MS' size='8pts' color='3300CC'>" + str +
                "</font></b></html>");

        jScrollPane2.getVerticalScrollBar().setValue(0);


        return v;
    }

    private int findNoOfRows(String s) {
        int i = 0;
        int pos = s.indexOf("<tr>");
        while (pos > -1) {
            pos = s.indexOf("<tr>", pos);
            i++;
        }
        log.debug("noofrows:" + i);
        return i;
    }


    /**
     * Sets the HTML content to be displayed.
     *
     * @param content an HTML document
     */
    private void setDocumentContent(String content) {

        HTMLDocument doc = new HTMLDocument();
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            log.error(e);
        }
        doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);

        try {
            cat.read(new ByteArrayInputStream(content.getBytes()), doc);
        } catch (IOException e) {
            log.error(e);
        }

        cat.setDocument(doc);
        cat.setCaretPosition(0);


    }

}
