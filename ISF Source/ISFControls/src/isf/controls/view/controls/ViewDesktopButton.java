package isf.controls.view.controls;

import isf.controls.models.DesktopDataObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewDesktopButton extends JButton
        implements ActionListener {

    private DesktopDataObject object;
    private Dimension dim;
    private Color fgColor;
    private static Color pfg;
    private static Color selectedColor = new Color(0xac5539);
    private static Color sbgColor;
    private static Color bgColor = new Color(0xd9d9d9);

    public ViewDesktopButton(String s, ImageIcon imageicon, boolean flag) {
        super(imageicon);
        fgColor = Color.black;
        setOpaque(true);
        setActionCommand(s.trim());
        setContentAreaFilled(false);
        setBorderPainted(flag);
        setFocusPainted(false);
        setBorder(new InscriptBorder(Color.white));
        setFont(new Font("Arial", 1, 11));
        dim = new Dimension(imageicon.getIconWidth(), imageicon.getIconHeight());
    }

    public ViewDesktopButton(String s, String s1, boolean flag) {
        super(s);
        fgColor = Color.black;
        setOpaque(true);
        setForeground(fgColor);
        setBackground(bgColor);
        setActionCommand(s.trim());
        setBorderPainted(flag);
        setFocusPainted(false);
        setBorder(new InscriptBorder(Color.white));
        setFont(new Font("Arial", 1, 11));
        ImageIcon imageicon = isf.controls.utils.ResolutionManager.getInstance().getImageIcon(s1);
        dim = new Dimension(imageicon.getIconWidth(), imageicon.getIconHeight());
        //System.out.println("dimofimageicon:" + dim + " string name :" + s1);
    }

    public ViewDesktopButton(String s, String s1, boolean flag, boolean center) {
        this(s, s1, flag);
        setHorizontalAlignment(JButton.LEFT);
        setForeground(Color.white);
        setBackground(Color.black);

    }

    public ViewDesktopButton(DesktopDataObject desktopdataobject, String s, boolean flag) {
        this(desktopdataobject.toString(), s, flag);
        object = desktopdataobject;
        setSelected(false);
    }

    public Insets getInsets() {
        return new Insets(0, 1, 0, 1);
    }

    public Dimension getPreferredSize() {

        return dim;
    }

    public void setSelected(boolean flag) {
        if (flag) {
            setBackground(sbgColor);
            setForeground(selectedColor);
        }
        if (!flag) {
            fgColor = object.isAdded() ? selectedColor : pfg;
            setForeground(fgColor);
            setBackground(bgColor);
        }
        super.setSelected(flag);
    }

    public void actionPerformed(ActionEvent actionevent) {
        ViewDesktopButton viewdesktopbutton = (ViewDesktopButton) actionevent.getSource();
        viewdesktopbutton.setSelected(!viewdesktopbutton.isSelected());
    }

    public DesktopDataObject getObject() {
        return object;
    }
/*
    PSVM(String args[]) {
        JFrame jframe = new JFrame();
        jframe.setSize(300, 400);
        JPanel jpanel = new JPanel();
        DesktopDataObject desktopdataobject = new DesktopDataObject("Front View", "Image1", "1.sid");
        jpanel.add(new ViewDesktopButton(desktopdataobject, "VD_blank.jpg", true));
        jframe.getContentPane().add(jpanel);
        jframe.setVisible(true);
    }
*/
    static {
        pfg = Color.black;
        sbgColor = Color.white;
    }
}
