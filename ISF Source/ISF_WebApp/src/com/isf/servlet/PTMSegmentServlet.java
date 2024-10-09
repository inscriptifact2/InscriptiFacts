package com.isf.servlet;


import com.isf.parsers.LazyParser;
import com.isf.webutils.CacheHandler;

import com.isf.webutils.ConfigUtils;
import com.isf.webutils.InscriptWebLoader;
//import isf.cache.utils.CacheHandler;
import isf.common.utils.LogManager;
import isf.ptm.formats.PTM;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Jul 17, 2007
 * Time: 12:43:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class PTMSegmentServlet extends HttpServlet {
    String ptmPath;
    String cachePath;
    Boolean zip;
    Logger log = LogManager.getLogger(this.getClass());
    // private String[] colorBands;

    public void init() throws ServletException {
        super.init();    //To change body of overridden methods use File | Settings | File Templates.
        //   colorBands = new String[]{"red","green","blue"};
        ptmPath = InscriptWebLoader.getInstance().getProperty("ptmFolder");
        cachePath = InscriptWebLoader.getInstance().getProperty("cacheDirectory");
        zip = Boolean.valueOf(InscriptWebLoader.getInstance().getProperty("cacheZipped"));
        this.getServletContext().setAttribute("ZIP", zip);


    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	/*HttpSession httpsession = request.getSession(false);
	 javax.servlet.ServletOutputStream servletoutputstream = response.getOutputStream();
	 if(httpsession ==null)
	 {
				response.sendError(1500, "sessioninvalidated");
				
                servletoutputstream.close();
                return;
	 }else
	 {*/

        String ptmName = request.getParameter("ptm");
        String type = request.getParameter("file");
        
        if(type==null)
            type = ".ptm";
        else{
            if(type.equalsIgnoreCase(PTM.TYPE_RAW_PTM+""))
                type = ".ptm";
            else
                type = ".rti";
        }
        if (ptmName != null) {

            String segment = request.getParameter("segment");
            try {
                StringBuffer buf = new StringBuffer();
                buf.append(ptmName);
                buf.append(type);
                File f = new File(ptmPath, buf.toString());
                String path = cachePath + File.separator + ptmName;
                if (!CacheHandler.getInstance().getCache().isCached(path)) {
                    log.debug("Not Cached, hence caching ptm now");
                    LazyParser.getPTMParser(new FileInputStream(f), ptmName, f.length()).cachePTM();
                }

                CacheHandler.getInstance().getCache().updateEntry(path);

                log.debug("Obtaining coeffs. will send now");
                long start = System.currentTimeMillis();
                String file = ConfigUtils.path + File.separator + ptmName + File.separator + ptmName + "_" + segment + ".jp2";


                OutputStream os = (response.getOutputStream());

                FileInputStream fis = new FileInputStream(file);
                int buflen = 10 * 1024 * 1024;
                byte[] data = new byte[buflen];
                int len = 0;
                BufferedInputStream bis = new BufferedInputStream(fis, buflen);
                while ((len = bis.read(data, 0, buflen)) != -1) {
                    os.write(data, 0, len);
                }
                log.debug("Wrote file to stream:" + file + ": in:" + (System.currentTimeMillis() - start));
                os.flush();

                bis.close();
                fis.close();
 

            } catch (Exception ie) {

                ie.printStackTrace();
            }
        } else {
            String zip = request.getParameter("zip");
            if (zip != null || zip.length() > 0) {
                Boolean com = Boolean.valueOf(zip);
                this.getServletContext().setAttribute("ZIP", com);
            }
        }
		//}


    }


}
