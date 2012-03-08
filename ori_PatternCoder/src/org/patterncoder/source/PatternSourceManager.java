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

import java.io.*;
import java.util.ArrayList;

/**
 * Classes implementing this interface deal with providing the location of pattern files.
 *
 * @author Michael Nairn
 */
public interface PatternSourceManager {
    
    /**
     * Returns a pattern image file specified by the name of the image.
     * @return the image file.
     * @param image the image required.
     * @throws java.io.FileNotFoundException thrown if directory not found.
     */
    public File getImage(String image)  throws FileNotFoundException;
    
    /**
     * Returns an ArrayList of Pattern source files. The ArrayList contains objects of type String.
     * @return the list of pattern source files.
     * @throws java.io.FileNotFoundException thrown if directory not found.
     */
    public ArrayList<String> getSourceFiles()  throws FileNotFoundException, Exception;
    
    /**
     * Returns the template file specified by the name of the template.
     * @return the template file.
     * @param template the template required.
     * @throws java.io.FileNotFoundException thrown if directory not found.
     */
    public File getClassTemplate(String template) throws FileNotFoundException;
    
}
