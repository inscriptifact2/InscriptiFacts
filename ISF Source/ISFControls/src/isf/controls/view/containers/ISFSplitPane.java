package isf.controls.view.containers;

import isf.controls.plaf.IPSplitPaneUI;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JSplitPane;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Jan 10, 2004
 * Time: 9:06:53 AM
 * To change this template use Options | File Templates.
 */
public class ISFSplitPane extends JSplitPane {
    int topHeight = 0;
    int lastPosition = 0;
    int curloc = 0;

    public ISFSplitPane() {
        super(1, false, null, null);
        topHeight = 100;
        lastPosition = 0;
        curloc = 0;
        setDivider(10);
        updateLocation();
        updateUI();
    }

    public ISFSplitPane(int orientation, boolean drag, Component one, Component two, int ht, int div) {
        super(orientation, drag, one, two);
        topHeight = ht;
        lastPosition = 0;
        curloc = 0;
        setDivider(div);
        updateLocation();


        updateUI();

    }

    public void paintChildren(Graphics g) {
        super.paintChildren(g);
    }


    public int getMaximumDividerLocation() {
        return topHeight;
    }

    public int getMinimumDividerLocation() {
        return 0;
    }


    public int getOneSize() {
        return topHeight;
    }

    public void setOneSize(int size) {
        //  System.out.println("changed size to:"+size);
        if (topHeight != size) {
            topHeight = size;
            if (curloc <= 0 || curloc > topHeight)
                curloc = 0;
            else
                curloc = topHeight;
            //setLastDividerLocation(curloc);
            lastPosition = curloc;
            curloc = topHeight - curloc;
        }

    }

    public void updateUI() {
        IPSplitPaneUI cui = new IPSplitPaneUI();
        //  System.out.println("called uisetting for combo"+cui);
        setUI(cui);
    }

    public void updateLocation() {

        // int curloc = getDividerLocation() ;
        //    System.out.println("superloc:="+curloc );
        if (curloc <= 0 || curloc > topHeight)
            curloc = 0;
        else
            curloc = topHeight;
        //setLastDividerLocation(curloc);
        lastPosition = curloc;
        curloc = topHeight - curloc;
        setDividerLocation(curloc);
        //   System.out.println("result:"+curloc+" oneHeight:"+topHeight+" orientation:"+getOrientation()+"  divSize:"+((IPSplitPaneUI)getUI()).getIsfDivider().getDividerSize() );

    }

    public void setDivider(int size) {
        //   System.out.println("called set divider:"+size);
        if (size > 0) {
            setDividerSize(10);
            super.setDividerSize(10);
            ((IPSplitPaneUI) getUI()).getIsfDivider().setDividerSize(10);

        } else {
            setDividerSize(0);
            super.setDividerSize(0);
            ((IPSplitPaneUI) getUI()).getIsfDivider().setDividerSize(0);
        }
    }

    public int getCurrentPos() {
        return curloc;
    }

    public void changeImage() {
        ((IPSplitPaneUI) getUI()).getIsfDivider().changeImage();
    }
}
