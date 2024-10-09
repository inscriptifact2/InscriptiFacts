/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.controls;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.containers.ControlMenu;
import isf.controls.view.containers.PTMPanelImpl;
import isf.controls.view.controls.ISFSlider;
import isf.ptm.formats.MVRTI;
import isf.viewer.utils.SpringUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

/**
 * @author IN030223
 */
public class MViewControlMenu extends ControlMenu {

   boolean added = false;
    private ISFSlider viewSlider = new ISFSlider(0, 40);
    private JLabel label = new JLabel("<html>Rotate View</html>");
    
    private PTMPanelImpl ptmw;

    

    public MViewControlMenu() {

        super();
        this.setLayout(new SpringLayout());


        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));


        label.setBackground(new Color(0xd9d9d9));
        label.setForeground(new Color(0xAC5539));
       

        viewSlider.setBackground(new Color(0xd9d9d9));
        viewSlider.setForeground(new Color(0xAC5539));
        viewSlider.setMajorTickSpacing(1);
        viewSlider.setMinorTickSpacing(0);
        viewSlider.setPaintLabels(true);
        viewSlider.setPaintTicks(true);
       // viewSlider.setSnapToTicks(true);



        this.add(label);
        this.add(viewSlider);
      


        SpringUtilities.makeCompactGrid(this, 1,2, // rows, cols
                1, 1, // initX, initY
                1, 1); // xPad, yPad

        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "MultiView");
        b.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(b);

    }

    public void init() {
       
        ptmw = (PTMPanelImpl) PTMFrameManager.getInstance().getPTMWindow();
       
        if (ptmw != null && ptmw.getPTM() instanceof isf.ptm.formats.MVRTI) {
            final MVRTI rti = (MVRTI)ptmw.getPTM();
            viewSlider.setMaximum( rti.getViews() );
            viewSlider.setMinimum(1);
            Dictionary labels = new Hashtable() ;
            int views = rti.getViews();
            int times = views / 6;

            for(int x = 0 ; x <= rti.getViews(); x+=times )
                 labels.put(new Integer(x),new JLabel(x+""));
             viewSlider.setLabelTable(labels);
            
            viewSlider.setValue(rti.getCurrentView());
          
            if (!added) {
                viewSlider.addChangeListener(new ChangeListener() {

                    public void stateChanged(javax.swing.event.ChangeEvent e) {
                       
                        JSlider source = (JSlider) e.getSource();
                        if (!source.getValueIsAdjusting()) {
                            int val = ( source.getValue());
                           // rti.getPTM(val);
                            ptmw.loadMViewFile(val);
                            ptmw.fireTransform();
                        } else {
                        }
                    }
                });

               
                added = true;
            }


        }
        PTMFrameManager.getInstance().registerChangeListener(this, ptmw);
    }

    public void processMouseEvent(MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }

    public void processKeyEvent(KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }

    public void menuSelectionChanged(boolean isIncluded) {
    }

    public MenuElement[] getSubElements() {
        return new MenuElement[0];
    }

    public Component getComponent() {
        return this;
    }

    public Dimension getPreferredSize() {
        return new Dimension(240, 120);
    }

    public Dimension getMinimumSize() {
        return new Dimension(230, 110);
    }

    public Dimension getMaximumSize() {
        return new Dimension(250, 130);
    }

    public void resetControls() {
        ptmw = (PTMPanelImpl) PTMFrameManager.getInstance().getPTMWindow();
        if (ptmw != null && ptmw.getPTM() instanceof isf.ptm.formats.MVRTI) {
            final MVRTI rti = (MVRTI)ptmw.getPTM();
            viewSlider.setValue(rti.getCurrentView());
           
        }
    }
}