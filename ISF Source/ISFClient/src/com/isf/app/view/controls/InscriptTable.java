package com.isf.app.view.controls;

import com.isf.app.models.CatalougeTableModel;
import com.isf.app.models.InscriptTableData;
import isf.common.utils.InscriptConfigLoader;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

public class InscriptTable extends JTable {

    int def = 20;

    public InscriptTable(TableModel tablemodel) {
        super(tablemodel);
        def = 20;
        setRowHeight(40);
        setTableHeader(null);
        // setRowHeights();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setRowHeights() {
        /*Vector vector = ((CatalougeTableModel) getModel()).getData();
        int i = 0;
        int j = vector.size();
        for (; i < j; i++) {
            setRowHeight(i, getRowHeight(i));
        }
        int maxrows = Integer.parseInt(InscriptConfigLoader.getInstance().getProperty("TestListSize"));
        if (j < maxrows) {

            for (; j < maxrows; j++)
                setRowHeight(j, def );
        }*/
        int maxrows = Integer.parseInt(InscriptConfigLoader.getInstance().getProperty("TestListSize"));
        for (int i = 0; i < maxrows; i++)
            setRowHeight(i, 40);

    }

    public boolean isCellEditable(int i, int i1) {
        //return super.isCellEditable(i, i1);    //To change body of overridden methods use File | Settings | File Templates.
        Vector vector = ((CatalougeTableModel) getModel()).getData();
        boolean editable = false;
        if (i < vector.size()) {
            if (vector.elementAt(i) instanceof InscriptTableData)
                editable = true;

        }
        return editable;

    }

    public int getRowHeight(int i) {
        /* int temp = 0;
         try {
             Vector vector = ((CatalougeTableModel) getModel()).getData();
             InscriptTableData inscripttabledata = (InscriptTableData) vector.elementAt(i);
             temp = def*2;
         } catch (Exception exception) {
             temp = def;
            // System.out.println("Here"+temp);

         } finally {
          //  System.out.println("Table Row Height :"+i+":"+temp);
           //  System.out.println("Table Row Height:"+i+":"+temp);
             return temp;
         }
        // return ResolutionManager.getInstance().getImageIcon("images/Go.jpg").getIconHeight()+10;*/
        return 40;
    }
/*
    PSVM(String args[]) {
        JFrame jframe = new JFrame();
        Container container = jframe.getContentPane();
        Vector vector = new Vector();
        CatalougeTextDataObject catalougetextdataobject = new CatalougeTextDataObject(15, 100);
        catalougetextdataobject.setValue("1Q28a Sa", "Rule of Congregation Messianic rule Regle de la Congregation");
        CatalougeTextDataObject catalougetextdataobject1 = new CatalougeTextDataObject(15, 100);
        catalougetextdataobject1.setValue("1027 Myst", "Book of Mysteries ;Livers des mysteres");
        vector.add(catalougetextdataobject);
        vector.add(catalougetextdataobject1);
        CatalougeTableModel catalougetablemodel = new CatalougeTableModel(vector);
        container.setLayout(new BorderLayout());
        InscriptTable inscripttable;
        container.add(new JScrollPane(inscripttable = new InscriptTable(catalougetablemodel)));
        //  inscripttable.getColumnModel().getColumn(0).setCellRenderer(new ResultListingRenderer());
        //  inscripttable.getColumnModel().getColumn(0).setCellEditor(new CatalogueCellEditor());
        jframe.setSize(500, 900);
        jframe.setVisible(true);
    }*/
}
