/*
 * BlueJHandler.java
 *
 * Date: 21 September 2005
 *
 */

package org.patterncoder;

import bluej.extensions.*;
import java.io.*;

/** 
 *Handles the BlueJ proxy object.
 *All classes using the proxy should register an instance of this class using the getInstance method.
 *
 * @author Michael Nairn
 */
public class BlueJHandler {
    
    /*Singleton*/
    private static BlueJHandler singleton = null;
    
    public static BlueJ thisBlueJ = null;
    
    /** 
     * Class Constructor, declared private in order to avoid multiple instances being created.
     */
    private BlueJHandler() {}
    
    /**
     * Controls the creation and management of the singleton object.
     *
     * @return the single instance of the BlueJHandler object.
     */
    public static synchronized BlueJHandler getInstance(){
        if(singleton == null){
            singleton = new BlueJHandler();
        }
        return singleton;
    }
    
     /**
     * Sets the BlueJ proxy currently in use.
      *
     * @param thisBlueJ The proxy object currently being used.
     */
    public synchronized void setBlueJ(BlueJ thisBlueJ){
       // this.thisBlueJ = thisBlueJ;
       BlueJHandler.thisBlueJ = thisBlueJ;
    }
    
    /**
     * Returns the current users BlueJ configuration directory.
     * <p>
     * C:\Documents and Settings\.....\BlueJ
     *
     * @return the current users BlueJ configuration directory.
     */
    public File getUserConfigDir(){
        return thisBlueJ.getUserConfigDir();
    }
    
    
    /**
     * Returns the BlueJ directory.
     * <p>
     * C:\BlueJ
     *
     * @return the BlueJ directory.
     */
    public File getBlueJDir(){
        return thisBlueJ.getSystemLibDir();
    }
    
    /**
     * Returns the directory of the currently opened project.
     *
     * @throws bluej.extensions.ProjectNotOpenException thrown if project not open.
     * @return the Project directory.
     */
    public File getProjectDir()throws ProjectNotOpenException{
        return thisBlueJ.getCurrentPackage().getProject().getDir();
    }
    
    /**
     * Returns the directory of the currently opened package.
     *
     * @throws bluej.extensions.ProjectNotOpenException thrown if project not open.
     * @throws bluej.extensions.PackageNotFoundException thrown if package not found.
     * @return the package directory.
     */
    public File getCurrentPackageDir()throws ProjectNotOpenException,PackageNotFoundException{
        return getCurrentPackage().getDir();
    }
    
    /**
     * Returns the current package opened in the BlueJ environment.
     *
     * @return the package currently opened.
     * @see bluej.extensions.BPackage
     */
    private BPackage getCurrentPackage(){
        return thisBlueJ.getCurrentPackage();
    }
    
    /**
     * Returns the name of the currently opened package in the BlueJ environment.
     *
     * @throws bluej.extensions.ProjectNotOpenException thrown if project not open.
     * @throws bluej.extensions.PackageNotFoundException thrown if package not found.
     * @return the name of the package.
     */
    public String getCurrentPackageName() throws ProjectNotOpenException,PackageNotFoundException{
        return thisBlueJ.getCurrentPackage().getName();
    }
    
    // throws ProjectNotOpenException, PackageNotFoundException, MissingJavaFileException
    /**
     * Adds a class to the current BlueJ project/package.
     * 
     * @param className the name of the class to add.
     */
    public void addClass(String className){
        try{
            thisBlueJ.getCurrentPackage().newClass(className);
        }catch(Exception e){System.out.println(e);}
    }
   
    //throws ProjectNotOpenException,PackageNotFoundException  
    /**
     * Reloads the current BlueJ package.
     */
    public void reload(){
        try{
        thisBlueJ.getCurrentPackage().reload();
        }catch(Exception e){System.out.println(e);}
    }
    
}