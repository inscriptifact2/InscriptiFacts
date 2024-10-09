package isf.sc.utils;

import isf.common.utils.LogManager;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import org.apache.log4j.Logger;

public class ReadThread extends Thread {

    long start;
    int pos, len;
    Hashtable holder;
    // Blob blob;
    RandomAccessFile file;
    Logger log = LogManager.getLogger(isf.sc.utils.ReadThread.class);

    public ReadThread(int p, ThreadGroup threadgroup, Hashtable v, long st, int end, RandomAccessFile b) {
        super(threadgroup, p + "");
        pos = p;
        start = st;
        len = end;
        file = b;
        holder = v;//(Vector)v.clone();
        start();
    }

    public void run() {
        try {
            //logger.debug ("Holder"+ holder);
            // logger.debug("Blob"+blob);

            byte[] bytes = new byte[len];
            file.seek(start);
            file.read(bytes, 0, len);
            holder.put(pos + "", bytes);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
