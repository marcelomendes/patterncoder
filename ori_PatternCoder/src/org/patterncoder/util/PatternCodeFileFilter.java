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
package org.patterncoder.util;

import java.io.File;
import java.io.FileFilter;

/**
 * A file filter class for filtering out unwanted files.
 * ＠see java.io.FileFilter
 * @author Kafai Cheng
 */
public class PatternCodeFileFilter implements FileFilter {
    
    /** filename extension that should be accepted **/ 
    private String acceptedExt;
    
    public PatternCodeFileFilter(){
        this("");
    }
    
    public PatternCodeFileFilter(String acceptedExt){
        setAcceptedExt(acceptedExt);
    }

    /**
     * Test whether a file should be accepted
     * @param f a file to be tested
     * @return true if the file is accepted by the filter, and false otherwise.
     */
    public boolean accept(File f) {
        return f.getName().endsWith(getAcceptedExt());
    }

    
    public String getAcceptedExt() {
        return acceptedExt;
    }

    public void setAcceptedExt(String acceptableExt) {
       this.acceptedExt = (acceptableExt == null) ? "" : ("." + acceptableExt);
       this.acceptedExt = this.acceptedExt.startsWith("..") ? this.acceptedExt.substring(1) : this.acceptedExt;
    }
}
