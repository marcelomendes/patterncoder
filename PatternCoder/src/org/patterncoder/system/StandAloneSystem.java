package org.patterncoder.system;

import java.io.File;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class StandAloneSystem extends AbstractSystem
{
    public StandAloneSystem()
    {
        AbstractSystem.instance = this;
    }

    @Override
    public void setPackage(Object myPackage)
    {
        // no special package
    }

    @Override
    public int getCloseStrategy()
    {
        return JFrame.EXIT_ON_CLOSE;
    }

    @Override
    public File getCoderDir()
    {
        // find path of jar
        URL url = this.getClass().getResource("StandAloneSystem.class");
        String urlOfInstallation = url.toString();
        int index = urlOfInstallation.indexOf("org/patterncoder/system/StandAloneSystem.class");
        urlOfInstallation = urlOfInstallation.substring(0, index);
        urlOfInstallation = urlOfInstallation.replaceAll("file:", "");
        filePatternCoderDir = new File(urlOfInstallation,
                PATTERN_FILES_DIR_NAME);
        return filePatternCoderDir;
    }

    @Override
    public String getProjectDir()
    {
        String result = "";
        JFileChooser fc = new JFileChooser();
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int state = fc.showSaveDialog(null);
        if (state == JFileChooser.APPROVE_OPTION)
        {
            result = fc.getSelectedFile().getPath();
        }
        return result;
    }

    @Override
    public String toString()
    {
        return "Stand-alone-System";
    }
}
