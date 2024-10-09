/*
 * PTMWindow.java
 *
 * Created on September 19, 2004, 10:36 PM
 */

package isf.ptm.controls;

import isf.ptm.formats.PTM;
import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.operations.Operation;
import java.awt.Container;
import javax.swing.JLayeredPane;

/**
 * 
 * @author Default
 */
public interface PTMWindow {

	public void fireTransform();

	public float getKSpec();

	public float getKDiff();

	public int getExp();

	public int getPTMWidth();

	public int getWidth();

	public int getHeight();

	public int getPTMHeight();

	public void setKSpec(float f);

	public void setKDiff(float f);

	public void setExp(int i);

	//public void setControls(PTMControls c);

	public float getLuminance(int lightSource);

	public void setLuminance(float f,int lightSource);

	public void setMapSampleSize(int i);

	public void setMapBlurType(int i);

	public void setMapKernelSize(int i);

	public void setMapGuassianBlurSigma(float f);

	public void refreshMap();

	public PTM getPTM();

	public void forceUpdate();

	public void setBrowser(Container c);

	public void setEnvironmentMap(EnvironmentMap e);

	public void setPixelTransformOp(Operation pto);

	public JLayeredPane getLayeredPane();
        
       // public void toggleLight();
        
        public void setExtrapolation(boolean flag);
        public boolean isExtrapolation();
        
        public boolean isFrameActive();
        public void setFrameActive(boolean flag);

}
