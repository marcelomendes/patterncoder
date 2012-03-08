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

package org.patterncoder.wizard;

import javax.swing.JPanel;
import org.patterncoder.PatternCoderException;
import org.patterncoder.wizard.panels.AbstractWizardPanel;


/**
 * This class is used to hold details of a stage within the wizard.
 * Details include the Component which is displayed and the next and previous steps.
 *
 * @author Michael Nairn
 */
public class WizardPanelDescriptor {
    
    private String wizardStep = "";//Displayed at top
    private String wizardStepDesc = "";
    private int stepNum;
    
    private Object panelID = "";//Unique reference
    
    private Object nextID = "";
    private Object previousID = "";
    
  //  private JPanel panel;
    private AbstractWizardPanel panel;
    
    /**
     * Creates a new instance of WizardPanelDescriptor, giving variable values for each of the
     * required variables of this class.
     * @param panelID the id used to identify this panel.
     * @param next the id of the panel next inline in the wizard.
     * 
     * @param previous the id of the panel previously inline in the wizard.
     * @param step the step name, displayed in bold at the top of the wizard.
     * @param desc the description of the stage of the wizard, displayed at the top of the wizard.
     * @param panel the component which is displayed.
     */
    public WizardPanelDescriptor(Object panelID,Object next,Object previous, String step, String desc, AbstractWizardPanel panel) {
        this.wizardStep = step;
        this.wizardStepDesc = desc;
        this.panel = panel; 
        this.panelID = panelID;
        this.nextID = next;
        this.previousID = previous;
    }
    
    /**
     * Returns the step string supplied when created.
     * The step value is displayed at the top of the wizard panel, as title 
     * to te step.
     * @return the wizard step string.
     */
    public String getStep(){
        return wizardStep;
    }
    
    /**
     * Returns the step description.
     * The step description is displayed at the top of the wizard to provide the user with a 
     * small amount of instruction for the step.
     * @return the step description.
     */
    public String getStepDesc(){
        return wizardStepDesc;
    }
    
    /**
     * Returns the component registered.
     * @return the JPanel component of this wizard step.
     */
    public JPanel getPanel(){
        return this.panel;
    }
    
    /**
     * returns the id value of this descriptor.
     * @return the step id.
     */
    public Object getPanelID(){
        return this.panelID;
    }
    
    /**
     * Returns the identity oblect value of the next step inline to be displayed.
     * @return the next panel id.
     */
    public Object getNextPanelID(){
        return this.nextID;
    }
    
    /**
     *  Returns the identity object value of the previous step inline to be displayed.
     * @return the previous step id.
     */
    public Object getPreviousPanelID(){
        return this.previousID;
    }
    
    /**
     * Sets the next Panel id.
     * @param id the panel id.
     */
    public void setNextPanelID(Object id){
        this.nextID = id;
    }
    
    /**
     * Sets the previous panel id.
     * @param id the panel id.
     */
    public void setPreviousID(Object id){
        this.previousID = id;
    }
    
    /**
     * Returns an integer value representing the position of this panel in the wizard process.
     * Used to display the step number at the top of the wizard.
     * @return the step number.
     */
    public int getStepNumber(){
        return stepNum;
    }
    
    /**
     * Sets step number value of this descriptor.
     * @param num the step number value.
     */
    public void setStepNum(int num){
        stepNum = num;
    }
    
    
    public void closingPanel() throws PatternCoderException{
        panel.closingPanel();
    }
    
    public void displayingPanel() throws PatternCoderException{
        panel.displayingPanel();
    }
    
}
