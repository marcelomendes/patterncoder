/*
 * Copyright (C) 2005 - 2007 the patternCoder team, http://www.patterncoder.org
 *
 * This file is part of the patternCoder application
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.patterncoder;

import org.patterncoder.utils.BlueJHandler;
import org.patterncoder.delegate.ErrorDialog;
import org.patterncoder.delegate.PatternCoderFrame;
import bluej.extensions.BPackage;
import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import bluej.extensions.MenuGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import org.patterncoder.dataModel.EnumPatterns;
import org.patterncoder.prefs.PanelPreferences;
import org.patterncoder.utils.FileHandler;

/**
 * Main class of the Design Patterns Extension. <p> Extends the BlueJ extensions
 * API's Extension class and is loaded by BlueJ at run time.
 *
 * @author Michael Nairn
 */
public class PatternCoder extends Extension implements ActionListener
{
    private final String EN_ERROR_FAIL_OPEN = "A problem has occured and the PatternCoder extension could not open correctly.\nPlease ensure that all pattern source files are in the correct directory and adhere to the correct schema document.\nPlease also ensure that the schema document itself is present in the correct directory.";
    private final String DE_ERROR_FAIL_OPEN = "PatternCoder konnte nicht gestartet werden.\nBitte stellen Sie sicher, dass alle Muster-Dateien im richtigen Verzeichnis liegen und gegen die Schema-Datei validiert werden können.\nStellen Sie auch sicher, dass die Schema-Datei selbst im richtigen Verzeichnis zu finden ist.";
    private final String EN_ERROR_URL = "The link is either dead or otherwise unreachable ";
    private final String DE_ERROR_URL = "Die URL konnte nicht erreicht werden.";
    public static final String VERSION = "0.5.3.004";
    public static final String NAME = "PatternCoder";
    public static final String DESCRIPTION = "Design patterns extension for BlueJ, developed at Glasgow Caledonian University and The University of the West of Scotland";
    public static final String DE_EXT_URL = "http://www.patterncoder.de";
    public static final String EN_EXT_URL = "http://www.patterncoder.org";
    private static PatternCoderFrame patFrame;
    private BPackage recentPackage;
    private BlueJ bluej;

    /**
     * Method is called once when the extension is first initialised
     *
     * @param bluej The BlueJ proxy object
     */
    @Override
    public void startup(BlueJ bluej)
    {
        this.bluej = bluej;
        BlueJHandler.getInstance().setBlueJ(bluej);
        FileHandler.setBlueJ(bluej);

        bluej.setMenuGenerator(new PatternCoderMenuGenerator());

        PanelPreferences myPreferences = new PanelPreferences(bluej);
        bluej.setPreferenceGenerator(myPreferences);
    }

    /**
     * Returns the PatternCoder-Frame
     *
     * @return PatternCoder Frame
     */
    public static JFrame getFrame()
    {
        return patFrame;
    }

    /**
     * Compatible or not.
     *
     * @return A boolean value, representing whether or not it is compatible
     */
    @Override
    public boolean isCompatible()
    {
        return true;
    }

    /**
     * Returns a string representing the version of the Extension.
     *
     * @return The version of the extension
     */
    @Override
    public String getVersion()
    {
        return VERSION;
    }

    /**
     * Returns a set name of the Patterns Extension.
     *
     * @return The name of the Extension
     */
    @Override
    public String getName()
    {
        return NAME;
    }

    /**
     * Called when the Extension is terminated. Allows any extension specific
     * tidying up to be done prior to closing.
     */
    @Override
    public void terminate()
    {
        //
    }

    /**
     * Returns a set description of the extension.
     *
     * @return A description of the extension.
     */
    @Override
    public String getDescription()
    {
        return DESCRIPTION;
    }

    /**
     * Returns a URL address associated with this extension
     *
     * @return A URL address
     */
    @Override
    public URL getURL()
    {
        try
        {
            return new URL(DE_EXT_URL);
        }
        catch (Exception ex)
        {
            ErrorDialog errorDialog = new ErrorDialog(DE_ERROR_URL + "\n" + ex.getMessage(), ex);
            errorDialog.setVisible(true);
            return null;
        }
    }

    /**
     * Initialises a new instance of Pattern frame when menu option is selected.
     * Called when the PatternExtension menu option is selected.
     *
     * @param anEvent The ActionEvent which caused the method to be called.
     */
    @Override
    public void actionPerformed(ActionEvent anEvent)
    {
        try
        {
            EnumPatterns[] enums = EnumPatterns.values();
            for (EnumPatterns tempEnum : enums)
            {
                tempEnum.init();
            }
            FileHandler.setPackage(recentPackage);
            patFrame = new PatternCoderFrame(bluej);
            patFrame.setVisible(true);

        }
        catch (Throwable ex)
        {
            new ErrorDialog(DE_ERROR_FAIL_OPEN + "\n" + ex.getMessage(), ex).setVisible(true);
        }
    }

    /**
     * PatternCoderMenuGenerator creates the menu option that is displayed in
     * the tools menu of the BlueJ Environment.
     *
     * Methods within this class, deal with any actions on that menu option.
     */
    class PatternCoderMenuGenerator extends MenuGenerator
    {
        /**
         * Generates the menu item that is displayed on the tools menu and
         * returns it
         *
         * @param aPackage The current package opened within BlueJ
         * @return A new JMenuItem object with correct header and actions
         */
        @Override
        public JMenuItem getToolsMenuItem(BPackage aPackage)
        {
            recentPackage = aPackage;
            JMenuItem tempItem = new JMenuItem("PatternCoder (German)");
            tempItem.addActionListener(PatternCoder.this);
            return tempItem;
        }

        /**
         * Called when the tools menu is about to be displayed. Checks if a
         * package is currently open and if it is not, greys out the menu option
         *
         * @param bp The current BlueJ Package.
         * @param jmi The extension menu item, on the tools menu.
         */
        @Override
        public void notifyPostToolsMenu(BPackage bluejPackage, JMenuItem menuItem)
        {
            if (bluejPackage == null)
            {
                menuItem.setEnabled(false);
            }
        }
    }
}
