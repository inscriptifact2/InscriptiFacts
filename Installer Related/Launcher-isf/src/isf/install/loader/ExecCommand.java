/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.install.loader;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import org.apache.log4j.LogManager;

/**
 *
 * @author raok1
 */
public class ExecCommand extends Thread {

    private String fName = "";
    private String vmOptionsMin = "-Xms768m";
    private String vmOptionsMax = "-Xmx1300m";
    private String cmd = "C:\\Program Files (x86)\\Java\\jre1.8.0_25\\bin\\java";
    private String jar = "lib\\ISFVIEWER.jar";
    private String userDir = "E:\\ISFSource\\ISFLauncher";
    private String tts="-Xdock:name=";//-agentlib:jdwp=transport=dt_socket,address=localhost:7800,server=n,suspend=y 
    private String title="";
    private boolean macOS = false;
    private int port;
    private static org.apache.log4j.Logger logger = LogManager.getLogger(ExecCommand.class);
   

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the jar
     */
    public String getJar() {
        return jar;
    }

    /**
     * @param jar the jar to set
     */
    public void setJar(String jar) {
        this.jar = jar;
    }

    /**
     * @return the userDir
     */
    public String getUserDir() {
        return userDir;
    }

    /**
     * @param userDir the userDir to set
     */
    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
            ProcessBuilder pb = new ProcessBuilder();
            
            pb = pb.directory(new File(userDir));

            if (getfName().equalsIgnoreCase("")) {
               
                if (port > 0) {
                    if(isMacOS())
                        pb = pb.command(cmd, tts+title,getVmOptionsMin(), getVmOptionsMax(), "-jar", jar, port + "");
                    else
                        pb =  pb.command("\""+cmd+"\"", getVmOptionsMin(), getVmOptionsMax(), "-jar", jar, port + "");
                } else {
                    if(isMacOS())
                        pb = pb.command(cmd,  tts+title,getVmOptionsMin(), getVmOptionsMax(), "-jar", jar);
                    else
                      pb=  pb.command("\""+cmd+"\"", getVmOptionsMin(), getVmOptionsMax(), "-jar", jar);
                }
            } else {
              if(isMacOS())
                pb =pb.command(cmd,tts+title, getVmOptionsMin(), getVmOptionsMax(), "-jar", jar, getfName(), port + "");
               else
                pb = pb.command("\""+cmd+"\"", getVmOptionsMin(), getVmOptionsMax(), "-jar", jar, getfName(), port + "");  
            }
           
            Process p = pb.start();
          
            inheritIO(p.getInputStream());
            inheritIO(p.getErrorStream());
            p.waitFor();
            System.exit(0);


        } catch (InterruptedException exp) {
            logger.debug(exp);
        } catch (IOException ioe) {
            logger.debug(ioe);
        }

    }

    private static void inheritIO(final InputStream src) {
        new Thread(new Runnable() {
            public void run() {
                Scanner sc = new Scanner(src);
                while (sc.hasNextLine()) {
                    logger.debug(sc.nextLine());

                }
            }
        }).start();
    }

    public static void main(String[] args) {
        ExecCommand exc = new ExecCommand();
        exc.start();
    }

    /**
     * @return the vmOptionsMin
     */
    public String getVmOptionsMin() {
        return vmOptionsMin;
    }

    /**
     * @param vmOptionsMin the vmOptionsMin to set
     */
    public void setVmOptionsMin(String vmOptionsMin) {
        this.vmOptionsMin = "-Xms" + vmOptionsMin;
    }

    /**
     * @return the vmOptionsMax
     */
    public String getVmOptionsMax() {
        return vmOptionsMax;
    }

    /**
     * @param vmOptionsMax the vmOptionsMax to set
     */
    public void setVmOptionsMax(String vmOptionsMax) {
        this.vmOptionsMax = "-Xmx" + vmOptionsMax;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the macOS
     */
    public boolean isMacOS() {
        return macOS;
    }

    /**
     * @param macOS the macOS to set
     */
    public void setMacOS(boolean macOS) {
        this.macOS = macOS;
    }

  
}
