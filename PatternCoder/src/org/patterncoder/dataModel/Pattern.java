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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Describes a design pattern with the attributes name, description, image and
 * components.<br> Examples: name = Observer<br> description = Define a one-to
 * many ...<br> image = Observer.bmp<br> components[0] = Observer<br>
 * components[1] = Observable<br> ... <br>
 *
 * @author Florian Siebler
 */
public class Pattern implements Comparable
{
    /**
     * Name of the pattern
     */
    public final String NAME;
    /**
     * Describes the pattern
     */
    public final String DESC;
    /**
     * Name of the image
     */
    public final String IMAGE_DIR;
    /**
     * List of components
     */
    private List<PatternComponent> components = new ArrayList<PatternComponent>();
    /**
     * Image of pattern
     */
    private Image image;

    public Pattern(String name, String desc, String imageDir)
    {
        this.NAME = name;
        this.DESC = desc.trim();
        this.IMAGE_DIR = imageDir;
    }

    /**
     * Return a component at requested index
     *
     * @param index Index of component
     * @return Component at requested index
     */
    public PatternComponent getComponent(int index)
    {
        return components.get(index);
    }

    /**
     * Returns the Image of the pattern
     *
     * @return Image of pattern
     */
    public Image getImage()
    {
        return image;
    }

    /**
     * Sets the image of the pattern
     *
     * @param image Image of the pattern
     */
    public void setImage(Image image)
    {
        this.image = image;
    }

    /**
     * Return the index of a given component
     *
     * @param component Component whose index is required
     * @return Index of component
     */
    public int getStepNumber(PatternComponent component)
    {
        return components.indexOf(component);
    }

    /**
     * Return the number of components in the pattern. The number of components
     * equals the number of steps in the wizard
     *
     * @return Number of components
     */
    public int stepCount()
    {
        return components.size();
    }

    /**
     * Returns the first component of the pattern
     *
     * @return First Compo or null if pattern has no components
     */
    public PatternComponent getFirstComponent()
    {
        if (components.size() > 0)
        {
            return components.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns all components of the pattern
     *
     * @return All components of the pattern
     */
    public PatternComponent[] getAllComponents()
    {
        PatternComponent[] result = new PatternComponent[components.size()];
        components.toArray(result);
        return result;
    }

    /**
     * Adds a component to the list of components
     *
     * @param component A new component
     */
    public void addComponent(PatternComponent component)
    {
        this.components.add(component);
        Collections.sort(components);
    }

    @Override
    public int compareTo(Object otherPattern)
    {
        Pattern tempPattern = (Pattern) otherPattern;
        String otherDescription = tempPattern.DESC;
        return DESC.compareTo(otherDescription);
    }

    @Override
    public String toString()
    {
        return this.NAME;
    }
}
