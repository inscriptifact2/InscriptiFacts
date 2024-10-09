package isf.controls.view.containers;

import isf.controls.models.DesktopDataObject;
import isf.common.utils.PTMConfig;
import isf.controls.view.listeners.SizeChangeListener;
import isf.ptm.controls.PTMWindow;
import isf.ptm.formats.PTM;
import isf.ptm.operations.Operation;

import isf.ptm.utils.LightSource;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 24, 2009
 * Time: 7:12:25 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PTMPanel extends ImagePanel implements PTMWindow,MouseListener,MouseMotionListener {
    public LightSource[] lights = new LightSource[PTM.MAX_LIGHTS];
    public abstract void addImage(ImageIcon ing);

    public abstract void fitToSize();

    public abstract void addImage(String fileName);

    public abstract void addImage(PTMConfig config);

    public abstract void addImage(PTMConfig config, PTM ptm);

    public abstract void addImage(DesktopDataObject config);

    public abstract void setDrawRect(boolean draw);

    public abstract String getCurrentLevel();

    public abstract void reset();

    public abstract boolean isAdditionalLight();

    public abstract Operation getPixelTransformOp();

    public abstract int getActiveLight();

    public abstract  void toggleLight(boolean dual);

    public abstract boolean isAnimated();

    public abstract void setScale(float f);

    public abstract float getScale();

    public abstract void setAnimated(boolean bool);

    public abstract boolean isLoading();

    public abstract String getDetails();

    public abstract void fireTransform();

    public abstract float getKSpec();

    public abstract float getKDiff();

    public abstract int getExp();

    public abstract int getPTMWidth();

    public abstract int getPTMHeight();

    public abstract void setKSpec(float v);

    public abstract void setKDiff(float v);

    public abstract void setExp(int i);

    public abstract float getLuminance(int i);

    public abstract void setLuminance(float v, int i);

    public abstract PTM getPTM();

    public abstract void setExtrapolation(boolean b);

    public abstract boolean isExtrapolation();

    public  void setSizeNotifier(SizeChangeListener sl)
    {
        notifier = sl;
    }

    public abstract void detail();

    public abstract void speed();

    public abstract void useHint(boolean b);

   public abstract void setActiveLight(int i) ;


    public boolean isPTMPanel() {
        return true;
    }
   private boolean lockLights = false;
    
    public void lockLights()
    {
        lockLights = !lockLights;
    }
    public boolean isLocked()
    {
        return lockLights;
    }
    
    public LightSource[] getLights()
    {
        return lights;
    }
    
    public abstract Rectangle getDisplayRect();
    public abstract Dimension getCurrentSize();
    
    public abstract void updateLightPosition(int xpos, int ypos);
    
    
}
