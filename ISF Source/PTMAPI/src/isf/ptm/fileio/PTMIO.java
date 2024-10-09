package isf.ptm.fileio;

import isf.ptm.formats.MVRTI;
import isf.ptm.formats.PTM;
import isf.ptm.utils.Utils;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author Default
 */
public abstract class PTMIO {

    private static final int BUFSIZ = 2048;
    static Logger log = LogManager.getLogger(isf.ptm.fileio.PTMIO.class);

    public static float cFinal(int cRaw, int bias, float scale) {
        return (cRaw - bias) * scale;
    }

    public static int cFinalInt(int cRaw, int bias, float scale) {
        return Math.round((cRaw - bias) * scale);
    }
    
     public static PTMReader getPTMParser(InputStream in, boolean rti) throws Exception {
     return _getParser(in,rti);
     }

    private static PTMReader _getParser(InputStream in, boolean rti) throws Exception {
        if (!rti) {
            return _initParser(in);
        }
        boolean error = false;
        PTMReader reader = null;
        int type = 0;
        String line = "#";

        try {

            in.mark(BUFSIZ);
            while (line.startsWith("#")) {
                line = PTMIO.getLine(in);
            }


            // read the rti type should be one of 4 ( ptm, sh,hsh or aptm);

            type = Integer.parseInt(line);
            // PTMIO.getLine(in);
            int[] details = PTMIO.getLineAsInts(in);
            int basis = details[1];
            boolean reset = true;
            try {
                in.reset();
            } catch (java.io.IOException e) {
                reset = false;
               // System.out.println("reset:" + reset);
                log.error(e.getMessage()+":: hence continuing reading form here onwards");
            }

            switch (type) {
                case PTM.TYPE_RTI_PTM:
                    if (basis == PTM.BASIS_LRGB) {
                        reader = new LRGBReader(in, rti);
                        reader.reset(reset);
                        //return r;
                    } else if (basis == PTM.BASIS_RGB) {
                        reader = new RGBReader(in, rti);
                        reader.reset(reset);

                    }
                    break;

                case PTM.TYPE_RTI_SH:
                    error = true;
                    break;
                case PTM.TYPE_RTI_HSH:
                    if (reset) {
                        reader = new HSHReader(in, rti);
                    } else {
                        reader = new HSHReader(in, rti, details, type);
                    }
                    reader.reset(reset);


                    break;
                case PTM.TYPE_RTI_APTM:
                    error = true;
                    break;
                default:
                    error = true;
                    break;
            }

        } catch (Exception e) {
           log.error( e);
            throw e;
        }
        if (error) {
            throw new Exception("Unsupported Type: " + type);
        }
        return reader;


        }

        
    public static MVRTI getMView(InputStream in)    throws Exception
    {
       return _initMulti(in);
    }

    private static MVRTI _initMulti(InputStream in) throws Exception {
        //read mview file and get the first ptm file:
            String line = "#";
            MVRTI mview = new MVRTI();

            try {

                while (line.startsWith("#")) {
                    line = PTMIO.getLine(in);
                }
                
                
                int[] details = PTMIO.getLineAsInts(line); // <max_views_x> <max_views_y> <actual_viewpoints>
                int maxX = details[0];
                int maxY = details[1];
                int views = details[2];
                mview.setMaxX(maxX);
                mview.setMaxY(maxY);
                mview.setViews(views);
                details = PTMIO.getLineAsInts(in); // <start_x> <start_y>
                details = PTMIO.getLineAsInts(in); // <use_flow> <turntable_based> <separation_x> <separation_y>
                int useFlow=details[0];
                int orientation = details[1];
                mview.setUseFlow(useFlow);
                mview.setOrientation(orientation);
                String[] lines=new String[2];
                Hashtable files = new Hashtable();
                for(int i = 0 ; i < views; i++)
                {
                    line = PTMIO.getLine(in);
                    StringTokenizer st = new StringTokenizer(line," ");
                    int p=0;
                    while(st.hasMoreTokens())
                        lines[p++] = st.nextToken();
                    files.put(lines[0],lines[1]);
                   
                }
                mview.setFiles(files);
                
                 
            }catch(Exception exp){
                log.error(exp);
                throw exp;
            }
            
        
       return mview;
    }
    
	

  private  static PTMReader _initParser(InputStream in) throws Exception {

        try {
            in.mark(BUFSIZ);
            String version = PTMIO.getLine(in);
            String type = PTMIO.getLine(in);

            if (type.equals(PTM.PTM_LRGB)) {
                boolean reset = true;
                try {
                    in.reset();
                } catch (java.io.IOException e) {
                    reset = false;
                }
                LRGBReader r = new LRGBReader(in);
                r.setVersion(version);
                r.reset(reset);
                return r;
            } /*else if (type.equals("PTM_FORMAT_JPEG_LRGB")) {
            boolean reset = true;
            try {
            in.reset();
            } catch (java.io.IOException e) {
            reset = false;
            }
            JPEGLRGBReader r = new JPEGLRGBReader(in);
            r.setVersion(version);
            r.reset(reset);
            return r;
            }*/ else if (type.equals(PTM.PTM_RGB)) {
                boolean reset = true;
                try {
                    in.reset();
                } catch (java.io.IOException e) {
                    reset = false;
                }
                RGBReader r = new RGBReader(in);
                r.setVersion(version);
                r.reset(reset);
                return r;
            } else {
                throw new Exception("Unsupported Type: " + type);
            }

        } catch (Exception e) {
            log.error(e);
            throw e;
        }

    // return null; /* never reached */

    }

    /**
     * Read a line of text. Add EOF check, or use buffered stream.
     */
    public static String getLine(InputStream in) {
        int b;
        StringBuffer buf = new StringBuffer();

        try {
            while ((b = in.read()) != '\n') {
                if(b != '\r')
                    buf.append((char) b);
            }
        } catch (Exception e) {
           log.error( e);
        }
        return new String(buf);
    }

    public static int[] getLineAsInts(InputStream in) {
    String[] values = Utils.split(PTMIO.getLine(in)," ");
    int[] details = new int[values.length];
    
       for(int i=0; i < values.length; i++)
       {
            details[i] = Integer.parseInt(values[i].trim());
       }
        return details;

    }
    
    public static int[] getLineAsInts(String line) {
    String[] values = Utils.split(line," ");
    int[] details = new int[values.length];
    
       for(int i=0; i < values.length; i++)
       {
           details[i] = Integer.parseInt(values[i].trim());
       }
        return details;

    }
    
}
