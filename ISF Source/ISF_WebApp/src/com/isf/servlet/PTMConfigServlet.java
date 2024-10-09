package com.isf.servlet;

import com.isf.webutils.ConfigUtils;
import com.isf.webutils.InscriptWebLoader;
import isf.common.utils.LogManager;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Jun 27, 2007
 * Time: 4:18:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class PTMConfigServlet extends HttpServlet {
    String ptmPath;
    String cachePath;
    int levels;
    Logger log = LogManager.getLogger(this.getClass());

    public void init() throws ServletException {
        super.init();    //To change body of overridden methods use File | Settings | File Templates.
        ptmPath = InscriptWebLoader.getInstance().getProperty("ptmFolder");
        cachePath = InscriptWebLoader.getInstance().getProperty("cacheDirectory");
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	 
	 
	String ptmName = request.getParameter("ptm");
	 
        
        if (ptmName != null ) {

            try {
                
                response.getOutputStream().write(ConfigUtils.getConfigString(ptmName).getBytes());


            } catch (Exception ie) {
                ie.printStackTrace();
                log.error(ie);
            }
        } else {
		
				
          /*  File f = new File(InscriptWebLoader.getInstance().getProperty("ptmFolder"));
            String[] ls;
            StringBuffer result = new StringBuffer();
            result.append("<Body><PTMS names=\"");
            String sep = "";
            FilenameFilter filter = new PTMFileFilter();
            int i;
            String name = "";
            for (ls = f.list(filter), i = 0;
                 ls != null && i < ls.length;
                 i++) {
                name = ls[i];
                if (name.toLowerCase().endsWith(".ptm"))
                    name = name.substring(0, name.length() - 4);
                result.append(sep);
                result.append(name);
                sep = ",";
            }*/
            
            File f = new File(InscriptWebLoader.getInstance().getProperty("cacheDirectory"));
            File[] ls;
            StringBuffer result = new StringBuffer();
            result.append("<Body><PTMS names=\"");
            String sep = "";
          
            int i;
            
            for (ls = f.listFiles(), i = 0;
                 ls != null && i < ls.length;
                 i++) {
                
                if(ls[i].isDirectory()){
                result.append(sep);
                result.append(ls[i].getName());
                sep = ",";
                }
            }
            result.append("\"></PTMS></Body>");
            //logger.debug("result:" + result);
            log.debug("Getting names:" + result.toString());
            response.getOutputStream().write(result.toString().getBytes());

        }
		


    }
}
