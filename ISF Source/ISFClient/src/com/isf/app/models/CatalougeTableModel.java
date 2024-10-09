package com.isf.app.models;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class CatalougeTableModel extends DefaultTableModel {

    public CatalougeTableModel(Vector vector) {
        super.dataVector = vector;
        //System.out.println("vector  :"+vector);
    }

    public int getRowCount() {
        int i = super.dataVector.size();
        //  return i < 25 ? 25 : i;
        return i;
    }

    public int getColumnCount() {
        return 1;
    }

    public Object getValueAt(int i, int j) {
        int k = super.dataVector.size();
        if (i >= k) {
            return "";
        } else {
            InscriptTableData inscripttabledata = (InscriptTableData) super.dataVector.elementAt(i);
            return inscripttabledata;
        }
    }

    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    public void fireTableRowsInserted(int i, int j) {
        super.fireTableRowsInserted(i, j);
    }

    public Class getColumnClass(int i) {
        return java.lang.String.class;
    }

    public Vector getData() {
        return super.dataVector;
    }

    public void setData(Vector vector) {
        super.dataVector.removeAllElements();
        //fireTableDataChanged();
        super.dataVector = vector;
        fireTableDataChanged();
    }

    public boolean isCellEditable(int i, int j) {
        int k = super.dataVector.size();
        return i < k;
    }

    public void setValueAt(Object obj, int i, int j) {
        fireTableDataChanged();
    }

    public void addElement(CatalougeTextDataObject ctdo) {
        super.dataVector.addElement(ctdo);
        fireTableDataChanged();
    }

    public void resetData() {
        super.dataVector = new Vector();
        fireTableDataChanged();
    }
}
