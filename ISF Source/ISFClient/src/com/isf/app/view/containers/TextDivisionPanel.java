package com.isf.app.view.containers;


import com.isf.app.models.TreeChild;
import com.isf.app.models.TreeParent;
import com.isf.app.view.gui.ObjectCellRenderer;
import com.isf.app.view.listeners.ObjectListListener;
import isf.common.view.gui.TextDivision;
import isf.common.view.gui.TextDivisionSubDivision;
import isf.controls.view.containers.TrimmedPanel;
import isf.controls.view.controls.InscriptButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.Vector;


public class TextDivisionPanel extends TrimmedPanel {

    private TextDivisionPanelViewer tdpv;
    // public static ObjectRenderer current;
    public static ObjectCellRenderer current;
    private String isfatno;
    private String topTitle;
    private Vector data;
    //  private ActionListener al;
    private ObjectListListener ol = null;

    public TextDivisionPanel() {
        isfatno = "";
        topTitle = "";
        data = new Vector();
        setLayout(new BorderLayout(0, 0));
        InscriptButton inscriptbutton = new InscriptButton("images/textdivcurve.jpg");
        //  System.out.println("********* after inscriptbutton **********");
        inscriptbutton.setBorderPainted(false);
        TrimmedPanel trimmedpanel = new TrimmedPanel();
        trimmedpanel.setLayout(new FlowLayout(0, 0, 0));
        trimmedpanel.add(inscriptbutton);
        trimmedpanel.setBackground(Color.black);
        setBackground(Color.black);
        tdpv = new TextDivisionPanelViewer(isf.controls.utils.ResolutionManager.getInstance().getImageIcon("images/4_listofdivisions01.jpg"));
        tdpv.setTitle(getTopTitle());
        add(trimmedpanel, "North");
        add(tdpv, "West");
        tdpv.setData(data);
    }

    public void setISFNumber(String s) {
        if (s != "") {
            isfatno = s;
        } else {
            throw new NullPointerException();
        }
    }

    public String getISFNumber() {
        return isfatno;
    }

    public void setTopTitle(String s) {
        topTitle = s;
        tdpv.setTitle(s);
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setData(Vector vector) {
        tdpv.setData(vector);
    }

    public void reset() {
        tdpv.setTitle("");
        tdpv.setData(new Vector());
    }

    public void addObjectListListener(ObjectListListener objectlistlistener) {
        ol = objectlistlistener;
        tdpv.addObjectListListener(ol);
    }

    public Vector getData()
            throws SQLException {
        return new Vector();
    }

    public static Vector getTestData() {
        String as[] = {
                "Folios 1-20", "Folios 81-100", "Folios 101-120", "Folios 121-140", "Folios 141-160", "Folios 161-181"
        };
        String as1[] = {
                "Folio 81a, (Lk 15:23-16:2a);", "Folio 82a, ", "Folio 83a, ", "Folio 84a, "
        };
        Vector vector = new Vector();
        for (int i = 0; i < as.length; i++) {
            TreeParent treeparent = new TreeParent(as[i] + "TP");
            for (int j = 0; j < as1.length; j++) {
                treeparent.addChild(new TreeChild(as[i] + " TC  " + as1[j]));
            }

            vector.addElement(treeparent);
        }

        TreeParent treeparent1 = new TreeParent("Folio With No Subdivisions");
        vector.addElement(treeparent1);
        return vector;
    }

    public static Vector testData() {
        Vector v = new Vector();
        TreeParent tp = null;
        Vector subdivs = new Vector();
        for (int j = 0; j < 5; j++) {
            TextDivisionSubDivision sub = new TextDivisionSubDivision();
            sub.setTextDivisionSubDivisionName("subdivename-" + j);
            sub.setTextDescTitle("textdesctitle-" + j);
            sub.setMainTextPubNo("maintextno-" + j);
            subdivs.addElement(sub);
        }
        for (int i = 1; i <= 10; i++) {
            TextDivision td = new TextDivision();
            td.setTextDivisionName("Coulmn-" + i);
            td.setTextDivisionDesc("Desc-" + i);
            td.setTextDivisionMuseumNo("museumNo-" + i);

            tp = new TreeParent(td);
            if (i == 2 || i == 5 || i == 9) {
                td.setTextDivisionSubDivisions(subdivs);
                for (int k = 0; k < subdivs.size(); k++) {
                    tp.addChild(new TreeChild(subdivs.elementAt(k)));

                }
            }
            v.addElement(tp);
        }
        return v;
    }

}
