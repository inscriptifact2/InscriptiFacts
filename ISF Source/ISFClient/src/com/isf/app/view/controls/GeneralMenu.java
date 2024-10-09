package com.isf.app.view.controls;

import com.isf.app.utils.GeneralMenuManager;
import isf.controls.plaf.GeneralMenuUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Dec 21, 2007
 * Time: 3:23:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeneralMenu extends JMenu //{
        implements MouseListener {

    private Dimension dim;
    int menuItemHeight;
    //  public static boolean hasExited = true;
    //   public static boolean isGMBClicked = false;
    //   public boolean isShowing;

    public GeneralMenu(String s) {
        super(s);
        menuItemHeight = 33;
        setDelay(300);
        setBackground(new Color(0xc8c0bc));
        setForeground(new Color(0));
        setActionCommand(s);
        setFocusPainted(false);
        setBorderPainted(true);
        setContentAreaFilled(false);
        setOpaque(true);
        setHorizontalAlignment(2);
        setVerticalTextPosition(0);
        setFont(new Font("Arial", 1, 11));
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(new InscriptDyanBorder(Color.white, 1, 1, 1, 1));


        addMouseListener(this);
        setUI(new GeneralMenuUI());

    }

    public GeneralMenu(String s, String s1) {
        this(s);
        // align = 0;
        InscriptImageIcon inscriptimageicon = new InscriptImageIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s1).getImage());
        setIcon(inscriptimageicon);
        dim = new Dimension(inscriptimageicon.getIconWidth(), inscriptimageicon.getIconHeight());
        menuItemHeight = inscriptimageicon.getIconHeight();
    }

    public JMenuItem add(JMenuItem jmenuitem) {
        super.add(jmenuitem);
        jmenuitem.setPreferredSize(getPreferredSize());
        if (getIcon() == null && !getActionCommand().startsWith(">")) {
            setText(">  " + getActionCommand());
        }
        return this;
    }

    public void mouseClicked(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
        //event.
        GeneralMenuManager.isGMBClicked = true;
        QueryMenu.isQMClicked = false;

    }

    public void mousePressed(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent event) {
        GeneralMenuManager.performExit(null, false);
        // System.out.println("Entering::" + hasExited + ":" + ((JMenuItem) (event.getSource())).getActionCommand());

    }

    public void mouseExited(MouseEvent event) {

        GeneralMenuManager.performExit((JMenu) event.getSource(), true);

    }


    public Dimension getPreferredSize() {

        if (getIcon() != null) {
            //System.out.println("Now Icon Height in WinMenu is : "+dim.getHeight() );
            return dim;
        }
        int i = getFontMetrics(new Font("Arial", 1, 11)).stringWidth(">  " + getActionCommand());
        if (super.getPreferredSize().width < i + 21) {
            //System.out.println("Now preferredsize less WinMenu is : "+menuItemHeight );
            return new Dimension(i + 21, menuItemHeight);
        } else {
            //System.out.println("Now preferredsize >= WinMenu is : "+menuItemHeight );
            return new Dimension(super.getPreferredSize().width, menuItemHeight);
        }
    }

    public int getHeight() {
        return menuItemHeight;
    }

    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }


   

    public static JMenuBar getGeneralMenu() {
        JMenuBar jmenubar = new JMenuBar();
        GeneralMenu generalMenu = new GeneralMenu("ONE", "images/GM_session_active.jpg");
        GeneralMenu GeneralMenu1 = new GeneralMenu("TWOGM_download_active.jpg", "images/GM_communication_active.jpg");
        GeneralMenu GeneralMenu2 = new GeneralMenu("THREE", "images/GM_help_active.jpg");
        GeneralMenu GeneralMenu3 = new GeneralMenu("FOUR", "images/qm_inactivearrow.jpg");
        jmenubar.add(generalMenu);
        jmenubar.add(GeneralMenu1);
        jmenubar.add(GeneralMenu2);
        jmenubar.add(GeneralMenu3);

        GeneralMenuItem GeneralMenu4 = new GeneralMenuItem("FOUR1");
        GeneralMenuItem GeneralMenu5 = new GeneralMenuItem("FOUR2");
        GeneralMenuItem GeneralMenu6 = new GeneralMenuItem("FOUR3");
        GeneralMenuItem GeneralMenu7 = new GeneralMenuItem("FOUR4");
        GeneralMenu3.add(GeneralMenu4);
        GeneralMenu3.add(GeneralMenu5);
        GeneralMenu3.add(GeneralMenu6);
        GeneralMenu3.add(GeneralMenu7);
        GeneralMenuItem GeneralMenu8 = new GeneralMenuItem("FOUR11");
        GeneralMenuItem GeneralMenu9 = new GeneralMenuItem("FOUR12");
        GeneralMenuItem GeneralMenu10 = new GeneralMenuItem("FOUR13");
        GeneralMenuItem GeneralMenu11 = new GeneralMenuItem("FOUR14");
        GeneralMenu4.add(GeneralMenu8);
        GeneralMenu4.add(GeneralMenu9);
        GeneralMenu4.add(GeneralMenu10);
        GeneralMenu4.add(GeneralMenu11);
        GeneralMenuItem GeneralMenu12 = new GeneralMenuItem("FOUR111");
        GeneralMenuItem GeneralMenu13 = new GeneralMenuItem("FOUR121");
        GeneralMenuItem GeneralMenu14 = new GeneralMenuItem("FOUR131");
        GeneralMenuItem GeneralMenu15 = new GeneralMenuItem("FOUR141");
        GeneralMenu2.add(GeneralMenu12);
        GeneralMenu2.add(GeneralMenu13);
        GeneralMenu2.add(GeneralMenu14);
        GeneralMenu2.add(GeneralMenu15);
        GeneralMenuItem GeneralMenu16 = new GeneralMenuItem("FOUR21");
        GeneralMenuItem GeneralMenu17 = new GeneralMenuItem("FOUR22");
        GeneralMenuItem GeneralMenu18 = new GeneralMenuItem("FOUR23");
        GeneralMenuItem GeneralMenu19 = new GeneralMenuItem("FOUR24");
        GeneralMenu5.add(GeneralMenu16);
        GeneralMenu5.add(GeneralMenu17);
        GeneralMenu5.add(GeneralMenu18);
        GeneralMenu5.add(GeneralMenu19);
        GeneralMenu5.setEnabled(false);
        GeneralMenuItem GeneralMenu20 = new GeneralMenuItem("FOUR31GM_download_active.jpg");
        GeneralMenuItem GeneralMenu21 = new GeneralMenuItem("FOUR32GM_download_active.jpg");
        GeneralMenuItem GeneralMenu22 = new GeneralMenuItem("FOUR33GM_download_active.jpg");
        GeneralMenuItem GeneralMenu23 = new GeneralMenuItem("FOUR34GM_download_active.jpg");
        GeneralMenu6.add(GeneralMenu20);
        GeneralMenu6.add(GeneralMenu21);
        GeneralMenu6.add(GeneralMenu22);
        GeneralMenu6.add(GeneralMenu23);
        GeneralMenuItem GeneralMenu24 = new GeneralMenuItem("ONE1");
        GeneralMenuItem GeneralMenu25 = new GeneralMenuItem("ONE2");
        GeneralMenuItem GeneralMenu26 = new GeneralMenuItem("ONE3");
        GeneralMenuItem GeneralMenu27 = new GeneralMenuItem("ONE4");
        generalMenu.add(GeneralMenu24);
        generalMenu.add(GeneralMenu25);
        generalMenu.add(GeneralMenu26);
        generalMenu.add(GeneralMenu27);
        GeneralMenuItem GeneralMenu28 = new GeneralMenuItem("ONE11");
        GeneralMenuItem GeneralMenu29 = new GeneralMenuItem("ONE12");
        GeneralMenuItem GeneralMenu30 = new GeneralMenuItem("ONE13");
        GeneralMenuItem GeneralMenu31 = new GeneralMenuItem("ONE14");
        GeneralMenu24.add(GeneralMenu28);
        GeneralMenu24.add(GeneralMenu29);
        GeneralMenu24.add(GeneralMenu30);
        GeneralMenu24.add(GeneralMenu31);
        GeneralMenuItem GeneralMenu32 = new GeneralMenuItem("ONE21");
        GeneralMenuItem GeneralMenu33 = new GeneralMenuItem("ONE22");
        GeneralMenuItem GeneralMenu34 = new GeneralMenuItem("ONE23");
        GeneralMenuItem GeneralMenu35 = new GeneralMenuItem("ONE24");
        GeneralMenu25.add(GeneralMenu32);
        GeneralMenu25.add(GeneralMenu33);
        GeneralMenu25.add(GeneralMenu34);
        GeneralMenu25.add(GeneralMenu35);
        return jmenubar;
    }

    public static JMenuBar getJMenu() {
        JMenuBar jmenubar = new JMenuBar();
        JMenu generalMenu = new JMenu("images/GM_session_active.jpg");
        JMenu JMenu1 = new JMenu("images/GM_communication_active.jpg");
        JMenu JMenu2 = new JMenu("images/GM_help_active.jpg");
        JMenu JMenu3 = new JMenu("images/qm_inactivearrow.jpg");
        jmenubar.add(generalMenu);
        jmenubar.add(JMenu1);
        jmenubar.add(JMenu2);
        jmenubar.add(JMenu3);

        JMenu JMenu4 = new JMenu("FOUR1");
        JMenu JMenu5 = new JMenu("FOUR2");
        JMenu JMenu6 = new JMenu("FOUR3");
        JMenu JMenu7 = new JMenu("FOUR4");
        JMenu3.add(JMenu4);
        JMenu3.add(JMenu5);
        JMenu3.add(JMenu6);
        JMenu3.add(JMenu7);
        JMenu JMenu8 = new JMenu("FOUR11");
        JMenu JMenu9 = new JMenu("FOUR12");
        JMenu JMenu10 = new JMenu("FOUR13");
        JMenu JMenu11 = new JMenu("FOUR14");
        JMenu4.add(JMenu8);
        JMenu4.add(JMenu9);
        JMenu4.add(JMenu10);
        JMenu4.add(JMenu11);
        JMenu JMenu12 = new JMenu("FOUR111");
        JMenu JMenu13 = new JMenu("FOUR121");
        JMenu JMenu14 = new JMenu("FOUR131");
        JMenu JMenu15 = new JMenu("FOUR141");
        JMenu2.add(JMenu12);
        JMenu2.add(JMenu13);
        JMenu2.add(JMenu14);
        JMenu2.add(JMenu15);
        JMenu JMenu16 = new JMenu("FOUR21");
        JMenu JMenu17 = new JMenu("FOUR22");
        JMenu JMenu18 = new JMenu("FOUR23");
        JMenu JMenu19 = new JMenu("FOUR24");
        JMenu5.add(JMenu16);
        JMenu5.add(JMenu17);
        JMenu5.add(JMenu18);
        JMenu5.add(JMenu19);
        JMenu5.setEnabled(false);
        JMenu JMenu20 = new JMenu("FOUR31GM_download_active.jpg");
        JMenu JMenu21 = new JMenu("FOUR32GM_download_active.jpg");
        JMenu JMenu22 = new JMenu("FOUR33GM_download_active.jpg");
        JMenu JMenu23 = new JMenu("FOUR34GM_download_active.jpg");
        JMenu6.add(JMenu20);
        JMenu6.add(JMenu21);
        JMenu6.add(JMenu22);
        JMenu6.add(JMenu23);
        JMenu JMenu24 = new JMenu("ONE1");
        JMenu JMenu25 = new JMenu("ONE2");
        JMenu JMenu26 = new JMenu("ONE3");
        JMenu JMenu27 = new JMenu("ONE4");
        generalMenu.add(JMenu24);
        generalMenu.add(JMenu25);
        generalMenu.add(JMenu26);
        generalMenu.add(JMenu27);
        JMenu JMenu28 = new JMenu("ONE11");
        JMenu JMenu29 = new JMenu("ONE12");
        JMenu JMenu30 = new JMenu("ONE13");
        JMenu JMenu31 = new JMenu("ONE14");
        JMenu24.add(JMenu28);
        JMenu24.add(JMenu29);
        JMenu24.add(JMenu30);
        JMenu24.add(JMenu31);
        JMenu JMenu32 = new JMenu("ONE21");
        JMenu JMenu33 = new JMenu("ONE22");
        JMenu JMenu34 = new JMenu("ONE23");
        JMenu JMenu35 = new JMenu("ONE24");
        JMenu25.add(JMenu32);
        JMenu25.add(JMenu33);
        JMenu25.add(JMenu34);
        JMenu25.add(JMenu35);
        return jmenubar;
    }

}
