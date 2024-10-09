/*
 * VRGB.java
 *
 * Created on September 11, 2004, 3:54 PM
 */

package isf.ptm.graphics;

import isf.ptm.utils.Utils;
import java.text.DecimalFormat;

/**
 * The VRGB class provides a vector object for 3 dimensional locations or RGB
 * pixel values. The class offers static methods for standard vector operations.
 * 
 * @author Default
 */
public class VRGB {

    public float red,  green,  blue;

    /** Creates a new instance of VRGB */
    public VRGB() {
       
    }

    /**
     * Creates a new VRGB object using the provided float [] as the underlying
     * values.
     *
     * @param f
     *            an array of 3 float values.
     */
    public VRGB(float[] f) {
        red = f[0];
        green = f[1];
        blue = f[2];
    }

    /**
     * Creates an new VRGB object using the provided float values
     *
     * @param x
     *            first float value
     * @param y
     *            second float value
     * @param z
     *            third float value
     */
    public VRGB(float x, float y, float z) {
        
        red = x;
        green = y;
        blue = z;
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return a human-readable String representation of this vector
     */
    public String toString() {
        DecimalFormat format = new DecimalFormat("0.000");
        StringBuffer sb = new StringBuffer();
        sb.append("(");
       
        sb.append(red + "," + green + "," + blue);
        sb.append(")");
        return new String(sb);
    }

    /**
     * Returns true if this vector is the same as the provided vector
     *
     * @param that
     *            The vector to be compared with this vector
     * @return true if the this vector and the provided vector are equivalent.
     */
    public boolean equals(VRGB that) {
        return (this.red == that.red && this.green == that.green && this.blue == that.blue);
    }

    /**
     * Converts a 32 bit pixel value with RGB in the lower 24 bits to a VRGB
     * object
     *
     * @return a new VRGB object with the pixels r,b,g values as the elements
     *         of the VRGB
     * @param pixel
     *            the 32 bit pixel value
     */
    public static VRGB convertPixel(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        float[] a = new float[]{(((float) red) * 2.0f - 255.0f) / 255.0f,
            (((float) green) * 2.0f - 255.0f) / 255.0f, (((float) blue) * 2.0f - 255.0f) / 255.0f
        };
        VRGB v = new VRGB(a);
       
        return v;
    }

    /**
     * Returns the underlying float array for the VRGB object.
     *
     * @return the underlying float [] used for the object.
     */
    public float[] toFloat() {
        return new float[]{red, green, blue};
    }

    /**
     * Returns the length of the VRGB vector
     *
     * @return the length of the vector as a float
     */
    public float len() {
        return (float) Math.sqrt(dot(red, green, blue));
    }

    /**
     * Normalizes the vector. For convenience, returns itself.
     *
     * @return the normalized vector.
     */
    public VRGB normalize() {
        float len = len();
        red /= len;
        green /= len;
        blue /= len;
        return this;
    }

    /**
     * Returns the dot product of this VRGB and the VRGB provided to the
     * method
     *
     * @param vector
     *            The VRGB object representing the right-hand side of the dot
     *            product
     * @return The dot product of this VRGB and the VRGB provided to the
     *         method.
     */
    public float dot(VRGB vector) {
        return (red * vector.red + green * vector.green + blue * vector.blue);
    }

    public float dot(float s0, float s1, float s2) {
        return (red * s0 + green * s1 + blue * s2);
    }

    public float customMinMaxDot(float min, float max, float s0, float s1, float s2) {
        float retval = (red * s0 + green * s1 + blue * s2);

        retval = (retval < max) ? max : retval;
        retval = (retval > min) ? min : retval;
        return retval;

    }

    public float dot(float[] s) {
        return (red * s[0] + green * s[1] + blue * s[2]);
    }

    /**
     * Returns the cross product of this VRGB and the VRGB provided to the
     * method
     *
     * @return The cross product of this VRGB and the VRGB provided to the
     *         method.
     * @param v
     *            The VRGB object representing the right-hand side of the cross
     *            product
     */
    public VRGB cross(VRGB v) {
        return new VRGB(green * v.blue - blue * v.green, blue * v.red - red * v.blue,
                red * v.green - green * v.red);
    }

    /**
     * Scales this vector by the provided float value
     *
     * @param s
     *            the amount by which each component of the vector is multiplied
     * @return a new scaled vector (the underlying vector does not change)
     */
    public VRGB scale(float s) {
        return new VRGB(red * s, green * s, blue * s);
    }

    /**
     * Adds two VRGB objects together
     *
     * @param v
     *            The vector to which this vector will be added
     * @return a new vector representing the sum of this vector and the provided
     *         vector. (this vector is unchanged)
     */
    public VRGB sum(VRGB v) {
        return new VRGB(red + v.red, green + v.green, blue + v.blue);
    }

    public VRGB sum(float[] v) {
        return new VRGB(red + v[0], green + v[1], blue + v[2]);
    }

    /**
     * Returns the difference of two vectors.
     *
     * @return This difference of this vector and the provided vector
     * @param v
     *            The vector representing the right-hand side of the difference.
     */
    public VRGB diff(VRGB v) {
        return new VRGB(red - v.red, green - v.green, blue - v.blue);
    }

    public VRGB diff(float v0, float v1, float v2) {
        return new VRGB(red - v0, green - v1, blue - v2);
    }

    /**
     * Returns a vector representing the reflection of the provided incedent
     * vector.
     *
     * @param normal
     *            The normal vector for the particular poiont.
     * @param incedent
     *            The vector representing incedent light.
     * @return The reflection of the incendent vector.
     */
    public static VRGB reflect(VRGB normal, VRGB incedent) {
        // R = 2 * N.I * N - I
        if (normal == null || incedent == null) {
            return null;
        }
        VRGB lhs = normal.scale(normal.dot(incedent) * 2);
        return lhs.diff(incedent);

    }

    /**
     * Returns a representation of this vector as a colored pixel value, with
     * RGB in the lower 24 bits.
     *
     * @return The 32 bit pixel representation of this vector.
     */
    public int toPixel() {
        float r1 = ((red + 1.0f) / 2.0f) * 255.0f;
        float g1 = ((green + 1.0f) / 2.0f) * 255.0f;
        float b1 = ((blue + 1.0f) / 2.0f) * 255.0f;
        int red = Utils.clamp(Math.round(r1));
        int green = Utils.clamp(Math.round(g1));
        int blue = Utils.clamp(Math.round(b1));
        return red << 16 | green << 8 | blue;
    }

    /**
     * Perturbs the normal slightly
     */
    public void perturb() {
        red += red * (Math.random() - 0.5f) / 10.0f;
        green += green * (Math.random() - 0.5f) / 10.0f;
        blue += blue * (Math.random() - 0.5f) / 10.0f;
        this.normalize();
    }

    
}
