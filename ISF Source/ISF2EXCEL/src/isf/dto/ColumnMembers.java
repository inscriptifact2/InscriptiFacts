package isf.dto;

import isf.UTILS.ExcelConstants;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package isf.dto:
//            ColumnProperties, TextDivision, TextObject, PhotoObject

public class ColumnMembers
{

    private ArrayList properties;
    private boolean isSequential;
    private boolean isIndexed;
    private boolean isBlank;

    public ColumnMembers(boolean isS, boolean isIn, ArrayList list)
    {
        isSequential = false;
        isIndexed = false;
        isBlank = false;
        isSequential = isS;
        isIndexed = isIn;
        properties = list;
    }

    public ColumnMembers()
    {
        isSequential = false;
        isIndexed = false;
        isBlank = false;
        isBlank = true;
    }

    public ArrayList getProperties()
    {
        return properties;
    }

    public void setProperties(ArrayList properties)
    {
        this.properties = properties;
    }

    public boolean isIsSequential()
    {
        return isSequential;
    }

    public void setIsSequential(boolean isSequential)
    {
        this.isSequential = isSequential;
    }

    public boolean isIsIndexed()
    {
        return isIndexed;
    }

    public void setIsIndexed(boolean isIndexed)
    {
        this.isIndexed = isIndexed;
    }

    public String getColumnDataAsString(TextObject text)
    {
        String retVal = "";
        StringBuilder sb = new StringBuilder();
        if(!isBlank && !isSequential)
        {
            String lable = "";
            String value = "";
            Iterator iterator = properties.iterator();
label0:
            do
            {
                if(!iterator.hasNext())
                {
                    break;
                }
                ColumnProperties p = (ColumnProperties)iterator.next();
                lable = p.getLable();
                value = p.getTargetMethod();
                if(value.isEmpty())
                {
                    if(lable.equalsIgnoreCase("directory"))
                    {
                        sb.append(text.getISFAssignedTextNo());
                    }
                    continue;
                }
                if(value.equalsIgnoreCase("literal"))
                {
                    sb.append(lable);
                    continue;
                }
                Method rm = null;
                Method amethod[] = text.getClass().getMethods();
                int i = amethod.length;
                int j = 0;
                do
                {
                    if(j >= i)
                    {
                        break;
                    }
                    Method d = amethod[j];
                    if(d.getName().equalsIgnoreCase(value))
                    {
                        rm = d;
                        break;
                    }
                    j++;
                } while(true);
                try
                {
                    Object obj = rm.invoke(text, new Object[0]);
                    if(obj == null)
                    {
                        continue;
                    }
                    if(obj instanceof String)
                    {
                        String colval = ((String)obj).trim();
                        if(!colval.isEmpty())
                        {
                            sb.append(lable).append(" ").append(colval).append(" \u2767 ");
                        }
                        continue;
                    }
                    if(!(obj instanceof ArrayList))
                    {
                        continue;
                    }
                    ArrayList list;
                    if(lable.equalsIgnoreCase("TEXTDIVISION"))
                    {
                        list = (ArrayList)(ArrayList)obj;
                        Iterator iterator1 = list.iterator();
                        do
                        {
                            String td;
                            String tdd;
                            do
                            {
                                if(!iterator1.hasNext())
                                {
                                    continue label0;
                                }
                                TextDivision row = (TextDivision)iterator1.next();
                                td = row.getTextDivision().trim();
                                tdd = row.getTextDivisionDescription().trim();
                                if(!td.isEmpty())
                                {
                                    sb.append("[Text Division:]").append(" ").append(td).append(" \u2767 ");
                                }
                            } while(tdd.isEmpty());
                            sb.append("[Text Division Description:]").append(" ").append(td).append(" \u2767 ");
                        } while(true);
                    }
                    list = (ArrayList)(ArrayList)obj;
                    int count = 1;
                    Iterator iterator2 = list.iterator();
                    do
                    {
                        String s;
                        do
                        {
                            if(!iterator2.hasNext())
                            {
                                continue label0;
                            }
                            s = (String)iterator2.next();
                            s = s.trim();
                        } while(s.isEmpty());
                        if(isIndexed)
                        {
                            sb.append(lable.replaceAll("<N>", (new StringBuilder()).append(count++).append("").toString())).append(" ").append(s).append(" \u2767 ");
                        } else
                        {
                            sb.append(lable).append(" ").append(s).append(" \u2767 ");
                        }
                    } while(true);
                }
                catch(Exception exp)
                {
                    System.out.println((new StringBuilder()).append("name:").append(value).toString());
                    exp.printStackTrace();
                }
            } while(true);
        } else
        if(isSequential)
        {
            ArrayList cLab = new ArrayList();
            ArrayList arrays = new ArrayList();
            for(int count = 0; count < properties.size(); count++)
            {
                ColumnProperties main = (ColumnProperties)properties.get(count);
                cLab.add(main.getLable());
                String refMethod = main.getTargetMethod();
                Method rm = null;
                Method amethod1[] = text.getClass().getMethods();
                int k = amethod1.length;
                int l = 0;
                do
                {
                    if(l >= k)
                    {
                        break;
                    }
                    Method d = amethod1[l];
                    if(d.getName().equalsIgnoreCase(refMethod))
                    {
                        rm = d;
                        break;
                    }
                    l++;
                } while(true);
                try
                {
                    Object obj = rm.invoke(text, new Object[0]);
                    arrays.add((ArrayList)obj);
                }
                catch(Exception exp1)
                {
                    exp1.printStackTrace();
                }
            }

            ArrayList p = (ArrayList)arrays.get(0);
            int size = p.size();
            for(int ct = 0; ct < size; ct++)
            {
                for(int pos = 0; pos < properties.size(); pos++)
                {
                    p = (ArrayList)arrays.get(pos);
                    if(p.size() <= ct)
                    {
                        continue;
                    }
                    String lab = (String)cLab.get(pos);
                    String val = (String)p.get(ct);
                    val = val.trim();
                    if(val.isEmpty())
                    {
                        continue;
                    }
                    if(isIndexed)
                    {
                        sb.append(lab.replaceAll("<N>", (new StringBuilder()).append(ct + 1).append("").toString())).append(" ").append(val).append(" \u2767 ");
                    } else
                    {
                        sb.append(lab).append(" ").append(val).append(" \u2767 ");
                    }
                }

            }

        }
        retVal = sb.toString();
        if(retVal.endsWith("\u2767 "))
        {
            retVal = retVal.substring(0, retVal.lastIndexOf("\u2767 "));
        }
        return retVal;
    }

    public String getColumnDataAsString(PhotoObject photo)
    {
        return getColumnDataAsString(photo, false);
    }

    public String getColumnDataAsString(PhotoObject photo, boolean useFileName)
    {
        String retVal = "";
        StringBuilder sb = new StringBuilder();
        String lable = "";
        String value = "";
        if(properties != null)
        {
            Iterator iterator = properties.iterator();
label0:
            do
            {
                if(!iterator.hasNext())
                {
                    break;
                }
                ColumnProperties p = (ColumnProperties)iterator.next();
                lable = p.getLable();
                value = p.getTargetMethod();
                if(value.isEmpty())
                {
                    if(lable.equalsIgnoreCase("filename"))
                    {
                        if(!useFileName)
                        {
                            sb.append(photo.getPhotographIdentificationNo());
                        } else
                        {
                            sb.append(photo.getFileName());
                        }
                    }
                    continue;
                }
                Method rm = null;
                Method amethod[] = photo.getClass().getMethods();
                int i = amethod.length;
                int j = 0;
                do
                {
                    if(j >= i)
                    {
                        break;
                    }
                    Method d = amethod[j];
                    if(d.getName().equalsIgnoreCase(value))
                    {
                        rm = d;
                        break;
                    }
                    j++;
                } while(true);
                try
                {
                    Object obj = rm.invoke(photo, new Object[0]);
                    if(obj == null)
                    {
                        continue;
                    }
                    if(obj instanceof String)
                    {
                        String colval = ((String)obj).trim();
                        if(!colval.isEmpty())
                        {
                            sb.append(lable).append(" ").append(colval).append(" \u2767 ");
                        }
                        continue;
                    }
                    if(!(obj instanceof ArrayList))
                    {
                        continue;
                    }
                    ArrayList list = (ArrayList)(ArrayList)obj;
                    int count = 1;
                    Iterator iterator1 = list.iterator();
                    do
                    {
                        String s;
                        do
                        {
                            if(!iterator1.hasNext())
                            {
                                continue label0;
                            }
                            s = (String)iterator1.next();
                            s = s.trim();
                        } while(s.isEmpty());
                        if(isIndexed)
                        {
                            sb.append(lable.replaceAll("<N>", (new StringBuilder()).append(count++).append("").toString())).append(" ").append(s).append(" \u2767 ");
                        } else
                        {
                            sb.append(lable).append(" ").append(s).append(" \u2767 ");
                        }
                    } while(true);
                }
                catch(Exception exp)
                {
                    System.out.println((new StringBuilder()).append("name:").append(value).toString());
                    exp.printStackTrace();
                }
            } while(true);
        }
        retVal = sb.toString();
        if(retVal.endsWith("\u2767 "))
        {
            retVal = retVal.substring(0, retVal.lastIndexOf("\u2767 "));
        }
        return retVal;
    }
}
