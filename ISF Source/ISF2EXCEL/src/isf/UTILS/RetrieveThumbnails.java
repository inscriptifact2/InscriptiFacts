package isf.UTILS;

import isf.db.ConnectionPool;
import java.io.*;
import java.sql.*;

public class RetrieveThumbnails
{

    public RetrieveThumbnails()
    {
    }

    public static void main(String args[])
    {
        try
        {
            Connection con = ConnectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement("Select photographidentificationno, imagethumbnail from photoobject where imageth" +
"umbnail is not null and dbms_lob.getLength(imagethumbnail) > 1"
);
            File imageDir = new File("C:\\Personal\\isfsource\\ISF2Excel\\BASEDIR\\Thumbnails");
            ResultSet resultset = stmt.executeQuery();
            Blob img = null;
            FileOutputStream fos = null;
            File image = null;
            int count = 1;
            int skipped = 1;
            byte array[] = null;
            while(resultset.next()) 
            {
                String name = resultset.getString(1);
                image = new File(imageDir, (new StringBuilder()).append(name).append(".jpg").toString());
                if(!image.exists())
                {
                    fos = new FileOutputStream(image);
                    img = resultset.getBlob(2);
                    array = img.getBytes(1L, (int)img.length());
                    fos.write(array);
                    fos.close();
                    System.out.println((new StringBuilder()).append("downloading:").append(name).append(":").append(count++).toString());
                } else
                {
                    System.out.println((new StringBuilder()).append("Skipping:").append(name).append(":").append(skipped++).toString());
                }
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }
}
