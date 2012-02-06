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
package org.patterncoder.source;

import bluej.extensions.ProjectNotOpenException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.patterncoder.BlueJHandler;
import org.patterncoder.ErrorHandler;
import org.patterncoder.util.PatternCodeFileFilter;
import org.xml.sax.SAXException;

/**
 * Deals with the directories required by the extension.
 * Any objects requireing directory information, should declare an instance of this class.
 *
 * @author Michael Nairn
 */
public class XMLSourceHandler implements PatternSourceManager {

    /*Singleton*/
    private static XMLSourceHandler singleton = null;
    private PatternFileValidator validator;
    private File extensionDir;//Location of PatternFiles folder
    private final String EXTENSION_DIR_NAME = "extensions";
    private final String PATTERN_FILES_DIR_NAME = "PatternFiles";
    private final String CLASS_TEMPLATES = "templates";
    private final String IMG_DIR = "images";
    private final String PAT_FILE_EXTENSION = ".xml";

    /**
     * Class constructor.
     * Declared private in order to prevent more than one object being created.
     */
    private XMLSourceHandler() {
    }

    /**
     * Controls the creation of the singleton object.
     *
     * @return the single instance of XMLSourceHandler.
     */
    public static synchronized XMLSourceHandler getInstance() {
        if (singleton == null) {
            singleton = new XMLSourceHandler();
        }
        return singleton;
    }

    /*Methods*/
    /**
     * Returns the image file of the associated image name. The image returned will be located
     * in the extension directory.
     * @return the pattern image.
     * @param image the name of the image required.
     * @throws java.io.FileNotFoundException thrown if the image file cannot be found.
     */
    public File getImage(String image) throws FileNotFoundException {
        return new File(getImageDir(), image);
    }

    /**
     * Returns an ArrayList of File objects. The File objects refer to the source files of each 
     * of the design patterns.
     * The source files are located in the PatternFiles directory in the extension directory.
     * @return a list of source files.
     * @throws java.lang.Exception thrown if a general exception occurs.
     * @throws java.io.FileNotFoundException thrown if the source file can not be found.
     */
    public ArrayList<String> getSourceFiles() throws FileNotFoundException, Exception {
        validator = new PatternFileValidator();
        ArrayList<String> sources = new ArrayList<String>();
        //String extDir = getExtensionDir()+"/";
        // String extDir = getExtensionDir()+ System.getProperties().getProperty("file.separator"); 
        // String[] temp = getExtensionDir().list();

        PatternCodeFileFilter patternFileFilter = new PatternCodeFileFilter(PAT_FILE_EXTENSION);

        File[] patternFiles = getExtensionDir().listFiles(patternFileFilter);

        File patternFile = null;
        for (int i = 0; i < patternFiles.length; i++) {
            try {
                patternFile = patternFiles[i];
                validator.validateFile(patternFile);
                sources.add(patternFile.getCanonicalPath());
            } catch (SAXException saxe) {
                
                ErrorHandler.logErrorMsg("Could not validate file " + patternFile.getAbsolutePath() + saxe.getMessage());
            } catch (IOException ioe){
                ErrorHandler.printErrorMsg("problem open file", ioe);
                ErrorHandler.logErrorMsg("Could not access file " + patternFile.getAbsolutePath() + ioe.getMessage());
            }
        }
        return sources;
    }

    /**
     * Returns the template file of a given template name.
     * Searches the template directory for a template with the given name.
     * @return the template file.
     * @param template the name of the template file.
     * @throws java.io.FileNotFoundException thrown if the template file can not be found.
     */
    public File getClassTemplate(String template) throws FileNotFoundException {
        return new File(getTemplateDir(), template);
    }

    /**
     * Returns the image directory. This is the location of all the Pattern images.
     *
     * @return the image directory.
     */
    private File getImageDir() throws FileNotFoundException {
        return new File(getExtensionDir(), IMG_DIR);
    }

    /**
     * Returns the template directory. This is the location of all the template files.
     * 
     * @return the template directory.
     */
    private File getTemplateDir() throws FileNotFoundException {
        return new File(getExtensionDir(), CLASS_TEMPLATES);
    }

    /**
     * Returns the extension directory. The extension directory is the location of the PatternFiles directory,
     * and all the Pattern files.
     *
     * @return the extension directory. 
     */
    private File getExtensionDir() throws FileNotFoundException {
        this.extensionDir = findExtensionDir();
        return this.extensionDir;
    }

    /**
     * Discovers the location of the PatternFiles folder, which contains files of all
     * patterns. The PatternFiles folder is installed into the same extensions folder as
     * the PatternsExtensions jar file.
     * <p>
     * BlueJ searches for extensions in three possible locations could be used for holding extensions:
     * <User Home>\bluej\extensions\
     * <BlueJ Home>\lib\extensions\
     * <BlueJ Project>\extensions\
     * 
     * These locations are checked for the PatternImages folder and if found that location is returned.
     * If no folder is found at any of the locations an Exception is thrown.
     * @return File instance representing the location of the PatternImages folder.
     * @throws java.lang.Exception throws exception if PatternFile directory does not exist.
     */
    private File findExtensionDir() throws FileNotFoundException {
        File f1 = new File(BlueJHandler.getInstance().getBlueJDir(), EXTENSION_DIR_NAME + "/" + PATTERN_FILES_DIR_NAME);
        if (f1.exists()) {
            return f1;
        } else {
            f1 = new File(BlueJHandler.getInstance().getUserConfigDir(), EXTENSION_DIR_NAME + "/" + PATTERN_FILES_DIR_NAME);
            if (f1.exists()) {
                return f1;
            } else {
                    f1 = new File(BlueJHandler.getInstance().getProjectDir(), EXTENSION_DIR_NAME + "/" + PATTERN_FILES_DIR_NAME);
               
                if (f1.exists()) {
                    return f1;
                } else {
                    throw new FileNotFoundException("Unable to find Patterns directory" + f1);
                }
            }
        }
    }//End of findExtensionDir   
}
