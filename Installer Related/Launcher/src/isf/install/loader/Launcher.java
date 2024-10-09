/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.install.loader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import javax.swing.JWindow;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author raok1
 */
public class Launcher {

    private static org.apache.log4j.Logger logger = LogManager.getLogger(Launcher.class);
    static String FNAME="";

    public static void main(String[] args) {
        PropertyConfigurator.configure("lib/log.ini");
        LaunchParams local = new LaunchParams("version.xml");
        String locCur = local.getVersion() + " Build: " + local.getBuild();
        Properties sys = new Properties(System.getProperties());   
        String os = sys.getProperty("os.name");
        boolean isMACOS = (os.toUpperCase().indexOf("MAC OS") > -1);
        if (local.getName().equalsIgnoreCase("ISF")) {
            JWindow winSplash = new JWindow();
            winSplash.setBackground(new Color(243, 231, 217));//0xE6DFD9
            LaunchPanel splashPanel = new LaunchPanel("isfsplash.jpg");
            winSplash.setLayout(new BorderLayout());
            winSplash.add(splashPanel, BorderLayout.CENTER);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            winSplash.setBounds(new Rectangle((dim.width - 461) / 2, (dim.height - 224) / 2, 461, 224));
            winSplash.setVisible(true);
            splashPanel.setVersions(locCur, "");
            //check for versions
            String[] files = checkForVersions(local);
            //download if required
            if (files != null && files.length > 0) {
                splashPanel.setVersions(locCur, local.getRemoteVersion());
                for (String s : files) {
                    s = s.trim();
                    splashPanel.setProgress(s);
                    try {
                        URL website = new URL("http://" + local.getHost() + "/version/" + s);
                        downloadFromUrl(website, s);
                    } catch (Exception exp) {
                        logger.error(exp);
                    }
                }
                splashPanel.stopLoad();
                local.cacheDetails();
            } else if (!local.getRemoteVersion().equalsIgnoreCase("NotFound")) {
                splashPanel.setProgress("You are using the lastest version", false);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ie) {
                    logger.error(ie);
                }
            } else {
                splashPanel.setProgress("You appear offline. Please connect to the internet and try again", false);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    logger.error(ie);
                }
                winSplash.setVisible(false);
                System.exit(0);
                return;
            }
        
        //launch application
            String javaHome = local.getJavahome();
            if(isMACOS && javaHome.startsWith(local.getInstalldir())){
                javaHome = javaHome+File.separator+"Contents"+File.separator+"Home"+File.separator+"jre"+ File.separator + "bin" + File.separator + "java";
            }else{
                javaHome = javaHome+ File.separator + "bin" + File.separator + "java";
            }
        //javaHome = javaHome + File.separator + "bin" + File.separator + "java";
        //    File f = new File(javaHome);
          //  f.
        ExecCommand ec = new ExecCommand();
        ec.setJar("lib" + File.separator + "ISFAPP.jar");
        ec.setCmd(javaHome);
        ec.setUserDir(local.getInstalldir());
        ec.setTitle("INSCRIPTIFACT - "+local.getVersion());
        ec.setMacOS(isMACOS);
        ec.setVmOptionsMax(local.getMaxheap());
        ec.setVmOptionsMin(local.getMinheap());
      
        winSplash.setVisible(false);
        ec.start();
        }else{ // processing Viewer
           
        

         if(isMACOS){ 
             processMacHandler();
        }else if(args.length > 0) {
            FNAME = args[0];// windows based file name
        }
         int port = local.getPort();
         //check server port active or not
      
         boolean launch = false;
         try {
               ServerSocket s =  new ServerSocket(local.getPort(),1,InetAddress.getLoopbackAddress());
               s.close();
                launch = true;

            } catch (SocketException se) {
                launch = false;
               logger.debug(se.getMessage());
            } catch (Exception exp) {
                launch = false;
               logger.debug(exp.getMessage());
            }
         if(launch){
            
            JWindow winSplash = new JWindow();
            winSplash.setBackground(new Color(243, 231, 217));//0xE6DFD9
            LaunchPanel splashPanel = new LaunchPanel("sasplash.jpg");
            winSplash.setLayout(new BorderLayout());
            winSplash.add(splashPanel, BorderLayout.CENTER);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            winSplash.setBounds(new Rectangle((dim.width - 461) / 2, (dim.height - 224) / 2, 461, 224));
            winSplash.setVisible(true);
            splashPanel.setVersions(locCur, "");
            String[] files = checkForVersions(local);
            if (files != null && files.length > 0) {
                splashPanel.setVersions(locCur, local.getRemoteVersion());
                for (String s : files) {
                    s = s.trim();
                    splashPanel.setProgress(s);
                    try {
                        URL website = new URL("http://" + local.getHost() + "/version/" + s);
                        downloadFromUrl(website, s);
                    } catch (Exception exp) {
                        logger.error(exp);
                    }
                }
                splashPanel.stopLoad();
                local.cacheDetails();
            } else if (!local.getRemoteVersion().equalsIgnoreCase("NotFound")) {
                splashPanel.setProgress("You are using the lastest version", false);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ie) {
                    logger.error(ie);
                }
            } else {
                splashPanel.setProgress("You appear offline. Will launch Standalone mode", false);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ie) {
                    logger.error(ie);
                }
            }
             winSplash.setVisible(false);
            String path = "file:///"+local.getInstalldir()+File.separator+"lib"+File.separator+"ISFVIEWER.jar";
             logger.debug("Path:"+path);
             String[] params = new String[2];
                if(FNAME.equalsIgnoreCase(""))
                    params[0]="standalone";
                else
                    params[0] = FNAME;
                params[1] = local.getPort()+"";
                try{
            JarClassLoader loader = new JarClassLoader(new URL(path));
            String classname = loader.getMainClassName();
            loader.invokeClass(classname, params);
                }catch(Exception malexp){
                    logger.error(malexp);
                }
         }else{
             //send fname via socket
                     try {
            Socket client = new Socket(InetAddress.getLoopbackAddress(), port);
            OutputStream soc = client.getOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(soc);

            logger.debug(FNAME);
            oos.writeObject(FNAME);
            oos.flush();
            oos.close();
            ObjectInputStream ios = new ObjectInputStream(client.getInputStream());

            logger.debug((String) (ios.readObject()));

            ios.close();
            client.close();
        } catch (Exception exp) {
            exp.printStackTrace();
            logger.error(exp);
        }
         }
    }
    }

    
    
    

private static String[] checkForVersions(LaunchParams local) {
       LaunchParams remote = new LaunchParams();
       boolean online =  remote.populateFromRemote(local.getHost(), local.getName());
       String[] files = null;
       if(!online)
       {
           return files;
       }

        String locCur = local.getVersion() + " Build: " + local.getBuild();
        String remCur = remote.getVersion() + " Build: " + remote.getBuild();
        local.setRemoteVersion(remCur);
        if (!remote.getVersion().equalsIgnoreCase("NotFound")) {

            if (!locCur.equalsIgnoreCase(remCur)) {
                files = remote.getFiles();
                local.setVersion(remote.getVersion());
                local.setBuild(remote.getBuild());
            }

        }
        return files;
    }

    private static void downloadFromUrl(URL url, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
         logger.debug("Downloading:"+localFilename);
        try {
            URLConnection urlConn = url.openConnection();//connect
            is = urlConn.getInputStream();               //get connection inputstream
            fos = new FileOutputStream("lib/" + localFilename);   //open outputstream to local file
            byte[] buffer = new byte[4096];              //declare 4KB buffer
            int len;
            //while we have availble data, continue downloading and storing to local file
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
         logger.debug("Downloaded:"+localFilename);
    }

    private static void processMacHandler() {
        com.apple.eawt.Application a = com.apple.eawt.Application.getApplication();
        a.setOpenFileHandler(new com.apple.eawt.OpenFilesHandler() {
            @Override
        public void openFiles(com.apple.eawt.AppEvent.OpenFilesEvent e) {
                if (e.getFiles().size() > 1) {
                    FNAME = null;
                } else {
                    FNAME = e.getFiles().get(0).getAbsolutePath();
                }
            }
        });

    }
    
    
     
    
}
