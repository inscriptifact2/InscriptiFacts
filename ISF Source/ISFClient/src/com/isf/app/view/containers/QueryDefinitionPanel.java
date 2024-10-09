package com.isf.app.view.containers;

import com.isf.app.models.TreeParent;
import com.isf.app.view.listeners.MainSearchListener;
import isf.controls.view.controls.InscriptButton;
import isf.controls.view.gui.VerticalLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class QueryDefinitionPanel extends JPanel
        implements ActionListener, MouseListener {

    InscriptButton jl8;
    JLabel keyword;
    JLabel text;
    public ButtonsPanel bp;
    public TextPanel textPanel;
    public QueryScrollPane qsp;
    public QueryMenuPanel qspParent;
    private ImageIcon icon;
    private MouseListener ml;
    private JPanel jpanel;

    private MainSearchListener mainSearchListener;

    public QueryDefinitionPanel() {
    }

    public QueryDefinitionPanel(QueryMenuPanel querymenupanel) {
        qspParent = querymenupanel;
        //querymenupanel.addMouseListener(this);
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(0xa49585));
        bp = new ButtonsPanel();
        //  bp.addMouseListener(this);
        icon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/QM_archiveby.jpg");
        add(jl8 = new InscriptButton(icon), "North");
        jl8.setBorderPainted(false);
        jl8.setBackground(new Color(0xa49585));
        jpanel = new JPanel() {

            public Insets getInsets() {
                double ad[] = isf.controls.utils.ResolutionManager.getInstance().getRatio();
                return new Insets(0, (int) (ad[0] * 14D), 0, 0);
            }

        };
        jpanel.setLayout(new VerticalLayout());
        jpanel.setBackground(new Color(0xa49585));

        jpanel.add(bp);
        qsp = new QueryScrollPane(querymenupanel);
        querymenupanel.addQueryScrollPane(qsp);
        qsp.setBorder(null);
        jpanel.add(qsp);
        textPanel = new TextPanel();
        textPanel.textNo.addActionListener(this);
        jpanel.add(textPanel);
        add(jpanel, "West");
        repaint();
    }

    public void addMainSearchListener(MainSearchListener mListener) {
        mainSearchListener = mListener;
        textPanel.addMainSearchListener(mainSearchListener);
        qsp.addMainSearchListener(mainSearchListener);
        // querymenupanel.addMainSearchListener(mainSearchListener);
    }

    public void addMouseListener(MouseListener ml) {
        this.ml = ml;
        jpanel.addMouseListener(ml);
    }

    public void reset() {
        textPanel.textNo.setSelectedIndex(-1);
        textPanel.publicationNo.setText("");
        textPanel.keyWord.setText("");
        // textPanel.repaint();
        qsp.reset();
    }

    public void setData(TreeParent treeparent) {
        qsp.setModal(treeparent);
    }

    public void mouseClicked(MouseEvent mouseevent) {
    }

    public void mousePressed(MouseEvent mouseevent) {
    }

    public void mouseReleased(MouseEvent mouseevent) {
    }

    public void mouseEntered(MouseEvent mouseevent) {
        //  System.out.println("qspentered");
        AbstractButton abstractbutton = (AbstractButton) mouseevent.getSource();
        if (abstractbutton.getActionCommand().equals("Qm_newsearch_active") && qspParent != null) {
            qspParent.hideAll();
        }

    }

    public void mouseExited(MouseEvent mouseevent) {
        //   System.out.println("qspexited");
        AbstractButton abstractbutton = (AbstractButton) mouseevent.getSource();
        if (abstractbutton.getActionCommand().equals("Qm_newsearch_active") && qspParent != null) {
            qspParent.addPanels();  //addLabels();
            // System.out.println("called from newsearch exit");
        }

    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    public Dimension getPreferredSize() {
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }

    public void actionPerformed(ActionEvent actionevent) {
        if (actionevent.getActionCommand().equals("Qm_newsearch_active")) {
            reset();
        }
        //if(actionevent.getSource()==textPanel.textNo)
        /*     if(actionevent.getActionCommand().equals("combobox"))
             {
                     System.out.println("from QDP &&&&&&&&&&&&&&&&&&&&&");
                     mainSearchListener.textPubSelection(3);
             }  */
    }

    public void setVisible(boolean flag) {
    }
/*
    PSVM(String args[]) {


        System.setProperty("sun.awt.noerasebackground", "true");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.getDefaults().put("ToolTip.background", new ColorUIResource(new Color(252, 250, 182)));
            UIManager.getDefaults().put("InternalFrame.closeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/exit.gif")));
            UIManager.getDefaults().put("InternalFrame.minimizeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/max.gif")));
            UIManager.getDefaults().put("InternalFrame.maximizeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/norm.gif")));
            UIManager.getDefaults().put("InternalFrame.iconifyIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/min.gif")));
            UIManager.getDefaults().put("InternalFrame.icon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/isf.jpg")));
            UIManager.getDefaults().put("InternalFrame.titleFont", new FontUIResource(new Font("Arial", 3, 10)));
            UIManager.getDefaults().put("InternalFrame.titleFont", new FontUIResource(new Font("Arial", 3, 10)));
//            UIManager.getDefaults().put("List.selectionBackground",new ColorUIResource(Color.white));
//             UIManager.getDefaults().put("List.selectionForeground",new ColorUIResource(new Color(0xac5539))) ;
//             UIManager.getDefaults().put("List.background",new ColorUIResource(Color.white));
//             UIManager.getDefaults().put("List.foreground",new ColorUIResource(Color.black));
//             UIManager.getDefaults().put("List.font",new FontUIResource(new Font("Arial",1,12)));
//             UIManager.getDefaults().put("List.selectionBorder",new ColorUIResource(Color.black));
//             UIManager.getDefaults().put("List.border",new ColorUIResource(Color.red));


            UIManager.getDefaults().put("ScrollBar.background", new ColorUIResource(new Color(0xd9d9d9)));
            UIManager.getDefaults().put("ScrollBar.borderColor", new ColorUIResource(Color.gray));
            UIManager.getDefaults().put("ScrollBar.buttonBorder", new LineBorder(Color.black, 1));
            //  UIManager.getDefaults().put( "ScrollBar.disabledThumbBackground", NewtonConstants.SCROLL_THUMB_BACKGROUND_COLOR);
            UIManager.getDefaults().put("ScrollBar.disabledBorder", new LineBorder(Color.black, 1));
            //  UIManager.getDefaults().put( "ScrollBar.rolloverBackground", NewtonConstants.SCROLL_THUMB_ROLLOVER_COLOR);
            //  UIManager.getDefaults().put( "ScrollBar.selectForeground", NewtonConstants.SCROLL_THUMB_ROLLOVER_COLOR);
            UIManager.getDefaults().put("ScrollBar.thumb", new ColorUIResource(new Color(0xa7a7a7)));
            //  UIManager.getDefaults().put( "ScrollBar.rolloverBackground", NewtonConstants.SCROLL_BACKGROUND_COLOR);
            UIManager.getDefaults().put("ScrollBar.trackBackground", new ColorUIResource(new Color(0xa7a7a7)));
            UIManager.getDefaults().put("ScrollBar.border", null);


            UIManager.put("MenuItemUI", "isf.controls.plaf.InscriptMenuItemUI");
            // UIManager.getDefaults().put("Label.border")
            //UIManager.put("ScrollBarUI","com.isf.isf.IPScrollBarUI");
            // UIManager.put("ComboBoxUI","isf.controls.plaf.ISFComboBoxUI" );
            UIManager.put("SplitPaneDividerUI", "javax.swing.plaf.basic.BasicSplitPaneDivider");


            //  System.out.println(UIManager.getDefaults().getColor("ToolTipUI"));
        } catch (Exception ie) {
        }


        DesktopTheme dt = new DesktopTheme(new Color(0xd9d9d9));
        MetalLookAndFeel mlf = (MetalLookAndFeel) UIManager.getLookAndFeel();
        mlf.setCurrentTheme(dt);

        JFrame jframe = new JFrame();
        jframe.setSize(1024, 700);
        jframe.setVisible(true);
        Container container = jframe.getContentPane();
        QueryMenuPanel querymenupanel = new QueryMenuPanel();
        QueryDefinitionPanel querydefinitionpanel = new QueryDefinitionPanel(querymenupanel);
        container.add(querydefinitionpanel, "West");

    }

*/
}
