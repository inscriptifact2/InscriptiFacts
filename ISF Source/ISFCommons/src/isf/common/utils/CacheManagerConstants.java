package isf.common.utils;

import java.io.File;

public interface CacheManagerConstants {

    public static final String folderName = Initial.getFolderName();
    public static final String cacheFolderName = Initial.getCacheFolderName();
    public static final String logFolderName = Initial.getLogFolderName();
    public static final String downloadFolderName = Initial.getDownloadFolderName();
    public static final int folderSize = 0x6400000;
    public static final String cacheEntriesFile = "CacheEntriesTable.txt";
 
    class Initial {

        public static String getFolderName() {
            String os = System.getProperty("os.name");
            
            String dir = System.getProperty("user.dir");
            if(os!=null && os.toLowerCase().indexOf("windows") > -1)
                dir = System.getProperty("java.io.tmpdir")+File.separator+"Inscriptifact";
            else if(os!=null && os.toLowerCase().indexOf("windows") == -1)
                dir = System.getProperty("user.dir");
            else
                dir = System.getProperty("java.io.tmpdir")+File.separator+"Inscriptifact";
            
            if(dir.endsWith(File.separator))
                dir = dir;
            else
                dir = dir + File.separator;
                
            //String s = dir + "Inscriptifact";
            String base = "";
            File ch = new File(dir);
            try {
                if (ch.exists()) {
                    base = dir;
                } else if (!ch.exists()) {
                    ch.mkdir();
                    base = dir;
                }
            } catch (Exception exp) {
                base = dir;
            } finally {
                return base;
            }
        }

        public static String getCacheFolderName() {
            String s = getFolderName() + File.separator + "cache";
            String base = "";
            File ch = new File(s);
            try {
                if (ch.exists()) {
                    base = s;
                } else if (!ch.exists()) {
                    ch.mkdir();
                    base = s;
                }
            } catch (Exception exp) {
                base = getFolderName();
            } finally {
                return base;
            }

        }

        public static String getLogFolderName() {
            String s = getFolderName() + File.separator + "logs";
            String base = "";
            File ch = new File(s);
            try {
                if (ch.exists()) {
                    base = s;
                } else if (!ch.exists()) {
                    ch.mkdir();
                    base = s;
                }
            } catch (Exception exp) {
                base = getFolderName();
            } finally {
                return base;
            }
        }
        
        public static String getDownloadFolderName(){
                      
            String dir = System.getProperty("user.home")+File.separator+"Desktop";
                        
            if(dir.endsWith(File.separator))
                dir = dir;
            else
                dir = dir + File.separator;
                
            String s = dir + "InscriptiFact_Images";
            String base = "";
            File ch = new File(s);
            try {
                if (ch.exists()) {
                    base = s;
                } else if (!ch.exists()) {
                    ch.mkdir();
                    base = s;
                }
            } catch (Exception exp) {
                base = dir;
            } finally {
                return base;
            }
        }
    }
}
