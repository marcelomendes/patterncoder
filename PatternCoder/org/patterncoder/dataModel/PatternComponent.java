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
package org.patterncoder.dataModel;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.patterncoder.utils.PatternCoderUtils;

/**
 * A Component represent a class or an interface in the pattern<br>
 * Examples: <br>
 *          TYPE = Observer
 *          DESC = Defines an interfache that must be .... 
 *     className = Observer (can be changed by user)
 *      template = Observer.tmpl
 *          STEP = 1
 *   WIZARD_DESC = Use the text field to enter a relevant name for the Observer Interface
 *   WIZARD_NAME = Rename Observer
 * 
 * @author Florian Siebler  
 */
public class PatternComponent implements Comparable
{
    /**
     * Index of component in pattern-creation process
     */
    public final int CLASS_ID;
    /**
     * Short description of component
     */
    public final String COMP_TYPE;
    /**
     * The name of the component in the project; can be changed by user todo:
     * Bezeichner
     */
    private String className;
    /**
     * Description of pattern
     */
    public final String DESC;
    /**
     * The source code for the compoennt
     */
    private String template;
    /**
     * Short description for wizard
     */
    private String WIZARD_NAME;
    /**
     * Describes what the user can do in this step
     */
    private String WIZARD_DESC;
    /**
     * Every step has it's own image. Today, all step have the same image; in
     * nearer future every step will have an own editable image
     */
    private Image image;
    /**
     * List of dependencies
     */
    private List<String> dependencies = new ArrayList<String>();

    public PatternComponent(int classID, String compType, String defaultClassName, String desc, String template)
    {
        this.CLASS_ID = classID;
        this.COMP_TYPE = compType;
        this.className = defaultClassName;
        this.DESC = desc;
        this.template = template;
    }

    /**
     * Returns all dependencies of the component
     * @return Array of dependent component
     */
    public String[] getAllDependencies()
    {
        String[] result = new String[dependencies.size()];
        dependencies.toArray(result);
        return result;
    }

    /**
     * Adds a new dependency to the component
     * @param dependency New dependency
     */
    public void addDependency(String dependency)
    {
        dependencies.add(dependency);
    }

    /**
     * Returns the template of this component
     * @return Source code
     */
    public String getTemplate()
    {
        return template;
    }

    /**
     * Sets the template of this component
     * @param template Source code
     */
    public void setTemplate(String template)
    {
        this.template = template;
    }

    /**
     * Return the className of the component 
     * @return className of component
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Set the name of the component 
     * @param className Name of component
     */
    public void setClassName(String className)
    {
        boolean stringOkay = PatternCoderUtils.verifyName(className);
        if (stringOkay)
        {
            this.className = className;
        }
        else
        {
            String eingabe;
            do
            {
                eingabe = JOptionPane.showInputDialog(null, "Please enter a valid class name: ", "validName");
            }
            while (!PatternCoderUtils.verifyName(eingabe));
        }
    }

    /**
     * Return the image of this component 
     * @return Image of this component
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * Sets the image of this component 
     * @param image Image of component
     */
    public void setImage(Image image)
    {
        this.image = image;
    }

    @Override
    public int compareTo(Object t)
    {
        Integer tempStep = this.CLASS_ID;
        Integer value = ((PatternComponent) t).CLASS_ID;
        return tempStep.compareTo(value);
    }

    public String getWizardName()
    {
        return this.WIZARD_NAME;
    }

    public String getWizardDesc()
    {
        return this.WIZARD_DESC;
    }

    public void addWizardText(String[] wizardInformation) throws Exception
    {
        String id = wizardInformation[0];
        String name = wizardInformation[1];
        String desc = wizardInformation[2];
        if (this.CLASS_ID == Integer.parseInt(id))
        {
            this.WIZARD_NAME = name;
            this.WIZARD_DESC = desc;
        }
        else
        {
            throw new Exception("WizardStep invalid in SourceFile for " + this.COMP_TYPE);
        }
    }
}
