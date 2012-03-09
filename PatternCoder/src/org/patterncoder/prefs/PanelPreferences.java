package org.patterncoder.prefs;

import bluej.extensions.BlueJ;
import bluej.extensions.PreferenceGenerator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.patterncoder.dataModel.EnumPatterns;
import org.patterncoder.delegate.PatternCoderFrame;

/**
 *
 * @author Florian
 */
@SuppressWarnings("serial")
public class PanelPreferences extends javax.swing.JPanel implements PreferenceGenerator
{
    public PanelPreferences(BlueJ bluej)
    {
        initComponents();
    }

    @Override
    public JPanel getPanel()
    {
        return this;
    }

    @Override
    public void loadValues()
    {
        //
    }

    @Override
    public void saveValues()
    {
        //
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        trvPatterns = new javax.swing.JTree();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("All patterns: ");

        trvPatterns.setModel(PatternCoderFrame.patternTree);
        jScrollPane2.setViewportView(trvPatterns);

        jButton1.setText("Import pattern");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jLabel1))
                .addContainerGap(217, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree trvPatterns;
    // End of variables declaration//GEN-END:variables
}
