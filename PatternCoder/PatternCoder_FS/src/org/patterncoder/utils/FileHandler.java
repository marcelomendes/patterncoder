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

import bluej.extensions.BPackage;
import bluej.extensions.BlueJ;
import bluej.extensions.PackageNotFoundException;
import bluej.extensions.ProjectNotOpenException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.patterncoder.delegate.ErrorDialog;

/**
 *
 * @author Florian Siebler
 */
public class FileHandler
{ 
    static File installDir;
  private final   static String SEPARATOR = System.getProperty("file.separator");

    static void reloadPackage()
    {
        try
        {
            myPackage.reload();
        } catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        } catch (PackageNotFoundException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        }
    }

    static BPackage getMyPackage()
    {
        return myPackage;
    }
    /**
     * Location of PatternFiles folder
     */
    private File extensionDir;
    /**
     * Name of directory below BlueJ-Installation
     */
    private static final String EXTENSION_DIR_NAME = "extensions";
    /**
     * Name of directory containing pattern files
     */
    private static final String PATTERN_FILES_DIR_NAME = "PatternFiles";
    /**
     * Name of directory containing templates
     */
    private static final String CLASS_TEMPLATES = "templates";
    /**
     * Name of directory containing images
     */
    private static final String IMG_DIR = "images";
    /**
     * Extension of pattern files
     */
    private static final String PAT_FILE_EXTENSION = ".xml";
    /**
     * Schema filename
     */
    static final String SCHEMA_FILENAME = "patternschema.xsd";
    /**
     * Directory of patternCoder source files
     */
    private static File filePatternCoderDir;
    /**
     * Recent package
     */
    private static BPackage myPackage;
    /**
     * The BlueJ proxy object
     */
    private static BlueJ bluej;

    private FileHandler()
    {
    }

    /**
     * Sets the BlueJ proxy object
     *
     * @param bluej BlueJ proxy object
     */
    public static void setBlueJ(BlueJ bluej)
    {
        FileHandler.bluej = bluej;
    }

    public static void setPackage(BPackage recentPackage)
    {
        FileHandler.myPackage = recentPackage;
    }

    public static String findSchemaDir() throws FileNotFoundException, IOException
    {
        String result = findPatternCoderDir().getCanonicalPath() + SEPARATOR + SCHEMA_FILENAME;
        return result;
    }

    /**
     * Returns the image directory. This is the location of all the Pattern
     * images.
     *
     * @return the image directory.
     */
    public static File findImageDir() throws FileNotFoundException
    {
        return new File(findPatternCoderDir(), IMG_DIR);
    }

    /**
     * Returns the template directory. This is the location of all the template
     * files.
     *
     * @return the template directory.
     */
    public static File findTemplateDir() throws FileNotFoundException
    {
        return new File(findPatternCoderDir(), CLASS_TEMPLATES);
    }

    /**
     * Discovers the location of the PatternFiles folder, which contains files
     * of all patterns. The PatternFiles folder is installed into the same
     * extensions folder as the PatternsExtensions jar file.<br> BlueJ searches
     * for extensions in three possible locations could be used for holding
     * extensions: <User Home>\bluej\extensions\ <BlueJ Home>\lib\extensions\
     * <BlueJ Project>\extensions\ <br> These locations are checked for the
     * PatternImages folder and if found that location is returned. If no folder
     * is found at any of the locations an Exception is thrown.
     *
     * @return File instance representing the location of the PatternImages
     * folder
     * @throws FileNotFoundException If PatternFile directory could not be found
     */
    public static File findPatternCoderDir() throws FileNotFoundException
    { 
        if (filePatternCoderDir == null)
        {
            BlueJHandler blueJHandler = BlueJHandler.getInstance();
            File baseDir = blueJHandler.getBlueJDir();
            filePatternCoderDir = new File(baseDir, EXTENSION_DIR_NAME + SEPARATOR + PATTERN_FILES_DIR_NAME);
            if (filePatternCoderDir.exists())
            {
                installDir = new File(baseDir, EXTENSION_DIR_NAME);
                return filePatternCoderDir;
            } else
            {
                baseDir = blueJHandler.getUserConfigDir();
                filePatternCoderDir = new File(baseDir, EXTENSION_DIR_NAME + SEPARATOR + PATTERN_FILES_DIR_NAME);
                if (filePatternCoderDir.exists())
                {
                    installDir = new File(baseDir, EXTENSION_DIR_NAME);
                    return filePatternCoderDir;
                } else
                {
                    baseDir = BlueJHandler.getInstance().getProjectDir();
                    filePatternCoderDir = new File(baseDir, EXTENSION_DIR_NAME + SEPARATOR + PATTERN_FILES_DIR_NAME);
                    if (filePatternCoderDir.exists())
                    {
                        installDir = new File(baseDir, EXTENSION_DIR_NAME);
                        return filePatternCoderDir;
                    } else
                    {
                        filePatternCoderDir = null;
                        throw new FileNotFoundException("Unable to find Patterns directory!");
                    }
                }
            }
        } else
        {
            return filePatternCoderDir;
        }
    }

    /**
     * Returns an instance of PatternCoderFileFilter
     *
     * @param filter Filter
     * @return Requested PatternCoderFileFilter
     */
    public static FileFilter getFileFilter(String filter)
    {
        return new FileFilter(filter);
    }

    /**
     * Returns an instance of PatternCoderFileFilter
     *
     * @return Requested PatternCoderFileFilter
     */
    public static FileFilter getFileFilter()
    {
        return new FileFilter("");
    }
}
