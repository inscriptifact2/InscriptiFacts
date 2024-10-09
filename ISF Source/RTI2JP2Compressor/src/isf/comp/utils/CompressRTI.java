/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.comp.utils;

import isf.comp.parsers.LazyParser;
import isf.comp.parsers.RTIParser;
import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author raok1
 */
public class CompressRTI {
    
    public static void main(String[] args){
        

                boolean caching = false;
                File destdir = new File(CompressValues.backupFolder);
                boolean isPTM = false;
                String ext = "";

 
                    boolean found = false;
                   // while (true) {
                    try{
                        if (!caching) {
                            caching = true;
                            File f = new File(CompressValues.ptmFolder);
                            File[] files = f.listFiles();
                            if (files != null) {
                                String name = "", path = "";
                                for (int i = 0; i < files.length; i++) {
                                    name = files[i].getName();
                                    
                                    if(files[i].isDirectory())
                                        continue;
                                    
                                    ext = "";
                                    isPTM = false;
                                    if (name.endsWith(".ptm")) {
                                        ext = ".ptm";
                                        isPTM = true;
                                    } else if (name.endsWith(".rti")) {
                                        ext = ".rti";
                                    } else if (name.endsWith(".hsh")) {
                                        ext = ".hsh";
                                    }
                                    //      if (name.endsWith(".ptm")) {
                                   // name = name.replaceAll(ext, "");
                                    name = name.substring(0,name.lastIndexOf(ext));
                                    path = CompressValues.cacheDirectory + File.separator + name;
                                   // if (!CacheHandler.getInstance().getCache().isCached(path)) {
                                      //  log.debug("caching : " + name);
                                        found = true;
                                        if (isPTM) {
                                            LazyParser.getPTMParser(new FileInputStream(files[i]), name, files[i].length()).cachePTM();
                                        } else {
                                            RTIParser parser = new RTIParser(new FileInputStream(files[i]), name, files[i].length());
                                            parser.cachePTM();
                                        }
                                      //  CacheHandler.getInstance().getCache().updateEntry(path);
                                        File target = new File(f, name + ext);
                                        File dest = new File(destdir, target.getName());
                                        if (target.exists()) {
                                            if(dest.exists())
                                                dest.delete();
                                           boolean success = target.renameTo(new File(destdir, target.getName()));
                                           if (!success) {
                                                System.out.println(target.getName() + " could not be moved");
                                            }
                                            //  --i;
                                        }

                                  



                                }
                            }
                            if (found) {
                            //    log.debug("Cached all Files");
                              //  serialize();
                                caching = false;
                                found = false;
                            }
                        }
                        
                    }catch(Exception exp){
                        exp.printStackTrace();
                    }




        
    }
    
}
