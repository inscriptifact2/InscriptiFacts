package isf.UTILS;

import isf.dto.*;
import java.io.*;

public class MTOPCNUtil {

    private static int number = 0;

    public static PhotoObject generateFromFile(String fileName)
    {
        PhotoObject po = new PhotoObject();
        po.setFileName(fileName);
        String fname = fileName.substring(0, fileName.indexOf("."));
        return po;
    }

    public static void main(String args[])
    {
        File dir = new File("/Users/kameshnittala/Public/ISFSource/ISF2EXCEL/BaseDir/Batch2_PersepolisA/Fort_0000-102_BE1/Tiff_16");
        StringBuilder sb = new StringBuilder();
        String mtopn = "";
        String otherDetails = "";
        File afile[] = dir.listFiles();
        int i = afile.length;
        for (int j = 0; j < i; j++)
        {
            File f = afile[j];
            String fname = f.getName();
            sb.append("File Name:").append(fname);
            if (fname.endsWith(".ptm") || fname.endsWith(".rti") || fname.endsWith(".hsh"))
            {
                mtopn = fname.replaceAll(".ptm", "").replaceAll(".hsh", "").replaceAll(".rti", "");
                otherDetails = "";
            }
            else
            {
                mtopn = fname.substring(0, fname.lastIndexOf("_"));
                otherDetails = fname.substring(fname.lastIndexOf("_"));
            }
            sb.append("\n\tMainTextPublnNo=").append(mtopn);
            if (otherDetails.length() > 1)
            {
                String x[] = otherDetails.split("-#");
                sb.append("\n\tLocation:").append(getLocation(x[0].substring(1, 3)));
                sb.append("\n\tOrder:").append(x[0].substring(3));
                sb.append("\n\tSequence:").append(x[1].replaceAll(".tif", "")).append("\n\n");
            }
            else
            {
                sb.append("\n\n");
            }
        }

        System.out.println(sb.toString());
    }

    private static String getLocation(String code)
    {
        String retVal = "";
        String s = code;
        byte byte0 = -1;
        switch (s.hashCode())
        {
            case 2704:
                if (s.equals("UE"))
                {
                    byte0 = 0;
                }
                break;

            case 2115:
                if (s.equals("BE"))
                {
                    byte0 = 1;
                }
                break;

            case 2239:
                if (s.equals("FE"))
                {
                    byte0 = 2;
                }
                break;

            case 2425:
                if (s.equals("LE"))
                {
                    byte0 = 3;
                }
                break;

            case 2611:
                if (s.equals("RE"))
                {
                    byte0 = 4;
                }
                break;

            case 2515:
                if (s.equals("OB"))
                {
                    byte0 = 5;
                }
                break;

            case 2628:
                if (s.equals("RV"))
                {
                    byte0 = 6;
                }
                break;
        }
        switch (byte0)
        {
            case 0: // '\0'
                retVal = "Upper Edge";
                break;

            case 1: // '\001'
                retVal = "Bottom Edge";
                break;

            case 2: // '\002'
                retVal = "Flat Edge";
                break;

            case 3: // '\003'
                retVal = "Left Edge";
                break;

            case 4: // '\004'
                retVal = "Right Edge";
                break;

            case 5: // '\005'
                retVal = "Obverse";
                break;

            case 6: // '\006'
                retVal = "Reverse";
                break;

            default:
                retVal = "Obverse";
                break;
        }
        return retVal;
    }

    public static String getNextNum()
    {
        if (number == 0)
        {
            File f = new File("lastRecord.txt");

            if (f.exists())
            {
                try
                {
                    FileReader fr = new FileReader(f);
                    BufferedReader bfr = new BufferedReader(fr);
                    String line = bfr.readLine();
                    number = Integer.parseInt(line);

                } catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
            else
            {
                number = 250001;
            }
        }
        else
        {
            number++;
        }

        String retString = number > 999999 ? "ISF_DO_" + number : "ISF_DO_0" + number;
        return retString;
    }

    public static void saveNextNum()
    {
        File f = new File("lastRecord.txt");
        number++;
        try
        {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(("" + number).getBytes());
            fos.flush();
            fos.close();
        } catch (Exception exp)
        {
            exp.printStackTrace();
        }

    }
}
