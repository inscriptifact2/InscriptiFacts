package isf.db;

import isf.dto.*;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.SwingUtilities;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import oracle.sql.*;

// Referenced classes of package isf.db:
//            RetrievePhotoObjects, ConnectionPool
public class RetrieveTextObjects {

    class Extractor
            implements Runnable {

        String ids;
        String secQuery;
        final RetrieveTextObjects this$0;
        boolean retrievePhotos = true;

        public void run()
        {
            /*  try
            {
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(secQuery);
                System.out.println((new StringBuilder()).append("Query:").append(secQuery).toString());
                ResultSet resultset = stmt.executeQuery();
                ResultSetMetaData resultsetmetadata = resultset.getMetaData();
                int i = resultsetmetadata.getColumnCount();
                boolean found = false;
                String td = "";
                String tdd = "";
                Method method = null;
                WritableWorkbook workbook;
                for (; resultset.next(); workbook.close())
                {
                    TextObject text = new TextObject();
                    for (int j = 0; j < i; j++)
                    {
                        String colName = resultsetmetadata.getColumnName(j + 1);
                        Object obj = resultset.getObject(j + 1);
                        Method amethod[] = text.getClass().getMethods();
                        int l = amethod.length;
                        int i1 = 0;
                        do
                        {
                            if (i1 >= l)
                            {
                                break;
                            }
                            Method d = amethod[i1];
                            if (d.getName().startsWith("set") && d.getName().toLowerCase().endsWith(colName.toLowerCase()))
                            {
                                method = d;
                                found = true;
                                break;
                            }
                            found = false;
                            i1++;
                        }
                        while (true);
                        if (!found)
                        {
                            System.out.println((new StringBuilder()).append(" method not found for:").append(colName).toString());
                        }
                        if (obj != null)
                        {
                            if (obj instanceof ARRAY)
                            {
                                ArrayList list = new ArrayList();
                                ARRAY array = (ARRAY) (ARRAY) obj;
                                Datum rows[] = array.getOracleArray();
                                for (int k = 0; k < rows.length; k++)
                                {
                                    if (rows[k] instanceof STRUCT)
                                    {
                                        Datum columns[] = ((STRUCT) rows[k]).getOracleAttributes();
                                        td = columns[3] != null ? columns[3].stringValue() : "";
                                        tdd = columns[5] != null ? columns[5].stringValue() : "";
                                        list.add(new TextDivision(td, tdd));
                                        continue;
                                    }
                                    if (rows[k] != null)
                                    {
                                        list.add(rows[k].stringValue());
                                    }
                                }

                                method.invoke(text, new Object[]
                                {
                                    list
                                });
                            }
                            else
                            {
                                method.invoke(text, new Object[]
                                {
                                    (String) obj
                                });
                            }
                        }
                        else
                        {
                            method.invoke(text, new Object[]
                            {
                                ""
                            });
                        }
                    }

                    String name = text.getISFAssignedTextNo();
                    File dir = new File((new StringBuilder()).append(location).append(File.separator).append(name).toString());
                    if (!dir.exists())
                    {
                        dir.mkdir();
                    }
                    else
                    {
                        System.out.println((new StringBuilder()).append(" Directory Already exists :").append(name).toString());
                    }
                    File record = new File(dir, (new StringBuilder()).append(name).append(".xls").toString());
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    workbook = null;
                    workbook = Workbook.createWorkbook(record, wbSettings);
                    WritableSheet sheet = workbook.createSheet(name, 0);
                    int col = 0;
                    Object c;
                    for (Iterator iterator = ColumnMapping.headers.iterator();
                            iterator.hasNext();
                            sheet.addCell(new Label(col++, 0, c.toString())))
                    {
                        c = iterator.next();
                    }

                    col = 0;
                    ColumnMembers tcm;
                    for (Iterator iterator1 = ColumnMapping.textProps.iterator();
                            iterator1.hasNext();
                            sheet.addCell(new Label(col++, 1, tcm.getColumnDataAsString(text))))
                    {
                        tcm = (ColumnMembers) iterator1.next();
                    }

                    workbook.write();
                }

                resultset.close();
                if (retrievePhotos)
                {
                    RetrievePhotoObjects ptr = new RetrievePhotoObjects(ids, location);
                    ptr.processPhotoRecords();
                }
            } catch (Exception exp)
            {
                exp.printStackTrace();
            } */

            try
            {
                Connection con = ConnectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement(secQuery);
                System.out.println((new StringBuilder()).append("Query:").append(secQuery).toString());
                ResultSet resultset = stmt.executeQuery();
                ResultSetMetaData resultsetmetadata = resultset.getMetaData();
                int i = resultsetmetadata.getColumnCount();
                boolean found = false;
                String td = "";
                String tdd = "";
                Method method = null;
                while (resultset.next())
                {
                    TextObject text = new TextObject();
                    for (int j = 0; j < i; j++)
                    {
                        String colName = resultsetmetadata.getColumnName(j + 1);
                        Object obj = resultset.getObject(j + 1);
                        Method amethod[] = text.getClass().getMethods();
                        int l = amethod.length;
                        int i1 = 0;
                        do
                        {
                            if (i1 >= l)
                            {
                                break;
                            }
                            Method d = amethod[i1];
                            if (d.getName().startsWith("set") && d.getName().toLowerCase().endsWith(colName.toLowerCase()))
                            {
                                method = d;
                                found = true;
                                break;
                            }
                            found = false;
                            i1++;
                        }
                        while (true);
                        if (!found)
                        {
                            System.out.println((new StringBuilder()).append(" method not found for:").append(colName).toString());
                        }
                        if (obj != null)
                        {
                            if (obj instanceof ARRAY)
                            {
                                ArrayList list = new ArrayList();
                                ARRAY array = (ARRAY) (ARRAY) obj;
                                Datum rows[] = array.getOracleArray();
                                for (int k = 0; k < rows.length; k++)
                                {
                                    if (rows[k] instanceof STRUCT)
                                    {
                                        Datum columns[] = ((STRUCT) rows[k]).getOracleAttributes();
                                        td = columns[3] != null ? columns[3].stringValue() : "";
                                        tdd = columns[5] != null ? columns[5].stringValue() : "";
                                        list.add(new TextDivision(td, tdd));
                                        continue;
                                    }
                                    if (rows[k] != null)
                                    {
                                        list.add(rows[k].stringValue());
                                    }
                                }

                                method.invoke(text, new Object[]
                                {
                                    list
                                });
                            }
                            else
                            {
                                method.invoke(text, new Object[]
                                {
                                    (String) obj
                                });
                            }
                        }
                        else
                        {
                            method.invoke(text, new Object[]
                            {
                                ""
                            });
                        }
                    }
                    catalog.add(text);
                }

                resultset.close();

                if (retrievePhotos)
                {

                    WritableWorkbook workbook;
                    for (int p = 0; p < catalog.size(); p++, workbook.close())
                    {
                        TextObject text = catalog.get(p);

                        String name = text.getISFAssignedTextNo();
                        File dir = new File((new StringBuilder()).append(location).append(File.separator).append(name).toString());
                        if (!dir.exists())
                        {
                            dir.mkdir();
                        }
                        else
                        {
                            System.out.println((new StringBuilder()).append(" Directory Already exists :").append(name).toString());
                        }
                        File record = new File(dir, (new StringBuilder()).append(name).append(".xls").toString());
                        WorkbookSettings wbSettings = new WorkbookSettings();
                        wbSettings.setLocale(new Locale("en", "EN"));
                        workbook = null;
                        workbook = Workbook.createWorkbook(record, wbSettings);
                        WritableSheet sheet = workbook.createSheet(name, 0);
                        int col = 0;
                        Object c;
                        for (Iterator iterator = ColumnMapping.headers.iterator();
                                iterator.hasNext();
                                sheet.addCell(new Label(col++, 0, c.toString())))
                        {
                            c = iterator.next();
                        }

                        col = 0;
                        ColumnMembers tcm;
                        for (Iterator iterator1 = ColumnMapping.textProps.iterator();
                                iterator1.hasNext();
                                sheet.addCell(new Label(col++, 1, tcm.getColumnDataAsString(text))))
                        {
                            tcm = (ColumnMembers) iterator1.next();
                        }

                        workbook.write();
                        workbook.close();

                        RetrievePhotoObjects ptr = new RetrievePhotoObjects(ids, location);
                        ptr.processPhotoRecords();

                    }
                }
                isCompleted = true;
            } catch (Exception exp)
            {
                //exp.printStackTrace();
                System.out.println(exp.getMessage());
            }

        }

        public Extractor(String vals, boolean retrievePhotos)
        {
            this.this$0 = RetrieveTextObjects.this;
            ids = "";
            secQuery = "";
            secQuery = query.replaceAll("<<LIST>>", vals);
            ids = vals;
            this.retrievePhotos = retrievePhotos;
        }
    }

    String records[];
    String location;

    int count;
    String query;
    ArrayList<TextObject> catalog = new ArrayList();
    boolean isCompleted = false;

    public RetrieveTextObjects(String list[], String dir, String filtercolumn)
    {
        records = null;
        location = "";
        count = 0;
        query = "Select TEXTDESCRIPTIVETITLE,TEXTDIVISION,MAINTEXTORPUBLCNNO,ALTTEXTTITLE,TEXTDES"
                + "CRIPTION,TEXTORPUBLCNNONOTE,EXCAVATIONDESCRIPTION,MEDIUM,SCRIPTNOTE,PHYSICALOBJE"
                + "CTDESCRIPTION,PHYSICALOBJECTNOTE,KEYWORDSORPHRASES,ISFFINDSITE,ANCIENTSTRUCTURE,"
                + "REGION,GEOGRAPHICALCOVERAGENOTE,ANCIENTCITYORLOCATION,MODERNCITYORLOCATION,MODER"
                + "NCOUNTRY,NAMEDTIMEPERIOD,DATEOFINSCRIPTIONNOTE,RELEVANTTIMELINE,TYPEOFITEMCATELO"
                + "GED,TEXTUNINSCRIBEDARTIFACT,LANGUAGENOTE,ISFASSIGNEDTEXTNO,MUSEUMACCESSIONNO,EXC"
                + "AVATIONNO,ISPARTOFCORPUS,ISPARTOFCORPUSCATEGORY,ISPARTOFCORPUSSUBCATEGORY,RIGHTS"
                + "PHYSICALOBJECT, LOCATIONOFORIGINAL from textobject  where <<FCOLUMN>>  in("
                + "<<LIST>>) ";
        records = list;
        location = dir;
        query = query.replaceAll("<<FCOLUMN>>", filtercolumn);
    }

    public void fetchTextRecords()
    {
        int start = 0;
        int span = 500;
        int count = 0;
        String delim = "";
        StringBuilder element = new StringBuilder();
        do
        {
            if (start + count >= records.length)
            {
                break;
            }
            element.append(delim).append(records[start + count]);
            count++;
            delim = ", ";
            if (count == span)
            {
                start += count;
                Extractor xp = new Extractor(element.toString(), true);
                SwingUtilities.invokeLater(xp);
                delim = "";
                count = 0;
                element = new StringBuilder();
            }
        }
        while (true);
        Extractor xpf = new Extractor(element.toString(), true);
        SwingUtilities.invokeLater(xpf);
    }

    public ArrayList<TextObject> retrieveTextCatalog()
    {
        StringBuilder element = new StringBuilder();
        String delim = "";
        for (int i = 0; i < records.length; i++)
        {
            element.append(delim).append(records[i]);
            delim = ", ";
        }
        Extractor xpf = new Extractor(element.toString(), false);
        SwingUtilities.invokeLater(xpf);
        while(!isCompleted){
            try
            {
                Thread.sleep(10000);
            } catch (InterruptedException ex)
            {
                //Logger.getLogger(RetrieveTextObjects.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
            System.out.println("Status:"+isCompleted);
        }
        return catalog;
    }

}
