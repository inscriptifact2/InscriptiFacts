/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.install.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author raok1
 */
public class LaunchParams {

    private String name, version, build, host, installdir,javahome,maxheap,minheap;
    private String[] files;
    private int port;
    private String remoteVersion;

    public LaunchParams() {
    }

    public LaunchParams(String fileName) {
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            setName(doc.getElementsByTagName("name").item(0).getTextContent());
            setVersion(doc.getElementsByTagName("version").item(0).getTextContent());
            setBuild(doc.getElementsByTagName("build").item(0).getTextContent());
            setHost(doc.getElementsByTagName("host").item(0).getTextContent());
            setInstalldir(doc.getElementsByTagName("installdir").item(0).getTextContent());
            if(getName().equalsIgnoreCase("Viewer"))
                setPort(Integer.parseInt(doc.getElementsByTagName("port").item(0).getTextContent()));
            if(getName().equalsIgnoreCase("Isf")){
                setJavahome(doc.getElementsByTagName("javahome").item(0).getTextContent());
                setMaxheap(doc.getElementsByTagName("maxheap").item(0).getTextContent());
                setMinheap(doc.getElementsByTagName("minheap").item(0).getTextContent());
            }
            setRemoteVersion("NotFound");

        } catch (NumberFormatException nfe) {
            setPort(1725);
            nfe.printStackTrace();
        } catch (Exception exp) {
            exp.printStackTrace();
            // Logger.debug(exp);
        }

    }

    public boolean populateFromRemote(String server, String appName) {
        boolean online = false;
        try {
            URL url = new URL("http://" + server + "/version.xml");
            URLConnection conn = url.openConnection();
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = dBuilder.parse(conn.getInputStream());
            doc.getDocumentElement().normalize();
            setName(appName);
            setVersion(doc.getElementsByTagName("version").item(0).getTextContent());
            setBuild(doc.getElementsByTagName("build").item(0).getTextContent());
            setFiles(doc.getElementsByTagName(appName.trim() + "Files").item(0).getTextContent());
            online=true;
        } catch (Exception exp) {
            exp.printStackTrace();
            this.reset();
            online=false;
            //Logger.debug(exp);
        }finally{
            return online;
        }

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the build
     */
    public String getBuild() {
        return build;
    }

    /**
     * @param build the build to set
     */
    public void setBuild(String build) {
        this.build = build;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the installdir
     */
    public String getInstalldir() {
        return installdir;
    }

    /**
     * @param installdir the installdir to set
     */
    public void setInstalldir(String installdir) {
        this.installdir = installdir;
    }

    public String getXMLContent() {
        StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\"?>");
        buffer.append("<application>");
        buffer.append("<name>" + getName() + "</name>");
        buffer.append("<version>" + getVersion() + "</version>");
        buffer.append("<build>" + getBuild() + "</build>");
        buffer.append("<host>" + getHost() + "</host>");
        buffer.append("<installdir>" + getInstalldir() + "</installdir>");
        if(getName().equalsIgnoreCase("ISF")){
        buffer.append("<javahome>" + getJavahome()+ "</javahome>");
        buffer.append("<maxheap>" + getMaxheap() + "</maxheap>");
        buffer.append("<minheap>" + getMinheap() + "</minheap>");
        }
         if(getName().equalsIgnoreCase("Viewer")){
            buffer.append("<port>" + getPort() + "</port>");
         }

        buffer.append("</application>");
        return buffer.toString();
    }

    /**
     * @return the viewerFiles
     */
    public String[] getFiles() {
        return files;
    }

    /**
     * @param viewerFiles the viewerFiles to set
     */
    public void setFiles(String fileNames) {
        files = fileNames.split(",");
    }

    public void cacheDetails() {
        try {
            FileOutputStream fos = new FileOutputStream("version.xml");
            fos.write(getXMLContent().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception eexp) {
            eexp.printStackTrace();

        }
    }

    public void reset() {
        setName("");
        setVersion("");
        setBuild("");
        setFiles("");
        setHost("");
        setInstalldir("");
        setMaxheap("-Xmx1300m");
        setMinheap("-Xms768m");
        setJavahome("");
        setPort(1725);

        setRemoteVersion("NotFound");
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
     * @return the remoteVersion
     */
    public String getRemoteVersion() {
        return remoteVersion;
    }

    /**
     * @param remoteVersion the remoteVersion to set
     */
    public void setRemoteVersion(String remoteVersion) {
        this.remoteVersion = remoteVersion;
    }

    /**
     * @return the javahome
     */
    public String getJavahome() {
        return javahome;
    }

    /**
     * @param javahome the javahome to set
     */
    public void setJavahome(String javahome) {
        this.javahome = javahome;
    }

    /**
     * @return the maxheap
     */
    public String getMaxheap() {
        return maxheap;
    }

    /**
     * @param maxheap the maxheap to set
     */
    public void setMaxheap(String maxheap) {
        this.maxheap = maxheap;
    }

    /**
     * @return the minheap
     */
    public String getMinheap() {
        return minheap;
    }

    /**
     * @param minheap the minheap to set
     */
    public void setMinheap(String minheap) {
        this.minheap = minheap;
    }
}
