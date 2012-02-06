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
package org.patterncoder.dataModel;

import java.awt.Image;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import org.patterncoder.delegate.ErrorDialog;
import org.patterncoder.utils.FileHandler;
import org.patterncoder.utils.XMLUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Florian Siebler
 */
public enum EnumPatterns
{
    BASIC("Basic relationship", "basic"),
    CREATIONAL("Creational Patterns", "creational"),
    BEHAVIORAL("Behavioral Patterns", "behavioral"),
    STRUCTURAL("Structural Patterns", "structural"),
    OTHERS("Non-GoF Patterns", "other");
    /**
     * Description of the category
     */
    private final String DESCRIPTION;
    /**
     * Directory where the patternFiles are stored
     */
    private String SUB_DIR;
    /**
     * Contains all design patterns of this categorie
     */
    private ArrayList<Pattern> patterns;

    /**
     * Initializes the enums
     *
     * @param description Description of Enum
     * @param subDir Directory where the patternFiles are stored
     */
    private EnumPatterns(String description, String subDir)
    {
        this.DESCRIPTION = description;
        this.SUB_DIR = subDir;
        this.resetPatterns();
    }

    /**
     * Creates a new list of patterns
     */
    public void resetPatterns()
    {
        patterns = new ArrayList<Pattern>();
    }

    /**
     * Return the index of the given node
     *
     * @param node Node to search
     * @return Index of node in list
     */
    int getIndexOf(Object node)
    {
        return patterns.indexOf(node);
    }

    /**
     * Returns the number of patterns in this category
     *
     * @return Number of patterns
     */
    public int size()
    {
        return patterns.size();
    }

    /**
     * Return a specified pattern
     *
     * @param index Index of the pattern in this category
     * @return Specified pattern
     */
    public Pattern get(int index)
    {
        return patterns.get(index);
    }

    public void init()
            throws FileNotFoundException, ZipException, IOException, SAXException, ParserConfigurationException, Exception
    {
        Map<String, String> templateList = new HashMap<String, String>();
        File coderDir = FileHandler.findPatternCoderDir();
        File patDir = new File(coderDir, SUB_DIR);
        File[] dateien = patDir.listFiles();
        for (File temp : dateien)
        {
            Pattern pattern = null;
            Image image = null;
            ZipFile zipFile = new ZipFile(temp);
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements())
            {
                ZipEntry eintrag = (ZipEntry) entries.nextElement();
                if (!eintrag.isDirectory())
                {
                    String name = eintrag.getName();
                    String tempName = name.toUpperCase();
                    InputStream inputStream = zipFile.getInputStream(eintrag);

                    //convert InputStream to String
                    int number = inputStream.available();
                    byte[] buffer = new byte[number];
                    inputStream.read(buffer);
                    String content = new String(buffer);

                    if (tempName.endsWith(".XML"))
                    {
                        pattern = readXML(content);
                    }
                    if (tempName.endsWith(".TMPL"))
                    {
                        String[] split = name.split("/");
                        name = split[split.length - 1];
                        templateList.put(name, content);
                    }
                    if (tempName.endsWith(".BMP"))
                    {
                        try
                        {
                            image = ImageIO.read(new ByteArrayInputStream(buffer));
                        }
                        catch (Throwable t)
                        {
                            new ErrorDialog("Could not create image: " + t.getMessage(), t).setVisible(true);
                        }
                    }
                }
            }
            pattern.setImage(image);

            PatternComponent[] allComponents = pattern.getAllComponents();
            for (PatternComponent tempComponent : allComponents)
            {
                String[] tempTemplate = tempComponent.getTemplate().split("/");
                String template = templateList.get(tempTemplate[tempTemplate.length - 1]);
                tempComponent.setTemplate(template);
            }
        }
    }

    /**
     * Reads the pattern description and validates it against the schema file.
     * Adds then the new pattern to the list of patterns
     *
     * @param is Description of pattern in XML-format
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private Pattern readXML(String content)
            throws FileNotFoundException, IOException, SAXException, ParserConfigurationException, Exception
    {
        // Validate data
        String schemaDir = FileHandler.findSchemaDir();
        XMLUtils.validate(schemaDir, content);

        // Read data and create Pattern  
        Document document = XMLUtils.readFile(content);
        Pattern tempPattern = XMLUtils.createPattern(document);

        patterns.add(tempPattern);
        Collections.sort(patterns);

        return tempPattern;
    }

    @Override
    public String toString()
    {
        return DESCRIPTION;
    }
}
