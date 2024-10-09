package isf.controls.view.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class ISFButton extends JButton {


    public ISFButton(String ac) {
        super(ac);
        setActionCommand(ac);
        setBackground(new Color(0xd9d9d9));
        // 0xa49585
        // 0xd9d9d9

        setForeground(new Color(0xAC5539));
        setBorderPainted(true);
        setFocusPainted(false);
        setFont(new Font("Arial", Font.BOLD, 12));
        setContentAreaFilled(true);

    }

    public ISFButton(String ac, String eicon, String disabledIcon) {
        super(ac);
        setActionCommand(ac);
        //if(orient){
        setHorizontalTextPosition(AbstractButton.CENTER);
        setVerticalTextPosition(AbstractButton.BOTTOM);
        //        }
        //  setBackground(Color.LIGHT_GRAY);
        
        ImageIcon enabled = isf.controls.utils.ResolutionManager.getInstance().loadImage(eicon);
        ImageIcon disabled = isf.controls.utils.ResolutionManager.getInstance().loadImage(disabledIcon);
        setIcon(enabled);
        setDisabledIcon(disabled);
        setForeground(Color.white);
        setBorderPainted(true);
        setFocusPainted(false);
        setFont(new Font("Tahoma", Font.PLAIN, 10));
        setContentAreaFilled(true);

        setBackground(Color.black);
        // setOpaque(true);
        //setHorizontalAlignment(2);
    }

   
/*    public void disableRight() {
        if (getCountRight() == 1) {
            enableRight();
            return;
        }
        for (TrimmedIconButton trimmediconbutton = right; trimmediconbutton != null; trimmediconbutton = trimmediconbutton.right) {
            trimmediconbutton.setEnabled(false);
            trimmediconbutton.setSelected(false);
        }

    }

    private void enableRight() {
        right.setEnabled(true);
        right.setSelected(false);
    }

    private int getCountRight() {
        int i = 0;
        for (TrimmedIconButton trimmediconbutton = right; trimmediconbutton != null; trimmediconbutton = trimmediconbutton.right) {
            i++;
        }

        return i;
    }

    

    public void enableLeft() {
        for (TrimmedIconButton trimmediconbutton = left; trimmediconbutton != null; trimmediconbutton = trimmediconbutton.left) {
            trimmediconbutton.setSelected(false);
            trimmediconbutton.setEnabled(true);
        }

    }*/

    public void setSelected(boolean flag) {
        super.setSelected(flag);
        if (flag)
            setForeground(Color.yellow);
        else
            setForeground(Color.white);
    }

    public Dimension getPreferredSize() {
        return new Dimension(59, 50);
    }

    public Insets getInsets() {
        return new Insets(1, 1, 1, 1);
    }


}
