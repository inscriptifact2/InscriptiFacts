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
public class ProcessBatch2PhotoList {

    public static void main(String[] args)
    {
        if (args.length < 3 || args.length > 3)
        {
            System.out.println("Incorrect number of parameters. please pass three parameters only:"
                    + "A) folder to create cataloge data into,"
                    + "B) file to read photo records from and finally "
                    + "C) file to read photo catalouge records from");
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
            String base = args[0];
            try
            {
                //process images from list.xls
                HashMap<String, ArrayList<String>> group = new HashMap<String, ArrayList<String>>();
                source = Workbook.getWorkbook(new File(args[1]));
                sheet = source.getSheet(0);

                int totalRows = sheet.getRows();
                int totalCols = sheet.getColumns();

                String fname = "", pin = "";
                ArrayList<String> attributes = new ArrayList<String>();
                for (int i = 0; i < totalRows; i++)
                {
                    attributes = new ArrayList<String>();
                    fname = sheet.getCell(0, i).getContents().trim();
                    pin = fname.substring(0, fname.lastIndexOf("."));
                    //     System.out.println("FileName:"+pin);
                    attributes.add(fname);
                    attributes.add(sheet.getCell(3, i).getContents().trim());
                    attributes.add(sheet.getCell(1, i).getContents().trim());
                    group.put(pin, attributes);
                }
                attributes = null;
                source.close();

                source = Workbook.getWorkbook(new File(args[2]));
                sheet = source.getSheet(0);
                totalRows = sheet.getRows();
                String colName = "", lastCol = "", iatn = "", mKey = "";
                int pos = 0;
                File text = null;
                File textXls = null;
                ColumnMembers tcm;
                ArrayList<String> values = new ArrayList<String>();
                HashMap<String, ArrayList<PhotoObject>> photoRecords = new HashMap<String, ArrayList<PhotoObject>>();
                // int texts = 0;
                PhotoObject ph = null;
                for (int r = 2; r < totalRows; r++)
                {
                    iatn = sheet.getCell(4, r).getContents().trim(); //56
                    mKey = sheet.getCell(34, r).getContents().trim(); //34
                    //  System.out.println(" photo record:"+r+"::mKey: "+iatn+" --");
                    attributes = group.get(mKey);
                    if (!group.containsKey(mKey))
                    {
                        System.out.println("mismatch @ row:" + r + "::" + mKey + " FileName:" + sheet.getCell(55, r).getContents().trim());
                    }
                    if(sheet.getCell(55, r).getContents().trim().contains("mview"))
                     System.out.println(sheet.getCell(55, r).getContents().trim());;

                    text = new File(base, iatn);
                    System.out.println("creating file:"+iatn);
                    if (!text.exists())
                    {
                        
                        text.mkdir();
                        new File(text, "Scans").mkdir();
                        new File(text, "Thumbnails").mkdir();
                        

                    }
                    
                    ph = new PhotoObject();

                    String s = "";
                    for (int c = 0; c < 57; c++)
                    {
                        values = new ArrayList<String>();
                        s = sheet.getCell(c, r).getContents().trim();
                        switch (c)
                        {
                            case 0:
                                ph.setPhotoDescriptiveTitle(s);
                                break;
                            case 1:
                                ph.setDigitalObjectTypeNote(s);
                                break;
                            case 2:
                                ph.setPhotographDescription(s);
                                break;
                            case 3:
                                values.add(s);
                                ph.setAncientTextRepresented(values);
                                break;
                            case 4:
                                ph.setISFAssignedTextNumber(s);
                                break;
                            case 5:
                                ph.setScriptNote(s);
                                break;
                            case 7:
                                ph.setISFFindSite(s);
                                break;
                            case 11:
                                values.add(s);
                                values.add(sheet.getCell(c + 1, r).getContents().trim());
                                values.add(sheet.getCell(c + 2, r).getContents().trim());
                                values.add(sheet.getCell(c + 3, r).getContents().trim());
                                values.add(sheet.getCell(c + 4, r).getContents().trim());
                                c = 15;
                                ph.setPhotographer(values);
                                break;
                            case 16:
                                ph.setDigitizationEquipmentSpecs(s);
                                break;
                            case 17:
                                values.add(s);
                                values.add(sheet.getCell(c + 1, r).getContents().trim());
                                values.add(sheet.getCell(c + 2, r).getContents().trim());
                                values.add(sheet.getCell(c + 3, r).getContents().trim());
                                values.add(sheet.getCell(c + 4, r).getContents().trim());
                                c = 21;
                                ph.setColaborator(values);
                                break;
                            case 23:
                                // System.out.println("DOP:"+s);
                                ph.setDateOfPhotograph(s);
                                break;
                            case 24:
                                // System.out.println("DODC:"+s);
                                ph.setDateOfDigitalConversion(s);
                                break;
                            case 25:
                                ph.setTypeOfItemCateloged(s);
                                break;
                            case 26:
                                ph.setArchivalFileResolution(s);
                                break;
                            case 28:
                                ph.setDigitalObjectFormat(s);
                                break;
                            case 29:
                                ph.setFilmTypeCode(s);
                                break;
                            case 30:
                                ph.setMagnificationCode(s);
                                break;
                            case 31:
                                ph.setPhotoDimensions(s);
                                break;
                            case 32:
                                ph.setLanguageNote(s);
                                break;
                            case 33:
                                ph.setiSFDigitalObjectIdentifier(s);
                                break;
                            case 34:
                                ph.setPhotographIdentificationNo(s);
                                break;
                            case 35:
                                ph.setPhotoMuseumAccessionNoNote(s);
                                break;
                            case 36:
                                values.add(s);
                                ph.setPhotoTextOrPublcnNoNote(values);
                                break;
                            case 39:
                                ph.setIsPartOfWSRProject(s);
                                break;
                            case 40:
                                values.add(s);
                                values.add(sheet.getCell(c + 1, r).getContents().trim());
                                ph.setTextDivision(values);
                                c = 41;
                                break;
                            case 42:
                                ph.setRightsDigitalObject(s);
                                break;
                            case 43:
                                ph.setRightsPhotograph(s);
                                break;
                            case 44:
                                values.add(s);
                                values.add(sheet.getCell(c + 1, r).getContents().trim());
                                values.add(sheet.getCell(c + 2, r).getContents().trim());
                                values.add(sheet.getCell(c + 3, r).getContents().trim());
                                values.add(sheet.getCell(c + 4, r).getContents().trim());
                                c = 48;
                                ph.setRightsPhysicalObject(values);
                                break;
                            case 49:
                                ph.setRightsPublicationPermission(s);
                                break;
                            default:
                                break;
                        }
                    }
                    if (attributes != null)
                    {
                        ph.setFileName(attributes.get(0), false);
                        ph.setPath(attributes.get(1));
                        ph.setArchivalFileSize(attributes.get(2));
                    }

                    ArrayList<PhotoObject> list = photoRecords.get(iatn);
                    if (list == null)
                    {
                        list = new ArrayList<PhotoObject>();
                    }
                    list.add(ph);
                    photoRecords.put(iatn, list);

                }

                source.close();
                
                
                for( Iterator txtItr = photoRecords.keySet().iterator(); txtItr.hasNext(); ){
                    iatn = txtItr.next()+"";
                
                   text = new File(base,iatn) ;
                
                
                textXls = new File(text, iatn + ".xls");
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                target = null;
                target = Workbook.createWorkbook(textXls, wbSettings);
                WritableSheet sheet1 = target.createSheet(iatn, 0);
                int col = 0;
                Object c;
                for (Iterator iterator = ColumnMapping.headers.iterator();
                        iterator.hasNext();
                        sheet1.addCell(new Label(col++, 0, c.toString())))
                {
                    c = iterator.next();
                }

                col = 0;

                col = 0;
                int row = 1;
                ArrayList<PhotoObject> recs = photoRecords.get(iatn);

                for (PhotoObject p : recs)
                {
                    col = 0;
                    for (Iterator iterator1 = ColumnMapping.photoProps.iterator();
                            iterator1.hasNext();
                            sheet1.addCell(new Label(col++, row, tcm.getColumnDataAsString(p, true))))
                    {
                        tcm = (ColumnMembers) iterator1.next();
                    }
                    row++;

                }

                target.write();
                target.close();
                }

            

            /*               //ProcessImagerecords and tag to array list as per the ISFTExtObject
                source = Workbook.getWorkbook(new File(args[3]));
                sheet = source.getSheet(0);
                totalRows = sheet.getRows();

                values = new ArrayList<String>();
                ArrayList<String> fileAttributes = new ArrayList<String>();
          //      HashMap<String, ArrayList<PhotoObject>> photoRecords = new HashMap<String, ArrayList<PhotoObject>>();
                //SimpleDateFormat mYear = new SimpleDateFormat("MMM-yyyy");
               // SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
                PhotoObject ph = null;
                for (int r = 2; r < totalRows; r++)
                {

                    String textId = sheet.getCell(4, r).getContents().trim();
                 //   System.out.println("working with:" + mKey);
 

                    ArrayList<PhotoObject> list = photoRecords.get(textId);
                    if (list == null)
                    {
                        list = new ArrayList<PhotoObject>();
                    }
                    list.add(ph);
                    photoRecords.put(textId, list);
                }
                source.close();
                System.out.println("DONE HERE");

                String location = args[1];
                System.out.println("Total Text Records:"+textRecords.keySet().size());
                for (Map.Entry<String, TextObject> entry
                        : textRecords.entrySet())
                {
                    String mt = entry.getKey();
                    TextObject text = entry.getValue();

                    String name = text.getISFAssignedTextNo();
                    System.out.println("processing " + name);
                    File dir = new File((new StringBuilder()).append(location).append(File.separator).append(name).toString());
                    if (!dir.exists())
                    {
                        dir.mkdir();
                    }
                    File scan = new File(dir, "Scans");
                    if (!scan.exists())
                    {
                        scan.mkdir();
                    }
                    File thumbs = new File(dir, "Thumbnails");
                    if (!thumbs.exists())
                    {
                        thumbs.mkdir();
                    }
                    File record = new File(dir, (new StringBuilder()).append(name).append(".xls").toString());
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    target = null;
                    target = Workbook.createWorkbook(record, wbSettings);
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
                    int row = 2;
                    ArrayList<PhotoObject> recs = photoRecords.get(name);

                    for (PhotoObject p : recs)
                    {
                        col = 0;
                        for (Iterator iterator1 = ColumnMapping.photoProps.iterator();
                                iterator1.hasNext();
                                sheet1.addCell(new Label(col++, row, tcm.getColumnDataAsString(p, true))))
                        {
                            tcm = (ColumnMembers) iterator1.next();
                        }
                        row++;

                    }

                    target.write();
                    target.close();

                }
             */
        }catch (Exception exp)
            {
                
                exp.printStackTrace();
            }

    }
}

}
