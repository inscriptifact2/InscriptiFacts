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
import java.util.*;
import jxl.*;
import jxl.write.*;

/**
 *
 * @author kameshnittala
 */
public class ProcessPersepolis {

    public static void main(String[] args)
    {
        if (args.length > 3 || args.length < 3)
        {
            System.out.println(" incorrect arguments: please provide the following A) Mode: DB or EXCEL B) location "
                    + "to pick list of records(xls) from C) location to save files to ");
            System.exit(0);
        }
        else
        {
            Workbook source = null;
            WritableWorkbook target = null;
            Sheet sheet = null;
            // ArrayList<String> photos = new ArrayList<String>();
            HashMap<String, ArrayList> group = new HashMap<String, ArrayList>();

            try
            {
                source = Workbook.getWorkbook(new File(args[1]));
                sheet = source.getSheet(0);
                int totalRows = sheet.getRows();
                ArrayList<PhotoObject> recs = null;
                String fname = "", mtopn;
                String[] values = null;
                PhotoObject po = new PhotoObject();
                for (int i = 0; i < totalRows; i++)
                {
                    fname = sheet.getCell(0, i).getContents().trim();
                    values = fname.split("_");
                    mtopn = values[0] + " " + values[1].replaceAll("-", ".");
                    recs = group.get(mtopn);
                    if (recs == null)
                    {
                        recs = new ArrayList<PhotoObject>();
                    }
                   // System.out.println("fname:"+fname);
                    po = new PhotoObject();
                    po.setFileName(fname, true);
                    po.setArchivalFileSize(sheet.getCell(1, i).getContents().trim());
                    po.setDateOfPhotograph(sheet.getCell(2, i).getContents().trim());
                    po.setPath(sheet.getCell(3, i).getContents().trim());

                    recs.add(po);
                    group.put(mtopn, recs);
                }
                source.close();
                String[] queryParams = new String[group.keySet().size()];
                int i = 0;
                for (Map.Entry<String, ArrayList> entry : group.entrySet())
                {
                    queryParams[i++] = new StringBuilder().append("'").append(entry.getKey()).append("'").toString();
                }
                RetrieveTextObjects rto = new RetrieveTextObjects(queryParams, args[2], "MAINTEXTORPUBLCNNO");
                ArrayList<TextObject> texts = rto.retrieveTextCatalog();
                File dest = new File(args[2]);
                File dir = null;
                //int num = Integer.parseInt(args[3]);
                //String number = ;
                for (TextObject text : texts)
                {
                    String iatn = text.getISFAssignedTextNo();

                    dir = new File(dest, iatn);
                    if (!dir.exists())
                    {
                        dir.mkdir();
                    }
                    File scan = new File(dir, "Scans");
                    if(!scan.exists()) scan.mkdir();
                    File thumbs = new File(dir,"Thumbnails");
                    if(!thumbs.exists()) thumbs.mkdir();
                    File xlsFile = new File(dir, (new StringBuilder()).append(iatn).append(".xls").toString());
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    target = null;
                    target = Workbook.createWorkbook(xlsFile, wbSettings);
                    WritableSheet wsheet = target.createSheet(iatn, 0);
                    int col = 0;
                    Object c;
                    for (Iterator iterator = ColumnMapping.headers.iterator();
                            iterator.hasNext();
                            wsheet.addCell(new Label(col++, 0, c.toString())))
                    {
                        c = iterator.next();
                    }

                    col = 0;
                    mtopn = text.getMaintextorpublcnno().trim();
                    recs = group.get(mtopn);
                    int row = 1;
                    ColumnMembers tcm;
                  
                    for (PhotoObject p : recs)
                    {
                        col=0;
                        p.setISFAssignedTextNumber(iatn);
                        p.setScriptNote(text.getScriptnote());
                        p.setISFFindSite(text.getISFFindSite());
                        p.setLanguageNote(text.getLanguageNote());
                        p.setPhotoMuseumAccessionNoNote(text.getMaintextorpublcnno());
                        p.setPhotoTextOrPublcnNoNote(text.getTextOrPublcnNoNote());
                        p.setiSFDigitalObjectIdentifier(MTOPCNUtil.getNextNum());
                        

                        for (Iterator iterator1 = ColumnMapping.photoProps.iterator();
                                iterator1.hasNext();
                                wsheet.addCell(new Label(col++, row, tcm.getColumnDataAsString(p,true))))
                        {
                            tcm = (ColumnMembers) iterator1.next();
                        }
                        row++;
                         
                    }
                    target.write();
                    target.close();

                }
                
                //System.out.println("last number:"+num);
                MTOPCNUtil.saveNextNum();

            } catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }
    }

}
