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
import java.nio.ByteBuffer;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.xml.parsers.ParserConfigurationException;
import org.patterncoder.delegate.ErrorDialog;
import org.patterncoder.system.AbstractSystem;
import org.patterncoder.utils.XMLUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Florian Siebler
 */
public enum EnumPatterns
{
    BASIC(java.util.ResourceBundle.getBundle(
    "org/patterncoder/dataModel/Bundle").getString("NAME_BASIC"),
    "basic", java.util.ResourceBundle.getBundle(
    "org/patterncoder/dataModel/Bundle").getString("DESC_BASIC")), CREATIONAL(
    java.util.ResourceBundle.getBundle(
    "org/patterncoder/dataModel/Bundle").getString(
    "NAME_CREATIONAL"), "creational", java.util.ResourceBundle.getBundle("org/patterncoder/dataModel/Bundle").getString(
    "DESC_CREATIONAL")), BEHAVIOURAL(
    java.util.ResourceBundle.getBundle(
    "org/patterncoder/dataModel/Bundle").getString(
    "NAME_BEHAVIORAL"), "behavioral", java.util.ResourceBundle.getBundle("org/patterncoder/dataModel/Bundle").getString(
    "DESC_BEHAVIORAL")), STRUCTURAL(
    java.util.ResourceBundle.getBundle(
    "org/patterncoder/dataModel/Bundle").getString(
    "NAME_STRUCTURAL"), "structural", java.util.ResourceBundle.getBundle("org/patterncoder/dataModel/Bundle").getString(
    "DESC_STRUCTURAL")), OTHERS(
    java.util.ResourceBundle.getBundle(
    "org/patterncoder/dataModel/Bundle").getString(
    "NAME_NON_GOF"), "other", java.util.ResourceBundle.getBundle("org/patterncoder/dataModel/Bundle").getString(
    "DESC_NON_GOF"));
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
     * Gives a description of the category
     */
    private String EXPLAIN;

    /**
     * Initializes the enums
     *
     * @param description Description of Enum
     * @param subDir Directory where the patternFiles are stored
     */
    private EnumPatterns(String description, String subDir, String explain)
    {
        this.DESCRIPTION = description;
        this.SUB_DIR = subDir;
        this.EXPLAIN = explain;
        this.resetPatterns();
    }

    /**
     * Returns an Explanation of the category
     *
     * @return Explanation of category
     */
    public String getExplanation()
    {
        return EXPLAIN;
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

    public void init(AbstractSystem system) throws ZipException, IOException
    {
        this.resetPatterns();
        Map<String, String> templateList = new HashMap<String, String>();
        File coderDir = system.getCoderDir();
        File patDir = new File(coderDir, SUB_DIR + AbstractSystem.SEPARATOR);
        if (!patDir.exists())
        {
            patDir.mkdir();
        }
        File[] dateien = patDir.listFiles();
        if (dateien != null && dateien.length > 0)
        {
            for (File temp : dateien)
            {
                Pattern pattern = null;
                Image image = null;
                ZipFile zipFile = new ZipFile(temp);
                @SuppressWarnings("rawtypes")
                Enumeration entries = zipFile.entries();

                ByteBuffer buffer = ByteBuffer.allocate(1024);

                while (entries.hasMoreElements())
                {
                    buffer.clear();

                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (!entry.isDirectory())
                    {
                        String name = entry.getName();
                        String tempName = name.toUpperCase();
                        InputStream inputStream = zipFile.getInputStream(entry);

                        // convert InputStream to String int number =
                        // inputStream.available();

                        int read;
                        while ((read = inputStream.read(buffer.array(),
                                buffer.position(), buffer.remaining())) != -1)
                        {
                            buffer.position(buffer.position() + read);
                            if (buffer.remaining() == 0)
                            {
                                ByteBuffer copy = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                copy.put(buffer);
                                buffer = copy;
                            }
                        }

                        if (tempName.endsWith(".XML"))
                        {
                            String content_XML = new String(buffer.array(), 0,
                                    buffer.position());
                            pattern = readXML(content_XML);
                        }
                        if (tempName.endsWith(".TMPL"))
                        {
                            String content = new String(buffer.array(), 0,
                                    buffer.position());
                            String[] split = name.split("/");
                            name = split[split.length - 1];
                            templateList.put(name, content);
                        }
                        if (tempName.endsWith(".BMP")
                                || tempName.endsWith(".JPG")
                                || tempName.endsWith(".GIF"))
                        {
                            try
                            {
                                ByteArrayInputStream tempStream = new ByteArrayInputStream(
                                        buffer.array(), 0, buffer.position());
                                image = ImageIO.read(tempStream);
                            }
                            catch (Throwable t)
                            {
                                new ErrorDialog(
                                        java.util.ResourceBundle.getBundle(
                                        "org/patterncoder/dataModel/Bundle").getString("IMAGE_NOT_CREATED")
                                        + t.getMessage(), t).setVisible(true);
                            }
                        }
                    }
                }
                pattern.setImage(image);

                PatternComponent[] allComponents = pattern.getAllComponents();
                for (PatternComponent tempComponent : allComponents)
                {
                    String[] tempTemplate = tempComponent.getTemplate().split(
                            "/");
                    String template = templateList.get(tempTemplate[tempTemplate.length - 1]);
                    tempComponent.setTemplate(template);
                }
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
    private Pattern readXML(String content_XML)
    {
        Pattern pattern = null;
        try
        {
            // Read and validate data
            Document document = XMLUtils.readAndValidate(content_XML);
            // create pattern
            pattern = XMLUtils.createPattern(document);
            patterns.add(pattern);
            Collections.sort(patterns);
        }
        catch (SAXException ex)
        {
            new ErrorDialog(ex.getMessage(), ex).setVisible(true);
        }
        catch (ParserConfigurationException ex)
        {
            new ErrorDialog(ex.getMessage(), ex).setVisible(true);
        }
        catch (IOException ex)
        {
            new ErrorDialog(ex.getMessage(), ex).setVisible(true);
        }
        catch (Exception ex)
        {
            new ErrorDialog(ex.getMessage(), ex).setVisible(true);
        }
        return pattern;
    }

    /**
     * Returns a ComboBoxModel containing all pattern Categories
     *
     * @return ComboBoxModel
     */
    public ComboBoxModel getComboBoxModel()
    {
        return new DefaultComboBoxModel(EnumPatterns.values());
    }

    @Override
    public String toString()
    {
        return DESCRIPTION;
    }
}
