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

import bluej.extensions.BlueJ;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import java.io.FileNotFoundException;
import javax.swing.JPanel;
//import bluej.extensions.*;
import java.awt.CardLayout;
import java.awt.Toolkit;
import java.util.Observer;
import java.util.Observable;
import org.xml.sax.SAXException;

import java.util.*;
import java.io.*;
import javax.swing.ImageIcon;

import org.patterncoder.source.PatternFileReaderDom;
import org.patterncoder.source.PatternFileReader;
import org.patterncoder.pattern.PatternModel;
import org.patterncoder.pattern.PatternImplementer;
import org.patterncoder.wizard.WizardModel;
import org.patterncoder.wizard.WizardPanelDescriptor;





/**
 * PatternCoderFrame controls the flow of the extension execution.
 *
 * @author Michael Nairn
 */
public class PatternCoderFrame extends javax.swing.JFrame implements org.patterncoder.wizard.Wizard, Observer{
    
    private JPanel patChooser;
    private PatternModel model;
    private PatternImplementer pi;
    private CardLayout cardLayout;
    private WizardModel wModel;
    private PatternFileReader reader;
    private WizardPanelDescriptor chooserPanel;
    
    private static final String PAT_CHOICE_PANEL_ID = "ChoicePanel";
    
      
    /**
     * Creates new PatternExtensionFrame
     * @param bluej the bluej proxy object.
     */
    public PatternCoderFrame(BlueJ bluej) throws FileNotFoundException, PatternCoderException, Exception{

        initComponents();
        this.setLocation(200,100);
        wModel = new WizardModel();

        BlueJHandler.getInstance().setBlueJ(bluej);
        model = new PatternModel();//throws PatternCoderException if there was a problem creating the parser
        model.populateModel();//throws FileNotFoundException if PatternFiles were not found.
        setStaticPanels();
        this.model.addObserver(this);
        setDynamicPanels();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        cardLayout = new CardLayout();
        displayPanel = new javax.swing.JPanel();
        navigationPanel = new javax.swing.JPanel();
        panelSeperator = new javax.swing.JSeparator();
        btnCancel = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnFinish = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        txtStep = new javax.swing.JLabel();
        txtStepDesc = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PatternCoder for BlueJ");
        setIconImage(new ImageIcon(PatternCoderFrame.class.getResource("patterncoder.gif")).getImage());
        displayPanel.setLayout(new java.awt.GridBagLayout());

        displayPanel.setLayout(cardLayout);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 650;
        gridBagConstraints.ipady = 390;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(displayPanel, gridBagConstraints);

        navigationPanel.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 779;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        navigationPanel.add(panelSeperator, gridBagConstraints);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 30, 7, 10);
        navigationPanel.add(btnCancel, gridBagConstraints);

        btnBack.setText("< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 7, 0);
        navigationPanel.add(btnBack, gridBagConstraints);

        btnNext.setText("Next >");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 7, 0);
        navigationPanel.add(btnNext, gridBagConstraints);

        btnFinish.setText("Finish");
        btnFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 7, 0);
        navigationPanel.add(btnFinish, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 100, 0, 0);
        navigationPanel.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = -129;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(navigationPanel, gridBagConstraints);

        titlePanel.setLayout(new java.awt.GridBagLayout());

        titlePanel.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 779;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 0);
        titlePanel.add(jSeparator1, gridBagConstraints);

        txtStep.setFont(new java.awt.Font("Tahoma", 1, 12));
        txtStep.setText("txtStep");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 472;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 100);
        titlePanel.add(txtStep, gridBagConstraints);

        txtStepDesc.setText("txtStepDesc");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 411;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 70, 0, 100);
        titlePanel.add(txtStepDesc, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = -129;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(titlePanel, gridBagConstraints);

        setBounds(0, 0, 659, 522);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        back();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        finish();
    }//GEN-LAST:event_btnFinishActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        cancel();
    }//GEN-LAST:event_btnCancelActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnFinish;
    private javax.swing.JButton btnNext;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JSeparator panelSeperator;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel txtStep;
    private javax.swing.JLabel txtStepDesc;
    // End of variables declaration//GEN-END:variables
    
   /*Methods*/

    /**
     * 
     * @param t 
     * @param o 
     */
    public void update(Observable t, Object o){
        setDynamicPanels();
    }
    
    public WizardModel getWizardModel(){
        return this.wModel;
    }
    
 
    private void setButtonStatus(){     
        if(wModel.getCurrentPanelDesc().getNextPanelID() == null){
            btnNext.setVisible(false);
            btnFinish.setVisible(true);
        }
        else{
            btnNext.setVisible(true);
            btnFinish.setVisible(false);
        }
        
        if(wModel.getCurrentPanelDesc().getPreviousPanelID() == null){
            btnBack.setEnabled(false);
           // skipBox.setVisible(true);
        }
        else{
            btnBack.setEnabled(true);
           // skipBox.setVisible(false);
        }
    }//End of setButtonStatus
    
    
    private void setWizardTitle(String step, String stepDesc){
        txtStep.setText(step);
        txtStepDesc.setText(stepDesc);
    }//End of setWizardTitle
    
    
    private void registerWizardPanel(Object id, WizardPanelDescriptor panel) {        
        displayPanel.add(panel.getPanel(), id); 
        wModel.registerWizardPanel(id, panel);
    } 
    
  
    private void setCurrentPanel(Object id) {   
        if(id == null){
            ErrorHandler.printErrorMsg("Unable to display wizard panel");
        }
        
        wModel.setCurrentPanelDesc(id);
  
        cardLayout.show(displayPanel, id.toString()); 
        
        int t = wModel.getCurrentPanelDesc().getStepNumber();
        int tt = wModel.totalNum();
    
        setWizardTitle(wModel.getCurrentPanelDesc().getStep()+" (Step "+t+" of "+tt+")", wModel.getCurrentPanelDesc().getStepDesc());
    }
    
  
    private void setStaticPanels(){
        chooserPanel = new WizardPanelDescriptor(PAT_CHOICE_PANEL_ID, null,null,"Select design pattern", "Select the design pattern you wish to adopt and click next.", new org.patterncoder.wizard.panels.PatternChooser(model));
        registerWizardPanel(chooserPanel.getPanelID(), chooserPanel);
    }
    
    /**
     *
     */
    //Must be called after data model has been created
    public void setDynamicPanels(){
        
        //Should clear and start again to ensure no overflow.
        
        for(int i=0;i<displayPanel.getComponentCount();i++){
            java.awt.Component tempComp = displayPanel.getComponent(i);
            if(!tempComp.equals(chooserPanel.getPanel())){
                displayPanel.remove(i);
            }
        }
        //clears WizardModel 
        wModel.clearWizardModel();
        
        try{
            reader = new PatternFileReaderDom();
        }catch(Exception e){
            ErrorHandler.printErrorMsg("An error occured creating file reader object\nThe extension will now close.");
            System.out.println(e.getMessage());
            exitExtension();
        }
        try{
            reader.parseFile(model.getCurrentSourceFile());
        }catch(IOException ioe){
            ErrorHandler.printErrorMsg("The source file for this pattern can not be found" + model.getCurrentSourceFile() + ioe.toString());
            System.out.println(ioe.getMessage());//
            exitExtension();
        }catch(SAXException saxe){
            ErrorHandler.printErrorMsg("The source file for this pattern may have been changed since the extension was loaded.\nAll pattern source files must conform to a schema. Please ensure that all files do so.\nThe extension will now exit.");
            System.out.println(saxe.getMessage());//
            exitExtension();
        }
        
        List<WizardPanelDescriptor> descriptors = new ArrayList<WizardPanelDescriptor>(reader.getWizardDescriptors(this,model));
        
        for(int i=0;i<descriptors.size();i++){
            if(descriptors.get(i).getPreviousPanelID() == null){
                descriptors.get(i).setPreviousID(PAT_CHOICE_PANEL_ID);
                chooserPanel.setNextPanelID(descriptors.get(i).getPanelID());
            }       
            registerWizardPanel(descriptors.get(i).getPanelID(), descriptors.get(i));
        }
        setCurrentPanel(PAT_CHOICE_PANEL_ID);
        setButtonStatus(); 
        
    }
    
    public void next(){
        try{
            wModel.getCurrentPanelDesc().closingPanel();//throws exception
            setCurrentPanel(wModel.getCurrentPanelDesc().getNextPanelID());
            setButtonStatus();  
        }catch(PatternCoderException bdpe){ErrorHandler.printWarningMsg(bdpe.getMessage());}     
    }
    
    public void back(){
        setCurrentPanel(wModel.getCurrentPanelDesc().getPreviousPanelID());
        setButtonStatus();   
    }
    
    public void finish(){
        try{          
            wModel.getCurrentPanelDesc().closingPanel();//throws exception
            pi = new PatternImplementer();
            pi.usePattern(model.getComponents());
            exitExtension();
        }catch(PatternCoderException bdpe){
            ErrorHandler.printWarningMsg(bdpe.getMessage());
        }catch(IOException ioe){
            ErrorHandler.printErrorMsg("A file required to implement the pattern is missing.\nPlease ensure that all template files are in the correct directory and match the names in the pattern source file.");
            ErrorHandler.logErrorMsg(ioe.getMessage());
            exitExtension();
        }catch(ProjectNotOpenException pnoe){  
            ErrorHandler.printErrorMsg("A problem occured trying to add classes to the current project.\nPlease make sure there is a project open.");
            ErrorHandler.logErrorMsg(pnoe.getMessage());
            exitExtension();
        }catch(PackageNotFoundException pnfe){
            ErrorHandler.printErrorMsg("A problem occured trying to find the package selected.");
            ErrorHandler.logErrorMsg(pnfe.getMessage());
            exitExtension();
        }catch(Exception e){
            ErrorHandler.printErrorMsg("A general exception has occured and the pattern could not be implemented.");
            ErrorHandler.logErrorMsg(e.getMessage());
            exitExtension();
        }    
    }
    
    public void cancel(){
        exitExtension();
    }
    
    private void exitExtension(){
        this.dispose();
    }
       
}// End of PatternExtensionFrame
