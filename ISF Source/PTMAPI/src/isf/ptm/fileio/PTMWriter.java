/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.ptm.fileio;

import isf.ptm.formats.PTM;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author IN030223
 */
public abstract class  PTMWriter {

    private static PTMWriter _initWriter(PTM ptm) {
        PTMWriter writer =null;
        int type = ptm.getFileType();
        if(type==PTM.TYPE_RAW_PTM){
        switch(ptm.getBasisType())
        {
            case PTM.BASIS_RGB: writer =  new RGBWriter(); break;
            case PTM.BASIS_LRGB: writer = new LRGBWriter();break;
            default: break;
        }
        }else if(type==PTM.TYPE_RTI_HSH)
        {
            //to do code a rti writer
            return new HSHWriter();
            //return null;
        }
        return writer;
    }
    
    public abstract void writePTM(PTM ptm, OutputStream os) throws IOException;
    public byte[] getLine(int x ){
        return (x+"\n").getBytes();
    }
    public byte[] getLine(float[] x ){
        String str = "";
        String sep ="";
        for(int i =0; i < x.length;i++){
            str+=sep+x[i];
            sep = " ";
        }
        return (str+"\n").getBytes();
    }
     public byte[] getLine(int[] x ){
       String str = "";
        String sep ="";
        for(int i =0; i < x.length;i++){
            str+=sep+x[i];
            sep = " ";
        }
        return (str+"\n").getBytes();
    }
    public byte[] getLine(String x ){
        return (x+"\n").getBytes();
    }
    
    public static PTMWriter getPTMWriter(PTM ptm)
    {
        return _initWriter(ptm);
    }

}
