/*
 * PatternsExtension.java  
 *
 * Date: 14 September 2005
 *
 */

package org.patterncoder;

import bluej.extensions.event.*;
import bluej.extensions.*;
import java.net.URL;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 *Main class of the Design Patterns Extension.
 *<p>
 *Extends the BlueJ extensions API's Extension class and is loaded by BlueJ at run time.
 *
 * @author Michael Nairn
 */
public class PatternCoder extends Extension implements PackageListener{
    
    public static final String VERSION = "1.0.0";
    public static final String NAME = "PatternCoder";
    public static final String DESCRIPTION = "Design Pattern Extension for BlueJ, developed at Bell College and Glasgow Caledonian University";
    public static final String EXT_URL = "http://www.patterncoder.org";
    
    private PatternCoderFrame patFrame;
    
    private BlueJ bluej;
    

    /*
     *Implementation specific methods
     */
    
    /**
     * tartup is called once when the extension is first initialised
     * @param bluej The BlueJ proxy object.
     */
    public void startup(BlueJ bluej){  
        System.setProperty("debug", "true");
        this.bluej = bluej;
        bluej.setMenuGenerator(new PatternCoderMenuGenerator());
        bluej.addPackageListener(this);      
    }
    
    /*Implemented methods from interface packageListener*/
    
    /**
     * This method is inherited through the PackageListener interface.
     * Is called whenever a package opening event occurs.
     *
     * @param ev The PackageEvent which caused this method to be called.
     */
    public void packageOpened( PackageEvent ev ) {
    }
    
    /**
     * This method is inherited through the PackageListener interface.
     * Is called whenever a package closing event occurs.
     *
     * @param ev The PackageEvent which caused this method to be called.
     */
    public void packageClosing( PackageEvent ev ) {
    }
    
    
    /*Methods inherited from Extension*/
    
    /**
     * Compatible or not.
     * @return A boolean value, representing whether or not it is compatible.
     */
    public boolean isCompatible() {
        return true;
    }
    
    /**
     * Returns a string representing the version of the Extension.
     * @return The version of the extension
     */
    public String  getVersion() {
        return VERSION;
    }
    
    /**
     * Returns a set name of the Patterns Extension.
     * @return The name of the Extension
     */
    public String  getName() {
        return NAME;
    }
    
    /**
     * Called when the Extension is terminated.
     * Allows any extension specific tidying up to be done prior to closing. 
     */
    public void terminate() {
    }
    
    /**
     * Returns a set description of the Pattern Extension.
     * @return A description of the extension.
     */
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Returns a URL address associated with this Extension
     * @return A URL address
     */
    public URL getURL() {
        try {
            return new URL(EXT_URL);
        } catch ( Exception urlExce ) {
            // The link is either dead or otherwise unreachable
            ErrorHandler.logErrorMsg("URL Exception"+urlExce.getMessage());
            return null;
        }
    } 
    
    /**
     * PatternCoderMenuGenerator creates the menu option that is displayed in the tools menu of the
     * BlueJ Environment.
     * 
     * Methods within this class, deal with any actions on that menu option.
     */
    class PatternCoderMenuGenerator extends MenuGenerator{
        
        /**
         * Generates the menu item that is displayed on the tools menu and returns it.
         *
         * @param aPackage The current package opened within BlueJ 
         * @return A new JMenuItem object with correct header and actions. 
         */
        public JMenuItem getToolsMenuItem(BPackage aPackage) {
            return new JMenuItem(new PatternAction("PatternCoder", "Tools menu:"));
        }
        
    
        /**
         * Called when the tools menu is about to be displayed. Checks if a package is currently open
         * and if it is not, greys out the menu option
         * @param bp The current BlueJ Package.
         * @param jmi The extension menu item, on the tools menu.
         */
        public void notifyPostToolsMenu(BPackage bp, JMenuItem jmi) {

            if(bp == null){
                jmi.setEnabled(false);
            }
        }//End of notifyPostToolsMenu
        

        /**
         * The nested class that instantiates the pattern menu.
         */
        class PatternAction extends AbstractAction {
            private String msgHeader;
            
            /**
             * Creates a new PatternAction object with specified properties.
             *
             * @param menuName The menu option name that appears on the Tools menu e.g Use design Pattern
             * @param msg msgHeader
             */
            public PatternAction(String menuName, String msg) {
                putValue(AbstractAction.NAME, menuName);
                msgHeader = msg;
            }

            /**
             * Initialises a new instance of Pattern frame when menu option is selected.
             * Called when the PatternExtension menu option is selected.
             *
             * @param anEvent The ActionEvent which caused the method to be called.
             */
            public void actionPerformed(ActionEvent anEvent) {
                try{
                    patFrame = new PatternCoderFrame(bluej);
                    patFrame.setVisible(true);
                }catch(PatternCoderException bdpe){
                    ErrorHandler.printErrorMsg("A problem occured whilst validating one or more XML files.\nPlease make sure that the schema document is present in the correct directory.");
                    ErrorHandler.logErrorMsg("BlueDP Error:\n"+bdpe.getMessage());
                }catch(FileNotFoundException fnfe){
                    ErrorHandler.printErrorMsg("Unable to open pattern source directory.\n\n" +
                    "Please ensure Pattern Files folder is located in the extensions directory.");
                    ErrorHandler.logErrorMsg(fnfe.getMessage());
                }catch(Exception e){
                    ErrorHandler.printErrorMsg("A problem has occured and the Design Patterns extension could not open correctly\nPlease ensure that all pattern source files are in the correct directory and adhere to the correct schema document.\nPlease also ensure that the schema document itself is present in the correct directory.");
                    ErrorHandler.logErrorMsg(e.getMessage());
                }
            }          
        }//End of PatternAction
    }//End of PatternCoderMenuGenerator 
    
    
    
}