package com.isf.app.utils;

import com.isf.app.models.ThumbData;
import com.isf.app.view.containers.DesktopViewer;
import isf.common.Debug;
import isf.common.request.RequestProxy;
import isf.common.utils.PTMConfig;
import isf.controls.controllers.LoadManager;
import isf.controls.models.DesktopDataObject;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

public class PopulateThread extends Thread implements Runnable {
    public Vector vector;
    public DesktopViewer vd;


    public PopulateThread(Vector v, DesktopViewer dv) {
        super();
        vector = v;
        vd = dv;
        Dimension d = vd.vd.getCatButtonSize();
        vd.jp.setDividerLocation(d.width);
       
        LoadManager.getInstance().setMaximum(0);
    }

    public void run(){
        ArrayList sids = new ArrayList();
        ArrayList ptms = new ArrayList();
        long size = 0;
        String notloaded = "";
      
        long actual = 0;

        for (int k = 0; k < vector.size(); k++) {
            ThumbData thumbdata = (ThumbData) vector.elementAt(k);
            String pin = thumbdata.getPhotoIdentificationNumber().trim();
            String header = thumbdata.getPhotoTitle() != null ? thumbdata.getPhotoTitle() : thumbdata.getPhotoIdentificationNumber();
            String display = thumbdata.getHeader() != null ? thumbdata.getHeader() : thumbdata.getISFDigitalObjIdentifier();
    
            if (thumbdata.getType().equalsIgnoreCase("SID")) {
                pin += "c.sid";
   
                DesktopDataObject desktopdataobject = new DesktopDataObject(header, display, pin, thumbdata.getPhotoIdentificationNumber());
                sids.add(desktopdataobject);
                size+=(250*1024);
                actual += (250*1024);
               // toLoad=+1;
            } else if (thumbdata.getType().equalsIgnoreCase("RTI") || thumbdata.getType().equalsIgnoreCase("HSH")) {
                PTMConfig ptm = RequestProxy.getRequestDispatcher().getPTMConfig(pin);
                String name = ptm.getName();
                long curS = ptm.getSize();
                //add values for title, header and PIN
                if(size + curS <= 241172400 ){
                DesktopDataObject ddo = null;
                if (thumbdata.getType().equalsIgnoreCase("RTI"))
                    ddo =   new DesktopDataObject(header, display, name + ".ptm",pin, ptm);
                else if (thumbdata.getType().equalsIgnoreCase("HSH"))
                    ddo =   new DesktopDataObject(header, display, name + ".rti",pin, ptm);
                ptms.add(ddo);
              //  toLoad+=1;
                size+=curS;
                actual += curS;
                }else{
                    notloaded +=name+",";
                    actual +=curS;
                }

                
            }

           
            // vd.addDesktopData(desktopdataobject);
            Debug.debug("AddedDDO TO DESKTOP DATA");
            // vd.putImage(desktopdataobject);
        }
          LoadManager.getInstance().setTotal(vector.size());
        // CursorManager.changeCursor(Cursor.DEFAULT_CURSOR );
   
       
      LoadManager.getInstance().setMaximum(size);
      LoadManager.getInstance().setActual(actual);

      LoadManager.getInstance().setNotLoaded(notloaded);
      LoadManager.getInstance().start();
      if (sids.size() > 0)
            vd.addDummy("MrSID Images");
        for (int i = 0; i < sids.size(); i++) {
            DesktopDataObject ddo = (DesktopDataObject) sids.get(i);
            vd.addDesktopData(ddo);
            vd.putImage(ddo);
        }
       

        if (ptms.size() > 0)
            vd.addDummy("RTI Images");
        for (int i = 0; i < ptms.size(); i++) {
            DesktopDataObject ddo = (DesktopDataObject) ptms.get(i);
            vd.addDesktopData(ddo);
            vd.putImage(ddo);
        }
        //vd.resetCursor();
     //   CursorManager.changeCursor(Cursor.DEFAULT_CURSOR);
       

    }

}