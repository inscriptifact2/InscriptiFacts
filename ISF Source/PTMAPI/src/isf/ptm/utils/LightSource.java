/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.ptm.utils;
import javax.vecmath.Point3f;

/**
 *
 * @author IN030223
 */
public class LightSource {
    
    private boolean enabled;
    private Point3f light;
    private float  luminance= 1.0f;
    private boolean active = false;
    private int xpos,ypos;
    
    
    public LightSource()
    {
    }

    public boolean isEnabled() {
        return enabled;
    }
    public Point3f getLight()
    {
        return light;
    }
     public void setLight(Point3f light)
    {
         this.light = light;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(!this.enabled)
            this.active=false;
    }

    public int getX() {
       return xpos;
      
    }

    public void setX(int xpos) {
        this.xpos= xpos;
    }

    public int getY() {
        return ypos;
    }

    public void setY(int ypos) {
        this.ypos= ypos;
    }
 
    public float getLuminance() {
        return luminance;
    }

    public void setLuminance(float luminance) {
        this.luminance = luminance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getDisplay(){
        return( "Luminance: "+luminance+"     Coordinates  X: "+xpos+" &  Y: "+ypos+" ");
    }
    
   

}
