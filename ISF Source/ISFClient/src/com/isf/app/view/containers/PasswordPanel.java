package com.isf.app.view.containers;

//import isf.controls.utils.LogManager;
import com.isf.app.view.listeners.LoginListener;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PasswordPanel extends TrimmedPanel
        implements MouseListener {

    public JPanel actionDeck;
    public JLabel changeLB;
    public JLabel forgotLB;
    public JLabel separatorLB;
    //   private ChangePasswordDialog dialog;
    private MouseListener ml;
    private LoginListener ll;
    private String userName;
    private String option;
    private Dimension resolution;
    //   private double panelWidth;
  
    public PasswordPanel() {
        
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel actions = new JPanel(){
           public Dimension getPreferredSize(){return new Dimension(240, 180);}
        };
        JPanel credits = new JPanel();
        
        actions.setOpaque(false); //false
        actions.setLayout(null);
        
        actionDeck = new JPanel();
        actionDeck.setOpaque(false);
       // actionDeck.setBackground(Color.white);
        actionDeck.setLayout(new FlowLayout(0, 0, 0));
       
        
         
        // resolution = new Dimension(1600,1200);
        resolution = ResolutionManager.getInstance().getScreen();
        int offset = (int) ((675 * resolution.getWidth() / 1280) - (660));        //640              //- (resolution.getWidth()/2)

        if (Toolkit.getDefaultToolkit().getBestCursorSize(32, 32).width <= 16) { //16
            if (resolution.getWidth() < 1280)
                offset = 20;
        }
        if (offset < 0)
            offset = 0;
       
       
       // pwdPanel.setBackground(Color.green);
        int fSize = 15;
        if (resolution.getWidth() < 1280)
            fSize = 15;
        else
            fSize = 18;
        //pwdPanel.setOpaque(false);
        
        // pwdPanel.setBackground(new Color(0xe6dfd9));
        changeLB = new JLabel("   change password | forgot password   ");
        changeLB.setFont(new Font("Trebuchet MS", Font.BOLD, fSize));
        changeLB.setForeground(new Color(0xcb995e));
        changeLB.setCursor(new Cursor(12));
        changeLB.addMouseListener(this);
        
        /*forgotLB = new JLabel("forgot password?");
        forgotLB.setFont(new Font("Trebuchet MS", Font.BOLD, fSize));
        forgotLB.setForeground(new Color(0xcb995e));
        forgotLB.setCursor(new Cursor(12));
        forgotLB.addMouseListener(this);*/
       /* separatorLB = new JLabel("  |  ");
        separatorLB.setForeground(new Color(0xab5439));*/
        actionDeck.add(changeLB);
      //  actionDeck.add(separatorLB);
      //  actionDeck.add(forgotLB);
       
        actionDeck.setBounds(offset, 25, 340, 100);
        changeLB.setBorder(new LineBorder(Color.BLUE,2));
        actions.add(actionDeck);
        add(actions,"North");
        
       
       
        credits.setOpaque(false);
        credits.setBackground(new Color(0xe6dfd9));
        //bottomPanel.setLayout(new SimpleVerticalLayout());
        credits.add(new JLabel(getTextLabel()));
        add(credits,"Center");
        //this.setBorder(new LineBorder(Color.black));

        //    setLogos();
    }

    /*  private void setLogos() {
          if (resolution.getWidth() == 1024D) {
              JPanel jpanel = new JPanel();
              jpanel.setOpaque(false);
              javax.swing.ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/1_logos_caos.jpg");
              jpanel.add(new JLabel(imageicon));
              add(jpanel, "West");
          }
      }*/

    public void addLoginListener(LoginListener loginlistener) {
        ll = loginlistener;
    }

    public void mouseEntered(MouseEvent mouseevent) {
        Object obj = mouseevent.getSource();
        JLabel jlabel = (JLabel) obj;
        jlabel.setCursor(jlabel.getCursor());
    }

    public void mouseExited(MouseEvent mouseevent) {
    }

    public void mousePressed(MouseEvent mouseevent) {
       // logger.debug("Clicked at :" + mouseevent.getX() + "---" + mouseevent.getY());
    }

    public void mouseReleased(MouseEvent mouseevent) {
    }

    public void mouseClicked(MouseEvent mouseevent) {
        Object obj = mouseevent.getSource();
        if ((JLabel) obj == changeLB) {
            option = "Change";
            changeLB.setCursor(new Cursor(3));
            ll.passwordSelectionChanged(option);
            changeLB.setCursor(new Cursor(Cursor.HAND_CURSOR));//new Cursor(0));
        } else if ((JLabel) obj == forgotLB) {
            option = "Forgot";
            forgotLB.setCursor(new Cursor(3));
            ll.forgotSelectionChanged(option);
            forgotLB.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /*  public Dimension getPreferredSize() {
          double ad[] = isf.controls.utils.ResolutionManager.getInstance().getRatio();
          Dimension dimension;
          if (resolution.getWidth() == 1024D) {
              dimension = new Dimension((int) (1020D * ad[0]), (int) (ad[1] * 1024D));
          } else {
              dimension = new Dimension((int) (660D * ad[0]), (int) (ad[1] * 1024D));
          }
          return dimension;
      }*/

    public void resetCursors() {
        forgotLB.setCursor(new Cursor(12));
        changeLB.setCursor(new Cursor(12));
    }
    
    
    private String getTextLabel() {
    
        String text = "<html><body> <pre><FONT SIZE=\"^\" face=\"Arial\" color=\"black\"><b>" 
                +"The West Semitic Research Project and [%!]InscriptiFact would like to<br>" 
                +"thank the <font color=\"#ac5539\">Underwood Family Trust Fund</font> [%!]for its generous funding of the<br>" 
                +"Internet Database Prototype.</b></font><br><br>" 
                +"<FONT SIZE=\"*\" face=\"Arial\" color=\"black\"><b>We would also like to thank the following [%!]for their support of the West Semitic Research <br>"
                +"Project and InscriptiFact:</b></font> <br><FONT SIZE=\"$\" face=\"Arial\" color=\"black\"><b>" 
                +"The Ahmanson Foundation<br>" 
                +"The Andrew W. Mellon Foundation<br>" 
                +"The Hewlett-Packard Company<br>"
                +"Institute for Museum and Library Services<br>"
                +"LizardTech Inc.<br>"
                +"The Oracle Corporation<br>"
                +"System Science Applications Inc.<br>"
                +"The University of Southern California [%!]Annenberg Center for Communication<br>"
                +"USC Office of the Provost<br>"
                +"USC  College of Letters Arts & Sciences<br>"
                +"USC Libraries<br>"
                +"Zumberge Research and Innovation Fund, [%!]University of Southern California</b></font></pre> </body></html>";


        if (resolution.getWidth() < 1200) {
            text = text.replace('^', '3');
            text = text.replace('*', '2');
            text = text.replace('$', '1');
            text = text.replace('[', '<');
            text = text.replace('%', 'b');
            text = text.replace('!', 'r');
            text = text.replace(']', '>');
            
        } else if (resolution.getWidth() >= 1200 && resolution.getWidth() < 2000) {
            text = text.replace('^', '4');
            text = text.replace('*', '3');
            text = text.replace('$', '2');
            text = text.replace('[', '-');
            text = text.replace('%', '-');
            text = text.replace('!', '-');
            text = text.replace(']', '-');
        } else if (resolution.getWidth() >= 2000 && resolution.getWidth() < 2600) {
            text = text.replace('^', '5');
            text = text.replace('*', '4');
            text = text.replace('$', '3');
             text = text.replace('[', '-');
            text = text.replace('%', '-');
            text = text.replace('!', '-');
            text = text.replace(']', '-');
        } else if (resolution.getWidth() >= 2600) {
            text = text.replace('^', '6');
            text = text.replace('*', '5');
            text = text.replace('$', '4');
             text = text.replace('[', '-');
            text = text.replace('%', '-');
            text = text.replace('!', '-');
            text = text.replace(']', '-');
        }
        StringTokenizer t = new StringTokenizer(text,"----");
        text="";
        while(t.hasMoreTokens())
            text = text+t.nextToken();
      
        //add(new JLabel(text));
        return text;

    }



}
