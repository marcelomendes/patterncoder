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

package org.patterncoder.pattern;

import java.util.Observable;
import java.io.File;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import org.patterncoder.wizard.WizardPanelDescriptor;
import org.xml.sax.SAXException;
import org.patterncoder.source.PatternFileReaderDom;
import org.patterncoder.source.PatternFileReader;
import org.patterncoder.source.PatternSourceManager;
import org.patterncoder.source.XMLSourceHandler;
import org.patterncoder.PatternCoderException;
import org.patterncoder.ErrorHandler;



/**
 * The PatternModel class is used to represent the data that is being used to display information to the user.
 *All data used in the Graphical user interface is stored within this model structure, allowing for a clear seperation
 *of control and data.
 *<p>
 *model in the Model View Controllor architecture.
 *<p>
 *PatternModel extends the Observable class from the java.util library, and allows all objects with the code
 *to registar an interest in any changes being made to the model.
 *
 * @author Michael Nairn
 */
public class PatternModel extends Observable{
        
    
    private List<String> files = new ArrayList<String>();   
    private List<String> patterns = new ArrayList<String>();

    private HashMap<Object,PatternClass> comps;
  
    private String curPattern = "";
    private int curIndex = 0;
    
    private String description = "";
    private String image = "";
    
    private PatternSourceManager sourceHandler;
    private PatternFileReader reader;
     
    
    /**
     * Class constructor.
     * @throws patext.BlueDPException 
     */
    public PatternModel() throws PatternCoderException{
        sourceHandler = XMLSourceHandler.getInstance();
        reader = new PatternFileReaderDom();
    }
    
    /**
     * Class Constructor specifying what PatternSourceManager and PatternFileReader objects to use.
     * 
     * @param edh the PatternSourceManager to use when locating files.
     * @param pfr the PatternFileReader to use when reading files.
     */
    public PatternModel(PatternSourceManager edh, PatternFileReader pfr){
        sourceHandler = edh;
        reader = pfr;
    }
    
    /**
     * Populates the model used to display information.
     * The model is populated by the Pattern files specified by the PatternSourceManager object, all pattern data required is
     * loaded into the model.
     * @throws java.lang.Exception thrown if a general exception occurs.
     * @throws java.io.FileNotFoundException thrown if the file used to populate creates a problem.
     */
    public void populateModel() throws FileNotFoundException, Exception{
        int index = 0;
        files = sourceHandler.getSourceFiles();
        File tempFile;
        int length = files.size();
        for(int i=0;i<length;i++){
            tempFile = new File(files.get(i).toString());   // testing bug fix for issue 5
            try{
                reader.parseFile(tempFile);
                if(reader.getPatternName()!=null){
                    patterns.add(reader.getPatternName());
                }
            }catch(SAXException saxe){
                System.out.println("A problem occured trying to validate"+tempFile);
                System.out.println(saxe.getMessage());//
            }catch(IOException e){
                ErrorHandler.logErrorMsg("Problem reading file: "+tempFile);
            }
        }
  
    }//End of populateModel
 
    
    /**
     *Returns the array of currently loaded patterns
     *
     *@return   the array of patterns currently loaded in memory
     */
    public List<String> getPatterns(){
        return patterns;
    }

    /**
     * Returns the source file of the pattern currently being displayed.
     * @return the source file of the currently displayed pattern.
     */
    public File getCurrentSourceFile(){
        return new File(files.get(curIndex).toString());
    }
    
    
    /**
     * Returns the currently selected patterns decription
     *
     *@return A description of the current pattern
     */
    public String getDescription(){return description;}
    

    /**
     * Sets the description of the current pattern to the specified string value.
     * 
     * @param description the description of the current pattern.
     */
    private void setDescription(String description){
        this.description = description;
    }
    
    /**
     * Sets the currently selected image
     * 
     * @param image the name of the image file used by the current pattern i.e Singleton.bmp
     * @throws java.lang.Exception throws Exception of no image file is found
     */
    private void setImage(String image)throws Exception{
        if (image == null){
            throw new Exception("No image file"); 
        }
        this.image = image;
    }
    
    /*Returns the currently selected image path*/
    /**
     * Returns the pattern image of the current pattern.
     * @return the name of the image file used by the current pattern.
     */
    public String getImage(){return image;}
       
    /**
     * Is called passing details of the currently selected pattern
     *Changes currently selected pattern variables accordingly.
     *Notifys all observing objects of the change
     *
     * @param curPattern The pattern currently selected by the user
     * @param index The index of the pattern in the ComboBox
     */
    public void setCurrentPattern(String curPattern,int index){
        try{
            this.curPattern = curPattern;
            this.curIndex = index;
            File temp = new File(files.get(curIndex).toString());
            reader.parseFile(temp);
            setDescription(reader.getPatternDesc());
            sourceHandler.getImage(reader.getPatternImage()).toString();
            setImage(sourceHandler.getImage(reader.getPatternImage()).toString());
            setComponents();//Put here 07/11/05
        }catch(Exception e){
            e.printStackTrace();
        } 
        setChanged();
        notifyObservers();
    }//End of setCurrentPattern   
    
    /**
     * Creates a list of components of type PatternClass.
     * This list is generated from the currently selected patterns source file and is a List 
     * of all classes used in the implementation of the Pattern.
     *
     * @see PatternClass
     */
    public void setComponents(){
        try{
            reader.parseFile(this.getCurrentSourceFile());
        }catch(Exception e){}
       // comps = new ArrayList<PatternClass>(reader.getPatternComponents());
       comps = reader.getPatternComponents();
    }
    
    /**
     * Returns a hash map containing the PatternClass objects for a pattern as created from the pattern source file.
     * The key values are specified from the class id value from the source file.
     *
     * @return comps a hash map of PatternClass objects with key values from pattern source file.
     * @see PatternClass
     */   
    public HashMap<Object,PatternClass> getComponents(){
        return comps;
    }
    
    /**
     * Returns the PatternClass object that is represented in the model by the supplied Object identifier.
     * @param id the Object id of the component.
     * @return the PatternClass object represented by the id.
     */
    public PatternClass getPatternComp(Object id){
        return comps.get(id);
    }
    
    /**
     * Returns the name of the pattern that is currently selected and is populating the model.
     * @return the name of the current pattern.
     */
    public String getCurrentPatternName(){
        return curPattern;
    }
    
  
     
}//End of PatternModel
