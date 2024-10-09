package isf.controls.view.gui;

import isf.controls.view.controls.InscriptBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class InscriptListCellRenderer extends DefaultListCellRenderer {

    public InscriptListCellRenderer() {

    }

    public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1) {
        //    jlist.getParent().setSize(

        JLabel jlabel = new JLabel(obj != null ? obj.toString().trim() : "");
        jlabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent event) {
                setForeground(new Color(0xac5539));
            }

            public void mouseExited(MouseEvent event) {
                setForeground(new Color(0));
            }
        });

        jlabel.setFont(new Font("Arial", 1, 12));
        jlabel.setBorder(new InscriptBorder(new Color(0xa7a7a7)));
        if (flag) {
            jlabel.setBackground(Color.white);
            jlabel.setForeground(new Color(0xac5539));
        } else {
            jlabel.setBackground(Color.white);
            jlabel.setForeground(new Color(0));
        }
        return jlabel;
    }




    /* public Dimension getPreferredSize(){
        return new Dimension((int)(dim.width *1.4), dim.height )  ;
    }*/
}
