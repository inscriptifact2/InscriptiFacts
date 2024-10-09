/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.viewer.controls;

import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 *
 * @author IN030223
 */
public class ISFSliderControl extends JSlider {

    private float minval,maxval;
    private int min,max;
    public ISFSliderControl(int min, int max, float minval, float maxval) {
        super(min, max);
        super.putClientProperty("JSlider.isFilled", Boolean.TRUE);
        int maxTick = max / 4;
        setMajorTickSpacing(maxTick);
        setMinorTickSpacing(maxTick / 5);
        setPaintTicks(true);
        this.minval = minval;
        this.maxval = maxval;
        
        
        
     //   setPaintLabels(true);
        Hashtable labels = new Hashtable() ;
        labels.put(new Integer(min),getLabel(min));
        labels.put(new Integer(min+1*maxTick),getLabel(min+1*maxTick));
        labels.put(new Integer(min + 2*maxTick),getLabel(min+2*maxTick));
        labels.put(new Integer(min+3*maxTick),getLabel(min+3*maxTick));
        labels.put(new Integer(max),getLabel(max));
                
                
        setLabelTable(labels);
        setPaintLabels(true);

    }
    
    
    public ISFSliderControl(int min, int max, int minval, int maxval) {
        super(min, max);
        int maxTick = max / 4;
        setMajorTickSpacing(maxTick);
        setMinorTickSpacing(maxTick / 5);
        setPaintTicks(true);
        this.min = minval;
        this.max = maxval;
        
        
        
     //   setPaintLabels(true);
        Hashtable labels = new Hashtable() ;
        labels.put(new Integer(min),getILabel(min));
        labels.put(new Integer(min+1*maxTick),getILabel(min+1*maxTick));
        labels.put(new Integer(min + 2*maxTick),getILabel(min+2*maxTick));
        labels.put(new Integer(min+3*maxTick),getILabel(min+3*maxTick));
        labels.put(new Integer(max),getILabel(max));
                
                
        setLabelTable(labels);
        setPaintLabels(true);

    }
    
    private JLabel getLabel ( int value)
    {
       return  new JLabel((float) (minval + value *(maxval - minval) /100 )+"");
    }
    
     private JLabel getILabel ( int value)
    {
       return  new JLabel( (min + value *(max - min) /100 )+"");
    }
     
     public void setIValue(int i)
     {
          super.setValue( ((i - min)*100/(max - min)));
     }
     
     public int getIValue()
    {
        int value = super.getValue();
        return  (min + value *(max - min) /100 );
    }
    
    public void setFValue(float f)
    {
       super.setValue( Math.round((f - minval)*100/(maxval - minval)));
    }
    
    public float getFValue()
    {
        int value = super.getValue();
        return (float) (minval + value *(maxval - minval) /100 );
    }
}