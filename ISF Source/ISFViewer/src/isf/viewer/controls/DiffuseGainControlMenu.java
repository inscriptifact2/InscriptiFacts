/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.viewer.controls;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.containers.ControlMenu;
import isf.ptm.controls.PTMWindow;
import isf.viewer.utils.SpringUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

/**
 * @author IN030223
 */
public class DiffuseGainControlMenu extends ControlMenu {
     float min = 1.0f;
    float max = 10.0f;
    float gain = 2.0f;
   ISFSliderControl dGainSlider = new ISFSliderControl(0, 100,min,max);
   // ISFSlider dGainSlider = new ISFSlider(0, 100);
   

    JLabel label = new JLabel("<html>dGain</html>");

    PTMWindow pf;

    public DiffuseGainControlMenu() {
        super();
        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));
        this.setLayout(new SpringLayout());
        this.add(label);
        label.setBackground(new Color(0xd9d9d9));
        label.setForeground(new Color(0xAC5539));

        dGainSlider.setBackground(new Color(0xd9d9d9));
        dGainSlider.setForeground(new Color(0xAC5539));
        this.add(dGainSlider);
        SpringUtilities.makeCompactGrid(this, 1, 2, // rows, cols
                1, 1, // initX, initY
                1, 1); // xPad, yPad
        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory
                .createEtchedBorder(EtchedBorder.LOWERED), "Diffuse Gain");
        b.setTitleJustification(TitledBorder.LEFT);
        this.setBorder(b);

    }

    public void init() {

//1 +100 *dgain * dgain*dgain*dgain;
        pf = PTMFrameManager.getInstance().getPTMWindow();

        float dg = pf.getPTM().getDGain();
       // dGainSlider.setValue((int) Math.round(Math.pow((dg - 1) / 100, 1 / 4) * 10));
        dGainSlider.setFValue(dg);

        //dGainSlider.setValue((int)(Math.sqrt(Math.sqrt());
        dGainSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                   // double val = ((double) source.getValue() / 10);
                   // pf.getPTM().setDGain((float) (1 + 100 * Math.pow(val, 4)));
                      float val = ((ISFSliderControl)source).getFValue() ;
                    pf.getPTM().setDGain(val);
                    pf.fireTransform();
                }
            }
        });
		PTMFrameManager.getInstance().registerChangeListener(this,pf);
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
        return new Dimension(240, 80);
    }

    public Dimension getMinimumSize() {
        return new Dimension(230, 70);
    }

    public Dimension getMaximumSize() {
        return new Dimension(250, 90);
    }
	
	public void resetControls()
	{
			pf =  PTMFrameManager.getInstance().getPTMWindow();
                        if(pf!=null && pf.getPTM()!=null){
			float dg = pf.getPTM().getDGain();
			dGainSlider.setFValue(dg);
                        }
	}

}