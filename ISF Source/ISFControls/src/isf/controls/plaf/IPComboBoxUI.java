
package isf.controls.plaf;

import isf.controls.view.controls.IsfComboBox;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class IPComboBoxUI extends MetalComboBoxUI {
    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {

            public void show() {
                Dimension popupSize = ((IsfComboBox) comboBox).getPopupSize();
                popupSize.setSize(popupSize.width,
                        getPopupHeightForRowCount(comboBox.getMaximumRowCount()));
                Rectangle popupBounds = computePopupBounds(0,
                        comboBox.getBounds().height, popupSize.width, popupSize.height);
                scroller.setMaximumSize(popupBounds.getSize());
                scroller.setPreferredSize(popupBounds.getSize());
                scroller.setMinimumSize(popupBounds.getSize());
                list.invalidate();
                int selectedIndex = comboBox.getSelectedIndex();
                if (selectedIndex == -1) {
                    list.clearSelection();
                    list.ensureIndexIsVisible(0);
                } else {
                    list.setSelectedIndex(selectedIndex);
                    list.ensureIndexIsVisible(selectedIndex);
                }
                //list.ensureIndexIsVisible( list.getSelectedIndex() );
                setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());

                show(comboBox, popupBounds.x, popupBounds.y);
            }
        };
        popup.getAccessibleContext().setAccessibleParent(comboBox);
        return popup;
    }
}

