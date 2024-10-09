package com.isf.app.view.containers;

import com.isf.app.controllers.ThumbContentManager;
import com.isf.app.controllers.ThumbDeselectorManager;
import com.isf.app.models.ImageThumbData;
import com.isf.app.models.ThumbData;
import com.isf.app.view.controls.AnchorButton;
import com.isf.app.view.controls.CatalougeButton;
import com.isf.app.view.controls.ToggleButton;
import com.isf.app.view.listeners.QuickViewListener;
import com.isf.app.view.listeners.ThumbDeselectorListener;
import com.isf.app.view.listeners.ThumbHeaderSelectionListener;
import com.isf.app.view.listeners.ThumbListener;
import isf.common.utils.LogManager;
import isf.controls.controllers.ImageLoaderManager;
import isf.controls.view.containers.TrimmedPanel;
import isf.controls.view.listeners.SortListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

public class ThumbViewerPanel extends TrimmedPanel
        implements ActionListener, ThumbListener, ThumbHeaderSelectionListener, ThumbDeselectorListener {

    public ThumbViewer tv;
    private TrimmedPanel top;
    private TrimmedPanel lp;
    Logger logger = LogManager.getLogger(com.isf.app.view.containers.ThumbViewerPanel.class);
    private JLabel rp;
    private Vector ranges;
    private Vector data;
    private Vector selectedData;
    private ToggleButton filmType, datePhoto, magCode;
    public CatalougeButton next;
    public CatalougeButton back;
    public ImageIcon ti;
    private isf.controls.utils.ResolutionManager rm;

    public ThumbViewerPanel(Insets insets, ActionListener al) {
        super(insets);
        selectedData = new Vector();
        setLayout(new BorderLayout(0, 0));
        selectedData = new Vector();
        rm = isf.controls.utils.ResolutionManager.getInstance();
        ti = rm.getImageIcon("images/dublininactive.jpg");
        top = new TrimmedPanel(new Insets(0, 0, 0, 0)) {

            public Dimension getPreferredSize() {
                return new Dimension(ti.getIconWidth() * 2, ti.getIconHeight());
            }

        };
        top.setLayout(new FlowLayout(0, 15, 0));
        top.setBackground(new Color(0xd9d9d9));
        JLabel jlabel = new JLabel("SORT BY:");
        jlabel.setFont(new Font("Arial", 1, rm.getFontSize(16)));
        top.add(jlabel);
        ToggleButtonPanel togglebuttonpanel = new ToggleButtonPanel(new Insets(0, 0, 0, 0));
        datePhoto = new ToggleButton("images/Photodate.jpg");
        datePhoto.setActionCommand("Photodate");
        datePhoto.setSelectedIcon(rm.getImageIcon("images/Photodate_select.jpg"));
        datePhoto.addActionListener(this);
        datePhoto.setProperties(false);
        togglebuttonpanel.add(datePhoto);
        filmType = new ToggleButton("images/Filmtype.jpg");
        filmType.setActionCommand("Filmtype");
        filmType.setSelectedIcon(rm.getImageIcon("images/Filmtype_select.jpg"));
        filmType.addActionListener(this);
        filmType.setProperties(false);
        togglebuttonpanel.add(filmType);
        magCode = new ToggleButton("images/Magnification.jpg");
        magCode.setActionCommand("Magnification");
        magCode.setSelectedIcon(rm.getImageIcon("images/Magnification_select.jpg"));
        magCode.addActionListener(this);
        magCode.setProperties(false);
        togglebuttonpanel.setBackground(new Color(0xd9d9d9));
        togglebuttonpanel.add(magCode);
        top.add(togglebuttonpanel);
        add(top, "North");
        tv = new ThumbViewer() {

            public Dimension getPreferredSize() {
                return new Dimension(super.ti.getIconWidth() * 2, super.ti.getIconHeight());
            }

        };
        tv.addThumbListener(this);
        add(tv, "Center");
        TrimmedPanel trimmedpanel = new TrimmedPanel(new Insets(0, 0, 0, 0)) {

            public Dimension getPreferredSize() {
                return new Dimension(ti.getIconWidth() * 2, ti.getIconHeight());
            }

        };
        trimmedpanel.setLayout(new BorderLayout(0, 0));
        double ad[] = rm.getRatio();
        lp = new TrimmedPanel(new Insets(0, 0, 0, 0));
        lp.setBackground(new Color(0xd9d9d9));
        lp.setLayout(new FlowLayout(0, (int) (ad[0] * 5D), 0));
        trimmedpanel.add(lp, "West");
        trimmedpanel.setBackground(new Color(0xd9d9d9));
        rp = new JLabel();

        lp.add(rp);
       
        next = new CatalougeButton("images/next.gif");          //todo next.jpg
        next.setActionCommand("next");
        next.addActionListener(al);
        back = new CatalougeButton("images/back.gif");      //todo back.jpg
        back.setActionCommand("back");
        back.addActionListener(al);
        TrimmedPanel trimmedpanel1 = new TrimmedPanel(new Insets(0, 0, 0, 0));
        trimmedpanel1.setLayout(new FlowLayout(2, (int) (ad[0] * 15D), (int) (ad[1] * 8D)));
        trimmedpanel1.setBackground(new Color(0xd9d9d9));
        trimmedpanel1.add(back);
        trimmedpanel1.add(next);
        trimmedpanel.add(trimmedpanel1, "East");
        add(trimmedpanel, "South");
        ThumbDeselectorManager.getInstance().addThumbDeselectorListener(this);
    }

    public void addQuickViewListener(QuickViewListener quickviewlistener) {
        tv.addQuickViewListener(quickviewlistener);
    }


    public void setLimits(Vector items) {
        data = items;
      //  ((ToggleButtonPanel) top.getComponent(1)).reset();
    }


    public void addThumbHeaderSelectionListener(ThumbHeaderSelectionListener thumbheaderselectionlistener) {
        tv.addThumbHeaderSelectionListener(thumbheaderselectionlistener);
    }

    
    public Vector getData() {
        return data;
    }
/*
    private void buildAnchorButtons() {
        Component acomponent[] = lp.getComponents();
        for (int i = 1; i < acomponent.length; i++) {
            if (acomponent[i] != rp) {
                lp.remove(acomponent[i]);
            }
        }

        if (ranges.size() <= 1) {
            //   goto1.setEnabled(false);
            back.setEnabled(false);
            next.setEnabled(false);
        } else {
            //     goto1.setEnabled(false);
            back.setEnabled(false);
            next.setEnabled(true);
        }
        int j = 0;
        for (int k = ranges.size(); j < k; j++) {
            p1 = new AnchorButton((j + 1) + "");
            p1.setRange((String) ranges.elementAt(j));
            p1.addActionListener(this);
            if (j == 0) {
                p1.setSelected(true);
            }
            lp.add(p1);
        }

        lp.validate();
        validate();
        lp.repaint();
    }
*/
    public void actionPerformed(ActionEvent actionevent) {
        SortListener sl = ImageLoaderManager.getInstance().getSorter();

        if (actionevent.getActionCommand().equals("Photodate")) {
            sl.sort(DPCODE);
        } else if (actionevent.getActionCommand().equals("Filmtype")) {
            sl.sort(FTCODE);
        } else if (actionevent.getActionCommand().equals("Magnification")) {
            sl.sort(MGCODE);
        } 
        else {
            switchOffAnchors();
            int i = 0;
            for (int j = ranges.size(); i < j; i++) {
                if (actionevent.getActionCommand().equals((i + 1) + "")) {
                    AnchorButton anchorbutton = (AnchorButton) actionevent.getSource();
                    anchorbutton.setSelected(true);
                //    tempRange = anchorbutton.getRange();
                //    anchorSelected = true;
                }
            }

        }
    }
/*
    private void enableNextAnchor() {
        Component acomponent[] = lp.getComponents();
        int i = 1;
        for (int j = acomponent.length; i < j; i++) {
            AnchorButton anchorbutton = (AnchorButton) acomponent[i];
            if (!anchorbutton.isSelected()) {
                continue;
            }
            if (i < acomponent.length - 1) {
                ((AnchorButton) acomponent[i + 1]).setSelected(true);
                anchorbutton.setSelected(false);
            }
            break;
        }

    }

    private void enablePrevAnchor() {
        Component acomponent[] = lp.getComponents();
        int i = 1;
        for (int j = acomponent.length; i < j; i++) {
            AnchorButton anchorbutton = (AnchorButton) acomponent[i];
            if (!anchorbutton.isSelected()) {
                continue;
            }
            if (i > 1) {
                ((AnchorButton) acomponent[i - 1]).setSelected(true);
                anchorbutton.setSelected(false);
            }
            break;
        }

    }
*/
    private void switchOffAnchors() {
        Component acomponent[] = lp.getComponents();
        int i = 0;
        for (int j = acomponent.length; i < j; i++) {
            if (acomponent[i] instanceof AnchorButton) {
                ((AnchorButton) acomponent[i]).setSelected(false);
            }
        }

    }

    public Vector getThumbViewerData() {
        Vector vector = new Vector();
        for (int i = 0; i < data.size(); i++) {
            Hashtable hashtable = (Hashtable) data.elementAt(i);
            ImageThumbData imagethumbdata = new ImageThumbData();
            imagethumbdata.setHeader((String) hashtable.get("THUMBNAILHEADER"));
            imagethumbdata.setLabel((String) hashtable.get("ISFDIGITALOBJECTIDENTIFIER"));
            imagethumbdata.setPhotoIdentificationNumber((String) hashtable.get("PHOTOGRAPHIDENTIFICATIONNO"));
            imagethumbdata.setArchivalFileSize((String) hashtable.get("ARCHIVALFILESIZE"));
            imagethumbdata.setActualSidSize((String) hashtable.get("ACTUALSIDSIZE"));
            imagethumbdata.setPhotoTitle((String) hashtable.get("PHOTODESCRIPTIVETITLE"));
            imagethumbdata.setIcon((ImageIcon) hashtable.get("IMAGETHUMBNAIL"));
            String typecode = (String) hashtable.get("FILMTYPECODE");
            String format = (String) hashtable.get("DIGITALOBJECTFORMAT");
            if (format!=null && format.trim().equalsIgnoreCase("image/ptm"))
                imagethumbdata.setType("RTI");
            else if (format!=null && format.trim().equalsIgnoreCase("image/hsh"))
                imagethumbdata.setType("HSH");
            else
                imagethumbdata.setType("SID");
            vector.addElement(imagethumbdata);
        }

        return vector;
    }

    public Vector getSelectedData() {
        return selectedData;
    }

    public void thumbSelected(ThumbData thumbdata) {

        if (selectedData.size() == 5) {
            return;
        }
        String s = thumbdata.getPhotoIdentificationNumber();
        for (int i = 0; i < data.size(); i++) {
            Hashtable hashtable = (Hashtable) data.elementAt(i);
            if (((String) hashtable.get("PHOTOGRAPHIDENTIFICATIONNO")).equals(s)) {
                selectedData.addElement(hashtable);
                return;
            }
        }

    }

    public void thumbDeSelected(ThumbData thumbdata) {
        if (selectedData.size() == 0) {

            return;
        }
        String s = thumbdata.getPhotoIdentificationNumber();
        int i = 0;
        for (int j = selectedData.size(); i < j; i++) {
            Hashtable hashtable = (Hashtable) selectedData.elementAt(i);
            if (((String) hashtable.get("PHOTOGRAPHIDENTIFICATIONNO")).equals(s)) {
                selectedData.removeElement(hashtable);
                return;
            }
        }

    }

    public ThumbContentManager getThumbContentManager() {
        return null;
    }

    public void headerSelected(ThumbData thumbdata) {
    }

    public void reset() {
        tv.reset();
        if (selectedData != null) {
            selectedData.clear();

        }
        if (data != null) {
            data.clear();
        }
        filmType.setSelected(false);
        datePhoto.setSelected(false);
        magCode.setSelected(false);
      }

    public void setFooter(String r) {
        rp.setText(r);
        // validate();

    }

    public void deselectSelection(String fName) {

        if (selectedData.size() == 0) {
            return;
        }
        String pin = fName.substring(0, fName.indexOf("c.sid"));
        logger.debug("pin:" + pin);
        int i = 0;
        for (int j = selectedData.size(); i < j; i++) {
            Hashtable hashtable = (Hashtable) selectedData.elementAt(i);
            if (((String) hashtable.get("PHOTOGRAPHIDENTIFICATIONNO")).equals(pin)) {
                selectedData.removeElement(hashtable);
                ThumbWindow selectedWindow = (ThumbWindow) tv.windows.get(pin);
                selectedWindow.deSelectThumb();
                tv.removeSelectedThumb(pin);
                return;
            }
        }
    }


    final String FTCODE = "FT";
    final String MGCODE = "MG";
    final String DPCODE = "DP"; // catphotoid


}
