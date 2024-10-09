/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.work;

import isf.UTILS.*;
import isf.db.*;
import isf.dto.*;
import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author kameshnittala
 */
public class ProcessTextCatalouge {

    public static void main(String[] args)
    {
        if (args.length < 3 || args.length > 3)
        {
            System.out.println("Incorrect number of parameters. please pass four parameters only:"
                    + "A) file to read text catalouge records from, B) folder to create cataloge data into,"
                    + "C) file to read photo records from ");
            System.exit(0);
        }
        else
        {
            //read excel file with text catalouge details. create isftextno wise
            // text catalouge and push into hashmap. retrieve each key and create folder in target directory
            // create excel sheet with first row as headers and second row as text catalogue
            Workbook source = null;
            WritableWorkbook target = null;
            Sheet sheet = null;
            try
            {
                source = Workbook.getWorkbook(new File(args[0]));
                sheet = source.getSheet(0);
                int totalRows = sheet.getRows();
                int totalCols = sheet.getColumns();
                String colName = "", lastCol = "";
                int pos = 0;
                ArrayList<String> values = new ArrayList<String>();
               // ArrayList<TextObject> texts = new ArrayList<TextObject>();
                HashMap<String, TextObject> textRecords = new HashMap<String, TextObject>();
                for (int r = 2; r < totalRows; r++)
                {
                    String mKey = sheet.getCell(1, r).getContents().trim();
                    TextObject t = new TextObject();
                   // System.out.println("Processing row no:" + r +" mt:"+mKey );
                    for (int c = 0; c < totalCols; c++)
                    {

                        colName = sheet.getCell(c, 1).getContents().trim();
                        // System.out.println(" "+colName);
                        if (!colName.isEmpty())
                        {
                            if (lastCol.equalsIgnoreCase(colName))
                            {
                                values.add(sheet.getCell(c, r).getContents().trim());
                            }
                            else
                            {
                                if (values.size() > 0)
                                {
                                    t.setValues(pos++, values);
                                }
                                lastCol = colName;
                                values = new ArrayList<String>();
                                values.add(sheet.getCell(c, r).getContents().trim());
                            }
                            
                            if (colName.equalsIgnoreCase("Text Division"))
                            {
                                values.add(sheet.getCell(c, r).getContents().trim());
                                values.add(sheet.getCell(c + 1, r).getContents().trim());
                                c++;
                                t.setValues(pos++, values);
                                lastCol = colName;
                                values = new ArrayList<String>();
                            }

                        }
                        else
                        {
                            if (values.size() > 0)
                            {
                                t.setValues(pos++, values);
                            }
                            values = new ArrayList<String>();
                        }
                    }
                    values = new ArrayList<String>();
                    lastCol = "";
                  //  texts.add(t);
                    pos = 0;
                    if(t==null){
                      System.out.println("mkey:"+mKey+"-");  
                    }
                    textRecords.put(mKey, t);
                    
                }
                source.close();
                //process images from list.xls
                HashMap<String, ArrayList> group = new HashMap<String, ArrayList>();
                 source = Workbook.getWorkbook(new File(args[2]));
                sheet = source.getSheet(0);
                totalRows = sheet.getRows();
                ArrayList<PhotoObject> recs = null;
                String fname = "", mtopn="";
                String[] vals = null;
                PhotoObject po = new PhotoObject();
                boolean decode = true;
                System.out.println("total rows:"+totalRows);
                for (int i = 0; i < totalRows; i++)
                {
                    fname = sheet.getCell(0, i).getContents().trim();
                   // System.out.println("Processing:"+fname);
                   // using decode method of file name
                   System.out.println("fname:"+fname+ " at "+ i);
                   if(fname.indexOf("_")>0){
                    vals = fname.split("_");
                    
                    mtopn = vals[0] + " " + vals[1].replaceAll("-", ".");
                    decode = true;
                    }
                    else{
                       
                        mtopn = fname.substring(0, fname.indexOf("."));
                        decode = false;
                        //System.out.println("NO DASH-"+mtopn);
                    }
                 //extracting ISFASSIGNEDTEXTNO from FLOCATION
                // String dir = fname.substring(0,fname.lastIndexOf(File.separator));
                   // System.out.println(" extratced dir"+dir);
                  //  String lastDir = dir.substring( dir.lastIndexOf(File.separator)+1);
                    //System.out.println("lastDir:"+ lastDir);
                  //  mtopn = lastDir.split(" ")[0];
                  //  System.out.println("Maintext:"+mtopn);
                    recs = group.get(mtopn);
                    if (recs == null)
                    {
                        recs = new ArrayList<PhotoObject>();
                    }
                    po = new PhotoObject();
                    po.setFileName(fname, true);
                    po.setArchivalFileSize(sheet.getCell(1, i).getContents().trim());
                    po.setDateOfPhotograph(sheet.getCell(2, i).getContents().trim());
                    po.setPath(sheet.getCell(3, i).getContents().trim());

                    recs.add(po);
                    group.put(mtopn, recs);
                }
                source.close();
                
                
                
                String location = args[1];
                for (Map.Entry<String, TextObject> entry : textRecords.entrySet())                   
                {
                    String mt = entry.getKey();
                    if(!mt.isEmpty()){
                  //  System.out.println("mt:"+mt);
                    TextObject text = entry.getValue();
   
                    String name = text.getISFAssignedTextNo();
                   System.out.println("asa:" + name +" :: mt:"+mt);
                    File dir = new File((new StringBuilder()).append(location).append(File.separator).append(name).toString());
                    if (!dir.exists())
                    {
                        dir.mkdir();
                    }
                    File scan = new File(dir, "Scans");
                    if(!scan.exists()) scan.mkdir();
                    File thumbs = new File(dir,"Thumbnails");
                    if(!thumbs.exists()) thumbs.mkdir();
                    File record = new File(dir, (new StringBuilder()).append(name).append(".xls").toString());
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    target = null;
                    target = Workbook.createWorkbook(record, wbSettings);
                   // System.out.println("Name @ " + i + ":"+name);
                    WritableSheet sheet1 = target.createSheet(name, 0);
                    int col = 0;
                    Object c;
                    for (Iterator iterator = ColumnMapping.headers.iterator();
                            iterator.hasNext();
                            sheet1.addCell(new Label(col++, 0, c.toString())))
                    {
                        c = iterator.next();
                    }

                    col = 0;
                    ColumnMembers tcm;
                    for (Iterator iterator1 = ColumnMapping.textProps.iterator();
                            iterator1.hasNext();
                            sheet1.addCell(new Label(col++, 1, tcm.getColumnDataAsString(text))))
                    {
                        tcm = (ColumnMembers) iterator1.next();
                    }
                    
                    
                    col = 0;  
                    recs = group.get(mt);//group.get(name);
                     
                    int row = 2;
                   

                    for (PhotoObject p : recs)
                    {
                         col=0;
                        p.setISFAssignedTextNumber(name);
                        p.setScriptNote(text.getScriptnote());
                        p.setISFFindSite(text.getISFFindSite());
                        p.setLanguageNote(text.getLanguageNote());
                        p.setPhotoMuseumAccessionNoNote(text.getMaintextorpublcnno());
                        p.setPhotoTextOrPublcnNoNote(text.getTextOrPublcnNoNote());
                        p.setiSFDigitalObjectIdentifier(MTOPCNUtil.getNextNum());

                        for (Iterator iterator1 = ColumnMapping.photoProps.iterator();
                                iterator1.hasNext();
                                sheet1.addCell(new Label(col++, row, tcm.getColumnDataAsString(p,true))))
                        {
                            tcm = (ColumnMembers) iterator1.next();
                        }
                        row++;
                        

                    }
                    
                    

                    target.write();
                    target.close();

                }
                }
               // System.out.println("last number: "+num);
               MTOPCNUtil.saveNextNum();
               

            } catch (Exception exp)
            {
                exp.printStackTrace();
            }

        }
    }

}
