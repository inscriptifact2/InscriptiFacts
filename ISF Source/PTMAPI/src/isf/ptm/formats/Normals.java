/*
 * ColorChannelOp.java
 *
 * Created on September 5, 2004, 9:41 PM
 */
package isf.ptm.formats;

import isf.ptm.graphics.GraphicsGems;
import isf.ptm.graphics.VRGB;
import isf.ptm.utils.Utils;
import java.util.ArrayList;

/**
 * Original code by Hans Wolters Java port by Cliff Lyon
 */
public class Normals {

    private static final float zerotol = 1.0e-14f; //5
    private static final float eps = 1.0e-8f;//zerotol;

    private static ArrayList _gN2(int[][] coeff, boolean isRGBPTM, int[] bias, float[] scale) {
        ArrayList normals = new ArrayList();
        float[] a = new float[6];
        float length2d;
        float disc, len;
        int length = coeff[0].length;
        int stat;
        int maxfound;
        float[] normal = new float[3];
        for (int i = 0; i < length; i++) {
             normal = new float[3];
             a = new float[6];
            int val = 0;
            if (!isRGBPTM) {
                a[0] = (float) coeff[i][0];
                a[1] = (float) coeff[i][1];
                a[2] = (float) coeff[i][2];
                a[3] = (float) coeff[i][3];
                a[4] = (float) coeff[i][4];
                a[5] = (float) coeff[i][5];
            } else {
                for (int k = 0; k < 6; k++) {
                    val = coeff[k][i];
                    a[k] = (float) (((((val >> 16) & 0xff) - bias[k]) * scale[k]) + ((((val >> 8) & 0xff) - bias[k]) * scale[k]) + (((val & 0xff) - bias[k]) * scale[k]));
                }
            }



            /*
             * These coordinates are where the first deriviative of the
             * polynominal is zero
             */
            /* Derivation in lab notebook 2951-43 */
            /*
             * normal[0] = (2 * a[1] * a[3] - (a[4] / a[2]) ) / (1.0 - 4 * a[0] *
             * a[1]);
             */

            /** zero denominator in upcoming computations */
            if (Math.abs(a[2] * a[2] - 4 * a[1] * a[0]) < zerotol) {
                normal[0] = 0;
                normal[1] = 0;
            } else {
                if (Math.abs(a[2]) < zerotol) {
                    normal[0] = -1.0f * a[3] / (2.0f * a[0]);
                    normal[1] = -1.0f * a[4] / (2.0f * a[1]);
                } else {
                    normal[0] = (2.0f * a[1] * a[3] - a[2] * a[4]) / (a[2] * a[2] - 4.0f * a[1] * a[0]);
                    normal[1] = (-2.0f * a[0] * normal[0] - a[3]) / a[2];
                }
            }

            /** polynomial is constant we are done, set normal to be at 0,0,1 */
            if (Math.abs(a[0]) < zerotol && Math.abs(a[1]) < zerotol && Math.abs(a[2]) < zerotol && Math.abs(a[3]) < zerotol && Math.abs(a[4]) < zerotol) {
                normal[0] = 0.0f;
                normal[1] = 0.0f;
                normal[2] = 1.0f;
            } /**
             * clip normal[0], normal[1] values - these can both be unbounded
             * theoretically. first check if the vector (normal[0],normal[1]) is
             * greater than 1
             */
            else {
                length2d = normal[0] * normal[0] + normal[1] * normal[0];

                /*
                 * Add check for saddle or minimum. if p_uu >0 and/or
                 * p_uu*p_vv-p_uv*p_uv <0 -> saddle or minimum if this is the
                 * case then we should always look at boundary
                 */
                if (4 * a[0] * a[1] - a[2] * a[2] > eps && a[0] < -eps) {
                    maxfound = 1;
                } else {
                    maxfound = 0;
                /**
                 * Changed by Hans J. Wolters 12-11-99: instead of clipping, we
                 * attempt to find the minimum value on the circle. we know that
                 * since the polynomial is monotonous in the interior the
                 * extrema must be attained at the boundary. The circle will be
                 * parametrized by x = 2t/(1+t^2), y = (1-t^2)/(1+t^2). by using
                 * Maple we derived the polynomial in t and subsequently its
                 * derivative wrt t, a quartic. The task is to find the real
                 * root(s) of this polynomial. We denote the 3 coefficients as
                 * db0,db1,db2,db3, db4 For precise derivation see Hans Wolters'
                 * lab book
                 */
                }
                if (length2d > 1 - eps || maxfound == 0) {
                    stat = computeMaximumOnCircle(a, normal);
                    if (stat == -1) // failed
                    {
                        length2d = (float) Math.sqrt(length2d);
                        if (length2d > zerotol) {
                            normal[0] /= length2d;
                            normal[1] /= length2d;
                        }
                    }
                }
                disc = 1.0f - normal[0] * normal[0] - normal[1] * normal[1];
                if (disc < 0.0) {
                    normal[2] = 0;
                } else {
                    normal[2] = (float) Math.sqrt(disc);
                }
            }
            //normal = Utils.normalize3(normal);
            len = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);
            normals.add( new VRGB((float) (normal[0] / len), (float) (normal[1] / len),
                    (float) (normal[2] / len)));
            normal = null;
            a = null;
        }
        return normals;
    }

    private static ArrayList _getN1(byte[][] coeff, boolean isRGBPTM, float[] bias, float[] scale) {
        int length = coeff.length;
        if (!isRGBPTM) {
            length = coeff[0].length;
        }

        ArrayList normals = new ArrayList();
        float[] a = new float[6];
        float length2d;
        float disc, len;
        int stat;
        int maxfound;
        float[] normal = new float[3];
        for (int i = 0; i < length; i++) {
            ////float[] normal = new float[3];
            //float[] a = new float[6];
            if (!isRGBPTM) {
                a[0] = (float) ((coeff[0][i] & 0x000000FF) - bias[0]) * scale[0];
                a[1] = (float) ((coeff[1][i] & 0x000000FF) - bias[1]) * scale[1];
                a[2] = (float) ((coeff[2][i] & 0x000000FF) - bias[2]) * scale[2];
                a[3] = (float) ((coeff[3][i] & 0x000000FF) - bias[3]) * scale[3];
                a[4] = (float) ((coeff[4][i] & 0x000000FF) - bias[4]) * scale[4];
                a[5] = (float) ((coeff[5][i] & 0x000000FF) - bias[5]) * scale[5];
            } else {
                for (int k = 0; k < 6; k++) {
                    a[k] = (float) ((((coeff[i][k] & 0x000000FF) - bias[k]) * scale[k]) + (((coeff[i][k + 6] & 0x000000FF) - bias[k]) * scale[k]) + (((coeff[i][k + 12] & 0x000000FF) - bias[k]) * scale[k]));
                }
            }



            /*
             * These coordinates are where the first deriviative of the
             * polynominal is zero
             */
            /* Derivation in lab notebook 2951-43 */
            /*
             * normal[0] = (2 * a[1] * a[3] - (a[4] / a[2]) ) / (1.0 - 4 * a[0] *
             * a[1]);
             */

            /** zero denominator in upcoming computations */
            if (Math.abs(a[2] * a[2] - 4 * a[1] * a[0]) < zerotol) {
                normal[0] = 0;
                normal[1] = 0;
            } else {
                if (Math.abs(a[2]) < zerotol) {
                    normal[0] = -1.0f * a[3] / (2.0f * a[0]);
                    normal[1] = -1.0f * a[4] / (2.0f * a[1]);
                } else {
                    normal[0] = (2.0f * a[1] * a[3] - a[2] * a[4]) / (a[2] * a[2] - 4.0f * a[1] * a[0]);
                    normal[1] = (-2.0f * a[0] * normal[0] - a[3]) / a[2];
                }
            }

            /** polynomial is constant we are done, set normal to be at 0,0,1 */
            if (Math.abs(a[0]) < zerotol && Math.abs(a[1]) < zerotol && Math.abs(a[2]) < zerotol && Math.abs(a[3]) < zerotol && Math.abs(a[4]) < zerotol) {
                normal[0] = 0.0f;
                normal[1] = 0.0f;
                normal[2] = 1.0f;
            } /**
             * clip normal[0], normal[1] values - these can both be unbounded
             * theoretically. first check if the vector (normal[0],normal[1]) is
             * greater than 1
             */
            else {
                length2d = normal[0] * normal[0] + normal[1] * normal[0];

                /*
                 * Add check for saddle or minimum. if p_uu >0 and/or
                 * p_uu*p_vv-p_uv*p_uv <0 -> saddle or minimum if this is the
                 * case then we should always look at boundary
                 */
                if (4 * a[0] * a[1] - a[2] * a[2] > eps && a[0] < -eps) {
                    maxfound = 1;
                } else {
                    maxfound = 0;
                /**
                 * Changed by Hans J. Wolters 12-11-99: instead of clipping, we
                 * attempt to find the minimum value on the circle. we know that
                 * since the polynomial is monotonous in the interior the
                 * extrema must be attained at the boundary. The circle will be
                 * parametrized by x = 2t/(1+t^2), y = (1-t^2)/(1+t^2). by using
                 * Maple we derived the polynomial in t and subsequently its
                 * derivative wrt t, a quartic. The task is to find the real
                 * root(s) of this polynomial. We denote the 3 coefficients as
                 * db0,db1,db2,db3, db4 For precise derivation see Hans Wolters'
                 * lab book
                 */
                }
                if (length2d > 1 - eps || maxfound == 0) {
                    stat = computeMaximumOnCircle(a, normal);
                    if (stat == -1) // failed
                    {
                        length2d = (float) Math.sqrt(length2d);
                        if (length2d > zerotol) {
                            normal[0] /= length2d;
                            normal[1] /= length2d;
                        }
                    }
                }
                disc = 1.0f - normal[0] * normal[0] - normal[1] * normal[1];
                if (disc < 0.0) {
                    normal[2] = 0;
                } else {
                    normal[2] = (float) Math.sqrt(disc);
                }
            }
            //normal = Utils.normalize3(normal);
            len = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);
            normals.add( new VRGB((float) (normal[0] / len), (float) (normal[1] / len),
                    (float) (normal[2] / len)));
        }
        return normals;
    }

    private static VRGB _getUN(float[] a) {
        float length2d;
        float disc;
        int stat;
        int maxfound;

       
        float[] normal = new float[3];
        
        if (Math.abs(a[2] * a[2] - 4 * a[1] * a[0]) < zerotol) {
            normal[0] = 0;
            normal[1] = 0;
        } else {
            if (Math.abs(a[2]) < zerotol) {
                normal[0] = -1.0f * a[3] / (2.0f * a[0]);
                normal[1] = -1.0f * a[4] / (2.0f * a[1]);
            } else {
                normal[0] = (2.0f * a[1] * a[3] - a[2] * a[4]) / (a[2] * a[2] - 4.0f * a[1] * a[0]);
                normal[1] = (-2.0f * a[0] * normal[0] - a[3]) / a[2];
            }
        }

        /** polynomial is constant we are done, set normal to be at 0,0,1 */
        if (Math.abs(a[0]) < zerotol && Math.abs(a[1]) < zerotol && Math.abs(a[2]) < zerotol && Math.abs(a[3]) < zerotol && Math.abs(a[4]) < zerotol) {
            normal[0] = 0.0f;
            normal[1] = 0.0f;
            normal[2] = 1.0f;
        } 
        else {
            length2d = normal[0] * normal[0] + normal[1] * normal[0];

            /*
             * Add check for saddle or minimum. if p_uu >0 and/or
             * p_uu*p_vv-p_uv*p_uv <0 -> saddle or minimum if this is the
             * case then we should always look at boundary
             */
            if (4 * a[0] * a[1] - a[2] * a[2] > eps && a[0] < -eps) {
                maxfound = 1;
            } else {
                maxfound = 0;
            /**
             * Changed by Hans J. Wolters 12-11-99: instead of clipping, we
             * attempt to find the minimum value on the circle. we know that
             * since the polynomial is monotonous in the interior the
             * extrema must be attained at the boundary. The circle will be
             * parametrized by x = 2t/(1+t^2), y = (1-t^2)/(1+t^2). by using
             * Maple we derived the polynomial in t and subsequently its
             * derivative wrt t, a quartic. The task is to find the real
             * root(s) of this polynomial. We denote the 3 coefficients as
             * db0,db1,db2,db3, db4 For precise derivation see Hans Wolters'
             * lab book
             */
            }
            if (length2d > 1 - eps || maxfound == 0) {
                stat = computeMaximumOnCircle(a, normal);
                if (stat == -1) // failed
                {
                    length2d = (float) Math.sqrt(length2d);
                    if (length2d > zerotol) {
                        normal[0] /= length2d;
                        normal[1] /= length2d;
                    }
                }
            }
            disc = 1.0f - normal[0] * normal[0] - normal[1] * normal[1];
            if (disc < 0.0) {
                normal[2] = 0;
            } else {
                normal[2] = (float) Math.sqrt(disc);
            }
        }
        normal = Utils.normalize3(normal);
        VRGB normals = new VRGB((float) normal[0], (float) normal[1],
                (float) normal[2]);

        return normals;
    }

    private static double evalPoly(float[] a, double t) {
        double u = 2 * t / (1 + t * t);
        double v = (1 - t * t) / (1 + t * t);
        return a[0] * u * u + a[1] * v * v + a[2] * u * v + a[3] * u + a[4] * v + a[5];
    }

    private static int computeMaximumOnCircle(float[] a, float[] normal) {
        float db0, db1, db2, db3, db4;
        float val1;
        double[] zeros = null;
        double[] c = null;
        float u, v, maxval, maxu = -1, maxv = -1, inc, arg, polyval;
        int index, nroots;

        index = -1;
        nroots = -1;

        db0 = a[2] - a[3];
        db1 = 4 * a[1] - 2 * a[4] - 4 * a[0];
        db2 = -6 * a[2];
        db3 = -4 * a[1] - 2 * a[4] + 4 * a[0];
        db4 = a[2] + a[3];

        /** polynomial is constant on circle, pick (0,1) as a solution */
        if (Math.abs(db0) < zerotol && Math.abs(db1) < zerotol && Math.abs(db2) < zerotol && Math.abs(db3) < zerotol) {
            normal[0] = 0.0f;
            normal[1] = 1.0f;
            zeros = null;
            c = null;
            return 1;
        }
        zeros = new double[4];
        if (db0 != 0) {
            c = new double[]{db4, db3, db2, db1, db0};
            nroots = GraphicsGems.SolveQuartic(c, zeros);
        } else if (db1 != 0) {
            c = new double[]{db4, db3, db2, db1};
            nroots = GraphicsGems.SolveCubic(c, zeros);
        } else /** TODO case where db2 is zero */
        {
            c = new double[]{db4, db3, db2};
            nroots = GraphicsGems.SolveQuadric(c, zeros);
        }
        if (nroots <= 0) {
            zeros = null;
            c = null;
            return -1;
        }

        switch (nroots) {
            case 1:
                index = 0;
                break;
            default:
                double[] vals = new double[nroots];
                for (int i = 0; i < vals.length; i++) {
                    vals[i] = evalPoly(a, zeros[i]);
                }
                index = Utils.indexOfMax(vals);
        }

        /**
         * I noticed that the fact that the pont (0,-1) on the circle can only
         * be attained in the limit causes it to be missed in case it really is
         * the maximum. Hence it is necessary to investigate a neighboring
         * region to find the potential maximum there, we look at the segment
         * from 260 degress to 280 degrees (270 degrees being the limit point).
         */
        normal[0] = (float) (2 * zeros[index] / (1 + zeros[index] * zeros[index]));
        normal[1] = (float) ((1 - zeros[index] * zeros[index]) / (1 + zeros[index] * zeros[index]));

        /**
         * test the correctness of solution:
         */
        maxval = -1000;

        for (int k = 0; k <= 20; k++) {
            inc = (1 / 9.0f) / 20 * k;
            arg = (float) Math.PI * (26.0f / 18.0f + inc);
            u = (float) Math.cos(arg);
            v = (float) Math.sin(arg);
            polyval = a[0] * u * u + a[1] * v * v + a[2] * u * v + a[3] * u + a[4] * v + a[5];
            if (maxval < polyval) {
                maxval = polyval;
                maxu = u;
                maxv = v;
            }
        }

        val1 = (float) (evalPoly(a, zeros[index]));
        if (maxval > val1) {
            normal[0] = maxu;
            normal[1] = maxv;
        }
        zeros = null;
        c = null;
        return 1;
    }

    public static ArrayList getNormals(LRGBPTM ptm) {
        return Normals.getNormals(ptm.getCoefficients(), false, null, null);
    }

    public static ArrayList getNormals(int[][] coeff, boolean isRGBPTM, int[] bias, float[] scale) {
        return _gN2(coeff, isRGBPTM, bias, scale);
    }

    public static ArrayList getNormals(byte[][] coeff, boolean isRGBPTM, float[] scale, float[] bias) {
        return _getN1(coeff, isRGBPTM, bias, scale);
    }

    public static VRGB getUnitNormals(float[] a) {

        return _getUN(a);
    }
}
