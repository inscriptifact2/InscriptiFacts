/*
 * Interface.java
 *
 * Created on September 5, 2004, 9:34 PM
 */
package isf.ptm.operations;

import isf.ptm.formats.PTM;
import isf.ptm.utils.LightSource;

/**
 *
 * @author Default
 */
public abstract class Operation {

    private static double[] _initHSH(double theta, double phi) {
        double[] hweights = new double[9];
        double cosTheta = Math.cos(theta);
        double cosPhi = Math.cos(phi);
        double sinPhi = Math.sin(phi);
        hweights[0] = 1 / Math.sqrt(2 * M_PI);
        hweights[1] = Math.sqrt(6 / M_PI) * (cosPhi * Math.sqrt(cosTheta - cosTheta * cosTheta));
        hweights[2] = Math.sqrt(3 / (2 * M_PI)) * (-1. + 2. * cosTheta);
        hweights[3] = Math.sqrt(6 / M_PI) * (Math.sqrt(cosTheta - cosTheta * cosTheta) * sinPhi);
        hweights[4] = Math.sqrt(30 / M_PI) * (Math.cos(2. * phi) * (-cosTheta + cosTheta * cosTheta));
        hweights[5] = Math.sqrt(30 / M_PI) * (cosPhi * (-1. + 2. * cosTheta) * Math.sqrt(cosTheta - cosTheta * cosTheta));
        hweights[6] = Math.sqrt(5 / (2 * M_PI)) * (1 - 6. * cosTheta + 6. * cosTheta * cosTheta);
        hweights[7] = Math.sqrt(30 / M_PI) * ((-1 + 2. * cosTheta) * Math.sqrt(cosTheta - cosTheta * cosTheta) * sinPhi);
        hweights[8] = Math.sqrt(30 / M_PI) * ((-cosTheta + cosTheta * cosTheta) * Math.sin(2. * phi));
        return hweights;
    }
    //public void performOperation(int[] pixels, PTM ptm);

    //public void performOperation(int[] pixels, PTM ptm, int mouseX, int mouseY);
    public abstract void performOperation(int[] pixels, LightSource[] sources, PTM ptm, int mouseX, int mouseY, int dW, int dH, int posx, int posy, int pW, int pH);

    public abstract void forceUpdate();

    public abstract void release();

    public abstract void clearCache();
    static double M_PI = Math.PI;

    public static double[] getHSH(double theta, double phi) {
        return _initHSH(theta, phi);
    }
    
    public abstract String getName();
    
}
