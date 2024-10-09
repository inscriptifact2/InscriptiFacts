package isf.controls.view.containers;

import isf.common.utils.LogManager;
import isf.controls.utils.ResolutionManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.apache.log4j.Logger;

public class InscriptTextPane extends JTextPane implements HyperlinkListener {
Logger log = LogManager.getLogger(isf.controls.view.containers.InscriptTextPane.class);
    private Dimension dim;
    private boolean scale;
    MouseListener ml;
    MouseMotionListener mml;

    public InscriptTextPane(Dimension dimension) {
        scale = true;
        dim = dimension;
        setContentType("text/html");
    }

    public InscriptTextPane(Dimension dimension, boolean flag) {
        scale = true;
        dim = dimension;
        scale = flag;
        setContentType("text/html");
    }

    public void paint(Graphics g) {
        super.paint(g);
      /*  g.setColor(Color.black);
        Dimension dimension = getPreferredSize();
        if (!scale) {
            g.drawLine((dimension.width * 48) / 100 -2 , 0, (dimension.width * 48) / 100  -2, dimension.height);
        } else {
            double ad[] = ResolutionManager.getInstance().getRatio();
            g.drawLine((int) (ad[0] * (double) ((dimension.width * 48) / 100 -2 )), 0, (int) (ad[0] * (double) ((dimension.width * 48) / 100 -2)), dimension.height);
        }*/
        
      /*   g.setColor(Color.red);
      
        if (!scale) {
            g.drawLine((dimension.width * 52) / 100+5 , 0, (dimension.width * 52) / 100 +5, dimension.height);
        } else {
            double ad[] = ResolutionManager.getInstance().getRatio();
            g.drawLine((int) (ad[0] * (double) ((dimension.width * 52) / 100 +5)), 0, (int) (ad[0] * (double) ((dimension.width * 52) / 100 +5)), dimension.height);
        }
        
        g.setColor(Color.green);
      
        if (!scale) {
            g.drawLine((dimension.width * 51) / 100 , 0, (dimension.width * 51) / 100 , dimension.height);
        } else {
            double ad[] = ResolutionManager.getInstance().getRatio();
            g.drawLine((int) (ad[0] * (double) ((dimension.width * 51) / 100 )), 0, (int) (ad[0] * (double) ((dimension.width * 51) / 100 )), dimension.height);
        }*/
    }

    public void addMouseListener(MouseListener mouselistener) {
        super.addMouseListener(mouselistener);
        ml = mouselistener;
    }

    public void addMouseMotionListener(MouseMotionListener mousemotionlistener) {
        super.addMouseMotionListener(mousemotionlistener);
        mml = mousemotionlistener;
    }

    public Dimension getPreferredSize() {
        return dim;
    }



    public void hyperlinkUpdate(HyperlinkEvent e) {

        // System.out.println(e.getURL());
        try {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                JFrame window = new JFrame();
                JTextPane pane = new JTextPane();

                pane.setContentType("text/html");
                pane.setEditable(false);
                pane.setPage(e.getURL());

                window.getContentPane().add(pane);

                window.setBounds(0, 0, 800, 600);
                // this.setPage(e.getURL());
                window.setVisible(true);
            }
        } catch (Exception exp) {
            log.error(exp);
        }
    }
}
