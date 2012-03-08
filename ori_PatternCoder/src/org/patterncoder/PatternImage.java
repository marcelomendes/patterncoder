/* Copyright (C) 2005 - 2007 the patternCoder team, http://www.patterncoder.org
 
    This file is part of the patternCoder application
 
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.patterncoder;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

import java.net.URI;

/**
 * PatternImage extends JPanel, and allows an image to be displayed easiliy on the
 * PatternExtension interface. Methods allow the image to be set and displayed at any size.
 *
 * @author Michael Nairn
 */
public class PatternImage extends javax.swing.JPanel {
    
    public Image img;
    private Image scaledImg;
    private String path;
    
    /** Creates a new instance of PatternImage */
    public PatternImage(){ 
        super.setBorder(new javax.swing.border.EtchedBorder());
    }
    
    /**
     * Overrides paint method of JPanel.
     *
     * @param g graphics object.
     */
    public void paint(Graphics g){
        if(scaledImg != null){
            g.drawImage(scaledImg,0,0,this);
        }
        super.paintBorder(g);
    }
    
    /**
     * Sets the image path to the curently selected pattern, and updates the
     * image. The image is scaled to fit the panel.
     * <p>
     * If image file does not exist or is unreadable, no image is displayed.
     * <p>
     * The image size is also defined by the parameters width and height.
     * @param width the desired width of the image.
     * @param height the desired height of the image.
     * @param path Fully qualified path to the image resource
     */
    public void setImage(String path, int width, int height){
        this.path = path;
        File temp = new File(path);
        try{
            if(temp.exists() && temp.canRead()){
                img = ImageIO.read(temp);
                scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            }
            else{scaledImg = null;}
        }catch(IOException ioe){}
        catch(Exception e){}
        this.repaint();
    }
    
    
    /**
     * Sets the image path to the curently selected pattern, and updates the
     * image. The image is scaled to fit the panel.
     * <p>
     * If image file does not exist or is unreadable, no image is displayed.
     * <p>
     * The image size is determined by the size of the Panel.
     * @param path Fully qualified path to the image resource.
     */
    public void setImage(String path){
        this.path = path;
        File temp = new File(path);
        try{
            if(temp.exists() && temp.canRead()){
                img = ImageIO.read(temp);
                scaledImg = img.getScaledInstance(getPanelWidth(), getPanelHeight(), Image.SCALE_SMOOTH);
            }
            else{scaledImg = null;}
        }catch(IOException ioe){}
        catch(Exception e){}
        this.repaint();
    }
    
    /**
     * Returns the width of the current image panel. 
     * Is used within the setImage method.
     * @return The width of the current image panel.
     */
    private int getPanelWidth(){
        return super.getWidth();
    }
    
    /**
     * Returns the height of the current image panel. 
     * Is used within the setImage method.
     * @return The height of the current image panel.
     */
    private int getPanelHeight(){
        return super.getHeight();
    }
    
}
