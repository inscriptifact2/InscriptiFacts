package isf.work;

import isf.db.RetrieveTextObjects;
import isf.dto.ColumnMapping;
import isf.dto.ColumnMembers;
import isf.dto.PhotoObject;
import java.io.*;
import java.util.*;
import jxl.*;
import jxl.write.*;

public class ISF2Excel
{

    public ISF2Excel()
    {
    }

    public static void main(String args[])
    {
        if(args.length < 3 || args.length > 3)
        {
            System.out.println("wrong no of arguments: please indicate a) mode ISF (for records in ISF)/ Excel (" +
"for records not in ISF), b) file to pick text records, c) directory to save exce" +
"l records, and finally the location of images"
);
        } else
        {
            String mode = args[0];
            String excelList = args[1];
            String baseDir = args[2];
            Workbook source = null;
            Sheet sheet = null;
            int totalRows = 0;
            if(mode.equalsIgnoreCase("ISF"))
            {
                try
                {
                    source = Workbook.getWorkbook(new File(excelList));
                    sheet = source.getSheet(0);
                    System.out.println((new StringBuilder()).append(" firstRecord:").append(sheet.getCell(0, 1).getContents().trim()).toString());
                    totalRows = sheet.getRows();
                    String textRecords[] = new String[totalRows - 1];
                    for(int pos = 1; pos < totalRows; pos++)
                    {
                        textRecords[pos - 1] = (new StringBuilder()).append("'").append(sheet.getCell(0, pos).getContents().trim()).append("'").toString();
                    }

                    source.close();
                    RetrieveTextObjects rto = new RetrieveTextObjects(textRecords, baseDir, "ISFASSIGNEDTEXTNO");
                    rto.fetchTextRecords();
                }
                catch(Exception exp)
                {
                    exp.printStackTrace();
                }
            } else
            {
                HashMap map = new HashMap();
                ArrayList pCols = ColumnMapping.photoProps;
                try
                {
                    File excel = new File(excelList);
                    System.out.println((new StringBuilder()).append("excel :").append(excel.getAbsolutePath()).append(":: excel:").append(excel.length()).toString());
                    source = Workbook.getWorkbook(excel);
                    sheet = source.getSheet(0);
                    totalRows = sheet.getRows();
                    ArrayList values = null;
                    for(int pos = 2; pos < totalRows && !sheet.getCell(0, pos).getContents().trim().equals(""); pos++)
                    {
                        values = new ArrayList();
                        String newText = sheet.getCell(2, pos).getContents().trim();
                        File newDir = new File(baseDir, newText);
                        if(!newDir.exists())
                        {
                            newDir.mkdir();
                        }
                        ArrayList records = (ArrayList)map.get(newText);
                        if(records == null)
                        {
                            records = new ArrayList();
                        }
                        for(int col = 0; col < 57; col++)
                        {
                            values.add(sheet.getCell(col, pos).getContents().trim());
                        }

                        PhotoObject photo = new PhotoObject(values);
                        records.add(photo);
                        map.put(newText, records);
                    }

                    source.close();
                    String text;
                    for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); System.out.println((new StringBuilder()).append("Text:").append(text).append(" completed.").toString()))
                    {
                        text = (String)iterator.next();
                        try
                        {
                            File dir = new File(baseDir, text);
                            File xlsFile = new File((new StringBuilder()).append(dir).append(File.separator).append(text).append(".xls").toString());
                            WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
                            WritableSheet wSheet = workbook.createSheet(text, 0);
                            int columns = 0;
                            int rows = 0;
                            Object c;
                            for(Iterator iterator1 = ColumnMapping.headers.iterator(); iterator1.hasNext(); wSheet.addCell(new Label(columns++, 0, c.toString())))
                            {
                                c = iterator1.next();
                            }

                            ArrayList records = (ArrayList)map.get(text);
                            for(Iterator iterator2 = records.iterator(); iterator2.hasNext();)
                            {
                                PhotoObject photo = (PhotoObject)iterator2.next();
                                columns = 0;
                                rows++;
                                Iterator iterator3 = pCols.iterator();
                                while(iterator3.hasNext()) 
                                {
                                    ColumnMembers tcm = (ColumnMembers)iterator3.next();
                                    wSheet.addCell(new Label(columns++, rows, tcm.getColumnDataAsString(photo, true)));
                                }
                            }

                            workbook.write();
                            workbook.close();
                        }
                        catch(FileNotFoundException fnfe)
                        {
                            fnfe.printStackTrace();
                        }
                    }

                }
                catch(Exception exp)
                {
                    exp.printStackTrace();
                }
            }
        }
    }
}
