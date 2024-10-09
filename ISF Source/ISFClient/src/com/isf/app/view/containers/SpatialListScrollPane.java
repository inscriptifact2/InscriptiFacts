package com.isf.app.view.containers;

import com.isf.app.view.controls.InscriptDyanBorder;
import com.isf.app.view.gui.SpatialListCellRenderer;
import com.isf.app.view.listeners.SpatialListSelectionListener;
import isf.common.view.gui.SpatialListImage;
import isf.controls.utils.CursorManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SpatialListScrollPane extends JScrollPane
        implements ListSelectionListener {

    private JList list;
    private SpatialListSelectionListener sl;
    private boolean prepopulate = false;

    public SpatialListScrollPane() {
        super(22, 31);
        //list = new JList();
        list = new JList();
        setBorder(new InscriptDyanBorder(Color.black, 0, 0, 0, 1));
        list.setCellRenderer(new SpatialListCellRenderer());
        list.addListSelectionListener(this);
        list.setSelectedIndex(-1);
        list.setBackground(new Color(0xd9d9d9));
        list.setBorder(BorderFactory.createEtchedBorder(0));
        prepopulate = false;
        setViewportView(list);
    }

    /*   public SpatialListScrollPane(Vector vector)
       {
           this();
           setModal(vector);
       }*/
    public void setDefault() {
        try {
            Object onj = list.getModel().getElementAt(1);
        } catch (ArrayIndexOutOfBoundsException aioob) {
            prepopulate = true;
            list.setSelectedIndex(0);
            //  fireSingleSelection();
        }


    }

    public Insets getInsets() {
        return new Insets(1, 1, 1, 1);
    }

    public Dimension getPreferredSize() {
        return new Dimension(155, 100);
    }

    public void setModal(Vector response) {
        list.setListData(response);

        /*  if(response.size()==1){
              prepopulate=true;
              list.setSelectedIndex(0);
             // prepopulate=true;

          }  */


    }


    private void fireSingleSelection() {
        SpatialListImage spatiallistimage = (SpatialListImage) list.getSelectedValue();
        //  setCursor(new Cursor(3));
        //  CursorManager.changeCursor(3);

        if (sl != null)
            sl.spatialListImageSelected(spatiallistimage);
        else
            CursorManager.changeCursor(0);

    }

    public void valueChanged(ListSelectionEvent listselectionevent) {
        if (listselectionevent.getValueIsAdjusting() || prepopulate) {
            SpatialListImage spatiallistimage = (SpatialListImage) list.getSelectedValue();
            // setCursor(new Cursor(3));
            //   CursorManager.changeCursor(3);

            if (sl != null)
                sl.spatialListImageSelected(spatiallistimage);
            //System.out.println("Image Selected");
            // else
            //  CursorManager.changeCursor(0);

        }

    }

    public SpatialListImage getSelectedObject() {
        return (SpatialListImage) list.getSelectedValue();
    }

    public void reset() {
        list.clearSelection();
        list.setSelectedIndex(-1);
    }

    public void addSpatialListSelectionListener(SpatialListSelectionListener spatiallistselectionlistener) {
        sl = spatiallistselectionlistener;
    }
}
