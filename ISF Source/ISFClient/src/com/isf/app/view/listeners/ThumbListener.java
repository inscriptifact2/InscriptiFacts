package com.isf.app.view.listeners;

import com.isf.app.controllers.ThumbContentManager;
import com.isf.app.models.ThumbData;

public interface ThumbListener
        extends ThumbHeaderSelectionListener {

    public abstract void thumbSelected(ThumbData thumbdata);

    public abstract void thumbDeSelected(ThumbData thumbdata);

    public abstract ThumbContentManager getThumbContentManager();
}
