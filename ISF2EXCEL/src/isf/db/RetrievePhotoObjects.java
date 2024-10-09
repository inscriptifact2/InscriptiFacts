package isf.db;

import isf.dto.*;
import java.io.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import oracle.sql.*;

// Referenced classes of package isf.db:
//            ConnectionPool

public class RetrievePhotoObjects
{

    private String textIDs;
    String base;
    private String query;

    public RetrievePhotoObjects(String isfassignedtextnos, String dir)
    {
        base = "";
        query = "select text.column_value as ISFASSIGNEDTEXTNO, photodescriptivetitle, digitalobj" +
"ecttypenote, photographdescription, ancienttextrepresented, scriptnote, isffinds" +
"ite,photographer, digitizationequipmentspecs,colaborator,dateofphotograph,dateof" +
"digitalconversion,typeofitemcateloged,archivalfileresolution,archivalfilesize,di" +
"gitalobjectformat,filmtypecode,magnificationcode,photodimensions,languagenote,is" +
"fdigitalobjectidentifier,photographidentificationno,photomuseumaccessionnonote,p" +
"hototextorpublcnnonote, ispartofwsrproject,textdivision, rightsdigitalobject,rig" +
"htsphotograph,rightsphysicalobject,rightspublicationpermission from photoobject," +
" table(isfassignedtextno) text  where text.column_value in(<<ID>>) order by text" +
".column_value"
;
        textIDs = isfassignedtextnos;
        query = query.replaceFirst("<<ID>>", textIDs);
        base = dir;
    }

    public void processPhotoRecords()
    {
        try
        {
            Connection con = ConnectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet resultset = stmt.executeQuery(query);
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            int i = resultsetmetadata.getColumnCount();
            boolean found = false;
            String textNo = "";
            String lastTextNo = "";
            Method method = null;
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = null;
            Workbook wb = null;
            WritableSheet sheet = null;
            ArrayList pCols = ColumnMapping.photoProps;
            int row = 2;
            do
            {
                if(!resultset.next())
                {
                    break;
                }
                textNo = (String)resultset.getObject(1);
                if(!textNo.equalsIgnoreCase(lastTextNo))
                {
                    row = 2;
                    lastTextNo = textNo;
                } else
                {
                    row++;
                }
                try
                {
                    File xlsFile = new File((new StringBuilder()).append(base).append(File.separator).append(textNo).append(File.separator).append(textNo).append(".xls").toString());
                    wb = Workbook.getWorkbook(xlsFile);
                    workbook = Workbook.createWorkbook(xlsFile, wb);
                    sheet = workbook.getSheet(0);
                }
                catch(FileNotFoundException fnfe)
                {
                    System.out.println((new StringBuilder()).append(textNo).append(" :: records retrieved but text not retrieved:").toString());
                    System.out.println(textIDs.indexOf((new StringBuilder()).append("'").append(textNo).append("'").toString()) > 0);
                    continue;
                }
                PhotoObject photo = new PhotoObject(textNo);
                for(int j = 1; j < i; j++)
                {
                    String colName = resultsetmetadata.getColumnName(j + 1);
                    Object obj = resultset.getObject(j + 1);
                    Method amethod[] = photo.getClass().getMethods();
                    int l = amethod.length;
                    int i1 = 0;
                    do
                    {
                        if(i1 >= l)
                        {
                            break;
                        }
                        Method d = amethod[i1];
                        if(d.getName().startsWith("set") && d.getName().toLowerCase().endsWith(colName.toLowerCase()))
                        {
                            method = d;
                            found = true;
                            break;
                        }
                        found = false;
                        i1++;
                    } while(true);
                    if(!found)
                    {
                        System.out.println((new StringBuilder()).append(" method not found for:").append(colName).toString());
                    }
                    if(obj != null)
                    {
                        if(obj instanceof ARRAY)
                        {
                            ArrayList list = new ArrayList();
                            ARRAY array = (ARRAY)(ARRAY)obj;
                            Datum rows[] = array.getOracleArray();
                            for(int k = 0; k < rows.length; k++)
                            {
                                if(rows[k] instanceof STRUCT)
                                {
                                    Datum columns[] = ((STRUCT)rows[k]).getOracleAttributes();
                                    list.add(new TextDivision(columns[3].stringValue(), columns[5].stringValue()));
                                    continue;
                                }
                                if(rows[k] != null)
                                {
                                    list.add(rows[k].stringValue());
                                }
                            }

                            method.invoke(photo, new Object[] {
                                list
                            });
                        } else
                        {
                            method.invoke(photo, new Object[] {
                                (String)obj
                            });
                        }
                    } else
                    {
                        method.invoke(photo, new Object[] {
                            ""
                        });
                    }
                }

                int col = 0;
                ColumnMembers tcm;
                for(Iterator iterator = pCols.iterator(); iterator.hasNext(); sheet.addCell(new Label(col++, row, tcm.getColumnDataAsString(photo))))
                {
                    tcm = (ColumnMembers)iterator.next();
                }

                workbook.write();
                wb.close();
                workbook.close();
            } while(true);
            resultset.close();
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
}
