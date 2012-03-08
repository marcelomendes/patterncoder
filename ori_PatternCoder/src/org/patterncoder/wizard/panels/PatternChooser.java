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

package org.patterncoder.wizard.panels;

import java.util.Observer;
import java.util.Observable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import org.patterncoder.pattern.PatternModel;
import org.patterncoder.PatternCoderException;


/**
 * PatternChooser is an extension of the JPanel class, and deals with the initial selection of a Design Pattern.
 * A model is used by this class in order to create the view.
 *
 * @author  Michael Nairn
 * @see JPanel
 */
//public class PatternChooser extends javax.swing.JPanel implements Observer {
public class PatternChooser extends AbstractWizardPanel{

    /*Instance of the model containing data about the current pattern*/
    private PatternModel model;
    private org.patterncoder.wizard.WizardModel wModel;


    /**
     * Class Constructor specifying the model object that is used for the view.
     * @param model the model object to be used.
     */
    public PatternChooser(PatternModel model){
        this.model = model;
        this.model.addObserver(this);
        initComponents();
        this.setSize(642, 378);
        addPatterns(model().getPatterns());
    }


    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        pnlChooser1 = new javax.swing.JPanel();
        cmbChooser1 = new javax.swing.JComboBox();
        pnlDiagram1 = new javax.swing.JPanel();
        patternImage2 = new org.patterncoder.PatternImage();
        pnlDescription1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription1 = new javax.swing.JEditorPane();
        pnlChooser = new javax.swing.JPanel();
        cmbChooser = new javax.swing.JComboBox();
        pnlDiagram = new javax.swing.JPanel();
        patternImage1 = new org.patterncoder.PatternImage();
        pnlDescription = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JEditorPane();

        jPanel1.setLayout(null);

        pnlChooser1.setLayout(new java.awt.GridBagLayout());

        pnlChooser1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pattern Options"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 277;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 28, 10);
        pnlChooser1.add(cmbChooser1, gridBagConstraints);

        jPanel1.add(pnlChooser1);
        pnlChooser1.setBounds(10, 10, 320, 80);

        pnlDiagram1.setLayout(new java.awt.GridBagLayout());

        pnlDiagram1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pattern Diagram"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 286;
        gridBagConstraints.ipady = 236;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDiagram1.add(patternImage2, gridBagConstraints);

        jPanel1.add(pnlDiagram1);
        pnlDiagram1.setBounds(10, 90, 320, 280);

        pnlDescription1.setLayout(new java.awt.GridBagLayout());

        pnlDescription1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pattern Information"));
        jScrollPane2.setBorder(null);
        txtDescription1.setEditable(false);
        txtDescription1.setContentType("text/html");
        txtDescription1.setOpaque(false);
        jScrollPane2.setViewportView(txtDescription1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 259;
        gridBagConstraints.ipady = 308;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 10, 10);
        pnlDescription1.add(jScrollPane2, gridBagConstraints);

        jPanel1.add(pnlDescription1);
        pnlDescription1.setBounds(340, 10, 300, 360);

        setLayout(new java.awt.GridBagLayout());

        pnlChooser.setLayout(new java.awt.GridBagLayout());

        pnlChooser.setBorder(javax.swing.BorderFactory.createTitledBorder("Pattern Options"));
        pnlChooser.setMinimumSize(new java.awt.Dimension(308, 76));
        pnlChooser.setPreferredSize(new java.awt.Dimension(308, 76));
        cmbChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChooserActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 277;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 28, 10);
        pnlChooser.add(cmbChooser, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        add(pnlChooser, gridBagConstraints);

        pnlDiagram.setLayout(new java.awt.GridBagLayout());

        pnlDiagram.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pattern Diagram"));
        pnlDiagram.setMinimumSize(new java.awt.Dimension(308, 280));
        pnlDiagram.setPreferredSize(new java.awt.Dimension(308, 280));
        pnlDiagram.setRequestFocusEnabled(false);
        patternImage1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                patternImage1ComponentResized(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 286;
        gridBagConstraints.ipady = 236;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDiagram.add(patternImage1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(1, 10, 8, 0);
        add(pnlDiagram, gridBagConstraints);

        pnlDescription.setLayout(new java.awt.GridBagLayout());

        pnlDescription.setBorder(javax.swing.BorderFactory.createTitledBorder("Pattern Information"));
        pnlDescription.setMinimumSize(new java.awt.Dimension(308, 360));
        pnlDescription.setPreferredSize(new java.awt.Dimension(308, 360));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        txtDescription.setEditable(false);
        txtDescription.setContentType("text/html");
        txtDescription.setBackground(new java.awt.Color(255,255,255));
        jScrollPane1.setViewportView(txtDescription);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 269;
        gridBagConstraints.ipady = 308;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 8);
        pnlDescription.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 8, 7);
        add(pnlDescription, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void patternImage1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_patternImage1ComponentResized
        setImage(model().getImage());
    }//GEN-LAST:event_patternImage1ComponentResized

    /*Called when an action is performed on the drop down list
     *Informs model of any change to currently selected patern
     */
    private void cmbChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbChooserActionPerformed
        model().setCurrentPattern(cmbChooser.getSelectedItem().toString(),cmbChooser.getSelectedIndex());
    }//GEN-LAST:event_cmbChooserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbChooser;
    private javax.swing.JComboBox cmbChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.patterncoder.PatternImage patternImage1;
    private org.patterncoder.PatternImage patternImage2;
    private javax.swing.JPanel pnlChooser;
    private javax.swing.JPanel pnlChooser1;
    private javax.swing.JPanel pnlDescription;
    private javax.swing.JPanel pnlDescription1;
    private javax.swing.JPanel pnlDiagram;
    private javax.swing.JPanel pnlDiagram1;
    private javax.swing.JEditorPane txtDescription;
    private javax.swing.JEditorPane txtDescription1;
    // End of variables declaration//GEN-END:variables

    /*Methods*/

    protected PatternModel model(){return model;}

    /*Called when the PatternModel is updated
     *Description and image should be updated
     */
    /**
     * Updates the model when a change occurs.
     * Implemented as part of the Observer interface.
     * @param t Observable object
     * @param o Object
     * @see Observer
     */
    public void update(Observable t, Object o){
        setDescription(model().getDescription());
        setImage(model().getImage());
    }

    /*Sets the description of the current pattern*/
    private void setDescription(String s){
        txtDescription.setText("<style type=\"text/css\">body " +
                "{font-size: 12pt; font-family: san-serif;color: #008800 }" +
                "</style>" +
                "<body>" +
                s +
                "</body>");
        try{
            txtDescription.setCaretPosition(0);
        }catch(Exception e){}
    }//End of setDescription

    /**
     * Sets the image of the current pattern
     * @param s the image name.
     */
    private void setImage(String s){
       // patternImage1.setImage(s, 300, 250);
        patternImage1.setImage(s);
    }//End of setImage

    /*Adds the patterns to the drop down list.
     *Pattern information is stored in the model.
     */
    private void addPatterns(List<String> patternArray){
        List<String> temp = new ArrayList<String>(patternArray);

        int length = temp.size();
        for(int i=0;i<length;i++){
            cmbChooser.addItem(temp.get(i));
        }
    }//End of addPattern

    /**
     * Returns the currently selected patterns source file.
     *
     * @return the currently selected patterns source file.
     */
    public    /**
     *
     * @throws patext.BlueDPException
     */
 File getCurrentPattern(){
        return model().getCurrentSourceFile();
    }


    /**
     * Called when the panel is about to close to carry out any tidying up operations required. Throws a new BlueDPException if this fails for any reason.
     * @throws patext.BlueDPException thrown if a problem occurs during the closing of the panel.
     */
    public void closingPanel() throws PatternCoderException{
    }

    /**
     * Called when the panel is about to open. Can carry out any initialisation operations the panel may require. Throws a new BlueDPException if this fails for any reason.
     * @throws patext.BlueDPException thrown if a problem occurs during the opening of the panel.
     */
    public void displayingPanel() throws PatternCoderException{
    }


}
