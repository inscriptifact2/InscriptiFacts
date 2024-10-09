/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.controllers;

import isf.controls.view.listeners.ImageLoaderListener;
import isf.controls.view.listeners.SortListener;

/**
 * @author IN030223
 */
public class ImageLoaderManager {

  //  private static String text;

    private static ImageLoaderListener iLL;

    private static ImageLoaderManager img;

    private static SortListener sl;

    private ImageLoaderManager() {

    }

    public static ImageLoaderManager getInstance() {
        if (img == null)
            img = new ImageLoaderManager();
        return img;
    }

    public void registerListener(ImageLoaderListener ill) {
        iLL = ill;
    }

    public ImageLoaderListener getLoader() {
        return iLL;
    }
    private static String[] cache;
    public static void cacheQuery(String type, String query)
    {
        if(cache == null)
            cache = new String[2];
        cache[0] = type;
        cache[1] = query;

    }
     public static void resetcache()
    {
         cache = new String[2];
         if(sl!=null)
             sl.resetSort();
    }
    public static String[] getCacheQuery()
    {
        return cache;
    }

 /*   public static void setTextCode(String code)
    {
        text = code;
        if(sl!=null)
             sl.setTextCode(text);

    }*/


    public void registerSorter(SortListener slt)
    {
        sl = slt;
      //  sl.setTextCode(text);
    }

    public SortListener getSorter()
    {
     /*  if(!sl.getTextCode().equalsIgnoreCase(text))
           sl.setTextCode(text);*/
        return sl;
    }


}
