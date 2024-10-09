package isf.net.request;

import isf.common.request.PTMRequestProxy;
import isf.common.request.RequestProxy;
import isf.common.utils.LogManager;
import isf.common.utils.InscriptConfigLoader;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: kamesh Date: May 24, 2009 Time: 9:23:11 AM To
 * change this template use File | Settings | File Templates.
 */
public class PTMRequestDispatcher extends PTMRequestProxy{

  //  static RequestDispatcherObject rdo;
    static Logger log = LogManager.getLogger(isf.net.request.PTMRequestDispatcher.class);
    
    private PTMRequestDispatcher(){}
    private static PTMRequestDispatcher proxy = new PTMRequestDispatcher();

    static {
       // rdo = RequestDispatcherObject.getInstance();
        ImageIO.setUseCache(false);
        //RequestProxy.prp = proxy;
    }
    public static PTMRequestDispatcher getInstance(){
        if(proxy==null){
            proxy = new PTMRequestDispatcher();
        }
        return proxy;
    }
    private static String serverPtm = InscriptConfigLoader.getInstance().getProperty("segmentUrl");

    public byte[] getImageCoeff(String file, int index, int band, int fileType) {
        GetMethod method =null;
        BufferedImage image ;
        byte[] bArray = null;
        try {
            method = new GetMethod(serverPtm);
            NameValuePair[] param = new NameValuePair[3];
            param[0] = new NameValuePair("ptm", file);

            param[1] = new NameValuePair("segment", band + "_" + index + "");
            param[2] = new NameValuePair("file", fileType + "");
            method.setQueryString(param);
            method.setFollowRedirects(true);

            int status ;
            status = RequestDispatcherObject.client.executeMethod(method);
            BufferedInputStream ins = new BufferedInputStream(method.getResponseBodyAsStream());
            image = ImageIO.read(ins);
            
           if (status == HttpStatus.SC_OK) {
                //return bArray;
               if (image != null) {
                bArray = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                ins.close();
            } 
            } else if (status == 1500) {
                RequestDispatcherObject.lol.loggedout("Your Session Has Expired. Please Login Again");
                //return null;
                bArray = null;
            } else if (status == 1401) {
                RequestDispatcherObject.lol.raiseError("Server Error Reported. Please Contact Administrator");
                //return null;
                 bArray = null;
            } else if (status == 1404) {
                RequestDispatcherObject.lol.raiseError("Unknown Error Reported. Please Contact Administrator");
                //return null;
                 bArray = null;
            } else if (status == 404) {
                RequestDispatcherObject.lol.raiseError("Requested Image File is Not Present. Please Contact Administrator.");
               // return null;
                 bArray = null;
            }
        } catch (Exception ie) {
            log.error(ie);
             bArray = null;
        }finally{
            if (method != null) {
                method.releaseConnection();
            }
            return bArray;
        }
       // return null;
    }
}
