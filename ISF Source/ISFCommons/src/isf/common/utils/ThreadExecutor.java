/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author IN030223
 */
public final class ThreadExecutor {
    private static ThreadExecutor executor ;
     ExecutorService threadPool = null;
     ExecutorService priorityPool = null;
     int count=0;
    
    private ThreadExecutor(){
    threadPool = Executors.newFixedThreadPool(15); 
    priorityPool = Executors.newFixedThreadPool(3); 
    }
    
    public static ThreadExecutor getInstance()
    {
        if(executor == null)
            executor = new ThreadExecutor();
        return executor;
    }
    public void execute(Runnable r,boolean flag)
    {
        if(!flag)
            threadPool.execute(r);
        else
            priorityPool.execute(r); 
       // System.out.println("Executing:"+(count++) + " :"+flag);
    }
    
   
    
    public void shutdown()
    {
        threadPool.shutdownNow();
        priorityPool.shutdownNow();
    }

}
