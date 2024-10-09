/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.utils;

import isf.common.utils.LogManager;
import isf.controls.controllers.ImageLoaderManager;
import isf.controls.models.DesktopDataObject;
import isf.viewer.containers.ISFDesktopFrame;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author raok1
 */
public class ClientSocket extends Thread {

    private Socket socket = null;
    static final Logger log = LogManager.getLogger(isf.viewer.utils.ClientSocket.class);

    public ClientSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        OutputStream out = null;
        String line = "";
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            line = (String) (ois.readObject());
            log.debug(line + " is being received");
            if (ISFDesktopFrame.validRTIFile(line)) {
                log.debug("Loading File:" + line);
                File f = new File(line);
                String path = f.getAbsolutePath();
                String name = f.getName();
                int ind = path.indexOf(name);
                // String curDir = path.substring(0, ind);
                DesktopDataObject ddo = new DesktopDataObject(name, name, path, name);
                ImageLoaderManager.getInstance().getLoader().addDesktopData(ddo);
                ImageLoaderManager.getInstance().getLoader().putImage(ddo);
            } else {
                log.debug("file name is wrong:" + line + "----");
            }
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            oos.writeObject(("File " + line + " processed."));

        } catch (IOException ex) {
            log.error(ex);
        } catch (ClassNotFoundException ex) {
            log.error(ex);
        } finally {
            try {
                ois.close();
                oos.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                log.error(ex);

            }
        }
    }
}
