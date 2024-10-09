/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.viewer.utils;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author raok1
 */
public class TestEncodeRates {
        private static Hashtable wildcard_encode_rates;
    static {
        wildcard_encode_rates = new Hashtable();
        String wildcards = PropertyLoader.getInstance().getProperty("wild");
        String[] nums = wildcards.split(",");
        String[] vals ;
        for(int i = 0 ; i < nums.length; i++)
        {
            vals = PropertyLoader.getInstance().getProperty("wild"+nums[i]).split(",") ;
            for(int x = 1 ; x<vals.length;x++)
                wildcard_encode_rates.put(vals[x], vals[0]);
        }

        Enumeration keys = wildcard_encode_rates.keys();
        while(keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();

               System.out.println(key+"--"+(String)wildcard_encode_rates.get(key));


        }

        
    }
        public String getEncodeRate(String type, String fileName) {
        String str = PropertyLoader.getInstance().getProperty(fileName.toLowerCase());
        if (str == null || str.equalsIgnoreCase("")) {
          str= getWildCard(fileName);
        //  logger.debug(fileName+":: "+str+"-");
          str = str.equalsIgnoreCase("0")?PropertyLoader.getInstance().getProperty(type):str;
           // str = InscriptWebLoader.getInstance().getProperty(type);
        }
        return str;
    }
    
        private String getWildCard(String fileName)
    {
        Enumeration keys = wildcard_encode_rates.keys();
        String key="";
        String encode="0";
        while(keys.hasMoreElements())
        {
            key = (String)keys.nextElement();
            if(fileName.startsWith(key))
            {
                encode = (String) wildcard_encode_rates.get(key);
                break;
            }
                
        }
        return encode;
    }
    public static void main(String[] args) {
        TestEncodeRates ter = new TestEncodeRates();
        String rate = ter.getEncodeRate("RGBER", "xlm.ptm");
        System.out.println("rate:"+rate);
        rate = ter.getEncodeRate("RTIER", "noname.rti");
        System.out.println("rate:"+rate);
        rate = ter.getEncodeRate("PTMER", "noname.ptm");
        System.out.println("rate:"+rate);
    }
}
