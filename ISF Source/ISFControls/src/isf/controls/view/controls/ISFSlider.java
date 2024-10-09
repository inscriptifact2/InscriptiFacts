/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.view.controls;

import javax.swing.JSlider;

/**
 * @author IN030223
 */
public class ISFSlider extends JSlider {

    public ISFSlider(int min, int max) {
        super(min, max);
        super.putClientProperty("JSlider.isFilled", Boolean.TRUE);
        if (min == 0)
            min = 1;
       // setPaintLabels(true);
        int maxTick = max / 4;
        setMajorTickSpacing(maxTick);
        setMinorTickSpacing(maxTick / 5);
        setPaintTicks(true);
     //   setPaintLabels(true);
      /*  Dictionary labels = new Hashtable() ;
        labels.put(new Integer(0),"1.0");
        labels.put(new Integer(25),"2.5");
        labels.put(new Integer(50),"5.0");
        labels.put(new Integer(75),"7.5");
        labels.put(new Integer(100),"10.0");
                
                
        setLabelTable(labels);*/
        setPaintLabels(true);

    }

}
