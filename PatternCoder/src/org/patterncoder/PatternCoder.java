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

import bluej.extensions.BPackage;
import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import bluej.extensions.MenuGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.JMenuItem;
import org.patterncoder.delegate.ErrorDialog;
import org.patterncoder.delegate.PatternCoderFrame;
import org.patterncoder.system.AbstractSystem;
import org.patterncoder.system.BlueJSystem;
import org.patterncoder.system.StandAloneSystem;

/**
 * Main class of the PatternCoder.<br> This version supports BlueJ and
 * stand-alone version.
 *
 * @author Michael Nairn
 */
public class PatternCoder extends Extension implements ActionListener
{
    /**
     * Number of this version
     */
    public static final String VERSION = "0.5.3.004";
    /**
     * Language dependant name of the patternCoder: patternCoder
     */
    public static final String NAME = java.util.ResourceBundle.getBundle(
            "org/patterncoder/Bundle").getString("PATTERNCODER");
    /**
     * Language dependant description of the patternCoder: patternCoder
     */
    public static final String DESCRIPTION = java.util.ResourceBundle.getBundle("org/patterncoder/Bundle").getString("ABOUT");
    /**
     * Language dependant URL of the patternCoder: patternCoder
     */
    public static final String EXT_URL = java.util.ResourceBundle.getBundle(
            "org/patterncoder/Bundle").getString("EXT_URL");
    /**
     * Reference to the runtime environment: BlueJ or stand-alone
     */
    private static AbstractSystem system;
    /**
     * Recent BlueJ package
     */
    private BPackage recentPackage;
    /**
     * Reference to the BlueJ instance
     */
    private BlueJ blueJ;

    /**
     * Returns the recent environment
     *
     * @return Reference to the environment
     */
    public static AbstractSystem getSystem()
    {
        return system;
    }

    /**
     * Starts the patternCoder as stand-alone
     *
     * @param args
     */
    public static void main(String[] args)
    {
        system = new StandAloneSystem();
        new PatternCoderFrame();
    }

    /**
     * Method is called once when the extension is first initialized
     *
     * @param pBlueJ The BlueJ proxy object
     */
    @Override
    public void startup(BlueJ pBlueJ)
    {
        try
        {
            this.blueJ = pBlueJ;
            pBlueJ.setMenuGenerator(new PatternCoderMenuGenerator());
        }
        catch (Throwable t)
        {
            new ErrorDialog("Method startup:\n" + t.getMessage(), t).setVisible(true);
        }
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
     * Returns the name of the Patterns Extension.
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
     * Returns a description of the extension.
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
            return new URL(EXT_URL);
        }
        catch (Exception ex)
        {
            new ErrorDialog(ex.getMessage(), ex).setVisible(true);
            return null;
        }
    }

    /**
     * Initializes a new instance of Pattern frame when menu option is selected.
     * Called when the PatternExtension menu option is selected.
     *
     * @param event The ActionEvent which caused the method to be called.
     */
    @Override
    public void actionPerformed(ActionEvent event)
    {
        try
        {
            system = new BlueJSystem(blueJ);
            system.setPackage(this.recentPackage);
            new PatternCoderFrame();

        }
        catch (Throwable t)
        {
            new ErrorDialog(t.getMessage(), t).setVisible(true);
        }
    }

    /**
     * PatternCoderMenuGenerator creates the menu option that is displayed in
     * the tools menu of the BlueJ environment.
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
            JMenuItem tempItem = new JMenuItem(NAME);
            tempItem.addActionListener(PatternCoder.this);
            tempItem.setToolTipText(DESCRIPTION);
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
        public void notifyPostToolsMenu(BPackage bluejPackage,
                JMenuItem menuItem)
        {
            if (bluejPackage == null)
            {
                menuItem.setEnabled(false);
            }
        }
    }
}
