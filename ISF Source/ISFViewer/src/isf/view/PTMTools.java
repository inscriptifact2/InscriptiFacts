package isf.view;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.controls.ISFButton;
import isf.viewer.containers.EffectsPopUp;
import isf.viewer.containers.PerformancePopUp;
import isf.viewer.containers.SourcesPopUp;
import isf.viewer.controls.ControlsButton;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 24, 2009
 * Time: 8:46:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PTMTools {

    public static javax.swing.JButton[] getPTMButtons() {

        return _initPTMButtons();


    }

    private static javax.swing.JButton[] _initPTMButtons() {
        ISFButton[] tools = new ISFButton[5];

        ControlsButton btnControl, btnEffects, btnSource;
        ISFButton btnLight, btnCopy;
        ControlsPopUp cpu = new ControlsPopUp();
        EffectsPopUp epu = new EffectsPopUp();
        SourcesPopUp spu = new SourcesPopUp();
        PerformancePopUp pup = new PerformancePopUp();

        btnLight = new ISFButton("Light", "images/flight.gif", "images/flight_d.gif");
        btnEffects = new ControlsButton("Effects", "images/effects.gif", "images/effects_d.gif", epu);
        btnControl = new ControlsButton("Controls", "images/cont.gif", "images/cont_d.gif", cpu, true);
        btnCopy = new ISFButton("Copy RTI", "images/copy.gif", "images/copy_d.gif");
        btnSource = new ControlsButton("Options", "images/light.gif", "images/light_d.gif", spu) ;
        btnLight.setToolTipText("Light  (Alt+L)");
        btnCopy.setToolTipText("Copy  (Alt+Y)");
        btnSource.setToolTipText("Light Options  (Alt+G)");

        tools[0] = btnLight;
        tools[1] = btnSource;
        tools[2] = btnEffects;
        tools[3] = btnCopy;
        tools[4] = btnControl;
        
        PTMFrameManager.getInstance().registerButton(btnControl);

        return tools;
    }


}
