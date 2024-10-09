package isf.common;


public class Debug
{

    public static boolean shallDisplay = false;

    public Debug()
    {
    }

    public static void debug()
    {
        if(shallDisplay)
        {
            debug("\n");
        }
    }

    public static void debug(Object obj)
    {
        if(shallDisplay)
        {
            debug("\n" + obj.toString());
        }
    }

    public static void debug(char ac[])
    {
        if(shallDisplay)
        {
            debug("\n" + ac);
        }
    }

    public static void debug(double d)
    {
        if(shallDisplay)
        {
            debug("\n" + d);
        }
    }

    public static void debug(float f)
    {
        if(shallDisplay)
        {
            debug("\n" + f);
        }
    }

    public static void debug(int i)
    {
        if(shallDisplay)
        {
            debug("\n" + i);
        }
    }

    public static void debug(long l)
    {
        if(shallDisplay)
        {
            debug("\n" + l);
        }
    }

    public static void debug(boolean flag)
    {
        if(shallDisplay)
        {
            debug("\n" + flag);
        }
    }

    public static void debug(char c)
    {
        if(shallDisplay)
        {
            debug("\n" + c);
        }
    }

}
