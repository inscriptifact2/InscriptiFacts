/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package isf.controls.controllers;

import isf.controls.utils.GlassPaneManager;
import isf.controls.view.controls.SidToolbar;

/**
 * @author IN030223
 */
public class ToolbarManager {

    private static ToolbarManager manager = null;
    private static SidToolbar bar;

    private ToolbarManager() {
    }

    public static ToolbarManager getInstance() {
        if (manager == null)
            manager = new ToolbarManager();
        return manager;
    }

    public void registerToolbar(SidToolbar bar) {
        this.bar = bar;
    }

    public void updateToolbar() {
       bar.setCommonButtons(true);
        if (GlassPaneManager.getInstance().isShowing())
            GlassPaneManager.getInstance().hideAll();

        if (PTMFrameManager.getInstance().getPTMWindow() != null) {
            bar.setPTMButtons(true);
        } else
            bar.setPTMButtons(false);
    }

}
