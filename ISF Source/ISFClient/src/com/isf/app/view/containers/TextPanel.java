package com.isf.app.view.containers;

import com.isf.app.view.controls.InscriptDyanBorder;
import com.isf.app.view.listeners.MainSearchListener;
import isf.common.TextPublicationComparator;
import isf.common.utils.LogManager;
import isf.controls.view.controls.IsfComboBox;
import isf.controls.view.gui.VerticalLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Vector;
import javax.swing.*;
import org.apache.log4j.Logger;

public class TextPanel extends JPanel
        implements MouseListener//,ActionListener
{

    public IsfComboBox textNo;
    //  public ISFComboBox textNo;
    public JTextField publicationNo;
    public JTextArea keyWord;
    public JLabel jlabel1, jlabel2, jlabel3;
    private ImageIcon rtcurveIcon;
    private Vector v;
    private MainSearchListener mainSearchListener;
    Logger logger = LogManager.getLogger(com.isf.app.view.containers.TextPanel.class);

    public TextPanel() {
        v = null;

        isf.controls.utils.ResolutionManager resolutionmanager = isf.controls.utils.ResolutionManager.getInstance();
        setBackground(new Color(0xd9d9d9));
        v = new Vector();

        JPanel jpanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(2, 2, 2, 2);
            }
        };
        jpanel.setBackground(new Color(0xd9d9d9));
        // setLayout(new LineLayout(true));
        setLayout(new VerticalLayout());
        JPanel jpanel1 = new JPanel() {
            public Insets getInsets() {
                return new Insets(2, 2, 2, 2);
            }
        };
        JLabel jlabel = new JLabel("                    ");
        jlabel.setBackground(new Color(0xd9d9d9));
        JScrollPane jscrollpane = new JScrollPane();
        jlabel1 = new JLabel("Keywords or Phrases", SwingConstants.CENTER);
        //   jlabel1.addMouseListener(this);
        //  JList list = new JList();
        //   JScrollPane pop = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER )  ;
        jlabel1.setBackground(new Color(0xd9d9d9));
        jlabel1.setFont(new Font("Arial", 1, 12));
        jlabel1.setForeground(new Color(0));
        keyWord = new JTextArea(3, resolutionmanager.getTextColumns(13));
        keyWord.addMouseListener(this);
        keyWord.setLineWrap(true);
        keyWord.setBorder(BorderFactory.createEtchedBorder());
        keyWord.addMouseListener(this);
        keyWord.setFont(new Font("Arial", 1, 12));
        jscrollpane.setViewportView(keyWord);
        jpanel.add(jscrollpane);
        jlabel2 = new JLabel("                    ");
        //   jlabel2.addMouseListener(this);
        jlabel2.setBackground(new Color(0xd9d9d9));
        jlabel3 = new JLabel("Text or Publication No.", SwingConstants.CENTER);
        // jlabel3.addMouseListener(this);
        jlabel3.setBackground(new Color(0xd9d9d9));
        jlabel3.setFont(new Font("Arial", 1, 12));
        jlabel3.setForeground(new Color(0));


        textNo = new IsfComboBox(v);
        //textNo.updateUI() ;

        int x = (v.size() > 10) ? 10 : v.size();
        // System.out.println(5+" compared to "+v.size());
        textNo.setMaximumRowCount(x);
        // System.out.println(textNo.getMaximumRowCount() );
        textNo.setActionCommand("combobox");
        textNo.addMouseListener(this);
        textNo.setFont(new Font("Arial", 1, 12));
        /* Dimension d = textNo.getPreferredSize() ;
         System.out.println("Dimension:"+d.width+"::"+d.height);
         textNo.setPreferredSize(new Dimension(70, d.height ));
         textNo.setPopupWidth(d.width);*/
        textNo.setSelectedIndex(-1);


        //////////////////////////////////////////////   ((InscriptComboUI)textNo.getUI()).setPopSize(textNo.getPreferredSize(),textNo);

        //   textNo.setRenderer(new InscriptListCellRenderer());
        textNo.setBackground(Color.white);
        //textNo.is
        //  textNo.addActionListener(this);
        //  textNo.setPopupVisible(false);

        publicationNo = new JTextField(resolutionmanager.getTextColumns(6));
        publicationNo.setActionCommand("textfield");
        publicationNo.addMouseListener(this);
        publicationNo.setFont(new Font("Arial", 1, 12));
        jpanel1.setBackground(new Color(0xd9d9d9));
        jpanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        // jpanel1.add(new JLabel("Prefix")) ;
        jpanel1.add(textNo);


        jpanel1.add(publicationNo);
        // jpanel1.add(pop);
        //  pop.setSize(75,50);
        // jpanel1.add(jp);
        rtcurveIcon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/QM_curveright.jpg");
        JLabel jlabel4 = new JLabel(rtcurveIcon);
        jlabel4.setBorder(new InscriptDyanBorder(new Color(0xa49585), 0, 0, 0, 1));
        add(jlabel);
        add(jlabel1);
        add(jpanel);
        add(jlabel2);
        add(jlabel3);
        add(jpanel1);
        add(jlabel4);

    }

    public void addMainSearchListener(MainSearchListener mListener) {
        mainSearchListener = mListener;
    }

    public void mouseEntered(MouseEvent mouseevent) {
        if (textNo.isPopupVisible()) {
            textNo.hidePopup();
        }
    }

    public void mouseClicked(MouseEvent mouseevent) {
        Object obj = mouseevent.getSource();
        if (obj == keyWord) {
            if (!keyWord.getText().equals("")) {
                // System.out.println("keyWord.getText()"+keyWord.getText());
                mainSearchListener.querySelection(1);
            }
        }

        if (obj == publicationNo) {
            if (!publicationNo.getText().equals("")) {
                // System.out.println("publicationNo.getText()"+publicationNo.getText());
                mainSearchListener.querySelection(2);
            }
        }

    }

    public void mouseExited(MouseEvent mouseevent) {
        if (textNo.isPopupVisible()) {
            textNo.hidePopup();
            // pop.setVisible(false);
        }
    }

    public void mousePressed(MouseEvent mouseevent) {


    }

    public void mouseReleased(MouseEvent mouseevent) {
    }

    public void setTextPublicationData(Vector vector) {
        int i = 0;
        v.removeAllElements();
        //System.out.println("vector   : "+vector);
        for (int j = vector.size(); i < j; i++) {
            v.addElement(vector.elementAt(i));
        }
        //System.out.println("V  : "+v);
        Collections.sort(v, new TextPublicationComparator());
        int x = (v.size() > 10) ? 10 : v.size();
        // System.out.println(10+" compared to "+v.size());
        textNo.setMaximumRowCount(x);
        ///   System.out.println(textNo.getMaximumRowCount() );
        //   textNo.updateUI();
        //  textNo.setSelectedIndex(-1);
        textNo.setFont(new Font("Arial", 1, 12));
        Dimension d = textNo.getPreferredSize();
        logger.debug("Dimension:" + d.width + "::" + d.height);
        textNo.setPreferredSize(new Dimension(70, d.height));
        textNo.setPopupWidth(d.width);
        textNo.setSelectedIndex(-1);

    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    /* public Dimension getPreferredSize()
     {
         return new Dimension(rtcurveIcon.getIconWidth(), rtcurveIcon.getIconHeight());//171
     }*/

   

    public void actionPerformed(ActionEvent event) {


    }
}
