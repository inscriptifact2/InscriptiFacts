package isf.controls.utils;

import isf.controls.view.listeners.ImageLoader;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class ResolutionManager
        implements ImageLoader {

    private static ResolutionManager resolutionManager;
    private Dimension base;
    private Dimension current;
    private ImageLoader imageLoader;
    private ClassLoader cl;

    private ResolutionManager() {
        base = new Dimension(1280, 1024);
        setImageLoader(this);
        current = Toolkit.getDefaultToolkit().getScreenSize();       //new Dimension(1600,1200);//
        //   current = new Dimension(2560,1600);//1280, 1024);//
        cl = getClass().getClassLoader();
        // System.out.println("current res  :"+current);
    }

    public static ResolutionManager getInstance() {
        if (resolutionManager == null) {
            resolutionManager = new ResolutionManager();
        }
        return resolutionManager;
    }

    public void setDefaultResolution(Dimension dimension) {
        base = dimension;
    }

    public void setImageLoader(ImageLoader imageloader) {
        imageLoader = imageloader;
    }

    public ImageIcon loadImage(String s) {
        
        if(cl!=null){

        return new ImageIcon(cl.getResource(s.toLowerCase()));
        }
        else
          return new ImageIcon();

    }

    public ImageIcon getImageIcon(String s) {
        ImageIcon imageicon = imageLoader.loadImage(s);
        int i = imageicon.getIconWidth();
        int j = imageicon.getIconHeight();
        imageicon.setImage(imageicon.getImage().getScaledInstance((i * current.width) / base.width, (j * current.height) / base.height, 4));
        return imageicon;
    }

    public ImageIcon getImageIcon(ImageIcon imageicon) {
        int i = imageicon.getIconWidth();
        int j = imageicon.getIconHeight();
        imageicon.setImage(imageicon.getImage().getScaledInstance((i * current.width) / base.width, (j * current.height) / base.height, 4));
        return imageicon;
    }

    public ImageIcon getScaledWidthIcon(String s) {
        ImageIcon imageicon = imageLoader.loadImage(s);
        int i = imageicon.getIconWidth();
        int j = imageicon.getIconHeight();
        imageicon.setImage(imageicon.getImage().getScaledInstance((i * current.width) / base.width, j, 4));
        return imageicon;
    }

    public double[] getRatio() {
        double ad[] = new double[2];
        ad[0] = ((double) current.width * 1.0D) / (double) base.width;
        ad[1] = ((double) current.height * 1.0D) / (double) base.height;
        return ad;
    }

    public int getFontSize(int i) {
        if (isCurrentResolutionLower()) {
            return i - 2;
        } else {
            return i;
        }
    }

    public int getTextColumns(int i) {
        if (isCurrentResolutionLower()) {
            return i - 2;
        } else {
            return i;
        }
    }

    private boolean isCurrentResolutionLower() {
        return current.width < base.width || current.height < base.height;
    }

    public Dimension getScreen() {
        return current;
    }

}
