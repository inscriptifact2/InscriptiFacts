package isf.view;

import isf.controls.controllers.PTMFrameManager;
import isf.controls.utils.CursorManager;
import isf.controls.utils.GlassPaneManager;
import isf.controls.view.containers.PTMPanel;
import isf.ptm.controls.PTMWindow;
import isf.ptm.operations.*;
import isf.viewer.containers.ButtonPopUp;
import isf.viewer.containers.CloseActionMenu;
import isf.viewer.controls.*;
import isf.viewer.utils.SpringUtilities;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 4, 2007
 * Time: 12:55:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ControlsPopUp extends ButtonPopUp implements MouseListener, MouseMotionListener  {

    // CardLayout layout;
    private GeneralControlMenu gp = null;
    private DualLightsControlMenu dlp =null;
    private SpecularControlMenu sp = null;
    private DiffuseGainControlMenu dp = null;
    private ExtrapolateSourceMenu esm = null;
    private MViewControlMenu mvcm = null;
    // private EnvironmentControlMenu envp = new EnvironmentControlMenu();
    private CloseActionMenu cam = null;
    private ThumbnailControlMenu tcm = null;
    // private JPanel panel;
    private int height;
    private PTMWindow ptmw = null;
    private boolean showSP = false, distance = false, showDP = false, showES = false, showNone = false;


    public void setSpecularEnabled(boolean b) {
        if(sp==null)
            sp = new SpecularControlMenu();
        sp.setEnabled(b);
        showSP = b;


    }


    public void setDiffuseGainEnabled(boolean b) {
        if(dp==null)
            dp=new DiffuseGainControlMenu();
        dp.setEnabled(b);
        showDP = b;


    }

    public void setExtrapolateEnabled(boolean b) {
        if(esm==null)
            esm = new ExtrapolateSourceMenu();
        esm.setEnabled(b);
        showES = b;


    }


    public void setDistanceEnabled(boolean b) {
        distance = b;
    }

    /**
     * Creates a new instance of ControlsPopUp
     */
    public ControlsPopUp() {

        setBackground(new Color(0xd9d9d9));
        setForeground(new Color(0xAC5539));


    }

    public void releaseControls() {
        if(gp!=null){
        gp.releaseControlMenu();
        gp =null;
        }
        if(sp!=null){
        sp.releaseControlMenu();
        sp=null; 
        }
        if(dp!=null){
        dp.releaseControlMenu();
        dp=null;
        }
        if(dlp!=null){
        dlp.releaseControlMenu();
        dlp=null;
        }
        if(esm!=null){
        esm.releaseControlMenu();
        esm=null;
        }
        if(cam!=null){
        cam.releaseControlMenu();
        cam=null;
        }
        if(tcm!=null){
        tcm.releaseControlMenu();
        tcm=null;
        }
        if(mvcm !=null)
        {
            mvcm.releaseControlMenu();
            mvcm=null;
        }
        
    }

    

    private void construct() {
        removeAll();
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0xd9d9d9));
        mainPanel.setForeground(new Color(0xAC5539));
        this.setLayout(new BorderLayout());
         
        height = 0;
        mainPanel.setLayout(new SpringLayout());
        int count = 0;
        cam = new CloseActionMenu();
        cam.init();
        add(cam,"North");
        height += cam.getPreferredSize().height;
        //count++;
        if (((PTMPanel) ptmw).isAdditionalLight()) {
            dlp = new DualLightsControlMenu();
            height += dlp.getPreferredSize().height;
            dlp.setDistanceEnabled(distance);
            dlp.init();
            mainPanel.add(dlp);
            count++;
        } else {
            gp = new GeneralControlMenu();
            height += gp.getPreferredSize().height;
            gp.setDistanceEnabled(distance);
            gp.init();
            mainPanel.add(gp);
            count++;
        }
        if(ptmw.getPTM() instanceof isf.ptm.formats.MVRTI)
        {
            mvcm = new MViewControlMenu();
            height +=mvcm.getPreferredSize().height;
            mvcm.init();
            mainPanel.add(mvcm);
            count++;
        }

        if (showES) {
            //TODO: clean this up when we fix the extrapolation
            // esm.init();
            // add(esm);
            // height+=esm.getPreferredSize().height;
            //  count++;
        }
        if (showSP) {
           // sp = new SpecularControlMenu();
            sp.init();
            mainPanel.add(sp);
            height += sp.getPreferredSize().height;
            count++;
        } else if (showDP) {
           // dp = new DiffuseGainControlMenu();
            dp.init();
            mainPanel.add(dp);
            height += dp.getPreferredSize().height;
            count++;
        }
        tcm = new ThumbnailControlMenu();
        tcm.init();
        mainPanel.add(tcm);
        height += tcm.getPreferredSize().height;
        count++;
        add(mainPanel,"Center");
        
        //System.out.println("Count:"+count+"::hegiht:"+height);
           
        SpringUtilities.makeCompactGrid(mainPanel, count, 1, // rows,
                // cols
                1,1, // initX, initY
                1, 1); // xPad, yPad

        /* CardLayout cl = (CardLayout) (panel.getLayout());
          height = 0;
          height += gp.getPreferredSize().getHeight();
          // cl.show(panel, (String)evt.getItem());
          if (showSP) {
              cl.show(panel, "specular");

              height += sp.getPreferredSize().getHeight();
          } else if (showDP) {
              cl.show(panel, "diffuse");

              height += dp.getPreferredSize().getHeight();
          } else if (showNone) {
              cl.show(panel, "dummy");
              height += em.getPreferredSize().getHeight();

          }   else if (showES) {
              cl.show(panel, "extrapolate");
              height += esm.getPreferredSize().getHeight();

          }/ *else if (envp.isEnabled()) {
              cl.show(panel, "environ");
              height += envp.getPreferredSize().getHeight();

          }*/


    }

    public void init() {
        addMouseListener(this);
        addMouseMotionListener(this);
        p = new Point();
        ptmw = PTMFrameManager.getInstance().getPTMWindow();
        Operation op = ((PTMPanel) ptmw).getPixelTransformOp();


        if (op instanceof DirectionalLightOperation) {
            setSpecularEnabled(false);
            setDiffuseGainEnabled(false);
            setDistanceEnabled(false);
        } else if (op instanceof DiffuseGainOperation) {
            setSpecularEnabled(false);
            setDiffuseGainEnabled(true);
            setDistanceEnabled(false);

        } else if (op instanceof SpecularOperation) {
            setSpecularEnabled(true);
            setDiffuseGainEnabled(false);
            setDistanceEnabled(false);

        } else if (op instanceof NormalMapOperation) {
            setSpecularEnabled(false);
            setDiffuseGainEnabled(false);
            setDistanceEnabled(false);
        } else if (op instanceof SpotLightOperation) {
            setSpecularEnabled(false);
            setDiffuseGainEnabled(false);
            setDistanceEnabled(true);
        }
        if (((PTMPanel) ptmw).isExtrapolation()) {
            setExtrapolateEnabled(true);

        }
        construct();

    }


    public Dimension getPreferredSize() {
        int width = 265;
        if(cam!=null)
           width =  cam.getPreferredSize().width;
        return new Dimension(width, height);
    }

    private Point start, end;
    private boolean dragging = false;

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        // if(e.isControlDown()){
        start = e.getPoint();
        temp = e.getPoint();
       
        GlassPaneManager gp1 = GlassPaneManager.getInstance();
        xposition = this.getX();
        yposition = this.getY();
        Dimension dimension = this.getSize();
        p.x = xposition - mouseX;
        p.y = yposition - mouseY;
        
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        graphics2d.setXORMode(Color.yellow);
        if(old!=null)
            graphics2d.draw(old);
        old = new Rectangle(p.x, p.y, dimension.width, dimension.height);
            graphics2d.draw(old);
       
    }

    public void mouseReleased(MouseEvent e) {
        if (dragging) {
            end = e.getPoint();
            Rectangle l = this.getBounds();
            setBounds(l.x + (end.x - start.x), l.y + (end.y - start.y), l.width, l.height);
            
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            CursorManager.getInstance().changeCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            dragging = false;
            GlassPaneManager gp1 = GlassPaneManager.getInstance();
            gp1.updateControlBounds(this.getBounds());
        // QuickViewGP quickviewgp = qvgp;
       Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
        old = null;
        }
        start = null;
        end = null;
        
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        dragging = true;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        CursorManager.getInstance().changeCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        GlassPaneManager.getInstance().updateControlBounds(this.getBounds());
        GlassPaneManager gp1 = GlassPaneManager.getInstance();
        Dimension dimension = this.getSize();
        p.x = ((e.getX() - temp.x) + xposition) - mouseX;
        p.y = ((e.getY() - temp.y) + yposition) - mouseY;
        Rectangle rectangle = new Rectangle(p.x, p.y, dimension.width, dimension.height);
        Graphics2D graphics2d = (Graphics2D) gp1.getGraphics();
        graphics2d.setXORMode(Color.yellow);
        graphics2d.draw(old);
        graphics2d.draw(rectangle);
        old = rectangle;
        
        
        
        
    }

    public void mouseMoved(MouseEvent e) {
        if (dragging)
            end = e.getPoint();
    }
    
    private Rectangle old;
    private Point p, temp;
    private int mouseX, mouseY, xposition,yposition;


}



