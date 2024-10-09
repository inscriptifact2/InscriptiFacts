package com.isf.app.view.containers;

import com.isf.app.exception.NoDataFoundException;
import com.isf.app.models.ImageThumbData;
import com.isf.app.models.ThumbData;
import com.isf.app.utils.CacheEntry;
import com.isf.app.utils.CacheManagerObject;
import com.isf.app.utils.MetaDataXmlConverter;
import com.isf.app.utils.ResultsUtil;
import com.isf.app.view.gui.InscriptFileNameFilter;
import com.isf.app.view.listeners.CacheUpdater;
import com.isf.app.view.listeners.QuickViewListener;
import com.isf.app.view.listeners.ThumbHeaderSelectionListener;
import com.isf.app.view.listeners.ThumbListener;
import isf.common.Debug;
import isf.common.request.RequestProxy;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.InscriptConfigLoader;
import isf.common.utils.LogManager;
import isf.controls.controllers.ImageLoaderManager;
import isf.controls.utils.*;
import isf.controls.view.containers.InscriptTextPane;
import isf.controls.view.containers.TrimmedPanel;
import isf.controls.view.controls.InscriptButton;
import isf.controls.view.listeners.SortListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class ImageResultsPanel extends JPanel
        implements ActionListener, QuickViewListener, MouseListener, MouseMotionListener, SortListener, ThumbHeaderSelectionListener, CacheUpdater {

    private HashMap mainData;
    Logger log = LogManager.getLogger(com.isf.app.view.containers.ImageResultsPanel.class);
    //   private Vector obtainedData;
    /*   private int currentRange=1;

    private JLabel a1;
     */

    ///   public final QVGlassPane gp1 = new QVGlassPane();
    private QuickViewGP qvgp;
    public TrimmedPanel qvpHolder;
    public QuickViewPanel qvp;
    public ThumbViewerPanel tvp;
    public ThumbListener tl;
    private Vector data;
    private String photoid;
    private boolean flag;
    int xposition;
    int yposition;
    int mouseX;
    int mouseY;
    Point p;
    Graphics og;
    Graphics ng;
    Graphics g;
    Point temp;
    Rectangle old;
    BasicStroke stroke;
    private int width;
    double r[];
    private JPanel jPanel1;
    private JButton jButton1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private int totalRecords = 0;
    private int currentRange = 1;
    private HashMap coreRange;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private int max;
    private JButton jButton3;
    private InscriptTextPane cat;

    public ImageResultsPanel() {
        ImageLoaderManager.getInstance().registerSorter(this);
        coreRange = new HashMap();
        tl = null;
        photoid = "";
        flag = false;
        p = new Point();
        stroke = new BasicStroke(2.0F);
        r = ResolutionManager.getInstance().getRatio();
        data = new Vector();
        initComponents();
        qvgp = new QuickViewGP();
        qvgp.addMouseListener(this);
        qvgp.addMouseMotionListener(this);
        // gp1.add(qvgp);
        max = Integer.parseInt(InscriptConfigLoader.getInstance().getProperty("ImagesListSize"));
    // obtainedData=new Vector();

    }

    private void initComponents() {
        mainData = new HashMap();
        tvp = new ThumbViewerPanel(new Insets(0, 0, 0, 0), this);
        tl = tvp.tv;
        tvp.addQuickViewListener(this);
        tvp.addThumbHeaderSelectionListener(this);
        tvp.tv.addCacheUpdater(this);
        //qvpane = new JScrollPane();
        jPanel1 = new JPanel();
        jPanel1.setBackground(Color.black);
        ImageIcon imageicon = ResolutionManager.getInstance().getImageIcon("images/catalougeinactive.jpg");
        jButton1 = new InscriptButton(imageicon);
        jButton1.setActionCommand("catalougeinactive");
        jButton1.setSelectedIcon(ResolutionManager.getInstance().getImageIcon("images/catalouge.jpg"));
        jButton1.setSelected(true);
        jButton1.addActionListener(this);
        jPanel2 = new JPanel() {

            public Insets getInsets() {
                return new Insets(0, 14, 0, 0);
            }
        };
        jPanel2.setBackground(Color.black);
        jPanel3 = new JPanel();
        ImageIcon imageicon1 = ResolutionManager.getInstance().getImageIcon("images/dublininactive.jpg");
        jButton3 = new InscriptButton(imageicon1);
        jButton3.setActionCommand("dublininactive");
        jButton3.addActionListener(this);
        jButton3.setSelectedIcon(ResolutionManager.getInstance().getImageIcon("images/dublin.jpg"));
        setLayout(new BorderLayout());
        jPanel1.setLayout(new FlowLayout(0, 0, 0));
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setAlignmentX(0.0F);
        ImageIcon imageicon2 = ResolutionManager.getInstance().getImageIcon("images/dublininactive.jpg");
        jScrollPane1 = new JScrollPane(22, 31);
        jButton1.setMargin(new Insets(0, 0, 0, 0));
        jButton1.setBorderPainted(false);
        jPanel1.add(jButton1);
        add(jPanel1, "North");
        jPanel2.setLayout(new BorderLayout());
        jPanel2.setAlignmentY(0.0F);
        jPanel2.setAlignmentX(0.0F);
        cat = new InscriptTextPane(new Dimension(imageicon2.getIconWidth() - 14, 9000), false);
        cat.setEditable(false);
        cat.setSelectionColor(Color.white);
        cat.setContentType("text/html");
        width = cat.getPreferredSize().width;
        // width = imageicon2.getIconWidth() - 14;////cat.getPreferredSize().width;
        jScrollPane1.setViewportView(cat);
        jPanel2.add(jScrollPane1, "West");
        jPanel3.setLayout(new FlowLayout(0, 0, 0));
        jButton3.setMargin(new Insets(0, 0, 0, 0));
        jButton3.setBorderPainted(false);
        jPanel3.add(jButton3);
        jPanel2.add(jPanel3, "North");
        jPanel4 = new JPanel() {

            public Dimension getPreferredSize() {
                ImageIcon imageicon3 = ResolutionManager.getInstance().getImageIcon("images/dublininactive.jpg");
                return new Dimension(imageicon3.getIconWidth(), 50);
            }
        };
        jPanel4.setBackground(Color.black);
        add(jPanel2, "West");
        add(jPanel4, "South");
        add(tvp, "Center");
    }

    public void showQuickView(ThumbData thumbdata) {
        // setCursor(new Cursor(3));
        if (CursorManager.wait) {
            return;
        }
        CursorManager.changeCursor(3);

        qvgp.setThumbData(new Insets(0, 0, 0, 0), thumbdata);
        qvgp.addArrowListener(this);
        //     getRootPane().setGlassPane(gp1);
        ///////////////////   qvgp.setSize(520, 574+54);
        qvgp.setSize(520, 633);
        GlassPaneManager gp1 = GlassPaneManager.getInstance();
        if (!gp1.isShowing()) {
            Dimension dimension1 = ResolutionManager.getInstance().getScreen();
            //     qvgp.setLocation(dimension1.width / 3, dimension1.height / 6);
            // gp1.setVisible(true);
            if (qvgp.isValidIcon()) {
                gp1.showComponent(qvgp, new Rectangle(dimension1.width / 3, dimension1.height / 6, qvgp.getWidth(), qvgp.getHeight()));
            } else {
                gp1.hideAll();
            }
            // setCursor(new Cursor(0));
            CursorManager.changeCursor(0);

        } else {
            Point point1 = qvgp.getLocation();
            //  qvgp.setLocation(point1.x, point1.y);
            // gp1.setVisible(true);
            if (qvgp.isValidIcon()) {
                gp1.showComponent(qvgp, new Rectangle(point1.x, point1.y, qvgp.getWidth(), qvgp.getHeight()));
            } else {
                gp1.hideAll();
            }
            // setCursor(new Cursor(0));
            CursorManager.changeCursor(0);

        }

    }

    public void headerSelected(ThumbData thumbdata) {
        data = tvp.getData();
        try {
            photoid = thumbdata.getPhotoIdentificationNumber();
            if (!flag) {
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setText((String) ((Vector) mainData.get(photoid)).elementAt(0));
                cat.setCaretPosition(0);
            } else {
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setText((String) ((Vector) mainData.get(photoid)).elementAt(1));
                cat.setCaretPosition(0);
            }
        } catch (Exception exception) {
            jScrollPane1.getVerticalScrollBar().setValue(0);
            cat.setCaretPosition(0);
        }
    }

    public void actionPerformed(ActionEvent actionevent) {
        if (actionevent.getSource() == jButton3) {
            flag = true;
            jScrollPane1.getVerticalScrollBar().setValue(0);
            if(photoid.trim().length()>0)
               cat.setText((String) ((Vector) mainData.get(photoid)).elementAt(1));
            else
               cat.setText(""); 
            cat.setCaretPosition(0);
            jButton3.setSelected(true);
            jButton1.setSelected(false);
        }
        if (actionevent.getSource() == jButton1) {
            flag = false;
            jScrollPane1.getVerticalScrollBar().setValue(0);
             if(photoid.trim().length()>0)
              cat.setText((String) ((Vector) mainData.get(photoid)).elementAt(0));
             else
               cat.setText(""); 
            cat.setCaretPosition(0);
            jButton3.setSelected(false);
            jButton1.setSelected(true);
        }
        if (qvgp.qvp != null && actionevent.getSource().equals(qvgp.qvp.downArrow)) {
            // gp1.setVisible(false);
            GlassPaneManager.getInstance().hideAll();
            revalidate();
            validate();
        }
        if (actionevent.getActionCommand().equals("next")) {
            try {
                //  tvp.next.setCursor(new Cursor(Cursor.WAIT_CURSOR) ) ;
                CursorManager.changeCursor(3);

                currentRange++;
                setData(getNextData());
                cat.setText("");
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setCaretPosition(0);
                //  tvp.next.setCursor(new Cursor(Cursor.DEFAULT_CURSOR )) ;
                CursorManager.changeCursor(0);

            } catch (NoDataFoundException ndef) {
                //   tvp.next.setCursor(new Cursor(0));
                CursorManager.changeCursor(0);

                JOptionPane.showMessageDialog(this, "You Are Accessing Last Records ...", "Error", 0);
            }
        } else if (actionevent.getActionCommand().equals("back")) {
            try {
                // tvp.back.setCursor(new Cursor(Cursor.WAIT_CURSOR) ) ;
                CursorManager.changeCursor(3);

                currentRange--;
                setData(getNextData());
                cat.setText("");
                jScrollPane1.getVerticalScrollBar().setValue(0);
                cat.setCaretPosition(0);

                //  tvp.back.setCursor(new Cursor(Cursor.DEFAULT_CURSOR )) ;
                CursorManager.changeCursor(0);

            } catch (NoDataFoundException ndfe) {
                //  tvp.back.setCursor(new Cursor(0));
                CursorManager.changeCursor(0);

                JOptionPane.showMessageDialog(this, "You Are Accessing First Records ...", "Error", 0);
            }
        }
    }

    public void addQuickViewListener(QuickViewListener quickviewlistener) {
    }

    public Vector getSelectedData() {
        return tvp.getSelectedData();
    }

    public String buildCatalougeData(String s, Hashtable hashtable) {
        String as[][] = CatalogueConstants.photoCatalogueNames;
        StringBuffer sb = new StringBuffer();
        String s1 = "<html><head><style> table{border: 1px black solid; background-color: black;  }  th,  td{ word-wrap:break-word; background-color: white;  }</style></head><body><table cellspacing=1  align='left' width='99%' >";
        String s2 = "</table></body></html>";
        //sb.append(s1);
        //   int i = 0;
        //   for (int j = data.size(); i < j; i++) {
        //  Hashtable hashtable = (Hashtable) data.elementAt(i);
        if (s.equals(hashtable.get("PHOTOGRAPHIDENTIFICATIONNO"))) {
            int k = 0;
            // System.out.println("obj  before for: "+hashtable.get("TEMPORALCOVERAGERANGE"));
            for (int l = as.length; k < l; k++) {
                //    System.out.println("obj  : "+hashtable.get(as[k][1])+"s  :"+as[k][0]);
                try {    ///////////
                    if (as[k][0].equals("Time Period:")) {
                        String tempCovRange = (String) hashtable.get("TEMPORALCOVERAGERANGE");
                        if (!hashtable.get("TEMPORALCOVERAGERANGE").equals("") || hashtable.get("TEMPORALCOVERAGERANGE") != null) {
                            sb.append(getString((String) hashtable.get(as[k][1]) + tempCovRange, as[k][0], ""));
                        } else {
                            sb.append(getString(hashtable.get(as[k][1]), as[k][0], ""));
                        }

                    } else if (as[k][0].equals("Compression Ratio:")) {
                        String cratio = (String) hashtable.get(as[k][1]);
                        //System.out.println("cratio ------------ :"+cratio);
                        //String [] carr=cratio.split(":");
                        int indF = cratio.indexOf(":");
                        int indL = cratio.lastIndexOf(":");


                        String first = cratio.substring(0, indF);
                        String middle = "00";
                        String last = "00";
                        //System.out.println("indF-- "+indF+"  indL-- "+indL);
                        if (indF != indL) {
                            middle = cratio.substring(indF + 1, indL);
                            last = cratio.substring(indL + 1);
                        } else {
                            middle = cratio.substring(indL + 1);
                        //System.out.println("middle  :"+middle);
                        }

                        if (first.startsWith("0")) {
                            first = first.substring(1);
                        }
                        if (middle.startsWith("0")) {
                            middle = middle.substring(1);
                        }
                        if (last.startsWith("0")) {
                            last = last.substring(1);
                        }
                        if (Integer.parseInt(last) == 0) {
                            cratio = first + ":" + middle;
                        } else {
                            cratio = first + ":" + middle + ":" + last;
                        }
                        //System.out.println("cratio final  :"+cratio);
                        sb.append(getString(cratio, as[k][0], ""));
                    } else if (as[k][0].equals("Film Type:")) {
                        //    System.out.println("--------------------------------- in film type ");
                        int code = Integer.parseInt((String) hashtable.get(as[k][1]));
                        //  System.out.println(" FCode------------------------ :"+code);
                        String desc = "";
                        switch (code) {
                            case 1:
                                desc = "Color transparency";
                                break;
                            case 2:
                                desc = "Color negative";
                                break;
                            case 3:
                                desc = "Black and white infrared negative";
                                break;
                            case 4:
                                desc = "High contrast black and white negative";
                                break;
                            case 5:
                                desc = "Medium contrast black and white negative";
                                break;
                            case 6:
                                desc = "Digital color";
                                break;
                            case 7:
                                desc = "Digital infrared";
                                break;
                            case 8:
                                desc = "Digital black and white";
                                break;
                            case 9:
                                desc = "PTM";
                                break;
                            case 10:
                                desc = "RTI";
                                break;
                            default:
                                desc = code + "";
                                break;
                        }
                        // System.out.println(" desc 33333333   :"+desc);
                        sb.append(getString(desc, as[k][0], ""));
                    } else if (as[k][0].equals("Magnification:")) {
                        //  System.out.println("------------------------------in magnifi  ");
                        int code = Integer.parseInt((String) hashtable.get(as[k][1]));
                        // System.out.println("Mcode--------------:"+code);
                        String desc = "";
                        // boolean flag1 = false;
                        switch (code) {
                            //   System.out.println("----------------------------in MSwitch  ");
                            case 1:
                                desc = "Reference";
                                break;
                            case 2:
                                desc = "Sectional";
                                break;
                            case 3:
                                desc = "Detailed";
                                break;


                        }
                        //  System.out.println("desc  44444444444   :"+desc);
                        sb.append(getString(desc, as[k][0], ""));
                    } else /////////////
                    {
                        sb.append(getString(hashtable.get(as[k][1]), as[k][0], ""));
                    }
                } catch (Exception exception) {
                    Debug.debug(exception);
                }
            }

        }

        //sb.append(s2);
        //System.out.println("STRING:"+sb.toString());
         return sb.toString().trim().length() >0 ? sb.insert(0, s1).append(s2).toString(): "";
    }

    private String buildDubinData(String s, Hashtable hashtable) {
        int fontsize = 2;
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        if (current.width > 1450 && current.height > 800){
            fontsize = 3;
        }else if(current.width <=1280 && current.height <=768)
        {
            fontsize= 1;
        }
        
        String as[][] = CatalogueConstants.photoDublinNames;
        String s1 = "<html><head><style> table{border: 1px black solid; background-color: black;  }  th,  td{ word-wrap:break-word; background-color: white;  }</style></head><body><table cellspacing=1  align='left' width='99%' >";
        String s2 = "</table></body></html>";
        String s4 = "<tr ><td colspan=2 >" + "<b><u><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></u></b><br>&nbsp;</td></tr>";
        String s6 = "";//<tr><td colspan =2><hr ></td></tr>";
        String s7 = "<li>";
        //  String s22 = "";
        StringBuffer val = new StringBuffer();
        StringBuffer sb = new StringBuffer();
       // sb.append(s1);

        // int i = 0;
        // for (int j = data.size(); i < j; i++) {
        // Hashtable hashtable = (Hashtable) data.elementAt(i);
        if (s.equals(hashtable.get("PHOTOGRAPHIDENTIFICATIONNO"))) {
            int k = 0;
            for (int l = as.length; k < l; k++) {
                try {
                    switch (k) {
                        case 3: // '\003'
                        case 4: // '\004'
                        case 5: // '\005'
                        case 6: // '\006'
                        case 7: // '\007'
                        //case 8:
                        case 9: // '\t'
                        //	case 10:
                        case 12: // '\f'
                        //	case 13:
                        case 14:
                        case 17: // '\020'
                        //  case 18:
                        case 19: // '\023'
                        case 20:
                        //  case 21: // '\026'
                        case 22: // '\027'
                        case 25:
                        case 26:
                        case 27:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                        default:
                            break;

                        case 0: // '\0'
                            String s8 = s4 + "TYPE" + s5;
                            String s23 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s23.equals("")) {
                                val.append(s23);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s8 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 1: // '\001'
                            String s9 = s4 + "TITLE" + s5;
                            String s24 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s24.equals("")) {
                                val.append(s24);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s9 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 2: // '\002'
                            String s10 = s4 + "DESCRIPTION" + s5;
                            String s25 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s25.equals("")) {
                                val.append(s25);
                            }
                            s25 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s25.equals("")) {
                                val.append(s25);
                            }
                            s25 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s25.equals("")) {
                                val.append(s25);
                            }
                            s25 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], s7);
                            if (!s25.equals("")) {
                                val.append(s25);
                            }
                            s25 = getString(hashtable.get(as[k + 4][1]), as[k + 4][0], s7);
                            if (!s25.equals("")) {
                                val.append(s25);
                            }
                            s25 = getString(hashtable.get(as[k + 5][1]), as[k + 5][0], s7);
                            if (!s25.equals("")) {
                                val.append(s25);
                            }
                            /*    s25 = getString(hashtable.get(as[k + 6][1]), as[k + 6][0], s7);

                            System.out.println("as[k + 6][1]--- : "+as[k + 6][1]+" ---  "+hashtable.get(as[k + 6][1]));
                            System.out.println("as[k + 6][0]--- : "+as[k + 6][0]);

                            if(!s25.equals(""))
                            {
                            val.append( s25;
                            }*/
                            if (!val.toString().equals("")) {
                                sb.append(s10 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 8: // '\b'8
                            String s11 = s4 + "IDENTIFIER" + s5;
                            String s26 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s26.equals("")) {
                                val.append(s26);
                            }
                            s26 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s26.equals("")) {
                                val.append(s26);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s11 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 10: // '\n'10
                            String s12 = s4 + "RELATION" + s5;
                            String s27 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s27.equals("")) {
                                val.append(s27);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s12);
                                sb.append(val.toString());
                                sb.append(s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 11: // '\013'11
                            String s13 = s4 + "DATE" + s5;
                            String s28 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s28.equals("")) {
                                val.append(s28);
                            }
                            s28 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s28.equals("")) {
                                val.append(s28);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s13 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 13: // '\r'13
                            String s14 = s4 + "LANGUAGE" + s5;
                            String s29 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s29.equals("")) {
                                val.append(s29);
                            }
                            s29 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s29.equals("")) {
                                val.append(s29);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s14 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 15: // '\016'14
                            String s15 = s4 + "GEOGRAPHICAL COVERAGE" + s5;
                            String s30 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s30.equals("")) {
                                val.append(s30);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s15 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 16: // '\017'15
                            String s16 = s4 + "TEMPORAL COVERAGE" + s5;

                            String tempCovRange = (String) hashtable.get("TEMPORALCOVERAGERANGE");
                            //System.out.println("as[k ][1]--- : "+as[k][1]+" ---  "+hashtable.get(as[k][1]));
                            //  System.out.println("as[k][0]--- : "+as[k][0]);
                            String s31 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s31.equals("")) {
                                val.append(s31);
                            }
                            // System.out.println("as[k ][1]--- : "+as[k+1][1]+" ---  "+hashtable.get(as[k+1][1]));
                            //   System.out.println("as[k][0]--- : "+as[k+1][0]);
                            if (!tempCovRange.equals("") || tempCovRange != null) {
                                s31 = getString(hashtable.get(as[k + 1][1]) + tempCovRange, as[k + 1][0], s7);
                            } else {
                                s31 = getString(hashtable.get(as[k + 1][1]), as[+1][0], s7);
                            }
                            // s31 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s31.equals("")) {
                                val.append(s31);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s16 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 18: // '\021'17
                            String s17 = s4 + "SOURCE" + s5;
                            String s32 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s32.equals("")) {
                                val.append(s32);
                            }
                            s32 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s32.equals("")) {
                                val.append(s32);
                            }
                            s32 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s32.equals("")) {
                                val.append(s32);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s17 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 21: // '\022'18
                            String s18 = s4 + "CREATOR" + s5;
                            String s33 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s33.equals("")) {
                                val.append(s33);
                            }
                            s33 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s33.equals("")) {
                                val.append(s33);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s18 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 23: // '\024'20
                            String s19 = s4 + "CONTRIBUTOR" + s5;
                            String s34 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s34.equals("")) {
                                val.append(s34);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s19 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 24: // '\025'21
                            String s20 = s4 + "RIGHTS" + s5;
                            String s35 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            if (!s35.equals("")) {
                                val.append(s35);
                            }
                            s35 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s35.equals("")) {
                                val.append(s35);
                            }
                            s35 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s35.equals("")) {
                                val.append(s35);
                            }
                            s35 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], s7);
                            if (!s35.equals("")) {
                                val.append(s35);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s20 + val.toString() + s6);
                                val = new StringBuffer();
                            }
                            break;

                        case 28: // '\030'24
                            //System.out.println("----in format----");
                            String s21 = s4 + "FORMAT" + s5;
                            String s36 = getString(hashtable.get(as[k][1]), as[k][0], s7);
                            //  System.out.println("as[k][1]  = "+hashtable.get(as[k][1])+"  as[k][0] = "+as[k][0]);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            // System.out.println("as[k+1][1]  = "+hashtable.get(as[k+1][1])+"  as[k+1][0] = "+as[k+1][0]);
                            s36 = getString(hashtable.get(as[k + 1][1]), as[k + 1][0], s7);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            //System.out.println("as[k+2][1]  = "+hashtable.get(as[k+2][1])+"  as[k+2][0] = "+as[k+2][0]);
                            s36 = getString(hashtable.get(as[k + 2][1]), as[k + 2][0], s7);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            //System.out.println("as[k+3][1]  = "+hashtable.get(as[k+3][1])+"  as[k+3][0] = "+as[k+3][0]);
                            s36 = getString(hashtable.get(as[k + 3][1]), as[k + 3][0], s7);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            ///////////
                            String cratio = (String) hashtable.get(as[k + 4][1]);
                            // System.out.println("cratio ------------ :"+cratio);
                            //String [] carr=cratio.split(":");
                            int indF = cratio.indexOf(":");
                        if (indF > -1) {
                            int indL = cratio.lastIndexOf(":");


                            String first = cratio.substring(0, indF);
                            String middle = "00";
                            String last = "00";
                            //System.out.println("indF-- "+indF+"  indL-- "+indL);
                            if (indF != indL) {
                                middle = cratio.substring(indF + 1, indL);
                                last = cratio.substring(indL + 1);
                            } else {
                                middle = cratio.substring(indL + 1);
                            //System.out.println("middle  :"+middle);
                            }

                            if (first.startsWith("0")) {
                                first = first.substring(1);
                            }
                            if (middle.startsWith("0")) {
                                middle = middle.substring(1);
                            }
                            if (last.startsWith("0")) {
                                last = last.substring(1);
                            }
                            if (Integer.parseInt(last) == 0) {
                                cratio = first + ":" + middle;
                            } else {
                                cratio = first + ":" + middle + ":" + last;
                            }
                        }
                        log.debug("cratio final  :" + cratio);
                            s36 = getString(cratio, as[k + 4][0], s7);
                            /* String cratio=(String)hashtable.get(as[k+4][1]);
                            System.out.println(" cratio --- "+cratio);
                            int indF = cratio.indexOf(":");
                            int indL = cratio.lastIndexOf(":");
                            System.out.println(" indL--"+indL);


                            String first=cratio.substring(0,indF);

                            String middle=cratio.substring(indF+1,indL);
                            String last=cratio.substring(indL+1);
                            if(first.startsWith("0"))
                            first=first.substring(1);
                            if(middle.startsWith("0"))
                            middle=middle.substring(1);
                            if(last.startsWith("0"))
                            last=last.substring(1);
                            if(Integer.parseInt(last)==0)
                            cratio=first+":"+middle;
                            else
                            cratio=first+":"+middle+":"+last;
                            s36=getString(cratio,as[k+4][0],s7);*/
                            /////////////
                            //  s36 = getString(hashtable.get(as[k + 4][1]), as[k + 4][0], s7);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            // System.out.println("as[k+5][1]  = "+hashtable.get(as[k+5][1])+"  as[k+5][0] = "+as[k+5][0]);
                            s36 = getString(hashtable.get(as[k + 5][1]), as[k + 5][0], s7);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            ////////////////////////////////////////////////////////
                            int filmTypeCode = Integer.parseInt((String) hashtable.get(as[k + 6][1]));
                            // System.out.println("as[k+6][1]  -- "+hashtable.get(as[k+6][1]));
                            //  System.out.println(" as[k + 6][0] -- "+as[k + 6][0]);

                            String filmTypeDesc = "";
                            switch (filmTypeCode) {
                                case 1:
                                    filmTypeDesc = "Color transparency";
                                    break;
                                case 2:
                                    filmTypeDesc = "Color negative";
                                    break;
                                case 3:
                                    filmTypeDesc = "Black and white infrared negative";
                                    break;
                                case 4:
                                    filmTypeDesc = "High contrast black and white negative";
                                    break;
                                case 5:
                                    filmTypeDesc = "Medium contrast black and white negative";
                                    break;
                                case 6:
                                    filmTypeDesc = "Digital color";
                                    break;
                                case 7:
                                    filmTypeDesc = "Digital infrared";
                                    break;
                                case 8:
                                    filmTypeDesc = "Digital black and white";
                                    break;
                                case 9:
                                    filmTypeDesc = "PTM";
                                    break;
                                case 10:
                                    filmTypeDesc = "RTI";
                                    break;
                                default:
                                    filmTypeDesc = filmTypeCode + "";
                                    break;

                            }

                            s36 = getString(filmTypeDesc, as[k + 6][0], s7);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }

                            int magnificationCode = Integer.parseInt((String) hashtable.get(as[k + 7][1]));
                            String magnificationDesc = "";
                            switch (magnificationCode) {
                                case 1:
                                    magnificationDesc = "Reference";
                                    break;
                                case 2:
                                    magnificationDesc = "Sectional";
                                    break;
                                case 3:
                                    magnificationDesc = "Detailed";
                                    break;
                            }

                            s36 = getString(magnificationDesc, as[k + 7][0], s7);
                            ////////////////////////////////////////////////////////////
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            s36 = getString(hashtable.get(as[k + 8][1]), as[k + 8][0], s7);
                            if (!s36.equals("")) {
                                val.append(s36);
                            }
                            if (!val.toString().equals("")) {
                                sb.append(s21);
                                sb.append(val.toString());
                                sb.append(s6);
                                val = new StringBuffer();
                            }
                            break;
                    }
                } catch (Exception exception) {
                    Debug.debug(exception);
                }
            }

        }

       
         return sb.toString().trim().length() >0 ? sb.insert(0, s1).append(s2).toString(): "";
    }

    private String getString(Object obj, String s, String s1) {
        int fontsize = 2;
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        if (current.width > 1450 && current.height > 800) {
            fontsize = 3;
        }else if(current.width <1280 && current.height <=768)
        {
            fontsize= 1;
        }
        String s2 = "<tr ><td width='48%' valign='top' >" + "<b><font face='Arial' size='" + fontsize + "' color='AC5539'>";
        String s3 = "</font></b><br>&nbsp;</td>";
        String s4 = "<td width='52%'  >" + "<b><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></b><br>&nbsp;</td></tr>";
        String s6 = "";//s1.equals("") ? "<tr><td colspan =2><hr></td></tr>" : "";
        StringBuffer s7 = new StringBuffer();
        if (obj instanceof String) {
            return isValid((String) obj) ? s2 + s1 + s + s3 + s4 + replaceSark((String) obj) + s5 + s6 : "";
        }
        if (obj instanceof Vector) {
            Vector vector = (Vector) obj;
            if (vector.size() == 0 || vector == null) {
                return "";
            }
            int i = 0;
            for (int j = vector.size(); i < j; i++) {
                Object obj1 = vector.elementAt(i);
                if (obj1 instanceof String) {
                    if (isValid((String) obj1)) {
                        s7.append(s2 + s1 + s + s3 + s4 + replaceSark((String) obj1) + s5 + s6);
                    }
                } else if (obj1 instanceof Vector) {
                    Vector vector1 = (Vector) obj1;
                    String s8 = "";
                    if (vector1.size() == 0 || vector1 == null) {
                        s8 = "";
                    }

                    int k = 0;
                    for (int l = vector1.size(); k < l; k++) {
                        //  System.out.println("elementAt--"+l+" : "+(String)vector1.elementAt(l));

                        if (s.equals("Script : Sub-script") || s.startsWith("Script :")) {
                            String subScript = "";
                            String script = (String) vector1.elementAt(0);
                            if (vector1.size() > 1) {
                                subScript = (String) vector1.elementAt(1);
                                s8 = s8 + script + " " + ":" + " " + subScript + " ";
                            } else if (script == null || script.equals(" ")) {
                                s8 = "";
                            } else {
                                s8 = s8 + script + " ";
                            }
                            break;
                        }
                        s8 = s8 + (String) vector1.elementAt(k) + " ";
                    }
                    if (s8 != null && !s8.equals("")) {
                        s7.append(s2 + s1 + s + s3 + s4 + replaceSark(s8) + s5 + s6);
                    }
                }
            }

        }
        return s7.toString();
    }

    private boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        }
        return !s.trim().equals("(   -   )");
    }

    private String replaceSark(String s) {
        for (; s.indexOf("@sark@") != -1; s = s.substring(0, s.indexOf("@sark@")) + "'" + s.substring(s.indexOf("@sark@") + 6)) {
        }
        // fix for MIS-PI 6/8A
        int x =0, p = s.indexOf("+");
        for (; s.indexOf("+",x) != -1; ) {
            s = s.substring(0, s.indexOf("+",x)+1) + " " + s.substring(s.indexOf("+",x) + 1);
            x = p+1;
            p = s.indexOf("+",x);
        } // end fix
 
        return s;
    }

    public void mousePressed(MouseEvent mouseevent) {
        temp = mouseevent.getPoint();
        // QuickViewGP quickviewgp = qvgp;
        //   quickviewgp.setCursor(new Cursor(13));
        CursorManager.changeCursor(13);
        GlassPaneManager gp1 = GlassPaneManager.getInstance();

        xposition = qvgp.getX();
        yposition = qvgp.getY();
        Dimension dimension = qvgp.getSize();
        p.x = xposition - mouseX;
        p.y = yposition - mouseY;
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        old = new Rectangle(p.x, p.y, dimension.width, dimension.height);
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
    }

    public void setPhotoIds(Vector vector) {
        //  tvp.setPhotoIds(vector);
    }

    public void mouseEntered(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
        GlassPaneManager gp1 = GlassPaneManager.getInstance();
        // QuickViewGP quickviewgp = qvgp;
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
        qvgp.setLocation(old.getLocation());
        old = null;
        //  quickviewgp.setCursor(new Cursor(0));
        CursorManager.changeCursor(0);

    }

    public void mouseClicked(MouseEvent mouseevent) {
    }

    public void mouseExited(MouseEvent mouseevent) {
    }

    public void mouseDragged(MouseEvent mouseevent) {
        GlassPaneManager gp1 = GlassPaneManager.getInstance();

        // QuickViewGP quickviewgp = qvgp;
        Dimension dimension = qvgp.getSize();
        p.x = ((mouseevent.getX() - temp.x) + xposition) - mouseX;
        p.y = ((mouseevent.getY() - temp.y) + yposition) - mouseY;
        Rectangle rectangle = new Rectangle(p.x, p.y, dimension.width, dimension.height);
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
        graphics2d.draw(rectangle);
        old = rectangle;
    }

    public void mouseMoved(MouseEvent mouseevent) {
    }

    public void reset() {

        tvp.reset();
        resetSort();
        data.clear();
        flag = false;
        photoid = "";
        clearCache();
        cat.setText("");
        jButton3.setSelected(false);
        jButton1.setSelected(true);
        mainData = new HashMap();
        //  obtainedData = new Vector();
        currentRange = 1;

    }

    private void clearCache() {
        CacheManagerObject cachemanagerobject = CacheManagerObject.getInstance();
        InscriptFileNameFilter inscriptfilenamefilter = new InscriptFileNameFilter(".s6");
        File file = new File(CacheManagerConstants.folderName);
        String as[] = file.list(inscriptfilenamefilter);
        for (int i = 0; i < as.length; i++) {
            Debug.debug("removing file " + as[i]);
            CacheEntry cacheentry = new CacheEntry(as[i]);
            cachemanagerobject.removeFromCache(cacheentry);

        }

        file = null;
    }

    public void populateData(Vector v, String tot, String isfrange) {

        reset();
        currentRange = 1;

        CacheManagerObject.getInstance().storeToCache(new CacheEntry(currentRange + "v.s6"), v);
        setData(v);
        // tvp.setLimits(v);

        totalRecords = Integer.parseInt(tot);
        if (totalRecords > max) {
            tvp.next.setEnabled(true);
        } else {
            tvp.next.setEnabled(false);
        }
        tvp.back.setEnabled(false);
        int rec = 1;
        StringTokenizer strTok = new StringTokenizer(isfrange, "#");
        while (strTok.hasMoreTokens()) {
            coreRange.put(rec + "", strTok.nextToken());
            rec++;

        }
        int count = (currentRange - 1) * max + 1;
        int lim = count + max - 1;
        if (lim > totalRecords) {
            lim = totalRecords;
        }
        String str = "Records " + count + " - " + lim + " of " + totalRecords + ".";
        if (totalRecords > 500) {
            str += " Please refine your search.";
        }
        tvp.setFooter("<html><b><font face='Trebuchet MS' size='8pts' color='3300CC'>" + str +
                "</font></b></html>");
        // rp.setText()
        //   tvp.tv.setData(data);

        CursorManager.changeCursor(0);

    }

    public void setData(Vector v) {
        // ctm.resetData();
        data = new Vector();
        mainData = new HashMap();
        int k = 0;
        for (int l = v.size(); k < l; k++) {
            Hashtable hashtable = (Hashtable) v.elementAt(k);
            ImageThumbData imagethumbdata = new ImageThumbData();
            imagethumbdata.setHeader((String) hashtable.get("THUMBNAILHEADER"));

            imagethumbdata.setLabel((String) hashtable.get("ISFDIGITALOBJECTIDENTIFIER"));
            String isfphoto = (String) hashtable.get("PHOTOGRAPHIDENTIFICATIONNO");
            imagethumbdata.setPhotoIdentificationNumber(isfphoto);
            imagethumbdata.setArchivalFileSize((String) hashtable.get("ARCHIVALFILESIZE"));
            imagethumbdata.setActualSidSize((String) hashtable.get("ACTUALSIDSIZE"));
            imagethumbdata.setPhotoTitle((String) hashtable.get("PHOTODESCRIPTIVETITLE"));
            imagethumbdata.setIcon((ImageIcon) hashtable.get("IMAGETHUMBNAIL"));
            imagethumbdata.setISFDigitalObjIdentifier((String) hashtable.get("ISFDIGITALOBJECTIDENTIFIER"));
            String typecode = (String) hashtable.get("FILMTYPECODE");
            String format = (String) hashtable.get("DIGITALOBJECTFORMAT");

            if (format != null && format.trim().equalsIgnoreCase("image/ptm")) {
                imagethumbdata.setType("RTI");
            } else if (format != null && format.trim().equalsIgnoreCase("image/hsh")) {
                imagethumbdata.setType("HSH");
            } else {
                imagethumbdata.setType("SID");
            }

            data.addElement(imagethumbdata);
            String CD = buildCatalougeData(isfphoto, hashtable);
            String DCD = buildDubinData(isfphoto, hashtable);
            Vector vec = new Vector();
            vec.addElement(CD);
            vec.addElement(DCD);
            //   jScrollPane2.invalidate();
            // jScrollPane2.validate();
            // jScrollPane2.repaint();
            mainData.put(isfphoto, vec);
        ///   jTable3.setRowHeights();

        }
        tvp.setLimits(v);
        tvp.tv.setData(data);
    //     obtainedData = v;
    }

  /*  PSVM(String args[]) {
        JFrame jframe = new JFrame();
        Vector vector = new Vector();
        vector.addElement("[AT_84_P13491_RS1_T");
        vector.addElement("AT_84_P13491_RS2_T");
        vector.addElement("AT_84_P13491_RS3_T");
        vector.addElement("AT_91_P13491_R_T");
        ImageResultsPanel imageresultspanel = new ImageResultsPanel();
        imageresultspanel.setPhotoIds(vector);
        jframe.getContentPane().add(imageresultspanel);
        jframe.setSize(500, 500);
        jframe.setVisible(true);
    }*/

    public Vector getNextData() throws NoDataFoundException {
        // int balance = totalRecords % max;
        int balance = totalRecords - (max * currentRange);
        //   JOptionPane.showMessageDialog(this,"Balance:"+balance+"::CurrentRange:"+currentRange);
        if (currentRange > 1) {
            tvp.back.setEnabled(true);
        } else {
            tvp.back.setEnabled(false);
        }

        if (balance <= 0) {
            tvp.next.setEnabled(false);
        } else {
            tvp.next.setEnabled(true);
        }
        Vector v;
        CacheManagerObject cmo = CacheManagerObject.getInstance();
        CacheEntry ce = new CacheEntry(currentRange +sortType+ "v.s6");
        boolean cache = cmo.isCached(ce);
        if (cache) {
            v = (Vector) cmo.retrieveFromCache(ce);
        //System.out.println("vector:"+v);
        } else {


            String isfphotos = (String) coreRange.get(currentRange + "");

            StringTokenizer st = new StringTokenizer(isfphotos, ",");
            String s1 = "<QUERY TYPE='" + sortType + "' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid + "'>";
            while (st.hasMoreTokens()) {
                s1 = s1 + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfnumber' VALUE=" + (st.nextToken()) + " OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
            }

            s1 = s1 + "</QUERY>";
            // System.out.println("string for query:"+s1);
            // RequestDispatcherObject requestdispatcherobject = new RequestDispatcherObject();
            String s2 = (String) RequestProxy.getRequestDispatcher().handleQueryEvent(s1);
            //  System.out.println("response:"+s2);

            MetaDataXmlConverter metadataxmlconverter = new MetaDataXmlConverter();
           // SAXParser saxparser = new SAXParser();
           // saxparser.setContentHandler(metadataxmlconverter);
            InputSource inputsource = new InputSource(new StringReader(s2));
            try {
                SAXParser saxparser = SAXParserFactory.newInstance().newSAXParser();
                saxparser.parse(inputsource,metadataxmlconverter);
            } catch (SAXParseException saxparseexception) {

                log.error(saxparseexception);
            } catch (Exception exception) {
                log.error(exception);

            }
            v = (Vector) metadataxmlconverter.getDataStructure();
            cmo.storeToCache(ce, v);
        }

        photoid = "";


        int count = (currentRange - 1) * max + 1;
        int lim = (count + max - 1);
        if (lim > totalRecords) {
            lim = totalRecords;
        }

        String str = "Records " + (count) + " - " + lim + " of " + totalRecords + ".";
        if (totalRecords > 500) {
            str += " Please refine your search.";
        }
        tvp.setFooter("<html><b><font face='Trebuchet MS' size='8pts' color='3300CC'>" + str +
                "</font></b></html>");
        return v;
    }

    public void updateCache(Vector v) {

        /* for(int i = 0; i<obtainedData.size();i++){
        Hashtable ht =(Hashtable) obtainedData.elementAt(i);
        ht.put("IMAGETHUMBNAIL",((ThumbData)v.elementAt(i)).getIcon());
        obtainedData.setElementAt(ht,i);
        }
        CacheManagerObject.getInstance().updateLocalFile(new CacheEntry(currentRange+"v.s6"),obtainedData);
        // System.out.println("obtained:"+obtainedData);
        CursorManager.changeCursor(0);
        // System.out.println("obtaineddata.size():"+obtainedData.size());*/
        // System.out.println("Doing Nothing in UpdateCache of ImageResultsPanel");
    }

    /*  public void setTextCode(String s) {
    textCode = s;
    }
    public String getTextCode() {
    return textCode ;
    }*/
    private String textCode = "";
    private String queryType = "";
    private String sortType = "photometadata";
    public void resetSort()
    {
        sortType = "photometadata";
        queryType="";
        textCode="";
    }

    public void sort(String type) {
        CursorManager.changeCursor(3);
        Vector vector1 = new Vector();
        String query = "";
        String[] cached = ImageLoaderManager.getCacheQuery();
        String size = "", list = "";
        String s14 = "";
        if (type.equalsIgnoreCase("FT")) {
            sortType = "photometadataFT";
            queryType = cached[0] + type;
            query = cached[1].replaceAll(cached[0], queryType);

        } else if (type.contains("DP")) {
            sortType = "photometadataDP";
            queryType = cached[0] + type;
            query = cached[1].replaceAll(cached[0], queryType);
        } else if (type.contains("MG")) {
            sortType = "photometadataMG";
            queryType = cached[0] + type;
            query = cached[1].replaceAll(cached[0], queryType);
        } else {
            sortType = "photometadata";
            queryType = cached[0];
            query = cached[1];
        }
        try {
          /*  String s8 = "<QUERY TYPE='" + type + "' MODE='' SESSIONID='" + RequestDispatcherObject.sessionid +
                    "'>";
            s8 = s8 + "<CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='isfassignedtextno' VALUE='" + (textCode) +
                    "' OPERATOR='=' CONNECTOR=''></QUERY_CONDITION></CLAUSE>";
            s8 = s8 + "</QUERY>";
            s14 = (String) RequestDispatcherObject.getInstance().handleQueryEvent(s8);
            if (s14 != null) {
                MetaDataXmlConverter generalxmlconverter = new MetaDataXmlConverter();
                SAXParser saxparser2 = new SAXParser();
                saxparser2.setContentHandler(generalxmlconverter);
                InputSource inputsource2 = new InputSource(new StringReader(s14));
                saxparser2.parse(inputsource2);*/
            String[] messages = new String[]{"","","",""};
            vector1 = ResultsUtil.getResults(queryType, query, messages, false);

                //String err = generalxmlconverter.getErrMsg();
             String err =messages[1];
                if (!"".equalsIgnoreCase(err)) {
                    CursorManager.changeCursor(0);
                    JOptionPane.showMessageDialog(this, err, "Error Message", 0);
                    return;
                }
              //  vector1 = (Vector) generalxmlconverter.getDataStructure();
              //  size = generalxmlconverter.getCount();
             //   list = generalxmlconverter.getListKeys();
                 size = messages[2];
                list = messages[3];
          /*  } else {
                return;
            }*/
        } catch (Exception ie) {
            CursorManager.changeCursor(0);
            JOptionPane.showMessageDialog(this, "Unknown exception occured. Please contact Administrator", "ERROR ",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (vector1.size() == 0) {
            // setCursor(new Cursor(0));
            CursorManager.changeCursor(0);

            JOptionPane.showMessageDialog(this, "No photoobjects are retrieved matching this query",
                    "Error Message", 0);
            return;
        }
        reset();
        populateData(vector1, size, list);
        invalidate();
        validate();
        repaint();

    }
}
