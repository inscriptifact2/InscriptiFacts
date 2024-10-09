package com.isf.app.view.controls;

import com.isf.app.models.InscriptTableData;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CatalougePanelTableCellRenderer extends JPanel
        implements TableCellRenderer {

    public static int count = 0;
    private static Dimension dim;
    private CatalougePanelTableCellRenderer r;
    protected CatalougeRendererLabel left;
    protected CatalougeRendererLabel right;
    protected CatalougeRendererLabel last;
    private static CatalougeButton cb = new CatalougeButton("images/Go.jpg");

    public CatalougePanelTableCellRenderer(Dimension dimension) {
        dim = dimension;
        setLayout(new FlowLayout(0, 0, 0));
        setBackground(Color.black);
        cb.setActionCommand("go");
    }

    public void addActionListener(ActionListener actionlistener) {
        cb.addActionListener(actionlistener);
    }

    public Component getTableCellRendererComponent(JTable jtable, Object obj, boolean flag, boolean flag1, int i, int j) {
        double ad[] = isf.controls.utils.ResolutionManager.getInstance().getRatio();
        dim.height = jtable.getRowHeight(i);
        dim.width = jtable.getPreferredScrollableViewportSize().width;
        if (obj instanceof String) {
            // return new JLabel("                 ");
            left = new CatalougeRendererLabel("", dim.width, dim.height);//(int)(ad[0] * ((double)dim.width))+120

            r = new CatalougePanelTableCellRenderer(dim);
            left.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 1, 0));
            r.add(left);
            left.setBackground(new Color(0));
            r.setBackground(new Color(0));
            return r;
        }

        InscriptTableData inscripttabledata = (InscriptTableData) obj;
        //dim.height = jtable.getRowHeight(i);
        //dim.width = jtable.getPreferredScrollableViewportSize().width;
        int k = inscripttabledata.getSize();
        left = new CatalougeRendererLabel(inscripttabledata.getName(), (int) (ad[0] * ((double) dim.width * 0.34999999999999998D)), dim.height);
        left.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 1, 1));
        right = new CatalougeRendererLabel(inscripttabledata.getValue(), (int) (ad[0] * ((double) dim.width * 0.55000000000000004D)) + 120, dim.height);
        right.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 1, 0));
        last = new CatalougeRendererLabel("  ", (int) (ad[0] * ((double) dim.width * 0.10000000000000001D)), dim.height);
        last.setBorder(new InscriptDyanBorder(Color.white, 0, 0, 1, 0));
        r = new CatalougePanelTableCellRenderer(dim);
        r.add(left);
        r.add(right);
        r.add(last);
        if (flag) {
            left.setBackground(new Color(0xac5539));
            right.setBackground(new Color(0xac5539));
            r.setBackground(new Color(0xac5539));
            //    last.setLayout(new FlowLayout(2, 0, (int) (ad[1] * (double) (dim.height - cb.getIcon().getIconHeight())) / 2));
            //   last.add(cb);
        } else {
            left.setBackground(new Color(0));
            right.setBackground(new Color(0));
            r.setBackground(new Color(0));
        }
        return r;
    }

}
