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
package org.patterncoder.utils;

import bluej.extensions.*;
import java.io.File;
import org.patterncoder.delegate.ErrorDialog;

/**
 * This Singleton class handles the BlueJ proxy object<br> All classes using the
 * proxy should register an instance of this class using the getInstance method.
 *
 * @author Michael Nairn
 */
public class BlueJHandler
{
    /**
     * The instance of this class
     */
    private static BlueJHandler instance = new BlueJHandler();
    private static BlueJ blueJ = null;

    /**
     * Constructor is declared private in order to avoid multiple instances
     * being created.
     */
    private BlueJHandler()
    {
    }

    /**
     * Returns the instance of this class
     *
     * @return The single instance of the BlueJHandler object
     */
    public static synchronized BlueJHandler getInstance()
    {
        return instance;
    }

    /**
     * Sets the BlueJ proxy currently in use.
     *
     * @param thisBlueJ The proxy object currently being used.
     */
    public synchronized void setBlueJ(BlueJ thisBlueJ)
    {
        BlueJHandler.blueJ = thisBlueJ;
    }

    /**
     * Returns the current users BlueJ configuration directory:<br> C:\Documents
     * and Settings\.....\BlueJ
     *
     * @return the current users BlueJ configuration directory.
     */
    public File getUserConfigDir()
    {
        File tempFile = blueJ.getUserConfigDir();
        return tempFile;
    }

    /**
     * Returns the BlueJ directory. <p> C:\BlueJ
     *
     * @return the BlueJ directory.
     */
    public File getBlueJDir()
    {
        return blueJ.getSystemLibDir();
    }

    /**
     * Returns the directory of the currently opened project.
     *
     * @throws ProjectNotOpenException thrown if project not open.
     * @return the Project directory.
     */
    public File getProjectDir()
    {
        File result = null;
        try
        {
            BProject project = blueJ.getCurrentPackage().getProject();
            result = project.getDir();
        }
        catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.getProjectDir()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        return result;
    }

    /**
     * Returns the directory of the currently opened package.
     *
     * @throws ProjectNotOpenException thrown if project not open.
     * @throws PackageNotFoundException thrown if package not found.
     * @return the package directory.
     */
    public File getCurrentPackageDir()
    {
        File result = null;
        try
        {
            result = getCurrentPackage().getDir();
        }
        catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.getCurrentPackageDir()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        catch (PackageNotFoundException ex)
        {
            (new ErrorDialog("BlueJHandler.getCurrentPackageDir()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        return result;
    }

    /**
     * Returns the current package opened in the BlueJ environment.
     *
     * @return the package currently opened.
     * @see bluej.extensions.BPackage
     */
    private BPackage getCurrentPackage()
    {
        return blueJ.getCurrentPackage();
    }

    /**
     * Returns the name of the currently opened package in the BlueJ
     * environment.
     *
     * @return The name of the package.
     */
    public String getCurrentPackageName()
    {
        String result = new String();
        try
        {
            result = blueJ.getCurrentPackage().getName();
        }
        catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.getCurrentPackageName()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        catch (PackageNotFoundException ex)
        {
            (new ErrorDialog("BlueJHandler.getCurrentPackageName()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        finally
        {
            return result;
        }
    }

    /**
     * Adds a class to the current BlueJ project/package.
     *
     * @param className the name of the class to add.
     */
    public void addClass(String className)
    {
        try
        {
            blueJ.getCurrentPackage().newClass(className);
        }
        catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.addClass()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        catch (PackageNotFoundException ex)
        {
            (new ErrorDialog("BlueJHandler.addClass()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        catch (MissingJavaFileException ex)
        {
            (new ErrorDialog("BlueJHandler.addClass()\n" + ex.getMessage(), ex)).setVisible(true);
        }
    }

    /**
     * Reloads the current BlueJ package.
     */
    public void reload()
    {
        try
        {
            blueJ.getCurrentPackage().reload();
        }
        catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        catch (PackageNotFoundException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        }
    }
}
