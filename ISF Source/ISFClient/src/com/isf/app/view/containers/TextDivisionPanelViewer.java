package com.isf.app.view.containers;

import com.isf.app.models.TreeChild;
import com.isf.app.models.TreeParent;
import com.isf.app.view.controls.InscriptDyanBorder;
import com.isf.app.view.controls.ObjectList;
import com.isf.app.view.controls.TrimmedIconButton;
import com.isf.app.view.gui.ObjectRenderer;
import com.isf.app.view.listeners.ObjectListListener;
import isf.controls.view.containers.TrimmedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextDivisionPanelViewer extends TrimmedPanel
        implements ActionListener {

    private ObjectList left;
    //  private ObjectList right;
    private ObjectListListener ol;
    private Dimension dim;
    private Dimension dim1;
    private JLabel title;
    ImageIcon icon;
    private Vector vector = null;
    private Vector tempVector = null;
    private ActionListener al = null;
    private MouseListener ml = null;
    private int tempIndex = -1;

    public TextDivisionPanelViewer(ImageIcon imageicon) {
        super(new Insets(0, 14, 0, 0));
        icon = imageicon;
        dim = new Dimension(imageicon.getIconWidth(), imageicon.getIconHeight());
        //  dim1 = new Dimension(dim.width - 14, dim.height);
        dim.width = (int) (4 * dim.width);//-14);//(125 * dim.width) / 40;
        //  System.out.println("width   :"+(int)((double)dim.width *0.197D+(double)dim.width *0.39468D+(double)dim.width *0.388D));
        setBackground(Color.black);
        setLayout(new BorderLayout(0, 0));
        left = new ObjectList();
        left.setSize(new Dimension(dim.width, dim.height));
        int i = (int) ((double) (dim.width) * 0.25D);

        ObjectRenderer name = new ObjectRenderer("Text Division", i, dim.height);
        name.setOpaque(true);
        name.setBackground(new Color(0xD9D9D9));
        name.setForeground(new Color(0x656565));
        name.setFont(new Font("Arial", Font.BOLD, 12));

        ObjectRenderer description = new ObjectRenderer("Description", 2 * i, dim.height);//39468D
        description.setOpaque(true);
        description.setBackground(new Color(0xD9D9D9));
        description.setForeground(new Color(0x656565));
        description.setFont(new Font("Arial", Font.BOLD, 12));

        ObjectRenderer muno = new ObjectRenderer("Museum Numbers", dim.width - (3 * i) - 14, dim.height);//(int)((double)dim.width * 0.39468D), dim.height);
        muno.setOpaque(true);
        muno.setBackground(new Color(0xD9D9D9));
        muno.setForeground(new Color(0x656565));
        muno.setFont(new Font("Arial", Font.BOLD, 12));
        JPanel p1 = new JPanel() {
            public Dimension getPreferredSize() {
                //return new Dimension((int)((dim.width-14)*.20),dim.height);

                return new Dimension(dim.width, dim.height);

            }
        };
        p1.setBackground(new Color(0xD9D9D9));
        // p1.setBorder(new InscriptDyanBorder(new Color(0x808080),0,0,1,0));
        p1.setSize(dim);
        p1.setLayout(new FlowLayout(0, 0, 0));//BorderLayout());
        p1.add(name);//,"West");
        p1.add(description);//,"Center");
        p1.add(muno);//,"East");
        JPanel jpanel = new JPanel() {

            public Dimension getPreferredSize() {
                return new Dimension(dim.width - 20, dim.height);//(int)((double)(dim.width - 14) * 0.29999999999999999D), dim.height);
            }

        };
        jpanel.setLayout(new BorderLayout(0, 0));
        jpanel.add(p1, "North");
        jpanel.add(left, "Center");
        add(jpanel, "Center");
        TrimmedPanel trimmedpanel = new TrimmedPanel();
        title = new JLabel("");
        title.setOpaque(true);
        title.setBackground(new Color(0xd9d9d9));
        title.setForeground(new Color(0x656565));
        title.setFont(new Font("Arial", 1, 17));
        setTitle("Codex Syrus Sinaiticus");
        title.setBorder(new InscriptDyanBorder(new Color(0x808080), 1, 0, 1, 1));
        trimmedpanel.setLayout(new BorderLayout(0, 0));
        trimmedpanel.add(new TrimmedIconButton(imageicon), "West");
        trimmedpanel.add(title, "Center");
        add(trimmedpanel, "North");
        setPreferredSize(new Dimension(dim.width - 20, dim.height));//dim
        left.addActionListener(this);
      //  left.setFireOnHover(true);
    }

    public void setTitle(String s) {
        title.setText(s);
    }

    public Dimension getPreferredSize() {
        return dim;
    }

    public void setData(Vector vector) {
        this.vector = vector;
        left.setData(vector);
    }

    public void addObjectListListener(ObjectListListener objectlistlistener) {
        //   System.out.println("ol from tdpv  :"+objectlistlistener);
        ol = objectlistlistener;
        left.addObjectListListener(ol);

    }

    public void addMouseListener(MouseListener ml) {
        this.ml = ml;
        left.addMouseListener(ml);
    }

    public void actionPerformed(ActionEvent actionevent) {
        ObjectRenderer objectrenderer = (ObjectRenderer) actionevent.getSource();
        //System.out.println("---- in action performed ----");
        TreeChild treechild = objectrenderer.getTreeChild();
        int index = -1;

        Vector temp = new Vector();
        tempVector = new Vector();
        if (treechild instanceof TreeParent) {

            if (treechild.toString().equals("")) {
                // System.out.println("in if ''");
                Vector vector1 = new Vector();
                vector1.addElement(new TreeChild(""));
                tempVector = vector1;
                //   System.out.println("---tempVector in if----"+tempVector);
                //   left.setData(tempVector);

            } else {
                // System.out.println("treechild.toString()  :"+treechild.toString());
                // System.out.println("in else ");
                Vector subdivs = ((TreeParent) treechild).getAllChilds();
                //System.out.println("vector.size() "+vector.size()+"   subdivs.size  "+subdivs.size());
                int listSize = vector.size() + subdivs.size();
                index = objectrenderer.getIndex();
                if (tempIndex == -1) {

                    index = index;
                    // System.out.println("in -1  "+index);
                } else if (tempIndex != -1 && index > tempIndex) {
                    index = index - subdivs.size();
                    //System.out.println("in ! -1 index "+index);
                    // System.out.println("in ! -1  tempIndex"+index);
                }
                // System.out.println("objrend index  :"+index);                //"vector.elementAt(0)  "+vector.elementAt(0));


                if (subdivs.size() > 0) {
                    for (int i = 0; i < vector.size(); i++) {
                        TreeParent t = (TreeParent) vector.elementAt(i);
                        tempVector.addElement(t);

                        if (index == i) {
                            for (int j = 0; j < subdivs.size(); j++) {
                                tempVector.addElement(subdivs.elementAt(j));
                            }
                        }
                    }
                    left.setData(tempVector);
                    tempIndex = index;
                    //System.out.println("I  have SubDivisions");
                } else {
                    //send message to populate next screen
                    // System.out.println("I am Here in text division "+index);
                    ol.objectSelected(treechild);
                }
                //    System.out.println("---tempVector in else----"+tempVector);
            }


            //  objectrenderer.isExpand=true;
            //   System.out.println("after click index  "+index);
            //   System.out.println("after click tempIndex  "+tempIndex);
        } else {
            //send message to populate next screen
            //  System.out.println("I am Here in text sub division ");
            ol.objectSelected(treechild);
        }
    }

}
