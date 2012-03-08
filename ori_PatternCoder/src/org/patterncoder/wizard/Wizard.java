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

package org.patterncoder.wizard;

/**
 * The wizard interface defines an interface that must be implemented by the main wizard class. Methods of this interace deal with the wizard navaigation.
 * @author Michael Nairn
 */
public interface Wizard{
      
    public void next();
    
    public void back();
    
    public void cancel();
    
    public void finish();
    
    /**
     * Returns the WizardModel object being used by the current wizard object.
     * @return The model being used by the current wizard.
     */
    public WizardModel getWizardModel();
       
}
