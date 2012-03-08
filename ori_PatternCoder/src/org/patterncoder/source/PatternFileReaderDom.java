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

package org.patterncoder.source;

import javax.xml.validation.*;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.List;
import java.util.ArrayList;
import org.xml.sax.SAXException;
import org.patterncoder.wizard.WizardPanelDescriptor;
import java.util.HashMap;
import org.patterncoder.wizard.Wizard;
import org.patterncoder.pattern.PatternClass;
import org.patterncoder.pattern.PatternModel;
import org.patterncoder.PatternCoderException;


/**
 * Reads in xml files, using the DOM xml parser.
 * Implements the PatternFileReader interface providing methods for retrieving pattern related data
 * from file.
 *
 * @author Michael Nairn
 */
public class PatternFileReaderDom implements PatternFileReader {
    
    private PatternFileValidator validator;
    private DocumentBuilder parser;
    private Document xmlDoc;
       
     /*Tags used within xml files*/
    
    //General Pattern tags
    private final String PATTERN_TAG = "pattern";
    private final String PAT_NAME_TAG = "patternName";
    private final String PAT_DESC_TAG = "patternDescription";
    private final String PAT_IMG_TAG = "patternImage";
   
    //Pattern component tags
    private final String CLASS_TAG = "class";
    private final String CLASS_DESC = "classDescription";
  //  private final String CLASS_DESC2 = "classDescription2";
    private final String CLASS_TEMPLATE = "classTemplate";
    private final String CLASS_DEF_NAME = "defaultName";
    private final String CLASS_ID = "classId";
    private final String DEPENDANT_CLASS = "dependantClass";
    private final String CLASS_TYPE = "compType";
    
    //Pattern wizard tags
    private final String WIZARD_TAG = "wizard";
    private final String WIZARD_STEP_TAG = "step";
    private final String STEP_TYPE = "type";
    private final String STEP_COMP_ID = "compId";
    private final String STEP_ID = "stepId";
    private final String STEP_NAME = "stepName";
    private final String STEP_DESC = "stepDesc";
    private final String STEP_NEXT = "nextStepId";
    private final String STEP_PREVIOUS = "previousStepId";
    
  
    /**
     * Creates new instance of PatternFileReaderDom.
     * <p>
     * Automatically creates a new parser object from the DOM factory methods.
     */
    public PatternFileReaderDom() throws PatternCoderException{
       try{
            validator = new PatternFileValidator();//throws SAXException
            parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();//throws ParserConfigurationError            
       }catch(ParserConfigurationException pce){
            throw new PatternCoderException(pce.getMessage());
       }catch(SAXException saxe){
            throw new PatternCoderException(saxe.getMessage());
       }
    }
    
    /**
     * Creates new instance of PatternFileReaderDom.
     * <p>
     * Allows the parser to be set.
     *
     * @param parser The parser object to use to parse pattern files.
     */
    public PatternFileReaderDom(DocumentBuilder parser) {
        this.parser = parser;
    }
    
    /**
     * Calls the parse method from the instantiated parser object on the supplied source file.
     * <p>
     * Generates a DOM tree object used by the other methods to retrieve information about the patterns.
     * The parseFile method must be called prior to calling any other methods.
     * @param sourceFile The input file to be parsed(XML).
     * @throws java.io.IOException Thrown if file does not exist.
     */
    public void parseFile(File sourceFile) throws IOException, SAXException{
        validator.validateFile(sourceFile.toString());
        xmlDoc = parser.parse(sourceFile);        
    }
    
    /**
     * Returns the name of the pattern from the associated pattern file
     *
     * @return The name of the pattern.
     */
    public String getPatternName(){
        NodeList patternNode = xmlDoc.getElementsByTagName(PATTERN_TAG);
        NamedNodeMap patAtts = patternNode.item(0).getAttributes();

        Node name = patAtts.getNamedItem(PAT_NAME_TAG);
        return name.getNodeValue();     
    }
    
     /**
     * Returns the description of the pattern from the associated pattern file
     *
     * @return The description of the pattern.
     */
    public String getPatternDesc(){
        NodeList patternNode = xmlDoc.getElementsByTagName(PATTERN_TAG);
        Element patternTag = (Element)patternNode.item(0);
        Element desc = (Element) patternTag.getElementsByTagName(PAT_DESC_TAG).item(0);
        return ((Text)desc.getFirstChild()).getData().trim();
    }
    
     /**
     * Returns the image file name of the pattern from the associated pattern file
     *
     * @return The name of the image file i.e Singleton.bmp.
     */
    public String getPatternImage(){
        NodeList patternNode = xmlDoc.getElementsByTagName(PATTERN_TAG);
        NamedNodeMap patAtts = patternNode.item(0).getAttributes();

        Node image = patAtts.getNamedItem(PAT_IMG_TAG);
        return image.getNodeValue();
    }
    
    /**
     * Creates a list of PatternClass objects which have been created using the "class" data
     * from the parsed file. This list contains the classes that should be created as part of the 
     * pattern implementation.
     *
     * @see PatternClass
     * @return list of PatternClass objects.
     */
    public HashMap<Object,PatternClass> getPatternComponents(){
        
        NodeList classNode = xmlDoc.getElementsByTagName(CLASS_TAG);
        Element classTag = (Element)classNode.item(0);
        
        HashMap<Object,PatternClass> patternComps = new HashMap<Object,PatternClass>();
        
        List<PatternClass> patternClasses = new ArrayList<PatternClass>();
        NodeList patternNode = xmlDoc.getElementsByTagName(PATTERN_TAG);
        Element pattern = (Element)patternNode.item(0);
        NodeList classNodes = pattern.getElementsByTagName(CLASS_TAG);

        for(int i=0;i<classNodes.getLength();i++){
            
            Element comp = (Element)classNodes.item(i);
            Element desc = (Element) comp.getElementsByTagName(CLASS_DESC).item(0);
            String classDescription = ((Text)desc.getFirstChild()).getData().trim();
                        
            NamedNodeMap classAtts = classNodes.item(i).getAttributes();            
            Node template = classAtts.getNamedItem(CLASS_TEMPLATE);
            Node name = classAtts.getNamedItem(CLASS_DEF_NAME);
            Node id = classAtts.getNamedItem(CLASS_ID);
            Node compType = classAtts.getNamedItem(CLASS_TYPE);
     
            PatternClass tempClass = new PatternClass(id.getNodeValue().toString(),compType.getNodeValue().toString(),template.getNodeValue().toString(), classDescription, name.getNodeValue().toString());
            
            Element patClass = (Element)classNodes.item(i);
            NodeList classInnerNodes = patClass.getElementsByTagName(DEPENDANT_CLASS);
            
            for(int t=0;t<classInnerNodes.getLength();t++){
                NamedNodeMap depAtts = classInnerNodes.item(t).getAttributes();
                Node dep = depAtts.getNamedItem("value");
                tempClass.addDependancy(dep.getNodeValue().toString());
            }
                     
            patternClasses.add(tempClass);
            patternComps.put(id.getNodeValue(), tempClass);//New
        }      
        return patternComps;
    }
    
    /**
     * Creates a list of WizardPanelDescriptor objects associated with the pattern source file being read in.
     * These descriptors identify the wizard steps involved and are used to create the wizard process structure.
     * 
     * The data model is passed in as the components of the wizard are created here and need this reference.
     * @param model the current data model.
     * @return  a list of WizardPanelDescriptor objects.
     */
    public List<WizardPanelDescriptor> getWizardDescriptors(Wizard parent, PatternModel model){
        List<WizardPanelDescriptor> descriptors = new ArrayList<WizardPanelDescriptor>();
        //should throw exception if empty
       
        NodeList patternNode = xmlDoc.getElementsByTagName(PATTERN_TAG);
        Element pattern = (Element)patternNode.item(0);
        
        NodeList wizardNode = pattern.getElementsByTagName(WIZARD_TAG); 

        Element step = (Element)wizardNode.item(0);
        
        NodeList stepNodes = step.getElementsByTagName(WIZARD_STEP_TAG);
        
        for(int i=0;i<stepNodes.getLength();i++){
            NamedNodeMap stepAtts = stepNodes.item(i).getAttributes();
            
            Node type = stepAtts.getNamedItem(STEP_TYPE);
            Node compId = stepAtts.getNamedItem(STEP_COMP_ID);
            Node stepId = stepAtts.getNamedItem(STEP_ID);
            Node next = stepAtts.getNamedItem(STEP_NEXT);
            Node previous = stepAtts.getNamedItem(STEP_PREVIOUS);
            Node stepName = stepAtts.getNamedItem(STEP_NAME);
            Node stepDesc = stepAtts.getNamedItem(STEP_DESC);
            
            String nextTemp = null;
            String previousTemp = null;
            
            if(next.getNodeValue() == ""){
                nextTemp = null;
            }
            else{
                nextTemp = next.getNodeValue().toString();
            }
            
            if(previous.getNodeValue() == ""){
                previousTemp = null;
            }
            else{
                previousTemp = previous.getNodeValue().toString();
            }
            
            WizardPanelDescriptor tempDescriptor = new WizardPanelDescriptor(stepId.getNodeValue(), nextTemp, previousTemp, stepName.getNodeValue().toString(), stepDesc.getNodeValue().toString(), new org.patterncoder.wizard.panels.ClassPanel(parent, model, compId.getNodeValue()));
            
            descriptors.add(tempDescriptor);
        }
        
        return descriptors;
    }
    
    /**
 * This ErrorHandler prints any Warning , Error or Fatal Error.
 */
 class ValidatorErrorHandler implements org.xml.sax.ErrorHandler{
    
    /** Creates a new instance of MyErrorHandler */
    public ValidatorErrorHandler() {
    }
    

    public void error(org.xml.sax.SAXParseException sAXParseException) throws org.xml.sax.SAXException {
        throw new SAXException("ERROR: " + sAXParseException.toString());
    }

    public void fatalError(org.xml.sax.SAXParseException sAXParseException) throws org.xml.sax.SAXException {
        //System.out.println("FATAL ERROR: " + sAXParseException.toString());
    }

    public void warning(org.xml.sax.SAXParseException sAXParseException) throws org.xml.sax.SAXException {
      //  System.out.println("WARNING: " + sAXParseException.toString());
    }
    
}
    
}
