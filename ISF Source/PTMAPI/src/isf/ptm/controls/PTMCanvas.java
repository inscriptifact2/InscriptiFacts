/*
 * PTMCanvas.java
 *
 * Created on July 22, 2004, 12:46 AM
 */

package isf.ptm.controls;

import javax.swing.JComponent;

/**
 * 
 * @author Default
 */
public abstract class PTMCanvas extends JComponent {

	public static final int MEMORY_IMAGE_SOURCE = 0;

	public static final int BUFFERED_IMAGE = 1;

        private static PTMCanvas _createCanvas(int type, int width, int height) {
        if (type == MEMORY_IMAGE_SOURCE)
                return null; /* deprecated */
        else if (type == BUFFERED_IMAGE)
                return new PTMCanvasBufferedImage(width, height);
        else
                return null;
    }

	//protected int[] pixels;

	protected int desiredWidth, desiredHeight;
	
	protected int displayWidth, displayHeight,offsetX,offsetY;

	protected boolean hints = false;

	protected boolean isFast = false;

	protected boolean fixed = false;

	public boolean isFast() {
		return isFast;
	}

	public static PTMCanvas createPTMCanvas(int width, int height, int type)
	{
        return _createCanvas(type, width, height);
	}



	public void useHint(boolean b) {
		hints = b;
	}

	public void speed() {
		;
	}

	public void detail() {
		;
	}

	public void fixedSize(boolean b) {
		fixed = b;
	}

	public void setDisplayWidth(int width)
	{
		displayWidth = width;
	}
	
	public void setDisplayHeight(int height)
	{
		displayHeight = height;
	}
        
        
        public void setOffsetY(int posY)
	{
		offsetY = posY;
	}
	
	public void setOffsetX(int posX)
	{
		offsetX = posX;
	}
	
	public int getDisplayWidth()
	{
		return displayWidth;
	}
	
	public int getDisplayHeight()
	{
		return displayHeight;
	}
	
	public void paint(java.awt.Graphics g) {
		this.paintComponent(g);
	}
}
