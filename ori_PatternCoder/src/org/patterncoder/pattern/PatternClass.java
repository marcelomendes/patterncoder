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

import java.io.File;
import java.util.*;

/**
 *PatternClass is used to create a model object of a class which is to be implemented as part 
 *of design pattern.
 *
 * @author Michael Nairn
 */
public class PatternClass {
    
    private String template;
    private String description;
    private String name;
    private String type;
    private int id;
    private List<String> dependancies;
    
    /** 
     * Creates a new instance of PatternClass 
     */
    public PatternClass() {
        dependancies = new ArrayList<String>();
    }
    
    /**
     * Creates a new instance of PatternClass
     * @param id The ID number of the component.
     * @param template The path to the template file used to generate the component.
     * @param description Component description.
     * @param name The name of the component.
     */
    public PatternClass(String id, String compType, String template, String description, String name){
        dependancies = new ArrayList<String>();
        setTemplate(template);
        setDescription(description);
        setClassName(name);
        setId(id);
        setCompType(compType);
    }
    
    /**
     * Sets the template file(.TMPL), which is the basis of the class implementation.
     * @param template The name of the template.
     */
    public void setTemplate(String template){this.template = template;}
    
    /**
     * Gets the template file(.TMPL), which is the basis of the class implementation.
     * @return The template (.TMPL) file needed to create the component.
     */
    public String getTemplate(){return this.template;}
    
    /**
     * Adds a description of the class to the object. This description can then be used during
     * the implementation procedure as required.
     * @param description description The description of the classes purpose.
     */
    public void setDescription(String description){this.description = description;}
    
    /**
     * Returns a string description of the current component.
     * @return Description of the component.
     */
    public String getDescription(){return this.description;}
    
    /**
     *Sets the name of the class.
     *<p>
     *This will automatically be set to the defaultName value supplied in the Pattern source file.
     *If unchanged before complete implementation, the class will be implemented with this default name.
     *
     *@param name The name of the class.
     */
    public void setClassName(String name){this.name = name;}
    
    /**
     * Returns the name of the component object.
     * @return The name of the component.
     */
    public String getName(){return this.name;} 
    
    /**
     * Returns the id of the component.
     * @return The id value of the component.
     */
    public int getId(){return this.id;}
    
    /**
     *Sets an ID value to the class.
     *<p>
     *An id value is required so that any associations between any other classes in the pattern can
     *be made.
     *
     *@param id An integer value represening the classes id.
     */
    public void setId(String id){
        try{
            this.id = Integer.parseInt(id);
        }catch(NumberFormatException nfe){System.out.println("Unable to set class id : "+id);}
    }
    

    /**
     * The PatternClass object maintains a List of other components within the pattern that
     * it must know about during its implementation.
     * <p>
     * This method adds an id value of one of these components to that list.
     * @param depId The id of the component to be added to the list of dependants.
     */
    public void addDependancy(String depId){
        dependancies.add(depId); 
    }
    
    /**
     * Returns the List of dependants for this component.
     * List should be used when generating the source code in order to
     * obtain the correct names of these listed components.
     * @return A List Strings representing the ID numbers of associated components.
     */
    public List<String> getDependants(){
        return dependancies;
    }
    
    /**
     * Sets the Component type i.e class.
     * @param type A string representation of the component type.
     */
    public void setCompType(String type){
        this.type = type;
    }
    
    /**
     * Returns the component type.
     * @return The component type.
     */
    public String getCompType(){
        return this.type;
    }

}//End PatternClass
