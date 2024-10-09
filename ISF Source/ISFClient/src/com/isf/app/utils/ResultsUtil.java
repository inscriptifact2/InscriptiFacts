/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.app.utils;

import isf.common.ISFBase64;
import isf.common.request.RequestProxy;
import isf.controls.controllers.ImageLoaderManager;
import java.io.StringReader;
import java.util.Vector;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

/**
 *
 * @author raok1
 */
public class ResultsUtil {

    public static Object getResults(XmlConverter converter, String query) {
        return "";
    }

    public static String getLogoutResults(String userName) {
        String s4 = "<QUERY TYPE='Logout' MODE='' SESSIONID='" + RequestProxy.getRequestDispatcher().sessionid +
                "'><CLAUSE CONNECTOR=''><QUERY_CONDITION KEY='useridentity' VALUE='" + ISFBase64.getQueryVal(userName) +
                "' OPERATOR='=' CONNECTOR=''>" + "</QUERY_CONDITION></CLAUSE></QUERY>";
        String result = (String) RequestProxy.getRequestDispatcher().handleQueryEvent(s4);
        return result;
    }

    public static Vector getResults(String queryType, String query, String[] msgs, boolean cache) throws Exception {
        String result = (String) RequestProxy.getRequestDispatcher().handleQueryEvent(query);
        Vector vector = null;
        if (result != null) {
            XmlConverter converter = DataStructureFactory.getConverter(queryType);
           // SAXParser saxparser4 = new SAXParser();
           // saxparser4.setContentHandler(converter);
            InputSource inputsource4 = new InputSource(new StringReader(result));
              SAXParser saxparser = SAXParserFactory.newInstance().newSAXParser();
            saxparser.parse(inputsource4,converter);
            msgs[0] = converter.getSccMsg();
            msgs[1] = converter.getErrMsg();
            vector = (Vector) converter.getDataStructure();
            if (converter instanceof com.isf.app.utils.InitializationXmlConverter) {
                msgs[2] = ((InitializationXmlConverter) converter).getSession();
            } else {
                msgs[2] = converter.getCount();
            }
            msgs[3] = converter.getListKeys();
        }
        if (cache) {
            ImageLoaderManager.cacheQuery(queryType, query);
        }

        return vector;


    }
}
