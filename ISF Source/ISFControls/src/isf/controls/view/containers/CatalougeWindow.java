package isf.controls.view.containers;

import isf.common.utils.LogManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.controls.InscriptButton;
import isf.controls.view.gui.VerticalLayout;
import isf.controls.view.listeners.CWListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CatalougeWindow extends JPanel
        implements ActionListener//, WindowListener//,MouseListener,MouseMotionListener,FocusHandler
{

    private InscriptButton catButton;
    private InscriptButton dubButton;
    //  private InscriptButton title;
    private JLabel title;
    private InscriptButton close1,  close2;
    private static Color bgColor = new Color(0xd9d9d9);
    private static ImageIcon icon = isf.controls.utils.ResolutionManager.getInstance().loadImage("images/cataloging_active.jpg");
    private static ImageIcon titleIcon = isf.controls.utils.ResolutionManager.getInstance().loadImage("images/dublincore_blank.jpg");
    private static ImageIcon closeRight = isf.controls.utils.ResolutionManager.getInstance().loadImage("images/dublincore_right.jpg");
    private static ImageIcon closeLeft = isf.controls.utils.ResolutionManager.getInstance().loadImage("images/dublincore_left.jpg");
    public Hashtable ht;
    public CWListener cwl;
    public InscriptTextPane cat;
    private int width;
    JScrollPane jScrollPane1;
    MouseListener ml;
    MouseMotionListener mml;
    int mouseX, mouseY, xposition, yposition;
    Logger log = LogManager.getLogger(isf.controls.view.containers.CatalougeWindow.class);

    public CatalougeWindow() {       //Frame frame
        //  super(frame);
        super();
        cwl = null;
        //  addWindowListener(this);
        //   addMouseListener(this);
        //  addMouseMotionListener(this);

        setLayout(new BorderLayout());
        setSize(titleIcon.getIconWidth() + closeLeft.getIconWidth() + closeRight.getIconWidth() + 8, 650);
        /* title = new InscriptButton("images/dublincore_blank01.jpg");
        title.setScaledStatus(true);
        title.addActionListener(this);
        title.setBorderPainted(false);
        title.setFocusPainted(false);
        title.setContentAreaFilled(false);*/
        title = new JLabel(titleIcon);

        //  close = new InscriptButton("images/dublic_close.jpg");
        close1 = new InscriptButton("images/dublincore_left.jpg");
        close1.setScaledStatus(true);
        close1.addActionListener(this);
        close1.setBorderPainted(false);
        close1.setFocusPainted(false);
        close1.setContentAreaFilled(false);
        close2 = new InscriptButton("images/dublincore_right.jpg");
        close2.setScaledStatus(true);
        close2.addActionListener(this);
        close2.setBorderPainted(false);
        close2.setFocusPainted(false);
        close2.setContentAreaFilled(false);
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.add(close1);
        p.add(title);
        p.add(close2);
        add(p, "North");
        TrimmedPanel trimmedpanel = new TrimmedPanel(new Insets(10, 5, 0, 5));
        trimmedpanel.setBackground(Color.black);
        trimmedpanel.setBorder(new EtchedBorder(0, bgColor, bgColor));
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new VerticalLayout());
        jpanel.setBackground(Color.black);
        jpanel.setBorder(null);
        trimmedpanel.add(jpanel);
        add(trimmedpanel, "Center");
        catButton = new InscriptButton("images/cataloging_active.jpg");
        catButton.setScaledStatus(true);
        catButton.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/cataloging_select.jpg"));
        catButton.addActionListener(this);
        catButton.setBackground(Color.black);
        catButton.setBorderPainted(false);
        catButton.setFocusPainted(false);
        catButton.setContentAreaFilled(false);
        catButton.setActionCommand("Catalouge");
        catButton.setHorizontalAlignment(2);
        jpanel.add(catButton);
        JPanel jpanel1 = new JPanel() {

            public Dimension getPreferredSize() {
                return new Dimension(CatalougeWindow.icon.getIconWidth(), 525);
            }
        };
        jpanel1.setBackground(Color.black);
        jpanel1.setLayout(new BorderLayout(0, 0));
        dubButton = new InscriptButton("images/dublincore_active.jpg");
        dubButton.setScaledStatus(true);
        dubButton.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/dublincore_select.jpg"));
        dubButton.addActionListener(this);
        dubButton.setBorderPainted(false);
        dubButton.setFocusPainted(false);
        dubButton.setContentAreaFilled(false);
        dubButton.setHorizontalAlignment(2);
        dubButton.setActionCommand("DoublinCoreCatalouge");
        jpanel1.add(dubButton, "North");
        jScrollPane1 = new JScrollPane(20, 31);
        cat = new InscriptTextPane(new Dimension(icon.getIconWidth() - 14, 9000), false);
        //     cat.addMouseMotionListener(this);
        //      cat.addMouseListener(this);

        cat.setEditable(false);
        cat.setSelectionColor(Color.white);
        cat.setContentType("text/html");

        //width = cat.getPreferredSize().width;
        width = icon.getIconWidth() - 14;//cat.getPreferredSize().width;
        jScrollPane1.setViewportView(cat);
        jScrollPane1.getVerticalScrollBar().setValue(0);
        jpanel1.add(jScrollPane1, "Center");
        jpanel.add(jpanel1);
        
        
        JLabel jlabel = new JLabel("© Copyright 2001 - "+(new GregorianCalendar()).get(GregorianCalendar.YEAR)+"  University of Southern California. All Rights Reserved.");
        jlabel.setFont(new Font("Arial", 1, 10));
        jlabel.setOpaque(true);
        jlabel.setBackground(Color.black);
        jlabel.setForeground(Color.white);
        jpanel.add(jlabel, "South");
    //  setLocation(350, 50);

    }

    public void addMouseListener(MouseListener mouselistener) {
        super.addMouseListener(mouselistener);
        ml = mouselistener;
    //  cat.addMouseListener(mouselistener);

    }

    public void addMouseMotionListener(MouseMotionListener mousemotionlistener) {
        super.addMouseMotionListener(mousemotionlistener);
        mml = mousemotionlistener;
    //   cat.addMouseMotionListener(mousemotionlistener);
    }

    public void actionPerformed(ActionEvent actionevent) {
        if (actionevent.getSource() == dubButton) {
            jScrollPane1.getVerticalScrollBar().setValue(0);
            if (cwl != null) {
                cwl.buttonSelected(dubButton.getActionCommand());
            }
            cat.setText(buildDubinData());
            cat.setCaretPosition(0);
            dubButton.setSelected(true);
            catButton.setSelected(false);
        } else if (actionevent.getSource() == catButton) {
            jScrollPane1.getVerticalScrollBar().setValue(0);
            if (cwl != null) {
                cwl.buttonSelected(catButton.getActionCommand());
            }
            cat.setText(buildCatalougeData());
            cat.setCaretPosition(0);
            dubButton.setSelected(false);
            catButton.setSelected(true);
        } else if (actionevent.getSource() == close1 || actionevent.getSource() == close2) {
            // dispose();
            GlassPaneManager.getInstance().hideAll();
        }
    }

    public void showDublinData() {
        jScrollPane1.getVerticalScrollBar().setValue(0);
        cat.setText(buildDubinData());
        cat.setCaretPosition(0);
        dubButton.setSelected(true);
        catButton.setSelected(false);
    }

    public void showCatalougeData() {
        jScrollPane1.getVerticalScrollBar().setValue(0);
        cat.setText(buildCatalougeData());
        cat.setCaretPosition(0);
        dubButton.setSelected(false);
        catButton.setSelected(true);
    }

    public void setData(Hashtable hashtable) {
        ht = hashtable;
    }

    private String buildCatalougeData() {
        String as[][] = isf.controls.utils.CatalogueConstants.photoCatalogueNames;
        String s = "<html><head><style> table{border: 1px black solid; background-color: black;  }  th,  td{ word-wrap:break-word; background-color: white;  }</style></head><body><table cellspacing=1  align='left' width='99%' >";
        String s1 = "</table></body></html>";
        //  String s2 = "<tr><td colspan =2><hr></td></tr>";

        StringBuffer sb = new StringBuffer();
       // sb.append(s);
        int k = 0;
        for (int j = as.length; k < j; k++) {
            try {
                ////////////////////////
                if (as[k][0].equals("Compression Ratio:")) {
                    String cratio = (String) ht.get(as[k][1]);
//System.out.println("cratio ------------ :"+cratio);
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
                    //System.out.println("cratio final  :"+cratio);
                    sb.append(getString(cratio, as[k][0], ""));
                /*String cratio=(String)ht.get(as[k][1]);
                //System.out.println("cratio ------------ :"+cratio);
                //String [] carr=cratio.split(":");
                int indF = cratio.indexOf(":");
                int indL = cratio.lastIndexOf(":");


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
                s3=s3+getString(cratio,as[k][0],"");*/
                } else if (as[k][0].equals("Film Type:")) {
                    //    System.out.println("--------------------------------- in film type ");
                    int code = Integer.parseInt((String) ht.get(as[k][1]));
                    //  System.out.println(" FCode------------------------ :"+code);
                    String desc = "";
                    switch (code) {
                        //     System.out.println("------------------------in film switch  ");
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
                                desc = code+"";
                                break;
                    }
                    // System.out.println(" desc 33333333   :"+desc);
                    sb.append(getString(desc, as[k][0], ""));
                } else if (as[k][0].equals("Magnification:")) {
                    //  System.out.println("------------------------------in magnifi  ");
                    int code = Integer.parseInt((String) ht.get(as[k][1]));
                    // System.out.println("Mcode--------------:"+code);
                    String desc = "";
                    // boolean flag1 = false;
                    switch (code) {
                        //   System.out.println("----------------------------in MSwitch  ");
                        case 1:
                            desc = "reference";
                            break;
                        case 2:
                            desc = "sectional";
                            break;
                        case 3:
                            desc = "detailed";
                            break;


                    }
                    //  System.out.println("desc  44444444444   :"+desc);
                    sb.append(getString(desc, as[k][0], ""));
                } else ////////////////////////
                {
                    sb.append(getString(ht.get(as[k][1]), as[k][0], ""));
                }
            } catch (Exception exception) {
                log.error(exception);
            }
        }
       // sb.append(s1);
        return sb.toString().trim().length() >0 ? sb.insert(0, s).append(s1).toString(): "";
    }

    private String buildDubinData() {
        int fontsize = 2;
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        if (current.width > 1450 && current.height > 800){
            fontsize = 3;
        }else if(current.width <=1280 && current.height <=768)
        {
            fontsize= 1;
        }
        String as[][] = isf.controls.utils.CatalogueConstants.photoDublinNames;
      /*  String s = "<html><head><style> table td{word-wrap:break-word;}</style></head><body><table border=0 align='left' width='" + (width-30) + "px' >";
        String s1 = "</table></body></html";
        String s2 = "";
        String s3 = "<tr><td width='" + ((width * 48) / 100 -2)+ "px' >" + "<b><u><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s4 = "</font></u></b></td><td width='" + ((width * 48) / 100  +2)+ "px' >" + "&nbsp;</td></tr>";
        String s5 = "<tr><td colspan =2><hr></td></tr>";
        String s6 = "<li>";*/
        String s = "<html><head><style> table{border: 1px black solid; background-color: black;  }  th,  td{ word-wrap:break-word; background-color: white;  }</style></head><body><table cellspacing=1  align='left' width='99%' >";
        String s1 = "</table></body></html>";
        String s2 = "";
        String s3 = "<tr ><td colspan=2 >" + "<b><u><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s4 = "</font></u></b><br>&nbsp;</td></tr>";
        String s5 = "";//<tr><td colspan =2><hr ></td></tr>";
        String s6 = "<li>";
        
        
        // String s21 = "";
        StringBuffer val = new StringBuffer();
        StringBuffer sb = new StringBuffer();
       // sb.append(s);
        int i = 0;
        for (int j = as.length; i < j; i++) {
            try {
                //  boolean flag = false;
                switch (i) {
                    case 3: // '\003'
                    case 4: // '\004'
                    case 5: // '\005'
                    case 6: // '\006'
                    case 7: // '\007'
                    //case 8:
                    case 9: // '\f'
                    case 12: // '\020'
                    case 14: // '\023'
                    case 17:
                    case 19:
                    case 20:
                    case 22: // '\026'
                    case 25: // '\027'
                    case 26:
                    case 27:
                    //case 28:
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
                        String s7 = s3 + "TYPE" + s4;
                        String s22 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s22.equals("")) {
                            val.append(s22);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s7);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 1: // '\001'
                        String s8 = s3 + "TITLE" + s4;
                        String s23 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s23.equals("")) {
                            val.append(s23);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s8);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 2: // '\002'
                        String s9 = s3 + "DESCRIPTION" + s4;
                        String s24 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s24.equals("")) {
                            val.append(s24);
                        }
                        s24 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s24.equals("")) {
                            val.append(s24);
                        }
                        s24 = getString(ht.get(as[i + 2][1]), as[i + 2][0], s6);
                        if (!s24.equals("")) {
                            val.append(s24);
                        }
                        s24 = getString(ht.get(as[i + 3][1]), as[i + 3][0], s6);
                        if (!s24.equals("")) {
                            val.append(s24);
                        }
                        s24 = getString(ht.get(as[i + 4][1]), as[i + 4][0], s6);
                        if (!s24.equals("")) {
                            val.append(s24);
                        }
                        s24 = getString(ht.get(as[i + 5][1]), as[i + 5][0], s6);
                        if (!s24.equals("")) {
                            val.append(s24);

                        }
                        /*     s24 = getString(ht.get(as[i + 6][1]), as[i + 6][0], s6);


                        if(!s24.equals(""))
                        {
                        val.append( s24;
                        }
                         */

                        if (!val.toString().equals("")) {
                            sb.append(s9);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 8: // '\b'
                        String s10 = s3 + "IDENTIFIER" + s4;
                        String s25 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s25.equals("")) {
                            val.append(s25);
                        }
                        s25 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s25.equals("")) {
                            val.append(s25);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s10);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 10: // '\n'10
                        String s11 = s3 + "RELATION" + s4;
                        String s26 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s26.equals("")) {
                            val.append(s26);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s11);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 11: // '\013'
                        String s12 = s3 + "DATE" + s4;
                        String s27 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s27.equals("")) {
                            val.append(s27);
                        }
                        s27 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s27.equals("")) {
                            val.append(s27);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s12);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 13: // '\r'
                        String s13 = s3 + "LANGUAGE" + s4;
                        String s28 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s28.equals("")) {
                            val.append(s28);
                        }
                        s28 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s28.equals("")) {
                            val.append(s28);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s13);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 15: // '\016'
                        String s14 = s3 + "GEOGRAPHICAL COVERAGE" + s4;
                        String s29 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s29.equals("")) {
                            val.append(s29);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s14);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 16: // '\017'
                        String s15 = s3 + "TEMPORAL COVERAGE" + s4;
                        String s30 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s30.equals("")) {
                            val.append(s30);
                        }
                        s30 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s30.equals("")) {
                            val.append(s30);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s15);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 18: // '\021'
                        String s16 = s3 + "SOURCE" + s4;
                        String s31 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s31.equals("")) {
                            val.append(s31);
                        }
                        s31 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s31.equals("")) {
                            val.append(s31);
                        }
                        s31 = getString(ht.get(as[i + 2][1]), as[i + 2][0], s6);
                        if (!s31.equals("")) {
                            val.append(s31);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s16);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 21: // '\022'
                        String s17 = s3 + "CREATOR" + s4;
                        String s32 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s32.equals("")) {
                            val.append(s32);
                        }
                        s32 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s32.equals("")) {
                            val.append(s32);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s17);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 23: // '\024'
                        String s18 = s3 + "CONTRIBUTOR" + s4;
                        String s33 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s33.equals("")) {
                            val.append(s33);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s18);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 24: // '\025'
                        String s19 = s3 + "RIGHTS" + s4;
                        String s34 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s34.equals("")) {
                            val.append(s34);
                        }
                        s34 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s34.equals("")) {
                            val.append(s34);
                        }
                        s34 = getString(ht.get(as[i + 2][1]), as[i + 2][0], s6);
                        if (!s34.equals("")) {
                            val.append(s34);
                        }
                        s34 = getString(ht.get(as[i + 3][1]), as[i + 3][0], s6);
                        if (!s34.equals("")) {
                            val.append(s34);
                        }
                        
                        if (!val.toString().equals("")) {
                            sb.append(s19);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;

                    case 28: // '\030'
                        String s20 = s3 + "FORMAT" + s4;
                        String s35 = getString(ht.get(as[i][1]), as[i][0], s6);
                        if (!s35.equals("")) {
                            val.append(s35);
                        }
                        s35 = getString(ht.get(as[i + 1][1]), as[i + 1][0], s6);
                        if (!s35.equals("")) {
                            val.append(s35);
                        }
                        s35 = getString(ht.get(as[i + 2][1]), as[i + 2][0], s6);
                        if (!s35.equals("")) {
                            val.append(s35);
                        }
                        s35 = getString(ht.get(as[i + 3][1]), as[i + 3][0], s6);
                        if (!s35.equals("")) {
                            val.append(s35);
                        }
                        /////////////////////////////////
                        String cratio = (String) ht.get(as[i + 4][1]);
                        //System.out.println("cratio ------------ :"+cratio);
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
                        s35 = getString(cratio, as[i + 4][0], s6);

                        /*    String cratio=(String)ht.get(as[i+4][1]);
                        int indF = cratio.indexOf(":");
                        int indL = cratio.lastIndexOf(":");
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
                        s35=getString(cratio,as[i+4][0],s6);*/
                        /////////////////////////////////////////
                        /*
                        s35 = getString(ht.get(as[i + 4][1]), as[i + 4][0], s6);*/
                        if (!s35.equals("")) {
                            val.append(s35);
                        }

                        s35 = getString(ht.get(as[i + 5][1]), as[i + 5][0], s6);
                        if (!s35.equals("")) {
                            val.append(s35);
                        }
                        ///////////////////////////////////////

                        int filmTypeCode = Integer.parseInt((String) ht.get(as[i + 6][1]));

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
                                filmTypeDesc = filmTypeCode+"";
                                break;
                        }

                        s35 = getString(filmTypeDesc, as[i + 6][0], s6);
                        if (!s35.equals("")) {
                            val.append(s35);
                        }

                        int magnificationCode = Integer.parseInt((String) ht.get(as[i + 7][1]));
                        String magnificationDesc = "";
                        switch (magnificationCode) {
                            case 1:
                                magnificationDesc = "reference";
                                break;
                            case 2:
                                magnificationDesc = "sectional";
                                break;
                            case 3:
                                magnificationDesc = "detailed";
                                break;
                        }

                        s35 = getString(magnificationDesc, as[i + 7][0], s6);

                        /////////////////////////////////////
                        /* s35 = getString(ht.get(as[i + 6][1]), as[i + 6][0], s6);*/
                        if (!s35.equals("")) {
                            val.append(s35);
                        }

                        s35 = getString(ht.get(as[i + 8][1]), as[i + 8][0], s6);
                        if (!s35.equals("")) {
                            val.append(s35);
                        }
                        if (!val.toString().equals("")) {
                            sb.append(s20);
                            sb.append(val.toString());
                            sb.append(s5);
                            val = new StringBuffer();
                        }
                        break;
                }
            } catch (Exception exception) {
                log.error(exception);
            }
        }
       // sb.append(s1);
        return sb.toString().trim().length()>0 ? sb.insert(0, s).append(s1).toString():"";
    }

    private String getString(Object obj, String s, String s1) {
        Dimension current = Toolkit.getDefaultToolkit().getScreenSize();
        int fontsize = 2;
        if (current.width > 1450 && current.height > 800) {
            fontsize = 3;
        }else if(current.width <=1280 && current.height <=768)
        {
            fontsize= 1;
        }
        String s2 = "<tr ><td width='48%' valign='top' >" + "<b><font face='Arial' size='" + fontsize + "' color='AC5539'>";
        String s3 = "</font></b><br>&nbsp;</td>";
        String s4 = "<td width='52%'  >" + "<b><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></b><br>&nbsp;</td></tr>";
        String s6 = "";//s1.equals("") ? "<tr><td colspan =2><hr></td></tr>" : "";
        /*String s2 = "<tr><td width='" + ((width * 48) / 100 -2) + "px' valign='top'>" + "<b><font face='Arial' size='" + fontsize + "' color='AC5539'>";
        String s3 = "</font></b></td>";
        String s4 = "<td width='" + ((width * 48) / 100 +2) + "px' >" + "<b><font face='Arial' size='" + fontsize + "' color='000000'>";
        String s5 = "</font></b></td></tr>";
        String s6 = s1.equals("") ? "<tr><td colspan =2><hr></td></tr>" : "";*/
        //String s7 = "";
        StringBuffer sb = new StringBuffer();
        if (obj instanceof String) {
            return isValid((String) obj) ? s2 + s1 + s + s3 + s4 + processString((String) obj) + s5 + s6 : "";
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
                        sb.append(s2);
                        sb.append(s1);
                        sb.append(s);
                        sb.append(s3);
                        sb.append(s4);
                        sb.append(processString((String) obj1));
                        sb.append(s5);
                        sb.append(s6);
                    }
                } else if (obj1 instanceof Vector) {
                    Vector vector1 = (Vector) obj1;
                    if (vector1.size() == 0 || vector1 == null) {
                        return "";
                    }
                    String s8 = "";
                    int k = 0;
                    for (int l = vector1.size(); k < l; k++) {
                        s8 = s8 + (String) vector1.elementAt(k) + " ";
                    }

                    sb.append(s2);
                    sb.append(s1);
                    sb.append(s);
                    sb.append(s3);
                    sb.append(s4);
                    sb.append(s8);
                    sb.append(s5);
                    sb.append(s6);
                }
            }

        }
        return sb.toString();
    }
    
    private String processString(String s)
    {
       int x =0, p = s.indexOf("+");
        for (; s.indexOf("+",x) != -1; ) {
            s = s.substring(0, s.indexOf("+",x)+1) + " " + s.substring(s.indexOf("+",x) + 1);
            x = p+1;
            p = s.indexOf("+",x);
        }
        return s;
    }

    private boolean isValid(String s) {
                // fix for MIS-PI 6/8A

        
        if (s == null) {
            return false;
        }
        if (s.trim().length() == 0) {
            return false;
        }
        
 // end fix
        return !s.trim().equals("(   -   )");
    }

    /* private String getString(Vector vector)
    {
    String s = "";
    if(vector == null || vector.size() == 0)
    {
    return s;
    }
    int i = 0;
    for(int j = vector.size(); i < j; i++)
    {
    s = s + (String)vector.elementAt(i);
    }

    return s;
    }*/

    /* public void windowActivated(WindowEvent windowevent) {
    getOwner().requestFocus();
    }

    public void windowDeactivated(WindowEvent windowevent) {
    }

    public void windowClosing(WindowEvent windowevent) {
    }

    public void windowClosed(WindowEvent windowevent) {
    }

    public void windowOpened(WindowEvent windowevent) {
    getOwner().requestFocus();
    }

    public void windowIconified(WindowEvent windowevent) {
    }

    public void windowDeiconified(WindowEvent windowevent) {
    }*/
    public void addCWListener(CWListener cwlistener) {
        cwl = cwlistener;
    }
    /*	public void setFocus()
    {
    cat.requestFocus();
    }
    public void mousePressed(MouseEvent mouseevent)
    {
    temp = mouseevent.getPoint();
    setCursor(new Cursor(13));
    cat.setCursor(new Cursor(13));
    xposition = getX();
    yposition = getY();
    System.out.println("xposition  :"+xposition);
    System.out.println("yposition  :"+yposition);
    Dimension dimension = getSize();
    System.out.println("size :"+dimension);
    p.x = xposition - mouseX;
    p.y = yposition - mouseY;
    Graphics2D graphics2d = (Graphics2D)getGraphics();
    old = new Rectangle(p.x, p.y, dimension.width, dimension.height);
    graphics2d.setXORMode(Color.yellow);
    graphics2d.draw(old);
    }
    public void mouseClicked(MouseEvent mouseevent){}
    public void mouseExited(MouseEvent mouseevent){}
    public void mouseEntered(MouseEvent mouseevent){}

    public void mouseReleased(MouseEvent mouseevent)
    {
    // 	CatalougeWindow cw = cp;
    System.out.println("in muse released");
    Graphics2D graphics2d = (Graphics2D)getGraphics();
    graphics2d.setXORMode(Color.yellow);
    graphics2d.draw(old);
    setLocation(old.getLocation());
    old = null;
    setCursor(new Cursor(0));
    cat.setCursor(new Cursor(0));
    }
    public void mouseDragged(MouseEvent mouseevent)
    {
    Dimension dimension = getSize();
    p.x = ((mouseevent.getX() - temp.x) + xposition) - mouseX;
    p.y = ((mouseevent.getY() - temp.y) + yposition) - mouseY;
    Rectangle rectangle = new Rectangle(p.x, p.y, dimension.width, dimension.height);
    //	System.out.println("in muse dragged  " +rectangle);
    Graphics2D graphics2d = (Graphics2D)getGraphics();
    graphics2d.setXORMode(Color.yellow);
    graphics2d.draw(old);
    graphics2d.draw(rectangle);
    old = rectangle;
    }
    public void mouseMoved(MouseEvent mouseevent){}*/

   /* PSVM(String args[]) {
        JFrame frame = new JFrame();

        CatalougeWindow catalougewindow = new CatalougeWindow();
        frame.getContentPane().add(catalougewindow);
        frame.setSize(catalougewindow.getSize());
        frame.setVisible(true);
    //catalougewindow.show();
    }*/
}
