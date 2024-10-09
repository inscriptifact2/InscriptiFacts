package com.isf.app.view.containers;

import com.isf.app.controllers.ThumbContentManager;
import com.isf.app.models.ThumbData;
import com.isf.app.view.gui.ImageConsumer;
import com.isf.app.view.listeners.CacheUpdater;
import com.isf.app.view.listeners.QuickViewListener;
import com.isf.app.view.listeners.ThumbHeaderSelectionListener;
import com.isf.app.view.listeners.ThumbListener;
import isf.common.utils.InscriptConfigLoader;
import isf.controls.utils.CursorManager;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.*;

public class ThumbViewer extends JScrollPane
        implements QuickViewListener, ThumbContentManager, ThumbListener, ImageConsumer {

    private JPanel tc;
    private Vector selectedThumbs;
    private ThumbWindow latest;
    private QuickViewListener qvl;
    private ThumbHeaderSelectionListener thsl;
    private ThumbListener tl;
    ImageIcon ti;
    int rows;
    CacheUpdater cup = null;
    Hashtable windows;
    //  private  Vector thumbs = null;
    //  private ThreadGroup tg;

    public ThumbViewer() {
        tc = new TrimmedPanel(new Insets(10, 20, 10, 20));
        selectedThumbs = new Vector();
        ti = isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/dublininactive.jpg");
        setViewportView(tc);
        int max = Integer.parseInt(InscriptConfigLoader.getInstance().getProperty("ImagesListSize"));
        rows = max / 5;
        if ((max % 5) > 0)
            rows++;

        tc.setLayout(new GridLayout(rows, 5, 10, 10));
        tc.setBackground(Color.black);
        windows = new Hashtable();
        //thumbs=new Vector();
        // tg = new ThreadGroup("Fetching");
    }

    public int getSelectionCount() {
        return selectedThumbs.size();
    }

    public void addCacheUpdater(CacheUpdater au) {
        cup = au;
    }

    public boolean canSelectMore(String type) {
        ThumbData thumb;
        int sids = 0, ptms = 0;
        for (int i = 0; i < selectedThumbs.size(); i++) {
            thumb = (ThumbData) selectedThumbs.get(i);
            if (thumb.getType().equalsIgnoreCase("SID"))
                sids++;
            else if (thumb.getType().equalsIgnoreCase("RTI"))
                ptms++;
        }

		
        if (  sids >= 5 || (sids >= 3 && ptms >= 1) || (sids >= 2 && ptms >= 2) || (ptms>=2 && type.equalsIgnoreCase("RTI")) ) {
            String msg = "You can select either 5 SIDs, 3 SIDs and 1 RTI, 2 SIDs and 2 RTIs, or 2 RTIs.  \n";
            msg += "You have selected  " + sids + " SIDs and " + ptms + " RTIs.";
            JOptionPane.showMessageDialog(this, msg, "Selection Error", JOptionPane.OK_OPTION);
            return false;
        }


        if (getSelectionCount() < 5) {
            return true;
        } /*else {
            JOptionPane.showMessageDialog(this, "You Can Select Only Five Images For the Viewer", "Error", 0);
            return false;
        }*/
        return false;
    }

    public void headerSelected(ThumbData thumbdata) {
        if (thsl != null) {
            thsl.headerSelected(thumbdata);
        }
    }

    public void thumbSelected(ThumbData thumbdata) {
        selectedThumbs.addElement(thumbdata);
        if (tl != null) {
            tl.thumbSelected(thumbdata);
        }
    }

    public void thumbDeSelected(ThumbData thumbdata) {
        selectedThumbs.remove(thumbdata);
        if (tl != null) {
            tl.thumbDeSelected(thumbdata);
        }
    }

    public ThumbContentManager getThumbContentManager() {
        return this;
    }

    public void addThumbListener(ThumbListener thumblistener) {
        tl = thumblistener;
    }

    public void showQuickView(ThumbData thumbdata) {
        if (!CursorManager.wait) {
            if (qvl != null) {
                qvl.showQuickView(thumbdata);
            }
        }
    }

    public void addQuickViewListener(QuickViewListener quickviewlistener) {
        qvl = quickviewlistener;
    }

    public void addThumbHeaderSelectionListener(ThumbHeaderSelectionListener thumbheaderselectionlistener) {
        thsl = thumbheaderselectionlistener;
    }

    public void setData(Vector vector) {
        //thumbs=vector;
        //System.out.println("VCTOR inThunbViewer:"+vector.size());
        tc.removeAll();
        windows = new Hashtable();
        int i = 0;
        for (int j = vector.size(); i < j; i++) {
            ThumbData thumbdata = (ThumbData) vector.elementAt(i);
            ThumbWindow thumbwindow = new ThumbWindow(thumbdata);
            windows.put(thumbdata.getPhotoIdentificationNumber().trim(), thumbwindow);
            if (isSelectedThumb(thumbdata)) {
                thumbwindow.setSelectedThumb();
            }
            tc.add(thumbwindow, i);
            if (latest == null) {
                latest = thumbwindow;
            } else {
                latest.right = thumbwindow;
                thumbwindow.left = latest;
                latest = thumbwindow;
            }
            thumbwindow.addQuickViewListener(this);
            thumbwindow.addThumbListener(this);
        }

        getVerticalScrollBar().setUnitIncrement(latest.getPreferredSize().height);
        getHorizontalScrollBar().setUnitIncrement(latest.getPreferredSize().width);
        int range = Integer.parseInt(InscriptConfigLoader.getInstance().getProperty("ImagesListSize"));
        for (; i < rows * 5; i++) {
            tc.add(new JLabel(" "));
        }
        /*  for (int x=0;x<vector.size();x++){
              ThumbData tdata = (ThumbData) vector.elementAt(x);
              if(tdata.getIcon()==null) {
               //   System.out.println("CallingThis:"+ tdata.getPhotoIdentificationNumber());
              String tmp = "cat=Thumbnail&item=/" + tdata.getPhotoIdentificationNumber() + "t.jpg";
              new FetchImageThread(tg,this,tmp,x);
              }
              else
                  System.out.println("Called r9m cahce");
          }  */ ///  experiment with this for staggered display
        validate();
        repaint();
    }

    public boolean isSelectedThumb(ThumbData thumbdata) {
        int i = 0;
        for (int j = selectedThumbs.size(); i < j; i++) {
            if (selectedThumbs.elementAt(i).equals(thumbdata)) {
                return true;
            }
        }

        return false;
    }

    public Vector getSelectedThumbs() {
        return selectedThumbs;
    }

    public void reset() {
        if (selectedThumbs != null) {
            selectedThumbs.clear();
        }
        windows = new Hashtable();
    }

    public Dimension getPreferredSize() {
        return new Dimension(ti.getIconWidth() * 2, ti.getIconHeight());
    }

    public synchronized void setUpImg(ImageIcon ic, int i) {
        /*  ((ThumbWindow)tc.getComponent(i)).setIcon(ic);
          ThumbData td = (ThumbData)thumbs.get(i)  ;
          td.setIcon(ic);
          thumbs.setElementAt(td,i);

            if(tg.activeCount() >1 )
               return;
            cup.updateCache(thumbs);

          }*/

        // System.out.println("Doing Nothing But Called From FetchThread");
    }

    public void removeSelectedThumb(String pin) {
        for (int i = 0; i < selectedThumbs.size(); i++) {
            ThumbData td = (ThumbData) selectedThumbs.elementAt(i);
            if (td.getPhotoIdentificationNumber().trim().equalsIgnoreCase(pin)) {
                if (isSelectedThumb(td)) {
                    thumbDeSelected(td);
                }
                break;
            }
        }
    }

}
