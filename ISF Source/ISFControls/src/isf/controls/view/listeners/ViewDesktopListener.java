package isf.controls.view.listeners;

import isf.controls.models.DesktopDataObject;
import isf.ptm.formats.PTM;

public interface ViewDesktopListener {

    public abstract String addImage(DesktopDataObject ddo); //, String s2
    
    public abstract String addImage(DesktopDataObject ddo, PTM ptm); //String s2,

    public abstract String checkImage(DesktopDataObject ddo); //, String index

    public abstract void performAction(int i);

   // public abstract void setMode(int i, MouseEvent me);

 
    
}
