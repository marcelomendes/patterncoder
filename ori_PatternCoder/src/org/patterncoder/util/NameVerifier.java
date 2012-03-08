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

/**
 * Utility class which simply provides methods to check for invalid characters within the names of java source files.
 * @author Michael Nairn
 */
public class NameVerifier {
    
    /**
     * Invalid characters.
     */
    static char[] chars = {'!','"','%','`',' ','~','#','^','&','*','(',')','+','=','{','}','[',']'
            ,'@',':',';','?','>','<','|'};
    
    /**
     * Checks a source name against a list of known invalid characters. Returns a boolean value indicating wether the name is valid or not.
     * @param name The name that requires a check for invalid characters.
     * @return returns true if the name is well formaed and valid. False is returned if the name is invalid.
     */
    public static boolean verifyName(String name){
        boolean wellFormed = true;
        
        if(name.compareTo("")==0){
            wellFormed = false;
        }else{
            for(int i=0;i<chars.length;i++){
                for(int nameChar=0;nameChar<name.length();nameChar++){
                    if(chars[i] == name.charAt(nameChar)){
                        wellFormed = false;
                        break;
                    }
                }
            }
            
        }
        
        return wellFormed;
    }
    
}
