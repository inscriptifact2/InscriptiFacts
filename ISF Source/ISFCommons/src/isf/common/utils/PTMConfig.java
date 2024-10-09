/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isf.common.utils;


import java.io.IOException;
import java.io.Serializable;

/**
 * @author IN030223
 */
public class PTMConfig implements Serializable {

    private String name;
    private int type;
    private int basistype;
    private String version;
    private String cached;
    private int rows,  columns,  segmentSize;
    private int width;
    private int height;
    private float[] bias;
    private float[] scale;
    private long size;
    private int colors, terms, elements;
    private String extension=".ptm";
    
    

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeObject(name);
        out.writeObject(new Integer(type));
        out.writeObject(version);
        out.writeObject(cached);
        out.writeObject(new Integer(rows));
        out.writeObject(new Integer(columns));
        out.writeObject(new Integer(segmentSize));
        out.writeObject(new Integer(width));
        out.writeObject(new Integer(height));
        out.writeObject(bias);
        out.writeObject(scale);
        out.writeObject(new Long(size));
        out.writeObject(new Integer(basistype));
        out.writeObject(new Integer(getColors()));
        out.writeObject(new Integer(getTerms()));
        out.writeObject(new Integer(getElements()));
        out.writeObject(extension);
        out.flush();

    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        name = ((String) in.readObject());
        type = ((Integer) in.readObject()).intValue();
        version = ((String) in.readObject());
        cached = ((String) in.readObject());
        rows = ((Integer) in.readObject()).intValue();
        columns = ((Integer) in.readObject()).intValue();
        segmentSize = ((Integer) in.readObject()).intValue();
        width = ((Integer) in.readObject()).intValue();
        height = ((Integer) in.readObject()).intValue();
        bias = ((float[]) in.readObject());
        scale = ((float[]) in.readObject());
        size = ((Long) in.readObject()).longValue();
        basistype = ((Integer) in.readObject()).intValue();
        setColors(((Integer) in.readObject()).intValue());
        setTerms(((Integer) in.readObject()).intValue());
        setElements(((Integer) in.readObject()).intValue());
        setExtension((String)in.readObject());
     }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCached() {
        return cached;
    }

    public void setCached(String cached) {
        this.cached = cached;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float[] getBias() {
        return bias;
    }

    public void setBias(float[] bias) {
        this.bias = bias;
    }

    public float[] getScale() {
        return scale;
    }

    public void setScale(float[] scale) {
        this.scale = scale;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getSegmentSize() {
        return segmentSize;
    }

    public void setSegmentSize(int segmentSize) {
        this.segmentSize = segmentSize;
    }

    public String getExt() {
       if(type == 0)// PTM.TYPE_RAW_PTM
       {
           return ".ptm";
       }else
       {
           return ".rti";
       }
    }

    
    public int getBasistype() {
        return basistype;
    }

    public void setBasistype(int basistype) {
        this.basistype = basistype;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

    
}
