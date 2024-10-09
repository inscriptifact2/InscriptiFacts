/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.sc.beans;

import isf.sc.utils.InscriptConfigServerLoader;
import isf.sc.utils.ReadThread;
import isf.sc.utils.SessionInvalidException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 *
 * @author raok1
 */
public class ImageBean extends ISFBean {

    public ImageBean() {

        location = InscriptConfigServerLoader.getInstance().getProperty("jp2Loc");
        extension = InscriptConfigServerLoader.getInstance().getProperty("ext_download");
    }
    String location = "";
    String extension = "";

    public Hashtable getSidFile(String s, String l, String st1, String session)
            throws SQLException, SessionInvalidException, Exception {

        //    byte abyte0[] = new String("temp").getBytes();
        Hashtable ht = new Hashtable();

        //   updateSession(session);
        RandomAccessFile jp2File = new RandomAccessFile(location + "/" + s + extension, "r");
        long length = jp2File.length();

        if (length > 0) {
            int noArray = 8;
            int len = (int) (length / noArray);
            int left = (int) length % noArray;
            long stpos = 0;
            ThreadGroup tg = new ThreadGroup("readSid");
            int i = 0;
            for (; i < noArray; i++) {
                new ReadThread(i, tg, ht, stpos, len, jp2File);
                stpos += len;
            }
            if (left > 0) {
                new ReadThread(i, tg, ht, stpos, left, jp2File);
            }
            while (tg.activeCount() > 0) {
            }
            ht.put("length", length + "");

        }


        return ht;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
}
