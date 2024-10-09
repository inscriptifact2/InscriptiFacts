package com.isf.app.models;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class InscriptTableModel extends AbstractTableModel {

    Vector v;

    public InscriptTableModel(Vector vector) {
        v = vector;
    }

    public int getRowCount() {
        return v.size();
    }

    public int getColumnCount() {
        return 2;
    }

    public Object getValueAt(int i, int j) {
        InscriptTableData inscripttabledata = (InscriptTableData) v.elementAt(i);
        if (j == 0) {
            return inscripttabledata.getName();
        }
        if (j == 1) {
            return inscripttabledata.getValue();
        } else {
            return null;
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
        return v;
    }

    public void setData(Vector vector) {
        v = vector;
        fireTableDataChanged();
    }
}
