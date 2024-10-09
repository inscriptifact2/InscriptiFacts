package com.isf.install;

import com.zerog.ia.api.pub.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author author
 */
public class AddFileAssociations extends CustomCodeAction {

    @Override
    public void install(InstallerProxy proxy) throws InstallException {
        String appname = proxy.substitute("$PRODUCT_NAME$");
        String installDir = proxy.substitute("$USER_INSTALL_DIR$");
        File infoplist = new File(installDir + File.separator + appname + ".app" + File.separator + "Contents" + File.separator + "Info.plist");
        String systemValue = "";

        DocumentBuilder dBuilder;
        Document doc;
        try {
            dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            doc = dBuilder.parse(infoplist);
            if (doc.getDoctype() != null) {
                systemValue = doc.getDoctype().getSystemId();
                doc.getDocumentElement().normalize();
                Node plist = doc.getElementsByTagName("plist").item(0);
                Node dict = doc.getElementsByTagName("dict").item(0);
                Node fNode = dict.getFirstChild();
                
                Element docTypeKey = doc.createElement("key");
                docTypeKey.setTextContent("CFBundleDocumentTypes");
                Element dtkArray = doc.createElement("array");
                Element dtkADict = doc.createElement("dict");
                Element btNameKey = doc.createElement("key");
                btNameKey.setTextContent("CFBundleTypeName");
                Element btName = doc.createElement("string");
                btName.setTextContent("ISFStandaloneViewer");
                Element conType = doc.createElement("key");
                conType.setTextContent("LSItemContentTypes");               
                Element ctArray = doc.createElement("array");
                Element ctAVal = doc.createElement("string");
                ctAVal.setTextContent("isf.ptmrti.viewer");
                ctArray.appendChild(ctAVal);
                Element btRoleKey = doc.createElement("key");
                btRoleKey.setTextContent("CFBundleTypeRole");
                Element btRVal = doc.createElement("string");
                btRVal.setTextContent("Viewer");
                Element lsHandlerKey = doc.createElement("key");
                lsHandlerKey.setTextContent("LSHandlerRank");
                Element lsHVal = doc.createElement("string");
                lsHVal.setTextContent("Owner");
                Element btIconKey = doc.createElement("key");
                btIconKey.setTextContent("CFBundleTypeRole");
                Element btIcon = doc.createElement("string");
                btIcon.setTextContent("LaunchAnywhere.icns");        
                dtkADict.appendChild(btNameKey);
                dtkADict.appendChild(btName);
                dtkADict.appendChild(conType);
                dtkADict.appendChild(ctArray);
                dtkADict.appendChild(btRoleKey);
                dtkADict.appendChild(btRVal);
                dtkADict.appendChild(btRoleKey);
                dtkADict.appendChild(btRVal);
                dtkADict.appendChild(lsHandlerKey);
                dtkADict.appendChild(lsHVal);
                dtkADict.appendChild(btIconKey);
                dtkADict.appendChild(btIcon);
                dtkArray.appendChild(dtkADict);

                
                
                Element utetDecKey = doc.createElement("key");
                utetDecKey.setTextContent("UTExportedTypeDeclarations");
                Element utArray = doc.createElement("array");
                Element utADict = doc.createElement("dict");
	        Element utType = doc.createElement("key");
                utType.setTextContent("UTTypeConformsTo");
                Element typeArray = doc.createElement("array");
                Element data = doc.createElement("string");
                data.setTextContent("public.data");
                Element img = doc.createElement("string");
                img.setTextContent("public.image");
                typeArray.appendChild(data);
                typeArray.appendChild(img);
  	        Element utTypeId = doc.createElement("key");
                utTypeId.setTextContent("UTTypeIdentifier");              
                Element utId = doc.createElement("string");
                utId.setTextContent("isf.ptmrti.viewer");			
  	        Element utTypeTag = doc.createElement("key");
                utTypeTag.setTextContent("UTTypeTagSpecification"); 
                Element tagDict = doc.createElement("dict");
                Element keyExt = doc.createElement("key");
                keyExt.setTextContent("public.filename-extension");
                Element extArr = doc.createElement("array");
                Element extn1 = doc.createElement("string");
                extn1.setTextContent("ptm");
                Element extn2 = doc.createElement("string");
                extn2.setTextContent("rti");
                Element extn3 = doc.createElement("string");
                extn3.setTextContent("hsh");
                Element extn4 = doc.createElement("string");
                extn4.setTextContent("mview");
                
                extArr.appendChild(extn1);
                extArr.appendChild(extn2);
                extArr.appendChild(extn3);
                extArr.appendChild(extn4);
                tagDict.appendChild(keyExt);
                tagDict.appendChild(extArr);
                
                utADict.appendChild(utType);
                utADict.appendChild(typeArray);
                utADict.appendChild(utTypeId);
                utADict.appendChild(utId);
                utADict.appendChild(utTypeTag);
                utADict.appendChild(tagDict);
                utArray.appendChild(utADict);
                
                Element bundleId = doc.createElement("key");
                bundleId.setTextContent("CFBundleIdentifier");              
                Element bundle = doc.createElement("string");
                bundle.setTextContent("isf.ptmrti.viewer");
                dict.insertBefore(bundle,fNode);
                dict.insertBefore(bundleId,bundle)  ;              
                dict.insertBefore(utArray,bundleId) ;
                dict.insertBefore(utetDecKey, utArray);
                dict.insertBefore(dtkArray, utetDecKey);
                dict.insertBefore(docTypeKey, dtkArray);  
  


                
                
                
                //till here*********************************************
                DOMSource source = new DOMSource(plist);
                StreamResult result = new StreamResult(new FileOutputStream(infoplist));
                TransformerFactory tFactory =
                        TransformerFactory.newInstance();

                Transformer transformer;
                try {
                    transformer = tFactory.newTransformer();
                    if (!systemValue.equalsIgnoreCase("")) {
                        transformer.setOutputProperty(
                                OutputKeys.DOCTYPE_SYSTEM, systemValue);
                    }
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.transform(source, result);

                } catch (TransformerConfigurationException ex) {
                    Logger.getLogger(AddFileAssociations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AddFileAssociations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(AddFileAssociations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddFileAssociations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(AddFileAssociations.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void uninstall(UninstallerProxy Uproxy) throws InstallException {
        String appname = Uproxy.substitute("$PRODUCT_NAME$");
        String installDir = Uproxy.substitute("$USER_INSTALL_DIR$");
        File infoplist = new File(installDir + File.separator + appname + ".app" + File.separator + "Contents" + File.separator + "Info.plist");
        if(infoplist.exists())
            infoplist.delete();
    
    }

    @Override
    public String getInstallStatusMessage() {

        return "Adding File Associations";//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUninstallStatusMessage() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "";
    }
}
