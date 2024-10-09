package isf.cache;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: 0223
 * Date: Aug 8, 2007
 * Time: 7:46:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class CacheItem implements Serializable {
    public CacheItem(String path) {
        this.name = path;
    }

    public CacheItem() {
    }

    private String name;
    private long lastAccessedTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }


    public void setSize(double size) {
        this.size = size;
    }

    private double size;


    public boolean equals(Object obj) {
        if (obj instanceof CacheItem) {
            CacheItem p = (CacheItem) obj;
            return (name.equals(p.getName()) && lastAccessedTime == p.getLastAccessedTime() && size == p.getSize());
        }
        return false;
    }


    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name + ": " + size;
    }

    public double getSize() {

        // size = getLength(new File(name)) / 1024 * 1024;
        return size;
    }



    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(name);
        out.writeObject(new Double(getSize()));
        out.writeObject(new Long(lastAccessedTime));
        out.flush();
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        size = ((Double) (in.readObject())).doubleValue();
        lastAccessedTime = ((Long) (in.readObject())).longValue();
    }
}
