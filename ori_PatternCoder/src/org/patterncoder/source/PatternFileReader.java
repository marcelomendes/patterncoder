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
import java.util.*;
import org.xml.sax.SAXException;
import org.patterncoder.wizard.WizardPanelDescriptor;
import org.patterncoder.wizard.Wizard;
import org.patterncoder.pattern.PatternClass;
import org.patterncoder.pattern.PatternModel;

/**
 * Classes implementing this interface deal with reading data from the Pattern source files.
 *
 * @author Michael Nairn
 */
public interface PatternFileReader {
    
    /**
     * Parses the specified source file, to allow for data manipulation.
     * @param file pattern source file.
     * @throws java.io.IOException thrown if ifle not found.
     */
    public void parseFile(File file)throws IOException, SAXException;
    
    /**
     * Returns the pattern name retrieved from the source file.
     *
     * @return the pattern name.
     */
    public String getPatternName();
    
    /**
     * Returns the pattern description retrieved from the source file.
     *
     * @return the pattern description.
     */
    public String getPatternDesc();
    
    /**
     * Returns the nme of the image used by the pattern.
     *
     * @return the name of the image.
     */
    public String getPatternImage();
    
    /**
     * Returns a List of PatternClass objects that were generated from the source file.
     *
     * @return a List of PatternClass objects.
     */
    public HashMap<Object,PatternClass> getPatternComponents();
    
    /**
     * Returns a list of WizardPanelDescriptor objects from data supplied in a source file.
     * @param model the current data model.
     * @return a list of WizardPanelDescriptor objects.
     */
    public List<WizardPanelDescriptor> getWizardDescriptors(Wizard parent, PatternModel model);
    
}
