package com.isf.app.view.containers;

import com.isf.app.view.listeners.ClickListener;
import isf.common.view.gui.SpatialListImage;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JPanel;

public class SpatialSearchPanel extends JPanel implements ClickListener {

    private SpatialListPanel spl;
    private IndexMapPanel imp;
    private ActionListener al;

    public SpatialSearchPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.black);
        spl = new SpatialListPanel();
        add(spl, "West");
        TrimmedPanel trimmedpanel = new TrimmedPanel(new Insets(20, 14, 10, 20));
        trimmedpanel.setLayout(new BorderLayout());
        trimmedpanel.setBackground(Color.black);
        imp = new IndexMapPanel();
        imp.addClickListener(this);
        trimmedpanel.add(imp, "Center");
        add(trimmedpanel, "Center");
        spl.addSpatialListSelectionListener(imp);
    }

    public void setSpatialData(Vector response) {
        spl.setSpatialData(response);
        //  imp.showMsg();
    }

    public void setDefault() {

        imp.setGlass();
        spl.setDefault();
    }

    public void hideGlass() {
        if (imp.imap.isShowing()) {
            imp.imap.setVisible(false);   //if(imp.glass.isshowing   )  imp.glass.setvisible(false)
            GlassPaneManager.getInstance().hideAll();

            // imp.imap.removeAll();
        }
    }

    public void showGlass() {

        if (imp.show) {
            imp.imap.showMsg(imp);
            GlassPaneManager.getInstance().showComponent(imp.imap);

        } else {
            imp.imap.setVisible(false);
            GlassPaneManager.getInstance().hideAll();


        }
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        imp.imap.addActionListener(actionlistener);
    }

    public void reset() {
        
        spl.reset();
        imp.reset();
        
    }

    public void clearAll() {
        imp.clearAll();
    }

    public Rectangle getSelectedPoints() {
        return imp.getSelectedPoints();
    }

    public SpatialListImage getSelSpatialListImage() {
        return imp.getSelSpatialListImage();
    }


    /* PSVM(String args[])
     {
         JFrame jframe = new JFrame();
         jframe.setSize(1024, 768);
         SpatialSearchPanel spatialsearchpanel = new SpatialSearchPanel();
         Vector vector = new Vector();
                 SpatialListImageObject spatiallistimageobject = new SpatialListImageObject("Front View", isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/geotest.jpg"));
                 vector.add(spatiallistimageobject);
                 spatiallistimageobject = new SpatialListImageObject("Front View", isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/geotest.jpg"));
                 vector.add(spatiallistimageobject);
                 spatiallistimageobject = new SpatialListImageObject("Back View", isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/geotest.jpg"));
                 vector.add(spatiallistimageobject);
                 spatiallistimageobject = new SpatialListImageObject("Left View", isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/geotest.jpg"));
         vector.add(spatiallistimageobject);
         spatialsearchpanel.setSpatialData("hai");
         jframe.getContentPane().add(spatialsearchpanel);
         jframe.setVisible(true);
     }
 */
/*
    public void setResponse(String response)throws SAXException,IOException{
        SpatialXmlConverter spatialxmlconverter = new SpatialXmlConverter(getSpatialData());
                    SAXParser saxparser = new SAXParser();
                    saxparser.setContentHandler();
                    InputSource inputsource = new InputSource(new StringReader(response));
                    saxparser.parse(inputsource);
                    String err = spatialxmlconverter.getErrMsg();
                    if(!"".equalsIgnoreCase(err)){
                        if(err.indexOf("ConnectException")>-1)
                            err="Connection to the server is lost. Please reconnect";
                        else
                            err="Unknown error occured. Please contact Administrator";
                        setCursor(new Cursor(0));
                        JOptionPane.showMessageDialog(this, err, "Error Message", 0);
                        return;
                    }

                    Vector vector4 = (Vector) spatialxmlconverter.getDataStructure();
                    if (vector4.size() == 0) {
                        setCursor(new Cursor(0));
                        JOptionPane.showMessageDialog(this, " No records are available for spatial search", "Error Message", 0);
                        return;
                    }
    }*/

    public void fireClose() {
        // if (imp.glass.isShowing()) {
        //   imp.glass.setVisible(false);
        if (imp.imap.isShowing()) {
            imp.imap.removeAll();
            GlassPaneManager.getInstance().showComponent(imp.imap);

        }//todo change to glass
    }

    public void popButton(Point p) {
        imp.imap.setAction(p);
        //  imp.glass.setVisible(true);      //todo  toglass
        GlassPaneManager.getInstance().showComponent(imp.imap);


    }


}
