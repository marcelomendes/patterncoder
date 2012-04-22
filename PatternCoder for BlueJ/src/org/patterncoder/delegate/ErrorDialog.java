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

import java.io.PrintWriter;
import java.io.StringWriter;
import org.patterncoder.PatternCoder;

/**
 * Component that show the message and the stacktrace of an exception
 *
 * @author Florian Siebler
 */
public class ErrorDialog extends javax.swing.JDialog
{
    /**
     * Initialize the components
     *
     * @param warning Message to display
     */
    private ErrorDialog(String warning)
    {
        super(PatternCoder.getFrame(), java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle").getString("TITLE"), true);
        initComponents();
        txtMessage.setText(warning);
    }

    /**
     * Initialize the components
     *
     * @param title Title of dialog
     * @param warning Message to display
     * @param throwable Throwable that has been thrown; if null, the tab for the
     * stacktrace will disapeare
     */
    public ErrorDialog(String warning, Throwable throwable)
    {
        this(warning);
        if (throwable == null)
        {
            tbbError.remove(scrStacktrace);
        }
        else
        {
            /**
             * convert stacktrace message into strings, and encapsulated in a
             * JTextArea
             */
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            String trace = stringWriter.toString();
            txtStacktrace.setText(trace);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbbError = new javax.swing.JTabbedPane();
        scrMessage = new javax.swing.JScrollPane();
        txtMessage = new javax.swing.JTextArea();
        scrStacktrace = new javax.swing.JScrollPane();
        txtStacktrace = new javax.swing.JTextArea();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtMessage.setColumns(20);
        txtMessage.setLineWrap(true);
        txtMessage.setRows(5);
        txtMessage.setWrapStyleWord(true);
        scrMessage.setViewportView(txtMessage);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/patterncoder/delegate/Bundle"); // NOI18N
        tbbError.addTab(bundle.getString("MESSAGE"), scrMessage); // NOI18N

        txtStacktrace.setColumns(20);
        txtStacktrace.setLineWrap(true);
        txtStacktrace.setRows(5);
        txtStacktrace.setWrapStyleWord(true);
        scrStacktrace.setViewportView(txtStacktrace);

        tbbError.addTab("Stacktrace", scrStacktrace);

        btnClose.setText(bundle.getString("CLOSE")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbbError, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbbError, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClose)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCloseActionPerformed
    {//GEN-HEADEREND:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JScrollPane scrMessage;
    private javax.swing.JScrollPane scrStacktrace;
    private javax.swing.JTabbedPane tbbError;
    private javax.swing.JTextArea txtMessage;
    private javax.swing.JTextArea txtStacktrace;
    // End of variables declaration//GEN-END:variables
}
