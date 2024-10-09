/*
 * PTMCanvas.java
 *
 * Created on July 20, 2004, 10:26 PM
 */
package isf.ptm.controls;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Default
 */
public final class PTMCanvasBufferedImage extends PTMCanvas {

    protected BufferedImage image;
    protected float ratio = 0;
    private int maxWidth = -1;
    private int maxHeight = -1;
    private int cachedWidth = 0,  cachedHeight = 0;
    private int posX,posY;

    public BufferedImage getImage() {
        return image;
    }

    /** Creates a new instance of PTMCanvas */
    public PTMCanvasBufferedImage(int width, int height) {
        _initImage(width, height);
       
    }

    private void _initImage(int width, int height) {
        desiredWidth = width;
        desiredHeight = height;
        cachedWidth = width;
        cachedHeight = height;
        displayWidth = width;
        displayHeight = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       // pixels = ((DataBufferInt) ((BufferedImage) image).getRaster().getDataBuffer()).getData();
        ratio = ((float) image.getWidth()) / image.getHeight();
        maxWidth = (int) (width * 1.3);
        maxHeight = (int) (height * 1.3);
    }

    public void paintComponent(Graphics g) {
         _initPC(g);
    }

    private void _initPC(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        
        this.setPreferredSize(new Dimension(desiredWidth, desiredHeight));
        this.revalidate();

        // TODO: this is only for the frame
        // desiredHeight = Math.min(r.height,image.getHeight());
        // desiredWidth = Math.min(r.width,image.getWidth());

        if (hints) {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
        int w1 = 0, h1 = 0;
         g2d.drawImage(image, w1, h1, displayWidth, displayHeight, this);
    }

    public void speed() {
        isFast = true;
        int w = cachedWidth / 2;
        int h = cachedHeight / 2;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
       // pixels = ((DataBufferInt) ((BufferedImage) image).getRaster().getDataBuffer()).getData();
    }

    public void detail() {
        isFast = false;
        int w = cachedWidth;
        int h = cachedHeight;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
       // pixels = ((DataBufferInt) ((BufferedImage) image).getRaster().getDataBuffer()).getData();
    }

    private void dumpAvgValues() {
        /*
        int r=0, g=0, b=0;
        
        for ( int i = 0; i < pixels.length; i++ )
        {
        r += ( ( pixels[ i ] >> 16 ) & 0xff );
        g += ( ( pixels[ i ] >> 8 ) & 0xff );
        b += ( ( pixels[ i ] ) & 0xff );
        }
        
        System.out.println ( "Red: " + ((float) r) / pixels.length );
        System.out.println ( "Grn: " + ((float) g) / pixels.length );
        System.out.println ( "Blu: " + ((float) b) / pixels.length );
         */
    }

    public void resize() {
        _reSize();
    }

    private void _reSize() {
        Rectangle r = this.getParent().getBounds();
        float rectangleRatio = ((float) r.width) / r.height;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        if (ratio >= rectangleRatio) /* image too wide */ {
            desiredWidth = r.width;
            desiredHeight = Math.round(((float) (r.width * imageHeight)) / imageWidth);
        } else {
            desiredHeight = r.height;
            desiredWidth = Math.round(((float) (r.height * imageWidth)) / imageHeight);
        }

        if (desiredWidth > maxWidth || desiredHeight > maxHeight) {
            desiredWidth = maxWidth;
            desiredHeight = maxHeight;
        }

       // this.setPreferredSize(new Dimension(desiredWidth, desiredHeight));
        this.revalidate();
    }
    
    public void setDesiredViewSize(Dimension desired,Dimension display,int x,int y)
    {
        desiredWidth = desired.width;
        desiredHeight = desired.height;
        displayWidth=display.width;
        displayHeight =display.height;
        posX=x;
        posY=y;
        this.revalidate();
                
    }
    
    public Dimension getCurrentSize()
    {
        return new Dimension(desiredWidth,desiredHeight);
        
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
       
    }

    public void mouseEntered(MouseEvent e) {
       
    }

    public void mouseExited(MouseEvent e) {
        
    }
}
