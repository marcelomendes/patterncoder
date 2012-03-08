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

import java.io.File;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.patterncoder.BlueJHandler;
import org.xml.sax.SAXException;



/**
 * The PatternFileValidator class is used to validate each of the xml source files against a xmlSchema.
 * If a source file does not get validated, it will not be included.
 * @author Michael Nairn
 */
public class PatternFileValidator {
    
    static final String W3C_XML_SCHEMA ="http://www.w3.org/2001/XMLSchema";

    /** system independent portion of the schema file path **/
    static final String EXT_PATH = "extensions/PatternFiles";
    
    /** schema filename **/
    static final String SCHEMA_FILENAME = "patternschema.xsd";
    
    private Schema schema;
    private Validator validator;
    
    /**
     * Creates a new instance of PatternFileValidator, and compiles the validating schema.
     * @throws org.xml.sax.SAXException A SAXException is thrown if the schema file cannot be found, or there is aproblem during comilation of the schema.
     */
    public PatternFileValidator() throws SAXException {
         
        schema = compileSchema(getPatternSchemaFile());
        validator = schema.newValidator();
        validator.setErrorHandler(new ValidatorErrorHandler());
    }
    
    /**
     * Compiles the schema file specified.
     * @param schema The schema file to be comiled.
     * @throws org.xml.sax.SAXException Throws a SAXException if compilation of the schema fails.
     * @return A compiled schema, which can be used in validating.
     */
    public static Schema compileSchema(String schema) throws SAXException{
        return compileSchema(new File(schema));
    }//compileSchema
    
    /**
     * Compiles the schema file specified.
     * @param schemaFile The schema file to be comiled.
     * @throws org.xml.sax.SAXException Throws a SAXException if compilation of the schema fails.
     * @return A compiled schema, which can be used in validating.
     */
    public static Schema compileSchema(File schemaFile) throws SAXException{
        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA);
        return sf.newSchema(schemaFile);
    }//compileSchema
    
    
    /**
     * Validates a source file against the compiled schema.Exceptions are thrown if the validation is Unsuccessful.
     * @param sourceFileName The source file to be validated.
     * @throws org.xml.sax.SAXException Thrown if the source file cannot be validated against the schema.
     * @throws java.io.IOException Thrown if the source file cannot be read or found for any reason.
     */
    public void validateFile(String sourceFileName) throws SAXException, IOException{
        // sourcefile = replaceSpaces(sourcefileName);   // avoids MalformedURLException if file path has spaces
        // validator.validate(new StreamSource(sourcefile));
        File sourceFile = new File(sourceFileName);
        validateFile(sourceFile);
    }
    
    /**
     * Validates a source file against the compiled schema.Exceptions are thrown if the validation is Unsuccessful.
     * @param sourceFile The source file to be validated.
     * @throws org.xml.sax.SAXException Thrown if the source file cannot be validated against the schema.
     * @throws java.io.IOException Thrown if the source file cannot be read or found for any reason.
     */
    public void validateFile(File sourceFile) throws SAXException, IOException{        
        validator.validate(new StreamSource(sourceFile));
    }
    
    
    /**
     * The ErrorHandler catches any errors that occur during validation and throws a new SAXException accordingly.
     */
    class ValidatorErrorHandler implements org.xml.sax.ErrorHandler{
        
        /** Creates a new instance of MyErrorHandler */
        public ValidatorErrorHandler() {
        }
        
        
        /**
         * Catches all exceptions that cause an error, and throws a new exception with the same message.
         * @param sAXParseException The exception thrown during validation.
         * @throws org.xml.sax.SAXException Throws a new SAXException, with slightly modified message.
         */
        public void error(org.xml.sax.SAXParseException sAXParseException) throws org.xml.sax.SAXException {
            throw new SAXException("ERROR: " + sAXParseException.toString());
        }
        
        /**
         * Catches all exceptions that cause an fatal error, and throws a new exception with the same message.
         * @param sAXParseException The exception thrown during validation.
         * @throws org.xml.sax.SAXException Throws a new SAXException, with slightly modified message.
         */
        public void fatalError(org.xml.sax.SAXParseException sAXParseException) throws org.xml.sax.SAXException {
            throw new SAXException("Fatal ERROR: " + sAXParseException.toString());
        }
        
        /**
         * Catches all exceptions that cause a warning, and throws a new exception with the same message.
         * @param sAXParseException The exception thrown during validation.
         * @throws org.xml.sax.SAXException Throws a new SAXException, with slightly modified message.
         */
        public void warning(org.xml.sax.SAXParseException sAXParseException) throws org.xml.sax.SAXException {
            throw new SAXException("Warning: " + sAXParseException.toString());
        }
        
    }
    
    
    /**
     * Replaces spaces in a string with %20
     * Required as Validator#validate causes MalformedURLException if file path has spaces
     * Validator#validate is called in validateFile in this class
     * @param aText    the string to process
     * @return         a copy of the string with spaces replaced by %20
     */
    private String replaceSpaces(String aText){
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character =  iterator.current();
        while (character != CharacterIterator.DONE ){
            if (character == ' ') {
                result.append("%20");
            } else {
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }
    
    
    /**
     * Dynamically create the path for the schema file using system properties.
     * @author Kafai Cheng
     * @return the schema file
     */
    private File getPatternSchemaFile(){
        File programClassDirectory = BlueJHandler.getInstance().getBlueJDir();
        //String programClassPath = System.getProperty("java.class.path").split(System.getProperty("path.separator"))[0];
        // File programClassDirectory = new File(programClassPath).getParentFile();
        File extensionDirectory = new File(programClassDirectory, EXT_PATH);
        File schemaFile = new File(extensionDirectory, SCHEMA_FILENAME);
        return schemaFile;
    }
    
}
