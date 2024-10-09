package isf.UTILS;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;

public class ProcessThumbnailsOnly
{

    public ProcessThumbnailsOnly()
    {
    }

    public static void main(String args[])
    {
        File imageDir = new File("C:\\Personal\\isfsource\\ISF2Excel\\BASEDIR\\Thumbnails");
        Path source = null;
        Path target = null;
        String name = "";
        File afile[] = imageDir.listFiles();
        int i = afile.length;
        for(int j = 0; j < i; j++)
        {
            File img = afile[j];
            if(!img.isFile())
            {
                continue;
            }
            name = img.getName().replaceAll(" ", "");
            source = Paths.get(imageDir.getPath(), new String[] {
                img.getName()
            });
            target = Paths.get(imageDir.getPath(), new String[] {
                name
            });
            try
            {
                Files.move(source, target, new CopyOption[0]);
            }
            catch(Exception exp)
            {
                exp.printStackTrace();
            }
        }

    }

    private static void copyFileUsingChannel(File source, File dest)
        throws IOException
    {
        FileChannel sourceChannel;
        FileChannel destChannel;
        sourceChannel = null;
        destChannel = null;
        sourceChannel = (new FileInputStream(source)).getChannel();
        destChannel = (new FileOutputStream(dest)).getChannel();
        destChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
        sourceChannel.close();
        destChannel.close();

        sourceChannel.close();
        destChannel.close();
        
    }
}
