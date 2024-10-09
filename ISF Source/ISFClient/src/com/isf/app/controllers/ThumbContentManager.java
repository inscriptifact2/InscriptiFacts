package com.isf.app.controllers;

public interface ThumbContentManager {

    public abstract int getSelectionCount();

    public abstract boolean canSelectMore(String type);
}
