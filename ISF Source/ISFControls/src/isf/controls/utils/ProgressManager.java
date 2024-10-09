/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.utils;

import isf.common.utils.PTMConfig;
import javax.swing.JComboBox;


/**
 * @author IN030223
 */
public class ProgressManager {

    private static JComboBox box;
    private static PTMConfig ptm ;


    public static void registerCombo(JComboBox combo) {
        box = combo;

    }


    public static void setConfig(PTMConfig config) {
       ptm = config;
        if (config == null){
            box.setSelectedIndex(-1);

        }
    }

    public static PTMConfig getConfig() {
        return ptm;
    }

}
