/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.viewer.controls;


import isf.common.utils.LogManager;
import isf.controls.controllers.PTMFrameManager;
import isf.controls.view.containers.ControlMenu;
import isf.ptm.controls.EnvironmentMapPanel;
import isf.ptm.controls.PTMWindow;
import isf.ptm.graphics.EnvironmentMap;
import isf.view.ControlsPopUp;
import isf.viewer.utils.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Logger;

/**
 * @author IN030223
 */
public class EnvironmentControlMenu extends ControlMenu {
    public static Object mutex = new short[0];

    JPanel top = new JPanel();

    JPanel bottom = new JPanel();

    JSlider group = new JSlider();

    JSlider sampleSlider = new JSlider();

    JSlider kernelSlider = new JSlider();

    JSlider sigmaSlider = new JSlider();

    JLabel sampleLabel = new JLabel("sample");

    JLabel kernelLabel = new JLabel("kernel");

    JLabel sigmaLabel = new JLabel("sigma");

    String[] mapStrings = {"none", "galileo", "grace", "cafe", "stpeters",
                           "uffizi", "rnl"};

    JComboBox mapList = new JComboBox(mapStrings);

    PTMWindow ptmw;
    Logger log = LogManager.getLogger(isf.viewer.controls.EnvironmentControlMenu.class);

    EnvironmentMapPanel emp = new EnvironmentMapPanel();

    {

        group.setMinimum(2);
        group.setMaximum(32);
        group.setValue(EnvironmentMap.DEFAULT_KERNEL_SIZE);
        sampleSlider.setMinimum(1);
        sampleSlider.setValue(2);
        // sampleSlider.setInverted(true);

        kernelSlider.setMinimum(1);
        kernelSlider.setMaximum(32);
        // kernelSlider.setInverted(true);
        kernelSlider.setValue(5);

        sigmaSlider.setMinimum(1);
        sigmaSlider.setMaximum(10);
        sigmaSlider.setMajorTickSpacing(1);
        // sigmaSlider.setInverted(true);
        sigmaSlider.setValue(1);
    };

    private ControlsPopUp floatingControlsPopUp = null;


    public void setFC(ControlsPopUp f) {
        floatingControlsPopUp = f;
        ptmw = PTMFrameManager.getInstance().getPTMWindow();
    }

    public EnvironmentControlMenu() {
        super();
        this.top.setLayout(new SpringLayout());
        this.bottom.setLayout(new SpringLayout());

        this.top.add(new JLabel("reflectance"));
        this.top.add(group);

        // this.top.add(sampleLabel);
        // this.top.add(sampleSlider);
        //
        // this.top.add(kernelLabel);
        // this.top.add(kernelSlider);
        //
        // this.top.add(sigmaLabel);
        // this.top.add(sigmaSlider);

        this.bottom.add(mapList);
        this.bottom.add(emp);

        mapList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JComboBox source = (JComboBox) ae.getSource();
                String selected = (String) source.getSelectedItem();
                // //System.out.println(selected);
                String name = selected + "_probe.jpg";
                String path = "jpview/imagefiles/";
                BufferedImage bi = null;
                try {
                    Class me = EnvironmentMapPanel.class;
                    ClassLoader cl = me.getClassLoader();
                    URL url = cl.getResource(path + name);
                    if (url != null)
                        bi = readUnbuffered(url.openStream()); // ImageUtils.
                } catch (Exception e) {
                    log.error(e);
                }

                synchronized (mutex) {

                    if (bi != null) {

                        if (emp.getEnvironmentMap() == null) {
                            if (ptmw != null) {
                                emp.setEnvironmentMap(new EnvironmentMap(bi,
                                        ptmw.getPTM()));
                                ptmw.getPTM().useEnv(true);
                            } else {
                                emp.setEnvironmentMap(new EnvironmentMap(bi,
                                        null));
                            }
                        } else {

                            emp.getEnvironmentMap().setImage(bi);
                            if (ptmw != null) {
                                ptmw.getPTM().useEnv(true);
                            }

                        }
                    } else {

                        if (emp.getEnvironmentMap() != null)
                        // emp.unsetEnvironmentMap();
                            emp.getEnvironmentMap().setImage(null);
                        ptmw.getPTM().useEnv(false);
                        //ptmw.setLuminance(1f);
                        //floatingControlsPopUp.setLuminanceSliderValue(25);
                    }
                    emp.redraw();
                    emp.repaint();
                    //emp.getCanvas().repaint();
                    if (ptmw != null) {
                        ptmw.refreshMap();
                        ptmw.fireTransform();
                    }
                }

            }


        });

        SpringUtilities.makeCompactGrid(this.top, 1, 2, // rows, cols
                6, 6, // initX, initY
                6, 6); // xPad, yPad

        SpringUtilities.makeCompactGrid(this.bottom, 2, 1, // rows, cols
                6, 6, // initX, initY
                10, 10); // xPad, yPad

        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory
                .createEtchedBorder(EtchedBorder.LOWERED), "Environment");
        b.setTitleJustification(TitledBorder.LEFT);

        this.setLayout(new BorderLayout());
        this.add(top, BorderLayout.NORTH);
        this.add(bottom, BorderLayout.SOUTH);
        this.setBorder(b);
    }

    public void init() {

        final PTMWindow pf = ptmw;

        group.addChangeListener(new ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()
                        && (pf.getPTM().getEnvironmentMap() != null)) {
                    int val = (int) source.getValue();
                    // //System.out.println("Read val: " + val);
                    synchronized (mutex) {
                        // sigmaSlider.setValue(Math.round(((float)val)/4));
                        // kernelSlider.setValue(val);
                        pf.setMapKernelSize(val);
                        pf.setMapGuassianBlurSigma(((float) val) / 4);
                        pf.refreshMap();
                        pf.fireTransform();
                    }
                } else {
                    // pf.getPTMCanvas().repaint();
                }
            }
        });


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
        return new Dimension(190, 180);
    }

    public Dimension getMinimumSize() {
        return new Dimension(180, 160);
    }

    public Dimension getMaximumSize() {
        return new Dimension(200, 200);
    }
    
     private BufferedImage readUnbuffered(InputStream is)            throws java.io.IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = is.read()) != -1) {
            baos.write(b);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return ImageIO.read(bais);
    }

}