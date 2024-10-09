package isf.controls.view.containers;

import isf.controls.models.DesktopDataObject;
import isf.common.utils.PTMConfig;
import isf.ptm.formats.PTM;
import isf.ptm.graphics.EnvironmentMap;
import isf.ptm.operations.Operation;

import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: May 24, 2009
 * Time: 7:12:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class PTMImpl extends PTMPanel {

    public PTMImpl() {
        this.setBackground(Color.black);
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        label.setOpaque(false);
        label.setText(" PTM Files require jdk 1.5 and above. Please consider an upgrade.");
        label.setBackground(Color.black);
        label.setForeground(Color.white);
        this.add(label, "Center");

    }

    public void fitToSize() {
    }

    public void addImage(ImageIcon ing) {
    }


    public void addImage(String fileName) {

    }

    public void addImage(PTMConfig config) {

    }

    public void addImage(PTMConfig config, PTM ptm) {

    }

    public void addImage(DesktopDataObject config) {

    }

    public void setDrawRect(boolean draw) {

    }

    public String getCurrentLevel() {
        return "";
    }

    public void reset() {

    }

    public boolean isAdditionalLight() {
        return false;
    }

    public Operation getPixelTransformOp() {
        return null;
    }

    public int getActiveLight() {
        return 0;
    }

    public void setActiveLight(int i) {

    }

    public boolean isAnimated() {
        return false;
    }

    public void setScale(float f) {

    }

    public float getScale() {
        return 0.0f;
    }

    public void setAnimated(boolean bool) {

    }

    public boolean isLoading() {
        return false;
    }

    public String getDetails() {
        return null;
    }

    public void fireTransform() {

    }

    public float getKSpec() {
        return 0.0f;
    }

    public float getKDiff() {
        return 0.0f;
    }

    public int getExp() {
        return 0;
    }

    public int getPTMWidth() {
        return 0;
    }

    public int getPTMHeight() {
        return 0;
    }

    public void setKSpec(float v) {

    }

    public void setKDiff(float v) {

    }

    public void setExp(int i) {

    }

    public float getLuminance(int i) {
        return 0.0f;
    }

    public void setLuminance(float v, int i) {

    }

    public void setMapSampleSize(int i) {

    }

    public void setMapBlurType(int i) {

    }

    public void setMapKernelSize(int i) {

    }

    public void setMapGuassianBlurSigma(float v) {

    }

    public void refreshMap() {

    }

    public PTM getPTM() {
        return null;
    }

    public void forceUpdate() {

    }

    public void setBrowser(Container container) {

    }

    public void setEnvironmentMap(EnvironmentMap environmentMap) {

    }

    public void setPixelTransformOp(Operation operation) {

    }

    public JLayeredPane getLayeredPane() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public  void toggleLight(boolean dual){}

    public void setExtrapolation(boolean b) {

    }

    public boolean isExtrapolation() {
        return false;
    }

   

    public void detail() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void speed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void useHint(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
/*
    public void setSingleLight() {
        //To change body of implemented methods use File | Settings | File Templates.
    }*/

    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public  void setMessageShown(boolean b){};
    public  boolean getMessageShown(){return false;};

   
    public boolean mustCurbResize() {
        return false;
    }

    public boolean isFrameActive() {
        return false;
    }

    public void setFrameActive(boolean arg0) {
        
    }

    
    public Rectangle getDisplayRect() {
        return new Rectangle(0,0,0,0);
    }
    public  Dimension getCurrentSize(){
        return new Dimension(0,0);
    }

  
    public void updateLightPosition(int xpos, int ypos) {
        
    }
}
