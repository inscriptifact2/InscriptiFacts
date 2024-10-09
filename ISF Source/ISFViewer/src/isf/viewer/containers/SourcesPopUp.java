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
import isf.ptm.operations.Operation;
import isf.ptm.operations.SpotLightOperation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * @author IN030223
 */
public class SourcesPopUp extends ButtonPopUp implements ActionListener {

    private PTMWindow ptmw = null;
    JButton btnDirectional, btnLocal, btnSpot, btnAddLight;

    public SourcesPopUp() {
        this.setLayout(new GridLayout(4, 1));
        btnDirectional = new ISFButton("Directional");
        btnDirectional.addActionListener(this);
        btnLocal = new ISFButton("Local Light");
        btnLocal.addActionListener(this);
        btnSpot = new ISFButton("Spot Light");
        btnSpot.addActionListener(this);
        btnAddLight = new ISFButton("Dual Lights");
        btnAddLight.setActionCommand("lights");
        btnAddLight.addActionListener(this);

        add(btnDirectional);
        add(btnLocal);
        add(btnSpot);
        add(btnAddLight);

    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        boolean canShow = true;
        ptmw = PTMFrameManager.getInstance().getPTMWindow();
        if (btn.getActionCommand().equalsIgnoreCase("Directional")) {
            ptmw.setPixelTransformOp(new DirectionalLightOperation());
            ((PTMPanel) ptmw).toggleLight(false);
            canShow = false;
           // btnAddLight.setEnabled(false);


        } else if (btn.getActionCommand().equalsIgnoreCase("Local Light")) {
            ptmw.setPixelTransformOp(new SpotLightOperation());
            ((PTMPanel) ptmw).toggleLight(false);
            btnAddLight.setEnabled(false);
        } else if (btn.getActionCommand().equalsIgnoreCase("Spot Light")) {
            SpotLightOperation op = new SpotLightOperation();
            op.setFlashlight(true);
            ptmw.setPixelTransformOp(op);
            ((PTMPanel) ptmw).toggleLight(false);
            btnAddLight.setEnabled(false);

        } else if (btn.getActionCommand().equalsIgnoreCase("lights")) {
            if(((PTMPanel) ptmw).isAdditionalLight())
                ((PTMPanel) ptmw).toggleLight(false);
            else
                ((PTMPanel) ptmw).toggleLight(true);
           /* String str = btnAddLight.getText();
            if(str.equalsIgnoreCase("Dual Lights"))
                 btnAddLight.setText("Single Light");
            else
                btnAddLight.setText("Dual Lights");*/

        }
        if (GlassPaneManager.getInstance().isShowing()) {
            GlassPaneManager.getInstance().hideAll();
        }
        ptmw.fireTransform();
        if(canShow)
        PTMFrameManager.getInstance().showControls();
    }

    public void init() {

        ptmw = PTMFrameManager.getInstance().getPTMWindow();
        Operation op = ((PTMPanel) ptmw).getPixelTransformOp();
        if (op instanceof SpotLightOperation || op instanceof DiffuseGainOperation) {
            btnAddLight.setEnabled(false);
        } else {
            btnAddLight.setEnabled(true);
        }
		
		
		if(!PTMFrameManager.getInstance().getLightOperation())
		{
			btnLocal.setEnabled(false);
			btnSpot.setEnabled(false);
		}else
        {
            btnLocal.setEnabled(true);
			btnSpot.setEnabled(true);
        }

    }


    public Dimension getPreferredSize() {
        return new Dimension(125, 100);
    }


    public void releaseControls() {
    }
}
