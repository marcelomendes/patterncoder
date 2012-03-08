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

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


/**
 * Handles all errors that are produced by the patterns extension.
 * 
 * Provides options to either display a message to the user or log the error into the log file.
 *
 * @author Michael Nairn
 */
public class ErrorHandler {

    private static final boolean DEBUG = System.getProperty("debug") != null ? true : false;
    private static final String ERROR_TITLE = "PatternCoder Error";
    private static final String WARNING_TITLE = "PatternCoder Warning";
    private static final String ERROR_TAB_TITLE = "Message";
    private static final String TRACE_TAB_TITLE = "System Trace";

    /**
     * Logs the message sent to it. Only does so if the system property "debug", is set to true.
     * @param s Message to log.
     */
    public static void logErrorMsg(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    /**
     * Displays an error dialog box to the user, with the specified message.
     * @param error Messsage to display.
     */
    public static void printErrorMsg(String error) {
        printErrorMsg(error, null);
    }

    /**
     * Displays a warning message dialog to the user, with the specified message.
     * @param warning Message to display.
     */
    public static void printWarningMsg(String warning) {
        printWarningMsg(warning, null);
    }

    /**
     * Displays an error dialog box to the user, with the specified message.
     * @param error Messsage to display.
     * @param throwable throwable raised by program exception
     */
    public static void printErrorMsg(String error, Throwable throwable) {
        JOptionPane.showMessageDialog(null,
                constructErrorDialog(error, throwable),
                ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a warning message dialog to the user, with the specified message.
     * @param warning Message to display.
     * @param throwable throwable raised by program exception
     */
    public static void printWarningMsg(String warning, Throwable throwable) {
        JOptionPane.showMessageDialog(null,
                constructErrorDialog(warning, throwable),
                WARNING_TITLE, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * A helper function for constructing error message tab panel for 
     * printWaringMsg and printErrorMsg functions.
     * @param warning message to display 
     * @param throwable throwable raised by program exception
     * @return a tab panel with error messages, and system stack trace
     */
    private static JComponent constructErrorDialog(String warning,
            Throwable throwable) {
        
        Insets defaultInsets = new Insets(0, 5, 10, 5);
        JLabel dummyLabel = new JLabel();
        
        JTextArea errorMsgArea = new JTextArea(warning);
        errorMsgArea.setLineWrap(true);
        errorMsgArea.setWrapStyleWord(true);
        errorMsgArea.setEditable(false);
        errorMsgArea.setColumns(20);
        errorMsgArea.setBackground(dummyLabel.getBackground());
        errorMsgArea.setMargin(defaultInsets);
        errorMsgArea.setFont(dummyLabel.getFont());

        if (throwable != null) {
            JTabbedPane errorMsgTabbedPane = new JTabbedPane();
            errorMsgTabbedPane.setAutoscrolls(true);
            errorMsgTabbedPane.setPreferredSize(new Dimension(400, 250));

            errorMsgTabbedPane.addTab(ERROR_TAB_TITLE, errorMsgArea);
            // convert stacktrace message into strings, and encapsulated 
            // in a JTextArea
            java.io.StringWriter strWriter = new java.io.StringWriter();
            java.io.PrintWriter w = new java.io.PrintWriter(strWriter);
            throwable.printStackTrace(w);

            // put message into a JTextArea
            javax.swing.JTextArea errorTraceArea =
                    new javax.swing.JTextArea(strWriter.toString());
            // errorTraceArea.setEditable(false);
            errorTraceArea.setMargin(defaultInsets);
            JScrollPane errorMsgScrollPane = new JScrollPane(errorTraceArea);
            errorMsgTabbedPane.addTab(TRACE_TAB_TITLE, errorMsgScrollPane);

            return errorMsgTabbedPane;
        } else {
            return errorMsgArea;
        }
    }
}
