package org.patterncoder.system;

import java.io.File;

public abstract class AbstractSystem
{
    protected final String PATTERN_FILES_DIR_NAME = "PatternFiles";
    protected File filePatternCoderDir = null;
    public final static String SEPARATOR = System.getProperty("file.separator");
    protected static AbstractSystem instance;

    public static AbstractSystem getSystem()
    {
        return instance;
    }

    public abstract String getProjectDir();

    public abstract void setPackage(Object myPackage);

    public abstract int getCloseStrategy();

    /**
     * Reloads the project view; standard: do nothing; can be changed by
     * NetBeans, BlueJ and Eclipse system
     */
    public void reload()
    {
    }

    /**
     * Returns the name of the currently opened package in the IDE; default
     * value is ""
     *
     * @return The name of the package.
     */
    public String getPackageName()
    {
        return "";
    }

    public abstract File getCoderDir();
}
