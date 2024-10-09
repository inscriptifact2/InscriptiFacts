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
public class ProcessBatch4TextAndPhoto {

    public static void main(String[] args)
    {
        if (args.length < 4 || args.length > 4)
        {
            System.out.println("Incorrect number of parameters. please pass four parameters only:"
                    + "A) file to read text catalouge records from, B) folder to create cataloge data into,"
                    + "C) file to read photo records from and finally "
                    + "D) file to read photo catalouge records from");
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
                HashMap<String, TextObject> textRecords = new HashMap<String, TextObject>();
                int texts = 0;
                for (int r = 2; r < totalRows; r++)
                {
                    String mKey = sheet.getCell(56, r).getContents().trim(); //1 // 53
                    System.out.println(" text record:"+r+"::mKey: "+mKey+" --");
                    TextObject t = new TextObject();
                   // System.out.println("Processing row no:" + r);
                    for (int c = 0; c < totalCols; c++)
                    {

                        colName = sheet.getCell(c, 1).getContents().trim();
                        if (!colName.isEmpty())
                        {
                            //System.out.println(colName + ":: " + pos);
                            if ((colName.replaceAll(" ", "").toLowerCase().startsWith("ispartofcorpus") && colName.toLowerCase().indexOf("category") < 0) && pos >= 29)
                            {
                                ArrayList list = t.getIsPartOfCorpus();
                                list.add(sheet.getCell(c, r).getContents().trim());
                                t.setIsPartOfCorpus(list);
                                continue;
                            }
                            else if ((colName.replaceAll(" ", "").toLowerCase().startsWith("ispartofcorpus") && colName.toLowerCase().replaceAll("-", "").indexOf("subcategory") > 0) && pos >= 29)
                            {
                                ArrayList list = t.getIsPartOfCorpusSubcategory();
                                list.add(sheet.getCell(c, r).getContents().trim());
                                t.setIsPartOfCorpusSubcategory(list);
                                continue;
                            }
                            else if ((colName.replaceAll(" ", "").toLowerCase().startsWith("ispartofcorpus") && colName.toLowerCase().indexOf("category") > 0) && pos >= 29)
                            {
                                ArrayList list = t.getIsPartOfCorpusCategory();
                                list.add(sheet.getCell(c, r).getContents().trim());
                                t.setIsPartOfCorpusCategory(list);
                                continue;
                            }

                            if (lastCol.equalsIgnoreCase(colName))
                            {
                                values.add(sheet.getCell(c, r).getContents().trim());
                            }
                            else
                            {
                                if (values.size() > 0)
                                {
                                    t.setValues(pos++, values);
                                    //System.out.println(colName + ":: " + pos);
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
                                //System.out.println(colName + ":: " + pos);
                            }

                        }
                        else
                        {
                            if (values.size() > 0)
                            {
                                t.setValues(pos++, values);
                                // System.out.println(colName + ":: " + pos);
                            }
                            values = new ArrayList<String>();
                        }
                    }
                    values = new ArrayList<String>();
                    lastCol = "";
                    pos = 0;
                    textRecords.put(mKey, t);
                    texts++;

                }
                System.out.println(" Total :"+texts );
                source.close();

                //process images from list.xls
                HashMap<String, ArrayList<String>> group = new HashMap<String, ArrayList<String>>();
                source = Workbook.getWorkbook(new File(args[2]));
                sheet = source.getSheet(0);
                totalRows = sheet.getRows();

                String fname = "", pin="";
                ArrayList<String> attributes = new ArrayList<String>();
                for (int i = 0; i < totalRows; i++)
                {
                    attributes = new ArrayList<String>();
                    fname = sheet.getCell(0, i).getContents().trim();
                   try{ 
                    pin = fname.substring(0, fname.lastIndexOf("."));
                   }catch(Exception exp123){ 
                    System.out.println("Row: "+i);
                   }
                    attributes.add(fname);
                    attributes.add(sheet.getCell(3, i).getContents().trim());
                    attributes.add(sheet.getCell(1, i).getContents().trim());
                    group.put(pin, attributes);
                }
                source.close();

                //ProcessImagerecords and tag to array list as per the ISFTExtObject
                source = Workbook.getWorkbook(new File(args[3]));
                sheet = source.getSheet(0);
                totalRows = sheet.getRows();

                values = new ArrayList<String>();
                ArrayList<String> fileAttributes = new ArrayList<String>();
                HashMap<String, ArrayList<PhotoObject>> photoRecords = new HashMap<String, ArrayList<PhotoObject>>();
                //SimpleDateFormat mYear = new SimpleDateFormat("MMM-yyyy");
               // SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
                PhotoObject ph = null;
                for (int r = 2; r < totalRows; r++)
                {
                    String mKey = sheet.getCell(34, r).getContents().trim(); //34
                    String textId = sheet.getCell(4, r).getContents().trim();
                 //   System.out.println("working with:" + mKey);
                    fileAttributes = group.get(mKey);
                    if(!group.containsKey(mKey)){
                        System.out.println("mismatch @ row:"+r +"::"+mKey+" FileName:"+sheet.getCell(55, r).getContents().trim());  
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
                                ph.setDateOfPhotograph(s);
                                break;
                            case 24:
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
                    if (fileAttributes != null)
                    {
                        ph.setFileName(fileAttributes.get(0), false);
                        ph.setPath(fileAttributes.get(1));
                        ph.setArchivalFileSize(fileAttributes.get(2));
                    }

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

            } catch (Exception exp)
            {
                exp.printStackTrace();
            }

        }
    }

}
