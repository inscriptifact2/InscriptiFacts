package isf.net.request;

import isf.common.request.ImageRequestProxy;
import isf.common.request.RequestProxy;
import isf.common.utils.InscriptConfigLoader;
import isf.common.utils.LogManager;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

public class ImageRequestObject extends ImageRequestProxy{

    static String s1;
    Logger log = LogManager.getLogger(isf.net.request.ImageRequestObject.class);
    private static ImageRequestObject proxy = new ImageRequestObject();

    static {
        try {
            s1 = InscriptConfigLoader.getInstance().p.getProperty("imgserver");
        } catch (Exception ie) {
        }
       
    }
    
    public static ImageRequestObject getInstance(){
        if(proxy==null){
            proxy = new ImageRequestObject();
        }
        return proxy;
    }

    private  ImageRequestObject() {
    }
    public ImageIcon fetchImage(String s) {
        ImageIcon ic =  null;
        String s2 = s1 + "/getImage?" + s;
        try {
            ic = new ImageIcon (new URL(s2));
        } catch (Exception ie) {
            log.error(ie);
        }
        return ic;
    }
    public Object getImageProperties(String s) {
        Object obj = null;
        try {
            String img = s1 + "/browse?" + s;
            URL url = new URL(img);
            URLConnection urlconnection = url.openConnection();

            java.io.InputStream inputstream = urlconnection.getInputStream();
            BufferedInputStream objectinputstream = new BufferedInputStream(inputstream);
            obj = new byte[1024];
            objectinputstream.read((byte[]) obj);
            objectinputstream.close();
        } catch (java.net.ConnectException ce) {
            log.error(ce);
            obj = null;
        } catch (Exception exception) {
            log.error(exception);
            obj= null;
        } finally {
            return obj;
        }
    }
    public String saveFile(String s, String filename) {
        String msg = "";
        try {
            String s2 = s1 + "/getImage?" + s;
            URL url = new URL(s2);
            URLConnection urlconnection = url.openConnection();
            java.io.InputStream inputstream = urlconnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputstream);
            FileOutputStream os = new FileOutputStream(filename);
            int i = bis.read();
            while (i != -1) {
                os.write(i);
                i = bis.read();
            }
            bis.close();
            os.flush();
            os.close();
            msg = "File Saved as " + filename;
        } catch (java.net.ConnectException ce) {
            msg = "Server not Available. Try Later.";
        } catch (Exception exception) {
            msg = "Error in Saving";
        } finally {
            return msg;
        }
    }
}
