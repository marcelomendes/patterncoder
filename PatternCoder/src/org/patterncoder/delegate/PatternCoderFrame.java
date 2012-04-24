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
package org.patterncoder.delegate;

import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.border.EtchedBorder;
import org.patterncoder.dataModel.EnumPatterns;
import org.patterncoder.dataModel.Pattern;
import org.patterncoder.dataModel.PatternCoderTreeModel;
import org.patterncoder.dataModel.PatternComponent;
import org.patterncoder.utils.PatternCoderUtils;

/**
 *
 * @author Florian Siebler
 */
public class PatternCoderFrame extends javax.swing.JFrame
{
    public static final PatternCoderTreeModel patternTree = new PatternCoderTreeModel();
    private final String BTN_NEXT_FINISH = java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("FINISH");
    private final String BTN_NEXT = java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("NEXT");
    private final String STEP_DESC_DEFAULT = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("SELECT_PATTERN"), new Object[]
            {
                BTN_NEXT
            });
    private final String ABOUT = java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("ABOUT PATTERNCODER");
    private final String SELECT_PATTERN = java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("SELECT DESIGN PATTERN");
    
    /**
     * Current selected pattern
     */
    private Pattern currentPattern;
    /**
     * current selected step of currentPattern
     */
    private PatternComponent currentComponent;
    /**
     * Prefix of strings to be shown
     */
    private final String INFORMATION_PREFIX = "<style type=\"text/css\">body "
            + "{font-size: 12pt; font-family: san-serif;color: #008800 }</style><body>";
    /**
     * Postfix of strings to be shown
     */
    private final String INFORMATION_POSTFIX = "</body>";
    /**
     * Describes what patternCoder is
     */
    private final String WHAT_IS_PATTERNCODER = INFORMATION_PREFIX
            + java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("WHAT_IS_PATTERNCODER"), new Object[]
            {
                INFORMATION_POSTFIX
            });

    /**
     * Creates PatternCoderFrame
     */
    public PatternCoderFrame()
    {
        initComponents();
        txtDescription.setText(WHAT_IS_PATTERNCODER);
        setLocationRelativeTo(null);
        lblStepDesc.setText(STEP_DESC_DEFAULT);
        lblDescription.setText(ABOUT);
        pnlImage.setBorder(new EtchedBorder());
        pnlInput.setVisible(false);
        URL fileURL = getClass().getResource("patterncoder.gif");
        Image image = Toolkit.getDefaultToolkit().getImage(fileURL);
        setIconImage(image);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Shows an image in the panel on the left side
     *
     * @param imagePfad Path to the image file
     */
    private void showImage(Image image)
    {
        pnlImage.setImage(image); 
        pnlImage.repaint(); 
    }

    /**
     * Sets the GUI to a valid state when the users clicks on a leaf that is a
     * pattern
     */
    private void startPattern()
    {
        btnNext.setEnabled(true);
        btnBack.setEnabled(false);
        pnlInput.setVisible(false);
        txtDescription.setText(INFORMATION_PREFIX + currentPattern.DESC + INFORMATION_POSTFIX);
        lblDesignPattern.setText(currentPattern.NAME + java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("Overview"));
        lblStepDesc.setText(java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("FURTHERINFORMATION"));
        currentComponent = null;
        Image image = currentPattern.getImage();
        showImage(image);
    }

    /**
     * Loads a given component in the view
     *
     * @param currentComponent
     */
    private void loadComponent(PatternComponent component)
    {
        edtComponent.setText(component.COMP_TYPE);
        edtName.setText(component.getClassName());
        txtDescription.setText(component.DESC);
        lblStepDesc.setText(component.getWizardDesc());
        String information = java.text.MessageFormat.format(java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("Step_Number"), new Object[]
                {
                    component.CLASS_ID, currentPattern.stepCount(), currentComponent.getWizardName()
                });
        lblDesignPattern.setText(information);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        pnlHeader = new javax.swing.JPanel();
        lblDesignPattern = new javax.swing.JLabel();
        lblStepDesc = new javax.swing.JLabel();
        pnlFooter = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        sprFooter = new javax.swing.JSeparator();
        splPatternCoderMain = new javax.swing.JSplitPane();
        pnlPattern = new javax.swing.JPanel();
        pnlInput = new javax.swing.JPanel();
        lblComponent = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        edtComponent = new javax.swing.JTextField();
        edtName = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JEditorPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        scrPatternTree = new javax.swing.JScrollPane();
        trvPatterns = new javax.swing.JTree();
        pnlImage = new org.patterncoder.delegate.PanelImage();

        jScrollPane1.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PatternCoder for BlueJ");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        pnlHeader.setBackground(new java.awt.Color(255, 255, 255));

        lblDesignPattern.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle"); // NOI18N
        lblDesignPattern.setText(bundle.getString("SELECT DESIGN PATTERN")); // NOI18N

        lblStepDesc.setText(bundle.getString("SELECT_PATTERN")); // NOI18N

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblDesignPattern, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(lblStepDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDesignPattern)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStepDesc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancel.setText(bundle.getString("CANCEL")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnNext.setText(bundle.getString("NEXT")); // NOI18N
        btnNext.setEnabled(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnBack.setText(bundle.getString("BACK")); // NOI18N
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addGap(35, 35, 35)
                .addComponent(btnCancel)
                .addContainerGap())
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnNext)
                    .addComponent(btnBack))
                .addContainerGap())
        );

        splPatternCoderMain.setResizeWeight(0.5);

        lblComponent.setText("Component: ");

        lblName.setText("Name: ");

        edtComponent.setEditable(false);
        edtComponent.setText("jTextField1");

        edtName.setText("jTextField1");

        javax.swing.GroupLayout pnlInputLayout = new javax.swing.GroupLayout(pnlInput);
        pnlInput.setLayout(pnlInputLayout);
        pnlInputLayout.setHorizontalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addComponent(lblComponent)
                        .addGap(18, 18, 18)
                        .addComponent(edtComponent, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                    .addGroup(pnlInputLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addGap(18, 18, 18)
                        .addComponent(edtName)))
                .addGap(188, 188, 188))
        );

        pnlInputLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblComponent, lblName});

        pnlInputLayout.setVerticalGroup(
            pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComponent)
                    .addComponent(edtComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(edtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblDescription.setText("jLabel1");

        txtDescription.setContentType("text/html");
        txtDescription.setEditable(false);
        jScrollPane3.setViewportView(txtDescription);

        javax.swing.GroupLayout pnlPatternLayout = new javax.swing.GroupLayout(pnlPattern);
        pnlPattern.setLayout(pnlPatternLayout);
        pnlPatternLayout.setHorizontalGroup(
            pnlPatternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatternLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPatternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatternLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addComponent(pnlInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlPatternLayout.setVerticalGroup(
            pnlPatternLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatternLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addContainerGap())
        );

        splPatternCoderMain.setRightComponent(pnlPattern);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.5);

        trvPatterns.setModel(patternTree);
        trvPatterns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trvPatternsMouseClicked(evt);
            }
        });
        scrPatternTree.setViewportView(trvPatterns);

        jSplitPane1.setTopComponent(scrPatternTree);

        pnlImage.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pnlImageComponentResized(evt);
            }
        });

        javax.swing.GroupLayout pnlImageLayout = new javax.swing.GroupLayout(pnlImage);
        pnlImage.setLayout(pnlImageLayout);
        pnlImageLayout.setHorizontalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );
        pnlImageLayout.setVerticalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(pnlImage);

        splPatternCoderMain.setLeftComponent(jSplitPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(splPatternCoderMain)
                    .addComponent(sprFooter))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splPatternCoderMain, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sprFooter, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void trvPatternsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_trvPatternsMouseClicked
    {//GEN-HEADEREND:event_trvPatternsMouseClicked
        currentComponent = null;
        Object selectedComponent = trvPatterns.getLastSelectedPathComponent();
        if (selectedComponent.getClass() == Pattern.class)
        /*
         * If the selected node is an instance of Pattern then show it's
         * overview
         */
        {
            currentPattern = (Pattern) selectedComponent;
            startPattern();
        }
        else
        {
            showImage(null);
            lblDescription.setText(ABOUT);
            lblStepDesc.setText(STEP_DESC_DEFAULT);
            txtDescription.setText(WHAT_IS_PATTERNCODER);
            if (selectedComponent.getClass() == EnumPatterns.class)
            {
                EnumPatterns patternCategory = (EnumPatterns) selectedComponent;
                String tempDesc = patternCategory.toString();
                lblDescription.setText(tempDesc);
                txtDescription.setText(patternCategory.getExplanation());
            }
            pnlInput.setVisible(false);
            btnNext.setEnabled(false);
            currentPattern = null;
            lblDesignPattern.setText(SELECT_PATTERN);
        }
        btnBack.setEnabled(false);
    }//GEN-LAST:event_trvPatternsMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNextActionPerformed
    {//GEN-HEADEREND:event_btnNextActionPerformed
        if (currentPattern.isLastComponent(currentComponent))
        {
            try
            {
                currentComponent.setClassName(edtName.getText());
                PatternCoderUtils.createFiles(currentPattern);
            }
            catch (ProjectNotOpenException ex)
            {
                new ErrorDialog("PatternCoderFrame.nextButtonClick" + ex.getMessage(), ex).setVisible(true);
            }
            catch (PackageNotFoundException ex)
            {
                new ErrorDialog("PatternCoderFrame.nextButtonClick" + ex.getMessage(), ex).setVisible(true);
            }
            catch (IOException ex)
            {
                new ErrorDialog("PatternCoderFrame.nextButtonClick" + ex.getMessage(), ex).setVisible(true);
            }
            finally
            {
                this.dispose();
            }
        }
        else
        {
            if (currentComponent == null)
            {
                currentComponent = currentPattern.getFirstComponent();
                pnlInput.setVisible(true);
            }
            else
            {
                btnBack.setEnabled(true);
                currentComponent.setClassName(edtName.getText());
                currentComponent = currentPattern.getNextComponent(currentComponent);
            }
            loadComponent(currentComponent);
            if (currentPattern.isLastComponent(currentComponent))
            {
                btnNext.setText(BTN_NEXT_FINISH);
            }
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBackActionPerformed
    {//GEN-HEADEREND:event_btnBackActionPerformed
        PatternComponent tempFirstComponent = currentPattern.getFirstComponent();
        PatternComponent previousComponent = currentPattern.getPreviousComponent(currentComponent);
        if (tempFirstComponent == previousComponent)
        {
            btnBack.setEnabled(false);
        }
        currentComponent = previousComponent;
        loadComponent(currentComponent);
        btnNext.setText(java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("NEXT >"));
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void pnlImageComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_pnlImageComponentResized
    {//GEN-HEADEREND:event_pnlImageComponentResized
        if (currentPattern != null)
        {
            Image image = currentPattern.getImage();
            showImage(image);
        }
    }//GEN-LAST:event_pnlImageComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNext;
    private javax.swing.JTextField edtComponent;
    private javax.swing.JTextField edtName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel lblComponent;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDesignPattern;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStepDesc;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private org.patterncoder.delegate.PanelImage pnlImage;
    private javax.swing.JPanel pnlInput;
    private javax.swing.JPanel pnlPattern;
    private javax.swing.JScrollPane scrPatternTree;
    private javax.swing.JSplitPane splPatternCoderMain;
    private javax.swing.JSeparator sprFooter;
    private javax.swing.JTree trvPatterns;
    private javax.swing.JEditorPane txtDescription;
    // End of variables declaration//GEN-END:variables
}
