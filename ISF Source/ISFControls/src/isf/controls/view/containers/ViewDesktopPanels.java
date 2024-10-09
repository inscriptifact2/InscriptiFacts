package isf.controls.view.containers;

//import com.isf.common.Debug;

import isf.common.Debug;
import isf.common.utils.LogManager;
import isf.controls.controllers.ActiveXListenerManager;
import isf.controls.models.DesktopDataObject;
import isf.common.utils.InscriptConfigLoader;
import isf.controls.view.controls.InscriptButton;
import isf.controls.view.controls.ViewDesktopButton;
import isf.controls.view.gui.VerticalLayout;
import isf.controls.view.listeners.ActivationListener;
import isf.controls.view.listeners.ActiveXListener;
import isf.controls.view.listeners.CWListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

public class ViewDesktopPanels extends JPanel
        implements ActiveXListener, CWListener {

    private ActionListener al;
    public ViewDesktopButton emptyButton;
    public InscriptButton catButton;
    public InscriptButton dublincatButton;
    private ViewDesktopButton button;
    private JPanel emptyPanel;
    private JPanel dataPanel;
    public JPanel catalogPanel;
    private Vector imageList;
    public int activeIndex;
    private ActivationListener acl;
    private boolean notified;
    private int index;
    boolean border1;
    boolean border2;
    private Vector selectedData;
    public String mode, appName;
    Logger logger = LogManager.getLogger(isf.controls.view.containers.ViewDesktopPanels.class);

    public ViewDesktopPanels() {
        appName = InscriptConfigLoader.getInstance().getProperty("appName");
        imageList = new Vector();
        notified = false;
        index = 1;
        border1 = true;
        border2 = false;
        button = new ViewDesktopButton("", "images/VD_blank.jpg", border1);
        init();
        mode = "Cataloging";
        ActiveXListenerManager.registerActivationListener( this);
        //System.out.println("\n\n============ActiveXListenerManager==============al " + ActiveXListenerManager.al);
    }

    public void init() {
        setLayout(new VerticalLayout());
        setBackground(Color.black);
        emptyPanel = new JPanel();
        emptyPanel.setLayout(new VerticalLayout());
        emptyPanel.setBackground(Color.black);
        catalogPanel = new JPanel();
        catalogPanel.setLayout(new FlowLayout(0, 0, 0));
        catalogPanel.setBackground(Color.black);
        dataPanel = new JPanel();
        dataPanel.setLayout(new VerticalLayout());
        dataPanel.setBackground(Color.black);

        if (appName != null && appName.equalsIgnoreCase("InscriptiFact")) {
            catalogPanel.add(catButton = new InscriptButton(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/VD_cataloging_active.jpg")));
            catButton.setBorder(BorderFactory.createLineBorder(Color.white));
            catButton.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/VD_cataloging_select.jpg"));
            catButton.setActionCommand("Cataloging");
            catButton.addActionListener(al);
            dataPanel.add(dublincatButton = new InscriptButton(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/VD_dublincore_active.jpg")));
            dublincatButton.setBorder(BorderFactory.createLineBorder(Color.white));
            dublincatButton.setSelectedIcon(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/VD_dublincore_select.jpg"));
            dublincatButton.setActionCommand("DublinCoreCataloging");
            dublincatButton.addActionListener(al);
        }
        emptyPanel.add(emptyButton = new ViewDesktopButton("", isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/VD_blankcurve.jpg"), border2));
        add(dataPanel);
        add(emptyPanel);
    }


    public void addDataObject(DesktopDataObject desktopdataobject) {
        imageList.addElement(desktopdataobject);
        dataPanel.add(button = new ViewDesktopButton(desktopdataobject, "images/VD_blank.jpg", border1));
        //button.setActionCommand(index + "");
        button.setActionCommand(desktopdataobject.getDisplayName() );
        index++;
        if (al != null) {
            button.addActionListener(al);
        }
        revalidate();
        validate();
        repaint();
    }

    public void addDummy(String type) {
        //  imageList.addElement(desktopdataobject);
        dataPanel.add(new ViewDesktopButton(type, "images/VD_blank.jpg", true, true));
       // index++;
       // imageList.addElement(null);
        revalidate();
        validate();
        repaint();
    }

    public Vector getDataList() {
        return imageList;
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        if (appName!=null && appName.equalsIgnoreCase("InscriptiFact")) {
            catButton.addActionListener(actionlistener);
            dublincatButton.addActionListener(actionlistener);
        }
    }

    public void addActivationListener(ActivationListener activationlistener) {
        acl = activationlistener;
    }

    public Insets getInsets() {
        return new Insets(0, 14, 0, 0);
    }

    public Dimension getPreferredSize() {
        int i = imageList.size();
        Dimension dimension;
        if (i != 0) {
            dimension = button.getPreferredSize();
        } else {
            dimension = emptyButton.getPreferredSize();
        }
        return new Dimension(dimension.width + 14, dimension.height);
    }

    public void minimized(String s) {
        // System.out.println(" \n ...\n in minimized() " + s);
        java.awt.Component acomponent[] = dataPanel.getComponents();
        for (int i = 1; i < acomponent.length; i++) {
            try {
                ViewDesktopButton viewdesktopbutton = (ViewDesktopButton) acomponent[i];
                if (viewdesktopbutton.getActionCommand().equalsIgnoreCase(s)) {
                    //System.out.println("display name for min" + s);
                    viewdesktopbutton.getObject().setAdded(false);
                    viewdesktopbutton.setSelected(false);
                }
            } catch (Exception exception) {
            }
        }

    }

    public void activated(String s) {
        // System.out.println(" \n ...\n in activated() " + s);
        java.awt.Component acomponent[] = dataPanel.getComponents();
        for (int i = 1; i < acomponent.length; i++) {
            try {
                ViewDesktopButton viewdesktopbutton = (ViewDesktopButton) acomponent[i];
                if (viewdesktopbutton.getActionCommand().equalsIgnoreCase(s)) {
                    viewdesktopbutton.setSelected(true);
                    activeIndex =  imageList.indexOf(viewdesktopbutton.getObject());
                } else {
                    viewdesktopbutton.setSelected(false);
                }
            } catch (Exception exception) {
            }
        }

        acl.activationChanged();
    }

    public void setSelectedData(Vector vector) {
        selectedData = vector;
    }

    public Hashtable getActiveImageData() {
       // System.out.println("    imagelist.size()    " + imageList.size() + "    " + activeIndex+":: Selected:"+selectedData.size());
        DesktopDataObject desktopdataobject = (DesktopDataObject) imageList.elementAt(activeIndex);
        int i = 0;
        for (int j = selectedData.size(); i < j; i++) {
            Hashtable hashtable = (Hashtable) selectedData.elementAt(i);
            String s = desktopdataobject.getPhotoIdentificationNo();
            if (s.equals(hashtable.get("PHOTOGRAPHIDENTIFICATIONNO"))) {
                logger.debug("getting data for:"+s);
                return hashtable;
            }
        }

        return null;
    }

    public void deleted(String s) {
        Debug.debug(" \n ...\n in deleted() " + s);
        java.awt.Component acomponent[] = dataPanel.getComponents();
        for (int i = 1; i < acomponent.length; i++) {
            try {
                ViewDesktopButton viewdesktopbutton = (ViewDesktopButton) acomponent[i];
                if (viewdesktopbutton.getActionCommand().equalsIgnoreCase(s)) {
                    imageList.remove(viewdesktopbutton.getObject());
                    dataPanel.remove(viewdesktopbutton);
                    revalidate();
                    validate();
                    repaint();
                    break;
                }
            } catch (Exception exception) {
            }
        }

    }

    public void setNotification(String s) {
        if (s.equalsIgnoreCase("ControlReady")) {
            notified = true;
        }
    }

    public boolean getNotification() {
        return notified;
    }

    public void buttonSelected(String s) {
        if (s.equals("Catalouge")) {
            catButton.setSelected(true);
            dublincatButton.setSelected(false);
            mode = "Cataloging";
        } else if (s.equals("DoublinCoreCatalouge")) {
            catButton.setSelected(false);
            dublincatButton.setSelected(true);
            mode = "DublinCoreCataloging";
        }
    }


    public void reset() {
         
        dataPanel.removeAll();
        if (appName.equalsIgnoreCase("InscriptiFact"))
            dataPanel.add(dublincatButton);
        imageList = new Vector();
        selectedData = new Vector();
        revalidate();
        validate();
    
}

    
}
