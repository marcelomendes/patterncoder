package org.patterncoder.system;

import bluej.extensions.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import org.patterncoder.delegate.ErrorDialog;

public class BlueJSystem extends AbstractSystem
{
    private BlueJ BLUEJ;
    private BPackage myPackage;
    private final String EXTENSION_DIR_NAME = "extensions";

    public BlueJSystem(BlueJ blueJ)
    {
        super();
        this.BLUEJ = blueJ;
    }

    @Override
    public void reload()
    {
        try
        {
            myPackage.reload();
        }
        catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        catch (PackageNotFoundException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        }
    }

    @Override
    public String getPackageName()
    {
        String result = "";
        try
        {
            result = BLUEJ.getCurrentPackage().getName();
        }
        catch (ProjectNotOpenException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        catch (PackageNotFoundException ex)
        {
            (new ErrorDialog("BlueJHandler.reload()\n" + ex.getMessage(), ex)).setVisible(true);
        }
        return result;
    }

    @Override
    public void setPackage(Object myPackage)
    {
        this.myPackage = (BPackage) myPackage;
    }

    @Override
    public int getCloseStrategy()
    {
        return JFrame.DISPOSE_ON_CLOSE;
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
     * @throws ProjectNotOpenException
     * @throws FileNotFoundException If PatternFile directory could not be found
     */
    @Override
    public File getCoderDir()
    {
        if (filePatternCoderDir == null)
        {
            File baseDir = BLUEJ.getSystemLibDir();
            filePatternCoderDir = new File(baseDir, EXTENSION_DIR_NAME
                    + SEPARATOR + PATTERN_FILES_DIR_NAME);
            if (filePatternCoderDir.exists())
            {
                return filePatternCoderDir;
            }
            else
            {
                baseDir = BLUEJ.getUserConfigDir();
                filePatternCoderDir = new File(baseDir, EXTENSION_DIR_NAME
                        + SEPARATOR + PATTERN_FILES_DIR_NAME);
                if (filePatternCoderDir.exists())
                {
                    return filePatternCoderDir;
                }
                else
                {
                    try
                    {
                        baseDir = BLUEJ.getCurrentPackage().getProject().getDir();
                        filePatternCoderDir = new File(baseDir,
                                EXTENSION_DIR_NAME + SEPARATOR
                                + PATTERN_FILES_DIR_NAME);
                        if (filePatternCoderDir.exists())
                        {
                            return filePatternCoderDir;
                        }
                    }
                    catch (ProjectNotOpenException e)
                    {
                        new ErrorDialog(e.getMessage(), e).setVisible(true);
                    }
                }
            }
            return null;
        }
        else
        {
            return filePatternCoderDir;
        }
    }

    @Override
    public String getProjectDir()
    {
        File result = null;
        BProject projectPath;
        try
        {
            projectPath = BLUEJ.getCurrentPackage().getProject();
            result = projectPath.getDir();
        }
        catch (ProjectNotOpenException e)
        {
            new ErrorDialog(e.getMessage(), e).setVisible(true);
        }
        return result.getPath();
    }

    @Override
    public String toString()
    {
        return "BlueJ-System";
    }
}
