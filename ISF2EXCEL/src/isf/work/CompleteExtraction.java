/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 *
 * @author raok1
 */
public class CompleteExtraction {

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 2) {
            System.out.println(" Please pass the following two arguments: \n"
                    + "A) Folder to read excel sheets from \n"
                    + "B) Folder to have completed records");

            System.exit(0);
        } else {
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            Workbook wb = null;
            String workDir = args[0];
            String compDir = args[1];
            int scansMoved = 0;
            int jpegsMoved =0;
            int hasText = 2;
            if(workDir.contains("Batch2"))
                hasText = 1;
            int checkSum= 0;
            File workingFolder = new File(workDir);
            File completedFolder = new File(compDir);


            //  boolean found = false;
            String photo = "", text = "", thumb = "";
            for (File textDir : workingFolder.listFiles()) {
                if (textDir.isDirectory() && textDir.getName().startsWith("ISF_TXT")) {
                    text = textDir.getName();
                    File target = new File(completedFolder, text);
                    if (!target.exists()) {
                        target.mkdir();
                    }

                    File scanDir = new File(target, "Scans");
                    if (!scanDir.exists()) {
                        scanDir.mkdir();
                    }
                    File thDir = new File(target, "Thumbnails");
                    if (!thDir.exists()) {
                        thDir.mkdir();
                    }

                    //First move Scans and then move Thumbnails
                    copyRecursive(workDir + File.separatorChar + text + File.separatorChar +"Scans", compDir+File.separatorChar + text + File.separatorChar +"Scans");
                    copyRecursive(workDir + File.separatorChar + text + File.separatorChar +"Thumbnails", compDir+File.separatorChar + text + File.separatorChar +"Thumbnails");
                    
                    try {
                        File srcXLS = new File(workDir + File.separatorChar + text + File.separatorChar + text + ".xls");
                        wb = Workbook.getWorkbook(srcXLS);
                        Sheet sheet = wb.getSheet(0);
                        int totalRows = sheet.getRows();
                        checkSum = totalRows-hasText;
                        
                        /// STEP 4 generate txt file with tab delimiter

                      //  int totalCols = sheet.getColumns();
                        StringBuilder sb = new StringBuilder();
                        for (int rows = 0; rows < totalRows; rows++) {
                            for (int cols = 0; cols < 27; cols++) {
                                sb.append(sheet.getCell(cols, rows).getContents().trim()).append("\t");
                            }
                            sb.append("\n");
                        }
                        File f = new File(compDir + File.separatorChar + text, text + ".txt");
                        if(f.exists())
                            f.delete();
                        
                        Path path = Paths.get(compDir + File.separatorChar + text, text + ".txt");
      //Creating a BufferedWriter object
      BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
      //Appending the UTF-8 String to the file
      writer.append(sb.toString());
      //Flushing data to the file
      writer.flush();
                      /*  
                        File f = new File(compDir + File.separatorChar + text, text + ".txt");
                        FileOutputStream fos = new FileOutputStream(f);
                        DataOutputStream outputStream = new DataOutputStream(fos);
                        
                        outputStream.writeUTF(sb.toString());
                       
                        outputStream.flush();
                        outputStream.close();
                        fos.close();*/
                        
                        wb.close();
                    } catch (Exception exp) {
                        exp.printStackTrace();
                    }
                    scansMoved =scanDir.listFiles().length;
                    jpegsMoved = thDir.listFiles().length;
                }
                           if(checkSum !=scansMoved){
            System.out.println(text+"  Scans Moved:"+scansMoved+ ":: expected:"+checkSum);
            //System.out.println("Thumbs Moved:"+jpegsMoved);
            }else{
                System.out.println(text+" processed fully");
            } 

            }

        }
    }
    
    public static void copyRecursive(String src, String target)
    { 
        try{
        Path sourceDir = Paths.get( src);
        Path destinationDir = Paths.get(target);
        if(!(Files.list(sourceDir).findAny().isPresent())){
            System.out.println("NoFilesFound at: "+src);
            return;
        }
            
        // Traverse the file tree and copy each file/directory.
        Files.walk(sourceDir)
                .forEach(sourcePath -> {

                        Path targetPath = destinationDir.resolve(sourceDir.relativize(sourcePath));
                       // System.out.printf("Copying %s to %s%n", sourcePath, targetPath);
            try {
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                //ex.printStackTrace();
                System.out.println(ex.getMessage());
                
            }

                });
        }catch(Exception exp){
            exp.printStackTrace();
        }
    }

}
