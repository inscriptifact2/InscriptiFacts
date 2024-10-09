/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.common.utils;

import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory implements ThreadFactory {

    int priority;

    public PriorityThreadFactory(int pace) {
        priority = pace;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setPriority(priority);
        return t;
    }
}