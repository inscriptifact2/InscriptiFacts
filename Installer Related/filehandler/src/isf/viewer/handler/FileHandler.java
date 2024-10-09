/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.handler;

import com.apple.eawt.OpenFilesHandler;
import java.lang.reflect.Method;

/**
 *
 * @author raok1
 */
public class FileHandler implements OpenFilesHandler {

    @Override
    public void openFiles(com.apple.eawt.AppEvent.OpenFilesEvent e) {
        String fname = "";
        if (e.getFiles().size() > 1) {
            fname = null;
        } else {
            fname = e.getFiles().get(0).getAbsolutePath();
        }
        try {
            Class[] params = {String.class};
            Class app = Class.forName("isf.install.loader.Launcher");
            Method m = app.getMethod("setFileName", params);
            m.invoke(null, fname);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }
}
