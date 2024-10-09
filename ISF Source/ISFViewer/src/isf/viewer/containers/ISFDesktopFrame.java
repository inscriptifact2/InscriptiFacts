package isf.viewer.containers;
//import com.isf.common.Debug;
import isf.common.utils.CacheManagerConstants;
import isf.common.utils.LogManager;
import isf.controls.controllers.*;
import isf.controls.models.DesktopDataObject;
import isf.controls.plaf.DesktopTheme;
import isf.controls.utils.CursorManager;
import isf.controls.utils.GlassPaneManager;
import isf.common.utils.InscriptConfigLoader;
import isf.controls.utils.ResolutionManager;
import isf.controls.view.containers.ISFSplitPane;
import isf.controls.view.containers.ProgressPane;
import isf.controls.view.containers.ViewDesktop;
import isf.controls.view.controls.SidDesktop;
import isf.controls.view.listeners.CursorListener;
import isf.controls.view.listeners.ImageLoaderListener;
import isf.controls.view.listeners.KeyHandler;
import isf.controls.view.listeners.ViewDesktopListener;
import isf.ptm.formats.PTM;
import isf.viewer.utils.ClientSocket;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import org.apache.log4j.Logger;

public class ISFDesktopFrame extends JFrame implements ImageLoaderListener, WindowListener, CursorListener {

    public ViewDesktop vd;
    //public ActivexPanel activexPanel;
    public SidDesktop activexPanel;
    public ISFSplitPane jp;
    public ProgressPane progressPane;
    /*  private String fileName;
     private JPanel panel;*/
    //private Frame parent;
    private int index;
    static Logger log = null;
  //  private static int port = 1725;
    private static ServerSocket listenSocket =null;

    public ISFDesktopFrame() {

        //  LogManager.initialize("log4j.txt");
        log = LogManager.getLogger(isf.viewer.containers.ISFDesktopFrame.class);

        ImageLoaderManager.getInstance().registerListener(this);
        CursorManager.getInstance().addCursorListener(this);

        index = 1;
        // setLayout(new BorderLayout(0, 0));
        vd = new ViewDesktop(this);
        activexPanel = new SidDesktop();
        activexPanel.reset();
        vd.addViewDesktopListener(activexPanel);
        vd.addDummy("RTI Images");
        Dimension dimension = vd.getPreferredSize();   //vd.getCatButtonSize();

        jp = new ISFSplitPane(1, false, vd, activexPanel, dimension.width, 10);
        java.awt.Rectangle rectangle = activexPanel.getBounds();
        activexPanel.resize(rectangle);
        jp.setDivider(10);
        jp.setOneTouchExpandable(true);
        // jp.setDividerLocation(dimension.width);


        IsfSplitManager.getInstance().setSplitPane(jp, 1);
        getContentPane().add(jp, "Center");
        progressPane = new ProgressPane();
        progressPane.registerBar();
        getContentPane().add(progressPane, BorderLayout.SOUTH);
        GlassPaneManager.getInstance().setUpGlasssPane(this);
        addWindowListener(this);
        PTMFrameManager.getInstance().setDiffuseOperation(true, false);
       

    }
    
     

    public void addDesktopData(DesktopDataObject desktopdataobject) {

        vd.addDataObject(desktopdataobject);

    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }

    public void doResize() {
        //System.out.println("Doing Resize");
        activexPanel.setSize(800, 400);
    }

    public void putImage(DesktopDataObject desktopdataobject) {


        jp.setOneSize(vd.getPreferredSize().width);
        if (desktopdataobject.isAdded()) {
            //System.out.println("desktopdataobject.getFileName()   "+desktopdataobject.getFileName());
            if (activexPanel.getActive().equals(desktopdataobject.getFileName())) {
                JOptionPane.showMessageDialog(this, "Image is already shown in the control", "Selection Error", 0);
            } else {
                activexPanel.setActive(desktopdataobject.getFileName());
            }
            return;
        }
        ViewDesktopListener viewdesktoplistener = vd.getVDL();
        if (viewdesktoplistener != null) {
            String s = viewdesktoplistener.addImage(desktopdataobject); // index+""
            //System.out.println(s);
            if (s.equalsIgnoreCase("ERROR")) {
                JOptionPane.showMessageDialog(this, " Maximum 5 images can be opened in viewer.", "Too Many Images", 0);
                ActiveXListenerManager.deleted(desktopdataobject.getDisplayName());
            } else if (s.equalsIgnoreCase("SUCCESS")) {
                desktopdataobject.setAdded(true);
                index++;
            }
        }

    }

    public void setSelectedData(Vector vector) {
        vd.setSelectedData(vector);
    }

    public void resetCursor() {
        CursorManager.changeCursor(0);

    }

    public void reset() {
        vd.reset();
        activexPanel.reset();
    }

    public static void main(String args[]) {
    
        Properties sys = System.getProperties();
        String os = sys.getProperty("os.name");
       InscriptConfigLoader.setAppName("VIEWER");
        System.out.println("Called main method first ");
        InscriptConfigLoader.initialize("InscriptViewer.ini");
        String version = InscriptConfigLoader.getInstance().getProperty("version");
        String strTitle = InscriptConfigLoader.getInstance().getProperty("title");
        String title = strTitle + " - "+version ;  
        System.out.println("Called main method second ");
       // String titleString = "Standalone RTI Viewer - ";
        if (os != null && ((os.toUpperCase().indexOf("MAC OS") > -1)|| (os.toUpperCase().indexOf("OS X") > -1))) {
            sys.put("com.sun.media.jai.disableMediaLib", "true");
            sys.put("com.apple.mrj.application.apple.menu.about.name", title);
            System.setProperties(sys);
        }


        System.out.println("tempdir:"+sys.getProperty("java.io.tmpdir"));

        int len = args.length;
        String mode = "standalone";
        String fileName = "";
        boolean loadFile = false;
        String param = "";
        int port = 1725;

     
        if (len < 1) {
            //no args launch plain standalone version
            mode = "standalone";
            //titleString = "Standalone RTI Viewer";
        } else { //if (len == 1)
            //file to be loaded in standalone mode or if len == web start progressive viewer
            param = args[0].toLowerCase();
            if (param.equalsIgnoreCase("web")) {
                mode = "web";
                // titleString = "Progressive RTI Viewer";
            } else if (param.equalsIgnoreCase("standalone")) {
                mode = "standalone";
                // titleString = "Standalone RTI Viewer";
            } else {
                //must be a file try to launcg it in standalone mode:
                mode = "standalone";
                //  titleString = "Standalone RTI Viewer";
                fileName = param;
                loadFile = true;
            }
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
                
            }
        } /*else if (len > 1) {
         JOptionPane.showMessageDialog(dv, "Wrong number of arguments. Please provide only 1 argument", "Error", 0);
         System.exit(0);
         }*/

        /*if (os != null && os.toUpperCase().indexOf("MAC OS")>-1) {
         sys.put("com.sun.media.jai.disableMediaLib", "true");
         System.setProperty("com.apple.mrj.application.apple.menu.about.name", titleString);
         System.setProperties(sys);
         }*/
        ImageIO.scanForPlugins();




        try {

            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.getDefaults().put("ToolTip.background", new ColorUIResource(new Color(252, 250, 182)));
            UIManager.getDefaults().put("InternalFrame.closeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/exit.gif")));
            UIManager.getDefaults().put("InternalFrame.minimizeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/max.gif")));
            UIManager.getDefaults().put("InternalFrame.maximizeIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/norm.gif")));
            UIManager.getDefaults().put("InternalFrame.iconifyIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/min.gif")));
            UIManager.getDefaults().put("InternalFrame.icon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/isf.jpg")));
            UIManager.getDefaults().put("InternalFrame.titleFont", new FontUIResource(new Font("Arial", 3, 10)));
            UIManager.getDefaults().put("InternalFrame.activeTitleForeground", new ColorUIResource(new Color(0xac5539)));
            UIManager.getDefaults().put("InternalFrame.activeTitleBackground", new ColorUIResource(new Color(0xd9d9d9)));
            UIManager.getDefaults().put("InternalFrame.inactiveTitleBackground", new ColorUIResource(new Color(0xd9d9d9)));
            UIManager.getDefaults().put("InternalFrame.inactiveTitleForeground", new ColorUIResource(Color.black));

            UIManager.getDefaults().put("ScrollBar.background", new ColorUIResource(new Color(0xd9d9d9)));
            UIManager.getDefaults().put("ScrollBar.borderColor", new ColorUIResource(Color.gray));
            UIManager.getDefaults().put("ScrollBar.buttonBorder", new LineBorder(Color.black, 1));
            UIManager.getDefaults().put("ScrollBar.disabledBorder", new LineBorder(Color.black, 1));
            UIManager.getDefaults().put("ScrollBar.thumb", new ColorUIResource(new Color(0xa7a7a7)));
            UIManager.getDefaults().put("ScrollBar.trackBackground", new ColorUIResource(new Color(0xa7a7a7)));
            UIManager.getDefaults().put("ScrollBar.border", null);

            // UIManager.getDefaults().put("Slider.horizontalThumbIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/thumb.JPG")));
            // UIManager.getDefaults().put("Slider.verticalThumbIcon", new IconUIResource(ResolutionManager.getInstance().loadImage("images/thumb.JPG")));
            UIManager.getDefaults().put("ProgressBar.selectionBackground", Color.BLUE);
            UIManager.getDefaults().put("ProgressBar.selectionForeground", Color.WHITE);
            UIManager.getDefaults().put("ProgressBar.foreground", new Color(84, 146, 87));
            UIManager.getDefaults().put("ProgressBar.repaintInterval", new Integer(50));
            UIManager.getDefaults().put("ProgressBar.cycleTime", new Integer(5000));

            UIManager.put("MenuItemUI", "isf.controls.plaf.InscriptMenuItemUI");
            UIManager.put("SplitPaneDividerUI", "javax.swing.plaf.basic.BasicSplitPaneDivider");



        } catch (Exception ie) {
            ie.printStackTrace();
            log.error(ie);
        }
        DesktopTheme dt = new DesktopTheme(new Color(0xd9d9d9));
        MetalLookAndFeel.setCurrentTheme(dt);

        ISFDesktopFrame dv = new ISFDesktopFrame();
        /*   if (len > 1) {
         JOptionPane.showMessageDialog(dv, "Wrong number of arguments. Please provide only 1 argument", "Error", 0);
         System.exit(0);
         }*/





       
        dv.setExtendedState(JFrame.MAXIMIZED_BOTH);
       // ISFDesktopFrame.port = port;
        
        //  dv.setSize(600,600);

       // dv.setTitle(titleString + " " + version + er);
        dv.setTitle(title);
        dv.setIconImage(isf.controls.utils.ResolutionManager.getInstance().loadImage("images/isf.jpg").getImage());
        ISFGarbageCollector gc = new ISFGarbageCollector();
        dv.setVisible(true);
        
        if (loadFile && ISFDesktopFrame.validRTIFile(fileName)) {
            log.debug("Loading File:" + fileName);
            File f = new File(fileName);
            String path = f.getAbsolutePath();
            String name = f.getName();
            int ind = path.indexOf(name);
            // String curDir = path.substring(0, ind);
            DesktopDataObject ddo = new DesktopDataObject(name, name, path, name);
            ImageLoaderManager.getInstance().getLoader().addDesktopData(ddo);
            ImageLoaderManager.getInstance().getLoader().putImage(ddo);
        }
        dv.startServer(port);

    }

    public KeyHandler getKeyHandler() {
        return activexPanel.sidtoolbar;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        try{
        listenSocket.close();
        log.debug("closed server socket");
        }catch(Exception exp){
           log.error(exp);
        }
             
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void putImage(DesktopDataObject desktopdataobject, PTM ptm) {
        ViewDesktopListener viewdesktoplistener = vd.getVDL();
        if (viewdesktoplistener != null) {
            String s = viewdesktoplistener.addImage(desktopdataobject, ptm);//, index + ""
            //System.out.println(s);
            if (s.equalsIgnoreCase("ERROR")) {
                JOptionPane.showMessageDialog(this, " Maximum 5 images can be opened in viewer.", "Too Many Images", 0);
                ActiveXListenerManager.deleted(desktopdataobject.getDisplayName());
            } else if (s.equalsIgnoreCase("SUCCESS")) {
                desktopdataobject.setAdded(true);
                index++;
            }
        }
    }

    public void addDesktopData(DesktopDataObject desktopdataobject, PTM ptm) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    Cursor oldCursor = null;

    public void changeCursor(int c) {
        if (c == -1) {
            if (oldCursor == null) {
                oldCursor = Cursor.getDefaultCursor();
            }
            setCursor(oldCursor);
            oldCursor = null;
        } else {
            oldCursor = getCursor();
            setCursor(new Cursor(c));

        }
    }

    public void changeCursor(Cursor r) {
        if (r != null) {
            oldCursor = getCursor();
            setCursor(r);
        }
    }

    public Rectangle getFrameBounds() {
        return getBounds();
    }

    public static boolean validRTIFile(String fname) {
        if (fname.endsWith(".ptm") || fname.endsWith(".rti") || fname.endsWith(".hsh") || fname.endsWith("mview")) {
            File f = new File(fname);
            return f.exists() && f.isFile();
        } else {
            return false;
        }
    }

    private void startServer(int port) {
         
         try{
              listenSocket =    new ServerSocket(port, 1, InetAddress.getLoopbackAddress());
              log.debug("Server Socket Started");
         }catch(IOException exp){
             log.debug(exp);
         }
         
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                   
                    String line = "";
                    while (true) {
                        Socket socket = listenSocket.accept();
                     /*  ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        line = (String)(ois.readObject());
                        log.debug(line+" is being received");
                        if (ISFDesktopFrame.validRTIFile(line)) {
                            log.debug("Loading File:" + line);
                            File f = new File(line);
                            String path = f.getAbsolutePath();
                            String name = f.getName();
                            int ind = path.indexOf(name);
                            // String curDir = path.substring(0, ind);
                            DesktopDataObject ddo = new DesktopDataObject(name, name, path, name);
                            ImageLoaderManager.getInstance().getLoader().addDesktopData(ddo);
                            ImageLoaderManager.getInstance().getLoader().putImage(ddo);
                        }else{
                            log.debug("file name is wrong:"+line+"----");
                        }
                        OutputStream out = socket.getOutputStream();
                        ObjectOutputStream  oos = new ObjectOutputStream(out);
                        oos.writeObject(("File "+line+" processed."));
                       // writer.newLine();
                        ois.close();
                        oos.close();
                        out.close();
                       // socket.close();*/
                       new  ClientSocket(socket).start();
                     }

                } catch (Exception ex) {
                    log.debug(ex);
                }
            }
        
        };
       t.start();
    }




    
    
}
