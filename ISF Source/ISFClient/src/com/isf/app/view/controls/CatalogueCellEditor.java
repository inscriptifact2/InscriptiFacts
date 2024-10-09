package com.isf.app.view.controls;

import com.isf.app.models.InscriptTableData;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

public class CatalogueCellEditor extends JPanel
        implements TableCellEditor {

    private Object value;
    private Dimension dim;
    public static CatalougeButton cb = new CatalougeButton("images/Go.jpg");
    private CellEditorListener cel;

    public CatalogueCellEditor() {
    }

    public CatalogueCellEditor(Object obj, Dimension dimension) {
        cb.setActionCommand("go");
        value = obj;
        dim = dimension;
        setLayout(new FlowLayout(0, 0, 0));
        setBackground(new Color(0xac5539));
        build();
    }

    public Component getTableCellEditorComponent(JTable jtable, Object obj, boolean flag, int i, int j) {
        Dimension dimension = new Dimension(jtable.getPreferredScrollableViewportSize().width, jtable.getRowHeight(i));
        return new CatalogueCellEditor(obj, dimension);
    }

    public void build() {
        if (value instanceof InscriptTableData) {
            double ad[] = isf.controls.utils.ResolutionManager.getInstance().getRatio();
            InscriptTableData inscripttabledata = (InscriptTableData) value;
            CatalougeRendererLabel catalougerendererlabel = new CatalougeRendererLabel(inscripttabledata.getName(), (int) (ad[0] * ((double) dim.width * 0.34999999999999998D)), dim.height);
            catalougerendererlabel.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 1, 1));
            CatalougeRendererLabel catalougerendererlabel1 = new CatalougeRendererLabel(inscripttabledata.getValue(), (int) (ad[0] * ((double) dim.width * 0.55000000000000004D + 120D)), dim.height);
            catalougerendererlabel1.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 1, 0));
            CatalougeRendererLabel catalougerendererlabel2 = new CatalougeRendererLabel("", (int) (ad[0] * ((double) dim.width * 0.10000000000000001D)), dim.height);
            catalougerendererlabel2.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 1, 0));
            add(catalougerendererlabel);
            add(catalougerendererlabel1);
            add(catalougerendererlabel2);
            catalougerendererlabel.setBackground(new Color(0xac5539));
            catalougerendererlabel1.setBackground(new Color(0xac5539));
            catalougerendererlabel2.setBackground(new Color(0xac5539));
            catalougerendererlabel2.setLayout(new FlowLayout(2, 0, (int) (ad[1] * (double) (dim.height - cb.getIcon().getIconHeight())) / 2));
            catalougerendererlabel2.add(cb);
        } else {
            add(new JLabel(""));
        }
    }

    public void addActionListener(ActionListener actionlistener) {
        cb.addActionListener(actionlistener);
    }

    public void addCellEditorListener(CellEditorListener celleditorlistener) {
        cel = celleditorlistener;
    }

    public void cancelCellEditing() {
        cel.editingCanceled(new ChangeEvent(" "));
    }

    public Object getCellEditorValue() {
        return value;
    }

    public boolean isCellEditable(EventObject eventobject) {
        //System.out.println("class:"+value.getClass().getCanonicalName() );
        /* if(value!=null && value instanceof InscriptTableData)
             return true;
         else
             return false;*/
        /* InscriptTable table = (InscriptTable)eventobject.getSource() ;
          int selection = table.getSelectedRow();
           Vector vector = ((CatalougeTableModel) table.getModel()).getData();
          if(vector.size() <= selection)
          {

              return true;
          }else{
              System.out.println(vector.size()+"::"+selection);
              return false;
          }*/
        return true;
    }

    public void removeCellEditorListener(CellEditorListener celleditorlistener) {
    }

    public boolean shouldSelectCell(EventObject eventobject) {
        /*   if(value!=null && value instanceof InscriptTableData)
               return true;
           else
               return false;*/
        return true;
    }

    public boolean stopCellEditing() {
        cel.editingStopped(new ChangeEvent(" "));
        return true;
    }

    public Dimension getPreferredSize() {
        return dim;
    }

}
