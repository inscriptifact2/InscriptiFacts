package com.isf.app.view.containers;

import com.isf.app.models.TreeParent;
import com.isf.app.view.listeners.MainSearchListener;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class MainSearchPanel extends TrimmedPanel {

    public QueryDefinitionPanel qdp;
    public QueryMenuPanel qmu;
    public ActionListener al;

    private MainSearchListener mainSearchListener;

    public MainSearchPanel(Insets insets) {
        super(insets);
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(0xa49585));
        qmu = new QueryMenuPanel();
        qdp = new QueryDefinitionPanel(qmu);
        add(qdp, "West");
        add(qmu, "Center");
    }

    public void addActionListener(ActionListener actionlistener) {
        al = actionlistener;
        qdp.bp.addActionListener(actionlistener);
    }

    public void addMainSearchListener(MainSearchListener mListener) {
        mainSearchListener = mListener;
        qdp.addMainSearchListener(mainSearchListener);
        //  qmu.addMainSearchListener(mainSearchListener);
    }


    public void setData(TreeParent treeparent) {
        qdp.setData(treeparent);
    }
/*
    PSVM(String args[]) {
        JFrame jframe = new JFrame();
        TreeParent tp = new TreeParent("At");
        tp.addChild(new TreeParent("Corpus"));
        tp.addChild(new TreeParent("Corpus1"));
        tp.addChild(new TreeParent("Corpus2"));
        tp.addChild(new TreeParent("Corpus3"));

        Container container = jframe.getContentPane();
        MainSearchPanel mainsearchpanel = new MainSearchPanel(new Insets(0, 0, 0, 0));
        mainsearchpanel.setData(tp);
        container.add(mainsearchpanel, "North");
        jframe.setSize(300, 300);
        jframe.setVisible(true);
    }
*/

}
