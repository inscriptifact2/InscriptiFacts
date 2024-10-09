package com.isf.app.view.containers;

import com.isf.app.utils.FetchImageThread;
import com.isf.app.view.gui.ImageConsumer;
import com.isf.app.view.listeners.ClickListener;
import com.isf.app.view.listeners.SpatialListSelectionListener;
import isf.common.utils.InscriptConfigLoader;
import isf.common.utils.LogManager;
import isf.common.view.gui.SpatialListImage;
import isf.controls.utils.CursorManager;
import isf.controls.utils.GlassPaneManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

public class IndexMapPanel extends JPanel
        implements SpatialListSelectionListener, ActionListener, ImageConsumer {

    private FrameTitlePanel ftp;
    private IndexImagePanel ip;
    private SpatialListImage spl;
    public static boolean show = true, ready = false;
    public IMapGP imap;
    Logger log = LogManager.getLogger(com.isf.app.view.containers.IndexMapPanel.class);

    public IndexMapPanel() {
        spl = null;
        setSize(500, 200);
        setLayout(new BorderLayout());
        ftp = new FrameTitlePanel();
        add(ftp, "North");
        ip = new IndexImagePanel();
        add(ip, "Center");
        setBackground(Color.black);
        // ip.setImage(null);
        setBorder(BorderFactory.createLineBorder(Color.white));
        imap = new IMapGP();
        //  glass.add(imap);

    }

    public String createText(String s) {
        return "<html><body><font face='Arial' color='#000000' size='13pts'><b>" + s + "</b></font></body></html>";
    }


    public void showMsg() {
        if (this.isShowing()) {
            // imap.setLocation(getLocationOnScreen());
            if (show) {
                imap.showMsg(this);
                GlassPaneManager.getInstance().showComponent(imap);
            }
            //  glass.setVisible(true);


        }

    }

    public Insets getInsets() {
        return new Insets(3, 3, 3, 3);
    }


    public void addClickListener(ClickListener cl) {
        ip.addClickListener(cl);
    }

    public void reset() {
        ftp.setViewName("");
        ftp.setTitle("");
        ip.reset();
        spl = null;

        if (imap.isShowing())
            imap.setVisible(false);
        GlassPaneManager.getInstance().hideAll();

    }

    public void setGlass() {
        //    getRootPane().setGlassPane(glass);      ///  getRootPane().setGlassPane(glass);
        ready = true;

    }

    public void clearAll() {
        ip.clearAll();
    }

    public void spatialListImageSelected(SpatialListImage spatiallistimage) {


        //todo is show up message that image is being fetched
        if (ready) {
            imap.showLoading();
            GlassPaneManager.getInstance().showComponent(imap);

            // glass.setVisible(true);
        } else {
            // getRootPane().setGlassPane(glass);
            ready = true;
            imap.showLoading();
            GlassPaneManager.getInstance().showComponent(imap);

            //  glass.setVisible(true);
        }
        try {
            if (spatiallistimage != null) {
                spl = spatiallistimage;
                String s = spatiallistimage.getIndexMapName();
                ip.reset();

                String cat = InscriptConfigLoader.getInstance().getProperty("Locimap");
                if (cat == null) cat = "Indexmap";
                String tmp = "cat=" + cat + "&item=/" + s.trim() + "x.jpg";
                CursorManager.changeCursor(3);
                new FetchImageThread(this, tmp, 0);

            } else {
                spatiallistimage = null;
                ftp.setViewName("");
                ftp.setTitle("");
                ip.setImage(null);
            }
        } catch (Exception exception) {
            log.error(exception);
        }


    }

    public void setUpImg(ImageIcon ic, int index) {
        ftp.setViewName(spl.getTextView());
        ftp.setTitle(spl.getTitle());
        if (ic != null)
            ip.setImage(ic);
        if (show) {
            imap.showMsg(this);
            GlassPaneManager.getInstance().showComponent(imap);

            // glass.setVisible(true);


        } else {
            imap.setVisible(false);
            GlassPaneManager.getInstance().hideAll();

        }


        // setCursor(new Cursor(Cursor.DEFAULT_CURSOR))  ;
        CursorManager.changeCursor(0);

    }

    public Rectangle getSelectedPoints() {
        return ip.getSelectedPoints();
    }

    public SpatialListImage getSelSpatialListImage() {
        return spl;
    }

    public void actionPerformed(ActionEvent event) {
        show = !imap.dontshow;
        //  glass.setVisible(false);
        imap.setVisible(false);
        GlassPaneManager.getInstance().hideAll();

    }


}
