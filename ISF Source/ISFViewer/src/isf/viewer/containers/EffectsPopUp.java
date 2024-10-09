/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.viewer.containers;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.containers.PTMPanel;
import isf.controls.view.controls.ISFButton;
import isf.ptm.controls.PTMWindow;
import isf.ptm.operations.DiffuseGainOperation;
import isf.ptm.operations.DirectionalLightOperation;
import isf.ptm.operations.NormalMapOperation;
import isf.ptm.operations.SpecularOperation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * @author IN030223
 */
public class EffectsPopUp extends ButtonPopUp implements ActionListener {
    private PTMWindow ptmw = null;
    JButton btnNone, btnSpecular, btnDiffuseGain, btnExtrapolate, btnNormalize, btnAnimate;

    public EffectsPopUp() {
        this.setLayout(new GridLayout(6, 1));
        btnNone = new ISFButton("No Effects");
        btnNone.addActionListener(this);
        btnAnimate = new ISFButton("Start Animation");
        btnAnimate.addActionListener(this);

        btnExtrapolate = new ISFButton("Extrapolate Source");
        btnExtrapolate.addActionListener(this);
        btnSpecular = new ISFButton("Specular");
        btnSpecular.addActionListener(this);
        btnDiffuseGain = new ISFButton("Diffuse Gain");
        btnDiffuseGain.addActionListener(this);
        btnNormalize = new ISFButton("Visualize Normals");
        btnNormalize.addActionListener(this);
        btnAnimate.setEnabled(false);
        add(btnNone);
        add(btnAnimate);
        add(btnExtrapolate);
        add(btnSpecular);
        add(btnDiffuseGain);
        add(btnNormalize);
        // addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {

        ptmw = PTMFrameManager.getInstance().getPTMWindow();

        boolean repaint = true;
        JButton btn = (JButton) e.getSource();
        ptmw.setExtrapolation(false);
        // ptmw = PTMFrameManager.getInstance().getPTMWindow();
        if (btn.getActionCommand().equalsIgnoreCase("Specular")) {
            ptmw.setPixelTransformOp(new SpecularOperation());


        } else if (btn.getActionCommand().equalsIgnoreCase("Diffuse Gain")) {
            ptmw.setPixelTransformOp(new DiffuseGainOperation());

        } else if (btn.getActionCommand().equalsIgnoreCase("No Effects")) {

            PTMPanel panel = (PTMPanel) ptmw;
            if (panel.isAnimated()) {
                // repaint = false;
                panel.setAnimated(false);
            }
            ptmw.setPixelTransformOp(new DirectionalLightOperation());

        } else if (btn.getActionCommand().equalsIgnoreCase("Visualize Normals")) {
            // ptmw.getPTM().computeNormals();
            ptmw.setPixelTransformOp(new NormalMapOperation());
        } else if (btn.getActionCommand().equalsIgnoreCase("Start Animation")) {
            repaint = false;
            PTMPanel panel = (PTMPanel) ptmw;
            panel.setAnimated(!panel.isAnimated());
        } else if (btn.getActionCommand().equalsIgnoreCase("Extrapolate Source")) {
            //ptmw.setPixelTransformOp(new ExtrapolateSourceOperation()) ;
            ptmw.setExtrapolation(true);

        }

        if (GlassPaneManager.getInstance().isShowing())
            GlassPaneManager.getInstance().hideAll();
        if (repaint) {
            ((PTMPanel) ptmw).fireTransform();
          //  System.out.println("fired ptm transform");
        }


    }

    public void init() {

        ptmw = PTMFrameManager.getInstance().getPTMWindow();
        PTMPanel panel = (PTMPanel) ptmw;
        boolean animated = panel.isAnimated();
        if (ptmw.isExtrapolation())
            btnExtrapolate.setEnabled(!animated);
        else
            btnExtrapolate.setEnabled(false);
        btnNormalize.setEnabled(!animated);
        btnSpecular.setEnabled(!animated);
        if(PTMFrameManager.getInstance().getDiffuseOperation())
        {
            btnDiffuseGain.setEnabled(!animated);
        } else
        {
           btnDiffuseGain.setEnabled(false); 
        }
       if(panel.isAdditionalLight())
           btnDiffuseGain.setEnabled(false); 
       
        if (animated)
            btnAnimate.setText("Stop Animation");
        else
            btnAnimate.setText("Start Animation");

    }


    public Dimension getPreferredSize() {
        return new Dimension(125, 150);
    }

   
    public void releaseControls() {
       
    }

}
