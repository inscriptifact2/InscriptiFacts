package com.isf.loader;

import com.isf.utils.ISFConnection;
import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;

public class ImageLoader
{

    String userDir;
    String sourceDir;
    Properties prop;
    File files[];
    int filesLength;
    File f;
    FileOutputStream fos;
    PrintWriter pw;
    String imageType;

    public ImageLoader(String image, File file)
    {
        userDir = System.getProperty("user.dir");
        sourceDir = file.getAbsolutePath();
        sourceDir = sourceDir.replace('\\', '/');
        prop = new Properties();
        imageType = image;
        System.out.println((new StringBuilder()).append("Uploading images from ---- ").append(file).append("\n").toString());
        boolean flag = file.isDirectory();
        if(flag)
        {
            files = file.listFiles();
            filesLength = files.length;
            f = new File((new StringBuilder()).append(userDir).append("/").append("ErrorLogFile_").append(image).append(".txt").toString());
            try
            {
                fos = new FileOutputStream(f);
                pw = new PrintWriter(fos);
                loadFileAndProcess();
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        } else
        {
            System.out.println((new StringBuilder()).append(file).append(" is not a directory").append("\n").toString());
        }
    }

    private void loadFileAndProcess()
    {
        try
        {
            int i;
            prop.load(new FileInputStream((new StringBuilder()).append(imageType).append(".dat").toString()));
            i = Integer.parseInt(prop.getProperty("count"));
            if(i == 0)
            {
                JOptionPane.showMessageDialog(new JFrame(), (new StringBuilder()).append(imageType).append(".dat is empty.").toString(), "Click OK", 1);
                return;
            }
            try
            {
                System.out.println("Started Updating .........\n");
                for(int j = 0; j < i; j++)
                {
                    //int k = j % 20;
                    String photoid = prop.getProperty((new StringBuilder()).append("PIN").append(j).toString());
                    String fileName = prop.getProperty((new StringBuilder()).append(imageType).append(j).toString());
                    process(fileName, photoid);
                    if(j%20 == 0)
                    {
                        System.out.println((new StringBuilder()).append("Finished Updating ").append(j).append(" Records\n").toString());
                    }
                }
                
                System.out.println("Finished Updating\n");
                fos.close();
                pw.close();
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
            return;
        }
        catch(FileNotFoundException ex)
        {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void process(String fileName, String photoid)
    {
        if(fileName != "")
        {
            searchDir(fileName, photoid);
        }
    }

    private void searchDir(String fileName, String photoid)
    {
        File file = new File((new StringBuilder()).append(sourceDir).append("/").append(fileName).toString());
        if(file.exists())
        {
            System.out.println("loading:"+fileName);
            writeToDB(fileName, photoid);
        } else
        {
            System.out.println("FileNotFound"+fileName);
            writeToErrorLog(fileName, photoid);
        }
    }

    private void writeToDB(String fileName, String photoid)
    {
        try
        {
            Connection connection = ISFConnection.getConnection();
            PreparedStatement preparedstatement = connection.prepareStatement((new StringBuilder()).append("update photoobject set ").append(imageType).append(" = empty_blob() where Photographidentificationno='").append(photoid).append("'").toString());
            preparedstatement.executeUpdate();
            preparedstatement.close();
            connection.commit();
            connection.close();
            Connection fillConn = ISFConnection.getConnection();
            Statement statement = fillConn.createStatement();
            String query = (new StringBuilder()).append("select ").append(imageType).append(" from photoobject where Photographidentificationno = '").append(photoid).append("' for update").toString();
            ResultSet resultset = statement.executeQuery(query);
            if(resultset.next())
            {
                BLOB blob = ((OracleResultSet)resultset).getBLOB(1);
                OutputStream outputstream = blob.getBinaryOutputStream();
                FileInputStream fileinputstream = new FileInputStream((new StringBuilder()).append(sourceDir).append("/").append(fileName).toString());
                byte abyte0[] = new byte[10240];
                for(int i = 0; (i = fileinputstream.read(abyte0)) != -1;)
                {
                    outputstream.write(abyte0, 0, i);
                }

                outputstream.flush();
                outputstream.close();
                fileinputstream.close();
            }
            resultset.close();
            statement.close();
            fillConn.close();
            Thread.sleep(1000L);
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    private void writeToErrorLog(String fileName, String id)
    {
        String str = (new StringBuilder()).append(fileName).append(" not found. Hence cannot be uploaded\n").toString();
        pw.println(str);
        pw.flush();
    }

    public static void main(String args[])
    {
        if(args.length < 2)
        {
            System.out.println("Syntax Error: ImageLoader <field> <imagelocation> \n eg. ImageLoader IMAGESID /v" +
"ar/isfimages/SID"
);
            return;
        }
        try
        {
            ImageLoader loader = new ImageLoader(args[0], new File(args[1]));
            System.out.println("Uploading finished");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
