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

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.io.IOException;
import bluej.extensions.ProjectNotOpenException;
import bluej.extensions.PackageNotFoundException;
import org.patterncoder.source.PatternSourceManager;
import org.patterncoder.source.XMLSourceHandler;
import org.patterncoder.BlueJHandler;
import org.patterncoder.PatternCoder;

/**
 * PatternImplementer generates all sources needed for a particular pattern, and is used to add them to the BlueJ project.
 * <p>
 * PatternImplementer first of all generates the java source file for each of the components and copies them to the correct project directory.
 * <p>
 * The classes are then added to the project, and the package reloaded in order to display all associations between classes.
 * 
 * @author Michael Nairn
 */
public class PatternImplementer {
    
    private PatternSourceManager psm;
    
    private final String CLASS_NAME_STRING = "\\$CLASSNAME";
    private final String DEPENDANT_CLASS = "\\$DEPENDANT";
    private final String PACKAGE_NAME = "\\$PKGLINE";
    
    private ArrayList<PatternClass> components;
    
    /**
     * Creates a new instance of PatternImplementer
     */
    public PatternImplementer() {
        this.psm = XMLSourceHandler.getInstance();
    }
    
    /**
     * Creates a new instance of PatternImplementer
     *
     * @param psm PatternSourceManager used to del with the source files (.TMPL file location).
     * @param bjh Instance of the BlueJHandler object used to deal with the BlueJ proxyobject. 
     * @param reader The FileReader object used to read the pattern files.
     */
    public PatternImplementer(PatternSourceManager psm, BlueJHandler bjh){
        this.psm = psm;
    }
    
    /**
     * The usePattern method implemets a pattern into the current BlueJ project.
     * A HashMap of PatternClass objects, specify the classes that should be implemented as part of the pattern.
     * @param classes a HashMap of PatternClass objects and their Object ids.
     * @throws java.io.IOException thrown if a file cannot be opened or if the file cannot be created correctly.
     * @throws bluej.extensions.ProjectNotOpenException thrown if there is no project open.
     * @throws bluej.extensions.PackageNotFoundException thrown if there is no package found.
     */
    public void usePattern(HashMap<Object,PatternClass> classes) throws IOException, ProjectNotOpenException, PackageNotFoundException{
        
        this.components = new ArrayList<PatternClass>();      
        Iterator iter = classes.keySet().iterator();
        
        while(iter.hasNext()){
            components.add(classes.get(iter.next()));
        }
        
        for(int i=0; i< classes.size();i++){
            PatternClass pc = components.get(i); 
            String template = pc.getTemplate();
            String className = pc.getName(); 
            createSourceFile(new File(BlueJHandler.getInstance().getCurrentPackageDir(), className+".java"), psm.getClassTemplate(template), className,  pc.getDependants());
        }
        this.reloadPackage();
    }
    
    /**
     * Generates the java source files.
     * All java source files are copied into the correct project directory as specified.
     * @param destination The package directory that the classes are being added to.
     * @param sourceTemplate The template file used as the basis for the java source.
     * @param className The name of the new class.
     * @param deps A List of components id's that the class must know about.
     * @throws java.io.IOException thrown if the java files cannot be created correctly.
     * @throws bluej.extensions.ProjectNotOpenException thrown if the project is not open.
     * @throws bluej.extensions.PackageNotFoundException thrown if the package cannot be found.
     */
    private void createSourceFile(File destination,File sourceTemplate, String className, List<String> deps) throws IOException, ProjectNotOpenException, PackageNotFoundException{
        
        Pattern classNamePattern = Pattern.compile(CLASS_NAME_STRING);     // this is java.util.regex.Pattern - nothing to do with design patterns!
        Pattern packageNamePattern = Pattern.compile(PACKAGE_NAME);
        String output = "";
        Matcher matcher;
        
        ArrayList<String> deps2 = new ArrayList<String>(deps);
        
        // Some code adopted from The Java Developers Almanac 1.4 by Patrick Chan
              
        FileInputStream fis = new FileInputStream(sourceTemplate);
        FileChannel fc = fis.getChannel();

        ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int)fc.size());
        CharBuffer cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);

        /*Checks for class name and replaces it with correct class name*/
        matcher = classNamePattern.matcher(cbuf);
        output = matcher.replaceAll(className);

        /*Checks for package and replaces it with current package name*/

        matcher = packageNamePattern.matcher(output);
        if(BlueJHandler.getInstance().getCurrentPackageName().compareTo("")!=0){
            output = matcher.replaceAll("package "+BlueJHandler.getInstance().getCurrentPackageName()+";");
        }else{
            output = matcher.replaceAll("");
        }

        /*Checks for dependant components used within this class and replaces the name with
         the correct name instance*/
        if(!deps2.isEmpty()){
            for(int i=0;i<deps2.size();i++){
                String temp = DEPENDANT_CLASS.concat(deps2.get(i));

                Pattern classDepPat = Pattern.compile(temp);
                matcher = classDepPat.matcher(output);
                for(int t=0;t<components.size();t++){
                    PatternClass temp2 = components.get(t);
                    String classDep = String.valueOf(temp2.getId());
                    if(deps2.get(i).compareTo(classDep)==0){
                        output = matcher.replaceAll(temp2.getName());
                    }
                }
            }   
        }

        /*Creates file in correct project/package folder*/
        FileWriter out = new FileWriter(destination);
        
        //Adds message to end of each generated source file.
        String newOutput = output.concat("\n\n/*\n " +
                                              "*Source file generated by patternCoder for BlueJ Version "+ PatternCoder.VERSION +".\n " +
                                              "*For more info, please visit "+PatternCoder.EXT_URL+".\n "+
                                              "*/");
        out.write(newOutput);

        fis.close();
        out.close();
            
       /*Adds the class to the project*/
        addClass(className);
    }//End of createSourceFile
   
    /**
     * Communicates with the BlueJ proxy object in order to add a class to te project.
     * 
     * @param className The name of the class to be added. Should be the same as the name supplied when generating the source file.
     */
    private void addClass(String className){
        BlueJHandler.getInstance().addClass(className);
    }
    
    /**
     * Communicates with the BlueJ proxy object in order to update the display.
     * <p>
     * The package must be reloaded in order for all associations between components to be displayed correctly.
     */
    private void reloadPackage(){
        BlueJHandler.getInstance().reload();
    }
    
}
