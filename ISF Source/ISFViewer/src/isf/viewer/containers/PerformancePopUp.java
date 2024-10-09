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
import isf.ptm.operations.Operation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * @author IN030223
 */
public class PerformancePopUp extends ButtonPopUp implements ActionListener {
    private PTMWindow ptmw = null;
    JButton btnDetail, btnSample, btnInterpolated;

    public PerformancePopUp() {
        this.setLayout(new GridLayout(3, 1));
        btnDetail = new ISFButton("Detail");
        btnDetail.addActionListener(this);
        btnSample = new ISFButton("Sampling");
        btnSample.addActionListener(this);
        btnInterpolated = new ISFButton("Interpolated Sampling");
        btnInterpolated.addActionListener(this);

        add(btnDetail);
        add(btnSample);
        add(btnInterpolated);

    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        ptmw = PTMFrameManager.getInstance().getPTMWindow();
        Operation pixelTransformOp = ((PTMPanel) ptmw).getPixelTransformOp();
        if (btn.getActionCommand().equalsIgnoreCase("Detail")) {
            ((PTMPanel) ptmw).detail();
            pixelTransformOp.clearCache();

        } else if (btn.getActionCommand().equalsIgnoreCase("Sampling")) {
            ((PTMPanel) ptmw).speed();
            pixelTransformOp.clearCache();
        } else if (btn.getActionCommand().equalsIgnoreCase("Interpolated Sampling")) {
            ((PTMPanel) ptmw).speed();
            ((PTMPanel) ptmw).useHint(true);

            pixelTransformOp.clearCache();
        }
        if (GlassPaneManager.getInstance().isShowing())
            GlassPaneManager.getInstance().hideAll();


    }

    public void init() {

    }


    public Dimension getPreferredSize() {
        return new Dimension(125, 100);
    }

    public void releaseControls() {
    }

}
