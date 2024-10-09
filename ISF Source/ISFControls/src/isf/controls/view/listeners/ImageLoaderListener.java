/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.view.listeners;

import isf.controls.models.DesktopDataObject;
import isf.ptm.formats.PTM;

/**
 * @author IN030223
 */
public interface ImageLoaderListener {

    public void putImage(DesktopDataObject desktopdataobject);

    public void addDesktopData(DesktopDataObject desktopdataobject);
    
    public void putImage(DesktopDataObject desktopdataobject, PTM ptm);


}
