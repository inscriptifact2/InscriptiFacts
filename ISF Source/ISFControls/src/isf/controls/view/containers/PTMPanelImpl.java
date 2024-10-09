package isf.controls.view.containers;

import isf.controls.models.DesktopDataObject;
import isf.common.utils.InscriptConfigLoader;
//import isf.controls.utils.LogManager;
import isf.common.utils.PTMConfig;
import isf.controls.view.controls.SidDesktop;
import isf.ptm.fileio.PTMIO;
import isf.ptm.formats.BLRGBPTM;
import isf.ptm.formats.HSH;
import isf.ptm.formats.MVRTI;
import isf.ptm.formats.PTM;
import isf.ptm.formats.RGBPTM;
import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.operations.DirectionalLightOperation;
import isf.ptm.operations.Operation;
import isf.ptm.utils.LightSource;
import isf.ptm.utils.SwingWorker;
import isf.ptm.utils.Utils;
import isf.cache.utils.ImageUtils;
import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
//import isf.net.request.PTMRequestDispatcher;
import isf.common.utils.SegmentUtils;
import isf.common.utils.ThreadExecutor;
import isf.controls.controllers.LoadManager;
import isf.controls.controllers.PTMFrameManager;
import isf.controls.controllers.ToolbarManager;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.vecmath.Matrix3f;
import javax.vecmath.Point3f;
import org.apache.log4j.Logger;

public class PTMPanelImpl extends PTMPanel implements
        MouseListener, MouseMotionListener, ComponentListener, Runnable { // Runnable

    static {
        ImageIO.scanForPlugins();
    }
     private Logger log = LogManager.getLogger(isf.controls.view.containers.PTMPanelImpl.class);
    private boolean animated = false, frameActive = false;
    private boolean loading = false;
    private boolean paintPosition = false;
    private Thread animator = null;
    private int mouseX = 0;
    private int mouseY = 0;
    private final short[] mutex = new short[0];
    private Operation pixelTransformOp = null;
    private SegmentUtils[] segments;
    private int activeLight = 0;
    private PTM ptm = null;
    int curWid, curHei;
    boolean dragging = false;
    private String fName, thumbname;
    private long size;
    protected double zoom = 1.0;
    //ptmcanvas variables
    protected BufferedImage actImg;
    int divx = 0, divy = 0;
    protected float ratio = 0;
    protected int[] pixels;
    private int[] tiles;
    private int peices;
    protected int displayWidth, displayHeight;
    protected int rows, cols, segSize;
    protected boolean hints = false;
    protected boolean isFast = false;
    protected boolean fixed = false;
    int width = 0, height = 0, posx = 0, posy = 0;
    boolean contained = true;
    boolean atMaxLevel = false, shown = false, drawRect = true;
    int obtained = 0;
    public boolean calculated = false, copied = false;
    StatusPanel status;
    int newPX, newPY, newMX, newMY, newDW, newDH;
    float factor;
    final int MAXPIXELS = 1280 * 1280;
    private boolean thumbnailCached = false;
    private float dxLight = 0.0f, dyLight = 0.0f;
    int clickXImageCoordinate = 0, clickYImageCoordinate = 0;
    PTMConfig config = null;
    int times = 0;
    boolean isRTI = false;

    public PTMPanelImpl() {
        this.setBackground(new Color(0, 0, 0));
        //    mode = SidDesktop.PTM;
        pixelTransformOp = new DirectionalLightOperation();
        addMouseMotionListener(this);
        addMouseListener(this);
        lights[0] = new LightSource();
        lights[1] = new LightSource();

        loading = true;

    }

    public void fireTransform() {
        ////System.out.println("Method:fireTransform()");

        Rectangle r = getBounds();

        if (curWid > width) {
            LightSource[] newLS = new LightSource[2];

            for (int i = 0; i < 2; i++) {
                newLS[i] = new LightSource();
                newLS[i].setActive(lights[i].isActive());
                newLS[i].setEnabled(lights[i].isEnabled());
                newLS[i].setLuminance(lights[i].getLuminance());
                newLS[i].setX((int) (lights[i].getX() * factor));  //* factor
                newLS[i].setY((int) (lights[i].getY() * factor)); //* factor

                if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {

                    Point3f light = lights[i].getLight();
                    //light.scale(factor);
                    newLS[i].setLight(light);

                }
            }
            // synchronized (mutex) {
            pixelTransformOp.performOperation(pixels, newLS, ptm,
                    newMX, newMY, newDW, newDH, newPX, newPY, width, height);
            paintImmediately(0, 0, r.width, r.height);
            // }

        } else {
            //  synchronized (mutex) {
            pixelTransformOp.performOperation(pixels, lights, ptm,
                    mouseX, mouseY, r.width, r.height, posx, posy, curWid, curHei);
            paintImmediately(0, 0, r.width, r.height);
            // }
        }

    }
    /*
     private void fireTransform(boolean nolock) {

     if (nolock) {

     Rectangle r = getBounds();
     pixelTransformOp.performOperation(pixels, lights, ptm,
     mouseX, mouseY, r.width, r.height, posx, posy, curWid, curHei);
     paintImmediately(0, 0, r.width, r.height);
     } else {
     fireTransform();
     }
     }
     */

    public String getCurrentLevel() {
        return " Zoom is at " + ((int) ((curWid * 100) / width)) + " %";
    }

    public void forceUpdate() {
        ////System.out.println("Method:forceUpdate()");
        pixelTransformOp.forceUpdate();
    }

    public int getExp() {
        if (ptm != null) {
            return ptm.getExp();
        } else {
            return 1;
        }
    }

    public float getKDiff() {
        if (ptm != null) {
            return ptm.getKDiff();
        } else {
            return 1.0f;
        }
    }

    public float getKSpec() {
        if (ptm != null) {
            return ptm.getKSpec();
        } else {
            return 1.0f;
        }
    }

    public void setScale(float scale) {
        ptm.setScale(scale);
    }

    public float getScale() {
        if (ptm != null) {
            return ptm.getScale();
        } else {
            return 1.0f;
        }
    }

    public float getLuminance(int pos) {
        if (lights[pos] != null) {
            return lights[pos].getLuminance();
        } else {
            return 2;
        }
    }

    public float getLuminance() {
        return lights[activeLight].getLuminance();
    }

    public int getActiveLight() {
        return activeLight;
    }

    public void setActiveLight(int pos) {
        activeLight = pos;
        for (int i = 0; i < 2; i++) {
            lights[i].setActive(false);
        }
        lights[pos].setActive(true);
        mouseX = lights[pos].getX();
        mouseY = lights[pos].getY();

    }

    public boolean isAdditionalLight() {
        boolean flag = false;
        if (lights[1] != null && lights[1].isEnabled()) {
            flag = true;
        }
        return flag;
    }

    public PTM getPTM() {

        return ptm;
    }

    public int getPTMHeight() {
        return height;
    }

    public int getPTMWidth() {
        return width;
    }
    boolean firstView = false;
    boolean multiView = false;

    private void loadLocalFile(String path) {
        final String ptmFile = path;
        
        long sfile = new File(ptmFile).length();
        if(sfile<=0){
            return;
        }
        try {

            if (status == null) {
                status = ((ViewerPanel) getParent()).getStatus();
            }
            status.showStatus("Waiting for resources. Please wait...");
            status.start();

            firstView = false;
            SwingWorker worker = new SwingWorker() {

                long start = System.currentTimeMillis();

                public Object construct() {
                    try {
                        while (!SidDesktop.canLoad()) {
                            Thread.sleep(500);
                        }

                        status.showStatus("Loading RTI Image...");

                        fName = ptmFile.substring(ptmFile.lastIndexOf(File.separatorChar) + 1);
                        if (fName.indexOf(".") > -1) {
                            fName = fName.substring(0, fName.indexOf("."));
                        }
                        thumbname = fName + "thumb.jpg";
                        FileInputStream fis = new FileInputStream(ptmFile);
                        size = new File(ptmFile).length();

                        if (ptmFile.endsWith(".ptm")) {
                            ptm = PTMIO.getPTMParser(fis, false).readPTM();
                        } else if (ptmFile.endsWith(".rti")) {
                            ptm = PTMIO.getPTMParser(fis, true).readPTM();

                        } else if (ptmFile.endsWith(".mview")) {
                            ptm = PTMIO.getMView(fis);
                            ((MVRTI) ptm).setDirectory(ptmFile);
                            ((MVRTI) ptm).getPTM(1);

                            multiView = true;

                        }
                        width = ptm.getWidth();
                        height = ptm.getHeight();
                        setDefaultLight();
                    } catch (Exception exp) {
                        log.error(exp);
                    }
                    zoomToFitDisplay();
                    ratio = ((float) width) / height;
                    //  maxWidth = (int) (width * 1.3);
                    // maxHeight = (int) (height * 1.3);
                    lights[0].setActive(true);
                    lights[0].setEnabled(true);
                    // lights[0].setX(mouseX);
                    // lights[0].setY(mouseY);
                    ptm.setName(fName);

                    loading = false;
                    LoadManager.getInstance().updateCount();
                    LoadManager.getInstance().update(size);
                    fireTransform();

                    return null;
                }

                @Override
                public void finished() {
                    status.showStatus("Finalizing load ...");

                    Runnable r = new Runnable() {

                        public void run() {
                            ptm.computeNormals();
                            status.showStatus("Ready ...");
                            status.stop();
                            saveThumbnail();
                            ToolbarManager.getInstance().updateToolbar();
                            SidDesktop.stopLoad();
                            if (multiView) {
                                firstView = true;
                            }
                            //System.out.println("Loaded and Computed");
                        }
                    };
                    SwingUtilities.invokeLater(r);

                    //System.out.println("FileLoaded in :" + (System.currentTimeMillis() - start));
                }
            };
            worker.start();

        } catch (Exception exp) {
            //System.out.println(exp);
            log.error(exp);
        }
    }

    public void loadMViewFile(int x) {

        try {
            final int index = x;
            SwingWorker worker = new SwingWorker() {

                long start = System.currentTimeMillis();

                public Object construct() {
                    try {

                        synchronized (mutex) {

                            String ptmFile = ((MVRTI) ptm).getFileName(index);

                            fName = ptmFile.substring(ptmFile.lastIndexOf(File.separatorChar) + 1);
                            LoadManager.getInstance().startLoading("Loading " + fName + "...");
                            if (fName.indexOf(".") > -1) {
                                fName = fName.substring(0, fName.indexOf("."));
                            }
                            thumbname = fName + "thumb.jpg";

                            thumbnailCached = false;
                            loading = true;
                            size = new File(ptmFile).length();

                            ((MVRTI) ptm).getPTM(index);

                        }

                    } catch (Exception exp) {
                        log.error(exp);
                    }

                    fireTransform();

                    return null;
                }

                @Override
                public void finished() {
                    //     status.showStatus("Finalizing load ...");

                    Runnable r = new Runnable() {

                        public void run() {

                            //System.out.println("Here two:" + thumbname);
                            loading = false;
                            saveThumbnail();
                            // PTMFrameManager.getInstance().refreshControls();
                            LoadManager.getInstance().stopLoading();

                        }
                    };
                    SwingUtilities.invokeLater(r);

                }
            };
            worker.start();

        } catch (Exception exp) {
            //System.out.println(exp);
            log.error(exp);
        }

    }

    private void loadFile(DesktopDataObject ddo) {
        final String ptmFile = ddo.getFileName();
        loadLocalFile(ptmFile);
    }

    private void loadFile(PTMConfig ptmCon) {
        //System.out.println("Started Loading File:" + ptmCon.getName());
        // long start = System.currentTimeMillis();
        final String ptmFile = ptmCon.getName();
        if (status == null) {
            status = ((ViewerPanel) getParent()).getStatus();
        }
        status.showStatus("Waiting for resources. Please wait...");
        status.start();

        offImg = null;
        offScreen = null;
        config = ptmCon;
        loading = true;
        width = config.getWidth();
        height = config.getHeight();
        times = 0;
        isRTI = false;

        if (config.getType() == PTM.TYPE_RAW_PTM || config.getType() == PTM.TYPE_RTI_PTM) {

            if (config.getBasistype() == PTM.BASIS_LRGB) {
                ptm = new BLRGBPTM();
                ((BLRGBPTM) ptm).setCoefficients(new byte[9][width * height]);
                ptm.setBasisType(PTM.BASIS_LRGB);
                times = 3;
            } else if (config.getBasistype() == PTM.BASIS_RGB) {
                ptm = new RGBPTM();
                ((RGBPTM) ptm).setBCoefficients(new byte[width * height][18]);
                ptm.setBasisType(PTM.BASIS_RGB);
                times = 6;
            }
        } else if (config.getType() == PTM.TYPE_RTI_HSH) {
            ptm = new HSH();
            isRTI = true;
            int totalTerms = 0;
            int basisTerms = config.getTerms();
            int colors = config.getColors();

            ((HSH) ptm).setBasisTerms(basisTerms);
            ((HSH) ptm).setColors(colors);
            ((HSH) ptm).setFileType(PTM.TYPE_RTI_HSH);
            ((HSH) ptm).setBasisType(config.getBasistype());
            ((HSH) ptm).setColors(colors);

            switch (config.getBasistype()) {
                case PTM.BASIS_LRGB:
                    totalTerms = basisTerms + colors;
                    break;
                case PTM.BASIS_RGB:
                    totalTerms = basisTerms * colors;
                    break;
            }

            ((HSH) ptm).setBCoefficients(new byte[width * height][totalTerms]);
            times = totalTerms / 3;

        }
        setDefaultLight();
        rows = config.getRows();
        cols = config.getColumns();
        peices = rows * cols;
        segments = new SegmentUtils[peices * times];
        tiles = new int[peices * times];

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = -1;
            //// segments[i] = new SegmentUtils();
        }

        ptm.setWidth(width);
        ptm.setHeight(height);
        ptm.setName(config.getName());
        ptm.setVersion(config.getVersion());
        ptm.setScaleArray(config.getScale());
        fName = config.getName();
        thumbname = fName + "thumb.jpg";
        size = config.getSize();
        ptm.setBias(config.getBias());
        segSize = config.getSegmentSize();

        zoomToFitDisplay();
        ratio = ((float) width) / height;
        //    maxWidth = (int) (width * 1.3);

        //   maxHeight = (int) (height * 1.3);
        lights[0].setActive(true);
        lights[0].setEnabled(true);
        // lights[0].setX(mouseX);
        // lights[0].setY(mouseY);

        SwingWorker worker = new SwingWorker() {

            public Object construct() {
                try {
                    while (!SidDesktop.canLoad()) {
                        Thread.sleep(500);
                    }

                    status.showStatus("Loading RTI Image...");
                    int index;
                    SegmentUtils seg;

                    if (isRTI) {
                        for (int i = 0; i <= times - 1; i++) {
                            for (int row = 0; row < rows; row++) { //8
                                for (int col = 0; col < cols; col++) { //8
                                    index = (row * cols) + col;
                                    seg = new SegmentUtils();
                                    seg.setBand(i);
                                    seg.setRow(row);
                                    seg.setCol(col);
                                    seg.setBandTile(index);
                                    seg.setSize(segSize);
                                    seg.setName(ptmFile);
                                    segments[(i * peices) + index] = seg;
                                    FetchImage segment = new FetchImage(seg, ptm);
                                    ThreadExecutor.getInstance().execute(segment, false);
                                    //segment.run();
                                }
                            }
                        }

                    } else {
                        for (int i = times - 1; i >= 0; i--) {
                            for (int row = 0; row < rows; row++) { //8
                                for (int col = 0; col < cols; col++) { //8
                                    index = (row * cols) + col;
                                    seg = new SegmentUtils();
                                    seg.setBand(i);
                                    seg.setRow(row);
                                    seg.setCol(col);
                                    seg.setBandTile(index);
                                    seg.setSize(segSize);
                                    seg.setName(ptmFile);
                                    segments[(i * peices) + index] = seg;
                                    FetchImage segment = new FetchImage(seg, ptm);
                                    ThreadExecutor.getInstance().execute(segment, false);
                                    //segment.run();
                                }
                            }
                        }
                    }

                } catch (Exception e) {

                    log.error(e);
                    return null;
                }

                return null;

            }

            @Override
            public void finished() {
                //  saveThumbnail();
            }
        };
        worker.start();

        UpdateProgress p = new UpdateProgress(ptmFile);
        p.startMonitor();

    }

    public void mouseClicked(MouseEvent e) {
        ////System.out.println("Fom Method: mouseCLicked()");

        if (isAnimated()) {
            setAnimated(false);

        }

    }

    @Override
    public void paint(Graphics g) {
        int mode = PTMFrameManager.getMode();

        Rectangle r = getBounds();

        if (aw != r.width || ah != r.height) {
            //  ax = desiredWidth;
            //  ay = desiredHeight;
            aw = r.width;
            ah = r.height;

            offImg = (BufferedImage) createImage(r.width, r.height);
            offScreen = offImg.getGraphics();
        } else if (offScreen == null) {

            offImg = (BufferedImage) createImage(r.width, r.height);
            offScreen = offImg.getGraphics();
        }

        if (curWid > width) {
            BufferedImage dest = new BufferedImage(displayWidth, displayHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = dest.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(1 / factor, 1 / factor);
            gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            //gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            gd.drawRenderedImage(actImg, at);
            if (mode == SidDesktop.SZ) {
                offScreen.setColor(Color.black);
                offScreen.fillRect(0, 0, r.width, r.height);
                offScreen.drawImage(dest, 0, 0, this);
                if ((sp != null && ep != null) && mode == 3 && drawRect) {
                    drawRect(offScreen);
                    // drawRect = true;
                }
            } else if (mode == SidDesktop.PAN && dragging) {
                offScreen.drawImage(dest, divx, divy, this);
            } else {
                offScreen.setColor(Color.black);
                offScreen.fillRect(0, 0, r.width, r.height);
                offScreen.drawImage(dest, 0, 0, this);
            }

        } else {
            if (mode == SidDesktop.SZ) {
                offScreen.setColor(Color.black);
                offScreen.fillRect(0, 0, r.width, r.height);
                offScreen.drawImage(actImg, 0, 0, this);
                if ((sp != null && ep != null) && mode == 3 && drawRect) {
                    drawRect(offScreen);
                    // drawRect = true;
                }
            } else if (mode == SidDesktop.PAN && dragging) {
                offScreen.drawImage(actImg, divx, divy, this);
            } else {
                offScreen.setColor(Color.black);
                offScreen.fillRect(0, 0, r.width, r.height);
                offScreen.drawImage(actImg, 0, 0, this);
            }

        }

        Graphics2D g2d = (Graphics2D) g;
        /*if (hints) {
         g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
         RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         }*/

        if (paintPosition && isAdditionalLight()) {
            ////System.out.println("here in draging and repainting");
            if (!dragging) {

                offScreen.setColor(Color.green);
                offScreen.fillArc(lights[0].getX() + posx - 5, lights[0].getY() + posy - 5, 10, 10, 0, 360);
                //offScreen.fillArc(lights[0].getX()  - 5, lights[0].getY()  - 5, 10, 10, 0, 360);
                offScreen.setColor(Color.red);
                offScreen.fillArc(lights[1].getX() + posx - 5, lights[1].getY() + posy - 5, 10, 10, 0, 360);
                //offScreen.fillArc(lights[1].getX()  - 5, lights[1].getY()  - 5, 10, 10, 0, 360);
            } else {
                int cur = 1 - activeLight;
                Color clr = (cur == 0) ? Color.green : Color.red;
                offScreen.setColor(clr);
                offScreen.fillArc(lights[cur].getX() + posx - 5, lights[cur].getY() + posy - 5, 10, 10, 0, 360);
                //offScreen.fillArc(lights[cur].getX()  - 5, lights[cur].getY()  - 5, 10, 10, 0, 360);
            }
        }  /*else {   // code required for debugging the light position.

         offScreen.setColor(Color.magenta);
         offScreen.fillArc(lights[activeLight].getX() + posx - 5, lights[activeLight].getY() + posy - 5, 10, 10, 0, 360);
         //System.out.println("ActiveLight:"+lights[activeLight].getX()+":"+lights[activeLight].getY());
         //System.out.println("Mouse:"+mouseX+":"+mouseY);
         //System.out.println("Pos:"+posx+":"+posy);
         }*/

        g2d.drawImage(offImg, 0, 0, this);
        //offScreen.dispose();
        g2d.dispose();

        r = null;
    }

    public void mouseDragged(MouseEvent e) {
        int mode = PTMFrameManager.getMode();
        // //System.out.println("Frame is Active:"+frameActive+" mode = "+mode);
        //  //System.out.println("IsActive:"+isActive()+"::frameActve:"+frameActive);

        if (frameActive) {
            dragging = true;

            if (e.getX() - posx > curWid || e.getY() - posy > curHei || e.getX() - posx < 0 || e.getY() - posy < 0) {
                int meX = e.getX() - posx;
                int meY = e.getY() - posy;
                meX = (meX > curWid) ? curWid : (meX < 0) ? 0 : meX;
                meY = (meY > curHei) ? curHei : (meY < 0) ? 0 : meY;
                if (ptm.getFileType() != PTM.TYPE_RTI_HSH) {
                    lights[activeLight].setX(meX);
                    lights[activeLight].setY(meY);
                } //else {
                mouseX = meX;
                mouseY = meY;

                // }
                repaint();
                return;
            }

            if (mode == SidDesktop.PTM) {
                if (sp == null) {
                    sp = e.getPoint();
                }
                synchronized (mutex) {

                    mouseX = e.getX() - posx;
                    mouseY = e.getY() - posy;

                    if (ptm.getFileType() != PTM.TYPE_RTI_HSH) {
                        lights[activeLight].setX(mouseX);
                        lights[activeLight].setY(mouseY);
                    } else {
                        calcLightVector();
                    }


                    /*  if (isAdditionalLight() && e.isControlDown()) {
                     paintPosition = true;
                     }*/
                    Rectangle r = getBounds();
                    if (curWid > width) {
                        newMX = (int) (mouseX * factor);
                        newMY = (int) (mouseY * factor);
                        LightSource[] newLS = new LightSource[2];
                        for (int i = 0; i < 2; i++) {
                            newLS[i] = new LightSource();
                            newLS[i].setActive(lights[i].isActive());
                            newLS[i].setEnabled(lights[i].isEnabled());
                            newLS[i].setLuminance(lights[i].getLuminance());
                            newLS[i].setX((int) (lights[i].getX() * factor));
                            newLS[i].setY((int) (lights[i].getY() * factor));

                            if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {

                                Point3f light = lights[i].getLight();
                                //light.scale(factor);
                                newLS[i].setLight(light);

                            }

                        }
                        pixelTransformOp.performOperation(pixels, newLS, ptm,
                                newMX, newMY, newDW, newDH, newPX, newPY, width, height);

                    } else {

                        pixelTransformOp.performOperation(pixels, lights, ptm,
                                mouseX, mouseY, r.width, r.height, posx, posy, curWid, curHei);

                    }
                    paintImmediately(0, 0, r.width, r.height);

                    //System.gc();
                }
            } else if (mode == 3 || mode == 4) {
                if (sp == null) {
                    sp = e.getPoint();
                }

                if (mode == SidDesktop.PAN) {

                    Point cp = new Point(e.getX(), e.getY());

                    posx = (posx - (sp.x - cp.x));
                    posy = (posy - (sp.y - cp.y));
                    divx += cp.x - sp.x;
                    divy += cp.y - sp.y;
                    //TODO: regenerate coeffs

                } else if (mode == SidDesktop.SZ) {
                    drawRect = true;
                }
                ep = new Point(e.getX(), e.getY());
                if (mode == SidDesktop.PAN) {
                    sp = ep;
                }
                repaint();
            }
            PTMFrameManager.getInstance().refreshControls();
        } else {
            frameActive = true;
            sp = e.getPoint();
            PTMFrameManager.getInstance().setPTMWindow(this);

            // //System.out.println("end:"+ep+" start:"+sp);
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(java.awt.event.MouseEvent me) {
    }

    public void mousePressed(MouseEvent e) {
        int mode = PTMFrameManager.getMode();
        sp = e.getPoint();
        if (sp.x < posx) {
            sp.x = posx;
        }
        if (sp.y < posy) {
            sp.y = posy;
        }
        if (frameActive) {

            divx = 0;
            divy = 0;

            //  handlePopup(e);
            if (isAdditionalLight() && mode == SidDesktop.PTM) {
                //  if (e.isControlDown()) {
                int x = e.getX() - posx;
                int y = e.getY() - posy;

                int posX, posY;
                LightSource s;

                for (int p = 0; p < lights.length; p++) {
                    s = lights[p];
                    posX = s.getX();
                    posY = s.getY();
                    if (((posX - x) * (posX - x)) + ((posY - y) * (posY - y)) <= 100) {
                        setActiveLight(p);
                        PTMFrameManager.getInstance().refreshControls();
                        break;
                    }
                }
            //    }
                //  paintPosition = true;
            } else {
                // paintPosition = false;
            }

        }

    }

    public void mouseReleased(MouseEvent e) {
        int mode = PTMFrameManager.getMode();
        if (frameActive) {
            if (dragging) {
                dragging = false;
            }

            /*     if (e.getX() - posx > curWid || e.getY() - posy > curHei || e.getX() - posx < 0 || e.getY() - posy < 0) {
             repaint();
             return;
             }*/
            working = true;
            drawRect = false;
            ep = new Point(e.getX(), e.getY());
            if (ep.x > curWid + posx) {
                ep.x = curWid + posx;
            }
            if (ep.y > curHei + posy) {
                ep.y = curHei + posy;
            }

            double oldZoom = zoom;
            divx = 0;
            divy = 0;
            clickXImageCoordinate = 0;
            clickYImageCoordinate = 0;

            if (mode == SidDesktop.ZOUT || mode == SidDesktop.ZIN) {

                clickXImageCoordinate = Math.abs(ep.x - posx);
                clickYImageCoordinate = Math.abs(ep.y - posy);
                if (mode == SidDesktop.ZIN && !SidDesktop.masked) {
                    clickXImageCoordinate = (int) (clickXImageCoordinate * 1.3);
                    clickYImageCoordinate = (int) (clickYImageCoordinate * 1.3);
                    zoom /= 1.3;
                    //curHei *= 1.3;

                } else if (mode == SidDesktop.ZOUT && !SidDesktop.masked) {
                    clickXImageCoordinate = (int) (clickXImageCoordinate / 1.3);
                    clickYImageCoordinate = (int) (clickYImageCoordinate / 1.3);
                    zoom *= 1.3;
                    //curHei /= 1.3;
                } else if (mode == SidDesktop.ZIN && SidDesktop.masked) {
                    clickXImageCoordinate = (int) (clickXImageCoordinate / 1.3);
                    clickYImageCoordinate = (int) (clickYImageCoordinate / 1.3);
                    zoom *= 1.3;
                    // curHei /= 1.3;
                } else if (mode == SidDesktop.ZOUT && SidDesktop.masked) {
                    clickXImageCoordinate = (int) (clickXImageCoordinate * 1.3);
                    clickYImageCoordinate = (int) (clickYImageCoordinate * 1.3);
                    zoom /= 1.3;
                    //curHei *= 1.3;
                }
                curWid = (int) Math.floor(width / zoom);
                curHei = (int) Math.floor(height / zoom);

                posx = (displayWidth - curWid) / 2;
                posy = (displayHeight - curHei) / 2;

                posx = (posx + ((int) (curWid / 2) - clickXImageCoordinate));
                posy = (posy + ((int) (curHei / 2) - clickYImageCoordinate));

                if (curWid * 1.3 >= width || curHei * 1.3 >= height) {
                    atMaxLevel = true;

                } else {
                    atMaxLevel = false;
                    shown = false;

                }

            } else if (mode == SidDesktop.SZ) {
                boolean stopAt130 = false;
                if ((curWid < width && curHei < height)) {
                    stopAt130 = true;
                }

                factor = 1.0f;
                int w = Math.abs(sp.x - ep.x);
                int h = Math.abs(sp.y - ep.y);

                w = (w == 0) ? 1 : w;
                h = (h == 0) ? 1 : h;
                float aspect = (float) w / (float) h;

                float cur, fin;
                if (aspect >= 1) {
                    // h = (int) (w / ratio);
                    cur = w;
                    fin = displayWidth;

                } else {
                    //   w = (int) (h * ratio);
                    cur = h;
                    fin = displayHeight;
                }
                while (cur * 1.3 < fin) {//&& desiredWidth<maxWidth) {

                    zoom /= 1.3;
                    cur *= 1.3;
                    factor *= 1.3;
                    if ((width / zoom) >= width || (height / zoom) >= height) {
                        atMaxLevel = true;
                        if (stopAt130) {
                            stopAt130 = false;
                            shown = false;
                            break;
                        }
                    } else {
                        atMaxLevel = false;
                        shown = true;

                    }

                }

                clickXImageCoordinate = ((sp.x) + w / 2) - posx;
                clickYImageCoordinate = ((sp.y) + h / 2) - posy;

                clickXImageCoordinate = (int) (clickXImageCoordinate * factor);
                clickYImageCoordinate = (int) (clickYImageCoordinate * factor);
                curWid = (int) Math.floor(width / zoom);
                curHei = (int) Math.floor(height / zoom);
                posx = (displayWidth - curWid) / 2;
                posy = (displayHeight - curHei) / 2;

                posx = posx + ((int) (curWid / 2) - clickXImageCoordinate);
                posy = posy + ((int) (curHei / 2) - clickYImageCoordinate);

            }
            if (mode == SidDesktop.PAN) {
                posx += sp.x - ep.x;
                posy += sp.y - ep.y;
                mouseX -= sp.x - ep.x;
                mouseY -= sp.y - ep.y;

                if (ptm.getFileType() != PTM.TYPE_RTI_HSH) {
                    lights[activeLight].setX(mouseX);
                    lights[activeLight].setY(mouseY);
                } else {
                    calcLightVector();
                }
            }

            if (mode != SidDesktop.PTM) {

                if (zoom != oldZoom) {
                    mouseX = (int) (mouseX / zoom * oldZoom);
                    mouseY = (int) (mouseY / zoom * oldZoom);
                    for (int i = 0; i < lights.length; i++) {
                        lights[i].setX((int) (lights[i].getX() / zoom * oldZoom));
                        lights[i].setY((int) (lights[i].getY() / zoom * oldZoom));
                    }

                }
            } else {
                if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {
                    calcLightVector();
                }
            }

            if (curWid > width) {
                calculated = false;
            }

            performResizeOnPTM();
            String msg = "Zoom is: " + ((curWid * 100) / width) + " %";
            if (curWid > width) {
                msg = msg + " Image is interpolated at this level.";
            }
            notifier.updateSize(msg);
            //update the view area on the control
            PTMFrameManager.getInstance().refreshControls();
            //  setCursor(old);

            working = false;

        } else {
            frameActive = true;
        }

    }

    public void notImplemented() {
        JOptionPane.showMessageDialog(this, "This feature is not available yet");
    }

    public void refreshMap() {
        ptm.getEnvironmentMap().refresh();
        ptm.recache();
    }

    /**
     * This method is called by the thread that was created in the start method.
     * It does the main animation.
     */
    public void run() {
        float anglex = 0;
        float angley = 0;

        int w, h;
        w = (curWid - 1) / 2;
        h = (curHei - 1) / 2;

        for (int i = 0; i < 360; i++) {
            int tryX = (int) ((Math.cos(Math.toRadians(i)) * w) + w);
            if (tryX == mouseX) {
                anglex = (float) Math.toRadians(i);
            }
            int tryY = (int) ((Math.sin(Math.toRadians(i)) * h) + h);
            if (tryY == mouseY) {
                angley = (float) Math.toRadians(i);
            }
        }

        while (Thread.currentThread() == animator && animated) {

            anglex = anglex + 0.02f;
            angley = angley + 0.03f;
            synchronized (mutex) {
                try {
                    mouseX = (int) ((Math.cos(anglex) * w) + w);
                    mouseY = (int) ((Math.sin(angley) * h) + h);

                    lights[activeLight].setX(mouseX);
                    lights[activeLight].setY(mouseY);

                    Rectangle r = getBounds();
                    if (curWid > width) {
                        newMX = (int) (mouseX * factor);
                        newMY = (int) (mouseY * factor);
                        LightSource[] newLS = new LightSource[2];
                        for (int i = 0; i < 2; i++) {
                            newLS[i] = new LightSource();
                            newLS[i].setActive(lights[i].isActive());
                            newLS[i].setEnabled(lights[i].isEnabled());
                            newLS[i].setLuminance(lights[i].getLuminance());
                            newLS[i].setX((int) (lights[i].getX() * factor));
                            newLS[i].setY((int) (lights[i].getY() * factor));

                            if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {

                                Point3f light = lights[i].getLight();
                                //light.scale(factor);
                                newLS[i].setLight(light);

                            }
                        }
                        pixelTransformOp.performOperation(pixels, newLS, ptm,
                                newMX, newMY, newDW, newDH, newPX, newPY, width, height);

                    } else {

                        pixelTransformOp.performOperation(pixels, lights, ptm,
                                mouseX, mouseY, r.width, r.height, posx, posy, curWid, curHei);

                    }
                    paintImmediately(0, 0, r.width, r.height);//System.gc();

                } catch (java.lang.NullPointerException npe) {
                    break; /* user closed the window */

                }
            }
        }

    }

    public void setBrowser(Container c) {
    }

    public void setEnvironmentMap(EnvironmentMap e) {
    }

    public void setExp(int i) {
        ptm.setExp(i);
    }

    public void setKDiff(float f) {
        ptm.setKDiff(f);
    }

    public void setKSpec(float f) {
        ptm.setKSpec(f);
    }

    public void setLuminance(float f, int pos) {
        lights[pos].setLuminance(f);
    }

    public void setLuminance(float f) {
        lights[activeLight].setLuminance(f);
    }

    public void setMapBlurType(int i) {
        switch (i) {
            case EnvironmentMap.BLUR_TYPE_SIMPLE:
                ptm.getEnvironmentMap().setSimpleBlur();
                break;
            case EnvironmentMap.BLUR_TYPE_GAUSSIAN:
                ptm.getEnvironmentMap().setGaussianBlur();
                break;
            case EnvironmentMap.BLUR_TYPE_NONE:
            default:
                ptm.getEnvironmentMap().setNoBlur();
        }
    }

    public void setMapGuassianBlurSigma(float f) {
        ptm.getEnvironmentMap().setGaussianSigma(f);
    }

    public void setMapKernelSize(int i) {
        ptm.getEnvironmentMap().setBlurKernelSize(i);
    }

    public void setMapSampleSize(int i) {
        if (ptm.getEnvironmentMap() != null) {
            ptm.getEnvironmentMap().setSampleSize(i);
        }
    }

    public void setPixelTransformOp(Operation pto) {
        if (pixelTransformOp != null) {
            pixelTransformOp.release();
            pixelTransformOp = null;
        }
        pixelTransformOp = pto;
    }

    public Operation getPixelTransformOp() {
        return pixelTransformOp;
    }

    public void setAnimated(boolean flag) {
        animated = flag;
        if (animated) {
            start();
        }
    }

    public boolean isAnimated() {
        return animated;
    }

    private void saveThumbnail() {
        int xf, yf;
        // double scaleFactor = (double) 1 / (double) 2;

        if (width > height) {
            xf = 220;
            yf = (int) (((float) 220 / (float) width) * height);
        } else {
            xf = (int) (((float) 220 / (float) height) * width);
            yf = 220;
        }
        //  scaleFactor = (float) xf / (float) width;

        double oldZoom = zoom;
        double izoom = 1.0;

        int icurWid = width;
        int icurHei = height;
        while (icurWid > xf || icurHei > yf) {
            izoom *= 1.3;
            icurWid = (int) Math.floor(width / izoom);
            icurHei = (int) Math.floor(height / izoom);
        }
        LightSource[] lts = new LightSource[PTM.MAX_LIGHTS];
        if (firstView) {
            lts[0] = new LightSource();
            lts[0].setEnabled(true);
            lts[0].setLuminance(2.0f);
            lts[0].setActive(true);
            lts[0].setLight(new Point3f(0, 0, 1));
            lts[1] = new LightSource();
            lts[1].setEnabled(false);
            lts[1].setLuminance(2.0f);
            lts[1].setActive(false);
            lts[1].setLight(new Point3f(0, 0, 1));

        } else {
            lts = lights;
        }

        //    int iposx = (xf - icurWid) / 2;
        //    int iposy = (yf - icurHei) / 2;
        //    Rectangle r = new Rectangle(0, 0, xf, yf);
        BufferedImage img = new BufferedImage(xf, yf, BufferedImage.TYPE_INT_RGB);
        int[] ipixels = ((DataBufferInt) ((BufferedImage) img).getRaster().getDataBuffer()).getData();

        Operation op = new DirectionalLightOperation();

        op.performOperation(ipixels, lts, ptm,
                xf / 2, yf / 2, xf, yf, 0, 0, xf, yf);

        //BufferedImage dest1 = new BufferedImage(xf, yf, BufferedImage.TYPE_INT_RGB);
        // Graphics2D gd = dest1.createGraphics();
        // AffineTransform at = AffineTransform.getScaleInstance(1, 1);
        // gd.drawRenderedImage(img, at);
        if (ptm instanceof isf.ptm.formats.HSH) {
            for (int i = 0; i < xf; i++) {
                for (int j = 0; j < yf; j++) {
                    double distance2 = ((i - (xf / 2)) * (i - (xf / 2))) + ((j - (yf / 2)) * (j - (yf / 2)));
                    double projectedDist = 0.0;
                    if (xf >= yf) {
                        projectedDist = Math.sqrt(distance2) / (xf / 2);
                    } else {
                        projectedDist = Math.sqrt(distance2) / (yf / 2);
                    }

                    if (projectedDist >= .99 && projectedDist <= 1.00) {
                        ipixels[j * xf + i] = (125 << 16 | 125 << 8 | 125);
                    }
                }
            }
        }
        ImageUtils.saveThumbnail(thumbname, img);
        thumbnailCached = true;
        PTMFrameManager.getInstance().refreshControls();
    }

    private void start() {
        SwingWorker worker = new SwingWorker() {

            public Object construct() {
                try {
                    float anglex = 0;
                    float angley = 0;

                    int w, h;
                    w = (curWid - 1) / 2;
                    h = (curHei - 1) / 2;

                    for (int i = 0; i < 360; i++) {
                        int tryX = (int) ((Math.cos(Math.toRadians(i)) * w) + w);
                        if (tryX == mouseX) {
                            anglex = (float) Math.toRadians(i);
                        }
                        int tryY = (int) ((Math.sin(Math.toRadians(i)) * h) + h);
                        if (tryY == mouseY) {
                            angley = (float) Math.toRadians(i);
                        }
                    }
                    while (animated) {
                        anglex = anglex + 0.02f;
                        angley = angley + 0.03f;
                        synchronized (mutex) {
                            try {
                                mouseX = (int) ((Math.cos(anglex) * w) + w);
                                mouseY = (int) ((Math.sin(angley) * h) + h);

                                lights[activeLight].setX(mouseX);
                                lights[activeLight].setY(mouseY);
                                Rectangle r = getBounds();
                                if (curWid > width) {
                                    newMX = (int) (mouseX * factor);
                                    newMY = (int) (mouseY * factor);
                                    LightSource[] newLS = new LightSource[2];
                                    for (int i = 0; i < 2; i++) {
                                        newLS[i] = new LightSource();
                                        newLS[i].setActive(lights[i].isActive());
                                        newLS[i].setEnabled(lights[i].isEnabled());
                                        newLS[i].setLuminance(lights[i].getLuminance());
                                        newLS[i].setX((int) (lights[i].getX() * factor));
                                        newLS[i].setY((int) (lights[i].getY() * factor));

                                    }
                                    pixelTransformOp.performOperation(pixels, newLS, ptm,
                                            newMX, newMY, newDW, newDH, newPX, newPY, width, height);

                                } else {

                                    pixelTransformOp.performOperation(pixels, lights, ptm,
                                            mouseX, mouseY, r.width, r.height, posx, posy, curWid, curHei);

                                }
                                paintImmediately(0, 0, r.width, r.height);//System.gc();

                            } catch (java.lang.NullPointerException npe) {
                                log.error(npe);
                                break;
                            }
                        }
                    }

                } catch (Exception e) {
                }
                return null;

            }

            @Override
            public void finished() {
            }
        };
        worker.start();

    }

    /*    public void start() {
     if (animated) {
     animator = new Thread(this);
     animator.setPriority(Thread.MIN_PRIORITY);
     animator.start();
     }
     }
    
     public void stop() {
     animated = false;
     animator.interrupt();
     animator = null;
     }*/
    public JLayeredPane getLayeredPane() {
        return JLayeredPane.getLayeredPaneAbove(this);
    }

    public void addImage(ImageIcon ing) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addImage(String ing) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addImage(PTMConfig config) {
        ////System.out.println("Method: addImage(String fileName)");
        copied = false;
        String imgName = config.getName();

        //System.out.println("Before Memory Used:" + Runtime.getRuntime().totalMemory());
        if (ImageUtils.isCached(imgName)) {
            //Load from the cache else download the ptm
            loadLocalFile(ImageUtils.getCachedPath() + imgName + config.getExtension());

        } else {
            loadFile(config);
        }

        this.addComponentListener(this);
    }

    public void addImage(DesktopDataObject config) {
        ////System.out.println("Method: addImage(String fileName)");

        copied = false;

        // //System.out.println("Before Memory Used:" + Runtime.getRuntime().totalMemory());
        loadFile(config);
       // //System.out.println("After Memory Used:" + Runtime.getRuntime().totalMemory());
        // //System.out.println("After Memory free:" + Runtime.getRuntime().freeMemory());
        this.addComponentListener(this);
    }

    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean isPTMPanel() {
        return true;
    }

    public boolean isHSHPanel() {
        if (ptm != null) {
            return ptm.getFileType() == PTM.TYPE_RTI_HSH;
        } else {
            return false;
        }
    }

    private void zoomToFitDisplay() {

        int w = width;
        int h = height;
        double oldZoom = zoom;
        zoom = 1.0;

        curWid = w;
        curHei = h;
        atMaxLevel = false;

        Rectangle r = getBounds();

        displayWidth = r.width;
        displayHeight = r.height;
        if (displayWidth == 0) {
            displayWidth = 340;
        }
        if (displayHeight == 0) {
            displayHeight = 340;
        }

        while (curWid > displayWidth || curHei > displayHeight) {
            zoom *= 1.3;
            curWid = (int) Math.floor(width / zoom);
            curHei = (int) Math.floor(height / zoom);
        }

        posx = (displayWidth - curWid) / 2;
        posy = (displayHeight - curHei) / 2;

        if (mouseX != 0) {
            mouseX = (int) ((mouseX / zoom) * oldZoom);
        } else {
            mouseX = (curWid / 2);
        }
        if (mouseY != 0) {
            mouseY = (int) ((mouseY / zoom) * oldZoom);
        } else {
            mouseY = (curHei / 2);
        }

        if (!lights[1].isEnabled()) {
            lights[0].setActive(true);
            lights[0].setEnabled(true);
            lights[0].setX(mouseX);
            lights[0].setY(mouseY);
            lights[1].setX(0);
            lights[1].setY(0);

        } else {
            lights[activeLight].setX(mouseX);
            lights[activeLight].setY(mouseY);
            lights[1 - activeLight].setX((int) ((lights[1 - activeLight].getX() / zoom) * oldZoom));
            lights[1 - activeLight].setY((int) ((lights[1 - activeLight].getY() / zoom) * oldZoom));
        }

        performResizeOnPTM();
        String msg = "";
        if (width != 0) {
            msg = "Zoom is: " + ((curWid * 100) / width) + " %";
            if (curWid > width) {
                msg = msg + " Image is interpolated at this level.";
            }
        }

        notifier.updateSize(msg);
        PTMFrameManager.getInstance().refreshControls();
        // return new Dimension(curWid, curHei);
    }

    public Dimension getCurrentSize() {

        return new Dimension(curWid, curHei);
    }

    public Dimension getDisplaySize() {
        return new Dimension(curWid, curHei);
    }

    public String getDetails() {
        ////System.out.println("Method:getDetails()");
        int tp = ptm.getFileType();
        String type = "";
        String strBreak = "";
        String colSpan = "colspan=1";
        if (tp == PTM.TYPE_RAW_PTM) {
            type = ptm.getBasisType() == 1 ? "LRGBPTM" : ptm.getBasisType() == 2 ? "RGBPTM" : "PRIMITIVE";
        } else if (tp == PTM.TYPE_RTI_HSH) {
            type = ptm.getBasisType() == 1 ? "HSH [LRGB]" : ptm.getBasisType() == 2 ? "HSH [RGB]" : "Unknown";
            strBreak = " </tr><tr>";
            colSpan = "colspan=3";
        }
        float[] bias = ptm.getBias();
        float[] scale = ptm.getScaleArray();
        String version = ptm.getVersion();

        // Dimension curSize = ((PTMCanvasBufferedImage) ptmCanvas).getCurrentSize();
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html><body><table border=1><tr><td><font face=verdana size=3 color=black><b>File Name :</b></font></td><td colspan=3><font face=verdana size=3 color=midnightblue><b>" + fName);
        buffer.append("</b></font></td></tr>");

        buffer.append("<tr><td><font face=verdana size=3 color=black><b>RTI Type :</b></font></td><td align = center><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(type + "</b></font></td><td><font face=verdana size=3 color=black><b>RTI Version :</b></font></td><td align=center><font face=verdana size=3 color=midnightblue><b>" + version + "</b></font></td></tr>");
        buffer.append("<tr><td><font face=verdana size=3 color=black><b>RTI Scale :</b></font></td><td align = center " + colSpan + "><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(arrayToString(scale) + "</b></font></td>" + strBreak + "<td><font face=verdana size=3 color=black><b>RTI Bias :</b></font></td><td align=center " + colSpan + "><font face=verdana size=3 color=midnightblue><b>" + arrayToString(bias) + "</b></font></td></tr>");

        buffer.append("<tr><td><font face=verdana size=3 color=black><b>File Size :</b></font></td><td colspan=3><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(size + " bytes</b></font></td></tr>");
        buffer.append("<tr><td><font face=verdana size=3 color=black><b>Current Width :</b></font></td><td align = center><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(curWid + " pixels</b></font></td><td><font face=verdana size=3 color=black><b>Current Height :</b></font></td><td align= center><font face=verdana size=3 color=midnightblue><b>" + curHei + " pixels</b></font></td></tr>");
        buffer.append("<tr><td><font face=verdana size=3 color=black><b>RTI Width :</b></font></td><td align = center><font face=verdana size=3 color=midnightblue><b>");
        buffer.append(width + "</b></font></td><td><font face=verdana size=3 color=black><b>RTI Height :</b></font></td><td align=center><font face=verdana size=3 color=midnightblue><b>" + height + "</b></font></td></tr></table></body></html>");

        return buffer.toString();
    }

    public void speed() {
        isFast = true;
        int w = curWid / 2;
        int h = curHei / 2;
    }

    public void detail() {
        isFast = false;
        int w = curWid;
        int h = curHei;
    }

    public void useHint(boolean b) {
        hints = b;
    }

    private String arrayToString(int[] arr) {
        String str = "[";
        String sep = "";
        for (int i = 0; i < arr.length; i++) {
            str = str + sep + arr[i];
            sep = ",";
        }
        str = str + "]";
        return str;
    }

    private String arrayToString(float[] arr) {
        String str = "[";
        String sep = "";
        for (int i = 0; i < arr.length; i++) {
            str = str + sep + arr[i];
            sep = ",";
        }
        str = str + "]";
        return str;
    }

    public void toggleLight(boolean dual) {
        lights[1].setEnabled(dual);

        if (!dual) {
            lights[1].setActive(false);
            lights[0].setActive(true);
            lights[0].setEnabled(true);
            lights[0].setX(curWid / 2);
            lights[0].setY(curHei / 2);
            lights[1].setX(0);
            lights[1].setY(0);
            activeLight = 0;
            mouseX = curWid / 2;
            mouseY = curHei / 2;
            paintPosition = false;
            if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {
                calcLightVector(0);

            }
        } else {
            lights[0].setX(10);
            lights[0].setY(10);
            lights[0].setActive(true);
            lights[0].setEnabled(true);
            lights[1].setX(curWid - 10);
            lights[1].setY((int) (curHei * 0.60) - 10);
            activeLight = 0;
            mouseX = 10;
            mouseY = 10;
            paintPosition = true;
            if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {
                calcLightVector(0);
                calcLightVector(1);
            }
        }
        //System.out.println("ActiveLight after toogle = " + activeLight);

    }

    public void setExtrapolation(boolean flag) {
        ptm.setExtrapolation(flag);
    }

    public boolean isExtrapolation() {
        return false;

    }

    public void componentResized(ComponentEvent e) {
        calculated = false;

        if (ptm != null) {

            zoomToFitDisplay();
            repaint();
        }
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void fitToSize() {
        zoomToFitDisplay();

    }

    public void reset() {
        long start = System.currentTimeMillis();

        if (loading) {
            SidDesktop.stopLoad();
        }
        if (animated) {
            setAnimated(false);
        }
        if (pixelTransformOp != null) {
            pixelTransformOp.release();
            pixelTransformOp = null;
        }
        if (ptm != null && !copied) {
            ptm.release();
            ptm = null;
        }

        actImg = null;
        pixels = null;
        copied = false;
        curWid = 0;
        curHei = 0;
        //  maxWidth = 0;
        //   maxHeight = 0;
        segments = null;
        tiles = null;
        posx = 0;
        posy = 0;
        offImg = null;
        offScreen = null;

        Runtime.getRuntime().gc();
    //     //System.out.println("Timeclose:" + (System.currentTimeMillis() - start));

    }

    public void setSingleLight() {

        lights[1].setActive(false);
        lights[1].setEnabled(false);
        lights[0].setActive(true);
        lights[0].setEnabled(true);
        paintPosition = false;
        activeLight = 0;
        mouseX = lights[0].getX();
        mouseY = lights[0].getY();

    }

    private void calculateValuesAtFullSize() {
        //recalculate posx,posy,mousex,mousey
        factor = ((float) width / (float) curWid);
        newPX = (int) (posx * factor);
        newPY = (int) (posy * factor);
        newMX = (int) (mouseX * factor);
        newMY = (int) (mouseY * factor);
        newDW = (int) (displayWidth * factor);
        newDH = (int) (displayHeight * factor);

        if (newDW < 1) {
            newDW = 1;
        }
        if (newDH < 1) {
            newDH = 1;
        }
        if (factor < 1) {

            actImg = new BufferedImage(newDW, newDH, BufferedImage.TYPE_INT_RGB);
            pixels = ((DataBufferInt) ((BufferedImage) actImg).getRaster().getDataBuffer()).getData();
        }

    }

    private void performResizeOnPTM() {

        if (!calculated && curWid > width) {
            calculated = true;
            calculateValuesAtFullSize();
        } else {
            actImg = new BufferedImage(displayWidth, displayHeight, BufferedImage.TYPE_INT_RGB);
            pixels = ((DataBufferInt) ((BufferedImage) actImg).getRaster().getDataBuffer()).getData();
        }

        if (pixelTransformOp != null) {
            fireTransform();
        }

    }

    @Override
    public boolean isAtMaxLevel() {
        return atMaxLevel;
    }

    public void setDrawRect(boolean flag) {
        // drawRect = dual;
    }

    public void setMessageShown(boolean b) {
        shown = b;
    }

    public boolean getMessageShown() {
        return shown;
    }

    private class FetchImage implements Runnable { //extends Thread

        private String file;
        private int index;
        private PTM ptm;
        int row, col, tilew, tileh, band;
        int top, left;
        int tileIndex = 0;

        public FetchImage(SegmentUtils seg, PTM ptm) {
            this.file = seg.getName();
            this.ptm = ptm;
            this.row = seg.getRow();
            this.col = seg.getCol();
            int segsize = seg.getSize();
            if (row == rows - 1) {
                tileh = height % segsize;
                if (((float) tileh) / segsize <= .3) {

                    tileh = segsize + tileh;
                }
            } else {
                this.tileh = segsize;
            }
            if (col == cols - 1) {
                tilew = width % segsize;
                if (((float) tilew) / segsize <= .3) {

                    tilew = segsize + tilew;
                }

            } else {
                this.tilew = segsize;
            }
            this.band = seg.getBand();
            this.index = seg.getBandTile();
            top = row * segsize;
            left = col * segsize;
            this.tileIndex = (band * peices) + index;
            //this.setName(tileIndex+"");
        }
       
        
        public void run() {
            
            
            try { 
                
               
                byte[] srcP = RequestProxy.getPTMRequestProxy().getImageCoeff(file, index, band, ptm.getFileType());
                if (srcP != null) {
                    LoadManager.getInstance().update(srcP.length);
                    ptm.setBCoeffs(top, left, tileh, tilew, band, srcP);

                    tiles[tileIndex] = 0;
                    obtained++;
                } else {
                    
                    tiles[tileIndex] = 1;
                }
               // log.error("Loaded Section:"+top+":"+left+":"+tileh+":"+tilew+":"+band);
               

            } catch (Exception exp) {
                log.error(exp);
            }
          
        }
    }

    private class UpdateProgress implements ActionListener {

        //  long start = System.currentTimeMillis();
        boolean changed = false;
        Timer timer;
        //  String filename;
        int count = 0;

        public void startMonitor() {
            timer.start();
        }

        public UpdateProgress(String name) {
            timer = new Timer(200, this);
            timer.setInitialDelay(0);
            timer.setCoalesce(true);
        }

        public void actionPerformed(ActionEvent e) {
            int x = 0;
            if ("WEB".equalsIgnoreCase(InscriptConfigLoader.getInstance().getProperty("mode"))) {
                if (count != obtained) {
                    count = obtained;
                    fireTransform();
                }

                int last = -1;
                boolean rtiLoading = true;

                for (int rt = 0; rt < times; rt++) {
                    for (int rx = 0; rx < rows; rx++) {
                        for (int rc = 0; rc < cols; rc++) {
                            x = (rt * peices) + ((rx * cols) + rc);
                            if (tiles[x] > 0) {
                                tiles[x] = -1;
                                FetchImage img = new FetchImage(segments[x], ptm);
                                ThreadExecutor.getInstance().execute(img, true);
                               // img.run();

                            }
                            if (tiles[x] != 0) {
                                last = x;
                            }
                        }
                    }
                    for (int p = 0; p < rows * cols; p++) {
                        if (tiles[p] != 0) {
                            rtiLoading = false;
                            break;
                        }
                    }
                }
                if (isRTI) {
                    if (rtiLoading && !changed) {
                        //System.out.println("Band finished");
                        status.showStatus("Loading interactivity data...");
                        changed = true;
                    }
                } else {
                    if (last < (tiles.length - (rows * cols)) && !changed) {
                        //System.out.println("Band finished");
                        status.showStatus("Loading interactivity data...");
                        changed = true;
                    }
                }

                if (last < 0) {//) == tiles.length) {

                    status.showStatus("Finalizing load ...");
                    timer.stop();

                    Runnable r = new Runnable() {

                        public void run() {

                            ptm.computeNormals();
                            updateFinalize();
                        }
                    };
                    SwingUtilities.invokeLater(r);

                }

            }
        }

        public void updateFinalize() {
            status.showStatus("Ready ..." + fName);
            SidDesktop.stopLoad();
            ToolbarManager.getInstance().updateToolbar();
            LoadManager.getInstance().updateCount();
            status.stop();

            saveThumbnail();
            ImageUtils.saveCache(fName, ptm, config);

            loading = false;

            count = 0;
            obtained = 0;
            //  //System.out.println("FileLoaded in :" + (System.currentTimeMillis() - start));
            System.gc();
        }
    }

    public void addImage(PTMConfig config, PTM src) {

        if (status == null) {
            status = ((ViewerPanel) getParent()).getStatus();
        }

        this.ptm = src;
        setDefaultLight();
        copied = true;
        if (config != null) {
            size = config.getSize();

        } else {
            size = 0;

        }
        fName = ptm.getName();
        fName = fName.substring(fName.lastIndexOf(File.separatorChar) + 1);
        if (fName.indexOf(".") > -1) {
            fName = fName.substring(0, fName.indexOf("."));
        }
        thumbname = fName + "thumb.jpg";
        //  fName = ptm.getName();
        width = ptm.getWidth();
        height = ptm.getHeight();

        zoomToFitDisplay();
        ratio = ((float) width) / height;

        lights[0].setActive(true);
        lights[0].setEnabled(true);
        lights[0].setX(mouseX);
        lights[0].setY(mouseY);

        loading = false;
        fireTransform();
        ToolbarManager.getInstance().updateToolbar();
        this.addComponentListener(this);
    }

    public boolean mustCurbResize() {
        return width * height >= MAXPIXELS;
    }

    private void updateLight() {
        if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {
            //  lights[activeLight].setLight(new Point3f(0,0,1));
            float[] values = Utils.convertToPolar(lights[activeLight].getLight());

            if (values[1] - dxLight < 0.0) {
                dxLight = values[1];
            } else if (values[1] - dxLight > Math.PI) {
                dxLight = (float) (values[1] - Math.PI);
            }
            if (values[2] - dyLight > Math.PI / 2) {
                dyLight = (float) (values[2] - Math.PI / 2);
            } else if (values[2] - dyLight < -Math.PI / 2) {
                dyLight = (float) (Math.PI / 2 + values[2]);
            }

            Matrix3f rotX = new Matrix3f();
            Matrix3f rotY = new Matrix3f();
            rotX.rotX(dyLight);
            rotY.rotY(dxLight);
            rotX.mul(rotY);
            Point3f temp = new Point3f();
            rotX.transform(lights[activeLight].getLight(), temp);
            if (temp.z < 0) {
                temp.z = 0;
            }
            Utils.normalize(temp);

            lights[activeLight].setLight(temp);

        //dxLight = 0;
            // dyLight = 0;
        }
    }

    private void calcLightVector() {
        //Projects the light vector on uv space
        int mx = mouseX;
        int my = mouseY;
        //   //System.out.println("MouseX:" + mx + "::MouseY:" + my + "::CurWidth:" + curWid + "::CurHeight:" + curHei);
        double distance2 = ((mx - (curWid / 2)) * (mx - (curWid / 2))) + ((my - (curHei / 2)) * (my - (curHei / 2)));

        double projectedDist = 0.0;

        int radius = curWid / 2;
        if (curWid >= curHei) {
            radius = curWid / 2;
        } else {
            radius = curHei / 2;
        }
        projectedDist = Math.sqrt(distance2) / radius;

        if (projectedDist < 0.99) {

            double alpha = Math.PI / 2;
            if ((radius != mx && curWid >= curHei && mx <= curWid) || (radius != my && curWid < curHei && my <= curHei)) {
                double m = ((curHei / 2.0) - my) / ((curWid / 2.0) - mx);
                alpha = Math.atan(m);
            }

            alpha = Math.abs(alpha);
            Point3f light = lights[activeLight].getLight();
            light.x = ((float) (mx > curWid / 2 ? projectedDist * Math.cos(alpha) : -(projectedDist * Math.cos(alpha))));
            light.y = ((float) (my > curHei / 2 ? -(projectedDist * Math.sin(alpha)) : projectedDist * Math.sin(alpha)));

            float inner = 1 - light.x * light.x - light.y * light.y;
            light.z = (inner > 0 ? (float) (Math.sqrt(inner)) : 0.0f);
            lights[activeLight].setLight(Utils.normalize(light));
            lights[activeLight].setX(mouseX); //mouseX
            lights[activeLight].setY(mouseY); //mouseY
        }

    }

    private void calcLightVector(int id) {
        //Projects the light vector on uv space
        int mx = lights[id].getX();
        int my = lights[id].getY();
        boolean widthWise = false;
        double distance2 = ((mx - (curWid / 2)) * (mx - (curWid / 2))) + ((my - (curHei / 2)) * (my - (curHei / 2)));
        double projectedDist = 0.0;
        if (curWid >= curHei) {
            projectedDist = Math.sqrt(distance2) / (curWid / 2);
            widthWise = true;
        } else {
            projectedDist = Math.sqrt(distance2) / (curHei / 2);
            widthWise = false;
        }
        if (projectedDist < 0.99) {

            double alpha = Math.PI / 2;
            if (curWid / 2 != mx && widthWise) {
                double m = ((curHei / 2.0) - my) / ((curWid / 2.0) - mx);
                alpha = Math.atan(m);
            } else if (curHei / 2 != my && !widthWise) {
                double m = ((curHei / 2.0) - my) / ((curWid / 2.0) - mx);
                alpha = Math.atan(m);
            }
            alpha = Math.abs(alpha);
            Point3f light = lights[id].getLight();
            light.x = ((float) (mx > curWid / 2 ? projectedDist * Math.cos(alpha) : -(projectedDist * Math.cos(alpha))));
            light.y = ((float) (my > curHei / 2 ? -(projectedDist * Math.sin(alpha)) : projectedDist * Math.sin(alpha)));
            float inner = 1 - light.x * light.x - light.y * light.y;
            light.z = (inner > 0 ? (float) (Math.sqrt(inner)) : 0.0f);
            lights[id].setLight(Utils.normalize(light));
            lights[id].setX(mx);
            lights[id].setY(my);
        }
    }

    public void setFrameActive(boolean flag) {
        frameActive = flag;
    }

    public boolean isFrameActive() {
        return frameActive;
    }

    public Rectangle getDisplayRect() {
        int dw = displayWidth;
        int dh = displayHeight;

        return new Rectangle(posx, posy, dw, dh);
    }

    public float getFactor() {
        return (float) curWid / (float) width;
    }

    public void updateLightPosition(int xpos, int ypos) {
        mouseX = xpos;
        mouseY = ypos;
        if (ptm.getFileType() == PTM.TYPE_RTI_HSH) {
            calcLightVector();
        } else {
            lights[activeLight].setX(mouseX);
            lights[activeLight].setY(mouseY);
        }
        //check this
        if (curWid > width) {
            newMX = (int) (mouseX * factor);
            newMY = (int) (mouseY * factor);
        }
        fireTransform();
    }

    private void setDefaultLight() {
        if (ptm != null && ptm.getFileType() != PTM.TYPE_RTI_HSH) {
            lights[0] = new LightSource();
            lights[0].setEnabled(true);
            lights[0].setLuminance(1.0f);
            lights[0].setActive(true);
            lights[0].setX(width / 2);
            lights[0].setY(height / 2);
            lights[1] = new LightSource();
            lights[1].setEnabled(false);
            lights[1].setLuminance(1.0f);
            lights[1].setActive(false);
            activeLight = 0;
        } else {
            lights[0] = new LightSource();
            lights[0].setEnabled(true);
            lights[0].setLuminance(2.0f);
            lights[0].setActive(true);
            lights[0].setLight(new Point3f(0, 0, 1));
            lights[1] = new LightSource();
            lights[1].setEnabled(false);
            lights[1].setLuminance(2.0f);
            lights[1].setActive(false);
            lights[1].setLight(new Point3f(0, 0, 1));
            PTMFrameManager.getInstance().setDiffuseOperation(false, false);
            PTMFrameManager.getInstance().setLightOperation(false);
        }
    }

    public void setStart(Point st, int m) {
        sp = st;
        if (st.x < posx) {
            sp.x = posx;
        }
        if (st.y < posy) {
            sp.y = posy;
        }
        //   mode = m;
    }

    public void setEnd(Point ed) {

        ep = ed;
        if (ed.x > posx + curWid) {
            ep.x = posx + curWid;
        }
        if (ed.y > posy + curHei) {
            ep.y = posy + curHei;
        }

    }

    public String saveImage(String name, String type) {
        BufferedImage buff;
        String msg = "";
        int w = ptm.getWidth();
        int h = ptm.getHeight();
        Operation oper = pixelTransformOp;
        float f = (float) w / (float) curWid;
        int k = (int) ((float) mouseX * f);
        int l = (int) ((float) mouseY * f);
        LightSource ls[] = new LightSource[2];
        for (int x = 0; x < 2; x++) {
            ls[x] = new LightSource();
            ls[x].setActive(lights[x].isActive());
            ls[x].setEnabled(lights[x].isEnabled());
            ls[x].setLuminance(lights[x].getLuminance());
            ls[x].setX((int) (lights[x].getX() * f));
            ls[x].setY((int) (lights[x].getY() * f));
            if (ptm.getFileType() == 3) {
                ls[x].setLight(lights[x].getLight());
            }
        }
        buff = new BufferedImage(w , h + 50, BufferedImage.TYPE_INT_RGB);
       // BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
      //  int ai[] = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        int ai[] = ((DataBufferInt) buff.getRaster().getDataBuffer()).getData();
        oper.performOperation(ai, ls, ptm, k, l, w, h, 0, 0, w, h);
        Graphics g = buff.getGraphics();
      //  g.setColor(Color.white);
      //  g.drawRect(0, 0, w + 4, h + 54);
      //  g.drawImage(img, 2, 2, w, h, null);
        g.setColor(Color.white);
        g.drawLine(2, h + 2, w + 2, h + 2);
         g.drawString(getDisplayText(), 10, h + 20);
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(name);
            ImageIO.write(buff, "jpeg", fileoutputstream);
            fileoutputstream.close();

            msg = (new StringBuilder()).append("Image has been saved as ").append(name).toString();
        } catch (Exception exp) {
            log.error(exp);
            msg = "error";
        } finally {
            buff=null;
           // img=null;
            ai=null;
            return msg;
        }

    }

    private String getDisplayText() {
          String s = "";
         float f = curWid / ptm.getWidth();
         String s1 = pixelTransformOp.getName();
         if(s1.equalsIgnoreCase("DiffuseGain"))
         {
         s = (new StringBuilder()).append("Effect: ").append(s1).append(" .       Gain:")
        .append(ptm.getDGain()).append("       ").toString();
         } else
         if(s1.equalsIgnoreCase("Specular"))
         {
         s = (new StringBuilder()).append("Effect: ").append(s1).append(" .      Shine:")
        .append(ptm.getKSpec())
        .append("        ").toString();
         s = (new StringBuilder()).append(s).append("Diffuse Shading:").append(ptm.getKDiff()).append("        Highlight Size:").append(ptm.getExp()).append("        ").toString();
         } else
         if(s1.equalsIgnoreCase("FlashLight"))
         {
         s = (new StringBuilder()).append("Effect: LocalLight .       Distance:").append(ptm.getZ()).append("     ").toString();
         } else
         if(s1.equalsIgnoreCase("SpotLight"))
         {
         s = (new StringBuilder()).append("Effect: ").append(s1).append(" .     Distance:").append(ptm.getZ()).append("     ").toString();
         } else
         {
         s = (new StringBuilder()).append("Effect: ").append(s1).append(" .      ").toString();
         }
         if(isAdditionalLight())
         {
         s = (new StringBuilder()).append(s).append("First Light: ").append(lights[0].getDisplay()).toString();
         s = (new StringBuilder()).append(s).append("Second Light: ").append(lights[1].getDisplay()).toString();
         } else
         {
         s = (new StringBuilder()).append(s).append(lights[0].getDisplay()).toString();
         }
         return s;
         
    }
}
